package org.flink.learning.streaming.workingwithstate;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 *@className UseValueStateInFlapMap
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-9
 **/
 
public class UseValueStateInFlapMap {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        List<Tuple2<Long, Long>> input = new ArrayList<>();
        input.add(new Tuple2<>(1L, 3L));
        input.add(new Tuple2<>(1L, 5L));
        input.add(new Tuple2<>(1L, 7L));
        input.add(new Tuple2<>(1L, 4L));
        input.add(new Tuple2<>(1L, 2L));
        DataStream<Tuple2<Long, Long>> dataStream = env.fromCollection(input);

        dataStream.keyBy(0).flatMap(new RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>>() {
            private transient ValueState<Tuple2<Long, Long>> sum;

            @Override
            public void flatMap(Tuple2<Long, Long> input, Collector<Tuple2<Long, Long>> out) throws Exception{
                Tuple2<Long, Long> currentSum = sum.value();
                currentSum.f0 += 1;
                currentSum.f1 += input.f1;
                sum.update(currentSum);

                if(currentSum.f0 >= 2){
                    out.collect(new Tuple2<>(input.f0, currentSum.f1/currentSum.f0));
                    sum.clear();
                }
            }

            @Override
            public void open(Configuration configuration) throws Exception{
                ValueStateDescriptor<Tuple2<Long, Long>> descriptor = new ValueStateDescriptor<Tuple2<Long, Long>>("average", TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {
                    @Override
                    public TypeInformation<Tuple2<Long, Long>> getTypeInfo() {
                        return super.getTypeInfo();
                    }
                }), Tuple2.of(0L, 0L));
                sum = getRuntimeContext().getState(descriptor);

            }
        }).print();

        env.execute();

    }
}
