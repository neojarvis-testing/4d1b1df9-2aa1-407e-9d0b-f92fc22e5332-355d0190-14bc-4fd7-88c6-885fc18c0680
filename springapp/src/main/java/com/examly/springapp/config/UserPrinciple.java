package com.examly.springapp.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.examly.springapp.model.User;

public class UserPrinciple implements UserDetails {
    private User user;

    /* Constructor to initialize UserPrinciple with a User object */
    public UserPrinciple(User user) {
        this.user = user;
    }

    /* Static method to build a UserPrinciple from a User object */
    public static UserDetails build(User user) {
        return new UserPrinciple(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /* Return the authorities granted to the user */
        return List.of(() -> "ROLE_" + user.getUserRole());
    }

    @Override
    public String getPassword() {
        /* Return the user's password */
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        /* Return the user's email as the username */
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        /* Indicate that the user's account is not expired */
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        /* Indicate that the user's account is not locked */
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        /* Indicate that the user's credentials are not expired */
        return true;
    }

    @Override
    public boolean isEnabled() {
        /* Indicate that the user's account is enabled */
        return true;
    }
}
