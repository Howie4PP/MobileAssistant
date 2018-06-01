package com.example.shenhaichen.mobileassistant.presenter;


import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.requesbean.AppsUpdateBean;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.apkparset.AndroidApk;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ProgressObserver;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.common.util.AppUtils;
import com.example.shenhaichen.mobileassistant.common.util.JsonUtils;
import com.example.shenhaichen.mobileassistant.common.util.PermissionUtil;
import com.example.shenhaichen.mobileassistant.presenter.contract.MainContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static android.Manifest.permission.READ_PHONE_STATE;

public class MainPresenter extends BasePresenter<MainContract.IMainModel,MainContract.MainView> {

    @Inject
    public MainPresenter(MainContract.IMainModel iMainModel, MainContract.MainView mainView) {
        super(iMainModel, mainView);
    }


    public  void requestPermission(){


        PermissionUtil.requestPermisson(mContext,READ_PHONE_STATE).doOnNext(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {

                if(!aBoolean){
                    mView.requestPermissionFail();
                }

            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
               mView.requestPermissionSuccess();
            }
        });

    }




    public void getAppUpdateInfo(){


        getIntalledApps()
                .flatMap(new Function<AppsUpdateBean, ObservableSource<List<AppInfo>>>() {
            @Override
            public ObservableSource<List<AppInfo>> apply(@NonNull AppsUpdateBean params) throws Exception {

                 return  mModel.getUpdateApps(params).compose(RxHttpResponseCompat.<List<AppInfo>>compatResult());
            }
        }).subscribe(new ProgressObserver<List<AppInfo>>(mContext,mView) {
            @Override
            public void onNext(List<AppInfo> appInfos) {

                if(appInfos !=null){

                    ACache.get(mContext).put(Constant.APP_UPDATE_LIST, JsonUtils.toJson(appInfos));
                }

                mView.changeAppNeedUpdateCount(appInfos==null?0:appInfos.size());


            }
        });

    }





    private Observable<AppsUpdateBean> getIntalledApps(){


        return  Observable.create(new ObservableOnSubscribe<AppsUpdateBean>() {


            @Override
            public void subscribe(ObservableEmitter<AppsUpdateBean> e) throws Exception {

               e.onNext(buildParams(AppUtils.getInstalledApps(mContext)));
                e.onComplete();
            }
        });

    }





    private AppsUpdateBean buildParams(List<AndroidApk> apks){


        StringBuilder packageNameBuilder = new StringBuilder();
        StringBuilder versionCodeBuilder = new StringBuilder();

        for(AndroidApk apk :apks){

            if(!apk.isSystem()){

                packageNameBuilder.append(apk.getPackageName()).append(",");
                versionCodeBuilder.append(apk.getAppVersionCode()).append(",");
            }
        }

        AppsUpdateBean param = new AppsUpdateBean();
        param.setPackageName(packageNameBuilder.toString());
        param.setVersionCode(versionCodeBuilder.toString());

        return param;

    }


}
