package com.example.shenhaichen.mobileassistant.presenter;

import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.bean.BaseBean;
import com.example.shenhaichen.mobileassistant.bean.PageBean;
import com.example.shenhaichen.mobileassistant.common.rx.RxHttpResponseCompat;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ErrorHandlerObserver;
import com.example.shenhaichen.mobileassistant.common.rx.observer.ProgressObserver;
import com.example.shenhaichen.mobileassistant.data.AppInfoModel;
import com.example.shenhaichen.mobileassistant.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by shenhaichen on 22/03/2018.
 */

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    public static final int RANKING = 1;
    public static final int GAME = 2;
    public static final int CATEGORY = 3;

    public static final int FEATURED = 0;
    public static final int TOPLIST = 1;
    public static final int LATEST = 2;

    @Inject
    public AppInfoPresenter(AppInfoModel appInfoModel, AppInfoContract.AppInfoView rankingView) {
        super(appInfoModel, rankingView);
    }

    /**
     * 要求普通的APP数据
     *
     * @param type
     * @param page
     */
    public void requestData(int type, int page) {
        request(type, page, 0, 0);
    }

    /**
     * 请求分类App数据
     *
     * @param page
     * @param categoryId
     * @param flagType
     */
    public void requestCategory(int page, int categoryId, int flagType) {
        request(CATEGORY, page, categoryId, flagType);
    }

    private void request(int type, int page, int categoryId, int flagType) {
        //在下拉加载的时候，是需要判断的页码的, 重复调用的相同的话，会覆盖掉原来的数据
        Observer observer = null;

        if (page == 0) {
            //加载第一页
            observer = new ProgressObserver<PageBean<AppInfo>>(mContext, mView) {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(PageBean<AppInfo> appInfoPageBean) {
                    mView.showResult(appInfoPageBean);
                }
            };
        } else {
            //加载下一页
            observer = new ErrorHandlerObserver<PageBean<AppInfo>>(mContext) {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(PageBean<AppInfo> appInfoPageBean) {
                    mView.showResult(appInfoPageBean);
                }

                @Override
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }
            };
        }

        getObservable(type, page, categoryId, flagType)
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(observer);

    }

    /**
     * 根据不同的要求，去请求不同的数据来源
     *
     * @param type
     * @param page
     * @return
     */
    private Observable<BaseBean<PageBean<AppInfo>>> getObservable(int type, int page, int categoryId, int flagType) {
        switch (type) {
            case RANKING:
                return mModel.toplist(page);
            case GAME:
                return mModel.game(page);
            case CATEGORY:
                //去获得不同的分类app
                if (flagType == FEATURED) {
                    return mModel.getFeaturedAppsByCategory(categoryId, page);
                } else if (flagType == TOPLIST) {
                    return mModel.getTopListAppsByCategory(categoryId, page);
                } else if (flagType == LATEST) {
                    return mModel.getNewListAppsByCategory(categoryId, page);
                }
            default:
                return Observable.empty();
        }

    }
}
