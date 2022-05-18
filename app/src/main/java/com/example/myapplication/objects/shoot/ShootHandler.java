package com.example.myapplication.objects.shoot;

import com.example.myapplication.objects.aircraft.AbstractAircraft;
import com.example.myapplication.objects.bullet.BaseBullet;

import java.util.List;

/**
 * 射击策略管理者
 * @author Wu Zekai
 */
public class ShootHandler {
    private IShootStrategy shootStrategy;

    public ShootHandler(IShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public void setShootStrategy(IShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        return shootStrategy.shoot(aircraft);
    }
}
