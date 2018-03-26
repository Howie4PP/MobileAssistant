package com.example.shenhaichen.mobileassistant.ui.fragment;

import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerAppInfoComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.AppInfoModule;
import com.example.shenhaichen.mobileassistant.presenter.AppInfoPresenter;
import com.example.shenhaichen.mobileassistant.ui.adapter.AppInfoAdapter;

/**
 * Created by shenhaichen on 29/12/2017.
 */

public class RankingFragment extends BaseAppInfoFragment{

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(true).showBrief(false)
                .showCategoryName(true).build();
    }
    @Override
    int getType() {
        return AppInfoPresenter.RANKING;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

        DaggerAppInfoComponent.builder()
                .appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build().injectRankingFragment(this);
    }


}
