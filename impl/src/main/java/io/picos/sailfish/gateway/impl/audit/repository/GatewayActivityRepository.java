package io.picos.sailfish.gateway.impl.audit.repository;


import io.picos.sailfish.gateway.impl.audit.model.GatewayActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GatewayActivityRepository extends ElasticsearchRepository<GatewayActivity, String> {

}
