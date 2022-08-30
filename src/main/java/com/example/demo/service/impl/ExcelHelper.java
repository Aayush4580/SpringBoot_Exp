package com.example.demo.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Department;
import com.example.demo.entity.Product;

public class ExcelHelper {

	// check that file is of excel type or not
	public static boolean checkExcelFormat(MultipartFile file) {

		String contentType = file.getContentType();

		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		} else {
			return false;
		}

	}

	// convert excel to list of products
	public static List<Product> convertExcelToListOfProduct(InputStream is) {
		List<Product> list = new ArrayList<>();

		try {

			try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
				XSSFSheet sheet = workbook.getSheet("Sheet1");

				int rowNumber = 0;
				Iterator<Row> iterator = sheet.iterator();

				while (iterator.hasNext()) {
					Row row = iterator.next();

					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					Iterator<Cell> cells = row.iterator();

					int cid = 0;

					Product p = new Product();

					while (cells.hasNext()) {
						Cell cell = cells.next();

						switch (cid) {
						case 0:
							p.setProductId((int) cell.getNumericCellValue());
							break;
						case 1:
							p.setProductName(cell.getStringCellValue());
							break;
						case 2:
							p.setProductDesc(cell.getStringCellValue());
							break;
						case 3:
							p.setProductPrice(cell.getNumericCellValue());
							break;
						default:
							break;
						}
						cid++;

					}

					list.add(p);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	// export to excel
	private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeHeaderLine(List<Department> departmentList, XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.createSheet("Student");

		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		createCell(sheet, row, 0, "Department Information", style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		font.setFontHeightInPoints((short) (10));

		row = sheet.createRow(1);
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		createCell(sheet, row, 0, "Department Id", style);
		createCell(sheet, row, 1, "Department Name", style);
		createCell(sheet, row, 2, "Department Code", style);
		createCell(sheet, row, 3, "Department Address", style);
		createCell(sheet, row, 4, "Board", style);

		// writeDataLines
		int rowCount = 2;

		CellStyle style1 = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeight(14);
		style1.setFont(font1);

		for (Department stu : departmentList) {
			Row row1 = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(sheet, row1, columnCount++, stu.getDepartmentId(), style1);
			createCell(sheet, row1, columnCount++, stu.getDepartmentName(), style1);
			createCell(sheet, row1, columnCount++, stu.getDepartmentCode(), style1);
			createCell(sheet, row1, columnCount++, stu.getDepartmentAddress(), style1);
			createCell(sheet, row1, columnCount++, stu.getBoard(), style1);
		}

	}

	public void export(List<Department> listStudent, HttpServletResponse response) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		writeHeaderLine(listStudent, workbook);

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

}
