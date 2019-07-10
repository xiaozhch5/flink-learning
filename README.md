# Flink-learning
* This is a maven project

## Flink-streaming reference materials are listed below

* You can download the whole package and import it into IDEA to run the code

* Flink-connectors are in the dir of "/src/main/java/org/flink/learning/connectors"

* Some examples are in the dir of "/src/main/java/org/flink/learning/examples"

* Transformations are in the dir of "/src/main/java/org/flink/learning/transformation"

* Watermarks are in the dir of "/src/main/java/org/flink/learning/watermarks"

* Some examples about working with state are in the dir of "/src/main/java/org/flink/learning/workingwithstate"

## Some important advice on running program on the standalone flink cluster

* You had better package the program **a fat jar**
* When you are going to **manage the files in the HDFS**, you **had better not** include the hadoop-*.jar in the root classpath at every machine of the cluster. The best way is to add dependencies in the pom.xml
