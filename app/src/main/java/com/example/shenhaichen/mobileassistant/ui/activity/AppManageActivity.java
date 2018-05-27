package com.example.shenhaichen.mobileassistant.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.ui.adapter.ViewPagerAdapter;
import com.example.shenhaichen.mobileassistant.ui.bean.FragmentInfo;
import com.example.shenhaichen.mobileassistant.ui.fragment.DownloadedFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.DownloadingFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.InstalledAppAppFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppManageActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    public int setLayout() {
        return R.layout.activity_download_manager;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {
        initToolbar();
        initTablayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initTablayout() {

        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),initFragments());
        mViewpager.setOffscreenPageLimit(adapter.getCount());
        mViewpager.setAdapter(adapter);

        mTabs.setupWithViewPager(mViewpager);


    }

    private void initToolbar() {
        //设置导航图标
        mToolbar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.setTitle(R.string.download_manager);
    }

    private List<FragmentInfo> initFragments(){

        List<FragmentInfo> mFragments = new ArrayList<>(4);

        mFragments.add(new FragmentInfo("下载",DownloadingFragment.class));
        mFragments.add(new FragmentInfo("已完成",DownloadedFragment.class));
        mFragments.add(new FragmentInfo("安装",InstalledAppAppFragment.class));


        return  mFragments;

    }
}
