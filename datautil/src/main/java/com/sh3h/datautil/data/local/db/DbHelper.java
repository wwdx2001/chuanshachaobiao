package com.sh3h.datautil.data.local.db;

import android.content.Context;

import com.sh3h.dataprovider.DBManager;
import com.sh3h.dataprovider.entity.ConditionInfo;
import com.sh3h.dataprovider.greendaoDao.ChaoBiaoSJDao;
import com.sh3h.dataprovider.greendaoDao.YanChiBiaoDao;
import com.sh3h.dataprovider.greendaoEntity.BIAOKAXX;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoJL;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoRW;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoSJ;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZTFL;
import com.sh3h.dataprovider.greendaoEntity.CiYuXX;
import com.sh3h.dataprovider.greendaoEntity.DingEJJBL;
import com.sh3h.dataprovider.greendaoEntity.DuoMeiTXX;
import com.sh3h.dataprovider.greendaoEntity.FeiYongZC;
import com.sh3h.dataprovider.greendaoEntity.HuanBiaoJL;
import com.sh3h.dataprovider.greendaoEntity.JiChaRW;
import com.sh3h.dataprovider.greendaoEntity.JiChaSJ;
import com.sh3h.dataprovider.greendaoEntity.JianHao;
import com.sh3h.dataprovider.greendaoEntity.JianHaoMX;
import com.sh3h.dataprovider.greendaoEntity.JiaoFeiXX;
import com.sh3h.dataprovider.greendaoEntity.QianFeiXX;
import com.sh3h.dataprovider.greendaoEntity.RushPayRW;
import com.sh3h.dataprovider.greendaoEntity.WaiFuCBSJ;
import com.sh3h.dataprovider.greendaoEntity.YanChiBiao;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUCardResult;
import com.sh3h.datautil.data.entity.DUChaoBiaoJL;
import com.sh3h.datautil.data.entity.DUChaoBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUCiYuXXInfo;
import com.sh3h.datautil.data.entity.DUCombined;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.datautil.data.entity.DUDelayRecordInfo;
import com.sh3h.datautil.data.entity.DUDelayRecordResult;
import com.sh3h.datautil.data.entity.DUDingEJJBL;
import com.sh3h.datautil.data.entity.DUFeiYongZC;
import com.sh3h.datautil.data.entity.DUHuanBiaoJL;
import com.sh3h.datautil.data.entity.DUHuanBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUJianHao;
import com.sh3h.datautil.data.entity.DUJianHaoMX;
import com.sh3h.datautil.data.entity.DUJiaoFeiXX;
import com.sh3h.datautil.data.entity.DUJiaoFeiXXInfo;
import com.sh3h.datautil.data.entity.DULgld;
import com.sh3h.datautil.data.entity.DULgldInfo;
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
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJInfo;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJResult;
import com.sh3h.datautil.data.entity.TraceInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class DbHelper {
    private static final String TAG = "DbHelper";

    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private boolean mIsInit;

    @Inject
    public DbHelper(@ApplicationContext Context context,
                    ConfigHelper configHelper) {
        mContext = context;
        mConfigHelper = configHelper;
        mIsInit = false;
    }

    /**
     * initialize
     */
    public synchronized void init() {
        if (!mIsInit) {
            mIsInit = true;
            DBManager.getInstance().init(mConfigHelper.getDBFilePath().getPath(), mContext);
        }
    }

    /**
     * destroy
     */
    public void destroy() {
        if (mIsInit) {
            mIsInit = false;
            DBManager.getInstance().destroy();
        }
    }

    /**
     * save tasks
     *
     * @param duTaskList
     */
    public boolean saveTasks(List<DUTask> duTaskList) {
        try {
            init();

            if (duTaskList != null) {
                for (DUTask duTask : duTaskList) {
                    ChaoBiaoRW chaoBiaoRW = duTask2ChaoBiaoRW(duTask);
                    DBManager.getInstance().insertChaoBiaoRW(chaoBiaoRW);
                }

                return true;
            } else {
                LogUtil.i(TAG, "---saveTasks: duTaskList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveTask---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * get tasks
     *
     * @param duTaskInfo
     * @return
     */
    public Observable<List<DUTask>> getTasks(final DUTaskInfo duTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUTask>>() {
            @Override
            public void call(Subscriber<? super List<DUTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duTaskInfo == null) {
                        throw new NullPointerException("duTaskInfo is null");
                    }

                    String account = duTaskInfo.getAccount();
                    if (TextUtil.isNullOrEmpty(account)) {
                        throw new NullPointerException("account is null");
                    }

                    List<ChaoBiaoRW> chaoBiaoRWList = DBManager.getInstance().getChaoBiaoRWList(account, ChaoBiaoRW.TYPE_CHAOBIAORW);
                    if (chaoBiaoRWList != null) {
                        List<DUTask> duTaskList = new ArrayList<>();
                        for (ChaoBiaoRW chaoBiaoRW : chaoBiaoRWList) {
                            DUTask duTask = chaoBiaoRW2DUTask(chaoBiaoRW);
                            duTask.setTongBuBZ(duTask.getYiChaoShu() == duTask.getZongShu()
                                    ? DBManager.getInstance().isUploaded(account, duTask.getcH()) : 0);
                            duTaskList.add(duTask);
                        }
                        subscriber.onNext(duTaskList);
                    } else {
                        LogUtil.i(TAG, "---getTasks: chaoBiaoRWList is null---");
                        subscriber.onError(new Throwable("chaoBiaoRWList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get SamplingTasks
     *
     * @param duSamplingTaskInfo
     * @return
     */
    public Observable<List<DUSamplingTask>> getSamplingTasks(final DUSamplingTaskInfo duSamplingTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUSamplingTask>>() {
            @Override
            public void call(Subscriber<? super List<DUSamplingTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if (duSamplingTaskInfo == null) {
                        throw new NullPointerException("duSamplingTaskInfo is null");
                    }
                    String account = duSamplingTaskInfo.getAccount();
                    if (TextUtil.isNullOrEmpty(account)) {
                        throw new NullPointerException("account is null");
                    }
                    List<JiChaRW> jiChaRWList = DBManager.getInstance().getJiChaRWList(account);
                    if (jiChaRWList != null) {
                        List<DUSamplingTask> duSamplingTasks = new ArrayList<>();
                        for (JiChaRW jiChaRW : jiChaRWList) {
                            DUSamplingTask duSamplingTask = jiChaRW2DUSamplingTask(jiChaRW);
                            duSamplingTasks.add(duSamplingTask);
                        }
                        subscriber.onNext(duSamplingTasks);
                    } else {
                        LogUtil.i(TAG, "---getSamplingTasks: jiChaRWList is null---");
                        subscriber.onError(new Throwable("jiChaRWList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * save sampling tasks
     *
     * @param duSamplingTaskList
     */
    public boolean saveSamplingTasks(List<DUSamplingTask> duSamplingTaskList) {
        try {
            init();

            if (duSamplingTaskList != null) {
                for (DUSamplingTask duSamplingTask : duSamplingTaskList) {
                    JiChaRW jiChaRW = duSamplingTask2JiChaRW(duSamplingTask);
                    DBManager.getInstance().insertJiChaRW(jiChaRW);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveSamplingTasks: duSamplingTaskList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveSamplingTasks---%s", e.getMessage()));
        }

        return false;
    }


    public Observable<Boolean> isSamplingTaskContainingFullData(final DUSamplingTaskInfo duSamplingTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duSamplingTaskInfo == null)
                            || TextUtil.isNullOrEmpty(duSamplingTaskInfo.getAccount())
                            || (duSamplingTaskInfo.getTaskId() <= 0)
                    ) {
                        throw new NullPointerException("parameter is null");
                    }

                    String account = duSamplingTaskInfo.getAccount();
                    int taskId = duSamplingTaskInfo.getTaskId();
                    JiChaRW jiChaRW = DBManager.getInstance().getJiChaRW(account, taskId);
                    if (jiChaRW == null) {
                        subscriber.onError(new Throwable("chaoBiaoRW is null"));
                        return;
                    }

                    if (jiChaRW.getI_ZongShu() <= 0) {
                        subscriber.onNext(true);
                        return;
                    }

                    List<JiChaSJ> jiChaSJList =
                            DBManager.getInstance().getListJiChaSJ(taskId, 6);
                    if (jiChaSJList == null) {
                        subscriber.onError(new Throwable("jiChaSJList is null"));
                        return;
                    }

                    int biaoKaXXCount = (int) DBManager.getInstance().getCountByTaskId(taskId);
//                    for (JiChaSJ jiChaSJ : jiChaSJList) {
//                        BIAOKAXX biaokaxx = DBManager.getInstance().getBiaoKaXX(jiChaSJ.getS_CID());
//                        if (biaokaxx != null) {
//                            biaoKaXXCount++;
//                        }
//                    }

                    if (jiChaSJList.size() != jiChaRW.getI_ZongShu() || biaoKaXXCount != jiChaRW.getI_ZongShu()) {
                        subscriber.onNext(false);
                    } else {
                        subscriber.onNext(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param account
     * @param taskIdList
     * @return
     */
    public Observable<List<DUTask>> getRemovedTasks(final String account,
                                                    final List<String> taskIdList) {
        return Observable.create(new Observable.OnSubscribe<List<DUTask>>() {
            @Override
            public void call(Subscriber<? super List<DUTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((account == null) || (taskIdList == null)) {
                        throw new NullPointerException("parameter is null");
                    }

                    List<ChaoBiaoRW> chaoBiaoRWList =
                            DBManager.getInstance().getRemovedChaoBiaoRW(account, taskIdList);
                    if (chaoBiaoRWList != null) {
                        List<DUTask> duTaskList = new ArrayList<>();
                        for (ChaoBiaoRW chaoBiaoRW : chaoBiaoRWList) {
                            DUTask duTask = chaoBiaoRW2DUTask(chaoBiaoRW);
                            duTaskList.add(duTask);
                        }
                        subscriber.onNext(duTaskList);
                    } else {
                        LogUtil.i(TAG, "---getRemovedTasks: chaoBiaoRWList is null---");
                        subscriber.onError(new Throwable("chaoBiaoRWList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param account
     * @param taskIdList
     * @return
     */
    public Observable<List<DUSamplingTask>> getRemovedSamplingTasks(final String account,
                                                                    final List<String> taskIdList) {
        return Observable.create(new Observable.OnSubscribe<List<DUSamplingTask>>() {
            @Override
            public void call(Subscriber<? super List<DUSamplingTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();
                    if ((account == null) || (taskIdList == null)) {
                        throw new NullPointerException("parameter is null");
                    }
                    List<JiChaRW> jiChaRWList =
                            DBManager.getInstance().getRemovedJiChaRW(account, taskIdList);
                    if (jiChaRWList != null) {
                        List<DUSamplingTask> duSamplingTasks = new ArrayList<>();
                        for (JiChaRW jiChaRW : jiChaRWList) {
                            DUSamplingTask duSamplingTask = jiChaRW2DUSamplingTask(jiChaRW);
                            duSamplingTasks.add(duSamplingTask);
                        }
                        subscriber.onNext(duSamplingTasks);
                    } else {
                        LogUtil.i(TAG, "---getRemovedSamplingTasks: jiChaRWList is null---");
                        subscriber.onError(new Throwable("jiChaRWList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get a task
     *
     * @param duTaskInfo
     * @return
     */
    public Observable<DUTask> getTask(final DUTaskInfo duTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<DUTask>() {
            @Override
            public void call(Subscriber<? super DUTask> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duTaskInfo == null)
                            || TextUtil.isNullOrEmpty(duTaskInfo.getAccount())
                            || TextUtil.isNullOrEmpty(duTaskInfo.getVolume())) {
                        throw new NullPointerException("duTaskInfo contains null parameter");
                    }

                    String account = duTaskInfo.getAccount();
                    int taskId = duTaskInfo.getTaskId();
                    String volume = duTaskInfo.getVolume();
                    ChaoBiaoRW chaoBiaoRW = DBManager.getInstance().getChaoBiaoRW(account,
                            taskId, volume);
                    if (chaoBiaoRW != null) {
                        DUTask duTask = chaoBiaoRW2DUTask(chaoBiaoRW);
                        subscriber.onNext(duTask);
                    } else {
                        LogUtil.i(TAG, "---getTask: chaoBiaoRW is null---");
                        subscriber.onError(new Throwable("chaoBiaoRW is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Boolean> updateTask(final DUTaskInfo duTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duTaskInfo == null)
                            || TextUtil.isNullOrEmpty(duTaskInfo.getAccount())
                            || (duTaskInfo.getTaskId() <= 0)
                            || TextUtil.isNullOrEmpty(duTaskInfo.getVolume())) {
                        throw new NullPointerException("duTaskInfo contains null parameter");
                    }

                    String account = duTaskInfo.getAccount();
                    int taskId = duTaskInfo.getTaskId();
                    String volume = duTaskInfo.getVolume();
                    boolean result = false;
                    if (duTaskInfo.getFilterType() == DUTaskInfo.FilterType.UPDATE_SYNC_FLAG) {
                        boolean needSync = duTaskInfo.isNeedSync();
                        result = DBManager.getInstance().updateChaoBiaoRW(account, taskId,
                                volume, needSync);
                    } else if (duTaskInfo.getFilterType() == DUTaskInfo.FilterType.UPDATE_TASK_COUNT) {
                        ChaoBiaoRW chaoBiaoRW =
                                DBManager.getInstance().getChaoBiaoRW(account, taskId, volume);
                        List<ChaoBiaoSJ> chaoBiaoSJList =
                                DBManager.getInstance().getListChaoBiaoSJ(taskId, volume, 6);
                        if (chaoBiaoRW != null) {
                            int readCount = 0;
                            int totalCount = 0;
                            if (chaoBiaoSJList != null) {
                                for (ChaoBiaoSJ chaoBiaoSJ : chaoBiaoSJList) {
                                    if (chaoBiaoSJ.getI_CHAOBIAOBZ() == DURecord.CHAOBIAOBZ_YICHAO) {
                                        readCount++;
                                    }
                                }

                                totalCount = chaoBiaoSJList.size();
                            }

                            if (readCount > totalCount) {
                                readCount = totalCount;
                            }

                            chaoBiaoRW.setI_YiChaoShu(readCount);
                            chaoBiaoRW.setI_ZongShu(totalCount);
                            result = DBManager.getInstance().updateChaoBiaoRW(chaoBiaoRW);
                        }
                    }

                    subscriber.onNext(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }


    public Observable<Boolean> updateSamplingTask(final DUSamplingTaskInfo duSamplingTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if ((duSamplingTaskInfo == null)
                            || TextUtil.isNullOrEmpty(duSamplingTaskInfo.getAccount())
                            || (duSamplingTaskInfo.getTaskId() <= 0)
                    ) {
                        throw new NullPointerException("duSamplingTaskInfo contains null parameter");
                    }

                    String account = duSamplingTaskInfo.getAccount();
                    int taskId = duSamplingTaskInfo.getTaskId();
                    boolean result = false;
                    if (duSamplingTaskInfo.getFilterType() == DUSamplingTaskInfo.FilterType.UPDATE_SYNC_FLAG) {
                        boolean needSync = duSamplingTaskInfo.isNeedSync();
                        result = DBManager.getInstance().updateJiChaRW(account, taskId, needSync);
                    } else if (duSamplingTaskInfo.getFilterType() == DUSamplingTaskInfo.FilterType.UPDATE_TASK_COUNT) {
                        JiChaRW jiChaRW =
                                DBManager.getInstance().getJiChaRW(account, taskId);
                        List<JiChaSJ> jiChaSJList =
                                DBManager.getInstance().getListJiChaSJ(taskId, 6);
                        if (jiChaRW != null) {
                            int readCount = 0;
                            int totalCount = 0;
                            if (jiChaSJList != null) {
                                for (JiChaSJ jiChaSJ : jiChaSJList) {
                                    if (jiChaSJ.getI_CHAOBIAOBZ() == DUSampling.CHAOBIAOBZ_YICHAO) {
                                        readCount++;
                                    }
                                }

                                totalCount = jiChaSJList.size();
                            }

                            if (readCount > totalCount) {
                                readCount = totalCount;
                            }

                            jiChaRW.setI_YiChaoShu(readCount);
                            jiChaRW.setI_ZongShu(totalCount);
                            result = DBManager.getInstance().updateJiChaRW(jiChaRW);
                        }
                    }

                    subscriber.onNext(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param duTaskInfo
     * @return
     */
    public Observable<List<DuoMeiTXX>> deleteTask(final DUTaskInfo duTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DuoMeiTXX>>() {
            @Override
            public void call(Subscriber<? super List<DuoMeiTXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duTaskInfo == null)
                            || (duTaskInfo.getFilterType() != DUTaskInfo.FilterType.DELETE)
                            || TextUtil.isNullOrEmpty(duTaskInfo.getAccount())
                            || TextUtil.isNullOrEmpty(duTaskInfo.getVolume())) {
                        throw new NullPointerException("duTaskInfo contains null parameter");
                    }

                    String account = duTaskInfo.getAccount();
                    int taskId = duTaskInfo.getTaskId();
                    String volume = duTaskInfo.getVolume();
                    DBManager.getInstance().deleteChaoBiaoRW(account, taskId, volume);
                    DBManager.getInstance().deleteBiaoKaXXs(volume);
                    DBManager.getInstance().deleteChaoBiaoSJ(account, taskId, volume);
                    List<DuoMeiTXX> duoMeiTXXList =
                            DBManager.getInstance().getAllDuoMeiTXXList(account, taskId, volume);
                    DBManager.getInstance().deleteDuoMeiTXX(account, taskId, volume);
                    subscriber.onNext(duoMeiTXXList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<DuoMeiTXX>> deleteUploadMedias() {
        return Observable.create(new Observable.OnSubscribe<List<DuoMeiTXX>>() {
            @Override
            public void call(Subscriber<? super List<DuoMeiTXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();
                    List<DuoMeiTXX> duoMeiTXXList = DBManager.getInstance().getUploadDuoMeiTXXList();
                    subscriber.onNext(duoMeiTXXList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param duSamplingTaskInfo
     * @return
     */
    public Observable<List<DuoMeiTXX>> deleteSamplingTask(final DUSamplingTaskInfo duSamplingTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DuoMeiTXX>>() {
            @Override
            public void call(Subscriber<? super List<DuoMeiTXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if ((duSamplingTaskInfo == null)
                            || (duSamplingTaskInfo.getFilterType() != DUSamplingTaskInfo.FilterType.DELETE)
                            || TextUtil.isNullOrEmpty(duSamplingTaskInfo.getAccount())
                    ) {
                        throw new NullPointerException("duJiChaTaskInfo contains null parameter");
                    }

                    String account = duSamplingTaskInfo.getAccount();
                    int taskId = duSamplingTaskInfo.getTaskId();
                    String volume = duSamplingTaskInfo.getVolume();

                    DBManager.getInstance().deleteJiChaRW(account, taskId);
                    DBManager.getInstance().deleteJiChaSJ(account, taskId);

                    List<DuoMeiTXX> duoMeiTXXList =
                            DBManager.getInstance().getJiChaDuoMeiTXXList(account, taskId);
                    DBManager.getInstance().deleteDuoMeiTXX(account, taskId, volume);
                    subscriber.onNext(duoMeiTXXList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get a WaiFuCBSJ
     *
     * @param duWaiFuCBSJInfo
     * @return
     */
    public Observable<DUWaiFuCBSJ> getWaiFuCBSJ(final DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return Observable.create(new Observable.OnSubscribe<DUWaiFuCBSJ>() {
            @Override
            public void call(Subscriber<? super DUWaiFuCBSJ> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    WaiFuCBSJ waiFuCBSJ = null;
                    switch (duWaiFuCBSJInfo.getFilterType()) {
                        case ONE:
                            waiFuCBSJ = DBManager.getInstance().getWaiFuCBSJ(duWaiFuCBSJInfo.getTaskId(),
                                    duWaiFuCBSJInfo.getVolume(), duWaiFuCBSJInfo.getCustomerId());
                            break;
                        case PREVIOUS_ONE:
                            waiFuCBSJ = DBManager.getInstance().getPreviousWaiFuCBSJ(duWaiFuCBSJInfo.getAccount(),
                                    duWaiFuCBSJInfo.getCustomerId());
                            break;
//                        case PREVIOUS_ONE_NOT_READING:
//                            waiFuCBSJ = DBManager.getInstance().getPreviousWaiFuCBSJ(duWaiFuCBSJInfo.getTaskId(),
//                                    duWaiFuCBSJInfo.getVolume(), duWaiFuCBSJInfo.getCeNeiXH(), true);
//                            break;
                        case NEXT_ONE:
                            waiFuCBSJ = DBManager.getInstance().getNextWaiFuCBSJ(duWaiFuCBSJInfo.getAccount(),
                                    duWaiFuCBSJInfo.getCustomerId());
                            break;
//                        case NEXT_ONE_NOT_READING:
//                            waiFuCBSJ = DBManager.getInstance().getNextWaiFuCBSJ(duWaiFuCBSJInfo.getTaskId(),
//                                    duWaiFuCBSJInfo.getVolume(), duWaiFuCBSJInfo.getCeNeiXH(), true);
//                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (waiFuCBSJ != null) {
                        DUWaiFuCBSJ duWaiFuCBSJ = waiFuCBSJ2DUWaiFuCBSJ(waiFuCBSJ);
                        subscriber.onNext(duWaiFuCBSJ);
                    } else {
                        LogUtil.i(TAG, "---getWaiFuCBSJ: WaiFuCBSJ is null---");
                        subscriber.onError(new Throwable("WaiFuCBSJ is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }


    public Observable<List<DUWaiFuCBSJ>> getWaiFuCBSJS(final DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUWaiFuCBSJ>>() {
            @Override
            public void call(Subscriber<? super List<DUWaiFuCBSJ>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duWaiFuCBSJInfo == null)
                            || TextUtil.isNullOrEmpty(duWaiFuCBSJInfo.getAccount())) {
                        throw new NullPointerException("parameter is null");
                    }

                    String account = duWaiFuCBSJInfo.getAccount();
                    List<WaiFuCBSJ> waiFuCBSJList = null;
                    List<String> userIds = null;
                    switch (duWaiFuCBSJInfo.getFilterType()) {
                        case SYNC_ALL:
                            waiFuCBSJList = DBManager.getInstance().getWaiFuCBSJList(account, 1);
                            break;
                        case SEARCH_ALL:
                            waiFuCBSJList = DBManager.getInstance().getWaiFuCBSJList(account, -1);
                            break;
                        case SEARCH:
                            if (duWaiFuCBSJInfo.getKey() != null) {
                                userIds = DBManager.getInstance().getBiaoKaXXByKeyWaiFu(duWaiFuCBSJInfo.getKey());
                                if (userIds != null) {
                                    waiFuCBSJList = DBManager.getInstance().getWaiFuCBSJs(userIds);
                                }
                            }
                            break;
                        case UNFINISHING:
                            waiFuCBSJList = DBManager.getInstance().getWaiFuCBSJList(account, 0);
                            break;
                        case FINISHING:
                            waiFuCBSJList = DBManager.getInstance().getWaiFuCBSJList(account, 3);
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (waiFuCBSJList != null) {
                        List<DUWaiFuCBSJ> duWaiFuCBSJList = new ArrayList<>();
                        for (WaiFuCBSJ waiFuCBSJ : waiFuCBSJList) {
                            DUWaiFuCBSJ duWaiFuCBSJ = waiFuCBSJ2DUWaiFuCBSJ(waiFuCBSJ);
                            duWaiFuCBSJList.add(duWaiFuCBSJ);
                        }

                        subscriber.onNext(duWaiFuCBSJList);
                    } else {
                        LogUtil.i(TAG, "---getRecords: chaoBiaoSJList is null---");
                        subscriber.onError(new Throwable("chaoBiaoSJList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * save records
     *
     * @param account
     * @param taskId
     * @param volume
     * @param duRecordList
     * @return
     */
    public boolean saveRecords(String account, int taskId, String volume, List<DURecord> duRecordList) {
        try {
            init();

            if ((!TextUtil.isNullOrEmpty(account))
                    && (taskId > 0)
                    && (!TextUtil.isNullOrEmpty(volume))
                    && (duRecordList != null)) {
                int readCount = 0;
                List<ChaoBiaoSJ> chaoBiaoSJList = new ArrayList<>();
                for (DURecord duRecord : duRecordList) {
                    ChaoBiaoSJ chaoBiaoSJ = duRecord2ChaoBiaoSJ(duRecord);
                    if (duRecord.getIchaobiaobz() == 1 && duRecord.getShangchuanbz() == 1) {
                        chaoBiaoSJ.setI_ShangChuanBZ(2);//从服务器获取已上传数据时候，将上传状态改为已上传。
                    }

                    if (duRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO) {
                        readCount++;
                    }

                    chaoBiaoSJList.add(chaoBiaoSJ);
                }

                int totalCount = chaoBiaoSJList.size();

                if (totalCount > 0) {
                    long t = System.currentTimeMillis();
                    DBManager.getInstance().insertChaoBiaoSJList(chaoBiaoSJList, false);
                    t = System.currentTimeMillis() - t;
                    LogUtil.i(TAG, "---saveRecords---" + t);
                }

//                ChaoBiaoRW chaoBiaoRW = DBManager.getInstance().getChaoBiaoRW(account, taskId, volume);
//                if (chaoBiaoRW == null) {
//                    return false;
//                }
//                chaoBiaoRW.setI_YiChaoShu(readCount);
//                chaoBiaoRW.setI_ZongShu(totalCount);
//                return DBManager.getInstance().updateChaoBiaoRW(chaoBiaoRW);
                return true;
            } else {
                LogUtil.i(TAG, "---saveRecords: parameter is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveRecords---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * save Delay Records
     *
     * @param duRecordList
     * @return
     */
    public boolean saveDelayRecords(List<DUDelayRecord> duRecordList) {
        try {
            init();

            if (duRecordList != null) {
                List<YanChiBiao> list = new ArrayList<>();
                List<String> cids = new ArrayList<>();
                List<String> chs = new ArrayList<>();
                String ch;
                for (DUDelayRecord duDelayRecord : duRecordList) {
                    YanChiBiao chiBiao = duDelayRecord2YanChiBiao(duDelayRecord);
                    list.add(chiBiao);
                    cids.add(duDelayRecord.getS_CID());
                    ch = chiBiao.getS_CH();
                    if (!chs.contains(ch)) {
                        chs.add(ch);
                    }
                }

                List<DuoMeiTXX> duoMeiTXXes = DBManager.getInstance().getDeleteDelayDuoMeiTXX(cids);
                cids.clear();
                if (duoMeiTXXes != null && duoMeiTXXes.size() > 0) {
                    String cid;
                    for (DuoMeiTXX duoMeiTXX : duoMeiTXXes) {
                        cid = duoMeiTXX.getS_CID();
                        if (!cids.contains(cid)) {
                            cids.add(cid);
                        }
                        String path = duoMeiTXX.getS_WENJIANLJ();
                        if (TextUtil.isNullOrEmpty(path)) {
                            continue;
                        }

                        File file = new File(path);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    DBManager.getInstance().deleteDelayDuoMeiTXX(cids);
                }

                long t = System.currentTimeMillis();
                DBManager.getInstance().insertYanChiBiaoList(list);

                t = System.currentTimeMillis() - t;
                LogUtil.i(TAG, "---saveDelayRecords---" + t);
                return true;
            } else {
                LogUtil.i(TAG, "---saveDelayRecords: parameter is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveDelayRecords---%s", e.getMessage()));
        }

        return false;
    }


    /**
     * save waifusj
     *
     * @param account
     * @param duWaiFuCBSJList
     * @return
     */
    public boolean saveWaiFuSJs(String account, List<DUWaiFuCBSJ> duWaiFuCBSJList) {
        try {
            init();

            if ((!TextUtil.isNullOrEmpty(account))
                    && (duWaiFuCBSJList != null)) {
                int readCount = 0;
                List<WaiFuCBSJ> waiFuCBSJList = new ArrayList<>();
                for (DUWaiFuCBSJ duWaiFuCBSJ : duWaiFuCBSJList) {
                    WaiFuCBSJ waiFuCBSJ = duWaiFuCBSJ2WaiFuCBSJ(duWaiFuCBSJ);
                    if (duWaiFuCBSJ.getIchaobiaobz() == 1 && duWaiFuCBSJ.getShangchuanbz() == 1) {
                        waiFuCBSJ.setI_ShangChuanBZ(2);//从服务器获取已上传数据时候，将上传状态改为已上传。
                    }

                    if (duWaiFuCBSJ.getIchaobiaobz() == DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO) {
                        readCount++;
                    }

                    waiFuCBSJList.add(waiFuCBSJ);
                }

                int totalCount = waiFuCBSJList.size();
                if (readCount > totalCount) {
                    readCount = totalCount;
                }

                // delete
                List<WaiFuCBSJ> waiFuCBSJListRemove = DBManager.getInstance()
                        .getRemoveWaiFuCBSJList(waiFuCBSJList, account);

                if (waiFuCBSJListRemove != null && waiFuCBSJListRemove.size() > 0) {
                    for (WaiFuCBSJ waiFuCBSJ : waiFuCBSJListRemove) {
                        //delete Medias files
                        List<DuoMeiTXX> duoMeiTXXList =
                                DBManager.getInstance().getJiChaDuoMeiTXXList(account, waiFuCBSJ.getI_RenWuBH());
                        for (DuoMeiTXX duoMeiTXX : duoMeiTXXList) {
                            String path = duoMeiTXX.getS_WENJIANLJ();
                            if (TextUtil.isNullOrEmpty(path)) {
                                continue;
                            }

                            File file = new File(path);
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                        //delete Medias table data
                        DBManager.getInstance().deleteDuoMeiTXX(account, waiFuCBSJ.getI_RenWuBH(), waiFuCBSJ.getS_CH());
                    }
                }

                DBManager.getInstance().insertWaiFuCBSJList(waiFuCBSJList, account);

                return true;
            } else {
                LogUtil.i(TAG, "---saveWaiFuSJs: parameter is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveWaiFuSJs---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * save records
     *
     * @param account
     * @param taskId
     * @param duSamplingList
     * @return
     */
    public boolean saveSamplings(String account, int taskId, List<DUSampling> duSamplingList) {
        try {
            init();
            if ((!TextUtil.isNullOrEmpty(account))
                    && (taskId > 0)
                    && (duSamplingList != null)) {
                int readCount = 0;
                List<JiChaSJ> jiChaSJList = new ArrayList<>();
                for (DUSampling duSampling : duSamplingList) {
                    JiChaSJ jiChaSJ = duSampling2JiChaSJ(duSampling);
                    if (duSampling.getIchaobiaobz() == 1 && duSampling.getShangchuanbz() == 1) {
                        jiChaSJ.setI_ShangChuanBZ(2);//从服务器获取已上传数据时候，将上传状态改为已上传。
                    }
                    if (duSampling.getIchaobiaobz() == DUSampling.CHAOBIAOBZ_YICHAO) {
                        readCount++;
                    }
                    jiChaSJList.add(jiChaSJ);
                }
                int totalCount = jiChaSJList.size();
                if (readCount > totalCount) {
                    readCount = totalCount;
                }
                if (totalCount > 0) {
                    long t = System.currentTimeMillis();
                    DBManager.getInstance().insertJiChaSJList(jiChaSJList, false);
                    t = System.currentTimeMillis() - t;
                    LogUtil.i(TAG, "---saveSamplings---" + t);
                }

//                ChaoBiaoRW chaoBiaoRW = DBManager.getInstance().getChaoBiaoRW(account, taskId, volume);
//                if (chaoBiaoRW == null) {
//                    return false;
//                }
//                chaoBiaoRW.setI_YiChaoShu(readCount);
//                chaoBiaoRW.setI_ZongShu(totalCount);
//                return DBManager.getInstance().updateChaoBiaoRW(chaoBiaoRW);
                return true;
            } else {
                LogUtil.i(TAG, "---saveSamplings: parameter is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveRecords---%s", e.getMessage()));
        }

        return false;
    }

    public Observable<List<String>> saveTemporaryRecords(final String account,
                                                         final List<DURecord> duRecordList) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (TextUtil.isNullOrEmpty(account)
                            || (duRecordList == null)) {
                        throw new NullPointerException("parameter is error");
                    }

                    DBManager.getInstance().deleteTemporaryBiaoKaXX(account);
                    DBManager.getInstance().deleteTemporaryChaoBiaoSJ(account);

                    List<String> volumeList = new ArrayList<String>();
                    boolean found = false;
                    List<ChaoBiaoSJ> destChaoBiaoSJList = new ArrayList<>();
                    for (DURecord duRecord : duRecordList) {
                        ChaoBiaoSJ chaoBiaoSJ = duRecord2ChaoBiaoSJ(duRecord);
                        if (duRecord.getIchaobiaobz() == 1 && duRecord.getShangchuanbz() == 1) {
                            chaoBiaoSJ.setI_ShangChuanBZ(2);//从服务器获取已上传数据时候，将上传状态改为已上传。
                        }

                        destChaoBiaoSJList.add(chaoBiaoSJ);
                        //DBManager.getInstance().insertChaoBiaoSJ(chaoBiaoSJ);

                        found = false;
                        for (String volume : volumeList) {
                            if (volume.equals(duRecord.getCh())) {
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            volumeList.add(duRecord.getCh());
                        }
                    }

                    long t = System.currentTimeMillis();
                    DBManager.getInstance().insertChaoBiaoSJList(destChaoBiaoSJList, true);
                    t = System.currentTimeMillis() - t;
                    LogUtil.i(TAG, "---saveTemporaryRecords---" + t);
                    subscriber.onNext(volumeList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param account
     * @param taskIdList
     * @return
     */
    public Observable<List<DURushPayTask>> getRemovedRushPayTasks(final String account,
                                                                  final List<String> taskIdList) {
        return Observable.create(new Observable.OnSubscribe<List<DURushPayTask>>() {
            @Override
            public void call(Subscriber<? super List<DURushPayTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if ((account == null) || (taskIdList == null)) {
                        throw new NullPointerException("parameter is null");
                    }
                    List<RushPayRW> rushPayRWList =
                            DBManager.getInstance().getRemovedRushPayRW(account, taskIdList);
                    if (rushPayRWList != null) {
                        List<DURushPayTask> duRushPayTasks = new ArrayList<>();
                        for (RushPayRW rushPayRW : rushPayRWList) {
                            DURushPayTask duRushPayTask = rushPayRW2DURushPayTask(rushPayRW);
                            duRushPayTasks.add(duRushPayTask);
                        }
                        subscriber.onNext(duRushPayTasks);
                    } else {
                        LogUtil.i(TAG, "---getRemovedRushPayTasks: rushPayRWList is null---");
                        subscriber.onError(new Throwable("rushPayRWList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * update records
     *
     * @param duRushPayTaskList
     * @return
     */
    public Observable<DURushPayTaskResult> updateRushPayTasks(final List<DURushPayTask> duRushPayTaskList) {
        return Observable.create(new Observable.OnSubscribe<DURushPayTaskResult>() {
            @Override
            public void call(Subscriber<? super DURushPayTaskResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duRushPayTaskList == null) {
                        throw new NullPointerException("duRushPayTaskList is null");
                    } else if (duRushPayTaskList.size() <= 0) {
                        throw new Exception("the count of duRushPayTaskList is 0");
                    }

                    int successCount = 0;
                    int failureCount = 0;
                    List<RushPayRW> rushPayRWList = new ArrayList<>();
                    for (DURushPayTask duRushPayTask : duRushPayTaskList) {

                        List<DuoMeiTXX> duCiYuXXList = DBManager.getInstance().getDuoMeiTXXList(
                                duRushPayTask.getS_MeterReader(),
                                null, null, duRushPayTask.getI_TaskId(), 4);
                        if (duCiYuXXList != null && duCiYuXXList.size() > 0) {
                            int notUpload = 0;
                            for (DuoMeiTXX duoMeiTXX : duCiYuXXList) {
                                if (duoMeiTXX.getI_SHANGCHUANBZ() != DUMedia.SHANGCHUANBZ_YISHANGC) {
                                    notUpload++;
                                }
                            }
                            duRushPayTask.setI_ISComplete(DURushPayTask.COMPLETE);
                            if (notUpload > 0) {
                                duRushPayTask.setI_IsUpload(DURushPayTask.SHANGCHUANBZ_WEISHANGC);
                            } else {
                                duRushPayTask.setI_IsUpload(DURushPayTask.SHANGCHUANBZ_YISHANGC);
                            }
                        }

                        RushPayRW rushPayRW = duRushPayTask2RushPayRW(duRushPayTask);
                        successCount++;
                        rushPayRWList.add(rushPayRW);
                    }

                    DBManager.getInstance().updateRushPayTaskList(rushPayRWList);


                    DURushPayTaskResult duRushPayTaskResult = null;
                    duRushPayTaskResult = new DURushPayTaskResult(
                            DURushPayTaskResult.FilterType.UPDATING,
                            duRushPayTaskList,
                            successCount,
                            failureCount);

                    subscriber.onNext(duRushPayTaskResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * update updateRushPayTasks
     *
     * @param duRushPayTaskList
     * @param isFromUploading
     * @return
     */
    public Observable<DURushPayTaskResult> updateRushPayTasks(final List<DURushPayTask> duRushPayTaskList,
                                                              final boolean isFromUploading) {
        return Observable.create(new Observable.OnSubscribe<DURushPayTaskResult>() {
            @Override
            public void call(Subscriber<? super DURushPayTaskResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if (duRushPayTaskList == null) {
                        throw new NullPointerException("duRushPayTaskList is null");
                    } else if (duRushPayTaskList.size() <= 0) {
                        throw new Exception("the count of duRushPayTaskList is 0");
                    }

                    int successCount = 0;
                    int failureCount = 0;
                    List<RushPayRW> rushPayRWs = new ArrayList<>();
                    for (DURushPayTask duRushPayTask : duRushPayTaskList) {
                        RushPayRW rushPayRW = duRushPayTask2RushPayRW(duRushPayTask);
                        if (isFromUploading) {
                            if (rushPayRW.getI_IsUpload() == DURushPayTask.SHANGCHUANBZ_YISHANGC) {
                                successCount++;
                            } else {
                                failureCount++;
                            }
                        } else {
                            successCount++;
                        }
                        rushPayRWs.add(rushPayRW);
                    }

                    DBManager.getInstance().updateRushPayList(rushPayRWs);

                    DURushPayTaskResult duRushPayTaskResult;
                    if (isFromUploading) {
                        duRushPayTaskResult = new DURushPayTaskResult(
                                DURushPayTaskResult.FilterType.UPLOADING,
                                successCount,
                                failureCount);
                    } else {
                        duRushPayTaskResult = new DURushPayTaskResult(
                                DURushPayTaskResult.FilterType.UPDATING,
                                successCount,
                                failureCount);
                    }
                    subscriber.onNext(duRushPayTaskResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * update updateWaiFuCBSjs
     *
     * @param duWaiFuCBSJList
     * @param isFromUploading
     * @return
     */
    public Observable<DUWaiFuCBSJResult> updateWaiFuCBSJs(final List<DUWaiFuCBSJ> duWaiFuCBSJList,
                                                          final boolean isFromUploading) {
        return Observable.create(new Observable.OnSubscribe<DUWaiFuCBSJResult>() {
            @Override
            public void call(Subscriber<? super DUWaiFuCBSJResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if (duWaiFuCBSJList == null) {
                        throw new NullPointerException("duWaiFuCBSJList is null");
                    } else if (duWaiFuCBSJList.size() <= 0) {
                        throw new Exception("the count of duWaiFuCBSJList is 0");
                    }

                    int successCount = 0;
                    int failureCount = 0;
                    List<WaiFuCBSJ> waiFuCBSJList = new ArrayList<>();
                    for (DUWaiFuCBSJ duWaiFuCBSJ : duWaiFuCBSJList) {
                        WaiFuCBSJ waiFuCBSJ = duWaiFuCBSJ2WaiFuCBSJ(duWaiFuCBSJ);
                        if (isFromUploading) {
                            if (duWaiFuCBSJ.getShangchuanbz() == DUWaiFuCBSJ.SHANGCHUANBZ_YISHANGC) {
                                successCount++;
                            } else {
                                failureCount++;
                            }
                        } else {
                            successCount++;
                        }
                        waiFuCBSJList.add(waiFuCBSJ);
                    }

                    DBManager.getInstance().updateWaiFuCBSJList(waiFuCBSJList);

                    DUWaiFuCBSJResult duWaiFuCBSJResult = null;
                    if (isFromUploading) {
                        duWaiFuCBSJResult = new DUWaiFuCBSJResult(
                                DUWaiFuCBSJResult.FilterType.UPLOADING,
                                duWaiFuCBSJList.get(0).getRenwubh(),
                                duWaiFuCBSJList.get(0).getCh(),
                                successCount,
                                failureCount);
                        duWaiFuCBSJResult.setCeNeiXH(duWaiFuCBSJList.get(0).getCeneixh());
                    } else {
                        duWaiFuCBSJResult = new DUWaiFuCBSJResult(
                                DUWaiFuCBSJResult.FilterType.UPDATING,
                                duWaiFuCBSJList.get(0).getRenwubh(),
                                duWaiFuCBSJList.get(0).getCh(),
                                successCount,
                                failureCount);
                        duWaiFuCBSJResult.setCeNeiXH(duWaiFuCBSJList.get(0).getCeneixh());
                    }

                    subscriber.onNext(duWaiFuCBSJResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get RushPayTasks
     *
     * @param duRushPayTaskInfo
     * @return DURushPayTaskResult
     */
    public Observable<List<DURushPayTask>> getRushPayTasks(final DURushPayTaskInfo duRushPayTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DURushPayTask>>() {
            @Override
            public void call(Subscriber<? super List<DURushPayTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if (duRushPayTaskInfo == null) {
                        throw new NullPointerException("duRushPayTaskInfo is null");
                    }
                    String account = duRushPayTaskInfo.getAccount();
                    if (TextUtil.isNullOrEmpty(account)) {
                        throw new NullPointerException("account is null");
                    }
                    List<RushPayRW> rushPayRWList = null;
                    DURushPayTaskInfo.FilterType filterType = duRushPayTaskInfo.getFilterType();
                    switch (filterType) {
                        case NOT_UPLOAD:
                            rushPayRWList = DBManager.getInstance().getRushPayRWList(account, 1);
                            break;
                        case ALL:
                            rushPayRWList = DBManager.getInstance().getRushPayRWList(account, 2);
                            break;
                        default:
                            break;
                    }

                    if (rushPayRWList != null) {
                        List<DURushPayTask> duRushPayTaskList = new ArrayList<>();
                        for (RushPayRW rushPayRW : rushPayRWList) {
                            DURushPayTask duRushPayTask = rushPayRW2DURushPayTask(rushPayRW);
                            duRushPayTaskList.add(duRushPayTask);
                        }
                        subscriber.onNext(duRushPayTaskList);
                    } else {
                        LogUtil.i(TAG, "---getRushPayTasks: rushPayRWList is null---");
                        subscriber.onError(new Throwable("rushPayRWList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<List<DuoMeiTXX>> deleteRushPayTask(final DURushPayTaskInfo duRushPayTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DuoMeiTXX>>() {
            @Override
            public void call(Subscriber<? super List<DuoMeiTXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    if ((duRushPayTaskInfo == null)
                            || (duRushPayTaskInfo.getFilterType() != DURushPayTaskInfo.FilterType.DELETE)
                            || TextUtil.isNullOrEmpty(duRushPayTaskInfo.getAccount())
                    ) {
                        throw new NullPointerException("duRushPayTaskInfo contains null parameter");
                    }

                    String account = duRushPayTaskInfo.getAccount();
                    int taskId = duRushPayTaskInfo.getTaskId();
                    DBManager.getInstance().deleteRushPayRW(account, taskId);

                    List<DuoMeiTXX> duoMeiTXXList =
                            DBManager.getInstance().getRushPayDuoMeiTXXList(account, taskId);
                    DBManager.getInstance().deleteDuoMeiTXX(account, taskId, "");
                    subscriber.onNext(duoMeiTXXList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * saveRushPayTasks
     *
     * @param duRushPayTaskList
     */
    public boolean saveRushPayTasks(List<DURushPayTask> duRushPayTaskList) {
        try {
            init();

            if (duRushPayTaskList != null) {
                for (DURushPayTask duRushPayTask : duRushPayTaskList) {
                    if (duRushPayTask.getI_ISComplete() == DURushPayTask.COMPLETE) {
                        duRushPayTask.setI_IsUpload(DURushPayTask.SHANGCHUANBZ_YISHANGC);
                    }
                    RushPayRW rushPayRW = duRushPayTask2RushPayRW(duRushPayTask);
                    boolean isInsert = DBManager.getInstance().insertRushPayRW(rushPayRW);
                    duRushPayTask.setNewData(isInsert);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveRushPayTasks: duRushPayTaskList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveRushPayTasks---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * update records
     *
     * @param duRecordList
     * @param isFromUploading
     * @return
     */
    public Observable<DURecordResult> updateRecords(final List<DURecord> duRecordList,
                                                    final boolean isFromUploading) {
        return Observable.create(new Observable.OnSubscribe<DURecordResult>() {
            @Override
            public void call(Subscriber<? super DURecordResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duRecordList == null) {
                        throw new NullPointerException("duRecordList is null");
                    } else if (duRecordList.size() <= 0) {
                        throw new Exception("the count of duRecordList is 0");
                    }

                    int successCount = 0;
                    int failureCount = 0;
                    List<ChaoBiaoSJ> chaoBiaoSJList = new ArrayList<>();
                    for (DURecord duRecord : duRecordList) {
                        ChaoBiaoSJ chaoBiaoSJ = duRecord2ChaoBiaoSJ(duRecord);
                        if (isFromUploading) {
                            if (duRecord.getShangchuanbz() == DURecord.SHANGCHUANBZ_YISHANGC) {
                                successCount++;
                            } else {
                                failureCount++;
                            }
                        } else {
                            successCount++;
                        }

                        chaoBiaoSJList.add(chaoBiaoSJ);
                    }

                    DBManager.getInstance().updateChaoBiaoSJList(chaoBiaoSJList);

                    DURecordResult duRecordResult = null;
                    if (isFromUploading) {
                        duRecordResult = new DURecordResult(
                                DURecordResult.FilterType.UPLOADING,
                                duRecordList.get(0).getRenwubh(),
                                duRecordList.get(0).getCh(),
                                successCount,
                                failureCount);
                    } else {
                        duRecordResult = new DURecordResult(
                                DURecordResult.FilterType.UPDATING,
                                duRecordList.get(0).getRenwubh(),
                                duRecordList.get(0).getCh(),
                                successCount,
                                failureCount);
                    }

                    subscriber.onNext(duRecordResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUDelayRecordResult> updateDelayRecords(final List<DUDelayRecord> duRecordList) {
        return Observable.create(new Observable.OnSubscribe<DUDelayRecordResult>() {
            @Override
            public void call(Subscriber<? super DUDelayRecordResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duRecordList == null) {
                        throw new NullPointerException("duRecordList is null");
                    } else if (duRecordList.size() <= 0) {
                        throw new Exception("the count of duRecordList is 0");
                    }

                    int successCount = 0;
                    int failureCount = 0;
                    List<YanChiBiao> yanChiBiaos = new ArrayList<>();
                    for (DUDelayRecord duRecord : duRecordList) {
                        YanChiBiao chaoBiaoSJ = duDelayRecord2YanChiBiao(duRecord);
                        successCount++;
                        yanChiBiaos.add(chaoBiaoSJ);
                    }

                    DBManager.getInstance().updateYanChiBiaoList(yanChiBiaos);

                    DUDelayRecordResult duRecordResult = new DUDelayRecordResult(
                            DUDelayRecordResult.FilterType.UPDATING,
                            duRecordList.get(0).getI_RENWUBH(),
                            duRecordList.get(0).getS_CH(),
                            successCount,
                            failureCount);

                    subscriber.onNext(duRecordResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * update sampling records
     *
     * @param duSamplingList
     * @param isFromUploading
     * @return
     */
    public Observable<DUSamplingResult> updateSamplings(final List<DUSampling> duSamplingList,
                                                        final boolean isFromUploading) {
        return Observable.create(new Observable.OnSubscribe<DUSamplingResult>() {
            @Override
            public void call(Subscriber<? super DUSamplingResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();
                    if (duSamplingList == null) {
                        throw new NullPointerException("duSamplingList is null");
                    } else if (duSamplingList.size() <= 0) {
                        throw new Exception("the count of duSamplingList is 0");
                    }

                    int successCount = 0;
                    int failureCount = 0;
                    List<JiChaSJ> jiChaSJList = new ArrayList<>();
                    for (DUSampling duSampling : duSamplingList) {
                        JiChaSJ jiChaSJ = duSampling2JiChaSJ(duSampling);
                        if (isFromUploading) {
                            if (duSampling.getShangchuanbz() == DUSampling.SHANGCHUANBZ_YISHANGC) {
                                successCount++;
                            } else {
                                failureCount++;
                            }
                        } else {
                            successCount++;
                        }

                        jiChaSJList.add(jiChaSJ);
                    }
                    DBManager.getInstance().updateJiChaSJList(jiChaSJList);
                    DUSamplingResult duSamplingResult = null;
                    if (isFromUploading) {
                        duSamplingResult = new DUSamplingResult(
                                DUSamplingResult.FilterType.UPLOADING,
                                duSamplingList.get(0).getRenwubh(),
                                duSamplingList.get(0).getCh(),
                                successCount,
                                failureCount);
                        duSamplingResult.setCid(jiChaSJList.get(0).getS_CID());
                    } else {
                        duSamplingResult = new DUSamplingResult(
                                DUSamplingResult.FilterType.UPDATING,
                                duSamplingList.get(0).getRenwubh(),
                                duSamplingList.get(0).getCh(),
                                successCount,
                                failureCount);
                        duSamplingResult.setCid(jiChaSJList.get(0).getS_CID());
                    }
                    subscriber.onNext(duSamplingResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get records
     *
     * @param duRecordInfo
     * @return
     */
    public Observable<List<DURecord>> getRecords(final DURecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DURecord>>() {
            @Override
            public void call(Subscriber<? super List<DURecord>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duRecordInfo == null) {
                        throw new Exception("param is null");
                    }

                    List<ChaoBiaoSJ> chaoBiaoSJList = null;
                    List<String> userIds = null;
                    switch (duRecordInfo.getFilterType()) {
                        case ALL:
                            chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), ChaoBiaoSJDao.ALLWORK);
                            break;
                        case NORMAL:
                            chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), ChaoBiaoSJDao.NORMAL);
                            break;
                        case UNNORMAL:
                            chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), ChaoBiaoSJDao.UNNORMAL);
                            break;
                        case FINISH:
                            chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), ChaoBiaoSJDao.FINISHWORK);
                            break;
                        case UNFINISH:
                            chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), ChaoBiaoSJDao.NOWORK);
                            break;
                        case SEARCH:
                            if (duRecordInfo.getKey() != null) {
                                userIds = DBManager.getInstance().getBiaoKaXXCIDS(duRecordInfo.getKey(), duRecordInfo.getVolume());
                                if (userIds != null) {
                                    chaoBiaoSJList = DBManager.getInstance().getChaoBiaoSJList(duRecordInfo.getTaskId(),
                                            duRecordInfo.getVolume(), userIds);
                                }
                            }
                            break;
                        case NEXT_UNFINISHED_ONE_WITH_CENEIXH:
                            chaoBiaoSJList = DBManager.getInstance().getNextUnfinishedChaoBiaoSJWithCeNeiXH(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume());
                            break;
                        case RE_UPDATING_ALL:
                            chaoBiaoSJList = DBManager.getInstance().getAllFinishedChaoBiaoSJ(duRecordInfo.getTaskId(), duRecordInfo.getVolume(),
                                    duRecordInfo.getAccount(), DURecord.CHAOBIAOBZ_YICHAO);
                            break;
                        case ALL_NO_CONDITION:
                            chaoBiaoSJList = DBManager.getInstance().getAllChaoBiaoSJ(duRecordInfo.getAccount());
                            break;
                        case NOT_UPLOADED:
                            chaoBiaoSJList = DBManager.getInstance().getNotUploadChaoBiaoSJ(duRecordInfo.getAccount(),
                                    duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume());
                            break;
                        case EACH_VOLUME:
                            chaoBiaoSJList = DBManager.getInstance().getAllChaoBiaoSJ(duRecordInfo.getAccount(),
                                    duRecordInfo.getTaskId(), duRecordInfo.getVolume());
                            break;
//                        case ALL_LIMIT:
//                            chaoBiaoSJList = DBManager.getInstance().getAllChaoBiaoSJWithLimit(
//                                    duRecordInfo.getAccount(),
//                                    duRecordInfo.getTaskId(),
//                                    duRecordInfo.getVolume(),
//                                    duRecordInfo.getCeNeiXH());
//                            break;
//                        case UNFINISHING_LIMIT:
//                            userIds = DBManager.getInstance().getBiaoKaXXCIDS("%%", duRecordInfo.getVolume());
//                            if (userIds != null) {
//                                chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJWithLimit(
//                                        duRecordInfo.getTaskId(),
//                                        duRecordInfo.getVolume(),
//                                        0,
//                                        userIds,
//                                        duRecordInfo.getCeNeiXH());
//                            }
//                            break;
//                        case FINISHING_LIMIT:
//                            userIds = DBManager.getInstance().getBiaoKaXXCIDS("%%", duRecordInfo.getVolume());
//                            if (userIds != null) {
//                                chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJWithLimit(
//                                        duRecordInfo.getTaskId(),
//                                        duRecordInfo.getVolume(),
//                                        1,
//                                        userIds,
//                                        duRecordInfo.getCeNeiXH());
//                            }
//                            break;
//                        case SEARCH_LIMIT:
//                            if (duRecordInfo.getKey() != null) {
//                                userIds = DBManager.getInstance().getBiaoKaXXCIDS(duRecordInfo.getKey(), duRecordInfo.getVolume());
//                                if (userIds != null) {
//                                    chaoBiaoSJList = DBManager.getInstance().getChaoBiaoSJListWithLimit(
//                                            duRecordInfo.getTaskId(),
//                                            duRecordInfo.getVolume(),
//                                            userIds,
//                                            duRecordInfo.getCeNeiXH());
//                                }
//                            }
//                            break;
                        case TEMPOPRARYDATA:
                            chaoBiaoSJList = DBManager.getInstance().getTemporaryChaoBiaoSJ(duRecordInfo.getAccount());
                            break;
                        case ARREAR:
                            chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), 6);
                            break;
                        case LOW_INCOME:
                            chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), 6);
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (chaoBiaoSJList != null) {
                        List<DURecord> duRecordList = new ArrayList<>();
                        for (ChaoBiaoSJ chaoBiaoSJ : chaoBiaoSJList) {
                            DURecord duRecord = chaoBiaoSJ2DURecord(chaoBiaoSJ);
                            duRecordList.add(duRecord);
                            if (duRecordInfo.isLocked()) {
                                chaoBiaoSJ.setI_ShangChuanBZ(DURecord.SHANGCHUANBZ_ZHENGZAISC);
                                DBManager.getInstance().updateChaoBiaoSJ(chaoBiaoSJ);
                            }
                        }

                        subscriber.onNext(duRecordList);
                    } else {
                        LogUtil.i(TAG, "---getRecords: chaoBiaoSJList is null---");
                        subscriber.onError(new Throwable("chaoBiaoSJList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get records
     *
     * @param duRecordInfo
     * @return
     */
    public Observable<List<DUDelayRecord>> getRecords(final DUDelayRecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUDelayRecord>>() {
            @Override
            public void call(Subscriber<? super List<DUDelayRecord>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duRecordInfo == null) {
                        throw new Exception("param is null");
                    }

                    List<YanChiBiao> yanChiBiaos = null;
                    List<String> userIds;
                    String account = duRecordInfo.getAccount();
                    switch (duRecordInfo.getFilterType()) {
                        case ALL:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.ALLWORK);
                            break;
                        case UNFINISHING:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.UNFINISHING);
                            break;
                        case FINISHING:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.FINISHWORK);
                            break;
                        case HIGHAMOUNT:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.HIGHAMOUNT);
                            break;
                        case LOWAMOUNT:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.LOWAMOUNT);
                            break;
                        case NOT_UPLOADED:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.FLAG_NOT_UPLOADED);
                            break;
//                        case SEARCH:
//                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, duRecordInfo.getKey());
//                            break;
//                        case NEXT_UNFINISHED_ONE_WITH_CENEIXH:
//                            yanChiBiaos = DBManager.getInstance().getNextUnfinishedChaoBiaoSJWithCeNeiXH(duRecordInfo.getTaskId(),
//                                    duRecordInfo.getVolume());
//                            break;
//                        case RE_UPDATING_ALL:
//                            yanChiBiaos = DBManager.getInstance().getAllFinishedChaoBiaoSJ(duRecordInfo.getTaskId(), duRecordInfo.getVolume(),
//                                    duRecordInfo.getAccount(), DURecord.CHAOBIAOBZ_YICHAO);
//                            break;
//                        case ALL_NO_CONDITION:
//                            yanChiBiaos = DBManager.getInstance().getAllChaoBiaoSJ(duRecordInfo.getAccount());
//                            break;
//                        case NOT_UPLOADED:
//                            yanChiBiaos = DBManager.getInstance().getChaoBiaoSJCBY(duRecordInfo.getAccount(),
//                                    duRecordInfo.getTaskId(),
//                                    duRecordInfo.getVolume(),
//                                    DURecord.CHAOBIAOBZ_YICHAO,
//                                    DURecord.SHANGCHUANBZ_WEISHANGC,
//                                    new Integer[]{DURecord.CHAOBIAOSJ, DURecord.NEWCHAOBIAOSJ});
//                            break;
//                        case EACH_VOLUME:
//                            yanChiBiaos = DBManager.getInstance().getAllChaoBiaoSJ(duRecordInfo.getAccount(),
//                                    duRecordInfo.getTaskId(), duRecordInfo.getVolume());
//                            break;
//                        case TEMPOPRARYDATA:
//                            yanChiBiaos = DBManager.getInstance().getTemporaryChaoBiaoSJ(duRecordInfo.getAccount());
//                            break;
//                        case ARREAR:
//                            yanChiBiaos = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
//                                    duRecordInfo.getVolume(), 6);
//                            break;
//                        case LOW_INCOME:
//                            yanChiBiaos = DBManager.getInstance().getListChaoBiaoSJ(duRecordInfo.getTaskId(),
//                                    duRecordInfo.getVolume(), 6);
//                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (yanChiBiaos != null) {
                        List<DUDelayRecord> duRecordList = new ArrayList<>();
                        for (YanChiBiao chaoBiaoSJ : yanChiBiaos) {
                            DUDelayRecord duRecord = yanChiBiao2DUDelayRecord(chaoBiaoSJ);
                            duRecordList.add(duRecord);
                        }

                        subscriber.onNext(duRecordList);
                    } else {
                        LogUtil.i(TAG, "---getRecords: chaoBiaoSJList is null---");
                        subscriber.onError(new Throwable("chaoBiaoSJList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get records
     *
     * @param info
     * @return
     */
    public Observable<List<DULgld>> getLglds(final DULgldInfo info) {
        return Observable.create(new Observable.OnSubscribe<List<DULgld>>() {
            @Override
            public void call(Subscriber<? super List<DULgld>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (info == null) {
                        throw new Exception("param is null");
                    }

                    List<YanChiBiao> yanChiBiaos;
                    List<ChaoBiaoSJ> chaoBiaoSJs;
                    String account = info.getAccount();
                    List<DULgld> duLglds = new ArrayList<>();
                    switch (info.getFilter()) {
                        case DULgldInfo.FILTER_RECORD_LG_INDEX:
                            chaoBiaoSJs = DBManager.getInstance().getLgldChaoBiaoSJ(account, ChaoBiaoSJDao.HIGHAMOUNT);
                            if (chaoBiaoSJs == null) {
                                LogUtil.i(TAG, "---getLglds: chaoBiaoSJList is null---");
                                subscriber.onError(new Throwable("chaoBiaoSJList is null"));
                                return;
                            }

                            for (ChaoBiaoSJ chaoBiaoSJ : chaoBiaoSJs) {
                                DULgld duRecord = chaoBiaoSJ2DULgld(chaoBiaoSJ);
                                duLglds.add(duRecord);
                            }

                            subscriber.onNext(duLglds);
                            return;
                        case DULgldInfo.FILTER_RECORD_Ld_INDEX:
                            chaoBiaoSJs = DBManager.getInstance().getLgldChaoBiaoSJ(account, ChaoBiaoSJDao.LOWAMOUNT);
                            if (chaoBiaoSJs == null) {
                                LogUtil.i(TAG, "---getLglds: chaoBiaoSJList is null---");
                                subscriber.onError(new Throwable("chaoBiaoSJList is null"));
                                return;
                            }

                            for (ChaoBiaoSJ chaoBiaoSJ : chaoBiaoSJs) {
                                DULgld duRecord = chaoBiaoSJ2DULgld(chaoBiaoSJ);
                                duLglds.add(duRecord);
                            }

                            subscriber.onNext(duLglds);
                            return;
                        case DULgldInfo.FILTER_DELAY_LG_INDEX:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.HIGHAMOUNT);
                            if (yanChiBiaos == null) {
                                LogUtil.i(TAG, "---getLglds: yanChiBiaos is null---");
                                subscriber.onError(new Throwable("yanChiBiaos is null"));
                                return;
                            }

                            for (YanChiBiao chaoBiaoSJ : yanChiBiaos) {
                                DULgld duRecord = yanChiBiao2DULgld(chaoBiaoSJ);
                                duLglds.add(duRecord);
                            }

                            subscriber.onNext(duLglds);
                            return;
                        case DULgldInfo.FILTER_DELAY_LD_INDEX:
                            yanChiBiaos = DBManager.getInstance().getListYanChiBiao(account, YanChiBiaoDao.LOWAMOUNT);
                            if (yanChiBiaos == null) {
                                LogUtil.i(TAG, "---getLglds: yanChiBiaos is null---");
                                subscriber.onError(new Throwable("yanChiBiaos is null"));
                                return;
                            }

                            for (YanChiBiao chaoBiaoSJ : yanChiBiaos) {
                                DULgld duRecord = yanChiBiao2DULgld(chaoBiaoSJ);
                                duLglds.add(duRecord);
                            }

                            subscriber.onNext(duLglds);
                            return;
                        default:
                            throw new Exception("filter type is error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get records
     *
     * @param duSamplingInfo
     * @return
     */
    public Observable<List<DUSampling>> getSamplings(final DUSamplingInfo duSamplingInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUSampling>>() {
            @Override
            public void call(Subscriber<? super List<DUSampling>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();
                    List<JiChaSJ> jiChaSJList = null;
                    List<String> userIds = null;
                    switch (duSamplingInfo.getFilterType()) {
                        case ALL:
                            jiChaSJList = DBManager.getInstance().getListJiChaSJ(duSamplingInfo.getTaskId(),
                                    6);
                            break;
                        case UNFINISHING:
                            jiChaSJList = DBManager.getInstance().getListJiChaSJ(duSamplingInfo.getTaskId(),
                                    0);
                            break;
                        case FINISHING:
                            jiChaSJList = DBManager.getInstance().getListJiChaSJ(duSamplingInfo.getTaskId(), 1);
                            break;
                        case SEARCH:
                            if (duSamplingInfo.getKey() != null) {
                                userIds = DBManager.getInstance().getBiaoKaXXCIDS(duSamplingInfo.getKey(), duSamplingInfo.getVolume());
                                if (userIds != null) {
                                    jiChaSJList = DBManager.getInstance().getJiChaSJList(duSamplingInfo.getTaskId(), userIds);
                                }
                            }
                            break;
                        case NEXT_UNFINISHED_ONE_WITH_CENEIXH:
                            jiChaSJList = DBManager.getInstance().getNextUnfinishedJiChaSJWithCeNeiXH(duSamplingInfo.getTaskId(),
                                    duSamplingInfo.getVolume());
                            break;
                        case RE_UPDATING_ALL:
                            jiChaSJList = DBManager.getInstance().getAllFinishedJiChaSJ(duSamplingInfo.getTaskId(), duSamplingInfo.getVolume(),
                                    duSamplingInfo.getAccount(), DURecord.CHAOBIAOBZ_YICHAO);
                            break;
                        case ALL_NO_CONDITION:
                            jiChaSJList = DBManager.getInstance().getAllJiChaSJ(duSamplingInfo.getAccount());
                            break;
                        case NOT_UPLOADED_SAMPLINGDATA:
                            jiChaSJList = DBManager.getInstance().getJiChaSJCBY(duSamplingInfo.getAccount(),
                                    duSamplingInfo.getTaskId(),
                                    duSamplingInfo.getVolume(),
                                    DURecord.CHAOBIAOBZ_YICHAO,
                                    DURecord.SHANGCHUANBZ_WEISHANGC);
                            break;
                        case EACH_VOLUME:
                            jiChaSJList = DBManager.getInstance().getAllJiChaSJ(duSamplingInfo.getAccount(), duSamplingInfo.getTaskId());
                            break;
//                        case ALL_LIMIT:
//                            chaoBiaoSJList = DBManager.getInstance().getAllChaoBiaoSJWithLimit(
//                                    duRecordInfo.getAccount(),
//                                    duRecordInfo.getTaskId(),
//                                    duRecordInfo.getVolume(),
//                                    duRecordInfo.getCeNeiXH());
//                            break;
//                        case UNFINISHING_LIMIT:
//                            userIds = DBManager.getInstance().getBiaoKaXXCIDS("%%", duRecordInfo.getVolume());
//                            if (userIds != null) {
//                                chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJWithLimit(
//                                        duRecordInfo.getTaskId(),
//                                        duRecordInfo.getVolume(),
//                                        0,
//                                        userIds,
//                                        duRecordInfo.getCeNeiXH());
//                            }
//                            break;
//                        case FINISHING_LIMIT:
//                            userIds = DBManager.getInstance().getBiaoKaXXCIDS("%%", duRecordInfo.getVolume());
//                            if (userIds != null) {
//                                chaoBiaoSJList = DBManager.getInstance().getListChaoBiaoSJWithLimit(
//                                        duRecordInfo.getTaskId(),
//                                        duRecordInfo.getVolume(),
//                                        1,
//                                        userIds,
//                                        duRecordInfo.getCeNeiXH());
//                            }
//                            break;
//                        case SEARCH_LIMIT:
//                            if (duRecordInfo.getKey() != null) {
//                                userIds = DBManager.getInstance().getBiaoKaXXCIDS(duRecordInfo.getKey(), duRecordInfo.getVolume());
//                                if (userIds != null) {
//                                    chaoBiaoSJList = DBManager.getInstance().getChaoBiaoSJListWithLimit(
//                                            duRecordInfo.getTaskId(),
//                                            duRecordInfo.getVolume(),
//                                            userIds,
//                                            duRecordInfo.getCeNeiXH());
//                                }
//                            }
//                            break;
//                        case TEMPOPRARYDATA:
//                            jiChaSJList = DBManager.getInstance().getTemporaryChaoBiaoSJ(duRecordInfo.getAccount());
//                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (jiChaSJList != null) {
                        List<DUSampling> duSamplingList = new ArrayList<>();
                        for (JiChaSJ jiChaSJ : jiChaSJList) {
                            DUSampling duSampling = jiChaSJ2DUSampling(jiChaSJ);
                            duSamplingList.add(duSampling);
                            if (duSamplingInfo.isLocked()) {
                                jiChaSJ.setI_ShangChuanBZ(DUSampling.SHANGCHUANBZ_ZHENGZAISC);
                                DBManager.getInstance().updateJiChaSJ(jiChaSJ);
                            }
                        }
                        subscriber.onNext(duSamplingList);
                    } else {
                        LogUtil.i(TAG, "---getSamplings: jiChaSJList is null---");
                        subscriber.onError(new Throwable("jiChaSJList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get a record
     *
     * @param duSamplingInfo
     * @return
     */
    public Observable<DUSampling> getSampling(final DUSamplingInfo duSamplingInfo) {
        return Observable.create(new Observable.OnSubscribe<DUSampling>() {
            @Override
            public void call(Subscriber<? super DUSampling> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    JiChaSJ jiChaSJ = null;
                    switch (duSamplingInfo.getFilterType()) {
                        case ONE:
                            jiChaSJ = DBManager.getInstance().getJiChaSJ(duSamplingInfo.getTaskId(),
                                    duSamplingInfo.getVolume(), duSamplingInfo.getCustomerId());
                            break;
                        case PREVIOUS_ONE:
                            jiChaSJ = DBManager.getInstance().getPreviousJiChaSJNew(duSamplingInfo.getAccount(), duSamplingInfo.getTaskId(),
                                    duSamplingInfo.getCustomerId(), false);
                            break;
                        case PREVIOUS_ONE_NOT_READING:
                            jiChaSJ = DBManager.getInstance().getPreviousJiChaSJNew(duSamplingInfo.getAccount(), duSamplingInfo.getTaskId(),
                                    duSamplingInfo.getCustomerId(), true);
                            break;
                        case NEXT_ONE:
                            jiChaSJ = DBManager.getInstance().getNextJiChaSJNew(duSamplingInfo.getAccount(), duSamplingInfo.getTaskId(),
                                    duSamplingInfo.getCustomerId(), false);
                            break;
                        case NEXT_ONE_NOT_READING:
                            jiChaSJ = DBManager.getInstance().getNextJiChaSJNew(duSamplingInfo.getAccount(), duSamplingInfo.getTaskId(),
                                    duSamplingInfo.getCustomerId(), true);
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (jiChaSJ != null) {
                        DUSampling duSampling = jiChaSJ2DUSampling(jiChaSJ);
                        subscriber.onNext(duSampling);
                    } else {
                        LogUtil.i(TAG, "---getSampling: jiChaSJ is null---");
                        subscriber.onError(new Throwable("jiChaSJ is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get a RushPayTask
     *
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<DURushPayTask> getRushPayTask(final DURushPayTaskInfo duRushPayTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<DURushPayTask>() {
            @Override
            public void call(Subscriber<? super DURushPayTask> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    RushPayRW rushPayRW = null;
                    switch (duRushPayTaskInfo.getFilterType()) {
                        case ONE:
                            rushPayRW = DBManager.getInstance().getRushPayRW(duRushPayTaskInfo.getAccount(),
                                    duRushPayTaskInfo.getTaskId());
                            break;
                        case PREVIOUS_ONE:
                            rushPayRW = DBManager.getInstance().getPreviousRushPayTask(duRushPayTaskInfo.getAccount(),
                                    duRushPayTaskInfo.getTaskId());
                            break;
                        case NEXT_ONE:
                            rushPayRW = DBManager.getInstance().getNextRushPayTask(duRushPayTaskInfo.getAccount(),
                                    duRushPayTaskInfo.getTaskId());
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (rushPayRW != null) {
                        DURushPayTask duRushPayTask = rushPayRW2DURushPayTask(rushPayRW);
                        subscriber.onNext(duRushPayTask);
                    } else {
                        LogUtil.i(TAG, "---getRushPayTask: rushPayRW is null---");
                        subscriber.onError(new Throwable("rushPayRW is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * get a JiChaTask
     *
     * @param duSamplingTaskInfo
     * @return
     */
    public Observable<DUSamplingTask> getSamplingTask(final DUSamplingTaskInfo duSamplingTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<DUSamplingTask>() {
            @Override
            public void call(Subscriber<? super DUSamplingTask> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duSamplingTaskInfo == null) || TextUtil.isNullOrEmpty(duSamplingTaskInfo.getAccount())
                    ) {
                        throw new NullPointerException("duSamplingTaskInfo contains null parameter");
                    }

                    String account = duSamplingTaskInfo.getAccount();
                    int taskId = duSamplingTaskInfo.getTaskId();
                    JiChaRW jiChaRW = DBManager.getInstance().getJiChaRW(account,
                            taskId);
                    if (jiChaRW != null) {
                        DUSamplingTask duSamplingTask = jiChaRW2DUSamplingTask(jiChaRW);
                        subscriber.onNext(duSamplingTask);
                    } else {
                        LogUtil.i(TAG, "---getSamplingTask: jiChaRW is null---");
                        subscriber.onError(new Throwable("jiChaRW is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Integer> getChaoJianS(final String account,
                                            final int taskId,
                                            final String volume,
                                            final String chaoJianZTS) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (TextUtil.isNullOrEmpty(account)
                            || TextUtil.isNullOrEmpty(volume)
                            || TextUtil.isNullOrEmpty(chaoJianZTS)) {
                        throw new Exception("param is null");
                    }

                    subscriber.onNext(DBManager.getInstance().getChaoJianS(account, taskId,
                            volume, chaoJianZTS));
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get a record
     *
     * @param duRecordInfo
     * @return
     */
    public Observable<DURecord> getRecord(final DURecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<DURecord>() {
            @Override
            public void call(Subscriber<? super DURecord> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    ChaoBiaoSJ chaoBiaoSJ = null;
                    switch (duRecordInfo.getFilterType()) {
                        case ONE:
                            chaoBiaoSJ = DBManager.getInstance().getChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), duRecordInfo.getCustomerId());
                            break;
                        case PREVIOUS_ONE:
                            chaoBiaoSJ = DBManager.getInstance().getPreviousChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), duRecordInfo.getOrderNumber(), false);
                            break;
                        case PREVIOUS_ONE_NOT_READING:
                            chaoBiaoSJ = DBManager.getInstance().getPreviousChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), duRecordInfo.getOrderNumber(), true);
                            break;
                        case NEXT_ONE:
                            chaoBiaoSJ = DBManager.getInstance().getNextChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), duRecordInfo.getOrderNumber(), false);
                            break;
                        case NEXT_ONE_NOT_READING:
                            chaoBiaoSJ = DBManager.getInstance().getNextChaoBiaoSJ(duRecordInfo.getTaskId(),
                                    duRecordInfo.getVolume(), duRecordInfo.getOrderNumber(), true);
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (chaoBiaoSJ != null) {
                        DURecord duRecord = chaoBiaoSJ2DURecord(chaoBiaoSJ);
                        subscriber.onNext(duRecord);
                    } else {
                        LogUtil.i(TAG, "---getRecord: chaoBiaoSJ is null---");
                        subscriber.onError(new Throwable("chaoBiaoSJ is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUDelayRecord> getDelayRecord(final DUDelayRecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<DUDelayRecord>() {
            @Override
            public void call(Subscriber<? super DUDelayRecord> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    YanChiBiao delayRecord = null;
                    switch (duRecordInfo.getFilterType()) {
                        case ONE:
                            delayRecord = DBManager.getInstance().getYanChiBiaoXX(duRecordInfo.getRenWuId(),
                                    duRecordInfo.getCh(), duRecordInfo.getCustomerId());
                            break;
                        case PREVIOUS_ONE:
                            delayRecord = DBManager.getInstance().getPreviousYanChiCB(duRecordInfo.getRenWuId(),
                                    duRecordInfo.getCh(), duRecordInfo.getCeNeiXH(), false);
                            break;
                        case PREVIOUS_ONE_NOT_READING:
                            delayRecord = DBManager.getInstance().getPreviousYanChiCB(duRecordInfo.getRenWuId(),
                                    duRecordInfo.getCh(), duRecordInfo.getCeNeiXH(), true);
                            break;
                        case NEXT_ONE:
                            delayRecord = DBManager.getInstance().getNextYanChiCB(duRecordInfo.getRenWuId(),
                                    duRecordInfo.getCh(), duRecordInfo.getCeNeiXH(), false);
                            break;
                        case NEXT_ONE_NOT_READING:
                            delayRecord = DBManager.getInstance().getNextYanChiCB(duRecordInfo.getRenWuId(),
                                    duRecordInfo.getCh(), duRecordInfo.getCeNeiXH(), true);
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (delayRecord != null) {
                        DUDelayRecord duRecord = yanChiBiao2DUDelayRecord(delayRecord);
                        subscriber.onNext(duRecord);
                    } else {
                        LogUtil.i(TAG, "---getRecord: chaoBiaoSJ is null---");
                        subscriber.onError(new Throwable("chaoBiaoSJ is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<com.sh3h.dataprovider.entity.JianHaoMX>> getJianHaoMXByJH(final String S_JH) {
        return Observable.create(new Observable.OnSubscribe<List<com.sh3h.dataprovider.entity.JianHaoMX>>() {
            @Override
            public void call(Subscriber<? super List<com.sh3h.dataprovider.entity.JianHaoMX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();
                    if (TextUtil.isNullOrEmpty(S_JH)) {
                        throw new Exception("filter type is error");
                    }

                    List<JianHaoMX> list = DBManager.getInstance().getJianHaoMXByJH(S_JH);
                    if (list != null && list.size() > 0) {
                        List<com.sh3h.dataprovider.entity.JianHaoMX> jianHaos = new ArrayList<>();
                        for (JianHaoMX jianHaoMX : list) {
                            jianHaos.add(JianHaoMX2JianHaoMX(jianHaoMX));
                        }

                        subscriber.onNext(jianHaos);
                    } else {
                        LogUtil.i(TAG, "---getJianHaoMXByJH: JianHaoMX is null---");
                        subscriber.onError(new Throwable("JianHaoMX is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * save records
     *
     * @param duCardList
     * @return
     */
    public boolean saveCards(List<DUCard> duCardList, boolean needDeletingFirstly) {
        try {
            init();

            if (duCardList != null) {
                List<BIAOKAXX> biaokaxxList = new ArrayList<>();
                for (DUCard duCard : duCardList) {
                    BIAOKAXX biaokaxx = duCard2BiaoKaXX(duCard);
                    if (needDeletingFirstly) {
                        //DBManager.getInstance().deleteBiaoKaXX(biaokaxx.getS_CID());
                        if (DBManager.getInstance().getBiaoKaXX(biaokaxx.getS_CID()) == null) {
                            biaokaxxList.add(biaokaxx);
                        }
                    } else {
                        biaokaxxList.add(biaokaxx);
                    }
                }

                long t = System.currentTimeMillis();
                DBManager.getInstance().insertOrUpdateBiaoKaXXList(biaokaxxList);
                t = System.currentTimeMillis() - t;
                LogUtil.i(TAG, "---saveCards---" + t);

                return true;
            } else {
                LogUtil.i(TAG, "---saveCards: duCardList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveRecords---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * save waifucards
     *
     * @param duCardList
     * @return
     */
    public boolean saveWaiFuCards(List<DUCard> duCardList, boolean needDeletingFirstly) {
        try {
            init();

            if (duCardList != null) {
                List<BIAOKAXX> biaokaxxList = new ArrayList<>();
                for (DUCard duCard : duCardList) {
                    BIAOKAXX biaokaxx = duCard2BiaoKaXX(duCard);
                    if (needDeletingFirstly) {
                        //DBManager.getInstance().deleteBiaoKaXX(biaokaxx.getS_CID());
                        if (DBManager.getInstance().getBiaoKaXX(biaokaxx.getS_CID()) == null) {
                            biaokaxxList.add(biaokaxx);
                        }
                    } else {
                        biaokaxxList.add(biaokaxx);
                    }
                }

                long t = System.currentTimeMillis();
                DBManager.getInstance().insertOrUpdateBiaoKaXXList(biaokaxxList);
                t = System.currentTimeMillis() - t;
                LogUtil.i(TAG, "---saveWaiFugCards---" + t);

                return true;
            } else {
                LogUtil.i(TAG, "---saveWaiFugCards: duCardList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveWaiFugCards---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * save records
     *
     * @param duCardList
     * @return
     */
    public boolean saveSamplingCards(List<DUCard> duCardList, boolean needDeletingFirstly) {
        try {
            init();

            if (duCardList != null) {
                List<BIAOKAXX> biaokaxxList = new ArrayList<>();
                for (DUCard duCard : duCardList) {
                    BIAOKAXX biaokaxx = duCard2BiaoKaXX(duCard);
                    if (needDeletingFirstly) {
                        //DBManager.getInstance().deleteBiaoKaXX(biaokaxx.getS_CID());
                        if (DBManager.getInstance().getBiaoKaXX(biaokaxx.getS_CID()) == null) {
                            biaokaxxList.add(biaokaxx);
                        }
                    } else {
                        biaokaxxList.add(biaokaxx);
                    }
                }

                long t = System.currentTimeMillis();
                DBManager.getInstance().insertOrUpdateBiaoKaXXList(biaokaxxList);
                t = System.currentTimeMillis() - t;
                LogUtil.i(TAG, "---saveSamplingCards---" + t);

                return true;
            } else {
                LogUtil.i(TAG, "---saveSamplingCards: duCardList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveSamplingCards---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * get cards
     *
     * @param duCardInfo
     * @return
     */
    public Observable<List<DUCard>> getCards(final DUCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUCard>>() {
            @Override
            public void call(Subscriber<? super List<DUCard>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    List<BIAOKAXX> biaoKaXXList = null;
                    switch (duCardInfo.getFilterType()) {
                        case ALL_NO_CONDITION:
                            biaoKaXXList = DBManager.getInstance().getAllBiaoKaXXList();
                            break;
                        case SEARCHING_ALL:
                            biaoKaXXList = DBManager.getInstance().getBiaoKaXXList(duCardInfo.getVolume());
                            break;
                        case TEMPORARY:
                            biaoKaXXList = DBManager.getInstance().getBiaoKaXXTemporaryList(duCardInfo.getAccount());
                            break;
                        case WAIFU:
                            biaoKaXXList = DBManager.getInstance().getBiaoKaXXWaiFuCBSJList(duCardInfo.getAccount());
                            break;
                        case SAMPLING:
                            biaoKaXXList = DBManager.getInstance().getBiaoKaXXListByTaskId(duCardInfo.getTaskId());
                            break;
                        case DELAY:
                            biaoKaXXList = DBManager.getInstance().getDelayBiaoKaXXList(duCardInfo.getAccount());
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (biaoKaXXList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        for (BIAOKAXX biaokaxx : biaoKaXXList) {
                            DUCard duCard = biaoKaXX2DUCard(biaokaxx);
                            duCardList.add(duCard);
                        }
                        subscriber.onNext(duCardList);
                    } else {
                        LogUtil.i(TAG, "---getCards: biaoKaXXList is null---");
                        subscriber.onError(new Throwable("biaoKaXXList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get sampling cards
     *
     * @param duCardInfo
     * @return
     */
    public Observable<List<DUCard>> getSamplingCards(final DUCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUCard>>() {
            @Override
            public void call(Subscriber<? super List<DUCard>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    List<BIAOKAXX> biaoKaXXList = null;
                    switch (duCardInfo.getFilterType()) {
                        case ALL_NO_CONDITION:
                            biaoKaXXList = DBManager.getInstance().getAllBiaoKaXXList();
                            break;
                        case SEARCHING_ALL:
                            biaoKaXXList = DBManager.getInstance().getBiaoKaXXListByGroupId(duCardInfo.getGroupId());
                            break;
                        case TEMPORARY:
                            biaoKaXXList = DBManager.getInstance().getBiaoKaXXTemporaryList(duCardInfo.getAccount());
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (biaoKaXXList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        for (BIAOKAXX biaokaxx : biaoKaXXList) {
                            DUCard duCard = biaoKaXX2DUCard(biaokaxx);
                            duCardList.add(duCard);
                        }
                        subscriber.onNext(duCardList);
                    } else {
                        LogUtil.i(TAG, "---getCards: biaoKaXXList is null---");
                        subscriber.onError(new Throwable("biaoKaXXList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUCardResult> getCardResults(final DUCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<DUCardResult>() {
            @Override
            public void call(Subscriber<? super DUCardResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    List<BIAOKAXX> biaoKaXXList = null;
                    switch (duCardInfo.getFilterType()) {
                        case SEARCHING_ALL:
                            biaoKaXXList = DBManager.getInstance().getBiaoKaXXList(duCardInfo.getVolume());
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (biaoKaXXList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        for (BIAOKAXX biaokaxx : biaoKaXXList) {
                            DUCard duCard = biaoKaXX2DUCard(biaokaxx);
                            duCardList.add(duCard);
                        }

                        DUCardResult duCardResult = new DUCardResult(
                                DUCardResult.FilterType.UPLOADING,
                                duCardInfo.getTaskId(),
                                duCardInfo.getVolume(),
                                duCardList);
                        subscriber.onNext(duCardResult);
                    } else {
                        LogUtil.i(TAG, "---getCards: biaoKaXXList is null---");
                        subscriber.onError(new Throwable("biaoKaXXList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get a card
     *
     * @param duCardInfo
     * @return
     */
    public Observable<DUCard> getCard(final DUCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<DUCard>() {
            @Override
            public void call(Subscriber<? super DUCard> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    BIAOKAXX biaokaxx = null;
                    switch (duCardInfo.getFilterType()) {
                        case SEARCHING_ONE:
                            biaokaxx = DBManager.getInstance().getBiaoKaXX(duCardInfo.getCustomerId());
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (biaokaxx != null) {
                        DUCard duCard = biaoKaXX2DUCard(biaokaxx);
                        subscriber.onNext(duCard);
                    } else {
                        LogUtil.i(TAG, "---getCard: biaokaxx is null---");
                        subscriber.onError(new Throwable("biaokaxx is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * get a card
     *
     * @param duCardInfo
     * @return
     */
    public Observable<DUCard> getSamplingCard(final DUCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<DUCard>() {
            @Override
            public void call(Subscriber<? super DUCard> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    BIAOKAXX biaokaxx = null;
                    switch (duCardInfo.getFilterType()) {
                        case SEARCHING_ONE:
                            biaokaxx = DBManager.getInstance().getBiaoKaXX(duCardInfo.getCustomerId());
                            break;
                        default:
                            throw new Exception("filter type is error");
                    }

                    if (biaokaxx != null) {
                        DUCard duCard = biaoKaXX2DUCard(biaokaxx);
                        subscriber.onNext(duCard);
                    } else {
                        LogUtil.i(TAG, "---getCard: biaokaxx is null---");
                        subscriber.onError(new Throwable("biaokaxx is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param duCardList
     * @return
     */
    public Observable<DUCardResult> updateCards(final List<DUCard> duCardList) {
        return Observable.create(new Observable.OnSubscribe<DUCardResult>() {
            @Override
            public void call(Subscriber<? super DUCardResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duCardList == null) {
                        throw new NullPointerException("duCardList is null");
                    } else if (duCardList.size() <= 0) {
                        throw new Exception("size <= 0");
                    }

                    int successCount = 0;
                    String volume = null;
                    List<BIAOKAXX> biaokaxxList = new ArrayList<>();
                    for (DUCard duCard : duCardList) {
                        volume = duCard.getCh();
                        BIAOKAXX biaokaxx = duCard2BiaoKaXX(duCard);
                        biaokaxxList.add(biaokaxx);
                        successCount++;
                    }

                    if ((successCount <= 0) || TextUtil.isNullOrEmpty(volume)) {
                        subscriber.onError(new Throwable("result is error"));
                        return;
                    }

                    if (DBManager.getInstance().updateBiaoKaXXList(biaokaxxList)) {
                        DUCardResult duCardResult = new DUCardResult(
                                DUCardResult.FilterType.UPDATING,
                                volume,
                                successCount,
                                0);
                        subscriber.onNext(duCardResult);
                    } else {
                        subscriber.onError(new Throwable("updateBiaoKaXXList is error"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param duCard
     * @return
     */
    public Observable<Boolean> updateOneCard(final DUCard duCard) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duCard == null) {
                        throw new NullPointerException("duCard is null");
                    }

                    BIAOKAXX biaokaxx = duCard2BiaoKaXX(duCard);
                    subscriber.onNext(DBManager.getInstance().updateBiaoKaXX(biaokaxx));

                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 根据条件从本地获取抄表记录
     *
     * @param duChaoBiaoJLInfo
     * @return
     */
    public Observable<List<DUChaoBiaoJL>> getChaoBiaoJLs(final DUChaoBiaoJLInfo duChaoBiaoJLInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUChaoBiaoJL>>() {
            @Override
            public void call(Subscriber<? super List<DUChaoBiaoJL>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duChaoBiaoJLInfo == null ||
                            duChaoBiaoJLInfo.getCustomerId() == null) {
                        throw new NullPointerException("duChaoBiaoJLInfo contain null pointer");
                    }

                    List<ChaoBiaoJL> chaoBiaoJLList = DBManager.getInstance().getListChaoBiaoJL(duChaoBiaoJLInfo.getCustomerId());
                    if (chaoBiaoJLList != null) {
                        List<DUChaoBiaoJL> duChaoBiaoJLList = new ArrayList<DUChaoBiaoJL>();
                        for (ChaoBiaoJL chaoBiaoJL : chaoBiaoJLList) {
                            DUChaoBiaoJL duChaoBiaoJL = new DUChaoBiaoJL(
                                    chaoBiaoJL.getID(),
                                    chaoBiaoJL.getS_CID(),
                                    chaoBiaoJL.getI_CHAOBIAON(),
                                    chaoBiaoJL.getI_CHAOBIAOY(),
                                    chaoBiaoJL.getI_ChaoCi(),
                                    chaoBiaoJL.getD_ChaoBiaoRQ(),
                                    chaoBiaoJL.getI_ShangCiCM(),
                                    chaoBiaoJL.getI_BENCICM(),
                                    chaoBiaoJL.getI_CHAOJIANSL(),
                                    chaoBiaoJL.getS_CHAOBIAOZT(),
                                    chaoBiaoJL.getS_ChaoBiaoY(),
                                    chaoBiaoJL.getS_CHAOBIAOBZ(),
                                    chaoBiaoJL.getI_CHAOBIAOZTBM(),
                                    chaoBiaoJL.getI_LIANGGAOLDYYBM()
                            );
                            duChaoBiaoJLList.add(duChaoBiaoJL);
                        }

                        subscriber.onNext(duChaoBiaoJLList);
                    } else {
                        LogUtil.i(TAG, "---getChaoBiaoJLs: chaoBiaoJLList is null---");
                        subscriber.onError(new Throwable("chaoBiaoJLList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 保存抄表记录至本地
     *
     * @param duChaoBiaoJLList
     */

    public boolean saveChaoBiaoJLs(List<DUChaoBiaoJL> duChaoBiaoJLList) {
        try {
            init();

            if (duChaoBiaoJLList != null) {
                List<ChaoBiaoJL> chaoBiaoJLList = new ArrayList<>();
                for (DUChaoBiaoJL duChaoBiaoJL : duChaoBiaoJLList) {
                    ChaoBiaoJL chaoBiaoJL = new ChaoBiaoJL(
                            -1,
                            duChaoBiaoJL.getCid(),
                            duChaoBiaoJL.getChaobiaon(),
                            duChaoBiaoJL.getIchaobiaoy(),
                            duChaoBiaoJL.getChaoci(),
                            duChaoBiaoJL.getChaobiaorq(),
                            duChaoBiaoJL.getShangcicm(),
                            duChaoBiaoJL.getBencicm(),
                            duChaoBiaoJL.getChaojiansl(),
                            duChaoBiaoJL.getChaobiaozt(),
                            duChaoBiaoJL.getSchaobiaoy(),
                            duChaoBiaoJL.getChaobiaobz(),
                            duChaoBiaoJL.getChaobiaoztbm(),
                            duChaoBiaoJL.getLianggaoldyybm());
                    chaoBiaoJLList.add(chaoBiaoJL);

                    DBManager.getInstance().clearChaoBiaoJL(duChaoBiaoJL.getCid());
                }

                DBManager.getInstance().insertChaoBiaoJLList(chaoBiaoJLList);

                return true;
            } else {
                LogUtil.i(TAG, "---saveChaoBiaoJLs: duChaoBiaoJLList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveChaoBiaoJLs---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * 根据条件从本地获取缴费信息
     *
     * @param duJiaoFeiXXInfo
     * @return
     */
    public Observable<List<DUJiaoFeiXX>> getJiaoFeiXXs(final DUJiaoFeiXXInfo duJiaoFeiXXInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUJiaoFeiXX>>() {
            @Override
            public void call(Subscriber<? super List<DUJiaoFeiXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duJiaoFeiXXInfo == null ||
                            duJiaoFeiXXInfo.getCustomerId() == null) {
                        throw new NullPointerException("duJiaoFeiXXInfo contain null pointer");
                    }

                    List<JiaoFeiXX> jiaoFeiXXList = DBManager.getInstance().getListJiaoFeiXX(duJiaoFeiXXInfo.getCustomerId());
                    if (jiaoFeiXXList != null) {
                        List<DUJiaoFeiXX> duJiaoFeiXXList = new ArrayList<DUJiaoFeiXX>();
                        for (JiaoFeiXX jiaoFeiXX : jiaoFeiXXList) {
                            DUJiaoFeiXX duJiaoFeiXX = new DUJiaoFeiXX(
                                    jiaoFeiXX.getS_CID(),
                                    jiaoFeiXX.getI_ZhangWuNY(),
                                    jiaoFeiXX.getI_FEEID(),
                                    jiaoFeiXX.getD_ChaoBiaoRQ(),
                                    jiaoFeiXX.getD_KAIZHANGRQ(),
                                    jiaoFeiXX.getD_SHOUFEIRQ(),
                                    jiaoFeiXX.getN_JE(),
                                    jiaoFeiXX.getN_SHISHOUWYJ(),
                                    jiaoFeiXX.getN_SHISHOUZJE(),
                                    jiaoFeiXX.getS_SHOUFEITJ()
                            );

                            duJiaoFeiXXList.add(duJiaoFeiXX);
                        }

                        subscriber.onNext(duJiaoFeiXXList);
                    } else {
                        LogUtil.i(TAG, "---getListJiaoFeiXX: jiaoFeiXXList is null---");
                        subscriber.onError(new Throwable("jiaoFeiXXList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 保存缴费信息至本地
     *
     * @param duJiaoFeiXXList
     */
    public boolean saveJiaoFeiXXs(List<DUJiaoFeiXX> duJiaoFeiXXList) {
        try {
            init();

            if (duJiaoFeiXXList != null) {
                List<JiaoFeiXX> jiaoFeiXXList = new ArrayList<>();
                for (DUJiaoFeiXX duJiaoFeiXX : duJiaoFeiXXList) {
                    JiaoFeiXX jiaoFeiXX = new JiaoFeiXX(
                            duJiaoFeiXX.getCid(),
                            duJiaoFeiXX.getZhangwuny(),
                            duJiaoFeiXX.getFeeid(),
                            duJiaoFeiXX.getChaobiaorq(),
                            duJiaoFeiXX.getKaizhangrq(),
                            duJiaoFeiXX.getShoufeirq(),
                            duJiaoFeiXX.getJe(),
                            duJiaoFeiXX.getShishouwyj(),
                            duJiaoFeiXX.getShishouzje(),
                            duJiaoFeiXX.getShoufeitj());
                    jiaoFeiXXList.add(jiaoFeiXX);

                    DBManager.getInstance().clearJiaoFeiXX(duJiaoFeiXX.getCid());
                }

                DBManager.getInstance().insertJiaoFeiXXs(jiaoFeiXXList);
                return true;
            } else {
                LogUtil.i(TAG, "---saveJiaoFeiXXs: duChaoBiaoJLList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveJiaoFeiXXs---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * 根据条件从本地获取欠费信息
     *
     * @param duQianFeiXXInfo
     * @return
     */
    public Observable<List<DUQianFeiXX>> getQianFeiXXs(final DUQianFeiXXInfo duQianFeiXXInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUQianFeiXX>>() {
            @Override
            public void call(Subscriber<? super List<DUQianFeiXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duQianFeiXXInfo == null ||
                            duQianFeiXXInfo.getCustomerId() == null) {
                        throw new NullPointerException("duQianFeiXXInfo contains null pointer");
                    }

                    List<QianFeiXX> qianFeiXXList = DBManager.getInstance().getListQianFeiXX(duQianFeiXXInfo.getCustomerId());
                    if (qianFeiXXList != null) {
                        List<DUQianFeiXX> duQianFeiXXList = new ArrayList<DUQianFeiXX>();
                        for (QianFeiXX qianFeiXX : qianFeiXXList) {
                            DUQianFeiXX duQianFeiXX = new DUQianFeiXX(
                                    qianFeiXX.getS_CH(),
                                    qianFeiXX.getS_CID(),
                                    qianFeiXX.getI_CHAOBIAON(),
                                    qianFeiXX.getI_CHAOBIAOY(),
                                    qianFeiXX.getI_ZhangWuNY(),
                                    qianFeiXX.getD_ChaoBiaoRQ(),
                                    qianFeiXX.getI_CHAOCI(),
                                    qianFeiXX.getI_KAIZHANGSL(),
                                    qianFeiXX.getN_JE(),
                                    qianFeiXX.getI_FEEID(),
                                    qianFeiXX.getN_YINGSHOUWYJ(),
                                    qianFeiXX.getN_SHUIFEI(),
                                    qianFeiXX.getN_PAISHUIF()
                            );

                            duQianFeiXXList.add(duQianFeiXX);
                        }

                        subscriber.onNext(duQianFeiXXList);
                    } else {
                        LogUtil.i(TAG, "---getQianFeiXXs: qianFeiXXList is null---");
                        subscriber.onError(new Throwable("qianFeiXXList is null"));
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 将欠费信息保存至本地
     *
     * @param duQianFeiXXList
     */
    public boolean saveQianFeiXXs(List<DUQianFeiXX> duQianFeiXXList) {
        try {
            init();

            if (duQianFeiXXList != null) {
                List<QianFeiXX> qianFeiXXList = new ArrayList<>();
                List<String> deleteCids = new ArrayList<>();
                String cid;
                for (DUQianFeiXX duQianFeiXX : duQianFeiXXList) {
                    QianFeiXX qianFeiXX = new QianFeiXX(
                            duQianFeiXX.getCh(),
                            duQianFeiXX.getCid(),
                            duQianFeiXX.getChaobiaon(),
                            duQianFeiXX.getChaobiaoy(),
                            duQianFeiXX.getZhangwuny(),
                            duQianFeiXX.getChaobiaorq(),
                            duQianFeiXX.getChaoci(),
                            duQianFeiXX.getKaizhangsl(),
                            duQianFeiXX.getJe(),
                            duQianFeiXX.getFeeid(),
                            duQianFeiXX.getYingshouwyj(),
                            duQianFeiXX.getShuifei(),
                            duQianFeiXX.getPaishuif());
                    qianFeiXXList.add(qianFeiXX);

                    cid = qianFeiXX.getS_CID();
                    if (!deleteCids.contains(cid)) {
                        deleteCids.add(cid);
                    }
                }

                DBManager.getInstance().clearQianFeiXX(deleteCids);
                DBManager.getInstance().insertQianFeiXXs(qianFeiXXList);
                return true;
            } else {
                LogUtil.i(TAG, "---saveQianFeiXXs: duQianFeiXXList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveQianFeiXXs---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * 根据条件从本地加载换表信息
     *
     * @param duHuanBiaoJLInfo
     * @return
     */
    public Observable<List<DUHuanBiaoJL>> getHuanBiaoXXs(final DUHuanBiaoJLInfo duHuanBiaoJLInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUHuanBiaoJL>>() {
            @Override
            public void call(Subscriber<? super List<DUHuanBiaoJL>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (duHuanBiaoJLInfo == null ||
                            duHuanBiaoJLInfo.getCustomerId() == null) {
                        throw new NullPointerException("duHuanBiaoJLInfo contains null pointer");
                    }

                    List<HuanBiaoJL> huanBiaoJLList = DBManager.getInstance().getHuanBiaoJLList(duHuanBiaoJLInfo.getCustomerId());
                    if (huanBiaoJLList != null) {
                        List<DUHuanBiaoJL> duHuanBiaoJLList = new ArrayList<DUHuanBiaoJL>();
                        for (HuanBiaoJL huanBiaoJL : huanBiaoJLList) {
                            DUHuanBiaoJL duHuanBiaoJL = new DUHuanBiaoJL(
                                    huanBiaoJL.getS_CID(),
                                    huanBiaoJL.getS_BiaoWeiXX(),
                                    huanBiaoJL.getS_DengJiR(),
                                    huanBiaoJL.getD_DengJiRQ(),
                                    huanBiaoJL.getS_HuanBiaoLX(),
                                    huanBiaoJL.getS_HuanBiaoYY(),
                                    huanBiaoJL.getS_JiubiaoBH(),
                                    huanBiaoJL.getS_JiuBiaoGYH(),
                                    huanBiaoJL.getS_JiuBiaoCJ(),
                                    huanBiaoJL.getS_XinBiaoBH(),
                                    huanBiaoJL.getS_XinBiaoGYH(),
                                    huanBiaoJL.getS_XinBiaoCJ(),
                                    huanBiaoJL.getS_JiuBiaoXH(),
                                    huanBiaoJL.getS_XinBiaoXH(),
                                    huanBiaoJL.getS_JiuBiaoKJ(),
                                    huanBiaoJL.getS_XinBiaoKJ(),
                                    huanBiaoJL.getI_XinBiaoLC(),
                                    huanBiaoJL.getI_ShangCiCM(),
                                    huanBiaoJL.getI_JiuBiaoCM(),
                                    huanBiaoJL.getI_XinBiaoDM(),
                                    huanBiaoJL.getD_ShiGongRQ(),
                                    huanBiaoJL.getS_HuiTianR(),
                                    huanBiaoJL.getD_HuanBiaoHTRQ(),
                                    huanBiaoJL.getS_HuanBiaoZT(),
                                    huanBiaoJL.getS_BeiZhu()
                            );
                            duHuanBiaoJLList.add(duHuanBiaoJL);
                        }
                        subscriber.onNext(duHuanBiaoJLList);
                    } else {
                        LogUtil.i(TAG, "---getHuanBiaoXXs: huanBiaoJLList is null---");
                        subscriber.onError(new Throwable("huanBiaoJLList is null"));
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 将换表信息保存至本地
     *
     * @param duHuanBiaoJLList
     */
    public boolean saveHuanBiaoXXs(List<DUHuanBiaoJL> duHuanBiaoJLList) {
        try {
            init();

            if (duHuanBiaoJLList != null) {
                List<HuanBiaoJL> huanBiaoJLList = new ArrayList<>();
                for (DUHuanBiaoJL duHuanBiaoJL : duHuanBiaoJLList) {
                    HuanBiaoJL huanBiaoJL = new HuanBiaoJL(
                            duHuanBiaoJL.getCid(),
                            duHuanBiaoJL.getBiaoweixx(),
                            duHuanBiaoJL.getDengjir(),
                            duHuanBiaoJL.getDengjirq(),
                            duHuanBiaoJL.getHuanbiaolx(),
                            duHuanBiaoJL.getHuanbiaoyy(),
                            duHuanBiaoJL.getJiubiaobh(),
                            duHuanBiaoJL.getJiubiaogyh(),
                            duHuanBiaoJL.getJiubiaocj(),
                            duHuanBiaoJL.getXinbiaobh(),
                            duHuanBiaoJL.getXinbiaogyh(),
                            duHuanBiaoJL.getXinbiaocj(),
                            duHuanBiaoJL.getJiubiaoxh(),
                            duHuanBiaoJL.getXinbiaoxh(),
                            duHuanBiaoJL.getJiubiaokj(),
                            duHuanBiaoJL.getXinbiaokj(),
                            duHuanBiaoJL.getXinbiaolc(),
                            duHuanBiaoJL.getShangcicm(),
                            duHuanBiaoJL.getJiubiaocm(),
                            duHuanBiaoJL.getXinbiaodm(),
                            duHuanBiaoJL.getShigongrq(),
                            duHuanBiaoJL.getHuitianr(),
                            duHuanBiaoJL.getHuanbiaohtrq(),
                            duHuanBiaoJL.getHuanbiaozt(),
                            duHuanBiaoJL.getBeizhu());
                    huanBiaoJLList.add(huanBiaoJL);

                    DBManager.getInstance().clearHuanBiaoJL(duHuanBiaoJL.getCid());
                }

                DBManager.getInstance().insertHuanBiaoJLs(huanBiaoJLList);
                return true;
            } else {
                LogUtil.i(TAG, "---saveHuanBiaoXXs: duHuanBiaoJLList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveHuanBiaoXXs---%s", e.getMessage()));
        }

        return false;
    }

//    /**
//     * insert words
//     * @param duWordsInfo
//     * @return
//     */
//    public Observable<DUWordsResult> insertWords(final DUWordsInfo duWordsInfo) {
//        return Observable.create(new Observable.OnSubscribe<DUWordsResult>() {
//            @Override
//            public void call(Subscriber<? super DUWordsResult> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//
//                try {
//                    init();
//
//                    if (duWordsInfo == null ||
//                            duWordsInfo.getDuEntityList() == null) {
//                        throw new NullPointerException("duWordsInfo contains null pointer");
//                    }
//
//                    List<IDUEntity> iduEntityList = duWordsInfo.getDuEntityList();
//                    for (IDUEntity duEntity : iduEntityList) {
//                        if (duWordsInfo.getXmlPath().contains(DUWordsInfo.XMLCBChaoBiaoZT)) {
//                            DBManager.getInstance().insertChaoBiaoZT(duEntity2DbEntity((DUChaoBiaoZT) duEntity));
//                        } else if (duWordsInfo.getXmlPath().contains(DUWordsInfo.XMLCBChaoBiaoZTFL)) {
//                            DBManager.getInstance().insertChaoBiaoZTFL(duEntity2DbEntity((DUChaoBiaoZTFL) duEntity));
//                        } else if (duWordsInfo.getXmlPath().contains(DUWordsInfo.XMLCYCiYuXX)) {
//                            DBManager.getInstance().insertCiYu(duEntity2DbEntity((DUCiYuXX) duEntity));
//                        } else if (duWordsInfo.getXmlPath().contains(DUWordsInfo.XMLJGDingEJJBL)) {
//                            DBManager.getInstance().insertDingEJJBL(duEntity2DbEntity((DUDingEJJBL) duEntity));
//                        } else if (duWordsInfo.getXmlPath().contains(DUWordsInfo.XMLJGFeiYongZC)) {
//                            DBManager.getInstance().insertFeiYongZC(duEntity2DbEntity((DUFeiYongZC) duEntity));
//                        } else if (duWordsInfo.getXmlPath().contains(DUWordsInfo.XMLJGJianHao)) {
//                            DBManager.getInstance().insertJianHao(duEntity2DbEntity((DUJianHao) duEntity));
//                        } else if (duWordsInfo.getXmlPath().contains(DUWordsInfo.XMLJGJianHaoMX)) {
//                            DBManager.getInstance().insertJianHaoMX(duEntity2DbEntity((DUJianHaoMX) duEntity));
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    subscriber.onError(new Throwable(e.getMessage()));
//                } finally {
//                    subscriber.onCompleted();
//                }
//            }
//        });
//    }

//    /**
//     * delete words
//     * @param duWordsInfo
//     * @return
//     */
//    public Observable<DUWordsResult> deleteWords(final DUWordsInfo duWordsInfo) {
//        return Observable.create(new Observable.OnSubscribe<DUWordsResult>() {
//            @Override
//            public void call(Subscriber<? super DUWordsResult> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//
//                try {
//                    init();
//
//                    DBManager.getInstance().deleteChaoBiaoZT();
//                    DBManager.getInstance().deleteChaoBiaoZTFL();
//                    DBManager.getInstance().deleteCiYu();
//                    DBManager.getInstance().deleteDingEJJBL();
//                    DBManager.getInstance().deleteFeiYongZC();
//                    DBManager.getInstance().deleteJianHao();
//                    DBManager.getInstance().deleteJianHaoMX();
//
//                    DBManager.getInstance().deleteChaoBiaoZT();
//                    DBManager.getInstance().deleteChaoBiaoZTFL();
//                    DBManager.getInstance().deleteCiYu();
//                    DBManager.getInstance().deleteDingEJJBL();
//                    DBManager.getInstance().deleteFeiYongZC();
//                    DBManager.getInstance().deleteJianHao();
//                    DBManager.getInstance().deleteJianHaoMX();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    subscriber.onError(new Throwable(e.getMessage()));
//                } finally {
//                    subscriber.onCompleted();
//                }
//            }
//        });
//    }

    /**
     * @param duChaoBiaoZTList
     * @return
     */
    public boolean saveChaoBiaoZTList(List<DUChaoBiaoZT> duChaoBiaoZTList) {
        try {
            init();

            if (duChaoBiaoZTList != null) {
                DBManager.getInstance().deleteChaoBiaoZT();

                for (DUChaoBiaoZT duChaoBiaoZT : duChaoBiaoZTList) {
                    ChaoBiaoZT chaoBiaoZT = new ChaoBiaoZT(
                            duChaoBiaoZT.getZhuangtaibm(),
                            duChaoBiaoZT.getShuiliangsfbm(),
                            duChaoBiaoZT.getZhuangtaiflbm(),
                            duChaoBiaoZT.getZhuangtaimc(),
                            duChaoBiaoZT.getKuaijiejpc());
                    DBManager.getInstance().insertChaoBiaoZT(chaoBiaoZT);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveChaoBiaoZTList: duChaoBiaoZTList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveChaoBiaoZTList---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * @param duChaoBiaoZTFLList
     * @return
     */
    public boolean saveChaoBiaoZTFLList(List<DUChaoBiaoZTFL> duChaoBiaoZTFLList) {
        try {
            init();

            if (duChaoBiaoZTFLList != null) {
                DBManager.getInstance().deleteChaoBiaoZTFL();

                for (DUChaoBiaoZTFL duChaoBiaoZTFL : duChaoBiaoZTFLList) {
                    ChaoBiaoZTFL chaoBiaoZTFL = new ChaoBiaoZTFL(
                            duChaoBiaoZTFL.getFenleibm(),
                            duChaoBiaoZTFL.getFenleimc());
                    DBManager.getInstance().insertChaoBiaoZTFL(chaoBiaoZTFL);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveDUChaoBiaoZTFLList: duChaoBiaoZTFLList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveDUChaoBiaoZTFLList---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * @param duCiYuXXList
     * @return
     */
    public boolean saveCiYuXXList(List<DUCiYuXX> duCiYuXXList) {
        try {
            init();

            if (duCiYuXXList != null) {
                DBManager.getInstance().deleteCiYu();

                for (DUCiYuXX duCiYuXX : duCiYuXXList) {
                    CiYuXX ciYuXX = new CiYuXX(
                            duCiYuXX.getId(),
                            duCiYuXX.getWordsid(),
                            duCiYuXX.getWordscontent(),
                            duCiYuXX.getWordsvalue(),
                            duCiYuXX.getWordsremark(),
                            duCiYuXX.getBelongid(),
                            duCiYuXX.getSortid(),
                            duCiYuXX.getIsactive());
                    DBManager.getInstance().insertCiYu(ciYuXX);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveCiYuXXList: duCiYuXXList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveCiYuXXList---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * @param duDingEJJBLList
     * @return
     */
    public boolean saveDingEJJBLList(List<DUDingEJJBL> duDingEJJBLList) {
        try {
            init();

            if (duDingEJJBLList != null) {
                DBManager.getInstance().deleteDingEJJBL();

                for (DUDingEJJBL duDingEJJBL : duDingEJJBLList) {
                    DingEJJBL dingEJJBL = new DingEJJBL(
                            duDingEJJBL.getId(),
                            duDingEJJBL.getBeil(),
                            duDingEJJBL.getKaishifloor(),
                            duDingEJJBL.getJieshufloor());
                    DBManager.getInstance().insertDingEJJBL(dingEJJBL);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveDingEJJBLList: duDingEJJBLList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveDingEJJBLList---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * @param duFeiYongZCList
     * @return
     */
    public boolean saveFeiYongZCList(List<DUFeiYongZC> duFeiYongZCList) {
        try {
            init();

            if (duFeiYongZCList != null) {
                DBManager.getInstance().deleteFeiYongZC();

                for (DUFeiYongZC duFeiYongZC : duFeiYongZCList) {
                    FeiYongZC feiYongZC = new FeiYongZC(
                            duFeiYongZC.getId(),
                            duFeiYongZC.getTiaojiah(),
                            duFeiYongZC.getFeiyongid(),
                            duFeiYongZC.getFeiyongmc(),
                            duFeiYongZC.getJiage(),
                            duFeiYongZC.getFeiyongdlid(),
                            duFeiYongZC.getXishu());
                    DBManager.getInstance().insertFeiYongZC(feiYongZC);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveFeiYongZCList: duFeiYongZCList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveFeiYongZCList---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * @param duJianHaoList
     * @return
     */
    public boolean saveJianHaoList(List<DUJianHao> duJianHaoList) {
        try {
            init();

            if (duJianHaoList != null) {
                DBManager.getInstance().deleteJianHao();

                for (DUJianHao duJianHao : duJianHaoList) {
                    JianHao jianHao = new JianHao(
                            duJianHao.getId(),
                            duJianHao.getTiaojiah(),
                            duJianHao.getDalei(),
                            duJianHao.getZhonglei(),
                            duJianHao.getXiaolei(),
                            duJianHao.getJianhao(),
                            duJianHao.getJietis(),
                            duJianHao.getBeizhu());
                    DBManager.getInstance().insertJianHao(jianHao);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveJianHaoList: duJianHaoList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveJianHaoList---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * @param duJianHaoMXList
     * @return
     */
    public boolean saveJianHaoMXList(List<DUJianHaoMX> duJianHaoMXList) {
        try {
            init();

            if (duJianHaoMXList != null) {
                DBManager.getInstance().deleteJianHaoMX();

                for (DUJianHaoMX duJianHaoMX : duJianHaoMXList) {
                    JianHaoMX jianHaoMX = new JianHaoMX(
                            duJianHaoMX.getId(),
                            duJianHaoMX.getTiaojiah(),
                            duJianHaoMX.getJianhao(),
                            duJianHaoMX.getFeiyongdlid(),
                            duJianHaoMX.getFeiyongid(),
                            duJianHaoMX.getQishiy(),
                            duJianHaoMX.getJieshuy(),
                            duJianHaoMX.getKaishisl(),
                            duJianHaoMX.getJieshusl(),
                            duJianHaoMX.getJietijb(),
                            duJianHaoMX.getZhekoul(),
                            duJianHaoMX.getZhekoulx(),
                            duJianHaoMX.getJiage());
                    DBManager.getInstance().insertJianHaoMX(jianHaoMX);
                }
                return true;
            } else {
                LogUtil.i(TAG, "---saveJianHaoMXList: duJianHaoMXList is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---saveJianHaoMXList---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * get results of combined searching
     *
     * @param duCombinedInfo
     * @return
     */
    public Observable<List<DUCard>> getCombinedResult(final DUCombinedInfo duCombinedInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUCard>>() {
            @Override
            public void call(Subscriber<? super List<DUCard>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duCombinedInfo == null)
                            || TextUtil.isNullOrEmpty(duCombinedInfo.getAccount())
                            || (duCombinedInfo.getDuCombined() == null)) {
                        throw new NullPointerException("duCombinedInfo is null");
                    }

                    String account = duCombinedInfo.getAccount();
                    DUCombined duCombined = duCombinedInfo.getDuCombined();

                    List<ChaoBiaoRW> chaoBiaoRWList = DBManager.getInstance().getChaoBiaoRWList(account);
                    List<String> ceBenList = new ArrayList<>();
                    if (chaoBiaoRWList != null) {
                        for (ChaoBiaoRW chaoBiaoRW : chaoBiaoRWList) {
                            ceBenList.add(chaoBiaoRW.getS_CH());
                        }
                    }

                    ConditionInfo conditionInfo = new ConditionInfo();
                    conditionInfo.set_account(account);
                    conditionInfo.set_cebenhao(duCombined.get_ch());
                    conditionInfo.set_cid(duCombined.get_cid());
                    conditionInfo.set_huming(duCombined.get_huming());
                    conditionInfo.set_dizhi(duCombined.get_dizhi());
                    conditionInfo.set_lianxidh(duCombined.get_lianxidh());
                    conditionInfo.set_jianhao(duCombined.get_jianhao());
                    conditionInfo.set_biaohao(duCombined.get_biaohao());
                    conditionInfo.set_koujinmin(duCombined.get_koujingmin());
                    conditionInfo.set_koujinmax(duCombined.get_koujingmax());
                    conditionInfo.set_qianfeibs(duCombined.get_qianfeibs());
                    conditionInfo.set_qianfeije(duCombined.get_qianfeije());
                    conditionInfo.set_huanbiaorq(duCombined.get_huanbiaorq());
                    conditionInfo.setCeBenList(ceBenList);

                    List<BIAOKAXX> biaoKaXXList =
                            DBManager.getInstance().getCombinedQuery(conditionInfo);
                    if (biaoKaXXList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        for (BIAOKAXX biaokaxx : biaoKaXXList) {
                            DUCard duCard = biaoKaXX2DUCard(biaokaxx);
                            duCardList.add(duCard);
                        }
                        subscriber.onNext(duCardList);
                    } else {
                        LogUtil.i(TAG, "---getCombinedResult: biaoKaXXList is null---");
                        subscriber.onError(new Throwable("biaoKaXXList is null"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<DUChaoBiaoZT>> getChaoBiaoZTList() {
        return Observable.create(new Observable.OnSubscribe<List<DUChaoBiaoZT>>() {
            @Override
            public void call(Subscriber<? super List<DUChaoBiaoZT>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    List<ChaoBiaoZT> chaoBiaoZTList = DBManager.getInstance().getAllChaobiaozt();
                    if (chaoBiaoZTList != null) {
                        List<DUChaoBiaoZT> duChaoBiaoZTList = new ArrayList<>();
                        for (ChaoBiaoZT chaoBiaoZT : chaoBiaoZTList) {
                            DUChaoBiaoZT duChaoBiaoZT = new DUChaoBiaoZT(
                                    chaoBiaoZT.getI_ZHUANGTAIBM(),
                                    chaoBiaoZT.getI_SHUILIANGSFBM(),
                                    chaoBiaoZT.getI_ZHUANGTAIFLBM(),
                                    chaoBiaoZT.getS_ZHUANGTAIMC(),
                                    chaoBiaoZT.getS_KUAIJIEJPC());
                            duChaoBiaoZTList.add(duChaoBiaoZT);
                        }
                        subscriber.onNext(duChaoBiaoZTList);
                    } else {
                        LogUtil.i(TAG, "---getChaoBiaoZTList: chaoBiaoZTList is null---");
                        subscriber.onError(new Throwable("chaoBiaoZTList is null"));
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<DUChaoBiaoZTFL>> getChaoBiaoZTFLList() {
        return Observable.create(new Observable.OnSubscribe<List<DUChaoBiaoZTFL>>() {
            @Override
            public void call(Subscriber<? super List<DUChaoBiaoZTFL>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    List<ChaoBiaoZTFL> chaoBiaoZTFLs = DBManager.getInstance().getAllChaobiaoZTFL();
                    if (chaoBiaoZTFLs != null) {
                        List<DUChaoBiaoZTFL> entityList = new ArrayList<>();
                        for (ChaoBiaoZTFL chaoBiaoZTFL : chaoBiaoZTFLs) {
                            DUChaoBiaoZTFL duChaoBiaoZTFL = new DUChaoBiaoZTFL(
                                    chaoBiaoZTFL.getI_FenLeiBM(),
                                    chaoBiaoZTFL.getS_FenLeiMC()
                            );

                            entityList.add(duChaoBiaoZTFL);
                        }
                        subscriber.onNext(entityList);
                    } else {
                        LogUtil.i(TAG, "---getChaoBiaoZTFLList: ChaoBiaoZTFLList is null---");
                        subscriber.onError(new Throwable("ChaoBiaoZTFLList is null"));
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<DUCiYuXX>> getCiYuXX(final DUCiYuXXInfo duCiYuXXInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUCiYuXX>>() {
            @Override
            public void call(Subscriber<? super List<DUCiYuXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if (duCiYuXXInfo == null ||
                        duCiYuXXInfo.getFilterType() == null) {
                    throw new NullPointerException("duCiYuXXInfo contains null parameter");
                } else {
                    try {
                        init();

                        List<CiYuXX> ciYuXXList = null;
                        switch (duCiYuXXInfo.getFilterType()) {
                            case LIANG_GAO_YY:
                                ciYuXXList = DBManager.getInstance().getLiangGaoYY();
                                break;
                            case LIANG_DI_YY:
                                ciYuXXList = DBManager.getInstance().getLiangDiYY();
                                break;
                            case NO_READ_REASON:
                                ciYuXXList = DBManager.getInstance().getNoReadReason();
                                break;
                            default:
                                break;
                        }

                        if (ciYuXXList != null) {
                            List<DUCiYuXX> duCiYuXXList = new ArrayList<>();
                            for (CiYuXX ciYuXX : ciYuXXList) {
                                DUCiYuXX duCiYuXX = new DUCiYuXX(
                                        ciYuXX.getID(),
                                        ciYuXX.getWORDSID(),
                                        ciYuXX.getWORDSCONTENT(),
                                        ciYuXX.getWORDSVALUE(),
                                        ciYuXX.getWORDSREMARK(),
                                        ciYuXX.getBELONGID(),
                                        ciYuXX.getSORTID(),
                                        ciYuXX.getISACTIVE());
                                duCiYuXXList.add(duCiYuXX);
                            }
                            subscriber.onNext(duCiYuXXList);
                        } else {
                            LogUtil.i(TAG, "---getCiYuXX: ciYuXXList is null---");
                            subscriber.onError(new Throwable("---getCiYuXX: ciYuXXList is null---"));
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                        subscriber.onError(new Throwable(e.getMessage()));
                    } finally {
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    public Observable<List<DUMedia>> getMediaList(final DUMediaInfo duMediaInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUMedia>>() {
            @Override
            public void call(Subscriber<? super List<DUMedia>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if (duMediaInfo == null
                        || (duMediaInfo.getDuMedia() == null)
                        || (duMediaInfo.getDuMedia().getAccount() == null)) {
                    throw new NullPointerException("duMediaInfo contains null parameter");
                } else {
                    try {
                        init();

                        List<DuoMeiTXX> duoMeiTXXList = null;
                        DUMedia duMedia = duMediaInfo.getDuMedia();
                        String account = duMedia.getAccount();
                        int renWuBH = duMedia.getRenwubh();
                        String ch = duMedia.getCh();
                        String cid = duMedia.getCid();
                        switch (duMediaInfo.getOperationType()) {
                            case SELECT:
                                int type = -1;
                                if (duMediaInfo.getMeterReadingType() == DUMediaInfo.MeterReadingType.NORMAL) {
                                    type = 0;
                                } else if (duMediaInfo.getMeterReadingType() == DUMediaInfo.MeterReadingType.WAIFU) {
                                    type = 1;
                                } else if (duMediaInfo.getMeterReadingType() == DUMediaInfo.MeterReadingType.SAMPLING) {
                                    type = 2;
                                } else if (duMediaInfo.getMeterReadingType() == DUMediaInfo.MeterReadingType.RUSH_PAY) {
                                    type = 4;
                                    cid = null;
                                } else if (duMediaInfo.getMeterReadingType() == DUMediaInfo.MeterReadingType.DELAYING) {
                                    type = 5;
                                } else {
                                    type = 0;
                                }
                                duoMeiTXXList = DBManager.getInstance().getDuoMeiTXXList(account, cid, ch, renWuBH, type);
                                break;
                            case NOT_UPLOADED:
                                duoMeiTXXList = DBManager.getInstance().getNotUploadedDuoMeiTXXList(account, renWuBH, ch);
                                break;
                            case EACH_VOLUME:
                                duoMeiTXXList = DBManager.getInstance().getAllDuoMeiTXXList(account, renWuBH, ch);
                                break;
                            case All:
                                duoMeiTXXList = DBManager.getInstance().getAllDuoMeiTXXList(account, duMediaInfo.getTaskIdsArry());
                                break;
                            case MORE_ITEMS:
                                duoMeiTXXList = DBManager.getInstance().getMoreDuoMeiTXXList(account, renWuBH, ch,
                                        duMediaInfo.getOffset(), duMediaInfo.getLimit());
                                break;
                            case MORE_ITEMS_WAIFU:
                                duoMeiTXXList = DBManager.getInstance().getMoreDuoMeiTXXList(account,
                                        duMediaInfo.getOffset(), duMediaInfo.getLimit());
                                break;
                            case MORE_ITEMS_DELAY:
                                duoMeiTXXList = DBManager.getInstance().getMoreDelayDuoMeiTXXList(account,
                                        duMediaInfo.getOffset(), duMediaInfo.getLimit());
                                break;
                            default:
                                break;
                        }

                        if (duoMeiTXXList != null) {
                            List<DUMedia> duMediaList = new ArrayList<>();
                            for (DuoMeiTXX duoMeiTXX : duoMeiTXXList) {
                                duMedia = new DUMedia(
                                        duoMeiTXX.getID(),
                                        duoMeiTXX.getS_CID(),
                                        duoMeiTXX.getI_WENJIANLX(),
                                        duoMeiTXX.getS_WENJIANLJ(),
                                        duoMeiTXX.getB_WENJIANNR(),
                                        duoMeiTXX.getS_WENJIANMC(),
                                        duoMeiTXX.getI_CHAOBIAOID(),
                                        duoMeiTXX.getI_X(),
                                        duoMeiTXX.getI_Y(),
                                        duoMeiTXX.getS_BEIZHU(),
                                        duoMeiTXX.getI_SHANGCHUANBZ(),
                                        duoMeiTXX.getI_TYPE(),
                                        duoMeiTXX.getS_CH(),
                                        0,
                                        duoMeiTXX.getS_ACCOUNT(),
                                        0L,
                                        "",
                                        "");
                                duMediaList.add(duMedia);
                            }
                            subscriber.onNext(duMediaList);
                        } else {
                            LogUtil.i(TAG, "---getMediaList: duMediaList is null---");
                            subscriber.onError(new Throwable("---getMediaList: duMediaList is null---"));
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                        subscriber.onError(new Throwable(e.getMessage()));
                    } finally {
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    public Observable<List<DUMedia>> getSamplingMediaList(final DUMediaInfo duMediaInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUMedia>>() {
            @Override
            public void call(Subscriber<? super List<DUMedia>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if (duMediaInfo == null
                        || (duMediaInfo.getDuMedia() == null)
                        || (duMediaInfo.getDuMedia().getAccount() == null)) {
                    throw new NullPointerException("duMediaInfo contains null parameter");
                } else {
                    try {
                        init();

                        List<DuoMeiTXX> duoMeiTXXList = null;
                        DUMedia duMedia = duMediaInfo.getDuMedia();
                        String account = duMedia.getAccount();
                        int renWuBH = duMedia.getRenwubh();
                        String ch = duMedia.getCh();
                        String cid = duMedia.getCid();
                        switch (duMediaInfo.getOperationType()) {
                            case SELECT:
                                duoMeiTXXList = DBManager.getInstance().getDuoMeiTXXList(account, cid, ch, renWuBH, 2);
                                break;
                            case NOT_UPLOADED:
                                duoMeiTXXList = DBManager.getInstance().getNotUploadedDuoMeiTXXList(account, renWuBH, null);
                                break;
                            case EACH_VOLUME:
                                duoMeiTXXList = DBManager.getInstance().getAllDuoMeiTXXList(account, renWuBH, ch);
                                break;
                            case All:
                                duoMeiTXXList = DBManager.getInstance().getAllDuoMeiTXXList(account, duMediaInfo.getTaskIdsArry());
                                break;
                            default:
                                break;
                        }

                        if (duoMeiTXXList != null) {
                            List<DUMedia> duMediaList = new ArrayList<>();
                            for (DuoMeiTXX duoMeiTXX : duoMeiTXXList) {
                                duMedia = new DUMedia(
                                        duoMeiTXX.getID(),
                                        duoMeiTXX.getS_CID(),
                                        duoMeiTXX.getI_WENJIANLX(),
                                        duoMeiTXX.getS_WENJIANLJ(),
                                        duoMeiTXX.getB_WENJIANNR(),
                                        duoMeiTXX.getS_WENJIANMC(),
                                        duoMeiTXX.getI_CHAOBIAOID(),
                                        duoMeiTXX.getI_X(),
                                        duoMeiTXX.getI_Y(),
                                        duoMeiTXX.getS_BEIZHU(),
                                        duoMeiTXX.getI_SHANGCHUANBZ(),
                                        duoMeiTXX.getI_TYPE(),
                                        duoMeiTXX.getS_CH(),
                                        0,
                                        duoMeiTXX.getS_ACCOUNT(),
                                        0L,
                                        "",
                                        "");
                                duMediaList.add(duMedia);
                            }
                            subscriber.onNext(duMediaList);
                        } else {
                            LogUtil.i(TAG, "---getMediaList: duMediaList is null---");
                            subscriber.onError(new Throwable("---getMediaList: duMediaList is null---"));
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                        subscriber.onError(new Throwable(e.getMessage()));
                    } finally {
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    public Observable<DUMediaResult> updateMedia(final DUMediaInfo duMediaInfo) {
        return Observable.create(new Observable.OnSubscribe<DUMediaResult>() {
            @Override
            public void call(Subscriber<? super DUMediaResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if ((duMediaInfo == null)
                        || (duMediaInfo.getDuMedia() == null)
                        || (duMediaInfo.getOperationType() == null)) {
                    throw new NullPointerException("duMediaInfo contains null parameter");
                } else {
                    try {
                        init();

                        DUMedia duMedia = duMediaInfo.getDuMedia();
                        DuoMeiTXX duoMeiTXX = new DuoMeiTXX(
                                duMedia.getId(),
                                duMedia.getCid(),
                                duMedia.getWenjianlx(),
                                duMedia.getWenjianlj(),
                                duMedia.getWenjiannr(),
                                duMedia.getWenjianmc(),
                                duMedia.getChaobiaoid(),
                                duMedia.getX(),
                                duMedia.getY(),
                                duMedia.getBeizhu(),
                                duMedia.getShangchuanbz(),
                                duMedia.getType(),
                                duMedia.getCh(),
                                duMedia.getAccount());
                        boolean ret = false;
                        switch (duMediaInfo.getOperationType()) {
                            case INSERT:
                                ret = DBManager.getInstance().insertDuoMeiTXX(duoMeiTXX);
                                break;
                            case UPDATE:
                                ret = DBManager.getInstance().updateDuoMeiTXX(duoMeiTXX);
                                break;
                            case SELECT:
                                break;
                            case DELETE:
                                ret = DBManager.getInstance().deleteDuoMeiTXX(duMediaInfo.getDuMedia().getWenjianmc());
                                break;
                            case DELETE_ALL:
                                ret = DBManager.getInstance().deleteDuoMeiTXXByTaskId(duMediaInfo.getDuMedia().getAccount(),
                                        duMediaInfo.getTaskIdsArry());
                                break;
                            default:
                                break;
                        }

                        DUMediaResult duMediaResult = new DUMediaResult();
                        List<DUMedia> duMediaList = new ArrayList<DUMedia>();
                        duMediaList.add(duMedia);
                        duMediaResult.setDuMediaList(duMediaList);
                        subscriber.onNext(duMediaResult);
                    } catch (Exception e) {
                        e.getStackTrace();
                        subscriber.onError(new Throwable(e.getMessage()));
                    } finally {
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    public Observable<Boolean> saveImage(final DUMediaInfo duMediaInfo) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if ((duMediaInfo == null)
                        || (duMediaInfo.getDuMedia() == null)
                        || (duMediaInfo.getOperationType() == null)) {
                    throw new NullPointerException("duMediaInfo contains null parameter");
                } else {
                    try {
                        init();

                        DUMedia duMedia = duMediaInfo.getDuMedia();
                        DuoMeiTXX duoMeiTXX = new DuoMeiTXX(
                                duMedia.getId(),
                                duMedia.getCid(),
                                duMedia.getWenjianlx(),
                                duMedia.getWenjianlj(),
                                duMedia.getWenjiannr(),
                                duMedia.getWenjianmc(),
                                duMedia.getChaobiaoid(),
                                duMedia.getX(),
                                duMedia.getY(),
                                duMedia.getBeizhu(),
                                duMedia.getShangchuanbz(),
                                duMedia.getType(),
                                duMedia.getCh(),
                                duMedia.getAccount());
                        boolean ret = false;
                        switch (duMediaInfo.getOperationType()) {
                            case INSERT:
                                ret = DBManager.getInstance().insertDuoMeiTXX(duoMeiTXX);
                                break;
                            case UPDATE:
                                ret = DBManager.getInstance().updateDuoMeiTXX(duoMeiTXX);
                                break;
                            case SELECT:
                                break;
                            case DELETE:
                                ret = DBManager.getInstance().deleteDuoMeiTXX(duMediaInfo.getDuMedia().getWenjianmc());
                                break;
                            case DELETE_ALL:
                                ret = DBManager.getInstance().deleteDuoMeiTXXByTaskId(duMediaInfo.getDuMedia().getAccount(),
                                        duMediaInfo.getTaskIdsArry());
                                break;
                            default:
                                break;
                        }

                        subscriber.onNext(ret);
                    } catch (Exception e) {
                        e.getStackTrace();
                        subscriber.onError(new Throwable(e.getMessage()));
                    } finally {
                        subscriber.onCompleted();
                    }
                }
            }
        });
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
    public Observable<Boolean> adjustCardAndRecords(final String account,
                                                    final List<DUCard> duCardList,
                                                    final List<DURecord> duRecordList,
                                                    final DUTask duTask,
                                                    final String cardId,
                                                    final int orderNumber,
                                                    final boolean isCardId,
                                                    final boolean isFront) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (TextUtil.isNullOrEmpty(account)
                            || (duCardList == null)
                            || (duCardList.size() <= 0)
                            || (duRecordList == null)
                            || (duCardList.size() != duRecordList.size())
                            || (duTask == null)
                            || (duTask.getRenWuBH() <= 0)
                            || TextUtil.isNullOrEmpty(duTask.getcH())
                            || TextUtil.isNullOrEmpty(cardId)) {
                        LogUtil.i(TAG, "---adjustCardAndRecords---parameter is error");
                        throw new NullPointerException("parameter is error");
                    }

                    // step-1
                    // select card by volume
                    String volume = duTask.getcH();
                    int taskId = duTask.getRenWuBH();
                    List<BIAOKAXX> biaokaxxList = DBManager.getInstance().getBiaoKaXXList(volume);
                    if ((biaokaxxList == null) || (biaokaxxList.size() <= 0)) {
                        subscriber.onError(new Throwable("no cards: " + volume));
                        return;
                    }

                    // step-2
                    // select records by volume
                    List<ChaoBiaoSJ> chaoBiaoSJList =
                            DBManager.getInstance().getAllChaoBiaoSJ(account, taskId, volume);
                    if ((chaoBiaoSJList == null) || (chaoBiaoSJList.size() <= 0)) {
                        subscriber.onError(new Throwable("no records: " + volume));
                        return;
                    }

                    // step-3
                    // sort cards by order number
                    //sort(biaokaxxList);

                    // step-4
                    // insert new cards with the position
                    // and update db
                    if (!updateCards(biaokaxxList, duCardList, duTask, cardId,
                            orderNumber, isCardId, isFront)) {
                        subscriber.onError(new Throwable("failure to update cards"));
                        return;
                    }

                    // step-5
                    // update records
                    updateRecords(biaokaxxList, chaoBiaoSJList, duRecordList, duTask);

                    // step-6
                    // update the task
                    duTask.setZongShu(duTask.getZongShu() + duCardList.size());
                    duTask.setTongBuBZ(DUTask.TONGBUBZ_SYNC);
                    DBManager.getInstance().updateChaoBiaoRW(duTask2ChaoBiaoRW(duTask));

                    subscriber.onNext(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }

            private boolean updateCards(List<BIAOKAXX> biaokaxxList,
                                        List<DUCard> duCardList,
                                        DUTask duTask,
                                        String cardId,
                                        int orderNumber,
                                        boolean isCardId,
                                        boolean isFront) {
                int position = -1;
                if (isCardId) {
                    position = findPosition(biaokaxxList, cardId);
                } else {
                    position = findPosition(biaokaxxList, orderNumber);
                }

                if (position == -1) {
                    return false;
                }

                if (!isFront) {
                    position++;
                }

                if (position > biaokaxxList.size()) {
                    position = biaokaxxList.size();
                }

                for (DUCard duCard : duCardList) {
                    duCard.setCh(duTask.getcH());
                    duCard.setShangchuanbz(DUCard.SHANGCHUANBZ_WEISHANGC);
                    biaokaxxList.add(position++, duCard2BiaoKaXX(duCard));
                }

                int i = 1;
                for (BIAOKAXX biaokaxx : biaokaxxList) {
                    biaokaxx.setI_CENEIXH(i++);
                }
                DBManager.getInstance().updateBiaoKaXXList(biaokaxxList);

                return true;
            }

            private boolean updateRecords(List<BIAOKAXX> biaokaxxList,
                                          List<ChaoBiaoSJ> chaoBiaoSJList,
                                          List<DURecord> duRecordList,
                                          DUTask duTask) {
                int orderNumber = -1;
                for (ChaoBiaoSJ chaoBiaoSJ : chaoBiaoSJList) {
                    orderNumber = getOrderNumber(biaokaxxList, chaoBiaoSJ.getS_CID());
                    if (orderNumber == -1) {
                        continue;
                    }

                    chaoBiaoSJ.setI_CENEIXH(orderNumber);
                }
                DBManager.getInstance().updateChaoBiaoSJList(chaoBiaoSJList);

                List<ChaoBiaoSJ> chaoBiaoSJs = new ArrayList<ChaoBiaoSJ>();
                for (DURecord duRecord : duRecordList) {
                    orderNumber = getOrderNumber(biaokaxxList, duRecord.getCid());
                    if (orderNumber == -1) {
                        continue;
                    }

                    ChaoBiaoSJ chaoBiaoSJ = duRecord2ChaoBiaoSJ(duRecord);
                    chaoBiaoSJ.setI_RenWuBH(duTask.getRenWuBH());
                    chaoBiaoSJ.setS_CH(duTask.getcH());
                    chaoBiaoSJ.setI_CENEIXH(orderNumber);
                    chaoBiaoSJs.add(chaoBiaoSJ);
                }

                DBManager.getInstance().updateChaoBiaoSJList(chaoBiaoSJs);
                return true;
            }

            private void sort(List<BIAOKAXX> biaokaxxList) {
                Collections.sort(biaokaxxList, new Comparator<BIAOKAXX>() {
                    public int compare(BIAOKAXX arg0, BIAOKAXX arg1) {
                        return arg0.getI_CENEIXH() - arg1.getI_CENEIXH();
                    }
                });
            }

            private int findPosition(List<BIAOKAXX> biaokaxxList, String cardId) {
                for (int i = 0; i < biaokaxxList.size(); i++) {
                    BIAOKAXX biaokaxx = biaokaxxList.get(i);
                    if (biaokaxx.getS_CID().equals(cardId)) {
                        return i;
                    }
                }

                return -1;
            }

            private int findPosition(List<BIAOKAXX> biaokaxxList, int orderNumber) {
                for (int i = 0; i < biaokaxxList.size(); i++) {
                    BIAOKAXX biaokaxx = biaokaxxList.get(i);
                    if (biaokaxx.getI_CENEIXH() == orderNumber) {
                        return i;
                    }
                }

                return -1;
            }

            private int getOrderNumber(List<BIAOKAXX> biaokaxxList, String cardId) {
                for (BIAOKAXX biaokaxx : biaokaxxList) {
                    if (biaokaxx.getS_CID().equals(cardId)) {
                        return biaokaxx.getI_CENEIXH();
                    }
                }

                return -1;
            }
        });
    }

    public Observable<Boolean> updateCbrwCbsjBkxx(final DUTask duTask,
                                                  final String cidRemove,
                                                  final List<DURecord> duRecordList,
                                                  final List<DUCard> duCardList,
                                                  final List<DURecord> removedDuRecordList,
                                                  final List<DUCard> removedDUCardList) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {

                    List<BIAOKAXX> biaokaxxList = DBManager.getInstance().getBiaoKaXXList(duTask.getcH());

                    //插入表卡位置
                    int numberRemove = -1;
                    for (int i = 0; i < biaokaxxList.size(); i++) {
                        if (biaokaxxList.get(i).getS_CID().equals(cidRemove)) {
                            numberRemove = i + 1;
                            break;
                        }
                    }

                    //更新表卡数据
                    for (DUCard duCard : removedDUCardList) {
                        BIAOKAXX biaokaxx = DBManager.getInstance().getBiaoKaXX(duCard.getCid());
                        biaokaxx.setS_CH(duTask.getcH());
                        DBManager.getInstance().updateBiaoKaXX(biaokaxx);
                        biaokaxxList.add(numberRemove, biaokaxx);
                    }

                    //重新调整表卡顺序
                    int newCeNeiXH = 1;
                    for (BIAOKAXX biaokaxx : biaokaxxList) {
                        biaokaxx.setI_CENEIXH(newCeNeiXH);
                        DBManager.getInstance().updateBiaoKaXX(biaokaxx);
                        newCeNeiXH++;
                    }

                    //更新抄表数据
                    for (DURecord duRecord : removedDuRecordList) {
                        ChaoBiaoSJ chaoBiaoSJ = DBManager.getInstance().getChaoBiaoSJByID(duRecord.getId());
                        chaoBiaoSJ.setI_RenWuBH(duTask.getRenWuBH());
                        chaoBiaoSJ.setS_CH(duTask.getcH());
                        chaoBiaoSJ.setS_ST(duTask.getsT());
                        DBManager.getInstance().updateChaoBiaoSJ(chaoBiaoSJ);
                    }

                    //重新调整抄表顺序
                    List<ChaoBiaoSJ> chaoBiaoSJList = DBManager.getInstance().getNextChaoBiaoSJList(duTask.getcH());

                    for (BIAOKAXX biaokaxx : biaokaxxList) {

                        for (ChaoBiaoSJ chaoBiaoSJ : chaoBiaoSJList) {
                            if (biaokaxx.getS_CID().equals(chaoBiaoSJ.getS_CID())) {
                                chaoBiaoSJ.setI_CeNeiPX(biaokaxx.getI_CENEIXH());
                                chaoBiaoSJ.setI_CENEIXH(biaokaxx.getI_CENEIXH());
                                DBManager.getInstance().updateChaoBiaoSJ(chaoBiaoSJ);
                            }

                        }

                    }
                    //调整任务表的总数
                    if (duTask != null) {
                        ChaoBiaoRW chaoBiaoRW = DBManager.getInstance().getChaoBiaoRWByS_CH(duTask.getcH());
                        chaoBiaoRW.setI_ZongShu(chaoBiaoRW.getI_ZongShu() + removedDuRecordList.size());
//                      chaoBiaoRW.setI_YiChaoShu(chaoBiaoRW.getI_YiChaoShu() + removedDuRecordList.size());
                        DBManager.getInstance().updateChaoBiaoRW(chaoBiaoRW);
                    }


//                    if (duTask != null) {
//                        ChaoBiaoRW chaoBiaoRW = DBManager.getInstance().getChaoBiaoRWByS_CH(duTask.getcH());
//                        chaoBiaoRW.setI_ZongShu(chaoBiaoRW.getI_ZongShu() + removedDuRecordList.size());
////                        chaoBiaoRW.setI_YiChaoShu(chaoBiaoRW.getI_YiChaoShu() + removedDuRecordList.size());
//                        DBManager.getInstance().updateChaoBiaoRW(chaoBiaoRW);
//                    }
//
//                    ChaoBiaoRW chaoBiaoRWNew = DBManager.getInstance().getChaoBiaoRWByS_CH(newCh);
//                    chaoBiaoRWNew.setI_ZongShu(chaoBiaoRWNew.getI_ZongShu() + changeCount);
//                    chaoBiaoRWNew.setI_YiChaoShu(chaoBiaoRWNew.getI_YiChaoShu() + changeCount);
//                    DBManager.getInstance().updateChaoBiaoRW(chaoBiaoRWNew);
////
//                    for (DUCard duCard : removedDUCardList) {
//                        BIAOKAXX biaokaxx = DBManager.getInstance().getBiaoKaXX(duCard.getCid());
//                        biaokaxx.setS_CH(duTask.getcH());
//                        biaokaxx.setI_CENEIXH(duCard.getCeneixh());
//                        DBManager.getInstance().updateBiaoKaXX(biaokaxx);
//                    }
//
//                    for (DURecord duRecord : removedDuRecordList) {
//                        ChaoBiaoSJ chaoBiaoSJ = DBManager.getInstance().getChaoBiaoSJByID(duRecord.getId());
//                        chaoBiaoSJ.setI_RenWuBH(duTask.getRenWuBH());
//                        chaoBiaoSJ.setS_CH(duTask.getcH());
//                        chaoBiaoSJ.setS_ST(duTask.getsT());
//                        chaoBiaoSJ.setI_CENEIXH(duRecord.getCeneixh());
//                        chaoBiaoSJ.setI_CeNeiPX(duRecord.getCeneipx());
//                        DBManager.getInstance().updateChaoBiaoSJ(chaoBiaoSJ);
//                    }

                    subscriber.onNext(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }

            }
        });
    }

    public Observable<Boolean> isTaskContainingFullData(final DUTaskInfo duTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if ((duTaskInfo == null)
                            || TextUtil.isNullOrEmpty(duTaskInfo.getAccount())
                            || (duTaskInfo.getTaskId() <= 0)
                            || TextUtil.isNullOrEmpty(duTaskInfo.getVolume())) {
                        throw new NullPointerException("parameter is null");
                    }

                    String account = duTaskInfo.getAccount();
                    int taskId = duTaskInfo.getTaskId();
                    String volume = duTaskInfo.getVolume();
                    ChaoBiaoRW chaoBiaoRW = DBManager.getInstance().getChaoBiaoRW(account, taskId, volume);
                    if (chaoBiaoRW == null) {
                        subscriber.onError(new Throwable("chaoBiaoRW is null"));
                        return;
                    }

                    if (chaoBiaoRW.getI_ZongShu() <= 0) {
                        subscriber.onNext(true);
                        return;
                    }

                    List<ChaoBiaoSJ> chaoBiaoSJList =
                            DBManager.getInstance().getListChaoBiaoSJ(taskId, volume, 6);
                    if (chaoBiaoSJList == null) {
                        subscriber.onError(new Throwable("chaoBiaoSJList is null"));
                        return;
                    }

                    if (chaoBiaoSJList.size() != chaoBiaoRW.getI_ZongShu()) {
                        subscriber.onNext(false);
                    } else {
                        subscriber.onNext(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<TraceInfo>> getChaoBiaoTrance(final int taskId, final String ch) {
        return Observable.create(new Observable.OnSubscribe<List<TraceInfo>>() {
            @Override
            public void call(Subscriber<? super List<TraceInfo>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (taskId <= 0 || ch == null) {
                        throw new NullPointerException("getChaoBiaoTrance parameter is null");
                    }

                    List<TraceInfo> infos = new ArrayList<>();

                    List<BIAOKAXX> biaokaxxes = DBManager.getInstance().getBiaoKaXXList(ch);
                    if (biaokaxxes != null && biaokaxxes.size() > 0) {
                        for (BIAOKAXX biaokaxx : biaokaxxes) {
                            TraceInfo info = biaoKaXX2TraceInfo(biaokaxx);
                            if (info == null) {
                                continue;
                            }
                            infos.add(info);
                        }
                    }

                    List<ChaoBiaoSJ> list = DBManager.getInstance().getListChaoBiaoSJ(taskId,
                            ch, ChaoBiaoSJDao.FINISHWORK);
                    if (list != null && list.size() > 0) {
                        for (ChaoBiaoSJ chaoBiaoSJ : list) {
                            TraceInfo info = chaoBiaoSJ2TraceInfo(chaoBiaoSJ);
                            if (info == null) {
                                continue;
                            }
                            infos.add(info);
                        }
                    }

                    subscriber.onNext(infos);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<ChaoBiaoZT>> getSortStatus(final String status) {
        return Observable.create(new Observable.OnSubscribe<List<ChaoBiaoZT>>() {
            @Override
            public void call(Subscriber<? super List<ChaoBiaoZT>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    init();

                    if (status == null) {
                        throw new NullPointerException("parameter is null");
                    }

                    subscriber.onNext(DBManager.getInstance().getZhuangTaiBMList(status));
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public void checkFinishedTasks(String account, List<DUTask> duTasks) {
        if (duTasks == null) {
            return;
        }

        try {
            for (DUTask duTask : duTasks) {
                List<ChaoBiaoSJ> chaoBiaoSJList =
                        DBManager.getInstance().getListChaoBiaoSJ(duTask.getRenWuBH(), duTask.getcH(), 1);
                if (chaoBiaoSJList == null || chaoBiaoSJList.size() == 0) {
                    continue;
                }

                duTask.setYiChaoShu(chaoBiaoSJList.size());
                DBManager.getInstance().updateChaoBiaoRW(account, duTask.getRenWuBH(), duTask.getcH(), chaoBiaoSJList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //type 0全部 1 抄表数据 2稽查数据
    public Observable<Boolean> isTimeSync(final long time, final String account, final Integer type) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    init();
                    boolean flag = false;
                    if (type == 1) {
                        flag = DBManager.getInstance().weiShangchuanRecordCount(time, account) > 0;
                    } else if (type == 2) {
                        flag = DBManager.getInstance().weiShangchuanJiChaRecordCount(time, account) > 0;
                    } else {
                        flag = DBManager.getInstance().weiShangchuanRecordCount(time, account) > 0 || DBManager.getInstance().weiShangchuanJiChaRecordCount(time, account) > 0;
                    }

                    subscriber.onNext(flag);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    private ChaoBiaoZT duEntity2DbEntity(DUChaoBiaoZT duChaoBiaoZT) {
        ChaoBiaoZT chaoBiaoZT = new ChaoBiaoZT(
                duChaoBiaoZT.getZhuangtaibm(),
                duChaoBiaoZT.getShuiliangsfbm(),
                duChaoBiaoZT.getZhuangtaiflbm(),
                duChaoBiaoZT.getZhuangtaimc(),
                duChaoBiaoZT.getKuaijiejpc()
        );

        return chaoBiaoZT;
    }

    private ChaoBiaoZTFL duEntity2DbEntity(DUChaoBiaoZTFL duChaoBiaoZTFL) {
        ChaoBiaoZTFL chaoBiaoZTFL = new ChaoBiaoZTFL(
                duChaoBiaoZTFL.getFenleibm(),
                duChaoBiaoZTFL.getFenleimc()
        );

        return chaoBiaoZTFL;
    }

    private CiYuXX duEntity2DbEntity(DUCiYuXX duCiYuXX) {
        CiYuXX ciYuXX = new CiYuXX(
                duCiYuXX.getId(),
                duCiYuXX.getWordsid(),
                duCiYuXX.getWordscontent(),
                duCiYuXX.getWordsvalue(),
                duCiYuXX.getWordsremark(),
                duCiYuXX.getBelongid(),
                duCiYuXX.getSortid(),
                duCiYuXX.getIsactive()
        );

        return ciYuXX;
    }

    private DingEJJBL duEntity2DbEntity(DUDingEJJBL duDingEJJBL) {
        DingEJJBL dingEJJBL = new DingEJJBL(
                duDingEJJBL.getId(),
                duDingEJJBL.getBeil(),
                duDingEJJBL.getKaishifloor(),
                duDingEJJBL.getJieshufloor()
        );

        return dingEJJBL;
    }

    private FeiYongZC duEntity2DbEntity(DUFeiYongZC duFeiYongZC) {
        FeiYongZC feiYongZC = new FeiYongZC(
                duFeiYongZC.getId(),
                duFeiYongZC.getTiaojiah(),
                duFeiYongZC.getFeiyongid(),
                duFeiYongZC.getFeiyongmc(),
                duFeiYongZC.getJiage(),
                duFeiYongZC.getFeiyongdlid(),
                duFeiYongZC.getXishu()
        );

        return feiYongZC;
    }

    private JianHao duEntity2DbEntity(DUJianHao duJianHao) {
        JianHao jianHao = new JianHao(
                duJianHao.getId(),
                duJianHao.getTiaojiah(),
                duJianHao.getDalei(),
                duJianHao.getZhonglei(),
                duJianHao.getXiaolei(),
                duJianHao.getJianhao(),
                duJianHao.getJietis(),
                duJianHao.getBeizhu());

        return jianHao;
    }

    private JianHaoMX duEntity2DbEntity(DUJianHaoMX duJianHaoMX) {
        JianHaoMX jianHaoMX = new JianHaoMX(
                duJianHaoMX.getId(),
                duJianHaoMX.getTiaojiah(),
                duJianHaoMX.getJianhao(),
                duJianHaoMX.getFeiyongdlid(),
                duJianHaoMX.getFeiyongid(),
                duJianHaoMX.getQishiy(),
                duJianHaoMX.getJieshuy(),
                duJianHaoMX.getKaishisl(),
                duJianHaoMX.getJieshusl(),
                duJianHaoMX.getJietijb(),
                duJianHaoMX.getZhekoul(),
                duJianHaoMX.getZhekoulx(),
                duJianHaoMX.getJiage()
        );

        return jianHaoMX;
    }

    private ChaoBiaoRW duTask2ChaoBiaoRW(DUTask duTask) {
        return new ChaoBiaoRW(
                duTask.getId(),
                duTask.getRenWuBH(),
                duTask.getChaoBiaoYBH(),
                duTask.getChaoBiaoYXM(),
                duTask.getPaiFaSJ(),
                duTask.getZhangWuNY(),
                duTask.getcH(),
                duTask.getCeBenMC(),
                duTask.getChaoBiaoZQ(),
                duTask.getGongCi(),
                duTask.getsT(),
                duTask.getZongShu(),
                duTask.getYiChaoShu());
    }

    private JiChaRW duSamplingTask2JiChaRW(DUSamplingTask duSamplingTask) {
        return new JiChaRW(
                duSamplingTask.getId(),
                duSamplingTask.getRenWuBH(),
                duSamplingTask.getChaoBiaoYBH(),
                duSamplingTask.getChaoBiaoYXM(),
                duSamplingTask.getPaiFaSJ(),
                duSamplingTask.getZhangWuNY(),
//                duJiChaTask.getcH(),
//                duJiChaTask.getCeBenMC(),
//                duJiChaTask.getChaoBiaoZQ(),
                duSamplingTask.getGongCi(),
                duSamplingTask.getsT(),
                duSamplingTask.getZongShu(),
                duSamplingTask.getYiChaoShu(),
                duSamplingTask.getTongBuBZ());
    }


    private DUTask chaoBiaoRW2DUTask(ChaoBiaoRW chaoBiaoRW) {
        return new DUTask(
                chaoBiaoRW.getID(),
                chaoBiaoRW.getI_RenWuBH(),
                chaoBiaoRW.getS_ChaoBiaoYBH(),
                chaoBiaoRW.getD_PaiFaSJ(),
                chaoBiaoRW.getS_ChaoBiaoYXM(),
                chaoBiaoRW.getI_ZhangWuNY(),
                chaoBiaoRW.getS_CH(),
                chaoBiaoRW.getS_CeBenMC(),
                chaoBiaoRW.getI_GongCi(),
                chaoBiaoRW.getS_ST(),
                chaoBiaoRW.getI_ZongShu(),
                chaoBiaoRW.getI_YiChaoShu(),
                chaoBiaoRW.getS_CHAOBIAOZQ(),
                0,
                0,
                0);
    }

    private DUSamplingTask JiChaRW2DUSamlingTask(JiChaRW jiChaRW) {
        return new DUSamplingTask(
                jiChaRW.getID(),
                jiChaRW.getI_RenWuBH(),
                jiChaRW.getS_ChaoBiaoYBH(),
                jiChaRW.getD_PaiFaSJ(),
                jiChaRW.getS_ChaoBiaoYXM(),
                jiChaRW.getI_ZhangWuNY(),
                jiChaRW.getI_GongCi(),
                jiChaRW.getS_ST(),
                jiChaRW.getI_ZongShu(),
                jiChaRW.getI_YiChaoShu(),
                jiChaRW.getI_TongBuBZ());
    }


    private BIAOKAXX duCard2BiaoKaXX(DUCard duCard) {
        return new BIAOKAXX(
                duCard.getId(),
                duCard.getCh(),
                duCard.getCeneixh(),
                duCard.getCid(),
                duCard.getKehubh(),
                duCard.getKehumc(),
                duCard.getSt(),
                duCard.getDizhi(),
                duCard.getLianxir(),
                duCard.getLianxisj(),
                duCard.getLianxidh(),
                duCard.getShoufeifs(),
                duCard.getYinhangmc(),
                duCard.getJianhao(),
                duCard.getJianhaomc(),
                duCard.getYonghuzt(),
                duCard.getLihu(),
                duCard.getBiaowei(),
                duCard.getShuibiaogyh(),
                duCard.getShuibiaotxm(),
                duCard.getKoujingmc(),
                duCard.getLiangcheng(),
                duCard.getBiaoxing(),
                duCard.getShuibiaocj(),
                duCard.getShuibiaofli(),
                duCard.getShuibiaoflmc(),
                duCard.getShuibiaobl(),
                duCard.getKaizhangfl(),
                duCard.getGongnengfl(),
                duCard.getShifoujhys(),
                duCard.getShifoushouljf(),
                duCard.getLajifeixs(),
                duCard.getShifoushouwyj(),
                duCard.getShifoudejj(),
                duCard.getDingesl(),
                duCard.getZongbiaobh(),
                duCard.getZhuangbiaorq(),
                duCard.getHuanbiaorq(),
                duCard.getXinbiaodm(),
                duCard.getJiubiaocm(),
                duCard.getX1(),
                duCard.getY1(),
                duCard.getX(),
                duCard.getY(),
                duCard.getFentanfs(),
                duCard.getFentanl(),
                duCard.getYucunkye(),
                duCard.getQianfeizbs(),
                duCard.getQianfeizje(),
                duCard.getBeizhu(),
                duCard.getShuibiaozt(),
                duCard.getRenkous(),
                duCard.getDibaoyhsl(),
                duCard.getGongceyhsl(),
                duCard.getYongshuizkl(),
                duCard.getPaishuizkl(),
                duCard.getZhekoul1(),
                duCard.getZhekoul2(),
                duCard.getZhekoul3(),
                duCard.getErcigs(),
                duCard.getDianzizd(),
                duCard.getXingzhengq(),
                duCard.getBiaokazt(),
                duCard.getSheshuiid(),
                duCard.getJiage(),
                duCard.getShuibiaolxbh(),
                duCard.getZizhuangbkzxs(),
                duCard.getShuibiaozl(),
                duCard.getShuibiaofls(),
                duCard.getYuanchuanid(),
                duCard.getZhongduanh(),
                duCard.getYuanchuancj(),
                duCard.getDuorenkfa(),
                duCard.getShifoujt(),
                duCard.getDuorenkjz(),
                duCard.getGongshuihtnx(),
                duCard.getFangdongdh(),
                duCard.getFangkedh(),
                duCard.getNianleij(),
                duCard.getHuanbiao(),
                duCard.getQianfei()
        );
    }

    private DUCard biaoKaXX2DUCard(BIAOKAXX biaokaxx) {
        return new DUCard(
                biaokaxx.getID(),
                biaokaxx.getS_CH(),
                biaokaxx.getI_CENEIXH(),
                biaokaxx.getS_CID(),
                biaokaxx.getS_KEHUBH(),
                biaokaxx.getS_KeHuMC(),
                biaokaxx.getS_ST(),
                biaokaxx.getS_DiZhi(),
                biaokaxx.getS_LIANXIR(),
                biaokaxx.getS_LIANXISJ(),
                biaokaxx.getS_LIANXIDH(),
                biaokaxx.getS_ShouFeiFS(),
                biaokaxx.getS_YinHangMC(),
                biaokaxx.getS_JianHao(),
                biaokaxx.getS_JianHaoMC(),
                biaokaxx.getI_YongHuZT(),
                biaokaxx.getD_LIHU(),
                biaokaxx.getS_BiaoWei(),
                biaokaxx.getS_SHUIBIAOGYH(),
                biaokaxx.getS_SHUIBIAOTXM(),
                biaokaxx.getS_KOUJINGMC(),
                biaokaxx.getI_LIANGCHENG(),
                biaokaxx.getS_BIAOXING(),
                biaokaxx.getS_SHUIBIAOCJ(),
                biaokaxx.getI_ShuiBiaoFL(),
                biaokaxx.getS_ShuiBiaoFLMC(),
                biaokaxx.getI_SHUIBIAOBL(),
                biaokaxx.getS_KaiZhangFL(),
                biaokaxx.getI_GONGNENGFL(),
                biaokaxx.getI_ShiFouJHYS(),
                biaokaxx.getI_ShiFouShouLJF(),
                biaokaxx.getN_LaJiFeiXS(),
                biaokaxx.getI_ShiFouShouWYJ(),
                biaokaxx.getI_ShiFouDEJJ(),
                biaokaxx.getI_DINGESL(),
                biaokaxx.getS_ZONGBIAOBH(),
                biaokaxx.getD_ZHUANGBIAORQ(),
                biaokaxx.getD_HUANBIAORQ(),
                biaokaxx.getI_XINBIAODM(),
                biaokaxx.getI_JIUBIAOCM(),
                biaokaxx.getS_X1(),
                biaokaxx.getS_Y1(),
                biaokaxx.getS_X(),
                biaokaxx.getS_Y(),
                biaokaxx.getI_FENTANFS(),
                biaokaxx.getI_FENTANL(),
                biaokaxx.getN_YuCunKYE(),
                biaokaxx.getI_QianFeiZBS(),
                biaokaxx.getN_QianFeiZJE(),
                biaokaxx.getS_BEIZHU(),
                biaokaxx.getI_SHUIBIAOZT(),
                biaokaxx.getN_RENKOUS(),
                biaokaxx.getI_DIBAOYHSL(),
                biaokaxx.getI_GONGCEYHSL(),
                biaokaxx.getN_YONGSHUIZKL(),
                biaokaxx.getN_PAISHUIZKL(),
                biaokaxx.getN_ZHEKOUL1(),
                biaokaxx.getN_ZHEKOUL2(),
                biaokaxx.getN_ZHEKOUL3(),
                biaokaxx.getI_ERCIGS(),
                biaokaxx.getI_DIANZIZD(),
                biaokaxx.getS_XINGZHENGQ(),
                biaokaxx.getS_BIAOKAZT(),
                biaokaxx.getS_SHESHUIID(),
                biaokaxx.getN_JIAGE(),
                biaokaxx.getS_SHUIBIAOLXBH(),
                biaokaxx.getN_ZIZHUANGBKZXS(),
                biaokaxx.getS_SHUIBIAOZL(),
                biaokaxx.getS_SHUIBIAOFL(),
                biaokaxx.getS_YUANCHUANID(),
                biaokaxx.getS_ZHONGDUANH(),
                biaokaxx.getS_YUANCHUANCJ(),
                biaokaxx.getS_DUORENKFA(),
                biaokaxx.getI_SHIFOUJT(),
                biaokaxx.getD_DUORENKJZ(),
                biaokaxx.getD_GONGSHUIHTNX(),
                biaokaxx.getS_FANGDONGDH(),
                biaokaxx.getS_FANGKEDH(),
                biaokaxx.getI_NIANLEIJ(),
                biaokaxx.getI_HUANBIAO(),
                biaokaxx.getI_QIANFEI(),
                0,
                0,
                0,
                0,
                "",
                "",
                "",
                "",
                0L,
                0L,
                "",
                "",
                "",
                0L,
                0,
                0,
                0,
                0,
                0,
                "");
    }

    private ChaoBiaoSJ duRecord2ChaoBiaoSJ(DURecord duRecord) {
        return new ChaoBiaoSJ(
                duRecord.getId(),
                duRecord.getRenwubh(),
                duRecord.getCh(),
                duRecord.getCeneixh(),
                duRecord.getCid(),
                duRecord.getSt(),
                duRecord.getChaobiaon(),
                duRecord.getIchaobiaoy(),
                duRecord.getCc(),
                duRecord.getChaobiaorq(),
                duRecord.getShangcicm(),
                duRecord.getBencicm(),
                duRecord.getChaojiansl(),
                duRecord.getZhuangtaibm(),
                duRecord.getZhuangtaimc(),
                duRecord.getShangcicbrq(),
                duRecord.getShangciztbm(),
                duRecord.getShangciztmc(),
                duRecord.getShangcicjsl(),
                duRecord.getShangciztlxs(),
                duRecord.getPingjunl1(),
                duRecord.getPingjunl2(),
                duRecord.getPingjunl3(),
                duRecord.getJe(),
                duRecord.getZongbiaocid(),
                duRecord.getSchaobiaoy(),
                duRecord.getIchaobiaobz(),
                duRecord.getJiubiaocm(),
                duRecord.getXinbiaodm(),
                duRecord.getHuanbiaorq(),
                duRecord.getFangshibm(),
                duRecord.getLianggaoldyybm(),
                duRecord.getChaobiaoid(),
                duRecord.getZhuangtailxs(),
                duRecord.getShuibiaobl(),
                duRecord.getYongshuizkl(),
                duRecord.getPaishuizkl(),
                duRecord.getTiaojiah(),
                duRecord.getJianhao(),
                duRecord.getXiazaisj(),
                duRecord.getLingyongslsm(),
                duRecord.getLianggaosl(),
                duRecord.getLiangdisl(),
                duRecord.getX1(),
                duRecord.getY1(),
                duRecord.getX(),
                duRecord.getY(),
                duRecord.getSchaobiaobz(),
                duRecord.getCeneipx(),
                duRecord.getXiazaics(),
                duRecord.getZuihouycxzsj(),
                duRecord.getZuihouycscsj(),
                duRecord.getShangchuanbz(),
                duRecord.getShenhebz(),
                duRecord.getKaizhangbz(),
                duRecord.getDiaodongbz(),
                duRecord.getWaifuyybh(),
                duRecord.getJietits(),
                duRecord.getYanciyy(),
                duRecord.getI_SHANGGEDBZQTS(),
                duRecord.getD_SHANGSHANGGYCBRQ()
        );
    }

    private YanChiBiao duDelayRecord2YanChiBiao(DUDelayRecord delayRecord) {
        return new YanChiBiao(
                delayRecord.getID(),
                delayRecord.getI_RENWUBH(),
                delayRecord.getI_CHAOBIAOID(),
                delayRecord.getS_CID(),
                delayRecord.getI_CHAOJIANSL(),
                delayRecord.getI_SHANGCICM(),
                delayRecord.getI_CHAOHUICM(),
                delayRecord.getI_ZHUANGTAIBM(),
                delayRecord.getS_ZHUANGTAIMC(),
                delayRecord.getI_LIANGGAOLDBM(),
                delayRecord.getD_CHAOBIAORQ(),
                delayRecord.getI_CHAOBIAON(),
                delayRecord.getI_CHAOBIAOY(),
                delayRecord.getS_CHAOBIAOY(),
                delayRecord.getI_FANGSHIBM(),
                delayRecord.getI_CHAOBIAOZT(),
                delayRecord.getD_SHANGCICBRQ(),
                delayRecord.getS_ST(),
                delayRecord.getS_CH(),
                delayRecord.getI_CENEIXH(),
                delayRecord.getI_JIUBIAOCM(),
                delayRecord.getI_XINBIAODM(),
                delayRecord.getD_HUANBIAORQ(),
                delayRecord.getD_HUANBIAOHTSJ(),
                delayRecord.getD_DENGJISJ(),
                delayRecord.getI_ZHUANGTAI(),
                delayRecord.getS_YANCHIYBH(),
                delayRecord.getS_HUITIANYBH(),
                delayRecord.getD_HUITIANSJ(),
                delayRecord.getS_CHULIQK(),
                delayRecord.getS_CAOZUOR(),
                delayRecord.getD_CAOZUOSJ(),
                delayRecord.getI_HUANBIAOFS(),
                delayRecord.getS_CHAOBIAOBZ(),
                delayRecord.getS_SHUIBIAOTXM(),
                delayRecord.getS_YANCHIYY(),
                delayRecord.getI_ZHENSHICM(),
                delayRecord.getI_LINYONGSLSM(),
                delayRecord.getS_X(),
                delayRecord.getS_Y(),
                delayRecord.getI_YANCHILX(),
                delayRecord.getS_YANCHIYBH(),
                delayRecord.getI_CHAOBIAOBZ(),
                delayRecord.getI_ShangChuanBZ(),
                delayRecord.getI_KaiZhangBZ(),
                delayRecord.getS_JH(),
                delayRecord.getI_LIANGGAOSL(),
                delayRecord.getI_LIANGDISL(),
                delayRecord.getI_PINGJUNL1(),
                delayRecord.getI_SHANGCISL(),
                delayRecord.getN_JE(),
                delayRecord.getS_JIETITS(),
                delayRecord.getS_ShangCiZTBM(),
                delayRecord.getI_SHANGGEDBZQTS(),
                delayRecord.getD_SHANGSHANGGYCBRQ()
        );
    }

    private WaiFuCBSJ duWaiFuCBSJ2WaiFuCBSJ(DUWaiFuCBSJ duWaiFuCBSJ) {
        return new WaiFuCBSJ(
                duWaiFuCBSJ.getId(),
                duWaiFuCBSJ.getRenwubh(),
                duWaiFuCBSJ.getCh(),
                duWaiFuCBSJ.getCeneixh(),
                duWaiFuCBSJ.getCid(),
                duWaiFuCBSJ.getSt(),
                duWaiFuCBSJ.getChaobiaon(),
                duWaiFuCBSJ.getIchaobiaoy(),
                duWaiFuCBSJ.getCc(),
                duWaiFuCBSJ.getChaobiaorq(),
                duWaiFuCBSJ.getShangcicm(),
                duWaiFuCBSJ.getBencicm(),
                duWaiFuCBSJ.getChaojiansl(),
                duWaiFuCBSJ.getZhuangtaibm(),
                duWaiFuCBSJ.getZhuangtaimc(),
                duWaiFuCBSJ.getShangcicbrq(),
                duWaiFuCBSJ.getShangciztbm(),
                duWaiFuCBSJ.getShangciztmc(),
                duWaiFuCBSJ.getShangcicjsl(),
                duWaiFuCBSJ.getShangciztlxs(),
                duWaiFuCBSJ.getPingjunl1(),
                duWaiFuCBSJ.getPingjunl2(),
                duWaiFuCBSJ.getPingjunl3(),
                duWaiFuCBSJ.getJe(),
                duWaiFuCBSJ.getZongbiaocid(),
                duWaiFuCBSJ.getSchaobiaoy(),
                duWaiFuCBSJ.getIchaobiaobz(),
                duWaiFuCBSJ.getJiubiaocm(),
                duWaiFuCBSJ.getXinbiaodm(),
                duWaiFuCBSJ.getHuanbiaorq(),
                duWaiFuCBSJ.getFangshibm(),
                duWaiFuCBSJ.getLianggaoldyybm(),
                duWaiFuCBSJ.getChaobiaoid(),
                duWaiFuCBSJ.getZhuangtailxs(),
                duWaiFuCBSJ.getShuibiaobl(),
                duWaiFuCBSJ.getYongshuizkl(),
                duWaiFuCBSJ.getPaishuizkl(),
                duWaiFuCBSJ.getTiaojiah(),
                duWaiFuCBSJ.getJianhao(),
                duWaiFuCBSJ.getXiazaisj(),
                duWaiFuCBSJ.getLingyongslsm(),
                duWaiFuCBSJ.getLianggaosl(),
                duWaiFuCBSJ.getLiangdisl(),
                duWaiFuCBSJ.getX1(),
                duWaiFuCBSJ.getY1(),
                duWaiFuCBSJ.getX(),
                duWaiFuCBSJ.getY(),
                duWaiFuCBSJ.getSchaobiaobz(),
                duWaiFuCBSJ.getCeneipx(),
                duWaiFuCBSJ.getXiazaics(),
                duWaiFuCBSJ.getZuihouycxzsj(),
                duWaiFuCBSJ.getZuihouycscsj(),
                duWaiFuCBSJ.getShangchuanbz(),
                duWaiFuCBSJ.getShenhebz(),
                duWaiFuCBSJ.getKaizhangbz(),
                duWaiFuCBSJ.getDiaodongbz(),
                duWaiFuCBSJ.getWaifuyybh(),
                duWaiFuCBSJ.getJietits(),
                duWaiFuCBSJ.getYanciyy(),
                duWaiFuCBSJ.getLastReadingChild(),
                duWaiFuCBSJ.getReadingChild(),
                duWaiFuCBSJ.getCheckOutsideType());
    }


    private DURecord chaoBiaoSJ2DURecord(ChaoBiaoSJ chaoBiaoSJ) {
        return new DURecord(
                chaoBiaoSJ.getID(),
                chaoBiaoSJ.getI_RenWuBH(),
                chaoBiaoSJ.getS_CH(),
                chaoBiaoSJ.getI_CENEIXH(),
                chaoBiaoSJ.getS_CID(),
                chaoBiaoSJ.getS_ST(),
                chaoBiaoSJ.getI_CHAOBIAON(),
                chaoBiaoSJ.getI_CHAOBIAOY(),
                chaoBiaoSJ.getI_CC(),
                chaoBiaoSJ.getD_CHAOBIAORQ(),
                chaoBiaoSJ.getI_SHANGCICM(),
                chaoBiaoSJ.getI_BENCICM(),
                chaoBiaoSJ.getI_CHAOJIANSL(),
                chaoBiaoSJ.getI_ZHUANGTAIBM(),
                chaoBiaoSJ.getS_ZHUANGTAIMC(),
                chaoBiaoSJ.getD_SHANGCICBRQ(),
                chaoBiaoSJ.getI_SHANGCIZTBM(),
                chaoBiaoSJ.getS_ShangCiZTMC(),
                chaoBiaoSJ.getI_ShangCiCJSL(),
                chaoBiaoSJ.getI_SHANGCIZTLXS(),
                chaoBiaoSJ.getI_PINGJUNL1(),
                chaoBiaoSJ.getI_PINGJUNL2(),
                chaoBiaoSJ.getI_PINGJUNL3(),
                chaoBiaoSJ.getN_JE(),
                chaoBiaoSJ.getS_ZONGBIAOCID(),
                chaoBiaoSJ.getS_CHAOBIAOY(),
                chaoBiaoSJ.getI_CHAOBIAOBZ(),
                chaoBiaoSJ.getI_JIUBIAOCM(),
                chaoBiaoSJ.getI_XINBIAODM(),
                chaoBiaoSJ.getD_HUANBIAORQ(),
                chaoBiaoSJ.getI_FANGSHIBM(),
                chaoBiaoSJ.getI_LIANGGAOLDYYBM(),
                chaoBiaoSJ.getI_CHAOBIAOID(),
                chaoBiaoSJ.getI_ZHUANGTAILXS(),
                chaoBiaoSJ.getI_SHUIBIAOBL(),
                chaoBiaoSJ.getN_YONGSHUIZKL(),
                chaoBiaoSJ.getN_PAISHUIZKL(),
                chaoBiaoSJ.getI_TIAOJIAH(),
                chaoBiaoSJ.getS_JianHao(),
                chaoBiaoSJ.getD_XIAZAISJ(),
                chaoBiaoSJ.getI_LINGYONGSLSM(),
                chaoBiaoSJ.getI_LIANGGAOSL(),
                chaoBiaoSJ.getI_LIANGDISL(),
                chaoBiaoSJ.getS_X1(),
                chaoBiaoSJ.getS_Y1(),
                chaoBiaoSJ.getS_X(),
                chaoBiaoSJ.getS_Y(),
                chaoBiaoSJ.getS_CHAOBIAOBZ(),
                chaoBiaoSJ.getI_CeNeiPX(),
                chaoBiaoSJ.getI_XiaZaiCS(),
                chaoBiaoSJ.getD_ZuiHouYCXZSJ(),
                chaoBiaoSJ.getD_ZuiHouYCSCSJ(),
                chaoBiaoSJ.getI_ShangChuanBZ(),
                chaoBiaoSJ.getI_ShenHeBZ(),
                chaoBiaoSJ.getI_KaiZhangBZ(),
                chaoBiaoSJ.getI_DiaoDongBZ(),
                chaoBiaoSJ.getI_WaiFuYYBH(),
                chaoBiaoSJ.getS_JIETITS(),
                chaoBiaoSJ.getS_YANCIYY(),
                0,
                0,
                0,
                0,
                0,
                "",
                0,
                0,
                chaoBiaoSJ.getI_SHANGGEDBZQTS(),
                chaoBiaoSJ.getD_SHANGSHANGGYCBRQ()
        );
    }

    private DUDelayRecord yanChiBiao2DUDelayRecord(YanChiBiao chaoBiaoSJ) {
        return new DUDelayRecord(
                chaoBiaoSJ.getID(),
                chaoBiaoSJ.getI_RENWUBH(),
                chaoBiaoSJ.getI_CHAOBIAOID(),
                chaoBiaoSJ.getS_CID(),
                chaoBiaoSJ.getI_CHAOJIANSL(),
                chaoBiaoSJ.getI_SHANGCICM(),
                chaoBiaoSJ.getI_CHAOHUICM(),
                chaoBiaoSJ.getI_ZHUANGTAIBM(),
                chaoBiaoSJ.getS_ZHUANGTAIMC(),
                chaoBiaoSJ.getI_LIANGGAOLDBM(),
                chaoBiaoSJ.getD_CHAOBIAORQ(),
                chaoBiaoSJ.getI_CHAOBIAON(),
                chaoBiaoSJ.getI_CHAOBIAOY(),
                chaoBiaoSJ.getS_CHAOBIAOY(),
                chaoBiaoSJ.getI_FANGSHIBM(),
                chaoBiaoSJ.getI_CHAOBIAOZT(),
                chaoBiaoSJ.getD_SHANGCICBRQ(),
                chaoBiaoSJ.getS_ST(),
                chaoBiaoSJ.getS_CH(),
                chaoBiaoSJ.getI_CENEIXH(),
                chaoBiaoSJ.getI_JIUBIAOCM(),
                chaoBiaoSJ.getI_XINBIAODM(),
                chaoBiaoSJ.getD_HUANBIAORQ(),
                chaoBiaoSJ.getD_HUANBIAOHTSJ(),
                chaoBiaoSJ.getD_DENGJISJ(),
                chaoBiaoSJ.getI_ZHUANGTAI(),
                chaoBiaoSJ.getS_YANCHIYBH(),
                chaoBiaoSJ.getS_HUITIANYBH(),
                chaoBiaoSJ.getD_HUITIANSJ(),
                chaoBiaoSJ.getS_CHULIQK(),
                chaoBiaoSJ.getS_CAOZUOR(),
                chaoBiaoSJ.getD_CAOZUOSJ(),
                chaoBiaoSJ.getI_HUANBIAOFS(),
                chaoBiaoSJ.getS_CHAOBIAOBZ(),
                chaoBiaoSJ.getS_SHUIBIAOTXM(),
                chaoBiaoSJ.getS_YANCHIYY(),
                chaoBiaoSJ.getI_ZHENSHICM(),
                chaoBiaoSJ.getI_LINYONGSLSM(),
                chaoBiaoSJ.getS_X(),
                chaoBiaoSJ.getS_Y(),
                chaoBiaoSJ.getI_YANCHILX(),
                chaoBiaoSJ.getS_YANCHIBH(),
                chaoBiaoSJ.getI_CHAOBIAOBZ(),
                chaoBiaoSJ.getI_ShangChuanBZ(),
                chaoBiaoSJ.getI_KaiZhangBZ(),
                chaoBiaoSJ.getS_JH(),
                chaoBiaoSJ.getI_LIANGGAOSL(),
                chaoBiaoSJ.getI_LIANGDISL(),
                chaoBiaoSJ.getI_PINGJUNL1(),
                chaoBiaoSJ.getI_SHANGCISL(),
                chaoBiaoSJ.getN_JE(),
                chaoBiaoSJ.getS_JIETITS(),
                chaoBiaoSJ.getS_ShangCiZTBM(),
                chaoBiaoSJ.getI_SHANGGEDBZQTS(),
                chaoBiaoSJ.getD_SHANGSHANGGYCBRQ()
        );
    }

    private com.sh3h.dataprovider.entity.JianHaoMX JianHaoMX2JianHaoMX(JianHaoMX jiChaRW) {
        return new com.sh3h.dataprovider.entity.JianHaoMX(
                jiChaRW.getID(),
                jiChaRW.getI_TIAOJIAH(),
                jiChaRW.getS_JIANHAO(),
                jiChaRW.getI_FEIYONGDLID(),
                jiChaRW.getI_FEIYONGID(),
                jiChaRW.getI_QISHIY(),
                jiChaRW.getI_JIESHUY(),
                jiChaRW.getI_KAISHISL(),
                jiChaRW.getI_JIESHUSL(),
                jiChaRW.getI_JIETIJB(),
                jiChaRW.getN_JIAGE(),
                jiChaRW.getN_ZHEKOUL(),
                jiChaRW.getI_ZHEKOULX(),
                jiChaRW.get_feiYongMC(),
                jiChaRW.getxiShu(),
                jiChaRW.getjieTiS(),
                jiChaRW.getdaLei(),
                jiChaRW.getzhongLei());
    }

    private DUSamplingTask jiChaRW2DUSamplingTask(JiChaRW jiChaRW) {
        return new DUSamplingTask(
                jiChaRW.getID(),
                jiChaRW.getI_RenWuBH(),
                jiChaRW.getS_ChaoBiaoYBH(),
                jiChaRW.getD_PaiFaSJ(),
                jiChaRW.getS_ChaoBiaoYXM(),
                jiChaRW.getI_ZhangWuNY(),
                jiChaRW.getI_GongCi(),
                jiChaRW.getS_ST(),
                jiChaRW.getI_ZongShu(),
                jiChaRW.getI_YiChaoShu(),
                jiChaRW.getI_TongBuBZ());
    }

    private RushPayRW duRushPayTask2RushPayRW(DURushPayTask duRushPayTask) {
        return new RushPayRW(
                duRushPayTask.getID(),
                duRushPayTask.getI_TaskId(),
                duRushPayTask.getS_CardId(),
                duRushPayTask.getS_CardName(),
                duRushPayTask.getS_CardAddress(),
                duRushPayTask.getS_SubComCode(),
                duRushPayTask.getD_QfMonths(),
                duRushPayTask.getD_QfMoney(),
                duRushPayTask.getI_IsFinish(),
                duRushPayTask.getS_MeterReader(),
                duRushPayTask.getS_ReceiptRemark(),
                duRushPayTask.getL_ReceiptTime(),
                duRushPayTask.getS_ReviewPerson(),
                duRushPayTask.getL_UploadTime(),
                duRushPayTask.getI_IsUpload(),
                duRushPayTask.getI_ISComplete()
        );
    }

    private DURushPayTask rushPayRW2DURushPayTask(RushPayRW rushPayRW) {
        return new DURushPayTask(
                rushPayRW.getID(),
                rushPayRW.getI_TaskId(),
                rushPayRW.getS_CardId(),
                rushPayRW.getS_CardName(),
                rushPayRW.getS_CardAddress(),
                rushPayRW.getS_SubComCode(),
                rushPayRW.getD_QfMonths(),
                rushPayRW.getD_QfMoney(),
                rushPayRW.getI_IsFinish(),
                rushPayRW.getS_MeterReader(),
                rushPayRW.getS_ReceiptRemark(),
                rushPayRW.getL_ReceiptTime(),
                rushPayRW.getS_ReviewPerson(),
                rushPayRW.getL_UploadTime(),
                rushPayRW.getI_IsUpload(),
                rushPayRW.getI_ISComplete()
        );
    }

    private DUWaiFuCBSJ waiFuCBSJ2DUWaiFuCBSJ(WaiFuCBSJ waiFuCBSJ) {
        return new DUWaiFuCBSJ(
                waiFuCBSJ.getID(),
                waiFuCBSJ.getI_RenWuBH(),
                waiFuCBSJ.getS_CH(),
                waiFuCBSJ.getI_CENEIXH(),
                waiFuCBSJ.getS_CID(),
                waiFuCBSJ.getS_ST(),
                waiFuCBSJ.getI_CHAOBIAON(),
                waiFuCBSJ.getI_CHAOBIAOY(),
                waiFuCBSJ.getI_CC(),
                waiFuCBSJ.getD_CHAOBIAORQ(),
                waiFuCBSJ.getI_SHANGCICM(),
                waiFuCBSJ.getI_BENCICM(),
                waiFuCBSJ.getI_CHAOJIANSL(),
                waiFuCBSJ.getI_ZHUANGTAIBM(),
                waiFuCBSJ.getS_ZHUANGTAIMC(),
                waiFuCBSJ.getD_SHANGCICBRQ(),
                waiFuCBSJ.getI_SHANGCIZTBM(),
                waiFuCBSJ.getS_ShangCiZTMC(),
                waiFuCBSJ.getI_ShangCiCJSL(),
                waiFuCBSJ.getI_SHANGCIZTLXS(),
                waiFuCBSJ.getI_PINGJUNL1(),
                waiFuCBSJ.getI_PINGJUNL2(),
                waiFuCBSJ.getI_PINGJUNL3(),
                waiFuCBSJ.getN_JE(),
                waiFuCBSJ.getS_ZONGBIAOCID(),
                waiFuCBSJ.getS_CHAOBIAOY(),
                waiFuCBSJ.getI_CHAOBIAOBZ(),
                waiFuCBSJ.getI_JIUBIAOCM(),
                waiFuCBSJ.getI_XINBIAODM(),
                waiFuCBSJ.getD_HUANBIAORQ(),
                waiFuCBSJ.getI_FANGSHIBM(),
                waiFuCBSJ.getI_LIANGGAOLDYYBM(),
                waiFuCBSJ.getI_CHAOBIAOID(),
                waiFuCBSJ.getI_ZHUANGTAILXS(),
                waiFuCBSJ.getI_SHUIBIAOBL(),
                waiFuCBSJ.getN_YONGSHUIZKL(),
                waiFuCBSJ.getN_PAISHUIZKL(),
                waiFuCBSJ.getI_TIAOJIAH(),
                waiFuCBSJ.getS_JianHao(),
                waiFuCBSJ.getD_XIAZAISJ(),
                waiFuCBSJ.getI_LINGYONGSLSM(),
                waiFuCBSJ.getI_LIANGGAOSL(),
                waiFuCBSJ.getI_LIANGDISL(),
                waiFuCBSJ.getS_X1(),
                waiFuCBSJ.getS_Y1(),
                waiFuCBSJ.getS_X(),
                waiFuCBSJ.getS_Y(),
                waiFuCBSJ.getS_CHAOBIAOBZ(),
                waiFuCBSJ.getI_CeNeiPX(),
                waiFuCBSJ.getI_XiaZaiCS(),
                waiFuCBSJ.getD_ZuiHouYCXZSJ(),
                waiFuCBSJ.getD_ZuiHouYCSCSJ(),
                waiFuCBSJ.getI_ShangChuanBZ(),
                waiFuCBSJ.getI_ShenHeBZ(),
                waiFuCBSJ.getI_KaiZhangBZ(),
                waiFuCBSJ.getI_DiaoDongBZ(),
                waiFuCBSJ.getI_WaiFuYYBH(),
                waiFuCBSJ.getS_JIETITS(),
                waiFuCBSJ.getS_YANCIYY(),
                waiFuCBSJ.getLastReadingChild(),
                waiFuCBSJ.getReadingChild(),
                waiFuCBSJ.getCheckOutSideType());
    }

    private JiChaSJ duSampling2JiChaSJ(DUSampling duSampling) {
        return new JiChaSJ(
                duSampling.getId(),
                duSampling.getRenwubh(),
                duSampling.getCh(),
                duSampling.getCeneixh(),
                duSampling.getCid(),
                duSampling.getSt(),
                duSampling.getChaobiaon(),
                duSampling.getIchaobiaoy(),
                duSampling.getCc(),
                duSampling.getChaobiaorq(),
                duSampling.getShangcicm(),
                duSampling.getBencicm(),
                duSampling.getChaojiansl(),
                duSampling.getZhuangtaibm(),
                duSampling.getZhuangtaimc(),
                duSampling.getShangcicbrq(),
                duSampling.getShangciztbm(),
                duSampling.getShangciztmc(),
                duSampling.getShangcicjsl(),
                duSampling.getShangciztlxs(),
                duSampling.getPingjunl1(),
                duSampling.getPingjunl2(),
                duSampling.getPingjunl3(),
                duSampling.getJe(),
                duSampling.getZongbiaocid(),
                duSampling.getSchaobiaoy(),
                duSampling.getIchaobiaobz(),
                duSampling.getJiubiaocm(),
                duSampling.getXinbiaodm(),
                duSampling.getHuanbiaorq(),
                duSampling.getFangshibm(),
                duSampling.getLianggaoldyybm(),
                duSampling.getChaobiaoid(),
                duSampling.getZhuangtailxs(),
                duSampling.getShuibiaobl(),
                duSampling.getYongshuizkl(),
                duSampling.getPaishuizkl(),
                duSampling.getTiaojiah(),
                duSampling.getJianhao(),
                duSampling.getXiazaisj(),
                duSampling.getLingyongslsm(),
                duSampling.getLianggaosl(),
                duSampling.getLiangdisl(),
                duSampling.getX1(),
                duSampling.getY1(),
                duSampling.getX(),
                duSampling.getY(),
                duSampling.getSchaobiaobz(),
                duSampling.getCeneipx(),
                duSampling.getXiazaics(),
                duSampling.getZuihouycxzsj(),
                duSampling.getZuihouycscsj(),
                duSampling.getShangchuanbz(),
                duSampling.getShenhebz(),
                duSampling.getKaizhangbz(),
                duSampling.getDiaodongbz(),
                duSampling.getWaifuyybh(),
                duSampling.getJietits(),
                duSampling.getYanciyy(),
                duSampling.getLastReadingChild(),
                duSampling.getReadingChild(), duSampling.getJiChaSL(), duSampling.getJiChaCM(), duSampling.getJiChaRQ(), duSampling.getJiChaZTBM(), duSampling.getJiChaZTMC());
    }


    private DUSampling jiChaSJ2DUSampling(JiChaSJ jiChaSJ) {
        return new DUSampling(
                jiChaSJ.getID(),
                jiChaSJ.getI_RenWuBH(),
                jiChaSJ.getS_CH(),
                jiChaSJ.getI_CENEIXH(),
                jiChaSJ.getS_CID(),
                jiChaSJ.getS_ST(),
                jiChaSJ.getI_CHAOBIAON(),
                jiChaSJ.getI_CHAOBIAOY(),
                jiChaSJ.getI_CC(),
                jiChaSJ.getD_CHAOBIAORQ(),
                jiChaSJ.getI_SHANGCICM(),
                jiChaSJ.getI_BENCICM(),
                jiChaSJ.getI_CHAOJIANSL(),
                jiChaSJ.getI_ZHUANGTAIBM(),
                jiChaSJ.getS_ZHUANGTAIMC(),
                jiChaSJ.getD_SHANGCICBRQ(),
                jiChaSJ.getI_SHANGCIZTBM(),
                jiChaSJ.getS_ShangCiZTMC(),
                jiChaSJ.getI_ShangCiCJSL(),
                jiChaSJ.getI_SHANGCIZTLXS(),
                jiChaSJ.getI_PINGJUNL1(),
                jiChaSJ.getI_PINGJUNL2(),
                jiChaSJ.getI_PINGJUNL3(),
                jiChaSJ.getN_JE(),
                jiChaSJ.getS_ZONGBIAOCID(),
                jiChaSJ.getS_CHAOBIAOY(),
                jiChaSJ.getI_CHAOBIAOBZ(),
                jiChaSJ.getI_JIUBIAOCM(),
                jiChaSJ.getI_XINBIAODM(),
                jiChaSJ.getD_HUANBIAORQ(),
                jiChaSJ.getI_FANGSHIBM(),
                jiChaSJ.getI_LIANGGAOLDYYBM(),
                jiChaSJ.getI_CHAOBIAOID(),
                jiChaSJ.getI_ZHUANGTAILXS(),
                jiChaSJ.getI_SHUIBIAOBL(),
                jiChaSJ.getN_YONGSHUIZKL(),
                jiChaSJ.getN_PAISHUIZKL(),
                jiChaSJ.getI_TIAOJIAH(),
                jiChaSJ.getS_JianHao(),
                jiChaSJ.getD_XIAZAISJ(),
                jiChaSJ.getI_LINGYONGSLSM(),
                jiChaSJ.getI_LIANGGAOSL(),
                jiChaSJ.getI_LIANGDISL(),
                jiChaSJ.getS_X1(),
                jiChaSJ.getS_Y1(),
                jiChaSJ.getS_X(),
                jiChaSJ.getS_Y(),
                jiChaSJ.getS_CHAOBIAOBZ(),
                jiChaSJ.getI_CeNeiPX(),
                jiChaSJ.getI_XiaZaiCS(),
                jiChaSJ.getD_ZuiHouYCXZSJ(),
                jiChaSJ.getD_ZuiHouYCSCSJ(),
                jiChaSJ.getI_ShangChuanBZ(),
                jiChaSJ.getI_ShenHeBZ(),
                jiChaSJ.getI_KaiZhangBZ(),
                jiChaSJ.getI_DiaoDongBZ(),
                jiChaSJ.getI_WaiFuYYBH(),
                jiChaSJ.getS_JIETITS(),
                jiChaSJ.getS_YANCIYY(),
                jiChaSJ.getI_LASTREADINGCHILD(),
                jiChaSJ.getI_READINGCHILD(),
                jiChaSJ.getI_JiChaCM(),
                jiChaSJ.getI_JiChaSL(),
                jiChaSJ.getI_JiChaZTBM(),
                jiChaSJ.getD_JiChaCBRQ(),
                jiChaSJ.getS_JiChaZTMC()
        );
    }

    private DULgld chaoBiaoSJ2DULgld(ChaoBiaoSJ chaoBiaoSJ) {
        return new DULgld(
                chaoBiaoSJ.getS_CID(),
                chaoBiaoSJ.getI_SHANGCICM(),
                chaoBiaoSJ.getI_BENCICM(),
                chaoBiaoSJ.getI_CHAOJIANSL(),
                chaoBiaoSJ.getS_ZHUANGTAIMC(),
                chaoBiaoSJ.getI_CHAOBIAOBZ(),
                chaoBiaoSJ.getI_CENEIXH()
        );
    }

    private DULgld yanChiBiao2DULgld(YanChiBiao yanChiBiao) {
        return new DULgld(
                yanChiBiao.getS_CID(),
                yanChiBiao.getI_SHANGCICM(),
                yanChiBiao.getI_CHAOHUICM(),
                yanChiBiao.getI_CHAOJIANSL(),
                yanChiBiao.getS_ZHUANGTAIMC(),
                yanChiBiao.getI_CHAOBIAOBZ(),
                yanChiBiao.getI_CENEIXH()
        );
    }

    private YanChiBiao dUDelayRecord2YanChiBiao(DUDelayRecord record) {
        return new YanChiBiao(
                record.getID(),
                record.getI_RENWUBH(),
                record.getI_CHAOBIAOID(),
                record.getS_CID(),
                record.getI_CHAOJIANSL(),
                record.getI_SHANGCICM(),
                record.getI_CHAOHUICM(),
                record.getI_ZHUANGTAIBM(),
                record.getS_ZHUANGTAIMC(),
                record.getI_LIANGGAOLDBM(),
                record.getD_CHAOBIAORQ(),
                record.getI_CHAOBIAON(),
                record.getI_CHAOBIAOY(),
                record.getS_CHAOBIAOY(),
                record.getI_FANGSHIBM(),
                record.getI_CHAOBIAOZT(),
                record.getD_SHANGCICBRQ(),
                record.getS_ST(),
                record.getS_CH(),
                record.getI_CENEIXH(),
                record.getI_JIUBIAOCM(),
                record.getI_XINBIAODM(),
                record.getD_HUANBIAORQ(),
                record.getD_HUANBIAOHTSJ(),
                record.getD_DENGJISJ(),
                record.getI_ZHUANGTAI(),
                record.getS_YANCHIYBH(),
                record.getS_HUITIANYBH(),
                record.getD_HUITIANSJ(),
                record.getS_CHULIQK(),
                record.getS_CAOZUOR(),
                record.getD_CAOZUOSJ(),
                record.getI_HUANBIAOFS(),
                record.getS_CHAOBIAOBZ(),
                record.getS_SHUIBIAOTXM(),
                record.getS_YANCHIYY(),
                record.getI_ZHENSHICM(),
                record.getI_LINYONGSLSM(),
                record.getS_X(),
                record.getS_Y(),
                record.getI_YANCHILX(),
                record.getS_YANCHIBH(),
                record.getI_CHAOBIAOBZ(),
                record.getI_ShangChuanBZ(),
                record.getI_KaiZhangBZ(),
                record.getS_JH(),
                record.getI_LIANGGAOSL(),
                record.getI_LIANGDISL(),
                record.getI_PINGJUNL1(),
                record.getI_SHANGCISL(),
                record.getN_JE(),
                record.getS_JIETITS(),
                record.getS_ShangCiZTBM(),
                record.getI_SHANGGEDBZQTS(),
                record.getD_SHANGSHANGGYCBRQ()
        );
    }

    public TraceInfo biaoKaXX2TraceInfo(BIAOKAXX biaoKaXX) {
        if (!isDouble(biaoKaXX.getS_X1()) || !isDouble(biaoKaXX.getS_Y1())) {
            return null;
        }

        return new TraceInfo(Double.parseDouble(biaoKaXX.getS_X1()), Double.parseDouble(biaoKaXX.getS_Y1()),
                biaoKaXX.getI_CENEIXH(), null, false);
    }

    public TraceInfo chaoBiaoSJ2TraceInfo(ChaoBiaoSJ chaoBiaoSJ) {
        if (!isDouble(chaoBiaoSJ.getS_X1()) || !isDouble(chaoBiaoSJ.getS_Y1())) {
            return null;
        }

        return new TraceInfo(Double.parseDouble(chaoBiaoSJ.getS_X1()), Double.parseDouble(chaoBiaoSJ.getS_Y1()),
                chaoBiaoSJ.getI_CENEIXH(), null, true);
    }

    //判断浮点数（double和float）
    private boolean isDouble(String str) {
        if (TextUtil.isNullOrEmpty(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

}
