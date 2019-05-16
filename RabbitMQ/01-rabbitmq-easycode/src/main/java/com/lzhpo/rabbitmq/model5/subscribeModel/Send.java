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
