package com.netreaders.service;

import com.netreaders.dto.SignUpForm;
import com.netreaders.models.User;

public interface UserService {

    User registerUser(SignUpForm signUpForm);

    User registerPriviledgedUser(SignUpForm signUpForm, String[] roles);

    User findByNickname(String username);
}
