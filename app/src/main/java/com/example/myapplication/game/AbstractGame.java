package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.myapplication.application.GameView;
import com.example.myapplication.objects.aircraft.HeroAircraft;

public abstract class AbstractGame implements Game {

    public final GameView view;

    protected final Bitmap background;
    private final ImageHandler myImageHandler;

    public AbstractGame(GameView client) {
        view = client;

        // image
        myImageHandler = new ImageHandler();
        background = initBackground();

        // fields
        heroAircraft = HeroAircraft.getInstance(this);

        setParameters();
    }

    abstract protected Bitmap initBackground();

    private final HeroAircraft heroAircraft;

    abstract protected void setParameters();

    @Override
    public void step() {

    }

    @Override
    public void repaint(Canvas canvas) {
        myImageHandler.drawBackground(canvas, background, view.screenWidth, view.screenHeight);
        paintObjectList();
        myImageHandler.drawAt(canvas, heroAircraft.texture, (int)view.fingerX, (int)view.fingerY);
    }

    private void paintObjectList() {

    }


}
