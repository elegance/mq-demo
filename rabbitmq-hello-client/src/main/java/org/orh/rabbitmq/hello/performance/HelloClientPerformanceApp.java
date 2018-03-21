package org.orh.rabbitmq.hello.performance;

import org.orh.rabbitmq.hello.client.HelloClient;
import org.orh.rabbitmq.hello.standard.HelloClientApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = {"org.orh.rabbitmq.hello.client", "org.orh.rabbitmq.hello.performance"})
public class HelloClientPerformanceApp {
    private static final int THREADS = 10;

    ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

    @Autowired
    private HelloClient helloClient;

    @PostConstruct
    public void init() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            final CountDownLatch begin = new CountDownLatch(1);
            final CountDownLatch end = new CountDownLatch(THREADS);

            for (int i = 0; i < THREADS; i++) {
                final int num = i;
                // 多线程 提高发送效率
                executorService.execute(() -> {
                    try {
                        begin.await();
                        for (int j = 0; j < 100; j++) {
                            helloClient.send("hello world:" + j + " t:" + Thread.currentThread().getName());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                });
            }
            begin.countDown();
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        stopWatch.stop();
        System.out.println("time: " + stopWatch.getTotalTimeMillis() + "ms");
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClientPerformanceApp.class, args).close();
    }
}
