package com.sh3h.meterreading.ui.sampling;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingInfo;
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
 * Created by xulongjun on 2016/11/24.
 */
public class SamplingListPresenter extends ParentPresenter<SamplingListMvpView> {
    private static final String TAG = "SamplingPresenter";
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public SamplingListPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
    }

    public void loadCardXXs(int taskId) {
            LogUtil.i(TAG, "---loadCardXXs ---");
            DUCardInfo duCardInfo = new DUCardInfo();
            duCardInfo.setTaskId(taskId);
            duCardInfo.setFilterType(DUCardInfo.FilterType.SAMPLING);
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

    public void loadRecordXXs(DUSamplingInfo.FilterType filterType,
                              int taskId,
                              String key,
                              long limit) {
        LogUtil.i(TAG, "---loadRecordXXs 1---");

            LogUtil.i(TAG, "---loadRecordXXs 3---");
            UserSession userSession = mPreferencesHelper.getUserSession();
            DUSamplingInfo duRecordInfo = new DUSamplingInfo(
                    filterType,
                    TextUtil.getString(userSession.getAccount()),
                    taskId,
                    "",
                    TextUtil.getString(key),
                    limit);
            mSubscription.add(mDataManager.getSamplings(duRecordInfo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<DUSampling>>() {
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
                        public void onNext(List<DUSampling> duSamplingList) {
                            LogUtil.i(TAG, "---loadRecordXXs onNext ---");
                            getMvpView().onLoadRecords(duSamplingList);
                        }
                    }));
    }
}
