package com.lzhpo.kafka.createProducer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * 创建生产者带回调函数（新API）
 * </p>
 */
public class CallBackProducer {

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
        // 增加服务端请求延时
        properties.put("linger.ms", 1);
        // 发送缓存区内存大小
        properties.put("buffer.memory", 33554432);
        // key序列化
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<String, String>("first", "Hello" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (recordMetadata != null){
                        System.out.println(recordMetadata.partition() + "---" +recordMetadata.offset());
                    }
                }
            });
        }

        producer.close();
    }
}
