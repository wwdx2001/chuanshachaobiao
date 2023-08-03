package com.sh3h.datautil.data;

import android.content.Context;

import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.dataprovider.greendaoEntity.DuoMeiTXX;
import com.sh3h.datautil.data.entity.Coordinate;
import com.sh3h.datautil.data.entity.DUBillPreview;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardCoordinateInfo;
import com.sh3h.datautil.data.entity.DUCardCoordinateResult;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUCardResult;
import com.sh3h.datautil.data.entity.DUChaoBiaoJL;
import com.sh3h.datautil.data.entity.DUChaoBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUCiYuXXInfo;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.datautil.data.entity.DUDelayCardInfo;
import com.sh3h.datautil.data.entity.DUDelayChaoBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUDelayHuanBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUDelayJiaoFeiXXInfo;
import com.sh3h.datautil.data.entity.DUDelayQianFeiInfo;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.datautil.data.entity.DUDelayRecordInfo;
import com.sh3h.datautil.data.entity.DUDelayRecordResult;
import com.sh3h.datautil.data.entity.DUDeviceInfo;
import com.sh3h.datautil.data.entity.DUDeviceResult;
import com.sh3h.datautil.data.entity.DUDownloadResult;
import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.datautil.data.entity.DUHuanBiaoJL;
import com.sh3h.datautil.data.entity.DUHuanBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUJiaoFeiXX;
import com.sh3h.datautil.data.entity.DUJiaoFeiXXInfo;
import com.sh3h.datautil.data.entity.DUKeepAliveInfo;
import com.sh3h.datautil.data.entity.DUKeepAliveResult;
import com.sh3h.datautil.data.entity.DULgld;
import com.sh3h.datautil.data.entity.DULgldInfo;
import com.sh3h.datautil.data.entity.DULoginInfo;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUMediaInfo;
import com.sh3h.datautil.data.entity.DUMediaResult;
import com.sh3h.datautil.data.entity.DUQianFeiXX;
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
import com.sh3h.datautil.data.entity.DUUpdateInfo;
import com.sh3h.datautil.data.entity.DUUpdateResult;
import com.sh3h.datautil.data.entity.DUUploadingFile;
import com.sh3h.datautil.data.entity.DUUploadingFileResult;
import com.sh3h.datautil.data.entity.DUUserResult;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJInfo;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJResult;
import com.sh3h.datautil.data.entity.TraceInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.UserConfig;
import com.sh3h.datautil.data.local.db.DbHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.data.remote.Downloader;
import com.sh3h.datautil.data.remote.HttpHelper;
import com.sh3h.datautil.data.remote.Parser;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.RenWuXXEntity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Singleton
public class DataManager {
    private static final String TAG = "DataManager";

    private final Context mContext;
    private final HttpHelper mHttpHelper;
    //    private final DatabaseHelper mDatabaseHelper;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;
    private final EventPosterHelper mEventPoster;
    private final Downloader mDownloader;
    private final Parser mParser;
    private boolean mIsUploadingImageRepeatedly;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       HttpHelper httpHelper,
                       PreferencesHelper preferencesHelper,
                       DbHelper dbHelper,
                       ConfigHelper configHelper,
                       Downloader downloader,
                       EventPosterHelper eventPosterHelper) {
        mContext = context;
        mHttpHelper = httpHelper;
        mPreferencesHelper = preferencesHelper;
        mDbHelper = dbHelper;
        mConfigHelper = configHelper;
        mDownloader = downloader;
        mEventPoster = eventPosterHelper;
        mParser = new Parser(dbHelper, configHelper, eventPosterHelper);
        mIsUploadingImageRepeatedly = false;
    }

    public void destroy() {
        mDbHelper.destroy();
    }

    /**
     * authorize
     *
     * @param duDeviceInfo device information
     * @return observable
     */
    public Observable<DUDeviceResult> authorize(DUDeviceInfo duDeviceInfo) {
        return mHttpHelper.authorize(duDeviceInfo);
    }

    public Observable<Coordinate> transformCoordinate(double x, double y) {
        return mHttpHelper.transformCoordinate(x, y);
    }

    /**
     * login
     *
     * @param duLoginInfo
     * @return
     */
    public Observable<Boolean> login(final DULoginInfo duLoginInfo) {
        return mHttpHelper.login(duLoginInfo)
                .map(new Func1<DUUserResult, Boolean>() {
                    @Override
                    public Boolean call(DUUserResult duUserResult) {
                        UserSession userSession = mPreferencesHelper.getUserSession();
                        userSession.setAccount(duLoginInfo.getAccount());
                        userSession.set_password(duLoginInfo.getPassword());
                        userSession.setUserId(duUserResult.getUserId());
                        userSession.setUserName(duUserResult.getUserName());
                        return userSession.save();
                    }
                });
    }

    /**
     * update app or data
     *
     * @param duUpdateInfo
     * @return
     */
    public Observable<DUFileResult> updateVersion(DUUpdateInfo duUpdateInfo) {
        return mHttpHelper.updateVersion(duUpdateInfo)
                .concatMap(new Func1<DUUpdateResult, Observable<DUFileResult>>() {
                    @Override
                    public Observable<DUFileResult> call(DUUpdateResult duUpdateResult) {
                        Observable<DUFileResult> observable =
                                mDownloader.downloadFile(duUpdateResult.getDuUpdateInfo(), duUpdateResult.getItemList());
                        if (observable == null) {
                            return Observable.create(new Observable.OnSubscribe<DUFileResult>() {
                                @Override
                                public void call(Subscriber<? super DUFileResult> subscriber) {
                                    if (subscriber.isUnsubscribed()) {
                                        return;
                                    }

                                    subscriber.onError(new Throwable("downloadFile is failure"));
                                }
                            });
                        }

                        return observable;
                    }
                })
                .doOnNext(new Action1<DUFileResult>() {
                    @Override
                    public void call(DUFileResult duFileResult) {
                        String path = duFileResult.getPath();
                        String name = duFileResult.getName();
                        int percent = duFileResult.getPercent();
                        if (TextUtil.isNullOrEmpty(path)
                                || TextUtil.isNullOrEmpty(name)) {
                            LogUtil.i(TAG, "---updateVersion doOnNext---null");
                            return;
                        }

                        LogUtil.i(TAG, String.format("---updateVersion doOnNext--name: %s, percent: %d",
                                name, percent));
                        if (percent >= 100) {
                            if (name.contains("data_")) {
                                mParser.parseData(duFileResult);
                            } else if (name.contains("app_")) {
                                mParser.unzipFile(duFileResult);
                            }
                        }
                    }
                });
    }

    /**
     * @param duTaskIdInfo
     * @param isLocal
     * @return
     */
    public Observable<List<String>> getTaskIds(DUTaskIdInfo duTaskIdInfo, boolean isLocal) {
        if (isLocal) {
            return null;
        } else {
            return mHttpHelper.getTaskIds(duTaskIdInfo)
                    .concatMap(new Func1<RenWuXXEntity, Observable<? extends List<String>>>() {
                        @Override
                        public Observable<? extends List<String>> call(RenWuXXEntity renWuXXEntity) {
                            return saveChaoBiaoJBH(renWuXXEntity);
                        }
                    });
        }
    }

    private Observable<List<String>> saveChaoBiaoJBH(RenWuXXEntity renWuXXEntity){
        List<String> taskIdList = new ArrayList<>();
        assert renWuXXEntity != null;
        String taskIds = renWuXXEntity.get_renwus();
        String[] ids = taskIds.split(",");
        for (String id : ids) {
            if (id.equals("") || id.equals(",")) {
                continue;
            }
            taskIdList.add(id);
        }

        File file = mConfigHelper.getAccountConfigFilePath();
        String chaoBiaoJBH = renWuXXEntity.get_chaobiaojbh();
        if (!TextUtil.isNullOrEmpty(chaoBiaoJBH)){
            mParser.writeAccount(file, chaoBiaoJBH);
        }
        return Observable.just(taskIdList);
    }

    /**
     * @param duTaskIdInfo
     * @param isLocal
     * @return
     */
    public Observable<List<String>> getSamplingTaskIds(DUTaskIdInfo duTaskIdInfo, boolean isLocal) {
        if (isLocal) {
            return null;
        } else {
            return mHttpHelper.getSamplingTaskIds(duTaskIdInfo);
        }
    }

    public Observable<Boolean> isSamplingTaskContainingFullData(DUSamplingTaskInfo duSamplingTaskInfo) {
        return mDbHelper.isSamplingTaskContainingFullData(duSamplingTaskInfo);
    }

    /**
     * @param duSampling
     * @return
     */
    public Observable<DUSamplingResult> updateSampling(final DUSampling duSampling, boolean isUpdatingTask) {
        List<DUSampling> duSamplingList = new ArrayList<>();
        duSamplingList.add(duSampling);
        if (!isUpdatingTask) {
            return mDbHelper.updateSamplings(duSamplingList, false);
        }

        return mDbHelper.updateSamplings(duSamplingList, false)
                .filter(new Func1<DUSamplingResult, Boolean>() {
                    @Override
                    public Boolean call(DUSamplingResult duSamplingResult) {
                        return duSamplingResult.getSuccessCount() == 1;
                    }
                })
                .concatMap(new Func1<DUSamplingResult, Observable<? extends DUSamplingTask>>() {
                    @Override
                    public Observable<? extends DUSamplingTask> call(DUSamplingResult duSamplingResult) {
                        DUSamplingTaskInfo duSamplingTaskInfo = new DUSamplingTaskInfo(
                                mPreferencesHelper.getUserSession().getAccount(),
                                duSamplingResult.getTaskId(),
                                duSamplingResult.getVolume(),
                                DUSamplingTaskInfo.FilterType.ONE);
                        return mDbHelper.getSamplingTask(duSamplingTaskInfo);
                    }
                })
                .concatMap(new Func1<DUSamplingTask, Observable<? extends DUSamplingResult>>() {
                    @Override
                    public Observable<? extends DUSamplingResult> call(final DUSamplingTask duSamplingTask) {
                        return Observable.create(new Observable.OnSubscribe<DUSamplingResult>() {
                            @Override
                            public void call(Subscriber<? super DUSamplingResult> subscriber) {
                                List<DUSamplingTask> duSamplingTaskList = new ArrayList<>();
                                duSamplingTaskList.add(duSamplingTask);
                                int finishedCount = duSamplingTask.getYiChaoShu() + 1;
                                if (finishedCount > duSamplingTask.getZongShu()) {
                                    finishedCount = duSamplingTask.getZongShu();
                                }
                                duSamplingTask.setYiChaoShu(finishedCount);
                                if (mDbHelper.saveSamplingTasks(duSamplingTaskList)) {
                                    DUSamplingResult duSamplingResult = new DUSamplingResult(
                                            DUSamplingResult.FilterType.UPDATING,
                                            duSamplingTask.getRenWuBH(),
                                            duSampling.getCh(),
                                            1,
                                            0);
                                    duSamplingResult.setCid(duSampling.getCid());
                                    subscriber.onNext(duSamplingResult);
                                } else {
                                    subscriber.onError(new Throwable("failure to updating the task"));
                                }
                            }
                        });
                    }
                });
    }

