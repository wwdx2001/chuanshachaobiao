package com.sh3h.meterreading.ui.task;


import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.entity.TraceInfo;
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
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.sh3h.datautil.data.entity.DUCardInfo.FilterType.SEARCHING_ALL;

public class TaskListPresenter extends ParentPresenter<TaskListMvpView> {
    private static final String TAG = "TaskListPresenter";
    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public TaskListPresenter(DataManager dataManager,
                             ConfigHelper configHelper,
                             PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        this.mPreferencesHelper = preferencesHelper;
    }

    public void loadTasks() {
        LogUtil.i(TAG, "---loadTasks start---");
        UserSession userSession = mPreferencesHelper.getUserSession();
        final DUTaskInfo duTaskInfo = new DUTaskInfo(userSession.getAccount());
        mSubscription.add(mDataManager.getTasks(duTaskInfo, true)
                .doOnNext(new Action1<List<DUTask>>() {
                    @Override
                    public void call(List<DUTask> duTasks) {
                        mDataManager.checkFinishedTasks(duTaskInfo.getAccount(), duTasks);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUTask>>() {
                    @Override
                    public void onCompleted() {

                        LogUtil.i(TAG, "---loadTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO 2018.7.27 LIBAO 添加下面一行
                        getMvpView().onLoadTasks(null);
                        LogUtil.i(TAG, String.format("---loadTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUTask> duTaskLst) {
                        LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
                        getMvpView().onLoadTasks(duTaskLst);
                    }
                }));
    }

    public void loadCards(String volume) {
        LogUtil.i(TAG, "---loadCards start---");
        DUCardInfo duCardInfo = new DUCardInfo(volume, SEARCHING_ALL);
        mSubscription.add(mDataManager.getCards(duCardInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadCards onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadCards onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCard> duCards) {
                        LogUtil.i(TAG, "---loadCards: onNext---");
                        getMvpView().onLoadCards(duCards);
                    }
                }));
    }

    public void getNextReadingRecord(final DUTask duTask) {
        LogUtil.i(TAG, "---getNextReadingRecord start---");
        if ((duTask == null)
                || (duTask.getRenWuBH() <= 0)
                || TextUtil.isNullOrEmpty(duTask.getcH())) {
            LogUtil.i(TAG, "---getNextReadingRecord 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DURecordInfo duRecordInfo = new DURecordInfo(
                DURecordInfo.FilterType.ALL,
                TextUtil.getString(userSession.getAccount()),
                duTask.getRenWuBH(),
                duTask.getcH(),
                "",
                0);
        mSubscription.add(mDataManager.getRecords(duRecordInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DURecord>>() {
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
                    public void onNext(List<DURecord> duRecordList) {
                        LogUtil.i(TAG, "---getNextReadingRecord: onNext---1");

                        DURecord startDURecord = null;
                        DURecord endDURecord = null;
                        for (DURecord duRecord : duRecordList) {
                            if (duRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO) {
                                if (startDURecord == null) {
                                    startDURecord = duRecord;
                                }

                                endDURecord = duRecord;
                            }
                        }

                        if (startDURecord != null) {
                            getMvpView().onGetNextReadingRecord(duTask,
                                    startDURecord,
                                    startDURecord.getCeneixh(),
                                    endDURecord.getCeneixh());
                        } else {
                            LogUtil.i(TAG, "---getNextReadingRecord: onNext---2");
                            getMvpView().onError("not found");
                        }
                    }
                }));
    }

    public void deleteTask(int taskId, String volume) {
        LogUtil.i(TAG, "---deleteTask start---");
        if ((taskId <= 0) || TextUtil.isNullOrEmpty(volume)) {
            LogUtil.i(TAG, "---deleteTask 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DUTaskInfo duTaskInfo = new DUTaskInfo(userSession.getAccount(),
                taskId, volume, DUTaskInfo.FilterType.DELETE);
        mSubscription.add(mDataManager.deleteTask(duTaskInfo)
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
                        getMvpView().onDeleteTask(aBoolean);
                    }
                })
        );
    }

    public void isTaskContainingFullData(DUTask duTask) {
        LogUtil.i(TAG, "---isTaskContainingFullData start---");
        if ((duTask == null) ||
                (duTask.getRenWuBH() <= 0)
                || TextUtil.isNullOrEmpty(duTask.getcH())) {
            LogUtil.i(TAG, "---isTaskContainingFullData 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DUTaskInfo duTaskInfo = new DUTaskInfo(userSession.getAccount(),
                duTask.getRenWuBH(), duTask.getcH(), DUTaskInfo.FilterType.ONE);
        mSubscription.add(mDataManager.isTaskContainingFullData(duTaskInfo)
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

    public void getChaoBiaoTrance(int taskId, String ch){
        LogUtil.i(TAG, "---getChaoBiaoTrance start---");
        if (taskId <= 0 || TextUtil.isNullOrEmpty(ch)) {
            LogUtil.i(TAG, "---getChaoBiaoTrance 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        mSubscription.add(mDataManager.getChaoBiaoTrance(taskId, ch)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<TraceInfo>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getChaoBiaoTrance onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getChaoBiaoTrance onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TraceInfo> traceInfos) {
                        getMvpView().onLoadTrance(traceInfos);
                    }
                }));
    }

}
