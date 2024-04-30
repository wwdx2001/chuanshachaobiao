package com.sh3h.meterreading.ui.urge.listener;

import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;

import java.util.List;

public interface OnTaskListener extends OnBaseDataListener {

  void getData(List<CallForPaymentTaskBean> data);
}
