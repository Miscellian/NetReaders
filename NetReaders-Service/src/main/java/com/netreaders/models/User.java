package com.netreaders.models;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;
    private String userNickname;
    private String userPassword;
    private String email;
    private String firstName;
    private String lastName;
    private Integer profilePhoto;
    private Collection<Role> roles;
}