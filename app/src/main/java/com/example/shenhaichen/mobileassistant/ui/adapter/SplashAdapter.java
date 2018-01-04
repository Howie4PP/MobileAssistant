package com.example.shenhaichen.mobileassistant.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhaichen on 04/01/2018.
 */

public class SplashAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragmentList;

    public SplashAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList( List<Fragment> fragments){
        if (fragments == null){
            mFragmentList = new ArrayList<>();
        }else {
            mFragmentList = fragments;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
