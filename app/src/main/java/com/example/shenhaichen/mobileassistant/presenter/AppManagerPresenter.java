package com.example.shenhaichen.mobileassistant.presenter;


import android.text.TextUtils;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.apkparset.AndroidApk;
import com.example.shenhaichen.mobileassistant.common.rx.RxSchedulers;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ProgressObserver;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppManagerContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.presenter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AppManagerPresenter extends BasePresenter<AppManagerContract.IAppManagerModel,AppManagerContract.AppManagerView> {

    @Inject
    public AppManagerPresenter(AppManagerContract.IAppManagerModel iAppManagerModel, AppManagerContract.AppManagerView appManagerView) {
        super(iAppManagerModel, appManagerView);
    }



    public void getDownlodingApps(){
        mModel.getDownloadRecord().compose(RxSchedulers.<List<DownloadRecord>>io_main())
                .subscribe(new ProgressObserver<List<DownloadRecord>>(mContext,mView) {
                    @Override
                    public void onNext(List<DownloadRecord> downloadRecords) {
                        mView.showDownloading(downloadRecordFilter(downloadRecords));
                    }
                });

    }



    public void getLocalApks(){

        mModel.getLocalApks().compose(RxSchedulers.<List<AndroidApk>>io_main())
                .subscribe(new ProgressObserver<List<AndroidApk>>(mContext,mView) {
                    @Override
                    public void onNext(List<AndroidApk> androidApks) {


                        mView.showApps(androidApks);
                    }
                });
    }



    public RxDownload geRxDowanload(){

        return mModel.getRxDownload();
    }

    public void getInstalledApp(){
        mModel.getInstalledApks().compose(RxSchedulers.<List<AndroidApk>> io_main())
                .subscribe(new ProgressObserver<List<AndroidApk>>(mContext,mView) {
                    @Override
                    public void onNext(List<AndroidApk> androidApks) {
                        mView.showApps(androidApks);
                    }
                });
    }

    public void  getUpdateApps(){


        String json =   ACache.get(mContext).getAsString(Constant.APP_UPDATE_LIST);


        if(!TextUtils.isEmpty(json)){

            Gson gson = new Gson();
            List<AppInfo> apps = gson.fromJson(json,new TypeToken<List<AppInfo>>(){}.getType());


            Observable.just(apps)
                    .compose(RxSchedulers.<List<AppInfo>>io_main())

                    .subscribe(new ProgressObserver<List<AppInfo>>(mContext,mView) {
                        @Override
                        public void onNext(List<AppInfo> appInfos) {

                            mView.showUpdateApps(appInfos);
                        }
                    });


        }


    }

    private List<DownloadRecord> downloadRecordFilter(List<DownloadRecord> downloadRecords){

        List<DownloadRecord> newList = new ArrayList<>();
        for (DownloadRecord r :downloadRecords){

            if(r.getFlag() != DownloadFlag.COMPLETED){

                newList.add(r);
            }
        }

        return newList;

    }
}
