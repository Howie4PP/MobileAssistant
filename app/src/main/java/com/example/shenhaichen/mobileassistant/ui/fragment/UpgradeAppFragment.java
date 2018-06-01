package com.example.shenhaichen.mobileassistant.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.ui.adapter.AppInfoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpgradeAppFragment extends AppManagerFragment {


    AppInfoAdapter mAdapter;

    public UpgradeAppFragment() {
        // Required empty public constructor
    }


    @Override
    public void init() {
        super.init();
        mPresenter.getUpdateApps();
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {

        mAdapter = AppInfoAdapter.builder().updateStatus(true).rxDownload(mPresenter.geRxDowanload()).build();

        return mAdapter;
    }




}
