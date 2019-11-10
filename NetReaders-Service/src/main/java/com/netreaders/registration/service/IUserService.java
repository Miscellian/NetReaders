package com.netreaders.registration.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.netreaders.registration.dto.UserDTO;
import com.netreaders.registration.model.User;
import com.netreaders.registration.model.VerificationToken;

@Service
public interface IUserService extends UserDetailsService {
	public User registerUser(UserDTO userDto);

	public User findByUsername(String username);

	//public User loginUser(UserDTO userDTO);

	public User findByUsernameAndPassword(String username, String password);

	public void createVerificationToken(User user, String token);

	public VerificationToken getVerificationToken(String verificationToken);

	public void enableRegisteredUser(User user);
}
