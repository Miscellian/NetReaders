package com.netreaders.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netreaders.dto.JwtResponse;
import com.netreaders.dto.LoginForm;
import com.netreaders.dto.SignUpForm;
import com.netreaders.dto.UserDto;
import com.netreaders.models.User;
import com.netreaders.security.JwtProvider;
import com.netreaders.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsersController {
	@Autowired
	private UserService userService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping(path = "/test")
	public String test() {
		return "You are indeed an admin";
	}
	
	@PostMapping("/registration")
	public boolean registerUser(@RequestBody SignUpForm signUpForm) throws SQLException {
		User registeredUser = userService.findByNickname(signUpForm.getUsername());
		if(registeredUser!=null) { //TODO implement error handling
			return false;
		}

		registeredUser = userService.registerUser(signUpForm);
		return true;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginForm loginForm) throws SQLException {
//		String encoded = passwordEncoder.encode();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginForm.getUsername(),loginForm.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}
}
