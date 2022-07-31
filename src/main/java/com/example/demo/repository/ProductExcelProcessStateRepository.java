package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductExcelProcessState;

@Repository
public interface ProductExcelProcessStateRepository extends JpaRepository<ProductExcelProcessState, Long> {

}
