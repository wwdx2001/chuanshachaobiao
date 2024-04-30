package com.sh3h.meterreading.ui.urge.listener;

import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;

import java.util.List;

public interface OnWorkOrderListener extends OnBaseDataListener {

  void getData(List<CuijiaoEntity> data);

}
