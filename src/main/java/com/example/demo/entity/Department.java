package com.example.demo.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.ExcelGeneratableRows;
import com.example.demo.dto.ExcelRow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department implements ExcelGeneratableRows {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long departmentId;
    @NotBlank(message = "Please add department name")
    @NotNull(message = "Department name cannot be null")
    private String departmentName;
    private String departmentAddress;
    @NotBlank(message = "Please add department Code")
    private String departmentCode;
    private String board;

    @Override
    public Map<String, ExcelRow<?>> getRowForExcel() {
        return new LinkedHashMap<>() {
            {
                put("Department ID", new ExcelRow<>(departmentId));
                put("Department Name", new ExcelRow<>(departmentName));
                put("Department Address", new ExcelRow<>(departmentAddress));
                put("Department Code", new ExcelRow<>(departmentCode));
                put("Board", new ExcelRow<>(board));
            }
        };
    }

}
