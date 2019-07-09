package org.flink.learning.streaming.watermarks;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.ArrayList;
import java.util.List;

/**
 *@className UsePeriodicTimestampAssignerToDefineWatermarks
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-7
 **/
 
public class UsePeriodicTimestampAssignerToDefineWatermarks {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        List<Tuple3<String, Long, Integer>> input = new ArrayList<>();
        input.add(new Tuple3<>("a", 1L, 1));
        input.add(new Tuple3<>("b", 2L, 1));
        input.add(new Tuple3<>("c", 3L, 1));
        input.add(new Tuple3<>("d", 7L, 1));
        input.add(new Tuple3<>("e", 8L, 1));
        input.add(new Tuple3<>("f", 4L, 1));
        input.add(new Tuple3<>("g", 5L, 1));
        input.add(new Tuple3<>("h", 6L, 1));
        input.add(new Tuple3<>("a", 9L, 1));
        input.add(new Tuple3<>("a", 10L, 1));
        input.add(new Tuple3<>("a", 11L, 1));

        DataStream<Tuple3<String, Long, Integer>> dataStream = env.fromCollection(input);

//          flink中实现Period Watermark Assigner有两种方式，下面这个方式为升序模式，具体可参考博客：https://love.lrting.top
//        DataStream<Tuple3<String, Long, Integer>> dataStream1 = dataStream.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Tuple3<String, Long, Integer>>() {
//            @Override
//            public long extractAscendingTimestamp(Tuple3<String, Long, Integer> stringLongIntegerTuple3) {
//                return stringLongIntegerTuple3.f1;
//            }
//        });
//
//          下面这种方式为设置固定的时间间隔来指定Watermark落后于Timestamp的区间长度
          DataStream<Tuple3<String, Long, Integer>> dataStream1 = dataStream.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, Long, Integer>>(Time.milliseconds(10)){
              @Override
              public long extractTimestamp(Tuple3<String, Long, Integer> stringLongIntegerTuple3){
                  return stringLongIntegerTuple3.f1;
                 }
          });


        DataStream<Tuple2<String, Integer>> dataStream2 = dataStream1.map(new MapFunction<Tuple3<String, Long, Integer>, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(Tuple3<String, Long, Integer> value) throws Exception {
                return new Tuple2<>(value.f0, value.f2);
            }
        });
        DataStream<Tuple2<String, Integer>> dataStream3 = dataStream2.keyBy(0).timeWindow(Time.milliseconds(15)).sum(1);
        dataStream3.print();
        env.execute();


    }
}
