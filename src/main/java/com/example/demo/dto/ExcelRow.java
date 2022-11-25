package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExcelRow<T> {
    private T value;
    private Boolean hasFormula;

    public ExcelRow(T value) {
        this.value = value;
        this.hasFormula = false;
    }
}
