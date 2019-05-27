package io.picos.sailfish.gateway.config;

import io.picos.sailfish.gateway.actuator.GatewayCacheEndpoint;
import io.picos.sailfish.gateway.actuator.GatewayHealthIndicator;
import io.picos.sailfish.gateway.actuator.GatewaySettingsEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfigurer {

    @Bean
    @Autowired
    GatewayCacheEndpoint cacheEndpoint(InfoEndpoint delegate) {
        return new GatewayCacheEndpoint(delegate);
    }

    @Bean
    GatewayHealthIndicator healthIndicator() {
        return new GatewayHealthIndicator();
    }

    @Bean
    @Autowired
    GatewaySettingsEndpoint settingsEndpoint(InfoEndpoint delegate) {
        return new GatewaySettingsEndpoint(delegate);
    }

}
