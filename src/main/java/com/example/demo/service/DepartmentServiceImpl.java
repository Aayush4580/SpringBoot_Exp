package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public Department saveDepartment(Department department) {
		// TODO Auto-generated method stub

		return departmentRepository.save(department);
	}

	@Override
	public List<Department> fetchDepartment() {
		// TODO Auto-generated method stub
		return departmentRepository.findAll();
	}

	@Override
	public Department fetchDepartmentById(Long id) throws DepartmentNotFoundException {
		// TODO Auto-generated method stub
		Optional<Department> department= departmentRepository.findById(id);
		if(!department.isPresent()) {
			throw new DepartmentNotFoundException("Department not found with id: "+id);
		}
		return department.get();
		
	}

	@Override
	public void deleteDepartmentById(Long id) {
		// TODO Auto-generated method stub
		departmentRepository.deleteById(id);
	}

	@Override
	public Department updateDepartment(Department department) throws DepartmentNotFoundException {
		// TODO Auto-generated method stub
		Department dm = fetchDepartmentById(department.getDepartmentId());
		dm.setDepartmentAddress(department.getDepartmentAddress());
		dm.setDepartmentCode(department.getDepartmentCode());
		return departmentRepository.save(dm);
	}

	@Override
	public Department fetchDepartmentByName(String name) {
		// TODO Auto-generated method stub
		return departmentRepository.findByDepartmentNameIgnoreCase(name);
	}

}
