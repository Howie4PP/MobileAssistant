package com.example.shenhaichen.mobileassistant.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.shenhaichen.mobileassistant.MyApplication;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.presenter.BasePresenter;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by shenhaichen on 09/01/2018.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private Unbinder mUnbinder;

    private MyApplication mApplication;
    
    @Inject
    T mPresenter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);

        setContentView(setLayout());

        mUnbinder = ButterKnife.bind(this);
        this.mApplication = (MyApplication) getApplication();

        setupAcitivtyComponent(mApplication.getmAppComponent());

        init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mUnbinder != Unbinder.EMPTY){

            mUnbinder.unbind();
        }
    }

    public abstract int setLayout();

    public abstract  void setupAcitivtyComponent(AppComponent appComponent);

    public abstract void  init();

}
