package com.example.myapplication.objects.props.factory;

import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.props.BaseProps;
import com.example.myapplication.objects.props.BombProps;

/**
 * 炸弹道具工厂
 * @author Wu Zekai
 */
public class BombPropsFactory implements IPropsFactory{
    @Override
    public BaseProps createProps(AbstractEnemyAircraft enemyAircraft) {
        return new BombProps(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                0,
                10
        );
    }
}
