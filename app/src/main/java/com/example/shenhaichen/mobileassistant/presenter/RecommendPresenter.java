package com.example.shenhaichen.mobileassistant.presenter;

import android.Manifest;
import android.app.Activity;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ProgressObserver;
import com.example.shenhaichen.mobileassistant.data.RecommendModel;
import com.example.shenhaichen.mobileassistant.presenter.contract.RecommendContract;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * MVP结构中的集合类，中转站
 * Created by shenhaichen on 03/01/2018.
 */
public class RecommendPresenter extends BasePresenter<RecommendModel, RecommendContract.View> {

    //    @Inject
    public RecommendPresenter(RecommendContract.View mView, RecommendModel mModel) {
        super(mModel, mView);
    }

//    public void requestPermission(){
//        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
//        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean){
//                            mView.onRequestPermissionSuccess();
//                        }else {
//                            mView.onRequestPermissionFailed();
//                        }
//                    }
//                });
//    }


    public void requestData() {
        //读取数据之前，要先授权
        //在授权之前需要在AndroidManifest中先写上这个权限，否则，有时会遇到无对话框
        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .flatMap(new Function<Boolean, ObservableSource<PageBean<AppInfo>>>() {
                    @Override
                    public ObservableSource<PageBean<AppInfo>> apply(Boolean aBoolean) throws Exception {
                        if(aBoolean){
                            mView.onRequestPermissionSuccess();
                            return  mModel.getApps().compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult());
                        }
                        else{
                            mView.onRequestPermissionFailed();
                            return Observable.empty();
                        }
                    }
                })
                //返回observable之后，要进行订阅（subscribe）
                .subscribe(new ProgressObserver<PageBean<AppInfo>>(mContext, mView) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //解除订阅
                    }
                    @Override
                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
                        if (appInfoPageBean != null) {
                            mView.showResult(appInfoPageBean.getDatas());
                        } else {
                            mView.noData();
                        }
                    }
                });

//        mModel.getApps()
//                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
//                .subscribe(new ProgressObserver<PageBean<AppInfo>>(mContext, mView) {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//                    @Override
//                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
//
//                    }
//                });
    }

}
