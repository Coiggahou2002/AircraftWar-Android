package com.example.myapplication.objects.props.factory;

import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.props.BaseProps;
import com.example.myapplication.objects.props.HealProps;

/**
 * 治疗道具工厂
 * @author Wu Zekai
 */
public class HealPropsFactory implements IPropsFactory{
    @Override
    public BaseProps createProps(AbstractEnemyAircraft enemyAircraft) {
        return new HealProps(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                0,
                10,
                50
        );
    }
}
