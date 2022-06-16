package com.example.myapplication.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameRoomDTO {
    @SerializedName("gameRoomId")
    @Expose
    private String gameRoomId;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("createTime")
    @Expose
    private String createTime;

    @SerializedName("playerIdA")
    @Expose
    private String playerIdA;

    @SerializedName("playerNameA")
    @Expose
    private String playerNameA;

    @SerializedName("playerScoreA")
    @Expose
    private Integer playerScoreA;

    @SerializedName("playerIdB")
    @Expose
    private String playerIdB;

    @SerializedName("playerNameB")
    @Expose
    private String playerNameB;

    @SerializedName("playerScoreB")
    @Expose
    private Integer playerScoreB;

    public GameRoomDTO(String gameRoomId, Integer status, String createTime, String playerIdA, String playerNameA, Integer playerScoreA, String playerIdB, String playerNameB, Integer playerScoreB) {
        this.gameRoomId = gameRoomId;
        this.status = status;
        this.createTime = createTime;
        this.playerIdA = playerIdA;
        this.playerNameA = playerNameA;
        this.playerScoreA = playerScoreA;
        this.playerIdB = playerIdB;
        this.playerNameB = playerNameB;
        this.playerScoreB = playerScoreB;
    }

    public String getPlayerNameA() {
        return playerNameA;
    }

    public void setPlayerNameA(String playerNameA) {
        this.playerNameA = playerNameA;
    }

    public String getPlayerNameB() {
        return playerNameB;
    }

    public void setPlayerNameB(String playerNameB) {
        this.playerNameB = playerNameB;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(String gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPlayerIdA() {
        return playerIdA;
    }

    public void setPlayerIdA(String playerIdA) {
        this.playerIdA = playerIdA;
    }

    public Integer getPlayerScoreA() {
        return playerScoreA;
    }

    public void setPlayerScoreA(Integer playerScoreA) {
        this.playerScoreA = playerScoreA;
    }

    public String getPlayerIdB() {
        return playerIdB;
    }

    public void setPlayerIdB(String playerIdB) {
        this.playerIdB = playerIdB;
    }

    public Integer getPlayerScoreB() {
        return playerScoreB;
    }

    public void setPlayerScoreB(Integer playerScoreB) {
        this.playerScoreB = playerScoreB;
    }
}
