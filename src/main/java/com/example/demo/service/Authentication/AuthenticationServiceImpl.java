package com.example.demo.service.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Authentication.UserDetails;
import com.example.demo.entity.Authentication.UserRole;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserDetailsRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	RoleRepository repository;
	@Autowired
	UserDetailsRepository userRepository;

	@Override
	public UserRole createNewRole(UserRole role) {
		return repository.save(role);
	}

	@Override
	public UserDetails createNewUserDetails(UserDetails userDetails) {
//		userDetails.setRoles(Set.of(new UserRole(null, null, null)));
		return userRepository.save(userDetails);
	}

}
