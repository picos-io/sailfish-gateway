package io.picos.sailfish.gateway.impl.auth.model;

import io.picos.sailfish.gateway.model.JwtOption;

public class ApplicationJwtOption implements JwtOption {

    private String algorithm;

    private String secret;

    private String issuer;

    private String audience;

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Override
    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }
}
