package com.netreaders.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private RegistrationTokenService registrationTokenService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/registration")
	public boolean registerUser(@RequestBody SignUpForm signUpForm) throws SQLException {
		User user = userService.findByNickname(signUpForm.getUsername());
		if (user != null) {
			return false;
		}
		user = userService.registerUser(signUpForm);
		RegistrationToken token = registrationTokenService.createToken(user);
		emailService.sendEmail(user, token);
		
		return true;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginForm loginForm) throws SQLException {
		User user = userService.findByNickname(loginForm.getUsername());
		if(user != null) {
			if(registrationTokenService.getByUser(user) != null) {
				return ResponseEntity.badRequest().build();
			}
			
		} else {
			return ResponseEntity.badRequest().build();
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
}
