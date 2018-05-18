package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.presenter.AppInfoPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;
import com.example.shenhaichen.mobileassistant.ui.activity.AppDetailActivity;
import com.example.shenhaichen.mobileassistant.ui.adapter.AppInfoAdapter;
import com.example.shenhaichen.mobileassistant.ui.decoration.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import zlc.season.rxdownload2.RxDownload;

/**
 * 排行和游戏fragment的父类，因为两个fragment的大致结构相同
 * 只是所读取的数据不相同
 * Created by shenhaichen on 25/03/2018.
 */

public abstract class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenter> implements
        AppInfoContract.AppInfoView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    @Inject
    RxDownload mRxDownload;

    protected AppInfoAdapter mAdapter;

    public int page = 0;
    public int type = 0;

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    public void init() {
        type = getType();
        mPresenter.requestData(type, page);
        initRecyclerView();
    }

    @Override
    public void onEmptyViewClick() {
        mPresenter.requestData(type, page);
    }

    /**
     * 根据不同的fragment,去设定不同的类型来获取数据
     *
     * @return
     */
    abstract int getType();

    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        // adapter 根据不同的需要设置其控件的值
        mAdapter = buildAdapter();
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

        //每一个app项目都必须要跳到具体的app页面
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                AppInfo appInfo = mAdapter.getItem(position);
                // 这是保存被点击的item的图像
                mApplication.setmView(view);
                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                intent.putExtra(Constant.APPINTO,appInfo);
                startActivity(intent);
            }
        });
    }

    /**
     * 根据不同的fragment,去实例化不同的adapter
     *
     * @return
     */
    abstract AppInfoAdapter buildAdapter();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public void showResult(PageBean<AppInfo> mData) {
        mAdapter.addData(mData.getDatas());
        //如果有多页的数据
        if (mData.isHasMore()) {
            page++;
        }
        mAdapter.setEnableLoadMore(mData.isHasMore());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(type, page);
    }

    @Override
    public void onLoadMoreComplete() {
        mAdapter.loadMoreComplete();
    }

}
