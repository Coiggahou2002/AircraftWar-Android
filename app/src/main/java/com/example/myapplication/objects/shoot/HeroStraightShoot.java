package com.example.myapplication.objects.shoot;

import com.example.myapplication.objects.aircraft.AbstractAircraft;
import com.example.myapplication.objects.bullet.BaseBullet;
import com.example.myapplication.objects.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄机直射策略
 * @author Wu Zekai
 */
public class HeroStraightShoot implements IShootStrategy{

    private final int power = 30;
    private final int direction = -1;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction*16;

        res.add(new HeroBullet(x, y, speedX, speedY, power));
        return res;
    }
}

