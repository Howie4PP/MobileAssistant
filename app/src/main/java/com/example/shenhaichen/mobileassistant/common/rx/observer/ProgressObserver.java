package com.example.shenhaichen.mobileassistant.common.rx.observer;

import android.content.Context;

import com.example.shenhaichen.mobileassistant.common.exception.BaseException;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

/**
 *  RxJava2中的下游类,封装了progress的方法
 * Created by shenhaichen on 06/01/2018.
 */

public abstract class ProgressObserver<T> extends ErrorHandlerObserver<T> {

    private BaseView view;

    public ProgressObserver(Context context, BaseView view) {
        super(context);
        this.view = view;
    }

    @Override
    public void onComplete() {
        view.disMissLoading();
    }

    @Override
    public void onError(Throwable e) {
        //传送错误的信息
        BaseException baseException = mErrorHandler.handleError(e);
        view.showError(baseException.getDisplayMessage());
    }


}
