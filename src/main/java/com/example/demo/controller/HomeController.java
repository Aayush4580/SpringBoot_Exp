package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DepartmentRepository.AnotherNameOnly;
import com.example.demo.repository.DepartmentRepository.NameOnly;

@RestController
public class HomeController {

//	@Value("${welcome.message}")
//    private String message;

	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping("/")
	public String serverUp() {
		return "server running";
	}

	@GetMapping("/test")
	public NameOnly checkNamedRepo() {
		return departmentRepository.findByNativeQuery(1);
	}

	@GetMapping("/test2")
	public AnotherNameOnly checkNamedRep2o() {
		return departmentRepository.findByNativeQueryAnother(1);
	}

}
