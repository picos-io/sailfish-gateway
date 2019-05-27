package io.picos.sailfish.gateway.auth;

import io.picos.sailfish.gateway.model.ApiPermission;
import io.picos.sailfish.gateway.model.User;

import java.util.List;

public interface IamService {

    User findUserByName(String username);

    List<ApiPermission> getUserPermissions(String username, String application);

}
