package io.picos.sailfish.gateway.impl.cache.event;

import io.picos.sailfish.gateway.auth.ApplicationService;
import io.picos.sailfish.gateway.cache.CacheEvictService;
import io.picos.sailfish.gateway.impl.cache.event.UserChangedEvent;
import io.picos.sailfish.gateway.message.UserChanged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

public class UserChangedEventListener {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CacheEvictService cacheService;

    @EventListener(UserChangedEvent.class)
    public void userChanged(UserChangedEvent userChangedEvent) {
        UserChanged userChanged = userChangedEvent.getSource();
        if (userChanged == null || userChanged.getUsername() == null) {
            return;
        }

        applicationService.listApplications()
                          .forEach(application -> cacheService.cleanUserData(userChanged.getUsername(),
                                                                             application));
    }

}
