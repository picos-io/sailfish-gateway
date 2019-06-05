package io.picos.sailfish.gateway.auth;

import io.picos.sailfish.gateway.model.ApiPermission;
import io.picos.sailfish.gateway.model.User;

import java.util.List;

public interface IamService {

    /**
     * @param username
     * @param application
     * @return the permission list filter by username & application code
     */
    List<ApiPermission> getUserPermissions(String username, String application);

}
