package com.netreaders.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String showHome() {
		return "home";
	}
	
	// moderators
	@GetMapping("/moderators")
	public String showModeratorsContent() {
		return "moderators";
	}
	
	// admins
	@GetMapping("/admins")
	public String showAdminContent() {
		return "admins";
	}
	// superadmin
	@GetMapping("/superadmin")
	public String showSuperadminContent() {
		return "superadmin";
	}
}
