package com.example.shenhaichen.mobileassistant.common.rx.observer;

/**
 * Created by shenhaichen on 06/01/2018.
 */

public abstract class ProgressDialogObserver<T> extends BaseObserver<T> {

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }



    @Override
    public void onError(Throwable e) {

    }
    private void dismissProgressDialog(){

    }
}
