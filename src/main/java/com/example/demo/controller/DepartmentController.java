package com.example.demo.controller;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

import com.example.demo.dto.NameOnlyDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.DepartmentReqBody;
import com.example.demo.entity.Product;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.ExcelHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class DepartmentController {
	// http://localhost:8080/swagger-ui/index.html#/
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;
//	@Autowired
//	private ProductExcelProcessStateService excelProcessStateService;

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

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headervalue = "attachment; filename=Student_info.xlsx";

		response.setHeader(headerKey, headervalue);
//		List<Product> products = productService.getAllProducts();
		List<Product> products = productService.get200Products();
//		System.err.println("products " + products);
		ExcelHelper exp = new ExcelHelper();
		exp.export(products, response);

	}

	// define a location
	public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

	// Define a method to upload files
	@PostMapping("/uploadToDir")
	public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles)
			throws IOException {
		System.err.println("inside method >>>> ");
		List<String> filenames = new ArrayList<>();
		System.err.println(" System.getProperty(\"user.home\") >>> " + System.getProperty("user.home"));
		System.err.println("DIRECTORY >> " + DIRECTORY);
		for (MultipartFile file : multipartFiles) {
			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
			copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
			filenames.add(filename);
		}
		return ResponseEntity.ok().body(filenames);
	}

	// Define a method to download files
	@GetMapping("downloadFromDir/{filename}")
	public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename)
			throws IOException, InterruptedException {
		Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
		if (!Files.exists(filePath)) {
			throw new FileNotFoundException(filename + " was not found on the server");
		}
		Thread.sleep(1000);
		Resource resource = new UrlResource(filePath.toUri());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("File-Name", filename);
		httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
		Thread.sleep(1000);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
				.headers(httpHeaders).body(resource);
	}

	@PostMapping("/department")
	public String saveDepartment(@RequestBody @Valid DepartmentReqBody department) {
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

	@GetMapping("/projection")
	public NameOnlyDTO projectionTest(@RequestBody Department department) throws DepartmentNotFoundException {
		return productRepository.findByNativeQuery(1);
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
