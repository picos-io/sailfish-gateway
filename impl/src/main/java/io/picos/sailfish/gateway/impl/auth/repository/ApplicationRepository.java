package io.picos.sailfish.gateway.impl.auth.repository;

import io.picos.sailfish.gateway.impl.auth.model.Application;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author dz
 */
public interface ApplicationRepository extends PagingAndSortingRepository<Application, String> {

    Application findByCode(String applicationCode);

}
