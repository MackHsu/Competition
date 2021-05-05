package com.example.competition.Database.Dao;

import com.example.competition.Database.DatabaseHelper;
import com.example.competition.Model.Message;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ConversationDao extends DatabaseHelper {
    public static String addMessage(String userId, String user2Id, String content) {
        String messageId = UUID.randomUUID().toString().replaceAll("-", "");
        String message2Id = UUID.randomUUID().toString().replaceAll("-", "");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        int iRow = 0;
        try {
            getConnection();

            String sql = "SELECT conversationId FROM conversation WHERE userId=? AND user2Id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userId);
            pStmt.setString(2, user2Id);
            res = pStmt.executeQuery();
            String conversationId = null;
            if (!res.next()) {
                sql = "INSERT INTO conversation(conversationId, userId, user2Id, date) VALUES(?,?,?,?)";
                conversationId = UUID.randomUUID().toString().replaceAll("-", "");
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, conversationId);
                pStmt.setString(2, userId);
                pStmt.setString(3, user2Id);
                pStmt.setString(4, dateStr);
                iRow = pStmt.executeUpdate();
                if (iRow <= 0) return "conversation 1 insert failed";
            } else {
                conversationId = res.getString("conversationId");
            }

            sql = "INSERT INTO message(messageId, conversationId,isSend,content,date) VALUES(?,?,1,?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, messageId);
            pStmt.setString(2, conversationId);
            pStmt.setString(3, content);
            pStmt.setString(4, dateStr);
            iRow = pStmt.executeUpdate();
            if (iRow <= 0) return "message 1 insert failed";

            sql = "UPDATE conversation SET date=? WHERE conversationId=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, dateStr);
            pStmt.setString(2, conversationId);
            iRow = pStmt.executeUpdate();
            if (iRow <= 0) return "conversation 1 date update failed";

            sql = "SELECT conversationId FROM conversation WHERE userId=? AND user2Id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, user2Id);
            pStmt.setString(2, userId);
            res = pStmt.executeQuery();
            String conversation2Id = null;
            if (!res.next()) {
                sql = "INSERT INTO conversation(conversationId, userId, user2Id, date) VALUES(?,?,?,?)";
                conversation2Id = UUID.randomUUID().toString().replaceAll("-", "");
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, conversation2Id);
                pStmt.setString(2, user2Id);
                pStmt.setString(3, userId);
                pStmt.setString(4, dateStr);
                iRow = pStmt.executeUpdate();
                if (iRow <= 0) return "conversation 2 insert failed";
            } else {
                conversation2Id = res.getString("conversationId");
            }

            sql = "INSERT INTO message(messageId,conversationId,isSend,content,date) VALUES(?,?,0,?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, message2Id);
            pStmt.setString(2, conversation2Id);
            pStmt.setString(3, content);
            pStmt.setString(4, dateStr);
            iRow = pStmt.executeUpdate();
            if (iRow <= 0) return "message 2 insert failed";

            sql = "UPDATE conversation SET date=? WHERE conversationId=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, dateStr);
            pStmt.setString(2, conversation2Id);
            iRow = pStmt.executeUpdate();
            if (iRow <= 0) return "conversation 2 date update failed";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return "insert succeeded";
    }

    public static List<Message> getMessageListWithUserId(String userId, String user2Id, int index, int count) {
        List<Message> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "SELECT conversationId FROM conversation WHERE userId=? AND user2Id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userId);
            pStmt.setString(2, user2Id);
            res = pStmt.executeQuery();
            if (res.next()) {
                String conversationId = res.getString("conversationId");
                sql = "SELECT messageId,conversationId,isSend,content,date FROM message WHERE conversationId=? ORDER BY date DESC, messageId ASC LIMIT ?,?";
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, conversationId);
                pStmt.setInt(2, index);
                pStmt.setInt(3, count);
                res = pStmt.executeQuery();
                while (res.next()) {
                    Message message = new Message();
                    message.setMessageId(res.getString("messageId"));
                    message.setConversationId(res.getString("conversationId"));
                    message.setIsSend(res.getInt("isSend"));
                    message.setContent(res.getString("content"));
                    Timestamp timestamp = (Timestamp) res.getObject("date");
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                    message.setDate(dateStr);
                    list.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        Collections.reverse(list);
        return list;
    }
}
