package org.flink.learning.transformation;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *@className ReduceTransformation
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-6
 **/
 
public class ReduceTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream dataStream = env.fromElements(new Tuple2<>("a", 3), new Tuple2<>("d", 1), new Tuple2<>("c", 1), new Tuple2<>("a", 2), new Tuple2<>("c", 3));
        KeyedStream<Tuple2<String, Integer>, Tuple> keyedStream = dataStream.keyBy(0);
        DataStream dataStream1 = keyedStream.reduce(new ReduceFunction<Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
                return new Tuple2<>(value1.f0, value1.f1 + value2.f1);
            }
        });
        dataStream1.print();

        env.execute();
    }
}
