package com.netreaders.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netreaders.models.Role;
import com.netreaders.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id, String username, String lastName, String firstName,
                         String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user, Collection<Role> roles) {
        List<GrantedAuthority> authorities = roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                (long)user.getUserId(),
                user.getUsername(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                user.getUserPassword(),
                authorities
        );
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
        return true;
    }
}
