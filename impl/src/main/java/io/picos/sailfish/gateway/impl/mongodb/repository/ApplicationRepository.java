package io.picos.sailfish.gateway.impl.mongodb.repository;

import io.picos.sailfish.gateway.impl.mongodb.model.Application;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, String> {

    Application findByCode(String applicationCode);

}
