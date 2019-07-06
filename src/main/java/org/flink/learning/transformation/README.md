# DataStream transformation
* This package contains three kind of datastream transformations
    * Single-DataStream
    * Multi-DataStream
    * Physical partition
## Single-DataStream
### map [DataStream -> DataStream]
```java
DataStream<Tuple2<Long, Long>> dataStream = env.fromElements(new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L));
DataStream<Tuple2<Long, Long>> dataStream1 = dataStream.map(new MapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>>() {
    @Override
    public Tuple2<Long, Long> map(Tuple2<Long, Long> value) throws Exception {
        return new Tuple2<>(value.f0, value.f1 + 1);
    }
})
```
### flatMap [DataStream -> DataStream]
```java
DataStream<String> dataStream = env.fromElements("hello python", "hello java", "hello scala", "hello c++");
dataStream.flatMap(new FlatMapFunction<String, String>() {
    @Override
    public void flatMap(String value, Collector<String> out){
        for(String word: value.split(" ")){
            out.collect(word);
        }
    }
})
```
### filter [DataStream -> DataStream]
```java
DataStream<Tuple2<Long, Long>> dataStream = env.fromElements(new Tuple2<>(1L, 2L), new Tuple2<>(1L, 3L), new Tuple2<>(1L, 4L), new Tuple2<>(1L, 5L));
dataStream.filter(new FilterFunction<Tuple2<Long, Long>>() {
    @Override
    public boolean filter(Tuple2<Long, Long> value) {
        return value.f1 > 3;
    }
})
```