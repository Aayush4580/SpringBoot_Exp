package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	public Department findByDepartmentNameIgnoreCase(String name);

	@Query(value = "SELECT PRODUCT_DESC firstname, PRODUCT_NAME lastname  FROM PRODUCT where id='22635'", nativeQuery = true)
	NameOnly findByNativeQuery(Integer id);

	public static interface NameOnly {

		String getFirstname();

		String getLastname();

	}

	@Query(value = "SELECT PRODUCT_DESC firstname, PRODUCT_NAME lastname  FROM PRODUCT where id='22637'", nativeQuery = true)
	AnotherNameOnly findByNativeQueryAnother(Integer id);

	public static interface AnotherNameOnly {

		String getFirstname();

		String getLastname();

	}
}
