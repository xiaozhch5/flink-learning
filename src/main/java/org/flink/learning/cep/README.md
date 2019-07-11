# Flink cep
## Prepare the execution environment
* Add the maven repository
```
<!-- https://mvnrepository.com/artifact/org.apache.flink/flink-cep -->
<dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-cep_2.12</artifactId>
    <version>1.8.0</version>
</dependency>
```
## Pattern API
* Build CEP programs with Pattern API.

* An example  of Pattern API
```java
DataStream<Event> input = ...

Pattern<Event, ?> pattern = Pattern.<Event>begin("start").where(
        new SimpleCondition<Event>() {
            @Override
            public boolean filter(Event event) {
                return event.getId() == 42;
            }
        }
    ).next("middle").subtype(SubEvent.class).where(
        new SimpleCondition<SubEvent>() {
            @Override
            public boolean filter(SubEvent subEvent) {
                return subEvent.getVolume() >= 10.0;
            }
        }
    ).followedBy("end").where(
         new SimpleCondition<Event>() {
            @Override
            public boolean filter(Event event) {
                return event.getName().equals("end");
            }
         }
    );

PatternStream<Event> patternStream = CEP.pattern(input, pattern);

DataStream<Alert> result = patternStream.process(
    new PatternProcessFunction<Event, Alert>() {
        @Override
        public void processMatch(
                Map<String, List<Event>> pattern,
                Context ctx,
                Collector<Alert> out) throws Exception {
            out.collect(createAlertFrom(pattern));
        }
    });
```
### Individual Patterns
* Individual patterns begins with **begin** function, apart from that, event type has to be identified. <start_pattern> is the PatternName object.
```java
Pattern pattern = Pattern.<Event>begin("start_pattern");
```
* Next, we use Pattern.where() to assign the condition, that's to say, when the condition is satisfied the Pattern could accept the event.
```java
Pattern pattern = start.where(new SimpleCondition<Event>(){...});
```
* Assign the circle times
    * Use times() function to define the fixed circle times
    ```java
    // define the fixed circle times, 4.
    Pattern pattern = start.times(4);
    // define the range of the circle times, 2, 3 or 4.
    Pattern pattern = start.times(2, 4);
    ```
    * Use optional() function to define the times of process or not process.
    ```java
    Pattern pattern = start.times(4).optional();
    Pattern pattern = start.times(2, 4).optional();
    ```
    * Use greedy() function to assign the pattern greedy mode.
    ```java
    Pattern pattern = start.times(4).greedy();
    Pattern pattern = start.times(2, 4).optional().greedy();
    ```
    * Use oneOrMore() to define the times.
    ```java
    Pattern pattern = start.oneOrMore();
    Pattern pattern = start.oneOrMore().greedy();
    Pattern pattern = start.oneOrMore().optional();
    Pattern pattern = start.oneOrMore().optional().greedy();
    ```
    * Use timesOrMore to define the times.
    ```java
    Pattern pattern = start.timesOrMore(2);
    Pattern pattern = start.timesOrMore(2).greedy();
    Pattern pattern = start.timesOrMore(2).optional().greedy();
    Pattern pattern = start.timesOrMore();
    ```
* Define the condition used in where(), or() and until() function.
    * Iterative Conditions
    * Simple Conditions
    * Combining Conditions
### Pattern sequence
### Pattern group
## Get the event
* When you have defined the pattern sequence or pattern group, you have put them together with the input dataStream.
```java
PatternStream pattrenStream = CEP.pattern(input, pattern);
```
* When CEP.pattern is conducted, PatternStream data will be produced. 
* FlinkCEP produces select() and flatSelect() function to extract the output of the event.