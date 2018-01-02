package com.example.shenhaichen.mobileassistant.network;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shenhaichen on 02/01/2018.
 */

public interface ApiService {

    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    //retrofit 参数设定
    @GET("featured")
    public Call<PageBean<AppInfo>> getApps(@Query("p") String jsonParam);

}
