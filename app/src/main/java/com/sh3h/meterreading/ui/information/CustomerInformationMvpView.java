package com.sh3h.meterreading.ui.information;


import com.sh3h.meterreading.ui.base.MvpView;

public interface CustomerInformationMvpView extends MvpView {
    void onError(String message);
    void onCheckForUpdatingCard(boolean canUpdate);
}
