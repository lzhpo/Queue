# RabbitMQ笔记

## 队列相关消息

### 1.Provider

消息生产者，就是投递消息的程序。

### 2.Consumer

消息消费者，就是接受消息的程序。

### 3.没有使用消息队列时消息传递方式

![](http://cdn.lzhpo.com/RabbitMQ-noqueue.png)

### 4.使用消息队列后消息传递方式

![](http://cdn.lzhpo.com/RabbitMQ-havequeue.png)

### 5.什么是队列？

队列就像存放了商品的仓库或者商店，是生产商品的工厂和购买商品的用户之间的中转站。

### 6.队列里存储了什么？

在 rabbitMQ 中，信息流从你的应用程序出发，来到 Rabbitmq 的队列，所有信息可以只存储在一个队列中。队列可以存储很多信息，因为它基本上是一个无限制的缓冲区，前提是你的机器有足够的存储空间。

### 7.队列和应用程序的关系？

多个生产者可以将消息发送到同一个队列中，多个消息者也可以只从同一个队列接收数据。



## RabbitMQ相关概念

### 1.Message

消息。消息是不具名的，它由消息头消息体组成。消息体是不透明的，而消息头则由
一系列可选属性组成，这些属性包括：routing-key(路由键)、priority(相对于其他消息的优先
权)、delivery-mode(指出消息可能持久性存储)等。

### 2.Publisher

消息的生产者。也是一个向交换器发布消息的客户端应用程序。

### 3.Consumer

消息的消费者。表示一个从消息队列中取得消息的客户端应用程序。

### 4.Exchange

交换器。用来接收生产者发送的消息并将这些消息路由给服务器中的队列。
三种常用的交换器类型
1. direct(发布与订阅 完全匹配)
2. fanout(广播)
3. topic(主题，规则匹配)

### 5.Binding

绑定。用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息
队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。

### 6.Queue

消息队列。用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一
个消息可投入一个或多个队列。消息一直在队列里面，等待消费者链接到这个队列将其取
走。

### 7.Routing-key

路由键。RabbitMQ 决定消息该投递到哪个队列的规则。
队列通过路由键绑定到交换器。
消息发送到 MQ 服务器时，消息将拥有一个路由键，即便是空的，RabbitMQ 也会将其
和绑定使用的路由键进行匹配。
如果相匹配，消息将会投递到该队列。
如果不匹配，消息将会进入黑洞。

### 8.Connection

链接。指 rabbit 服务器和服务建立的 TCP 链接。

### 9.Channel

信道。
1，Channel 中文叫做信道，是 TCP 里面的虚拟链接。例如：电缆相当于 TCP，信道是
一个独立光纤束，一条 TCP 连接上创建多条信道是没有问题的。
2，TCP 一旦打开，就会创建 AMQP 信道。
3，无论是发布消息、接收消息、订阅队列，这些动作都是通过信道完成的。

### 10.Virtual Host

虚拟主机。表示一批交换器，消息队列和相关对象。虚拟主机是共享相同的身份认证
和加密环境的独立服务器域。每个 vhost 本质上就是一个 mini 版的 RabbitMQ 服务器，拥有
自己的队列、交换器、绑定和权限机制。vhost 是 AMQP 概念的基础，必须在链接时指定，
RabbitMQ 默认的 vhost 是/

### 11.Borker

表示消息队列服务器实体。

### 12.交换器和队列的关系

交换器是通过路由键和队列绑定在一起的，如果消息拥有的路由键跟队列和交换器的
路由键匹配，那么消息就会被路由到该绑定的队列中。
也就是说，消息到队列的过程中，消息首先会经过交换器，接下来交换器在通过路由
键匹配分发消息到具体的队列中。
路由键可以理解为匹配的规则。

### 13.RabbitMQ 为什么需要信道？为什么不是 TCP 直接通信？

1. TCP 的创建和销毁开销特别大。创建需要 3 次握手，销毁需要 4 次分手。
2. 如果不用信道，那应用程序就会以 TCP 链接 Rabbit，高峰时每秒成千上万条链接
会造成资源巨大的浪费，而且操作系统每秒处理 TCP 链接数也是有限制的，必定造成性能
瓶颈。
3. 信道的原理是一条线程一条通道，多条线程多条通道同用一条 TCP 链接。一条 TCP
链接可以容纳无限的信道，即使每秒成千上万的请求也不会成为性能的瓶颈。



## 安装RabbitMQ

### Windows

-   安装Erlang：<http://erlang.org/download/otp_win64_21.3.exe>
-   下载rabbitmq：<https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.14/rabbitmq-server-3.7.14.exe>
-   按照提示进行安装，安装完成后进入rabbitmq的安装目录:D:\RabbitMQ Server\rabbitmq_server-3.7.14\sbin
-   在地址栏输入cmd并回车启动命令行输入以下命令：rabbitmq-plugins enable rabbitmq_management
-   访问地址查看是否安装成功：<http://127.0.0.1:15672/>
-   输入账号密码登录：guest guest

### Linux

-   安装erlang：`yum install erlang`，如报错No package erlang available，需要安装EPEL库。

-   安装wget：`yum -y install wget`

-   安装EPEL库：
    -   ```shell
        wget http://dl.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm
        
        rpm -ivh epel-release-6-8.noarch.rpm
        ```

-   安装RabbitMQ rpm包：

    -   ```shell
        wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.5.0/rabbitmq-server-3.5.0-1.noarch.rpm
        
        rpm -ivh rabbitmq-server-3.5.0-1.noarch.rpm
        ```

-   启动RabbitMQ，并验证启动情况：`rabbitmq-server --detached &ps aux |grep rabbitmq`

-   以服务的方式启动：`service rabbitmq-server start`

-   检查5672端口是否打开：

    -   ```shell
        /sbin/iptables -I INPUT -p tcp --dport 5672 -j ACCEPT
        /etc/rc.d/init.d/iptables save
        /etc/init.d/iptables restart
        /etc/init.d/iptables status
        ```

-   启用维护插件（web管理界面）:

    -   ```shell
        rabbitmq-plugins enable rabbitmq_management
        ```

-   重启RabbitMQ：

    -   ```shell
        service rabbitmq-server restart
        ```

-   访问UI界面：http://ip/15672。账号密码：guest

-   无法登陆解决：

    -   ```shell
        vim /etc/rabbitmq/rabbitmq.config
        #写入以下信息，并保存
        [{rabbit, [{loopback_users, []}]}].
        ```



### Docker

**使用docker镜像中国下载Rabbitmq镜像，选择带有management的，因为这个是有WEB界面：**

```shell
#使用docker镜像中国下载Rabbitmq镜像，选择带有management的，因为这个是有WEB界面。
[root@docker ~]# docker pull registry.docker-cn.com/library/rabbitmq:3.7-management
```

![](http://cdn.lzhpo.com/Docker-docker-hub-rabbitmq-1.png)

**选择官方的：**

![](http://cdn.lzhpo.com/Docker-docker-hub-rabbitmq-2.png)

**我选择的是这个3.7版本：**

![](http://cdn.lzhpo.com/Docker-docker-hub-rabbitmq-3.png)

```shell
#查看镜像
[root@docker ~]# docker images
REPOSITORY                                TAG                 IMAGE ID            CREATED             SIZE
registry.docker-cn.com/library/rabbitmq   3.7-management      24cb552c7c00        12 days ago         212 MB

#运行容器
[root@docker ~]# docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq 24cb552c7c00
#查看进程
[root@docker ~]# docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                                                                        NAMES
73943a64f336        24cb552c7c00        "docker-entrypoint..."   7 minutes ago       Up 7 minutes        4369/tcp, 5671/tcp, 0.0.0.0:5672->5672/tcp, 15671/tcp, 25672/tcp, 0.0.0.0:15672->15672/tcp   rabbitmq
[root@docker ~]# 

#关闭防火墙设置开机不启动
[root@docker ~]# systemctl stop firewalld
[root@docker ~]# systemctl disable firewalld
```

**此时就可以登录Rabbitmq的WEB界面了，访问地址是[ip:15672]默认用户名和密码都是guest。**

![](http://cdn.lzhpo.com/RabbitMQ-rabbitmq-login.png)

## RabbitMQ交换器

### Direct交换器
>   发布与订阅，完全匹配。

![](http://cdn.lzhpo.com/RabbitMQ-Direct%E4%BA%A4%E6%8D%A2%E5%99%A8.png)

#### 生产者
##### pom.xml

```powershell
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.lzhpo.rabbitmq</groupId>
    <artifactId>rabbitmq-direct-provider</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>rabbitmq-direct-provider</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```
##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

#设置交换器的名称
mq.config.exchange=log.direct
#info 路由键
mq.config.queue.info.routing.key=log.info.routing.key
#error 路由键
mq.config.queue.error.routing.key=log.error.routing.key
#error 队列名称
mq.config.queue.error=log.error
```
##### Sender

```java
package com.lzhpo.rabbitmq.rabbitmqdirectprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class Sender {

	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	//routingkey 路由键
	@Value("${mq.config.queue.error.routing.key}")
	private String routingkey;
	/*
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange, this.routingkey, msg);
	}
}

```
##### RabbitmqDirectProviderApplicationTests测试类

```java
package com.lzhpo.rabbitmq.rabbitmqdirectprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqDirectProviderApplicationTests {

    @Autowired
    private Sender sender;

    /**
     * 测试消息队列
     * @throws Exception
     */
    @Test
    public void contextLoads() throws Exception {
        while(true){
            Thread.sleep(1000);
            this.sender.send("Hello RabbitMQ");
        }
    }

}
```

#### 消费者
##### pom.xml和生产者的一样。

##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

#设置交换器的名称
mq.config.exchange=log.direct
#info 队列名称
mq.config.queue.info=log.info
#info 路由键
mq.config.queue.info.routing.key=log.info.routing.key
#error 队列名称
mq.config.queue.error=log.error
#error 路由键
mq.config.queue.error.routing.key=log.error.routing.key
```
##### ErrorReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqdirectconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.error}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.DIRECT),
					key="${mq.config.queue.error.routing.key}"
			)
		)
public class ErrorReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Error..........receiver: "+msg);
	}
}
```
##### InfoReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqdirectconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.info}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.DIRECT),
					key="${mq.config.queue.info.routing.key}"
			)
		)
public class InfoReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Info........receiver: "+msg);
	}
}
```
##### Main

```java
package com.lzhpo.rabbitmq.rabbitmqdirectconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqDirectConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDirectConsumerApplication.class, args);
    }

}
```

#### 测试结果

先启动消费者，再运行生产者的测试类`RabbitmqDirectProviderApplicationTests`测试类。

![](http://cdn.lzhpo.com/RabbitMQ-Direct%E4%BA%A4%E6%8D%A2%E5%99%A8%E6%B5%8B%E8%AF%95.png)



### Topic交换器

>   主题，规则匹配。

![](http://cdn.lzhpo.com/RabbitMQ-Topic%E4%BA%A4%E6%8D%A2%E5%99%A8.png)

#### 生产者

##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
#设置交换器的名称
mq.config.exchange=log.topic
```

##### OrderSender

```java
package com.lzhpo.rabbitmq.rabbitmqtopicprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class OrderSender {

	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	/*
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.debug", "order.log.debug....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.info", "order.log.info....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.warn","order.log.warn....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.error", "order.log.error....."+msg);
	}
}
```

##### ProductSender

```java
package com.lzhpo.rabbitmq.rabbitmqtopicprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class ProductSender {

	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	/*
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.debug", "product.log.debug....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.info", "product.log.info....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.warn","product.log.warn....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"product.log.error", "product.log.error....."+msg);
	}
}
```

##### UserSender

```java
package com.lzhpo.rabbitmq.rabbitmqtopicprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class UserSender {

	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	/*
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"user.log.debug", "user.log.debug....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"user.log.info", "user.log.info....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"user.log.warn","user.log.warn....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"user.log.error", "user.log.error....."+msg);
	}
}
```

##### RabbitmqTopicProviderApplicationTests测试类

```java
package com.lzhpo.rabbitmq.rabbitmqtopicprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTopicProviderApplicationTests {

    @Autowired
    private UserSender usersender;

    @Autowired
    private ProductSender productsender;

    @Autowired
    private OrderSender ordersender;

    /**
     * 测试消息队列
     */
    @Test
    public void contextLoads() {
        this.usersender.send("UserSender.....");
        this.productsender.send("ProductSender....");
        this.ordersender.send("OrderSender......");
    }

}
```

#### 消费者

##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
#设置交换器的名称
mq.config.exchange=log.topic
#info 队列名称
mq.config.queue.info=log.info
#error 队列名称
mq.config.queue.error=log.error
#log 队列名称
mq.config.queue.logs=log.all
```

##### ErrorReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqtopicconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.error}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.TOPIC),
					key="*.log.error"
			)
		)
public class ErrorReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("......Error........receiver: "+msg);
	}
}
```

##### InfoReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqtopicconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.info}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.TOPIC),
					key="*.log.info"
			)
		)
public class InfoReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("......Info........receiver: "+msg);
	}
}
```

##### LogsReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqtopicconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.logs}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.TOPIC),
					key="*.log.*"
			)
		)
