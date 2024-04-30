package com.sh3h.meterreading.ui.urge.contract;

import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.urge.listener.OnWorkOrderListener;

import java.util.List;

public interface CallForPaymentWordOrderContract {

  public interface Model {
    void getWorkOrderList(String mS_ch, OnWorkOrderListener listener);
  }

  public interface Presenter {
    void searchData(String searchText, List<CuijiaoEntity> mWordOrderList);
  }

  public interface View extends MvpView, BaseView {
    void searchDataNotify(List<CuijiaoEntity> mWordOrderList);
  }

}
