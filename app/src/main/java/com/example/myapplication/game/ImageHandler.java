package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class ImageHandler {
    private final Paint myPaint;

    public ImageHandler() {
        myPaint = new Paint();

    }

    private double backgroundPosition = 0;

    public void drawBackground(Canvas canvas, Bitmap background, int areaWidth, int areaHeight) {
        Rect src = new Rect(0, 0, background.getWidth(), background.getHeight());
        Rect des1 = new Rect(0, (int)(areaHeight * (backgroundPosition - 1)),
                areaWidth, (int)(areaHeight * backgroundPosition));
        Rect des2 = new Rect(0, (int)(areaHeight * backgroundPosition),
                areaWidth, (int)(areaHeight * (backgroundPosition + 1)));

        canvas.drawBitmap(background, src, des1, myPaint);
        canvas.drawBitmap(background, src, des2, myPaint);

        backgroundPosition += 0.01;
        if(backgroundPosition >= 1) {
            backgroundPosition -= 1;
        }
    }

    public void drawAt(Canvas canvas, Bitmap bitmap, int x, int y) {

    }

}
