package com.example.shenhaichen.mobileassistant.presenter;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.data.RecommendModel;
import com.example.shenhaichen.mobileassistant.presenter.contract.RecommendContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVP结构中的集合类，中转站
 * Created by shenhaichen on 03/01/2018.
 */

public class RecommendPresenter implements RecommendContract.Presenter {

    private RecommendContract.View mView;
    private RecommendModel mModel;

//    @Inject
    public RecommendPresenter(RecommendContract.View mView, RecommendModel mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void requestData() {
        mView.showLoading();
        mModel.getApps(new Callback<PageBean<AppInfo>>() {
            @Override
            public void onResponse(Call<PageBean<AppInfo>> call, Response<PageBean<AppInfo>> response) {

                if (response != null){
                    mView.showResult(response.body().getDatas());
                }else {
                    mView.noData();
                }
                mView.dismissLoading();

            }

            @Override
            public void onFailure(Call<PageBean<AppInfo>> call, Throwable t) {
                mView.error(t.getMessage());
                mView.dismissLoading();
            }
        });

    }
}
