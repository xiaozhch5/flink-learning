package org.flink.learning.streaming.transformation;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *@className AggregationsTransformation
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-6
 **/
 
public class AggregationsTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<Integer, Integer>> dataStream = env.fromElements(new Tuple2<>(1, 8), new Tuple2<>(2, 1), new Tuple2<>(2, 4), new Tuple2<>(1, 3));
        KeyedStream<Tuple2<Integer, Integer>, Tuple> keyedStream = dataStream.keyBy(0);
//        keyedStream.min(1).print().setParallelism(1);
//        keyedStream.minBy(1).print().setParallelism(1);
//        keyedStream.max(1).print().setParallelism(1);
//        keyedStream.maxBy(1).print().setParallelism(1);
        keyedStream.sum(1).print().setParallelism(1);

        env.execute();

    }
}
