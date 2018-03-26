package com.example.shenhaichen.mobileassistant.dagger.component;

import com.example.shenhaichen.mobileassistant.dagger.module.LoginModule;
import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.ui.activity.LoginActivity;

import dagger.Component;

/**
 * Created by shenhaichen on 22/03/2018.
 */
@FragmentScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {

    void injectLoginActivity(LoginActivity activity);
}
