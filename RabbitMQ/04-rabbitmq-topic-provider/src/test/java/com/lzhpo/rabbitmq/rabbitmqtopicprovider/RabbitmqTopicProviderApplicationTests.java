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
