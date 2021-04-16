package com.example.competition.Services;

import com.example.competition.SQLConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CompetitionService {
    public static int getCompetitionCount() {

        int count = 0;

        try {
            Class.forName(SQLConfig.CLS).newInstance();
            Connection connection = DriverManager.getConnection(SQLConfig.getURL(), SQLConfig.USER, SQLConfig.PWD);
            String sql = "SELECT COUNT(competition_id) AS count FROM competition";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
