package com.kostApp.kostApp.security;

import com.kostApp.kostApp.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * class with user details, ned for spring security
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final com.kostApp.kostApp.models.User user;

    public UserDetails(User user) {
        this.user = user;
    }

    /**
     * method for return user role
     *
     * @return - user role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * method for return user password
     *
     * @return - user password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * method for return user nikname
     *
     * @return - user nikname
     */
    @Override
    public String getUsername() {
        return user.getNik();
    }

    /**
     * util method
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * util method
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * util method
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * util method
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * method for return user
     *
     * @return - user
     */
    public User getUser() {
        return user;
    }
}
