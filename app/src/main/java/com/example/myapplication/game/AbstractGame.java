package com.example.myapplication.game;

import android.graphics.Canvas;

import com.example.myapplication.application.GameView;
import com.example.myapplication.objects.aircraft.HeroAircraft;

public abstract class AbstractGame implements Game {

    public final GameView view;

    private final PaintHandler myPaintHandler;

    public AbstractGame(GameView client) {
        view = client;

        setParameters();

        // image
        ImageManager.init(this);
        myPaintHandler = new PaintHandler();

        heroAircraft = HeroAircraft.getInstance();
    }

    private final HeroAircraft heroAircraft;

    abstract protected void setParameters();

    @Override
    public void step() {

    }

    @Override
    public void repaint(Canvas canvas) {
        myPaintHandler.drawBackground(canvas, ImageManager.BACKGROUND_IMAGE, view.screenWidth, view.screenHeight);
        paintObjectList();
        myPaintHandler.drawAt(canvas, heroAircraft.getImage(), (int)view.fingerX, (int)view.fingerY);
    }

    private void paintObjectList() {

    }


}
