package com.example.shenhaichen.mobileassistant.presenter;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.data.RecommendModel;
import com.example.shenhaichen.mobileassistant.presenter.contract.RecommendContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
        //返回observable之后，要进行订阅（subscribe）
        mModel.getApps()
                //Schedulers.io()表示放到异步线程中进行网络操作
                .subscribeOn(Schedulers.io())
                //表示返回主线程操作UI
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageBean<AppInfo>>() {
                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
                        if (appInfoPageBean != null) {
                            mView.showResult(appInfoPageBean.getDatas());
                        } else {
                            mView.noData();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        mView.error(e.getMessage());
                        mView.dismissLoading();
                    }
                });

//        mModel.getApps(new Callback<PageBean<AppInfo>>() {
//            @Override
//            public void onResponse(Call<PageBean<AppInfo>> call, Response<PageBean<AppInfo>> response) {
//
//                if (response != null){
//                    mView.showResult(response.body().getDatas());
//                }else {
//                    mView.noData();
//                }
//                mView.dismissLoading();
//
//            }
//
//            @Override
//            public void onFailure(Call<PageBean<AppInfo>> call, Throwable t) {
//                mView.error(t.getMessage());
//                mView.dismissLoading();
//            }
//        });

    }
}
