package org.flink.learning.streaming.connectors;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
/**
 *@className ReadFromHive
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-4
 **/
 
public class ReadFromHive {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<TaxiFare> dataStream = env.addSource(new TaxiRideSourceFromHive());

        dataStream.print();
        env.execute("flink add data");
    }
}
