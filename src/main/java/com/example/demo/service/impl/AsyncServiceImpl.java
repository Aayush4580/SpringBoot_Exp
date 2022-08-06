package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.AsyncDTO;
import com.example.demo.service.CallBack;
import com.example.demo.util.GlobalProperties;

@Service
public class AsyncServiceImpl {

	public List<AsyncDTO> slowTest() throws InterruptedException {
		List<AsyncDTO> asyncDTOs = new ArrayList<AsyncDTO>();
		Thread[] ts = new Thread[] { new Thread(() -> {
			run("/slow", (response) -> {
				asyncDTOs.add((AsyncDTO) response);
			});
		}), new Thread(() -> {
			run("/slow1", new CallBack() {

				@Override
				public void getCallBack(Object response) {
					asyncDTOs.add((AsyncDTO) response);
				}
			});
		}) };
		for (Thread t : ts) {
			t.start();
		}
		try {
			for (Thread t : ts) {
				t.join();
			}
			return asyncDTOs;
		} catch (InterruptedException ex) {
			throw new InterruptedException(ex.getMessage());
		}

	}

	public void run(String endpoint, CallBack callBack) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			AsyncDTO response = restTemplate.getForObject(GlobalProperties.URL + endpoint, AsyncDTO.class);
			callBack.getCallBack(response);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}
}
