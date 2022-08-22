package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entity.Department;

@DataJpaTest
public class DepartmentRepositoryTest {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	void setUp() {
		Department department = new Department();
		department.setDepartmentAddress("Ahemedabad");
		department.setDepartmentCode("IT-06");
		department.setDepartmentName("IT");
		department.setDepartmentId(1l);
	}

	@Test
	public void findDepartmentById() {
		Department department = departmentRepository.findById((long) 1).get();
		assertEquals(department.getDepartmentName(), "ME");
	}
}
