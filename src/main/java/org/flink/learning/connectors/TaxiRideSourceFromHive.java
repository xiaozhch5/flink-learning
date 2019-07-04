package org.flink.learning.connectors;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *@className TaxiRideSourceFromHive
 *@description 扩展SourceFunction，实现里面的run、cancel方法。
 *@author zhchxiao
 *
 *@date 19-7-4
 **/

public class TaxiRideSourceFromHive implements SourceFunction<TaxiFare> {
    private String driveName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://hadoop2:10000/db_hive_edu";
    private String sql = "SELECT * FROM taxifares";

    @Override
    public void run(SourceContext<TaxiFare> tr) throws Exception{
        Class.forName(driveName);
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            TaxiFare taxiFare = new TaxiFare(result.getLong("ride_id"),
                    result.getLong("taxi_id"),
                    result.getLong("driver_id"),
                    result.getString("start_time"),
                    result.getString("payment_type"),
                    result.getFloat("tip"),
                    result.getFloat("tolls"),
                    result.getFloat("total_fare"));
            tr.collect(taxiFare);
        }


    }
    @Override
    public void cancel(){

    }
}
