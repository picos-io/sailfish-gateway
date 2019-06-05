package io.picos.sailfish.gateway.impl.auth;

import io.picos.sailfish.gateway.auth.AuthzService;
import io.picos.sailfish.gateway.auth.IamService;
import io.picos.sailfish.gateway.exception.HasNoPermissionException;
import io.picos.sailfish.gateway.model.ApiPermission;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Override
    public void authorize(UserDetails user, String application, String httpMethod, String requestUri) {
        String username = user.getUsername();
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
