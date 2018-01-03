package com.example.shenhaichen.mobileassistant.dagger.component;

import com.example.shenhaichen.mobileassistant.dagger.module.RecommendModule;
import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.ui.fragment.RecommendFragment;

import dagger.Component;

/**
 * 关联module
 * Created by shenhaichen on 03/01/2018.
 */
@FragmentScope
@Component(modules = RecommendModule.class, dependencies = AppComponent.class)
public interface RecommendComponent {
    void inject(RecommendFragment recommendFragment);
}
