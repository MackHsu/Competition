package com.example.competition.Database.Model;

import java.io.Serializable;
import java.util.Date;

public class Competition implements Serializable {
    private String competitionId;
    private String name;
    private String img;
    private Date signUpDate1;
    private Date signUpDate2;
    private Date competitionDate1;
    private Date competitionDate2;
    private String host;
    private String typeId;
    private String levelId;
    private String description;

    public Competition(String competitionId, String name, String img, Date signUpDate1, Date signUpDate2, Date competitionDate1, Date competitionDate2, String host, String typeId, String levelId, String desc) {
        this.competitionId = competitionId;
        this.name = name;
        this.img = img;
        this.signUpDate1 = signUpDate1;
        this.signUpDate2 = signUpDate2;
        this.competitionDate1 = competitionDate1;
        this.competitionDate2 = competitionDate2;
        this.host = host;
        this.typeId = typeId;
        this.levelId = levelId;
        this.description = desc;
    }

    public Competition() {
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getDescription() {
        return description.replace("\\n", "\n");
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
