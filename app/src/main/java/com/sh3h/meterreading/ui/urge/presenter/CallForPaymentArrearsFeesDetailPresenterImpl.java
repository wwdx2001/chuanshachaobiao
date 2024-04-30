package com.sh3h.meterreading.ui.urge.presenter;

import com.example.dataprovider3.entity.CallForPaymentArrearsFeesDetailBean;
import com.sh3h.meterreading.ui.base.BasePresenter;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentArrearsFeesDetailContract;
import com.sh3h.meterreading.ui.urge.listener.OnArrearsFeesDetailListener;
import com.sh3h.meterreading.ui.urge.model.CallForPaymentArrearsFeesDetailModelImpl;

import java.util.List;

public class CallForPaymentArrearsFeesDetailPresenterImpl extends BasePresenter
        implements CallForPaymentArrearsFeesDetailContract.Presenter, OnArrearsFeesDetailListener {

    private CallForPaymentArrearsFeesDetailContract.View detailView;
    private CallForPaymentArrearsFeesDetailContract.Model detailModel;

    public CallForPaymentArrearsFeesDetailPresenterImpl(CallForPaymentArrearsFeesDetailContract.View detailView) {
        this.detailView = detailView;
        this.detailModel = new CallForPaymentArrearsFeesDetailModelImpl();
    }

    @Override
    public void onFail(String result) {
        detailView.failed(result);
    }

    @Override
    public void onError(Exception e) {

    }


    @Override
    public void getData(List<CallForPaymentArrearsFeesDetailBean> data) {
        detailView.success(data);
    }

    @Override
    public void getArrearsFeesDetail(String renwuid, String s_cid) {
        detailModel.getArrearsFeesDetail(renwuid, s_cid, this);
    }
}
