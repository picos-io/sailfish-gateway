package io.picos.sailfish.gateway.impl.mongodb.repository;

import io.picos.sailfish.gateway.impl.mongodb.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByUsername(String username);

}
