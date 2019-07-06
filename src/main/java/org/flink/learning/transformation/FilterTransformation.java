package org.flink.learning.transformation;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *@className FilterTransformation
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-6
 **/
 
public class FilterTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<Long, Long>> dataStream = env.fromElements(new Tuple2<>(1L, 2L), new Tuple2<>(1L, 3L), new Tuple2<>(1L, 4L), new Tuple2<>(1L, 5L));
        dataStream.filter(new FilterFunction<Tuple2<Long, Long>>() {
            @Override
            public boolean filter(Tuple2<Long, Long> value) {
                return value.f1 > 3;
            }
        }).print().setParallelism(1);

        env.execute();


    }
}
