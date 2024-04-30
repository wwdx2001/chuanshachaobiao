package com.sh3h.meterreading;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.imagepicker.ImagePicker;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.ipc.IMainService;
import com.sh3h.ipc.IRemoteServiceCallback;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.injection.component.ApplicationComponent;
import com.sh3h.meterreading.injection.component.DaggerApplicationComponent;
import com.sh3h.meterreading.injection.module.ApplicationModule;
import com.sh3h.meterreading.location.GpsLocation;
import com.sh3h.meterreading.service.SyncDataInfo;
import com.sh3h.meterreading.service.SyncService;
import com.sh3h.meterreading.service.SyncSubDataInfo;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.service.VersionService;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.GlideImagePickerImageLoader;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhouyou.http.EasyHttp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sh3h.meterreading.service.SyncType.UPLOADING_DOWNLOADING_ALL_RUSH_PAY_TASKS;


public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    private static final String HOST_SERVICE_NAME = "com.sh3h.mainshell.service.HostService";
    private static final String BINDING_NAME = "bindingName";

    @Inject
    Bus mEventBus;

    @Inject
    DataManager mDataManager;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    GpsLocation mGpsLocation;

    private ApplicationComponent mApplicationComponent;

    private long timeError;

    private boolean mIsConfigInit;
    private boolean mIsUserConfigInit;
    private Subscription mSubscription;

    private IMainService mainService;
    private boolean mIsGpsLocated;
    private GpsLocation.MRLocation mMRLocation;

    private static MainApplication instance;

    public MainApplication() {
        mApplicationComponent = null;
        timeError = 0;
        mIsConfigInit = false;
        mIsUserConfigInit = false;
        mainService = null;
        mIsGpsLocated = false;
        mMRLocation = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//            //Fabric.with(this, new Crashlytics());
//        }
        instance = this;
        EasyHttp.init(this);
        MultiDex.install(this);
        ZXingLibrary.initDisplayOpinion(this);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        mEventBus.register(this);

        initEasyHttp();
        initImagePicker();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        //设置图片加载器
        imagePicker.setImageLoader(new GlideImagePickerImageLoader());
        //选中数量限制
        imagePicker.setSelectLimit(3);
        //显示拍照按钮
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
    }

    private void initEasyHttp() {
        EasyHttp.init(this);
        EasyHttp.getInstance()
          .setBaseUrl(StringUtils.isEmpty(SPUtils.getInstance().getString(Const.URL)) ? URL.BASE_XUNJIAN_URL : SPUtils.getInstance().getString(Const.URL))
//        .addCommonHeaders(headers)
          .setCacheTime(-1)
          .setCacheMaxSize(200 * 1024 * 1024)
          .setCertificates()
          .setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
              return true;
            }
          })
          .setReadTimeOut(60 * 1000 * 30)
          .setWriteTimeOut(60 * 1000 * 30)
          .setConnectTimeout(60 * 1000 * 30)
          //.setOkconnectionPool(new ConnectionPool())//设置请求连接池
          //可以全局统一设置超时重连次数,默认为3次,那么最差的情况会请求4次(一次原始请求,三次重连请求),
          //不需要可以设置为0
          .setRetryCount(1)//网络不好自动重试3次
          //可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
          .setRetryDelay(500)//每次延时500ms重试
          //可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
          .setRetryIncreaseDelay(100)//每次延时叠加500ms
          .setCacheVersion(1)//缓存版本为1
          .debug("EasyHttp", true);
  }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MainApplication getInstance () {
        return instance;
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public void setTimeError(long timeError) {
        this.timeError = timeError;
    }

    public long getCurrentTime() {
        long time = new Date().getTime();
        return time + timeError;
    }

    public Date getCurrentDate() {
        return new Date(getCurrentTime());
    }

    public String parseSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = null;
        SyncType syncType = syncDataEnd.getSyncType();
        if ((syncType == SyncType.UPLOADING_VOLUME)
                || (syncType == SyncType.UPLOADING_DOWNLOADING_ALL)) {
            Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
            if (syncDataInfoMap == null) {
                return null;
            }

            // records
            List<SyncDataInfo> syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_RECORDS.getName());
            if (syncDataInfoList == null) {
                return null;
            }

            int successCount1, failureCount1;
            successCount1 = failureCount1 = 0;
            boolean isStaticsRecord = false;
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.NONE) {
                    continue;
                }

                successCount1 += syncDataInfo.getSuccessCount();
                failureCount1 = syncDataInfo.getFailureCount();
                isStaticsRecord = true;
            }

            // medias
            int successCount2, failureCount2;
            successCount2 = failureCount2 = 0;
            boolean isStaticsMedia = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_MEDIAS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    List<SyncSubDataInfo> syncSubDataInfoList = syncDataInfo.getSyncSubDataInfoList();
                    if (syncSubDataInfoList == null) {
                        continue;
                    }

                    for (SyncSubDataInfo syncSubDataInfo : syncSubDataInfoList) {
                        if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.SUCCESS) {
                            successCount2++;
                            isStaticsMedia = true;
                        } else if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.FAILURE) {
                            failureCount2++;
                            isStaticsMedia = true;
                        }
                    }
                }
            }

            // cards
            int successCount3, failureCount3;
            successCount3 = failureCount3 = 0;
            boolean isStaticsCard = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_CARDS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
                        successCount3++;
                        isStaticsCard = true;
                    } else if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
                        failureCount3++;
                        isStaticsCard = true;
                    }
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            if (isStaticsRecord) {
                stringBuilder.append(getResources().getString(R.string.text_uploadRecords));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount1);
                if (failureCount1 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount1);
                }
                stringBuilder.append("]");
            }

            if (isStaticsMedia) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadMedia));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount2);
                if (failureCount2 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount2);
