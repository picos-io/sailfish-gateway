package io.picos.sailfish.gateway.impl.mongodb.model;

import io.picos.sailfish.gateway.model.Forwarded;

public class ApplicationForwarded implements Forwarded {

    private String userIdKey;

    private String usernameKey;

    @Override
    public String getUserIdKey() {
        return userIdKey;
    }

    public void setUserIdKey(String userIdKey) {
        this.userIdKey = userIdKey;
    }

    @Override
    public String getUsernameKey() {
        return usernameKey;
    }

    public void setUsernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
    }
}
