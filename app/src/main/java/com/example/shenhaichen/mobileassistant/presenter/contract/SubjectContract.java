package com.example.shenhaichen.mobileassistant.presenter.contract;


import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.bean.Subject;
import com.example.shenhaichen.mobileassistant.bean.SubjectDetail;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

import io.reactivex.Observable;

public class SubjectContract {


    public  interface   SubjectView extends BaseView {


        void showSubjects(PageBean<Subject> subjects);
        void onLoadMoreComplete();

        void showSubjectDetail(SubjectDetail detail);

    }


    public interface ISubjectModel{

        Observable<BaseBean<PageBean<Subject>>> getSubjects(int page);

        Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id);



    }
}
