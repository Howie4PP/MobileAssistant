package com.example.shenhaichen.mobileassistant.data;


import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.requesbean.AppsUpdateBean;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.MainContract;

import java.util.List;

import io.reactivex.Observable;

public class MainModel implements MainContract.IMainModel {

    private ApiService mApiService;

    public MainModel(ApiService apiService){
        this.mApiService = apiService;
    }


    @Override
    public Observable<BaseBean<List<AppInfo>>> getUpdateApps(AppsUpdateBean param) {
        return mApiService.getAppsUpdateinfo(param.getPackageName(),param.getVersionCode());
    }
}
