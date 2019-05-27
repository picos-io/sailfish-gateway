package io.picos.sailfish.gateway.model;

public interface JwtOption {

    String getAlgorithm();

    String getSecret();

    String getIssuer();

    String getAudience();

}
