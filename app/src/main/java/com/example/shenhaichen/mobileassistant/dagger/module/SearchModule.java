package com.example.shenhaichen.mobileassistant.dagger.module;


import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.data.SearchModel;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.SearchContract;

import dagger.Module;
import dagger.Provides;


@Module
public class SearchModule {


    private SearchContract.SearchView mView;


    public SearchModule(SearchContract.SearchView view){

        this.mView = view;
    }

    @FragmentScope
    @Provides
    public SearchContract.ISearchModel provideModel(ApiService apiService){

        return  new SearchModel(apiService);
    }

    @FragmentScope
    @Provides
    public SearchContract.SearchView provideView(){

        return  mView;
    }
}
