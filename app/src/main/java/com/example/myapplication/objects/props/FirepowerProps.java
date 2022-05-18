package com.example.myapplication.objects.props;

import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.objects.aircraft.HeroAircraft;

/**
 * 火力道具
 * @author Wu Zekai
 */
public class FirepowerProps extends BaseProps {
    public FirepowerProps(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    private static final Object ACTIVATE_LOCK = new Object();
    private static Thread activateThread;

    @Override
    public void activate(AbstractGame game){
//        MusicManager.playMusic(MusicManager.MUSIC.SUPPLY);
        synchronized (ACTIVATE_LOCK) {
            if (activateThread != null && activateThread.isAlive()) {
                activateThread.interrupt();
            }
            activateThread = new Thread(this::tryActivate);
            activateThread.start();
        }
        this.vanish();
    }

    private void tryActivate() {
        HeroAircraft.getInstance().setScatteringShootStrategy();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return;
        }
        HeroAircraft.getInstance().setStraightShootStrategy();
    }
}
