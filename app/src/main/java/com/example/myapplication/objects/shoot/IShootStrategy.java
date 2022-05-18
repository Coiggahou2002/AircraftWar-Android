package com.example.myapplication.objects.shoot;

import com.example.myapplication.objects.aircraft.AbstractAircraft;
import com.example.myapplication.objects.bullet.BaseBullet;

import java.util.List;

/**
 * @author Wu Zekai
 */
public interface IShootStrategy {
    /**
     * 射击逻辑
     * @param aircraft 射击者
     * @return 子弹列表
     */
    List<BaseBullet> shoot(AbstractAircraft aircraft);
}