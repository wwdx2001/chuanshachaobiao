package com.sh3h.meterreading.ui.main;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
//import timber.log.Timber;

public class MainPresenter extends ParentPresenter<MainMvpView> {
    private static final String TAG = "MainPresenter";
    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public MainPresenter(DataManager dataManager,
                         ConfigHelper configHelper,
                         PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public void init(boolean isFromLogin) {
//        if (isFromLogin) {
//            SystemConfig systemConfig = mConfigHelper.getSystemConfig();
//            boolean b = systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO, false);
//            if (b) {
//                getMvpView().startVersionService();
//            } else {
//                b = systemConfig.getBoolean(SystemConfig.PARAM_SYS_SYNC_DATA_AUTO, false);
//                if (b) {
//                    getMvpView().startSyncService();
//                }
//
//                getMvpView().startKeepAliveService();
//            }
//        } else {
//            getMvpView().startKeepAliveService();
//        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        getMvpView().initSubTitle(userSession.getUserName());
    }

    public void destroy() {
        LogUtil.i(TAG, "---destroy---");
        mDataManager.destroy();
//        if (mPreferencesHelper.isRecovery()) {
//            LogUtil.i(TAG, "---destroy recovery 1---");
//            mSubscription.add(mConfigHelper.clearConfigs()
//                    .doOnCompleted(new Action0() {
//                        @Override
//                        public void call() {
//                            LogUtil.i(TAG, "---destroy recovery 2---");
//                            mPreferencesHelper.clearUserSession();
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Subscriber<Void>() {
//                        @Override
//                        public void onCompleted() {
//                            LogUtil.i(TAG, "---destroy recovery onCompleted---");
//                            getMvpView().onExit(null);
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            LogUtil.i(TAG, "---destroy recovery onError---" + e.getMessage());
//                            getMvpView().onExit(e.getMessage());
//                        }
//
//                        @Override
//                        public void onNext(Void aVoid) {
//                            LogUtil.i(TAG, "---destroy recovery onNext---");
//                        }
//                    }));
//        } else {
            LogUtil.i(TAG, "---destroy 2---");
            getMvpView().onExit(null);
//        }
    }

}
