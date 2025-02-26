package com.mph.salelaptop.config;

import com.mph.salelaptop.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final Users user;

    public UserDetailsImpl(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Giả sử bạn có một trường "role" trong entity Users
        // Để đơn giản, tôi chỉ lấy role name. Bạn có thể tùy chỉnh logic này.
        String roleName = user.getRole().getRoleName(); // Giả sử role có một trường là roleName
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // Giả sử bạn có một trường "enabled" trong entity Users
    }
}