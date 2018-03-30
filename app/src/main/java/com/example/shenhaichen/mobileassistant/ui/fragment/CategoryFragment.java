package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.Category;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerCategoryComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.CategoryModule;
import com.example.shenhaichen.mobileassistant.presenter.CategoryPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.CategoryContract;
import com.example.shenhaichen.mobileassistant.ui.activity.CategoryAppActivity;
import com.example.shenhaichen.mobileassistant.ui.adapter.CategoryAdapter;
import com.example.shenhaichen.mobileassistant.ui.decoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by shenhaichen on 29/12/2017.
 */

public class CategoryFragment extends ProgressFragment<CategoryPresenter> implements CategoryContract.CategoryView {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private CategoryAdapter categoryAdapter;

    @Override
    public void onEmptyViewClick() {

    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void init() {
        initRecyclerView();
        mPresenter.getAllCategory();
    }

    private void initRecyclerView() {

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecycleView.addItemDecoration(itemDecoration);
        categoryAdapter = new CategoryAdapter();
        mRecycleView.setAdapter(categoryAdapter);

        mRecycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CategoryAppActivity.class);
                intent.putExtra(Constant.CATEGORY, categoryAdapter.getData().get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCategoryComponent.builder().appComponent(appComponent)
                .categoryModule(new CategoryModule(this)).build()
                .inject(this);
    }

    @Override
    public void showData(List<Category> categories) {
         categoryAdapter.addData(categories);
    }
}
