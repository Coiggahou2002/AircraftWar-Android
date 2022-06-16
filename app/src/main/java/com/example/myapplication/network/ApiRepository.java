package com.example.myapplication.network;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRepository {
    @POST("/user/register")
    Observable<ApiResponse> register(@Body UserCredentialDTO userCredentialDTO);

    @POST("/user/login")
    Observable<ApiResponse> login(@Body UserCredentialDTO userCredentialDTO);

    @GET("/user/get_info")
    Observable<ApiResponse> getUserInfo(@Query("userId") String userId);

    @FormUrlEncoded
    @POST("/game/create_new_room")
    Observable<ApiResponse> createNewRoom(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("/game/join_game_room")
    Observable<ApiResponse> joinGameRoom(@Field("gameRoomId") String gameRoomId,
                                         @Field("userId") String userId);

    @GET("/game/get_pending_rooms")
    Observable<ApiResponse> getPendingRooms();

    @GET("/game/get_room_info_by_id")
    Observable<ApiResponse> getRoomInfoById(@Query("gameRoomId") String gameRoomId);

    @FormUrlEncoded
    @POST("/game/update_game_info_by_id")
    Observable<ApiResponse> updateGameInfoById(@Field("gameRoomId") String gameRoomId,
                                               @Field("userId") String userId,
                                               @Field("score") int score);

    @GET("/props/get_all")
    Observable<ApiResponse> getAllProps();

    @FormUrlEncoded
    @POST("/props/buy_prop")
    Observable<ApiResponse> buyProp(@Field("propId") String userId,
                                    @Field("userId") String propId);
}

