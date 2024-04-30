package com.sh3h.meterreading.ui.urge.listener;

import com.example.dataprovider3.entity.CallForPaymentArrearsFeesDetailBean;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;

import java.util.List;

public interface OnArrearsFeesDetailListener extends OnBaseDataListener {

  void getData(List<CallForPaymentArrearsFeesDetailBean> data);

}
