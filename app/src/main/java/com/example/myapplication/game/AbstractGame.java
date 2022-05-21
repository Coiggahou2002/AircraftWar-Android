package com.example.myapplication.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.myapplication.Config;
import com.example.myapplication.Utils;
import com.example.myapplication.application.GameView;
import com.example.myapplication.objects.AbstractFlyingObject;
import com.example.myapplication.objects.aircraft.AbstractEnemyAircraft;
import com.example.myapplication.objects.aircraft.BossEnemy;
import com.example.myapplication.objects.aircraft.EliteEnemy;
import com.example.myapplication.objects.aircraft.HeroAircraft;
import com.example.myapplication.objects.aircraft.factory.BossEnemyFactory;
import com.example.myapplication.objects.aircraft.factory.EliteEnemyFactory;
import com.example.myapplication.objects.aircraft.factory.IEnemyFactory;
import com.example.myapplication.objects.aircraft.factory.MobEnemyFactory;
import com.example.myapplication.objects.bullet.BaseBullet;
import com.example.myapplication.objects.props.BaseProps;
import com.example.myapplication.objects.props.factory.BombPropsFactory;
import com.example.myapplication.objects.props.factory.FirepowerPropsFactory;
import com.example.myapplication.objects.props.factory.HealPropsFactory;
import com.example.myapplication.objects.props.factory.IPropsFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AbstractGame implements Game {

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    /**
     * 飞机列表
     */
    private final HeroAircraft heroAircraft;
    private final List<AbstractEnemyAircraft> enemyAircrafts = new LinkedList<>();
    private final List<BaseBullet> heroBullets = new LinkedList<>();
    private final List<BaseBullet> enemyBullets = new LinkedList<>();
    private final List<BaseProps> props = new LinkedList<>();

    /**
     * 游戏基本参数
     */
    private int score = 0;
    private int time = 0;
    protected int enemyMaxNumber = 5;
    protected int enemyScore = 10;

    /**
     * 难度变化参数
     */
    protected boolean difficultyChange = false;
    private int difficultyChangeCycleTime = 0;
    private int difficultyChangeDuration = 2000;
    private int defaultEnemyHp = 30;
    private double enemyHpRate = 1.0;
    private double enemySpawnRate = 1.0;
    protected double enemyHpRateBooster = 1.0;
    protected double enemySpawnRateBooster = 1.0;
    protected double eliteEnemyRateBooster = 1.0;

    /**
     * boss属性
     */
    protected boolean hasBoss = true;
    private final int bossBaseHp = 500;
    protected int bossIncreaseHp = 0;
    private int deadBossCount = 0;
    private final int bossScoreInterval = 100;
    private int nextBossScore = bossScoreInterval;
    private boolean onBossStage = false;

    /**
     * 指示子弹的发射频率
     */
    private int shootCycleTime = 0;
    protected int shootDuration = 600;

    /**
     * 指示敌机的产生频率
     */
    private int spawnCycleTime = 0;
    protected int spawnDuration = 600;

    /**
     * 每种敌机生成的概率(普通/精英)
     * 每种道具生成概率(治疗/炸弹/火力/不生成道具)
     */
    protected List<Double> enemyProbabilities = new ArrayList<>(Arrays.asList(0.5, 0.5));
    protected List<Double> propsProbabilities = new ArrayList<>(Arrays.asList(0.3, 0.3, 0.3, 0.1));

    /**
     * 敌机工厂及道具工厂
     */
    private final IEnemyFactory mobEnemyFactory = new MobEnemyFactory();
    private final IEnemyFactory eliteEnemyFactory = new EliteEnemyFactory();
    private final IEnemyFactory bossEnemyFactory = new BossEnemyFactory();
    private final IPropsFactory healPropsFactory = new HealPropsFactory();
    private final IPropsFactory bombPropsFactory = new BombPropsFactory();
    private final IPropsFactory firepowerPropsFactory = new FirepowerPropsFactory();

    public final GameView view;
    private final PaintHandler myPaintHandler;

    public AbstractGame(GameView client) {
        view = client;

        setParameters();

        // image
        ImageManager.init(this);
        myPaintHandler = new PaintHandler();

        // game component
        heroAircraft = HeroAircraft.getInstance();
        heroAircraft.reborn();
    }

    abstract protected void setParameters();

    @Override
    public void step() {
            time += timeInterval;

            shootAction();
            if (difficultyChange) {
                difficultyChangeAction();
            }
            enemyGenerateAction();
            if (hasBoss) {
                bossSpawnAction();
            }
            moveAction();
            crashCheckAction();
            postProcessAction();
            gameOverCheck();
    }


    private void shootAction() {
        shootCycleTime += timeInterval;
        if(shootCycleTime >= shootDuration) {
            shootCycleTime -= shootDuration;
            // 敌机射击
            for(AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
            // 英雄射击
            heroAircraft.setLocation((int)view.fingerX, (int)view.fingerY);
            heroBullets.addAll(heroAircraft.shoot());
        }
    }

    private void enemyGenerateAction() {
        spawnCycleTime += timeInterval;
        if(spawnCycleTime >= (spawnDuration / enemySpawnRate)) {
            spawnCycleTime -= spawnDuration;
            if(enemyAircrafts.size() < enemyMaxNumber) {
                switch (Utils.getByRandom(enemyProbabilities)) {
                    case 0:
                        enemyAircrafts.add(mobEnemyFactory.createEnemy((int)(defaultEnemyHp * enemyHpRate)));
                        break;
                    case 1:
                        enemyAircrafts.add(eliteEnemyFactory.createEnemy((int)(defaultEnemyHp * enemyHpRate)));
                        break;
                    default:
                        Log.e(Config.GAME_ERROR_TAG, "Enemy Generator Error!");
                }
            }
        }
    }

    private void difficultyChangeAction() {
        difficultyChangeCycleTime += timeInterval;
        if(difficultyChangeCycleTime >= difficultyChangeDuration) {
            difficultyChangeCycleTime -= difficultyChangeDuration;

            enemyHpRate *= enemyHpRateBooster;
            enemySpawnRate *= enemySpawnRateBooster;

            double val0 = enemyProbabilities.get(0);
            double val1 = enemyProbabilities.get(1);
            enemyProbabilities.set(1, val1 * eliteEnemyRateBooster);

            Log.i(Config.GAME_INFO_TAG, "难度变化！精英机概率："
                    + String.format("%.2f", val1 * eliteEnemyRateBooster / (val0 + val1))
                    + "敌机周期："
                    + String.format("%.2f", spawnDuration / enemySpawnRate)
                    + "敌机属性提升倍率："
                    + String.format("%.2f", enemyHpRate)
            );
        }
    }

    private void bossSpawnAction() {
        // boss死亡后再获得100分时生成新boss
        if(onBossStage) {
            nextBossScore = score + bossScoreInterval;
        }
        else if(score >= nextBossScore) {
            enemyAircrafts.add(bossEnemyFactory.createEnemy(
                    bossBaseHp + bossIncreaseHp * deadBossCount
            ));
            System.out.println("Boss生成");
//            MusicManager.playBossBgm();
            deadBossCount++;
            onBossStage = true;
        }
    }

    private void moveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
        for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
        for (BaseProps prop : props) {
            prop.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for(BaseBullet bullet : enemyBullets) {
            bullet.tryHit(heroAircraft);
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if(enemyAircraft.notValid()) {
                    continue;
                }
                bullet.tryHit(enemyAircraft);
                if (enemyAircraft.notValid()) {
                    // 获得分数，产生道具补给
                    score += enemyScore;
                    if (enemyAircraft instanceof EliteEnemy) {
                        propsGenerateAction(enemyAircraft);
                    }
                    // Boss阶段结束判定
                    if (enemyAircraft instanceof BossEnemy) {
//                        MusicManager.playNormalBgm();
                        propsGenerateAction(enemyAircraft);
                        onBossStage = false;
                    }
                }
            }
        }

        // 英雄机 与 敌机 相撞，均损毁
        for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.crash(heroAircraft)) {
                enemyAircraft.vanish();
                heroAircraft.decreaseHp(Integer.MAX_VALUE);
            }
        }

         // 我方获得道具，道具生效
        for(BaseProps prop : props) {
            if(prop.notValid()) {
                continue;
            }
            if(heroAircraft.crash(prop)) {
                prop.activate(this);
            }
        }
    }

    private void propsGenerateAction(AbstractEnemyAircraft enemyAircraft) {
        switch (Utils.getByRandom(propsProbabilities)) {
            case 0:
                props.add(healPropsFactory.createProps(enemyAircraft));
                break;
            case 1:
                props.add(bombPropsFactory.createProps(enemyAircraft));
                break;
            case 2:
                props.add(firepowerPropsFactory.createProps(enemyAircraft));
                break;
            case 3:
                Log.v(Config.GAME_VERBOSE_TAG, "No Props Generated");
                break;
            default:
                Log.e(Config.GAME_ERROR_TAG, "Props Generator Error!");
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 游戏结束检查，若结束：
     * 1. 停止游戏
     * 2. 处理音效
     * 3. 通知Main线程
     */
    private void gameOverCheck() {
        if (heroAircraft.getHp() <= 0) {
//            MusicManager.stop();
//            MusicManager.playMusic(MusicManager.MUSIC.GAME_OVER);
            view.onGameStop(score);
        }
    }


    @Override
    public void repaint(Canvas canvas) {
        myPaintHandler.drawBackground(canvas, ImageManager.BACKGROUND_IMAGE, view.screenWidth, view.screenHeight);

        paintObjectLists(canvas, enemyBullets);
        paintObjectLists(canvas, heroBullets);
        paintObjectLists(canvas, enemyAircrafts);
        paintObjectLists(canvas, props);

        myPaintHandler.drawAtCenter(canvas, heroAircraft.getImage(), (int)view.fingerX, (int)view.fingerY);
        myPaintHandler.drawGameTexts(canvas, score, heroAircraft.getHp());
    }

    private void paintObjectLists(Canvas canvas, List<? extends AbstractFlyingObject> objects) {
        for (AbstractFlyingObject object : objects) {
            myPaintHandler.drawAtCenter(canvas, object.getImage(), object.getLocationX(), object.getLocationY());
        }
    }

    public void addScore(int enemyCount) {
        score += enemyScore * enemyCount;
    }

}
