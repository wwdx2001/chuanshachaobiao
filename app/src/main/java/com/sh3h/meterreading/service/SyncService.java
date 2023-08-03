package com.sh3h.meterreading.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardCoordinateInfo;
import com.sh3h.datautil.data.entity.DUCardCoordinateResult;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUCardResult;
import com.sh3h.datautil.data.entity.DUChaoBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.datautil.data.entity.DUDelayRecordInfo;
import com.sh3h.datautil.data.entity.DUDelayRecordResult;
import com.sh3h.datautil.data.entity.DUDownloadResult;
import com.sh3h.datautil.data.entity.DUHuanBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUJiaoFeiXXInfo;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUMediaInfo;
import com.sh3h.datautil.data.entity.DUMediaResult;
import com.sh3h.datautil.data.entity.DUQianFeiXXInfo;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DURecordResult;
import com.sh3h.datautil.data.entity.DURushPayTask;
import com.sh3h.datautil.data.entity.DURushPayTaskInfo;
import com.sh3h.datautil.data.entity.DURushPayTaskResult;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingInfo;
import com.sh3h.datautil.data.entity.DUSamplingResult;
import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.entity.DUSamplingTaskInfo;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskIdInfo;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJInfo;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.util.AndroidComponentUtil;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.meterreading.util.DeviceUtil;
import com.sh3h.meterreading.util.FileUtil;
import com.sh3h.meterreading.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.sh3h.datautil.data.entity.DURushPayTaskInfo.FilterType.NOT_UPLOAD;
import static com.sh3h.datautil.data.local.config.ConfigHelper.FOLDER_LOG;
import static com.sh3h.datautil.data.local.config.ConfigHelper.STORAGE_PATH;
import static com.sh3h.meterreading.service.SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_WAIFU;
import static com.sh3h.meterreading.service.SyncDataInfo.OperationType.UPLOADING_RUSH_PAY_TASK_MEDIAS;
import static com.sh3h.meterreading.service.SyncDataInfo.OperationType.UPLOADING_WAIFUCBSJS;
//import timber.log.Timber;


public class SyncService extends Service {
    private static final String TAG = "SyncService";
    private static final int SYS_DATA_DOWNLOAD_TIME = 3;

    public static final String ACTIVITY_NAME = "ActivityName";
    public static final String SYNC_TYPE = "SyncType";
    public static final String TASK_ID = "TaskId";
    public static final String GROUP_ID = "GroupId";
    public static final String VOLUME = "Volume";
    public static final String CUSTOMER_ID = "CustomerId";
    public static final String ORDER_NUMBER = "OrderNumber";
    public static final String SORT_INDEX = "SortIndex";
    public static final String LONGITUDE = "Longitude";
    public static final String LATITUDE = "Latitude";

    @Inject
    DataManager mDataManager;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mBus;

    private Subscription mSubscription;

    private String mAccount;
    private boolean mIsOperationFinished;
    private SyncType mSyncType;

    private int mMonthCount;
    private boolean mIsDownloadingTotal;
    private boolean mHasUploadingCardsFunction;

    private Map<String, List<SyncDataInfo>> syncDataInfoMap;

    private int offset;
    private boolean isUploadMoreMediaFinished;

