package com.sh3h.meterreading.ui.urge.model;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentSearchBean;
import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentSearchListContract;
import com.sh3h.meterreading.ui.urge.listener.OnSearchListListener;
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
                        e.getCause().printStackTrace();
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
        if (bean.size() == 0) {
            listener.onFail("最少选中一项");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < bean.size(); i++) {
            if (bean.get(i).isCheck()) {
                stringBuilder.append(",").append(bean.get(i).getS_CID());
            }
        }
        String ids = stringBuilder.toString().substring(1);

        EasyHttp.post(URL.BASE_URGE_URL1 + URL.CreateCuiJiaoGD)
                .params("Cids", ids)
                .params("cby", "0018")
//                .params("cby", SPUtils.getInstance().getString(Const.S_YUANGONGH))
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
                        listener.onFail("提交成功");
                    }
                });
    }
}
