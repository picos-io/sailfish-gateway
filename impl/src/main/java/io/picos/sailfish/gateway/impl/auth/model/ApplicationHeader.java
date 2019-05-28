package io.picos.sailfish.gateway.impl.auth.model;

import io.picos.sailfish.gateway.model.Header;

public class ApplicationHeader implements Header {

    private String userIdKey;

    private String userNameKey;

    @Override
    public String getUserIdKey() {
        return userIdKey;
    }

    public void setUserIdKey(String userIdKey) {
        this.userIdKey = userIdKey;
    }

    @Override
    public String getUserNameKey() {
        return userNameKey;
    }

    public void setUserNameKey(String userNameKey) {
        this.userNameKey = userNameKey;
    }
}
