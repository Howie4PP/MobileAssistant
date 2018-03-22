package com.example.shenhaichen.mobileassistant.data.network;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.bean.requesbean.LoginRequestBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by shenhaichen on 02/01/2018.
 */
public interface ApiService {

    String BASE_URL = "*****************";

    //retrofit与RxJava整合后的写法
    @GET("featured2")
    Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParam);

    @GET("index")
    Observable<BaseBean<IndexBean >> index();

    @GET("toplist")
    Observable<BaseBean<AppInfo>> toplist(@Query("page") int page);

    @POST("login")
    public Observable<BaseBean> login(@Body LoginRequestBean bean);

    @FormUrlEncoded // FormBody
    @POST("login")
    public   void login2(@Field("phone") String phone);
}
