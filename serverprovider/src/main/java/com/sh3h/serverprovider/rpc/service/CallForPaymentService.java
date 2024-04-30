package com.sh3h.serverprovider.rpc.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentArrearsFeesDetailBean;
import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.example.dataprovider3.greendaoDao.CuijiaoEntityDao;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.mobileutil.util.GsonUtils;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.NetworkStatusUtil;
import com.sh3h.serverprovider.entity.ImageItem;
import com.sh3h.serverprovider.entity.ResultBean;
import com.sh3h.serverprovider.entity.ResultEntity;
import com.sh3h.serverprovider.entity.VoiceItem;
import com.sh3h.serverprovider.rpc.util.URL;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.subsciber.BaseSubscriber;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;

public class CallForPaymentService extends BaseApiService {
    private final String TAG = "CallForPaymentService";
    private final String UPLOADDATA_ERROR_CODE = "-99999";

    private boolean isSuccess = false;
    private String responce = "fail";


    /**
     * 催缴任务
     */
    public static final String CS_SEL_CJCEHAOCXPDA = "CS_SEL_CJCEHAOCXPDA";

    /**
     * 催缴任务工单列表
     */
    public static final String CS_SEL_CJCHSCIDCXPDA = "CS_SEL_CJCHSCIDCXPDA";

    /**
     * 欠费详情
     */
    public static final String CS_SEL_CJSCIDZDMXPDA = "CS_SEL_CJSCIDZDMXPDA";

    /**
     * 催缴任务详情
     */
    public static final String CS_SEL_CJXIANGXIMXPDA = "CS_SEL_CJXIANGXIMXPDA";

    /**
     * 催缴工单详情上传回填信息
     */
    public static final String CS_ins_CJRENWUMXPDA = "CS_ins_CJRENWUMXPDA";

    @Override
    public String getHandlerURL() {
        return URL.BASE_URGE_URL1;
    }

