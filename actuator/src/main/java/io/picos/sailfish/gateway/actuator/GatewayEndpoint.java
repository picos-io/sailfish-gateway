package io.picos.sailfish.gateway.actuator;

import io.picos.sailfish.gateway.support.GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class GatewayEndpoint implements Endpoint<GatewayProperties> {

    @Value("${spring.application.name}")
    private String endpoint;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public String getId() {
        return this.endpoint;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    @Override
    public GatewayProperties invoke() {
        return this.gatewayProperties;
    }
}
