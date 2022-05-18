package com.example.myapplication.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.myapplication.Config;

public class GameActivity extends AppCompatActivity {

    private Intent currentIntent;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentIntent = getIntent();
        setViews();
    }

    private void setViews() {
        gameView = new GameView(this);
        gameView.difficulty = currentIntent.getIntExtra(Config.DIFFICULTY, 0);
        gameView.musicEnable = currentIntent.getBooleanExtra(Config.MUSIC_ENABLE, false);
        Log.i(Config.GAME_ACTIVITY_INFO_TAG, gameView.difficulty + " ");
        setContentView(gameView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE ||
            event.getAction() == MotionEvent.ACTION_DOWN) {
            gameView.fingerX = event.getX();
            gameView.fingerY = event.getY();
        }
        Log.v(Config.GAME_ACTIVITY_INFO_TAG, gameView.fingerX + " " + gameView.fingerY);
        return true;
    }
}