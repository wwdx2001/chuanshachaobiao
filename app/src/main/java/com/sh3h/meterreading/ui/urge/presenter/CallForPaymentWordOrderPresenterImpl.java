package com.sh3h.meterreading.ui.urge.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.ui.base.BasePresenter;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentWordOrderContract;
import com.sh3h.meterreading.ui.urge.listener.OnWorkOrderListener;
import com.sh3h.meterreading.ui.urge.model.CallForPaymentWordOrderModelImpl;
import com.sh3h.meterreading.util.StringCheckUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CallForPaymentWordOrderPresenterImpl extends BasePresenter
        implements CallForPaymentWordOrderContract.Presenter, OnWorkOrderListener {

  private CallForPaymentWordOrderContract.View wordOrderView;
  private CallForPaymentWordOrderContract.Model wordOrderModel;

  public CallForPaymentWordOrderPresenterImpl(CallForPaymentWordOrderContract.View wordOrderView) {
    this.wordOrderView = wordOrderView;
    this.wordOrderModel = new CallForPaymentWordOrderModelImpl();
  }

  @Override
  public void getData(List<CuijiaoEntity> data) {
    wordOrderView.success(data);
  }

  @Override
  public void onFail(String result) {
    wordOrderView.failed(result);
  }

  @Override
  public void onError(Exception e) {
  }

  public void getWorkOrderList(String mS_ch) {
    wordOrderModel.getWorkOrderList(mS_ch, this);
  }


  /**
   * 通过销根号或者用户名和地址进行搜索
   * @param searchText 要查找的内容
   * @param mWordOrderList
   */
  public void searchData(String searchText, List<CuijiaoEntity> mWordOrderList) {
    if (mWordOrderList != null && mWordOrderList.size() > 0) {
      if (!"".equals(searchText)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          List<CuijiaoEntity> collect;
          if (StringCheckUtils.isInt(searchText)) {
            collect = mWordOrderList.stream()
              .filter(receiptListEntity -> receiptListEntity.getS_CID().contains(searchText))
              .collect(Collectors.toList());
          } else {
            collect = mWordOrderList.stream()
              .filter(receiptListEntity -> receiptListEntity.getS_HM().contains(searchText) || receiptListEntity.getS_DZ().contains(searchText))
              .collect(Collectors.toList());
          }
          wordOrderView.searchDataNotify(collect);
        } else {
          List<CuijiaoEntity> datas = new ArrayList<>();
          for (CuijiaoEntity data: mWordOrderList) {
            if (data.getS_CID().contains(searchText) || data.getS_HM().contains(searchText) || data.getS_DZ().contains(searchText)) {
              datas.add(data);
            }
          }
          wordOrderView.searchDataNotify(datas);
        }
      } else {
        wordOrderView.searchDataNotify(mWordOrderList);
      }
    }
  }

  /**
   * 用册号 地址 欠费总金额进行升降序
   * @param isGradeDown 是否是降序
   * @param s 查询类型
   * @param data 数据源
   */
  @RequiresApi(api = Build.VERSION_CODES.N)
  public void listSort(Boolean isGradeDown, String s, List<CuijiaoEntity> data) {
    if (s.equals("ch")) {
      Collections.sort(data, new Comparator<CuijiaoEntity>() {
        @Override
        public int compare(CuijiaoEntity entity, CuijiaoEntity entity2) {
          if (!isGradeDown) {
            return (int) (Long.parseLong(entity.getS_CID()) - Long.parseLong(entity2.getS_CID()));
          } else {
            return (int) (Long.parseLong(entity2.getS_CID()) - Long.parseLong(entity.getS_CID()));
          }
        }
      });
    } else if (s.equals("address")) {
      Collections.sort(data, new Comparator<CuijiaoEntity>() {
        @Override
        public int compare(CuijiaoEntity entity, CuijiaoEntity entity2) {
          if (!isGradeDown) {
            return entity.getS_DZ().compareTo(entity2.getS_DZ());
          } else {
            return entity2.getS_DZ().compareTo(entity.getS_DZ());
          }
        }
      });
    } else {
      Collections.sort(data, new Comparator<CuijiaoEntity>() {
        @Override
        public int compare(CuijiaoEntity entity, CuijiaoEntity entity2) {
          if (!isGradeDown) {
            return (int) (Float.parseFloat(entity.getN_QIANFEIZJE()) - Float.parseFloat(entity2.getN_QIANFEIZJE()));
          } else {
            return (int) (Float.parseFloat(entity2.getN_QIANFEIZJE()) - Float.parseFloat(entity.getN_QIANFEIZJE()));
          }
        }
      });
    }

    wordOrderView.success(data);

  }
}
