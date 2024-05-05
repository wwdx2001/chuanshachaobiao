package com.sh3h.meterreading.ui.urge.model;

import android.annotation.SuppressLint;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentSearchBean;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentSearchListContract;
import com.sh3h.meterreading.ui.urge.listener.OnSearchListListener;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.GsonUtils;
import com.sh3h.serverprovider.entity.ResultBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CallForPaymentSearchListModelImpl implements CallForPaymentSearchListContract.Model{
    private final String TAG = "CallForPaymentSearchListModelImpl";

    @Override
    public void getData(String cid, String address, OnSearchListListener listener) {
        EasyHttp.post(URL.BASE_URGE_URL1 + URL.GetQianFeiCuiJiaoHZ)
                .params("cid", cid)
                .params("address", address)
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
                            Type type = new TypeToken<ArrayList<CallForPaymentSearchBean>>() {
                            }.getType();
                            ArrayList<CallForPaymentSearchBean> data = gson.fromJson(gson.toJson(resultBean.getData()), type);
                            listener.getDataListener(data);
                        } else {
                            listener.onFail(resultBean.getMsgInfo());
                        }
                    }
                });
    }

    @SuppressLint("LongLogTag")
    @Override
    public void submitData(List<CallForPaymentSearchBean> bean, OnSearchListListener listener) {
        JsonArray jsonArray = new JsonArray();
        for (CallForPaymentSearchBean searchBean : bean) {
            if (searchBean.isCheck()) {
                jsonArray.add(searchBean.getS_CID());
            }
        }

        if (jsonArray.size() == 0) {
            return;
        }

        EasyHttp.post(URL.BASE_URGE_URL1 + URL.CreateCuiJiaoGD)
                .params("Cids", jsonArray.toString())
                .params("cby", "0018")
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
                        listener.onFail(resultBean.getMsgInfo());
                    }
                });
    }
}
