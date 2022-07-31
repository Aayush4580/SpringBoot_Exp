package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	private Integer productId;

	private String productName;

	private String productDesc;

	private Double productPrice;

}
