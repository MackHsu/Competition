package com.example.competition.Model;

import android.graphics.Bitmap;

import com.example.competition.R;

import java.util.Date;

public class Competition {
    private int imageResourceId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
    private Bitmap img;
    private Date signUpDate1;
    private Date signUpDate2;
    private Date competitionDate1;
    private Date competitionDate2;
    private String host;
    private String type;
    private String level;

    public Competition() {
        imageResourceId = R.drawable.ic_favorite;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Date getSignUpDate1() {
        return signUpDate1;
    }

    public void setSignUpDate1(Date signUpDate1) {
        this.signUpDate1 = signUpDate1;
    }

    public Date getSignUpDate2() {
        return signUpDate2;
    }

    public void setSignUpDate2(Date signUpDate2) {
        this.signUpDate2 = signUpDate2;
    }

    public Date getCompetitionDate1() {
        return competitionDate1;
    }

    public void setCompetitionDate1(Date competitionDate1) {
        this.competitionDate1 = competitionDate1;
    }

    public Date getCompetitionDate2() {
        return competitionDate2;
    }

    public void setCompetitionDate2(Date competitionDate2) {
        this.competitionDate2 = competitionDate2;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
