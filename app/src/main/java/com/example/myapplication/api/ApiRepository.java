package com.example.myapplication.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 以注解形式在此配置所有API
 * @author coiggahou
 */
public interface ApiRepository {
    /**
     * 测试接口, 返回happy
     * @return
     */
    @GET("/test")
    Call<ApiResponse> testCommunication();
}
