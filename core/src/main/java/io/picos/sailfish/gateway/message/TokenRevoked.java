package io.picos.sailfish.gateway.message;

import java.io.Serializable;

public class TokenRevoked implements Serializable {

    private String token;

    public TokenRevoked() {
    }

    public TokenRevoked(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TokenRevoked{");
        sb.append("token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
