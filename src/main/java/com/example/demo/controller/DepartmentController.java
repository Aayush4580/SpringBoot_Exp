package com.example.demo.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Department;
import com.example.demo.entity.DepartmentReqBody;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin
public class DepartmentController {
	// http://localhost:8080/swagger-ui/index.html#/
	@Autowired
	private DepartmentService departmentService;

//	public DepartmentController(DepartmentService departmentService) {
//		super();
//		this.departmentService = departmentService;
//	}

//	@Autowired
//	private ProductExcelProcessStateService excelProcessStateService;
////
//	@Autowired
//	private ProductService productService;
//
//	@PostMapping("/upload")
//	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
//	example upload excel exists in resource
//		if (ExcelHelper.checkExcelFormat(file)) {
//			ProductExcelProcessState excelProcessState = productService.saveExcel(file);
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("excelProcessId", excelProcessState.getProcessId().toString());
//			map.put("message", "Please request with the processId to get the updated status");
//			return ResponseEntity.ok(map);
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
//	}
//
//	@GetMapping("/getExcelUploadStatus/{id}")
//	public ProductExcelProcessState getExcelUploadStatus(@PathVariable("id") String id) throws InterruptedException {
//		log.info("inside getExcelUploadStatus method");
////		Thread.sleep(3000);
//		return excelProcessStateService.getProductProcessStatus(Long.parseLong(id));
//	}

	@PostMapping("/department")
	public String saveDepartment(@Valid @RequestBody DepartmentReqBody department) {
		log.info("inside save department method");
		return departmentService.saveDepartment(department);
	}

	@GetMapping("/getDepartment")
	public List<Department> getDepartment(@RequestParam("code") String code,
			@RequestParam("departmentName") String departmentName, @RequestParam("board") String board) {
		log.info("inside save department method");
		return departmentService.getDepartment(code, departmentName, board.toUpperCase());
	}

	@GetMapping("/getDepartmentByPojo")
	public List<Department> getDepartmentByPojo() {
		System.err.println("inside method >>>> ");
		Department department = new Department();
//		department.setDepartmentId(Long.parseLong("8"));
//		department.setBoard("CBSC");
		department.setDepartmentName("ME");
//		department.setDepartmentCode(code);
		System.out.println("department >> " + department);
		return departmentService.getDepartmentByPojo(department);
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
		log.info(">>>>> delete called >>>> ");
		try {
			departmentService.deleteDepartmentById(id);
			return "deparment deleted successfully";
		} catch (Exception ex) {
			log.info("inside catch block");
			throw new EntityNotFoundException("not found with id " + id);
		}

	}

}
