package com.example.shenhaichen.mobileassistant.presenter;


import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.bean.Subject;
import com.example.shenhaichen.mobileassistant.bean.SubjectDetail;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ErrorHandlerObserver;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ProgressObserver;
import com.example.shenhaichen.mobileassistant.presenter.contract.SubjectContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class SubjectPresenter extends BasePresenter<SubjectContract.ISubjectModel,SubjectContract.SubjectView> {


    @Inject
    public SubjectPresenter(SubjectContract.ISubjectModel iSubjectModel,
                            SubjectContract.SubjectView subjectView) {
        super(iSubjectModel, subjectView);
    }




    public void  getSubjects(int page){


        Observer subscriber =null;

        if(page ==0){

            subscriber= new ProgressObserver<PageBean<Subject>>(mContext,mView) {
                @Override
                public void onNext(PageBean<Subject> pageBean) {
                    mView.showSubjects(pageBean);
                }
            };
        }
        else{
            subscriber = new ErrorHandlerObserver<PageBean<Subject>>(mContext) {
                @Override
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(PageBean<Subject> pageBean) {

                    mView.showSubjects(pageBean);
                }
            };
        }

        mModel.getSubjects(page)
                .compose(RxHttpResponseCompat.<PageBean<Subject>>compatResult())
                .subscribe(subscriber);


    }



    public void getSubjectDetail(int id){

        mModel.getSubjectDetail(id).compose(RxHttpResponseCompat.<SubjectDetail>compatResult())
                .subscribe(new ProgressObserver<SubjectDetail>(mContext,mView) {
                    @Override
                    public void onNext(SubjectDetail subjectDetail) {

                        mView.showSubjectDetail(subjectDetail);
                    }
                });


    }










}
