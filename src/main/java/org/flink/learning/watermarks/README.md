# Watermarks and Timestamps
## Define the watermarks and timestamps in the SourceFunction
```java
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
```
## Use Timestamp Assigner to define the watermarks and timestamps
```java
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

// flink中实现Period Watermark Assigner有两种方式，下面这个方式为升序模式，具体可参考博客：https://love.lrting.top
DataStream<Tuple3<String, Long, Integer>> dataStream1 = dataStream.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Tuple3<String, Long, Integer>>() {
    @Override
    public long extractAscendingTimestamp(Tuple3<String, Long, Integer> stringLongIntegerTuple3) {
        return stringLongIntegerTuple3.f1;
    }
});

// 下面这种方式为设置固定的时间间隔来指定Watermark落后于Timestamp的区间长度
DataStream<Tuple3<String, Long, Integer>> dataStream1 = dataStream.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, Long, Integer>>(Time.milliseconds(10)){
    @Override
    public long extractTimestamp(Tuple3<String, Long, Integer> stringLongIntegerTuple3){
        return stringLongIntegerTuple3.f1;
    }
});
```
## Customize Timestamp Assigner and Watermark Generator