    public SyncService() {
        mAccount = null;
        mIsOperationFinished = true;
        mSyncType = SyncType.UPLOADING_DOWNLOADING_ALL;
        mMonthCount = SYS_DATA_DOWNLOAD_TIME;
        mIsDownloadingTotal = false;
        mHasUploadingCardsFunction = false;
        syncDataInfoMap = null;
        offset = 0;
        isUploadMoreMediaFinished = true;
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.get(this).getComponent().inject(this);
        mAccount = mPreferencesHelper.getUserSession().getAccount();
        String region = mConfigHelper.getRegion();
        if ((!TextUtil.isNullOrEmpty(region))
                && region.equals(SystemConfig.REGION_WENZHOU)) {
            mHasUploadingCardsFunction = true;
        } else {
            mHasUploadingCardsFunction = false;
        }

        mBus.register(this);

        syncDataInfoMap = new HashMap<>();
        syncDataInfoMap.put(SyncDataInfo.OperationType.DOWNLOADING_TASKS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.DOWNLOADING_SAMPLING_TASKS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ONE_TASK.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_TASK.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_SINGLE_RECORD.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_VOLUME_RECORDS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_SINGLE_MEDIAS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_VOLUME_MEDIAS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_VOLUME_CARDS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_SAMPING_TASK.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING_MEDIAS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_SAMPLING_VOLUME_MEDIAS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_VOlUME_SAMPLINGS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_WAIFUCBSJS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_WAIFU_MEDIAS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_WAIFU.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_DELAY_RECORDS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_DELAY_CARDS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.UPLOADING_DELAY_MEDIAS.getName(),
                new ArrayList<SyncDataInfo>());
        syncDataInfoMap.put(SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL.getName(),
                new ArrayList<SyncDataInfo>());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        /**
         * 2018.7.19 libao
         * bug，log日志导致手机存储爆满，app操作时。比如上传图片时写log日志的时候由于内存不足抛出异常，导致上传图片失败
         * 解决办法：操作数据的时候先查询手机sd卡剩余空间，当空间小于2的时候清空一下log日志
         */
        float availableSize=SystemUtil.getAvailableInternalMemorySize(this);
//        ApplicationsUtil.showMessage(getApplicationContext(),availableSize+"");
        if(availableSize<4){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File dir = new File(STORAGE_PATH, FOLDER_LOG);
                    FileUtil.deleteFile(dir);
                }
            }).start();

        }
        if (!NetworkUtil.isNetworkConnected(this)) {
            ApplicationsUtil.showMessage(this, R.string.text_network_not_connected);
            stopSelf(startId);
            LogUtil.i(TAG, "---onStartCommand---network isn't connected");
            return START_NOT_STICKY;
        }

        LogUtil.i(TAG, "---onStartCommand---" + (intent != null));
        if (intent == null) {
            return START_STICKY;
        }

        SyncType syncType;
        int type = intent.getIntExtra(SYNC_TYPE, 0);
        try {
            syncType = SyncType.values()[type];
        } catch (Exception e) {
            e.printStackTrace();
            syncType = SyncType.UPLOADING_DOWNLOADING_ALL;
        }

        LogUtil.i(TAG, "---onStartCommand---" + syncType.ordinal());
        if (syncType == SyncType.QUERYING_QIANFEI) {
            if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }

            String customerId = intent.getStringExtra(CUSTOMER_ID);
            if ((!TextUtil.isNullOrEmpty(customerId))) {
                isQianFei(customerId);
            }

            return START_STICKY;
        } else if (syncType == SyncType.UPLOADING_CARD_COORDINATE) {
            if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }

            int taskId = intent.getIntExtra(TASK_ID, 0);
            String volume = intent.getStringExtra(VOLUME);
            String customerId = intent.getStringExtra(CUSTOMER_ID);
            double longitude = intent.getDoubleExtra(LONGITUDE, 0);
            double latitude = intent.getDoubleExtra(LATITUDE, 0);
            if ((!TextUtil.isNullOrEmpty(volume)) && (!TextUtil.isNullOrEmpty(customerId))) {
                uploadCardCoordinate(taskId, volume, customerId, longitude, latitude);
            }

            return START_STICKY;
        }

        if (!mIsOperationFinished) {
            mEventPosterHelper.postEventSafely(new UIBusEvent.ServiceIsBusy("SyncService"));
            return START_STICKY;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

        mMonthCount = mConfigHelper.getMonthCount();
        mIsDownloadingTotal = mConfigHelper.isDownloadingTotal();
        mSyncType = syncType;
        mEventPosterHelper.postEventSafely(new UIBusEvent.SyncDataStart(mSyncType));
        int taskId, orderNumber;
        String volume, customerId;
        int groupId, sortIndex;
        switch (mSyncType) {
            case UPLOADING_DOWNLOADING_ALL:
                //TODO 2018.7.22 LIBAO 不上传数据
                download();
//                uploadData(); // ok
                break;
            case DOWNLOADING_VOLUME:
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                if ((taskId > 0) && (!TextUtil.isNullOrEmpty(volume))) {
                    download(taskId, volume); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPLOADING_VOLUME:
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                if ((taskId > 0) && (!TextUtil.isNullOrEmpty(volume))) {
                    uploadData(taskId, volume); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPDATING_VOLUME_CARD:
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                if ((taskId > 0) && (!TextUtil.isNullOrEmpty(volume))) {
                    downloadVolumeCards(taskId, volume); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPLOADING_RECORD_MEDIAS:
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                customerId = intent.getStringExtra(CUSTOMER_ID);
                orderNumber = intent.getIntExtra(ORDER_NUMBER, 0);
                if ((taskId > 0)
                        && (!TextUtil.isNullOrEmpty(volume))
                        && (!TextUtil.isNullOrEmpty(customerId))) {
                    uploadData(taskId, volume, customerId, orderNumber); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPLOADING_OUT_RECORD_MEDIAS:
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                customerId = intent.getStringExtra(CUSTOMER_ID);
                orderNumber = intent.getIntExtra(ORDER_NUMBER, 0);
                if ((taskId > 0)
                        && (!TextUtil.isNullOrEmpty(volume))
                        && (!TextUtil.isNullOrEmpty(customerId))) {
                    uploadOutData(taskId, volume, customerId, orderNumber); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPLOADING_SAMPLING_MEDIAS:
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                customerId = intent.getStringExtra(CUSTOMER_ID);
                sortIndex = intent.getIntExtra(SORT_INDEX, 0);
                if ((taskId > 0)
                        && (!TextUtil.isNullOrEmpty(customerId))) {
                    uploadSamplingData(taskId, volume, customerId, sortIndex); // ok
                } else {
                    postEndEvent();
                }
                break;
            case DOWNLOADING_ALL_DETAIL_INFO: // TODO
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                customerId = intent.getStringExtra(CUSTOMER_ID);
                if (!TextUtil.isNullOrEmpty(customerId)) {
                    downloadDetailInfo(taskId, volume); // ok
                } else {
                    postEndEvent();
                }
                break;
            case DOWNLOADING_DETAIL_INFO:
                taskId = intent.getIntExtra(TASK_ID, 0);
                volume = intent.getStringExtra(VOLUME);
                customerId = intent.getStringExtra(CUSTOMER_ID);
                if ((!TextUtil.isNullOrEmpty(volume)) && (!TextUtil.isNullOrEmpty(customerId))) {
                    downloadDetailInfo(taskId, volume, customerId); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPLOADING_DOWNLOADING_ALL_SAMPLING_TASKS:
                uploadSamplings();
                break;
//            case UPLOADING_DOWNLOADING_ALL_DATA:
//                uploadData(); // ok
//                break;
            case DOWNLOADING_SAMPLING:
                taskId = intent.getIntExtra(TASK_ID, 0);
                if (taskId > 0) {
                    downloadSamplingAndBiaoKaXX(taskId); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPLOADING_SAMPLING:
                taskId = intent.getIntExtra(TASK_ID, 0);
                if (taskId > 0) {
                    uploadSamplingsData(taskId); // ok
                } else {
                    postEndEvent();
                }
                break;
            case UPLOADING_ALL_MEDIA_REPEATEDLY:
                mDataManager.setUploadingImageRepeatedly(true);
                uploadMedias();
                break;
            case UPLOADING_DOWNLOADING_ALL_RUSH_PAY_TASKS:
//                uploadRushPays();
                break;
            case SYNC_WAIFUCBSJS:
                uploadWaiFuCBSJS();
                break;
            case UPLOADING_WAIFUCBSJS_MEDIAS:
                uploadWaiFuCBSJS();
                break;
            case UPLOADING_DOWNLOADING_DELAY_ALL:
                uploadDownloadDelayData();
                break;
            default:
                postEndEvent();
                break;
        }

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

    /**
     *下载数据
     */
    private void download() {
        LogUtil.i(TAG, "---download 1---");
        mIsOperationFinished = false;
        final List<DUTask> duTaskList = new ArrayList<>();
//        syncDataInfoMap.clear();
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_TASKS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        DUTaskIdInfo duTaskIdInfo = new DUTaskIdInfo(mAccount);

        mSubscription = mDataManager.getTaskIds(duTaskIdInfo, false)
                .concatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---download 2---taskId count: %d", strings.size()));

                        return Observable.from(strings).onBackpressureBuffer();
                    }
                })
                .concatMap(new Func1<String, Observable<List<DUTask>>>() {
                    @Override
                    public Observable<List<DUTask>> call(String s) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---download 3---taskId: %s", s));

                        DUTaskInfo duTaskInfo = new DUTaskInfo(
                                mAccount,
                                TextUtil.getInt(s),
                                DeviceUtil.getDeviceID(MainApplication.get(SyncService.this)));
                        return mDataManager.getTasks(duTaskInfo, false);
                    }
                })
                .doOnNext(new Action1<List<DUTask>>() {
                    @Override
                    public void call(List<DUTask> duTasks) {
                        for (DUTask duTask : duTasks) {
                            LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                    "---download onNext---taskId: %d, volume: %s",
                                    duTask.getRenWuBH(), duTask.getcH()));

                            //TODO 2018 LIBAO 添加此判断
                            if(duTask.getYiChaoShu()!=duTask.getZongShu()) {
                                duTaskList.add(duTask);
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.DOWNLOADING_TASKS,
                                        duTask.getRenWuBH(),
                                        duTask.getcH(),
                                        null,
                                        SyncDataInfo.OperationResult.SUCCESS,
                                        false);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---download onCompleted---");

                        downloadMore(duTaskList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---download onError---%s", e.getMessage()));

                        downloadMore(duTaskList);
                    }

                    @Override
                    public void onNext(List<DUTask> duTasks) {
                        LogUtil.i(TAG, "---download onNext---");
                    }
                });
    }
    /**
     * 下载所有数据
     * @param duTaskList
     */
    private void downloadMore(List<DUTask> duTaskList) {
        LogUtil.i(TAG, "---downloadMore 1---");
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_TASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        if ((duTaskList == null) || (duTaskList.size() <= 0)) {
            LogUtil.i(TAG, "---downloadMore 2---");
            //TODO LIBAO 2018.7.27 增加下面两行
            mIsOperationFinished = true;
            deleteTasks();
//            postEndEvent();
            return;
        }



        Observable<DUDownloadResult> observable;
        //if (mIsDownloadingTotal) {
        observable = Observable.mergeDelayError(
                downloadCards(duTaskList, false),
                downloadRecords(duTaskList),
//                  downloadTemporaryRecordsAndCards(),
                downloadChaoBiaoJLs(duTaskList),
                downloadJiaoFeiXXs(duTaskList),
                downloadQianFeiXXs(duTaskList),
                downloadHuanBiaoXXs(duTaskList));
//        } else {
//            observable = Observable.mergeDelayError(
//                    downloadCards(duTaskList, false),
//                    downloadRecords(duTaskList)
////                  downloadTemporaryRecordsAndCards()
//            );
//        }

        mSubscription = observable
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case CARD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_CARDS;
                                break;
                            case RECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_RECORDS;
                                break;
                            case PRERECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_PRERECORDS;
                                break;
                            case PAYMENT:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_PAYMENTS;
                                break;
                            case ARREARAGE:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_ARREARAGES;
                                break;
                            case REPLACEMENT:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_REPLACEMENTS;
                                break;
                            case TEMPORARY:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_TEMPORARY;
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                0,
                                volume,
                                null,
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadMore onNext---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadMore onCompleted---");
                        mIsOperationFinished = true;
                        deleteTasks();
//                        postEndEvent();
//                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---downloadMore onError---" + e.getMessage());
                        mIsOperationFinished = true;
                        deleteTasks();
//                        postEndEvent();
//                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---downloadMore onNext---");
                    }
                });
    }

    /**
     * 删除数据
     */
    public void deleteTasks() {
        LogUtil.i(TAG, "---deleteTasks 1---");
        mIsOperationFinished = false;

        final int[] totalCount = new int[1];
        mSubscription = mDataManager.getRemovedTasks(mAccount)
                .concatMap(new Func1<List<DUTask>, Observable<DUTask>>() {
                    @Override
                    public Observable<DUTask> call(List<DUTask> duTaskList) {
                        LogUtil.i(TAG, "---deleteTasks 2---");
                        totalCount[0] = duTaskList.size();
                        return Observable.from(duTaskList);
                    }
                })
                .concatMap(new Func1<DUTask, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(DUTask duTask) {
                        totalCount[0]--;
                        DUTaskInfo duTaskInfo = new DUTaskInfo(
                                mAccount,
                                duTask.getRenWuBH(),
                                duTask.getcH(),
                                DUTaskInfo.FilterType.DELETE);
                        return mDataManager.deleteTask(duTaskInfo);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---deleteTasks onCompleted---");
                        if (totalCount[0] <= 0) {
                            LogUtil.i(TAG, "---uploadData 6 onCompleted---post event");
                            //TODO 2018.7.27  注释
//                            download();
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---deleteTasks onError---" + e.getMessage());
                        //TODO 2018.7.27  注释
//                        download();
                        postEndEvent();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---deleteTasks onNext---" + aBoolean);
                    }
                });
    }

    private void uploadSamplings() {
        LogUtil.i(TAG, "---uploadSampling---");
        final int[] currentIndex = {0};
        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOlUME_SAMPLINGS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }
        DUSamplingTaskInfo duSamplingTaskInfo = new DUSamplingTaskInfo(mAccount);
        mSubscription = mDataManager.getSamplingTasks(duSamplingTaskInfo, true)
                .concatMap(new Func1<List<DUSamplingTask>, Observable<DUSamplingTask>>() {
                    @Override
                    public Observable<DUSamplingTask> call(List<DUSamplingTask> duSamplingTaskList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 2---task count: %d", duSamplingTaskList.size()));
                        if (syncDataInfoList != null) {
                            for (DUSamplingTask duSamplingTask : duSamplingTaskList) {
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.UPLOADING_VOlUME_SAMPLINGS,
                                        duSamplingTask.getRenWuBH(),
                                        "",
//                                        duJiChaTask.getcH(),
                                        null,
                                        SyncDataInfo.OperationResult.NONE,
                                        false);
                            }
                        }
                        return Observable.from(duSamplingTaskList);
                    }
                })
                .concatMap(new Func1<DUSamplingTask, Observable<List<DUSampling>>>() {
                    @Override
                    public Observable<List<DUSampling>> call(DUSamplingTask duSamplingTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 3---taskId: %d, volume: %s",
                                duSamplingTask.getRenWuBH(), ""
//                              duSamplingTask.getcH()
                        ));

                        currentIndex[0] = findSyncDataInfoIndex(syncDataInfoList,
                                duSamplingTask.getRenWuBH());

                        DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                                mAccount,
                                duSamplingTask.getRenWuBH(),
//                                duSamplingTask.getcH(),
                                DUSamplingInfo.FilterType.NOT_UPLOADED_SAMPLINGDATA);
                        return mDataManager.getSamplings(duSamplingInfo);
                    }
                })
                .filter(new Func1<List<DUSampling>, Boolean>() {
                    @Override
                    public Boolean call(List<DUSampling> duSamplingList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 4---record count: %d",
                                duSamplingList.size()));

                        boolean result = duSamplingList.size() > 0;
                        if (!result) {
                            setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                    SyncDataInfo.OperationResult.NONE);
                        }

                        return result;
                    }
                })
                .concatMap(new Func1<List<DUSampling>, Observable<DUSamplingResult>>() {
                    @Override
                    public Observable<DUSamplingResult> call(List<DUSampling> duSamplingList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 5---record count: %d",
                                duSamplingList.size()));

                        DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                                mAccount,
                                duSamplingList.get(0).getRenwubh(),
                                DeviceUtil.getDeviceID(getApplicationContext()),
                                DUSamplingInfo.FilterType.UPLOADING_SAMPLINGS,
                                duSamplingList);
                        return mDataManager.uploadSamplings(duSamplingInfo);
                    }
                })
                .doOnNext(new Action1<DUSamplingResult>() {
                    @Override
                    public void call(DUSamplingResult duSamplingResult) {
                        setSyncDataInfo(syncDataInfoList,
                                currentIndex[0],
                                duSamplingResult.getFailureCount() == 0 ?
                                        SyncDataInfo.OperationResult.SUCCESS :
                                        SyncDataInfo.OperationResult.FAILURE,
                                duSamplingResult.getSuccessCount(),
                                duSamplingResult.getFailureCount());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUSamplingResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadData 6 onCompleted---");
                        uploadSamplingMedias();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 7 onError---%s", e.getMessage()));
                        uploadSamplingMedias();
                    }

                    @Override
                    public void onNext(DUSamplingResult duSamplingResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 8 onNext--- taskId: %d, volume: %s",
                                duSamplingResult.getTaskId(), duSamplingResult.getVolume()));
                    }
                });
    }

    private void uploadWaiFuCBSJS() {
        LogUtil.i(TAG, "---uploadWaiFuCBSJS---");
        mIsOperationFinished = false;
        final int[] currentIndex = {0};
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(UPLOADING_WAIFUCBSJS.getName());

        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    UPLOADING_WAIFUCBSJS,
                    0,
                    "waifu",
                    "",
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        final List<SyncDataInfo> syncDataInfoListDownload =
                syncDataInfoMap.get(DOWNLOADING_ALL_DATA_OF_ALL_WAIFU.getName());
        if (syncDataInfoListDownload != null) {
            syncDataInfoListDownload.clear();
        }

        DUWaiFuCBSJInfo duWaiFuCBSJInfo = new DUWaiFuCBSJInfo(
                DUWaiFuCBSJInfo.FilterType.SYNC_ALL,
                mAccount);

        mSubscription = mDataManager.getWaiFuCBSJS(duWaiFuCBSJInfo)
                .filter(new Func1<List<DUWaiFuCBSJ>, Boolean>() {
                    @Override
                    public Boolean call(List<DUWaiFuCBSJ> duWaiFuCBSJs) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadWaiFuCBSJS 2---record count: %d",
                                duWaiFuCBSJs.size()));
                        return duWaiFuCBSJs.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DUWaiFuCBSJ>, Observable<DUWaiFuCBSJResult>>() {
                    @Override
                    public Observable<DUWaiFuCBSJResult> call(List<DUWaiFuCBSJ> duWaiFuCBSJs) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadWaiFuCBSJS 2---WaiFuCBSJ count: %d", duWaiFuCBSJs.size()));
                        DUWaiFuCBSJInfo duWaiFuCBSJInfo1 = new DUWaiFuCBSJInfo();
                        duWaiFuCBSJInfo1.setAccount(mAccount);
                        duWaiFuCBSJInfo1.setDuWaiFuCBSJList(duWaiFuCBSJs);
                        return mDataManager.uploadWaiFuCBSJs(duWaiFuCBSJInfo1);
                    }
                }).doOnNext(new Action1<DUWaiFuCBSJResult>() {
                    @Override
                    public void call(DUWaiFuCBSJResult duWaiFuCBSJResult) {
                        setSyncDataInfo(syncDataInfoList,
                                currentIndex[0],
                                duWaiFuCBSJResult.getFailureCount() == 0 ?
                                        SyncDataInfo.OperationResult.SUCCESS :
                                        SyncDataInfo.OperationResult.FAILURE,
                                duWaiFuCBSJResult.getSuccessCount(),
                                duWaiFuCBSJResult.getFailureCount());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUWaiFuCBSJResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadWaiFuCBSJS onCompleted---");
                        uploadMoreWaifuMedias(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---uploadWaiFuCBSJS onError: %s---", e.getMessage()));
                        uploadMoreWaifuMedias(true);
                    }

                    @Override
                    public void onNext(DUWaiFuCBSJResult duWaiFuCBSJResult) {
                        LogUtil.i(TAG, "---uploadWaiFuCBSJS onNext---");
                    }
                });
    }

    /**
     * 上传延迟数据
     */
    private void uploadDownloadDelayData() {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadDelayData 1---"));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_DELAY_RECORDS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_DELAY_RECORDS,
                    0,
                    "",
                    null,
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        final DUDelayRecordInfo duRecordInfo = new DUDelayRecordInfo(
                DUDelayRecordInfo.FilterType.NOT_UPLOADED, mAccount);
        mSubscription = mDataManager.getRecords(duRecordInfo)
                .filter(new Func1<List<DUDelayRecord>, Boolean>() {
                    @Override
                    public Boolean call(List<DUDelayRecord> duRecordList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadDelayData 2---record count: %d",
                                duRecordList.size()));
                        return duRecordList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DUDelayRecord>, Observable<DUDelayRecordResult>>() {
                    @Override
                    public Observable<DUDelayRecordResult> call(List<DUDelayRecord> duRecordList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadDelayData 3---record count: %d",
                                duRecordList.size()));
                        DUDelayRecordInfo duRecordInfo = new DUDelayRecordInfo(
                                DUDelayRecordInfo.FilterType.UPLOADING, mAccount, duRecordList);
                        return mDataManager.uploadRecords(duRecordInfo);
                    }
                })
                .doOnNext(new Action1<DUDelayRecordResult>() {
                    @Override
                    public void call(DUDelayRecordResult duRecordResult) {
                        setSyncDataInfo(syncDataInfoList,
                                0,
                                duRecordResult.getFailureCount() == 0 ?
                                        SyncDataInfo.OperationResult.SUCCESS :
                                        SyncDataInfo.OperationResult.FAILURE,
                                duRecordResult.getSuccessCount(),
                                duRecordResult.getFailureCount());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, 0,
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDelayRecordResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadDelayData 4 onCompleted---");

                        if (mSyncType == SyncType.UPLOADING_DOWNLOADING_DELAY_ALL) {
                            uploadDelayCards();
                        } else {
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadDelayData 5 onError--- error: %s", e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDelayRecordResult duRecordResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadDelayData 6 onNext--- "));
                    }
                });
    }

    private void deleteUploadPhoto(){
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---deleteUploadPhoto 1---"));
        mSubscription = mDataManager.deleteUploadMedias()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---deleteUploadPhoto 2 onCompleted---");
                        deleteTasks();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---deleteUploadPhoto 3 onError--- error: %s", e.getMessage()));
                        deleteTasks();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                    }
                });
    }

    private void downloadDelay() {
        LogUtil.i(TAG, "---downloadDelay 1---");

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }


        mSubscription = Observable.mergeDelayError(
                mDataManager.downloadDelayCards(),
                mDataManager.getDelayRecords(),
                mDataManager.getDelayQianFeiXXs(),
                mDataManager.getDelayChaoBiaoJL(),
                mDataManager.getDelayJiaoFeiXX(),
                mDataManager.getDelayHuanBiaoJL())
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case CARD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL;
                                break;
                            case RECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL;
                                break;
                            case QIANFEI:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL;
                                break;
                            case CHAOBIAOXX:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL;
                                break;
                            case JIAOFEIXX:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL;
                                break;
                            case HUANBIAOXX:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_DELAY_ALL;
                                break;
                            default:
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                0,
                                "",
                                null,
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadMore onNext---%s, count: %d",
                                name, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---download(volume) onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---download(volume) onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---download 2 onNext---");
                    }
                });
    }

    private void uploadMoreWaifuMedias(boolean firstTime) {
        LogUtil.i(TAG, "---uploadMoreWaifuMedias 1---");
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_WAIFU_MEDIAS.getName());
        if (firstTime) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_WAIFU_MEDIAS,
                    -1,
                    "waifu",
                    null,
                    SyncDataInfo.OperationResult.NONE,
                    true);
            offset = 0;
            LogUtil.i(TAG, "---uploadMoreWaifuMedias 2---");
        } else {
            offset += DUMediaInfo.LIMIT;
            LogUtil.i(TAG, "---uploadMoreWaifuMedias 3---");
        }


        isUploadMoreMediaFinished = false;
        DUMedia duMedia = new DUMedia();
        duMedia.setAccount(mAccount);
