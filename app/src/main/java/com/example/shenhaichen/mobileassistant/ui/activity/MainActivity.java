package com.example.shenhaichen.mobileassistant.ui.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.User;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.font.IFont;
import com.example.shenhaichen.mobileassistant.common.imageloader.GlideCircleTransform;
import com.example.shenhaichen.mobileassistant.common.rx.RxBus;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerMainComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.MainModule;
import com.example.shenhaichen.mobileassistant.presenter.MainPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.MainContract;
import com.example.shenhaichen.mobileassistant.ui.adapter.ViewPagerAdapter;
import com.example.shenhaichen.mobileassistant.ui.bean.FragmentInfo;
import com.example.shenhaichen.mobileassistant.ui.fragment.CategoryFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.GamesFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.RankingFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.RecommendFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.SettingFragment;
import com.example.shenhaichen.mobileassistant.ui.widget.BadgeActionProvider;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends  BaseActivity<MainPresenter> implements MainContract.MainView  {

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

    private  BadgeActionProvider badgeActionProvider;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

        DaggerMainComponent.builder().appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

    }

    @Override
    public void init() {
        boolean key_smart_install= getSharedPreferences(getPackageName()+"_preferences",MODE_PRIVATE).getBoolean("key_smart_install",false);

        Log.d("MainActivity","key_smart_install="+key_smart_install);

        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {

                initUserHeadView(user);
            }
        });

        mPresenter.requestPermission();

        mPresenter.getAppUpdateInfo();
    }

    private List<FragmentInfo>  initFragments(){

        List<FragmentInfo> mFragments = new ArrayList<>(4);

        mFragments.add(new FragmentInfo("推荐",RecommendFragment.class));
        mFragments.add(new FragmentInfo ("排行", RankingFragment.class));


        mFragments.add(new FragmentInfo ("游戏", GamesFragment.class));
        mFragments.add(new FragmentInfo ("分类", CategoryFragment.class));

        return  mFragments;

    }

    private void initTabLayout() {


        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),initFragments());
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mViewPager.setAdapter(adapter);


        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initDrawerLayout() {


        headerView = mNavigationView.getHeaderView(0);

        mUserHeadView = (ImageView) headerView.findViewById(R.id.img_avatar);
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, IFont.Icon.cniao_head).colorRes(R.color.white));

        mTextUserName = (TextView) headerView.findViewById(R.id.txt_username);


        mNavigationView.getMenu().findItem(R.id.menu_app_update).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_loop));
        mNavigationView.getMenu().findItem(R.id.menu_download_manager).setIcon(new IconicsDrawable(this, IFont.Icon.cniao_download));
        mNavigationView.getMenu().findItem(R.id.menu_app_uninstall).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_trash_outline));
        mNavigationView.getMenu().findItem(R.id.menu_setting).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_gear_outline));

        mNavigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, IFont.Icon.cniao_shutdown));

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.menu_logout:

                        logout();

                        break;
                    case R.id.menu_download_manager:

                        toAppManagerActivity(1);

                        break;
                    case R.id.menu_app_uninstall:

                        toAppManagerActivity(3);

                        break;
                    case R.id.menu_app_update:

                        toAppManagerActivity(2);

                        break;

                    case R.id.menu_setting:

                        startActivity(new Intent(MainActivity.this,SettingFragment.class));

                        break;



                }


                return false;
            }
        });




        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);

        drawerToggle.syncState();

        mDrawerLayout.addDrawerListener(drawerToggle);


    }

    private void initToolbar(){

        mToolbar.inflateMenu(R.menu.menu_toolbar);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.action_search){

                    startActivity(new Intent(MainActivity.this,SearchActivity.class));
                }

                return true;
            }
        });


        MenuItem downloadMenuItem = mToolbar.getMenu().findItem(R.id.action_download);


        badgeActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(downloadMenuItem);

        badgeActionProvider.setIcon(DrawableCompat.wrap(new IconicsDrawable(this, IFont.Icon.cniao_download).color(ContextCompat.getColor(this,R.color.white))));

        badgeActionProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toAppManagerActivity(badgeActionProvider.getBadgeNum()>0?2:0);
            }
        });

    }

    private void initUserHeadView(User user){

        Glide.with(this).load(user.getLogo_url()).transform(new GlideCircleTransform(this)).into(mUserHeadView);

        mTextUserName.setText(user.getUsername());
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
    public void getUserInfo(User user) {
//        ImageLoader.load(user.getLogo_url(), mUserHeadView);
        initUserHeaderView(user);
    }

    private void initUser() {
        Object objectUser = ACache.get(this).getAsObject(Constant.USER);
        if(objectUser ==null){

            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            });

        }
        else{

            User user = (User) objectUser;
            initUserHeadView(user);

        }
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
//        RxBus.get().unregister(this);
    }

    private void toAppManagerActivity(int position){

        Intent intent = new Intent(MainActivity.this,AppManageActivity.class);

        intent.putExtra(Constant.POSITION,position);

        startActivity(new Intent(intent));

    }

    @Override
    public void requestPermissionSuccess() {
        initToolbar();
        initDrawerLayout();
        initTabLayout();
        initUser();
    }

    @Override
    public void requestPermissionFail() {
        Toast.makeText(MainActivity.this,"授权失败....",Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeAppNeedUpdateCount(int count) {
        if(count>0){

            badgeActionProvider.setText(count+"");
        }
        else{

            badgeActionProvider.hideBadge();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void disMissLoading() {

    }

    @Override
    public void showError(String mes) {

    }
}
