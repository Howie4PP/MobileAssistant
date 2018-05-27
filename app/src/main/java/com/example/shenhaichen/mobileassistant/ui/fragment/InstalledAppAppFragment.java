package com.example.shenhaichen.mobileassistant.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.shenhaichen.mobileassistant.common.apkparset.AndroidApk;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerAppManagerComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.AppManagerModule;
import com.example.shenhaichen.mobileassistant.ui.adapter.AndroidApkAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstalledAppAppFragment extends AppManagerFragment {

    private AndroidApkAdapter mAdapter;

    @Override
    public void init() {
        super.init();
        mPresenter.getInstalledApp();
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appManagerModule(new AppManagerModule(this))
                .appComponent(appComponent).build().injectInstalled(this);

    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {

        mAdapter = new AndroidApkAdapter(AndroidApkAdapter.FLAG_APP);

        return null;
    }

    @Override
    public void showApps(List<AndroidApk> apps) {
        mAdapter.addData(apps);
    }
}
