package com.netreaders.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String userPassword;
    private String email;
    private String firstName;
    private String lastName;
    private Integer profilePhoto;
}