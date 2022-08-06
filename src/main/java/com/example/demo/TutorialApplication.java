package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TutorialApplication {

	@Value("${domain.url}")
	private static String URL;

	public static void main(String[] args) {

		SpringApplication.run(TutorialApplication.class, args);
//		System.out.println("url in main >> " + URL);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
