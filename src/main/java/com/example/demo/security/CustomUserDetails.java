package com.example.demo.security;

import com.example.demo.implement.member.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long memberId;
    private final List<Role> roles;

    public CustomUserDetails(Long memberId, List<Role> roles) {
        this.memberId = memberId;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getValue()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        throw new IllegalArgumentException();
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.memberId);
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isEnabled() {
        throw new IllegalArgumentException();
    }
}
