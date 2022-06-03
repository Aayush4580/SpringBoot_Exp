package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Department;
import com.example.demo.entity.DepartmentReqBody;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.service.DepartmentService;

@RestController
@RequestMapping("/api")
public class DepartmentController {
	// http://localhost:8080/swagger-ui/index.html#/
	@Autowired
	private DepartmentService departmentService;

	private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@PostMapping("/department")
	public String saveDepartment(@Valid @RequestBody DepartmentReqBody department) {
		logger.info("inside save department method");
		return departmentService.saveDepartment(department);
	}

	@GetMapping("/department")
	public List<Department> fetchDepartment() {
		return departmentService.fetchDepartment();
	}

	@GetMapping("/department/{id}")
	public Department fetchDepartmentById(@PathVariable("id") Long id) throws DepartmentNotFoundException {
		return departmentService.fetchDepartmentById(id);
	}

	@GetMapping("/department/name/{name}")
	public Department fetchDepartmentByName(@PathVariable("name") String name) {
		return departmentService.fetchDepartmentByName(name);
	}

	@PutMapping("/department")
	public Department updateDepartmentById(@RequestBody Department department) throws DepartmentNotFoundException {
		return departmentService.updateDepartment(department);
	}

	@DeleteMapping("/department/{id}")
	public String deletehDepartmentById(@PathVariable("id") Long id) {
		try {
			departmentService.deleteDepartmentById(id);
			return "deparment deleted successfully";
		} catch (Exception ex) {
			return "id not found";
		}

	}

}
