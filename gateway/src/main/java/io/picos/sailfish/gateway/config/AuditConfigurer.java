package io.picos.sailfish.gateway.config;

import io.picos.sailfish.gateway.impl.support.DefaultGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author dz
 */
@Configuration
@ConditionalOnProperty(prefix = "gateway", name = "auditEnabled", havingValue = "true", matchIfMissing = true)
//@EnableElasticsearchRepositories(basePackages = "io.picos.sailfish.gateway.impl.audit.persistence")
public class AuditConfigurer {

    @Autowired
    private DefaultGatewayProperties gatewayProperties;


}
