package com.example.myapplication.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PaintHandler {
    private final Paint myPaint;

    public PaintHandler() {
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

        backgroundPosition += 0.002;
        if(backgroundPosition >= 1) {
            backgroundPosition -= 1;
        }
    }

    public void drawAtCenter(Canvas canvas, Bitmap bitmap, int x, int y) {
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect des = new Rect(x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2,
                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, src, des, myPaint);
    }

}
