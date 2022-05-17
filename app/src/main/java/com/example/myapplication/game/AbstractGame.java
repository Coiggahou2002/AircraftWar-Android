package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.myapplication.application.GameView;

public abstract class AbstractGame implements Game {

    protected final GameView view;

    protected final Bitmap background;
    private final ImageHandler myImageHandler;

    public AbstractGame(GameView client) {
        view = client;

        myImageHandler = new ImageHandler();
        background = initBackground();

        setParameters();
    }

    abstract protected Bitmap initBackground();

    abstract protected void setParameters();

    @Override
    public void step() {

    }

    @Override
    public void repaint(Canvas canvas) {
        myImageHandler.drawBackground(canvas, background, view.screenWidth, view.screenHeight);
        paintObjectList();
    }

    private void paintObjectList() {

    }


}
