package com.sh3h.meterreading.ui.urge.model;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.example.dataprovider3.greendaoDao.CuijiaoEntityDao;
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
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.NetworkStatusUtil;
import com.sh3h.serverprovider.entity.ResultBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class CallForPaymentTaskModelImpl implements CallForPaymentTaskContract.CallForPaymentTaskModel {
  private final String TAG = "CallForPaymentTaskModelImpl";

  /**
   * 获取任务列表
   * @param searchText
   * @param listener
   */
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
                    getOrderListData(data, listener);
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

  /**
   * 获取工单列表
   * @param taskData
   * @param listener
   */
  private void getOrderListData(ArrayList<CallForPaymentTaskBean> taskData, OnTaskListener listener) {
    List<Observable<String>> list = new ArrayList<>();


    for (CallForPaymentTaskBean bean : taskData) {
      Observable<String> execute = EasyHttp.post(URL.BASE_URGE_URL1 + URL.CS_SEL_CJCHSCIDCXPDA)
//                    .params("CuiJiaoR", "0018")
              .params("CuiJiaoR", SPUtils.getInstance().getString(com.sh3h.serverprovider.rpc.util.Const.S_YUANGONGH))
              .params("s_ch", bean.getS_CEBENH())
              .cacheMode(CacheMode.NO_CACHE)
              .retryCount(0)
              .sign(true)
              .timeStamp(true)
              .execute(String.class);
      list.add(execute);

    }

      Observable.zip(list, new Function<Object[], List<String>>() {
        @Override
        public List<String> apply(Object[] objects) throws Exception {
          List<String> list = new ArrayList<>();
          for (Object object : objects) {
            list.add((String) object);
          }
          return list;
        }
      }).map(new Function<List<String>, List<CuijiaoEntity>>() {
        @Override
        public List<CuijiaoEntity> apply(List<String> strings) throws Exception {
          List<CuijiaoEntity> cuijiaoEntityList = new ArrayList<>();
          for (int i = 0; i < strings.size(); i++) {
            Gson gson = GsonUtils.getGson(ToNumberPolicy.LONG_OR_DOUBLE);
            ResultBean resultBean = gson.fromJson(strings.get(i), ResultBean.class);
            if (resultBean.getMsgCode().equals("true")) {
              Type type = new TypeToken<ArrayList<CuijiaoEntity>>() {
              }.getType();
              ArrayList<CuijiaoEntity> data = gson.fromJson(gson.toJson(resultBean.getData()), type);
              if (data != null && data.size() > 0) {
                cuijiaoEntityList.addAll(data);
                saveData(data, taskData.get(i).getS_CEBENH());
              }
            }
          }
          return cuijiaoEntityList;
        }
      }).subscribe(new BaseSubscriber<List<CuijiaoEntity>>() {
        @Override
        public void onNext(List<CuijiaoEntity> cuijiaoEntities) {
          super.onNext(cuijiaoEntities);
          getOrderDetailData(taskData, cuijiaoEntities, listener);
        }

        @Override
        public void onError(ApiException e) {
          listener.onError(e);
          e.getCause().printStackTrace();
        }
      });

  }

  /**
   * 获取工单详情
   * @param taskData
   * @param entities
   * @param listener
   */
  private void getOrderDetailData(ArrayList<CallForPaymentTaskBean> taskData, List<CuijiaoEntity> entities, OnTaskListener listener) {
    List<Observable<String>> list = new ArrayList<>();

    for (int i = 0; i < entities.size(); i++) {
      String renwuid = entities.get(i).getS_RENWUID();
      String s_cid = entities.get(i).getS_CID();

      Observable<String> execute = EasyHttp.post(URL.BASE_URGE_URL1 + URL.CS_SEL_CJXIANGXIMXPDA)
              .params("renwuid", renwuid)
              .params("s_cid", s_cid)
              .cacheMode(CacheMode.NO_CACHE)
              .retryCount(0)
              .sign(true)
              .timeStamp(true)
              .execute(String.class);
      list.add(execute);

    }

    Observable.zip(list, new Function<Object[], List<String>>() {
      @Override
      public List<String> apply(Object[] objects) throws Exception {
        List<String> list = new ArrayList<>();
        for (Object object : objects) {
          list.add((String) object);
        }
        return list;
      }
    }).map(new Function<List<String>, List<CuijiaoEntity>>() {
      @Override
      public List<CuijiaoEntity> apply(List<String> strings) throws Exception {
        List<CuijiaoEntity> cuijiaoEntityList = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
          Gson gson = GsonUtils.getGson(ToNumberPolicy.LONG_OR_DOUBLE);
          ResultBean resultBean = gson.fromJson(strings.get(i).toString(), ResultBean.class);
          if (resultBean.getMsgCode().equals("true")) {
            Type type = new TypeToken<ArrayList<CuijiaoEntity>>() {
            }.getType();
            ArrayList<CuijiaoEntity> data = gson.fromJson(gson.toJson(resultBean.getData()), type);
            if (data != null && data.size() > 0) {
              saveData(data.get(0), entities.get(i).getS_RENWUID(), entities.get(i).getS_CID());
            }
          }
        }
        return cuijiaoEntityList;
      }
    }).subscribe(new BaseSubscriber<List<CuijiaoEntity>>() {
      @Override
      public void onNext(List<CuijiaoEntity> cuijiaoEntities) {
        super.onNext(cuijiaoEntities);
        listener.getData(taskData);
      }

      @Override
      public void onError(ApiException e) {
        listener.onError(e);
        e.getCause().printStackTrace();
      }
    });
  }

  public static CuijiaoEntity mergeObject(CuijiaoEntity sourceEntity, CuijiaoEntity sourceEntity2) throws IllegalAccessException {

    Class user1Class = sourceEntity.getClass();
    CuijiaoEntity targetEntity = new CuijiaoEntity();

    Field[] fields = user1Class.getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);
      Object o1 = field.get(sourceEntity);
      Object o2 = field.get(sourceEntity2);

      if (o1 != null) {
        field.set(targetEntity, o1);
      } else if (o2 != null) {
        field.set(targetEntity, o2);
      }
    }

    return targetEntity;
  }

  private void saveData(CuijiaoEntity data, String renwuid, String s_cid) {
    data.setId((long) (s_cid + renwuid).hashCode());
    data.setS_CID(s_cid);
    data.setIsDetailMessage(true);

    List<CuijiaoEntity> list = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
            .queryBuilder().where(CuijiaoEntityDao.Properties.S_RENWUID.eq(renwuid)).list();

    try {
      if (list.size() == 0) {
        list.add(new CuijiaoEntity());
      }
      CuijiaoEntity cuijiaoEntity = mergeObject(data, list.get(0));
      GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
              .insertOrReplaceInTx(cuijiaoEntity);
    } catch (Exception e) {
      e.printStackTrace();
      LogUtil.e(TAG, "数据保存失败：" + e.getMessage());
    }
  }

  private void saveData(ArrayList<CuijiaoEntity> data, String s_ch) {
    for (CuijiaoEntity bean : data) {
      bean.setId((long) (bean.getS_CID() + bean.getS_RENWUID()).hashCode());
      bean.setIsDetailMessage(false);
      bean.setS_CH(s_ch);
    }

    GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
            .insertOrReplaceInTx(data);
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
