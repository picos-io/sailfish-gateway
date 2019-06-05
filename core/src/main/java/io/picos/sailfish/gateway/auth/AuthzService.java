package io.picos.sailfish.gateway.auth;

import io.picos.sailfish.gateway.exception.HasNoPermissionException;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthzService {

    /**
     * Check the permission of specified user, if not passed, the HasNoPermissionException is thrown
     *
     * @param user
     * @param application
     * @param httpMethod
     * @param requestUri
     * @throws HasNoPermissionException if has no permission
     */
    void authorize(UserDetails user, String application, String httpMethod, String requestUri);

}
