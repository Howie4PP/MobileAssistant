package com.example.shenhaichen.mobileassistant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shenhaichen.mobileassistant.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTabLayout();
        initToolbar();

    }

    private void initTabLayout(){
        PagerAdapter mPageAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initToolbar(){
        headerView = mNavigationView.getHeaderView(0);
        headerView.setOnClickListener(this);
        //the menu which inside the navigation view to use this listener
        mNavigationView.setNavigationItemSelectedListener(this);
        //set menu of toolbar
        mToolbar.inflateMenu(R.menu.menu_toolbar);
        //combine the toolbar and drawerLayout
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,mToolbar,R.string.open,R.string.close);
        //sync the toolbar and drawerLayout
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


}
