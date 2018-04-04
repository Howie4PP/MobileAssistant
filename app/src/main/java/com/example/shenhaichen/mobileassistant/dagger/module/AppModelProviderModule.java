package com.example.shenhaichen.mobileassistant.dagger.module;

import com.example.shenhaichen.mobileassistant.data.AppInfoModel;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenhaichen on 04/04/2018.
 */

@Module
public class AppModelProviderModule {

    @Provides
    public AppInfoModel provideModel(ApiService apiService){
        return new AppInfoModel(apiService);
    }

}
