package com.example.shenhaichen.mobileassistant.dagger.component;


import com.example.shenhaichen.mobileassistant.dagger.module.MainModule;
import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.ui.activity.MainActivity;

import dagger.Component;



@FragmentScope
@Component(modules = MainModule.class,dependencies= AppComponent.class)
public interface MainComponent {

    void  inject(MainActivity activity);
}
