package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerRecommendComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.RecommendModule;
import com.example.shenhaichen.mobileassistant.presenter.RecommendPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;
import com.example.shenhaichen.mobileassistant.ui.adapter.IndexMultiAdapter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by shenhaichen on 29/12/2017.
 */

public class RecommendFragment extends ProgressFragment<RecommendPresenter> implements AppInfoContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private IndexMultiAdapter mRecommendAdapter;
    @Inject
    RecommendPresenter mPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    //继承自父类的方法，父类调用
    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    //继承自父类的方法，父类调用
    @Override
    public void init() {
        initRecyclerView();
        mPresenter.requestData();

    }

    //继承自父类的方法，父类调用
    @Override
    public void onEmptyViewClick() {
        mPresenter.requestData();
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        //dagger注入
        DaggerRecommendComponent.builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build().inject(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    public void showResult(IndexBean mData) {
        //将数据绑定到recyclerView
        mRecommendAdapter = new IndexMultiAdapter(getContext());
        mRecommendAdapter.setmIndexBean(mData);
        mRecyclerView.setAdapter(mRecommendAdapter);

    }

    @Override
    public void onRequestPermissionSuccess() {


        Toast.makeText(getActivity(),"yes",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionFailed() {
        Toast.makeText(getActivity(),"no",Toast.LENGTH_SHORT).show();
    }
}
