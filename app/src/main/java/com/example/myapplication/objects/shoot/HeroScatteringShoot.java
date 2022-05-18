package com.example.myapplication.objects.shoot;

import com.example.myapplication.objects.aircraft.AbstractAircraft;
import com.example.myapplication.objects.bullet.BaseBullet;
import com.example.myapplication.objects.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄机散射策略
 * @author Wu Zekai
 */
public class HeroScatteringShoot implements IShootStrategy{

    private final int shootNum = 3;
    private final int power = 30;
    private final int direction = -1;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction*10;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y,  speedX + (i*2 - shootNum + 1), speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
