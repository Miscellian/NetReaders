package com.netreaders.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netreaders.dto.JwtResponse;
import com.netreaders.dto.LoginForm;
import com.netreaders.dto.SignUpForm;
import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;
import com.netreaders.security.JwtProvider;
import com.netreaders.service.EmailService;
import com.netreaders.service.RegistrationTokenService;
import com.netreaders.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;
	private EmailService emailService;
	private RegistrationTokenService registrationTokenService;
	private AuthenticationManager authenticationManager;
	private JwtProvider jwtProvider;

	@Autowired
	public UserController(UserService userService, EmailService emailService, RegistrationTokenService registrationTokenService, AuthenticationManager authenticationManager, JwtProvider jwtProvider){
		this.userService=userService;
		this.emailService=emailService;
		this.registrationTokenService=registrationTokenService;
		this.authenticationManager=authenticationManager;
		this.jwtProvider=jwtProvider;
	}

	@PostMapping("/registration")
	public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpForm) throws SQLException {
		User user = userService.findByNickname(signUpForm.getUser_name());
		if (user != null) {
			return ResponseEntity.badRequest().body("User already exists");
		}
		user = userService.registerUser(signUpForm);
		RegistrationToken token = registrationTokenService.createToken(user);
		emailService.sendEmail(user, token);
		
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginForm loginForm) throws SQLException {
		User user = userService.findByNickname(loginForm.getUsername());
		if(user != null) {
			if(registrationTokenService.getByUser(user) != null) {
				return ResponseEntity.badRequest().body("Finish your registration first");
			}
		} else {
			return ResponseEntity.badRequest().body("User Already exists");
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@GetMapping("/confirmRegistration")
	public ResponseEntity<?> confirmRegistration(@RequestParam(name = "token",required = true) String token) throws SQLException {
		
		if(registrationTokenService.tokenIsValid(token)) {
			registrationTokenService.removeToken(token);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	@PostMapping("/createAdmin")
	public boolean createAdmin(@RequestBody SignUpForm signUpForm) throws SQLException {
		User user = userService.findByNickname(signUpForm.getUser_name());
		if (user != null) {
			return false;
		}
		user = userService.registerPriviledgedUser(signUpForm, new String[] {"ADMIN"});
		
		return true;
	}
	
	@PostMapping("/createModerator")
	public boolean createModerator(@RequestBody SignUpForm signUpForm,
								   @RequestBody String[] roles) throws SQLException {
		User user = userService.findByNickname(signUpForm.getUser_name());
		if (user != null) {
			return false;
		}
		user = userService.registerPriviledgedUser(signUpForm, roles);
		
		return true;
	}
	
}
