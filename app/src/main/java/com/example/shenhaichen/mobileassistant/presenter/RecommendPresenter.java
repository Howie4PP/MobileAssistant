package com.example.shenhaichen.mobileassistant.presenter;

import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ProgressObserver;
import com.example.shenhaichen.mobileassistant.data.AppInfoModel;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MVP结构中的集合类，中转站
 * Created by shenhaichen on 03/01/2018.
 */
public class RecommendPresenter extends BasePresenter<AppInfoModel, AppInfoContract.View> {

    @Inject
    public RecommendPresenter(AppInfoContract.View mView, AppInfoModel mModel) {
        super(mModel, mView);
    }

    public void requestData() {
        //读取数据之前，要先授权
        //在授权之前需要在AndroidManifest中先写上这个权限，否则，有时会遇到无对话框
//        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
//        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
//                .flatMap(new Function<Boolean, ObservableSource<PageBean<AppInfo>>>() {
//                    @Override
//                    public ObservableSource<PageBean<AppInfo>> apply(Boolean aBoolean) throws Exception {
//                        if(aBoolean){
//                            mView.onRequestPermissionSuccess();
//                            return  mModel.getApps().compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult());
//                        }
//                        else{
//                            mView.onRequestPermissionFailed();
//                            return Observable.empty();
//                        }
//                    }
//                })
//                //返回observable之后，要进行订阅（subscribe）
//                .subscribe(new ProgressObserver<PageBean<AppInfo>>(mContext, mView) {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        //解除订阅
//                    }
//                    @Override
//                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
//                        if (appInfoPageBean != null) {
//                            mView.showResult(appInfoPageBean.getDatas());
//                        } else {
//                            mView.noData();
//                        }
//                    }
//                });

        mModel.index().compose(RxHttpResponseCompat.<IndexBean>compatResult())
                .subscribe(new ProgressObserver<IndexBean>(mContext, mView) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IndexBean indexBean) {
                        if (indexBean != null) {
                            mView.showResult(indexBean);
                        }
                    }
                });


    }

}
