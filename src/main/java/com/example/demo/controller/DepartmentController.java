package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Department;
import com.example.demo.entity.DepartmentReqBody;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.impl.ExcelHelper;
import com.example.demo.service.impl.ProductService;

@RestController
@RequestMapping("/api")
public class DepartmentController {
	// http://localhost:8080/swagger-ui/index.html#/
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ProductService productService;

	private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		if (ExcelHelper.checkExcelFormat(file)) {
			// true

			this.productService.save(file);

			return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
	}

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
