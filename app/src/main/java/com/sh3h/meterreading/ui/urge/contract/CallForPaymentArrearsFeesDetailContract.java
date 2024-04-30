package com.sh3h.meterreading.ui.urge.contract;

import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.urge.listener.OnArrearsFeesDetailListener;

public interface CallForPaymentArrearsFeesDetailContract {

  public interface Model {
    void getArrearsFeesDetail(String renwuid, String s_cid, OnArrearsFeesDetailListener listener);
  }

  public interface Presenter {
    void getArrearsFeesDetail(String renwuid, String s_cid);
  }

  public interface View extends MvpView, BaseView {
  }

}