//        duMedia.setRenwubh(taskId);
//        duMedia.setCh(volume);
        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.MORE_ITEMS_WAIFU,
                DUMediaInfo.MeterReadingType.NORMAL,
                offset,
                DUMediaInfo.LIMIT,
                duMedia);

        mSubscription = mDataManager.getMediaList(duMediaInfo)
                .filter(new Func1<List<DUMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<DUMedia> duMediaList) {
                        isUploadMoreMediaFinished = duMediaList.size() <= 0;
                        return !isUploadMoreMediaFinished;
                    }
                })
                .concatMap(new Func1<List<DUMedia>, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(List<DUMedia> duMediaList) {
                        return mDataManager.uploadMoreMedias(duMediaList, true);
                    }
                })
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null) && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
//                            int taskId = duMedia.getRenwubh();
//                            String volume = duMedia.getCh();
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess =
                                    (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);

                            int index = findSyncDataInfoIndex(syncDataInfoList, -1, "waifu");
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, index);
                            if (syncDataInfo == null) {
                                return;
                            }

                            addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                    SyncSubDataInfo.OperationType.MEDIA_INFO,
                                    customerId,
                                    fileName,
                                    isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                            : SyncSubDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadMoreWaifuMedias onCompleted---");

                        if (isUploadMoreMediaFinished) {
                            if (mSyncType == SyncType.UPLOADING_WAIFUCBSJS_MEDIAS) {
                                postEndEvent();
                            } else {
                                downloadWaiFuMore();
                            }
                        } else {
                            mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMoreMediasEndInternal());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadMoreWaifuMedias onError---" + e.getMessage());

                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadMoreWaifuMedias onNext---");
                    }
                });
    }


//    private void uploadMediasForWaifu(boolean firstTime) {
//        LogUtil.i(TAG, "---uploadMediasForWaifu all 1---");
//        if (firstTime) {
//            offset = 0;
//            LogUtil.i(TAG, "---uploadMediasForWaifu 2---");
//        } else {
//            offset += DUMediaInfo.LIMIT;
//            LogUtil.i(TAG, "---uploadMediasForWaifu 3---");
//        }
//
//        mIsOperationFinished = false;
//        int taskId = 0;
//        String volume = "waifu";
//        final int[] currentIndex = {0};
//        final List<SyncDataInfo> syncDataInfoList =
//                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_WAIFU_MEDIAS.getName());
//        if (syncDataInfoList != null) {
//            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
//                List<SyncSubDataInfo> syncSubDataInfoList =
//                        syncDataInfo.getSyncSubDataInfoList();
//                if (syncSubDataInfoList != null) {
//                    syncSubDataInfoList.clear();
//                }
//            }
//            syncDataInfoList.clear();
//
//            addSyncDataInfo(syncDataInfoList,
//                    SyncDataInfo.OperationType.UPLOADING_WAIFU_MEDIAS,
//                    taskId,
//                    volume,
//                    null,
//                    SyncDataInfo.OperationResult.NONE,
//                    true);
//        }
//
//        DUMedia duMedia = new DUMedia(
//                mAccount,
//                taskId,
//                volume,
//                "");
//
//        DUMediaInfo duMediaInfo = new DUMediaInfo(
//                DUMediaInfo.OperationType.MORE_ITEMS,
//                DUMediaInfo.MeterReadingType.WAIFU,
//                offset,
//                DUMediaInfo.LIMIT,
//                duMedia);
//
//        mSubscription = mDataManager.uploadMedias(duMediaInfo)
//                .doOnNext(new Action1<DUMediaResult>() {
//                    @Override
//                    public void call(DUMediaResult duMediaResult) {
//                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
//                        if ((duMediaList != null) && (duMediaList.size() == 1)) {
//                            DUMedia duMedia = duMediaList.get(0);
//                            int taskId = duMedia.getRenwubh();
//                            String volume = duMedia.getCh();
//                            String customerId = duMedia.getCid();
//                            String fileName = duMedia.getWenjianmc();
//                            boolean isSuccess =
//                                    (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);
//
//                            currentIndex[0] = findSyncDataInfoIndex(syncDataInfoList, taskId, volume);
//                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, currentIndex[0]);
//                            if (syncDataInfo == null) {
//                                return;
//                            }
//
//                            addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
//                                    SyncSubDataInfo.OperationType.MEDIA_INFO,
//                                    customerId,
//                                    fileName,
//                                    isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
//                                            : SyncSubDataInfo.OperationResult.FAILURE);
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<DUMediaResult>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.i(TAG, "---uploadMediasForWaifu all onCompleted---");
//                        if (mSyncType == SyncType.UPLOADING_WAIFUCBSJS_MEDIAS) {
//                            postEndEvent();
//                        } else {
//                            downloadWaiFuMore();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.i(TAG, "---uploadMediasForWaifu all onError---" + e.getMessage());
//                        if (mSyncType == SyncType.UPLOADING_WAIFUCBSJS_MEDIAS) {
//                            postEndEvent();
//                        } else {
//                            downloadWaiFuMore();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(DUMediaResult duMediaResult) {
//                        LogUtil.i(TAG, "---uploadMediasForWaifu all onNext---");
//                    }
//                });
//    }


    private void uploadRushPays() {
        LogUtil.i(TAG, "---uploadRushPays---");
        final int[] currentIndex = {0};
        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_RUSH_PAY_TASKS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }
        DURushPayTaskInfo duRushPayTaskInfo = new DURushPayTaskInfo(mAccount, NOT_UPLOAD);

        mSubscription = mDataManager.getRushPayTasks(duRushPayTaskInfo, true)
                .concatMap(new Func1<List<DURushPayTask>, Observable<DURushPayTaskResult>>() {
                    @Override
                    public Observable<DURushPayTaskResult> call(List<DURushPayTask> duRushPayTaskList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadRushPays 2---task count: %d", duRushPayTaskList.size()));
                        if (syncDataInfoList != null) {
                            for (DURushPayTask duRushPayTask : duRushPayTaskList) {
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.UPLOADING_RUSH_PAY_TASKS,
                                        duRushPayTask.getI_TaskId(),
                                        "",
                                        null,
                                        SyncDataInfo.OperationResult.NONE,
                                        false);
                            }
                        }
                        DURushPayTaskInfo duRushPayTaskInfo = new DURushPayTaskInfo();
                        duRushPayTaskInfo.setAccount(mAccount);
                        duRushPayTaskInfo.setDuRushPayTaskList(duRushPayTaskList);
                        return mDataManager.uploadRushPayTasks(duRushPayTaskInfo);
                    }
                }).doOnNext(new Action1<DURushPayTaskResult>() {
                    @Override
                    public void call(DURushPayTaskResult duRushPayTaskResult) {
                        setSyncDataInfo(syncDataInfoList,
                                currentIndex[0],
                                duRushPayTaskResult.getFailureCount() == 0 ?
                                        SyncDataInfo.OperationResult.SUCCESS :
                                        SyncDataInfo.OperationResult.FAILURE,
                                duRushPayTaskResult.getSuccessCount(),
                                duRushPayTaskResult.getFailureCount());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DURushPayTaskResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadRushPays 6 onCompleted---");
//                        if (mSyncType == SyncType.UPLOADING_DOWNLOADING_ALL_JICHA_TASKS) {
                        uploadRushPayMedias();
//                        } else {
//                            deleteJiChaTasks();
//                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadRushPays 7 onError---%s", e.getMessage()));
//                        if (mSyncType == SyncType.UPLOADING_DOWNLOADING_ALL_JICHA_TASKS) {
                        uploadRushPayMedias();
//                        } else {
//                            deleteJiChaTasks();
//                        }
                    }

                    @Override
                    public void onNext(DURushPayTaskResult duRushPayTaskResult) {
//                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
//                                "---uploadRushPays 8 onNext--- taskId: %d, volume: %s",
//                                duSamplingResult.getTaskId(), duSamplingResult.getVolume()));
                    }
                });
    }


    /**
     *
     */
    private void uploadRushPayMedias() {
        LogUtil.i(TAG, "---uploadJiChaMedias  1---");

        mIsOperationFinished = false;
        final int[] currentIndex = {0};
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(UPLOADING_RUSH_PAY_TASK_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }
            syncDataInfoList.clear();

            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_RUSH_PAY_TASK_MEDIAS,
                    0,
                    "",
                    "RushPay",
                    SyncDataInfo.OperationResult.NONE,
                    true);
        }

        DUMedia duMedia = new DUMedia();
        duMedia.setAccount(mAccount);
        duMedia.setType(DUMedia.MEDIA_TYPE_RUSH_PAY);

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.NOT_UPLOADED,
                DUMediaInfo.MeterReadingType.RUSH_PAY,
                duMedia);
        mSubscription = mDataManager.uploadMedias(duMediaInfo)
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null) && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            int taskId = duMedia.getRenwubh();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess =
                                    (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);

                            currentIndex[0] = findSyncDataInfoIndex(syncDataInfoList, 0, "");
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, currentIndex[0]);
                            if (syncDataInfo == null) {
                                return;
                            }

                            addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                    SyncSubDataInfo.OperationType.MEDIA_INFO,
                                    duMedia.getCid(),
                                    fileName,
                                    isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                            : SyncSubDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadRushPayMedias all onCompleted---");
                        deleteRushPayTasks();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadRushPayMedias all onError---" + e.getMessage());
                        deleteRushPayTasks();
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadRushPayMedias all onNext---");
                    }
                });
    }

    public void deleteRushPayTasks() {
        LogUtil.i(TAG, "---deleteJiChaTasks 1---");
        mIsOperationFinished = false;

        final int[] totalCount = new int[1];
        mSubscription = mDataManager.getRemovedRushPayTasks(mAccount)
                .concatMap(new Func1<List<DURushPayTask>, Observable<DURushPayTask>>() {
                    @Override
                    public Observable<DURushPayTask> call(List<DURushPayTask> duRushPayTasks) {
                        LogUtil.i(TAG, "---deleteTasks 2---");
                        totalCount[0] = duRushPayTasks.size();
                        return Observable.from(duRushPayTasks);
                    }
                })
                .concatMap(new Func1<DURushPayTask, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(DURushPayTask duRushPayTask) {
                        totalCount[0]--;
                        DURushPayTaskInfo duRushPayTaskInfo = new DURushPayTaskInfo(
                                mAccount,
                                duRushPayTask.getI_TaskId(),
                                DURushPayTaskInfo.FilterType.DELETE);
                        return mDataManager.deleteRushPayTask(duRushPayTaskInfo);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---deleteTasks onCompleted---");
                        if (totalCount[0] <= 0) {
                            LogUtil.i(TAG, "---uploadData 6 onCompleted---post event");
//                            if (mSyncType == SyncType.UPLOADING_DOWNLOADING_ALL_DATA) {
                            //syncWaiFuCBSJS();
                            downloadRushPays();
//                            } else {
//                                postEndEvent();
//                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---deleteTasks onError---" + e.getMessage());
                        downloadRushPays();
//                        postEndEvent();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---deleteTasks onNext---" + aBoolean);
                    }
                });
    }

    private void downloadRushPays() {
        LogUtil.i(TAG, "---downloadRushPays 1---");
        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_RUSHPAYTASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        DURushPayTaskInfo duRushPayTaskInfo = new DURushPayTaskInfo();
        duRushPayTaskInfo.setAccount(mAccount);
        mSubscription = mDataManager.downloadRushPays(duRushPayTaskInfo)
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        for (DURushPayTask duRushPayTask : duDownloadResult.getDuRushPayTaskList()) {
                            LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                    "---download onNext---taskId: %d",
                                    duRushPayTask.getI_TaskId()
                            ));
                            if (duRushPayTask.isNewData()) {
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.DOWNLOADING_RUSHPAYTASK,
                                        duRushPayTask.getI_TaskId(),
                                        "",//duJiChaTask.getcH(),
                                        null,
                                        SyncDataInfo.OperationResult.SUCCESS,
                                        false);
                            }

                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---download onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---download onError---%s", e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---download onNext---");
                    }
                });
    }

    /**
     * @param taskId
     */
    private void uploadSamplingsData(final int taskId) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadData(taskId) 1---taskId: %d", taskId));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOlUME_SAMPLINGS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_VOlUME_SAMPLINGS,
                    taskId,
                    "",
                    null,
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        final DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                mAccount,
                taskId,
                DUSamplingInfo.FilterType.NOT_UPLOADED_SAMPLINGDATA);
        mSubscription = mDataManager.getSamplings(duSamplingInfo)
                .filter(new Func1<List<DUSampling>, Boolean>() {
                    @Override
                    public Boolean call(List<DUSampling> duSamplingList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadSaplingsData(taskId) 2---record count: %d",
                                duSamplingList.size()));

                        return duSamplingList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DUSampling>, Observable<DUSamplingResult>>() {
                    @Override
                    public Observable<DUSamplingResult> call(List<DUSampling> duSamplingList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadSaplingsData(taskId) 3---record count: %d",
                                duSamplingList.size()));
                        DUSamplingInfo DUSamplingsInfo = new DUSamplingInfo(
                                mAccount,
                                taskId,
                                DeviceUtil.getDeviceID(getApplicationContext()),
                                DUSamplingInfo.FilterType.UPLOADING,
                                duSamplingList);
                        return mDataManager.uploadSamplings(DUSamplingsInfo);
                    }
                })
                .doOnNext(new Action1<DUSamplingResult>() {
                    @Override
                    public void call(DUSamplingResult duSamplingResult) {
                        setSyncDataInfo(syncDataInfoList,
                                0,
                                duSamplingResult.getFailureCount() == 0 ?
                                        SyncDataInfo.OperationResult.SUCCESS :
                                        SyncDataInfo.OperationResult.FAILURE,
                                duSamplingResult.getSuccessCount(),
                                duSamplingResult.getFailureCount());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, 0,
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUSamplingResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadData(taskId, volume) 4 onCompleted---");

                        if (mSyncType == SyncType.UPLOADING_SAMPLING) {
//                            if (mHasUploadingCardsFunction) {
//                                uploadCards(taskId, volume, null);
//                            } else {
//                                uploadBackFlowHTs(taskId, volume);
//                                //uploadMedias(taskId, volume);
//                            }
                            uploadSamplingMedias(taskId);
                        } else {
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadSaplingsData(taskId) 5 onError--- taskId: %d, error: %s",
                                taskId, e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUSamplingResult duSamplingResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData(taskId, volume) 6 onNext--- taskId: %d",
                                taskId));
                    }
                });
    }


    private void downloadSamplingAndBiaoKaXX(final int taskId) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---download(volume) 1---taskId: %d", taskId));

        mIsOperationFinished = false;
        mIsDownloadingTotal = true;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ONE_TASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        List<DUSamplingTask> duSamplingTaskList = new ArrayList<>();
        DUSamplingTask duSamplingTask = new DUSamplingTask();
        duSamplingTask.setRenWuBH(taskId);
        duSamplingTaskList.add(duSamplingTask);
        mSubscription = Observable.mergeDelayError(
                downloadSamplingCards(duSamplingTaskList),
                downloadSamplingRecords(duSamplingTaskList))
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
//                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
//                        switch (filterType) {
//                            case CARD:
//                                operationType = SyncDataInfo.OperationType.DOWNLOADING_CARDS;
//                                break;
//                            case RECORD:
//                                operationType = SyncDataInfo.OperationType.DOWNLOADING_RECORDS;
//                                break;
//                            case BACKFLOW:
//                                operationType = SyncDataInfo.OperationType.DOWNLOADING_BACKFLOW;
//                                break;
//                            default:
//                                break;
//                        }

