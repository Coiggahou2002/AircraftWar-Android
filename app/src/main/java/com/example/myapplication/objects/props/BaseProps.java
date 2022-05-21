package com.example.myapplication.objects.props;

import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.game.multimedia.ImageManager;
import com.example.myapplication.objects.AbstractFlyingObject;

/**
 * @author Wu Zekai
 */
public abstract class BaseProps extends AbstractFlyingObject {
    public BaseProps(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= ImageManager.screenHeight) {
            vanish();
        }
    }

    /**
     * 道具被激活的行为
     * @param game 游戏类的引用，作为事件的信息
     */
    abstract public void activate(AbstractGame game);
}