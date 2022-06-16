package com.example.myapplication.application;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.myapplication.Config;
import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.game.EasyGame;
import com.example.myapplication.game.Game;
import com.example.myapplication.game.HardGame;
import com.example.myapplication.game.multimedia.ImageManager;
import com.example.myapplication.game.NormalGame;
import com.example.myapplication.game.multimedia.MusicService;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView
    implements SurfaceHolder.Callback, Runnable {

    // game setting parameters
    public int difficulty = 0;

    // game running parameters
    public float fingerX = 0, fingerY = 0;
    public int screenWidth = 400, screenHeight = 800;

    // class process parameters
    public Game game;
    public GameActivity myActivity;

    public final Resources myResources;
    public final SurfaceHolder mySurfaceHolder;

    private final int gameInterval = 16;
    private final ScheduledExecutorService executorService;

    public GameView(Context context) {
        super(context);

        myActivity = (GameActivity) context;
        myResources = context.getResources();

        mySurfaceHolder = this.getHolder();
        mySurfaceHolder.addCallback(this);

        this.setFocusable(true);

        executorService = new ScheduledThreadPoolExecutor(1, r -> {
            Thread t = new Thread(r);
            t.setName("game view thread");
            return t;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        executorService.scheduleWithFixedDelay(
                this::gamePeriod,
                gameInterval,
                gameInterval,
                TimeUnit.MILLISECONDS
        );
    }

    // Render Acceleration using GPU
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void gamePeriod() {
        try {
            game.step();
            synchronized (mySurfaceHolder) {
                Canvas canvas = mySurfaceHolder.lockHardwareCanvas();
                game.repaint(canvas);
                mySurfaceHolder.unlockCanvasAndPost(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGameStop(int score) {
        Log.i(Config.GAME_ACTIVITY_INFO_TAG, "" + score);
        executorService.shutdown();
        myActivity.onGameStop(score);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        AbstractGame gameInsRef = getGame();
        GlobalVariableManager.gameInsRef = gameInsRef;
        new Thread(this).start();
    }

    private AbstractGame getGame() {
        AbstractGame gameRef = null;
        switch (difficulty) {
            case 0:
                game = new EasyGame(this);
                break;
            case 1:
                game = new NormalGame(this);
                break;
            case 2:
                game = new HardGame(this);
                break;
            default:
                break;
        }
        gameRef = (AbstractGame) game;
        return gameRef;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        screenWidth = i1;
        screenHeight = i2;

        // update ImageManager simultaneously
        ImageManager.screenWidth = screenWidth;
        ImageManager.screenHeight = screenHeight;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        executorService.shutdown();
    }
}
