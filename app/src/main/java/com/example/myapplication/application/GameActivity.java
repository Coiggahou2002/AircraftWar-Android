package com.example.myapplication.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.myapplication.Config;
import com.example.myapplication.game.AbstractGame;
import com.example.myapplication.game.multimedia.MusicService;
import com.example.myapplication.network.ApiRepository;
import com.example.myapplication.network.ApiResponse;
import com.example.myapplication.network.ApiResponseStatus;
import com.example.myapplication.network.GameHandler;
import com.example.myapplication.network.GameRoomDTO;
import com.example.myapplication.network.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Time;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GameActivity extends AppCompatActivity {

    private Intent currentIntent;
    private GameView gameView;

    int difficulty;
    int online;
    String username;
    GameHandler networkHandler;

    private boolean musicEnabled;
    public MusicService.MusicBinder myMusicBinder;
    private MusicConnect myMusicConnect;

    private Intent standingIntent;
    private Retrofit retrofit;
    private ApiRepository apiRepository;
    private boolean firstTouched = false;

    public void invokeOnlineSync(AbstractGame gameRef) {
        if (GlobalVariableManager.isOnlineMode) {
            // 游戏开始，开始轮询，同时发送自己分数

            GlobalVariableManager.onlineGameStatus = GlobalVariableManager.GAME_STATUS_PLAYING;

            Log.d("shit", "score : " + String.valueOf(gameRef.getScore()));

            GlobalVariableManager.myScore = gameRef.getScore();


            Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                    .flatMap(new Function<Long, ObservableSource<ApiResponse>>() {
                        @Override
                        public ObservableSource<ApiResponse> apply(Long aLong) throws Exception {
                            return apiRepository.updateGameInfoById(
                                    GlobalVariableManager.onlineRoomId,
                                    GlobalVariableManager.userId,
                                    gameRef.getScore()
                            );
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiResponse resp) {
                            // 每次接受最新同步状态，更新画面显示对手分数（自己的根据本地来）

                            Toast.makeText(GameActivity.this, "onNext exec", Toast.LENGTH_SHORT).show();
                            Integer code = resp.getCode();

                            Log.d("shit", resp.getData().toString());
                            Log.d("shit", String.valueOf(gameRef.getScore()));

                            if (code == ApiResponseStatus.SUCCESS.getCode()) {
                                GameRoomDTO gameRoomInfo = new Gson().fromJson(
                                        new Gson().toJson(resp.getData()),
                                        new TypeToken<GameRoomDTO>(){}.getType()
                                );
                                // 判断一下自己是A还是B，取对手分数付给全局opponentScore，画布会自动更新
                                if (Objects.equals(GlobalVariableManager.userId, gameRoomInfo.getPlayerIdA())) {
                                    GlobalVariableManager.opponentScore = gameRoomInfo.getPlayerScoreB();
                                } else if (Objects.equals(GlobalVariableManager.userId, gameRoomInfo.getPlayerIdB())) {
                                    GlobalVariableManager.opponentScore = gameRoomInfo.getPlayerScoreA();
                                } else {
                                    Toast.makeText(GameActivity.this, "分数实时同步出现未知错误Objects例外出现", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(GameActivity.this, "分数实时同步出现未知错误-返回码不正常", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                            Toast.makeText(GameActivity.this, "分数实时同步出现网络错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentIntent = getIntent();
        setViews();

        retrofit = RetrofitManager.getRetrofitInstance();
        apiRepository = RetrofitManager.getApiServiceRepo();

        difficulty = currentIntent.getIntExtra(Config.DIFFICULTY, 0);
        online = currentIntent.getIntExtra(Config.ONLINE, 0);
        username = getIntent().getStringExtra(Config.USERNAME);
        networkHandler = (GameHandler) currentIntent.getSerializableExtra(Config.NETWORK_HANDLER);

        musicEnabled = currentIntent.getBooleanExtra(Config.MUSIC_ENABLE, true);
        myMusicConnect = new MusicConnect();
        bindService(new Intent(this, MusicService.class),
                myMusicConnect,
                Context.BIND_AUTO_CREATE
        );

        standingIntent = new Intent(GameActivity.this, StandingActivity.class);
        standingIntent.putExtra(Config.DIFFICULTY, difficulty);
        standingIntent.putExtra(Config.ONLINE, online);
        standingIntent.putExtra(Config.USERNAME, username);
        standingIntent.putExtra(Config.NETWORK_HANDLER, networkHandler);



    }

    private void setViews() {
        gameView = new GameView(this);
        gameView.difficulty = currentIntent.getIntExtra(Config.DIFFICULTY, 0);
        Log.i(Config.GAME_ACTIVITY_INFO_TAG, "" + gameView.difficulty);
        setContentView(gameView);
        GlobalVariableManager.gameInsRef = (AbstractGame) gameView.game;
    }

    class MusicConnect implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(Config.MUSIC_INFO_TAG, "Connect Music");
            myMusicBinder = (MusicService.MusicBinder) service;
            myMusicBinder.setEnable(musicEnabled);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    }

    protected void onGameStop(int score) {
        if (online == 1) {
            Log.d("shit", "gameover-online");
            // TODO: 此处回头加个GUI文本提示
            standingIntent.putExtra(
                    Config.OPPONENT_SCORE,
                    GlobalVariableManager.opponentScore
            );
        }
        else {
//            networkHandler.onNormalGameOver(username, score, difficulty);
            Log.d("shit", "gameover-single");
        }
        standingIntent.putExtra(Config.SCORE, score);
        startActivity(standingIntent);
        finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!firstTouched) {
            invokeOnlineSync(GlobalVariableManager.gameInsRef);
            firstTouched = true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE ||
            event.getAction() == MotionEvent.ACTION_DOWN) {
            gameView.fingerX = event.getX();
            gameView.fingerY = event.getY();
        }
        Log.v(Config.GAME_ACTIVITY_INFO_TAG, gameView.fingerX + " " + gameView.fingerY);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(myMusicConnect);
    }
}