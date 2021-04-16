package com.example.competition.Database;

import com.example.competition.SQLConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHelper {
    public static Connection conn;  // 全局数据库连接
    public static Statement stmt;  // 命令集
    public static PreparedStatement pStmt; // 预编译命令集
    public static ResultSet res;  // 结果集

    // 获取连接
    public static void getConnection() {
        try {
            Class.forName(SQLConfig.CLS).newInstance();
            conn = DriverManager.getConnection(SQLConfig.getURL(), SQLConfig.USER, SQLConfig.PWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 关闭连接
    public static void closeAll() {
        try {
            if (res != null) {
                res.close();
                res = null;
            }
            if (pStmt != null) {
                pStmt.close();
                pStmt = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
