package org.flink.learning.transformation;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *@className MapTransformation
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-6
 **/
 
public class MapTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<Long, Long>> dataStream = env.fromElements(new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L));
        DataStream<Tuple2<Long, Long>> dataStream1 = dataStream.map(new MapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>>() {
            @Override
            public Tuple2<Long, Long> map(Tuple2<Long, Long> value) {
                return new Tuple2<>(value.f0, value.f1 + 1);
            }
        });
        dataStream1.print();

        env.execute("map transformation");
    }
}
