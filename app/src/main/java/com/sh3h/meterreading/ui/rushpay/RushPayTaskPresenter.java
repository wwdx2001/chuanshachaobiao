package com.sh3h.meterreading.ui.rushpay;


import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DURushPayTask;
import com.sh3h.datautil.data.entity.DURushPayTaskInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RushPayTaskPresenter extends ParentPresenter<RushPayTaskMvpView> {
    private static final String TAG = "RushPayTaskPresenter";
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public RushPayTaskPresenter(DataManager dataManager, PreferencesHelper preferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = preferencesHelper;
    }

    public void loadRushPayTasks() {
        LogUtil.i(TAG, "---loadRushPayTasks start---");
        UserSession userSession = mPreferencesHelper.getUserSession();
        DURushPayTaskInfo duRushPayTaskInfo = new DURushPayTaskInfo();
        duRushPayTaskInfo.setAccount(userSession.getAccount());
        mSubscription.add(mDataManager.getRushPayTasks(duRushPayTaskInfo, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DURushPayTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadRushPayTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadRushPayTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DURushPayTask> duRushPayTaskList) {
                        LogUtil.i(TAG, "---loadRushPayTasks: duRushPayTaskList---");
                        getMvpView().onLoadRushPayTasks(duRushPayTaskList);
                    }
                }));
    }


    public void deleteRushPayTask(int taskId) {
        LogUtil.i(TAG, "---deleteRushPayTask start---");
        if (taskId <= 0) {
            LogUtil.i(TAG, "---deleteRushPayTask 2---");
            getMvpView().onError("parameter is null");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DURushPayTaskInfo duRushPayTaskInfo = new DURushPayTaskInfo(
                userSession.getAccount(),
                taskId,
                DURushPayTaskInfo.FilterType.DELETE);
        mSubscription.add(mDataManager.deleteRushPayTask(duRushPayTaskInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---deleteRushPayTask onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---deleteRushPayTask onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---deleteRushPayTask: onNext---");
                        getMvpView().onDeleteRushPayTask(aBoolean);
                    }
                })
        );
    }
}
