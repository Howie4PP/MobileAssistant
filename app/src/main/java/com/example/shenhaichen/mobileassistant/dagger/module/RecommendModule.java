package com.example.shenhaichen.mobileassistant.dagger.module;

import com.example.shenhaichen.mobileassistant.data.AppInfoModel;
import com.example.shenhaichen.mobileassistant.presenter.RecommendPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * dagger的 Module类，提供实例,所有在provides中的实例，dagger都会自动去寻找并添加
 * Created by shenhaichen on 03/01/2018.
 */
@Module(includes = AppModelProviderModule.class)
public class RecommendModule {

    private AppInfoContract.View mView;

    public RecommendModule(AppInfoContract.View view) {
        this.mView = view;
    }

    @Provides
    public AppInfoContract.View provideView(){
        return mView;
    }



    @Provides
    public RecommendPresenter providePresenter(AppInfoContract.View view, AppInfoModel model){
        return new RecommendPresenter(view, model);
    }

}
