# DataStream transformation
* This package contains three kind of datastream transformations
    * Single-DataStream
    * Multi-DataStream
    * Physical partition
## Single-DataStream
### map() [DataStream -> DataStream]
```java
DataStream<Tuple2<Long, Long>> dataStream = env.fromElements(new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L));
DataStream<Tuple2<Long, Long>> dataStream1 = dataStream.map(new MapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>>() {
    @Override
    public Tuple2<Long, Long> map(Tuple2<Long, Long> value) throws Exception {
        return new Tuple2<>(value.f0, value.f1 + 1);
    }
});
```
### flatMap() [DataStream -> DataStream]
```java
DataStream<String> dataStream = env.fromElements("hello python", "hello java", "hello scala", "hello c++");
dataStream.flatMap(new FlatMapFunction<String, String>() {
    @Override
    public void flatMap(String value, Collector<String> out){
        for(String word: value.split(" ")){
            out.collect(word);
        }
    }
});
```
### filter() [DataStream -> DataStream]
```java
DataStream<Tuple2<Long, Long>> dataStream = env.fromElements(new Tuple2<>(1L, 2L), new Tuple2<>(1L, 3L), new Tuple2<>(1L, 4L), new Tuple2<>(1L, 5L));
dataStream.filter(new FilterFunction<Tuple2<Long, Long>>() {
    @Override
    public boolean filter(Tuple2<Long, Long> value) {
        return value.f1 > 3;
    }
});
```
### keyBy() [DataStream -> KeyedStream]
```java
DataStream<Tuple2<Long, Integer>> dataStream = env.fromElements(new Tuple2<>(1L, 1), new Tuple2<>(2L, 2), new Tuple2<>(3L, 1), new Tuple2<>(4L, 8));
KeyedStream<Tuple2<Long, Integer>, Tuple> keyedStream = dataStream.keyBy(1);
```
### reduce() [KeyedStream -> DataStream]
```java
DataStream dataStream = env.fromElements(new Tuple2<>("a", 3), new Tuple2<>("d", 1), new Tuple2<>("c", 1), new Tuple2<>("a", 2), new Tuple2<>("c", 3));
KeyedStream<Tuple2<String, Integer>, Tuple> keyedStream = dataStream.keyBy(0);
DataStream dataStream1 = keyedStream.reduce(new ReduceFunction<Tuple2<String, Integer>>() {
    @Override
    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
        return new Tuple2<>(value1.f0, value1.f1 + value2.f1);
    }
});
```
### Aggregations [KeyedStream -> DataStream]
* sum()
* min()
* minBy()
* max()
* maxBy()
```java
DataStream<Tuple2<Integer, Integer>> dataStream = env.fromElements(new Tuple2<>(1, 8), new Tuple2<>(2, 1), new Tuple2<>(2, 4), new Tuple2<>(1, 3));
KeyedStream<Tuple2<Integer, Integer>, Tuple> keyedStream = dataStream.keyBy(0);
keyedStream.min(1).print().setParallelism(1);
keyedStream.minBy(1).print().setParallelism(1);
keyedStream.max(1).print().setParallelism(1);
keyedStream.maxBy(1).print().setParallelism(1);
keyedStream.sum(1).print().setParallelism(1);
```
## Multi-DataStream
### union() [DataStream -> DataStream]
```java
DataStream<Tuple2<Integer, Integer>> dataStream1 = env.fromElements(new Tuple2<>(1, 8), new Tuple2<>(2, 1), new Tuple2<>(2, 4), new Tuple2<>(1, 3));
DataStream<Tuple2<Integer, Integer>> dataStream2 = env.fromElements(new Tuple2<>(2, 8), new Tuple2<>(3, 1), new Tuple2<>(3, 4), new Tuple2<>(2, 3));
DataStream<Tuple2<Integer, Integer>> dataStream3 = env.fromElements(new Tuple2<>(3, 8), new Tuple2<>(4, 1), new Tuple2<>(4, 4), new Tuple2<>(3, 3));

DataStream dataStream4 = dataStream1.union(dataStream2, dataStream3);
```