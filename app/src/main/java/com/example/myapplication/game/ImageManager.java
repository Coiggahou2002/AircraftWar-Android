package com.example.myapplication.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;
import com.example.myapplication.objects.aircraft.BossEnemy;
import com.example.myapplication.objects.aircraft.EliteEnemy;
import com.example.myapplication.objects.aircraft.HeroAircraft;
import com.example.myapplication.objects.aircraft.MobEnemy;
import com.example.myapplication.objects.bullet.EnemyBullet;
import com.example.myapplication.objects.bullet.HeroBullet;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND_IMAGE;
    public static Bitmap BACKGROUND_IMAGE_EASY;
    public static Bitmap BACKGROUND_IMAGE_NORMAL;
    public static Bitmap BACKGROUND_IMAGE_HARD;

    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap HEAL_PROPS_IMAGE;
    public static Bitmap BOMB_PROPS_IMAGE;
    public static Bitmap FIREPOWER_PROPS_IMAGE;

    public static int screenWidth = 400, screenHeight = 800;

    public static void init(AbstractGame game) {

        Resources r = game.view.myResources;

        BACKGROUND_IMAGE_EASY = BitmapFactory.decodeResource(r, R.drawable.background_easy);
        BACKGROUND_IMAGE_NORMAL = BitmapFactory.decodeResource(r, R.drawable.background_normal);
        BACKGROUND_IMAGE_HARD = BitmapFactory.decodeResource(r, R.drawable.background_hard);

        HERO_IMAGE = BitmapFactory.decodeResource(r, R.drawable.hero);
        HERO_BULLET_IMAGE = BitmapFactory.decodeResource(r, R.drawable.bullet_hero);
        ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(r, R.drawable.bullet_enemy);
        MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(r, R.drawable.mob);
        ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(r, R.drawable.elite);
        BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(r, R.drawable.boss);
        HEAL_PROPS_IMAGE = BitmapFactory.decodeResource(r, R.drawable.prop_blood);
        BOMB_PROPS_IMAGE = BitmapFactory.decodeResource(r, R.drawable.prop_bomb);
        FIREPOWER_PROPS_IMAGE = BitmapFactory.decodeResource(r, R.drawable.prop_bullet);

        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
//        CLASSNAME_IMAGE_MAP.put(HealProps.class.getName(), HEAL_PROPS_IMAGE);
//        CLASSNAME_IMAGE_MAP.put(BombProps.class.getName(), BOMB_PROPS_IMAGE);
//        CLASSNAME_IMAGE_MAP.put(FirepowerProps.class.getName(), FIREPOWER_PROPS_IMAGE);

        if(game.view.difficulty == 0) {
            BACKGROUND_IMAGE = BACKGROUND_IMAGE_EASY;
        }
        else if(game.view.difficulty == 1) {
            BACKGROUND_IMAGE = BACKGROUND_IMAGE_NORMAL;
        }
        else {
            BACKGROUND_IMAGE = BACKGROUND_IMAGE_HARD;
        }
    }

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }
}
