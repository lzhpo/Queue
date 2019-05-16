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
