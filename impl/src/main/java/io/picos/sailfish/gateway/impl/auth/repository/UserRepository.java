package io.picos.sailfish.gateway.impl.auth.repository;

import io.picos.sailfish.gateway.impl.auth.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author dz
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByUsername(String username);

}
