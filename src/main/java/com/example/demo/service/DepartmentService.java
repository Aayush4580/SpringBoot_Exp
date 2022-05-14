package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;
import com.example.demo.exception.DepartmentNotFoundException;

public interface DepartmentService {

	public Department saveDepartment(Department department);

	public List<Department> fetchDepartment();

	public Department fetchDepartmentById(Long id) throws DepartmentNotFoundException;

	public void deleteDepartmentById(Long id);

	public Department updateDepartment(Department department) throws DepartmentNotFoundException;

	public Department fetchDepartmentByName(String name);

}
