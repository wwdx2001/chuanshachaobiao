package com.sh3h.meterreading.ui.remoteinspection;

import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;

import java.util.List;

public interface RemoteinSpectionListMvpView extends MvpView {
  void onSaveXunJianBK(List<BiaoKaBean> biaoKaBean);
  void onloadXunJianData(List<BiaoKaBean> biaoKaBean);
  void onloadXunJianListData(List<BiaoKaListBean> biaoKaBean);
  void onIsSaveBiaoKaWholeEntity(List<BiaoKaWholeEntity> biaoKaWholeEntities);

  void onError(String s);
  void onSuccess(String s);

  void onsaveXunJianBK(Boolean aBoolean);

  void onloadXunJianListData2(List<BiaoKaListBean> biaoKaBeans, String type);
}
