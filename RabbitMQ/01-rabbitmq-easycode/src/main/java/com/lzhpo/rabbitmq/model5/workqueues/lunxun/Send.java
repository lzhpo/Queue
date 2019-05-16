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
