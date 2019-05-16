package com.lzhpo.kafka.createProducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * 先在集群中创建一个topic：kafka-topics.sh --zookeeper localhost:2181 --create --replication-factor 1 --partitions 1 --topic first
 * 然后在集群中开一个消费者：kafka-console-consumer.sh --zookeeper localhost:2181 --from-beginning --topic first
 *
 * 创建生产者（新API）
 * </p>
 */
public class NewProducer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        // Kafka服务端的主机名和端口号
        properties.put("bootstrap.servers", "192.168.200.111:9092");
        // 等待所有副本节点的应答
        properties.put("acks", "all");
        // 消息发送最大尝试次数
        properties.put("retries", 0);
        // 一批消息处理大小
        properties.put("batch.size", 16384);
        // 请求延时
        properties.put("linger.ms", 1);
        // 发送缓存区内存大小
        properties.put("buffer.memory",33554432);
        // key序列化
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<String, String>("first", Integer.toString(i), "HelloWorld-" +i));
        }

        producer.close();
    }
}
