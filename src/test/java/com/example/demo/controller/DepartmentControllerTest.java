package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.Department;
import com.example.demo.entity.DepartmentReqBody;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;

	@BeforeEach
	void setUp() {
	}

	@Test
	void saveDepartment() throws Exception {
		Department inputDepartment = new Department(1l,"Ahemedabad","IT-06","IT","CBSC");

		DepartmentReqBody body = new DepartmentReqBody();
		body.setBoardList(List.of(1, 2));
		body.setDepartment(inputDepartment);
		Mockito.when(departmentService.saveDepartment(body)).thenReturn("succesfuly saved department");

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(body);
		mockMvc.perform(post("/api/department").contentType(MediaType.APPLICATION_JSON)
//				.content("{\n" + "\t\"departmentName\":\"IT\",\n" + "\t\"departmentAddress\":\"Ahmedabad\",\n"
//						+ "\t\"departmentCode\":\"IT-06\"\n" + "}")
				.content(jsonStr)).andExpect(status().isOk());

	}

	@Test
	void fetchDepartmentById() throws Exception {
		Department department = new Department();
		department.setDepartmentAddress("Ahemedabad");
		department.setDepartmentCode("IT-06");
		department.setDepartmentName("IT");
		department.setDepartmentId(1l);
		Mockito.when(departmentService.fetchDepartmentById(1L)).thenReturn(department);

		mockMvc.perform(get("/api/department/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentName").value(department.getDepartmentName()));
	}

	@Test
	void fetchDepartmentByIdException() throws Exception {
		Mockito.when(departmentService.fetchDepartmentById(1L)).thenThrow(new DepartmentNotFoundException("Not Found"));

		mockMvc.perform(get("/api/department/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
