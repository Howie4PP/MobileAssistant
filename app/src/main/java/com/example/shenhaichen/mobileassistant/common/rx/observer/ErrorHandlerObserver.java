package com.example.shenhaichen.mobileassistant.common.rx.observer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.shenhaichen.mobileassistant.common.exception.BaseException;
import com.example.shenhaichen.mobileassistant.common.rx.RxErrorHandler;
import com.example.shenhaichen.mobileassistant.ui.activity.LoginActivity;

/**
 * RxJava2中的下游类,封装了Error报告的方法
 * Created by shenhaichen on 06/01/2018.
 */

public abstract class ErrorHandlerObserver<T> extends BaseObserver<T> {
    protected RxErrorHandler mErrorHandler = null;

    protected Context mContext;

    public ErrorHandlerObserver(Context context){

        this.mContext = context;

        mErrorHandler = new RxErrorHandler(context);

    }

    @Override
    public void onError(Throwable e) {
        //打印错误信息在控制台上
        e.printStackTrace();

        BaseException baseException =  mErrorHandler.handleError(e);

        if(baseException==null){
            e.printStackTrace();
            Log.d("ErrorHandlerSubscriber",e.getMessage());
        }
        else {
            //在token失效后，需要跳转至登录界面
            mErrorHandler.showErrorMessage(baseException);
            if (baseException.getCode() == BaseException.ERROR_API_TOKEN_FAILURE){
                toLogin();
            }
        }

    }

    private void toLogin() {
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }
}
