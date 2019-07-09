package org.flink.learning.streaming.transformation;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


/**
 *@className KeyByTransformation
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-6
 **/
 
public class KeyByTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<Long, Integer>> dataStream = env.fromElements(new Tuple2<>(1L, 1), new Tuple2<>(2L, 2), new Tuple2<>(3L, 1), new Tuple2<>(4L, 8));
        KeyedStream<Tuple2<Long, Integer>, Tuple> keyedStream = dataStream.keyBy(1);
        keyedStream.print();

        env.execute();

    }
}
