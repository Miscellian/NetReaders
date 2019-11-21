package com.netreaders.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.netreaders.dto.UserDto;
//import com.netreaders.models.Token;
import com.netreaders.models.User;
import com.netreaders.registration.OnRegistrationSuccessEvent;
import com.netreaders.service.UserService;

//@RestController
//@RequestMapping(value = "/api/user")
//public class UserController {
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private ApplicationEventPublisher eventPublisher;
//	
//	@Qualifier("messageSource")
//	@Autowired
//	private MessageSource messages;
//	
//	@PostMapping("/login")
//	public String login(@ModelAttribute("user") UserDto userDto, BindingResult result, Model model) throws SQLException{
// 	String username = userDto.getUserName();
//	String password = userDto.getPassword();
//		if(result.hasErrors()) {
//			return "fancy-login";
//		}
//		User loggedInUser = userService.findByNickname(username);
//		if(loggedInUser==null) {
//			return "fancy-login";
//		}
//		return "login-success";
//	}
//	
//
//	@CrossOrigin(origins = "http://localhost:8080")
//	@RequestMapping(name = "/registration", method = RequestMethod.POST, produces = "application/json", consumes = "applicaton/json")
//	public String registerUser(@RequestParam(value = "User",required = false) UserDto userDto) throws SQLException {
//		User registeredUser = userService.findByNickname(userDto.getUserName());
//		if(registeredUser!=null) { //TODO implement error handling
//		}
//
//		registeredUser = userService.registerUser(userDto);
//		try {
//			eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(), request.getContextPath()));
//		}catch(Exception re) {
//			throw new Exception("Error while sending confirmation email");
//		}
//		return "True";
//	}
//
//	
//	@GetMapping("/confirmRegistration")
//	public String confirmRegistration (WebRequest request, Model model, @RequestParam String token) throws SQLException {
//		Locale locale=request.getLocale();
//		Token verificationToken = userService.getTokenName(token);
//		if(verificationToken == null) {
//			String message = messages.getMessage("auth.message.invalidToken", null, locale);
//			model.addAttribute("message", message);
//			return "access-denied";
//		}
//
//		Calendar calendar = Calendar.getInstance();
//		if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
//			String message = messages.getMessage("auth.message.expired", null, locale);
//			model.addAttribute("message", message);
//			return "access-denied";
//		}
//
//		return "confirmRegistration";
//		}
//	}