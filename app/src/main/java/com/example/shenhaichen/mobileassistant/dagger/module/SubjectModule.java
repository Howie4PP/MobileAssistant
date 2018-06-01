package com.example.shenhaichen.mobileassistant.dagger.module;


import com.example.shenhaichen.mobileassistant.dagger.scope.FragmentScope;
import com.example.shenhaichen.mobileassistant.data.SubjectModel;
import com.example.shenhaichen.mobileassistant.data.network.ApiService;
import com.example.shenhaichen.mobileassistant.presenter.contract.SubjectContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SubjectModule {


    private SubjectContract.SubjectView mView;


    public SubjectModule(SubjectContract.SubjectView view){
        this.mView = view;
    }



    @FragmentScope
    @Provides
    public SubjectContract.ISubjectModel provideModel(ApiService apiService){

        return  new SubjectModel(apiService);
    }



    @FragmentScope
    @Provides
    public SubjectContract.SubjectView provideView(){


        return  mView;
    }
}
