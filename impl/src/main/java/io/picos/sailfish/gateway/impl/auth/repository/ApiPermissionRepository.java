package io.picos.sailfish.gateway.impl.auth.repository;

import io.picos.sailfish.gateway.impl.auth.model.ApiPermission;
import io.picos.sailfish.gateway.impl.auth.model.Role;
import io.picos.sailfish.gateway.impl.auth.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author dz
 */
public interface ApiPermissionRepository extends PagingAndSortingRepository<ApiPermission, String> {

    List<ApiPermission> findListByRolesIn(List<Role> roleList);

}
