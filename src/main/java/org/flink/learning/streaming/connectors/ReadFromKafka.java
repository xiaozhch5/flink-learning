package org.flink.learning.streaming.connectors;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 *@className ReadFromKafka
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-4
 **/
 
public class ReadFromKafka {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties kafkaProp = new Properties();
        kafkaProp.put("bootstrap.servers", "hadoop1:9092");
        kafkaProp.put("group.id", "test");

        DataStream<String> dataStream =  env.addSource(new FlinkKafkaConsumer<>("testtest", new SimpleStringSchema(), kafkaProp).setStartFromEarliest());
        dataStream.print();
        env.execute("read from kafka");
    }
}
