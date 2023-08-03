package com.sh3h.meterreading.ui.lgld;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.datautil.data.entity.DUDelayRecordInfo;
import com.sh3h.datautil.data.entity.DULgld;
import com.sh3h.datautil.data.entity.DULgldInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiMeng on 2017/10/30.
 */
public class LgldListPresenter extends ParentPresenter<LgldListMvpView>{
    private static final String TAG = "LgldListPresenter";
    private String account;

    @Inject
    LgldListPresenter(DataManager dataManager, PreferencesHelper helper) {
        super(dataManager);
        account = helper.getUserSession().getAccount();
    }

    public void loadCardXXs() {
        LogUtil.i(TAG, "---loadCardXXs 1---");
        DUCardInfo duCardInfo = new DUCardInfo(account);
        duCardInfo.setFilterType(DUCardInfo.FilterType.ALL_NO_CONDITION);
        mSubscription.add(mDataManager.getCards(duCardInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadCardXXs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadCardXXs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCard> duCardList) {
                        getMvpView().onLoadCards(duCardList);
                    }
                }));
    }

    public void loadRecordXXs(int filter) {
        LogUtil.i(TAG, "---loadRecordXXs 1---");
        DULgldInfo info = new DULgldInfo(account, filter);
        mSubscription.add(mDataManager.getLglds(info)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DULgld>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadRecordXXs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadRecordXXs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DULgld> duLglds) {
                        LogUtil.i(TAG, "---loadRecordXXs onNext ---");
                        getMvpView().onLoadRecords(duLglds);
                    }
                }));
    }

}
