package org.flink.learning.streaming.workingwithstate;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;


public class MyFlatMapFunction extends RichFlatMapFunction<Tuple1<String>, Tuple2<String, Integer>>{
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
}
