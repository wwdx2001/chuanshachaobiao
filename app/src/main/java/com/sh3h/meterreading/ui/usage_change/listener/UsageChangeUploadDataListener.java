package com.sh3h.meterreading.ui.usage_change.listener;

import com.example.dataprovider3.entity.UsageChangeUploadWholeEntity;
import com.sh3h.dataprovider.greendaoEntity.JianHao;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.List;

public interface UsageChangeUploadDataListener extends OnBaseDataListener {

    void getCode(List<XJXXWordBean> beans);
    void getSaveDataListener(UsageChangeUploadWholeEntity entity);
    void getJianHaoList(List<JianHao> list);
    void uploadSuccess(String s);

}
