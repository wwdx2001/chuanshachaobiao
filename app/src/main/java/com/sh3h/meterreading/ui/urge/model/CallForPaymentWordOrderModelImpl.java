package com.sh3h.meterreading.ui.urge.model;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.example.dataprovider3.greendaoDao.CuijiaoEntityDao;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentWordOrderContract;
import com.sh3h.meterreading.ui.urge.listener.OnWorkOrderListener;
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

public class CallForPaymentWordOrderModelImpl implements CallForPaymentWordOrderContract.Model {

    private final String TAG = "CallForPaymentWordOrderModelImpl";

    @SuppressLint("LongLogTag")
    @Override
    public void getWorkOrderList(String s_ch, OnWorkOrderListener listener) {
        if (NetworkStatusUtil.isNetworkJudgment(MainApplication.getInstance())) {
            String account = SPUtils.getInstance().getString(Const.S_YUANGONGH);

            EasyHttp.post(URL.BASE_URGE_URL1 + URL.CS_SEL_CJCHSCIDCXPDA)
                    .params("CuiJiaoR", "0018")
                    .params("s_ch", s_ch)
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
                                listener.getData(data);
                                saveData(data, s_ch);
                            } else {
                                listener.onFail(resultBean.getMsgInfo());
                            }
                        }
                    });
        } else {
            List<CuijiaoEntity> data = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getCuijiaoEntityDao()
                    .queryBuilder().where(CuijiaoEntityDao.Properties.S_CH.eq(s_ch)).list();
            if (data != null && data.size() > 0) {
                listener.getData(data);
            } else {
                listener.onFail(null);
            }
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
}
