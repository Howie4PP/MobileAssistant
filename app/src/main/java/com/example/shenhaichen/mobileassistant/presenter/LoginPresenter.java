package com.example.shenhaichen.mobileassistant.presenter;

import com.example.shenhaichen.mobileassistant.bean.LoginBean;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ErrorHandlerObserver;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.common.util.VerificationUtils;
import com.example.shenhaichen.mobileassistant.presenter.contract.LoginContract;
import com.hwangjr.rxbus.RxBus;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by shenhaichen on 26/03/2018.
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginModel, LoginContract.LoginView> {

    @Inject
    public LoginPresenter(LoginContract.ILoginModel iLoginModel, LoginContract.LoginView loginView) {
        super(iLoginModel, loginView);
    }

    public void login(String phone, String pwd){
        if (!VerificationUtils.matcherPhoneNum(phone)){
            mView.checkPhoneError();
            return;
        }else {
            mView.checkPhoneSuccess();
        }

        mModel.login(phone,pwd).compose(RxHttpResponseCompat.<LoginBean>compatResult())
                .subscribe(new ErrorHandlerObserver<LoginBean>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.loginSuccess(loginBean);
                        saveUserInfo(loginBean);

                        //RxBus 发送数据
                        RxBus.get().post(loginBean.getUser());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveUserInfo(LoginBean loginBean){
        ACache aCache = ACache.get(mContext);

        aCache.put(Constant.TOKEN, loginBean.getToken());
        aCache.put(Constant.USER, loginBean.getToken());
    }

}
