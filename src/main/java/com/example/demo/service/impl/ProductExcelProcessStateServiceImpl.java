package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductExcelProcessState;
import com.example.demo.repository.ProductExcelProcessStateRepository;
import com.example.demo.service.ProductExcelProcessStateService;

@Service
public class ProductExcelProcessStateServiceImpl implements ProductExcelProcessStateService{
    @Autowired
	private ProductExcelProcessStateRepository excelProcessStateRepository;

	public ProductExcelProcessState getProductProcessStatus(Long productProcessId) {
		return excelProcessStateRepository.findById(productProcessId).get();
	}
}
