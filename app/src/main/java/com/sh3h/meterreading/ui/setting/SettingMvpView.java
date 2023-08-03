package com.sh3h.meterreading.ui.setting;


import com.sh3h.datautil.data.entity.DUUploadingFileResult;
import com.sh3h.meterreading.ui.base.MvpView;

public interface SettingMvpView extends MvpView {
    void initStyle(boolean isGrid);
    void initQuality(boolean isNormal);
    void initVersion(String version);
    void initUpdateVersion(boolean flag);
    void initSyncData(boolean flag);
    void initSingleUpload(boolean flag);
    void initUploadAfterCeben(boolean flag);
    void initUploadAll(boolean flag);
    void initDownloadAll(boolean flag);
    void initLeftOrRightOperation(boolean isLeft);
    void showMessage(String message);
    void showMessage(int message);
    void exitSystem();
    void onUploadFile(DUUploadingFileResult duUploadingFileResult);
}
