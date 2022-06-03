package com.example.demo.entity;

import java.util.List;

import lombok.Data;

@Data
public class DepartmentReqBody {
	private Department department;
	private List<Integer> boardList;
}
