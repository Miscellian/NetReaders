package com.netreaders.service;

import com.netreaders.dto.JwtResponse;
import com.netreaders.dto.LoginForm;
import com.netreaders.dto.SignUpForm;
import com.netreaders.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UserService extends UserDetailsService {

    User registerUser(SignUpForm signUpForm);

    User registerPriviledgedUser(SignUpForm signUpForm, String[] roles);

    User findByUsername(String username);
    
    boolean userExists(String username);
    
    JwtResponse login(LoginForm loginForm);
s    
    boolean checkCredentials(LoginForm loginForm);

    Collection<User> getAdminsList();

    Collection<User> getModeratorsList();
}
