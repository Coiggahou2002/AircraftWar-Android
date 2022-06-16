package com.example.myapplication.game.multimedia;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.myapplication.application.GlobalVariableManager;

public class PaintHandler {
    private final Paint myPaint;

    public PaintHandler() {
        myPaint = new Paint();
        myPaint.setColor(Color.RED);
        myPaint.setStrokeWidth(30);
        myPaint.setTextSize(100);
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

    public void drawGameTexts(Canvas canvas, int score, int hp) {
        int x = 20, y1 = 120, y2 = 220, y3=320;
        canvas.drawText("分数: " + score, x, y1, myPaint);
        canvas.drawText("血量: " + hp, x, y2, myPaint);
        if (GlobalVariableManager.isOnlineMode) {
            canvas.drawText("对手分数: " + GlobalVariableManager.opponentScore, x, y3, myPaint);
        }
    }


}
