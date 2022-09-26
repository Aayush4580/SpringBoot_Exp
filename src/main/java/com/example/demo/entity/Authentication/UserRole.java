package com.example.demo.entity.Authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "role_Name" }))
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private Integer roleId;
	@Column(name = "role_Name", unique = true)
	private String roleName;
	private String roleDescription;
}
