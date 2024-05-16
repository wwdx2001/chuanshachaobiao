package com.sh3h.meterreading.ui.billservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.DUBillServiceInfo;
import com.example.dataprovider3.entity.DUBillServiceInfoResultBean;
import com.example.dataprovider3.entity.DUBillServiceRwBH;
import com.example.dataprovider3.entity.DUBillServiceUpload;
import com.google.gson.Gson;
import com.sh3h.dataprovider.DBManager;
import com.sh3h.dataprovider.greendaoEntity.DuoMeiTXX;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUMediaInfo;
import com.sh3h.datautil.data.entity.DUMediaResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.meterreading.util.ApplicationsUtil;
import com.sh3h.meterreading.util.SystemEquipmentUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.subsciber.BaseSubscriber;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BillServicePresenter extends ParentPresenter<BillServiceMvpView> {

    private static final String TAG = "BillServicePresenter";
    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;
    private Gson gson;
    private boolean isUploadMoreMediaFinished;
    private Subscription subscription;
    String baseUrl;
    private UserSession userSession;

    @Inject
    public BillServicePresenter(DataManager dataManager,
                                ConfigHelper configHelper,
                                PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        this.mPreferencesHelper = preferencesHelper;
        userSession = this.mPreferencesHelper.getUserSession();
        DBManager.getInstance().init(mConfigHelper.getDBFilePath().getPath(), ActivityUtils.getTopActivity());
    }


    public void getBillServiceBH(final boolean isRefresh) {
        initHttpConfig();
        Log.e("BillServiceActivity", "isRefresh=" + isRefresh);
        if (isRefresh) {
            getEasyHttpBillServiceBH(isRefresh);
        } else {
            LitePal.findAllAsync(DUBillServiceInfoResultBean.class)
                    .listen(new FindMultiCallback<DUBillServiceInfoResultBean>() {
                        @Override
                        public void onFinish(List<DUBillServiceInfoResultBean> list) {
                            Log.e("BillServiceActivity", "list=" + list.size());
                            if (list == null || list.size() == 0) {
                                getEasyHttpBillServiceBH(isRefresh);
                            } else {//本地已经有数据
//                                list.get(0).delete();
//                                list.remove(0);
                                if (getMvpView() != null)
                                    getMvpView().onBillServiceListNext(list);
                            }
                        }
                    });
        }
    }

    private void initHttpConfig() {
        SystemConfig systemConfig = mConfigHelper.getSystemConfig();
        String baseUrl;
        if (systemConfig.getBoolean(SystemConfig.PARAM_SERVER_USING_RESERVED, false)) {
            baseUrl = systemConfig.getString(SystemConfig.PARAM_SERVER_RESERVED_BASE_URI);
        } else {
            baseUrl = systemConfig.getString(SystemConfig.PARAM_SERVER_BASE_URI);
        }
//        baseUrl = "http://222.72.139.66:8033/test/";
        LogUtils.e("http", "baseurl==============" + baseUrl);
        EasyHttp.getInstance().setBaseUrl(baseUrl);
    }

    private void getEasyHttpBillServiceBH(final boolean isRefresh) {
        LogUtils.e("http", "baseUrl==" + EasyHttp.getBaseUrl());
        String json = "{\"id\": \"15\",\n"
                + "\"method\": \"getGAOZHIRWByChaoBiaoY\",\n"
                + "\"params\": \n"
                + "{ \n"
                + "\t\"Account\" : " + "\"" + userSession.getAccount() + "\"" + " \n"
                + "}\n"
                + "}";
        EasyHttp.post("Business.ashx")
                .upJson(json)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (isRefresh && getMvpView() != null) {
                            getMvpView().showProgressDialog("正在同步数据");
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        if (getMvpView() != null) {
                            getMvpView().hideProgressDialog();
                            getMvpView().onError(e);
                        }
                        ApplicationsUtil.showMessage(MainApplication.getInstance(), e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Gson gson = new Gson();
                        DUBillServiceRwBH billServiceRwBH = gson.fromJson(s, DUBillServiceRwBH.class);
                        if (billServiceRwBH.getResult() != null) {
                            if (billServiceRwBH.getResult().getS_RENWUS() != null) {
                                getBillServiceList(billServiceRwBH.getResult().getS_RENWUS());
                            } else {
                                if (getMvpView() != null) {
                                    getMvpView().hideProgressDialog();
                                    getMvpView().onFile("请求失败");
                                }
                            }
                        } else {
                            if (getMvpView() != null) {
                                getMvpView().hideProgressDialog();
                                getMvpView().onFile("请求失败");
                            }
                        }
                    }
                });
    }

    private void getBillServiceList(String bh) {
        String[] split = bh.split(",");
        List<io.reactivex.Observable<String>> observableList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String json = "{\"id\": \"2\",\n"
                    + "\"method\": \"PDA_DownLoadGAOZHIRW\",\n"
                    + "\"params\": \n"
                    + "{ \n"
                    + "\t\"renwubh\" : " + "\"" + split[i] + "\"" + " \n"
                    + "}\n"
                    + "}";
            io.reactivex.Observable<String> observable =
                    EasyHttp.post("Business.ashx")
                            .upJson(json)
                            .execute(new CallClazzProxy<ApiResult<String>, String>(String.class) {
                            });
            observableList.add(observable);
        }
        io.reactivex.Observable.zip(observableList, new Function<Object[], Object>() {
            @Override
            public Object[] apply(Object[] objects) throws Exception {
                if (getMvpView() != null)
                    getMvpView().hideProgressDialog();
                Gson gson = new Gson();
                final List<DUBillServiceInfoResultBean> newList = new ArrayList<>();
                for (int i = 0; i < objects.length; i++) {
                    String s = (String) objects[i];
                    DUBillServiceInfo billServiceInfo = gson.fromJson(s, DUBillServiceInfo.class);
                    if (billServiceInfo.getResult() != null && billServiceInfo.getResult().size() > 0) {
                        final List<DUBillServiceInfoResultBean> list =
                                DUBillServiceInfoResultBean.toResultList(billServiceInfo.getResult());
                        newList.addAll(list);
                    }
                }

                LitePal.findAllAsync(DUBillServiceInfoResultBean.class)
                        .listen(new FindMultiCallback<DUBillServiceInfoResultBean>() {
                            @Override
                            public void onFinish(final List<DUBillServiceInfoResultBean> list) {
                                newList.removeAll(list);
                                list.addAll(newList);
                                LitePal.saveAllAsync(newList).listen(new SaveCallback() {
                                    @Override
                                    public void onFinish(boolean b) {
                                        if (b && getMvpView() != null) {
                                            getMvpView().onBillServiceListNext(list);
                                        }
                                    }
                                });
                            }
                        });
                return objects;
            }
        }).subscribe(new BaseSubscriber<Object>() {
            @Override
            public void onNext(Object o) {
//                super.onNext(o);
//                Gson gson = new Gson();
//                String s = gson.toJson(o);
//                Type type = new TypeToken<DUBillServiceInfo>() {
//                }.getType();
//                List<DUBillServiceInfo> list = new Gson()
//                        .fromJson(s, type);

            }

            @Override
            public void onError(ApiException e) {
                if (getMvpView() != null)
                    getMvpView().onError(e);
            }
        });


