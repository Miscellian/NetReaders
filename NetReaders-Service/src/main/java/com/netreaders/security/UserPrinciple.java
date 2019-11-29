package com.netreaders.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netreaders.models.Role;
import com.netreaders.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple build(User user, Collection<Role> roles) {
        List<GrantedAuthority> authorities = roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                (long) user.getId(),
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
