package com.sh3h.meterreading.ui.outside;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2016/4/15.
 */
public class OutsideListPresenter extends ParentPresenter<OutsideListMvpView> {
    private static final String TAG = "OutsideListPresenter";

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public OutsideListPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
    }

    public void loadCardXXs() {
        LogUtil.i(TAG, "---loadCardXXs 1---");
        DUCardInfo duCardInfo = new DUCardInfo();
        UserSession userSession = mPreferencesHelper.getUserSession();
        duCardInfo.setAccount(userSession.getAccount());
        duCardInfo.setFilterType(DUCardInfo.FilterType.WAIFU);
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
                        LogUtil.i(TAG, "---loadCardXXs onNext ---");
                        getMvpView().onLoadCards(duCardList);
                    }
                }));

    }

    public void loadWaiFuCBSJS(DUWaiFuCBSJInfo.FilterType filterType,
                               String key,
                               long limit) {
        LogUtil.i(TAG, "---loadWaiFuCBSJS 1---");
        UserSession userSession = mPreferencesHelper.getUserSession();
        DUWaiFuCBSJInfo duWaiFuCBSJInfo = new DUWaiFuCBSJInfo(
                filterType,
                TextUtil.getString(key),
                TextUtil.getString(userSession.getAccount())
        );
        mSubscription.add(mDataManager.getWaiFuCBSJS(duWaiFuCBSJInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUWaiFuCBSJ>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadWaiFuCBSJS onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadWaiFuCBSJS onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUWaiFuCBSJ> duWaiFuCBSJs) {
                        LogUtil.i(TAG, "---loadWaiFuCBSJS onNext ---");
                        getMvpView().loadWaiFuCBSJS(duWaiFuCBSJs);
                    }
                }));
    }

}
