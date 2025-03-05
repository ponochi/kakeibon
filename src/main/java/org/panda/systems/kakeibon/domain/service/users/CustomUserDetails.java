package org.panda.systems.kakeibon.domain.service.users;


import org.panda.systems.kakeibon.domain.model.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomUserDetails implements UserDetails {
    private final Users users;

    @Autowired
    public CustomUserDetails(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_" + this.users.getRoleName().name());
    }

    @Override
    public String getPassword() {
        return this.users.getPassword();
    }

    public void setPassword(String password) {
        this.users.setPassword(password);
    }

    @Override
    public String getUsername() {
        return this.users.getUsername();
    }

    public void setUsername(String username) {
        this.users.setUsername(username);
    }

    @Override
    public boolean isAccountNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return org.springframework.security.core.userdetails.UserDetails.super.isEnabled();
    }

    public String toString() {
        return "CustomUserDetails(users=" + this.getUsers() + ")";
    }
}
