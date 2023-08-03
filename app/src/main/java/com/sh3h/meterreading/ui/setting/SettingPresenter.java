package com.sh3h.meterreading.ui.setting;


import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUUploadingFile;
import com.sh3h.datautil.data.entity.DUUploadingFileResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.config.UserConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.meterreading.util.DeviceUtil;
import com.sh3h.meterreading.util.SystemUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingPresenter extends ParentPresenter<SettingMvpView> {
    private static final String TAG = "SettingPresenter";

    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public SettingPresenter(DataManager dataManager,
                            ConfigHelper configHelper,
                            PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public void init(Context context) {
        LogUtil.i(TAG, "---init---");
        getMvpView().initStyle(mConfigHelper.isGridStyle());
        getMvpView().initQuality(mConfigHelper.isPhotoQualityNormal());
        getMvpView().initVersion(SystemUtil.getVersionName(context));
        getMvpView().initUpdateVersion(mConfigHelper.isUpdateVersion());
        getMvpView().initSyncData(mConfigHelper.isSyncData());
        getMvpView().initSingleUpload(mConfigHelper.isSingleUpload());
        getMvpView().initUploadAfterCeben(mConfigHelper.isUploadAfterCeben());
        getMvpView().initUploadAll(mConfigHelper.isUploadAll());
        getMvpView().initDownloadAll(mConfigHelper.isDownloadAll());
        getMvpView().initLeftOrRightOperation(mConfigHelper.isLeftOrRightOperation());
    }

    public String getBaseUri() {
        return mConfigHelper.getBaseUri();
    }

    public String getReservedBaseUri() {
        return mConfigHelper.getReservedBaseUri();
    }

    public String getFileUrl() {
        return mConfigHelper.getFileUrl();
    }

    public String getReservedFileUrl() {
        return mConfigHelper.getFileReservedUrl();
    }

    //获得地图服务URL
    public String getMapUrl() {
        return mConfigHelper.getMapServerUrl();
    }

    public void saveStyle(boolean isGrid) {
        LogUtil.i(TAG, String.format("---saveStyle---isGrid: %s", isGrid ? "true" : "false"));
        if (mConfigHelper.isGridStyle() == isGrid) {
            LogUtil.i(TAG, String.format("---saveStyle---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveGridStyle(isGrid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveStyle onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveStyle onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveStyle onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveQuality(boolean isNormal) {
        LogUtil.i(TAG, String.format("---saveQuality---isNormal: %s", isNormal ? "true" : "false"));
        if (mConfigHelper.isPhotoQualityNormal() == isNormal) {
            LogUtil.i(TAG, String.format("---saveQuality---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.savePhotoQuality(isNormal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveQuality onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveQuality onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveQuality onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void logout() {
        if (mPreferencesHelper.clearUserSession()) {
            getMvpView().exitSystem();
        } else {
            getMvpView().showMessage(R.string.text_log_out_failure);
        }
    }

    public void clearHistory() {
        //TODO
        // modify in future
        if (mPreferencesHelper.setRecovery(true)) {
            getMvpView().exitSystem();
        } else {
            getMvpView().showMessage(R.string.text_clearing_history_failure);
        }
    }

    public void restoreFactory() {
        if (mPreferencesHelper.setRecovery(true)) {
            getMvpView().exitSystem();
        } else {
            getMvpView().showMessage(R.string.text_restoring_failure);
        }
    }

    public void saveUpdateVersion(boolean flag) {
        LogUtil.i(TAG, String.format("---saveUpdateVersion---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isUpdateVersion() == flag) {
            LogUtil.i(TAG, String.format("---saveUpdateVersion---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveUpdateVersion(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveUpdateVersion onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveUpdateVersion onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveUpdateVersion onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveSyncData(boolean flag) {
        LogUtil.i(TAG, String.format("---saveSyncData---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isSyncData() == flag) {
            LogUtil.i(TAG, String.format("---saveSyncData---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveSyncData(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveSyncData onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveSyncData onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveSyncData onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }


    public void saveSingleUpload(boolean flag) {
        LogUtil.i(TAG, String.format("---saveSingleUpload---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isSingleUpload() == flag) {
            LogUtil.i(TAG, String.format("---saveSingleUpload---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveSingleUpload(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveSingleUpload onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveSingleUpload onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveSingleUpload onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveAfterCeBenUpload(boolean flag) {
        LogUtil.i(TAG, String.format("---saveAfterCeBenUpload---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isUploadAfterCeben() == flag) {
            LogUtil.i(TAG, String.format("---saveAfterCeBenUpload---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveUploadAfterCeben(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveAfterCeBenUpload onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveAfterCeBenUpload onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveAfterCeBenUpload onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveUploadAll(boolean flag) {
        LogUtil.i(TAG, String.format("---saveUploadAll---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isUploadAll() == flag) {
            LogUtil.i(TAG, String.format("---saveUploadAll---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveUploadAll(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveUploadAll onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveUploadAll onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveUploadAll onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveDownloadAll(boolean flag) {
        LogUtil.i(TAG, String.format("---saveDownloadAll---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isDownloadAll() == flag) {
            LogUtil.i(TAG, String.format("---saveDownloadAll---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveDownloadAll(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveDownloadAll onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveDownloadAll onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveDownloadAll onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveNetWorkSetting(String baseUri,
                                   String reservedBaseUri,
                                   String fileUrl,
                                   String reservedFileUrl,
                                   boolean isReservedAddress,
                                   String mapUrl) {
        LogUtil.i(TAG, String.format("---saveNetWork---baseuri: %s--reservedBaseUri:%s",
                baseUri, reservedBaseUri));
        if ((TextUtil.isNullOrEmpty(baseUri)
                || TextUtil.isNullOrEmpty(reservedBaseUri))
                || TextUtil.isNullOrEmpty(mapUrl)) {
            getMvpView().showMessage(R.string.text_saving_failure);
            return;
        }

//        if (mConfigHelper.getBaseUri().equals(baseUri)
//                && mConfigHelper.getReservedBaseUri().equals(reservedBaseUri)
//                && (mConfigHelper.isReservedAddress() == isReservedAddress)
//                && mConfigHelper.getMapServerUrl().equals(mapUrl)) {
//            LogUtil.i(TAG, String.format("---saveNetWork---repeat"));
//            return;
//        }

        mSubscription.add(mConfigHelper.saveNetWorkSetting(baseUri, reservedBaseUri, fileUrl,
                reservedFileUrl, isReservedAddress, mapUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveNetWork onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveNetWork onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveNetWork onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveLeftOrRightOperation(boolean flag) {
        LogUtil.i(TAG, String.format("---saveLeftOrRightOperation---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isLeftOrRightOperation() == flag) {
            LogUtil.i(TAG, String.format("---saveLeftOrRightOperation---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveLeftOrRightOperation(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveLeftOrRightOperation onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveLeftOrRightOperation onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveLeftOrRightOperation onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public String getReadingDate(boolean isStart) {
        return mConfigHelper.getReadingDate(isStart);
    }

    public void uploadFile() {
        String account = mPreferencesHelper.getUserSession().getAccount();
        String path = mConfigHelper.getDBFilePath().getPath();
        DUUploadingFile duUploadingFile = new DUUploadingFile(
                DUUploadingFile.FileType.ZIP,
                account,
                path);
        mSubscription.add(mDataManager.uploadFile(duUploadingFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUUploadingFileResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---uploadFile onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---uploadFile onError---" + e.getMessage());
                        getMvpView().onUploadFile(new DUUploadingFileResult(false));
                    }

                    @Override
                    public void onNext(DUUploadingFileResult duUploadingFileResult) {
                        LogUtil.i(TAG, "---uploadFile onNext---");
                        getMvpView().onUploadFile(duUploadingFileResult);
                    }
                }));
    }
}
