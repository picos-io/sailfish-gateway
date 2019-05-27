package io.picos.sailfish.gateway.impl.mongodb.repository;

import io.picos.sailfish.gateway.impl.mongodb.model.ApiPermission;
import io.picos.sailfish.gateway.impl.mongodb.model.Role;
import io.picos.sailfish.gateway.impl.mongodb.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApiPermissionRepository extends PagingAndSortingRepository<ApiPermission, String> {

    List<ApiPermission> findListByRolesIn(List<Role> roleList);

}
