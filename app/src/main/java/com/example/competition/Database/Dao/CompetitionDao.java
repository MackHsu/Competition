package com.example.competition.Database.Dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.competition.Database.Model.Competition;
import com.example.competition.Database.DatabaseHelper;
import com.example.competition.Model.Discuss;
import com.example.competition.Model.DiscussReply;
import com.example.competition.Model.Recruitment;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public static Competition getCompetitionDetail(String competitionId) {
        Competition competition = null;
        try {
            getConnection();

            String sql = "SELECT name,signUpDate1,signUpDate2,competitionDate1,competitionDate2,host,typeId,levelId,description FROM competition WHERE competitionId=?;";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, competitionId);
            res = pStmt.executeQuery();

            ResultSetMetaData rsmd = res.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(res.next()) {
                // 通过反射给competition中的字段赋值
                competition = new Competition();
                for (int i = 0; i < columnCount; i++) {
                    Object value = res.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = Competition.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(competition, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return competition;
    }

    public static int newRecruitment(String competitionId, String userId, String content) {
        int iRow = 0;
        try {
            getConnection();

            String sql = "INSERT INTO recruitment(recruitmentId, competitionId, userId, content, date) VALUES(REPLACE(UUID(),'-',''),?,?,?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, competitionId);
            pStmt.setString(2, userId);
            pStmt.setString(3, content);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = format.format(date);
            pStmt.setString(4, dateStr);
            iRow = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return iRow;
    }

    public static List<Recruitment> getRecruitmentList(String competitionId, int index, int count) {
        List<Recruitment> list = new ArrayList<>();
        try {
            getConnection();

            String sql = "SELECT * FROM recruitment WHERE competitionId=? ORDER BY date DESC, recruitmentId ASC LIMIT " + index + "," + count;
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, competitionId);
            res = pStmt.executeQuery();

            ResultSetMetaData rsmd = res.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(res.next()) {
                Recruitment recruitment = new Recruitment();
                for (int i = 0; i < columnCount; i++) {
                    Object value = res.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = Recruitment.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    if (columnLabel.equals("date")) {
                        Timestamp timestamp = (Timestamp) value;
                        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                        field.set(recruitment, dateStr);
                    } else
                        field.set(recruitment, value);
                }
                list.add(recruitment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public static String newDiscuss(String title, String competitionId, String userId, String content) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        String discussId = UUID.randomUUID().toString().replaceAll("-", "");
        String replyId = UUID.randomUUID().toString().replaceAll("-", "");
        String ret = null;
        try {
            getConnection();

            String sql = "INSERT INTO discuss(discussId,title,date,competitionId,userId) VALUE(?,?,?,?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, discussId);
            pStmt.setString(2, title);
            pStmt.setString(3, dateStr);
            pStmt.setString(4, competitionId);
            pStmt.setString(5, userId);
            int row = pStmt.executeUpdate();
            if (row == 1) {
                String sql2 = "INSERT INTO reply(replyId,discussId,userId,date,content,isFirst) VALUES(?,?,?,?,?,1)";
                pStmt = conn.prepareStatement(sql2);
                pStmt.setString(1, replyId);
                pStmt.setString(2, discussId);
                pStmt.setString(3, userId);
                pStmt.setString(4, dateStr);
                pStmt.setString(5, content);
                row = pStmt.executeUpdate();
                if (row == 1) ret = discussId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ret;
    }

    public static List<Discuss> getDiscussList(String competitionId, int index, int count) {
        List<Discuss> list = new ArrayList<>();
        try {
            getConnection();

            String sql = "SELECT * FROM discuss WHERE competitionId=? ORDER BY date DESC, discussId ASC LIMIT ?,?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, competitionId);
            pStmt.setInt(2, index);
            pStmt.setInt(3, count);
            res = pStmt.executeQuery();

            while(res.next()) {
                Discuss discuss = new Discuss();
                discuss.setDiscussId(res.getString("discussId"));
                discuss.setTitle(res.getString("title"));
                Timestamp timestamp = (Timestamp) res.getObject("date");
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                discuss.setDate(dateStr);
                discuss.setCompetitionId(res.getString("competitionId"));
                discuss.setUserId(res.getString("userId"));
                list.add(discuss);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public static String getDiscussContent(String discussId) {
        String content = null;
        try {
            getConnection();
            String sql = "SELECT content FROM reply WHERE discussId=? AND isFirst=1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, discussId);
            res = pStmt.executeQuery();
            if (res.next()) {
                content = res.getString("content");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return content;
    }

    public static List<DiscussReply> getReplyList(String discussId, int index, int count) {
        List<DiscussReply> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "SELECT * FROM reply WHERE discussId=? ORDER BY date ASC, replyId ASC LIMIT ?,?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, discussId);
            pStmt.setInt(2, index);
            pStmt.setInt(3, count);
            res = pStmt.executeQuery();

            while(res.next()) {
                DiscussReply reply = new DiscussReply();
                if (res.getInt("isFirst") == 1) reply.setItemType(DiscussReply.DISCUSS);
                reply.setContent(res.getString("content"));
                Timestamp timestamp = (Timestamp) res.getObject("date");
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                reply.setDate(dateStr);
                reply.setDiscussId(discussId);
                reply.setReplyId(res.getString("replyId"));
                reply.setReplyUserId(res.getString("replyUserId"));
                reply.setUserId(res.getString("userId"));
                list.add(reply);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public static String newReply(String discussId, String content, String userId, String replyUserId) {
        String ret = null;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        String replyId = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            getConnection();
            String sql = "INSERT INTO reply(replyId,discussId,userId,date,content,replyUserId,isFirst) VALUE(?,?,?,?,?,?,0)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, replyId);
            pStmt.setString(2, discussId);
            pStmt.setString(3, userId);
            pStmt.setString(4, dateStr);
            pStmt.setString(5, content);
            pStmt.setString(6, replyUserId == null ? "" : replyUserId);

            int row = pStmt.executeUpdate();
            if (row > 0) {
                ret = replyId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ret;
    }

    public static String getDiscussTitle(String discussId) {
        String title = null;
        try {
            getConnection();
            String sql = "SELECT title FROM discuss WHERE discussId=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, discussId);
            res = pStmt.executeQuery();
            if (res.next()) {
                title = res.getString("title");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return title;
    }
}
