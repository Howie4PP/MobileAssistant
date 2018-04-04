package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.imageloader.ImageLoader;
import com.example.shenhaichen.mobileassistant.common.util.DateUtils;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerAppDetailComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.AppDetailModule;
import com.example.shenhaichen.mobileassistant.presenter.AppDetailPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;
import com.example.shenhaichen.mobileassistant.ui.adapter.AppInfoAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;

/**
 * Created by shenhaichen on 04/04/2018.
 */

public class AppDetailFragment extends ProgressFragment<AppDetailPresenter> implements AppInfoContract.AppDetailView {

    @BindView(R.id.img_icon_app)
    ImageView mImgIconApp;
    @BindView(R.id.text_app)
    TextView mTextApp;
    @BindView(R.id.view_gallery_app)
    LinearLayout mViewGallery;
    @BindView(R.id.expandable_text)
    TextView mExpandableText;
    @BindView(R.id.expand_collapse)
    ImageButton mExpandableCollapse;
    @BindView(R.id.view_introduction_app)
    ExpandableTextView mViewIntroductionApp;
    @BindView(R.id.txt_update_time_app)
    TextView mTxtUpdateTimeApp;
    @BindView(R.id.txt_version_app)
    TextView mTxtVersionApp;
    @BindView(R.id.txt_apk_size_app)
    TextView mTxtApkSizeApp;
    @BindView(R.id.txt_publisher_app)
    TextView mTxtPublisherApp;
    @BindView(R.id.txt_publisher2_app)
    TextView mTxtPublisher2App;
    @BindView(R.id.recycler_view_same_dev_app)
    RecyclerView mRecyclerViewSameDevApp;
    @BindView(R.id.recycler_view_relate_app)
    RecyclerView mRecyclerViewRelateApp;

    private int mAppId;
    private LayoutInflater mInflater;
    private AppInfoAdapter mAdapter;

    public AppDetailFragment() {
    }

    @SuppressLint("ValidFragment")
    public AppDetailFragment(int id) {
        this.mAppId = id;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_app_detail;
    }

    @Override
    public void init() {

        mInflater = LayoutInflater.from(getActivity());
        mPresenter.getAppDetail(mAppId);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerAppDetailComponent.builder().appComponent(appComponent)
                .appDetailModule(new AppDetailModule(this))
                .build().inject(this);
    }

    @Override
    public void showAppDetail(AppInfo appInfo) {

        showScreenShot(appInfo.getScreenshot());
        mViewIntroductionApp.setText(appInfo.getIntroduction());
        //获取App基本信息
        mTxtUpdateTimeApp.setText(DateUtils.formatDate(appInfo.getUpdateTime()));
        mTxtApkSizeApp.setText((appInfo.getApkSize() / 1014 / 1024) + "mb");
        mTxtPublisherApp.setText(appInfo.getPublisherName());
        mTxtPublisher2App.setText(appInfo.getPublisherName());

        showHorizontalViewApp(appInfo);

    }

    /**
     * 在linearLayout中不断的加入imageView
     */
    private void showScreenShot(String screenShot) {
        String[] urls = screenShot.split(",");
        for (String url : urls) {
            ImageView imageView = (ImageView) mInflater.inflate(R.layout.template_imageview, mViewGallery, false);
            ImageLoader.load(Constant.BASE_IMG_URL + url, imageView);
            mViewGallery.addView(imageView);
        }

    }

    /**
     * 展示横向列表的app
     * @param appInfo
     */
    private void showHorizontalViewApp(AppInfo appInfo){

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo_vertical)
                   .build();
        // 在设置不同的recyclerView的时候，不能使用相同的LayoutManager，要不然会报错
        mRecyclerViewSameDevApp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        mAdapter.addData(appInfo.getSameDevAppInfoList());
        mRecyclerViewSameDevApp.setAdapter(mAdapter);

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo_vertical)
                .build();
        mRecyclerViewRelateApp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        mAdapter.addData(appInfo.getRelateAppInfoList());
        mRecyclerViewRelateApp.setAdapter(mAdapter);
    }


}
