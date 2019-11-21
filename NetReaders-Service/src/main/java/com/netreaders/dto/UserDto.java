package com.netreaders.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.netreaders.utils.FieldMatch;
import com.netreaders.utils.ValidEmail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FieldMatch.List({
		@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match") })
public class UserDto {
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String userName;

	@NotNull(message = "is required")
	@Size(min = 8, message = "is required")
	private String password;

	@NotNull(message = "is required")
	@Size(min = 8, message = "is required")
	private String matchingPassword;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String firstName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String lastName;

	@ValidEmail
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;
	
	@NotNull(message = "is required")
	private boolean enabled;

	
}
