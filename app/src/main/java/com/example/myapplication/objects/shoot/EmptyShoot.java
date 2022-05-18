package com.example.myapplication.objects.shoot;

import com.example.myapplication.objects.aircraft.AbstractAircraft;
import com.example.myapplication.objects.bullet.BaseBullet;

import java.util.Collections;
import java.util.List;

/**
 * 空射策略
 * @author Wu Zekai
 */
public class EmptyShoot implements IShootStrategy{

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        // 注意，此处使用该方法，比直接new一个List实现类更高效
        return Collections.emptyList();
    }
}
