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
