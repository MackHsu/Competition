package com.example.competition.Database.Dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.competition.Database.Model.Competition;
import com.example.competition.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDao extends DatabaseHelper {

    /**
     * 获取竞赛列表
     * @param search 竞赛名搜索字段
     * @param type 竞赛类型
     * @param level 竞赛级别
     * @param index 分页查询 起始
     * @param count 分页查询 个数
     * @return 获取到的结果
     */
    public static List<Competition> getCompetition(String search, String type, String level, int index, int count) {
         List<Competition> list = new ArrayList<>();
         try {
             getConnection();

             String sql = "SELECT competitionId,name,signUpDate1,signUpDate2,competitionDate1,competitionDate2,host,typeId,levelId FROM competition";
             List<String> parameters = new ArrayList<>();
             if (search != null || type != null || level != null ) {
                 sql += " WHERE";
                 if (search != null) {
                     sql += " name LIKE ?";
                     parameters.add(search);
                 }
                 if (type != null) {
                     sql += " AND type_id=?";
                     parameters.add(type);
                 }
                 if (level != null) {
                     sql += " AND level_id=?";
                     parameters.add(level);
                 }
             }
             int end = index + count;
             sql += " LIMIT " + index + "," + count + ";";

             pStmt = conn.prepareStatement(sql);
             for (int i = 0; i < parameters.size(); i++) pStmt.setObject(i + 1, parameters.get(i));

             res = pStmt.executeQuery();
             ResultSetMetaData rsmd = res.getMetaData();
             int columnCount = rsmd.getColumnCount();

             while(res.next()) {
                 Competition competition = new Competition();
                 // 通过反射给competition中的字段赋值
                 for (int i = 0; i < columnCount; i++) {
                     Object value = res.getObject(i + 1);
                     String columnLabel = rsmd.getColumnLabel(i + 1);
                     Field field = Competition.class.getDeclaredField(columnLabel);
                     field.setAccessible(true);
                     field.set(competition, value);
                 }
                 list.add(competition);
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             closeAll();
         }
        return list;
    }

    public static int updateImg(String competitionId, String imagePath) {
        int iRow = 0;
        try {
            getConnection();

            FileInputStream fis = new FileInputStream(imagePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = -1;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.close();
            fis.close();
            byte[] bytes = baos.toByteArray();
            Blob pic = conn.createBlob();
            pic.setBytes(1, bytes);
            String sql = "UPDATE competition SET img=? WHERE competitionId=?;";
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1, pic);
            pStmt.setObject(2, competitionId);
            iRow = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    public static Bitmap getImg(String competitionId) {
        Bitmap image = null;
        try {
            getConnection();
            String sql = "SELECT img FROM competition WHERE competitionId=?;";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, competitionId);
            res = pStmt.executeQuery();
            Blob blob = null;
            if (res.next()) {
                blob = res.getBlob("img");
            }

            InputStream is = blob.getBinaryStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            byte[] bytes = baos.toByteArray();
            image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return image;
    }

    public static String getTypeName(String typeId) {
        String name = null;
        try {
            getConnection();

            String sql = "SELECT name FROM ctype WHERE typeId=?;";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, typeId);
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

    public static String getLevelName(String levelId) {
        String name = null;
        try {
            getConnection();

            String sql = "SELECT name FROM clevel WHERE levelId=?;";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, levelId);
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
