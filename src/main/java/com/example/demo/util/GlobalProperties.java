package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalProperties {

	public static String URL;
	public static String MESSAGE;

	@Value("${domain.url}")
	public void setUrl(String url) {
		URL = url;
	}

	@Value("${welcome.message}")
	public void setMessage(String msg) {
		MESSAGE = msg;
	}

}
