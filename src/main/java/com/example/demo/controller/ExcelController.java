package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.CreateExcelPOIStream;
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
        String fileId = "department";
        ByteArrayInputStream in = masterServiceImpl.downloadsFiles(objects, fileType, fileId);
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

    @GetMapping("/excelStream")
    public void downloadExcelStream(HttpServletResponse response) throws IOException {
        System.err.println("starting time >> " + System.currentTimeMillis());
        String str = "StreamData";
        String headervalue = String.format("attachment; filename=%s%s", str, ".xlsx");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headervalue);

        CreateExcelPOIStream createExcelPOIStream = new CreateExcelPOIStream();
        // List<Department> departments = departmentService.fetchDepartment();
        List<Department> departments = new ArrayList<>();
        for(int i=1;i<=200000;i++){
            departments.add(new Department(Long.valueOf(i),"dName"+i,"DeparmentAddress"+i,"DCode"+i,"board"+i));
        }
        createExcelPOIStream.downloadStreamFiles(new ArrayList<>(departments), response);
        System.err.println("ending time >> " + System.currentTimeMillis());
    }
}
