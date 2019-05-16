package com.lzhpo.kafka.createProducer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * 创建生产者（过时的API）</p>
 */
public class OldProducer {

    public static void main(String[] args) {

        //配置
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "192.168.200.111:9092");
        properties.put("request.required.acks", "1");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");

        Producer<Integer, String> producer = new Producer<Integer,String>(new ProducerConfig(properties));

        //消息内容
        KeyedMessage<Integer, String> message = new KeyedMessage<Integer, String>("first", "HelloWorld");
        //发送消息
        producer.send(message );
    }
}
