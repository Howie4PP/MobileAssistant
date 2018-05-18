package com.example.shenhaichen.mobileassistant.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppDownloadInfo;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.RxSchedulers;
import com.example.shenhaichen.mobileassistant.common.util.AppUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;


/**
 * Created by shenhaichen on 13/04/2018.
 */
public class DownLoadButtonController {

    private String mDownloadedDir = null; //文件的下载目录
    private RxDownload mRxDownload;
    private String filePath = null;
    private Api mApi;


    public DownLoadButtonController(RxDownload rxDownload) {
        mRxDownload = rxDownload;
        if (mRxDownload != null){
            mApi = mRxDownload.getRetrofit().create(Api.class);
        }
    }

    /**
     * 根据AppInfo的信息去做状态的处理
     *
     * @param appInfo
     */
    @SuppressLint("CheckResult")
    public void handClick(final DownloadStateButton btn, final AppInfo appInfo) {

        if(mApi == null) return;

        bindClick(btn, appInfo);

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

                            return getAppDownloadInfo(appInfo)
                                    .flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                        @Override
                                        public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {

                                            appInfo.setAppDownloadInfo(appDownloadInfo);
                                            return receiveDownloadStatus(appDownloadInfo);
                                        }
                                    });

                        }
                        return Observable.just(downloadEvent);
                    }
                })
                .compose(RxSchedulers.<DownloadEvent>io_main())
                .subscribe(new DownLoadConsumer(btn, appInfo, btn.getContext()));
    }

    /**
     * 点击按钮的状态绑定
     *
     * @param btn
     */
    @SuppressLint("CheckResult")
    private void bindClick(final DownloadStateButton btn, final AppInfo appInfo) {
        RxView.clicks(btn).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                int flag = (int) btn.getTag(R.id.tag_apk_flag);
                switch (flag) {
                    case DownloadFlag.INSTALLED:
                        runApp(btn.getContext(), appInfo);
                        break;
                    case DownloadFlag.STARTED:
                        pausedDownload(appInfo);
                        break;
                    case DownloadFlag.PAUSED:
                        startDownLoad(btn, appInfo);
                        break;
                    case DownloadFlag.NORMAL:
                        startDownLoad(btn,appInfo);
                        break;
                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(), appInfo);
                        break;
                }
            }
        });

    }

    private void installApp(Context context, AppInfo appInfo) {
        AppUtils.installApk(context, filePath);
    }

    /**
     * 重新开始下载
     *
     * @param appInfo
     */
    @SuppressLint("CheckResult")
    private void startDownLoad(DownloadStateButton btn, final AppInfo appInfo) {

        //TODO 这里需要一个权限判断

        AppDownloadInfo mDownLoadInfo = appInfo.getAppDownloadInfo();
        // 在开始下载的时候，如果没有得到程序的数据对象，则需要先获取
        if (mDownLoadInfo == null) {
            getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
                @Override
                public void accept(AppDownloadInfo appDownloadInfo) throws Exception {
                    appInfo.setAppDownloadInfo(appDownloadInfo);
                    //数据订阅（subscribe）之后，才会发送并被获取
                    mRxDownload.serviceDownload(appDownloadInfo.getDownloadUrl()).subscribe();

                }
            });
        } else {
            //数据订阅（subscribe）之后，才会发送并被获取
            downLoad(btn, appInfo);
        }
    }

    @SuppressLint("CheckResult")
    private void downLoad(DownloadStateButton btn, AppInfo info) {
        mRxDownload.serviceDownload(info.getAppDownloadInfo().getDownloadUrl()).subscribe();
        mRxDownload.receiveDownloadStatus(info.getAppDownloadInfo().getDownloadUrl())
                .subscribe(new DownLoadConsumer(btn, info, btn.getContext()));
    }


    /**
     * 暂停下载
     *
     * @param appInfo
     */
    private void pausedDownload(AppInfo appInfo) {
        AppDownloadInfo downloadInfo = appInfo.getAppDownloadInfo();
        mRxDownload.pauseServiceDownload(downloadInfo.getDownloadUrl());
    }

    /**
     * 运行App的方法
     *
     * @param context
     * @param appInfo
     */
    private void runApp(Context context, AppInfo appInfo) {
    }


    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo) {

        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(context, appInfo.getPackageName()) ? DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);

        return Observable.just(event);

    }


    public Observable<DownloadEvent> isApkFileExist(AppInfo appInfo) {

        filePath = mDownloadedDir + File.separator + appInfo.getReleaseKeyHash();
        File file = new File(filePath);

        DownloadEvent event = new DownloadEvent();

        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);

        return Observable.just(event);

    }


    public Observable<DownloadEvent> receiveDownloadStatus(AppDownloadInfo appInfo) {

        return mRxDownload.receiveDownloadStatus(appInfo.getDownloadUrl());
    }


    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo) {

        return mApi.getAppDownloadInfo(appInfo.getId()).compose(RxHttpResponseCompat
        .<AppDownloadInfo>compatResult());
    }

    class DownLoadConsumer implements Consumer<DownloadEvent> {

        DownloadStateButton btn;
        AppInfo mInfo;
        Context mContext;

        public DownLoadConsumer(DownloadStateButton btn, AppInfo info, Context mContext) {
            this.mContext = mContext;
            this.btn = btn;
            mInfo = info;
        }

        @Override
        public void accept(DownloadEvent downloadEvent) throws Exception {
            int flag = downloadEvent.getFlag();

            btn.setTag(R.id.tag_apk_flag, flag);

            bindClick(btn, mInfo);

            switch (flag) {

                case DownloadFlag.INSTALLED:
                    btn.setText("运行");
                    break;
                case DownloadFlag.STARTED:
                    btn.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    break;
                case DownloadFlag.PAUSED:
                    btn.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    btn.paused();
                    break;
                case DownloadFlag.NORMAL:
                    btn.download();
                    break;
                case DownloadFlag.COMPLETED:
                    btn.setText(mContext.getResources().getString(R.string.install));
                    break;
                case DownloadFlag.FAILED:
                    btn.setText(mContext.getResources().getString(R.string.failed));
                    break;
                case DownloadFlag.DELETED:
                    break;

            }
        }
    }


    interface Api {
        @GET("download/{id}")
        Observable<BaseBean<AppDownloadInfo>> getAppDownloadInfo(@Path("id") int id);
    }

}
