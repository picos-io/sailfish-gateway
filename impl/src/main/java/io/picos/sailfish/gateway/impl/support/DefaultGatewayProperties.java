package io.picos.sailfish.gateway.impl.support;

import io.picos.sailfish.gateway.support.GatewayProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gateway")
public class DefaultGatewayProperties implements GatewayProperties {

    private String version = "UNKNOWN";

    private boolean cacheEnabled = false;

    private boolean auditEnabled = false;

    private String httpHeaderUserId = "X-User-Id";

    private String httpHeaderUserName = "X-User-Name";

    private String oauth2CheckTokenUrl = "http://oauth2/oauth/check_token";

    private String oauth2ClientId = "gateway";

    private String oauth2ClientSecret = "gateway-secret";

    private String oauth2ResourceId = "gateway";

    private boolean enableCors = false;

    private String corsAllowedOrigin = "*";

    private String corsAllowedMethod = "*";

    private String tokenChangedChannel = "sailfish_token_changed";

    private String userChangedChannel = "sailfish_user_changed";

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    @Override
    public boolean isAuditEnabled() {
        return auditEnabled;
    }

    public void setAuditEnabled(boolean auditEnabled) {
        this.auditEnabled = auditEnabled;
    }

    @Override
    public String getHttpHeaderUserId() {
        return httpHeaderUserId;
    }

    public void setHttpHeaderUserId(String httpHeaderUserId) {
        this.httpHeaderUserId = httpHeaderUserId;
    }

    @Override
    public String getHttpHeaderUserName() {
        return httpHeaderUserName;
    }

    public void setHttpHeaderUserName(String httpHeaderUserName) {
        this.httpHeaderUserName = httpHeaderUserName;
    }

    @Override
    public String getOauth2CheckTokenUrl() {
        return oauth2CheckTokenUrl;
    }

    public void setOauth2CheckTokenUrl(String oauth2CheckTokenUrl) {
        this.oauth2CheckTokenUrl = oauth2CheckTokenUrl;
    }

    @Override
    public String getOauth2ClientId() {
        return oauth2ClientId;
    }

    public void setOauth2ClientId(String oauth2ClientId) {
        this.oauth2ClientId = oauth2ClientId;
    }

    @Override
    public String getOauth2ClientSecret() {
        return oauth2ClientSecret;
    }

    public void setOauth2ClientSecret(String oauth2ClientSecret) {
        this.oauth2ClientSecret = oauth2ClientSecret;
    }

    @Override
    public String getOauth2ResourceId() {
        return oauth2ResourceId;
    }

    public void setOauth2ResourceId(String oauth2ResourceId) {
        this.oauth2ResourceId = oauth2ResourceId;
    }

    public boolean isEnableCors() {
        return enableCors;
    }

    public void setEnableCors(boolean enableCors) {
        this.enableCors = enableCors;
    }

    @Override
    public String getCorsAllowedOrigin() {
        return corsAllowedOrigin;
    }

    public void setCorsAllowedOrigin(String corsAllowedOrigin) {
        this.corsAllowedOrigin = corsAllowedOrigin;
    }

    @Override
    public String getCorsAllowedMethod() {
        return corsAllowedMethod;
    }

    public void setCorsAllowedMethod(String corsAllowedMethod) {
        this.corsAllowedMethod = corsAllowedMethod;
    }

    @Override
    public String getTokenChangedChannel() {
        return tokenChangedChannel;
    }

    public void setTokenChangedChannel(String tokenChangedChannel) {
        this.tokenChangedChannel = tokenChangedChannel;
    }

    @Override
    public String getUserChangedChannel() {
        return userChangedChannel;
    }

    public void setUserChangedChannel(String userChangedChannel) {
        this.userChangedChannel = userChangedChannel;
    }
}
