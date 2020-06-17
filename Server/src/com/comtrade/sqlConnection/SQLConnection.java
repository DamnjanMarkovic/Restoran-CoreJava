package com.comtrade.sqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private volatile static SQLConnection instance;
    private Connection connection;
    private static Object mutex = new Object();

    public Connection getConnection() {
        return connection;
    }

    private SQLConnection(){

    }

    public static SQLConnection getInstance() {
        if (instance==null){
            instance = new SQLConnection();
        }
        return instance;
    }

    public void startTransaction(){
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Sql2020D!");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Sql2020D!");
            //url: jdbc:mysql://localhost:3306/jhipster?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC



            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "phpmyadmin", "Sql2020D!");
            connection.setAutoCommit(false);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public void confirmTransaction(){
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelTransaction(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
