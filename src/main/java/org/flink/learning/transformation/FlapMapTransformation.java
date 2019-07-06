package org.flink.learning.transformation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 *@className FlapMapTransformation
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-6
 **/
 
public class FlapMapTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.fromElements("hello python", "hello java", "hello scala", "hello c++");
        dataStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out){
                for(String word: value.split(" ")){
                    out.collect(word);
                }
            }
        }).print().setParallelism(1);
        env.execute();
    }
}
