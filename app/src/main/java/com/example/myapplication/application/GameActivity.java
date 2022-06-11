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
import com.example.myapplication.network.GameHandler;

public class GameActivity extends AppCompatActivity {

    private Intent currentIntent;
    private GameView gameView;

    int difficulty;
    int online;
    String username;
    GameHandler networkHandler;

    private boolean musicEnabled;
    public MusicService.MusicBinder myMusicBinder;
    private MusicConnect myMusicConnect;

    private Intent standingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentIntent = getIntent();
        setViews();

        difficulty = currentIntent.getIntExtra(Config.DIFFICULTY, 0);
        online = currentIntent.getIntExtra(Config.ONLINE, 0);
        username = getIntent().getStringExtra(Config.USERNAME);
        networkHandler = (GameHandler) currentIntent.getSerializableExtra(Config.NETWORK_HANDLER);

        musicEnabled = currentIntent.getBooleanExtra(Config.MUSIC_ENABLE, true);
        myMusicConnect = new MusicConnect();
        bindService(new Intent(this, MusicService.class),
                myMusicConnect,
                Context.BIND_AUTO_CREATE
        );

        standingIntent = new Intent(GameActivity.this, StandingActivity.class);
        standingIntent.putExtra(Config.DIFFICULTY, difficulty);
        standingIntent.putExtra(Config.ONLINE, online);
        standingIntent.putExtra(Config.USERNAME, username);
        standingIntent.putExtra(Config.NETWORK_HANDLER, networkHandler);
    }

    private void setViews() {
        gameView = new GameView(this);
        gameView.difficulty = currentIntent.getIntExtra(Config.DIFFICULTY, 0);
        Log.i(Config.GAME_ACTIVITY_INFO_TAG, "" + gameView.difficulty);
        setContentView(gameView);
    }

    class MusicConnect implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(Config.MUSIC_INFO_TAG, "Connect Music");
            myMusicBinder = (MusicService.MusicBinder) service;
            myMusicBinder.setEnable(musicEnabled);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    }

    protected void onGameStop(int score) {
        if (online == 1) {
            // TODO: 此处回头加个GUI文本提示
            standingIntent.putExtra(Config.OPPONENT_SCORE,
                    networkHandler.onOnlineGameOver(username, score)
            );
        }
        else {
            networkHandler.onNormalGameOver(username, score, difficulty);
        }
        standingIntent.putExtra(Config.SCORE, score);
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