package com.lzhpo.kafka.kafkaStreams;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：【Kafka数据清洗案例】</p>
 * <p> Description：
 *
 * 实时处理字符带有”>>>”前缀的内容。例如输入”111>>>222”，最终处理成“222”
 *
 * 生产者（定义输入的topic）：
 * kafka-console-producer.sh --broker-list localhost:9092 --topic first
 *
 * 消费者（定义输出的topic）：
 * kafka-console-consumer.sh --zookeeper localhost:2181 --from-beginning --topic second
 * </p>
 */
public class APP {

    public static void main(String[] args) {

        // 定义输入的topic
        String from = "first";
        // 定义输出的topic
        String to = "second";

        // 设置参数
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "logFilter");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.200.111:9092");

        StreamsConfig config = new StreamsConfig(settings);

        // 构建拓扑
        TopologyBuilder builder = new TopologyBuilder();

        builder.addSource("SOURCE", from)
                .addProcessor("PROCESS", new ProcessorSupplier<byte[], byte[]>() {

                    @Override
                    public Processor<byte[], byte[]> get() {
                        // 具体分析处理
                        return new LogProcessor();
                    }
                }, "SOURCE")
                .addSink("SINK", to, "PROCESS");

        // 创建kafka stream
        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();
    }

}
