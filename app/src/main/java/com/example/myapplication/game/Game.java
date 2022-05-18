package com.example.myapplication.game;

import android.graphics.Canvas;

public interface Game {
    /** Run a game logical cycle */
    void step();

    /**
     * Paint current game status on canvas
     * @param canvas the canvas to paint on
     */
    void repaint(Canvas canvas);
}
