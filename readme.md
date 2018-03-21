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



### RabbitMQ

#### 名词解释

Exchanges: 交换机

Queues: 队列

**RabbitMQ 只有Queue ，没有Topic，通过Exchange 与 Queue 的组合来实现 Tpoic 所具备的功能，Exchange 与 Queue 之间有一个 Binding 关系**

Producer ---> Special Exchange (根据Binding来路由消息)

#### 消息模式

* **单播：如果 Exchange 与 Queues 间的Binding 各部相同，消息将被路由到其中的一个Queue，随后被一个Consumer消费。**
* **广播：如果 Exchagne 与 Queues 间的Binding 完全相同，消息将被路由到每个Queue，随后被每个Consumer消费**

**补充：RabbitMQ 提供了一个默认的 Exchange(名词 AMQP default), 简单情况下使用默认即可，如果我们需要广播(发布订阅)才会使用自定义 Exchange.**