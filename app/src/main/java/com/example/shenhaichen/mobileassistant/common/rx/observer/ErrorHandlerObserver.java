package com.example.shenhaichen.mobileassistant.common.rx.observer;

import android.content.Context;

import com.example.shenhaichen.mobileassistant.common.exception.BaseException;
import com.example.shenhaichen.mobileassistant.common.rx.RxErrorHandler;

/**
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
//
//        if(baseException==null){
//            e.printStackTrace();
//            Log.d("ErrorHandlerSubscriber",e.getMessage());
//        }
//        else {
            mErrorHandler.showErrorMessage(baseException);
//        }

    }
}
