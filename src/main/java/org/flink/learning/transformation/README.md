# DataStream transformation
* This package contains three kind of datastream transformations
    * Single-DataStream
    * Multi-DataStream
    * Physical partition
## Single-DataStream
### map [DataStream -> DataStream]
```
DataStream<Tuple2<Long, Long>> dataStream = env.fromElements(new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L), new Tuple2<>(1L, 2L));
DataStream<Tuple2<Long, Long>> dataStream1 = dataStream.map(new MapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>>() {
    @Override
    public Tuple2<Long, Long> map(Tuple2<Long, Long> value) throws Exception {
        return new Tuple2<>(value.f0, value.f1 + 1);
    }
});
```
### flatMap [DataStream -> DataStream]