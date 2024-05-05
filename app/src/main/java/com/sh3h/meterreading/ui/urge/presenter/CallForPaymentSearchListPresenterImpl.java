package com.sh3h.meterreading.ui.urge.presenter;

import com.example.dataprovider3.entity.CallForPaymentSearchBean;
import com.sh3h.meterreading.ui.base.BasePresenter;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentSearchListContract;
import com.sh3h.meterreading.ui.urge.listener.OnSearchListListener;
import com.sh3h.meterreading.ui.urge.model.CallForPaymentSearchListModelImpl;

import java.util.List;

public class CallForPaymentSearchListPresenterImpl extends BasePresenter<CallForPaymentSearchListContract.View>
        implements  CallForPaymentSearchListContract.Presenter, OnSearchListListener {

    CallForPaymentSearchListContract.Model model;
    CallForPaymentSearchListContract.View view;

    public CallForPaymentSearchListPresenterImpl(CallForPaymentSearchListContract.View view) {
        this.model = new CallForPaymentSearchListModelImpl();
        this.view = view;
    }


    @Override
    public void getDataListener(List<CallForPaymentSearchBean> bean) {
        view.success(bean);
    }

    @Override
    public void getSearchListData(String cid, String address) {
        model.getData(cid, address, this);
    }

    @Override
    public void submitData(List<CallForPaymentSearchBean> bean) {
        model.submitData(bean, this);
    }

    @Override
    public void onFail(String result) {

    }

    @Override
    public void onError(Exception e) {

    }
}
