package com.example.shenhaichen.mobileassistant.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.LoginBean;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.dagger.component.DaggerLoginComponent;
import com.example.shenhaichen.mobileassistant.dagger.module.LoginModule;
import com.example.shenhaichen.mobileassistant.presenter.LoginPresenter;
import com.example.shenhaichen.mobileassistant.presenter.contract.LoginContract;
import com.example.shenhaichen.mobileassistant.ui.widget.LoadingButton;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    public static final String DE = LoginActivity.class.getSimpleName();

    @BindView(R.id.login_btn)
    LoadingButton loginBtn;

    @BindView(R.id.login_tool_bar)
    Toolbar mToolBar;

    @BindView(R.id.txt_mobi)
    EditText txt_mobile;
    @BindView(R.id.txt_password)
    EditText txt_pwd;

    @BindView(R.id.view_mobi_wrapper)
    TextInputLayout mMobileWrapper;
    @BindView(R.id.view_password_wrapper)
    TextInputLayout mPwdWrapper;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build().injectLoginActivity(this);
    }

    @Override
    public void init() {
        initView();
    }

    private void initView() {

        mToolBar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        InitialValueObservable<CharSequence> obMobile = RxTextView.textChanges(txt_mobile);
        InitialValueObservable<CharSequence> obPassword = RxTextView.textChanges(txt_pwd);
        //将两个控件连通其值一起绑定
        InitialValueObservable.combineLatest(obMobile, obPassword, new BiFunction<CharSequence, CharSequence, Boolean>() {

            @Override
            public Boolean apply(CharSequence mobile, CharSequence pwd) throws Exception {
                //判断输入的手机号和密码是否符合要求
                return (isPhoneValid(mobile.toString()) && isPwdValid(pwd.toString()));
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                RxView.enabled(loginBtn).accept(aBoolean);
            }
        });

        //button的时间点击事件
        RxView.clicks(loginBtn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                mPresenter.login(txt_mobile.getText().toString().trim(), txt_pwd.getText().toString().trim());
//                Log.d(DE,txt_mobile.getText().toString().trim());
//                Log.d(DE,txt_pwd.getText().toString().trim());
            }
        });
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPwdValid(String pwd) {
        return pwd.length() >= 6;
    }

    @Override
    public void loginSuccess(LoginBean bean) {
//        Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void showLoading() {
         loginBtn.showLoading();
    }

    @Override
    public void disMissLoading() {
        loginBtn.showButtonText();
    }

    @Override
    public void showError(String mes) {
        loginBtn.showButtonText();
    }

    @Override
    public void checkPhoneError() {

        mMobileWrapper.setError("手机号格式不正确");
    }

    @Override
    public void checkPhoneSuccess() {
        mMobileWrapper.setError("");
        mMobileWrapper.setErrorEnabled(false);
    }


}
