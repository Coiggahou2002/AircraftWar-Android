package com.example.myapplication.objects.aircraft;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.game.AbstractGame;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft{

    private volatile static HeroAircraft heroAircraft;

    public static HeroAircraft getInstance(AbstractGame game) {
        if(heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if(heroAircraft == null) {
                    heroAircraft = new HeroAircraft(game);
                }
            }
        }
        return heroAircraft;
    }

    private HeroAircraft(AbstractGame game) {
        super(game);
        Log.i("test", "test");
//        setStraightShootStrategy();
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
                game.view.screenWidth / 2,
                game.view.screenHeight - getHeight() ,
                0, 0, 500
        );
    }

    @Override
    protected Bitmap initTexture() {
        Log.i("test", game.view.toString());
        return BitmapFactory.decodeResource(
                game.view.myResources,
                R.drawable.hero
        );
    }

    /**
     *  英雄机由鼠标控制，不通过forward函数移动
     */
    @Override
    public void forward() {}

//    /** 攻击方式 */
//    private final IShootStrategy straightShootStrategy = new HeroStraightShoot();
//    private final IShootStrategy scatteringShootStrategy = new HeroScatteringShoot();
//
//    public void setStraightShootStrategy() {
//        shootHandler.setShootStrategy(straightShootStrategy);
//    }
//
//    public void setScatteringShootStrategy() {
//        shootHandler.setShootStrategy(scatteringShootStrategy);
//    }
}
