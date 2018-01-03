package com.example.shenhaichen.mobileassistant.dagger.module;

import com.example.shenhaichen.mobileassistant.data.RecommendModel;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.RecommendPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.RecommendContract;

import dagger.Module;
import dagger.Provides;

/**
 * dagger的 Module类，提供实例,所有在provides中的实例，dagger都会自动去寻找并添加
 * Created by shenhaichen on 03/01/2018.
 */
@Module
public class RecommendModule {

    private RecommendContract.View mView;

    public RecommendModule(RecommendContract.View view) {
        this.mView = view;
    }

    @Provides
    public RecommendContract.View provideView(){
        return mView;
    }

    @Provides
    public RecommendModel provideModel(ApiService apiService){
        return new RecommendModel(apiService);
    }

    @Provides
    public RecommendPresenter providePresenter(RecommendContract.View view, RecommendModel model){
        return new RecommendPresenter(view, model);
    }


}
