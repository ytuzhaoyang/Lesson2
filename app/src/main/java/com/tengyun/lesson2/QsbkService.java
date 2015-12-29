package com.tengyun.lesson2;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/12/29.
 */
public interface QsbkService {
    //写接口
    @GET("/article/list/{type}")
    Call<List<Item>> getList(@Path("type")String type, @Query("page")int pager);
    //POST请求用@Filed
    // 写注解
}
