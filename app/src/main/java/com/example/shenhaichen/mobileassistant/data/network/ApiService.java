package com.example.shenhaichen.mobileassistant.data.network;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by shenhaichen on 02/01/2018.
 */
public interface ApiService {

    String BASE_URL = "*****";

    //retrofit与RxJava整合后的写法
    @GET("featured")
    Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParam);

}
