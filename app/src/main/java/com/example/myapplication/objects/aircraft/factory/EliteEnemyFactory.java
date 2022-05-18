package com.example.myapplication.objects.aircraft.factory;

import com.example.myapplication.Utils;
import com.example.myapplication.game.ImageManager;
import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.aircraft.EliteEnemy;

/**
 * 精英敌机工厂
 * @author Wu Zekai
 */
public class EliteEnemyFactory implements IEnemyFactory{
    @Override
    public AbstractEnemyAircraft createEnemy() {
        return new EliteEnemy(
                (int) (Math.random() * (ImageManager.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * ImageManager.screenHeight * 0.2),
                5 * Utils.getDirectionByRandom(),
                10,
                30
        );
    }

    @Override
    public AbstractEnemyAircraft createEnemy(int hp) {
        return new EliteEnemy(
                (int) (Math.random() * (ImageManager.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * ImageManager.screenHeight * 0.2),
                5 * Utils.getDirectionByRandom(),
                10,
                hp
        );
    }
}