//                        addSyncDataInfo(syncDataInfoList,
//                                operationType,
//                                0,
//                                volume,
//                                null,
//                                SyncDataInfo.OperationResult.SUCCESS,
//                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadMore onNext---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---download(volume) onCompleted---");
                        DUSamplingTaskInfo duSamplingTaskInfo = new DUSamplingTaskInfo(
                                DUSamplingTaskInfo.FilterType.UPDATE_TASK_COUNT,
                                mAccount,
                                taskId,
                                "",
                                false);
                        mSubscription = mDataManager.updateSamplingTask(duSamplingTaskInfo)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Boolean>() {
                                    @Override
                                    public void onCompleted() {
                                        LogUtil.i(TAG, "---download(volume) 1 onCompleted---");
                                        postEndEvent();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtil.i(TAG, "---download(volume) 2 onError---" + e.getMessage());
                                        postEndEvent();
                                    }

                                    @Override
                                    public void onNext(Boolean aBoolean) {
                                        LogUtil.i(TAG, "---download(volume) 2 onNext---");
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---download(volume) onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---download 2 onNext---");
                    }
                });
    }



    /**
     * @param duSamplingTasks
     */
    private void downloadSamplingMore(List<DUSamplingTask> duSamplingTasks) {
        LogUtil.i(TAG, "---downloadMore 1---");
        if ((duSamplingTasks == null) || (duSamplingTasks.size() <= 0)) {
            LogUtil.i(TAG, "---downloadMore 2---");
            postEndEvent();
            return;
        }

        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ALL_SAMPING_TASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        Observable<DUDownloadResult> observable
                = Observable.mergeDelayError(
                downloadSamplingCards(duSamplingTasks),
                downloadSamplingRecords(duSamplingTasks)
        );

        mSubscription = observable
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case CARD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_CARDS;
                                break;
                            case RECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_RECORDS;
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                duDownloadResult.getTaskId(),
                                volume,
                                null,
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadMore onNext---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadMore onCompleted---");

                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---downloadMore onError---" + e.getMessage());

                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---downloadMore onNext---");
                    }
                });
    }

    /**
     * downloadWaiFuMore
     */
    private void downloadWaiFuMore() {
        LogUtil.i(TAG, "---downloadWaiFuMore 1---");
        if (mAccount == null) {
            LogUtil.i(TAG, "---downloadWaiFuMore 2---");
            postEndEvent();
            return;
        }

        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(DOWNLOADING_ALL_DATA_OF_ALL_WAIFU.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        Observable<DUDownloadResult> observable
                = Observable.mergeDelayError(
                downloadWaiFuCards(mAccount),
                downloadWaiFuCBSJs(mAccount)
        );

        mSubscription = observable
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case CARD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_CARDS;
                                break;
                            case RECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_RECORDS;
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                duDownloadResult.getCount(),
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadWaiFuMore onNext---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadWaiFuMore onCompleted---");

                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---downloadWaiFuMore onError---" + e.getMessage());

                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---downloadWaiFuMore onNext---");
                    }
                });
    }

    /**
     * 下载册本列表
     * @param taskId
     * @param volume
     */
    private void download(final int taskId, final String volume) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---download(volume) 1---taskId: %d, volume: %s", taskId, volume));

        mIsOperationFinished = false;
        mIsDownloadingTotal = true;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ONE_TASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        List<DUTask> duTaskList = new ArrayList<>();
        DUTask duTask = new DUTask();
        duTask.setRenWuBH(taskId);
        duTask.setcH(volume);
        duTaskList.add(duTask);
        mSubscription = Observable.mergeDelayError(
                downloadCards(duTaskList, true),
                downloadRecords(duTaskList))
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case CARD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_CARDS;
                                break;
                            case RECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_RECORDS;
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                0,
                                volume,
                                null,
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadMore onNext---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---download(volume) onCompleted---");

                        DUTaskInfo duTaskInfo = new DUTaskInfo(
                                DUTaskInfo.FilterType.UPDATE_TASK_COUNT,
                                mAccount,
                                taskId,
                                volume,
                                false);
                        mSubscription = mDataManager.updateTask(duTaskInfo)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Boolean>() {
                                    @Override
                                    public void onCompleted() {
                                        LogUtil.i(TAG, "---download(volume) 1 onCompleted---");
                                        postEndEvent();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtil.i(TAG, "---download(volume) 2 onError---" + e.getMessage());
                                        postEndEvent();
                                    }

                                    @Override
                                    public void onNext(Boolean aBoolean) {
                                        LogUtil.i(TAG, "---download(volume) 2 onNext---");
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---download(volume) onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---download 2 onNext---");
                    }
                });
    }

    private void downloadVolumeCards(final int taskId, final String volume) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---downloadVolumeCards(volume) 1---taskId: %d, volume: %s", taskId, volume));

        mIsOperationFinished = false;
        mIsDownloadingTotal = true;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ONE_TASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        List<DUTask> duTaskList = new ArrayList<>();
        DUTask duTask = new DUTask();
        duTask.setRenWuBH(taskId);
        duTask.setcH(volume);
        duTaskList.add(duTask);
        mSubscription = downloadCards(duTaskList, true)
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case CARD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_CARDS;
                                break;
                            case RECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_RECORDS;
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                0,
                                volume,
                                null,
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadMore onNext---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadVolumeCards(volume) onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---download(volume) onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---download 2 onNext---");
                    }
                });
    }

    /**
     * @param taskId
     * @param volume
     */
    private void downloadDetailInfo(int taskId, String volume) {
        LogUtil.i(TAG, "---downloadDetailInfo 1---");

        mIsOperationFinished = false;

        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ONE_TASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        List<DUTask> duTaskList = new ArrayList<>();
        DUTask duTask = new DUTask();
        duTask.setRenWuBH(taskId);
        duTask.setcH(volume);
        duTaskList.add(duTask);
        mSubscription = Observable.mergeDelayError(
                downloadChaoBiaoJLs(duTaskList),
                downloadJiaoFeiXXs(duTaskList),
                downloadQianFeiXXs(duTaskList),
                downloadHuanBiaoXXs(duTaskList))
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case PRERECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_PRERECORDS;
                                break;
                            case PAYMENT:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_PAYMENTS;
                                break;
                            case ARREARAGE:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_ARREARAGES;
                                break;
                            case REPLACEMENT:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_REPLACEMENTS;
                                break;
                            case TEMPORARY:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_TEMPORARY;
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                0,
                                volume,
                                null,
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadDetailInfo---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadDetailInfo onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---downloadDetailInfo onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---downloadDetailInfo onNext---");
                    }
                });
    }

    /**
     * @param customerId
     */
    private void downloadDetailInfo(int taskId, String volume, String customerId) {
        LogUtil.i(TAG, "---downloadDetailInfo(customerId) 1---");

        mIsOperationFinished = false;

        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_ALL_DATA_OF_ONE_TASK.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        mSubscription = Observable.mergeDelayError(
                downloadChaoBiaoJLs(volume, customerId),
                downloadJiaoFeiXXs(volume, customerId),
                downloadQianFeiXXs(volume, customerId),
                downloadHuanBiaoXXs(volume, customerId),
                downloadCard(volume, customerId))
                .doOnNext(new Action1<DUDownloadResult>() {
                    @Override
                    public void call(DUDownloadResult duDownloadResult) {
                        String name = TextUtil.getString(duDownloadResult.getFilterType().getName());
                        String volume = TextUtil.getString(duDownloadResult.getVolume());
                        DUDownloadResult.FilterType filterType = duDownloadResult.getFilterType();
                        int count = duDownloadResult.getCount();
                        SyncDataInfo.OperationType operationType = SyncDataInfo.OperationType.NONE;
                        switch (filterType) {
                            case PRERECORD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_PRERECORDS;
                                break;
                            case PAYMENT:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_PAYMENTS;
                                break;
                            case ARREARAGE:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_ARREARAGES;
                                break;
                            case REPLACEMENT:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_REPLACEMENTS;
                                break;
                            case TEMPORARY:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_TEMPORARY;
                                break;
                            case CARD:
                                operationType = SyncDataInfo.OperationType.DOWNLOADING_CARD;
                                break;
                        }

                        addSyncDataInfo(syncDataInfoList,
                                operationType,
                                0,
                                volume,
                                null,
                                SyncDataInfo.OperationResult.SUCCESS,
                                false);

                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadDetailInfo(customerId)---%s, volume: %s, count: %d",
                                name, volume, count));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUDownloadResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadDetailInfo(customerId) onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---downloadDetailInfo(customerId) onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUDownloadResult duDownloadResult) {
                        LogUtil.i(TAG, "---downloadDetailInfo(customerId) onNext---");
                    }
                });
    }

    private Observable<DUDownloadResult> downloadCards(List<DUTask> duTaskList, final boolean isDownloadingNewly) {
        LogUtil.i(TAG, "---getCards 1---");
        return Observable.from(duTaskList)
                .concatMap(new Func1<DUTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(final DUTask duTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---getCards 2---taskId: %d, volume: %s",
                                duTask.getRenWuBH(), duTask.getcH()));

                        if (isDownloadingNewly) { // TODO: 2017/10/9, mIsDownloadingTotal
                            LogUtil.i(TAG, "---downloadCards---total");
                            return mDataManager.downloadCards(new DUCardInfo(
                                    duTask.getcH(),
                                    DUCardInfo.FilterType.FORMAL));
                        } else {
                            LogUtil.i(TAG, "---downloadCards---not existing");
                            return mDataManager.getCards(new DUCardInfo(
                                    duTask.getcH(),
                                    DUCardInfo.FilterType.SEARCHING_ALL))
                                    .filter(new Func1<List<DUCard>, Boolean>() {
                                        @Override
                                        public Boolean call(List<DUCard> duCards) {
                                            return duCards.size() <= 0;
                                        }
                                    })
                                    .concatMap(new Func1<List<DUCard>, Observable<? extends DUDownloadResult>>() {
                                        @Override
                                        public Observable<? extends DUDownloadResult> call(List<DUCard> duCards) {
                                            return mDataManager.downloadCards(new DUCardInfo(
                                                    duTask.getcH(),
                                                    DUCardInfo.FilterType.FORMAL));
                                        }
                                    });
                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    private Observable<DUDownloadResult> downloadSamplingCards(List<DUSamplingTask> duSamplingTasks) {
        LogUtil.i(TAG, "---downloadSamplingCards 1---");
        return Observable.from(duSamplingTasks)
                .concatMap(new Func1<DUSamplingTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(final DUSamplingTask duSamplingTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---downloadSamplingCards 2---taskId: %d",
                                duSamplingTask.getRenWuBH()));

//                        if (mIsDownloadingTotal) {
                        LogUtil.i(TAG, "---downloadSamplingCards---total");
                        return mDataManager.downloadSamplingCards(new DUCardInfo(
                                duSamplingTask.getRenWuBH(),
                                DUCardInfo.FilterType.SAMPLING));
//                        } else {
//                            LogUtil.i(TAG, "---downloadSamplingCards---not existing");
//                            DUCardInfo duCardInfo = new DUCardInfo();
//                            duCardInfo.setTaskId(duSamplingTask.getRenWuBH());
//                            duCardInfo.setFilterType(DUCardInfo.FilterType.SEARCHING_ALL);
//                            return mDataManager.getCards(duCardInfo)
//                                    .filter(new Func1<List<DUCard>, Boolean>() {
//                                        @Override
//                                        public Boolean call(List<DUCard> duCards) {
//                                            return duCards.size() <= 0;
//                                        }
//                                    })
//                                    .concatMap(new Func1<List<DUCard>, Observable<? extends DUDownloadResult>>() {
//                                        @Override
//                                        public Observable<? extends DUDownloadResult> call(List<DUCard> duCards) {
//                                            return mDataManager.downloadSamplingCards(new DUCardInfo(
//                                                    duSamplingTask.getRenWuBH(),
//                                                    DUCardInfo.FilterType.FORMAL));
//                                        }
//                                    });
//                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadWaiFuCards(String mAccount) {
        LogUtil.i(TAG, "---downloadWaiFuCards 1---");
        DUCardInfo duCardInfo = new DUCardInfo();
        duCardInfo.setAccount(mAccount);
        return mDataManager.downloadWaiFuCards(duCardInfo)
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadRecords(List<DUTask> duTaskList) {
        LogUtil.i(TAG, "---getRecords 1---");
        return Observable.from(duTaskList)
                .concatMap(new Func1<DUTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(final DUTask duTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---getRecords 2---taskId: %d, volume: %s",
                                duTask.getRenWuBH(), duTask.getcH()));

                        if (false) { // TODO: 2017/10/9 ,mIsDownloadingTotal
                            LogUtil.i(TAG, "---downloadRecords---total");
                            DURecordInfo duRecordInfo = new DURecordInfo(
                                    duTask.getRenWuBH(),
                                    duTask.getcH());
                            return mDataManager.downloadRecords(duRecordInfo);
                        } else {
                            LogUtil.i(TAG, "---downloadRecords---not existing");
                            DURecordInfo duRecordInfo = new DURecordInfo(
                                    mAccount,
                                    duTask.getRenWuBH(),
                                    duTask.getcH(),
                                    DURecordInfo.FilterType.EACH_VOLUME);
                            return mDataManager.getRecords(duRecordInfo)
                                    .filter(new Func1<List<DURecord>, Boolean>() {
                                        @Override
                                        public Boolean call(List<DURecord> duRecords) {
                                            return duRecords.size() <= 0;
                                        }
                                    })
                                    .concatMap(new Func1<List<DURecord>, Observable<? extends DUDownloadResult>>() {
                                        @Override
                                        public Observable<? extends DUDownloadResult> call(List<DURecord> duRecords) {
                                            DURecordInfo duRecordInfo = new DURecordInfo(
                                                    duTask.getRenWuBH(),
                                                    duTask.getcH());
                                            return mDataManager.downloadRecords(duRecordInfo);
                                        }
                                    });
                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    private Observable<DUDownloadResult> downloadSamplingRecords(List<DUSamplingTask> duSamplingTaskList) {
        LogUtil.i(TAG, "---getRecords 1---");
        return Observable.from(duSamplingTaskList)
                .concatMap(new Func1<DUSamplingTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(final DUSamplingTask duSamplingTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---getRecords 2---taskId: %d",
                                duSamplingTask.getRenWuBH()));

                        if (false) { // TODO: 2017/10/9, mIsDownloadingTotal
                            LogUtil.i(TAG, "---downloadRecords---total");
                            DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                                    duSamplingTask.getRenWuBH(),
                                    ""//duSamplingTask.getcH()
                            );
                            duSamplingInfo.setAccount(mAccount);
                            return mDataManager.downloadSamplingRecords(duSamplingInfo);
                        } else {
                            LogUtil.i(TAG, "---downloadRecords---not existing");
                            DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                                    mAccount,
                                    duSamplingTask.getRenWuBH(),
//                                    duSamplingTask.getcH(),
                                    DUSamplingInfo.FilterType.EACH_VOLUME);
                            return mDataManager.getSamplings(duSamplingInfo)
                                    .filter(new Func1<List<DUSampling>, Boolean>() {
                                        @Override
                                        public Boolean call(List<DUSampling> duSamplings) {
                                            return duSamplings.size() <= 0;
                                        }
                                    })
                                    .concatMap(new Func1<List<DUSampling>, Observable<? extends DUDownloadResult>>() {
                                        @Override
                                        public Observable<? extends DUDownloadResult> call(List<DUSampling> duSamplings) {
                                            DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                                                    duSamplingTask.getRenWuBH(),
                                                    ""//duSamplingTask.getcH()
                                            );
                                            duSamplingInfo.setAccount(mAccount);
                                            return mDataManager.downloadSamplingRecords(duSamplingInfo);
                                        }
                                    });
                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadWaiFuCBSJs(String account) {
        LogUtil.i(TAG, "---downloadWaiFuCBSJs 1---");
        DUWaiFuCBSJInfo duWaiFuCBSJInfo = new DUWaiFuCBSJInfo();
        duWaiFuCBSJInfo.setAccount(mAccount);
        return mDataManager.downloadWaiFuSJs(duWaiFuCBSJInfo)
                .subscribeOn(Schedulers.io());

    }

    private Observable<DUDownloadResult> downloadTemporaryRecordsAndCards() {
        LogUtil.i(TAG, "---downloadTemporaryRecords 1---");
        DURecordInfo duRecordInfo = new DURecordInfo(mAccount);
        return mDataManager.downloadTemporaryRecordsAndCards(duRecordInfo)
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadChaoBiaoJLs(List<DUTask> duTaskList) {
        return Observable.from(duTaskList)
                .concatMap(new Func1<DUTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(DUTask duTask) {
                        DUChaoBiaoJLInfo duChaoBiaoJLInfo = new DUChaoBiaoJLInfo(
                                duTask.getcH(),
                                mMonthCount
                        );
                        return mDataManager.downloadChaoBiaoJLs(duChaoBiaoJLInfo);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadJiaoFeiXXs(List<DUTask> duTaskList) {
        return Observable.from(duTaskList)
                .concatMap(new Func1<DUTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(DUTask duTask) {
                        DUJiaoFeiXXInfo duJiaoFeiXXInfo = new DUJiaoFeiXXInfo(
                                duTask.getcH(),
                                mMonthCount
                        );
                        return mDataManager.downloadJiaoFeiXXs(duJiaoFeiXXInfo);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadQianFeiXXs(List<DUTask> duTaskList) {
        return Observable.from(duTaskList)
                .concatMap(new Func1<DUTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(DUTask duTask) {
                        DUQianFeiXXInfo duQianFeiXXInfo = new DUQianFeiXXInfo(
                                duTask.getcH(),
                                mMonthCount
                        );
                        return mDataManager.downloadQianFeiXXs(duQianFeiXXInfo);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadHuanBiaoXXs(List<DUTask> duTaskList) {
        return Observable.from(duTaskList)
                .concatMap(new Func1<DUTask, Observable<DUDownloadResult>>() {
                    @Override
                    public Observable<DUDownloadResult> call(DUTask duTask) {
                        DUHuanBiaoJLInfo duHuanBiaoJLInfo = new DUHuanBiaoJLInfo(
                                duTask.getcH(),
                                mMonthCount);
                        return mDataManager.downloadHuanBiaoXXs(duHuanBiaoJLInfo);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadCard(String volume, String customerId) {
        DUCardInfo duCardInfo = new DUCardInfo(
                DUCardInfo.FilterType.FORMAL,
                volume,
                customerId);
        return mDataManager.downloadCards(duCardInfo)
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadChaoBiaoJLs(String volume, String customerId) {
        DUChaoBiaoJLInfo duChaoBiaoJLInfo = new DUChaoBiaoJLInfo(volume, customerId);
        return mDataManager.downloadChaoBiaoJLs(duChaoBiaoJLInfo)
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadJiaoFeiXXs(String volume, String customerId) {
        DUJiaoFeiXXInfo duJiaoFeiXXInfo = new DUJiaoFeiXXInfo(volume, customerId);
        return mDataManager.downloadJiaoFeiXXs(duJiaoFeiXXInfo)
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadQianFeiXXs(String volume, String customerId) {
        DUQianFeiXXInfo duQianFeiXXInfo = new DUQianFeiXXInfo(volume, customerId);
        return mDataManager.downloadQianFeiXXs(duQianFeiXXInfo)
                .subscribeOn(Schedulers.io());
    }

    private Observable<DUDownloadResult> downloadHuanBiaoXXs(String volume, String customerId) {
        DUHuanBiaoJLInfo duHuanBiaoJLInfo = new DUHuanBiaoJLInfo(volume, customerId);
        return mDataManager.downloadHuanBiaoXXs(duHuanBiaoJLInfo)
                .subscribeOn(Schedulers.io());
    }

    /**
     *
     */
    private void uploadData() {
        LogUtil.i(TAG, "---uploadData 1---");

        mIsOperationFinished = false;
        final int[] currentIndex = {0};
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_RECORDS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        DUTaskInfo duTaskInfo = new DUTaskInfo(mAccount);
        mSubscription = mDataManager.getTasks(duTaskInfo, true)
                .concatMap(new Func1<List<DUTask>, Observable<DUTask>>() {
                    @Override
                    public Observable<DUTask> call(List<DUTask> duTaskList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 2---task count: %d", duTaskList.size()));

                        if (syncDataInfoList != null) {
                            for (DUTask duTask : duTaskList) {
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.UPLOADING_VOLUME_RECORDS,
                                        duTask.getRenWuBH(),
                                        duTask.getcH(),
                                        null,
                                        SyncDataInfo.OperationResult.NONE,
                                        false);
                            }
                        }

                        return Observable.from(duTaskList);
                    }
                })
                .concatMap(new Func1<DUTask, Observable<List<DURecord>>>() {
                    @Override
                    public Observable<List<DURecord>> call(DUTask duTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 3---taskId: %d, volume: %s",
                                duTask.getRenWuBH(), duTask.getcH()));

                        currentIndex[0] = findSyncDataInfoIndex(syncDataInfoList,
                                duTask.getRenWuBH(), duTask.getcH());

                        DURecordInfo duRecordInfo = new DURecordInfo(
                                mAccount,
                                duTask.getRenWuBH(),
                                duTask.getcH(),
                                DURecordInfo.FilterType.NOT_UPLOADED);
                        return mDataManager.getRecords(duRecordInfo);
                    }
                })
                .filter(new Func1<List<DURecord>, Boolean>() {
                    @Override
                    public Boolean call(List<DURecord> duRecordList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 4---record count: %d",
                                duRecordList.size()));

                        boolean result = duRecordList.size() > 0;
                        if (!result) {
                            setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                    SyncDataInfo.OperationResult.NONE);
                        }

                        return result;
                    }
                })
                .concatMap(new Func1<List<DURecord>, Observable<DURecordResult>>() {
                    @Override
                    public Observable<DURecordResult> call(List<DURecord> duRecordList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 5---record count: %d",
                                duRecordList.size()));

                        DURecordInfo duRecordInfo = new DURecordInfo(
                                mAccount,
                                duRecordList.get(0).getRenwubh(),
                                TextUtil.getString(duRecordList.get(0).getCh()),
                                DURecordInfo.FilterType.UPLOADING,
                                duRecordList);
                        return mDataManager.uploadRecords(duRecordInfo);
                    }
                })
                .doOnNext(new Action1<DURecordResult>() {
                    @Override
                    public void call(DURecordResult duRecordResult) {
                        setSyncDataInfo(syncDataInfoList,
                                currentIndex[0],
                                duRecordResult.getFailureCount() == 0 ?
                                        SyncDataInfo.OperationResult.SUCCESS :
                                        SyncDataInfo.OperationResult.FAILURE,
                                duRecordResult.getSuccessCount(),
                                duRecordResult.getFailureCount());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DURecordResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadData 6 onCompleted---");
                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingDataEndInternal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 7 onError---%s", e.getMessage()));
                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingDataEndInternal());
                    }

                    @Override
                    public void onNext(DURecordResult duRecordResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData 8 onNext--- taskId: %d, volume: %s",
                                duRecordResult.getTaskId(), duRecordResult.getVolume()));
                    }
                });
    }

    /**
     * @param taskId
     * @param volume
     */
    private void uploadData(final int taskId, final String volume) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadData(taskId, volume) 1---taskId: %d, volume: %s", taskId, volume));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_RECORDS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_VOLUME_RECORDS,
                    taskId,
                    volume,
                    null,
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        final DURecordInfo duRecordInfo = new DURecordInfo(
                mAccount,
                taskId,
                TextUtil.getString(volume),
                DURecordInfo.FilterType.NOT_UPLOADED);
        mSubscription = mDataManager.getRecords(duRecordInfo)
                .filter(new Func1<List<DURecord>, Boolean>() {
                    @Override
                    public Boolean call(List<DURecord> duRecordList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData(taskId, volume) 2---record count: %d",
                                duRecordList.size()));

                        return duRecordList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DURecord>, Observable<DURecordResult>>() {
                    @Override
                    public Observable<DURecordResult> call(List<DURecord> duRecordList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData(taskId, volume) 3---record count: %d",
                                duRecordList.size()));
                        DURecordInfo duRecordInfo = new DURecordInfo(
                                mAccount,
                                taskId,
                                TextUtil.getString(volume),
                                DURecordInfo.FilterType.UPLOADING,
                                duRecordList);
                        return mDataManager.uploadRecords(duRecordInfo);
                    }
                })
                .doOnNext(new Action1<DURecordResult>() {
                    @Override
                    public void call(DURecordResult duRecordResult) {
                        setSyncDataInfo(syncDataInfoList,
                                0,
                                duRecordResult.getFailureCount() == 0 ?
                                        SyncDataInfo.OperationResult.SUCCESS :
                                        SyncDataInfo.OperationResult.FAILURE,
                                duRecordResult.getSuccessCount(),
                                duRecordResult.getFailureCount());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, 0,
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DURecordResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadData(taskId, volume) 4 onCompleted---");

                        if (mSyncType == SyncType.UPLOADING_VOLUME) {
                            if (mHasUploadingCardsFunction) {
                                uploadCards(taskId, volume, null);
                            } else {
                                uploadMedias(taskId, volume);
                            }
                        } else {
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData(taskId, volume) 5 onError--- taskId: %d, volume: %s, error: %s",
                                taskId, volume, e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DURecordResult duRecordResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData(taskId, volume) 6 onNext--- taskId: %d, volume: %s",
                                taskId, volume));
                    }
                });
    }

    /**
     * @param taskId
     * @param volume
     * @param customerId
     * @param orderNumber
     */
    private void uploadData(final int taskId,
                            final String volume,
                            final String customerId,
                            final int orderNumber) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadData single 1---taskId: %d, volume: %s, customerId: %s, orderNumber: %d",
                taskId, volume, customerId, orderNumber));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_RECORD.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_SINGLE_RECORD,
                    taskId,
                    volume,
                    customerId,
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        DURecordInfo duRecordInfo = new DURecordInfo(DURecordInfo.FilterType.ONE,
                mAccount, taskId, volume, customerId, orderNumber);
        mSubscription = mDataManager.getRecord(duRecordInfo)
                .filter(new Func1<DURecord, Boolean>() {
                    @Override
                    public Boolean call(DURecord duRecord) {
                        return (duRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO)
                                && (duRecord.getShangchuanbz() == DURecord.SHANGCHUANBZ_WEISHANGC);
                    }
                })
                .concatMap(new Func1<DURecord, Observable<DURecordResult>>() {
                    @Override
                    public Observable<DURecordResult> call(DURecord duRecord) {
                        LogUtil.i(TAG, "---uploadData single 2---");
                        List<DURecord> duRecordList = new ArrayList<DURecord>();
                        duRecordList.add(duRecord);
                        DURecordInfo duRecordInfo = new DURecordInfo(
                                mAccount,
                                duRecord.getRenwubh(),
                                TextUtil.getString(duRecord.getCh()),
                                DURecordInfo.FilterType.UPLOADING,
                                duRecordList);
                        return mDataManager.uploadRecords(duRecordInfo);
                    }
                })
                .doOnNext(new Action1<DURecordResult>() {
                    @Override
                    public void call(DURecordResult duRecordResult) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(duRecordResult.getSuccessCount() == 1 ?
                                    SyncDataInfo.OperationResult.SUCCESS :
                                    SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DURecordResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadData single onCompleted---");

                        if (mSyncType == SyncType.UPLOADING_RECORD_MEDIAS) {
                            if (mHasUploadingCardsFunction) {
                                uploadCards(taskId, volume, customerId);
                            } else {
                                uploadMedias(taskId, volume, customerId);
                            }
                        } else {
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData single onError---taskId: %d, volume: %s, customerId: %s, orderNumber: %d, error: %s",
                                taskId, volume, customerId, orderNumber, e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DURecordResult duRecordResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData single onNext---taskId: %d, volume: %s, customerId: %s, orderNumber: %d",
                                taskId, volume, customerId, orderNumber));
                    }
                });
    }


    /**
     * @param taskId
     * @param volume
     * @param customerId
     * @param orderNumber
     */
    private void uploadOutData(final int taskId,
                               final String volume,
                               final String customerId,
                               final int orderNumber) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadOutData single 1---taskId: %d, volume: %s, customerId: %s, orderNumber: %d",
                taskId, volume, customerId, orderNumber));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_RECORD.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_SINGLE_RECORD,
                    taskId,
                    volume,
                    customerId,
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        DUWaiFuCBSJInfo duWaiFuCBSJInfo = new DUWaiFuCBSJInfo(DUWaiFuCBSJInfo.FilterType.ONE,
                mAccount, taskId, volume, customerId, orderNumber);
        mSubscription = mDataManager.getWaiFuCBSJ(duWaiFuCBSJInfo)
                .filter(new Func1<DUWaiFuCBSJ, Boolean>() {
                    @Override
                    public Boolean call(DUWaiFuCBSJ duWaiFuCBSJ) {
                        return (duWaiFuCBSJ.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO)
                                && (duWaiFuCBSJ.getShangchuanbz() == DURecord.SHANGCHUANBZ_WEISHANGC);
                    }
                })
                .concatMap(new Func1<DUWaiFuCBSJ, Observable<DUWaiFuCBSJResult>>() {
                    @Override
                    public Observable<DUWaiFuCBSJResult> call(DUWaiFuCBSJ duWaiFuCBSJ) {
                        LogUtil.i(TAG, "---uploadOutData single 2---");
                        List<DUWaiFuCBSJ> duWaiFuCBSJs = new ArrayList<>();
                        duWaiFuCBSJs.add(duWaiFuCBSJ);
                        DUWaiFuCBSJInfo duWaiFuCBSJInfo1 = new DUWaiFuCBSJInfo(
                                mAccount,
                                duWaiFuCBSJ.getRenwubh(),
                                TextUtil.getString(duWaiFuCBSJ.getCh()),
                                DUWaiFuCBSJInfo.FilterType.UPLOADING,
                                duWaiFuCBSJs);
                        return mDataManager.uploadWaiFuCBSJs(duWaiFuCBSJInfo1);
                    }
                })
                .doOnNext(new Action1<DUWaiFuCBSJResult>() {
                    @Override
                    public void call(DUWaiFuCBSJResult duWaiFuCBSJResult) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(duWaiFuCBSJResult.getSuccessCount() == 1 ?
                                    SyncDataInfo.OperationResult.SUCCESS :
                                    SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUWaiFuCBSJResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadOutData single onCompleted---");
                        uploadOutRecordMedias(taskId, volume, customerId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadOutData single onError---taskId: %d, volume: %s, customerId: %s, orderNumber: %d, error: %s",
                                taskId, volume, customerId, orderNumber, e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUWaiFuCBSJResult duWaiFuCBSJResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadOutData single onNext---taskId: %d, volume: %s, customerId: %s, orderNumber: %d",
                                taskId, volume, customerId, orderNumber));
                    }
                });
    }


    /**
     * @param taskId
     * @param customerId
     * @param sortIndex
     */
    private void uploadSamplingData(final int taskId,
                                    final String volume,
                                    final String customerId,
                                    final int sortIndex) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadSamplingData single 1---taskId: %d,customerId: %s, sortIndex: %d",
                taskId, customerId, sortIndex));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING,
                    taskId,
                    "",
                    customerId,
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        DUSamplingInfo duSamplingInfo = new DUSamplingInfo(DUSamplingInfo.FilterType.ONE,
                mAccount, taskId, volume, customerId, sortIndex);
        mSubscription = mDataManager.getSampling(duSamplingInfo)
                .filter(new Func1<DUSampling, Boolean>() {
                    @Override
                    public Boolean call(DUSampling duSampling) {
                        return (duSampling.getIchaobiaobz() == DUSampling.CHAOBIAOBZ_YICHAO)
                                && (duSampling.getShangchuanbz() == DUSampling.SHANGCHUANBZ_WEISHANGC);
                    }
                })
                .concatMap(new Func1<DUSampling, Observable<DUSamplingResult>>() {
                    @Override
                    public Observable<DUSamplingResult> call(DUSampling duSampling) {
                        LogUtil.i(TAG, "---uploadSamplingData single 2---");
                        List<DUSampling> duSamplingList = new ArrayList<>();
                        duSamplingList.add(duSampling);
                        DUSamplingInfo duSamplingInfo = new DUSamplingInfo(
                                mAccount,
                                duSampling.getRenwubh(),
                                DeviceUtil.getDeviceID(MainApplication.get(SyncService.this)),
                                DUSamplingInfo.FilterType.UPLOADING,
                                duSamplingList);
                        return mDataManager.uploadSamplings(duSamplingInfo);
                    }
                })
                .doOnNext(new Action1<DUSamplingResult>() {
                    @Override
                    public void call(DUSamplingResult duSamplingResult) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(duSamplingResult.getSuccessCount() == 1 ?
                                    SyncDataInfo.OperationResult.SUCCESS :
                                    SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUSamplingResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadSamplingData single onCompleted---");
                        if (mSyncType == SyncType.UPLOADING_SAMPLING_MEDIAS) {
                            uploadSamplingMedias(taskId, volume, customerId);
                        } else {
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadSamplingData single onError---taskId: %d,customerId: %s, sortIndex: %d, error: %s",
                                taskId, customerId, sortIndex, e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUSamplingResult duSamplingResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadSamplingData single onNext---taskId: %d,customerId: %s, orderNumber: %d",
                                taskId, customerId, sortIndex));
                    }
                });
    }

    /**
     *
     */
    private void uploadCards() {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadCards 1---"));

        mIsOperationFinished = false;
        final int[] currentIndex = {0};
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_CARDS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        DUTaskInfo duTaskInfo = new DUTaskInfo(mAccount);
        mSubscription = mDataManager.getTasks(duTaskInfo, true)
                .concatMap(new Func1<List<DUTask>, Observable<DUTask>>() {
                    @Override
                    public Observable<DUTask> call(List<DUTask> duTaskList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadCards 2---"));

                        if (syncDataInfoList != null) {
                            for (DUTask duTask : duTaskList) {
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.UPLOADING_VOLUME_CARDS,
                                        duTask.getRenWuBH(),
                                        duTask.getcH(),
                                        null,
                                        SyncDataInfo.OperationResult.NONE,
                                        false);
                            }
                        }

                        return Observable.from(duTaskList);
                    }
                })
                .filter(new Func1<DUTask, Boolean>() {
                    @Override
                    public Boolean call(DUTask duTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadCards 3---"));

                        currentIndex[0] = findSyncDataInfoIndex(syncDataInfoList,
                                duTask.getRenWuBH(), duTask.getcH());

                        boolean result = duTask.getTongBuBZ() == DUTask.TONGBUBZ_SYNC;
                        if (!result) {
                            setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                    SyncDataInfo.OperationResult.NONE);
                        }

                        return result;
                    }
                })
                .concatMap(new Func1<DUTask, Observable<DUCardResult>>() {
                    @Override
                    public Observable<DUCardResult> call(DUTask duTask) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadCards 4---"));

                        DUCardInfo duCardInfo = new DUCardInfo(
                                DUCardInfo.FilterType.SEARCHING_ALL,
                                mAccount,
                                duTask.getRenWuBH(),
                                duTask.getcH(),
                                null);
                        return mDataManager.getCardResults(duCardInfo);
                    }
                })
                .concatMap(new Func1<DUCardResult, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(DUCardResult duCardResult) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadCards 5---"));

                        DUCardInfo duCardInfo = new DUCardInfo(
                                DUCardInfo.FilterType.UPLOADING_ALL,
                                mAccount,
                                duCardResult.getTaskId(),
                                duCardResult.getVolume(),
                                duCardResult.getDuCardList());
                        return mDataManager.uploadCards(duCardInfo);
                    }
                })
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                aBoolean ? SyncDataInfo.OperationResult.SUCCESS : SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                SyncDataInfo.OperationResult.FAILURE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadCards onCompleted---");

                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingCardsEndInternal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards onError---%s", e.getMessage()));

                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingCardsEndInternal());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards onNext---%s", aBoolean ? "true" : "false"));
                    }
                });
    }

    /**
     * @param taskId
     * @param volume
     */
    private void uploadCards(final int taskId, final String volume, final String customerId) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadCards(taskId, volume) 1---taskId: %d, volume: %s", taskId, volume));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_CARDS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_VOLUME_CARDS,
                    taskId,
                    volume,
                    customerId,
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        DUTaskInfo duTaskInfo = new DUTaskInfo(
                mAccount,
                taskId,
                volume,
                DUTaskInfo.FilterType.ONE);
        mSubscription = mDataManager.getTask(duTaskInfo)
                .filter(new Func1<DUTask, Boolean>() {
                    @Override
                    public Boolean call(DUTask duTask) {
                        LogUtil.i(TAG, "---uploadCards(taskId, volume) 1");

                        return duTask.getTongBuBZ() == DUTask.TONGBUBZ_SYNC;
                    }
                })
                .concatMap(new Func1<DUTask, Observable<List<DUCard>>>() {
                    @Override
                    public Observable<List<DUCard>> call(DUTask duTask) {
                        LogUtil.i(TAG, "---uploadCards(taskId, volume) 2");

                        DUCardInfo duCardInfo = new DUCardInfo(volume,
                                DUCardInfo.FilterType.SEARCHING_ALL);
                        return mDataManager.getCards(duCardInfo);
                    }
                })
                .filter(new Func1<List<DUCard>, Boolean>() {
                    @Override
                    public Boolean call(List<DUCard> duCardList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards(taskId, volume) 3---record count: %d",
                                duCardList.size()));

                        return duCardList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DUCard>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(List<DUCard> duCardList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards(taskId, volume) 3---record count: %d",
                                duCardList.size()));

                        DUCardInfo duCardInfo = new DUCardInfo(
                                DUCardInfo.FilterType.UPLOADING_ALL,
                                mAccount,
                                taskId,
                                volume,
                                duCardList);
                        return mDataManager.uploadCards(duCardInfo);
                    }
                })
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(aBoolean ?
                                    SyncDataInfo.OperationResult.SUCCESS :
                                    SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadCards(taskId, volume) 4 onCompleted---");

                        if (mSyncType == SyncType.UPLOADING_VOLUME) {
                            uploadMedias(taskId, volume);
                        } else if (mSyncType == SyncType.UPLOADING_RECORD_MEDIAS) {
                            uploadMedias(taskId, volume, customerId);
                        } else {
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards(taskId, volume) 5 onError--- taskId: %d, volume: %s, error: %s",
                                taskId, volume, e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards(taskId, volume) 6 onNext--- taskId: %d, volume: %s",
                                taskId, volume));
                    }
                });
    }

    /**
     *上传延迟表卡信息
     */
    private void uploadDelayCards() {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadDelayCards 1---"));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_DELAY_CARDS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_DELAY_CARDS,
                    0,
                    "",
                    "",
                    SyncDataInfo.OperationResult.NONE,
                    false);
        }

        DUCardInfo duCardInfo = new DUCardInfo("",
                DUCardInfo.FilterType.DELAY);
        mSubscription = mDataManager.getCards(duCardInfo)
                .filter(new Func1<List<DUCard>, Boolean>() {
                    @Override
                    public Boolean call(List<DUCard> duCardList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards(taskId, volume) 3---record count: %d",
                                duCardList.size()));

                        return duCardList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DUCard>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(List<DUCard> duCardList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadCards(taskId, volume) 3---record count: %d",
                                duCardList.size()));

                        DUCardInfo duCardInfo = new DUCardInfo(
                                DUCardInfo.FilterType.UPLOADING_ALL,
                                mAccount,
                                0,
                                "",
                                duCardList);
                        return mDataManager.uploadCards(duCardInfo);
                    }
                })
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(aBoolean ?
                                    SyncDataInfo.OperationResult.SUCCESS :
                                    SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                        if (syncDataInfo != null) {
                            syncDataInfo.setOperationResult(SyncDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadCards(taskId, volume) 4 onCompleted---");

                        if (mSyncType == SyncType.UPLOADING_DOWNLOADING_DELAY_ALL) {
                            uploadDelayMedias();
                        }else {
                            postEndEvent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadDelayCards 5 onError--- error: %s", e.getMessage()));
                        postEndEvent();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadDelayCards 6 onNext---: %s", aBoolean));
                    }
                });
    }

    /**
     *
     */
    private void uploadMedias() {
        LogUtil.i(TAG, "---uploadMedias all 1---");

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }
            syncDataInfoList.clear();
        }

        DUTaskInfo duTaskInfo = new DUTaskInfo(mAccount);
        mSubscription = mDataManager.getTasks(duTaskInfo, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadMedias all onCompleted---");

                        uploadMoreMedias(true, 0, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadMedias all onError---" + e.getMessage());

                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                    }

                    @Override
                    public void onNext(List<DUTask> duTaskList) {
                        LogUtil.i(TAG, "---uploadMedias all onNext---");

                        if (syncDataInfoList != null) {
                            for (DUTask duTask : duTaskList) {
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.UPLOADING_VOLUME_MEDIAS,
                                        duTask.getRenWuBH(),
                                        duTask.getcH(),
                                        null,
                                        SyncDataInfo.OperationResult.NONE,
                                        true);
                            }
                        }

                    }
                });
    }

    private void uploadMoreMedias(boolean firstTime, final int taskId, final String volume) {
        LogUtil.i(TAG, "---uploadMoreMedias 1---");
        if (firstTime) {
            offset = 0;
            LogUtil.i(TAG, "---uploadMoreMedias 2---");
        } else {
            offset += DUMediaInfo.LIMIT;
            LogUtil.i(TAG, "---uploadMoreMedias 3---");
        }

        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_MEDIAS.getName());
        isUploadMoreMediaFinished = false;
        DUMedia duMedia = new DUMedia();
        duMedia.setAccount(mAccount);
        duMedia.setRenwubh(taskId);
        duMedia.setCh(volume);
        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.MORE_ITEMS,
                DUMediaInfo.MeterReadingType.NORMAL,
                offset,
                DUMediaInfo.LIMIT,
                duMedia);

        mSubscription = mDataManager.getMediaList(duMediaInfo)
                .filter(new Func1<List<DUMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<DUMedia> duMediaList) {
                        isUploadMoreMediaFinished = duMediaList.size() <= 0;
                        return !isUploadMoreMediaFinished;
                    }
                })
                .concatMap(new Func1<List<DUMedia>, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(List<DUMedia> duMediaList) {
                        return mDataManager.uploadMoreMedias(duMediaList, false);
                    }
                })
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null) && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            int taskId = duMedia.getRenwubh();
                            String volume = duMedia.getCh();
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess =
                                    (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);

                            int index = findSyncDataInfoIndex(syncDataInfoList, taskId, volume);
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, index);
                            if (syncDataInfo == null) {
                                return;
                            }

                            addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                    SyncSubDataInfo.OperationType.MEDIA_INFO,
                                    customerId,
                                    fileName,
                                    isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                            : SyncSubDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadMoreMedias onCompleted---");

                        if (isUploadMoreMediaFinished) {
                            mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                        } else {
                            mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMoreMediasEndInternal(
                                    taskId, volume));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadMoreMedias onError---" + e.getMessage());

                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadMoreMedias onNext---");
                    }
                });
    }

    private void uploadDelayMoreMedias() {
        LogUtil.i(TAG, "---uploadDelayMoreMedias 1---");
        offset = 0;

        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_DELAY_MEDIAS.getName());
        isUploadMoreMediaFinished = false;
        DUMedia duMedia = new DUMedia();
        duMedia.setAccount(mAccount);
        duMedia.setType(DUMedia.MEDIA_TYPE_DELAY);
        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.MORE_ITEMS_DELAY,
                DUMediaInfo.MeterReadingType.DELAYING,
                offset,
                DUMediaInfo.LIMIT,
                duMedia);

        mSubscription = mDataManager.getMediaList(duMediaInfo)
                .filter(new Func1<List<DUMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<DUMedia> duMediaList) {
                        isUploadMoreMediaFinished = duMediaList.size() <= 0;
                        return !isUploadMoreMediaFinished;
                    }
                })
                .concatMap(new Func1<List<DUMedia>, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(List<DUMedia> duMediaList) {
                        return mDataManager.uploadMoreMedias(duMediaList, false);
                    }
                })
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null) && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess =
                                    (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);

                            SyncDataInfo syncDataInfo =
                                    syncDataInfoList != null && syncDataInfoList.size() > 0
                                            ? syncDataInfoList.get(0) : null;
                            if (syncDataInfo == null) {
                                return;
                            }

                            addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                    SyncSubDataInfo.OperationType.MEDIA_INFO,
                                    customerId,
                                    fileName,
                                    isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                            : SyncSubDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadDelayMoreMedias onCompleted---");

                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingDelayMediasEndInternal(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadDelayMoreMedias onError---" + e.getMessage());

                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingDelayMediasEndInternal(false));
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadDelayMoreMedias onNext---");
                    }
                });
    }

    /**
     *
     */
    private void uploadSamplingMedias() {
        LogUtil.i(TAG, "---uploadMedias all 1---");

        mIsOperationFinished = false;
        final int[] currentIndex = {0};
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SAMPLING_VOLUME_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }
            syncDataInfoList.clear();
        }

        DUSamplingTaskInfo duSamplingTaskInfo = new DUSamplingTaskInfo(mAccount);
        mDataManager.getSamplingTasks(duSamplingTaskInfo, true)
                .concatMap(new Func1<List<DUSamplingTask>, Observable<DUSamplingTask>>() {
                    @Override
                    public Observable<DUSamplingTask> call(List<DUSamplingTask> duSamplingTaskList) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---uploadData all 2---task count: %d", duSamplingTaskList.size()));

                        if (syncDataInfoList != null) {
                            for (DUSamplingTask duSamplingTask : duSamplingTaskList) {
                                addSyncDataInfo(syncDataInfoList,
                                        SyncDataInfo.OperationType.UPLOADING_SAMPLING_VOLUME_MEDIAS,
                                        duSamplingTask.getRenWuBH(),
                                        "",//duSamplingTask.getcH()
                                        null,
                                        SyncDataInfo.OperationResult.NONE,
                                        true);
                            }
                        }

                        return Observable.from(duSamplingTaskList);
                    }
                })
                .concatMap(new Func1<DUSamplingTask, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(DUSamplingTask duSamplingTask) {
                        LogUtil.i(TAG, "---uploadMedias all 3---");

                        currentIndex[0] = findSyncDataInfoIndex(syncDataInfoList,
                                duSamplingTask.getRenWuBH(), "");
                        setSyncDataInfo(syncDataInfoList, currentIndex[0],
                                SyncDataInfo.OperationResult.NONE);

                        DUMedia duMedia = new DUMedia();
                        duMedia.setAccount(mAccount);
                        duMedia.setRenwubh(duSamplingTask.getRenWuBH());
                        duMedia.setType(DUMedia.MEDIA_TYPE_SAMPLING);

                        DUMediaInfo duMediaInfo = new DUMediaInfo(
                                DUMediaInfo.OperationType.NOT_UPLOADED,
                                DUMediaInfo.MeterReadingType.NORMAL,
                                duMedia);
                        return mDataManager.uploadSamplingMedias(duMediaInfo);
                    }
                })
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null) && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            int taskId = duMedia.getRenwubh();
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess =
                                    (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);

                            currentIndex[0] = findSyncDataInfoIndex(syncDataInfoList, taskId);
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, currentIndex[0]);
                            if (syncDataInfo == null) {
                                return;
                            }

                            addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                    SyncSubDataInfo.OperationType.MEDIA_INFO,
                                    customerId,
                                    fileName,
                                    isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                            : SyncSubDataInfo.OperationResult.FAILURE);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadMedias all onCompleted---");
                        deleteSamplingTasks();
