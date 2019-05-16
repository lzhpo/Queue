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
    /**
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
     **/

}
