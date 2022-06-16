package com.example.myapplication.network;

import java.util.Date;

public class GameRecord {
    private String id;
    private Integer score;
    private Date createTime;
    private String userId;

    public GameRecord(String id, Integer score, Date createTime, String userId) {
        this.id = id;
        this.score = score;
        this.createTime = createTime;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
