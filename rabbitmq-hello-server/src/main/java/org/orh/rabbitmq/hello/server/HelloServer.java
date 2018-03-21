package org.orh.rabbitmq.hello.server;

import org.orh.rabbitmq.hello.Person;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HelloServer {

    @RabbitListener(queues = "hello-queue")
    public void receive(String message) {
        System.out.println(message);
    }

    //    @RabbitListener(queues = "hello-queue") 同一个  queue 名称，可以接收消息，但是会有偶发性的异常，没找到原因
    @RabbitListener(queues = "hello-queue-obj")
    public void receive(Person person) {
        System.out.println(person);
    }
}
