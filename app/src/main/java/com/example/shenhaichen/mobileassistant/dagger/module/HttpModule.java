package com.example.shenhaichen.mobileassistant.dagger.module;

import com.example.shenhaichen.mobileassistant.data.network.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shenhaichen on 03/01/2018.
 */

@Module
public class HttpModule {

    //网络模块尽量使用单例模式，减少内存消耗
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        //log 拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //开发模式记录整个body，否则只记录基本信息如返回200， http协议版本等
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //如果使用Https,需要创建SSLSocketFactory, 并设置到client
        //SSLSocketFactory sslSocketFactory = null;

        return new OkHttpClient.Builder()
                //HeadInterceptor实现了Interceptor,用来往 Request header添加一些业务数据，如App版本，token信息
                //.addInterceptor(new HeadInterceptor())
                .addInterceptor(logging)
                //连接超时和读取时间的设置
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient);

        return builder.build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

}
