package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.dto.ExcelGeneratableRows;
import com.example.demo.dto.ExcelRow;

public class DynamicExcelHelper {
    public static void writeToExcel(XSSFWorkbook workbook, String sheetName, List<ExcelGeneratableRows> rows) {
        if (!Strings.isNotEmpty(sheetName))
            sheetName = "Sheet 1";
        Sheet sheet = workbook.createSheet(sheetName);

        List<String> headers = new ArrayList<>(rows.get(0).getRowForExcel().keySet());
        int rowCount = 0;
        int colCount = 0;
        Row row = sheet.createRow(rowCount++);

        // create the headers row
        for (String header : headers) {
            Cell cell = row.createCell(colCount++);
            cell.setCellValue(header);
        }

        // create the Data rows
        List<Map<String, ExcelRow<?>>> data = rows.stream().map(ExcelGeneratableRows::getRowForExcel)
                .collect(Collectors.toList());
        for (final Map<String, ExcelRow<?>> rowData : data) {
            row = sheet.createRow(rowCount++);
            colCount = 0;
            for (String header : headers) {
                Cell cell = row.createCell(colCount++);
                Object cellValue = rowData.get(header).getValue();
                if (rowData.get(header).getHasFormula()) {
                    String formula = (String) rowData.get(header).getValue();
                    cell.setCellFormula(formula.replace("$", String.valueOf(rowCount)));
                } else {
                    if (cellValue != null) {
                        if (cellValue instanceof String) {
                            cell.setCellValue(String.valueOf(cellValue));
                        } else if (cellValue instanceof Integer) {
                            cell.setCellValue((Integer) cellValue);
                        }
                    }
                }
                sheet.autoSizeColumn(colCount);
                colCount++;
            }
        }

    }
}
