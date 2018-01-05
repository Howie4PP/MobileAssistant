package com.example.shenhaichen.mobileassistant.common.rx;

import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.common.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * rx 转换类， basebean to pagebean
 * Created by shenhaichen on 05/01/2018.
 */

public class RxHttpResponseCompat {

    //返回一个Transformer去转换泛型
    public static <T> ObservableTransformer<BaseBean<T>, T> compatResult(){
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
                //Basebean 转为 T
                return upstream.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                        @Override
                    public ObservableSource<T> apply(final BaseBean<T> tBaseBean) throws Exception {
                        if (tBaseBean.success()){
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                                    try {
                                        subscriber.onNext(tBaseBean.getData());
                                        subscriber.onComplete();
                                    }
                                    catch (Exception e){
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        }else{
                            //抛出异常
                            return  Observable.error(new ApiException(tBaseBean.getStatus(),tBaseBean.getMessage()));
                        }
                    }
                    //Schedulers.io()表示放到异步线程中进行网络操作, 而后返回主线程操作UI
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }
}
