package com.example.shenhaichen.mobileassistant.data;


import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.SearchResult;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.SearchContract;

import java.util.List;

import io.reactivex.Observable;

public class SearchModel implements SearchContract.ISearchModel {


    private ApiService mApiService;


    public SearchModel(ApiService apiService){

        this.mApiService = apiService;
    }

    public  Observable<BaseBean<List<String>>> getSuggestion(String keyword){


        return  mApiService.searchSuggest(keyword);

    }

    @Override
    public Observable<BaseBean<SearchResult>> search(String keyword) {
        return  mApiService.search(keyword);
    }
}
