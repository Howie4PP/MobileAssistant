package com.example.shenhaichen.mobileassistant.ui.fragment;


import com.example.shenhaichen.mobileassistant.presenter.AppInfoPresenter;
import com.example.shenhaichen.mobileassistant.ui.adapter.AppInfoAdapter;

/**
 * Created by Ivan on 16/9/26.
 */

public class HotAppFragment extends BaseAppInfoFragment {

    @Override
    int getType() {
        return AppInfoPresenter.HOT_APP_LIST;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return  AppInfoAdapter.builder().showPosition(true).showBrief(false).showCategoryName(true).rxDownload(mRxDownload).build();
    }





}
