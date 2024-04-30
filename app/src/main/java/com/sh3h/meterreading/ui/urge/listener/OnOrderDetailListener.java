package com.sh3h.meterreading.ui.urge.listener;

import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;

public interface OnOrderDetailListener extends OnBaseDataListener {

  void getData(CuijiaoEntity data);
  void getBackFillData(CallForPaymentBackFillDataBean bean);
  void getResult(Object o);
}
