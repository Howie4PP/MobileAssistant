package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.annotation.SuppressLint;

import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerAppInfoComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.AppInfoModule;
import com.example.shenhaichen.mobileassistant.presenter.AppInfoPresenter;
import com.example.shenhaichen.mobileassistant.ui.adapter.AppInfoAdapter;

/**
 * Created by shenhaichen on 30/03/2018.
 */

@SuppressLint("ValidFragment")
public class CategoryAppFragment extends BaseAppInfoFragment {

    private int categoryId;
    private int mFlagType;

    public CategoryAppFragment(int categoryId, int flagType) {
        this.categoryId = categoryId;
        this.mFlagType = flagType;
    }


    @Override
    public void init() {
        //需要等待取得数据后，在实例化控件，要不然会报空指针
        mPresenter.requestCategory(page, categoryId, mFlagType);
        initRecyclerView();
    }

    @Override
    int getType() {
        return AppInfoPresenter.CATEGORY;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(false).showBrief(true)
                .showCategoryName(false).build();

    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this)).build()
                .injectCategoryAppFragment(this);
    }
}
