package com.example.demo.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public void save(MultipartFile file) {

		try {
			List<Product> products = ExcelHelper.convertExcelToListOfProduct(file.getInputStream());
			System.out.println("products  >>> " + products);
			this.productRepository.saveAll(products);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Product> getAllProducts() {
		return this.productRepository.findAll();
	}
}
