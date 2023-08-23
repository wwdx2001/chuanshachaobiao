package com.sh3h.meterreading.ui.InspectionInput.lr;

import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.List;

public interface LROperatingMvpView extends MvpView {
  void onError(String message);

  void onloadBiaokaListData(List<BiaoKaListBean> biaoKaBeans);

  void onloadBiaokaList(List<BiaoKaBean> biaoKaBeans);

    void onloadWordData(List<XJXXWordBean> xjxxWordBeans, String type);
}
