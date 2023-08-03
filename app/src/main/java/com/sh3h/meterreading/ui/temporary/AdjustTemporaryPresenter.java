package com.sh3h.meterreading.ui.temporary;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskInfo;
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
 * Created by xulongjun on 2016/2/22.
 */
public class AdjustTemporaryPresenter extends ParentPresenter<AdjustTemporaryMvpView> {

    private static final String TAG = "AdjustTemporaryPresenter";
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AdjustTemporaryPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
    }

    /**
     *
     */
    public void loadChaoBiaoRWs() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        DUTaskInfo duTaskInfo = new DUTaskInfo(userSession.getAccount());
        mSubscription.add(mDataManager.getTasks(duTaskInfo, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadChaoBiaoRWs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadChaoBiaoRWs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUTask> duTaskLst) {
                        LogUtil.i(TAG, "---loadChaoBiaoRWs: onloadTasks---");
                        getMvpView().onLoadTasks(duTaskLst);
                    }
                })
        );
    }

    /**
     *
     */
    public void loadRecordXXs() {
        LogUtil.i(TAG, "---loadRecordXXs 1---");

        DURecordInfo duRecordInfo = new DURecordInfo();
        UserSession userSession = mPreferencesHelper.getUserSession();
        duRecordInfo.setAccount(userSession.getAccount());
        duRecordInfo.setFilterType(DURecordInfo.FilterType.TEMPOPRARYDATA);
        mSubscription.add(mDataManager.getRecords(duRecordInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DURecord>>() {
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
                    public void onNext(List<DURecord> duRecordList) {
                        LogUtil.i(TAG, "---loadRecordXXs onNext ---");
                        getMvpView().onLoadRecords(duRecordList);
                    }
                })
        );
    }

    /**
     *
     */
    public void loadCardXXs() {
        LogUtil.i(TAG, "---loadCardXXs 3---");
        DUCardInfo duCardInfo = new DUCardInfo();
        UserSession userSession = mPreferencesHelper.getUserSession();
        duCardInfo.setAccount(userSession.getAccount());
        duCardInfo.setFilterType(DUCardInfo.FilterType.TEMPORARY);
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
                        LogUtil.i(TAG, "---loadCardXXs onNext---");
                        getMvpView().onLoadCards(duCardList);
                    }
                })
        );
    }

    /**
     *
     * @param duCardInfo
     */
    public void loadDestinationCards(DUCardInfo duCardInfo) {
        LogUtil.i(TAG, "---loadDestinationCards 1---");
        mSubscription.add(mDataManager.getCards(duCardInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadDestinationCards onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadDestinationCards onError: %s---",
                                e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCard> duCardList) {
                        LogUtil.i(TAG, "---loadDestinationCards onNext---");
                        getMvpView().onLoadDestinationCards(duCardList);
                    }
                })
        );
    }

    /**
     *
     * @param duCardList
     * @param duRecordList
     * @param duTask
     * @param cardId
     * @param orderNumber
     * @param isCardId
     * @param isFront
     */
    public void adjustCardAndRecords(List<DUCard> duCardList,
                                     List<DURecord> duRecordList,
                                     DUTask duTask,
                                     String cardId,
                                     int orderNumber,
                                     boolean isCardId,
                                     boolean isFront) {
        LogUtil.i(TAG, "---adjustCardAndRecords 1---");
        if ((duCardList == null)
                || (duCardList.size() <= 0)
                || (duRecordList == null)
                || (duCardList.size() != duRecordList.size())
                || (duTask == null)
                || (duTask.getRenWuBH() <= 0)
                || TextUtil.isNullOrEmpty(duTask.getcH())
                || TextUtil.isNullOrEmpty(cardId)) {
            getMvpView().onError("parameter is error");
            LogUtil.i(TAG, "---adjustCardAndRecords 2---");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        mSubscription.add(mDataManager.adjustCardAndRecords(userSession.getAccount(),
                duCardList, duRecordList, duTask, cardId, orderNumber, isCardId, isFront)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---adjustCardAndRecords onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---adjustCardAndRecords onError: %s---",
                                e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---adjustCardAndRecords onNext---");
                        getMvpView().onAdjustCardAndRecords(aBoolean);
                    }
                })
        );
    }

}
