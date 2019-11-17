package com.netreaders.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.netreaders.dto.UserDto;
import com.netreaders.models.Token;
import com.netreaders.models.User;
import com.netreaders.registration.OnRegistrationSuccessEvent;
import com.netreaders.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Qualifier("messageSource")
	@Autowired
	private MessageSource messages;

	/*@GetMapping("/login")
	public String showLoginForm() {
		return "fancy-login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("user") UserDTO userDto, BindingResult result, Model model){
 	String username = userDto.getUserName();
		String password = userDto.getPassword();
		if(result.hasErrors()) {
			return "fancy-login";
		}
		User loggedInUser = userService.findByUsernameAndPassword(username,password);
		if(loggedInUser==null) {
			return "fancy-login";
		}
		return "login-success";
	}*/

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "registration";
	}
	
	
	@PostMapping("/registration")
	public String registerNewUser(@ModelAttribute("user") UserDto userDto, BindingResult result, HttpServletRequest request, Model model) throws SQLException {
		User registeredUser = new User();
		String userName = userDto.getUserName();
		if (result.hasErrors()) {
			return "registration";
		}
		registeredUser = userService.findByNickname(userName);
		if(registeredUser!=null) {
			model.addAttribute("error","There is already an account with this username: " + userName);
			return "registration";
		}

		registeredUser = userService.registerUser(userDto);
		try {
			eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(), request.getContextPath()));
		}catch(Exception re) {
//			throw new Exception("Error while sending confirmation email");
		}
		return "registrationSuccess";
	}

	
	@GetMapping("/confirmRegistration")
	public String confirmRegistration (WebRequest request, Model model, @RequestParam String token) throws SQLException {
		Locale locale=request.getLocale();
		Token verificationToken = userService.getTokenName(token);
		if(verificationToken == null) {
			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return "access-denied";
		}

		Calendar calendar = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
			String message = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", message);
			return "access-denied";
		}

		return "confirmRegistration";
		}
	}