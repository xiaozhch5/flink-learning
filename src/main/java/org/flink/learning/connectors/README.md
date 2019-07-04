# flink-connectors

## flink-connector-kafka
* add the kafka connector maven
```
<!-- https://mvnrepository.com/artifact/org.apache.flink/flink-connector-kafka -->
<dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-connector-kafka_2.12</artifactId>
    <version>1.8.0</version>
</dependency>
```
* The example of reading data from kafka is related to <font color=#FFE4C4> ReadFromKafka.java</font>
## flink-connector-hive
* Customize the dataSource
* Add the hive-jdbc maven
* TaxiRideSourceFromHive.java implements the SourceFinction and the TaxiFare.java is the predefined class.
```
<!-- https://mvnrepository.com/artifact/org.apache.hive/hive-jdbc -->
<dependency>
    <groupId>org.apache.hive</groupId>
    <artifactId>hive-jdbc</artifactId>
    <version>3.1.1</version>
</dependency>
```
The example of reading data from kafka is related to ReadFromHive.java
## flink-connector-mysql