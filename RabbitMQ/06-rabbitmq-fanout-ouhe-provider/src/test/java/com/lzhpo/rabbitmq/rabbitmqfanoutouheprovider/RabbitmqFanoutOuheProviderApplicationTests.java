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
