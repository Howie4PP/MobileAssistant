package com.example.shenhaichen.mobileassistant.dagger.module;

import com.example.shenhaichen.mobileassistant.data.AppInfoModel;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * dagger的 Module类，提供实例,所有在provides中的实例，dagger都会自动去寻找并添加
 * Created by shenhaichen on 03/01/2018.
 */
@Module
public class AppInfoModule {

    private AppInfoContract.AppInfoView mView;

    public AppInfoModule(AppInfoContract.AppInfoView view) {
        this.mView = view;
    }

    @Provides
    public AppInfoContract.AppInfoView provideView(){
        return mView;
    }

    @Provides
    public AppInfoModel provideModel(ApiService apiService){
        return new AppInfoModel(apiService);
    }



}
