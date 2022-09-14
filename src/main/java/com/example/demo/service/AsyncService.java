package com.example.demo.service;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {
	CompletableFuture<Void> sendReminderToEmployee();

	String withoutFutureAsync() throws InterruptedException;
}
