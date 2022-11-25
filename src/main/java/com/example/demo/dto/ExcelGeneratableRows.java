package com.example.demo.dto;

import java.util.Map;

public interface ExcelGeneratableRows {
    Map<String, ExcelRow<?>> getRowForExcel();
}
