package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ProductExcelProcessState {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long processId;
	private String status;
}