//                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadMedias all onError---" + e.getMessage());
                        deleteSamplingTasks();
//                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadingMediasEndInternal());
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadMedias all onNext---");
                    }
                });
    }

    /**
     * @param taskId
     * @param volume
     */
    private void uploadMedias(int taskId, String volume) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadMedias---taskId: %d, volume: %s", taskId, volume));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_VOLUME_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }

            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_VOLUME_MEDIAS,
                    taskId,
                    volume,
                    null,
                    SyncDataInfo.OperationResult.NONE,
                    true);
        }

        uploadMoreMedias(true, taskId, volume);
        /*DUMedia duMedia = new DUMedia();
        duMedia.setAccount(mAccount);
        duMedia.setRenwubh(taskId);
        duMedia.setCh(volume);

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.NOT_UPLOADED,
                DUMediaInfo.MeterReadingType.NORMAL,
                duMedia);
        mSubscription = mDataManager.uploadMedias(duMediaInfo)
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null)
                                && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess = (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                            if (syncDataInfo != null) {
                                addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                        SyncSubDataInfo.OperationType.MEDIA_INFO,
                                        customerId,
                                        fileName,
                                        isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                                : SyncSubDataInfo.OperationResult.FAILURE);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadMedias onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadMedias onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadMedias onNext---");
                    }
                });*/
    }

    /**
     *
     */
    private void uploadDelayMedias() {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE, "---uploadDelayMedias---"));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_DELAY_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }

            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_DELAY_MEDIAS,
                    0,
                    "",
                    null,
                    SyncDataInfo.OperationResult.NONE,
                    true);
        }

        uploadDelayMoreMedias();
    }

    /**
     * @param taskId
     */
    private void uploadSamplingMedias(int taskId) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadMedias---taskId: %d", taskId));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SAMPLING_VOLUME_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }

            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_SAMPLING_VOLUME_MEDIAS,
                    taskId,
                    "",
                    null,
                    SyncDataInfo.OperationResult.NONE,
                    true);
        }

        DUMedia duMedia = new DUMedia();
        duMedia.setAccount(mAccount);
        duMedia.setRenwubh(taskId);

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.NOT_UPLOADED,
                DUMediaInfo.MeterReadingType.NORMAL,
                duMedia);
        mSubscription = mDataManager.uploadSamplingMedias(duMediaInfo)
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null)
                                && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess = (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                            if (syncDataInfo != null) {
                                addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                        SyncSubDataInfo.OperationType.MEDIA_INFO,
                                        customerId,
                                        fileName,
                                        isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                                : SyncSubDataInfo.OperationResult.FAILURE);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadMedias onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadMedias onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadMedias onNext---");
                    }
                });
    }


    /**
     * @param taskId
     * @param volume
     * @param customerId
     */
    private void uploadOutRecordMedias(final int taskId, String volume, String customerId) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadOutRecordMedias---taskId: %d, volume: %s, customerId: %s",
                taskId, volume, customerId));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }

            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_SINGLE_MEDIAS,
                    taskId,
                    volume,
                    customerId,
                    SyncDataInfo.OperationResult.NONE,
                    true);
        }

        DUMedia duMedia = new DUMedia(
                mAccount,
                taskId,
                volume,
                customerId);
        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.SELECT,
                DUMediaInfo.MeterReadingType.WAIFU,
                duMedia);
        mSubscription = mDataManager.getMediaList(duMediaInfo)
                .filter(new Func1<List<DUMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<DUMedia> duMediaList) {
                        return duMediaList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DUMedia>, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(List<DUMedia> duMediaList) {
                        return mDataManager.uploadMoreMedias(duMediaList, true);
                    }
                })
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null)
                                && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess = (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                            if (syncDataInfo != null) {
                                addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                        SyncSubDataInfo.OperationType.MEDIA_INFO,
                                        customerId,
                                        fileName,
                                        isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                                : SyncSubDataInfo.OperationResult.FAILURE);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadOutRecordMedias onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadOutRecordMedias onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadOutRecordMedias onNext---");
                    }
                });
    }

    /**
     * @param taskId
     * @param volume
     * @param customerId
     */
    private void uploadMedias(int taskId, String volume, String customerId) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadMedias---taskId: %d, volume: %s, customerId: %s",
                taskId, volume, customerId));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }

            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_SINGLE_MEDIAS,
                    taskId,
                    volume,
                    customerId,
                    SyncDataInfo.OperationResult.NONE,
                    true);
        }

        DUMedia duMedia = new DUMedia(
                mAccount,
                taskId,
                volume,
                customerId);
        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.SELECT,
                DUMediaInfo.MeterReadingType.NORMAL,
                duMedia);
        mSubscription = mDataManager.getMediaList(duMediaInfo)
                .filter(new Func1<List<DUMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<DUMedia> duMediaList) {
                        return duMediaList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DUMedia>, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(List<DUMedia> duMediaList) {
                        return mDataManager.uploadMoreMedias(duMediaList, false);
                    }
                })
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null)
                                && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess = (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                            if (syncDataInfo != null) {
                                addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                        SyncSubDataInfo.OperationType.MEDIA_INFO,
                                        customerId,
                                        fileName,
                                        isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                                : SyncSubDataInfo.OperationResult.FAILURE);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadMedias onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadMedias onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadMedias onNext---");
                    }
                });
    }

    /**
     * @param taskId
     * @param customerId
     */
    private void uploadSamplingMedias(int taskId, String volume, String customerId) {
        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                "---uploadSamplingMedias---taskId: %d, customerId: %s",
                taskId, customerId));

        mIsOperationFinished = false;
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING_MEDIAS.getName());
        if (syncDataInfoList != null) {
            for (SyncDataInfo syncDataInfo : syncDataInfoList) {
                List<SyncSubDataInfo> syncSubDataInfoList =
                        syncDataInfo.getSyncSubDataInfoList();
                if (syncSubDataInfoList != null) {
                    syncSubDataInfoList.clear();
                }
            }

            syncDataInfoList.clear();
            addSyncDataInfo(syncDataInfoList,
                    SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING_MEDIAS,
                    taskId,
                    volume,
                    customerId,
                    SyncDataInfo.OperationResult.NONE,
                    true);
        }

        DUMedia duMedia = new DUMedia(
                mAccount,
                taskId,
                volume,
                customerId);
        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.SELECT,
                DUMediaInfo.MeterReadingType.NORMAL,
                duMedia);
        mSubscription = mDataManager.uploadSamplingMedias(duMediaInfo)
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null)
                                && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            String customerId = duMedia.getCid();
                            String fileName = duMedia.getWenjianmc();
                            boolean isSuccess = (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);
                            SyncDataInfo syncDataInfo = findSyncDataInfo(syncDataInfoList, 0);
                            if (syncDataInfo != null) {
                                addSyncSubDataInfo(syncDataInfo.getSyncSubDataInfoList(),
                                        SyncSubDataInfo.OperationType.MEDIA_INFO,
                                        customerId,
                                        fileName,
                                        isSuccess ? SyncSubDataInfo.OperationResult.SUCCESS
                                                : SyncSubDataInfo.OperationResult.FAILURE);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadSamplingMedias onCompleted---");
                        postEndEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadSamplingMedias onError---" + e.getMessage());
                        postEndEvent();
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                        LogUtil.i(TAG, "---uploadSamplingMedias onNext---");
                    }
                });
    }



    public void deleteSamplingTasks() {
        LogUtil.i(TAG, "---deleteTasks 1---");
        mIsOperationFinished = false;

        final int[] totalCount = new int[1];
        mSubscription = mDataManager.getRemovedSamplingTasks(mAccount)
                .concatMap(new Func1<List<DUSamplingTask>, Observable<DUSamplingTask>>() {
                    @Override
                    public Observable<DUSamplingTask> call(List<DUSamplingTask> duSamplingTaskList) {
                        LogUtil.i(TAG, "---deleteTasks 2---");
                        totalCount[0] = duSamplingTaskList.size();
                        return Observable.from(duSamplingTaskList);
                    }
                })
                .concatMap(new Func1<DUSamplingTask, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(DUSamplingTask duSamplingTask) {
                        totalCount[0]--;
                        DUSamplingTaskInfo duSamplingTaskInfo = new DUSamplingTaskInfo(
                                mAccount,
                                duSamplingTask.getRenWuBH(),
                                "", //duSamplingTask.getcH(),
                                DUSamplingTaskInfo.FilterType.DELETE);
                        return mDataManager.deleteSamplingTask(duSamplingTaskInfo);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---deleteTasks onCompleted---");
                        if (totalCount[0] <= 0) {
                            LogUtil.i(TAG, "---uploadData 6 onCompleted---post event");
                            downloadSampling();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---deleteTasks onError---" + e.getMessage());
                        downloadSampling();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---deleteTasks onNext---" + aBoolean);
                    }
                });
    }

    /**
     *
     */
    private void downloadSampling() {
        LogUtil.i(TAG, "---download 1---");
        mIsOperationFinished = false;
        final List<DUSamplingTask> duSamplingTaskList = new ArrayList<>();
        final List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.DOWNLOADING_SAMPLING_TASKS.getName());
        if (syncDataInfoList != null) {
            syncDataInfoList.clear();
        }

        DUTaskIdInfo duTaskIdInfo = new DUTaskIdInfo(mAccount);
        mSubscription = mDataManager.getSamplingTaskIds(duTaskIdInfo, false)
                .concatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---download 2---taskId count: %d", strings.size()));

                        return Observable.from(strings);
                    }
                })
                .concatMap(new Func1<String, Observable<List<DUSamplingTask>>>() {
                    @Override
                    public Observable<List<DUSamplingTask>> call(String s) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---download 3---taskId: %s", s));

                        DUSamplingTaskInfo duTaskInfo = new DUSamplingTaskInfo(
                                mAccount,
                                TextUtil.getInt(s),
                                DeviceUtil.getDeviceID(MainApplication.get(SyncService.this)));
                        return mDataManager.getSamplingTasks(duTaskInfo, false);
                    }
                })
                .doOnNext(new Action1<List<DUSamplingTask>>() {
                    @Override
                    public void call(List<DUSamplingTask> duSamplingTasks) {
                        for (DUSamplingTask duSamplingTask : duSamplingTasks) {
                            LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                    "---download onNext---taskId: %d, volume: %s",
                                    duSamplingTask.getRenWuBH(), ""));
                            duSamplingTaskList.add(duSamplingTask);
//                          addSyncDataInfo(syncDataInfoList,
//                                    SyncDataInfo.OperationType.DOWNLOADING_SAMPLING_TASKS,
//                                    duSamplingTask.getRenWuBH(),
//                                    duSamplingTask.getcH(),
//                                    null,
//                                    SyncDataInfo.OperationResult.SUCCESS,
//                                    false);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUSamplingTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---download onCompleted---");
                        downloadSamplingMore(duSamplingTaskList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format(ConstDataUtil.LOCALE,
                                "---download onError---%s", e.getMessage()));

                        downloadSamplingMore(duSamplingTaskList);
                    }

                    @Override
                    public void onNext(List<DUSamplingTask> duSamplingTasks) {
                        LogUtil.i(TAG, "---download onNext---");
                    }
                });
    }

    private void isQianFei(final String customerId) {
        LogUtil.i(TAG, "---isQianFei 1---");
        mSubscription = mDataManager.isQianFei(customerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---isQianFei onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---isQianFei onError: %s---", e.getMessage()));
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, String.format("---isQianFei onNext: %s---", aBoolean ? "true" : "false"));
                        mEventPosterHelper.postEventSafely(new UIBusEvent.QianFeiInfo(aBoolean, customerId));
                    }
                });
    }

    private void uploadCardCoordinate(final int taskId,
                                      final String volume,
                                      final String customerId,
                                      final double longitude,
                                      final double latitude) {
        LogUtil.i(TAG, String.format("---uploadCardCoordinate 1---%s", customerId));
        DUCardCoordinateInfo duCardCoordinateInfo = new DUCardCoordinateInfo(
                taskId,
                volume,
                customerId,
                longitude,
                latitude);
        mSubscription = mDataManager.uploadCardCoordinate(duCardCoordinateInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUCardCoordinateResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadCardCoordinate onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---uploadCardCoordinate onError: %s---",
                                e.getMessage()));
                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadCardCoordinateResult(
                                taskId,
                                volume,
                                customerId,
                                false,
                                longitude,
                                latitude));
                    }

                    @Override
                    public void onNext(DUCardCoordinateResult duCardCoordinateResult) {
                        LogUtil.i(TAG, "---uploadCardCoordinate onNext---");
                        mEventPosterHelper.postEventSafely(new UIBusEvent.UploadCardCoordinateResult(
                                taskId,
                                volume,
                                customerId,
                                duCardCoordinateResult.isSuccess(),
                                longitude,
                                latitude));
                    }
                });
    }

    private void addSyncDataInfo(List<SyncDataInfo> syncDataInfoList,
                                 SyncDataInfo.OperationType operationType,
                                 int taskId,
                                 String volume,
                                 String customerId,
                                 SyncDataInfo.OperationResult operationResult,
                                 boolean needSyncSubDataInfo) {
        if (syncDataInfoList != null) {
            SyncDataInfo syncDataInfo = new SyncDataInfo(
                    operationType,
                    taskId,
                    volume,
                    customerId,
                    operationResult);
            if (needSyncSubDataInfo) {
                syncDataInfo.setSyncSubDataInfoList(new ArrayList<SyncSubDataInfo>());
            }
            syncDataInfoList.add(syncDataInfo);
        }
    }

    private void addSyncDataInfo(List<SyncDataInfo> syncDataInfoList,
                                 SyncDataInfo.OperationType operationType,
                                 int successCount,
                                 SyncDataInfo.OperationResult operationResult,
                                 boolean needSyncSubDataInfo) {
        if (syncDataInfoList != null) {
            SyncDataInfo syncDataInfo = new SyncDataInfo(
                    operationType,
                    successCount,
                    operationResult);
            if (needSyncSubDataInfo) {
                syncDataInfo.setSyncSubDataInfoList(new ArrayList<SyncSubDataInfo>());
            }
            syncDataInfoList.add(syncDataInfo);
        }
    }

    private void addSyncSubDataInfo(List<SyncSubDataInfo> syncSubDataInfoList,
                                    SyncSubDataInfo.OperationType operationType,
                                    String customerId,
                                    String fileName,
                                    SyncSubDataInfo.OperationResult operationResult) {
        if ((syncSubDataInfoList != null)
                && (!TextUtil.isNullOrEmpty(customerId))
                && (!TextUtil.isNullOrEmpty(fileName))) {
            SyncSubDataInfo syncSubDataInfo = new SyncSubDataInfo(
                    operationType,
                    customerId,
                    fileName,
                    operationResult);
            syncSubDataInfoList.add(syncSubDataInfo);
        }
    }

    private int findSyncDataInfoIndex(List<SyncDataInfo> syncDataInfoList,
                                      int taskId,
                                      String volume) {
        if ((syncDataInfoList == null)
                || (syncDataInfoList.size() <= 0)
                || TextUtil.isNullOrEmpty(volume)) {
            return -1;
        }

        int index = 0;
        for (SyncDataInfo syncDataInfo : syncDataInfoList) {
            if ((syncDataInfo.getTaskId() == taskId) && (syncDataInfo.getVolume().equals(volume))) {
                return index;
            }
            index++;
        }

        return -1;
    }

    private int findSyncDataInfoIndex(List<SyncDataInfo> syncDataInfoList,
                                      int taskId) {
        if ((syncDataInfoList == null)
                || (syncDataInfoList.size() <= 0)) {
            return -1;
        }

        int index = 0;
        for (SyncDataInfo syncDataInfo : syncDataInfoList) {
            if (syncDataInfo.getTaskId() == taskId) {
                return index;
            }
            index++;
        }

        return -1;
    }

    private SyncDataInfo findSyncDataInfo(List<SyncDataInfo> syncDataInfoList, int index) {
        if ((syncDataInfoList == null)
                || (index < 0)
                || (index >= syncDataInfoList.size())) {
            return null;
        }

        return syncDataInfoList.get(index);
    }

    private void setSyncDataInfo(List<SyncDataInfo> syncDataInfoList,
                                 int index,
                                 SyncDataInfo.OperationResult operationResult) {
        if ((syncDataInfoList != null) && (index >= 0) && (index < syncDataInfoList.size())) {
            SyncDataInfo syncDataInfo = syncDataInfoList.get(index);
            if (syncDataInfo != null) {
                syncDataInfo.setOperationResult(operationResult);
            }
        }
    }

    private void setSyncDataInfo(List<SyncDataInfo> syncDataInfoList,
                                 int index,
                                 SyncDataInfo.OperationResult operationResult,
                                 int successCount,
                                 int failureCount) {
        if ((syncDataInfoList != null) && (index >= 0) && (index < syncDataInfoList.size())) {
            SyncDataInfo syncDataInfo = syncDataInfoList.get(index);
            if (syncDataInfo != null) {
                syncDataInfo.setOperationResult(operationResult);
                syncDataInfo.setSuccessCount(successCount);
                syncDataInfo.setFailureCount(failureCount);
            }
        }
    }

    private void postEndEvent() {
        mEventPosterHelper.postEventSafely(new UIBusEvent.SyncDataEnd(mSyncType, syncDataInfoMap));
    }

    @Subscribe
    public void onUploadingDataEndInternal(UIBusEvent.UploadingDataEndInternal uploadingDataEndInternal) {
        LogUtil.i(TAG, "---onUploadingDataEnd 1---");
        mIsOperationFinished = true;
        if (mSyncType == SyncType.UPLOADING_DOWNLOADING_ALL) {
            LogUtil.i(TAG, "---onUploadingDataEnd 2---");
            if (mHasUploadingCardsFunction) {
                uploadCards();
            } else {
                uploadMedias();
            }
        }
    }

    @Subscribe
    public void onUploadingCardsEndInternal(UIBusEvent.UploadingCardsEndInternal uploadingCardsEndInternal) {
        LogUtil.i(TAG, "---onUploadingCardsEnd 1---");
        mIsOperationFinished = true;
        if (mSyncType == SyncType.UPLOADING_DOWNLOADING_ALL) {
            LogUtil.i(TAG, "---onUploadingCardsEnd 2---");
            uploadMedias();
        }
    }

    @Subscribe
    public void onUploadingMediasEndInternal(UIBusEvent.UploadingMediasEndInternal uploadingMediasEndInternal) {
        LogUtil.i(TAG, "---onUploadingMediasEnd 1---");
        mIsOperationFinished = true;
        if (mSyncType == SyncType.UPLOADING_DOWNLOADING_ALL
                || mSyncType == SyncType.UPLOADING_VOLUME) {
            LogUtil.i(TAG, "---onUploadingMediasEnd 2---");
//            deleteUploadPhoto();
//            deleteTasks();
            mDataManager.setUploadingImageRepeatedly(false);
            postEndEvent();
        } else {
            mDataManager.setUploadingImageRepeatedly(false);
            postEndEvent();
        }
    }

    @Subscribe
    public void onUploadingDelayMediasEndInternal(UIBusEvent.UploadingDelayMediasEndInternal internal) {
        LogUtil.i(TAG, "---onUploadingDelayMediasEndInternal 1---");
        mIsOperationFinished = true;
        mDataManager.setUploadingImageRepeatedly(false);
        downloadDelay();
    }

    @Subscribe
    public void onUploadingMoreMediasEndInternal(UIBusEvent.UploadingMoreMediasEndInternal uploadingMoreMediasEndInternal) {
        LogUtil.i(TAG, "---uploadingMoreMediasEndInternal 1---");
        if (mSyncType == SyncType.UPLOADING_WAIFUCBSJS_MEDIAS
                || mSyncType == SyncType.SYNC_WAIFUCBSJS) {
            uploadMoreWaifuMedias(false);
        } else {
            int taskId = uploadingMoreMediasEndInternal.getTaskId();
            String volume = uploadingMoreMediasEndInternal.getVolume();
            uploadMoreMedias(false, taskId, volume);
        }

    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncDataEnd---");
        mIsOperationFinished = true;
    }
}
