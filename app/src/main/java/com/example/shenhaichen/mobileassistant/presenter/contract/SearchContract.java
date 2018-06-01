package com.example.shenhaichen.mobileassistant.presenter.contract;

import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.SearchResult;
import com.example.shenhaichen.mobileassistant.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public class SearchContract {


    public  interface  SearchView extends BaseView {

        void showSearchHistory(List<String> list);
        void showSuggestions(List<String> list);
        void showSearchResult(SearchResult result);

    }


    public interface ISearchModel{

        Observable<BaseBean<List<String>>> getSuggestion(String keyword);

        Observable<BaseBean<SearchResult>> search(String keyword);

    }
}
