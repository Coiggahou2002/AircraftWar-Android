package com.example.myapplication.objects.aircraft;

import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.objects.AbstractFlyingObject;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(AbstractGame game) {
        super(game);
    }

    public AbstractAircraft(
            AbstractGame game, int locationX, int locationY, int speedX, int speedY, int hp) {
        super(game, locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int increase) {
        hp += increase;
        if(hp > maxHp) {
            hp = maxHp;
        }
    }

    public int getHp() {
        return hp;
    }

//    /**
//     *  攻击方式 默认为空射
//     */
//    protected final ShootHandler shootHandler = new ShootHandler(new EmptyShoot());
//
//    /**
//     * 飞机射击方法
//     */
//    public List<BaseBullet> shoot() {
//        return shootHandler.shoot(this);
//    }

}



