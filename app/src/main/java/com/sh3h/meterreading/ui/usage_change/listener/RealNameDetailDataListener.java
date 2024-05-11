package com.sh3h.meterreading.ui.usage_change.listener;

import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.List;

public interface RealNameDetailDataListener extends OnBaseDataListener {

    void getUserTypeListener(List<XJXXWordBean> beans);
    void getSaveDataListener(RealNameWholeEntity bean);
    void uploadSuccess(String s);

}
