package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private GameSurfaceView gameSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameSurfaceView = new GameSurfaceView(this);
        setContentView(gameSurfaceView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            gameSurfaceView.x = event.getX();
            gameSurfaceView.y = event.getY();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return true;
    }
}