package com.example.demo.service.Authentication;

import com.example.demo.entity.Authentication.UserDetails;
import com.example.demo.entity.Authentication.UserRole;

public interface AuthenticationService {
	UserRole createNewRole(UserRole role);

	UserDetails createNewUserDetails(UserDetails userDetails);
}
