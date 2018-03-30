package com.example.shenhaichen.mobileassistant.data;

import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.Category;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.CategoryContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by shenhaichen on 26/03/2018.
 */

public class CategoryModel implements CategoryContract.ICategoryModel{

    private ApiService mApiService;

    public CategoryModel(ApiService apiService){
        this.mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<Category>>> getCategories() {
        return mApiService.getCategories();
    }
}
