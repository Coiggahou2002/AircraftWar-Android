package com.example.myapplication.objects.aircraft;

import com.example.myapplication.game.multimedia.ImageManager;
import com.example.myapplication.objects.shoot.HeroScatteringShoot;
import com.example.myapplication.objects.shoot.HeroStraightShoot;
import com.example.myapplication.objects.shoot.IShootStrategy;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private volatile static HeroAircraft heroAircraft;

    public static HeroAircraft getInstance() {
        if(heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if(heroAircraft == null) {
                    heroAircraft = new HeroAircraft();
                }
            }
        }
        return heroAircraft;
    }

    private HeroAircraft() {
        setStraightShootStrategy();
        reborn();
    }

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    public void setProperty(int locationX, int locationY, int speedX, int speedY, int hp) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hp = hp;
        this.maxHp = 2 * hp;
        this.isValid = true;
    }

    /**
     * 用于游戏重开时初始化英雄机
     */
    public void reborn() {
        setProperty(
                ImageManager.screenWidth / 2,
                ImageManager.screenHeight - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 500
        );
    }

    /**
     *  英雄机由鼠标控制，不通过forward函数移动
     */
    @Override
    public void forward() {}

    /** 攻击方式 */
    private final IShootStrategy straightShootStrategy = new HeroStraightShoot();
    private final IShootStrategy scatteringShootStrategy = new HeroScatteringShoot();

    public void setStraightShootStrategy() {
        shootHandler.setShootStrategy(straightShootStrategy);
    }

    public void setScatteringShootStrategy() {
        shootHandler.setShootStrategy(scatteringShootStrategy);
    }
}
