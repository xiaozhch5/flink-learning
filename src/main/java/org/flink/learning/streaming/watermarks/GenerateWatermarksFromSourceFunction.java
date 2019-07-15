package org.flink.learning.streaming.watermarks;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *@className GenerateWatermarksFromSourceFunction
 *@description define the watermarks and timestamps in the SourceFunction
 *@author zhchxiao
 *
 *@date 19-7-7
 **/
 
public class GenerateWatermarksFromSourceFunction {
    static Logger log = Logger.getLogger(GenerateWatermarksFromSourceFunction.class);
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 在数据源中定义watermarks
        List<Tuple3<String, Long, Integer>> input = new ArrayList<>();
        input.add(new Tuple3<>("a", 1L, 1));
        input.add(new Tuple3<>("b", 1L, 1));
        input.add(new Tuple3<>("b", 3L, 1));

        DataStreamSource<Tuple3<String, Long, Integer>> dataStream = env.addSource(new SourceFunction<Tuple3<String, Long, Integer>>() {
            @Override
            public void run(SourceContext<Tuple3<String, Long, Integer>> sourceContext) throws Exception {
                input.forEach(value -> {
                    sourceContext.collectWithTimestamp(value, value.f1);
                    sourceContext.emitWatermark(new Watermark(value.f1 - 1));
                });
                sourceContext.emitWatermark(new Watermark(Long.MAX_VALUE));
            }


            @Override
            public void cancel() {

            }
        });

        dataStream.print();
        log.info("This is logger info");
        env.execute();
    }
}
