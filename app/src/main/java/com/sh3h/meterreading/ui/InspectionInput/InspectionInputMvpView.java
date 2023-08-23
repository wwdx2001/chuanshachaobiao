package com.sh3h.meterreading.ui.InspectionInput;

import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;

import java.util.List;

public interface InspectionInputMvpView  extends MvpView {
  void onError(String s);
  void onSuccess(String s);

  void onloadBiaokaList(List<BiaoKaBean> biaoKaBeans, boolean isnext);

  void onloadBiaokaListData(List<BiaoKaListBean> biaoKaBeans, boolean isnext);

  void onSaveBiaoKaWholeEntity(Boolean aBoolean, Boolean isfnish);

    void loadBiaokaListBeans(List<BiaoKaListBean> biaoKaBeans, String type);

  void loadBiaokaWholeEntBeans(List<BiaoKaWholeEntity> biaoKaBeans);
}
