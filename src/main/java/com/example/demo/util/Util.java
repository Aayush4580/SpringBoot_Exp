package com.example.demo.util;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class Util {
	public static void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				if (!Files.isSymbolicLink(f.toPath())) {
					deleteDir(f);
				}
			}
		}
		file.delete();
	}

	public static String getData(String val, String fileId) {
		switch (fileId) {
		case "department":
			return departmentHeader(val);
		default:
			return val;
		}

	}

	private static String departmentHeader(String val) {
		HashMap<String, String> product_map = new HashMap<>();
		product_map.put("departmentId", "Deparment ID");
		product_map.put("departmentName", "Deparment Name");
		product_map.put("departmentAddress", "Deparment Address");
		product_map.put("departmentCode", "Deparment Code");
		product_map.put("board", "Board");
		return product_map.get(val) != null ? product_map.get(val) : val;
	}
}
