package com.lzhpo.kafka.consumer.highAPI;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * 先创建生产者：kafka-console-producer.sh --broker-list localhost:9092 --topic first
 *
 * 创建消费者（过时API）
 * </p>
 */
public class CustomConsumer {

    public static void main(String[] args) {

        Properties properties = new Properties();

        properties.put("zookeeper.connect", "192.168.200.111:2181");
        properties.put("group.id", "g1");
        properties.put("zookeeper.session.timeout.ms", "500");
        properties.put("zookeeper.sync.time.ms", "250");
        properties.put("auto.commit.interval.ms", "1000");

        // 创建消费者连接器
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));

        HashMap<String, Integer> topicCount = new HashMap<>();
        topicCount.put("first", 1);

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCount);

        KafkaStream<byte[], byte[]> stream = consumerMap.get("first").get(0);

        ConsumerIterator<byte[], byte[]> it = stream.iterator();

        while (it.hasNext()) {
            System.out.println(new String(it.next().message()));
        }
    }
}
