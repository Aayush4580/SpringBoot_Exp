package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AsyncDTO;
import com.example.demo.service.impl.AsyncServiceImpl;

@RestController
public class AsyncController {

	@GetMapping("slow")
	public AsyncDTO slow() throws InterruptedException {
		Thread.sleep(1000);
		return new AsyncDTO("Hello");
	}

	@GetMapping("slow1")
	public AsyncDTO slow1() throws InterruptedException {
		Thread.sleep(2000);
		return new AsyncDTO("Hello bye");
	}

	@GetMapping("slowTest")
	public List<AsyncDTO> slowTest() throws InterruptedException {
		AsyncServiceImpl asyncServiceImpl = new AsyncServiceImpl();
		return asyncServiceImpl.slowTest();
	}
}
