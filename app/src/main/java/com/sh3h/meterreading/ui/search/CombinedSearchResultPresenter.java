package com.sh3h.meterreading.ui.search;


import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CombinedSearchResultPresenter extends ParentPresenter<CombinedSearchResultMvpView> {
    private static final String TAG = "CombinedSearchResultPresenter";

    private List<DUCard> duCardList;

    private boolean isLocalSearch;

    @Inject
    public CombinedSearchResultPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getCombinedResults(DUCombinedInfo duCombinedInfo, boolean isLocalSearch) {
        if (duCombinedInfo == null) {
            return;
        }
        duCardList = null;
        mSubscription.add(mDataManager.getCombinedResult(duCombinedInfo, isLocalSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getCombinedResults onCompleted---");
                        getMvpView().onCompleted(duCardList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getCombinedResults onError: %s---",
                                e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCard> duCards) {
                        LogUtil.i(TAG, "---getCombinedResults onNext---");
                        duCardList = duCards;
                    }
                }));
    }

}
