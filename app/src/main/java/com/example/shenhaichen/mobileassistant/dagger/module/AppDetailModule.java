package com.example.shenhaichen.mobileassistant.dagger.module;

import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * dagger的 Module类，提供实例,所有在provides中的实例，dagger都会自动去寻找并添加
 * Created by shenhaichen on 03/01/2018.
 */
@Module(includes = AppModelProviderModule.class)
public class AppDetailModule {

    private AppInfoContract.AppDetailView mView;

    public AppDetailModule(AppInfoContract.AppDetailView view) {
        mView = view;
    }

    @Provides
    public AppInfoContract.AppDetailView provideView(){
        return mView;
    }

}
