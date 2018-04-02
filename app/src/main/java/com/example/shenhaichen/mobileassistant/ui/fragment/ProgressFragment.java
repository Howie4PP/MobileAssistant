package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.shenhaichen.mobileassistant.MyApplication;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.presenter.BasePresenter;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 准备好基本fragment类, 进行progress和 fragment的嵌套
 * Created by shenhaichen on 06/01/2018.
 */

public abstract class ProgressFragment<T extends BasePresenter> extends Fragment implements BaseView{

    private FrameLayout mRootView;
    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
    private Unbinder unbinder;
    private TextView tipTextView;

    protected MyApplication mApplication;

    @Inject
    T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这里是最初的步骤，初始化最外层的布局
        mRootView = (FrameLayout) inflater.inflate(R.layout.fragment_progress, container, false);

        mViewProgress = mRootView.findViewById(R.id.fragment_progress_view_progress);
        mViewContent = mRootView.findViewById(R.id.fragment_progress_view_content);
        mViewEmpty = mRootView.findViewById(R.id.fragment_progress_view_empty);
        tipTextView = mRootView.findViewById(R.id.fragment_progress_text_tip);
        //如果是没有显示任何信息，需要点击重新加载
        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  onEmptyViewClick();
            }
        });

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        第二步，待外层布局初始化完成，在将需要的布局加入到里层的content中
        super.onActivityCreated(savedInstanceState);
        this.mApplication = (MyApplication) getActivity().getApplication();
        setupActivityComponent(mApplication.getmAppComponent());
        setContentLayout();
        init();
    }

    private void setContentLayout() {
        //第一个参数是需要的layout id,第二个参数是需要加入的layout ID, 第三个true表示确认加入
        View view = LayoutInflater.from(getActivity()).inflate(setLayout(), mViewContent, true);
        unbinder = ButterKnife.bind(this, view);
    }

    /**
     * 当三个状态中的一个显示的时候，另外两个必须隐藏起来
     *
     * @param layoutID
     */
    public void showView(int layoutID) {
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            if (mRootView.getChildAt(i).getId() == layoutID) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public abstract void onEmptyViewClick();

    //父类私有方法
    private void showProgress() {
        showView(R.id.fragment_progress_view_progress);
    }
    //父类私有方法
    private void showContentView() {
        showView(R.id.fragment_progress_view_content);
    }
    //父类私有方法
    private void showEmptyView() {
        showView(R.id.fragment_progress_view_empty);
    }
    //父类私有方法
    private void showEmptyView(int resID) {
        showEmptyView();
        tipTextView.setText(resID);
    }
    //父类私有方法
    private void showEmptyView(String resID) {
        showEmptyView();
        tipTextView.setText(resID);
    }

    public abstract int setLayout();

    public abstract void init();

    public abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    public void onDestroy() {
        super.onDestroy();
        //ButterKnife 解除绑定
        if(unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void showError(String mes) {
        showEmptyView(mes);
    }

    @Override
    public void disMissLoading() {
        showContentView();
    }
}
