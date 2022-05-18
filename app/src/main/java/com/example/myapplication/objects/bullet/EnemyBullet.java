package com.example.myapplication.objects.bullet;

import com.example.myapplication.objects.props.BombTarget;

/**
 * @author hitsz
 */
public class EnemyBullet extends BaseBullet implements BombTarget {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
//        BombProps.addTarget(this);
    }

    @Override
    public void onBombActive() {
        this.vanish();
    }
}
