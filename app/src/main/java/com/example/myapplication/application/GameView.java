package com.example.myapplication.application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView
    implements SurfaceHolder.Callback, Runnable {

    // game setting parameters
    public int difficulty = 0;
    public boolean musicEnable = false;

    // game running parameters
    public float fingerX = 0, fingerY = 0;
    public int screenWidth = 400, screenHeight = 800;

    public GameView(Context context) {
        super(context);

        mySurfaceHolder = this.getHolder();
        mySurfaceHolder.addCallback(this);

        myPaint = new Paint();

        this.setFocusable(true);
    }

    // class process parameters
    private boolean gameValid = true;

    private final SurfaceHolder mySurfaceHolder;
    private final Paint myPaint;

    @Override
    public void run() {
        while(gameValid) {
            synchronized (mySurfaceHolder) {
                gamePeriod();
            }
            try {
                Thread.sleep(10);
            } catch (Exception ignored) {}
        }
    }

    private void gamePeriod() {
        Canvas canvas = mySurfaceHolder.lockCanvas();

        myPaint.setAntiAlias(true);

        myPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, screenWidth, screenHeight, myPaint);

        myPaint.setColor(Color.GREEN);
        canvas.drawRect(fingerX, fingerY, fingerX + 10, fingerY + 10, myPaint);

        mySurfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        screenWidth = i1;
        screenHeight = i2;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        gameValid = false;
    }
}
