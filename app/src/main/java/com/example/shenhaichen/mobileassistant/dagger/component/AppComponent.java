package com.example.shenhaichen.mobileassistant.dagger.component;

import com.example.shenhaichen.mobileassistant.dagger.module.HttpModule;
import com.example.shenhaichen.mobileassistant.dagger.module.MyAppModule;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 级别最高的Component,作为公共component
 * 多个singleton module 用{}括起来
 * Created by shenhaichen on 03/01/2018.
 */
@Component(modules = {MyAppModule.class, HttpModule.class})
@Singleton
public interface AppComponent {

    public ApiService getApiService();
}
