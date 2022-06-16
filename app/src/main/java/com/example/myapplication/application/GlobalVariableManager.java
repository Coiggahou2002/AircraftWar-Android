package com.example.myapplication.application;

import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.network.GameProp;
import com.example.myapplication.network.GameRoomDTO;

import java.util.List;

public class GlobalVariableManager {
    public static String userId;
    public static String username;
    public static int coin;
    public static String avatarPath;
    public static List<GameRoomDTO> gameRoomList;
    public static List<GameProp> propList;
    public static int chosenRoomIdx;
    public static int chosenPropIdx;
    public static boolean isOnlineMode;
    public static String onlineRoomId;
    public static int myScore;
    public static int opponentScore;
    public static int onlineGameStatus;
    public static final int GAME_STATUS_END = 2;
    public static final int GAME_STATUS_PLAYING = 1;
    public static final int GAME_STATUS_STANDBY = 0;
    public static AbstractGame gameInsRef;
}