package com.example.shenhaichen.mobileassistant.dagger.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenhaichen on 03/01/2018.
 */
@Module
public class MyAppModule {

    private Application mApplication;

    public MyAppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Application provideApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return new Gson();
    }
}
