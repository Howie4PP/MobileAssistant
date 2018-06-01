package com.example.shenhaichen.mobileassistant.data;


import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.bean.Subject;
import com.example.shenhaichen.mobileassistant.bean.SubjectDetail;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.SubjectContract;

import io.reactivex.Observable;

public class SubjectModel implements SubjectContract.ISubjectModel {


    private ApiService mApiService;
    public SubjectModel(ApiService apiService){
        this.mApiService = apiService;

    }


    @Override
    public Observable<BaseBean<PageBean<Subject>>> getSubjects(int page) {
        return mApiService.subjects(page);
    }

    @Override
    public Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id) {
        return mApiService.subjectDetail(id);
    }
}
