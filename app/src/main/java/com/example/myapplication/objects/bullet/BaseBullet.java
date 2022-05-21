package com.example.myapplication.objects.bullet;

import com.example.myapplication.game.multimedia.ImageManager;
import com.example.myapplication.objects.AbstractFlyingObject;
import com.example.myapplication.objects.aircraft.AbstractAircraft;

/**
 * 子弹类。
 * 也可以考虑不同类型的子弹
 *
 * @author hitsz
 */
public class BaseBullet extends AbstractFlyingObject {

    private final int power;

    public BaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= ImageManager.screenWidth) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= ImageManager.screenHeight ) {
            // 向下飞行出界
            vanish();
        } else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public void tryHit(AbstractAircraft aircraft) {
        if(!notValid() && !aircraft.notValid() && crash(aircraft)) {
            aircraft.decreaseHp(power);
            vanish();
        }
    }

    public int getPower() {
        return power;
    }
}
