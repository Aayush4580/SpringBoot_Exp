package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.AsyncDTO;
import com.example.demo.service.AsyncService;
import com.example.demo.service.CallBack;
import com.example.demo.util.GlobalProperties;

@Service
public class AsyncServiceImpl implements AsyncService {

    public List<AsyncDTO> slowTest() throws InterruptedException {
        List<AsyncDTO> asyncDTOs = new ArrayList<AsyncDTO>();
        try {
            Thread[] ts = new Thread[] { new Thread(() -> {
                restTemplateCall("/slow", (response) -> {
                    asyncDTOs.add((AsyncDTO) response);
                });
            }), new Thread(() -> {
                restTemplateCall("/slow1", new CallBack() {
                    @Override
                    public void getCallBack(Object response) {
                        asyncDTOs.add((AsyncDTO) response);
                    }
                });
            }) };

            for (Thread t : ts) {
                t.start();
            }

            for (Thread t : ts) {
                t.join();
            }
            return asyncDTOs;
        } catch (InterruptedException ex) {
            throw new InterruptedException(ex.getMessage());
        }

    }

    public void restTemplateCall(String endpoint, CallBack callBack) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            AsyncDTO response = restTemplate.getForObject(GlobalProperties.URL + endpoint, AsyncDTO.class);
            callBack.getCallBack(response);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public AsyncDTO newServiceCall(Integer time) throws InterruptedException {
        Thread.sleep(time);
        return new AsyncDTO("hello");
    }

    @Override
    public List<String> concurrentAwait() throws InterruptedException, ExecutionException {
        System.err.println("att the start of method 1 >>>>> ");
        List<CompletableFuture<String>> completableFutures = Arrays.asList(concurrentAwait2(), concurrentAwait3());

        CompletableFuture<Void> resultantCf = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

        CompletableFuture<List<String>> allFutureResults = resultantCf
                .thenApply(t -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        List<String> strList = allFutureResults.get();
        System.out.println("Result - " + strList);
        System.err.println("att the end >>>>> ");
        return strList;
    }

    public CompletableFuture<String> concurrentAwait2() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            sleepThread(5000);
            String stringToPrint = "Educative";
            System.out.println("----\nsupplyAsync first future - " + stringToPrint);
            System.out.println("Thread execution - " + Thread.currentThread().getName());
            return stringToPrint;
        });
        return completableFuture1;
    }

    public CompletableFuture<String> concurrentAwait3() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            sleepThread(3000);
            String stringToPrint = "Edpresso";
            System.out.println("----\nsupplyAsync second future - " + stringToPrint);
            System.out.println("Thread execution - " + Thread.currentThread().getName());
            return stringToPrint;
        });
        return completableFuture2;
    }

    @Override
    public void asyncMethod() throws InterruptedException {
        System.err.println("att the start of method 1 >>>>> ");
        method2().thenAcceptAsync((str) -> {
            System.err.println("att the end of method 2 >>>>> ");
        });
        method3().thenAcceptAsync((str) -> {
            System.err.println("att the end of method 3 >>>>> ");
        });
        System.err.println("att the end >>>>> ");
    }

    public static CompletableFuture<String> method2() {
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.err.println("inside method 2 >>>>> " + Thread.currentThread().getName());
            sleepThread(5000);
            return "method 2";
        });
        return voidCompletableFuture;
    }

    public static CompletableFuture<String> method3() throws InterruptedException {
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.err.println("inside method 3 >>>>> " + Thread.currentThread().getName());
            sleepThread(2000);
            return "method 3";
        });
        return voidCompletableFuture;
    }

    @Override
    public CompletableFuture<Void> sendReminderToEmployee() {
//
//        Executor executor=Executors.newFixedThreadPool(5);
//
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("fetchEmployee : " + Thread.currentThread().getName());
//            return EmployeeDatabase.fetchEmployees();
//        },executor).thenApplyAsync((employees) -> {
//			  this employees contain all the employees
//            System.out.println("filter new joiner employee  : " + Thread.currentThread().getName());
//            return employees.stream()
//                    .filter(employee -> "TRUE".equals(employee.getNewJoiner()))
//                    .collect(Collectors.toList());
//        },executor).thenApplyAsync((employees) -> {
//		      this employees contain new joiner employees from all the employees
//            System.out.println("filter training not complete employee  : " + Thread.currentThread().getName());
//            return employees.stream()
//                    .filter(employee -> "TRUE".equals(employee.getLearningPending()))
//                    .collect(Collectors.toList());
//        },executor).thenApplyAsync((employees) -> {
//            System.out.println("get emails  : " + Thread.currentThread().getName());
//            return employees.stream().map(Employee::getEmail).collect(Collectors.toList());
//        },executor).thenAcceptAsync((emails) -> {
//			  accept doesn't return anything
//            System.out.println("send email  : " + Thread.currentThread().getName());
//            emails.forEach(EmployeeReminderService::sendEmail);
//        },executor);
//        return voidCompletableFuture;
        return null;
    }

    @Override
    public String withoutFutureAsync() throws InterruptedException {
        List<Thread> threads = new ArrayList<Thread>();
        threads.add(new Thread(() -> {
            System.out.println("executing first thread  : " + Thread.currentThread().getName());
            sleepThread(1000);
        }));
        threads.add(new Thread(() -> {
            System.out.println("executing second thread  : " + Thread.currentThread().getName());
            sleepThread(2000);
        }));
        threads.add(new Thread(() -> {
            System.out.println("executing third thread  : " + Thread.currentThread().getName());
            sleepThread(500);
        }));
        threads.add(new Thread(() -> {
            sleepThread(3000);
        }));
        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        return "with out completable future";
    }

    private static String sleepThread(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sleep time " + time;
    }
}
