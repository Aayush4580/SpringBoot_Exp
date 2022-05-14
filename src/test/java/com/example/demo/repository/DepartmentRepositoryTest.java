package com.example.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entity.Department;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class DepartmentRepositoryTest {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@BeforeEach
	void setUp() {
		Department department = Department.builder().departmentName("ME").departmentAddress("Ahmedabad")
				.departmentCode("ME-06").build();

		entityManager.persist(department);
	}
	
	@Test
	public void findDepartmentById() {
		Department department=departmentRepository.findById((long) 1).get();
		assertEquals(department.getDepartmentName(), "ME");
	}
}
