package io.picos.sailfish.gateway.config;

import io.picos.sailfish.gateway.audit.AuditService;
import io.picos.sailfish.gateway.impl.audit.AuditServiceImpl;
import io.picos.sailfish.gateway.impl.support.DefaultGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author dz
 */
@Configuration
@ConditionalOnProperty(prefix = "gateway", name = "auditEnabled", havingValue = "true", matchIfMissing = true)
@ImportAutoConfiguration(ElasticsearchAutoConfiguration.class)
@EnableElasticsearchRepositories(basePackages = "io.picos.sailfish.gateway.impl.audit.repository")
public class AuditConfigurer {

    @Autowired
    private DefaultGatewayProperties gatewayProperties;

    @Bean
    AuditService auditService() {
        return new AuditServiceImpl();
    }

}
