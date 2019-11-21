package com.netreaders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
}
