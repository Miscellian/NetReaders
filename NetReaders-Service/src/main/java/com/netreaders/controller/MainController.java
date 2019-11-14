package com.netreaders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping(value = "/**/{path:[^.]*}")
	public String redirect() {
		return "forward:/";
	}
}
