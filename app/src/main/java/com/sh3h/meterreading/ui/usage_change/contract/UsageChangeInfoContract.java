package com.sh3h.meterreading.ui.usage_change.contract;

import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.usage_change.listener.UsageChangePriceDataListener;

public interface UsageChangeInfoContract {

    interface Model {
        void getPriceChagnesData(String jh, UsageChangePriceDataListener listener);
    }

    interface Prensenter {
        void getPriceChagnesData(String jh);
    }

    interface View extends MvpView, BaseView {
    }
}
