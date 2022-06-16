package com.example.myapplication.application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.myapplication.Config;
import com.example.myapplication.R;
import com.example.myapplication.network.ApiRepository;
import com.example.myapplication.network.ApiResponse;
import com.example.myapplication.network.ApiResponseStatus;
import com.example.myapplication.network.GameHandler;
import com.example.myapplication.network.GameHandlerNaiveImpl;
import com.example.myapplication.network.GameProp;
import com.example.myapplication.network.GameRoomDTO;
import com.example.myapplication.network.RetrofitManager;
import com.example.myapplication.network.UserInfoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class StartActivity extends AppCompatActivity {

    Intent gameIntent;
    GameHandler networkHandler;

    String username;

    Button shopButton, infoButton, easyButton, normalButton, hardButton, onlineButton, createRoomButton;
    CheckBox musicCheckBox;

    AlertDialog chooseGameRoomDialog;
    AlertDialog waitingForMatchDialog;
    AlertDialog userInfoDialog;
    AlertDialog shopDialog;
    ApiRepository apiRepository;
    Retrofit retrofit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkHandler = new GameHandlerNaiveImpl();

        username = getIntent().getStringExtra(Config.USERNAME);

        gameIntent = new Intent(StartActivity.this, GameActivity.class);
        gameIntent.putExtra(Config.USERNAME, username);

        retrofit = RetrofitManager.getRetrofitInstance();
        apiRepository = RetrofitManager.getApiServiceRepo();
        setViews();
        createListeners();
    }

    private void setViews() {
        setContentView(R.layout.start_menu);
        shopButton = findViewById(R.id.shop_button);
        infoButton = findViewById(R.id.info_button);
        easyButton = findViewById(R.id.easy_button);
        normalButton = findViewById(R.id.normal_button);
        hardButton = findViewById(R.id.hard_button);
        onlineButton = findViewById(R.id.online_button);
        createRoomButton = findViewById(R.id.create_room_button);
        musicCheckBox = findViewById(R.id.music_check);
    }

    private void createListeners() {
        shopButton.setOnClickListener(view -> {
            apiRepository.getAllProps()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiResponse resp) {
                            Integer code = resp.getCode();
                            if (code == ApiResponseStatus.SUCCESS.getCode()) {
                                List<GameProp> propList = new Gson().fromJson(
                                        new Gson().toJson(resp.getData()),
                                        new TypeToken<List<GameProp>>(){}.getType()
                                );
                                GlobalVariableManager.propList = propList;
                                String[] propStringList = new String[propList.size()];
                                for (int i = 0; i < propStringList.length; i++) {
                                    GameProp prop = propList.get(i);
                                    propStringList[i] = prop.getName() + ", " + prop.getWorth() + "金币/个, " + "剩余库存 " + prop.getStock() + "件";
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                                builder.setTitle("道具商城");
                                builder.setSingleChoiceItems(propStringList, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        GlobalVariableManager.chosenPropIdx = i;
                                    }
                                });
                                builder.setPositiveButton("购买", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        apiRepository.buyProp(GlobalVariableManager.userId, GlobalVariableManager.propList.get(GlobalVariableManager.chosenPropIdx).getId()
                                                )
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ApiResponse>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(ApiResponse apiResponse) {
                                                        Integer respCode = apiResponse.getCode();
                                                        if (respCode == ApiResponseStatus.SUCCESS.getCode()) {
                                                            Toast.makeText(StartActivity.this, "购买成功", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(StartActivity.this, "购买失败: " + apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Toast.makeText(StartActivity.this, "网络错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    }
                                });
                                shopDialog = builder.create();
                                shopDialog.show();
                            } else {

                                Toast.makeText(StartActivity.this, "网络错误" + code, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(StartActivity.this, "网络错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });
        infoButton.setOnClickListener(view -> {
            apiRepository.getUserInfo(GlobalVariableManager.userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiResponse value) {
                            Integer code = value.getCode();
                            if (code == ApiResponseStatus.SUCCESS.getCode()) {
                                UserInfoDTO userInfoDTO = new Gson().fromJson(
                                        new Gson().toJson(value.getData()),
                                        new TypeToken<UserInfoDTO>(){}.getType()
                                );
                                GlobalVariableManager.userId = userInfoDTO.getUserId();
                                GlobalVariableManager.coin = userInfoDTO.getCoin();
                                GlobalVariableManager.username = userInfoDTO.getUsername();
                                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                                builder.setTitle("用户信息");
                                builder.setMessage("用户ID: " + GlobalVariableManager.userId + "\n"
                                        + "用户名: " + GlobalVariableManager.username + "\n"
                                        + "金币: " + GlobalVariableManager.coin
                                );
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                userInfoDialog = builder.create();
                                userInfoDialog.show();
                            } else {
                                Toast.makeText(StartActivity.this, "获取用户信息错误" + value.getCode() + value.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(StartActivity.this, "网络错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });
        easyButton.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Easy Mode Selected");
            gameIntent.putExtra(Config.DIFFICULTY, 0);
            onQuit();
        });

        normalButton.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Normal Mode Selected");
            gameIntent.putExtra(Config.DIFFICULTY, 1);
            onQuit();
        });

        hardButton.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Hard Mode Selected");
            gameIntent.putExtra(Config.DIFFICULTY, 2);
            onQuit();
        });

        onlineButton.setOnClickListener(view -> {
            apiRepository.getPendingRooms()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(ApiResponse apiResponse) {
                            Integer code = apiResponse.getCode();
                            String gameRoomListJson = new Gson().toJson(apiResponse.getData());
                            GlobalVariableManager.gameRoomList = new Gson().fromJson(gameRoomListJson, new TypeToken<List<GameRoomDTO>>() {
                            }.getType());
                            GlobalVariableManager.chosenRoomIdx = 0;
                            if (code == ApiResponseStatus.SUCCESS.getCode()) {
                                String[] gameRoomStringList = new String[GlobalVariableManager.gameRoomList.size()];
                                for (int i = 0; i < gameRoomStringList.length; i++) {
                                    GameRoomDTO gameRoom = GlobalVariableManager.gameRoomList.get(i);
                                    gameRoomStringList[i] = "(等待中)" + "[" + gameRoom.getPlayerNameA() + "创建的房间]" + gameRoom.getGameRoomId();
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                                builder.setTitle("选择一个游戏房间");
                                builder.setSingleChoiceItems(gameRoomStringList, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        GlobalVariableManager.chosenRoomIdx = i;
                                        Toast.makeText(StartActivity.this, gameRoomStringList[i], Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setPositiveButton("加入", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        GlobalVariableManager.onlineRoomId = GlobalVariableManager.gameRoomList.get(GlobalVariableManager.chosenRoomIdx).getGameRoomId();
                                        apiRepository.joinGameRoom(GlobalVariableManager.onlineRoomId, GlobalVariableManager.userId)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ApiResponse>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(ApiResponse response) {
                                                        Integer respCode = response.getCode();
                                                        if (respCode == ApiResponseStatus.GAME_ROOM_READY.getCode()) {
                                                            // 开始游戏
                                                            Toast.makeText(StartActivity.this, "加入房间成功，游戏开始", Toast.LENGTH_LONG).show();
                                                            GlobalVariableManager.isOnlineMode = true;
                                                            onQuit();
                                                        } else {
                                                            Toast.makeText(StartActivity.this, "服务端发生错误，开始游戏失败", Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Toast.makeText(StartActivity.this, "网络错误" + e.getMessage(), Toast.LENGTH_LONG).show();
                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                chooseGameRoomDialog = builder.create();
                                chooseGameRoomDialog.show();
                            } else {
                                Toast.makeText(StartActivity.this, "服务端错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(StartActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });

        createRoomButton.setOnClickListener(view -> {
            apiRepository.createNewRoom(GlobalVariableManager.userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiResponse apiResponse) {
                            Integer code = apiResponse.getCode();
                            if (code == ApiResponseStatus.SUCCESS.getCode()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                                String gameRoomId = apiResponse.getData().toString();
                                builder.setTitle("等待匹配中");
                                builder.setCancelable(false);
//                                builder.setPositiveButton("开始游戏", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                    }
//                                });
                                builder.setMessage("房间 [" + gameRoomId + "] 已创建，正在匹配对手");
                                waitingForMatchDialog = builder.create();
                                waitingForMatchDialog.show();
                                // 是否匹配到对手，轮询结束标志
                                final boolean[] hasFoundOpponent = {false};
                                apiRepository.getRoomInfoById(gameRoomId)
                                        .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                                            @Override
                                            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                                                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                                                    @Override
                                                    public ObservableSource<?> apply(Object o) throws Exception {
                                                        if (hasFoundOpponent[0]) {
                                                            return Observable.error(new Throwable("轮询结束"));
                                                        } else {
                                                            // 2s轮询一次
                                                            return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                                                        }
                                                    }
                                                });
                                            }
                                        })
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<ApiResponse>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(ApiResponse response) {
                                                GameRoomDTO gameRoomInfo = new Gson().fromJson(
                                                        new Gson().toJson(response.getData()),
                                                        new TypeToken<GameRoomDTO>(){}.getType()
                                                );
                                                Toast.makeText(StartActivity.this, "发轮询了" + response.getCode().toString(), Toast.LENGTH_SHORT).show();
                                                String playerIdB = gameRoomInfo.getPlayerIdB();
                                                if (playerIdB != null) {
                                                    hasFoundOpponent[0] = true;
                                                    Toast.makeText(StartActivity.this, "找到对手，游戏开始！", Toast.LENGTH_LONG).show();
                                                    GlobalVariableManager.isOnlineMode = true;
                                                    GlobalVariableManager.onlineRoomId = gameRoomInfo.getGameRoomId();
                                                    onQuit();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                                Toast.makeText(StartActivity.this, "网络错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            } else {
                                Toast.makeText(StartActivity.this, apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                            Toast.makeText(StartActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });
    }


    private void tryMatch() {
        if (networkHandler.tryMatch(username)) {
            gameIntent.putExtra(Config.DIFFICULTY, 1);
            gameIntent.putExtra(Config.ONLINE, 1);
            gameIntent.putExtra(Config.NETWORK_HANDLER, networkHandler);
            onQuit();
        }
        else {
            // TODO: 此处回头加个GUI文本提示
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Online Mode Failed");
        }
    }

    private void onQuit() {
        // Get Music State
        gameIntent.putExtra(Config.MUSIC_ENABLE, musicCheckBox.isChecked());
        Log.i(Config.START_ACTIVITY_INFO_TAG, "Music: " + musicCheckBox.isChecked());

        // Goto Game
        startActivity(gameIntent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return true;
    }
}