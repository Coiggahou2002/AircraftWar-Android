package com.example.myapplication.objects.aircraft.factory;

import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;

/**
 * 敌机工厂接口
 * @author Wu Zekai
 */
public interface IEnemyFactory {
    /**
     * 工厂方法创建对象的函数
     * @return
     * 本次创建的Enemy对象
     */
    AbstractEnemyAircraft createEnemy();

    /**
     * 以指定hp创建对象
     * @param hp 血量
     * @return 本次创建的敌机对象
     */
    AbstractEnemyAircraft createEnemy(int hp);
}

