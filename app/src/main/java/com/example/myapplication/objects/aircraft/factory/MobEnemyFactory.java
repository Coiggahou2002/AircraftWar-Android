package com.example.myapplication.objects.aircraft.factory;

import com.example.myapplication.game.multimedia.ImageManager;
import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.aircraft.MobEnemy;

/**
 * 普通敌机工厂
 * @author Wu Zekai
 */
public class MobEnemyFactory implements IEnemyFactory{
    @Override
    public AbstractEnemyAircraft createEnemy() {
        return new MobEnemy(
                (int) (Math.random() * (ImageManager.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * ImageManager.screenHeight * 0.2),
                0,
                10,
                30
        );
    }

    @Override
    public AbstractEnemyAircraft createEnemy(int hp) {
        return new MobEnemy(
                (int) (Math.random() * (ImageManager.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * ImageManager.screenHeight * 0.2),
                0,
                10,
                hp
        );
    }
}
