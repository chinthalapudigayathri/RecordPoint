package com.gayathri.projects.security;

import com.gayathri.projects.entity.Employee;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


public class CustomUserDetail implements UserDetails
{

    private final Employee employee;

    public CustomUserDetail(Employee employee) {
        this.employee = employee;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return employee.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

    }

    @Override
    public @Nullable String getPassword() {
        return employee.getPassword();

    }

    @Override
    public String getUsername() {
        return employee.getUsername();

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
        return employee.isEnabled();
    }

}