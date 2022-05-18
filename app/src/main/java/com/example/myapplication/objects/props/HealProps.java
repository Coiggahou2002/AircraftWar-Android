package com.example.myapplication.objects.props;

import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.objects.aircraft.HeroAircraft;

/**
 * 治疗道具
 * @author Wu Zekai
 */
public class HealProps extends BaseProps {
    private final int healHp;

    public HealProps(int locationX, int locationY, int speedX, int speedY, int healHp) {
        super(locationX, locationY, speedX, speedY);
        this.healHp = healHp;
    }

    @Override
    public void activate(AbstractGame game) {
//        MusicManager.playMusic(MusicManager.MUSIC.SUPPLY);
        HeroAircraft.getInstance().increaseHp(healHp);
        this.vanish();
    }
}
