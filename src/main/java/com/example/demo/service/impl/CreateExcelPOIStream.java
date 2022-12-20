package com.example.demo.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.example.demo.dto.ExcelGeneratableRows;
import com.example.demo.dto.ExcelRow;

public class CreateExcelPOIStream {
    public void downloadStreamFiles(List<ExcelGeneratableRows> rows, HttpServletResponse response)
            throws IOException {

        FileOutputStream streamOut;
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed
                                                             // to disk
            Sheet sheet = workbook.createSheet();

            // Output data
            List<String> headers = new ArrayList<>(rows.get(0).getRowForExcel().keySet());
            int rownum = 0;
            int colCount = 0;
            Row row = sheet.createRow(rownum++);

            // create the headers row
            for (String header : headers) {
                Cell cell = row.createCell(colCount++);
                cell.setCellValue(header);
            }
            // create the Data rows
            List<Map<String, ExcelRow<?>>> data = rows.stream().map(ExcelGeneratableRows::getRowForExcel)
                    .collect(Collectors.toList());
           
            for (final Map<String, ExcelRow<?>> rowData : data) {
                row = sheet.createRow(rownum++);
                colCount = 0;
                for (String header : headers) {
                    Cell cell = row.createCell(colCount++);
                    Object cellValue = rowData.get(header).getValue();
                    if (cellValue != null) {
                        if (cellValue instanceof String) {
                            cell.setCellValue(String.valueOf(cellValue));
                        } else if (cellValue instanceof Integer) {
                            cell.setCellValue((Integer) cellValue);
                        } else if (cellValue instanceof Double) {
                            cell.setCellValue((Double) cellValue);
                        } else if (cellValue instanceof Long) {
                            cell.setCellValue((Long) cellValue);
                        }
                    }
                }
            }
            // Save workbook to final destination
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
