package com.example.shenhaichen.mobileassistant.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.User;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.font.IFont;
import com.example.shenhaichen.mobileassistant.common.imageloader.GlideCircleTransform;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.ui.adapter.ViewPagerAdapter;
import com.example.shenhaichen.mobileassistant.ui.bean.FragmentInfo;
import com.example.shenhaichen.mobileassistant.ui.fragment.CategoryFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.GamesFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.RankingFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.RecommendFragment;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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
    private ImageView mUserHeadView;
    private TextView mTextUserName;

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
        initDrawerLayout();
        initUser();
    }


    private void initTabLayout() {


        PagerAdapter mPageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), initFragments());
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

    private void initDrawerLayout() {
        headerView = mNavigationView.getHeaderView(0);

        //左滑界面登录头像和文字
        mUserHeadView = headerView.findViewById(R.id.img_avatar);
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, IFont.Icon.cniao_head).colorRes(R.color.color_w));
        mTextUserName = headerView.findViewById(R.id.txt_username);

        mNavigationView.getMenu().findItem(R.id.menu_app_update).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_loop));
        mNavigationView.getMenu().findItem(R.id.menu_download_manager).setIcon(new IconicsDrawable(this, IFont.Icon.cniao_download));
        mNavigationView.getMenu().findItem(R.id.menu_app_uninstall).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_trash_outline));
        mNavigationView.getMenu().findItem(R.id.menu_setting).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_gear_outline));
        mNavigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, IFont.Icon.cniao_shutdown));
        //设置navigation view的监听
        mNavigationView.setNavigationItemSelectedListener(this);
        //设置toolbar的menu
        mToolbar.inflateMenu(R.menu.menu_toolbar);
        //联合toolbar 和 drawerLayout
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.open, R.string.close);
        //同步两个控件
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    /**
     * Navigation view的监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                break;
        }
        return false;
    }

    /**
     * 退出登录
     */
    private void logout() {
        ACache aCache = ACache.get(this);
        aCache.put(Constant.TOKEN, "");
        aCache.put(Constant.USER, "");
        //退出登录后的,重新设定头像和用户名
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, IFont.Icon.cniao_head).colorRes(R.color.color_w));
        mTextUserName.setText(getResources().getText(R.string.unlogin).toString());
        Snackbar.make( mUserHeadView,getResources().getText(R.string.logout).toString(), Snackbar.LENGTH_SHORT)
                .show();
    }

    /**
     * 使用RxBus从LoginPresenter那边拿数据
     *
     * @param user
     */
    @Subscribe
    public void getUserInfo(User user) {
//        ImageLoader.load(user.getLogo_url(), mUserHeadView);
        initUserHeaderView(user);
    }

    private void initUser() {
        Object objectUser = ACache.get(this).getAsObject(Constant.USER);
        if (objectUser == null) {
            //点击头像跳转到登录页面，进行登录
            headerView.setOnClickListener(this);
        } else {
            User user = (User) objectUser;
            initUserHeaderView(user);
        }
    }


    private List<FragmentInfo> initFragments(){

        List<FragmentInfo> mFragments = new ArrayList<>(4);

        mFragments.add(new FragmentInfo("推荐",RecommendFragment.class));
        mFragments.add(new FragmentInfo ("排行", RankingFragment.class));


        mFragments.add(new FragmentInfo ("游戏", GamesFragment.class));
        mFragments.add(new FragmentInfo ("分类", CategoryFragment.class));

        return  mFragments;

    }

    /**
     * 在得到数据后，把头像和名称加载进控件当中
     *
     * @param user
     */
    private void initUserHeaderView(User user) {
        Glide.with(this).load(user.getLogo_url())
                .transform(new GlideCircleTransform(this)).into(mUserHeadView);
        mTextUserName.setText(user.getUsername());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
