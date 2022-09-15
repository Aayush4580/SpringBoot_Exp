package com.example.demo.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface AsyncService {
	CompletableFuture<Void> sendReminderToEmployee();

	String withoutFutureAsync() throws InterruptedException;

	void asyncMethod() throws InterruptedException;
	
	List<String> concurrentAwait() throws InterruptedException, ExecutionException;
}
