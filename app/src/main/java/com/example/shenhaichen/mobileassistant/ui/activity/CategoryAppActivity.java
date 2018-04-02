package com.example.shenhaichen.mobileassistant.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.Category;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.ui.adapter.CategoryAppAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;

public class CategoryAppActivity extends BaseActivity {

    @BindView(R.id.category_tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.category_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.category_view_pager)
    ViewPager mViewPager;

    private Category mCategory;

    @Override
    public int setLayout() {
        return R.layout.activity_category_app;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {
        getData();
        initTabLayout();
        mToolbar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000))
        );

    }

    private void getData() {
        Intent intent = getIntent();
        mCategory = (Category) intent.getSerializableExtra(Constant.CATEGORY);
    }

    private void initTabLayout() {

        CategoryAppAdapter mPageAdapter = new CategoryAppAdapter(getSupportFragmentManager(), mCategory.getId(), this);
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
}
