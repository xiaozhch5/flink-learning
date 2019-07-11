package org.flink.learning.cep;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;
import java.util.Map;

/**
 *@className IndividualCepExample
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-11
 **/
 
public class IndividualCepExample {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream dataStream = env.fromElements(Tuple2.of(1, 2), Tuple2.of(2, 3), Tuple2.of(3, 4), Tuple2.of(4, 5), Tuple2.of(5, 6), Tuple2.of(6, 7)).setParallelism(1);
        Pattern pattern = Pattern
                .<Tuple2<Integer, Integer>>begin("start")
                .where(new SimpleCondition<Tuple2<Integer, Integer>>() {
                    @Override
                    public boolean filter(Tuple2<Integer, Integer> tuple2){
                        return  tuple2.f0 > 2;
                    }
                })
                .times(1);
        PatternStream<Tuple2<Integer, Integer>> patternStream = CEP.pattern(dataStream, pattern);
        DataStream<Tuple2<Integer, Integer>> result = patternStream.select(new PatternSelectFunction<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
            @Override
            public Tuple2<Integer, Integer> select(Map<String, List<Tuple2<Integer, Integer>>> map) throws Exception {
                return map.get("start").iterator().next();
            }
        });
        result.print().setParallelism(1);
        env.execute();


    }
}
