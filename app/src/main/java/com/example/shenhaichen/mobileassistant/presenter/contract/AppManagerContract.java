package com.example.shenhaichen.mobileassistant.presenter.contract;


import com.example.shenhaichen.mobileassistant.common.apkparset.AndroidApk;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by Ivan on 2017/1/3.
 */

public interface AppManagerContract {

    interface AppManagerView extends BaseView {


        void showDownloading(List<DownloadRecord> downloadRecords);

        void showApps(List<AndroidApk> apps);


    }

    interface IAppManagerModel {


        Observable<List<DownloadRecord>> getDownloadRecord();

        RxDownload getRxDownload();


        Observable<List<AndroidApk>> getLocalApks();


    }


}
