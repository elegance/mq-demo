package org.orh.rabbitmq.hello.standard;

import org.orh.rabbitmq.hello.Person;
import org.orh.rabbitmq.hello.client.HelloClient;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"org.orh.rabbitmq.hello.client", "org.orh.rabbitmq.hello.standard"})
public class HelloClientApplication {

    @Autowired
    private HelloClient helloClient;

    @Bean
    public Queue helloQueue() {
        return new Queue("hello-queue");
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

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            helloClient.send("hello world - test " + i);
        }

        // 发送对象
        Person person = new Person();
        person.setName("Jim");
        person.setAge(18);
        helloClient.send(person);
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClientApplication.class, args).close();
    }
}
