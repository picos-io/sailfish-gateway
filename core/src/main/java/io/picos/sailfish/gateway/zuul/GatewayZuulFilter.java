package io.picos.sailfish.gateway.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.picos.sailfish.gateway.auth.ApplicationService;
import io.picos.sailfish.gateway.auth.AuthzService;
import io.picos.sailfish.gateway.exception.ApplicationDisabledException;
import io.picos.sailfish.gateway.exception.ApplicationNotFoundException;
import io.picos.sailfish.gateway.model.Application;
import io.picos.sailfish.gateway.model.Header;
import io.picos.sailfish.gateway.support.GatewayProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

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
        return SecurityContextHolder.getContext() != null &&
            SecurityContextHolder.getContext().getAuthentication() != null;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String requestUri = ctx.getRequest().getRequestURI();
        final String requestMethod = ctx.getRequest().getMethod();
        final Application application = requestedApplication(requestUri);
        final String applicationCode = application.getCode();
        final UserDetails user = currentUser();

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Receiving user = %s, application = %s, operation = %s , uri = %s",
                                       user.getUsername(),
                                       applicationCode,
                                       requestMethod,
                                       requestUri));
        }

        try {
            authzService.authorize(user, applicationCode, requestMethod, requestUri);
            ctx.setSendZuulResponse(true);

            try {
                String username = URLEncoder.encode(user.getUsername(), "UTF-8");
                final String headerKey = getXForwardedUsername(application);
                ctx.addZuulRequestHeader(headerKey, username);

                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Gateway adding request HEADER: %s=%s ",
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
                logger.debug(String.format("Authorized user = %s, application = %s, operation = %s , uri = %s",
                                           user.getUsername(),
                                           applicationCode,
                                           requestMethod,
                                           requestUri));
            }

        } catch (Throwable e) {
            String authorizationHeader = ctx.getRequest().getHeader("Authorization");
            logger.error(String.format("Authorization[%s] & Request[%s %s] goes failure, the reason is %s",
                                       authorizationHeader,
                                       requestMethod,
                                       requestUri,
                                       e.getMessage()), e);

            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            if (null == ctx.getResponseBody()) {
                final String errMsg =
                    String.format("{\"method\": \"%s\",\"requestUri\": \"%s\",\"errorMessage\": \"%s\"}",
                                  requestMethod,
                                  requestUri,
                                  e.getMessage());
                ctx.setResponseBody(errMsg);
                ctx.getResponse()
                   .setContentType(MediaType.APPLICATION_JSON.getType());
            }
        }

        return null;
    }

    private UserDetails currentUser() {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
                                                                                          .getAuthentication();
        return (UserDetails) authentication.getUserAuthentication().getPrincipal();
    }

    private Application requestedApplication(String requestUri) {
        final String applicationCode = StringUtils.split(requestUri, "/")[0];
        Application application = applicationService.findByCode(applicationCode);
        if (application == null) {
            throw new ApplicationNotFoundException(String.format("Unsupported application '%s'", applicationCode));
        }

        if (!application.isEnabled()) {
            throw new ApplicationDisabledException(String.format("The application '%s' is disabled", applicationCode));
        }

        return application;
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
