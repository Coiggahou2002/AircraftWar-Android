package com.example.myapplication.network;

import java.util.List;

public class UserInfoDTO {
    private String userId;
    private String username;
    private String avatarPath;
    private Integer coin;
    private List<GameRecord> records;

    public UserInfoDTO(String userId, String username, String avatarPath, Integer coin, List<GameRecord> records) {
        this.userId = userId;
        this.username = username;
        this.avatarPath = avatarPath;
        this.coin = coin;
        this.records = records;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public List<GameRecord> getRecords() {
        return records;
    }

    public void setRecords(List<GameRecord> records) {
        this.records = records;
    }
}
