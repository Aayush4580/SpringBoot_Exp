package com.example.demo.service;

import java.security.Principal;
import java.util.List;

import com.example.demo.entity.User;

public interface UserService {

	User saveUser(User user);

	List<String> getRolesByLoggedInUser(Principal principal);

}
