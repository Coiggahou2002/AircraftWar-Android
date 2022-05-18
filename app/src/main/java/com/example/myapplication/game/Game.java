package com.example.myapplication.game;

import android.graphics.Canvas;

public interface Game {
    /** Run game logical cycle */
    void start();

    /**
     * Paint current game status on canvas
     * @param canvas the canvas to paint on
     */
    void repaint(Canvas canvas);
}
