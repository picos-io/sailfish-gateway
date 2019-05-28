package io.picos.sailfish.gateway.actuator;

import io.picos.sailfish.gateway.cache.CacheEvictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InfoEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Evict the caches for gateway
 */
@Component
public class GatewayCacheEndpoint extends EndpointMvcAdapter {

    @Autowired
    private CacheEvictService cacheEvictService;

    @Autowired
    public GatewayCacheEndpoint(GatewayEndpoint delegate) {
        super(delegate);
    }

    @RequestMapping(value = "/clean-user-cache", method = RequestMethod.POST)
    @ResponseBody
    public boolean cleanAllUserData() {
        cacheEvictService.cleanAllUserData();
        return true;
    }

    @RequestMapping(value = "/clean-token-cache", method = RequestMethod.POST)
    @ResponseBody
    public boolean cleanAllTokenData() {
        cacheEvictService.cleanAllTokenData();
        return true;
    }

}
