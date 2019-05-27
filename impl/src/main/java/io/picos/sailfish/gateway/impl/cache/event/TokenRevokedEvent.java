package io.picos.sailfish.gateway.impl.cache.event;

import io.picos.sailfish.gateway.message.TokenRevoked;
import org.springframework.context.ApplicationEvent;

public class TokenRevokedEvent extends ApplicationEvent {

    public TokenRevokedEvent(TokenRevoked tokenRevoked) {
        super(tokenRevoked);
    }

    @Override
    public TokenRevoked getSource() {
        return (TokenRevoked) super.getSource();
    }

}
