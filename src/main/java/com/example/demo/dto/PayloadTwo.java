package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadTwo {

	private long id;
	private String name;
	private List<String> addresssList;

}