public class LogsReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("......All........receiver: "+msg);
	}
}
```

##### Main

```java
package com.lzhpo.rabbitmq.rabbitmqtopicconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqTopicConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqTopicConsumerApplication.class, args);
    }

}
```

#### 测试结果

先启动消费者，然后再运行`RabbitmqTopicProviderApplicationTests`测试类。

![](http://cdn.lzhpo.com/RabbitMQ-Topic%E4%BA%A4%E6%8D%A2%E5%99%A8%E6%B5%8B%E8%AF%95.png)



### Fanout交换器

>   广播。

![](http://cdn.lzhpo.com/RabbitMQ-Fanout%E4%BA%A4%E6%8D%A2%E5%99%A81.png)

![](http://cdn.lzhpo.com/RabbitMQ-Fanout%E4%BA%A4%E6%8D%A2%E5%99%A82.png)

#### 生产者

##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
#设置交换器的名称
mq.config.exchange=order.fanout
```

##### Sender

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class Sender {
	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	/*
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"",
				msg);
	}
}
```

##### RabbitmqFanoutProviderApplicationTests测试类

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqFanoutProviderApplicationTests {

    @Autowired
    private Sender sender;

    /**
     * 测试消息队列
     * @throws Exception
     */
    @Test
    public void contextLoads() throws Exception {
        while(true){
            Thread.sleep(1000);
            this.sender.send("Hello RabbitMQ");
        }
    }

}
```

#### 消费者

##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
#设置交换器的名称
mq.config.exchange=order.fanout
#短信服务队列名称
mq.config.queue.sms=order.sms
#push 服务队列名称
mq.config.queue.push=order.push
```

##### SmsReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 *                key:路由键
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.sms}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.FANOUT)
			)
		)
public class SmsReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Sms........receiver: "+msg);
	}
}
```

##### PushReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.push}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.FANOUT)
			)
		)
public class PushReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Error..........receiver: "+msg);
	}
}
```

##### Main

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqFanoutConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqFanoutConsumerApplication.class, args);
    }

}
```

#### 测试结果

先启动消费者，然后再启动生产者`RabbitmqFanoutProviderApplicationTests`测试类。

![](http://cdn.lzhpo.com/RabbitMQ-Fanout%E4%BA%A4%E6%8D%A2%E5%99%A8%E6%B5%8B%E8%AF%95.png)



## 使用 RabbitMQ 实现松耦合设计

![](http://cdn.lzhpo.com/RabbitMQ-Fanout-OuHe.png)

### 生产者

##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

mq.config.exchange=order.fanout
```

##### Sender

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutouheprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitAmqpTemplate;

    //exchange 交换器名称
    @Value("${mq.config.exchange}")
    private String exchange;

    /*
     * 发送消息的方法
     */
    public void send(String msg){
        //向消息队列发送消息
        //参数一：交换器名称。
        //参数二：路由键
        //参数三：消息
        this.rabbitAmqpTemplate.convertAndSend(this.exchange,"", msg);
    }
}
```

##### RabbitmqFanoutOuheProviderApplicationTests测试类

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutouheprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqFanoutOuheProviderApplicationTests {

    @Autowired
    private Sender sender;

    /**
     * 测试消息队列
     */
    @Test
    public void contextLoads() {
        this.sender.send("Hello RabbitMQ");
    }

}
```

