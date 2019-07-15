package org.flink.learning.streaming.workingwithstate;
/**
 * @className WordCount
 * @description 利用flink中的状态管理机制进行单词统计
 *
 * @author zhchxiao
 * @date 2019-7-15
 */

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
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
        });

        DataStream<Tuple2<String, Integer>> dataStream2 = dataStream1.keyBy(0).flatMap(new RichFlatMapFunction<Tuple1<String>, Tuple2<String, Integer>>() {
            private transient ValueState<Integer> state;
            @Override
            public void open(Configuration configuration) throws Exception{
                ValueStateDescriptor<Integer> valueStateDescriptor = new ValueStateDescriptor<>("wordcount", Integer.class, 0);
                state = getRuntimeContext().getState(valueStateDescriptor);
            }

            @Override
            public void flatMap(Tuple1<String> s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                int count = state.value();
                count++;
                state.update(count);
                collector.collect(Tuple2.of(s.f0, count));
            }
        });

        dataStream2.print().setParallelism(1);
        env.execute();
    }
}
