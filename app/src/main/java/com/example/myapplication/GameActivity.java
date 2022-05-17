package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private Intent previousIntent;

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        previousIntent = getIntent();
        setView();
    }

    private void setView() {
        gameView = new GameView(this);
        gameView.difficulty = previousIntent.getIntExtra(Config.DIFFICULTY, 0);
        gameView.music_enable = previousIntent.getBooleanExtra(Config.MUSIC_ENABLE, false);
        setContentView(gameView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE ||
            event.getAction() == MotionEvent.ACTION_DOWN) {
            gameView.finger_x = event.getX();
            gameView.finger_y = event.getY();
        }
        Log.i(Config.GAME_ACTIVITY_INFO_TAG, gameView.finger_x + " " + gameView.finger_y);
        return true;
    }
}