package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;
import com.example.myapplication.application.GameView;

import java.util.ArrayList;
import java.util.Arrays;

public class NormalGame extends AbstractGame{
    public NormalGame(GameView client) {
        super(client);
    }

    @Override
    protected void setParameters() {
        // 敌机生成速度正常
        spawnDuration = 600;
        // 射击速度正常
        shootDuration = 600;
        // 有Boss机
        hasBoss = true;
        // Boss血量不增加
        bossIncreaseHp = 0;
        // 道具比率调整
        propsProbabilities = new ArrayList<>(Arrays.asList(0.3, 0.3, 0.2, 0.2));
        // 难度变化
        difficultyChange = true;
        enemyHpRateBooster = 1.01;
        enemySpawnRateBooster = 1.005;
        eliteEnemyRateBooster = 1.005;
    }
}
