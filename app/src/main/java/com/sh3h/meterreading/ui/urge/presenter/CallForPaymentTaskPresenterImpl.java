package com.sh3h.meterreading.ui.urge.presenter;

import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.sh3h.meterreading.ui.base.BasePresenter;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentTaskContract;
import com.sh3h.meterreading.ui.urge.listener.OnTaskListener;
import com.sh3h.meterreading.ui.urge.model.CallForPaymentTaskModelImpl;

import java.util.List;

public class CallForPaymentTaskPresenterImpl extends BasePresenter<CallForPaymentTaskContract.CallForPaymentTaskView>
        implements CallForPaymentTaskContract.CallForPaymentTaskPresenter, OnTaskListener {

    private CallForPaymentTaskContract.CallForPaymentTaskView taskView;
    private CallForPaymentTaskContract.CallForPaymentTaskModel taskModel;

    public CallForPaymentTaskPresenterImpl(CallForPaymentTaskContract.CallForPaymentTaskView taskView) {
        this.taskView = taskView;
        this.taskModel = new CallForPaymentTaskModelImpl();
    }

    @Override
    public void getData(List<CallForPaymentTaskBean> data) {
        taskView.success(data);
    }

    @Override
    public void onFail(String result) {
        taskView.failed(result);
    }

    @Override
    public void onError(Exception e) {
        taskView.error(e.getMessage());
    }

    @Override
    public void getTaskList(String searchText) {
        taskModel.getTaskList(searchText, this);
    }
}

