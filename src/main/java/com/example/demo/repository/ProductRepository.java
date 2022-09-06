package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.NameOnlyDTO;
import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

//	@Query("SELECT new com.example.demo.dto.NameOnlyDTO(p.PRODUCT_DESC,p.PRODUCT_NAME) FROM PRODUCT p where p.id='22635'")
	@Query("SELECT new com.example.demo.dto.NameOnlyDTO(p.productDesc,p.productName )FROM Product p where p.id='22635'")
	NameOnlyDTO findByNativeQuery(Integer id);

	@Query(value = "SELECT * FROM PRODUCT limit 200", nativeQuery = true)
	List<Product> get200Products();
}
