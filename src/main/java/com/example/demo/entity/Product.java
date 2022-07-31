package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
	@SequenceGenerator(name = "product_generator", allocationSize = 1, initialValue = 1, sequenceName = "product_seq")
	private Long id;
	// sequence generator will create new sequence in DB and will will update its
	// value afterAll post operation by the allocation size
	// usually for long id we should use sequence generator
	private Integer productId;

	private String productName;

	private String productDesc;

	private Double productPrice;

}
