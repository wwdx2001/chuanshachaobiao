package com.sh3h.meterreading.ui.main;

import com.sh3h.meterreading.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    void startVersionService();
    void startSyncService();
    void startKeepAliveService();
    void onExit(String message);
    void initSubTitle(String subTitle);
}
