package com.example.competition.Database.Dao;

import android.util.Log;

import com.example.competition.Database.DatabaseHelper;
import com.example.competition.MyUtils;

import java.util.UUID;

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

    public static String addFavoriteCompetition(String userId, String competitionId) {
        String ret = null;
        String favCompetitionId = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            getConnection();
            String sql = "SELECT * FROM favoritecompetition WHERE userId=? AND competitionId=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userId);
            pStmt.setString(2, competitionId);
            res = pStmt.executeQuery();

            if (!res.next()) {
                sql = "INSERT INTO favoritecompetition(favCompetitionId,userId,competitionId) VALUES(?,?,?)";
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, favCompetitionId);
                pStmt.setString(2, userId);
                pStmt.setString(3, competitionId);
                int iRow = pStmt.executeUpdate();
                if (iRow > 0) {
                    ret = favCompetitionId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ret;
    }

    public static String addFavoriteReply(String userId, String replyId) {
        String ret = null;
        String favReplyId = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            getConnection();
            String sql = "SELECT * FROM favoritereply WHERE userId=? AND replyId=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userId);
            pStmt.setString(2, replyId);
            res = pStmt.executeQuery();

            if (!res.next()) {
                sql = "INSERT INTO favoritereply(favReplyId,userId,replyId) VALUES(?,?,?)";
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, favReplyId);
                pStmt.setString(2, userId);
                pStmt.setString(3, replyId);
                int iRow = pStmt.executeUpdate();
                if (iRow > 0) {
                    ret = favReplyId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ret;
    }

    public static String addFavoriteRecruitment(String userId, String recruitmentId) {
        String ret = null;
        String favRecruitmentId = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            getConnection();
            String sql = "SELECT * FROM favoriterecruitment WHERE userId=? AND recruitmentId=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userId);
            pStmt.setString(2, recruitmentId);
            res = pStmt.executeQuery();

            if (!res.next()) {
                sql = "INSERT INTO favoriterecruitment(favRecruitmentId,userId,recruitmentId) VALUES(?,?,?)";
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, favRecruitmentId);
                pStmt.setString(2, userId);
                pStmt.setString(3, recruitmentId);
                int iRow = pStmt.executeUpdate();
                if (iRow > 0) {
                    ret = favRecruitmentId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ret;
    }
}
