package com.example.myapplication.objects.aircraft.factory;

import com.example.myapplication.Utils;
import com.example.myapplication.game.ImageManager;
import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.aircraft.BossEnemy;

/**
 * Boss敌机工厂
 * @author Wu Zekai
 */
public class BossEnemyFactory implements IEnemyFactory{
    @Override
    public AbstractEnemyAircraft createEnemy() {
        return new BossEnemy(
                ImageManager.screenWidth - ImageManager.BOSS_ENEMY_IMAGE.getWidth(),
                (int) (ImageManager.screenHeight * 0.1),
                5 * Utils.getDirectionByRandom(),
                0,
                400);
    }

    @Override
    public AbstractEnemyAircraft createEnemy(int hp) {
        return new BossEnemy(
                ImageManager.screenWidth - ImageManager.BOSS_ENEMY_IMAGE.getWidth(),
                (int) (ImageManager.screenHeight * 0.1),
                5 * Utils.getDirectionByRandom(),
                0,
                hp);
    }
}
