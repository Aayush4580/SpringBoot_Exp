package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AsyncDTO;
import com.example.demo.service.impl.AsyncServiceImpl;

@RestController
public class AsyncController {

	@GetMapping("slow")
	public AsyncDTO slow() throws InterruptedException {
		// this function takes 1 second to execute
		Thread.sleep(1000);
		return new AsyncDTO("Hello");
	}

	@GetMapping("slow1")
	public AsyncDTO slow1() throws InterruptedException {
		// this function takes 2 second to execute
		Thread.sleep(2000);
		return new AsyncDTO("Hello bye");
	}

	@GetMapping("slowTest")
	public List<AsyncDTO> slowTest() throws InterruptedException {
		// this function will work as an promise all and consume both slow and slow1 api
		// and return response based on the max time taken api. that is 2 sec
		AsyncServiceImpl asyncServiceImpl = new AsyncServiceImpl();
		return asyncServiceImpl.slowTest();
	}

	@GetMapping("slowTest2")
	public CompletableFuture<String> slowTest2() throws Exception {
		AsyncServiceImpl asyncServiceImpl = new AsyncServiceImpl();
		return CompletableFuture.supplyAsync(() -> {
			try {
				asyncServiceImpl.newServiceCall(2000);
				asyncServiceImpl.newServiceCall(2000);
				asyncServiceImpl.newServiceCall(3000);
				return "Result of the asynchronous computation";
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e.getMessage();
			}

		});
	}

}
