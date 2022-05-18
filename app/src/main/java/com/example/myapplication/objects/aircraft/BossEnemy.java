package com.example.myapplication.objects.aircraft;

/**
 * Boss敌机
 * @author Wu Zekai
 */
public class BossEnemy extends AbstractEnemyAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
//        shootHandler.setShootStrategy(new EnemyScatteringShoot());
    }
}
