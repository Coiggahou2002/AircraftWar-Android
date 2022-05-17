package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    int count = 0;
    public float x = 50, y = 50;
    int screenWidth = 480, screenHeight = 800;
    boolean mbLoop = false;
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;
    private Paint mPaint;
    private Image bgImg;
    public GameSurfaceView(Context context) {
        super(context);
        mbLoop = true;
        mPaint = new Paint();
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
    }

    public void draw(){
        //通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }
        if(count < 100){
            count ++;
        }else {
            count = 0;
        }
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        //绘制一个全屏大小的矩形

        canvas.drawRect(0,0,screenWidth, screenHeight, mPaint);
        switch (count % 4){
            case 0:
                mPaint.setColor(Color.BLUE);
                break;
            case 1:
                mPaint.setColor(Color.GREEN);
                break;
            case 2:
                mPaint.setColor(Color.RED);
                break;
            case 3:
                mPaint.setColor(Color.YELLOW);
                break;
            default:
                mPaint.setColor(Color.WHITE);
        }
        //绘制一个圆形
        canvas.drawCircle(x,y,50,mPaint);
        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);
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
        mbLoop = false;
    }

    @Override
    public void run() {
        while (mbLoop) {
            synchronized (mSurfaceHolder) {
                draw();
            }
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }
        }
    }
}
