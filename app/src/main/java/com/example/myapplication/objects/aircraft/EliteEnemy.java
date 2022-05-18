package com.example.myapplication.objects.aircraft;

import com.example.myapplication.objects.props.BombProps;
import com.example.myapplication.objects.props.BombTarget;
import com.example.myapplication.objects.shoot.EnemyStraightShoot;

/**
 * 精英敌机
 * @author Wu Zekai
 */
public class EliteEnemy extends AbstractEnemyAircraft implements BombTarget {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootHandler.setShootStrategy(new EnemyStraightShoot());
        BombProps.addTarget(this);
    }

    @Override
    public void onBombActive() {
        this.vanish();
    }
}