### 消费者

##### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

mq.config.exchange=order.fanout

mq.config.queue.sms=order.sms

mq.config.queue.push=order.push

mq.config.queue.red=red
```



##### PushReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutouheconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.push}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.FANOUT)
			)
		)
public class PushReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Push..........receiver: "+msg);
	}
}
```

##### RedReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutouheconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 *                key:路由键
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.red}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.FANOUT)
			)
		)
public class RedReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("给用户发送10元红包........receiver: "+msg);
	}
}
```

##### SmsReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutouheconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 *                key:路由键
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.sms}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.FANOUT)
			)
		)
public class SmsReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Sms........receiver: "+msg);
	}
}
```

##### Main

```java
package com.lzhpo.rabbitmq.rabbitmqfanoutouheconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqFanoutOuheConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqFanoutOuheConsumerApplication.class, args);
    }

}
```

#### 测试结果

先运行消费者，然后再运行生产者的`RabbitmqFanoutOuheProviderApplicationTests`测试类。

![](http://cdn.lzhpo.com/RabbitMQ-Fanout-OuHe%E6%B5%8B%E8%AF%95.png)



## RabbitMQ消息处理

>   消息的可靠性是 RabbitMQ 的一大特色，那么 RabbitMQ 是如何保证消息可靠性的呢——消息持久化。

### RabbitMQ的消息持久化处理

#### 生产者

###### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

mq.config.exchange=log.direct

mq.config.queue.info.routing.key=log.info.routing.key

mq.config.queue.error.routing.key=log.error.routing.key

mq.config.queue.error=log.error
```

###### Sender

```java
package com.lzhpo.rabbitmq.rabbitmqdurabledirectprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class Sender {

	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	//routingkey 路由键
	@Value("${mq.config.queue.error.routing.key}")
	private String routingkey;
	/*
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange, this.routingkey, msg);
	}
}
```

###### RabbitmqDurableDirectProviderApplicationTests测试类

```java
package com.lzhpo.rabbitmq.rabbitmqdurabledirectprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqDurableDirectProviderApplicationTests {

    @Autowired
    private Sender sender;

    /**
     * 测试消息队列
     */
    @Test
    public void contextLoads() throws Exception {
        int flag = 0;
        while(true){
            flag++;
            Thread.sleep(2000);
            this.sender.send("Hello RabbitMQ "+flag);
        }
    }

}
```

#### 消费者

###### application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

mq.config.exchange=log.direct

mq.config.queue.info=log.info

mq.config.queue.info.routing.key=log.info.routing.key

mq.config.queue.error=log.error

mq.config.queue.error.routing.key=log.error.routing.key

```

###### ErrorReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqdurabledirectconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.error}",autoDelete="false"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.DIRECT),
					key="${mq.config.queue.error.routing.key}"
			)
		)
