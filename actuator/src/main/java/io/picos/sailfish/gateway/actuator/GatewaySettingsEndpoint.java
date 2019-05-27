package io.picos.sailfish.gateway.actuator;

import io.picos.sailfish.gateway.impl.support.DefaultGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InfoEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Evict the caches for gateway
 */
public class GatewaySettingsEndpoint extends EndpointMvcAdapter {

    @Autowired
    private DefaultGatewayProperties gatewayProperties;

    @Autowired
    public GatewaySettingsEndpoint(InfoEndpoint delegate) {
        super(delegate);
    }

    @RequestMapping(value = "/gateway-settings", method = RequestMethod.GET)
    @ResponseBody
    public DefaultGatewayProperties getGatewayProperties() {
        return gatewayProperties;
    }

}
