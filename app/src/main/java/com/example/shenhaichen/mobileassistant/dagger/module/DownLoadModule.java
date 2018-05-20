package com.example.shenhaichen.mobileassistant.dagger.module;

import android.app.Application;
import android.os.Environment;

import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.util.ACache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import zlc.season.rxdownload2.RxDownload;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * dagger的 Module类，提供实例,所有在provides中的实例，dagger都会自动去寻找并添加
 * Created by shenhaichen on 03/01/2018.
 */
@Module
public class DownLoadModule {
    @Provides
    @Singleton
    public RxDownload provideRxDownLoad(Application application, Retrofit retrofit, File downloadDir){

        ACache.get(application).put(Constant.APK_DOWNLOAD_DIR, downloadDir.getPath());

        return RxDownload.getInstance(application)
                .retrofit(retrofit)
                .defaultSavePath(downloadDir.getPath())
                .maxThread(10)
                .maxDownloadNumber(5);
    }

    @Provides
    @Singleton
    File provideDownLoadDir(){
        return Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
    }

}
