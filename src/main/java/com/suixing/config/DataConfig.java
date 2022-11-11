package com.suixing.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataConfig {
    @Bean(value = "getMySQlDataModel")
    public DataModel getMySQlDataModel(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDatabaseName("suixing");

        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource,"recommend","user_id","car_id","rec_value","rec_time");
        return  dataModel;
    }
}
