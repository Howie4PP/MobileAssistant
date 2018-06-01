package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.Subject;
import com.example.shenhaichen.mobileassistant.bean.SubjectDetail;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.imageloader.ImageLoader;
import com.example.shenhaichen.mobileassistant.ui.adapter.AppInfoAdapter;
import com.example.shenhaichen.mobileassistant.ui.decoration.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import zlc.season.rxdownload2.RxDownload;

public class SubjectDetailFragment extends BaseSubjectFragment {


    @BindView(R.id.imageview)
    ImageView mImageView;
    @BindView(R.id.txt_desc)
    TextView mtxtDesc;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Inject
    RxDownload mRxDownload;



    private Subject mSubject;

    private AppInfoAdapter mAdapter;


    @SuppressLint("ValidFragment")
    public SubjectDetailFragment(Subject subject){

        this.mSubject = subject;

    }

    @Override
    public void init() {

        initRecycleView();

        mPresenter.getSubjectDetail(mSubject.getRelatedId());


    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {

        ImageLoader.load(Constant.BASE_IMG_URL + detail.getPhoneBigIcon(),mImageView);

        mtxtDesc.setText(detail.getDescription());

        mAdapter.addData(detail.getListApp());



    }

    private void initRecycleView() {

        mAdapter = AppInfoAdapter.builder().showBrief(false).showCategoryName(true)
                .rxDownload(mRxDownload).build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_subject_detail;
    }
}
