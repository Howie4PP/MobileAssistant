package com.example.shenhaichen.mobileassistant.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppDownloadInfo;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.RxSchedulers;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.common.util.AppUtils;
import com.example.shenhaichen.mobileassistant.common.util.PermissionUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadBean;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * Created by shenhaichen on 13/04/2018.
 */
public class DownLoadButtonController {

    //    private String mDownloadedDir = null; //文件的下载目录
    private RxDownload mRxDownload;
    private String filePath = null;
    private Api mApi;


    public DownLoadButtonController(RxDownload rxDownload) {
        mRxDownload = rxDownload;
        if (mRxDownload != null) {
            mApi = mRxDownload.getRetrofit().create(Api.class);
        }
    }

    public void handClick(final DownloadStateButton btn, final String url) {


    }

    /**
     * 根据AppInfo的信息去做状态的处理
     *
     * @param appInfo
     */
    @SuppressLint("CheckResult")
    public void handClick(final DownloadStateButton btn, final AppInfo appInfo) {

        if (mApi == null) return;

        bindClick(btn, appInfo);

        isAppInstalled(btn.getContext(), appInfo)
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(DownloadEvent downloadEvent) throws Exception {
                        if (DownloadFlag.UN_INSTALL == downloadEvent.getFlag()) {

                            return isApkFileExist(btn.getContext(), appInfo);

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
                                            return receiveDownloadStatus(appDownloadInfo.getDownloadUrl());
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
                        runApp(btn.getContext(), appInfo.getPackageName());
                        break;
                    case DownloadFlag.STARTED:
                        pausedDownload(appInfo.getAppDownloadInfo().getDownloadUrl());
                        break;
                    case DownloadFlag.PAUSED:
                        startDownLoad(btn, appInfo);
                        break;
                    case DownloadFlag.NORMAL:
                        startDownLoad(btn, appInfo);
                        break;
                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(), appInfo);
                        break;
                }
            }
        });

    }

    private void installApp(Context context, AppInfo appInfo) {

        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR)
                + File.separator + appInfo.getReleaseKeyHash();

        AppUtils.installApk(context, path);
    }

    /**
     * 重新开始下载
     *
     * @param appInfo
     */
    @SuppressLint("CheckResult")
    private void startDownLoad(final DownloadStateButton btn, final AppInfo appInfo) {

        //TODO 这里需要一个权限判断
        PermissionUtil.requestPermisson(btn.getContext(), WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            final AppDownloadInfo downloadInfo = appInfo.getAppDownloadInfo();
                            if (downloadInfo == null) {

                                getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
                                    @Override
                                    public void accept(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {

                                        appInfo.setAppDownloadInfo(appDownloadInfo);

                                        downLoad(btn, appInfo);

                                    }
                                });
                            } else {

                                downLoad(btn, appInfo);
                            }
                        }
                    }
                });

    }

    @SuppressLint("CheckResult")
    private void downLoad(DownloadStateButton btn, AppInfo info) {
        mRxDownload.serviceDownload(appInfo2DownloadBean(info)).subscribe();
        mRxDownload.receiveDownloadStatus(info.getAppDownloadInfo().getDownloadUrl())
                .subscribe(new DownLoadConsumer(btn, info, btn.getContext()));
    }


    /**
     *
     * 需要订阅，暂停下载
     *
     */
    private void pausedDownload(String appDownloadInfo) {
//        AppDownloadInfo downloadInfo = appDownloadInfo;
        mRxDownload.pauseServiceDownload(appDownloadInfo).subscribe();
    }

    /**
     * 运行App的方法
     *
     * @param context
     * @param appInfo
     */
    private void runApp(Context context, String appInfo) {
        AppUtils.runApp(context,appInfo);
    }


    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo) {

        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(context, appInfo.getPackageName()) ? DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);

        return Observable.just(event);

    }


    public Observable<DownloadEvent> isApkFileExist(Context context, AppInfo appInfo) {

        filePath = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + File.separator + appInfo.getReleaseKeyHash();
        File file = new File(filePath);

        DownloadEvent event = new DownloadEvent();

        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);

        return Observable.just(event);

    }


    public Observable<DownloadEvent> receiveDownloadStatus(String url){

        return  mRxDownload.receiveDownloadStatus(url);
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
            //回调以后，进行绑定
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

    public AppInfo downloadRecord2AppInfo(DownloadRecord bean){


        AppInfo info = new AppInfo();

        info.setId(Integer.parseInt(bean.getExtra1()));
        info.setIcon(bean.getExtra2());
        info.setDisplayName(bean.getExtra3());
        info.setPackageName(bean.getExtra4());
        info.setReleaseKeyHash(bean.getExtra5());


        AppDownloadInfo downloadInfo = new AppDownloadInfo();

        downloadInfo.setDowanloadUrl(bean.getUrl());

        info.setAppDownloadInfo(downloadInfo);

        return info;



    }

    private DownloadBean appInfo2DownloadBean(AppInfo info){

        DownloadBean downloadBean = new DownloadBean();

        downloadBean.setUrl(info.getAppDownloadInfo().getDownloadUrl());
        downloadBean.setSaveName(info.getReleaseKeyHash() +".apk");
        downloadBean.setExtra1(info.getId()+"");
        downloadBean.setExtra2(info.getIcon());
        downloadBean.setExtra3(info.getDisplayName());
        downloadBean.setExtra4(info.getPackageName());
        downloadBean.setExtra5(info.getReleaseKeyHash());

        return  downloadBean;
    }
}
