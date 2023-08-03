package com.sh3h.meterreading.ui.information;


import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CustomerInformationPresenter extends ParentPresenter<CustomerInformationMvpView> {
    private static final String TAG = "VolumeListPresenter";

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public CustomerInformationPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
    }

    public void checkForUpdatingCard(final int taskId,
                                     final String volume) {
        LogUtil.i(TAG, "---checkForUpdatingCard---");

        Observable<DUTask> observable;
        if (taskId == 0) {
            UserSession userSession = mPreferencesHelper.getUserSession();
            DUTaskInfo duTaskInfo = new DUTaskInfo(userSession.getAccount());

            observable = mDataManager.getTasks(duTaskInfo, true)
                    .concatMap(new Func1<List<DUTask>, Observable<DUTask>>() {
                        @Override
                        public Observable<DUTask> call(final List<DUTask> duTasks) {
                            return Observable.create(new Observable.OnSubscribe<DUTask>() {
                                @Override
                                public void call(Subscriber<? super DUTask> subscriber) {
                                    boolean found = false;
                                    for (DUTask duTask : duTasks) {
                                        if (duTask.getcH().equals(TextUtil.getString(volume))) {
                                            subscriber.onNext(duTask);
                                            found = true;
                                            break;
                                        }
                                    }

                                    if (!found) {
                                        DUTask duTask = new DUTask();
                                        duTask.setcH(volume);
                                        duTask.setTongBuBZ(DUTask.TONGBUBZ_NORMAL);
                                        subscriber.onNext(duTask);
                                    }

                                    subscriber.onCompleted();
                                }
                            });
                        }
                    });
        } else {
            UserSession userSession = mPreferencesHelper.getUserSession();
            DUTaskInfo duTaskInfo = new DUTaskInfo(
                    TextUtil.getString(userSession.getAccount()),
                    taskId,
                    TextUtil.getString(volume),
                    DUTaskInfo.FilterType.ONE);
            observable = mDataManager.getTask(duTaskInfo);
        }

        mSubscription.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUTask>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---checkForUpdatingCard onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---checkForUpdatingCard onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUTask duTask) {
                        LogUtil.i(TAG, "---checkForUpdatingCard onNext---");
                        getMvpView().onCheckForUpdatingCard(duTask.getTongBuBZ() == DUTask.TONGBUBZ_NORMAL);
                    }
                }));
    }
}
