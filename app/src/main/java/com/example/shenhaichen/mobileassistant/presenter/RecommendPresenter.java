package com.example.shenhaichen.mobileassistant.presenter;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.data.RecommendModel;
import com.example.shenhaichen.mobileassistant.presenter.contract.RecommendContract;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


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
//        mView.showLoading();
        //返回observable之后，要进行订阅（subscribe）
        mModel.getApps()
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(new Observer<PageBean<AppInfo>>() {
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

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    new Observer<PageBean<AppInfo>>() {
//        @Override
//        public void onSubscribe(Disposable d) {
//
//        }
//
//        @Override
//        public void onNext(PageBean<AppInfo> appInfoPageBean) {
//
//        }
//
//        @Override
//        public void onError(Throwable e) {
//
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    }
}
