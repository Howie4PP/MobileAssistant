package com.example.shenhaichen.mobileassistant.dagger.component;

import com.example.shenhaichen.mobileassistant.dagger.module.AppInfoModule;
import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.ui.fragment.GamesFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.RankingFragment;

import dagger.Component;

/**
 * Created by shenhaichen on 22/03/2018.
 */
@FragmentScope
@Component(modules = AppInfoModule.class, dependencies = AppComponent.class)
public interface AppInfoComponent {

    void injectRankingFragment(RankingFragment fragment);
    void injectGameFragment(GamesFragment fragment);
}
