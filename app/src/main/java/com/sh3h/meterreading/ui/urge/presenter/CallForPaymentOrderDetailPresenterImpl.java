package com.sh3h.meterreading.ui.urge.presenter;

import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentOrderDetailContract;
import com.sh3h.meterreading.ui.urge.listener.OnOrderDetailListener;
import com.sh3h.meterreading.ui.urge.model.CallForPaymentOrderDetailModelImpl;
import com.sh3h.mobileutil.util.TextUtil;

public class CallForPaymentOrderDetailPresenterImpl
        implements CallForPaymentOrderDetailContract.Presenter, OnOrderDetailListener {

    private CallForPaymentOrderDetailContract.View detailView;
    private CallForPaymentOrderDetailContract.Model detailModel;

    public CallForPaymentOrderDetailPresenterImpl(CallForPaymentOrderDetailContract.View detailView) {
        this.detailView = detailView;
        this.detailModel = new CallForPaymentOrderDetailModelImpl();
    }

    @Override
    public void getOrderDetail(String renwuid, String s_cid) {
        detailModel.getOrderDetail(renwuid, s_cid, this);
    }

    @Override
    public void saveOrUploadData(CallForPaymentBackFillDataBean bean, boolean isSave) {
        if (!TextUtil.isNullOrEmpty(bean.getCallForPayImages())) {
            detailModel.saveOrUploadData(bean, isSave, this);
        } else {
            onFail("请上传催缴照片");
        }
    }


    @Override
    public void getData(CuijiaoEntity data) {
        detailView.success(data);
    }

    @Override
    public void getBackFillData(CallForPaymentBackFillDataBean bean) {
        detailView.getBackFillData(bean);
    }

    @Override
    public void getResult(Object o) {
        detailView.getResult((String) o);
    }

    @Override
    public void onFail(String result) {
        detailView.failed(result);
    }

    @Override
    public void onError(Exception e) {

    }
}
