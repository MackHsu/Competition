package com.example.competition.Model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;

public class DiscussReply implements MultiItemEntity {

    private int id;
    private int index;
    private Date time;
    private int replyIndex;
    private String content;
    private int itemType;

    public static final int DISCUSS = 0;
    public static final int REPLY = 1;

    public DiscussReply() {
        id = 0;
        index = 0;
        time = new Date();
        replyIndex = 0;
        content = "test content";
        itemType = DISCUSS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getReplyIndex() {
        return replyIndex;
    }

    public void setReplyIndex(int replyIndex) {
        this.replyIndex = replyIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
