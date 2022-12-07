package com.example.demo.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.example.demo.dto.ExcelGeneratableRows;
import com.example.demo.dto.ExcelRow;

import lombok.Data;

@Entity
@Data
public class Product implements ExcelGeneratableRows {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", allocationSize = 1, initialValue = 1, sequenceName = "product_seq")
    private Long id;
    // sequence generator will create new sequence in DB and will will update its
    // value afterAll post operation by the allocation size
    // usually for long id we should use sequence generator
    private Integer productId;

    private String productName;

    @Column(name = "PRODUCT_DESC")
    private String productDesc;

    private Double productPrice;

    @Override
    public Map<String, ExcelRow<?>> getRowForExcel() {
        // TODO Auto-generated method stub
        return new LinkedHashMap<>() {
            {
                put("Product Id", new ExcelRow<>(productId));
                put("Product Name", new ExcelRow<>(productName));
                put("Product Desc", new ExcelRow<>(productDesc));
                put("Product Price", new ExcelRow<>(productPrice));
            }
        };
    }

}