public class ErrorReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Error..........receiver: "+msg);
	}
}
```

###### InfoReceiver

```java
package com.lzhpo.rabbitmq.rabbitmqdurabledirectconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 * @author Administrator
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列
 * 
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.info}",autoDelete="true"),
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.DIRECT),
					key="${mq.config.queue.info.routing.key}"
			)
		)
public class InfoReceiver {

	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg){
		System.out.println("Info........receiver: "+msg);
	}
}
```

###### Main

```java
package com.lzhpo.rabbitmq.rabbitmqdurabledirectconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqDurableDirectConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDurableDirectConsumerApplication.class, args);
    }

}
```

##### 测试结果

先启动消费者，再运行`RabbitmqDurableDirectProviderApplicationTests`测试类。

![](http://cdn.lzhpo.com/RabbitMQ-%E6%B6%88%E6%81%AF%E6%8C%81%E4%B9%85%E5%8C%96.png)

### RabbitMQ中的消息确认ACK机制

>   什么是消息确认ACK？

如果在处理消息的过程中，消费者的服务器在处理消息时出现异常，那可能这条正常在处理的消息就没有完成消息消费，数据就会丢失。为了确保数据不会丢失，RabbitMQ支持消息确认ACK。

>   ACK的消息确认机制？

ACK机制是消费者从RabbitMQ手到消息并处理完成后，反馈给RabbitMQ，RabbitMQ收到反馈后才将此消息从队列中删除。

1.  如果一个消费者在处理消息出现了网络不稳定、服务器异常等现象，那么就不会有ACK反馈，RabbitMQ会认为这个消息没有正常消费，会将消息重新放入队列中。
2.  如果在集群的环境下：RabbitMQ会立即将这个消息推送给这个在线的其它消费者。这种机制保证了再消费者服务端故障的时候，不会丢失任何消息和任务。
3.  消息永远不会从RabbitMQ中删除：只有当消费者正确发送ACK反馈，RabbitMQ确认收到后，消息才会从RabbitMQ服务器的数据中删除。
4.  消息的ACK确认机制默认是打开的。

>   ACK机制的开发注意事项？

如果忘记了ACK，那么后果很严重。当Consumer退出时，Message会一直重新分发。然后RabbitMQ会占用越来越多的内存，由于RabbitMQ会长时间运行，因此这个“内存泄漏”是致命的。

>   修改 Consusmer 配置文件解决 ACK 反馈问题。

在`application.properties`配置文件中添加以下

```properties
############Rabbitmq的消息确认ACK机制############
#开启重试
spring.rabbitmq.listener.retry.enabled=true
#重试次数，默认为 3 次
spring.rabbitmq.listener.retry.max-attempts=5
```

## RabbitMQ六种消息模式

pom依赖:

```powershell
<dependency>
    <groupId>com.rabbitmq</groupId>
    <artifactId>amqp-client</artifactId>
    <version>4.0.2</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.10</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.5</version>
</dependency>

<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.11</version>
</dependency>
```

RabbitMQ的连接工具（我单独写出来了一个工具类，方便使用）：

```java
package com.lzhpo.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：获取Rabbitmq的连接工具</p>
 * <p> Description：</p>
 */
