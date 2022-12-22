package com.example.demo.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.demo.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MasterServiceImpl {
    public static List<HashMap<Object, Object>> getListOfObjectToListOfHashMap(List<?> objects)
            throws JsonProcessingException {
        List<HashMap<Object, Object>> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < objects.size(); i++) {
            HashMap<Object, Object> map = new HashMap<>();
            String temp = mapper.writeValueAsString(objects.get(i));
            String temo1 = temp.substring(1, temp.length() - 1);
            String[] temp2 = temo1.split(",");
            for (int j = -1; j < temp2.length; j++) {
                if (j == -1) {
                    map.put("SrNo", i + 1);
                } else {
                    String tempKey = temp2[j].toString().split(":")[0].toString();
                    String tempValue = temp2[j].toString().split(":")[1].toString();
                    char ch = tempValue.charAt(0);
                    if (ch == '"') {
                        map.put(tempKey.substring(1, tempKey.length() - 1),
                                tempValue.substring(1, tempValue.length() - 1));
                    } else {
                        map.put(tempKey.substring(1, tempKey.length() - 1), tempValue);
                    }
                }
            }
            list.add(map);
        }
        return list;
    }

    public ByteArrayInputStream downloadsFiles(List<?> objects, String fileType, String fileId) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<HashMap<Object, Object>> list = getListOfObjectToListOfHashMap(objects);

        String[] COLUMNs = getColumnsNameFromListOfObject(objects);
        System.err.println("COLUMNs >> " + Arrays.toString(COLUMNs));
        try {
            if (fileType.equals("Excel")) {
                generateExcel(list, COLUMNs, fileId, out);
            }
//			else if (fileType.equals("Pdf")) {
//				generatePdf(list, COLUMNs, out);
//			} else if (fileType.equals("Csv")) {
//				generatePdf(list, COLUMNs, out);
//			}
        } catch (Exception ex) {
            System.out.println("Error occurred:" + ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public static final String[] getColumnsNameFromListOfObject(List<?> objects) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String strObjects = mapper.writeValueAsString(objects.get(0));
        String[] setHeader = strObjects.substring(1, strObjects.length() - 1).split(",");
        String header = "SrNo";
        for (int i = 0; i < setHeader.length; i++) {
            String str = setHeader[i].toString().split(":")[0].toString();
            header = header + "," + str.substring(1, str.length() - 1);
        }
        return header.split(",");
    }

    public static final void generateExcel(List<HashMap<Object, Object>> list, String[] COLUMNs, String fileId,
            ByteArrayOutputStream out) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Excelshit");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(Util.getData(COLUMNs[col], fileId));
                cell.setCellStyle(headerCellStyle);
            }
            int rowIdx = 1;
            for (int k = 0; k < list.size(); k++) {
                Row row = sheet.createRow(rowIdx++);
                for (Map.Entry<Object, Object> entry : list.get(k).entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    for (int col = 0; col < COLUMNs.length; col++) {
                        if (key.toString().equals(COLUMNs[col].toString())) {
                            row.createCell(col).setCellValue(value.toString());
                        }
                    }
                }
            }
            workbook.write(out);
        }
    }

//	private static final void generatePdf(List<HashMap<Object, Object>> list, String[] COLUMNs,
//			ByteArrayOutputStream out) throws DocumentException {
//		Document document = new Document();
//		com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
//		com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
//		PdfPCell hcell = null;
//		PdfPTable table = new PdfPTable(COLUMNs.length);
//		for (int col = 0; col < COLUMNs.length; col++) {
//			hcell = new PdfPCell(new Phrase(COLUMNs[col], headerFont));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			table.addCell(hcell);
//		}
//		for (int index = 0; index < list.size(); index++) {
//			PdfPCell cell = null;
//			for (Map.Entry<Object, Object> entry : list.get(index).entrySet()) {
//				Object key = entry.getKey();
//				Object value = entry.getValue();
//				for (int col = 0; col < COLUMNs.length; col++) {
//					if (key.toString().equals(COLUMNs[col].toString())) {
//						cell = new PdfPCell(new Phrase(value.toString(), dataFont));
//						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					}
//				}
//				table.addCell(cell);
//			}
//		}
//		PdfWriter.getInstance(document, out);
//		document.open();
//		document.add(table);
//		document.close();
//	}
}
