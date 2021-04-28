package com.example.competition.Database.Dao;

import android.util.Log;

import com.example.competition.Database.DatabaseHelper;
import com.example.competition.MyUtils;

public class UserDao extends DatabaseHelper {

    public static int SIGN_UP_OK = 0;
    public static int SIGN_UP_FAIL = 1;

    public static int signUp(String account, String password) {
        int result = SIGN_UP_FAIL;
        try {
            getConnection();

            String accountMD5 = MyUtils.MD5Encrypt(account);
            String passwordMD5 = MyUtils.MD5Encrypt(password);

            String sql = "INSERT INTO user(userId,account,password) VALUES(REPLACE(UUID(),'-',''),?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, accountMD5);
            pStmt.setString(2, passwordMD5);
            int iRow = pStmt.executeUpdate();
            Log.d("UserDao", "signUp: iRow = " + iRow);
            if (iRow > 0) result = SIGN_UP_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String signIn(String account, String password) {
        String userId = null;
        try {
            String accountMD5 = MyUtils.MD5Encrypt(account);
            String passwordMD5 = MyUtils.MD5Encrypt(password);

            getConnection();

            String sql = "SELECT userID FROM user WHERE account=? AND password=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, accountMD5);
            pStmt.setString(2, passwordMD5);
            res = pStmt.executeQuery();

            if (res.next()) {
                userId = res.getString("userId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static String getUserName(String userId) {
        String name = null;
        try {
            getConnection();

            String sql = "SELECT name FROM user WHERE userId=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userId);
            res = pStmt.executeQuery();
            if (res.next()) {
                name = res.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return name;
    }
}
