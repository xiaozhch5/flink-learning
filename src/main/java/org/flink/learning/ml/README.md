# Flink ML
## Prepare the execution environment
* Add the maven repository
```
<!-- https://mvnrepository.com/artifact/org.apache.flink/flink-ml -->
<dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-ml_2.11</artifactId>
    <version>1.8.0</version>
</dependency>
```
## Prepare the data
* FlinkML define two methods to build Vector data. One is to read LibSVM data, the other is to read datafile and transform it into DataSet\<String\>.
```java
// Reading LibSVM data
DataSet<LabeledVector> trainData = MLUtils.readLibSVM(env, "/path/svmfile1");
DataSet<LabeledVector> testData = MLUtils.readLibSVM(env, "/path/svmfile2");

// Write the data into LibSVM
DataSet<LabeledVector> svmData = ...
DataSink<String> dataSink = MLUtils.writeLibSVM("/path/svmfile2", svmData);
```
```java
DataSet trainCsvData = env.readCsvFile<>("/path/svm_train.data")
DataSet<LabeledVector> trainData = trainCsvData.map(new MapFunction<>(){...});
```