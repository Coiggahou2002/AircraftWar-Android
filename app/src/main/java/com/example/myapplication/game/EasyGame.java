package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;
import com.example.myapplication.application.GameView;

public class EasyGame extends AbstractGame{
    public EasyGame(GameView client) {
        super(client);
    }

    @Override
    protected Bitmap initBackground() {
        return BitmapFactory.decodeResource(
                view.myResources,
                R.drawable.background_easy
                );
    }

    @Override
    protected void setParameters() {
    }
}
