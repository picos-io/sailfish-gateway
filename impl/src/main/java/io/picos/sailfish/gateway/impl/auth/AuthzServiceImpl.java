package io.picos.sailfish.gateway.impl.auth;

import io.picos.sailfish.gateway.auth.AuthzService;
import io.picos.sailfish.gateway.auth.IamService;
import io.picos.sailfish.gateway.exception.AuthzFailureException;
import io.picos.sailfish.gateway.exception.HasNoPermissionException;
import io.picos.sailfish.gateway.model.ApiPermission;
import io.picos.sailfish.gateway.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dz
 */
@Service("authzService")
public class AuthzServiceImpl implements AuthzService {

    private static final Log logger = LogFactory.getLog(AuthzServiceImpl.class);

    @Autowired
    private IamService iamService;

    @Autowired
    private TokenStore tokenStore;

    public String loadUsernameByAccessToken(String token) {
        final OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(token);
        if (auth2Authentication == null) {
            logger.warn(String.format("Not found the oauth2 authentication for token %s", token));
            throw new InvalidTokenException(token);
        }
        final Authentication authentication = auth2Authentication.getUserAuthentication();
        if (authentication == null) {
            logger.warn(String.format("Not found the authentication for token %s", token));
            throw new InvalidTokenException(token);
        }
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails == null) {
            logger.warn(String.format("Not found the user for token %s", token));
            throw new InvalidTokenException(token);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Resolve token '%s' to authentication '%s'", token, userDetails.getUsername()));
        }
        return userDetails.getUsername();
    }

    @Override
    public User authorize(String token, String application, String httpMethod, String requestUri) {
        final String username = loadUsernameByAccessToken(token);
        final User user = iamService.findUserByName(username);
        if (user == null) {
            throw new AuthzFailureException(String.format(
                "The user '%s' from token is not found, the token might be faked.",
                username));
        }
        final List<ApiPermission> permissions = iamService.getUserPermissions(username, application);

        boolean isAllowed = permissions.stream()
                                       .anyMatch(permission -> isApiAllowed(permission,
                                                                            application,
                                                                            httpMethod,
                                                                            requestUri));

        if (!isAllowed) {
            throw new HasNoPermissionException(String.format("%s has no permission to %s %s",
                                                             username,
                                                             httpMethod,
                                                             requestUri));
        }

        return user;
    }

    private boolean isApiAllowed(ApiPermission permission,
                                 String service,
                                 String op,
                                 String uri) {
        String fullApiUri = new StringBuilder("/").append(service)
                                                  .append(permission.getApiUri())
                                                  .toString();
        return permission.getMethod().equalsIgnoreCase(op)
            && uri.startsWith(fullApiUri);
    }
}
