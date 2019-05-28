package io.picos.sailfish.gateway.impl.cache.event;

import io.picos.sailfish.gateway.cache.CacheEvictService;
import io.picos.sailfish.gateway.impl.cache.event.TokenRevokedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

/**
 * @author dz
 */
public class TokenRevokedEventListener {

    @Autowired
    private CacheEvictService cacheService;

    @EventListener(TokenRevokedEvent.class)
    public void tenantChanged(TokenRevokedEvent tokenChangedEvent) {
        if (tokenChangedEvent.getSource() == null) {
            return;
        }

        cacheService.cleanTokenData(tokenChangedEvent.getSource().getToken());
    }

}
