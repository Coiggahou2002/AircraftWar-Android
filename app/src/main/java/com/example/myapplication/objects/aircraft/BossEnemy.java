package com.example.myapplication.objects.aircraft;

import com.example.myapplication.objects.shoot.EnemyScatteringShoot;

/**
 * Boss敌机
 * @author Wu Zekai
 */
public class BossEnemy extends AbstractEnemyAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootHandler.setShootStrategy(new EnemyScatteringShoot());
    }
}
