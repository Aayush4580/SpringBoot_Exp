package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.example.demo.entity.ProductExcelProcessState;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProductExcelProcessStateService;
import com.example.demo.service.impl.ExcelHelper;
import com.example.demo.service.impl.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class DepartmentController {
	// http://localhost:8080/swagger-ui/index.html#/
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ProductExcelProcessStateService excelProcessStateService;

	@Autowired
	private ProductService productService;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		if (ExcelHelper.checkExcelFormat(file)) {
			ProductExcelProcessState excelProcessState = productService.save(file);
			System.out.println("exprocess State >>> ");
			Map<String, String> map = new HashMap<String, String>();
			map.put("excelProcessId", excelProcessState.getProcessId().toString());
			map.put("message", "Please request with the processId to get the updated status");
			return ResponseEntity.ok(map);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
	}

	@GetMapping("/getExcelUploadStatus/{id}")
	public ProductExcelProcessState getExcelUploadStatus(@PathVariable("id") String id) {
		log.info("inside getExcelUploadStatus method");
		return excelProcessStateService.getProductProcessStatus(Long.parseLong(id));
	}

	@PostMapping("/department")
	public String saveDepartment(@Valid @RequestBody DepartmentReqBody department) {
		log.info("inside save department method");
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
