package com.example.demo.service;
import com.example.demo.entity.ProductExcelProcessState;


public interface ProductExcelProcessStateService {
	ProductExcelProcessState getProductProcessStatus(Long productProcessId);
}
