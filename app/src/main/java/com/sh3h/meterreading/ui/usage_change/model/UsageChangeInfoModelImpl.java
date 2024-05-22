package com.sh3h.meterreading.ui.usage_change.model;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.UsageChangeInfoPriceEntity;
import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.ui.usage_change.contract.UsageChangeInfoContract;
import com.sh3h.meterreading.ui.usage_change.listener.UsageChangePriceDataListener;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.GsonUtils;
import com.sh3h.serverprovider.entity.ResultBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UsageChangeInfoModelImpl implements UsageChangeInfoContract.Model {

    @Override
    public void getPriceChagnesData(String jh, UsageChangePriceDataListener listener) {
        EasyHttp.post(URL.BASE_URGE_URL1 + URL.GetJiaGeMX)
                .params("S_JH", jh)
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
                            Type type = new TypeToken<ArrayList<UsageChangeInfoPriceEntity>>() {
                            }.getType();
                            ArrayList<UsageChangeInfoPriceEntity> data = gson.fromJson(gson.toJson(resultBean.getData()), type);
                            listener.getPriceChangesListener(data);
                        } else {
                            listener.onFail(resultBean.getMsgInfo());
                        }
                    }
                });
    }
}
