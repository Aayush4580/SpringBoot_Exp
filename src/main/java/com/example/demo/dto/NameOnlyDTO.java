package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameOnlyDTO {
	private String product_desc;
	private String product_name;

	public NameOnlyDTO(String product_desc, String product_name) {
		super();
		this.product_desc = product_desc;
		this.product_name = product_name;
	}

}
