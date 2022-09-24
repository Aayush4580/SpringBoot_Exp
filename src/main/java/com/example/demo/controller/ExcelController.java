package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.MasterServiceImpl;

@RestController
public class ExcelController {
	@Autowired
	MasterServiceImpl masterServiceImpl;

	@Autowired
	ProductService productService;

	@Autowired
	DepartmentService departmentService;

	@GetMapping(value = "/dynamicfile")
	public ResponseEntity<InputStreamResource> downloadsFiles() throws IOException {
		List<?> objects = new ArrayList<>();
//		if (flag == 1) {
//		objects = productService.get200Products();
		objects = departmentService.fetchDepartment();
//		}
		String fileType = "Excel";
		String fileName = "something";
		ByteArrayInputStream in = masterServiceImpl.downloadsFiles(objects, fileType);
		HttpHeaders headers = new HttpHeaders();
		if (fileType.equals("Excel")) {
			headers.add("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
		} else if (fileType.equals("Pdf")) {
			headers.add("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
		} else if (fileType.equals("Csv")) {
			headers.add("Content-Disposition", "attachment; filename=" + fileName + ".csv");
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
}
