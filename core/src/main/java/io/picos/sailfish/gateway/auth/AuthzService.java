package io.picos.sailfish.gateway.auth;

import io.picos.sailfish.gateway.model.User;

public interface AuthzService {

    /**
     * Check the permission of specified token, if passed, the authorized user is returned
     *
     * @param token
     * @param application
     * @param httpMethod
     * @param requestUrl
     * @return
     */
    User authorize(String token, String application, String httpMethod, String requestUrl);

}
