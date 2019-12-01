package com.netreaders.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.netreaders.dto.JwtResponse;
import com.netreaders.dto.LoginForm;
import com.netreaders.dto.SignUpForm;
import com.netreaders.models.User;

public interface UserService extends UserDetailsService {

    User registerUser(SignUpForm signUpForm);

    User registerPriviledgedUser(SignUpForm signUpForm, String[] roles);

    User findByUsername(String username);
    
    boolean userExists(String username);
    
    JwtResponse login(LoginForm loginForm);
    
    boolean checkCredentials(LoginForm loginForm);
}
