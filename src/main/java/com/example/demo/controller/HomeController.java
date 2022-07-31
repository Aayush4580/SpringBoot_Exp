package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
//	@Value("${welcome.message}")
//    private String message;

	@GetMapping("/")
	public String serverUp() {
		return "server running";
	}

}
