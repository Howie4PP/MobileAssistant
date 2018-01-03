package com.example.shenhaichen.mobileassistant.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.ui.bean.FragmentInfo;
import com.example.shenhaichen.mobileassistant.ui.fragment.CategoryFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.GamesFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.RankingFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhaichen on 29/12/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments = new ArrayList<>(4);
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        initFragment();
    }

    private void initFragment() {

        mFragments.add(new FragmentInfo(context.getString(R.string.recommend), RecommendFragment.class));
        mFragments.add(new FragmentInfo(context.getString(R.string.ranking), RankingFragment.class));
        mFragments.add(new FragmentInfo(context.getString(R.string.game), GamesFragment.class));
        mFragments.add(new FragmentInfo(context.getString(R.string.category), CategoryFragment.class));
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) mFragments.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragments.get(position).getTitle();
    }
}
