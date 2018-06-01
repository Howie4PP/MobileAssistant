package com.example.shenhaichen.mobileassistant.ui.fragment;


import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.bean.Subject;
import com.example.shenhaichen.mobileassistant.bean.SubjectDetail;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerSubjectComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.SubjectModule;
import com.example.shenhaichen.mobileassistant.presenter.SubjectPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.SubjectContract;

public abstract class BaseSubjectFragment extends ProgressFragment<SubjectPresenter> implements SubjectContract.SubjectView {

    @Override
    public void showSubjects(PageBean<Subject> subjects) {

    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {

    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectComponent.builder().appComponent(appComponent).subjectModule(new SubjectModule(this))
                .build().inject(this);
    }
}
