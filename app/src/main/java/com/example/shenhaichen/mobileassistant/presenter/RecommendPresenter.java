package com.example.shenhaichen.mobileassistant.presenter;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ErrorHandlerObserver;
import com.example.shenhaichen.mobileassistant.data.RecommendModel;
import com.example.shenhaichen.mobileassistant.presenter.contract.RecommendContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * MVP结构中的集合类，中转站
 * Created by shenhaichen on 03/01/2018.
 */

public class RecommendPresenter extends BasePresenter<RecommendModel,RecommendContract.View> {

    //    @Inject
    public RecommendPresenter(RecommendContract.View mView, RecommendModel mModel) {
     super(mModel,mView);
    }


    public void requestData() {
        mView.showLoading();
        //返回observable之后，要进行订阅（subscribe）
        mModel.getApps()
                //Schedulers.io()表示放到异步线程中进行网络操作
                .subscribeOn(Schedulers.io())
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                //而后返回主线程操作UI
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandlerObserver<PageBean<AppInfo>>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PageBean<AppInfo> appInfoPageBean) {

                        if (appInfoPageBean != null){
                            mView.showResult(appInfoPageBean.getDatas());
                        }else {
                            mView.noData();
                        }

                    }

                    @Override
                    public void onComplete() {
                           mView.dismissLoading();
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
