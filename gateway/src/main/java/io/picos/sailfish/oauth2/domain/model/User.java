package io.picos.sailfish.oauth2.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User extends StringIdentifier implements UserDetails {

    public static final String ADMINISTRATOR = "administrator";

    public static boolean isAdministrator(User appUser) {
        return ADMINISTRATOR.equals(appUser.getUsername());
    }

    // 用户帐号名
    @Indexed(unique = true)
    private String username;

    // 显示名
    private String displayName;

    @JsonIgnore
    private String password;

    private boolean expired = false;

    private boolean locked = false;

    private boolean enabled = true;

    @Transient
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    @DBRef
    private List<AppRole> roles = new ArrayList<>();

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = trim(username);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = trim(displayName);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public List<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AppRole> roles) {
        this.roles = roles;
    }

    public void addRole(AppRole role) {
        if (this.roles.contains(role)) {
            return;
        }
        this.roles.add(role);
    }

    public void removeRole(AppRole role) {
        this.roles.remove(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
