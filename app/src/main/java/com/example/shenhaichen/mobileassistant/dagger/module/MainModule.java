package com.example.shenhaichen.mobileassistant.dagger.module;


import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.data.MainModel;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.MainContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {


    private MainContract.MainView mView;


    public MainModule(MainContract.MainView view){

        this.mView = view;
    }



    @FragmentScope
    @Provides
    public MainContract.MainView provideView(){


        return  mView;
    }

    @FragmentScope
    @Provides
    public MainContract.IMainModel provideModel(ApiService apiService){

        return  new MainModel(apiService);
    }


}
