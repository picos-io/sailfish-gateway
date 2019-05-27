package io.picos.sailfish.gateway.support;

public interface GatewayProperties {

    String getVersion();

    boolean isCacheEnabled();

    boolean isAuditEnabled();

    String getXForwardedUserid();

    String getXForwardedUsername();

    String getOauth2CheckTokenUrl();

    String getOauth2ClientId();

    String getOauth2ClientSecret();

    String getOauth2ResourceId();

    String getCorsAllowedOrigin();

    String getCorsAllowedMethod();

    String getTokenChangedChannel();

    String getUserChangedChannel();

}
