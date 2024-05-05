package com.sh3h.meterreading.ui.urge.listener;

import com.example.dataprovider3.entity.CallForPaymentSearchBean;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;

import java.util.List;

public interface OnSearchListListener extends OnBaseDataListener {

    void getDataListener(List<CallForPaymentSearchBean> bean);

}
