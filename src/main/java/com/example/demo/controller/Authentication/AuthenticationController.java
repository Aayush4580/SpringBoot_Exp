package com.example.demo.controller.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Authentication.UserDetails;
import com.example.demo.entity.Authentication.UserRole;
import com.example.demo.service.Authentication.AuthenticationService;

@RestController
public class AuthenticationController {
	@Autowired
	AuthenticationService authenticationService;

	@PostMapping("/createNewRole")
	public UserRole createNewRole(@RequestBody UserRole role) {
		return authenticationService.createNewRole(role);
	}

	@PostMapping("/createNewUser")
	public UserDetails createNewUser(@RequestBody UserDetails userDetails) {
		return authenticationService.createNewUserDetails(userDetails);
	}
}
