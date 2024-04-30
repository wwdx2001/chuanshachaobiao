package com.sh3h.meterreading.ui.urge.contract;

import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.urge.listener.OnTaskListener;

public interface CallForPaymentTaskContract {

  interface CallForPaymentTaskModel {
    void getTaskList(String searchText, OnTaskListener listener);
  }

  interface CallForPaymentTaskPresenter {
    void getTaskList(String searchText);
  }

  interface CallForPaymentTaskView extends BaseView, MvpView {

  }

}
