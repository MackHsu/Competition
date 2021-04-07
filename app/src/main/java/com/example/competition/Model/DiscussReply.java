package com.example.competition.Model;

import java.util.Date;

public class DiscussReply {

    private int id;
    private int index;
    private Date time;
    private int replyIndex;
    private String content;

    public DiscussReply() {
        id = 0;
        index = 0;
        time = new Date();
        replyIndex = 0;
        content = "test content";
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
}
