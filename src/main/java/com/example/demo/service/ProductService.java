package com.example.demo.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductExcelProcessState;

public interface ProductService {
    ProductExcelProcessState saveExcel(MultipartFile file);

    List<Product> getAllProducts();

    List<Product> get200Products();

    XSSFWorkbook generateDynamicExcel();
}
