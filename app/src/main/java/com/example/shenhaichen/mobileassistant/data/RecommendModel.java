package com.example.shenhaichen.mobileassistant.data;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;

import io.reactivex.Observable;

/**
 *MVP 架构的 model
 * Created by shenhaichen on 03/01/2018.
 */

public class RecommendModel {

    private ApiService mApiService;

    public RecommendModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    /**
     * @return 返回一个时间源在外部调用
     */
    public Observable<PageBean<AppInfo>> getApps(){

       return mApiService.getApps("{'page':0}");

    }

}