//                    stringBuilder.append("]");
                }
                stringBuilder.append("]");
            }

            if (isStaticsCard) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadCards));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount3);
                if (failureCount3 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount3);
                }
                stringBuilder.append("]");
            }

            if (syncType == SyncType.UPLOADING_DOWNLOADING_ALL) {
                /*StringBuilder volumes = new StringBuilder();
                syncDataInfoList =
                        syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_TASKS.getName());
                if (syncDataInfoList != null) {
                    for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                        if (syncDataInfo.getOperationResult() != SyncDataInfo.OperationResult.SUCCESS) {
                            continue;
                        }

                        String volume = syncDataInfo.getVolume();
                        if (!TextUtil.isNullOrEmpty(volume)) {
                            if (volumes.length() > 0) {
                                volumes.append(",");
                            }

                            volumes.append(volume);
                        }
                    }
                }

                if (volumes.length() > 0) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(getResources().getString(R.string.text_downloadVolume));
                    stringBuilder.append(" [");
                    stringBuilder.append(volumes.toString());
                    stringBuilder.append("]");
                }*/

                StringBuilder volumes = new StringBuilder();
                StringBuilder temporaryVolumes = new StringBuilder();
                syncDataInfoList =
                        syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_TASK.getName());
                if (syncDataInfoList != null) {
                    for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                        if (syncDataInfo.getOperationResult() != SyncDataInfo.OperationResult.SUCCESS) {
                            continue;
                        }

                        if (syncDataInfo.getOperationType() == SyncDataInfo.OperationType.DOWNLOADING_TEMPORARY) {
                            String volume = syncDataInfo.getVolume();
                            if (!TextUtil.isNullOrEmpty(volume)) {
                                if (temporaryVolumes.length() > 0) {
                                    temporaryVolumes.append(",");
                                }

                                temporaryVolumes.append(volume);
                            }
                        } else if (syncDataInfo.getOperationType() == SyncDataInfo.OperationType.DOWNLOADING_RECORDS) {
                            String volume = syncDataInfo.getVolume();
                            if (!TextUtil.isNullOrEmpty(volume)) {
                                if (volumes.length() > 0) {
                                    volumes.append(",");
                                }

                                volumes.append(volume);
                            }
                        }
                    }
                }

                if (volumes.length() > 0) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(getResources().getString(R.string.text_downloadVolume));
                    stringBuilder.append(" [");
                    stringBuilder.append(volumes.toString());
                    stringBuilder.append("]");
                }

                if (temporaryVolumes.length() > 0) {
                    if (temporaryVolumes.length() > 0) {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(getResources().getString(R.string.text_downloadTemporaryVolume));
                    stringBuilder.append(" [");
                    stringBuilder.append(temporaryVolumes.toString());
                    stringBuilder.append("]");
                }
            }
            if (!TextUtil.isNullOrEmpty(stringBuilder.toString())) {
                result = stringBuilder.toString();
            }else {
                result="暂无数据";
            }
        } else if (syncType == SyncType.UPLOADING_DOWNLOADING_DELAY_ALL) {
            Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
            if (syncDataInfoMap == null) {
                return null;
            }

            // records
            List<SyncDataInfo> syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_DELAY_RECORDS.getName());
            if (syncDataInfoList == null) {
                return null;
            }

            int successCount1, failureCount1;
            successCount1 = failureCount1 = 0;
            boolean isStaticsRecord = false;
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.NONE) {
                    continue;
                }

                successCount1 += syncDataInfo.getSuccessCount();
                failureCount1 = syncDataInfo.getFailureCount();
                isStaticsRecord = true;
            }

            // medias
            int successCount2, failureCount2;
            successCount2 = failureCount2 = 0;
            boolean isStaticsMedia = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_DELAY_MEDIAS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    List<SyncSubDataInfo> syncSubDataInfoList = syncDataInfo.getSyncSubDataInfoList();
                    if (syncSubDataInfoList == null) {
                        continue;
                    }

                    for (SyncSubDataInfo syncSubDataInfo : syncSubDataInfoList) {
                        if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.SUCCESS) {
                            successCount2++;
                            isStaticsMedia = true;
                        } else if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.FAILURE) {
                            failureCount2++;
                            isStaticsMedia = true;
                        }
                    }
                }
            }

            // cards
            int successCount3, failureCount3;
            successCount3 = failureCount3 = 0;
            boolean isStaticsCard = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_DELAY_CARDS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
                        successCount3++;
                        isStaticsCard = true;
                    } else if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
                        failureCount3++;
                        isStaticsCard = true;
                    }
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            if (isStaticsRecord) {
                stringBuilder.append(getResources().getString(R.string.text_upload_delay_records));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount1);
                if (failureCount1 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount1);
                }
                stringBuilder.append("]");
            }

            if (isStaticsMedia) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadMedia));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount2);
                if (failureCount2 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount2);
                }
                stringBuilder.append("]");
            }

            if (isStaticsCard) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadCards));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount3);
                if (failureCount3 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount3);
                }
                stringBuilder.append("]");
            }

            String volumes = getString(R.string.text_failure);
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL.getName());
            if (syncDataInfoList != null && syncDataInfoList.size() == 6) {
                if (syncDataInfoList.get(0).getOperationResult() == SyncDataInfo.OperationResult.SUCCESS
                        && syncDataInfoList.get(1).getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
                    volumes = getString(R.string.text_success);
                }
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(getResources().getString(R.string.text_download_delay));
            stringBuilder.append(" [");
            stringBuilder.append(volumes);
            stringBuilder.append("]");

            result = stringBuilder.toString();
        }

        return result;
    }

    public String parseSpecialSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = null;
        SyncType syncType = syncDataEnd.getSyncType();
        if (syncType == SyncType.UPDATING_VOLUME_CARD) {
            Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
            if (syncDataInfoMap == null) {
                return null;
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getResources().getString(R.string.text_refresh_cards));
            List<SyncDataInfo> syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ONE_TASK.getName());
            if ((syncDataInfoList != null) && (syncDataInfoList.size() > 0)) {
                SyncDataInfo syncDataInfo = syncDataInfoList.get(0);
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
                    stringBuilder.append(getResources().getString(R.string.text_success));
                } else {
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                }
            } else {
                stringBuilder.append(getResources().getString(R.string.text_failure));
            }
            result = stringBuilder.toString();
        }

        return result;
    }

    public String parseSyncSamplingDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = null;
        SyncType syncType = syncDataEnd.getSyncType();
        if ((syncType == SyncType.UPLOADING_SAMPLING)
                || (syncType == SyncType.UPLOADING_DOWNLOADING_ALL_SAMPLING_TASKS)) {
            Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
            if (syncDataInfoMap == null) {
                return null;
            }

            // records
            List<SyncDataInfo> syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOlUME_SAMPLINGS.getName());
            if (syncDataInfoList == null) {
                return null;
            }

            int successCount1, failureCount1;
            successCount1 = failureCount1 = 0;
            boolean isStaticsRecord = false;
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.NONE) {
                    continue;
                }

                successCount1 += syncDataInfo.getSuccessCount();
                failureCount1 = syncDataInfo.getFailureCount();
                isStaticsRecord = true;
            }

            // medias
            int successCount2, failureCount2;
            successCount2 = failureCount2 = 0;
            boolean isStaticsMedia = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SAMPLING_VOLUME_MEDIAS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    List<SyncSubDataInfo> syncSubDataInfoList = syncDataInfo.getSyncSubDataInfoList();
                    if (syncSubDataInfoList == null) {
                        continue;
                    }

                    for (SyncSubDataInfo syncSubDataInfo : syncSubDataInfoList) {
                        if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.SUCCESS) {
                            successCount2++;
                            isStaticsMedia = true;
                        } else if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.FAILURE) {
                            failureCount2++;
                            isStaticsMedia = true;
                        }
                    }
                }
            }

            // cards
            int successCount3, failureCount3;
            successCount3 = failureCount3 = 0;
            boolean isStaticsCard = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_CARDS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
                        successCount3++;
                        isStaticsCard = true;
                    } else if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
                        failureCount3++;
                        isStaticsCard = true;
                    }
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            if (isStaticsRecord) {
                stringBuilder.append(getResources().getString(R.string.text_uploadSamplingRecords));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount1);
                if (failureCount1 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount1);
                }
                stringBuilder.append("]");
            }

            if (isStaticsMedia) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadMedia));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount2);
                if (failureCount2 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount2);
                    stringBuilder.append("]");
                }
                stringBuilder.append("]");
            }

            if (isStaticsCard) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadCards));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount3);
                if (failureCount3 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount3);
                }
                stringBuilder.append("]");
            }

            if (syncType == SyncType.UPLOADING_DOWNLOADING_ALL_SAMPLING_TASKS) {
                /*StringBuilder volumes = new StringBuilder();
                syncDataInfoList =
                        syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_TASKS.getName());
                if (syncDataInfoList != null) {
                    for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                        if (syncDataInfo.getOperationResult() != SyncDataInfo.OperationResult.SUCCESS) {
                            continue;
                        }

                        String volume = syncDataInfo.getVolume();
                        if (!TextUtil.isNullOrEmpty(volume)) {
                            if (volumes.length() > 0) {
                                volumes.append(",");
                            }

                            volumes.append(volume);
                        }
                    }
                }

                if (volumes.length() > 0) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(getResources().getString(R.string.text_downloadVolume));
                    stringBuilder.append(" [");
                    stringBuilder.append(volumes.toString());
                    stringBuilder.append("]");
                }*/

                StringBuilder taskIds = new StringBuilder();
                syncDataInfoList =
                        syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_SAMPING_TASK.getName());
                if (syncDataInfoList != null) {
                    for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                        if (syncDataInfo.getOperationResult() != SyncDataInfo.OperationResult.SUCCESS) {
                            continue;
                        }

                        if (syncDataInfo.getOperationType() == SyncDataInfo.OperationType.DOWNLOADING_RECORDS) {
                            int taskId = syncDataInfo.getTaskId();
                            if (taskIds.length() > 0) {
                                taskIds.append(",");
                            }
                            taskIds.append(String.valueOf(taskId));
                        }
                    }
                }

                if (taskIds.length() > 0) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(getResources().getString(R.string.text_downloadTaskId));
                    stringBuilder.append(" [");
                    stringBuilder.append(taskIds.toString());
                    stringBuilder.append("]");
                }
            }

            result = stringBuilder.toString();
        }

        return result;
    }

    public String parseOutRecordSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = null;
        SyncType syncType = syncDataEnd.getSyncType();
        if (syncType == SyncType.SYNC_WAIFUCBSJS || syncType == SyncType.UPLOADING_WAIFUCBSJS_MEDIAS) {
            Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
            if (syncDataInfoMap == null) {
                return null;
            }

            List<SyncDataInfo> syncDataInfoList = null;

            // records
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_WAIFUCBSJS.getName());
            if (syncDataInfoList == null) {
                return null;
            }
            int successCount, failureCount;
            successCount = failureCount = 0;
            boolean isStaticsUpload = false;
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.NONE) {
                    continue;
                }
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
                    successCount = syncDataInfo.getSuccessCount();
                }
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
                    failureCount = syncDataInfo.getSuccessCount();
                }
                if (successCount != 0 || failureCount != 0) {
                    isStaticsUpload = true;
                }
            }

            // medias
            int successCount2, failureCount2;
            successCount2 = failureCount2 = 0;
            boolean isStaticsMedia = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_WAIFU_MEDIAS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    List<SyncSubDataInfo> syncSubDataInfoList = syncDataInfo.getSyncSubDataInfoList();
                    if (syncSubDataInfoList == null) {
                        continue;
                    }

                    for (SyncSubDataInfo syncSubDataInfo : syncSubDataInfoList) {
                        if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.SUCCESS) {
                            successCount2++;
                        } else if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.FAILURE) {
                            failureCount2++;
                        }
                        if (successCount2 != 0 || failureCount2 != 0) {
                            isStaticsMedia = true;
                        }
                    }
                }
            }

            // download records
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_WAIFU.getName());
            boolean isStaticsRecord = false;
            int successCount1, failureCount1;
            successCount1 = failureCount1 = 0;
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.NONE) {
                        continue;
                    }
                    if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS
                            && syncDataInfo.getOperationType() == SyncDataInfo.OperationType.DOWNLOADING_RECORDS) {
                        successCount1 = syncDataInfo.getSuccessCount();
                    }
                    if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE
                            && syncDataInfo.getOperationType() == SyncDataInfo.OperationType.DOWNLOADING_RECORDS) {
                        failureCount1 = syncDataInfo.getFailureCount();
                    }
                    if (successCount1 != 0 || failureCount1 != 0) {
                        isStaticsRecord = true;
                    }

                }

            }

            StringBuilder stringBuilder = new StringBuilder();
            if (isStaticsUpload) {
                stringBuilder.append(getResources().getString(R.string.text_uploadOutRecords));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount);
                if (failureCount > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount);
                }
                stringBuilder.append("]");
            }

            if (isStaticsMedia) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadMedia));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount2);
                if (failureCount2 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount2);
