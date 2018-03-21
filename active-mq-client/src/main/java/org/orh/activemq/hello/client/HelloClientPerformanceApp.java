package org.orh.activemq.hello.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class HelloClientPerformanceApp {
    @Autowired
    private HelloClient helloClient;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @PostConstruct
    public void init() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < 100; i++) {
            final int num = i;
            // 多线程 提高发送效率
//            executorService.execute(() -> {
//                helloClient.send("hello world:" + num);
//            });
            helloClient.send("hello world:" + num);
        }
        stopWatch.stop();
        System.out.println("time: " + stopWatch.getTotalTimeMillis() + "ms");

    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClientApplication.class, args);
    }
}
