package com.sh3h.meterreading.ui.information;

import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUJiaoFeiXX;
import com.sh3h.datautil.data.entity.DUJiaoFeiXXInfo;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liurui on 2016/2/18.
 */
public class PayWaterPresenter extends ParentPresenter<PayWaterMvpView> {
    private static final String TAG = "PayWaterPresenter";

    @Inject
    public PayWaterPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getJiaoFeiXXs(DUJiaoFeiXXInfo duJiaoFeiXXInfo, boolean isLocal, Context context){
        if (!isLocal && !NetworkUtil.isNetworkConnected(context)) {
            getMvpView().onError(context.getString(R.string.text_network_not_connected));
            return;
        }
        mSubscription.add(mDataManager.getJiaoFeiXXs(duJiaoFeiXXInfo, isLocal)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<DUJiaoFeiXX>>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i(TAG, "---getJiaoFeiXXs onCompleted---");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i(TAG, String.format("---getJiaoFeiXXs onError: %s---", e.getMessage()));
                                getMvpView().onError(e.getMessage());
                            }

                            @Override
                            public void onNext(List<DUJiaoFeiXX> duJiaoFeiXXes) {
                                LogUtil.i(TAG, "---getJiaoFeiXXs onNext---");
                                getMvpView().onGetJiaoFeiXXs(duJiaoFeiXXes);
                            }
                        })
        );
    }
}
