package com.netreaders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserForm {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Integer userId;
}
