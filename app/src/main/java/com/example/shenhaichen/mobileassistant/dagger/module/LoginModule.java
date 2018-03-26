package com.example.shenhaichen.mobileassistant.dagger.module;

import com.example.shenhaichen.mobileassistant.data.LoginModel;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * dagger的 Module类，提供实例,所有在provides中的实例，dagger都会自动去寻找并添加
 * Created by shenhaichen on 03/01/2018.
 */
@Module
public class LoginModule {

    private LoginContract.LoginView mView;

    public LoginModule(LoginContract.LoginView mView) {
        this.mView = mView;
    }

    @Provides
    public LoginContract.LoginView provideView(){
        return mView;
    }

    @Provides
    public LoginContract.ILoginModel provideModel(ApiService apiService){
        return new LoginModel(apiService);
    }



}
