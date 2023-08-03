package com.sh3h.meterreading.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.util.AndroidComponentUtil;
import com.sh3h.mobileutil.util.LogUtil;

import javax.inject.Inject;


/**
 * Created by xulongjun on 2016/3/11.
 */
public class KeepAliveService extends Service {
    private static final String TAG = "KeepAliveService";

    @Inject
    DataManager mDataManager;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    public KeepAliveService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.get(this).getComponent().inject(this);
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "---onStartCommand---");
        KeepAliveHelper.getInstance().init(mDataManager, MainApplication.get(this),
                mConfigHelper, mPreferencesHelper);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, "---onDestroy---");
        KeepAliveHelper.getInstance().onDestroy();
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, KeepAliveService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, KeepAliveService.class);
    }
}
