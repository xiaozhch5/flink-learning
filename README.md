# Flink-learning
* This is a maven project

## Flink-streaming reference materials are listed below

* You can download the whole package and import it into IDEA to run the code

* Flink-connectors are in the dir of "[/src/main/java/org/flink/learning/streaming/connectors](https://github.com/xiaozhch5/flink-learning/tree/master/src/main/java/org/flink/learning/streaming/connectors)"

* Some examples are in the dir of "[/src/main/java/org/flink/learning/streaming/examples](https://github.com/xiaozhch5/flink-learning/tree/master/src/main/java/org/flink/learning/streaming/examples)"

* Transformations are in the dir of "[/src/main/java/org/flink/learning/streaming/transformation](https://github.com/xiaozhch5/flink-learning/tree/master/src/main/java/org/flink/learning/streaming/transformation)"

* Watermarks are in the dir of "[/src/main/java/org/flink/learning/streaming/watermarks](https://github.com/xiaozhch5/flink-learning/tree/master/src/main/java/org/flink/learning/streaming/watermarks)"

* Some examples about working with state are in the dir of "[/src/main/java/org/flink/learning/streaming/workingwithstate](https://github.com/xiaozhch5/flink-learning/tree/master/src/main/java/org/flink/learning/streaming/workingwithstate)"

## Some important advices on running program on the standalone flink cluster

* You had better package the program to **a fat jar**
* When you are going to **manage the files in the HDFS**, you **had better not** include the hadoop-*.jar in the classpath at every machine of the cluster. The best way is to add dependencies in the pom.xml
* When you add the dependence to the pom.xml, please double check the corresponding scala version. They should be the same.

## Some problems and solutions encountered in running flink 
* The problems and solutions are stored in the dirs of [Problems_and_solutions_encountered_in_running_flink](https://github.com/xiaozhch5/flink-learning/tree/master/Problems_and_solutions_encountered_in_running_flink).