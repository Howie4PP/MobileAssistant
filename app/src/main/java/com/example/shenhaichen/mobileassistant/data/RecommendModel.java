package com.example.shenhaichen.mobileassistant.data;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.network.ApiService;
import com.example.shenhaichen.mobileassistant.network.HttpManager;

import retrofit2.Callback;

/**
 * Created by shenhaichen on 03/01/2018.
 */

public class RecommendModel {

    public RecommendModel() {
    }

    public void getApps(Callback<PageBean<AppInfo>> mCallBack){
        HttpManager mManager = new HttpManager();

        ApiService mService = mManager.getRetrofit(mManager.getOkHttpClient()).create(ApiService.class);
        //需要外部传入callback
        mService.getApps("{'page':0}").enqueue(mCallBack);


    }


}
