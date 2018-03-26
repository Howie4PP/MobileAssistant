package com.example.shenhaichen.mobileassistant.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.User;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.ui.adapter.ViewPagerAdapter;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.main_tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;

    private View headerView;


    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        RxBus.get().register(this);

        initTabLayout();
        initToolbar();
    }

    private void initTabLayout(){



        PagerAdapter mPageAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(mPageAdapter);

        LinearLayout mLinearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        //在所有tab的中间显示分割线
        mLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //设置分割线的距离，LinearLayout的内距离
        mLinearLayout.setDividerPadding(20);
        //设置分割线的样式
        mLinearLayout.setDividerDrawable(getDrawable(R.drawable.divider_vertical));
        //设置分割颜色（颜色尽量和tab一样）
        mLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initToolbar(){
        headerView = mNavigationView.getHeaderView(0);
        headerView.setOnClickListener(this);
        //设置navigation view的监听
        mNavigationView.setNavigationItemSelectedListener(this);
        //设置toolbar的menu
        mToolbar.inflateMenu(R.menu.menu_toolbar);
        //联合toolbar 和 drawerLayout
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,mToolbar,R.string.open,R.string.close);
        //同步两个控件
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_app_update:

                Toast.makeText(this,"update",Toast.LENGTH_SHORT).show();

                break;
            case R.id.menu_message:
                Toast.makeText(this,"message",Toast.LENGTH_SHORT).show();

                break;
            case R.id.menu_setting:
                Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show();

                break;

        }

        return false;
    }
    @Subscribe
    public void getUserInfo(User user){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}
