package com.example.myapplication.objects.props.factory;

import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.props.BaseProps;

/**
 * 道具工厂
 * @author Wu Zekai
 */
public interface IPropsFactory {
    /**
     * 工厂方法创建对象的函数
     * @param enemyAircraft 因死亡需要掉落道具的敌机对象
     * @return
     * 本次创建的Props对象
     */
    BaseProps createProps(AbstractEnemyAircraft enemyAircraft);
}