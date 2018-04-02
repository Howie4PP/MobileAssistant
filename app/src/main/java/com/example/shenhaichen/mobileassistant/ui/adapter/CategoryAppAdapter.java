package com.example.shenhaichen.mobileassistant.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.ui.fragment.CategoryAppFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhaichen on 29/12/2017.
 */

public class CategoryAppAdapter extends FragmentStatePagerAdapter {

    private List<String> titles = new ArrayList<>(3);
    private int mCategoryId;

    public CategoryAppAdapter(FragmentManager fm, int mCategoryId, Context mContext) {
        super(fm);
        this.mCategoryId = mCategoryId;

        titles.add(mContext.getResources().getString(R.string.featured));
        titles.add(mContext.getResources().getString(R.string.ranking));
        titles.add(mContext.getResources().getString(R.string.latest));

    }


    @Override
    public Fragment getItem(int position) {
        return new CategoryAppFragment(mCategoryId, position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position);
    }
}
