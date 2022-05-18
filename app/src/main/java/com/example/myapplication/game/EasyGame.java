package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;
import com.example.myapplication.application.GameView;

import java.util.ArrayList;
import java.util.Arrays;

public class EasyGame extends AbstractGame{
    public EasyGame(GameView client) {
        super(client);
    }

    @Override
    protected void setParameters() {
        // 敌机生成速度减缓
        spawnDuration = 800;
        // 射击速度减缓
        shootDuration = 800;
        // 击败敌机得分减少
        enemyScore = 8;
        // 没有Boss机
        hasBoss = false;
        // 精英机比率降低
        enemyProbabilities.set(0, 0.6);
        enemyProbabilities.set(1, 0.4);
        // 道具比率调整
        propsProbabilities = new ArrayList<>(Arrays.asList(0.4, 0.3, 0.2, 0.1));
    }
}
