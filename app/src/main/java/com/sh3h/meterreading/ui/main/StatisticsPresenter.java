package com.sh3h.meterreading.ui.main;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
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
 * Created by liurui on 2016/2/19.
 */
public class StatisticsPresenter extends ParentPresenter<StatisticsMvpView> {
    private static final String TAG = "StatisticsPresenter";

    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public StatisticsPresenter(DataManager dataManager, ConfigHelper configHelper, PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public void getAllTasks(boolean isLocal) {
        mSubscription.add(mDataManager.getTasks(new DUTaskInfo(mPreferencesHelper.getUserSession().getAccount()), isLocal)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<DUTask>>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i(TAG, "---getAllTasks onCompleted---");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i(TAG, String.format("---getAllTasks onError: %s---", e.getMessage()));
                                getMvpView().onError(e.getMessage());
                            }

                            @Override
                            public void onNext(List<DUTask> duTasks) {
                                LogUtil.i(TAG, "---getAllTasks onNext---");
                                getMvpView().onGetAllTasks(duTasks);
                            }
                        })
        );
    }

    public void getChaoJianS(int taskId,
                              String volume) {
        LogUtil.i(TAG, "---getChaoJianS 1---");
        if (volume == null) {
            LogUtil.i(TAG, "---getChaoJianS 2---");
            getMvpView().onError("volume is null");
        } else {
            LogUtil.i(TAG, "---getChaoJianS 3---");
            UserSession userSession = mPreferencesHelper.getUserSession();
            String account = userSession.getAccount();
            String chaoJianZTS = mConfigHelper.getChaoJianZTS();
            mSubscription.add(mDataManager.getChaoJianS(account, taskId, volume, chaoJianZTS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            LogUtil.i(TAG, "---getChaoJianS onCompleted---");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.i(TAG, String.format("---getChaoJianS onError: %s---", e.getMessage()));
                            getMvpView().onError(e.getMessage());
                        }

                        @Override
                        public void onNext(Integer integer) {
                            LogUtil.i(TAG, "---getChaoJianS onNext ---");
                            getMvpView().onGetChaoJianS(integer);
                        }
                    }));
        }
    }

//    public void getTask(int renWuBH, String ch) {
//        mSubscription.add(mDataManager.getTask(new DUTaskInfo(
//                        mPreferencesHelper.getUserSession().getAccount(),
//                        renWuBH,
//                        ch,
//                        0,
//                        DUTaskInfo.FilterType.ONE))
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(new Subscriber<DUTask>() {
//                            @Override
//                            public void onCompleted() {
//                                LogUtil.i(TAG, "---getTask onCompleted---");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                LogUtil.i(TAG, String.format("---getTask onError: %s---", e.getMessage()));
//                                getMvpView().onError(e.getMessage());
//                            }
//
//                            @Override
//                            public void onNext(DUTask duTask) {
//                                LogUtil.i(TAG, "---getTask onNext---");
//                                getMvpView().onGetTask(duTask);
//                            }
//                        })
//        );
//
//    }
}
