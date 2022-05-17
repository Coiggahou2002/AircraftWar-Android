package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;
import com.example.myapplication.application.GameView;

public class HardGame extends AbstractGame{
    public HardGame(GameView client) {
        super(client);
    }

    @Override
    protected Bitmap initBackground() {
        return BitmapFactory.decodeResource(
                view.myResources,
                R.drawable.background_hard
        );
    }

    @Override
    protected void setParameters() {

    }
}
