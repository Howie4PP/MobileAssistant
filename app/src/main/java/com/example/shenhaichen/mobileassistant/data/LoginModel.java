package com.example.shenhaichen.mobileassistant.data;

import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.LoginBean;
import com.example.shenhaichen.mobileassistant.bean.requesbean.LoginRequestBean;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.LoginContract;

import io.reactivex.Observable;

/**
 * Created by shenhaichen on 26/03/2018.
 */

public class LoginModel implements LoginContract.ILoginModel {

    private ApiService mApiService;

    public LoginModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    @Override
    public Observable<BaseBean<LoginBean>> login(String phone, String pwd) {

        LoginRequestBean requestBean = new LoginRequestBean(phone,pwd);

        return mApiService.login(requestBean);
    }
}