//                    stringBuilder.append("]");
                }
                stringBuilder.append("]");
            }

            if (isStaticsRecord) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_downOutRecords));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount1);
                if (failureCount1 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount1);
                }
                stringBuilder.append("]");
            }

            result = stringBuilder.toString();
        }

        return result;
    }

    public String parseRushPaySyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = null;
        SyncType syncType = syncDataEnd.getSyncType();
        if (syncType == UPLOADING_DOWNLOADING_ALL_RUSH_PAY_TASKS) {
            Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
            if (syncDataInfoMap == null) {
                return null;
            }

            List<SyncDataInfo> syncDataInfoList = null;

            // records
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_RUSH_PAY_TASKS.getName());
            if (syncDataInfoList == null) {
                return null;
            }
            int successCount, failureCount;
            successCount = failureCount = 0;
            boolean isStaticsUpload = false;
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.NONE) {
                    continue;
                }

                successCount += syncDataInfo.getSuccessCount();
                failureCount = syncDataInfo.getFailureCount();
                isStaticsUpload = true;
            }

            // medias
            int successCount2, failureCount2;
            successCount2 = failureCount2 = 0;
            boolean isStaticsMedia = false;
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_RUSH_PAY_TASK_MEDIAS.getName());
            if (syncDataInfoList != null) {
                for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                    List<SyncSubDataInfo> syncSubDataInfoList = syncDataInfo.getSyncSubDataInfoList();
                    if (syncSubDataInfoList == null) {
                        continue;
                    }

                    for (SyncSubDataInfo syncSubDataInfo : syncSubDataInfoList) {
                        if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.SUCCESS) {
                            successCount2++;
                            isStaticsMedia = true;
                        } else if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.FAILURE) {
                            failureCount2++;
                            isStaticsMedia = true;
                        }
                    }
                }
            }

            // download records
            syncDataInfoList =
                    syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_RUSHPAYTASK.getName());

            if (syncDataInfoList == null) {
                return null;
            }
            int successCount1, failureCount1;
            successCount1 = failureCount1 = 0;
            boolean isStaticsRecord = false;
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.NONE) {
                    continue;
                }
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
                    successCount1 = successCount1 + 1;
                }
                if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
                    failureCount1 = failureCount1 + 1;
                }
                isStaticsRecord = true;
            }


            StringBuilder stringBuilder = new StringBuilder();
            if (isStaticsUpload) {
                stringBuilder.append(getResources().getString(R.string.text_uploadRushPayRecords));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount);
                if (failureCount > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount);
                }
                stringBuilder.append("]");
            }

            if (isStaticsRecord) {
                stringBuilder.append(getResources().getString(R.string.text_downRushPayRecords));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount1);
                if (failureCount1 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount1);
                }
                stringBuilder.append("]");
            }


            if (isStaticsMedia) {
                stringBuilder.append("\n");
                stringBuilder.append(getResources().getString(R.string.text_uploadMedia));
                stringBuilder.append(" [");
                stringBuilder.append(getResources().getString(R.string.text_success));
                stringBuilder.append(": ");
                stringBuilder.append(successCount2);
                if (failureCount2 > 0) {
                    stringBuilder.append(", ");
                    stringBuilder.append(getResources().getString(R.string.text_failure));
                    stringBuilder.append(": ");
                    stringBuilder.append(failureCount2);
//                    stringBuilder.append("]");
                }
                stringBuilder.append("]");
            }
            result = stringBuilder.toString();
        }

        return result;
    }

    @Subscribe
    public void onSyncDataStart(UIBusEvent.SyncDataStart syncDataStart) {
        LogUtil.i(TAG, "---onSyncDataStart---");
        if (syncDataStart.getSyncType() != SyncType.UPLOADING_DOWNLOADING_ALL && syncDataStart.getSyncType() != SyncType.UPLOADING_VOLUME) {
            ApplicationsUtil.showMessage(this, R.string.text_synchronizing_data);
        }
    }

    @Subscribe
    public void onServiceIsBusy(UIBusEvent.ServiceIsBusy serviceIsBusy) {
        String type = serviceIsBusy.getType();
        if (!TextUtil.isNullOrEmpty(type)) {
            LogUtil.i(TAG, String.format("---onServiceIsBusy---%s", type));
        } else {
            LogUtil.i(TAG, "---onServiceIsBusy---");
        }

        ApplicationsUtil.showMessage(this, R.string.text_system_busy);
    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncDataEnd---");
        if (syncDataEnd.getSyncType() == SyncType.UPLOADING_DOWNLOADING_ALL) {
            ApplicationsUtil.showMessage(this, R.string.load_state_33);
        } else if (syncDataEnd.getSyncType() == SyncType.UPLOADING_VOLUME) {
            ApplicationsUtil.showMessage(this, R.string.text_upload_success);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_synchronizing_data_over);
        }
    }

