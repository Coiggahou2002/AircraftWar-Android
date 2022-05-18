package com.example.myapplication.objects.aircraft;

import com.example.myapplication.objects.props.BombProps;
import com.example.myapplication.objects.props.BombTarget;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemyAircraft implements BombTarget {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        BombProps.addTarget(this);
    }

    @Override
    public void onBombActive() {
        this.vanish();
    }
}

