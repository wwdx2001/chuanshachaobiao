package com.sh3h.meterreading.ui.InspectionInput.lr;

import com.sh3h.meterreading.ui.base.MvpView;

public interface LRDuoMeitiMvpView extends MvpView {
    void onError(String message);

    void onSaveNewImage(Boolean aBoolean);
}
