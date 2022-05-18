package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;
import com.example.myapplication.application.GameView;

import java.util.ArrayList;
import java.util.Arrays;

public class HardGame extends AbstractGame{
    public HardGame(GameView client) {
        super(client);
    }

    @Override
    protected void setParameters() {
        // 敌机生成速度加快
        spawnDuration = 400;
        // 射击速度加快
        shootDuration = 400;
        // 敌机数目增多
        enemyMaxNumber = 10;
        // 有Boss机
        hasBoss = true;
        // Boss血量增加
        bossIncreaseHp = 100;
        // 精英机比率升高
        enemyProbabilities.set(0, 0.4);
        enemyProbabilities.set(1, 0.6);
        // 道具比率调整
        propsProbabilities = new ArrayList<>(Arrays.asList(0.2, 0.2, 0.3, 0.3));
        // 难度变化
        difficultyChange = true;
        enemyHpRateBooster = 1.02;
        enemySpawnRateBooster = 1.01;
        eliteEnemyRateBooster = 1.01;
    }
}
