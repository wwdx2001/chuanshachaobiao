package com.sh3h.meterreading.ui.urge.model;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentArrearsFeesDetailBean;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentArrearsFeesDetailContract;
import com.sh3h.meterreading.ui.urge.listener.OnArrearsFeesDetailListener;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.GsonUtils;
import com.sh3h.mobileutil.util.NetworkStatusUtil;
import com.sh3h.serverprovider.entity.ResultBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CallForPaymentArrearsFeesDetailModelImpl implements CallForPaymentArrearsFeesDetailContract.Model {

    private final String TAG = "CallForPaymentArrearsFeesDetailModelImpl";

    @Override
    public void getArrearsFeesDetail(String renwuid, String s_cid, OnArrearsFeesDetailListener listener) {
        if (NetworkStatusUtil.isNetworkJudgment(MainApplication.getInstance())) {
            EasyHttp.post(URL.BASE_URGE_URL1 + URL.CS_SEL_CJSCIDZDMXPDA)
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
                                Type type = new TypeToken<ArrayList<CallForPaymentArrearsFeesDetailBean>>() {
                                }.getType();
                                ArrayList<CallForPaymentArrearsFeesDetailBean> data = gson.fromJson(gson.toJson(resultBean.getData()), type);
                                listener.getData(data);
                                saveData(renwuid, s_cid, data);
                            } else {
                                listener.onFail(resultBean.getMsgInfo());
                            }
                        }
                    });
        } else {
            List<CallForPaymentArrearsFeesDetailBean> data = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCallForPaymentArrearsFeesDetailBeanDao()
                    .queryBuilder().list();
            if (data != null && data.size() > 0) {
                listener.getData(data);
            } else {
                listener.onFail(null);
            }
        }


    }

    private void saveData(String renwuid, String s_cid, ArrayList<CallForPaymentArrearsFeesDetailBean> data) {
        for (CallForPaymentArrearsFeesDetailBean bean : data) {
            bean.setId((long) (s_cid + renwuid).hashCode());
            bean.setS_RENWUID(renwuid);
            bean.setS_CID(s_cid);
        }

        GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCallForPaymentArrearsFeesDetailBeanDao()
                .insertOrReplaceInTx(data);
    }
}
