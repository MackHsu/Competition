package com.example.competition.Model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;

public class DiscussReply implements MultiItemEntity {

    private int itemType;
    private String replyId;
    private String discussId;
    private String userId;
    private String date;
    private String content;
    private String replyUserId;

    public static final int DISCUSS = 1;
    public static final int REPLY = 0;

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public DiscussReply() {
        itemType = REPLY;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getDiscussId() {
        return discussId;
    }

    public void setDiscussId(String discussId) {
        this.discussId = discussId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
