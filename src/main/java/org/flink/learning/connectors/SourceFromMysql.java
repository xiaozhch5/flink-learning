package org.flink.learning.connectors;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *@className SourceFromMysql
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-5
 **/
 
public class SourceFromMysql implements SourceFunction<HotItems> {

    @Override
    public void run(SourceContext<HotItems> sc) throws Exception{
        String driveName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://host:port/flinktest?useSSL=false";
        String userName = "userName";
        String password = "password";
        String sql = "SELECT * FROM hotItems";
        Class.forName(driveName);
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            HotItems hotItems = new HotItems(result.getInt("userId"),
                    result.getInt("itemId"),
                    result.getInt("categoryId"),
                    result.getString("behavior"),
                    result.getInt("timestamps"));
            sc.collect(hotItems);
        }

    }
    @Override
    public void cancel(){

    }
}
