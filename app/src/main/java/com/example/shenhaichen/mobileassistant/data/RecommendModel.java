package com.example.shenhaichen.mobileassistant.data;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;

import retrofit2.Callback;

/**
 *MVP 架构的 model
 * Created by shenhaichen on 03/01/2018.
 */

public class RecommendModel {


    private ApiService mApiService;

    public RecommendModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    public void getApps(Callback<PageBean<AppInfo>> mCallBack){
        //需要外部传入callback
        mApiService.getApps("{'page':0}").enqueue(mCallBack);

    }

}