public class ConnectionUtils {

    /**
     * 获取Rabbitmq的连接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("127.0.0.1");

        //AMQP 5672
        factory.setPort(5672);

        //vhost
        factory.setVirtualHost("/");

        //用户名
        factory.setUsername("guest");

        //密码
        factory.setPassword("guest");

        return factory.newConnection();
    }
}
```

### 简单队列

>   简单队列：一个生产者P发送消息到队列Q,一个消费者C接收。

![](http://cdn.lzhpo.com/RabbitMQ-simple-queue.png)

#### 生产者(Send)

```java
package com.lzhpo.rabbitmq.model5.simplequeues;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：生产者生产消息</p>
 * <p> Description：</p>
 */
public class Send {

    private static final  String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //需要发送的消息
        String msg = "hello simple!";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("---send msg：" +msg);

        //关闭
        channel.close();
        connection.close();
    }
}
```

#### 消费者(Recv)

```java
package com.lzhpo.rabbitmq.model5.simplequeues;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：消费者获取消息</p>
 * <p> Description：</p>
 */
public class Recv {

    private static final  String QUEUE_NAME = "test_simple_queue";

    /**
     * main()入口
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        //oldApi();//老版本api
        newApi();//新版本api
    }

    /**
     * 新版本api
     */
    private static void newApi() throws Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("new api recv：" + msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    /**
     * 老版本api
     * @throws Exception
     */
    private static void oldApi() throws Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            String msgString = new String(delivery.getBody());
            System.out.println("[recv] msg: " +msgString);
        }
    }

}
```

### 工作队列

#### 轮询分发

>   【轮询分发】：结果就是不管谁忙或清闲，都不会给谁多一个任务或少一个任务，任务总是你一个我一个的分。

![](http://cdn.lzhpo.com/RabbitMQ-work-queues.png)

##### 生产者(Send)

```java
package com.lzhpo.rabbitmq.model5.workqueues.lunxun;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：【轮询分发】</p>
 * <p> Description：
 * 备注:消费者 1 我们处理时间是 1s ;而消费者 2 中处理时间是 2s;
 * 但是我们看到的现象并不是 1 处理的多 消费者 2 处理的少。
 * -----------------------------------
 * 消费者1【Recv1】：
 *  [1] Received '.0'
 *  [x] Done
 *  [1] Received '.2'
 *  [x] Done
 *  ......
 *  [1] Received '.46'
 *  [x] Done
 *  [1] Received '.48'
 *  [x] Done
 *  消费者 1 将偶数部分处理掉了
 * -----------------------------------
 * 消费者2【Recv2】：
 *  [2] Received '.1'
 *  [x] Done
 *  [2] Received '.3'
 *  [x] Done
 *  ......
 *  [2] Received '.47'
 *  [x] Done
 *  [2] Received '.49'
 *  [x] Done
 * 消费者 2 中将奇数部分处理掉了。
 * -----------------------------------
 * 我想要的是 1 处理的多,而 2 处理的少
 * 测试结果:
 * 1.消费者 1 和消费者 2 获取到的消息内容是不同的,同一个消息只能被一个消费者获取
 * 2.消费者 1 和消费者 2 货到的消息数量是一样的 一个奇数一个偶数
 * 按道理消费者 1 获取的比消费者 2 要多
 * -----------------------------------
 * 这种方式叫做【轮询分发】：结果就是不管谁忙或清闲，都不会给谁多一个任务或少一个任务，任务总是你一个我一个的分。
 * -----------------------------------
 * </p>
 */