//    /**
//     * 初始化GPS定位
//     */
//    @Deprecated
//    public void initGpsLocation() {
//        String region = mConfigHelper.getRegion();
//        switch (region) {
//            case SystemConfig.REGION_WENZHOU:
//                mGpsLocation.initLocation(true, true);
//                break;
//            case SystemConfig.REGION_JIADING:
//            case SystemConfig.REGION_YIWU:
//                break;
//            case SystemConfig.REGION_SUZHOU:
//                mGpsLocation.initLocation(true, false);
//                break;
//            case SystemConfig.REGION_CHANGSHU:
//                mGpsLocation.initLocation(false, false);
//                break;
//        }
//    }
//
//    @Deprecated
//    public void destroyGpsLocation() {
//        String region = mConfigHelper.getRegion();
//        switch (region) {
//            case SystemConfig.REGION_WENZHOU:
//                mGpsLocation.destroyLocation(true);
//                break;
//            case SystemConfig.REGION_JIADING:
//            case SystemConfig.REGION_YIWU:
//                break;
//            case SystemConfig.REGION_SUZHOU:
//                mGpsLocation.destroyLocation(true);
//                break;
//            case SystemConfig.REGION_CHANGSHU:
//                mGpsLocation.destroyLocation(false);
//                break;
//        }
//    }

    public boolean isGpsLocated() {
        return mIsGpsLocated;
    }

    public GpsLocation.MRLocation getMRLocation() {
        return mMRLocation;
    }

    public void initConfig() {
        LogUtil.i(TAG, "---initConfig---1");
        if (mIsConfigInit) {
            LogUtil.i(TAG, "---initConfig---2");
            mEventPosterHelper.postEventSafely(new UIBusEvent.InitConfigResult(true));
            return;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            LogUtil.i(TAG, "---initConfig---3");
        }

        LogUtil.i(TAG, "---initConfig---4");
        mSubscription = mConfigHelper.initDefaultConfigs().
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---initConfig onCompleted---");
                        mIsConfigInit = true;
                        mDataManager.initLogger();
                        mEventPosterHelper.postEventSafely(new UIBusEvent.InitConfigResult(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "---initConfig onError---" + e.getMessage());
                        mEventPosterHelper.postEventSafely(new UIBusEvent.InitConfigResult(false));
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        LogUtil.i(TAG, "---initConfig onNext---");
                    }
                });
    }

    public boolean isConfigInit() {
        return mIsConfigInit;
    }

    public void initUserConfig(String account, int userId, String userName, final boolean isReservedAddress, final String pictureQuality) {
        LogUtil.i(TAG, "---initUserConfig---1");
//        if (mIsUserConfigInit) {
//            LogUtil.i(TAG, "---initUserConfig---2");
//            mEventPosterHelper.postEventSafely(new UIBusEvent.InitUserConfigResult(true));
//            return;
//        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            LogUtil.i(TAG, "---initUserConfig---3");
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        if (userSession != null) {
            userSession.setAccount(TextUtil.getString(account));
            userSession.set_password("");
            userSession.setUserId(userId);
            userSession.setUserName(TextUtil.getString(userName));
            userSession.save();
        }

        mSubscription = mConfigHelper.initUserConfig(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---initUserConfig onCompleted---");
                        saveSonAPKConfigure(isReservedAddress, pictureQuality);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---initUserConfig onError: %s---", e.getMessage()));
                        mEventPosterHelper.postEventSafely(new UIBusEvent.InitUserConfigResult(false));
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        LogUtil.i(TAG, String.format("---initUserConfig onNext---"));
                    }
                });
    }

    public boolean isUserConfigInit() {
        return mIsUserConfigInit;
    }

    private void saveSonAPKConfigure(boolean isReservedAddress, String pictureQuality) {
        LogUtil.i(TAG, "---saveSonAPKConfigure---1");
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            LogUtil.i(TAG, "---saveSonAPKConfigure---2");
        }

        mSubscription = mConfigHelper.saveSonAPKConfigure(isReservedAddress, TextUtil.getString(pictureQuality))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveSonAPKConfigure onCompleted---");
                        mEventPosterHelper.postEventSafely(new UIBusEvent.InitUserConfigResult(true));
                        mIsUserConfigInit = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveSonAPKConfigure onError---" + e.getMessage());
                        mEventPosterHelper.postEventSafely(new UIBusEvent.InitUserConfigResult(false));
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveSonAPKConfigure onNext---");
                    }
                });
    }

    public void onDestroy() {
        LogUtil.i(TAG, "---meterreading onDestroy---");
//        if (KeepAliveService.isRunning(this)) {
//            stopService(KeepAliveService.getStartIntent(this));
//        }

        if (SyncService.isRunning(this)) {
            stopService(SyncService.getStartIntent(this));
        }

        if (VersionService.isRunning(this)) {
            stopService(VersionService.getStartIntent(this));
        }

        unbindHostService();
        System.exit(0);
    }

    @Subscribe
    public void onInitConfigResult(UIBusEvent.InitConfigResult initConfigResult) {
        LogUtil.i(TAG, "---onInitConfigResult---" + initConfigResult.isSuccess());
    }

    @Subscribe
    public void onInitUserConfigResult(UIBusEvent.InitUserConfigResult initUserConfigResult) {
        LogUtil.i(TAG, "---onInitUserConfigResult---" + initUserConfigResult.isSuccess());
        if (initUserConfigResult.isSuccess()) {
            if (!VersionService.isRunning(this)) {
                startService(VersionService.getStartIntent(this));
            }

//            if (!KeepAliveService.isRunning(this)) {
//                startService(KeepAliveService.getStartIntent(this));
//            }
        }
    }

    @Subscribe
    public void onVersionServiceEnd(UIBusEvent.VersionServiceEnd versionServiceEnd) {
        LogUtil.i(TAG, "onVersionServiceEnd");

    }

    public void bindHostService() {
        Log.i(TAG, "---bindHostService---1");
        if (mainService == null) {
            Log.i(TAG, "---bindHostService---2");
            Intent intent = new Intent(HOST_SERVICE_NAME);
            intent = createExplicitFromImplicitIntent(this, intent);
            if (intent != null) {
                intent.putExtra(BINDING_NAME, IMainService.class.getName());
                bindService(intent, mainConnection, BIND_AUTO_CREATE);
            }
        }
    }

    public void unbindHostService() {
        Log.i(TAG, "---unbindHostService---1");
        if (mainService != null) {
            Log.i(TAG, "---unbindHostService---2");
            unbindService(mainConnection);
            mainService = null;
        }
    }

    public void setMyModule(MyModule myModule) {
        try {
            if ((mainService != null) && (myModule != null)) {
                mainService.setMyModule(myModule);
            }
        } catch (RemoteException exception) {
            exception.printStackTrace();
            Log.e(TAG, "---setMyModule---" + exception.getMessage());
        }
    }

    private static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    private ServiceConnection mainConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mainService = IMainService.Stub.asInterface(service);
            LogUtil.i(TAG, "---mainConnection onServiceConnected---");
            try {
                mainService.registerCallback(mCallback);
                mainService.addPid(android.os.Process.myPid());
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.i(TAG, "---mainConnection onServiceDisconnected---");
            try {
                mainService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
            mainService = null;
        }
    };


