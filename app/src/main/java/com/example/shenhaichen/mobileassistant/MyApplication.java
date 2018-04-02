package com.example.shenhaichen.mobileassistant;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerAppComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.HttpModule;
import com.example.shenhaichen.mobileassistant.dagger.module.MyAppModule;

/**
 * Created by shenhaichen on 03/01/2018.
 */

public class MyApplication extends Application {

    private AppComponent mAppComponent;
    private View mView;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public AppComponent getmAppComponent() {
        return mAppComponent;
    }


    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注入，得到整个的AppComponent
        mAppComponent = DaggerAppComponent.builder()
                .myAppModule(new MyAppModule(this))
                .httpModule(new HttpModule()).build();
    }
}
