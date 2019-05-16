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
