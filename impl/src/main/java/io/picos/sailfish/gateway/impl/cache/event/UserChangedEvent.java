package io.picos.sailfish.gateway.impl.cache.event;

import io.picos.sailfish.gateway.message.UserChanged;
import org.springframework.context.ApplicationEvent;

/**
 * @author dz
 */
public class UserChangedEvent extends ApplicationEvent {

    public UserChangedEvent(UserChanged userChanged) {
        super(userChanged);
    }

    @Override
    public UserChanged getSource() {
        return (UserChanged) super.getSource();
    }

}

