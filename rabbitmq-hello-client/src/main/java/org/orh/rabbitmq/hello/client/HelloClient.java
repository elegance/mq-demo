package org.orh.rabbitmq.hello.client;

import org.orh.rabbitmq.hello.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend("hello-queue", message);
    }

    public void send(Person person) {
        rabbitTemplate.convertAndSend("hello-queue-obj", person);
    }
}
