package com.sh3h.meterreading.ui.usage_change.presenter;

import com.example.dataprovider3.entity.UsageChangeInfoPriceEntity;
import com.sh3h.meterreading.ui.base.BasePresenter;
import com.sh3h.meterreading.ui.usage_change.contract.UsageChangeInfoContract;
import com.sh3h.meterreading.ui.usage_change.listener.UsageChangePriceDataListener;
import com.sh3h.meterreading.ui.usage_change.model.UsageChangeInfoModelImpl;

import java.util.List;

public class UsageChangeInfoPresenterImpl extends BasePresenter<UsageChangeInfoContract.View> implements
        UsageChangeInfoContract.Prensenter, UsageChangePriceDataListener {

    private UsageChangeInfoContract.View view;
    private UsageChangeInfoContract.Model model;

    public UsageChangeInfoPresenterImpl(UsageChangeInfoContract.View view) {
        this.view = view;
        this.model = new UsageChangeInfoModelImpl();
    }

    @Override
    public void getPriceChagnesData(String jh) {
        model.getPriceChagnesData(jh, this);
    }

    @Override
    public void getPriceChangesListener(List<UsageChangeInfoPriceEntity> data) {
        view.success(data);
    }

    @Override
    public void onFail(String result) {
        view.failed(result);
    }

    @Override
    public void onError(Exception e) {

    }
}
