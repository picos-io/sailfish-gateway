package io.picos.sailfish.gateway.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.picos.sailfish.gateway.auth.ApplicationService;
import io.picos.sailfish.gateway.auth.AuthzService;
import io.picos.sailfish.gateway.exception.ApplicationDisabledException;
import io.picos.sailfish.gateway.exception.ApplicationNotFoundException;
import io.picos.sailfish.gateway.exception.AuthorizationHeaderRequiredException;
import io.picos.sailfish.gateway.model.Application;
import io.picos.sailfish.gateway.model.Header;
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

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

public class GatewayZuulFilter extends ZuulFilter {

    private static final Log logger = LogFactory.getLog(GatewayZuulFilter.class);

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AuthzService authzService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String uri = ctx.getRequest().getRequestURI();

        //ignore spring boot actuator
        return !(uri.startsWith("/health") || uri.startsWith("/info"));
    }

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
        Header header = application.getHeader();
        if (header == null) {
            return gatewayProperties.getHttpHeaderUserId();
        }
        String userIdKey = header.getUserIdKey();
        if (StringUtils.isEmpty(userIdKey)) {
            return gatewayProperties.getHttpHeaderUserId();
        }
        return userIdKey;
    }

    private String getXForwardedUsername(Application application) {
        Header header = application.getHeader();
        if (header == null) {
            return gatewayProperties.getHttpHeaderUserName();
        }
        String userNameKey = header.getUserNameKey();
        if (StringUtils.isEmpty(userNameKey)) {
            return gatewayProperties.getHttpHeaderUserName();
        }
        return userNameKey;
    }

}
