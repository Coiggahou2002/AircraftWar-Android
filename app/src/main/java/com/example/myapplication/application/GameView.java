package com.example.myapplication.application;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.myapplication.game.EasyGame;
import com.example.myapplication.game.Game;
import com.example.myapplication.game.HardGame;
import com.example.myapplication.game.ImageManager;
import com.example.myapplication.game.NormalGame;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView
    implements SurfaceHolder.Callback, Runnable {

    // game setting parameters
    public int difficulty = 0;
    public boolean musicEnable = false;

    // game running parameters
    public float fingerX = 0, fingerY = 0;
    public int screenWidth = 400, screenHeight = 800;

    // class process parameters
    private Game game;

    public final Resources myResources;
    public final SurfaceHolder mySurfaceHolder;

    private final int gameInterval = 16;
    private final ScheduledExecutorService executorService;

    public GameView(Context context) {
        super(context);

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

    @Override
    public void run() {
        executorService.scheduleWithFixedDelay(
                this::gamePeriod,
                gameInterval,
                gameInterval,
                TimeUnit.MILLISECONDS
        );
    }

    private void gamePeriod() {
        game.step();
        synchronized (mySurfaceHolder) {
            Canvas canvas = mySurfaceHolder.lockCanvas();
            game.repaint(canvas);
            mySurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        getGame();
        new Thread(this).start();
    }

    private void getGame() {
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
        }
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