//    public Observable<DUDownloadResult> syncWaiFuCBSJS(final DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
//        return mDbHelper.getWaiFuCBSJS(duWaiFuCBSJInfo)
//                .concatMap(new Func1<List<DUWaiFuCBSJ>, Observable<List<DUWaiFuCBSJ>>>() {
//                    @Override
//                    public Observable<List<DUWaiFuCBSJ>> call(List<DUWaiFuCBSJ> duWaiFuCBSJList) {
//                        duWaiFuCBSJInfo.setDuWaiFuCBSJList(duWaiFuCBSJList);
//                        return mHttpHelper.syncWaiFuCBSJS(duWaiFuCBSJInfo);
//                    }
//                })
//                .concatMap(new Func1<List<DUWaiFuCBSJ>, Observable<? extends List<String>>>() {
//                    @Override
//                    public Observable<? extends List<String>> call(final List<DUWaiFuCBSJ> duWaiFuCBSJs) {
//                        return Observable.create(new Observable.OnSubscribe<List<String>>() {
//                            @Override
//                            public void call(Subscriber<? super List<String>> subscriber) {
//                                if (subscriber.isUnsubscribed()) {
//                                    return;
//                                }
//
//                                List<String> customerIdList = mDbHelper.saveWaiFuCBSJS(duWaiFuCBSJs);
//                                if (customerIdList != null) {
//                                    subscriber.onNext(customerIdList);
//                                    subscriber.onCompleted();
//                                } else {
//                                    subscriber.onError(new Throwable("saveWaiFuCBSJS is error"));
//                                }
//                            }
//                        });
//                    }
//                })
//                .concatMap(new Func1<List<String>, Observable<? extends String>>() {
//                    @Override
//                    public Observable<? extends String> call(List<String> strings) {
//                        return Observable.from(strings);
//                    }
//                })
//                .concatMap(new Func1<String, Observable<? extends DUDownloadResult>>() {
//                    @Override
//                    public Observable<? extends DUDownloadResult> call(String s) {
//                        DUCardInfo duCardInfo = new DUCardInfo(
//                                DUCardInfo.FilterType.WAIFU,
//                                "waifu",
//                                s);
//                        return downloadCards(duCardInfo);
//                    }
//                });
//    }

    /**
     * 下载任务
     * @param duTaskInfo
     * @param isLocal
     * @return
     */
    public Observable<List<DUTask>> getTasks(DUTaskInfo duTaskInfo, boolean isLocal) {
        if (isLocal) {
            return mDbHelper.getTasks(duTaskInfo);
        } else {
            return mHttpHelper.getTasks(duTaskInfo)
                    .doOnNext(new Action1<List<DUTask>>() {
                        @Override
                        public void call(List<DUTask> duTaskList) {
                            mDbHelper.saveTasks(duTaskList);
                        }
                    });
        }
    }

    /**
     * @param duSamplingTaskInfo
     * @param isLocal
     * @return
     */
    public Observable<List<DUSamplingTask>> getSamplingTasks(DUSamplingTaskInfo duSamplingTaskInfo, boolean isLocal) {
        if (isLocal) {
            return mDbHelper.getSamplingTasks(duSamplingTaskInfo);
        } else {
            return mHttpHelper.getSamplingTasks(duSamplingTaskInfo)
                    .doOnNext(new Action1<List<DUSamplingTask>>() {
                        @Override
                        public void call(List<DUSamplingTask> duSamplingTasks) {
                            mDbHelper.saveSamplingTasks(duSamplingTasks);
                        }
                    });
        }
    }

    /**
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadRushPays(DURushPayTaskInfo duRushPayTaskInfo) {
        return mHttpHelper.getRushPayTasks(duRushPayTaskInfo)
                .concatMap(new Func1<List<DURushPayTask>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DURushPayTask> duRushPayTaskList) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }
//                                String account = mPreferencesHelper.getUserSession().getAccount();
                                if (mDbHelper.saveRushPayTasks(duRushPayTaskList)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.RUSHPAY,
                                            duRushPayTaskList,
                                            duRushPayTaskList.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveRushPayTasks is error"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }


    /**
     * @param duRushPayTaskInfo
     * @param isLocal
     * @return
     */
    public Observable<List<DURushPayTask>> getRushPayTasks(DURushPayTaskInfo duRushPayTaskInfo, boolean isLocal) {
        if (isLocal) {
            return mDbHelper.getRushPayTasks(duRushPayTaskInfo);
        } else {
            return mHttpHelper.getRushPayTasks(duRushPayTaskInfo)
                    .doOnNext(new Action1<List<DURushPayTask>>() {
                        @Override
                        public void call(List<DURushPayTask> duRushPayTasks) {
                            mDbHelper.saveRushPayTasks(duRushPayTasks);
                        }
                    });
        }
    }

    /**
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<Boolean> deleteRushPayTask(DURushPayTaskInfo duRushPayTaskInfo) {
        return mDbHelper.deleteRushPayTask(duRushPayTaskInfo)
                .concatMap(new Func1<List<DuoMeiTXX>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(final List<DuoMeiTXX> duoMeiTXXes) {
                        return Observable.create(new Observable.OnSubscribe<Boolean>() {
                            @Override
                            public void call(Subscriber<? super Boolean> subscriber) {
                                for (DuoMeiTXX duoMeiTXX : duoMeiTXXes) {
                                    String path = duoMeiTXX.getS_WENJIANLJ();
                                    if (TextUtil.isNullOrEmpty(path)) {
                                        continue;
                                    }
                                    File file = new File(path);
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                }
                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * @param duSamplingInfo
     * @return
     */
    public Observable<List<DUSampling>> getSamplings(DUSamplingInfo duSamplingInfo) {
        return mDbHelper.getSamplings(duSamplingInfo);
    }

    /**
     * @param duSamplingInfo
     * @return
     */
    public Observable<DUSampling> getSampling(DUSamplingInfo duSamplingInfo) {
        return mDbHelper.getSampling(duSamplingInfo);
    }

    public Observable<List<DUWaiFuCBSJ>> getWaiFuCBSJS(DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return mDbHelper.getWaiFuCBSJS(duWaiFuCBSJInfo);
    }

    /**
     * @param duWaiFuCBSJ
     * @return
     */
    public Observable<DUWaiFuCBSJResult> updateWaiFuCBSJ(final DUWaiFuCBSJ duWaiFuCBSJ) {
        List<DUWaiFuCBSJ> duWaiFuCBSJList = new ArrayList<>();
        duWaiFuCBSJList.add(duWaiFuCBSJ);
        return mDbHelper.updateWaiFuCBSJs(duWaiFuCBSJList, false);
//
//        return mDbHelper.updateWaiFuCBSJs(duWaiFuCBSJList, false)
//                .filter(new Func1<DUWaiFuCBSJResult, Boolean>() {
//                    @Override
//                    public Boolean call(DUWaiFuCBSJResult duWaiFuCBSJResult) {
//                        return duWaiFuCBSJResult.getSuccessCount() == 1;
//                    }
//                })
//                .concatMap(new Func1<DUWaiFuCBSJResult, Observable<? extends DUTask>>() {
//                    @Override
//                    public Observable<? extends DUTask> call(DUWaiFuCBSJResult duRecordResult) {
//                        DUTaskInfo duTaskInfo = new DUTaskInfo(
//                                mPreferencesHelper.getUserSession().getAccount(),
//                                duRecordResult.getTaskId(),
//                                duRecordResult.getVolume(),
//                                DUTaskInfo.FilterType.ONE);
//                        return mDbHelper.getTask(duTaskInfo);
//                    }
//                })
//                .concatMap(new Func1<DUTask, Observable<? extends DUWaiFuCBSJResult>>() {
//                    @Override
//                    public Observable<? extends DUWaiFuCBSJResult> call(final DUTask duTask) {
//                        return Observable.create(new Observable.OnSubscribe<DUWaiFuCBSJResult>() {
//                            @Override
//                            public void call(Subscriber<? super DUWaiFuCBSJResult> subscriber) {
//                                List<DUTask> duTaskList = new ArrayList<DUTask>();
//                                duTaskList.add(duTask);
//                                int finishedCount = duTask.getYiChaoShu() + 1;
//                                if (finishedCount > duTask.getZongShu()) {
//                                    finishedCount = duTask.getZongShu();
//                                }
//                                duTask.setYiChaoShu(finishedCount);
//                                if (mDbHelper.saveTasks(duTaskList)) {
//                                    subscriber.onNext(new DUWaiFuCBSJResult(
//                                            DUWaiFuCBSJResult.FilterType.UPDATING,
//                                            duTask.getRenWuBH(),
//                                            duWaiFuCBSJ.getCh(),
//                                            1,
//                                            0));
//                                } else {
//                                    subscriber.onError(new Throwable("failure to updating the task"));
//                                }
//                            }
//                        });
//                    }
//                });
    }

    /**
     * @param duWaiFuCBSJInfo
     * @return
     */
    public Observable<DUWaiFuCBSJ> getWaiFuCBSJ(DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return mDbHelper.getWaiFuCBSJ(duWaiFuCBSJInfo);
    }


    /**
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<DURushPayTask> getDuRushPayTask(DURushPayTaskInfo duRushPayTaskInfo) {
        return mDbHelper.getRushPayTask(duRushPayTaskInfo);
    }

    /**
     * @param account
     * @return
     */
    public Observable<List<DUTask>> getRemovedTasks(final String account) {
        DUTaskIdInfo duTaskIdInfo = new DUTaskIdInfo(account);
        return mHttpHelper.getTaskIds(duTaskIdInfo)
                .concatMap(new Func1<RenWuXXEntity, Observable<? extends List<String>>>() {
                    @Override
                    public Observable<? extends List<String>> call(RenWuXXEntity renWuXXEntity) {
                        return saveChaoBiaoJBH(renWuXXEntity);
                    }
                })
                .concatMap(new Func1<List<String>, Observable<? extends List<DUTask>>>() {
                    @Override
                    public Observable<? extends List<DUTask>> call(List<String> taskIdList) {
                        return mDbHelper.getRemovedTasks(account, taskIdList);
                    }
                });
    }

    /**
     * @param account
     * @return
     */
    public Observable<List<DUSamplingTask>> getRemovedSamplingTasks(final String account) {
        DUTaskIdInfo duTaskIdInfo = new DUTaskIdInfo(account);
        return mHttpHelper.getSamplingTaskIds(duTaskIdInfo)
                .concatMap(new Func1<List<String>, Observable<? extends List<DUSamplingTask>>>() {
                    @Override
                    public Observable<? extends List<DUSamplingTask>> call(List<String> taskIdList) {
                        return mDbHelper.getRemovedSamplingTasks(account, taskIdList);
                    }
                });
    }

    /**
     * @param account
     * @return
     */
    public Observable<List<DURushPayTask>> getRemovedRushPayTasks(final String account) {
        DURushPayTaskInfo duRushPayTaskInfo = new DURushPayTaskInfo();
        duRushPayTaskInfo.setAccount(account);
        return mHttpHelper.getRushPayTaskIds(duRushPayTaskInfo)
                .concatMap(new Func1<List<String>, Observable<? extends List<DURushPayTask>>>() {
                    @Override
                    public Observable<? extends List<DURushPayTask>> call(List<String> taskIdList) {
                        return mDbHelper.getRemovedRushPayTasks(account, taskIdList);
                    }
                });
    }


    public Observable<DUTask> getTask(DUTaskInfo duTaskInfo) {
        return mDbHelper.getTask(duTaskInfo);
    }

    /**
     * @param duTaskInfo
     * @return
     */
    public Observable<Boolean> updateTask(DUTaskInfo duTaskInfo) {
        return mDbHelper.updateTask(duTaskInfo);
    }

    /**
     * @param duSamplingTaskInfo
     * @return
     */
    public Observable<Boolean> updateSamplingTask(DUSamplingTaskInfo duSamplingTaskInfo) {
        return mDbHelper.updateSamplingTask(duSamplingTaskInfo);
    }

    /**
     * @param duTaskInfo
     * @return
     */
    public Observable<Boolean> deleteTask(DUTaskInfo duTaskInfo) {
        return mDbHelper.deleteTask(duTaskInfo)
                .concatMap(new Func1<List<DuoMeiTXX>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(final List<DuoMeiTXX> duoMeiTXXes) {
                        return Observable.create(new Observable.OnSubscribe<Boolean>() {
                            @Override
                            public void call(Subscriber<? super Boolean> subscriber) {
                                for (DuoMeiTXX duoMeiTXX : duoMeiTXXes) {
                                    String path = duoMeiTXX.getS_WENJIANLJ();
                                    if (TextUtil.isNullOrEmpty(path)) {
                                        continue;
                                    }

                                    File file = new File(path);
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                }

                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * @return
     */
    public Observable<Boolean> deleteUploadMedias() {
        return mDbHelper.deleteUploadMedias()
                .concatMap(new Func1<List<DuoMeiTXX>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(final List<DuoMeiTXX> duoMeiTXXes) {
                        return Observable.create(new Observable.OnSubscribe<Boolean>() {
                            @Override
                            public void call(Subscriber<? super Boolean> subscriber) {
                                for (DuoMeiTXX duoMeiTXX : duoMeiTXXes) {
                                    String path = duoMeiTXX.getS_WENJIANLJ();
                                    if (TextUtil.isNullOrEmpty(path)) {
                                        continue;
                                    }

                                    File file = new File(path);
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                }

                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * @param duSamplingTaskInfo
     * @return
     */
    public Observable<Boolean> deleteSamplingTask(DUSamplingTaskInfo duSamplingTaskInfo) {
        return mDbHelper.deleteSamplingTask(duSamplingTaskInfo)
                .concatMap(new Func1<List<DuoMeiTXX>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(final List<DuoMeiTXX> duoMeiTXXes) {
                        return Observable.create(new Observable.OnSubscribe<Boolean>() {
                            @Override
                            public void call(Subscriber<? super Boolean> subscriber) {
                                for (DuoMeiTXX duoMeiTXX : duoMeiTXXes) {
                                    String path = duoMeiTXX.getS_WENJIANLJ();
                                    if (TextUtil.isNullOrEmpty(path)) {
                                        continue;
                                    }

                                    File file = new File(path);
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                }

                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * @param duRecordInfo
     * @param isLocal
     * @return
     */
//    public Observable<List<DURecord>> getRecords(DURecordInfo duRecordInfo, boolean isLocal) {
//        if (isLocal) {
//            return mDbHelper.getRecords(duRecordInfo);
//        } else {
//            return mHttpHelper.getRecords(duRecordInfo)
//                    .doOnNext(new Action1<List<DURecord>>() {
//                        @Override
//                        public void call(List<DURecord> duRecordList) {
//                            mDbHelper.saveRecords(duRecordList);
//                        }
//                    });
//        }
//    }

    /**
     * @param duRecordInfo
     * @return
     */
    public Observable<List<DURecord>> getRecords(DURecordInfo duRecordInfo) {
        duRecordInfo.setWaterHighN(mConfigHelper.getWaterHighNumberPro());
        return mDbHelper.getRecords(duRecordInfo);
    }

    /**
     * @param duDelayRecordInfo
     * @return
     */
    public Observable<List<DUDelayRecord>> getRecords(DUDelayRecordInfo duDelayRecordInfo) {
        duDelayRecordInfo.setWaterHighN(mConfigHelper.getWaterHighNumberPro());
        return mDbHelper.getRecords(duDelayRecordInfo);
    }

    public Observable<List<DULgld>> getLglds(DULgldInfo info) {
        return mDbHelper.getLglds(info);
    }

    public Observable<Integer> getChaoJianS(String account, int taskId, String volume, String chaoJianZTS) {
        return mDbHelper.getChaoJianS(account, taskId, volume, chaoJianZTS);
    }

    /**
     * @param duRecordList
     * @return
     */
    public Observable<DURecordResult> updateRecords(List<DURecord> duRecordList) {
        return mDbHelper.updateRecords(duRecordList, false);
    }

    /**
     * @param duRecord
     * @return
     */
    public Observable<DURecordResult> updateRecord(final DURecord duRecord, boolean isUpdatingTask) {
        List<DURecord> duRecordList = new ArrayList<>();
        duRecordList.add(duRecord);
        if (!isUpdatingTask) {
            return mDbHelper.updateRecords(duRecordList, false);
        }

        return mDbHelper.updateRecords(duRecordList, false)
                .filter(new Func1<DURecordResult, Boolean>() {
                    @Override
                    public Boolean call(DURecordResult duRecordResult) {
                        return duRecordResult.getSuccessCount() == 1;
                    }
                })
                .concatMap(new Func1<DURecordResult, Observable<? extends DUTask>>() {
                    @Override
                    public Observable<? extends DUTask> call(DURecordResult duRecordResult) {
                        DUTaskInfo duTaskInfo = new DUTaskInfo(
                                mPreferencesHelper.getUserSession().getAccount(),
                                duRecordResult.getTaskId(),
                                duRecordResult.getVolume(),
                                DUTaskInfo.FilterType.ONE);
                        return mDbHelper.getTask(duTaskInfo);
                    }
                })
                .concatMap(new Func1<DUTask, Observable<? extends DURecordResult>>() {
                    @Override
                    public Observable<? extends DURecordResult> call(final DUTask duTask) {
                        return Observable.create(new Observable.OnSubscribe<DURecordResult>() {
                            @Override
                            public void call(Subscriber<? super DURecordResult> subscriber) {
                                List<DUTask> duTaskList = new ArrayList<DUTask>();
                                duTaskList.add(duTask);
                                int finishedCount = duTask.getYiChaoShu() + 1;
                                if (finishedCount > duTask.getZongShu()) {
                                    finishedCount = duTask.getZongShu();
                                }
                                duTask.setYiChaoShu(finishedCount);
                                if (mDbHelper.saveTasks(duTaskList)) {
                                    subscriber.onNext(new DURecordResult(
                                            DURecordResult.FilterType.UPDATING,
                                            duTask.getRenWuBH(),
                                            duRecord.getCh(),
                                            1,
                                            0));
                                } else {
                                    subscriber.onError(new Throwable("failure to updating the task"));
                                }
                            }
                        });
                    }
                });
    }

    public Observable<DUDelayRecordResult> updateDelayRecord(final DUDelayRecord duRecord) {
        List<DUDelayRecord> duRecordList = new ArrayList<>();
        duRecordList.add(duRecord);
        return mDbHelper.updateDelayRecords(duRecordList);
    }

    /**
     * @param duRecordInfo
     * @return
     */
    public Observable<DURecord> getRecord(DURecordInfo duRecordInfo) {
        return mDbHelper.getRecord(duRecordInfo);
    }

    public Observable<DUDelayRecord> getDelayRecord(DUDelayRecordInfo duRecordInfo) {
        return mDbHelper.getDelayRecord(duRecordInfo);
    }

    public Observable<List<com.sh3h.dataprovider.entity.JianHaoMX>> getJianHaoMXByJH(final String S_JH){
        return mDbHelper.getJianHaoMXByJH(S_JH);
    }

    /**
     * @param duRecordInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadRecords(DURecordInfo duRecordInfo) {
        final int taskId = duRecordInfo.getTaskId();
        final String volume = duRecordInfo.getVolume();
        return mHttpHelper.getRecords(duRecordInfo)
                .concatMap(new Func1<List<DURecord>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DURecord> duRecordList) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                String account = mPreferencesHelper.getUserSession().getAccount();

                                if (mDbHelper.saveRecords(account, taskId, volume, duRecordList)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.RECORD,
                                            volume,
                                            duRecordList.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveRecords is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * @return 1
     */
    public Observable<DUDownloadResult> getDelayRecords() {
        DUDelayRecordInfo info = new DUDelayRecordInfo();
        info.setAccount(mPreferencesHelper.getUserSession().getAccount());
        info.setDeviceId("");
        return mHttpHelper.getDelayRecords(info)
                .concatMap(new Func1<List<DUDelayRecord>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUDelayRecord> duRecordList) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveDelayRecords(duRecordList)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.RECORD,
                                            "",
                                            duRecordList.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveRecords is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * @return 1
     */
    public Observable<DUDownloadResult> getDelayQianFeiXXs() {
        return mHttpHelper.getDelayQianFeiXXs(
                new DUDelayQianFeiInfo(mPreferencesHelper.getUserSession().getAccount(), ""))
                .concatMap(new Func1<List<DUQianFeiXX>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUQianFeiXX> duQianFeiXXes) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveQianFeiXXs(duQianFeiXXes)) { // modify: 2017-10-9, mDbHelper.deleteQianFeiXXs(volume) &&
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.QIANFEI,
                                            "",
                                            duQianFeiXXes.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("getDelayQianFeiXXs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * @return
     */
    public Observable<DUDownloadResult> getDelayChaoBiaoJL() {
        return mHttpHelper.getDelayChaoBiaoJL(new DUDelayChaoBiaoJLInfo(mPreferencesHelper.getUserSession().getAccount(), ""))
                .concatMap(new Func1<List<DUChaoBiaoJL>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUChaoBiaoJL> duChaoBiaoJLs) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveChaoBiaoJLs(duChaoBiaoJLs)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.CHAOBIAOXX,
                                            "",
                                            duChaoBiaoJLs.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveChaoBiaoJLs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Observable<DUDownloadResult> getDelayJiaoFeiXX() {
        return mHttpHelper.getDelayJiaoFeiXX(
                new DUDelayJiaoFeiXXInfo(mPreferencesHelper.getUserSession().getAccount(), ""))
                .concatMap(new Func1<List<DUJiaoFeiXX>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUJiaoFeiXX> duJiaoFeiXXes) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveJiaoFeiXXs(duJiaoFeiXXes)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.JIAOFEIXX,
                                            "",
                                            duJiaoFeiXXes.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveJiaoFeiXXs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Observable<DUDownloadResult> getDelayHuanBiaoJL() {
        return mHttpHelper.getDelayHuanBiaoJL(
                new DUDelayHuanBiaoJLInfo(mPreferencesHelper.getUserSession().getAccount(), ""))
                .concatMap(new Func1<List<DUHuanBiaoJL>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUHuanBiaoJL> duHuanBiaoJLs) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveHuanBiaoXXs(duHuanBiaoJLs)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.HUANBIAOXX,
                                            "",
                                            duHuanBiaoJLs.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveHuanBiaoXXs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * @param duSamplingInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadSamplingRecords(DUSamplingInfo duSamplingInfo) {
        final int taskId = duSamplingInfo.getTaskId();
        return mHttpHelper.getSamplingRecords(duSamplingInfo)
                .concatMap(new Func1<List<DUSampling>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUSampling> duSamplings) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                String account = mPreferencesHelper.getUserSession().getAccount();
                                if (mDbHelper.saveSamplings(account, taskId, duSamplings)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.RECORD,
                                            taskId,
                                            duSamplings.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveSamplings is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * @param duWaiFuCBSJInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadWaiFuSJs(DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return mHttpHelper.getWaiFuCBSJs(duWaiFuCBSJInfo)
                .concatMap(new Func1<List<DUWaiFuCBSJ>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUWaiFuCBSJ> duWaiFuCBSJs) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }
                                String account = mPreferencesHelper.getUserSession().getAccount();
                                if (mDbHelper.saveWaiFuSJs(account, duWaiFuCBSJs)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.RECORD,
                                            "waifu",
                                            duWaiFuCBSJs.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveWaiFuSJs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }


    /**
     * @param duRecordInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadTemporaryRecordsAndCards(final DURecordInfo duRecordInfo) {
        return mHttpHelper.getTemporaryRecords(duRecordInfo)
                .filter(new Func1<List<DURecord>, Boolean>() {
                    @Override
                    public Boolean call(List<DURecord> duRecordList) {
                        return duRecordList.size() > 0;
                    }
                })
                .concatMap(new Func1<List<DURecord>, Observable<? extends List<String>>>() {
                    @Override
                    public Observable<? extends List<String>> call(List<DURecord> duRecordList) {
                        return mDbHelper.saveTemporaryRecords(duRecordInfo.getAccount(), duRecordList);
                    }
                })
                .concatMap(new Func1<List<String>, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .concatMap(new Func1<String, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(String s) {
                        return downloadCards(new DUCardInfo(s, DUCardInfo.FilterType.TEMPORARY));
                    }
                });
    }

    /**
     * @param duRecordInfo
     * @return
     */
    public Observable<DURecordResult> uploadRecords(DURecordInfo duRecordInfo) {
        return mHttpHelper.uploadRecords(duRecordInfo)
                .concatMap(new Func1<List<DURecord>, Observable<? extends DURecordResult>>() {
                    @Override
                    public Observable<? extends DURecordResult> call(List<DURecord> duRecordList) {
                        return mDbHelper.updateRecords(duRecordList, true);
                    }
                });
    }


    /**
     * @param duRecordInfo
     * @return
     */
    public Observable<DUDelayRecordResult> uploadRecords(DUDelayRecordInfo duRecordInfo) {
        return mHttpHelper.uploadRecords(duRecordInfo)
                .concatMap(new Func1<List<DUDelayRecord>, Observable<? extends DUDelayRecordResult>>() {
                    @Override
                    public Observable<? extends DUDelayRecordResult> call(List<DUDelayRecord> duRecordList) {
                        return mDbHelper.updateDelayRecords(duRecordList);
                    }
                });
    }

    /**
     * @param duSamplingInfo
     * @return
     */
    public Observable<DUSamplingResult> uploadSamplings(DUSamplingInfo duSamplingInfo) {
        return mHttpHelper.uploadSamplings(duSamplingInfo)
                .concatMap(new Func1<List<DUSampling>, Observable<? extends DUSamplingResult>>() {
                    @Override
                    public Observable<? extends DUSamplingResult> call(List<DUSampling> duSamplingList) {
                        return mDbHelper.updateSamplings(duSamplingList, true);
                    }
                });
    }

    /**
     * @param duRushPayTaskList
     * @return
     */
    public Observable<DURushPayTaskResult> updateDuRushPayTasks(List<DURushPayTask> duRushPayTaskList) {
        return mDbHelper.updateRushPayTasks(duRushPayTaskList);
    }

    public Observable<DURushPayTaskResult> uploadRushPayTasks(DURushPayTaskInfo duRushPayTaskInfo) {
        return mHttpHelper.uploadRushPayTasks(duRushPayTaskInfo)
                .concatMap(new Func1<List<DURushPayTask>, Observable<? extends DURushPayTaskResult>>() {
                    @Override
                    public Observable<? extends DURushPayTaskResult> call(List<DURushPayTask> duSamplingList) {
                        return mDbHelper.updateRushPayTasks(duSamplingList, true);
                    }
                });
    }

    public Observable<DUWaiFuCBSJResult> uploadWaiFuCBSJs(DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return mHttpHelper.uploadWaiFuCBSJs(duWaiFuCBSJInfo)
                .concatMap(new Func1<List<DUWaiFuCBSJ>, Observable<? extends DUWaiFuCBSJResult>>() {
                    @Override
                    public Observable<? extends DUWaiFuCBSJResult> call(List<DUWaiFuCBSJ> duWaiFuCBSJs) {
                        return mDbHelper.updateWaiFuCBSJs(duWaiFuCBSJs, true);
                    }
                });
    }

    /**
     * @param duCardInfo
     * @param isLocal
     * @return
     */
//    public Observable<List<DUCard>> getCards(DUCardInfo duCardInfo, boolean isLocal) {
//        if (isLocal) {
//            return mDbHelper.getCards(duCardInfo);
//        } else {
//            return mHttpHelper.getCards(duCardInfo)
//                    .doOnNext(new Action1<List<DUCard>>() {
//                        @Override
//                        public void call(List<DUCard> cardList) {
//                            mDbHelper.saveCards(cardList);
//                        }
//                    });
//        }
//    }

    /**
     * @param duCardInfo
     * @return
     */
    public Observable<List<DUCard>> getCards(DUCardInfo duCardInfo) {
        return mDbHelper.getCards(duCardInfo);
    }

    /**
     * @param duCardInfo
     * @return
     */
    public Observable<List<DUCard>> getSamplingCards(DUCardInfo duCardInfo) {
        return mDbHelper.getSamplingCards(duCardInfo);
    }

    /**
     * @param duCardInfo
     * @return
     */
    public Observable<DUCardResult> getCardResults(DUCardInfo duCardInfo) {
        return mDbHelper.getCardResults(duCardInfo);
    }

    /**
     * @param duCardInfo
     * @return
     */
    public Observable<DUCard> getCard(DUCardInfo duCardInfo) {
        return mDbHelper.getCard(duCardInfo);
    }

    /**
     * @param duCardInfo
     * @return
     */
    public Observable<DUCard> getSamplingCard(DUCardInfo duCardInfo) {
        return mDbHelper.getCard(duCardInfo);
    }

    /**
     * @param duCardList
     * @return
     */
    public Observable<DUCardResult> updateCards(List<DUCard> duCardList) {
        return mDbHelper.updateCards(duCardList);
    }

    /**
     * @param duCard
     * @return
     */
    public Observable<Boolean> updateOneCard(DUCard duCard) {
        return mDbHelper.updateOneCard(duCard);
    }

    /**
     * @param duCardInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadCards(final DUCardInfo duCardInfo) {
        return mHttpHelper.getCards(duCardInfo)
                .concatMap(new Func1<List<DUCard>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUCard> duCardList) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;

                                }

                                boolean isTemporaryCards =
                                        duCardInfo.getFilterType() == DUCardInfo.FilterType.TEMPORARY;
                                if (mDbHelper.saveCards(duCardList, isTemporaryCards)) {
                                    if (isTemporaryCards) {
                                        subscriber.onNext(new DUDownloadResult(
                                                DUDownloadResult.FilterType.TEMPORARY,
                                                duCardInfo.getVolume(),
                                                duCardList.size()));
                                    } else {
                                        subscriber.onNext(new DUDownloadResult(
                                                DUDownloadResult.FilterType.CARD,
                                                duCardInfo.getVolume(),
                                                duCardList.size()));
                                    }
                                } else {
                                    subscriber.onError(new Throwable("saveCards is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<DUDownloadResult> downloadDelayCards() {
        DUDelayCardInfo info = new DUDelayCardInfo();
        info.setAccount(mPreferencesHelper.getUserSession().getAccount());
        info.setDeviceId("");
        return mHttpHelper.getDelayCards(info)
                .concatMap(new Func1<List<DUCard>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUCard> duCardList) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;

                                }

                                if (mDbHelper.saveCards(duCardList, false)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.CARD,
                                            "",
                                            duCardList.size()));
                                } else {
                                    subscriber.onError(new Throwable("saveCards is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * @param duCardInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadWaiFuCards(final DUCardInfo duCardInfo) {
        return mHttpHelper.getWaiFuCards(duCardInfo)
                .concatMap(new Func1<List<DUCard>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUCard> duCardList) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;

                                }

                                if (mDbHelper.saveWaiFuCards(duCardList, false)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.CARD,
                                            duCardInfo.getVolume(),
                                            duCardList.size()));

                                } else {
                                    subscriber.onError(new Throwable("saveCards is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }


    /**
     * @param duCardInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadSamplingCards(final DUCardInfo duCardInfo) {
        return mHttpHelper.getSamplingCards(duCardInfo)
                .concatMap(new Func1<List<DUCard>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUCard> duCardList) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;

                                }

                                boolean isTemporaryCards =
                                        duCardInfo.getFilterType() == DUCardInfo.FilterType.TEMPORARY;
                                if (mDbHelper.saveSamplingCards(duCardList, isTemporaryCards)) {
                                    if (isTemporaryCards) {
                                        subscriber.onNext(new DUDownloadResult(
                                                DUDownloadResult.FilterType.TEMPORARY,
                                                duCardInfo.getTaskId(),
                                                duCardList.size()));
                                    } else {
                                        subscriber.onNext(new DUDownloadResult(
                                                DUDownloadResult.FilterType.CARD,
                                                duCardInfo.getTaskId(),
                                                duCardList.size()));
                                    }
                                } else {
                                    subscriber.onError(new Throwable("saveSamplingCards is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }


    public Observable<Boolean> uploadCards(final DUCardInfo duCardInfo) {
        return mHttpHelper.uploadCards(duCardInfo)
                .filter(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        return aBoolean;
                    }
                })
                .concatMap(new Func1<Boolean, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(Boolean aBoolean) {
                        DUTaskInfo duTaskInfo = new DUTaskInfo(
                                DUTaskInfo.FilterType.UPDATE_SYNC_FLAG,
                                duCardInfo.getAccount(),
                                duCardInfo.getTaskId(),
                                duCardInfo.getVolume(),
                                false);
                        return mDbHelper.updateTask(duTaskInfo);
                    }
                });
    }

    /**
     * 获取抄表记录信息
     *
     * @param duChaoBiaoJLInfo
     * @param isLocal
     * @return
     */
    public Observable<List<DUChaoBiaoJL>> getChaoBiaoJLs(DUChaoBiaoJLInfo duChaoBiaoJLInfo, boolean isLocal) {
        if (isLocal) {
            return mDbHelper.getChaoBiaoJLs(duChaoBiaoJLInfo);
        } else {
            return mHttpHelper.getChaoBiaoJLs(duChaoBiaoJLInfo);
//                    .doOnNext(new Action1<List<DUChaoBiaoJL>>() {
//                        @Override
//                        public void call(List<DUChaoBiaoJL> duChaoBiaoJLList) {
//                            mDbHelper.saveChaoBiaoJLs(duChaoBiaoJLList);
//                        }
//                    });
        }
    }

    /**
     * @param duChaoBiaoJLInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadChaoBiaoJLs(DUChaoBiaoJLInfo duChaoBiaoJLInfo) {
        final String volume = duChaoBiaoJLInfo.getVolume();
        return mHttpHelper.getChaoBiaoJLs(duChaoBiaoJLInfo)
                .concatMap(new Func1<List<DUChaoBiaoJL>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUChaoBiaoJL> duChaoBiaoJLs) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveChaoBiaoJLs(duChaoBiaoJLs)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.PRERECORD,
                                            volume,
                                            duChaoBiaoJLs.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveChaoBiaoJLs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取缴费信息
     *
     * @param duJiaoFeiXXInfo
     * @param isLocal
     * @return
     */
    public Observable<List<DUJiaoFeiXX>> getJiaoFeiXXs(DUJiaoFeiXXInfo duJiaoFeiXXInfo, boolean isLocal) {
        if (isLocal) {
            return mDbHelper.getJiaoFeiXXs(duJiaoFeiXXInfo);
        } else {
            return mHttpHelper.getJiaoFeiXXs(duJiaoFeiXXInfo);
//                    .doOnNext(new Action1<List<DUJiaoFeiXX>>() {
//                        @Override
//                        public void call(List<DUJiaoFeiXX> duJiaoFeiXXList) {
//                            mDbHelper.saveJiaoFeiXXs(duJiaoFeiXXList);
//                        }
//                    });
        }
    }

    /**
     * @param duJiaoFeiXXInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadJiaoFeiXXs(DUJiaoFeiXXInfo duJiaoFeiXXInfo) {
        final String volume = duJiaoFeiXXInfo.getVolume();
        return mHttpHelper.getJiaoFeiXXs(duJiaoFeiXXInfo)
                .concatMap(new Func1<List<DUJiaoFeiXX>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUJiaoFeiXX> duJiaoFeiXXes) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveJiaoFeiXXs(duJiaoFeiXXes)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.PAYMENT,
                                            volume,
                                            duJiaoFeiXXes.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveJiaoFeiXXs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取欠费信息
     *
     * @param duQianFeiXXInfo
     * @param isLocal
     * @return
     */
    public Observable<List<DUQianFeiXX>> getQianFeiXXs(DUQianFeiXXInfo duQianFeiXXInfo, boolean isLocal) {
        if (isLocal) {
            return mDbHelper.getQianFeiXXs(duQianFeiXXInfo);
        } else {
            return mHttpHelper.getQianFeiXXs(duQianFeiXXInfo);
//                    .doOnNext(new Action1<List<DUQianFeiXX>>() {
//                        @Override
//                        public void call(List<DUQianFeiXX> duQianFeiXXList) {
//                            mDbHelper.saveQianFeiXXs(duQianFeiXXList);
//                        }
//                    });
        }
    }

    /**
     * @param duQianFeiXXInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadQianFeiXXs(DUQianFeiXXInfo duQianFeiXXInfo) {
        final String volume = duQianFeiXXInfo.getVolume();
        return mHttpHelper.getQianFeiXXs(duQianFeiXXInfo)
                .concatMap(new Func1<List<DUQianFeiXX>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUQianFeiXX> duQianFeiXXes) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveQianFeiXXs(duQianFeiXXes)) { // modify: 2017-10-9, mDbHelper.deleteQianFeiXXs(volume) &&
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.ARREARAGE,
                                            volume,
                                            duQianFeiXXes.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveQianFeiXXs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取换表信息
     *
     * @param duHuanBiaoJLInfo
     * @return
     */
    public Observable<List<DUHuanBiaoJL>> getHuanBiaoXXs(DUHuanBiaoJLInfo duHuanBiaoJLInfo, boolean isLocal) {
        if (isLocal) {
            return mDbHelper.getHuanBiaoXXs(duHuanBiaoJLInfo);
        } else {
            return mHttpHelper.getHuanBiaoXXs(duHuanBiaoJLInfo);
//                    .doOnNext(new Action1<List<DUHuanBiaoJL>>() {
//                        @Override
//                        public void call(List<DUHuanBiaoJL> duHuanBiaoJLList) {
//                            mDbHelper.saveHuanBiaoXXs(duHuanBiaoJLList);
//                        }
//                    });
        }
    }

    /**
     * @param duHuanBiaoJLInfo
     * @return
     */
    public Observable<DUDownloadResult> downloadHuanBiaoXXs(DUHuanBiaoJLInfo duHuanBiaoJLInfo) {
        final String volume = duHuanBiaoJLInfo.getVolume();
        return mHttpHelper.getHuanBiaoXXs(duHuanBiaoJLInfo)
                .concatMap(new Func1<List<DUHuanBiaoJL>, Observable<? extends DUDownloadResult>>() {
                    @Override
                    public Observable<? extends DUDownloadResult> call(final List<DUHuanBiaoJL> duHuanBiaoJLs) {
                        return Observable.create(new Observable.OnSubscribe<DUDownloadResult>() {
                            @Override
                            public void call(Subscriber<? super DUDownloadResult> subscriber) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }

                                if (mDbHelper.saveHuanBiaoXXs(duHuanBiaoJLs)) {
                                    subscriber.onNext(new DUDownloadResult(
                                            DUDownloadResult.FilterType.REPLACEMENT,
                                            volume,
                                            duHuanBiaoJLs.size()
                                    ));
                                } else {
                                    subscriber.onError(new Throwable("saveHuanBiaoXXs is error"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * @return
     */
    public Observable<List<DUChaoBiaoZT>> getChaoBiaoZTList() {
        return mDbHelper.getChaoBiaoZTList();
    }


    /**
     * @return
     */
    public Observable<List<DUChaoBiaoZTFL>> getChaoBiaoZTFLList() {
        return mDbHelper.getChaoBiaoZTFLList();
    }

    /**
     * @param duCiYuXXInfo
     * @return
     */
    public Observable<List<DUCiYuXX>> getCiYuXXList(DUCiYuXXInfo duCiYuXXInfo) {
        return mDbHelper.getCiYuXX(duCiYuXXInfo);
    }

    /**
     * @param duMediaInfo
     * @return
     */
    public Observable<List<DUMedia>> getMediaList(DUMediaInfo duMediaInfo) {
        return mDbHelper.getMediaList(duMediaInfo);
    }

    /**
     * @param duMediaInfo
     * @return
     */
    public Observable<List<DUMedia>> getSamplingMediaList(DUMediaInfo duMediaInfo) {
        return mDbHelper.getSamplingMediaList(duMediaInfo);
    }

    /**
     * @param duMediaInfo
     */
    public Observable<DUMediaResult> uploadMedias(DUMediaInfo duMediaInfo) {
        return mDbHelper.getMediaList(duMediaInfo)
                .concatMap(new Func1<List<DUMedia>, Observable<DUMedia>>() {
                    @Override
                    public Observable<DUMedia> call(List<DUMedia> duMediaList) {
                        return Observable.from(duMediaList);
                    }
                })
                .filter(new Func1<DUMedia, Boolean>() {
                    @Override
                    public Boolean call(DUMedia duMedia) {
                        return duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_WEISHANGC;
                    }
                })
                .concatMap(new Func1<DUMedia, Observable<DUMedia>>() {
                    @Override
                    public Observable<DUMedia> call(DUMedia duMedia) {
                        return mHttpHelper.uploadMedia(duMedia);
                    }
                })
                .concatMap(new Func1<DUMedia, Observable<DUMedia>>() {
                    @Override
                    public Observable<DUMedia> call(DUMedia duMedia) {
                        return mHttpHelper.uploadMediaRelation(duMedia, false);
                    }
                })
                .concatMap(new Func1<DUMedia, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(DUMedia duMedia) {
                        DUMediaInfo duMediaInfo = new DUMediaInfo(
                                DUMediaInfo.OperationType.UPDATE,
                                DUMediaInfo.MeterReadingType.NORMAL,
                                duMedia);
                        return mDbHelper.updateMedia(duMediaInfo);
                    }
                });
    }

    public Observable<DUMediaResult> uploadMoreMedias(List<DUMedia> duMediaList, final boolean isOutSide) {
        if (duMediaList == null){
            return Observable.error(new Exception("duMediaList params is error"));
        }

        if ( mIsUploadingImageRepeatedly) {
            for (DUMedia duMedia : duMediaList) {
                duMedia.setFileHash(null);
                duMedia.setUrl(null);
                duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
            }
        }

        return Observable.from(duMediaList)
                .filter(new Func1<DUMedia, Boolean>() {
                    @Override
                    public Boolean call(DUMedia duMedia) {
                        return mIsUploadingImageRepeatedly
                                || duMedia.getShangchuanbz() != DUMedia.SHANGCHUANBZ_YISHANGC;
                    }
                })
                .concatMap(new Func1<DUMedia, Observable<DUMedia>>() {
                    @Override
                    public Observable<DUMedia> call(DUMedia duMedia) {
                        return mHttpHelper.uploadMedia(duMedia);
                    }
                })
//                .filter(new Func1<DUMedia, Boolean>() {
//                    @Override
//                    public Boolean call(DUMedia duMedia) {
//                        return mIsUploadingImageRepeatedly
//                                || duMedia.getShangchuanbz() != DUMedia.SHANGCHUANBZ_YISHANGC;
//                    }
//                })
//                .concatMap(new Func1<DUMedia, Observable<? extends DUMedia>>() {
//                    @Override
//                    public Observable<? extends DUMedia> call(DUMedia duMedia) {
//                        return mHttpHelper.uploadMediaRelation(duMedia, isOutSide);
//                    }
//                })
                .concatMap(new Func1<DUMedia, Observable<? extends DUMediaResult>>() {
                    @Override
                    public Observable<? extends DUMediaResult> call(DUMedia duMedia) {
                        DUMediaInfo duMediaInfo = new DUMediaInfo(
                                DUMediaInfo.OperationType.UPDATE,
                                DUMediaInfo.MeterReadingType.NORMAL,
                                duMedia);
                        return mDbHelper.updateMedia(duMediaInfo);
                    }
                });
    }

    /**
     * @param duMediaInfo
     */
    public Observable<DUMediaResult> uploadSamplingMedias(DUMediaInfo duMediaInfo) {
        return mDbHelper.getSamplingMediaList(duMediaInfo)
                .concatMap(new Func1<List<DUMedia>, Observable<DUMedia>>() {
                    @Override
                    public Observable<DUMedia> call(List<DUMedia> duMediaList) {
                        return Observable.from(duMediaList);
                    }
                })
                .filter(new Func1<DUMedia, Boolean>() {
                    @Override
                    public Boolean call(DUMedia duMedia) {
                        return duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_WEISHANGC;
                    }
                })
                .concatMap(new Func1<DUMedia, Observable<DUMedia>>() {
                    @Override
                    public Observable<DUMedia> call(DUMedia duMedia) {
                        return mHttpHelper.uploadSamplingMedia(duMedia);
                    }
                })
                .concatMap(new Func1<DUMedia, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(DUMedia duMedia) {
                        DUMediaInfo duMediaInfo = new DUMediaInfo(
                                DUMediaInfo.OperationType.UPDATE,
                                DUMediaInfo.MeterReadingType.NORMAL,
                                duMedia);
                        return mDbHelper.updateMedia(duMediaInfo);
                    }
                });
    }

    /**
     * @param duMediaInfo
     * @return
     */
    public Observable<Boolean> saveNewImage(DUMediaInfo duMediaInfo) {
        return mDbHelper.saveImage(duMediaInfo);
    }

    /**
     * @param duMediaInfo
     * @return
     */
    public Observable<Boolean> deleteImage(DUMediaInfo duMediaInfo) {
        return mDbHelper.saveImage(duMediaInfo);
    }

    /**
     * @param dUCombinedInfo
     * @param isLocal
     * @return
     */
    public Observable<List<DUCard>> getCombinedResult(final DUCombinedInfo dUCombinedInfo,
                                                      boolean isLocal) {
        UserSession userSession = mPreferencesHelper.getUserSession();
        dUCombinedInfo.setAccount(userSession.getAccount());
        if (isLocal) {
            return mDbHelper.getCombinedResult(dUCombinedInfo);
        } else {
            return mHttpHelper.getCombinedResult(dUCombinedInfo)
                    .doOnNext(new Action1<List<DUCard>>() {
                        @Override
                        public void call(List<DUCard> cardList) {
//                            mDbHelper.saveCards(cardList);
                        }
                    });
        }
    }

    /**
     * @param account
     * @param duCardList
     * @param duRecordList
     * @param duTask
     * @param cardId
     * @param orderNumber
     * @param isCardId
     * @param isFront
     * @return
     */
    public Observable<Boolean> adjustCardAndRecords(String account,
                                                    List<DUCard> duCardList,
                                                    List<DURecord> duRecordList,
                                                    DUTask duTask,
                                                    String cardId,
                                                    int orderNumber,
                                                    boolean isCardId,
                                                    boolean isFront) {
        return mDbHelper.adjustCardAndRecords(account, duCardList, duRecordList, duTask,
                cardId, orderNumber, isCardId, isFront);
    }

    /**
     * @param duTask
     * @return
     */
    public Observable<Boolean> updateCbrwCbsjBkxx(DUTask duTask,
                                                  String cidRemove,
                                                  List<DURecord> duRecordList,
                                                  List<DUCard> duCardList,
                                                  List<DURecord> removedDuRecordList,
                                                  List<DUCard> removedDUCardList) {
        return mDbHelper.updateCbrwCbsjBkxx(duTask, cidRemove, duRecordList, duCardList,
                removedDuRecordList, removedDUCardList);
    }

    /**
     * @param customerId
     * @return
     */
    public Observable<Boolean> isQianFei(String customerId) {
        return mHttpHelper.isQianFei(customerId);
    }

    public Observable<Boolean> isTaskContainingFullData(DUTaskInfo duTaskInfo) {
        return mDbHelper.isTaskContainingFullData(duTaskInfo);
    }

    public Observable<List<TraceInfo>> getChaoBiaoTrance(int taskId, String ch){
        return mDbHelper.getChaoBiaoTrance(taskId, ch);
    }

    public Observable<DUKeepAliveResult> keepAlive(DUKeepAliveInfo duKeepAliveInfo) {
        return mHttpHelper.keepAlive(duKeepAliveInfo);
    }

    public Observable<DUUploadingFileResult> uploadFile(DUUploadingFile duUploadingFile) {
        return mHttpHelper.uploadFile(duUploadingFile);
    }

    public Observable<DUCardCoordinateResult> uploadCardCoordinate(DUCardCoordinateInfo duCardCoordinateInfo) {
        return mHttpHelper.uploadCardCoordinate(duCardCoordinateInfo);
    }

    public void checkFinishedTasks(String account, List<DUTask> duTasks) {
        mDbHelper.checkFinishedTasks(account, duTasks);
    }

//    /**
//     *
//     * @param duWordsInfo
//     * @return
//     */
//    public Observable<DUWordsResult> inserWords(DUWordsInfo duWordsInfo) {
//        return mDbHelper.insertWords(duWordsInfo);
//    }
//
//    /**
//     *
//     * @param duWordsInfo
//     * @return
//     */
//    public Observable<DUWordsResult> deleteWords(DUWordsInfo duWordsInfo) {
//        return mDbHelper.deleteWords(duWordsInfo);
//    }


    /**
     * initialize logger file
     */
    public void initLogger() {
        LogUtil.initLogger(mConfigHelper.getLogFilePath().getPath());
    }

    public Observable<List<ChaoBiaoZT>> getSortStatus() {
        String param = mConfigHelper.getUserConfig()
                .getString(UserConfig.PARAM_CB_DEFAULT_CYCBZT);
        return mDbHelper.getSortStatus(param);
    }

    public void setUploadingImageRepeatedly(boolean uploadingImageRepeatedly) {
        mIsUploadingImageRepeatedly = uploadingImageRepeatedly;
    }

    public Observable<Boolean> isTimeSync(final long time, final String account, final Integer type) {
        return mDbHelper.isTimeSync(time, account, type);
    }

    /**
     * 在线计算金额
     * @param duRecord
     * @return
     */
    public Observable<List<DUBillPreview>> calculateCash(DURecord duRecord, int meterType){
        return mHttpHelper.calculateCash(duRecord, meterType);
    }
}
