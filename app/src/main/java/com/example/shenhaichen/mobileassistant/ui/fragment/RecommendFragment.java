package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.adapter.RecommendAdapter;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.presenter.RecommendPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.RecommendContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhaichen on 29/12/2017.
 */

public class RecommendFragment extends Fragment implements RecommendContract.View {

    @BindView(R.id.recommend_recycle_view)
    RecyclerView mRecyclerView;
    private RecommendAdapter mRecommendAdapter;
    private RecommendPresenter mPresenter;
    @BindView(R.id.recommend_progressbar)
    ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recomend,container,false);
        ButterKnife.bind(this, view);
        mPresenter = new RecommendPresenter(this);
        initData();
        return view;
    }

    private void initData(){
         mPresenter.requestData();
    }

    private void initRecyclerView(List<AppInfo> mData){
        mRecommendAdapter = new RecommendAdapter(mData, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(mRecommendAdapter);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showResult(List<AppInfo> mData) {
           //将数据绑定到recyclerView
           initRecyclerView(mData);
    }

    @Override
    public void noData() {
        Toast.makeText(getContext(),"无数据啊~~",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(String error) {
        Toast.makeText(getContext(),"无服务器错误啊,"+error.toString(),Toast.LENGTH_SHORT).show();
    }
}
