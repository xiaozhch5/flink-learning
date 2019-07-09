package org.flink.learning.streaming.examples;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.flink.learning.streaming.connectors.HotItems;
import org.flink.learning.streaming.connectors.SourceFromMysql;


/**
 *@className CalculateHotItemsNum
 *@description 计算相同时间内商品的数量
 *@author zhchxiao
 *
 *@date 19-7-5
 **/
 
public class CalculateHotItemsNum {


    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream dataStream = env.addSource(new SourceFromMysql()).setParallelism(1);

        SingleOutputStreamOperator<Tuple6<Long, Long, Long, String, Long, Integer>> keyedStream = dataStream.map(new MapFunction<HotItems, Tuple6<Long, Long, Long, String, Long, Integer>>() {
            @Override
            public Tuple6<Long, Long, Long, String, Long, Integer> map(HotItems value) {
                return new Tuple6<>(value.getUserId(), value.getItemId(), value.getCategoryId(), value.getBehavior(), value.getTimestamps(), 1);
            }
        }).keyBy(4).reduce(new ReduceFunction<Tuple6<Long, Long, Long, String, Long, Integer>>() {
            @Override
            public Tuple6<Long, Long, Long, String, Long, Integer> reduce(Tuple6<Long, Long, Long, String, Long, Integer> value1, Tuple6<Long, Long, Long, String, Long, Integer> value2) {
                return new Tuple6<>(0L, 0L, 0L, null, value1.f4, value1.f5 + value2.f5);
            }
        });
        keyedStream.setParallelism(1).print();


        env.execute("read data from mysql");


    }

}
