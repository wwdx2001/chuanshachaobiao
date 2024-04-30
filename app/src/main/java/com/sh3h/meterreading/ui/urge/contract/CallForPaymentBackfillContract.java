package com.sh3h.meterreading.ui.urge.contract;

import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.List;

public interface CallForPaymentBackfillContract {

    public interface Presenter {
        List<XJXXWordBean> getQFYYWordData(String type, String secondLevel);
    }

    public interface View extends MvpView, BaseView {
    }

}