public class Send {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 50; i++) {
            //消息内容
            String message = "." +i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" +message +"'");
            Thread.sleep(i*10);
        }

        channel.close();
        connection.close();
    }
}
```

##### 消费者1(Recv1)

```java
package com.lzhpo.rabbitmq.model5.workqueues.lunxun;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv1 {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义一个消息的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(" [1] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
            }
        };
        boolean autoAck = true; //消息的确认模式自动应答
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
    private static void doWork(String task) throws InterruptedException {
        Thread.sleep(1000);
    }
    @SuppressWarnings("unused")
    public static void oldAPi() throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成状态false 自动true 自动应答 不需要手动确认
        channel.basicConsume(QUEUE_NAME, true, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
```

##### 消费者2(Recv2)

```java
package com.lzhpo.rabbitmq.model5.workqueues.lunxun;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv2 {
    private final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义一个消息的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(" [2] Received '" + message + "'");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
            }
        };
        boolean autoAck = true; //
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
```

#### 公平分发

>   使用公平分发，必须关闭自动应答，改为手动应答。

##### 生产者(Send)

```java
package com.lzhpo.rabbitmq.model5.workqueues.gongping;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：【公平分发】</p>
 * <p> Description：
 * 虽然上面的分配法方式也还行，但是有个问题就是：比如：现在有 2 个消费者，所有的偶数的消息都是繁忙的，而
 * 奇数则是轻松的。按照轮询的方式，偶数的任务交给了第一个消费者，所以一直在忙个不停。奇数的任务交给另一
 * 个消费者，则立即完成任务，然后闲得不行。
 *
 * 而 RabbitMQ 则是不了解这些的。他是不知道你消费者的消费能力的,这是因为当消息进入队列，RabbitMQ 就会分派
 * 消息。而 rabbitmq 只是盲目的将消息轮询的发给消费者。你一个我一个的这样发送.
 *
 * 为了解决这个问题，我们使用 basicQos( prefetchCount = 1)方法，来限制 RabbitMQ 只发不超过 1 条的消息给同
 * 一个消费者。当消息处理完毕后，有了反馈 ack，才会进行第二次发送。(也就是说需要手动反馈给 Rabbitmq )
 *
 * 还有一点需要注意，使用【公平分发】，必须关闭自动应答，改为手动应答。
 *
 *
 * 这时候现象就是消费者 1 速度大于消费者 2
 * </p>
 */
public class Send {
    private final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        // 创建一个频道
        Channel channel = connection.createChannel();
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        int prefetchCount = 1;

        //每个消费者发送确认信号之前，消息队列不发送下一个消息过来，一次只处理一个消息
        //限制发给同一个消费者不得超过1条消息
        channel.basicQos(prefetchCount);
        // 发送的消息
        for (int i = 0; i < 50; i++) {
            String message = "." + i;
            // 往队列中发出一条消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            Thread.sleep(i * 10);
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
```

##### 消费者1(Recv1)

```java
package com.lzhpo.rabbitmq.model5.workqueues.gongping;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv1 {
    private final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);//保证一次只分发一个
        //定义一个消息的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(" [1] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        /**手动应答**/
        boolean autoAck = false; //手动确认消息
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
    private static void doWork(String task) throws InterruptedException {
        Thread.sleep(1000);
    }
}

```

##### 消费者2(Recv2)

```java
package com.lzhpo.rabbitmq.model5.workqueues.gongping;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * Message acknowledgment（消息应答）：
 *
 * boolean autoAck = false;
 * channel.basicConsume(QUEUE_NAME, autoAck, consumer);
 *
 * boolean autoAck = true;(自动确认模式)一旦 RabbitMQ 将消息分发给了消费者，就会从内存中删除。
 * 在这种情况下，如果杀死正在执行任务的消费者，会丢失正在处理的消息，也会丢失已经分发给这个消
 * 费者但尚未处理的消息。
 *
 * boolean autoAck = false; (手动确认模式) 我们不想丢失任何任务，如果有一个消费者挂掉了，那么
 * 我们应该将分发给它的任务交付给另一个消费者去处理。 为了确保消息不会丢失，RabbitMQ 支持消
 * 息应答。消费者发送一个消息应答，告诉 RabbitMQ 这个消息已经接收并且处理完毕了。RabbitMQ 可
 * 以删除它了。
 *
 * 消息应答是默认打开的。也就是 boolean autoAck =false;
 *
 * </p>
 */
public class Recv2 {
    private final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);//保证一次只分发一个
        //定义一个消息的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(" [2] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);/**关闭自动确认应答，手动应答**/
                }
            }
        };
        /**关闭自动应答**/
        boolean autoAck = false; //关闭自动确认
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
    private static void doWork(String task) throws InterruptedException {
        Thread.sleep(2000);
    }
    public static void oldAPi() throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成状态
        channel.basicConsume(QUEUE_NAME, false, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            // 休眠1秒
            Thread.sleep(1000);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);/**关闭自动确认应答，手动应答**/
        }
    }
}

