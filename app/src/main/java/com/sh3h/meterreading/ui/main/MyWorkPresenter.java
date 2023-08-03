package com.sh3h.meterreading.ui.main;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.entity.DUSamplingTaskInfo;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sh3h.datautil.data.local.config.SystemConfig.REGION_SUZHOU;
import static com.sh3h.datautil.data.local.config.SystemConfig.REGION_WENZHOU;

/**
 * Created by Administrator on 2016/2/20.
 */
public class MyWorkPresenter extends ParentPresenter<MyWorkMvpView> {
    private static final String TAG = "MyWorkPresenter";

    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;


    @Inject
    public MyWorkPresenter(DataManager dataManager, ConfigHelper configHelper, PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public void initRegion() {
        getMvpView().onInitRegion(REGION_WENZHOU);
    }

    public void checkStyle() {
        getMvpView().initStyle(true);
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

    public void getAllSamplingTasks(boolean isLocal) {
        mSubscription.add(mDataManager.getSamplingTasks(new DUSamplingTaskInfo(mPreferencesHelper.getUserSession().getAccount()), isLocal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUSamplingTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getAllSamplingTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getAllSamplingTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUSamplingTask> duSamplingTaskList) {
                        LogUtil.i(TAG, "---getAllSamplingTasks onNext---");
                        getMvpView().onGetSamplingTasks(duSamplingTaskList);
                    }
                })
        );
    }
}
