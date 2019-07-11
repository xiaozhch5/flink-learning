# Table API & SQL introduction
## Build the developing environment
* Add the Marven repository
```
<!-- https://mvnrepository.com/artifact/org.apache.flink/flink-table -->
<dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-table</artifactId>
    <version>${flink.version}</version>
    <type>pom</type>
    <scope>provided</scope>
</dependency>

<!-- Apache Flink dependencies -->
<!-- These dependencies are provided, because they should not be packaged into the JAR file. -->
<dependency>
	<groupId>org.apache.flink</groupId>
	<artifactId>flink-java</artifactId>
	<version>${flink.version}</version>
	<scope>provided</scope>
</dependency>

<dependency>
	<groupId>org.apache.flink</groupId>
	<artifactId>flink-streaming-java_${scala.binary.version}</artifactId>
	<version>${flink.version}</version>
	<scope>provided</scope>
</dependency>
``` 
## TableEnvironment basic operation
* Build TableEnvironment
```java
StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();

```