package io.picos.sailfish.gateway.actuator;

import io.picos.sailfish.gateway.impl.support.DefaultGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class GatewayHealthIndicator extends AbstractHealthIndicator {

    @Autowired
    DefaultGatewayProperties gatewayProperties;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.withDetail("version", gatewayProperties.getVersion()).up();
    }

}
