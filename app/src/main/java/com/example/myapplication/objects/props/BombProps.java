package com.example.myapplication.objects.props;

import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.game.multimedia.MusicService;
import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;

import java.util.LinkedList;
import java.util.List;

/**
 * 炸弹道具
 * @author Wu Zekai
 */
public class BombProps extends BaseProps {
    public BombProps(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    private final static List<BombTarget> BOMB_TARGETS = new LinkedList<>();

    public static void addTarget(BombTarget target) {
        BOMB_TARGETS.add(target);
    }

    @Override
    public void activate(AbstractGame game) {
        this.vanish();

        int enemyCount = 0;
        for(BombTarget target : BOMB_TARGETS) {
            if(target != null && !target.notValid()) {
                target.onBombActive();
                if(target instanceof AbstractEnemyAircraft) {
                    enemyCount++;
                }
            }
        }
        BOMB_TARGETS.clear();
        game.addScore(enemyCount);
    }
}
