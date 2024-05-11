package com.sh3h.meterreading.ui.urge.model;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentTaskContract;
import com.sh3h.meterreading.ui.urge.listener.OnTaskListener;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.GsonUtils;
import com.sh3h.mobileutil.util.NetworkStatusUtil;
import com.sh3h.serverprovider.entity.ResultBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CallForPaymentTaskModelImpl implements CallForPaymentTaskContract.CallForPaymentTaskModel {
  private final String TAG = "CallForPaymentTaskModelImpl";

  @SuppressLint("LongLogTag")
  @Override
  public void getTaskList(String searchText, OnTaskListener listener) {
    // 判断网络状态
    if (NetworkStatusUtil.isNetworkJudgment(MainApplication.getInstance())) {
      String account = SPUtils.getInstance().getString(Const.S_YUANGONGH);
      EasyHttp.post(URL.BASE_URGE_URL1 + URL.CS_SEL_CJCEHAOCXPDA)
//              .params("CuiJiaoR", "0018")
              .params("CuiJiaoR", SPUtils.getInstance().getString(com.sh3h.serverprovider.rpc.util.Const.S_YUANGONGH))
              .params("key", searchText)
              .execute(new SimpleCallBack<String>() {
                @Override
                public void onStart() {
                  super.onStart();
                }

                @Override
                public void onError(com.zhouyou.http.exception.ApiException e) {
                  ToastUtils.showLong(e.getMessage());
                  listener.onError(e);
                }

                @Override
                public void onSuccess(String s) {
                  Gson gson = GsonUtils.getGson(ToNumberPolicy.LONG_OR_DOUBLE);
                  ResultBean resultBean = gson.fromJson(s.toString(), ResultBean.class);
                  if (resultBean.getMsgCode().equals("true")) {
                    Type type = new TypeToken<ArrayList<CallForPaymentTaskBean>>() {
                    }.getType();
                    ArrayList<CallForPaymentTaskBean> data = gson.fromJson(gson.toJson(resultBean.getData()), type);
                    listener.getData(data);
                    saveData(data);
                  } else {
                    listener.onFail(resultBean.getMsgInfo());
                  }
                }
              });
    } else {
      List<CallForPaymentTaskBean> data = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCallForPaymentTaskBeanDao()
        .queryBuilder().list();
      if (data != null && data.size() > 0) {
        listener.getData(data);
      } else {
        listener.onFail(null);
      }
    }
  }

  private void saveData(ArrayList<CallForPaymentTaskBean> data) {
    ArrayList<CallForPaymentTaskBean> taskBeans = new ArrayList<>();
    for (CallForPaymentTaskBean bean : data) {
      bean.setId((long) bean.getS_CEBENH().hashCode());
      taskBeans.add(bean);
    }
    if (taskBeans.size() > 0) {
      GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCallForPaymentTaskBeanDao()
        .deleteAll();
    }

    GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCallForPaymentTaskBeanDao()
      .insertOrReplaceInTx(data);

  }

}
