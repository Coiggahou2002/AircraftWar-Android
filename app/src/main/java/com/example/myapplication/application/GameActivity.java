package com.example.myapplication.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myapplication.Config;
import com.example.myapplication.game.multimedia.MusicService;

public class GameActivity extends AppCompatActivity {

    private Intent currentIntent;
    private GameView gameView;

    public MusicService.MusicBinder myMusicBinder;
    private MusicConnect myMusicConnect;
    private Intent musicIntent;

    private Intent standingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentIntent = getIntent();
        setViews();

        myMusicConnect = new MusicConnect();
        musicIntent = new Intent(this, MusicService.class);
        bindService(musicIntent, myMusicConnect, Context.BIND_AUTO_CREATE);

        standingIntent = new Intent(GameActivity.this, StandingActivity.class);
    }

    private void setViews() {
        gameView = new GameView(this);
        gameView.difficulty = currentIntent.getIntExtra(Config.DIFFICULTY, 0);
        gameView.musicEnable = currentIntent.getBooleanExtra(Config.MUSIC_ENABLE, false);
        Log.i(Config.GAME_ACTIVITY_INFO_TAG, "" + gameView.difficulty);
        setContentView(gameView);
    }

    class MusicConnect implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(Config.MUSIC_INFO_TAG, "Connect Music");
            myMusicBinder = (MusicService.MusicBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    }

    protected void onGameStop() {
        startActivity(standingIntent);
        finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(myMusicConnect);
    }
}