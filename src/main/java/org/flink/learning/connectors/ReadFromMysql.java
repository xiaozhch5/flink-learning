package org.flink.learning.connectors;
/**
 *@className ReadFromMysql
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-5
 **/

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.*;




public class ReadFromMysql {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource dataStream = env.createInput(
                JDBCInputFormat.buildJDBCInputFormat()
                        .setDrivername("com.mysql.jdbc.Driver")
                        .setDBUrl("jdbc:mysql://host:port/database?useSSL=false")
                        .setUsername("username")
                        .setPassword("password")
                        .setQuery("select * from Student")
                        .setRowTypeInfo(new RowTypeInfo(BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO))
                        .finish());


        dataStream.print();
        env.execute("mysql");
    }

}

