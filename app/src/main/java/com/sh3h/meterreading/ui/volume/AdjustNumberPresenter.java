package com.sh3h.meterreading.ui.volume;


import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUCardResult;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DURecordResult;
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

public class AdjustNumberPresenter extends ParentPresenter<AdjustNumberMvpView> {
    private static final String TAG = "AdjustNumberPresenter";

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AdjustNumberPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
    }

    /**
     *
     */
    public void loadChaoBiaoRWs() {
        LogUtil.i(TAG, "---loadChaoBiaoRWs 1---");
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
     * @param volume
     */
    public void loadCardXXs(String volume) {
        LogUtil.i(TAG, "---loadCardXXs 1---");
        if (volume == null) {
            LogUtil.i(TAG, "---loadCardXXs 2---");
            getMvpView().onError("volume is null");
        } else {
            LogUtil.i(TAG, "---loadCardXXs 3---");
            DUCardInfo duCardInfo = new DUCardInfo(
                    volume,
                    DUCardInfo.FilterType.SEARCHING_ALL);
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
                        public void onNext(List<DUCard> duCardLst) {
                            LogUtil.i(TAG, "---loadCardXXs onNext---");
                            getMvpView().onLoadCards(duCardLst);
                        }
                    }));
        }
    }

    /**
     * @param filterType
     * @param taskId
     * @param volume
     * @param key
     * @param limit
     */
    public void loadRecordXXs(DURecordInfo.FilterType filterType,
                              int taskId,
                              String volume,
                              String key,
                              long limit) {
        LogUtil.i(TAG, "---loadRecordXXs 1---");
        if ((taskId <= 0) || (volume == null)) {
            LogUtil.i(TAG, "---loadRecordXXs 2---");
            getMvpView().onError("parameter is error");
        } else {
            LogUtil.i(TAG, "---loadRecordXXs 3---");
            UserSession userSession = mPreferencesHelper.getUserSession();
            DURecordInfo duRecordInfo = new DURecordInfo(
                    filterType,
                    TextUtil.getString(userSession.getAccount()),
                    taskId,
                    volume,
                    TextUtil.getString(key),
                    limit);
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
                    }));
        }
    }

    /**
     * @param duCardList
     */
    public void adjustCardXXs(List<DUCard> duCardList) {
        LogUtil.i(TAG, "---adjustCardXXs 1---");
        if ((duCardList == null)
                || (duCardList.size() <= 0)) {
            LogUtil.i(TAG, "---adjustCardXXs 2---");
            getMvpView().onError("parameter is error");
        } else {
            LogUtil.i(TAG, "---adjustCardXXs 3---");
            mSubscription.add(mDataManager.updateCards(duCardList)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Subscriber<DUCardResult>() {
                                @Override
                                public void onCompleted() {
                                    LogUtil.i(TAG, "---adjustCardXXs onCompleted---");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtil.i(TAG, String.format("---adjustCardXXs onError: %s---", e.getMessage()));
                                    getMvpView().onError(e.getMessage());
                                }

                                @Override
                                public void onNext(DUCardResult duCardResult) {
                                    LogUtil.i(TAG, "---adjustCardXXs onNext---");
                                    getMvpView().onAdjustCards(duCardResult);
                                }
                            })
            );
        }
    }

    /**
     * @param duRecordList
     */
    public void adjustRecordXXs(List<DURecord> duRecordList) {
        LogUtil.i(TAG, "---adjustRecordXXs 1---");
        if ((duRecordList == null)
                || (duRecordList.size() <= 0)) {
            LogUtil.i(TAG, "---adjustRecordXXs 2---");
            getMvpView().onError("parameter is error");
        } else {
            LogUtil.i(TAG, "---adjustRecordXXs 3---");
            mSubscription.add(mDataManager.updateRecords(duRecordList)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Subscriber<DURecordResult>() {
                                @Override
                                public void onCompleted() {
                                    LogUtil.i(TAG, "---adjustRecordXXs onCompleted---");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtil.i(TAG, String.format("---adjustRecordXXs onError: %s---", e.getMessage()));
                                    getMvpView().onError(e.getMessage());
                                }

                                @Override
                                public void onNext(DURecordResult duRecordResult) {
                                    LogUtil.i(TAG, "---adjustRecordXXs onNext---");
                                    getMvpView().onAdjustRecords(duRecordResult);
                                }
                            })
            );
        }
    }

    public void adjustTask(int taskId, String volume) {
        LogUtil.i(TAG, "---adjustTask 1---");
        if ((taskId <= 0)
                || TextUtil.isNullOrEmpty(volume)) {
            LogUtil.i(TAG, "---adjustTask 2---");
            getMvpView().onError("parameter is error");
        } else {
            LogUtil.i(TAG, "---adjustTask 3---");
            UserSession userSession = mPreferencesHelper.getUserSession();
            DUTaskInfo duTaskInfo = new DUTaskInfo(
                    DUTaskInfo.FilterType.UPDATE_SYNC_FLAG,
                    userSession.getAccount(),
                    taskId,
                    volume,
                    true);
            mSubscription.add(mDataManager.updateTask(duTaskInfo)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Subscriber<Boolean>() {
                                @Override
                                public void onCompleted() {
                                    LogUtil.i(TAG, "---adjustTask onCompleted---");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtil.i(TAG, String.format("---adjustTask onError: %s---",
                                            e.getMessage()));
                                    getMvpView().onError(e.getMessage());
                                }

                                @Override
                                public void onNext(Boolean aBoolean) {
                                    LogUtil.i(TAG, "---adjustTask onNext---");
                                    getMvpView().onAdjustTask(aBoolean);
                                }
                            })
            );
        }
    }

    public void adjustVolume(DUTask removeDuTask,
                             List<DURecord> duRecordList,
                             List<DUCard> duCardList,
                             List<DURecord> removedDuRecordList,
                             List<DUCard> removedDUCardList) {
        getMvpView().onAdjustVolume();
    }


//    public void updateChaoBiaoRWAndBiaoKAXX(int changeCount, String oldCh, String newCh, List<DURecord> updataRecords) {
//
//        if (changeCount == 0 || oldCh.equals("") || newCh.equals("") || updataRecords == null || updataRecords.size() <= 0) {
//            return;
//        }
//
//        mSubscription.add(mDataManager.updateChaoBiaoRWAndBiaoKAXX(changeCount, oldCh, newCh, updataRecords)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(new Subscriber<Boolean>() {
//                            @Override
//                            public void onCompleted() {
//                                LogUtil.i(TAG, "---updateChaoBiaoRWAndBiaoKAXX onCompleted---");
//                                getMvpView().updateChaoBiaoRWAndBiaoKAXX();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                LogUtil.i(TAG, String.format("---loadRecordXXs onError: %s---", e.getMessage()));
//                                getMvpView().onError(e.getMessage());
//                            }
//
//                            @Override
//                            public void onNext(Boolean aBoolean) {
//
//                                if (aBoolean) {
//                                    LogUtil.i(TAG, "---updateChaoBiaoRWAndBiaoKAXX onNext---");
//                                }
//                            }
//
//                        })
//        );
//
//
//    }

}