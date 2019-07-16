package org.flink.learning.streaming.workingwithstate;
/**
 * @className WordCount
 * @description 利用flink中的状态管理机制进行单词统计
 *
 * @author zhchxiao
 * @date 2019-7-15
 */

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class WordCount {
    public static void main(String[] args) throws Exception{
        String string = "i have an apple, you don't have an apple, she has an apple";
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.fromElements(string);
        DataStream<Tuple1<String>> dataStream1 = dataStream.flatMap(new FlatMapFunction<String, Tuple1<String>>() {
            @Override
            public void flatMap(String s, Collector<Tuple1<String>> collector) throws Exception {
                for(String string: s.split(" ")){
                    collector.collect(Tuple1.of(string));
                }
            }
        }).setParallelism(1);
        //
        DataStream<Tuple2<String, Integer>> dataStream2 = dataStream1.keyBy(0).flatMap(new MyFlatMapFunction()).setParallelism(1);
        dataStream2.print().setParallelism(1);
        env.execute();
    }
}
