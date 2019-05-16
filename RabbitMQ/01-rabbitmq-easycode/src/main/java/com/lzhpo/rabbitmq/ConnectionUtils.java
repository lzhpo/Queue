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