```

### 消息订阅模式

>   【订阅模式】：一个消息被多个消费者消费。
>
>   1.  一个生产者，多个消费者。
>   2.  每一个消费者都有自己的队列。
>   3.  生产者没有直接把消息发送到队列，而是发送到了交换机、转发器exchange。
>   4.  每个队列都要绑定到交换机上。
>   5.  生产者发送的消息经过交换机到达队列，就能实现一个消息被多个消费者消费。

![](http://cdn.lzhpo.com/RabbitMQ-subscribe-model.png)

![](http://cdn.lzhpo.com/RabbitMQ-subscribe-model.png)

#### 生产者(Send)

```java
package com.lzhpo.rabbitmq.model5.subscribeModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * 先运行Send创建交换器
 *
 * 但是这个发送的消息到哪了呢? 消息丢失了!!!因为交换机没有存储消息的能力,在 rabbitmq 中只有队列存储消息的
 * 能力.因为这时还没有队列,所以就会丢失;
 * 小结:消息发送到了一个没有绑定队列的交换机时,消息就会丢失!
 *
 * 【订阅模式】：一个消息被多个消费者消费。
 * 1.一个生产者，多个消费者。
 * 2.每一个消费者都有自己的队列。
 * 3.生产者没有直接把消息发送到队列，而是发送到了交换机、转发器exchange
 * 4.每个队列都要绑定到交换机上
 * 5.生产者发送的消息经过交换机到达队列，就能实现一个消息被多个消费者消费。
 *
 * 邮件->注册->短信
 *
 * </p>
 */
public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange 交换机 转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //订阅模式

        // 消息内容
        String msg = "Hello PB";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("Send: " +msg);

        channel.close();
        connection.close();
    }
}

```

#### 消费者1(Recv1)

```java
package com.lzhpo.rabbitmq.model5.subscribeModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv1 {

    private final static String QUEUE_NAME = "test_queue_fanout_email";
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /** 绑定队列到交换机 **/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        //------------下面逻辑和work模式一样-----
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}

```

#### 消费者2(Recv2)

```java
package com.lzhpo.rabbitmq.model5.subscribeModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv2 {

    private final static String QUEUE_NAME = "test_queue_fanout_sms";
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /** 绑定队列到交换机 **/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        // 同一时刻服务器只会发一条消息给消费者
        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}

```

### 路由模式

>   1.  发送消息到交换机并且要指定路由key 。
>   2.  消费者将队列绑定到交换机时需要指定路由key。

![](http://cdn.lzhpo.com/RabbitMQ-routing-model.png)

#### 生产者(Send)

```java
package com.lzhpo.rabbitmq.model5.routingModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * 先运行Send创建交换器
 * </p>
 */
public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 消息内容
        String msg = "hello direct!";
        //routingKey
        //String routingKey = "error";//error两个都可以收到
        //String routingKey = "info";//info只有Recv2能收到
        String routingKey = "warning";//warning只有Recv2能收到
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

        System.out.println("-------------send: " +msg);

        channel.close();
        connection.close();
    }
}

```

#### 消费者1(Recv1)

```java
package com.lzhpo.rabbitmq.model5.routingModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv1 {

    private final static String QUEUE_NAME = "test_queue_direct_1";
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /** 绑定队列到交换机 **/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}

```

#### 消费者2(Recv2)

```java
package com.lzhpo.rabbitmq.model5.routingModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv2 {

    private final static String QUEUE_NAME = "test_queue_direct_2";
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /** 绑定队列到交换机 **/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}

```

### 主题模式

>   Topic主题模式：将路由键和某模式进行匹配，此时队列需要绑定在一个模式上，“#”匹配一个词或多个词，“*”只匹配一个词。

![](http://cdn.lzhpo.com/RabbitMQ-topic-model.png)

#### 生产者(Send)

```java
package com.lzhpo.rabbitmq.model5.topicModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * Topic主题模式：将路由键和某种模式匹配。
 * </p>
 */
public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 消息内容
        String message = "id=1001";
        channel.basicPublish(EXCHANGE_NAME, "item.delete", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}

```

#### 消费者1(Recv1)

```java
package com.lzhpo.rabbitmq.model5.topicModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv1 {

    private final static String QUEUE_NAME = "test_queue_topic_1";
    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.update");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.delete");
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}

```

#### 消费者2(Recv2)

```java
package com.lzhpo.rabbitmq.model5.topicModel;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
public class Recv2 {

    private final static String QUEUE_NAME = "test_queue_topic_2";
    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /** 绑定队列到交换机 **/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.#");//全匹配：item.#
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}

```

### RPC远程调用模式

>   前面学习了如何使用work队列在多个worker之间分配任务，但是如果需要在远程机器上运行个函数并等待结果，就需要使用RPC（远程过程调用）模式来实现。

![](http://cdn.lzhpo.com/RabbitMQ-rpc.png)

参考官网教程【模拟RPC服务来返回斐波那契数列】：[https://www.rabbitmq.com/tutorials/tutorial-six-java.html](https://www.rabbitmq.com/tutorials/tutorial-six-java.html)



