package com.example.demo.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductExcelProcessState;
import com.example.demo.repository.ProductExcelProcessStateRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductExcelProcessStateRepository excelProcessStateRepository;

    public ProductExcelProcessState saveExcel(MultipartFile file) {
        ProductExcelProcessState excelProcessState = new ProductExcelProcessState();
        excelProcessState.setStatus("IN_PROGRESS");
        ProductExcelProcessState processState = excelProcessStateRepository.save(excelProcessState);
        Thread thread = new Thread(() -> {
            try {
                List<Product> products = ExcelHelper.convertExcelToListOfProduct(file.getInputStream());
                System.out.println("products  >>> " + products);

                productRepository.saveAll(products);
                excelProcessState.setStatus("COMPLETE");
                excelProcessStateRepository.save(excelProcessState);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        return processState;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> get200Products() {
        // TODO Auto-generated method stub
        return productRepository.get200Products();
    }

    public XSSFWorkbook generateDynamicExcel() {

        List<Product> products = productRepository.get200Products();
        XSSFWorkbook workbook = new XSSFWorkbook();
//        List<ExcelGeneratableRows> excelGeneratableRows = new ArrayList<>(products);
//        DynamicExcelHelper.writeToExcel(workbook, "Sheet1", excelGeneratableRows);
        return workbook;
    }
}
