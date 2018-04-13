package com.example.shenhaichen.mobileassistant.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.shenhaichen.mobileassistant.bean.AppDownloadInfo;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.common.util.AppUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;


/**
 * Created by shenhaichen on 13/04/2018.
 */

public class DownLoadButtonController {

    private String mDownloadedDir = null;
    private RxDownload mRxDownload;

    /**
     * 根据AppInfo的信息去做状态的处理
     *
     * @param appInfo
     */
    public void handClick(final DownloadStateButton btn, final AppInfo appInfo) {

        isAppInstalled(btn.getContext(), appInfo)
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(DownloadEvent downloadEvent) throws Exception {
                        if (DownloadFlag.UN_INSTALL == downloadEvent.getFlag()) {

                            return isApkFileExist(appInfo);

                        }
                        return Observable.just(downloadEvent);
                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(DownloadEvent downloadEvent) throws Exception {
                        if (DownloadFlag.FILE_EXIST == downloadEvent.getFlag()) {

                            return getAppDownloadInfo(appInfo).flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                @Override
                                public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {


                                    return receiveDownloadStatus(appDownloadInfo);
                                }
                            });

                        }
                        return Observable.just(downloadEvent);
                    }
                })
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(DownloadEvent downloadEvent) throws Exception {

                        int flag = downloadEvent.getFlag();

                        switch (flag) {

                            case DownloadFlag.INSTALLED:
                                btn.setText("运行");
                                break;
                            case DownloadFlag.STARTED:
                                btn.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                                break;
                            case DownloadFlag.PAUSED:
                                btn.paused();
                                break;
                            case DownloadFlag.NORMAL:
                                btn.download();
                                break;
                        }
                    }
                });
    }


    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo) {

        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(context, appInfo.getPackageName()) ? DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);

        return Observable.just(event);

    }


    public Observable<DownloadEvent> isApkFileExist(AppInfo appInfo) {

        String path = mDownloadedDir + File.separator + appInfo.getReleaseKeyHash();
        File file = new File(path);

        DownloadEvent event = new DownloadEvent();

        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);

        return Observable.just(event);

    }


    public Observable<DownloadEvent> receiveDownloadStatus(AppDownloadInfo appInfo) {

        return mRxDownload.receiveDownloadStatus(appInfo.getDownloadUrl());
    }


    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo) {

        return null;
    }

}
