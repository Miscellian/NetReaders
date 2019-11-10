package com.netreaders.registration.controller;

import java.util.Calendar;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.netreaders.registration.dto.UserDTO;
import com.netreaders.registration.event.OnRegistrationSuccessEvent;
import com.netreaders.registration.model.User;
import com.netreaders.registration.model.VerificationToken;
import com.netreaders.registration.service.IUserService;

@Controller
public class AccountController {
	
	@Autowired
	private IUserService service;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Qualifier("messageSource")
	@Autowired
	private MessageSource messages;

	@GetMapping("/login")
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
		User loggedInUser = service.findByUsernameAndPassword(username,password);
		if(loggedInUser==null) {
			return "fancy-login";
		}
		//service.loginUser(userDto);
		return "login-success";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);
		return "registration";
	}
	
	
	//@PostMapping("/registration")
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerNewUser(@ModelAttribute("user") UserDTO userDto, BindingResult result, WebRequest request, Model model) {
		User registeredUser = new User();
		String userName = userDto.getUserName();
		if (result.hasErrors()) {
			return "registration";
		}
		registeredUser = service.findByUsername(userName);
		if(registeredUser!=null) {
			model.addAttribute("error","There is already an account with this username: " + userName);
			return "registration";
		}

		registeredUser = service.registerUser(userDto);
		try {
			eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(), request.getContextPath()));
		}catch(Exception re) {
			re.printStackTrace();
//			throw new Exception("Error while sending confirmation email");
		}
		return "registrationSuccess";
	}
	/*@RequestMapping(value="/register", method=RequestMethod.POST)
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else if(userService.isUserAlreadyPresent(user)){
            modelAndView.addObject("successMessage", "user already exists!");
        }
        // we will save the user if, no binding errors
        else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User is registered successfully!");
        }
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("register");
        return modelAndView;
    }*/
	
	@RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
	public String confirmRegistration
		(WebRequest request, Model model,@RequestParam("token") String token) {
		Locale locale=request.getLocale();
		VerificationToken verificationToken = service.getVerificationToken(token);
		if(verificationToken == null) {
			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return "redirect:access-denied";
		}
		User user = verificationToken.getUser();
		Calendar calendar = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
			String message = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", message);
			return "redirect:access-denied";
		}
		
		user.setEnabled(true);
		service.enableRegisteredUser(user);
		return "fancy-login";
	}
}
