package com.sh3h.meterreading.ui.urge.presenter;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentBackfillContract;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.List;

import javax.inject.Inject;

public class CallForPaymentBackfillPresenterImpl extends ParentPresenter<CallForPaymentBackfillContract.View>
        implements CallForPaymentBackfillContract.Presenter {
    @Inject
    public CallForPaymentBackfillPresenterImpl(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public List<XJXXWordBean> getQFYYWordData(String type, String secondLevel) {
        return mDataManager.getQFYYWordData(type, secondLevel);
    }
}
