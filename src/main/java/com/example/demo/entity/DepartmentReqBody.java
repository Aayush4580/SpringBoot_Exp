package com.example.demo.entity;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class DepartmentReqBody {
	@Valid
	private Department department;
	@NotEmpty(message = "board list cannot be empty")
	private List<Integer> boardList;
}
