package com.example.shenhaichen.mobileassistant.presenter.contract;

import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.LoginBean;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

import io.reactivex.Observable;

/**
 * Created by shenhaichen on 26/03/2018.
 */

public interface LoginContract {

    interface  ILoginModel{

        Observable<BaseBean<LoginBean>> login(String phone, String pwd);
    }


    interface LoginView extends BaseView{
        void checkPhoneError();
        void checkPhoneSuccess();

        void loginSuccess(LoginBean bean);
    }


}
