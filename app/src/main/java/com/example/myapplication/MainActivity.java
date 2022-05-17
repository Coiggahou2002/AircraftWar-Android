package com.example.myapplication;

import android.os.Bundle;

import com.example.myapplication.api.ApiResponse;
import com.example.myapplication.api.ApiRepository;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.constant.NetworkConfig;
import com.example.myapplication.databinding.ActivityMainBinding;

import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private GameSurfaceView gameSurfaceView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        setContentView(R.layout.activity_main);
//        gameSurfaceView = new GameSurfaceView(this);
//        setContentView(gameSurfaceView);


        /**
         * 下面部分代码是测试通信用的
         * 向服务器发送一个简单http请求
         * 在textView上显示http响应内容
         */
        textView = findViewById(R.id.textview_first);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRepository apiRepository = retrofit.create(ApiRepository.class);
        Call<ApiResponse> call = apiRepository.testCommunication();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d(TAG, response.toString());
                textView.setText("success" + response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                textView.setText("failed" + t.getMessage());
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            gameSurfaceView.x = event.getX();
            gameSurfaceView.y = event.getY();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return true;
    }
}