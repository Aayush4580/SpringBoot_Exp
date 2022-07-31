package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductExcelProcessState;
import com.example.demo.repository.ProductExcelProcessStateRepository;

@Service
public class ProductExcelProcessStateService {

	@Autowired
	private ProductExcelProcessStateRepository excelProcessStateRepository;

	public ProductExcelProcessState getProductProcessStatus(Long productProcessId) {
		return excelProcessStateRepository.findById(productProcessId).get();
	}
}
