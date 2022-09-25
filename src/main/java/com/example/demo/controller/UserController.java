package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public String joinGroup(@RequestBody User user) {
		userService.saveUser(user);
		return "Hi " + user.getUserName() + " welcome to group !";
	}

	// Can be only accessed by ROLE_USER
	// basic auth username: Aayush pw:1234
	@GetMapping("/test")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public List<String> getUserData(Principal principal) {
		List<String> activeRoles = userService.getRolesByLoggedInUser(principal);
		return activeRoles;
	}

}
