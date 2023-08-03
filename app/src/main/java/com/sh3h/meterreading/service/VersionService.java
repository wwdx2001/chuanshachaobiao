package com.sh3h.meterreading.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.datautil.data.entity.DUUpdateInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.VersionConfig;
import com.sh3h.datautil.event.DataBusEvent;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.util.AndroidComponentUtil;
import com.sh3h.meterreading.util.PatchUtils;
import com.sh3h.meterreading.util.SignUtils;
import com.sh3h.meterreading.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
//import timber.log.Timber;

public class VersionService extends Service {
    private static final String TAG = "VersionService";
    private static final String APK_SUFFIX = ".apk";
    private static final String DATA_SUFFIX = "data";

    @Inject
    DataManager mDataManager;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mBus;

    private boolean mIsOperationFinished;
    private Subscription mSubscription;

    private int mAppVersion;
    private int mDataVersion;

    private int insertControl = 0;
    private String parentDirectory = null;

    public VersionService() {
        mIsOperationFinished = true;
        mSubscription = null;
        mAppVersion = 1;
        mDataVersion = 1;
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, VersionService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, VersionService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.get(this).getComponent().inject(this);
        mBus.register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if (!NetworkUtil.isNetworkConnected(this)) {
            ApplicationsUtil.showMessage(this, R.string.text_network_not_connected);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        LogUtil.i(TAG, "---onStartCommand---" + (intent != null));
        if (intent == null) {
            return START_STICKY;
        }

        if (!mIsOperationFinished) {
            mEventPosterHelper.postEventSafely(new UIBusEvent.ServiceIsBusy("VersionService"));
            return START_STICKY;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

        mIsOperationFinished = false;
        mEventPosterHelper.postEventSafely(new UIBusEvent.VersionServiceStart());
        mAppVersion = SystemUtil.getVersionCode(MainApplication.get(this));
        mDataVersion = mConfigHelper.getVersionConfig().getInteger(VersionConfig.DATA_VERSION, 1);
        DUUpdateInfo duUpdateInfo = new DUUpdateInfo(mAppVersion, mDataVersion);
        mSubscription = mDataManager.updateVersion(duUpdateInfo)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUFileResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "onCompleted");
                        mIsOperationFinished = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "onError " + e.getMessage());
                        mIsOperationFinished = true;
                        mEventPosterHelper.postEventSafely(new UIBusEvent.VersionServiceEnd(false));
                        stopSelf(startId);
                    }

                    @Override
                    public void onNext(DUFileResult duFileResult) {
                        String path = duFileResult.getPath();
                        String name = duFileResult.getName();
                        int percent = duFileResult.getPercent();
                        LogUtil.i(TAG, String.format("---onNext: %s, %d", name, percent));
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        mBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void installLocalApk(String path) {
        if ((path != null) && path.contains(".apk")) {
            File apk = new File(path);
            // TODO Auto-generated method stub
            /*********下载完成，点击安装***********/
            Uri uri = Uri.fromFile(apk);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            /**********加这个属性是因为使用Context的startActivity方法的话，就需要开启一个新的task**********/
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);
            return;
        }

        String oldApkPath = getOldApkPath();
        if (path != null && oldApkPath != null) {
            String patchApkPath = path.replace(".patch", ".apk");
            if (PatchUtils.patch(oldApkPath, patchApkPath, path) == 0) {
                //判断合成版本的签名
                String signatureNew = SignUtils.getUnInstalledApkSignature(getBaseContext(), patchApkPath);
                String signatureSource = SignUtils.getInstalledApkSignature(getBaseContext(), getPackageName());
                if (signatureNew != null && signatureSource != null) {
                    File apk = new File(patchApkPath);
                    // TODO Auto-generated method stub
                    /*********下载完成，点击安装***********/
                    Uri uri = Uri.fromFile(apk);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    /**********加这个属性是因为使用Context的startActivity方法的话，就需要开启一个新的task**********/
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    startActivity(intent);
                }
            }
        }
    }

    private String getOldApkPath() {
        String oldApkSource;
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), 0);
            oldApkSource = appInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            oldApkSource = null;
        }

        return oldApkSource;
    }

    @Subscribe
    public void onParserResult(DataBusEvent.ParserResult parserResult) {
        LogUtil.i(TAG, "---onParserResult---");
        switch (parserResult.getOperationType()) {
            case APK_START:
                break;
            case APK_DOING:
                break;
            case APK_END:
                if (parserResult.isSuccess()
                        && (!TextUtil.isNullOrEmpty(parserResult.getMessage()))) {
                    installLocalApk(parserResult.getMessage());
                    mEventPosterHelper.postEventSafely(new UIBusEvent.VersionServiceEnd(true));
                } else {
                    mEventPosterHelper.postEventSafely(new UIBusEvent.VersionServiceEnd(false));
                }
                break;
            case DATA_START:
                break;
            case DATA_DOING:
                break;
            case DATA_END:
                mEventPosterHelper.postEventSafely(new UIBusEvent.VersionServiceEnd(false));
                break;
            default:
                break;
        }
    }
}
