package com.example.shenhaichen.mobileassistant.data;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;

import io.reactivex.Observable;

/**
 *MVP 架构的 model,负责逻辑处理，这里是调用网络请求
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
    public Observable<BaseBean<PageBean<AppInfo>>> getApps(){

       return mApiService.getApps("{'page':0}");

    }

    //请求banner数据
    public Observable<BaseBean<IndexBean>> index(){
        return mApiService.index();
    }

}
