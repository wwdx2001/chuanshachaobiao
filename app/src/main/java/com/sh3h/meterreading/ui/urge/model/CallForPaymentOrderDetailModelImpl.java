package com.sh3h.meterreading.ui.urge.model;

import android.annotation.SuppressLint;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.example.dataprovider3.greendaoDao.CallForPaymentBackFillDataBeanDao;
import com.example.dataprovider3.greendaoDao.CuijiaoEntityDao;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentOrderDetailContract;
import com.sh3h.meterreading.ui.urge.listener.OnOrderDetailListener;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.GsonUtils;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.NetworkStatusUtil;
import com.sh3h.serverprovider.entity.ImageItem;
import com.sh3h.serverprovider.entity.ResultBean;
import com.sh3h.serverprovider.entity.ResultEntity;
import com.sh3h.serverprovider.entity.VoiceItem;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;

import static com.sh3h.meterreading.util.Const.UPLOADDATA_ERROR_CODE;

public class CallForPaymentOrderDetailModelImpl implements CallForPaymentOrderDetailContract.Model {

  private final String TAG = "CallForPaymentOrderDetailModelImpl";
  private boolean isSuccess = false;

  @SuppressLint("LongLogTag")
  @Override
  public void getOrderDetail(String renwuid, String s_cid, OnOrderDetailListener listener) {

    List<CallForPaymentBackFillDataBean> dataBeans = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCallForPaymentBackFillDataBeanDao()
      .queryBuilder().where(CallForPaymentBackFillDataBeanDao.Properties.V_RENWUID.eq(renwuid)).list();
    if (dataBeans != null && dataBeans.size() > 0) {
      CallForPaymentBackFillDataBean bean = dataBeans.get(0);
      if (bean != null) {
        listener.getBackFillData(bean);
      }
    }

    if (NetworkStatusUtil.isNetworkJudgment(MainApplication.getInstance())) {
        EasyHttp.post(URL.BASE_URGE_URL1 + URL.CS_SEL_CJXIANGXIMXPDA)
                .params("renwuid", renwuid)
                .params("s_cid", s_cid)
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
                            Type type = new TypeToken<ArrayList<CuijiaoEntity>>() {
                            }.getType();
                            ArrayList<CuijiaoEntity> data = gson.fromJson(gson.toJson(resultBean.getData()), type);
                            saveData(data.get(0), renwuid, s_cid, listener);
                        } else {
                            listener.onFail(resultBean.getMsgInfo());
                        }
                    }
                });
    } else {
      List<CuijiaoEntity> data = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
        .queryBuilder().where(CuijiaoEntityDao.Properties.S_RENWUID.eq(renwuid)).list();
      Log.i(TAG, "getOrderDetail: " + renwuid);
      if (data != null && data.size() > 0) {
        CuijiaoEntity cuijiaoEntity = data.get(0);
        if (cuijiaoEntity != null && cuijiaoEntity.getIsDetailMessage()) {
          listener.getData(cuijiaoEntity);
        } else {
          listener.onFail(null);
        }
      } else {
        listener.onFail(null);
      }
    }

  }

  public static CuijiaoEntity mergeObject(CuijiaoEntity sourceEntity, CuijiaoEntity sourceEntity2) throws Exception {

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

  private void saveData(CuijiaoEntity data, String renwuid, String s_cid, OnOrderDetailListener listener){
    data.setId((long) (s_cid + renwuid).hashCode());
    data.setS_CID(s_cid);
    data.setIsDetailMessage(true);

    List<CuijiaoEntity> list = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
      .queryBuilder().where(CuijiaoEntityDao.Properties.S_RENWUID.eq(renwuid)).list();

    try {
        CuijiaoEntity cuijiaoEntity = mergeObject(data, list.get(0));
        listener.getData(cuijiaoEntity);
        GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
        .insertOrReplaceInTx(cuijiaoEntity);
    } catch (Exception e) {
      e.printStackTrace();
      LogUtil.e(TAG, "数据保存失败：" + e.getMessage());
    }





//    GreenDaoUtils.getInstance().getDaoSession(BaseApplication.getInstance()).getCuijiaoEntityDao()
//      .deleteInTx(list);
  }

  @Override
  public void saveOrUploadData(CallForPaymentBackFillDataBean bean, boolean isSave, OnOrderDetailListener listener) {
    if (isSave) {
      bean.setIsSave(true);
      GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCallForPaymentBackFillDataBeanDao()
        .insertOrReplaceInTx(bean);
    } else {
        Type imageListType = new TypeToken<ArrayList<com.sh3h.serverprovider.entity.ImageItem>>() {
        }.getType();
        Type voiceListType = new TypeToken<ArrayList<VoiceItem>>() {
        }.getType();

        List<com.sh3h.serverprovider.entity.ImageItem> list1 = com.blankj.utilcode.util.GsonUtils.fromJson(bean.getCallForPayImages(), imageListType);
        List<com.sh3h.serverprovider.entity.ImageItem> list2 = com.blankj.utilcode.util.GsonUtils.fromJson(bean.getOtherImages(), imageListType);
        List<VoiceItem> list3 = com.blankj.utilcode.util.GsonUtils.fromJson(bean.getVoices(), voiceListType);
        List<ImageItem> list4 = com.blankj.utilcode.util.GsonUtils.fromJson(bean.getVideos(), imageListType);
        list1.addAll(list2);
        if (list4 != null) {
            list1.addAll(list4);
        }

        List<File> files = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            File file = new File(list1.get(i).path);
            if (file.exists()) {
                files.add(file);
            }
        }

        for (VoiceItem item : list3) {
            File file = new File(item.path);
            if (file.exists()) {
                files.add(file);
            }
        }

        List<io.reactivex.Observable<String>> observableList = new ArrayList<>();

        io.reactivex.Observable<String> upImageObservable = EasyHttp.post(URL.BASE_URGE_URL1 + URL.AppReturnOrderUploadFileCuiJiao)
                .params("S_LEIXING", "CuiJiaoD")
                .params("S_GONGDANBH", bean.getV_RENWUID())
                .addFileParams("S_ZHAOPIANLJ", files, null)
                .cacheMode(CacheMode.NO_CACHE)
                .retryCount(0)
                .sign(true)
                .timeStamp(true)
                .execute(String.class);

        io.reactivex.Observable<String> upDataObservable = EasyHttp.post(URL.BASE_URGE_URL1 + URL.CS_ins_CJRENWUMXPDA)
                .params("v_caozuor", bean.getV_CAOZUOR())
                .params("V_CAOZUOSJ", bean.getV_CAOZUOSJ())
                .params("V_RENWUM", bean.getV_RENWUM())
                .params("V_RENWUID", bean.getV_RENWUID())
                .params("V_QIANFEIYY1", String.valueOf(bean.getV_QIANFEIYY1_POSITION()))
                .params("V_QIANFEIYY2", String.valueOf(bean.getV_QIANFEIYY2_POSITION()))
                .params("V_ISYONGSHUI", String.valueOf(bean.getV_ISYONGSHUI_POSITION()))
                .params("V_ISLIANXIYH", String.valueOf(bean.getV_ISLIANXIYH_POSITION()))
                .params("V_ISZHUTICZ", String.valueOf(bean.getV_ISZHUTICZ_POSITION()))
                .params("V_ISZHUTICZ", String.valueOf(bean.getV_ISZHUTICZ_POSITION()))
                .params("V_ISYYFF", String.valueOf(bean.getV_ISYYFF_POSITION()))
                .params("V_ISXINXIBG", String.valueOf(bean.getV_ISXINXIBG_POSITION()))
                .params("V_LIANXIR", bean.getV_LIANXIR())
                .params("V_LIANXIDH", bean.getV_LIANXIDH())
                .params("V_BEIZHU", bean.getV_BEIZHU())
                .sign(true)
                .timeStamp(true)
                .retryCount(0)
                .execute(String.class);


        upImageObservable
                .flatMap(new io.reactivex.functions.Function<String, io.reactivex.ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        ResultEntity resultEntity = com.blankj.utilcode.util.GsonUtils.fromJson(s, ResultEntity.class);
                        if (UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                            resultEntity.setMsgCode(UPLOADDATA_ERROR_CODE);
                            return io.reactivex.Observable.just(com.blankj.utilcode.util.GsonUtils.toJson(resultEntity));
                        } else {
                            if ("true".equals(resultEntity.getMsgCode())) {
                                isSuccess = true;
                                return upDataObservable;
                            } else {
                                resultEntity.setMsgCode(UPLOADDATA_ERROR_CODE);
                                return io.reactivex.Observable.just(com.blankj.utilcode.util.GsonUtils.toJson(resultEntity));
                            }
                        }
                    }
                }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onError(com.zhouyou.http.exception.ApiException e) {
                        e.getCause().printStackTrace();
                        //失败回调
                        LogUtils.e("----------getNBModuleDZWFGDInfo：" + e.getCode() + "---"
                                + e.getMessage());
                        ToastUtils.showLong(e.getCode() + "---"
                                + e.getMessage());
                        if (isSuccess) {
                            //图片上传成功，数据上传失败
                            ToastUtils.showLong("图片上传成功，" + e.getCode() + "---"
                                    + e.getMessage());
                        } else {
                            ToastUtils.showLong(e.getCode() + "---"
                                    + e.getMessage());
                        }
                        ToastUtils.showLong(e.getCause().toString());
                    }

                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        //成功回调
                        ResultEntity resultEntity = com.blankj.utilcode.util.GsonUtils.fromJson((String) s, ResultEntity.class);
                        if (UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                            //图片上传失败
                            ToastUtils.showLong(resultEntity.getMsgInfo());
                        } else if ("true".equals(resultEntity.getMsgCode())) {
                            //数据图片都上传成功
                            listener.getResult(resultEntity.getMsgInfo());
                            //删除回填数据
                            GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
                                    .getCallForPaymentBackFillDataBeanDao().deleteInTx(bean);
                            //删除上传成功的工单数据
                            List<CuijiaoEntity> list = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
                                    .queryBuilder().where(CuijiaoEntityDao.Properties.S_RENWUID.eq(bean.getV_RENWUID())).list();
                            GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
                                    .deleteInTx(list);
                        } else {
                            //图片上传成功，数据上传失败
                            ToastUtils.showLong("图片上传成功，" + resultEntity.getMsgInfo());
                        }
                    }
                });
    }
  }
}
