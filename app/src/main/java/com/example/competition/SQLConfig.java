package com.example.competition;

public class SQLConfig {
    final public static String CLS = "com.mysql.jdbc.Driver";
//    final public static String IP = "10.132.7.82:3306";
    final public static String IP = "192.168.137.1:3306";
    final public static String SCHEMA = "competitionapp";
    final public static String USER = "myuser";
    final public static String PWD = "123456";

    public static String getURL() {
        return "jdbc:mysql://" + IP + "/" + SCHEMA
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    }
}