//        EasyHttp.post("test/Business.ashx")
//                .upJson(json)
//                .execute(new SimpleCallBack<String>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        getMvpView().hideProgressDialog();
//                        ApplicationsUtil.showMessage(MainApplication.getInstance().getContext(), e.getMessage());
//                        getMvpView().onError(e);
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        getMvpView().hideProgressDialog();
//                        Gson gson = new Gson();
//                        DUBillServiceInfo billServiceInfo = gson.fromJson(s, DUBillServiceInfo.class);
//                        if (billServiceInfo.getResult() != null && billServiceInfo.getResult().size() > 0) {
//                            final List<DUBillServiceInfoResultBean> newList =
//                                    DUBillServiceInfoResultBean.toResultList(billServiceInfo.getResult());
//                            LitePal.findAllAsync(DUBillServiceInfoResultBean.class)
//                                    .listen(new FindMultiCallback<DUBillServiceInfoResultBean>() {
//                                        @Override
//                                        public void onFinish(final List<DUBillServiceInfoResultBean> list) {
//                                            newList.removeAll(list);
//                                            list.addAll(newList);
//                                            LitePal.saveAllAsync(newList).listen(new SaveCallback() {
//                                                @Override
//                                                public void onFinish(boolean b) {
//                                                    if (b) {
//                                                        getMvpView().onBillServiceListNext(list);
//                                                    }
//                                                }
//                                            });
//                                        }
//                                    });
//                        } else {
//                            getMvpView().onFile("请求失败");
//                        }
//                    }
//                });
    }

    public void submitData2(List<DUBillServiceInfoResultBean> mListData) {
        List<DUBillServiceInfoResultBean> waitCommitListData = new ArrayList<>();// 实际有数据要上传的
        int size = mListData.size();
        if (size == 0) {
            ApplicationsUtil.showMessage(MainApplication.getInstance(), "当前没有可提交的账单数据");
            return;
        }
        if (userSession == null) {
            userSession = mPreferencesHelper.getUserSession();
        }
        for (int i = 0; i < size; i++) {
            DUBillServiceInfoResultBean resultBean = mListData.get(i);
            List<DuoMeiTXX> duoMeiTXXList = DBManager.getInstance().getNotUploadedZDSDDuoMeiTXXList(
                    userSession.getAccount(),
                    resultBean.getID(), resultBean.getS_ZHUMA());
            Log.e("账单送达", "duoMeiTXXList=" + duoMeiTXXList);
            if (duoMeiTXXList != null && duoMeiTXXList.size() > 0
                    && !"2".equals(resultBean.getI_RENWUZT())) { // 状态不是已上传且有照片且文件类型是账单送达
                Log.e("账单送达", "册本号：" + resultBean.getS_ZHUMA() + duoMeiTXXList.get(0).getS_WENJIANLJ());
                waitCommitListData.add(resultBean);
            }
        }
        if (waitCommitListData.size() == 0) { // 没有图片，不能提交
            ApplicationsUtil.showMessage(MainApplication.getInstance(), "当前没有已拍照账单册本，请先拍照后再提交");
            return;
        }
        if (getMvpView() != null) {
            getMvpView().showProgressDialog("正在提交数据...");
        }
        // 开始提交
        recursionSubmit(waitCommitListData, 0);
    }

    /**
     * 递归提交
     *
     * @param mListData
     */
    private void recursionSubmit(List<DUBillServiceInfoResultBean> mListData, int currentPosition) {
        uploadImage(mListData, currentPosition);
    }

    public void submitData(List<DUBillServiceInfoResultBean> mListData) {
        if (mListData.size() > 0) {
            if (userSession == null) {
                userSession = mPreferencesHelper.getUserSession();
            }
            gson = new Gson();
            if (getMvpView() != null) {
                getMvpView().showProgressDialog("正在提交数据");
            }
            boolean isSubmit = false;
            int size = mListData.size();
            for (int i = 0; i < size; i++) {
                DUBillServiceInfoResultBean resultBean = mListData.get(i);
                List<DuoMeiTXX> duoMeiTXXList = DBManager.getInstance().getNotUploadedDuoMeiTXXList(userSession.getAccount(),
                        resultBean.getI_RENWUBH(), resultBean.getS_ZHUMA());
                Log.e("BillServiceActivity", "duoMeiTXXList=" + duoMeiTXXList);
                if (duoMeiTXXList != null && duoMeiTXXList.size() > 0
                ) {
                    isSubmit = true;
//                    uploadImage(resultBean, i + 1, size);
                }
            }

            if (!isSubmit && getMvpView() != null) {
                getMvpView().hideProgressDialog();
                ApplicationsUtil.showMessage(MainApplication.getInstance(), "当前没有已拍照账单册本，请先拍照后再提交");
            }
        } else {
            ApplicationsUtil.showMessage(MainApplication.getInstance(), "当前没有可提交的账单数据");
        }

    }

    public void uploadImage(final List<DUBillServiceInfoResultBean> mListData, final int currentPosition) {
        Log.e("账单送达", "currentPosition=" + currentPosition + "mListData.size=" + mListData.size());
        if (currentPosition >= mListData.size()) {
            if (getMvpView() != null) {
                getMvpView().hideProgressDialog();
            }
            return;
        }
        if (userSession == null) {
            userSession = mPreferencesHelper.getUserSession();
        }
        DUBillServiceInfoResultBean resultBean = mListData.get(currentPosition);
        final boolean[] isSuccess = {false};
        isUploadMoreMediaFinished = true;
        DUMedia duMedia = new DUMedia(
                userSession.getAccount(),
                resultBean.getI_RENWUBH(),
                resultBean.getS_ZHUMA(),
//                "GZD"
                resultBean.getI_RENWUBH() + "");

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.SELECT,
                DUMediaInfo.MeterReadingType.NORMAL,
                duMedia);
        mDataManager.setUploadingImageRepeatedly(false);
        subscription = mDataManager.getMediaList(duMediaInfo)
                .filter(new Func1<List<DUMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<DUMedia> duMediaList) {
                        isUploadMoreMediaFinished = duMediaList.size() > 0;
                        LogUtil.i(TAG, "---duMediaList.size()---" + duMediaList.size());
                        LogUtil.i(TAG, "---isUploadMoreMediaFinished---" + currentPosition + isUploadMoreMediaFinished);
                        return isUploadMoreMediaFinished;
                    }
                })
                .concatMap(new Func1<List<DUMedia>, Observable<DUMediaResult>>() {
                    @Override
                    public Observable<DUMediaResult> call(List<DUMedia> duMediaList) {
                        return mDataManager.uploadMoreMedias2(duMediaList, false);
                    }
                })
                .doOnNext(new Action1<DUMediaResult>() {
                    @Override
                    public void call(DUMediaResult duMediaResult) {
                        List<DUMedia> duMediaList = duMediaResult.getDuMediaList();
                        if ((duMediaList != null) && (duMediaList.size() == 1)) {
                            DUMedia duMedia = duMediaList.get(0);
                            isSuccess[0] = (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_YISHANGC);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUMediaResult>() {
                    @Override
                    public void onCompleted() {
                        Log.e("账单送达", "图片上传完成，上传数据" + isSuccess[0]);
                        if (isSuccess[0]) {
                            uploadData(mListData, currentPosition);
                        } else {
                            if (getMvpView() != null) {
                                ToastUtils.showShort("图片上传失败");
                                getMvpView().hideProgressDialog();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getMvpView() != null)
                            getMvpView().hideProgressDialog();
                        LogUtil.i(TAG, "---uploadMoreMedias onError---" + e.getMessage());
                    }

                    @Override
                    public void onNext(DUMediaResult duMediaResult) {
                    }
                });
    }

    public void uploadData(final List<DUBillServiceInfoResultBean> mListData, final int currentPosition) {
        if (userSession == null) {
            userSession = mPreferencesHelper.getUserSession();
        }
        final DUBillServiceInfoResultBean resultBean = mListData.get(currentPosition);
        String json = "{\"id\": \"1\",\n" +
                "\"method\": \"PDA_UPLOADGAOZHIRW\",\n" +
                "\"params\": \n" +
                "{ \n" +
                "\t\"renwubh\" : " + "\"" + resultBean.getI_RENWUBH() + "\"" + " \n " +
                "}\n" +
                "}";
        final int[] currentPositions = new int[]{currentPosition};
        EasyHttp.post("Business.ashx")
                .upJson(json)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ApplicationsUtil.showMessage(MainApplication.getInstance(), e.getMessage());
                        List<DuoMeiTXX> duoMeiTXXList = DBManager.getInstance().getNotUploadedDuoMeiTXXList(userSession.getAccount(),
                                resultBean.getI_RENWUBH(), resultBean.getS_ZHUMA());
                        for (int i = 0; i < duoMeiTXXList.size(); i++) {
                            DuoMeiTXX duoMeiTXX = duoMeiTXXList.get(i);
                            duoMeiTXX.setI_SHANGCHUANBZ(DUMedia.SHANGCHUANBZ_WEISHANGC);
                            DBManager.getInstance().updateDuoMeiTXX(duoMeiTXX);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        Log.e("账单送达", "数据上传完成，继续上传图片");
                        uploadImage(mListData, ++currentPositions[0]);
                    }

                    @Override
                    public void onSuccess(String s) {
                        Gson gson = new Gson();
                        DUBillServiceUpload billServiceInfo = gson.fromJson(s, DUBillServiceUpload.class);
                        if (billServiceInfo != null && billServiceInfo.isResult()) {
                            resultBean.setI_RENWUZT(2);
                            resultBean.updateAll("myId = ?", resultBean.getID() + "");
                            if (getMvpView() != null)
                                getMvpView().notifyListData(resultBean);
//                            resultBean.delete();
                        } else {
                            List<DuoMeiTXX> duoMeiTXXList = DBManager.getInstance().getNotUploadedDuoMeiTXXList(userSession.getAccount(),
                                    resultBean.getI_RENWUBH(), resultBean.getS_ZHUMA());
                            for (int i = 0; i < duoMeiTXXList.size(); i++) {
                                DuoMeiTXX duoMeiTXX = duoMeiTXXList.get(i);
                                duoMeiTXX.setI_SHANGCHUANBZ(DUMedia.SHANGCHUANBZ_WEISHANGC);
                                DBManager.getInstance().updateDuoMeiTXX(duoMeiTXX);
                            }
                        }
                    }
                });
    }

    /**
     * @param taskId
     * @param volume
     * @param customerId
     */
    public void loadImageInfo(int taskId, final String volume, String customerId) {
        if ((taskId <= 0)
                || TextUtil.isNullOrEmpty(volume)) {
            if (getMvpView() != null)
                getMvpView().onFile("parameter is error");
            LogUtil.i(TAG, "---loadImageInfo---parameter is error");
            return;
        }

//        UserSession userSession = mPreferencesHelper.getUserSession();
        DUMedia duMedia = new DUMedia(
                userSession.getAccount(),
                taskId,
                volume,
                customerId);

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.SELECT,
                DUMediaInfo.MeterReadingType.NORMAL,
                duMedia);
        mSubscription.add(mDataManager.getMediaList(duMediaInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUMedia>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---getMediaList onError---" + e.getMessage());
                        if (getMvpView() != null)
                            getMvpView().onFile(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUMedia> duMediaList) {
                        List<String> imgPathList = new ArrayList<>();
                        List<DUMedia> duoMeiTXXList = new ArrayList<>();
                        if (duMediaList != null && duMediaList.size() > 0) {
                            File dir = new File(mConfigHelper.getImageFolderPath(), volume);
                            if (!dir.exists()) {
                                dir.mkdir();
                            }

                            for (DUMedia duoMeiTXX : duMediaList) {
                                if ((duoMeiTXX == null) || (duoMeiTXX.getWenjianmc() == null)) {
                                    continue;
                                }

                                File file = new File(dir, duoMeiTXX.getWenjianmc());
                                if (!file.exists()) {
                                    continue;
                                }

                                imgPathList.add(file.getPath());//将图片路径保存在列表中
                                duoMeiTXXList.add(duoMeiTXX);
                            }
                        }
                        if (getMvpView() != null) {
                            getMvpView().onLoadImgPathList(imgPathList);
                            getMvpView().onLoadDuoMeiTXXList(duoMeiTXXList);
                        }
                    }
                }));
    }

    /**
     * @param duoMeiTXX
     */
    public void saveNewImage(final DUMedia duoMeiTXX) {
        if (duoMeiTXX == null && getMvpView() != null) {
            getMvpView().onFile("---saveNewImage: duoMeiTXX is null---");
            return;
        }

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.INSERT,
                DUMediaInfo.MeterReadingType.NORMAL,
                duoMeiTXX
        );
        mSubscription.add(mDataManager.saveNewImage(duMediaInfo)
                .concatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        return compressImageAndAddStamp(duoMeiTXX);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveNewImage onError---" + e.getMessage());
                        if (getMvpView() != null)
                            getMvpView().onFile(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            if (getMvpView() != null)
                                getMvpView().onSaveNewImage(duoMeiTXX);
                        } else {
                            LogUtil.i(TAG, "---saveNewImage onNext---false");
                            if (getMvpView() != null)
                                getMvpView().onFile("saveNewImage is error");
                        }
                    }
                }));
    }

    /**
     * @param imgPathList
     * @param context
     */
    public void loadImageViews(final List<String> imgPathList, final Context context) {
        Observable.create(new Observable.OnSubscribe<List<ImageView>>() {
            @Override
            public void call(Subscriber<? super List<ImageView>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if ((imgPathList == null) || (context == null)) {
                    LogUtil.i(TAG, "---loadImageViews---parameter is null");
                    throw new NullPointerException("parameter is null");
                }

                try {
                    List<ImageView> imageViewList = new ArrayList<>();
                    for (String path : imgPathList) {
                        Bitmap bitmap = SystemEquipmentUtil.decodeBitmap(path);
                        ImageView imageView = new ImageView(context);
                        imageView.setImageBitmap(bitmap);
                        imageViewList.add(imageView);
                    }

                    subscriber.onNext(imageViewList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                    LogUtil.i(TAG, "---loadImageViews---" + e.getMessage());
                } finally {
                    subscriber.onCompleted();
                }
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ImageView>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadImageViews onError---" + e.getMessage());
                        if (getMvpView() != null)
                            getMvpView().onFile(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ImageView> imageViews) {
                        if (getMvpView() != null)
                            getMvpView().onLoadImageViews(imageViews);
                    }
                });
    }

    /**
     * @param index
     * @param name
     * @param volume
     */
    public void deleteImage(final int index, final String name, final String volume) {
        if (TextUtil.isNullOrEmpty(name) || TextUtil.isNullOrEmpty(volume)) {
            if (getMvpView() != null)
                getMvpView().onFile("parameter is null");
            return;
        } else {
            DUMedia duMedia = new DUMedia();
            duMedia.setWenjianmc(name);

            DUMediaInfo duMediaInfo = new DUMediaInfo(
                    DUMediaInfo.OperationType.DELETE,
                    DUMediaInfo.MeterReadingType.NORMAL,
                    duMedia);
            mSubscription.add(mDataManager.deleteImage(duMediaInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.i(TAG, "---deleteImage onError---" + e.getMessage());
                            if (getMvpView() != null)
                                getMvpView().onFile(e.getMessage());
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean) {
                                File dir = new File(mConfigHelper.getImageFolderPath(), volume);
                                File file = new File(dir, name);
                                if (file.exists()) {
                                    file.delete();
                                }
                                if (getMvpView() != null)
                                    getMvpView().onDeleteImage(index);
                            } else {
                                if (getMvpView() != null)
                                    getMvpView().onFile("deleteImage is error");
                            }
                        }
                    }));
        }
    }

    /**
     * @param duoMeiTXX
     * @return
     */
    private Observable<Boolean> compressImageAndAddStamp(final DUMedia duoMeiTXX) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if ((duoMeiTXX == null)
                        || TextUtil.isNullOrEmpty(duoMeiTXX.getWenjianlj())
                        || TextUtil.isNullOrEmpty(duoMeiTXX.getWenjianmc())) {
                    throw new NullPointerException("duMediaInfo contains null parameter");
                }

                try {
                    File file = new File(duoMeiTXX.getWenjianlj());
                    if (!file.exists()) {
                        subscriber.onError(new Throwable("file isn't existing"));
                        return;
                    }

                    //缩放的比例，缩放是很难按准备的比例进行缩放的，其值表明缩放的倍数，SDK中建议其值是2的指数
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap fBitmap = BitmapFactory.decodeFile(file.getPath(), options);
                    if (fBitmap == null) {
                        subscriber.onError(new Throwable("failure to decode the picture"));
                        return;
                    }

                    // rotate the bitmap
                    int degree = getBitmapDegree(file.getPath());
                    if (degree != 0) {
                        fBitmap = rotateBitmapByDegree(fBitmap, degree);
                    }

                    // add the stamp
                    fBitmap = addWatermark(fBitmap);

                    // save image
                    if (!saveImage(fBitmap, file)) {
                        subscriber.onError(new Throwable("failure to save the picture"));
                        return;
                    }

                    subscriber.onNext(true);
                } catch (Exception e) {
                    LogUtil.e("FileNotFoundException", e.getMessage(), e);
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * save the image
     *
     * @param bitmap
     */
    private boolean saveImage(Bitmap bitmap, File file) {
        if ((bitmap == null) || (file == null)) {
            return false;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            int quality = mConfigHelper.isPhotoQualityNormal() ? 200 : 400;
            while (baos.toByteArray().length / 1024 > quality) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options /= 2;//每次减少一半
            }

            FileOutputStream fos = new FileOutputStream(file);
            baos.writeTo(fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 读取图片旋转的角度
     *
     * @param path
     * @return
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */

    private Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }

        if (returnBm == null) {
            returnBm = bm;
        }

        if (bm != returnBm) {
            bm.recycle();
        }

        return returnBm;
    }

    /**
     * 添加水印
     *
     * @param bitmap
     * @return
     */

    private Bitmap addWatermark(Bitmap bitmap) {
        Bitmap icon = null;
        try {
            int width = bitmap.getWidth();
            int hight = bitmap.getHeight();
            icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(icon);
            Paint photoPaint = new Paint();
            photoPaint.setDither(true);
            photoPaint.setFilterBitmap(true);
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, dst, photoPaint);
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
            textPaint.setTextSize(15.0f);
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            textPaint.setColor(Color.RED);
            textPaint.setAlpha(150);
            String t = TextUtil.format(System.currentTimeMillis(), TextUtil.FORMAT_FULL_DATETIME);
            //Date date = new Date(System.currentTimeMillis());
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //canvas.drawText(sdf.format(date), (float) (width * 0.19), (float) (hight * 0.97), textPaint);
            // canvas.drawText(t, (float) (width * 0.19), (float) (hight * 0.04), textPaint);
            canvas.drawText(t, (float) (width * 0.19), (float) (hight * 0.03), textPaint);
//            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.save();
            canvas.restore();


        } catch (Exception e) {
            e.printStackTrace();
            icon = bitmap;
        }

        return icon;
    }

    public static String getImageStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        return Base64.encodeToString(data, Base64.DEFAULT);//返回Base64编码过的字节数组字符串
    }

}
