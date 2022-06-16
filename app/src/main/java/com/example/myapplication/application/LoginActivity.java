package com.example.myapplication.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Config;
import com.example.myapplication.R;
import com.example.myapplication.network.ApiRepository;
import com.example.myapplication.network.ApiResponse;
import com.example.myapplication.network.ApiResponseStatus;
import com.example.myapplication.network.LoginHandler;
import com.example.myapplication.network.LoginHandlerNaiveImpl;
import com.example.myapplication.network.RetrofitManager;
import com.example.myapplication.network.UserCredentialDTO;
import com.example.myapplication.network.UserInfoDTO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    Intent startIntent;
    LoginHandler networkHandler;

    TextView loginTitle;
    Button loginButton, registerButton;
    TextInputEditText usernameEditor, passwordEditor;
    Editable username, password;
    Retrofit retrofit;
    ApiRepository apiRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 请在完成LoginHandler实现后，将此处替换为实现类
        networkHandler = new LoginHandlerNaiveImpl();

        startIntent = new Intent(LoginActivity.this, StartActivity.class);

        retrofit = RetrofitManager.getRetrofitInstance();
        apiRepository = RetrofitManager.getApiServiceRepo();

        setViews();
        createListeners();
    }

    private void setViews() {
        setContentView(R.layout.login);
        loginTitle = findViewById(R.id.login_title);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        usernameEditor = findViewById(R.id.username_input);
        passwordEditor = findViewById(R.id.password_input);
    }

    private void createListeners() {
        usernameEditor.setOnEditorActionListener((v, actionId, event) -> {
            if (
                    event != null &&
                            (event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(usernameEditor.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        passwordEditor.setOnEditorActionListener((v, actionId, event) -> {
            if (
                    event != null &&
                            (event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(passwordEditor.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            if (!isUserCredentialNotEmpty()) {
                return;
            }
            UserCredentialDTO userCredentialDTO = new UserCredentialDTO(
                    usernameEditor.getText().toString(),
                    passwordEditor.getText().toString()
            );
            apiRepository.login(userCredentialDTO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiResponse apiResponse) {
                            Integer code = apiResponse.getCode();
                            String userInfoDTOJson = new Gson().toJson(apiResponse.getData());
                            UserInfoDTO userInfoDTO = new Gson().fromJson(userInfoDTOJson, new TypeToken<UserInfoDTO>() {
                            }.getType());
                            if (code == ApiResponseStatus.SUCCESS.getCode()) {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                GlobalVariableManager.userId = userInfoDTO.getUserId();
                                GlobalVariableManager.username = userInfoDTO.getUsername();
                                GlobalVariableManager.avatarPath = userInfoDTO.getAvatarPath();
                                GlobalVariableManager.coin = userInfoDTO.getCoin();
                                Toast.makeText(LoginActivity.this, GlobalVariableManager.userId, Toast.LENGTH_SHORT).show();
                                onQuit();
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败: " + apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(LoginActivity.this, "网络错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        registerButton.setOnClickListener(v -> {
            if (!isUserCredentialNotEmpty()) {
                return;
            }
            UserCredentialDTO userCredentialDTO = new UserCredentialDTO(
                    usernameEditor.getText().toString(),
                    passwordEditor.getText().toString()
            );
            apiRepository.register(userCredentialDTO)
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
                                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(LoginActivity.this, "网络错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });
    }

    private boolean isUserCredentialNotEmpty() {
        username = usernameEditor.getText();
        password = passwordEditor.getText();
        if (username == null || username.toString().equals("")) {
            loginTitle.setText(R.string.empty_username);
            return false;
        }
        if (password == null || password.toString().equals("")) {
            loginTitle.setText(R.string.empty_password);
            return false;
        }
        return true;
    }


    private void onQuit() {
        Log.i(Config.LOGIN_ACTIVITY_INFO_TAG, username.toString());

        startIntent.putExtra(Config.USERNAME, username.toString());
        startActivity(startIntent);
        finish();
    }
}
