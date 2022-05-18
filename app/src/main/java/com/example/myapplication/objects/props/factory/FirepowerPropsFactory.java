package com.example.myapplication.objects.props.factory;

import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.props.BaseProps;
import com.example.myapplication.objects.props.FirepowerProps;

/**
 * 火力道具工厂
 * @author Wu Zekai
 */
public class FirepowerPropsFactory implements IPropsFactory{
    @Override
    public BaseProps createProps(AbstractEnemyAircraft enemyAircraft) {
        return new FirepowerProps(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                0,
                10
        );
    }
}
