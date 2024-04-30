package com.sh3h.meterreading.ui.urge.contract;

import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.urge.listener.OnOrderDetailListener;

public interface CallForPaymentOrderDetailContract {

  public interface Model {
    void getOrderDetail(String renwuid, String s_cid, OnOrderDetailListener listener);
    void saveOrUploadData(CallForPaymentBackFillDataBean bean, boolean isSave, OnOrderDetailListener listener);
  }

  public interface Presenter {
    void getOrderDetail(String renwuid, String s_cid);
    void saveOrUploadData(CallForPaymentBackFillDataBean bean, boolean isSave);
  }

  interface View extends MvpView, BaseView {
    void getResult(String o);
    void getBackFillData(CallForPaymentBackFillDataBean bean);
  }

}
