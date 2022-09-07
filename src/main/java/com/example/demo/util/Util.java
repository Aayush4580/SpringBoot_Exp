package com.example.demo.util;

import java.io.File;
import java.nio.file.Files;

public class Util {
	public static void deleteDir(File file) {
		File[] contents = file.listFiles();
		System.err.println("contents >> " + contents);
		if (contents != null) {
			for (File f : contents) {
				if (!Files.isSymbolicLink(f.toPath())) {
					deleteDir(f);
				}
			}
		}
		file.delete();
	}
}
