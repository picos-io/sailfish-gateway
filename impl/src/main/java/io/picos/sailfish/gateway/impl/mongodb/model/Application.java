package io.picos.sailfish.gateway.impl.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "Applications")
public class Application implements io.picos.sailfish.gateway.model.Application {

    @Id
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private boolean enabled;

    private boolean jwtProtected;

    private ApplicationJwtOption jwtOption;

    private ApplicationForwarded forwarded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isJwtProtected() {
        return jwtProtected;
    }

    public void setJwtProtected(boolean jwtProtected) {
        this.jwtProtected = jwtProtected;
    }

    @Override
    public ApplicationJwtOption getJwtOption() {
        return jwtOption;
    }

    public void setJwtOption(ApplicationJwtOption jwtOption) {
        this.jwtOption = jwtOption;
    }

    @Override
    public ApplicationForwarded getForwarded() {
        return forwarded;
    }

    public void setForwarded(ApplicationForwarded forwarded) {
        this.forwarded = forwarded;
    }
}