//    private void transformCoordinate(final MyLocation myLocation) {
//        LogUtil.i(TAG, "---transformCoordinate---1");
//        if (!mIsConfigInit) {
//            return;
//        }
//
//        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
//            mSubscription.unsubscribe();
//            LogUtil.i(TAG, "---transformCoordinate---2");
//        }
//
//        if (NetworkUtil.isNetworkConnected(this)) {
//            mSubscription = mDataManager.transformCoordinate(myLocation.getLongitude(), myLocation.getLatitude())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Subscriber<Coordinate>() {
//                        @Override
//                        public void onCompleted() {
//                            LogUtil.i(TAG, "---transformCoordinate onCompleted---");
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            LogUtil.i(TAG, String.format("---transformCoordinate onError: %s---", e.getMessage()));
//                        }
//
//                        @Override
//                        public void onNext(Coordinate duCoordinate) {
//                            LogUtil.i(TAG, String.format("---transformCoordinate onNext---"));
//
//                            if (duCoordinate != null) {
//                                myLocation.setLongitude(duCoordinate.getLongitude());
//                                myLocation.setLatitude(duCoordinate.getLatitude());
//                                mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_LOCATION, myLocation));
//                            }
//                        }
//                    });
//        }
//    }

    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        public void locationChanged(MyLocation myLocation) {
            //LogUtil.i(TAG, "locationChanged");
            //transformCoordinate(myLocation);
            mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_LOCATION, myLocation));
        }

        /**
         * This is called by the remote service regularly to tell us about
         * new values.  Note that IPC calls are dispatched through a thread
         * pool running in each process, so the code executing here will
         * NOT be running in our main thread like most other things -- so,
         * to update the UI, we need to use a Handler to hop over there.
         */
        public void moduleChanged(MyModule myModule) {
            LogUtil.i(TAG, "moduleChanged");
            //mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_MODULE, myModule));
        }

        public void exitSystem() {
            LogUtil.i(TAG, "exitSystem");
            mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_EXIT));
        }
    };

    private static final int MY_MSG_LOCATION = 1;
    private static final int MY_MSG_MODULE = 2;
    private static final int MY_MSG_EXIT = 3;
    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private MainApplication mMainApplication;

        public MyHandler(MainApplication mainApplication) {
            mMainApplication = mainApplication;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MY_MSG_LOCATION:
                    if (msg.obj instanceof MyLocation) {
                        MyLocation myLocation = (MyLocation) msg.obj;
                        mMainApplication.mIsGpsLocated = myLocation.isGpsLocated();
                        mMainApplication.mMRLocation = new GpsLocation.MRLocation(
                                myLocation.getLocalLongitude(),
                                myLocation.getmLocalLatitude(),
                                myLocation.getDirection(),
                                myLocation.getAccuracy());
                    }
                    break;
                case MY_MSG_MODULE:
                    break;
                case MY_MSG_EXIT:
                    mMainApplication.onDestroy();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
