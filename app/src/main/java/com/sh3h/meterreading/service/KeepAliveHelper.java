package com.sh3h.meterreading.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUKeepAlive;
import com.sh3h.datautil.data.entity.DUKeepAliveInfo;
import com.sh3h.datautil.data.entity.DUKeepAliveResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.config.VersionConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.location.GpsLocation;
import com.sh3h.meterreading.util.DeviceUtil;
import com.sh3h.meterreading.util.SystemUtil;
import com.sh3h.mobileutil.util.LogUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KeepAliveHelper extends BroadcastReceiver {
    private static final String TAG = "KeepAliveHelper";
    private static final String ACTION_NAME = "keepAlive";

    private static KeepAliveHelper keepAliveHelper = null;

    private DataManager dataManager;
    private Context context;
    private ConfigHelper configHelper;
    private PreferencesHelper preferencesHelper;

    private DUKeepAliveInfo duKeepAliveInfo;
    private DUKeepAlive duKeepAlive;
    private int inteval;

    private Subscription mSubscription;

    public KeepAliveHelper() {
        context = null;
        configHelper = null;
        preferencesHelper = null;
        duKeepAlive = null;
        duKeepAliveInfo = null;
        inteval = SystemConfig.KEEP_LIVE_INTERVAL_DEFAULT_VALUE;
        mSubscription = null;
    }

    public static KeepAliveHelper getInstance() {
        if (keepAliveHelper == null) {
            keepAliveHelper = new KeepAliveHelper();
        }

        return keepAliveHelper;
    }

    public void init(DataManager dataManager,
                     Context context,
                     ConfigHelper configHelper,
                     PreferencesHelper preferencesHelper) {
        LogUtil.i(TAG, "---init 1---");
        if ((dataManager == null)
                || (context == null)
                || (configHelper == null)
                || (preferencesHelper == null)) {
            LogUtil.i(TAG, "---init 2---");
            return;
        }

        LogUtil.i(TAG, "---init 3---");
        this.context = context;
        this.configHelper = configHelper;
        this.preferencesHelper = preferencesHelper;
        this.dataManager = dataManager;
        this.duKeepAlive = new DUKeepAlive();
        this.duKeepAliveInfo = new DUKeepAliveInfo(duKeepAlive);

        getAppInfo(context, configHelper);
        getUserInfo(preferencesHelper);
        getDeviceInfo(context);
        getLocation(context);
        getDate();
        getInterval(configHelper);
        scheduleAlarms(context, true);
    }

    public void onDestroy() {
        LogUtil.i(TAG, "---onDestroy---");
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        LogUtil.i(TAG, "---onReceive 1---");
        if ((context != null)
                && (intent != null)
                && (intent.getAction() != null)
                && intent.getAction().equals(ACTION_NAME)) {

            LogUtil.i(TAG, "---onReceive 2---");
            KeepAliveHelper.getInstance().sendData();
        }
    }

    /**
     * 获取系统参数 appVersion，dateVersion
     */
    private void getAppInfo(Context context, ConfigHelper configHelper) {
        if ((context == null) || (configHelper == null) || (duKeepAlive == null)) {
            return;
        }

        int appVersion = SystemUtil.getVersionCode(context);
        duKeepAlive.setAppVersion(String.valueOf(appVersion));

        int dataVersion = configHelper.getVersionConfig().getInteger(VersionConfig.DATA_VERSION, 1);
        duKeepAlive.setDataVersion(String.valueOf(dataVersion));
    }

    /**
     * 获取共享的USER信息ou
     */
    private void getUserInfo(PreferencesHelper preferencesHelper) {
        if ((preferencesHelper == null) || (duKeepAlive == null)) {
            return;
        }

        int userId = preferencesHelper.getUserSession().getUserId();
        duKeepAlive.setUserID(String.valueOf(userId));
    }

    /**
     * 获取手机设备id，电话号码
     */
    private void getDeviceInfo(Context context) {
        if ((context == null) || (duKeepAlive == null)) {
            return;
        }

        duKeepAlive.setDeviceID(DeviceUtil.getDeviceID(context));
    }

    /**
     * 获取经纬度
     */
    private void getLocation(Context context) {
        if ((context == null) || (duKeepAlive == null)) {
            return;
        }

//        if (!application.isGpsLocated()) {
//            mKeepAliveInfoEntity.setS_X("0.0");
//            mKeepAliveInfoEntity.setS_Y("0.0");
//            return;
//        }
//
        GpsLocation.MRLocation gpsLocation = MainApplication.get(context).getMRLocation();
        if (gpsLocation != null) {
            duKeepAlive.setX(Double.toString(gpsLocation.getLongitude()));
            duKeepAlive.setY(Double.toString(gpsLocation.getLatitude()));
        }
        else {
        duKeepAlive.setX("0.0");
        duKeepAlive.setY("0.0");
        }
    }

    private void getDate() {
        if (duKeepAlive == null) {
            return;
        }

        duKeepAlive.setSendTime(System.currentTimeMillis());
    }

    private void getInterval(ConfigHelper configHelper) {
        if ((configHelper == null) || (duKeepAlive == null)) {
            return;
        }

        inteval = configHelper.getKeepAliveInterval();
    }

    private void scheduleAlarms(Context context, boolean firsTime) {
        if (context == null) {
            return;
        }

        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, KeepAliveHelper.class);
        i.setAction(ACTION_NAME);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        mgr.cancel(pi);
        if (firsTime) {
            LogUtil.i(TAG, "---scheduleAlarms---first time");
            mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pi);
        } else {
            LogUtil.i(TAG, "---scheduleAlarms---loop time");
            mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + inteval, pi);
        }
    }

    private void sendData() {
        LogUtil.i(TAG, "---sendData 1---");
        if ((context == null)
                || (dataManager == null)
                || (duKeepAlive == null)
                || (duKeepAliveInfo == null)) {
            LogUtil.i(TAG, "---sendData 2---");
            return;
        }

        LogUtil.i(TAG, "---sendData 3---");
        if (NetworkUtil.isNetworkConnected(context)) {
            LogUtil.i(TAG, "---sendData 4---");
            getLocation(context);
            getDate();

            if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }

            mSubscription = dataManager.keepAlive(duKeepAliveInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DUKeepAliveResult>() {
                        @Override
                        public void onCompleted() {
                            LogUtil.i(TAG, "---onCompleted---");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.i(TAG, "---onError---" + e.getMessage());
                        }

                        @Override
                        public void onNext(DUKeepAliveResult duKeepAliveResult) {
                            long timeError = duKeepAliveResult.getTime() - System.currentTimeMillis();
                            LogUtil.i(TAG, "---onNext---timeError: " + timeError);
                            MainApplication.get(context).setTimeError(timeError);
                        }
                    });
        }

        scheduleAlarms(context, false);
    }
}
