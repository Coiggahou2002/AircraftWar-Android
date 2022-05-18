package com.example.myapplication.objects.aircraft;

import com.example.myapplication.game.ImageManager;

/**
 * 抽象敌机 这一层实现了forward方法和vanish判定
 * @author Wu Zekai
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft {

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= ImageManager.screenHeight) {
            vanish();
        }
    }
}
