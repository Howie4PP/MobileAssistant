package com.example.shenhaichen.mobileassistant.presenter.contract;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

/**
 * mvp结构中的contract, 可以说是View和presenter的接口方法设定类
 * Created by shenhaichen on 03/01/2018.
 */

public interface AppInfoContract {
    interface View extends BaseView {

        //得到结果
        void showResult(IndexBean mData);

        void onRequestPermissionSuccess();
        void onRequestPermissionFailed();

    }

    interface AppInfoView extends BaseView{
        //得到结果
        void showResult(PageBean<AppInfo> mData);
        void onLoadMoreComplete();
    }

}
