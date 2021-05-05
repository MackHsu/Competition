package com.example.competition.Model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Message implements MultiItemEntity {
    public static final int TYPE_SEND = 0;
    public static final int TYPE_RECEIVE = 1;

    private String messageId;
    private String conversationId;
    private int isSend;
    private String content;
    private String date;

    @Override
    public int getItemType() {
        return isSend == 0 ? TYPE_RECEIVE : TYPE_SEND;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Message() {
    }
}
