package com.example.shenhaichen.mobileassistant.dagger.component;


import com.example.shenhaichen.mobileassistant.dagger.module.AppManagerModule;
import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.ui.fragment.DownloadedFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.DownloadingFragment;
import com.example.shenhaichen.mobileassistant.ui.fragment.InstalledAppAppFragment;

import dagger.Component;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.di
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

@FragmentScope
@Component(modules = AppManagerModule.class,dependencies = AppComponent.class)
public interface AppManagerComponent {

    void inject(DownloadingFragment fragment);
    void injectDownloaded(DownloadedFragment fragment);
    void injectInstalled(InstalledAppAppFragment fragment);

}
