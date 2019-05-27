package io.picos.sailfish.gateway.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.picos.sailfish.gateway.auth.ApplicationService;
import io.picos.sailfish.gateway.auth.AuthzService;
import io.picos.sailfish.gateway.exception.ApplicationDisabledException;
import io.picos.sailfish.gateway.exception.ApplicationNotFoundException;
import io.picos.sailfish.gateway.exception.AuthorizationHeaderRequiredException;
import io.picos.sailfish.gateway.model.Application;
import io.picos.sailfish.gateway.model.Forwarded;
import io.picos.sailfish.gateway.model.User;
import io.picos.sailfish.gateway.support.GatewayProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GatewayZuulFilter extends ZuulFilter {

    private static final Log logger = LogFactory.getLog(GatewayZuulFilter.class);

    public final String[] ACTUATOR_FETURE_URI = new String[]
        {"auditevents",//	显示当前应用程序的审计事件信息
            "beans",//	显示应用Spring Beans的完整列表
            "caches",//	显示可用缓存信息
            "conditions",//	显示自动装配类的状态及及应用信息
            "configprops",//	显示所有 @ConfigurationProperties 列表
            "env",//	显示 ConfigurableEnvironment 中的属性
            "flyway",//	显示 Flyway 数据库迁移信息
            "health",//	显示应用的健康信息（未认证只显示status，认证显示全部信息详情）
            "info",//	显示任意的应用信息（在资源文件写info.xxx即可）
            "liquibase",//	展示Liquibase 数据库迁移
            "metrics",//	展示当前应用的 metrics 信息
            "mappings",//	显示所有 @RequestMapping 路径集列表
            "scheduledtasks",//	显示应用程序中的计划任务
            "sessions",//	允许从Spring会话支持的会话存储中检索和删除用户会话。
            "shutdown",//	允许应用以优雅的方式关闭（默认情况下不启用）
            "threaddump",//	执行一个线程dump
            "ttptrace"};

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AuthzService authzService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String uri = ctx.getRequest().getRequestURI();

        //ignore spring boot actuator
        return !(uri.startsWith("/health") ||
            uri.startsWith("/info") ||
            uri.startsWith("/routes") ||
            uri.startsWith("/actuator"));
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is
     * the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation
     * ignores it.
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String requestURI = ctx.getRequest().getRequestURI();
        final String requestMethod = ctx.getRequest().getMethod();

        //resolveApplication
        final String applicationCode = StringUtils.split(requestURI, "/")[0];
        Application application = applicationService.findByCode(applicationCode);
        if (application == null) {
            throw new ApplicationNotFoundException(String.format("Unsupported application '%s'", applicationCode));
        }

        if (!application.isEnabled()) {
            throw new ApplicationDisabledException(String.format("The application '%s' is disabled", applicationCode));
        }

        String authorizationHeader = ctx.getRequest().getHeader("Authorization");
        //always forward the authorization header
        ctx.addZuulRequestHeader("Authorization", authorizationHeader);

        if (authorizationHeader == null || authorizationHeader.trim().length() == 0) {
            logger.warn("Gateway: Authorization header is missing");
            throw new AuthorizationHeaderRequiredException(
                "OAuth2 Token not present in the request header with Authorization");
        }

        final String accessToken = authorizationHeader.substring(authorizationHeader.lastIndexOf(" ") + 1);

        try {
            final User user = authzService.authorize(accessToken, applicationCode, requestMethod, requestURI);
            ctx.setSendZuulResponse(true);

            try {
                final String userId = URLEncoder.encode(user.getId(), "UTF-8");
                final String headerKey = getXForwardedUserid(application);
                ctx.addZuulRequestHeader(headerKey, userId);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Zuul adding request HEADER: %s=%s ",
                                               headerKey,
                                               userId));
                }
            } catch (UnsupportedEncodingException e) {
                logger.warn(e.toString());
            }

            try {
                String username = URLEncoder.encode(user.getUsername(), "UTF-8");
                final String headerKey = getXForwardedUsername(application);
                ctx.addZuulRequestHeader(headerKey, username);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Zuul adding request HEADER: %s=%s ",
                                               headerKey,
                                               username));
                }
            } catch (UnsupportedEncodingException e) {
                logger.warn(e.toString());
            }

            if (application.isJwtProtected() && application.getJwtOption() != null) {
                //TODO JWT

            }

            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Authorized: user = %s, application = %s, operation = %s , uri = %s",
                                           user.getUsername(),
                                           applicationCode,
                                           requestMethod,
                                           requestURI));
            }

        } catch (Throwable e) {
            logger.error(String.format("Authorizing [token=%s, method=%s, requestUri=%s] goes failure, the reason is %s",
                                       requestMethod,
                                       requestURI,
                                       accessToken,
                                       e.getMessage()));

            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            if (null == ctx.getResponseBody()) {
                final String errMsg =
                    String.format("{\"method\": \"%s\",\"requestUri\": \"%s\",\"errorMessage\": \"%s\"}",
                                  requestMethod,
                                  requestURI,
                                  e.getMessage());
                ctx.setResponseBody(errMsg);
                ctx.getResponse()
                   .setContentType(MediaType.APPLICATION_JSON.getType());
            }
        }

        return null;
    }

    private String getXForwardedUserid(Application application) {
        Forwarded xForwared = application.getForwarded();
        if (xForwared == null) {
            return gatewayProperties.getXForwardedUserid();
        }
        String xForwaredUserid = xForwared.getUserIdKey();
        if (StringUtils.isEmpty(xForwaredUserid)) {
            return gatewayProperties.getXForwardedUserid();
        }
        return xForwaredUserid;
    }

    private String getXForwardedUsername(Application application) {
        Forwarded xForwared = application.getForwarded();
        if (xForwared == null) {
            return gatewayProperties.getXForwardedUsername();
        }
        String xForwaredUsername = xForwared.getUsernameKey();
        if (StringUtils.isEmpty(xForwaredUsername)) {
            return gatewayProperties.getXForwardedUsername();
        }
        return xForwaredUsername;
    }

}
