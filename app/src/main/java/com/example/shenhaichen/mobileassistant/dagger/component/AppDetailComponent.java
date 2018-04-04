package com.example.shenhaichen.mobileassistant.dagger.component;

import com.example.shenhaichen.mobileassistant.dagger.module.AppDetailModule;
import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.ui.fragment.AppDetailFragment;

import dagger.Component;

/**
 * Created by shenhaichen on 22/03/2018.
 */
@FragmentScope
@Component(modules = AppDetailModule.class, dependencies = AppComponent.class)
public interface AppDetailComponent {

    void inject(AppDetailFragment appDetailFragment);

}