    public List<CallForPaymentTaskBean> getCallForPayTaskList(String account, String searchText, Context context) throws ApiException {
        List<CallForPaymentTaskBean> data = null;
        if (NetworkStatusUtil.isNetworkJudgment(context)) {
            JSONObject resp = null;
            EasyHttp.post(URL.BASE_URGE_URL1 + CS_SEL_CJCEHAOCXPDA)
                    .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                    .params("cby", "0018")
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onError(com.zhouyou.http.exception.ApiException e) {
                            ToastUtils.showLong(e.getMessage());
                        }

                        @Override
                        public void onSuccess(String s) {
                            Gson gson = GsonUtils.getGson(ToNumberPolicy.LONG_OR_DOUBLE);
                            ResultBean resultBean = gson.fromJson(resp.toString(), ResultBean.class);
                            if (resultBean.getMsgCode().equals("true")) {
                                Type type = new TypeToken<ArrayList<CallForPaymentTaskBean>>() {
                                }.getType();
//                                data = gson.fromJson(gson.toJson(resultBean.getData()), type);
//                                saveTaskData(data, context);
                            }
                        }
                    });
        } else {
            List<CallForPaymentTaskBean> taskBeans = GreenDaoUtils.getInstance().getDaoSession(context).getCallForPaymentTaskBeanDao()
                    .queryBuilder().list();
            data = taskBeans;
            if (data != null && data.size() > 0) {

            } else {

            }
        }

        return data;
    }

    private void saveTaskData(List<CallForPaymentTaskBean> data, Context context) {
        ArrayList<CallForPaymentTaskBean> taskBeans = new ArrayList<>();
        for (CallForPaymentTaskBean bean : data) {
            bean.setId((long) bean.getS_CEBENH().hashCode());
            taskBeans.add(bean);
        }
        if (taskBeans.size() > 0) {
            GreenDaoUtils.getInstance().getDaoSession(context).getCallForPaymentTaskBeanDao()
                    .deleteAll();
        }

        GreenDaoUtils.getInstance().getDaoSession(context).getCallForPaymentTaskBeanDao()
                .insertOrReplaceInTx(data);

    }

    public List<CuijiaoEntity> getCallForPayWorkOrderList(String s_ch, Context mContext) throws ApiException {
        List<CuijiaoEntity> data = null;
        if (NetworkStatusUtil.isNetworkJudgment(getContext())) {
            JSONObject resp = null;
            try {
                resp = this.callJSONObject(
                        CS_SEL_CJCHSCIDCXPDA, s_ch);
                Gson gson = GsonUtils.getGson(ToNumberPolicy.LONG_OR_DOUBLE);
                ResultBean resultBean = gson.fromJson(resp.toString(), ResultBean.class);
                if (resultBean.getMsgCode().equals("true")) {
                    Type type = new TypeToken<ArrayList<CuijiaoEntity>>() {
                    }.getType();
                    data = gson.fromJson(gson.toJson(resultBean.getData()), type);
                    saveWorkOrderData(data, s_ch, mContext);
                } else {
                    data = new ArrayList<>();
                }
            } catch (ApiException e) {
                LogUtil.e("API", "获取催缴工单列表失败", e);
                throw e;
            }
        } else {
            List<CuijiaoEntity> entityList = GreenDaoUtils.getInstance().getDaoSession(mContext).getCuijiaoEntityDao()
                    .queryBuilder().where(CuijiaoEntityDao.Properties.S_CH.eq(s_ch)).list();
            data = entityList;
            if (data != null && data.size() > 0) {
            } else {
            }
        }

        return data;
    }

    private void saveWorkOrderData(List<CuijiaoEntity> data, String s_ch, Context context) {
        for (CuijiaoEntity bean : data) {
            bean.setId((long) (bean.getS_CID() + bean.getS_RENWUID()).hashCode());
            bean.setIsDetailMessage(false);
            bean.setS_CH(s_ch);
        }

        GreenDaoUtils.getInstance().getDaoSession(context).getCuijiaoEntityDao()
                .insertOrReplaceInTx(data);
    }

    public CuijiaoEntity getCallForPayOrderDetail(String renwuid, String s_cid, Context mContext) throws ApiException {
        CuijiaoEntity data = null;
        if (NetworkStatusUtil.isNetworkJudgment(getContext())) {
            JSONObject resp = null;
            try {
                resp = this.callJSONObject(
                        CS_SEL_CJXIANGXIMXPDA, renwuid, s_cid);
                Gson gson = GsonUtils.getGson(ToNumberPolicy.LONG_OR_DOUBLE);
                ResultBean resultBean = gson.fromJson(resp.toString(), ResultBean.class);
                if (resultBean.getMsgCode().equals("true")) {
                    Type type = new TypeToken<ArrayList<CuijiaoEntity>>() {
                    }.getType();
                    data = gson.fromJson(gson.toJson(resultBean.getData()), type);
                    saveOrderDetailData(data, renwuid, s_cid, mContext);
                }
            } catch (ApiException e) {
                LogUtil.e("API", "获取催缴工单详情失败", e);
                throw e;
            }
        } else {
            List<CuijiaoEntity> entityList = GreenDaoUtils.getInstance().getDaoSession(mContext).getCuijiaoEntityDao()
                    .queryBuilder().where(CuijiaoEntityDao.Properties.S_RENWUID.eq(renwuid)).list();
            Log.i(TAG, "getOrderDetail: " + renwuid);
            if (entityList != null && entityList.size() > 0) {
                CuijiaoEntity cuijiaoEntity = entityList.get(0);
                if (cuijiaoEntity != null && cuijiaoEntity.getIsDetailMessage()) {
                    data = cuijiaoEntity;
                } else {
                }
            } else {
            }
        }

        return data;
    }

    public static CuijiaoEntity mergeObject(CuijiaoEntity sourceEntity, CuijiaoEntity targetEntity) throws Exception {

        Class user1Class = sourceEntity.getClass();
        Class user2Class = targetEntity.getClass();

        Field[] user1Fields = user1Class.getDeclaredFields();
        Field[] user2Fields = user2Class.getDeclaredFields();
        for (int i = 0; i < user1Fields.length; i++) {
            Field sourceField = user1Fields[i];
            if (Modifier.isStatic(sourceField.getModifiers())) {
                continue;
            }
            Field targetField = user2Fields[i];
            if (Modifier.isStatic(targetField.getModifiers())) {
                continue;
            }
            sourceField.setAccessible(true);
            targetField.setAccessible(true);

            if (sourceField.get(sourceEntity) != null) {
                targetField.set(targetEntity, sourceField.get(sourceEntity));
            }
        }
        return targetEntity;
    }

    private void saveOrderDetailData(CuijiaoEntity data, String renwuid, String s_cid, Context context) {
        data.setId((long) (s_cid + renwuid).hashCode());
        data.setS_CID(s_cid);
        data.setIsDetailMessage(true);

        List<CuijiaoEntity> list = GreenDaoUtils.getInstance().getDaoSession(context).getCuijiaoEntityDao()
                .queryBuilder().where(CuijiaoEntityDao.Properties.S_RENWUID.eq(data.getS_RENWUID())).list();

        try {
            CuijiaoEntity cuijiaoEntity = mergeObject(data, list.get(0));
            GreenDaoUtils.getInstance().getDaoSession(context).getCuijiaoEntityDao()
                    .insertOrReplaceInTx(cuijiaoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "数据保存失败：" + e.getMessage());
        }
    }

    public List<CallForPaymentArrearsFeesDetailBean> getArrearsFeesDetail(String renwuid, String s_cid, Context mContext) throws ApiException {
        if (NetworkStatusUtil.isNetworkJudgment(getContext())) {

        }
        JSONObject resp = null;
        List<CallForPaymentArrearsFeesDetailBean> data = null;
        try {
            resp = this.callJSONObject(
                    CS_SEL_CJSCIDZDMXPDA, renwuid, s_cid);
            Gson gson = GsonUtils.getGson(ToNumberPolicy.LONG_OR_DOUBLE);
            ResultBean resultBean = gson.fromJson(resp.toString(), ResultBean.class);
            if (resultBean.getMsgCode().equals("true")) {
                Type type = new TypeToken<ArrayList<CallForPaymentArrearsFeesDetailBean>>() {
                }.getType();
                List<CallForPaymentArrearsFeesDetailBean> o = gson.fromJson(gson.toJson(resultBean.getData()), type);
                data = o;
            }
        } catch (ApiException e) {
            LogUtil.e("API", "获取催缴工单详情失败", e);
            throw e;
        }

        return data;
    }


    @SuppressLint("CheckResult")
    public String saveOrUploadData(CallForPaymentBackFillDataBean bean, boolean isSave, Context context) {
        if (isSave) {
            bean.setIsSave(true);
            GreenDaoUtils.getInstance().getDaoSession(context).getCallForPaymentBackFillDataBeanDao()
                    .insertOrReplaceInTx(bean);
        } else {
            Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
            }.getType();
            Type voiceListType = new TypeToken<ArrayList<VoiceItem>>() {
            }.getType();

            List<ImageItem> list1 = com.blankj.utilcode.util.GsonUtils.fromJson(bean.getCallForPayImages(), imageListType);
            List<ImageItem> list2 = com.blankj.utilcode.util.GsonUtils.fromJson(bean.getOtherImages(), imageListType);
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

            io.reactivex.Observable<String> upImageObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL + URL.UploadFile)
                    .params("S_LEIXING", "CuiJiaoD")
                    .params("S_GONGDANBH", bean.getV_RENWUID())
                    .addFileParams("S_ZHAOPIANLJ", files, null)
                    .cacheMode(CacheMode.NO_CACHE)
                    .retryCount(0)
                    .sign(true)
                    .timeStamp(true)
                    .execute(String.class);

            io.reactivex.Observable<String> upDataObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL + CS_ins_CJRENWUMXPDA)
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
                                if ("00".equals(resultEntity.getMsgCode())) {
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
                            //失败回调
                            LogUtils.e("----------getNBModuleDZWFGDInfo：" + e.getCode() + "---"
                                    + e.getMessage());
                            ToastUtils.showLong(e.getCode() + "---"
                                    + e.getMessage());
                            if (isSuccess) {
                                //图片上传成功，数据上传失败
                                ToastUtils.showLong("图片上传成功，" + e.getCode() + "---"
                                        + e.getMessage());
//            GreenDaoUtils.getInstance().getDaoSession(BaseApplication.getInstance())
//              .getCallForPaymentBackFillDataBeanDao().deleteInTx(bean);
                            } else {
                                ToastUtils.showLong(e.getCode() + "---"
                                        + e.getMessage());
                            }
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
//                                listener.getResult(resultEntity.getMsgInfo());
                                responce = "success";
                                BusUtils.post("bus", responce);
                                //删除回填数据
                                GreenDaoUtils.getInstance().getDaoSession(context)
                                        .getCallForPaymentBackFillDataBeanDao().deleteInTx(bean);
                                //删除上传成功的工单数据
                                List<CuijiaoEntity> list = GreenDaoUtils.getInstance().getDaoSession(context).getCuijiaoEntityDao()
                                        .queryBuilder().where(CuijiaoEntityDao.Properties.S_RENWUID.eq(bean.getV_RENWUID())).list();
                                GreenDaoUtils.getInstance().getDaoSession(context).getCuijiaoEntityDao()
                                        .deleteInTx(list);
                            } else {
                                //图片上传成功，数据上传失败
                                ToastUtils.showLong("图片上传成功，" + resultEntity.getMsgInfo());
                            }
                        }
                    });
        }
        return responce;
    }


}
