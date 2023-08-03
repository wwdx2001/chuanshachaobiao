package com.sh3h.datautil.data.remote;


import com.sh3h.datautil.data.entity.DUBillPreview;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardCoordinateInfo;
import com.sh3h.datautil.data.entity.DUCardCoordinateResult;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUChaoBiaoJL;
import com.sh3h.datautil.data.entity.DUChaoBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUCombined;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.datautil.data.entity.DUDelayCardInfo;
import com.sh3h.datautil.data.entity.DUDelayChaoBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUDelayHuanBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUDelayIdInfo;
import com.sh3h.datautil.data.entity.DUDelayJiaoFeiXXInfo;
import com.sh3h.datautil.data.entity.DUDelayQianFeiInfo;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.datautil.data.entity.DUDelayRecordInfo;
import com.sh3h.datautil.data.entity.DUDeviceInfo;
import com.sh3h.datautil.data.entity.DUDeviceResult;
import com.sh3h.datautil.data.entity.DUHuanBiaoJL;
import com.sh3h.datautil.data.entity.DUHuanBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUJiaoFeiXX;
import com.sh3h.datautil.data.entity.DUJiaoFeiXXInfo;
import com.sh3h.datautil.data.entity.DUKeepAlive;
import com.sh3h.datautil.data.entity.DUKeepAliveInfo;
import com.sh3h.datautil.data.entity.DUKeepAliveResult;
import com.sh3h.datautil.data.entity.DULoginInfo;
import com.sh3h.datautil.data.entity.DULoginResult;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUQianFeiXX;
import com.sh3h.datautil.data.entity.DUQianFeiXXInfo;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DURushPayTask;
import com.sh3h.datautil.data.entity.DURushPayTaskInfo;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingInfo;
import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.entity.DUSamplingTaskInfo;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskIdInfo;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.entity.DUUpdateInfo;
import com.sh3h.datautil.data.entity.DUUpdateResult;
import com.sh3h.datautil.data.entity.DUUploadingFile;
import com.sh3h.datautil.data.entity.DUUploadingFileResult;
import com.sh3h.datautil.data.entity.DUUserInfo;
import com.sh3h.datautil.data.entity.DUUserResult;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJInfo;
import com.sh3h.datautil.util.ZipUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.BiaoKaXXEntity;
import com.sh3h.serverprovider.entity.BillPreviewEntity;
import com.sh3h.serverprovider.entity.CalculateBillEntity;
import com.sh3h.serverprovider.entity.ChaoBiaoJLEntity;
import com.sh3h.serverprovider.entity.ChaoBiaoRWEntity;
import com.sh3h.serverprovider.entity.ChaoBiaoSJEntity;
import com.sh3h.serverprovider.entity.ClientInfoEntity;
import com.sh3h.serverprovider.entity.CombinedInfoEntity;
import com.sh3h.serverprovider.entity.DelayInfoEntity;
import com.sh3h.serverprovider.entity.DeviceInfoEntity;
import com.sh3h.serverprovider.entity.HuanBiaoXXEntity;
import com.sh3h.serverprovider.entity.JiChaRWEntity;
import com.sh3h.serverprovider.entity.JiChaSJEntity;
import com.sh3h.serverprovider.entity.JiaoFeiXXEntity;
import com.sh3h.serverprovider.entity.KeepAliveEntity;
import com.sh3h.serverprovider.entity.KeepAliveInfoEntity;
import com.sh3h.serverprovider.entity.LoginInfoEntity;
import com.sh3h.serverprovider.entity.LoginResultEntity;
import com.sh3h.serverprovider.entity.QianFeiXXEntity;
import com.sh3h.serverprovider.entity.RegisterResultEntity;
import com.sh3h.serverprovider.entity.RenWuXXEntity;
import com.sh3h.serverprovider.entity.RushPaySJEntity;
import com.sh3h.serverprovider.entity.RushPaySJInfoEntity;
import com.sh3h.serverprovider.entity.RushPaySJResultEntity;
import com.sh3h.serverprovider.entity.UpdateInfoEntity;
import com.sh3h.serverprovider.entity.UserInfoEntity;
import com.sh3h.serverprovider.entity.WaiFuSJEntity;
import com.sh3h.serverprovider.rpc.service.BaseApiService;
import com.sh3h.serverprovider.rpc.service.BusinessApiService;
import com.sh3h.serverprovider.rpc.service.SynchronousTaskApiService;
import com.sh3h.serverprovider.rpc.service.SystemApiService;
import com.sh3h.serverprovider.rpc.service.UserApiService;
import com.sh3h.serverprovider.rpc.service.VersionApiService;
import com.sh3h.serverprovider.rpc.service.WorkApiService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class JsonRpcService {
    public JsonRpcService() {
//        businessApiService = new BusinessApiService();
//        synchronousTaskApiService = new SynchronousTaskApiService();
//        systemApiService = new SystemApiService();
//        workApiService = new WorkApiService();
    }

    public void init(String baseUrl) {
        BaseApiService.setBaseURL(baseUrl);
    }

    public void setDebug(boolean isDebug) {
        BaseApiService.setDebug(isDebug);
    }

    /**
     * authorize
     *
     * @param duDeviceInfo
     * @return
     */
    public Observable<DUDeviceResult> authorize(final DUDeviceInfo duDeviceInfo) {
        return Observable.create(new Observable.OnSubscribe<DUDeviceResult>() {
            @Override
            public void call(Subscriber<? super DUDeviceResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duDeviceInfo == null)
                            || TextUtil.isNullOrEmpty(duDeviceInfo.getDeviceID())
                            || TextUtil.isNullOrEmpty(duDeviceInfo.getMacAddress())) {
                        throw new NullPointerException("duDeviceInfo contains null pointer");
                    }

                    DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
                    deviceInfoEntity.setDeviceID(duDeviceInfo.getDeviceID());
                    deviceInfoEntity.setMACAddress(duDeviceInfo.getMacAddress());
                    SystemApiService systemApiService = new SystemApiService();
                    RegisterResultEntity registerResultEntity =
                            systemApiService.registerMachine(deviceInfoEntity);
                    if (registerResultEntity != null) {
                        if (registerResultEntity.getSuccessed()) {
                            DUDeviceResult duDeviceResult = new DUDeviceResult(
                                    registerResultEntity.getSuccessed(),
                                    registerResultEntity.getCode(),
                                    registerResultEntity.getError(),
                                    registerResultEntity.getJiHuoZT(),
                                    registerResultEntity.getJiHuoSJ(),
                                    registerResultEntity.getSheBeiZT());
                            subscriber.onNext(duDeviceResult);
                        } else {
                            String error = registerResultEntity.getError();
                            if (TextUtil.isNullOrEmpty(error)) {
                                error = "";
                            }

                            subscriber.onError(new Throwable(String.format("error: %s", error)));
                        }
                    } else {
                        subscriber.onError(new Throwable("the return value is null"));
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
     * login
     *
     * @param duLoginInfo
     * @return
     */
    public Observable<DULoginResult> login(final DULoginInfo duLoginInfo) {
        return Observable.create(new Observable.OnSubscribe<DULoginResult>() {
            @Override
            public void call(Subscriber<? super DULoginResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duLoginInfo == null)
                            || TextUtil.isNullOrEmpty(duLoginInfo.getAccount())
                            || TextUtil.isNullOrEmpty(duLoginInfo.getPassword())) {
                        throw new NullPointerException("duLoginInfo contains null pointer");
                    }

                    LoginInfoEntity loginInfoEntity = new LoginInfoEntity();
                    loginInfoEntity.setAccount(duLoginInfo.getAccount());
                    loginInfoEntity.setPassword(duLoginInfo.getPassword());
                    UserApiService userApiService = new UserApiService();
                    LoginResultEntity loginResultEntity = userApiService.Login(loginInfoEntity);
                    if (loginResultEntity != null) {
                        if (loginResultEntity.isSuccessed()) {
                            DULoginResult duLoginResult = new DULoginResult(
                                    loginResultEntity.getError(),
                                    loginResultEntity.get_UserID());
                            subscriber.onNext(duLoginResult);
                        } else {
                            subscriber.onError(new Throwable(String.format("error: %d",
                                    loginResultEntity.getError())));
                        }
                    } else {
                        subscriber.onError(new Throwable("the returned value is null"));
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
     * get user information
     *
     * @param duUserInfo
     * @return
     */
    public Observable<DUUserResult> getUserInfo(final DUUserInfo duUserInfo) {
        return Observable.create(new Observable.OnSubscribe<DUUserResult>() {
            @Override
            public void call(Subscriber<? super DUUserResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duUserInfo == null) {
                        throw new NullPointerException("duUserInfo contains null pointer");
                    }

                    UserApiService userApiService = new UserApiService();
                    UserInfoEntity userInfoEntity = userApiService.getUserInfo(duUserInfo.getUserId());
                    if (userInfoEntity != null) {
                        DUUserResult duLoginResult = new DUUserResult(
                                userInfoEntity.getUserId(),
                                userInfoEntity.getUserName(),
                                userInfoEntity.getAccount(),
                                userInfoEntity.getPWS(),
                                userInfoEntity.getCellPhone(),
                                userInfoEntity.getPhone(),
                                userInfoEntity.getAddress());
                        subscriber.onNext(duLoginResult);
                    } else {
                        subscriber.onError(new Throwable("the returned value is null"));
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
     * @param duUpdateInfo
     * @return
     */
    public Observable<DUUpdateResult> updateVersion(final DUUpdateInfo duUpdateInfo) {
        return Observable.create(new Observable.OnSubscribe<DUUpdateResult>() {
            @Override
            public void call(Subscriber<? super DUUpdateResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duUpdateInfo == null) {
                        throw new NullPointerException("duUpdateInfo is null");
                    }

                    ClientInfoEntity clientInfoEntity = new ClientInfoEntity();
                    clientInfoEntity.setAppVersion(duUpdateInfo.getAppVersion());
                    clientInfoEntity.setDataVersion(duUpdateInfo.getDataVersion());
                    VersionApiService versionApiService = new VersionApiService();
                    UpdateInfoEntity updateInfoEntity = versionApiService.hasNewUpdate(clientInfoEntity);
                    if (updateInfoEntity != null) {
                        DUUpdateResult duUpdateResult = new DUUpdateResult();
                        List<UpdateInfoEntity.Item> srcItems = updateInfoEntity.getItems();
                        List<DUUpdateResult.Item> destItems = new ArrayList<>();
                        for (UpdateInfoEntity.Item srcItem : srcItems) {
                            if (srcItem.getType() == UpdateInfoEntity.ItemType.App) {
                                continue;
                            }
                            DUUpdateResult.Item destItem = new DUUpdateResult.Item(srcItem.getType().ordinal(),
                                    srcItem.isEnable(), srcItem.getVersion(), srcItem.getDesc(),
                                    srcItem.getUrl());
                            destItems.add(destItem);
                        }
                        duUpdateResult.setItemList(destItems);
                        duUpdateResult.setDuUpdateInfo(duUpdateInfo);
                        subscriber.onNext(duUpdateResult);
                    } else {
                        subscriber.onError(new Throwable("userInfoEntity is null"));
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
     * get task ids
     *
     * @param duTaskIdInfo
     * @return
     */
    public Observable<RenWuXXEntity> getTaskIds(final DUTaskIdInfo duTaskIdInfo) {
        return Observable.create(new Observable.OnSubscribe<RenWuXXEntity>() {
            @Override
            public void call(Subscriber<? super RenWuXXEntity> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duTaskIdInfo == null)
                            || TextUtil.isNullOrEmpty(duTaskIdInfo.getAccount())) {
                        throw new NullPointerException("duTaskIdInfo contains null pointer");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    RenWuXXEntity renWuXXEntity
                            = synchronousTaskApiService.getRenWuBHByChaoBiaoY(duTaskIdInfo.getAccount());
                    if ((renWuXXEntity != null)
                            && (renWuXXEntity.get_message() != null)
                            && (renWuXXEntity.get_message().equals(RenWuXXEntity.SUCCESS_MESSAGE))
                            && (renWuXXEntity.get_renwus() != null)
                            && (renWuXXEntity.get_chaobiaojbh() != null)) {
                        subscriber.onNext(renWuXXEntity);
                    } else {
                        subscriber.onError(new Throwable("renWuXXEntity is null"));
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
     * get delay ids
     *
     * @param duDelayIdInfo
     * @return
     */
    public Observable<List<String>> getDelayIds(final DUDelayIdInfo duDelayIdInfo) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duDelayIdInfo == null)
                            || TextUtil.isNullOrEmpty(duDelayIdInfo.getAccount())) {
                        throw new NullPointerException("duTaskIdInfo contains null pointer");
                    }

                    WorkApiService workApiService = new WorkApiService();
                    String delayBHs = workApiService
                            .DownloadDelayRenwuBH(duDelayIdInfo.getAccount(), duDelayIdInfo.getDeviceId());
                    if (delayBHs != null) {
                        List<String> taskIdList = new ArrayList<>();
                        String[] ids = delayBHs.split(",");
                        for (String id : ids) {
                            if (id.equals("") || id.equals(",")) {
                                continue;
                            }
                            taskIdList.add(id);
                        }

                        subscriber.onNext(taskIdList);
                    } else {
                        subscriber.onError(new Throwable("delayBHs is null"));
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
     * get sampling task ids
     *
     * @param duTaskIdInfo
     * @return
     */
    public Observable<List<String>> getSamplingTaskIds(final DUTaskIdInfo duTaskIdInfo) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duTaskIdInfo == null)
                            || TextUtil.isNullOrEmpty(duTaskIdInfo.getAccount())) {
                        throw new NullPointerException("duTaskIdInfo contains null pointer");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    RenWuXXEntity renWuXXEntity = synchronousTaskApiService.getJichaRenWuBHByChaoBiaoY(duTaskIdInfo.getAccount());
                    if ((renWuXXEntity != null)
                            && (renWuXXEntity.get_message() != null)
                            && (renWuXXEntity.get_message().equals(RenWuXXEntity.SUCCESS_MESSAGE))
                            && (renWuXXEntity.get_renwus() != null)) {
                        List<String> taskIdList = new ArrayList<>();

                        String taskIds = renWuXXEntity.get_renwus();
                        String[] ids = taskIds.split(",");
                        for (String id : ids) {
                            if (id.equals("") || id.equals(",")) {
                                continue;
                            }
                            taskIdList.add(id);
                        }

                        subscriber.onNext(taskIdList);
                    } else {
                        subscriber.onError(new Throwable("renWuXXEntity is null"));
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
     * getRushPayTasks
     *
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<List<DURushPayTask>> getRushPayTasks(final DURushPayTaskInfo duRushPayTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DURushPayTask>>() {
            @Override
            public void call(Subscriber<? super List<DURushPayTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duRushPayTaskInfo == null) {
                        throw new NullPointerException("duRushPayTaskInfo is null");
                    }
                    String account = duRushPayTaskInfo.getAccount();
                    if (TextUtil.isNullOrEmpty(account)) {
                        throw new NullPointerException("duJiChaTaskInfo contains null pointer");
                    }
                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    List<RushPaySJEntity> rushPaySJEntities =
                            synchronousTaskApiService.downLoadRushPays(account);
                    if (rushPaySJEntities != null) {
                        List<DURushPayTask> duRushPayTasks = new ArrayList<>();
                        for (RushPaySJEntity rushPaySJEntity : rushPaySJEntities) {
                            DURushPayTask duRushPayTask = new DURushPayTask(
                                    -1,
                                    rushPaySJEntity.getTaskId(),
                                    rushPaySJEntity.getCardId(),
                                    rushPaySJEntity.getCardName(),
                                    rushPaySJEntity.getCardAddress(),
                                    rushPaySJEntity.getSubcomCode(),
                                    rushPaySJEntity.getQfMonths(),
                                    rushPaySJEntity.getQfMoney(),
                                    rushPaySJEntity.getIsFinish(),
                                    rushPaySJEntity.getMeterReader(),
                                    rushPaySJEntity.getReceiptRemark(),
                                    rushPaySJEntity.getReceiptTime(),
                                    rushPaySJEntity.getReviewPerson(),
                                    rushPaySJEntity.getUploadTime(),
                                    0,
                                    rushPaySJEntity.getIsComplete()
                            );
                            duRushPayTasks.add(duRushPayTask);
                        }
                        subscriber.onNext(duRushPayTasks);
                    } else {
                        subscriber.onError(new Throwable("rushPaySJEntities is null"));
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
     * getRushPayTaskIds
     *
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<List<String>> getRushPayTaskIds(final DURushPayTaskInfo duRushPayTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duRushPayTaskInfo == null)
                            || TextUtil.isNullOrEmpty(duRushPayTaskInfo.getAccount())) {
                        throw new NullPointerException("duRushPayTaskInfo contains null pointer");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    RenWuXXEntity renWuXXEntity = synchronousTaskApiService.getRushPayRenWuBHByChaoBiaoY(duRushPayTaskInfo.getAccount());
                    if ((renWuXXEntity != null)
                            && (renWuXXEntity.get_message() != null)
                            && (renWuXXEntity.get_message().equals(RenWuXXEntity.SUCCESS_MESSAGE))
                            && (renWuXXEntity.get_renwus() != null)) {
                        List<String> taskIdList = new ArrayList<>();

                        String taskIds = renWuXXEntity.get_renwus();
                        String[] ids = taskIds.split(",");
                        for (String id : ids) {
                            if (id.equals("") || id.equals(",")) {
                                continue;
                            }
                            taskIdList.add(id);
                        }

                        subscriber.onNext(taskIdList);
                    } else {
                        subscriber.onError(new Throwable("renWuXXEntity is null"));
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
                    if (duTaskInfo == null) {
                        throw new NullPointerException("duTaskInfo is null");
                    }

                    String account = duTaskInfo.getAccount();
                    int taskId = duTaskInfo.getTaskId();
                    String deviceid = duTaskInfo.getDeviceId();

                    if (TextUtil.isNullOrEmpty(account)
                            || TextUtil.isNullOrEmpty(deviceid)) {
                        throw new NullPointerException("duTaskInfo contains null pointer");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    List<ChaoBiaoRWEntity> chaoBiaoRWEntities =
                            synchronousTaskApiService.downLoadChaoBiaoRW(taskId, deviceid, account);
                    if (chaoBiaoRWEntities != null) {
                        List<DUTask> duTaskList = new ArrayList<>();
                        for (ChaoBiaoRWEntity chaoBiaoRWEntity : chaoBiaoRWEntities) {
                            DUTask duTask = new DUTask(
                                    chaoBiaoRWEntity.getId(),
                                    chaoBiaoRWEntity.getRenWuBH(),
                                    chaoBiaoRWEntity.getChaoBiaoYBH(),
                                    chaoBiaoRWEntity.getPaiFaSJ(),
                                    chaoBiaoRWEntity.getchaoBiaoYXM(),
                                    chaoBiaoRWEntity.getZhangWuNY(),
                                    chaoBiaoRWEntity.getCH(),
                                    chaoBiaoRWEntity.getCeBenMC(),
                                    chaoBiaoRWEntity.getGongCi(),
                                    chaoBiaoRWEntity.getST(),
                                    chaoBiaoRWEntity.getZongShu(),
                                    chaoBiaoRWEntity.getYiChaoShu(),
                                    chaoBiaoRWEntity.getChaoBiaoZQ(),
                                    0,
                                    ChaoBiaoRWEntity.CHAOBIAO_TASK,
                                    chaoBiaoRWEntity.get_groupId());
                            duTaskList.add(duTask);
                        }

                        subscriber.onNext(duTaskList);
                    } else {
                        subscriber.onError(new Throwable("chaoBiaoRWEntities is null"));
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
     * getSamplingTasks
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
                    if (duSamplingTaskInfo == null) {
                        throw new NullPointerException("duSamplingTaskInfo is null");
                    }

                    String account = duSamplingTaskInfo.getAccount();
                    int taskId = duSamplingTaskInfo.getTaskId();
                    String deviceId = duSamplingTaskInfo.getDeviceId();

                    if (TextUtil.isNullOrEmpty(account)
                            || TextUtil.isNullOrEmpty(deviceId)) {
                        throw new NullPointerException("duSamplingTaskInfo contains null pointer");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    List<JiChaRWEntity> jiChaRWEntities =
                            synchronousTaskApiService.downLoadJiChaRW(taskId, deviceId, account);
                    if (jiChaRWEntities != null) {
                        List<DUSamplingTask> duSamplingTasks = new ArrayList<>();
                        for (JiChaRWEntity jiChaRWEntity : jiChaRWEntities) {
                            DUSamplingTask duTask = new DUSamplingTask(
                                    jiChaRWEntity.getId(),
                                    jiChaRWEntity.getRenWuBH(),
                                    jiChaRWEntity.getChaoBiaoYBH(),
                                    jiChaRWEntity.getPaiFaSJ(),
                                    jiChaRWEntity.getchaoBiaoYXM(),
                                    jiChaRWEntity.getZhangWuNY(),
                                    jiChaRWEntity.getGongCi(),
                                    jiChaRWEntity.getST(),
                                    jiChaRWEntity.getZongShu(),
                                    jiChaRWEntity.getYiChaoShu(),
                                    0);
                            duSamplingTasks.add(duTask);
                        }

                        subscriber.onNext(duSamplingTasks);
                    } else {
                        subscriber.onError(new Throwable("jiChaRWEntities is null"));
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
     * uploadRushPays
     *
     * @param duRushPayTaskInfo
     * @return
     */
    public Observable<List<DURushPayTask>> uploadRushPays(final DURushPayTaskInfo duRushPayTaskInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DURushPayTask>>() {
            @Override
            public void call(Subscriber<? super List<DURushPayTask>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duRushPayTaskInfo == null) {
                        throw new NullPointerException("duRushPayTaskInfo is null");
                    }

                    List<DURushPayTask> duRushPayTasks = duRushPayTaskInfo.getDuRushPayTaskList();
                    if (duRushPayTasks == null) {
                        throw new NullPointerException("duRushPayTasks is null");
                    }

                    List<RushPaySJInfoEntity> rushPaySJInfoEntities = new ArrayList<>();
                    for (DURushPayTask duRushPayTask : duRushPayTasks) {
                        if (duRushPayTask.getI_ISComplete() == DURushPayTask.COMPLETE
                                && duRushPayTask.getI_IsUpload() == DURushPayTask.SHANGCHUANBZ_WEISHANGC) {
                            RushPaySJInfoEntity rushPaySJInfoEntity = new RushPaySJInfoEntity(
                                    duRushPayTask.getI_TaskId(),
                                    duRushPayTask.getS_ReceiptRemark(),
                                    duRushPayTask.getL_ReceiptTime(),
                                    duRushPayTask.getS_ReviewPerson(),
                                    new Date().getTime()
                            );
                            rushPaySJInfoEntities.add(rushPaySJInfoEntity);
                        }

                    }
                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    List<RushPaySJResultEntity> rushPaySJResultEntities =
                            synchronousTaskApiService.updateRushPaySJToServer(rushPaySJInfoEntities,
                                    duRushPayTaskInfo.getAccount());

                    for (DURushPayTask duRushPayTask : duRushPayTasks) {
                        duRushPayTask.setI_IsUpload(DURushPayTask.SHANGCHUANBZ_WEISHANGC);
                        if (rushPaySJResultEntities != null) {
                            for (RushPaySJResultEntity rushPaySJResultEntity : rushPaySJResultEntities) {
                                if ((duRushPayTask.getI_TaskId() == rushPaySJResultEntity.getTaskId())
                                        && rushPaySJResultEntity.isSuccess()) {
                                    duRushPayTask.setI_IsUpload(DURushPayTask.SHANGCHUANBZ_YISHANGC);
                                    break;
                                }
                            }
                        }
                    }

                    subscriber.onNext(duRushPayTasks);
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
     * uploadWaiFuSJs
     *
     * @param duWaiFuCBSJInfo
     * @return
     */
    public Observable<List<DUWaiFuCBSJ>> uploadWaiFuSJs(final DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUWaiFuCBSJ>>() {
            @Override
            public void call(Subscriber<? super List<DUWaiFuCBSJ>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duWaiFuCBSJInfo == null) {
                        throw new NullPointerException("duWaiFuCBSJInfo is null");
                    }

                    List<DUWaiFuCBSJ> duWaiFuCBSJs = duWaiFuCBSJInfo.getDuWaiFuCBSJList();
                    if (duWaiFuCBSJs == null) {
                        throw new NullPointerException("duWaiFuCBSJs is null");
                    }

                    List<WaiFuSJEntity> srcWaiFuSJEntityList = new ArrayList<>();
                    for (DUWaiFuCBSJ duWaiFuCBSJ : duWaiFuCBSJs) {
                        WaiFuSJEntity waiFuSJEntity = new WaiFuSJEntity(
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
                                duWaiFuCBSJ.getLastReadingChild(),
                                duWaiFuCBSJ.getReadingChild(),
                                duWaiFuCBSJ.getCheckOutsideType());
                        srcWaiFuSJEntityList.add(waiFuSJEntity);
                    }
                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    List<WaiFuSJEntity> destWaiFuSJEntities =
                            synchronousTaskApiService.updateWaiFuSJToServer(srcWaiFuSJEntityList,
                                    duWaiFuCBSJInfo.getAccount());

                    for (DUWaiFuCBSJ duWaiFuCBSJ : duWaiFuCBSJs) {
                        duWaiFuCBSJ.setShangchuanbz(DURushPayTask.SHANGCHUANBZ_WEISHANGC);
                        if (destWaiFuSJEntities != null) {
                            for (WaiFuSJEntity waiFuSJEntity : destWaiFuSJEntities) {
                                if ((duWaiFuCBSJ.getRenwubh() == waiFuSJEntity.getI_RenWuBH())
                                        && (duWaiFuCBSJ.getCh().equals(waiFuSJEntity.getCH()))
                                        && (duWaiFuCBSJ.getCid().equals(waiFuSJEntity.getCID()))) {
                                    duWaiFuCBSJ.setShangchuanbz(DURecord.SHANGCHUANBZ_YISHANGC);
                                    break;
                                }
                            }
                        }
                    }

                    subscriber.onNext(duWaiFuCBSJs);
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
                    if (duRecordInfo == null) {
                        throw new NullPointerException("duRecordInfo is null");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    int taskId = duRecordInfo.getTaskId();
                    List<ChaoBiaoSJEntity> chaoBiaoSJEntityList =
                            synchronousTaskApiService.downLoadChaoBiaoSJ(taskId);
                    if (chaoBiaoSJEntityList != null) {
                        List<DURecord> duRecordList = new ArrayList<>();
                        for (ChaoBiaoSJEntity chaoBiaoSJEntity : chaoBiaoSJEntityList) {
                            //TODO LIBAO 2018.7.26 因为报错，后台修改返回全是0
//                            if (chaoBiaoSJEntity.getI_RenWuBH()!=0) {
                            DURecord duRecord = new DURecord(
                                    -1,
                                    chaoBiaoSJEntity.getI_RenWuBH(),
                                    chaoBiaoSJEntity.getCH(),
                                    chaoBiaoSJEntity.getCeNeiXH(),
                                    chaoBiaoSJEntity.getCID(),
                                    chaoBiaoSJEntity.getST(),
                                    chaoBiaoSJEntity.getChaoBiaoN(),
                                    chaoBiaoSJEntity.getChaoBiaoYue(),
                                    chaoBiaoSJEntity.getChaoCi(),
                                    chaoBiaoSJEntity.getChaoBiaoRQ(),
                                    chaoBiaoSJEntity.getShangciCM(),
                                    chaoBiaoSJEntity.getBenCiCM(),
                                    chaoBiaoSJEntity.getChaoJIanSL(),
                                    chaoBiaoSJEntity.getZhuangTaiBM(),
                                    chaoBiaoSJEntity.getZhuangTaiMC(),
                                    chaoBiaoSJEntity.getShangciCBRQ(),
                                    chaoBiaoSJEntity.getShangCiZTBM(),
                                    chaoBiaoSJEntity.getShangCiZTMC(),
                                    chaoBiaoSJEntity.getShangCiCJSL(),
                                    chaoBiaoSJEntity.getShangCiZTLXS(),
                                    chaoBiaoSJEntity.getPingJunL1(),
                                    chaoBiaoSJEntity.getPingJunL2(),
                                    chaoBiaoSJEntity.getPingJunL3(),
                                    chaoBiaoSJEntity.getJE(),
                                    chaoBiaoSJEntity.getZongBiaoCID(),
                                    chaoBiaoSJEntity.getChaoBiaoY(),
                                    chaoBiaoSJEntity.getChaoBiaoBZ(),
                                    chaoBiaoSJEntity.getJiuBiaoCM(),
                                    chaoBiaoSJEntity.getXinBiaoDM(),
                                    chaoBiaoSJEntity.getHuanBiaoRQ(),
                                    chaoBiaoSJEntity.getFangShiBM(),
                                    chaoBiaoSJEntity.getLiangGaoLDYYBM(),
                                    chaoBiaoSJEntity.getChaoBiaoID(),
                                    chaoBiaoSJEntity.getZhuangTaiLXS(),
                                    chaoBiaoSJEntity.getShuiBiaoBL(),
                                    chaoBiaoSJEntity.getYongShuiZKL(),
                                    chaoBiaoSJEntity.getPaiShuiZKL(),
                                    chaoBiaoSJEntity.getTiaoJiaH(),
                                    chaoBiaoSJEntity.getJianHao(),
                                    chaoBiaoSJEntity.getXiaZaiSJ(),
                                    chaoBiaoSJEntity.getLingYongSLSM(),
                                    chaoBiaoSJEntity.getLiangGaoSL(),
                                    chaoBiaoSJEntity.getLiangDiSL(),
                                    chaoBiaoSJEntity.getX1(),
                                    chaoBiaoSJEntity.getY1(),
                                    chaoBiaoSJEntity.getX(),
                                    chaoBiaoSJEntity.getY(),
                                    chaoBiaoSJEntity.getChaoBiaoBeiZhu(),
                                    chaoBiaoSJEntity.getceNeiPX(),
                                    chaoBiaoSJEntity.getI_XiaZaiCS(),
                                    chaoBiaoSJEntity.getD_ZuiHouYCXZSJ(),
                                    chaoBiaoSJEntity.getD_ZuiHouYCSCSJ(),
                                    chaoBiaoSJEntity.getI_ShangChuanBZ(),
                                    chaoBiaoSJEntity.getI_ShenHeBZ(),
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    chaoBiaoSJEntity.getI_LastReadingChild(),
                                    chaoBiaoSJEntity.getI_ReadingChild(),
                                    ChaoBiaoSJEntity.CHAOBIAOSJ,
                                    chaoBiaoSJEntity.getI_GroupId(),
                                    chaoBiaoSJEntity.getI_SortIndex(),
                                    chaoBiaoSJEntity.getS_CardType(),
                                    chaoBiaoSJEntity.getI_IsRead(),
                                    chaoBiaoSJEntity.getI_ReasonUnRead(),
                                    chaoBiaoSJEntity.getI_SHANGGEDBZQTS(),
                                    chaoBiaoSJEntity.getD_SHANGSHANGGYCBRQ()
                            );
                            duRecordList.add(duRecord);
                        }
//                        }

                        subscriber.onNext(duRecordList);
                    } else {
                        subscriber.onError(new Throwable("chaoBiaoSJEntityList is null"));
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
    public Observable<List<DUDelayRecord>> getDelayRecords(final DUDelayRecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUDelayRecord>>() {
            @Override
            public void call(Subscriber<? super List<DUDelayRecord>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duRecordInfo == null) {
                        throw new NullPointerException("duRecordInfo is null");
                    }

                    WorkApiService workApiService = new WorkApiService();
                    List<DelayInfoEntity> list =
                            workApiService.DownloadDelayInfos(duRecordInfo.getAccount(), duRecordInfo.getDeviceId());
                    if (list != null) {
                        List<DUDelayRecord> duRecordList = new ArrayList<>();
                        for (DelayInfoEntity delayInfo : list) {
                            DUDelayRecord duRecord = new DUDelayRecord(
                                    -1L,
                                    delayInfo.getRENWUBH(),
                                    delayInfo.getCHAOBIAOID(),
                                    delayInfo.getCID(),
                                    delayInfo.getChaoJianSL(),
                                    delayInfo.getShangCiCM(),
                                    delayInfo.getCHAOHUICM(),
                                    delayInfo.getZHUANGTAIBM(),
                                    delayInfo.getZHUANGTAIMC(),
                                    delayInfo.getLIANGGAOLDBM(),
                                    delayInfo.getChaoBiaoRQ(),
                                    delayInfo.getChaoBiaoN(),
                                    delayInfo.getChaoBiaoY(),
                                    delayInfo.getCHAOBIAOY(),
                                    delayInfo.getFANGSHIBM(),
                                    delayInfo.getCHAOBIAOZT(),
                                    delayInfo.getSHANGCICBRQ(),
                                    delayInfo.getST(),
                                    delayInfo.getCH(),
                                    delayInfo.getCeNeiXH(),
                                    delayInfo.getJIUBIAOCM(),
                                    delayInfo.getXINBIAODM(),
                                    delayInfo.getHUANBIAORQ(),
                                    delayInfo.getHUANBIAOHTSJ(),
                                    delayInfo.getDENGJISJ(),
                                    delayInfo.getZHUANGTAI(),
                                    delayInfo.getYANCHIYBH(),
                                    delayInfo.getHUITIANYBH(),
                                    delayInfo.getHUITIANSJ(),
                                    delayInfo.getCHULIQK(),
                                    delayInfo.getCAOZUOR(),
                                    delayInfo.getCAOZUOSJ(),
                                    delayInfo.getHUANBIAOFS(),
                                    delayInfo.getCHAOBIAOBZ(),
                                    delayInfo.getSHUIBIAOTXM(),
                                    delayInfo.getYANCHIYY(),
                                    delayInfo.getI_ZHENSHICM(),
                                    delayInfo.getLINYONGSLSM(),
                                    delayInfo.getX(),
                                    delayInfo.getY(),
                                    delayInfo.getYANCHILX(),
                                    delayInfo.getYANCHIBH(),
                                    delayInfo.getI_CHAOBIAOBZ(),
                                    delayInfo.getI_ShangChuanBZ(),
                                    delayInfo.getI_KaiZhangBZ(),
                                    delayInfo.getJH(),
                                    delayInfo.getLIANGGAOSL(),
                                    delayInfo.getLIANGDISL(),
                                    delayInfo.getPINGJUNL1(),
                                    delayInfo.getSHANGCISL(),
                                    delayInfo.getJE(),
                                    delayInfo.getS_JIETITS(),
                                    delayInfo.getS_ShangCiZTBM(),
                                    delayInfo.getI_SHANGGEDBZQTS(),
                                    delayInfo.getD_SHANGSHANGGYCBRQ()
                            );
                            duRecordList.add(duRecord);
                        }

                        subscriber.onNext(duRecordList);
                    } else {
                        subscriber.onError(new Throwable("list is null"));
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
     * @param duWaiFuCBSJInfo
     * @return
     */
    public Observable<List<DUWaiFuCBSJ>> getWaiFuCBSJs(final DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUWaiFuCBSJ>>() {
            @Override
            public void call(Subscriber<? super List<DUWaiFuCBSJ>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duWaiFuCBSJInfo == null) {
                        throw new NullPointerException("duWaiFuCBSJInfo is null");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    String account = duWaiFuCBSJInfo.getAccount();
                    List<WaiFuSJEntity> waiFuSJEntityList =
                            synchronousTaskApiService.downLoadWaiFuCBSJ(account);
                    if (waiFuSJEntityList != null) {
                        List<DUWaiFuCBSJ> duWaiFuCBSJs = new ArrayList<>();
                        for (WaiFuSJEntity waiFuSJEntity : waiFuSJEntityList) {
                            DUWaiFuCBSJ duWaiFuCBSJ = new DUWaiFuCBSJ(
                                    -1,
                                    waiFuSJEntity.getI_RenWuBH(),
                                    waiFuSJEntity.getCH(),
                                    waiFuSJEntity.getCeNeiXH(),
                                    waiFuSJEntity.getCID(),
                                    waiFuSJEntity.getST(),
                                    waiFuSJEntity.getChaoBiaoN(),
                                    waiFuSJEntity.getChaoBiaoYue(),
                                    waiFuSJEntity.getChaoCi(),
                                    waiFuSJEntity.getChaoBiaoRQ(),
                                    waiFuSJEntity.getShangciCM(),
                                    waiFuSJEntity.getBenCiCM(),
                                    waiFuSJEntity.getChaoJIanSL(),
                                    waiFuSJEntity.getZhuangTaiBM(),
                                    waiFuSJEntity.getZhuangTaiMC(),
                                    waiFuSJEntity.getShangciCBRQ(),
                                    waiFuSJEntity.getShangCiZTBM(),
                                    waiFuSJEntity.getShangCiZTMC(),
                                    waiFuSJEntity.getShangCiCJSL(),
                                    waiFuSJEntity.getShangCiZTLXS(),
                                    waiFuSJEntity.getPingJunL1(),
                                    waiFuSJEntity.getPingJunL2(),
                                    waiFuSJEntity.getPingJunL3(),
                                    waiFuSJEntity.getJE(),
                                    waiFuSJEntity.getZongBiaoCID(),
                                    account,
                                    waiFuSJEntity.getChaoBiaoBZ(),
                                    waiFuSJEntity.getJiuBiaoCM(),
                                    waiFuSJEntity.getXinBiaoDM(),
                                    waiFuSJEntity.getHuanBiaoRQ(),
                                    waiFuSJEntity.getFangShiBM(),
                                    waiFuSJEntity.getLiangGaoLDYYBM(),
                                    waiFuSJEntity.getChaoBiaoID(),
                                    waiFuSJEntity.getZhuangTaiLXS(),
                                    waiFuSJEntity.getShuiBiaoBL(),
                                    waiFuSJEntity.getYongShuiZKL(),
                                    waiFuSJEntity.getPaiShuiZKL(),
                                    waiFuSJEntity.getTiaoJiaH(),
                                    waiFuSJEntity.getJianHao(),
                                    waiFuSJEntity.getXiaZaiSJ(),
                                    waiFuSJEntity.getLingYongSLSM(),
                                    waiFuSJEntity.getLiangGaoSL(),
                                    waiFuSJEntity.getLiangDiSL(),
                                    waiFuSJEntity.getX1(),
                                    waiFuSJEntity.getY1(),
                                    waiFuSJEntity.getX(),
                                    waiFuSJEntity.getY(),
                                    waiFuSJEntity.getChaoBiaoBeiZhu(),
                                    waiFuSJEntity.getceNeiPX(),
                                    waiFuSJEntity.getI_XiaZaiCS(),
                                    waiFuSJEntity.getD_ZuiHouYCXZSJ(),
                                    waiFuSJEntity.getD_ZuiHouYCSCSJ(),
                                    waiFuSJEntity.getI_ShangChuanBZ(),
                                    waiFuSJEntity.getI_ShenHeBZ(),
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    waiFuSJEntity.getI_LastReadingChild(),
                                    waiFuSJEntity.getI_ReadingChild(),
                                    waiFuSJEntity.getI_CheckOutsideType()
                            );
                            duWaiFuCBSJs.add(duWaiFuCBSJ);
                        }

                        subscriber.onNext(duWaiFuCBSJs);
                    } else {
                        subscriber.onError(new Throwable("waiFuSJEntityList is null"));
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
     * get sampling records
     *
     * @param duSamplingInfo
     * @return
     */
    public Observable<List<DUSampling>> getSamplingRecords(final DUSamplingInfo duSamplingInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUSampling>>() {
            @Override
            public void call(Subscriber<? super List<DUSampling>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duSamplingInfo == null) {
                        throw new NullPointerException("duSamplingInfo is null");
                    }
                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    int taskId = duSamplingInfo.getTaskId();
                    List<JiChaSJEntity> jiChaSJEntityList =
                            synchronousTaskApiService.downLoadJiChaSJ(taskId);
                    if (jiChaSJEntityList != null) {
                        List<DUSampling> duSamplingList = new ArrayList<>();
                        for (JiChaSJEntity jiChaSJEntity : jiChaSJEntityList) {
                            DUSampling duSampling = new DUSampling(
                                    -1,
                                    jiChaSJEntity.getI_RenWuBH(),
                                    jiChaSJEntity.getCH(),
                                    jiChaSJEntity.getCeNeiXH(),
                                    jiChaSJEntity.getCID(),
                                    jiChaSJEntity.getST(),
                                    jiChaSJEntity.getChaoBiaoN(),
                                    jiChaSJEntity.getChaoBiaoYue(),
                                    jiChaSJEntity.getChaoCi(),
                                    jiChaSJEntity.getChaoBiaoRQ(),
                                    jiChaSJEntity.getShangciCM(),
                                    jiChaSJEntity.getBenCiCM(),
                                    jiChaSJEntity.getChaoJIanSL(),
                                    jiChaSJEntity.getZhuangTaiBM(),
                                    jiChaSJEntity.getZhuangTaiMC(),
                                    jiChaSJEntity.getShangciCBRQ(),
                                    jiChaSJEntity.getShangCiZTBM(),
                                    jiChaSJEntity.getShangCiZTMC(),
                                    jiChaSJEntity.getShangCiCJSL(),
                                    jiChaSJEntity.getShangCiZTLXS(),
                                    jiChaSJEntity.getPingJunL1(),
                                    jiChaSJEntity.getPingJunL2(),
                                    jiChaSJEntity.getPingJunL3(),
                                    jiChaSJEntity.getJE(),
                                    jiChaSJEntity.getZongBiaoCID(),
                                    jiChaSJEntity.getChaoBiaoY(),
                                    jiChaSJEntity.getChaoBiaoBZ(),
                                    jiChaSJEntity.getJiuBiaoCM(),
                                    jiChaSJEntity.getXinBiaoDM(),
                                    jiChaSJEntity.getHuanBiaoRQ(),
                                    jiChaSJEntity.getFangShiBM(),
                                    jiChaSJEntity.getLiangGaoLDYYBM(),
                                    jiChaSJEntity.getChaoBiaoID(),
                                    jiChaSJEntity.getZhuangTaiLXS(),
                                    jiChaSJEntity.getShuiBiaoBL(),
                                    jiChaSJEntity.getYongShuiZKL(),
                                    jiChaSJEntity.getPaiShuiZKL(),
                                    jiChaSJEntity.getTiaoJiaH(),
                                    jiChaSJEntity.getJianHao(),
                                    jiChaSJEntity.getXiaZaiSJ(),
                                    jiChaSJEntity.getLingYongSLSM(),
                                    jiChaSJEntity.getLiangGaoSL(),
                                    jiChaSJEntity.getLiangDiSL(),
                                    jiChaSJEntity.getX1(),
                                    jiChaSJEntity.getY1(),
                                    jiChaSJEntity.getX(),
                                    jiChaSJEntity.getY(),
                                    jiChaSJEntity.getChaoBiaoBeiZhu(),
                                    jiChaSJEntity.getceNeiPX(),
                                    jiChaSJEntity.getI_XiaZaiCS(),
                                    jiChaSJEntity.getD_ZuiHouYCXZSJ(),
                                    jiChaSJEntity.getD_ZuiHouYCSCSJ(),
                                    jiChaSJEntity.getI_ShangChuanBZ(),
                                    jiChaSJEntity.getI_ShenHeBZ(),
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    jiChaSJEntity.getI_LastReadingChild(),
                                    jiChaSJEntity.getI_ReadingChild(),
                                    jiChaSJEntity.get_jiChaCM(),
                                    jiChaSJEntity.get_jiChaSL(),
                                    jiChaSJEntity.get_jiChaZTBM(),
                                    jiChaSJEntity.get_jiChaCBRQ(),
                                    jiChaSJEntity.get_jiChaZTMC());
                            duSamplingList.add(duSampling);
                        }
                        subscriber.onNext(duSamplingList);
                    } else {
                        subscriber.onError(new Throwable("chaoBiaoSJEntityList is null"));
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
     * @param duRecordInfo
     * @return
     */
    public Observable<List<DURecord>> getTemporaryRecords(final DURecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DURecord>>() {
            @Override
            public void call(Subscriber<? super List<DURecord>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duRecordInfo == null) {
                        throw new NullPointerException("duRecordInfo is null");
                    } else if (TextUtil.isNullOrEmpty(duRecordInfo.getAccount())) {
                        throw new NullPointerException("account is null");
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    List<ChaoBiaoSJEntity> chaoBiaoSJEntityList =
                            synchronousTaskApiService.downTemporaryLoadChaoBiaoSJ(duRecordInfo.getAccount());
                    if (chaoBiaoSJEntityList != null) {
                        List<DURecord> duRecordList = new ArrayList<>();
                        for (ChaoBiaoSJEntity chaoBiaoSJEntity : chaoBiaoSJEntityList) {
                            DURecord duRecord = new DURecord(
                                    -1,
                                    chaoBiaoSJEntity.getI_RenWuBH(),
                                    chaoBiaoSJEntity.getCH(),
                                    chaoBiaoSJEntity.getCeNeiXH(),
                                    chaoBiaoSJEntity.getCID(),
                                    chaoBiaoSJEntity.getST(),
                                    chaoBiaoSJEntity.getChaoBiaoN(),
                                    chaoBiaoSJEntity.getChaoBiaoYue(),
                                    chaoBiaoSJEntity.getChaoCi(),
                                    chaoBiaoSJEntity.getChaoBiaoRQ(),
                                    chaoBiaoSJEntity.getShangciCM(),
                                    chaoBiaoSJEntity.getBenCiCM(),
                                    chaoBiaoSJEntity.getChaoJIanSL(),
                                    chaoBiaoSJEntity.getZhuangTaiBM(),
                                    chaoBiaoSJEntity.getZhuangTaiMC(),
                                    chaoBiaoSJEntity.getShangciCBRQ(),
                                    chaoBiaoSJEntity.getShangCiZTBM(),
                                    chaoBiaoSJEntity.getShangCiZTMC(),
                                    chaoBiaoSJEntity.getShangCiCJSL(),
                                    chaoBiaoSJEntity.getShangCiZTLXS(),
                                    chaoBiaoSJEntity.getPingJunL1(),
                                    chaoBiaoSJEntity.getPingJunL2(),
                                    chaoBiaoSJEntity.getPingJunL3(),
                                    chaoBiaoSJEntity.getJE(),
                                    chaoBiaoSJEntity.getZongBiaoCID(),
                                    chaoBiaoSJEntity.getChaoBiaoY(),
                                    chaoBiaoSJEntity.getChaoBiaoBZ(),
                                    chaoBiaoSJEntity.getJiuBiaoCM(),
                                    chaoBiaoSJEntity.getXinBiaoDM(),
                                    chaoBiaoSJEntity.getHuanBiaoRQ(),
                                    chaoBiaoSJEntity.getFangShiBM(),
                                    chaoBiaoSJEntity.getLiangGaoLDYYBM(),
                                    chaoBiaoSJEntity.getChaoBiaoID(),
                                    chaoBiaoSJEntity.getZhuangTaiLXS(),
                                    chaoBiaoSJEntity.getShuiBiaoBL(),
                                    chaoBiaoSJEntity.getYongShuiZKL(),
                                    chaoBiaoSJEntity.getPaiShuiZKL(),
                                    chaoBiaoSJEntity.getTiaoJiaH(),
                                    chaoBiaoSJEntity.getJianHao(),
                                    chaoBiaoSJEntity.getXiaZaiSJ(),
                                    chaoBiaoSJEntity.getLingYongSLSM(),
                                    chaoBiaoSJEntity.getLiangGaoSL(),
                                    chaoBiaoSJEntity.getLiangDiSL(),
                                    chaoBiaoSJEntity.getX1(),
                                    chaoBiaoSJEntity.getY1(),
                                    chaoBiaoSJEntity.getX(),
                                    chaoBiaoSJEntity.getY(),
                                    chaoBiaoSJEntity.getChaoBiaoBeiZhu(),
                                    chaoBiaoSJEntity.getceNeiPX(),
                                    chaoBiaoSJEntity.getI_XiaZaiCS(),
                                    chaoBiaoSJEntity.getD_ZuiHouYCXZSJ(),
                                    chaoBiaoSJEntity.getD_ZuiHouYCSCSJ(),
                                    chaoBiaoSJEntity.getI_ShangChuanBZ(),
                                    chaoBiaoSJEntity.getI_ShenHeBZ(),
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    chaoBiaoSJEntity.getI_LastReadingChild(),
                                    chaoBiaoSJEntity.getI_ReadingChild(),
                                    ChaoBiaoSJEntity.NEW_CHAOBIAOSJ,
                                    chaoBiaoSJEntity.getI_GroupId(),
                                    chaoBiaoSJEntity.getI_SortIndex(),
                                    chaoBiaoSJEntity.getS_CardType(),
                                    chaoBiaoSJEntity.getI_IsRead(),
                                    chaoBiaoSJEntity.getI_ReasonUnRead(),
                                    chaoBiaoSJEntity.getI_SHANGGEDBZQTS(),
                                    chaoBiaoSJEntity.getD_SHANGSHANGGYCBRQ()
                            );
                            duRecordList.add(duRecord);
                        }

                        subscriber.onNext(duRecordList);
                    } else {
                        subscriber.onError(new Throwable("chaoBiaoSJEntityList is null"));
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
                    if (duCardInfo == null) {
                        throw new NullPointerException("duCardInfo is null");
                    } else if (TextUtil.isNullOrEmpty(duCardInfo.getVolume())) {
                        throw new NullPointerException("duCardInfo volume is null");
                    }

                    String customerId = duCardInfo.getCustomerId();
                    BusinessApiService businessApiService = new BusinessApiService();
                    List<BiaoKaXXEntity> biaoKaXXEntityList = null;
                    if (!TextUtil.isNullOrEmpty(customerId)) {
                        BiaoKaXXEntity biaoKaXXEntity = businessApiService.queryBiaoKaXX(customerId);
                        if (biaoKaXXEntity == null) {
                            subscriber.onError(new Throwable("biaoKaXXEntity is null"));
                            return;
                        }

                        biaoKaXXEntityList = new ArrayList<>();
                        biaoKaXXEntityList.add(biaoKaXXEntity);
                    } else {
                        biaoKaXXEntityList =
                                businessApiService.queryBiaoKaXXList(duCardInfo.getVolume());
                    }

                    if (biaoKaXXEntityList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        int shangchuanbz =
                                (duCardInfo.getFilterType() == DUCardInfo.FilterType.TEMPORARY ? -1 : 0);
                        int downloadType =
                                (duCardInfo.getFilterType() == DUCardInfo.FilterType.TEMPORARY ?
                                        BiaoKaXXEntity.NEW_BIAOKAXX : BiaoKaXXEntity.CHAOBIAO_BIAOKAXX);
                        for (BiaoKaXXEntity biaoKaXXEntity : biaoKaXXEntityList) {
                            DUCard duCard = new DUCard(
                                    -1,
                                    biaoKaXXEntity.getcH(),
                                    biaoKaXXEntity.getCeNeiXH(),
                                    biaoKaXXEntity.getcID(),
                                    biaoKaXXEntity.getKeHuBH(),
                                    biaoKaXXEntity.getKeHuMC(),
                                    biaoKaXXEntity.getsT(),
                                    biaoKaXXEntity.getDiZhi(),
                                    biaoKaXXEntity.getLianXiR(),
                                    biaoKaXXEntity.getLianXiSJ(),
                                    biaoKaXXEntity.getLianXiDH(),
                                    biaoKaXXEntity.getShouFeiFS(),
                                    biaoKaXXEntity.getYinHangMC(),
                                    biaoKaXXEntity.getJianHao(),
                                    biaoKaXXEntity.getJianHaoMC(),
                                    biaoKaXXEntity.getYongHuZT(),
                                    biaoKaXXEntity.getLiHuRQ(),
                                    biaoKaXXEntity.getBiaoWei(),
                                    biaoKaXXEntity.getShuiBiaoGYH(),
                                    biaoKaXXEntity.getShuiBiaoTXM(),
                                    biaoKaXXEntity.getKouJingMC(),
                                    biaoKaXXEntity.getLiangCheng(),
                                    biaoKaXXEntity.getBiaoXing(),
                                    biaoKaXXEntity.getShuiBiaoCJ(),
                                    biaoKaXXEntity.getShuiBiaoFL(),
                                    biaoKaXXEntity.getShuiBiaoFLMC(),
                                    biaoKaXXEntity.getShuibiaoBL(),
                                    biaoKaXXEntity.getKaiZhangFL(),
                                    biaoKaXXEntity.getGongNengFL(),
                                    biaoKaXXEntity.getShiFouJHYS(),
                                    biaoKaXXEntity.getShiFouShouLJF(),
                                    biaoKaXXEntity.getLaJiFeiXS().doubleValue(),
                                    biaoKaXXEntity.getShiFouShouWYJ(),
                                    biaoKaXXEntity.getShiFouDEJJ(),
                                    biaoKaXXEntity.getDingESL(),
                                    biaoKaXXEntity.getZongBiaoBH(),
                                    biaoKaXXEntity.getZhuangBiaoRQ(),
                                    biaoKaXXEntity.getHuanBiaoRQ(),
                                    biaoKaXXEntity.getXinBiaoDM(),
                                    biaoKaXXEntity.getJiuBiaoCM(),
                                    biaoKaXXEntity.getX1(),
                                    biaoKaXXEntity.getY1(),
                                    biaoKaXXEntity.getX(),
                                    biaoKaXXEntity.getY(),
                                    biaoKaXXEntity.getFenTanFS(),
                                    biaoKaXXEntity.getFenTanL(),
                                    biaoKaXXEntity.getYuCunKYE().doubleValue(),
                                    biaoKaXXEntity.getQianFeiZBS(),
                                    biaoKaXXEntity.getQianFeiZJE().doubleValue(),
                                    biaoKaXXEntity.getBeiZhu(),
                                    biaoKaXXEntity.getshuibiaoZT(),
                                    biaoKaXXEntity.getRENKOUS().doubleValue(),
                                    biaoKaXXEntity.getDIBAOYHSL(),
                                    biaoKaXXEntity.getGONGCEYHSL(),
                                    biaoKaXXEntity.getYONGSHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getPAISHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL1().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL2().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL3().doubleValue(),
                                    0,
                                    0,
                                    null,
                                    null,
                                    null,
                                    0,
                                    null,
                                    0,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    biaoKaXXEntity.getMeterInstallTypeName(),
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    biaoKaXXEntity.get_nianLeiJ(),
                                    0,
                                    0,
                                    0,
                                    shangchuanbz,
                                    downloadType,
                                    biaoKaXXEntity.get_GroupId(),
                                    biaoKaXXEntity.get_KuaiHao(),
                                    biaoKaXXEntity.get_CustomerType(),
                                    biaoKaXXEntity.get_IsVIP(),
                                    biaoKaXXEntity.get_VIPCode(),
                                    biaoKaXXEntity.get_DateOfContract(),
                                    biaoKaXXEntity.get_Contractage(),
                                    biaoKaXXEntity.get_PostType(),
                                    biaoKaXXEntity.get_Creditrating(),
                                    biaoKaXXEntity.get_WaterMode(),
                                    biaoKaXXEntity.get_Strongdate(),
                                    biaoKaXXEntity.get_PayMethod(),
                                    biaoKaXXEntity.get_CaliberValue(),
                                    biaoKaXXEntity.get_MonthTotal(),
                                    biaoKaXXEntity.get_QuarterTotal(),
                                    biaoKaXXEntity.get_YearTotal(),
                                    biaoKaXXEntity.get_Extend()
                            );
                            duCardList.add(duCard);
                        }

                        subscriber.onNext(duCardList);
                    } else {
                        subscriber.onError(new Throwable("biaoKaXXEntityList is null"));
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

    public Observable<List<DUCard>> getDelayCards(final DUDelayCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUCard>>() {
            @Override
            public void call(Subscriber<? super List<DUCard>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duCardInfo == null) {
                        throw new NullPointerException("duCardInfo is null");
                    } else if (TextUtil.isNullOrEmpty(duCardInfo.getAccount())) {
                        throw new NullPointerException("duCardInfo account is null");
                    }

                    List<BiaoKaXXEntity> biaoKaXXEntityList = new WorkApiService()
                            .QueryDelayBiaoKaXX(duCardInfo.getAccount(), duCardInfo.getDeviceId());

                    if (biaoKaXXEntityList != null) {
                        List<DUCard> list = new ArrayList<>();
                        for (BiaoKaXXEntity biaoKaXXEntity : biaoKaXXEntityList) {
                            DUCard duCard = new DUCard(
                                    -1,
                                    biaoKaXXEntity.getcH(),
                                    biaoKaXXEntity.getCeNeiXH(),
                                    biaoKaXXEntity.getcID(),
                                    biaoKaXXEntity.getKeHuBH(),
                                    biaoKaXXEntity.getKeHuMC(),
                                    biaoKaXXEntity.getsT(),
                                    biaoKaXXEntity.getDiZhi(),
                                    biaoKaXXEntity.getLianXiR(),
                                    biaoKaXXEntity.getLianXiSJ(),
                                    biaoKaXXEntity.getLianXiDH(),
                                    biaoKaXXEntity.getShouFeiFS(),
                                    biaoKaXXEntity.getYinHangMC(),
                                    biaoKaXXEntity.getJianHao(),
                                    biaoKaXXEntity.getJianHaoMC(),
                                    biaoKaXXEntity.getYongHuZT(),
                                    biaoKaXXEntity.getLiHuRQ(),
                                    biaoKaXXEntity.getBiaoWei(),
                                    biaoKaXXEntity.getShuiBiaoGYH(),
                                    biaoKaXXEntity.getShuiBiaoTXM(),
                                    biaoKaXXEntity.getKouJingMC(),
                                    biaoKaXXEntity.getLiangCheng(),
                                    biaoKaXXEntity.getBiaoXing(),
                                    biaoKaXXEntity.getShuiBiaoCJ(),
                                    biaoKaXXEntity.getShuiBiaoFL(),
                                    biaoKaXXEntity.getShuiBiaoFLMC(),
                                    biaoKaXXEntity.getShuibiaoBL(),
                                    biaoKaXXEntity.getKaiZhangFL(),
                                    biaoKaXXEntity.getGongNengFL(),
                                    biaoKaXXEntity.getShiFouJHYS(),
                                    biaoKaXXEntity.getShiFouShouLJF(),
                                    biaoKaXXEntity.getLaJiFeiXS().doubleValue(),
                                    biaoKaXXEntity.getShiFouShouWYJ(),
                                    biaoKaXXEntity.getShiFouDEJJ(),
                                    biaoKaXXEntity.getDingESL(),
                                    biaoKaXXEntity.getZongBiaoBH(),
                                    biaoKaXXEntity.getZhuangBiaoRQ(),
                                    biaoKaXXEntity.getHuanBiaoRQ(),
                                    biaoKaXXEntity.getXinBiaoDM(),
                                    biaoKaXXEntity.getJiuBiaoCM(),
                                    biaoKaXXEntity.getX1(),
                                    biaoKaXXEntity.getY1(),
                                    biaoKaXXEntity.getX(),
                                    biaoKaXXEntity.getY(),
                                    biaoKaXXEntity.getFenTanFS(),
                                    biaoKaXXEntity.getFenTanL(),
                                    biaoKaXXEntity.getYuCunKYE().doubleValue(),
                                    biaoKaXXEntity.getQianFeiZBS(),
                                    biaoKaXXEntity.getQianFeiZJE().doubleValue(),
                                    biaoKaXXEntity.getBeiZhu(),
                                    biaoKaXXEntity.getshuibiaoZT(),
                                    biaoKaXXEntity.getRENKOUS().doubleValue(),
                                    biaoKaXXEntity.getDIBAOYHSL(),
                                    biaoKaXXEntity.getGONGCEYHSL(),
                                    biaoKaXXEntity.getYONGSHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getPAISHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL1().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL2().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL3().doubleValue(),
                                    0,
                                    0,
                                    null,
                                    null,
                                    null,
                                    0,
                                    null,
                                    0,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    biaoKaXXEntity.getMeterInstallTypeName(),
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    biaoKaXXEntity.get_GroupId(),
                                    biaoKaXXEntity.get_KuaiHao(),
                                    biaoKaXXEntity.get_CustomerType(),
                                    biaoKaXXEntity.get_IsVIP(),
                                    biaoKaXXEntity.get_VIPCode(),
                                    biaoKaXXEntity.get_DateOfContract(),
                                    biaoKaXXEntity.get_Contractage(),
                                    biaoKaXXEntity.get_PostType(),
                                    biaoKaXXEntity.get_Creditrating(),
                                    biaoKaXXEntity.get_WaterMode(),
                                    biaoKaXXEntity.get_Strongdate(),
                                    biaoKaXXEntity.get_PayMethod(),
                                    biaoKaXXEntity.get_CaliberValue(),
                                    biaoKaXXEntity.get_MonthTotal(),
                                    biaoKaXXEntity.get_QuarterTotal(),
                                    biaoKaXXEntity.get_YearTotal(),
                                    biaoKaXXEntity.get_Extend()
                            );
                            list.add(duCard);
                        }

                        subscriber.onNext(list);
                    } else {
                        subscriber.onError(new Throwable("biaoKaXXEntityList is null"));
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
     * get waifu cards
     *
     * @param duCardInfo
     * @return
     */

    public Observable<List<DUCard>> getWaiFuCards(final DUCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUCard>>() {
            @Override
            public void call(Subscriber<? super List<DUCard>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duCardInfo == null) {
                        throw new NullPointerException("duCardInfo is null");
                    } else if (duCardInfo.getAccount() == null) {
                        throw new NullPointerException("duCardInfo account is null");
                    }

                    String customerId = duCardInfo.getCustomerId();
                    BusinessApiService businessApiService = new BusinessApiService();
                    List<BiaoKaXXEntity> biaoKaXXEntityList = null;
                    if (!TextUtil.isNullOrEmpty(customerId)) {
                        BiaoKaXXEntity biaoKaXXEntity = businessApiService.queryBiaoKaXX(customerId);
                        if (biaoKaXXEntity == null) {
                            subscriber.onError(new Throwable("biaoKaXXEntity is null"));
                            return;
                        }

                        biaoKaXXEntityList = new ArrayList<>();
                        biaoKaXXEntityList.add(biaoKaXXEntity);
                    } else {
                        biaoKaXXEntityList =
                                businessApiService.queryBiaoKaXXListByAccount(duCardInfo.getAccount());
                    }

                    if (biaoKaXXEntityList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        int shangchuanbz =
                                (duCardInfo.getFilterType() == DUCardInfo.FilterType.TEMPORARY ? -1 : 0);
                        for (BiaoKaXXEntity biaoKaXXEntity : biaoKaXXEntityList) {
                            DUCard duCard = new DUCard(
                                    -1,
                                    biaoKaXXEntity.getcH().trim(),
                                    biaoKaXXEntity.getCeNeiXH(),
                                    biaoKaXXEntity.getcID().trim(),
                                    biaoKaXXEntity.getKeHuBH().trim(),
                                    biaoKaXXEntity.getKeHuMC().trim(),
                                    biaoKaXXEntity.getsT().trim(),
                                    biaoKaXXEntity.getDiZhi().trim(),
                                    biaoKaXXEntity.getLianXiR().trim(),
                                    biaoKaXXEntity.getLianXiSJ().trim(),
                                    biaoKaXXEntity.getLianXiDH().trim(),
                                    biaoKaXXEntity.getShouFeiFS().trim(),
                                    biaoKaXXEntity.getYinHangMC().trim(),
                                    biaoKaXXEntity.getJianHao().trim(),
                                    biaoKaXXEntity.getJianHaoMC().trim(),
                                    biaoKaXXEntity.getYongHuZT(),
                                    biaoKaXXEntity.getLiHuRQ(),
                                    biaoKaXXEntity.getBiaoWei().trim(),
                                    biaoKaXXEntity.getShuiBiaoGYH().trim(),
                                    biaoKaXXEntity.getShuiBiaoTXM().trim(),
                                    biaoKaXXEntity.getKouJingMC().trim(),
                                    biaoKaXXEntity.getLiangCheng(),
                                    biaoKaXXEntity.getBiaoXing().trim(),
                                    biaoKaXXEntity.getShuiBiaoCJ().trim(),
                                    biaoKaXXEntity.getShuiBiaoFL(),
                                    biaoKaXXEntity.getShuiBiaoFLMC().trim(),
                                    biaoKaXXEntity.getShuibiaoBL(),
                                    biaoKaXXEntity.getKaiZhangFL().trim(),
                                    biaoKaXXEntity.getGongNengFL(),
                                    biaoKaXXEntity.getShiFouJHYS(),
                                    biaoKaXXEntity.getShiFouShouLJF(),
                                    biaoKaXXEntity.getLaJiFeiXS().doubleValue(),
                                    biaoKaXXEntity.getShiFouShouWYJ(),
                                    biaoKaXXEntity.getShiFouDEJJ(),
                                    biaoKaXXEntity.getDingESL(),
                                    biaoKaXXEntity.getZongBiaoBH().trim(),
                                    biaoKaXXEntity.getZhuangBiaoRQ(),
                                    biaoKaXXEntity.getHuanBiaoRQ(),
                                    biaoKaXXEntity.getXinBiaoDM(),
                                    biaoKaXXEntity.getJiuBiaoCM(),
                                    biaoKaXXEntity.getX1().trim(),
                                    biaoKaXXEntity.getY1().trim(),
                                    biaoKaXXEntity.getX().trim(),
                                    biaoKaXXEntity.getY().trim(),
                                    biaoKaXXEntity.getFenTanFS(),
                                    biaoKaXXEntity.getFenTanL(),
                                    biaoKaXXEntity.getYuCunKYE().doubleValue(),
                                    biaoKaXXEntity.getQianFeiZBS(),
                                    biaoKaXXEntity.getQianFeiZJE().doubleValue(),
                                    biaoKaXXEntity.getBeiZhu().trim(),
                                    biaoKaXXEntity.getshuibiaoZT(),
                                    biaoKaXXEntity.getRENKOUS().doubleValue(),
                                    biaoKaXXEntity.getDIBAOYHSL(),
                                    biaoKaXXEntity.getGONGCEYHSL(),
                                    biaoKaXXEntity.getYONGSHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getPAISHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL1().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL2().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL3().doubleValue(),
                                    0,
                                    0,
                                    null,
                                    null,
                                    null,
                                    0,
                                    null,
                                    0,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    0,
                                    0,
                                    0,
                                    0,
                                    shangchuanbz,
                                    BiaoKaXXEntity.OUTSIDE_BIAOKAXX,
                                    biaoKaXXEntity.get_GroupId(),
                                    biaoKaXXEntity.get_KuaiHao(),
                                    biaoKaXXEntity.get_CustomerType(),
                                    biaoKaXXEntity.get_IsVIP(),
                                    biaoKaXXEntity.get_VIPCode(),
                                    biaoKaXXEntity.get_DateOfContract(),
                                    biaoKaXXEntity.get_Contractage(),
                                    biaoKaXXEntity.get_PostType(),
                                    biaoKaXXEntity.get_Creditrating(),
                                    biaoKaXXEntity.get_WaterMode(),
                                    biaoKaXXEntity.get_Strongdate(),
                                    biaoKaXXEntity.get_PayMethod(),
                                    biaoKaXXEntity.get_CaliberValue(),
                                    biaoKaXXEntity.get_MonthTotal(),
                                    biaoKaXXEntity.get_QuarterTotal(),
                                    biaoKaXXEntity.get_YearTotal(),
                                    biaoKaXXEntity.get_Extend()
                            );
                            duCardList.add(duCard);
                        }

                        subscriber.onNext(duCardList);
                    } else {
                        subscriber.onError(new Throwable("biaoKaXXEntityList is null"));
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
                    if (duCardInfo == null) {
                        throw new NullPointerException("duCardInfo is null");
                    } else if (duCardInfo.getTaskId() == 0) {
                        throw new NullPointerException("duCardInfo taskid is null");
                    }

                    String customerId = duCardInfo.getCustomerId();
                    BusinessApiService businessApiService = new BusinessApiService();
                    List<BiaoKaXXEntity> biaoKaXXEntityList = null;
                    if (!TextUtil.isNullOrEmpty(customerId)) {
                        BiaoKaXXEntity biaoKaXXEntity = businessApiService.queryBiaoKaXX(customerId);
                        if (biaoKaXXEntity == null) {
                            subscriber.onError(new Throwable("biaoKaXXEntity is null"));
                            return;
                        }

                        biaoKaXXEntityList = new ArrayList<>();
                        biaoKaXXEntityList.add(biaoKaXXEntity);
                    } else {
                        biaoKaXXEntityList =
                                businessApiService.queryBiaoKaXXListByTaskId(duCardInfo.getTaskId());
                    }

                    if (biaoKaXXEntityList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        int shangchuanbz =
                                (duCardInfo.getFilterType() == DUCardInfo.FilterType.TEMPORARY ? -1 : 0);
                        for (BiaoKaXXEntity biaoKaXXEntity : biaoKaXXEntityList) {
                            DUCard duCard = new DUCard(
                                    -1,
                                    biaoKaXXEntity.getcH(),
                                    biaoKaXXEntity.getCeNeiXH(),
                                    biaoKaXXEntity.getcID(),
                                    biaoKaXXEntity.getKeHuBH(),
                                    biaoKaXXEntity.getKeHuMC(),
                                    biaoKaXXEntity.getsT(),
                                    biaoKaXXEntity.getDiZhi(),
                                    biaoKaXXEntity.getLianXiR(),
                                    biaoKaXXEntity.getLianXiSJ(),
                                    biaoKaXXEntity.getLianXiDH(),
                                    biaoKaXXEntity.getShouFeiFS(),
                                    biaoKaXXEntity.getYinHangMC(),
                                    biaoKaXXEntity.getJianHao(),
                                    biaoKaXXEntity.getJianHaoMC(),
                                    biaoKaXXEntity.getYongHuZT(),
                                    biaoKaXXEntity.getLiHuRQ(),
                                    biaoKaXXEntity.getBiaoWei(),
                                    biaoKaXXEntity.getShuiBiaoGYH(),
                                    biaoKaXXEntity.getShuiBiaoTXM(),
                                    biaoKaXXEntity.getKouJingMC(),
                                    biaoKaXXEntity.getLiangCheng(),
                                    biaoKaXXEntity.getBiaoXing(),
                                    biaoKaXXEntity.getShuiBiaoCJ(),
                                    biaoKaXXEntity.getShuiBiaoFL(),
                                    biaoKaXXEntity.getShuiBiaoFLMC(),
                                    biaoKaXXEntity.getShuibiaoBL(),
                                    biaoKaXXEntity.getKaiZhangFL(),
                                    biaoKaXXEntity.getGongNengFL(),
                                    biaoKaXXEntity.getShiFouJHYS(),
                                    biaoKaXXEntity.getShiFouShouLJF(),
                                    biaoKaXXEntity.getLaJiFeiXS().doubleValue(),
                                    biaoKaXXEntity.getShiFouShouWYJ(),
                                    biaoKaXXEntity.getShiFouDEJJ(),
                                    biaoKaXXEntity.getDingESL(),
                                    biaoKaXXEntity.getZongBiaoBH(),
                                    biaoKaXXEntity.getZhuangBiaoRQ(),
                                    biaoKaXXEntity.getHuanBiaoRQ(),
                                    biaoKaXXEntity.getXinBiaoDM(),
                                    biaoKaXXEntity.getJiuBiaoCM(),
                                    biaoKaXXEntity.getX1(),
                                    biaoKaXXEntity.getY1(),
                                    biaoKaXXEntity.getX(),
                                    biaoKaXXEntity.getY(),
                                    biaoKaXXEntity.getFenTanFS(),
                                    biaoKaXXEntity.getFenTanL(),
                                    biaoKaXXEntity.getYuCunKYE().doubleValue(),
                                    biaoKaXXEntity.getQianFeiZBS(),
                                    biaoKaXXEntity.getQianFeiZJE().doubleValue(),
                                    biaoKaXXEntity.getBeiZhu(),
                                    biaoKaXXEntity.getshuibiaoZT(),
                                    biaoKaXXEntity.getRENKOUS().doubleValue(),
                                    biaoKaXXEntity.getDIBAOYHSL(),
                                    biaoKaXXEntity.getGONGCEYHSL(),
                                    biaoKaXXEntity.getYONGSHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getPAISHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL1().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL2().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL3().doubleValue(),
                                    0,
                                    0,
                                    null,
                                    null,
                                    null,
                                    0,
                                    null,
                                    0,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    0,
                                    0,
                                    0,
                                    0,
                                    shangchuanbz,
                                    BiaoKaXXEntity.SAMPLING_BIAOKAXX,
                                    biaoKaXXEntity.get_GroupId(),
                                    biaoKaXXEntity.get_KuaiHao(),
                                    biaoKaXXEntity.get_CustomerType(),
                                    biaoKaXXEntity.get_IsVIP(),
                                    biaoKaXXEntity.get_VIPCode(),
                                    biaoKaXXEntity.get_DateOfContract(),
                                    biaoKaXXEntity.get_Contractage(),
                                    biaoKaXXEntity.get_PostType(),
                                    biaoKaXXEntity.get_Creditrating(),
                                    biaoKaXXEntity.get_WaterMode(),
                                    biaoKaXXEntity.get_Strongdate(),
                                    biaoKaXXEntity.get_PayMethod(),
                                    biaoKaXXEntity.get_CaliberValue(),
                                    biaoKaXXEntity.get_MonthTotal(),
                                    biaoKaXXEntity.get_QuarterTotal(),
                                    biaoKaXXEntity.get_YearTotal(),
                                    biaoKaXXEntity.get_Extend()
                            );
                            duCardList.add(duCard);
                        }

                        subscriber.onNext(duCardList);
                    } else {
                        subscriber.onError(new Throwable("biaoKaXXEntityList is null"));
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
     * upload records
     *
     * @param duRecordInfo
     * @return
     */
    public Observable<List<DURecord>> uploadRecords(final DURecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DURecord>>() {
            @Override
            public void call(Subscriber<? super List<DURecord>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duRecordInfo == null) {
                        throw new NullPointerException("duRecordInfo is null");
                    }

                    List<DURecord> srcDuRecordList = duRecordInfo.getDuRecordList();
                    if (srcDuRecordList == null) {
                        throw new NullPointerException("srcDuRecordList is null");
                    }

                    List<ChaoBiaoSJEntity> srcChaoBiaoSJEntityList = new ArrayList<>();
                    for (DURecord duRecord : srcDuRecordList) {
                        ChaoBiaoSJEntity chaoBiaoSJEntity = new ChaoBiaoSJEntity(
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
                                duRecord.getLastReadingChild(),
                                duRecord.getReadingChild(),
                                duRecord.getGroupId(),
                                duRecord.getSortIndex(),
                                duRecord.getCardType(),
                                duRecord.getIsRead(),
                                duRecord.getReasonUnRead(),
                                duRecord.getI_SHANGGEDBZQTS(),
                                duRecord.getD_SHANGSHANGGYCBRQ()
                        );

                        srcChaoBiaoSJEntityList.add(chaoBiaoSJEntity);
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();

                    List<ChaoBiaoSJEntity> destChaoBiaoSJEntityList
                            = synchronousTaskApiService.updateChaoBiaoSJToServer(srcChaoBiaoSJEntityList,
                            duRecordInfo.getDeviceId(), duRecordInfo.getAccount(), duRecordInfo.getTaskId());

                    for (DURecord duRecord : srcDuRecordList) {
                        duRecord.setShangchuanbz(DURecord.SHANGCHUANBZ_YISHANGC);
                        if (destChaoBiaoSJEntityList != null) {
                            for (ChaoBiaoSJEntity chaoBiaoSJEntity : destChaoBiaoSJEntityList) {
                                if ((duRecord.getRenwubh() == chaoBiaoSJEntity.getI_RenWuBH())
                                        && (duRecord.getCh().equals(chaoBiaoSJEntity.getCH()))
                                        && (duRecord.getCid().equals(chaoBiaoSJEntity.getCID()))) {
                                    duRecord.setShangchuanbz(DURecord.SHANGCHUANBZ_WEISHANGC);
                                    break;
                                }
                            }
                        }
                    }

                    subscriber.onNext(srcDuRecordList);
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
     * upload records
     *
     * @param duRecordInfo
     * @return
     */
    public Observable<List<DUDelayRecord>> uploadRecords(final DUDelayRecordInfo duRecordInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUDelayRecord>>() {
            @Override
            public void call(Subscriber<? super List<DUDelayRecord>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duRecordInfo == null) {
                        throw new NullPointerException("duRecordInfo is null");
                    }

                    List<DUDelayRecord> delayRecords = duRecordInfo.getDelayRecords();
                    if (delayRecords == null) {
                        throw new NullPointerException("srcDuRecordList is null");
                    }

                    List<DelayInfoEntity> list = new ArrayList<>();
                    for (DUDelayRecord record : delayRecords) {
                        DelayInfoEntity delay = new DelayInfoEntity(
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

                        list.add(delay);
                    }

                    WorkApiService synchronousTaskApiService = new WorkApiService();

                    List<DelayInfoEntity> delayInfos = synchronousTaskApiService
                            .SyncDelayInfos(duRecordInfo.getAccount(), duRecordInfo.getDeviceId(), list);

                    for (DUDelayRecord duRecord : delayRecords) {
                        duRecord.setI_ShangChuanBZ(DURecord.SHANGCHUANBZ_YISHANGC);
                        if (delayInfos != null) {
                            for (DelayInfoEntity delayInfo : delayInfos) {
                                if ((duRecord.getI_RENWUBH() == delayInfo.getRENWUBH())
                                        && (duRecord.getS_CH().equals(delayInfo.getCH()))
                                        && (duRecord.getS_CID().equals(delayInfo.getCID()))) {
                                    duRecord.setI_ShangChuanBZ(DURecord.SHANGCHUANBZ_WEISHANGC);
                                    break;
                                }
                            }
                        }
                    }

                    subscriber.onNext(delayRecords);
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
     * upload records
     *
     * @param duSamplingInfo
     * @return
     */
    public Observable<List<DUSampling>> uploadSamplings(final DUSamplingInfo duSamplingInfo) {
        return Observable.create(new Observable.OnSubscribe<List<DUSampling>>() {
            @Override
            public void call(Subscriber<? super List<DUSampling>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duSamplingInfo == null) {
                        throw new NullPointerException("duRecordInfo is null");
                    }

                    List<DUSampling> srcDuSamplingList = duSamplingInfo.getDuSamplingList();
                    if (srcDuSamplingList == null) {
                        throw new NullPointerException("srcDuSamplingList is null");
                    }

                    List<JiChaSJEntity> srcJiChaSJEntityList = new ArrayList<>();
                    for (DUSampling duSampling : srcDuSamplingList) {
                        JiChaSJEntity jiChaSJEntity = new JiChaSJEntity(
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
                                duSampling.getLastReadingChild(),
                                duSampling.getReadingChild(),
                                duSampling.getJiChaCM(),
                                duSampling.getJiChaSL(),
                                duSampling.getJiChaZTBM(),
                                duSampling.getJiChaRQ(),
                                duSampling.getJiChaZTMC()
                        );
                        srcJiChaSJEntityList.add(jiChaSJEntity);
                    }

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    List<JiChaSJEntity> destJiChaSJEntityList =
                            synchronousTaskApiService.updateJiChaSJToServer(srcJiChaSJEntityList,
                                    duSamplingInfo.getDeviceId(), duSamplingInfo.getAccount(), duSamplingInfo.getTaskId());

                    for (DUSampling duSampling : srcDuSamplingList) {
                        duSampling.setShangchuanbz(duSampling.SHANGCHUANBZ_WEISHANGC);
                        if (destJiChaSJEntityList != null) {
                            for (JiChaSJEntity jiChaSJEntity : destJiChaSJEntityList) {
                                if ((duSampling.getRenwubh() == jiChaSJEntity.getI_RenWuBH())
                                        && (duSampling.getCh().equals(jiChaSJEntity.getCH()))
                                        && (duSampling.getCid().equals(jiChaSJEntity.getCID()))) {
                                    duSampling.setShangchuanbz(DUSampling.SHANGCHUANBZ_YISHANGC);
                                    break;
                                }
                            }
                        }
                    }

                    subscriber.onNext(srcDuSamplingList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Boolean> uploadCards(final DUCardInfo duCardInfo) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duCardInfo == null)
                            || TextUtil.isNullOrEmpty(duCardInfo.getAccount())
                            || (duCardInfo.getTaskId() <= 0)
                            || TextUtil.isNullOrEmpty(duCardInfo.getVolume())
                            || (duCardInfo.getDuCardList() == null)
                            || (duCardInfo.getDuCardList().size() <= 0)) {
                        throw new NullPointerException("duCardInfo contain null pointer");
                    }

                    List<BiaoKaXXEntity> biaoKaXXEntityList = new ArrayList<BiaoKaXXEntity>();
                    List<DUCard> duCardList = duCardInfo.getDuCardList();
                    for (DUCard duCard : duCardList) {
                        BiaoKaXXEntity biaoKaXXEntity = new BiaoKaXXEntity();
                        biaoKaXXEntity.setcH(duCard.getCh());
                        biaoKaXXEntity.setCeNeiXH(duCard.getCeneixh());
                        biaoKaXXEntity.setcID(duCard.getCid());
                        biaoKaXXEntityList.add(biaoKaXXEntity);
                    }

                    BusinessApiService businessApiService = new BusinessApiService();
                    boolean ret = businessApiService.UpdateBiaoKaXXList(
                            duCardInfo.getAccount(), duCardInfo.getTaskId(), biaoKaXXEntityList);
                    subscriber.onNext(ret);
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
     * get records of history
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
                    if (duChaoBiaoJLInfo == null) {
                        throw new NullPointerException("duChaoBiaoJLInfo contain null pointer");
                    }

                    BusinessApiService businessApiService = new BusinessApiService();
                    DUChaoBiaoJLInfo.FilterType filterType = duChaoBiaoJLInfo.getFilterType();
                    List<ChaoBiaoJLEntity> chaoBiaoJLEntityList;
                    if (filterType == DUChaoBiaoJLInfo.FilterType.ONE) {
                        String customerId = duChaoBiaoJLInfo.getCustomerId();
                        if (TextUtil.isNullOrEmpty(customerId)) {
                            throw new NullPointerException("duChaoBiaoJLInfo contain null pointer");
                        }

                        chaoBiaoJLEntityList = businessApiService.queryChaoBiaoXX(customerId);
                    } else {
                        if (duChaoBiaoJLInfo.getVolume() == null
                                || duChaoBiaoJLInfo.getMonth() < 0) {
                            throw new NullPointerException("duChaoBiaoJLInfo contain null pointer");
                        }

                        chaoBiaoJLEntityList = businessApiService.queryAllChaoBiaoXX(
                                duChaoBiaoJLInfo.getVolume(),
                                duChaoBiaoJLInfo.getMonth());
                    }
                    if (chaoBiaoJLEntityList != null) {
                        List<DUChaoBiaoJL> duChaoBiaoJLs = new ArrayList<>();
                        for (ChaoBiaoJLEntity chaoBiaoJLEntity : chaoBiaoJLEntityList) {
                            DUChaoBiaoJL duChaoBiaoJL = new DUChaoBiaoJL(
                                    chaoBiaoJLEntity.getId(),
                                    chaoBiaoJLEntity.getcId(),
                                    chaoBiaoJLEntity.getChaoBiaoN(),
                                    chaoBiaoJLEntity.getChaoBiaoY(),
                                    chaoBiaoJLEntity.getChaoCi(),
                                    chaoBiaoJLEntity.getChaoBiaoRQ(),
                                    chaoBiaoJLEntity.getShangCiCM(),
                                    chaoBiaoJLEntity.getBenCiCM(),
                                    chaoBiaoJLEntity.getChaoJianSL(),
                                    chaoBiaoJLEntity.getChaoBiaoZT(),
                                    chaoBiaoJLEntity.getS_ChaoBiaoY(),
                                    chaoBiaoJLEntity.getChaoBiaoBZ(),
                                    chaoBiaoJLEntity.get_CHAOBIAOZTBM(),
                                    chaoBiaoJLEntity.get_LIANGGAOLDYYBM()
                            );
                            duChaoBiaoJLs.add(duChaoBiaoJL);
                        }

                        subscriber.onNext(duChaoBiaoJLs);
                    } else {
                        subscriber.onError(new Throwable("ChaoBiaoJLEntity is null"));
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
     * get information of jiao fei
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
                    if (duJiaoFeiXXInfo == null) {
                        throw new NullPointerException("duJiaoFeiXXInfo contains null pointer");
                    }

                    DUJiaoFeiXXInfo.FilterType filterType = duJiaoFeiXXInfo.getFilterType();
                    BusinessApiService businessApiService = new BusinessApiService();
                    List<JiaoFeiXXEntity> jiaoFeiXXEntityList;
                    if (filterType == DUJiaoFeiXXInfo.FilterType.ONE) {
                        String customerId = duJiaoFeiXXInfo.getCustomerId();
                        if (TextUtil.isNullOrEmpty(customerId)) {
                            throw new NullPointerException("duJiaoFeiXXInfo contain null pointer");
                        }

                        jiaoFeiXXEntityList = businessApiService.queryJiaoFeiXX(customerId);
                    } else {
                        if (duJiaoFeiXXInfo.getVolume() == null
                                || duJiaoFeiXXInfo.getMonth() < 0) {
                            throw new NullPointerException("duJiaoFeiXXInfo contains null pointer");
                        }

                        jiaoFeiXXEntityList = businessApiService.queryAllJiaoFeiXX(duJiaoFeiXXInfo.getVolume(),
                                duJiaoFeiXXInfo.getMonth());
                    }

                    if (jiaoFeiXXEntityList != null) {
                        List<DUJiaoFeiXX> duJiaoFeiXXList = new ArrayList<DUJiaoFeiXX>();
                        for (JiaoFeiXXEntity jiaoFeiXXEntity : jiaoFeiXXEntityList) {
                            DUJiaoFeiXX duJiaoFeiXX = new DUJiaoFeiXX(
                                    jiaoFeiXXEntity.getcId(),
                                    jiaoFeiXXEntity.getZhangWuNY(),
                                    jiaoFeiXXEntity.getFeEID(),
                                    jiaoFeiXXEntity.getChaoBiaoRQ(),
                                    jiaoFeiXXEntity.getKaiZhangRQ(),
                                    jiaoFeiXXEntity.getShouFeiRQ(),
                                    jiaoFeiXXEntity.getjE(),
                                    jiaoFeiXXEntity.getShiShouWYJ(),
                                    jiaoFeiXXEntity.getShiShouZJE(),
                                    jiaoFeiXXEntity.getShouFeiTJ()
                            );

                            duJiaoFeiXXList.add(duJiaoFeiXX);
                        }

                        subscriber.onNext(duJiaoFeiXXList);
                    } else {
                        subscriber.onError(new Throwable("jiaoFeiXXEntityList is null"));
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
     * get information of qian fei
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
                    if (duQianFeiXXInfo == null) {
                        throw new NullPointerException("duQianFeiXXInfo contains null pointer");
                    }

                    DUQianFeiXXInfo.FilterType filterType = duQianFeiXXInfo.getFilterType();
                    BusinessApiService businessApiService = new BusinessApiService();
                    List<QianFeiXXEntity> qianFeiXXEntityList;
                    if (filterType == DUQianFeiXXInfo.FilterType.ONE) {
                        String customerId = duQianFeiXXInfo.getCustomerId();
                        if (TextUtil.isNullOrEmpty(customerId)) {
                            throw new NullPointerException("duQianFeiXXInfo contain null pointer");
                        }

                        qianFeiXXEntityList = businessApiService.queryQianF(customerId);
                    } else {
                        if (duQianFeiXXInfo.getVolume() == null
                                || duQianFeiXXInfo.getMonth() < 0) {
                            throw new NullPointerException("duQianFeiXXInfo contains null pointer");
                        }

                        qianFeiXXEntityList = businessApiService.queryAllQianF(duQianFeiXXInfo.getVolume(),
                                duQianFeiXXInfo.getMonth());
                    }

                    if (qianFeiXXEntityList != null) {
                        List<DUQianFeiXX> duQianFeiXXList = new ArrayList<DUQianFeiXX>();
                        for (QianFeiXXEntity qianFeiXXEntity : qianFeiXXEntityList) {
                            DUQianFeiXX duQianFeiXX = new DUQianFeiXX(
                                    qianFeiXXEntity.getcH(),
                                    qianFeiXXEntity.getcId(),
                                    qianFeiXXEntity.getChaoBiaoN(),
                                    qianFeiXXEntity.getChaoBiaoY(),
                                    qianFeiXXEntity.getZhangWuNY(),
                                    qianFeiXXEntity.getChaoBiaoRQ(),
                                    qianFeiXXEntity.getChaoCi(),
                                    qianFeiXXEntity.getKaiZhangSL(),
                                    (double) qianFeiXXEntity.getjE(),
                                    qianFeiXXEntity.getFeeId(),
                                    (double) qianFeiXXEntity.getYingShouWYJ(),
                                    qianFeiXXEntity.get_SHUIFEI(),
                                    qianFeiXXEntity.get_PAISHUIF()
                            );
                            duQianFeiXXList.add(duQianFeiXX);
                        }
                        subscriber.onNext(duQianFeiXXList);
                    } else {
                        subscriber.onError(new Throwable("qianFeiXXEntityList is null"));
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
     * get information of qian fei
     *
     * @param info
     * @return
     */
    public Observable<List<DUQianFeiXX>> getDelayQianFeiXXs(final DUDelayQianFeiInfo info) {
        return Observable.create(new Observable.OnSubscribe<List<DUQianFeiXX>>() {
            @Override
            public void call(Subscriber<? super List<DUQianFeiXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (info == null) {
                        throw new NullPointerException("getDelayQianFeiXXs info null");
                    }

                    WorkApiService workApiService = new WorkApiService();
                    List<QianFeiXXEntity> qianFeiXXEntityList = workApiService
                            .getDelayQianFeiXXs(info.getAccount(), info.getDeviceId());
                    if (qianFeiXXEntityList != null) {
                        List<DUQianFeiXX> duQianFeiXXList = new ArrayList<>();
                        for (QianFeiXXEntity qianFeiXXEntity : qianFeiXXEntityList) {
                            DUQianFeiXX duQianFeiXX = new DUQianFeiXX(
                                    qianFeiXXEntity.getcH(),
                                    qianFeiXXEntity.getcId(),
                                    qianFeiXXEntity.getChaoBiaoN(),
                                    qianFeiXXEntity.getChaoBiaoY(),
                                    qianFeiXXEntity.getZhangWuNY(),
                                    qianFeiXXEntity.getChaoBiaoRQ(),
                                    qianFeiXXEntity.getChaoCi(),
                                    qianFeiXXEntity.getKaiZhangSL(),
                                    (double) qianFeiXXEntity.getjE(),
                                    qianFeiXXEntity.getFeeId(),
                                    (double) qianFeiXXEntity.getYingShouWYJ(),
                                    qianFeiXXEntity.get_SHUIFEI(),
                                    qianFeiXXEntity.get_PAISHUIF()
                            );
                            duQianFeiXXList.add(duQianFeiXX);
                        }
                        subscriber.onNext(duQianFeiXXList);
                    } else {
                        subscriber.onError(new Throwable("getDelayQianFeiXXs is null"));
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

    public Observable<List<DUChaoBiaoJL>> getDelayChaoBiaoJL(final DUDelayChaoBiaoJLInfo info) {
        return Observable.create(new Observable.OnSubscribe<List<DUChaoBiaoJL>>() {
            @Override
            public void call(Subscriber<? super List<DUChaoBiaoJL>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (info == null) {
                        throw new NullPointerException("getDelayChaoBiaoJL contain null pointer");
                    }

                    WorkApiService workApiService = new WorkApiService();
                    List<ChaoBiaoJLEntity> chaoBiaoJLEntityList =
                            workApiService.queryDelayChaoBiaoJL(info.getAccount(), info.getDeviceId());

                    if (chaoBiaoJLEntityList != null) {
                        List<DUChaoBiaoJL> duChaoBiaoJLs = new ArrayList<>();
                        for (ChaoBiaoJLEntity chaoBiaoJLEntity : chaoBiaoJLEntityList) {
                            DUChaoBiaoJL duChaoBiaoJL = new DUChaoBiaoJL(
                                    chaoBiaoJLEntity.getId(),
                                    chaoBiaoJLEntity.getcId(),
                                    chaoBiaoJLEntity.getChaoBiaoN(),
                                    chaoBiaoJLEntity.getChaoBiaoY(),
                                    chaoBiaoJLEntity.getChaoCi(),
                                    chaoBiaoJLEntity.getChaoBiaoRQ(),
                                    chaoBiaoJLEntity.getShangCiCM(),
                                    chaoBiaoJLEntity.getBenCiCM(),
                                    chaoBiaoJLEntity.getChaoJianSL(),
                                    chaoBiaoJLEntity.getChaoBiaoZT(),
                                    chaoBiaoJLEntity.getS_ChaoBiaoY(),
                                    chaoBiaoJLEntity.getChaoBiaoBZ(),
                                    chaoBiaoJLEntity.get_CHAOBIAOZTBM(),
                                    chaoBiaoJLEntity.get_LIANGGAOLDYYBM()
                            );
                            duChaoBiaoJLs.add(duChaoBiaoJL);
                        }

                        subscriber.onNext(duChaoBiaoJLs);
                    } else {
                        subscriber.onError(new Throwable("DUDelayChaoBiaoJLInfo is null"));
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
     * get information of jiao fei
     *
     * @param info
     * @return
     */
    public Observable<List<DUJiaoFeiXX>> getDelayJiaoFeiXX(final DUDelayJiaoFeiXXInfo info) {
        return Observable.create(new Observable.OnSubscribe<List<DUJiaoFeiXX>>() {
            @Override
            public void call(Subscriber<? super List<DUJiaoFeiXX>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (info == null) {
                        throw new NullPointerException("getJiaoFeiXX contains null pointer");
                    }

                    WorkApiService workApiService = new WorkApiService();
                    List<JiaoFeiXXEntity> jiaoFeiXXEntityList =
                            workApiService.queryDelayJiaoFeiXX(info.getAccount(), info.getDeviceId());

                    if (jiaoFeiXXEntityList != null) {
                        List<DUJiaoFeiXX> duJiaoFeiXXList = new ArrayList<>();
                        for (JiaoFeiXXEntity jiaoFeiXXEntity : jiaoFeiXXEntityList) {
                            DUJiaoFeiXX duJiaoFeiXX = new DUJiaoFeiXX(
                                    jiaoFeiXXEntity.getcId(),
                                    jiaoFeiXXEntity.getZhangWuNY(),
                                    jiaoFeiXXEntity.getFeEID(),
                                    jiaoFeiXXEntity.getChaoBiaoRQ(),
                                    jiaoFeiXXEntity.getKaiZhangRQ(),
                                    jiaoFeiXXEntity.getShouFeiRQ(),
                                    jiaoFeiXXEntity.getjE(),
                                    jiaoFeiXXEntity.getShiShouWYJ(),
                                    jiaoFeiXXEntity.getShiShouZJE(),
                                    jiaoFeiXXEntity.getShouFeiTJ()
                            );

                            duJiaoFeiXXList.add(duJiaoFeiXX);
                        }

                        subscriber.onNext(duJiaoFeiXXList);
                    } else {
                        subscriber.onError(new Throwable("jiaoFeiXXEntityList is null"));
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
     * get information of huan biao
     *
     * @param info
     * @return
     */
    public Observable<List<DUHuanBiaoJL>> getDelayHuanBiaoJL(final DUDelayHuanBiaoJLInfo info) {
        return Observable.create(new Observable.OnSubscribe<List<DUHuanBiaoJL>>() {
            @Override
            public void call(Subscriber<? super List<DUHuanBiaoJL>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (info == null) {
                        throw new NullPointerException("getDelayHuanBiaoJL contains null pointer");
                    }

                    WorkApiService workApiService = new WorkApiService();
                    List<HuanBiaoXXEntity> huanBiaoXXEntityList =
                            workApiService.queryDelayHuanBiaoXX(info.getAccount(), info.getDeviceId());

                    if (huanBiaoXXEntityList != null) {
                        List<DUHuanBiaoJL> duHuanBiaoJLList = new ArrayList<>();
                        for (HuanBiaoXXEntity huanBiaoXXEntity : huanBiaoXXEntityList) {
                            DUHuanBiaoJL duHuanBiaoJL = new DUHuanBiaoJL(
                                    huanBiaoXXEntity.getS_CID(),
                                    huanBiaoXXEntity.getS_BiaoWeiXX(),
                                    huanBiaoXXEntity.getS_DengJiR(),
                                    huanBiaoXXEntity.getD_DengJiRQ(),
                                    huanBiaoXXEntity.getS_HuanBiaoLX(),
                                    huanBiaoXXEntity.getS_HuanBiaoYY(),
                                    huanBiaoXXEntity.getS_JiubiaoBH(),
                                    huanBiaoXXEntity.getS_JiuBiaoGYH(),
                                    huanBiaoXXEntity.getS_JiuBiaoCJ(),
                                    huanBiaoXXEntity.getS_XinBiaoBH(),
                                    huanBiaoXXEntity.getS_XinBiaoGYH(),
                                    huanBiaoXXEntity.getS_XinBiaoCJ(),
                                    huanBiaoXXEntity.getS_JiuBiaoXH(),
                                    huanBiaoXXEntity.getS_XinBiaoXH(),
                                    huanBiaoXXEntity.getS_JiuBiaoKJ(),
                                    huanBiaoXXEntity.getS_XinBiaoKJ(),
                                    huanBiaoXXEntity.getI_XinBiaoLC(),
                                    huanBiaoXXEntity.getI_ShangCiCM(),
                                    huanBiaoXXEntity.getI_JiuBiaoCM(),
                                    huanBiaoXXEntity.getI_XinBiaoDM(),
                                    huanBiaoXXEntity.getD_ShiGongRQ(),
                                    huanBiaoXXEntity.getS_HuiTianR(),
                                    huanBiaoXXEntity.getD_HuanBiaoHTRQ(),
                                    huanBiaoXXEntity.getS_HuanBiaoZT(),
                                    huanBiaoXXEntity.getS_BeiZhu()
                            );
                            duHuanBiaoJLList.add(duHuanBiaoJL);
                        }
                        subscriber.onNext(duHuanBiaoJLList);
                    } else {
                        subscriber.onError(new Throwable("huanBiaoXXEntityList is null"));
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
     * get information of huan biao
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
                    if (duHuanBiaoJLInfo == null) {
                        throw new NullPointerException("duHuanBiaoJLInfo contains null pointer");
                    }

                    DUHuanBiaoJLInfo.FilterType filterType = duHuanBiaoJLInfo.getFilterType();
                    BusinessApiService businessApiService = new BusinessApiService();
                    List<HuanBiaoXXEntity> huanBiaoXXEntityList;
                    if (filterType == DUHuanBiaoJLInfo.FilterType.ONE) {
                        String customerId = duHuanBiaoJLInfo.getCustomerId();
                        if (TextUtil.isNullOrEmpty(customerId)) {
                            throw new NullPointerException("duHuanBiaoJLInfo contain null pointer");
                        }

                        huanBiaoXXEntityList = businessApiService.queryHuanBiaoXX(customerId);
                    } else {
                        if (duHuanBiaoJLInfo.getVolume() == null
                                || duHuanBiaoJLInfo.getMonth() < 0) {
                            throw new NullPointerException("duHuanBiaoJLInfo contains null pointer");
                        }

                        huanBiaoXXEntityList = businessApiService.queryAllHuanBiaoXX(duHuanBiaoJLInfo.getVolume(),
                                duHuanBiaoJLInfo.getMonth());
                    }

                    if (huanBiaoXXEntityList != null) {
                        List<DUHuanBiaoJL> duHuanBiaoJLList = new ArrayList<DUHuanBiaoJL>();
                        for (HuanBiaoXXEntity huanBiaoXXEntity : huanBiaoXXEntityList) {
                            DUHuanBiaoJL duHuanBiaoJL = new DUHuanBiaoJL(
                                    huanBiaoXXEntity.getS_CID(),
                                    huanBiaoXXEntity.getS_BiaoWeiXX(),
                                    huanBiaoXXEntity.getS_DengJiR(),
                                    huanBiaoXXEntity.getD_DengJiRQ(),
                                    huanBiaoXXEntity.getS_HuanBiaoLX(),
                                    huanBiaoXXEntity.getS_HuanBiaoYY(),
                                    huanBiaoXXEntity.getS_JiubiaoBH(),
                                    huanBiaoXXEntity.getS_JiuBiaoGYH(),
                                    huanBiaoXXEntity.getS_JiuBiaoCJ(),
                                    huanBiaoXXEntity.getS_XinBiaoBH(),
                                    huanBiaoXXEntity.getS_XinBiaoGYH(),
                                    huanBiaoXXEntity.getS_XinBiaoCJ(),
                                    huanBiaoXXEntity.getS_JiuBiaoXH(),
                                    huanBiaoXXEntity.getS_XinBiaoXH(),
                                    huanBiaoXXEntity.getS_JiuBiaoKJ(),
                                    huanBiaoXXEntity.getS_XinBiaoKJ(),
                                    huanBiaoXXEntity.getI_XinBiaoLC(),
                                    huanBiaoXXEntity.getI_ShangCiCM(),
                                    huanBiaoXXEntity.getI_JiuBiaoCM(),
                                    huanBiaoXXEntity.getI_XinBiaoDM(),
                                    huanBiaoXXEntity.getD_ShiGongRQ(),
                                    huanBiaoXXEntity.getS_HuiTianR(),
                                    huanBiaoXXEntity.getD_HuanBiaoHTRQ(),
                                    huanBiaoXXEntity.getS_HuanBiaoZT(),
                                    huanBiaoXXEntity.getS_BeiZhu()
                            );
                            duHuanBiaoJLList.add(duHuanBiaoJL);
                        }
                        subscriber.onNext(duHuanBiaoJLList);
                    } else {
                        subscriber.onError(new Throwable("huanBiaoXXEntityList is null"));
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
     * combined search
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
                    if ((duCombinedInfo == null)
                            || (duCombinedInfo.getDuCombined() == null)) {
                        throw new NullPointerException("duComBinedInfo contains null parameter");
                    }

                    // TODO
                    BusinessApiService businessApiService = new BusinessApiService();

                    CombinedInfoEntity combinedInfoEntity = new CombinedInfoEntity();
                    DUCombined combined = duCombinedInfo.getDuCombined();
                    combinedInfoEntity.set_ch(combined.get_ch());
                    // combinedInfoEntity.set_account(duComBinedInfo.getAccount());
                    combinedInfoEntity.set_cid(combined.get_cid());
                    combinedInfoEntity.set_huming(combined.get_huming());
                    combinedInfoEntity.set_dizhi(combined.get_dizhi());
                    combinedInfoEntity.set_lianxidh(combined.get_lianxidh());
                    // combinedInfoEntity.set_jianhao(duComBinedInfo.getDuComBined().get_jianhao());
                    combinedInfoEntity.set_biaohao(combined.get_biaohao());


                    List<BiaoKaXXEntity> biaoKaXXEntityList =
                            businessApiService.getCombinedQueryingResults(combinedInfoEntity);

                    if (biaoKaXXEntityList != null) {
                        List<DUCard> duCardList = new ArrayList<>();
                        for (BiaoKaXXEntity biaoKaXXEntity : biaoKaXXEntityList) {
                            DUCard duCard = new DUCard(
                                    -1,
                                    biaoKaXXEntity.getcH(),
                                    biaoKaXXEntity.getCeNeiXH(),
                                    biaoKaXXEntity.getcID(),
                                    biaoKaXXEntity.getKeHuBH(),
                                    biaoKaXXEntity.getKeHuMC(),
                                    biaoKaXXEntity.getsT(),
                                    biaoKaXXEntity.getDiZhi(),
                                    biaoKaXXEntity.getLianXiR(),
                                    biaoKaXXEntity.getLianXiSJ(),
                                    biaoKaXXEntity.getLianXiDH(),
                                    biaoKaXXEntity.getShouFeiFS(),
                                    biaoKaXXEntity.getYinHangMC(),
                                    biaoKaXXEntity.getJianHao(),
                                    biaoKaXXEntity.getJianHaoMC(),
                                    biaoKaXXEntity.getYongHuZT(),
                                    biaoKaXXEntity.getLiHuRQ(),
                                    biaoKaXXEntity.getBiaoWei(),
                                    biaoKaXXEntity.getShuiBiaoGYH(),
                                    biaoKaXXEntity.getShuiBiaoTXM(),
                                    biaoKaXXEntity.getKouJingMC(),
                                    biaoKaXXEntity.getLiangCheng(),
                                    biaoKaXXEntity.getBiaoXing(),
                                    biaoKaXXEntity.getShuiBiaoCJ(),
                                    biaoKaXXEntity.getShuiBiaoFL(),
                                    biaoKaXXEntity.getShuiBiaoFLMC(),
                                    biaoKaXXEntity.getShuibiaoBL(),
                                    biaoKaXXEntity.getKaiZhangFL(),
                                    biaoKaXXEntity.getGongNengFL(),
                                    biaoKaXXEntity.getShiFouJHYS(),
                                    biaoKaXXEntity.getShiFouShouLJF(),
                                    biaoKaXXEntity.getLaJiFeiXS().doubleValue(),
                                    biaoKaXXEntity.getShiFouShouWYJ(),
                                    biaoKaXXEntity.getShiFouDEJJ(),
                                    biaoKaXXEntity.getDingESL(),
                                    biaoKaXXEntity.getZongBiaoBH(),
                                    biaoKaXXEntity.getZhuangBiaoRQ(),
                                    biaoKaXXEntity.getHuanBiaoRQ(),
                                    biaoKaXXEntity.getXinBiaoDM(),
                                    biaoKaXXEntity.getJiuBiaoCM(),
                                    biaoKaXXEntity.getX1(),
                                    biaoKaXXEntity.getY1(),
                                    biaoKaXXEntity.getX(),
                                    biaoKaXXEntity.getY(),
                                    biaoKaXXEntity.getFenTanFS(),
                                    biaoKaXXEntity.getFenTanL(),
                                    biaoKaXXEntity.getYuCunKYE().doubleValue(),
                                    biaoKaXXEntity.getQianFeiZBS(),
                                    biaoKaXXEntity.getQianFeiZJE().doubleValue(),
                                    biaoKaXXEntity.getBeiZhu(),
                                    biaoKaXXEntity.getshuibiaoZT(),
                                    biaoKaXXEntity.getRENKOUS().doubleValue(),
                                    biaoKaXXEntity.getDIBAOYHSL(),
                                    biaoKaXXEntity.getGONGCEYHSL(),
                                    biaoKaXXEntity.getYONGSHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getPAISHUIZKL().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL1().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL2().doubleValue(),
                                    biaoKaXXEntity.getZHEKOUL3().doubleValue(),
                                    0,
                                    0,
                                    null,
                                    null,
                                    null,
                                    0,
                                    null,
                                    0,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    BiaoKaXXEntity.CHAOBIAO_BIAOKAXX,
                                    biaoKaXXEntity.get_GroupId(),
                                    biaoKaXXEntity.get_KuaiHao(),
                                    biaoKaXXEntity.get_CustomerType(),
                                    biaoKaXXEntity.get_IsVIP(),
                                    biaoKaXXEntity.get_VIPCode(),
                                    biaoKaXXEntity.get_DateOfContract(),
                                    biaoKaXXEntity.get_Contractage(),
                                    biaoKaXXEntity.get_PostType(),
                                    biaoKaXXEntity.get_Creditrating(),
                                    biaoKaXXEntity.get_WaterMode(),
                                    biaoKaXXEntity.get_Strongdate(),
                                    biaoKaXXEntity.get_PayMethod(),
                                    biaoKaXXEntity.get_CaliberValue(),
                                    biaoKaXXEntity.get_MonthTotal(),
                                    biaoKaXXEntity.get_QuarterTotal(),
                                    biaoKaXXEntity.get_YearTotal(),
                                    biaoKaXXEntity.get_Extend()
                            );
                            duCardList.add(duCard);
                        }

                        subscriber.onNext(duCardList);
                    } else {
                        subscriber.onError(new Throwable("biaoKaXXEntityList is null"));
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
     * @param duMedia
     * @return
     */
    public Observable<DUMedia> uploadMedia(final DUMedia duMedia) {
        return Observable.create(new Observable.OnSubscribe<DUMedia>() {
            @Override
            public void call(Subscriber<? super DUMedia> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duMedia == null) {
                        throw new NullPointerException("duMedia is null");
                    } else if ((duMedia.getAccount() == null)
                            || (duMedia.getCh() == null)
                            || (duMedia.getCid() == null)
                            || (duMedia.getWenjianlj() == null)
                            || (duMedia.getWenjianmc() == null)) {
                        throw new NullPointerException("parameter is null");
                    }
                    String filePath = duMedia.getWenjianlj(); //
                    File file = new File(filePath);
                    if (!file.exists()) {
//                        Log.e("fileIsNoExists", filePath + "文件不存在" + i);
                        //xiaochao --
//                        throw new NullPointerException(filePath + " is not exist");
                        // xiaochao ++
                        duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_YISHANGC);
                        subscriber.onNext(duMedia);
                        return;
                    }

                    String ch = duMedia.getCh();
                    int chaobiaoid = duMedia.getChaobiaoid();
                    String cid = duMedia.getCid();
                    String fileName = duMedia.getWenjianmc(); //
                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = fis.read(buffer)) >= 0) {
                        outputStream.write(buffer, 0, count);
                    }
                    fis.close();

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    boolean ret = synchronousTaskApiService.upLoadFileToServer(fileName,
                            ch, chaobiaoid, cid, outputStream.toByteArray());
                    if (ret) {
//                        Log.e("fileIsNoExists", "文件上传成功" + i);
                        duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_YISHANGC);
                    } else {
//                        Log.e("fileIsNoExists", "文件上传失败" + i);
                        duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                    }

                    subscriber.onNext(duMedia);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUMedia> uploadMediaRelation(final DUMedia duMedia, final boolean isOutSide) {
        return Observable.create(new Observable.OnSubscribe<DUMedia>() {
            @Override
            public void call(Subscriber<? super DUMedia> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duMedia == null) {
                        throw new NullPointerException("duMedia is null");
                    } else if ((duMedia.getAccount() == null)
                            || (duMedia.getCh() == null)
                            || (duMedia.getCid() == null)
                            || (duMedia.getWenjianlj() == null)
                            || (duMedia.getWenjianmc() == null)
                            || TextUtil.isNullOrEmpty(duMedia.getUrl())
                            || TextUtil.isNullOrEmpty(duMedia.getFileHash())) {
                        //throw new NullPointerException("parameter is null");
                        duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                    } else {
                        String mediaType;
                        int meterReaderId;
                        if (isOutSide) {
                            mediaType = "OutSideFile";
                            meterReaderId = duMedia.getRenwubh();
                        } else {
                            mediaType = "RecordFile";
                            meterReaderId = duMedia.getChaobiaoid();
                        }
                        SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                        boolean ret = synchronousTaskApiService.uploadFileRelation(meterReaderId,
                                duMedia.getCid(), duMedia.getUrl(), duMedia.getFileHash(),
                                mediaType);
                        if (ret) {
                            duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_YISHANGC);
                        } else {
                            duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                        }
                    }

                    subscriber.onNext(duMedia);
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
     * @param duMedia
     * @return
     */
    public Observable<DUMedia> uploadSamplingMedia(final DUMedia duMedia) {
        return Observable.create(new Observable.OnSubscribe<DUMedia>() {
            @Override
            public void call(Subscriber<? super DUMedia> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duMedia == null) {
                        throw new NullPointerException("duMedia is null");
                    } else if ((duMedia.getAccount() == null)
                            || (duMedia.getCh() == null)
                            || (duMedia.getCid() == null)
                            || (duMedia.getWenjianlj() == null)
                            || (duMedia.getWenjianmc() == null)) {
                        throw new NullPointerException("parameter is null");
                    }

                    String ch = duMedia.getCh();
                    int chaobiaoid = duMedia.getChaobiaoid();
                    String cid = duMedia.getCid();
                    String filePath = duMedia.getWenjianlj(); //
                    String fileName = duMedia.getWenjianmc(); //
                    FileInputStream fis = new FileInputStream(filePath);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = fis.read(buffer)) >= 0) {
                        outputStream.write(buffer, 0, count);
                    }
                    fis.close();

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    boolean ret = synchronousTaskApiService.upLoadFileToServer(fileName,
                            ch, chaobiaoid, cid, outputStream.toByteArray());
                    if (ret) {
                        duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_YISHANGC);
                    } else {
                        duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                    }

                    subscriber.onNext(duMedia);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Boolean> isQianFei(final String customerId) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    BusinessApiService businessApiService = new BusinessApiService();
                    boolean isQianFei = businessApiService.isQianFei(customerId);
                    subscriber.onNext(isQianFei);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUKeepAliveResult> keepAlive(final DUKeepAliveInfo duKeepAliveInfo) {
        return Observable.create(new Observable.OnSubscribe<DUKeepAliveResult>() {
            @Override
            public void call(Subscriber<? super DUKeepAliveResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duKeepAliveInfo == null)
                            || (duKeepAliveInfo.getDuKeepAlive() == null)) {
                        throw new NullPointerException("parameter is null");
                    }

                    DUKeepAlive duKeepAlive = duKeepAliveInfo.getDuKeepAlive();
                    KeepAliveInfoEntity keepAliveInfoEntity = new KeepAliveInfoEntity(
                            duKeepAlive.getUserID(),
                            duKeepAlive.getDeviceID(),
                            duKeepAlive.getAppVersion(),
                            duKeepAlive.getDataVersion(),
                            duKeepAlive.getSendTime(),
                            duKeepAlive.getX(),
                            duKeepAlive.getY());
                    SystemApiService systemApiService = new SystemApiService();
                    KeepAliveEntity keepAliveEntity = systemApiService.keepAlive(keepAliveInfoEntity);
                    subscriber.onNext(new DUKeepAliveResult(keepAliveEntity.getCurrentTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUUploadingFileResult> uploadFile(final DUUploadingFile duUploadingFile) {
        return Observable.create(new Observable.OnSubscribe<DUUploadingFileResult>() {
            @Override
            public void call(Subscriber<? super DUUploadingFileResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duUploadingFile == null)
                            || TextUtil.isNullOrEmpty(duUploadingFile.getAccount())
                            || TextUtil.isNullOrEmpty(duUploadingFile.getPath())) {
                        throw new NullPointerException("duUploadingFile is null");
                    }

                    String account = duUploadingFile.getAccount();
                    String path = duUploadingFile.getPath();
                    int index = path.lastIndexOf("/");
                    String name = path.substring(index + 1);
                    String dir = path.substring(0, index);
                    if (TextUtil.isNullOrEmpty(name)
                            || TextUtil.isNullOrEmpty(dir)) {
                        throw new NullPointerException("name or dir is null");
                    }

                    DUUploadingFile.FileType fileType = duUploadingFile.getFileType();
                    File file = new File(path);
                    if (!file.exists()) {
                        subscriber.onError(new Throwable("file isn't existing"));
                        return;
                    }

                    String zipFileName = String.format("%d.zip", new Date().getTime());
                    String zipFilePath = String.format("%s/%s", dir, zipFileName);
                    ZipUtil.ZipFolder(path, zipFilePath);
                    file = new File(zipFilePath);
                    if (!file.exists()) {
                        subscriber.onError(new Throwable("file isn't existing"));
                        return;
                    }

                    FileInputStream fis = new FileInputStream(zipFilePath);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = fis.read(buffer)) >= 0) {
                        outputStream.write(buffer, 0, count);
                    }
                    fis.close();

                    SynchronousTaskApiService synchronousTaskApiService = new SynchronousTaskApiService();
                    boolean ret = synchronousTaskApiService.uploadFile(account,
                            zipFileName, outputStream.toByteArray());
                    file.delete();
                    subscriber.onNext(new DUUploadingFileResult(ret));
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUCardCoordinateResult> uploadCardCoordinate(final DUCardCoordinateInfo duCardCoordinateInfo) {
        return Observable.create(new Observable.OnSubscribe<DUCardCoordinateResult>() {
            @Override
            public void call(Subscriber<? super DUCardCoordinateResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((duCardCoordinateInfo == null)
                            || TextUtil.isNullOrEmpty(duCardCoordinateInfo.getVolume())
                            || TextUtil.isNullOrEmpty(duCardCoordinateInfo.getCustomerId())) {
                        throw new NullPointerException("duCardCoordinateInfo contains null pointer");
                    }

                    int taskId = duCardCoordinateInfo.getTaskId();
                    String volume = duCardCoordinateInfo.getVolume();
                    String customerId = duCardCoordinateInfo.getCustomerId();
                    String longitude = String.valueOf(duCardCoordinateInfo.getLongitude());
                    String latitude = String.valueOf(duCardCoordinateInfo.getLatitude());
                    BusinessApiService businessApiService = new BusinessApiService();
                    boolean result = businessApiService.UpdateBiaoKaGIS(customerId, longitude, latitude);
                    subscriber.onNext(new DUCardCoordinateResult(
                            duCardCoordinateInfo,
                            result));
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
     * 计算金额
     *
     * @param duRecord
     * @return
     */
    public Observable<List<DUBillPreview>> calculateCash(final DURecord duRecord, final int meterType) {
        return Observable.create(new Observable.OnSubscribe<List<DUBillPreview>>() {
            @Override
            public void call(Subscriber<? super List<DUBillPreview>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    if (duRecord == null
                            || duRecord.getRenwubh() == 0
                            || duRecord.getChaobiaoid() == 0
                            || (duRecord.getBencicm() <= 0 && duRecord.getChaojiansl() <= 0)) {
                        throw new NullPointerException("duRecord contain null pointer");
                    }

                    BusinessApiService businessApiService = new BusinessApiService();
                    CalculateBillEntity calculateBillEntity = new CalculateBillEntity(duRecord.getRenwubh(),
                            duRecord.getCid(), duRecord.getChaobiaoid(), duRecord.getBencicm(), duRecord.getReadingChild(),
                            duRecord.getZhuangtaibm(), duRecord.getChaojiansl(), meterType, 0);
                    List<BillPreviewEntity> billPreviewEntityList = businessApiService.CalculateCash(calculateBillEntity);
                    List<DUBillPreview> resultList = new ArrayList<>();
                    DUBillPreview duBillPreview = null;
                    for (BillPreviewEntity billPreviewEntity : billPreviewEntityList) {
                        if (billPreviewEntity != null) {
                            duBillPreview = new DUBillPreview();
                            duBillPreview.setMeterCardId(billPreviewEntity.getMeterCardId());
                            duBillPreview.setTempFeeId(billPreviewEntity.getTempFeeId());
                            duBillPreview.setAccWater(billPreviewEntity.getAccWater());
                            duBillPreview.setAccMoney(billPreviewEntity.getAccMoney());
                            duBillPreview.setMessage(billPreviewEntity.getMessage());
                            resultList.add(duBillPreview);
                        }
                    }
                    subscriber.onNext(resultList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

}
