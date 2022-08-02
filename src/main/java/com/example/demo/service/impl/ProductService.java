package com.example.demo.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductExcelProcessState;
import com.example.demo.repository.ProductExcelProcessStateRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductExcelProcessStateRepository excelProcessStateRepository;

	public ProductExcelProcessState save(MultipartFile file) {
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
}
