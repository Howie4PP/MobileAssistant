package com.example.shenhaichen.mobileassistant.presenter.contract;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.requesbean.AppsUpdateBean;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;


public class MainContract {

    public  interface   MainView extends BaseView {

        void requestPermissionSuccess();
        void requestPermissionFail();

        void changeAppNeedUpdateCount(int count);

    }

    public interface IMainModel{
        Observable<BaseBean<List<AppInfo>>> getUpdateApps(AppsUpdateBean param);

    }
}
