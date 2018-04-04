package com.example.shenhaichen.mobileassistant.data;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;

import io.reactivex.Observable;

/**
 * MVP 架构的 model,负责逻辑处理，这里是调用网络请求
 * Created by shenhaichen on 03/01/2018.
 */

public class AppInfoModel {

    private ApiService mApiService;

    public AppInfoModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    /**
     * @return 返回一个时间源在外部调用
     */
//    public Observable<BaseBean<PageBean<AppInfo>>> getApps(){
//
//       return mApiService.getApps("{'page':0}");
//
//    }

    //请求banner数据
    public Observable<BaseBean<IndexBean>> index() {
        return mApiService.index();
    }

    public Observable<BaseBean<PageBean<AppInfo>>> toplist(int page) {
        return mApiService.toplist(page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> game(int page) {
        return mApiService.game(page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getFeaturedAppsByCategory(int categoryid, int page) {
        return mApiService.getFeaturedAppsByCategory(categoryid, page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getTopListAppsByCategory(int categoryid, int page) {
        return mApiService.getTopListAppsByCategory(categoryid, page);
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getNewListAppsByCategory(int categoryid, int page) {
        return mApiService.getNewListAppsByCategory(categoryid, page);
    }

    public Observable<BaseBean<AppInfo>> getAppDetail(int id) {
        return mApiService.getAppDetail(id);
    }

}
