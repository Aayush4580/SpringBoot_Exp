package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@SpringBootTest
public class DepartmentServiceTest {

	@Autowired
	private DepartmentService departmentService;

	@MockBean
	private DepartmentRepository departmentRepository;

	@BeforeEach
	void setUp() {
		Department department = new Department();
		department.setDepartmentAddress("Ahemedabad");
		department.setDepartmentCode("IT-06");
		department.setDepartmentName("IT");
		department.setDepartmentId(1l);

		Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase(Mockito.anyString())).thenReturn(department);
	}

	@Test
	@DisplayName("Get Data based on department name")
	public void findDepartmentByName() {
		String departmentName = "IT";

		Department found = departmentService.fetchDepartmentByName(departmentName);
		assertEquals(departmentName, found.getDepartmentName());
	}

	@Test
	@DisplayName("Get all department")
	public void fetchDepartment() {
		List<Department> mockedList = new ArrayList<>();
		Department department = new Department();
		department.setDepartmentAddress("Ahemedabad");
		department.setDepartmentCode("IT-06");
		department.setDepartmentName("IT");
		department.setDepartmentId(1l);
		mockedList.add(department);
		Mockito.when(departmentRepository.findAll()).thenReturn(mockedList);
		List<Department> list = departmentService.fetchDepartment();
		assertEquals(list.size(), 1);
	}

	@Test
	@DisplayName("Get all department exception")
	public void fetchDepartmentException() {
		Mockito.when(departmentRepository.findAll())
				.thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
		ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
				() -> departmentService.fetchDepartment());
		assertEquals("500 INTERNAL_SERVER_ERROR", exception.getMessage());
	}
}
