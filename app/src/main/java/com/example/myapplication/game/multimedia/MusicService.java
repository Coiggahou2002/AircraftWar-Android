package com.example.myapplication.game.multimedia;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import com.example.myapplication.Config;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class MusicService extends Service {

    public enum MusicName {
        BULLET,
        BULLET_HIT,
        SUPPLY,
        BOMB,
        GAME_OVER
    }

    private Map<MusicName, Integer> soundID = new HashMap<>();
    private SoundPool mySoundPool;
    private MediaPlayer normalBgmPlayer;
    private MediaPlayer bossBgmPlayer;

    private boolean enabled = true;

    public MusicService() {}

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(Config.MUSIC_INFO_TAG,"Create Music Service");

        mySoundPool = new SoundPool.Builder().setMaxStreams(5).build();
        soundID.put(MusicName.BULLET, mySoundPool.load(this, R.raw.bullet, 1));
        soundID.put(MusicName.BULLET_HIT, mySoundPool.load(this, R.raw.bullet_hit, 1));
        soundID.put(MusicName.SUPPLY, mySoundPool.load(this, R.raw.get_supply, 1));
        soundID.put(MusicName.BOMB, mySoundPool.load(this, R.raw.bomb_explosion, 1));
        soundID.put(MusicName.GAME_OVER, mySoundPool.load(this, R.raw.game_over, 1));

        normalBgmPlayer = MediaPlayer.create(this, R.raw.bgm);
        normalBgmPlayer.setLooping(true);

        bossBgmPlayer = MediaPlayer.create(this, R.raw.bgm_boss);
        bossBgmPlayer.setLooping(true);

        normalBgmPlayer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    public class MusicBinder extends Binder {

        public void setEnable(boolean enabled) {
            if (!enabled) {
                normalBgmPlayer.pause();
                bossBgmPlayer.pause();
            }
            MusicService.this.enabled = enabled;
        }

        public void playBossBgm() {
            if (enabled) {
                normalBgmPlayer.pause();
                bossBgmPlayer.start();
            }
        }

        public void playNormalBgm() {
            if (enabled) {
                bossBgmPlayer.pause();
                normalBgmPlayer.start();
            }
        }

        public void stopAll() {
            if (enabled) {
                stopMusic();
            }
        }

        public void play(MusicName name) {
            if (enabled) {
                Integer id = soundID.get(name);
                if(id != null) {
                    mySoundPool.play(id, 1, 1, 0, 0, 1);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    private void stopMusic() {
        if(normalBgmPlayer != null) {
            normalBgmPlayer.stop();
            normalBgmPlayer.reset();
            normalBgmPlayer.release();
            normalBgmPlayer = null;
        }

        if(bossBgmPlayer != null) {
            bossBgmPlayer.stop();
            bossBgmPlayer.reset();
            bossBgmPlayer.release();
            bossBgmPlayer = null;
        }
    }
}