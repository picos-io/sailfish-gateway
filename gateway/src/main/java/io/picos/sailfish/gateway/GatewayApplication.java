package io.picos.sailfish.gateway;

import io.picos.sailfish.gateway.impl.support.DefaultGatewayProperties;
import io.picos.sailfish.gateway.zuul.GatewayZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class,
    RedisRepositoriesAutoConfiguration.class,
    ElasticsearchAutoConfiguration.class,
    ElasticsearchRepositoriesAutoConfiguration.class
})
@EnableZuulProxy
@EnableDiscoveryClient
@EnableAsync
@EnableConfigurationProperties(DefaultGatewayProperties.class)
public class GatewayApplication {

    @Bean
    public GatewayZuulFilter gatewayZuulFilter() {
        return new GatewayZuulFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
