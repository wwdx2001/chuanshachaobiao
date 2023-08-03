package com.sh3h.meterreading.ui.sampling;


import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingInfo;
import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.entity.DUSamplingTaskInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SamplingTaskPresenter extends ParentPresenter<SamplingTaskMvpView> {
    private static final String TAG = "SamplingTaskPresenter";
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public SamplingTaskPresenter(DataManager dataManager, PreferencesHelper preferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = preferencesHelper;
    }

    public void loadSamplingTask() {
        LogUtil.i(TAG, "---loadJiChaTask start---");
        UserSession userSession = mPreferencesHelper.getUserSession();
        DUSamplingTaskInfo duSamplingTaskInfo = new DUSamplingTaskInfo(userSession.getAccount());
        mSubscription.add(mDataManager.getSamplingTasks(duSamplingTaskInfo, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUSamplingTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadJiChaTask onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadJiChaTask onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUSamplingTask> duSamplingTasks) {
                        LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
                        getMvpView().onLoadSamplingTasks(duSamplingTasks);
                    }
                }));
    }


    public void getNextReadingRecord(final DUSamplingTask duSamplingTask) {
        LogUtil.i(TAG, "---getNextReadingRecord start---");
        if ((duSamplingTask == null)
                || (duSamplingTask.getRenWuBH() <= 0)
                ) {
            LogUtil.i(TAG, "---getNextReadingRecord 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                DUSamplingInfo.FilterType.ALL,
                TextUtil.getString(userSession.getAccount()),
                duSamplingTask.getRenWuBH(),
                "",
                "",
                0);
        mSubscription.add(mDataManager.getSamplings(duSamplingInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUSampling>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getNextReadingRecord onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getNextReadingRecord onError: %s---",
                                e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUSampling> duRecordList) {
                        LogUtil.i(TAG, "---getNextReadingRecord: onNext---1");
                        List<DUSampling> duSamplingWCList = new ArrayList<DUSampling>();
                        ArrayList<String> cIDs = new ArrayList<String>();
                        for (DUSampling duSampling : duRecordList) {
                            if (duSampling.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO) {
                                duSamplingWCList.add(duSampling);
                                cIDs.add(duSampling.getCid());
                            }
                        }

                        if (duSamplingWCList.size() != 0) {
                            getMvpView().onGetNextReadingRecord(duSamplingTask,
                                    duSamplingWCList.get(0),
                                    duSamplingWCList.get(0).getCeneixh(),
                                    duSamplingWCList.get(duSamplingWCList.size()-1).getCeneixh(),
                                    duSamplingWCList.get(0).getCid(),
                                    duSamplingWCList.get(duSamplingWCList.size()-1).getCid(),
                                    cIDs);
                        } else {
                            LogUtil.i(TAG, "---getNextReadingRecord: onNext---2");
                            getMvpView().onError("not found");
                        }
                    }
                }));
    }

    public void deleteSamplingTask(int taskId) {
        LogUtil.i(TAG, "---deleteTask start---");
        if (taskId <= 0) {
            LogUtil.i(TAG, "---deleteTask 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DUSamplingTaskInfo duSamplingTaskInfo = new DUSamplingTaskInfo(userSession.getAccount(),
                taskId, "", DUSamplingTaskInfo.FilterType.DELETE);
        mSubscription.add(mDataManager.deleteSamplingTask(duSamplingTaskInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
                        getMvpView().onDeleteSamplingTask(aBoolean);
                    }
                })
        );
    }

    public void isTaskContainingFullData(DUSamplingTask duSamplingTask) {
        LogUtil.i(TAG, "---isTaskContainingFullData start---");
        if ((duSamplingTask == null) ||
                (duSamplingTask.getRenWuBH() <= 0)
                ) {
            LogUtil.i(TAG, "---isTaskContainingFullData 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DUSamplingTaskInfo duJiChaTaskInfo = new DUSamplingTaskInfo(userSession.getAccount(),
                duSamplingTask.getRenWuBH(), "", DUSamplingTaskInfo.FilterType.ONE);
        mSubscription.add(mDataManager.isSamplingTaskContainingFullData(duJiChaTaskInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---isTaskContainingFullData onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---isTaskContainingFullData onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---isTaskContainingFullData: onLoadTasks---");
                        getMvpView().onIsTaskContainingFullData(aBoolean);
                    }
                })
        );
    }
}
