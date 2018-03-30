package com.example.shenhaichen.mobileassistant.presenter.contract;

import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.Category;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by shenhaichen on 26/03/2018.
 */

public interface CategoryContract {

    interface  ICategoryModel{
        Observable<BaseBean<List<Category>>> getCategories();
    }

    interface CategoryView extends BaseView{
         void showData(List<Category> categories);
    }


}
