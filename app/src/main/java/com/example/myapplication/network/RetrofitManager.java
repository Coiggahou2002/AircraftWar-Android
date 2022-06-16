package com.example.myapplication.network;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    // 单例模式，全局唯一Retrofit实例
    private static Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(NetworkConfig.API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            ;

    private static ApiRepository apiServiceRepo;

    public static Retrofit getRetrofitInstance() {
        return mRetrofit;
    }

    public static ApiRepository getApiServiceRepo() {
        if (apiServiceRepo != null) {
            return apiServiceRepo;
        }
        apiServiceRepo = mRetrofit.create(ApiRepository.class);
        return apiServiceRepo;
    }
}
