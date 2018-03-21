## MQ 消息队列DEMO

[TOC]

------



### ActiveMQ

#### 两类消息通道

* **Queue**: 单播，“点对点”通信问题， Producer ---> Special Queue ---> Consumer
* **Topic**: 广播消息，“发布与订阅”，Producer --> Special Topic --> Consumers

#### 环境搭建(docker 安装)

```sh
docker run -d -p 8161:8161 -p 61616:61616 -e ACTIVEMQ_ADMIN_LOGIN=admin -e ACTIVEMQ_ADMIN_PASSWORD=admin --name activemq webcenter/activemq
```

#### 管理界面

http://localhost:8161/

#### 编码

Producer:

`pom.xml` 依赖：

```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```

`DemoProducer.java`：

```java
@Autowired
private JmsTemplate jmsTemplate;

public void send(String message) {
    jmsTemplate.convertAndSend("hello-queue", message); // queueName, message
}
```

`application.yml`：

```yaml
spring:
  activemq:
    broker-url: tcp://localhost:61616
    user: admin
    password: admin
```

`DemoConsumer.java`：

```java
@JmsListener(destination = "hello-queue")
public void receive(String message) {
    System.out.println(message);
}
```



