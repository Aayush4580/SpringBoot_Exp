package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AnotherNameOnly;
import com.example.demo.dto.NameOnly;
import com.example.demo.repository.DepartmentRepository;

@RestController
@CrossOrigin
public class HomeController {

//	@Value("${welcome.message}")
//    private String message;

	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping("/")
	public String serverUp() {
		return "server running";
	}

//	@GetMapping("/test")
//	public NameOnly checkNamedRepo() {
//		NameOnly obj = departmentRepository.findByNativeQuery(1);
////		ModelMapper modelMapper = new ModelMapper();
////		NameO bookDTO = modelMapper.map(book, BookDto.class);
//		return obj;
//	}

	@GetMapping("/test2")
	public List<NameOnly> checkNamedRep2o() {
		List<AnotherNameOnly> anotherNameOnlies = departmentRepository.findByNativeQueryAnother(1);
		List<NameOnly> list = new ArrayList<>();
		for (AnotherNameOnly anotherNameOnly : anotherNameOnlies) {
			NameOnly nameOnly = new NameOnly();
			nameOnly.setProduct_name("Object " + anotherNameOnly.getLastname());
			nameOnly.setProduct_desc("Object " + anotherNameOnly.getFirstname());
			list.add(nameOnly);
		}
		return list;
	}

}
