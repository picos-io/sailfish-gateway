package io.picos.sailfish.gateway.impl.auth;

import io.picos.sailfish.gateway.auth.IamService;
import io.picos.sailfish.gateway.impl.cache.CacheConstants;
import io.picos.sailfish.gateway.impl.auth.repository.ApiPermissionRepository;
import io.picos.sailfish.gateway.impl.auth.repository.UserRepository;
import io.picos.sailfish.gateway.model.ApiPermission;
import io.picos.sailfish.gateway.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dz
 */
@Service("iamService")
public class IamServiceImpl implements IamService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiPermissionRepository apiPermissionRepository;

    @Override
    public User findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Cacheable(value = CacheConstants.USER_CACHE, key = "#username + '_'+ #application")
    public List<ApiPermission> getUserPermissions(String username, String application) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(application)) {
            return Collections.emptyList();
        }

        io.picos.sailfish.gateway.impl.auth.model.User user = userRepository.findByUsername(username);
        if (user == null) {
            return Collections.emptyList();
        }

        return apiPermissionRepository.findListByRolesIn(user.getRoles())
                                      .stream()
                                      .map(item -> (ApiPermission) item)
                                      .collect(Collectors.toList());
    }


}
