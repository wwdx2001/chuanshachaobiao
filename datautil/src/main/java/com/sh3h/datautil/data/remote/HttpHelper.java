package com.sh3h.datautil.data.remote;

import android.content.Context;

import com.example.dataprovider3.entity.CallForPaymentArrearsFeesDetailBean;
import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.datautil.data.entity.Coordinate;
import com.sh3h.datautil.data.entity.DUBillPreview;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardCoordinateInfo;
import com.sh3h.datautil.data.entity.DUCardCoordinateResult;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUChaoBiaoJL;
import com.sh3h.datautil.data.entity.DUChaoBiaoJLInfo;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.datautil.data.entity.DUCoor;
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
import com.sh3h.datautil.data.entity.DUKeepAliveInfo;
import com.sh3h.datautil.data.entity.DUKeepAliveResult;
import com.sh3h.datautil.data.entity.DULoginInfo;
import com.sh3h.datautil.data.entity.DULoginResult;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUMediaResponse;
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
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.MapConfig;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.RenWuXXEntity;
import com.squareup.otto.Bus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton
public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private final Bus mBus;
    private boolean isConnected;
    private boolean isTransformCoordinateConnected;
    private boolean isRestfulApi;
    private RestfulApiService httpService;
    private RestfulApiService transformService;
    private JsonRpcService jsonRpcService;

    @Inject
    public HttpHelper(@ApplicationContext Context context,
                      ConfigHelper configHelper,
                      Bus bus) {
        mContext = context;
        mConfigHelper = configHelper;
        mBus = bus;
        isConnected = false;
        isTransformCoordinateConnected = false;
        isRestfulApi = false;
        httpService = null;
        jsonRpcService = null;
    }

    /**
     * @param duDeviceInfo
     * @return
     */
    public Observable<DUDeviceResult> authorize(DUDeviceInfo duDeviceInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.authorize(duDeviceInfo);
        }
    }

    public Observable<DUUserResult> login(DULoginInfo duLoginInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.login(duLoginInfo)
                    .flatMap(new Func1<DULoginResult, Observable<DUUserResult>>() {
                        @Override
                        public Observable<DUUserResult> call(DULoginResult duLoginResult) {
                            DUUserInfo duUserInfo = new DUUserInfo(duLoginResult.getUserID());
                            return jsonRpcService.getUserInfo(duUserInfo);
                        }
                    });
        }
    }

    public Observable<DUUpdateResult> updateVersion(DUUpdateInfo duUpdateInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.updateVersion(duUpdateInfo);
        }
    }

    public Observable<DUMedia> uploadMedia2(DUMedia duMedia) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            if (httpService != null) {
                return uploadImage(duMedia);
            } else {
                //走这里
                return jsonRpcService.uploadMedia2(duMedia);
            }
        }
    }

    public Observable<RenWuXXEntity> getTaskIds(DUTaskIdInfo duTaskIdInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getTaskIds(duTaskIdInfo);
        }
    }

    public Observable<List<String>> getDelayIds(DUDelayIdInfo duTaskIdInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getDelayIds(duTaskIdInfo);
        }
    }

    public Observable<List<String>> getSamplingTaskIds(DUTaskIdInfo duTaskIdInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getSamplingTaskIds(duTaskIdInfo);
        }
    }

    public Observable<List<DUTask>> getTasks(DUTaskInfo duTaskInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getTasks(duTaskInfo);
        }
    }

    public Observable<List<DUSamplingTask>> getSamplingTasks(DUSamplingTaskInfo duSamplingTaskInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getSamplingTasks(duSamplingTaskInfo);
        }
    }

    public Observable<List<DUCard>> getCombinedResult(DUCombinedInfo duCombinedInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getCombinedResult(duCombinedInfo);
        }
    }

    public Observable<List<DURecord>> getRecords(DURecordInfo duRecordInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getRecords(duRecordInfo);
        }
    }

    public Observable<List<DUDelayRecord>> getDelayRecords(DUDelayRecordInfo duRecordInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getDelayRecords(duRecordInfo);
        }
    }

    public Observable<List<DUSampling>> getSamplingRecords(DUSamplingInfo duSamplingInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getSamplingRecords(duSamplingInfo);
        }
    }

    public Observable<List<DUWaiFuCBSJ>> getWaiFuCBSJs(DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getWaiFuCBSJs(duWaiFuCBSJInfo);
        }
    }

    public Observable<List<DURecord>> getTemporaryRecords(DURecordInfo duRecordInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getTemporaryRecords(duRecordInfo);
        }
    }

    public Observable<List<DUCard>> getCards(DUCardInfo duCardInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getCards(duCardInfo);
        }
    }

    public Observable<List<DUCard>> getDelayCards(DUDelayCardInfo duCardInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getDelayCards(duCardInfo);
        }
    }

    public Observable<List<DUCard>> getWaiFuCards(DUCardInfo duCardInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getWaiFuCards(duCardInfo);
        }
    }

    public Observable<List<DUCard>> getSamplingCards(DUCardInfo duCardInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getSamplingCards(duCardInfo);
        }
    }

    public Observable<List<DURecord>> uploadRecords(DURecordInfo duRecordInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadRecords(duRecordInfo);
        }
    }

    public Observable<List<DUDelayRecord>> uploadRecords(DUDelayRecordInfo duRecordInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadRecords(duRecordInfo);
        }
    }

    public Observable<List<DUSampling>> uploadSamplings(DUSamplingInfo duSamplingInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadSamplings(duSamplingInfo);
        }
    }

    public Observable<List<DURushPayTask>> uploadRushPayTasks(DURushPayTaskInfo duRushPayTaskInfo) {
        connect();
        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadRushPays(duRushPayTaskInfo);
        }
    }

    public Observable<List<DUWaiFuCBSJ>> uploadWaiFuCBSJs(DUWaiFuCBSJInfo duWaiFuCBSJInfo) {
        connect();
        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadWaiFuSJs(duWaiFuCBSJInfo);
        }
    }


    public Observable<List<DURushPayTask>> getRushPayTasks(DURushPayTaskInfo duRushPayTaskInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getRushPayTasks(duRushPayTaskInfo);
        }
    }

    public Observable<List<String>> getRushPayTaskIds(DURushPayTaskInfo duRushPayTaskInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getRushPayTaskIds(duRushPayTaskInfo);
        }
    }


    public Observable<Boolean> uploadCards(DUCardInfo duCardInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadCards(duCardInfo);
        }
    }

    public Observable<List<DUChaoBiaoJL>> getChaoBiaoJLs(DUChaoBiaoJLInfo duChaoBiaoJLInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getChaoBiaoJLs(duChaoBiaoJLInfo);
        }
    }


    public Observable<List<DUJiaoFeiXX>> getJiaoFeiXXs(DUJiaoFeiXXInfo duJiaoFeiXXInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getJiaoFeiXXs(duJiaoFeiXXInfo);
        }
    }

    public Observable<List<DUQianFeiXX>> getQianFeiXXs(DUQianFeiXXInfo duQianFeiXXInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getQianFeiXXs(duQianFeiXXInfo);
        }
    }

    public Observable<List<DUQianFeiXX>> getDelayQianFeiXXs(DUDelayQianFeiInfo info) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getDelayQianFeiXXs(info);
        }
    }

    public Observable<List<DUChaoBiaoJL>> getDelayChaoBiaoJL(DUDelayChaoBiaoJLInfo info) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getDelayChaoBiaoJL(info);
        }
    }

    public Observable<List<DUJiaoFeiXX>> getDelayJiaoFeiXX(DUDelayJiaoFeiXXInfo info) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getDelayJiaoFeiXX(info);
        }
    }

    public Observable<List<DUHuanBiaoJL>> getDelayHuanBiaoJL(DUDelayHuanBiaoJLInfo info) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getDelayHuanBiaoJL(info);
        }
    }

    public Observable<List<DUHuanBiaoJL>> getHuanBiaoXXs(DUHuanBiaoJLInfo duHuanBiaoJLInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getHuanBiaoXXs(duHuanBiaoJLInfo);
        }
    }

    public Observable<DUMedia> uploadMedia(DUMedia duMedia) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            if (httpService != null) {
                return uploadImage(duMedia);
            } else {
                return jsonRpcService.uploadMedia(duMedia);
            }
        }
    }

    public Observable<List<DUMedia>> uploadMediaList(List<DUMedia> duMediaList) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            if (httpService != null) {
                return uploadImageList2(duMediaList);
            } else {
                return null;
            }
        }
    }

    public Observable<DUMedia> uploadMediaRelation(DUMedia duMedia,boolean isOutSide) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadMediaRelation(duMedia,isOutSide);
        }
    }

    public Observable<DUMedia> uploadSamplingMedia(DUMedia duMedia) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadSamplingMedia(duMedia);
        }
    }

    public Observable<Boolean> isQianFei(String customerId) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.isQianFei(customerId);
        }
    }

    public Observable<DUKeepAliveResult> keepAlive(DUKeepAliveInfo duKeepAliveInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.keepAlive(duKeepAliveInfo);
        }
    }

    public Observable<DUUploadingFileResult> uploadFile(DUUploadingFile duUploadingFile) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadFile(duUploadingFile);
        }
    }

    public Observable<DUCardCoordinateResult> uploadCardCoordinate(DUCardCoordinateInfo duCardCoordinateInfo) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.uploadCardCoordinate(duCardCoordinateInfo);
        }
    }

    public Observable<Coordinate> transformCoordinate(double x, double y) {
        transformCoordinateConect();
        return transformService.transformCoordinate("json", x, y)
                .map(new Func1<DUCoor, Coordinate>() {
                    @Override
                    public Coordinate call(DUCoor coor) {
                        Coordinate coordinate = new Coordinate();
                        if (coor != null && coor.isSuccess() &&!TextUtil.isNullOrEmpty(coor.getCoor())) {
                            String data = coor.getCoor();
                            String[] datas = data.split(",");
                            if (datas.length == 2) {
                                try {
                                    coordinate.setLongitude(Double.parseDouble(datas[0]));
                                    coordinate.setLatitude(Double.parseDouble(datas[1]));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return coordinate;
                    }
                });
    }

    private void transformCoordinateConect(){
        if (isTransformCoordinateConnected){
            return;
        }

        MapConfig mapConfig = mConfigHelper.getMapConfig();
        String mapTransformUrl = mapConfig.getString(MapConfig.PARAM_MAP_SERVER_URL_TRANSFORM);

        if (!TextUtil.isNullOrEmpty(mapTransformUrl)) {
            transformService = RestfulApiService.Factory.newInstance(mBus, mapTransformUrl);
            isTransformCoordinateConnected = true;
        }
    }

    private void connect() {
        if (isConnected) {
            return;
        }

        SystemConfig systemConfig = mConfigHelper.getSystemConfig();
        String baseUrl;
        if (systemConfig.getBoolean(SystemConfig.PARAM_SERVER_USING_RESERVED, false)) {
            baseUrl = systemConfig.getString(SystemConfig.PARAM_SERVER_RESERVED_BASE_URI);
        } else {
            baseUrl = systemConfig.getString(SystemConfig.PARAM_SERVER_BASE_URI);
        }
        isRestfulApi = systemConfig.getBoolean(SystemConfig.PARAM_SYS_RESTFUL_API, false);
        boolean isDebug = systemConfig.getBoolean(SystemConfig.PARAM_SYS_IS_DEBUG_MODE, false);
        if (isRestfulApi) { // restful api

        } else { // json rpc
            jsonRpcService = new JsonRpcService();
            jsonRpcService.init(baseUrl);
            jsonRpcService.setDebug(isDebug);
        }
        isConnected = true;

//        String fileUrl;
//        if (systemConfig.getBoolean(SystemConfig.PARAM_SERVER_USING_RESERVED, false)) {
//            fileUrl = systemConfig.getString(SystemConfig.PARAM_FILE_SERVER_RESERVED_URL);
//        } else {
//            fileUrl = systemConfig.getString(SystemConfig.PARAM_FILE_SERVER_URL);
//        }

//        if (!TextUtil.isNullOrEmpty(fileUrl)) {
//            httpService = RestfulApiService.Factory.newInstance(mBus, fileUrl);
//        }
    }

    private Observable<DUMedia> uploadImage(final DUMedia duMedia) {
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

                    if (TextUtil.isNullOrEmpty(duMedia.getUrl())
                            || TextUtil.isNullOrEmpty(duMedia.getFileHash())) {
                        File file = new File(duMedia.getWenjianlj());
                        if (file.exists()) {
                            // 创建 RequestBody，用于封装 请求RequestBody
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                            // MultipartBody.Part is used to send also the actual file name
                            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                            // add another part within the multipart request
                            String descriptionString = "hello, this is description speaking";
                            RequestBody description =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

                            // 执行请求
                            Call<List<DUMediaResponse>> call = httpService.uploadImage(description, body);
                            Response<List<DUMediaResponse>> response = call.execute();
                            List<DUMediaResponse> duMediaResponseList = response.body();
                            if ((duMediaResponseList != null)
                                    && (duMediaResponseList.size() == 1)
                                    && !TextUtil.isNullOrEmpty(duMediaResponseList.get(0).getUrl())
                                    && !TextUtil.isNullOrEmpty(duMediaResponseList.get(0).getFileHash())) {
                                duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                                duMedia.setUrl(duMediaResponseList.get(0).getUrl());
                                duMedia.setFileHash(duMediaResponseList.get(0).getFileHash());
                            } else {
                                duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                            }
                        } else {
                            duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                        }
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

    private Observable<List<DUMedia>> uploadImageList(final List<DUMedia> duMediaList) {
        return Observable.create(new Observable.OnSubscribe<List<DUMedia>>() {
            @Override
            public void call(Subscriber<? super List<DUMedia>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duMediaList == null) {
                        throw new NullPointerException("duMedia is null");
                    }

                    List<DUMedia> uploadingDUMediaList = new ArrayList<DUMedia>();
                    MultipartBody.Part[] bodies = new MultipartBody.Part[6];
                    int count = 0;
                    for (DUMedia duMedia : duMediaList) {
                        duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                        if ((duMedia.getAccount() == null)
                                || (duMedia.getCh() == null)
                                || (duMedia.getCid() == null)
                                || (duMedia.getWenjianlj() == null)
                                || (duMedia.getWenjianmc() == null)) {
                            continue;
                        }

//                        if (!TextUtil.isNullOrEmpty(duMedia.getUrl())
//                                && !TextUtil.isNullOrEmpty(duMedia.getFileHash())) {
//                            continue;
//                        }

                        File file = new File(duMedia.getWenjianlj());
                        if (file.exists()) {
                            // create RequestBody instance from file
                            RequestBody requestFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

                            // MultipartBody.Part is used to send also the actual file name
                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                            bodies[count++] = body;
                            uploadingDUMediaList.add(duMedia);
                            if (count >= 6) {
                                break;
                            }
                        }
                    }

                    if ((count > 0) && (count == uploadingDUMediaList.size())) {
                        // add another part within the multipart request
                        String descriptionString = "hello, this is description speaking";
                        RequestBody description =
                                RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

                        // 执行请求
                        Call<List<DUMediaResponse>> call = httpService.uploadImages(description,
                                bodies[0], bodies[1], bodies[2], bodies[3], bodies[4], bodies[5]);
                        Response<List<DUMediaResponse>> response = call.execute();
                        List<DUMediaResponse> duMediaResponseList = response.body();
                        if (duMediaResponseList != null) {

                        } else {

                        }
                    }

                    subscriber.onNext(duMediaList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    private Observable<List<DUMedia>> uploadImageList2(final List<DUMedia> duMediaList) {
        return Observable.create(new Observable.OnSubscribe<List<DUMedia>>() {
            @Override
            public void call(Subscriber<? super List<DUMedia>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duMediaList == null) {
                        throw new NullPointerException("duMedia is null");
                    }

                    List<DUMedia> uploadingDUMediaList = new ArrayList<DUMedia>();
                    Map<String, RequestBody> params = new HashMap<>();
                    for (DUMedia duMedia : duMediaList) {
                        if (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC) {
                            continue;
                        }

                        if ((duMedia.getAccount() == null)
                                || (duMedia.getCh() == null)
                                || (duMedia.getCid() == null)
                                || (duMedia.getWenjianlj() == null)
                                || (duMedia.getWenjianmc() == null)) {
                            duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                            continue;
                        }

                        if (!TextUtil.isNullOrEmpty(duMedia.getUrl())
                                && !TextUtil.isNullOrEmpty(duMedia.getFileHash())) {
                            continue;
                        }

                        File file = new File(duMedia.getWenjianlj());
                        if (file.exists()) {
                            // create RequestBody instance from file
                            RequestBody requestBody =
                                    RequestBody.create(MediaType.parse("image/jpg"), file);
                            params.put("file\"; filename=\"" + file.getName(), requestBody);
                            uploadingDUMediaList.add(duMedia);
                        }
                    }

                    if ((params.size() > 0) && (params.size() == uploadingDUMediaList.size())) {
                        // 执行请求
                        Call<List<DUMediaResponse>> call = httpService.uploadImages(params);
                        Response<List<DUMediaResponse>> response = call.execute();
                        List<DUMediaResponse> duMediaResponseList = response.body();
                        if ((duMediaResponseList != null)
                                && (duMediaResponseList.size() == uploadingDUMediaList.size())) {
                            for (DUMedia duMedia : uploadingDUMediaList) {
                                for (DUMediaResponse duMediaResponse : duMediaResponseList) {
                                    String srcFileName = duMedia.getWenjianmc();
                                    String destFileName = duMediaResponse.getOriginFileName();
                                    if (TextUtil.isNullOrEmpty(srcFileName)
                                            || TextUtil.isNullOrEmpty(destFileName)
                                            || (!srcFileName.equals(destFileName))) {
                                        LogUtil.i(TAG, "not match");
                                        continue;
                                    }

                                    duMedia.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
                                    duMedia.setUrl(duMediaResponse.getUrl());
                                    duMedia.setFileHash(duMediaResponse.getFileHash());
                                }
                            }
                        }
                    }

                    subscriber.onNext(duMediaList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<List<DUBillPreview>> calculateCash(DURecord duRecord, int meterType) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.calculateCash(duRecord, meterType);
        }
    }

    public Observable<List<CallForPaymentTaskBean>> getCallForPayTaskList(String account, String searchText, Context mContext) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getCallForPayTaskList(account, searchText, mContext);
        }
    }

    public Observable<List<CuijiaoEntity>> getCallForPayWorkOrderList(String s_ch, Context mContext) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getCallForPayWorkOrderList(s_ch, mContext);
        }
    }

    public Observable<CuijiaoEntity> getCallForPayOrderDetail(String renwuid, String s_cid, Context mContext) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getCallForPayOrderDetail(renwuid, s_cid, mContext);
        }
    }

    public Observable<List<CallForPaymentArrearsFeesDetailBean>> getArrearsFeesDetail(String renwuid, String s_cid, Context mContext) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.getArrearsFeesDetail(renwuid, s_cid, mContext);
        }
    }

    public Observable<String> saveOrUploadData(CallForPaymentBackFillDataBean bean, boolean isSave, Context context) {
        connect();

        if (isRestfulApi) {
            return null;
        } else {
            return jsonRpcService.saveOrUploadData(bean, isSave, context);
        }
    }
}
