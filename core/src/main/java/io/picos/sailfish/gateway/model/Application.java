package io.picos.sailfish.gateway.model;

public interface Application {

    String getCode();

    String getName();

    boolean isEnabled();

    boolean isJwtProtected();

    JwtOption getJwtOption();

    Header getHeader();

}
