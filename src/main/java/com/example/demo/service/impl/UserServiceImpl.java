package com.example.demo.service.impl;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDetailsMapper;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.UserConstant;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// As this return UserDetails Object so we need to
		// convert this to our User object
		Optional<User> user = userRepository.findByUserName(username);
		return user.map(UserDetailsMapper::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(UserConstant.DEFAULT_ROLE);
		userRepository.save(user);
		return userRepository.save(user);
	}

	@Override
	public List<String> getRolesByLoggedInUser(Principal principal) {
		String roles = getLoggedInUser(principal).getRoles();
		List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
		if (assignRoles.contains("ROLE_ADMIN")) {
			return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
		}
		if (assignRoles.contains("ROLE_MODERATOR")) {
			return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());
		}
		if (assignRoles.contains("ROLE_USER")) {
			return Arrays.stream(UserConstant.USER_ACCESS).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private User getLoggedInUser(Principal principal) {
		return userRepository.findByUserName(principal.getName()).get();
	}
}
