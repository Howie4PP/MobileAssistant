package com.example.shenhaichen.mobileassistant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.common.util.ZoomOutPageTransformer;
import com.example.shenhaichen.mobileassistant.ui.adapter.SplashAdapter;
import com.example.shenhaichen.mobileassistant.ui.fragment.SplashFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        View.OnClickListener{

    @BindView(R.id.splash_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.splash_btn)
    Button mBtn;
    @BindView(R.id.splash_img_icon_1)
    ImageView icon1;
    @BindView(R.id.splash_img_icon_2)
    ImageView icon2;
    @BindView(R.id.splash_img_icon_3)
    ImageView icon3;

    private SplashAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SplashFragment.newInstance(R.drawable.guide_1,R.color.color_bg_guide1,R.string.guide_1));
        fragments.add(SplashFragment.newInstance(R.drawable.guide_2,R.color.color_bg_guide2,R.string.guide_2));
        fragments.add(SplashFragment.newInstance(R.drawable.guide_3,R.color.color_bg_guide3,R.string.guide_3));

        mAdapter = new SplashAdapter(getSupportFragmentManager());
        mBtn.setOnClickListener(this);

        mAdapter.setFragmentList(fragments);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position){
            case 0:
                icon1.setImageResource(R.color.color_w);
                icon2.setImageResource(R.color.colorAccent);
                icon3.setImageResource(R.color.colorAccent);
                break;
            case 1:
                icon1.setImageResource(R.color.colorAccent);
                icon2.setImageResource(R.color.color_w);
                icon3.setImageResource(R.color.colorAccent);
                break;
            case 2:
                icon3.setImageResource(R.color.color_w);
                icon2.setImageResource(R.color.colorAccent);
                icon1.setImageResource(R.color.colorAccent);
                mBtn.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        // ACache 是一个大神写的类似sharePreference的类，可以临时储存数据
        ACache.get(this).put(Constant.IS_SHOW_SPLASH,"0");
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }
}
