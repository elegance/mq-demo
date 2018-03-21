package org.orh.rabbitmq.hello.server;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages = "org.orh.rabbitmq.hello")
public class HelloServerApplication {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello-queue"); // 创建队列，避免报错（不会重复创建）
    }

    @Bean
    public Queue helloQueueObj() {
        return new Queue("hello-queue-obj");
    }

    // 加入此 bean 即可反序列化 传输的对象 ，如传输的 Person 对象，如果没有此 bean ，Person 必须实现 Serializable，默认使用JDK 序列化、反序列化
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloServerApplication.class, args);
    }
}
