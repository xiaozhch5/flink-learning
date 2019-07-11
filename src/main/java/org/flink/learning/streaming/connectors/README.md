# flink-connectors

## flink-connector-kafka
* Add the kafka connector maven
```
<!-- https://mvnrepository.com/artifact/org.apache.flink/flink-connector-kafka -->
<dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-connector-kafka_2.11</artifactId>
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
* The example of reading data from Hive is related to ReadFromHive.java
## flink-connector-mysql
You Can use addSource() function or createInput() function of the StreamExecutionEnvironment.

Two of the examples are listed below

### createInput() method
* Add the flink-jdbc maven and mysql-connector java maven
```
<!-- https://mvnrepository.com/artifact/org.apache.flink/flink-jdbc -->
<dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-jdbc_2.11</artifactId>
    <version>1.8.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>5.1.47</version>
</dependency>
```
* The example of reading data from mysql is related to ReadFromHive.java
### addSource() method
* Add the mysql-connector-java maven
```
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>5.1.47</version>
</dependency>
```
* The SourceFunction is related to SourceFromMysql.java and the example of reading data from mysql is related to CalculateHotItemsNum.java in the examples package
