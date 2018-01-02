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

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.adapter.RecommendAdapter;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.network.ApiService;
import com.example.shenhaichen.mobileassistant.network.HttpManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shenhaichen on 29/12/2017.
 */

public class RecommendFragment extends Fragment {

    @BindView(R.id.recommend_recycle_view)
    RecyclerView mRecyclerView;
    private RecommendAdapter mRecommendAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recomend,container,false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData(){
        HttpManager mManager = new HttpManager();
        ApiService mService = mManager.getRetrofit(mManager.getOkHttpClient()).create(ApiService.class);

        mService.getApps("{'page':0}").enqueue(new Callback<PageBean<AppInfo>>() {
            @Override
            public void onResponse(Call<PageBean<AppInfo>> call, Response<PageBean<AppInfo>> response) {
                PageBean<AppInfo> pageBean = response.body();
                List<AppInfo> mDatas = pageBean.getDatas();
                initRecyclerView(mDatas);
            }

            @Override
            public void onFailure(Call<PageBean<AppInfo>> call, Throwable t) {

            }
        });
    }

    private void initRecyclerView(List<AppInfo> mDatas){
        mRecommendAdapter = new RecommendAdapter(mDatas, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(mRecommendAdapter);
    }

}
