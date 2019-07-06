package org.flink.learning.transformation;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *@className UnionTransformation
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-6
 **/
 
public class UnionTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<Integer, Integer>> dataStream1 = env.fromElements(new Tuple2<>(1, 8), new Tuple2<>(2, 1), new Tuple2<>(2, 4), new Tuple2<>(1, 3));
        DataStream<Tuple2<Integer, Integer>> dataStream2 = env.fromElements(new Tuple2<>(2, 8), new Tuple2<>(3, 1), new Tuple2<>(3, 4), new Tuple2<>(2, 3));
        DataStream<Tuple2<Integer, Integer>> dataStream3 = env.fromElements(new Tuple2<>(3, 8), new Tuple2<>(4, 1), new Tuple2<>(4, 4), new Tuple2<>(3, 3));

        DataStream dataStream4 = dataStream1.union(dataStream2, dataStream3);
        dataStream4.print();

        env.execute();

    }
}
