package com.example.demo.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateopenCSV {
    public static void writeDataToCSVUsingArray(PrintWriter writer, String[] csvHeader, List<?> dataList)
            throws IOException {
        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

//        String[] nameMapping = { "id", "productId", "productName", "productPrice" };
        String[] nameMapping = getColumnsMapper(dataList);
//        System.err.println("nameMapping >> " + Arrays.toString(nameMapping));
        csvWriter.writeHeader(csvHeader);

        for (Object row : dataList) {
            csvWriter.write(row, nameMapping);
        }

        csvWriter.close();
    }

    private final static String[] getColumnsMapper(List<?> objects) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String strObjects = mapper.writeValueAsString(objects.get(0));
//        System.err.println("strObjects >> " + strObjects);
        String[] setHeader = strObjects.substring(1, strObjects.length() - 1).split(",");
//        System.err.println("setHeader >> " + Arrays.toString(setHeader));
        String header = "";
        for (int i = 0; i < setHeader.length; i++) {
            String str = setHeader[i].toString().split(":")[0].toString();
            header += str.substring(1, str.length() - 1) + ",";
        }
        return header.split(",");
    }
}
