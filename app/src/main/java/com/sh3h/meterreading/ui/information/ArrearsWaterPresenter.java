package com.sh3h.meterreading.ui.information;

import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUQianFeiXX;
import com.sh3h.datautil.data.entity.DUQianFeiXXInfo;
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
public class ArrearsWaterPresenter extends ParentPresenter<ArrearsWaterMvpView> {

    private static final String TAG = "ArrearsWaterPresenter";

    @Inject
    public ArrearsWaterPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getQianFeiXXs(DUQianFeiXXInfo duQianFeiXXInfo , boolean isLocal, Context context) {
        if (!isLocal && !NetworkUtil.isNetworkConnected(context)) {
            getMvpView().onError(context.getString(R.string.text_network_not_connected));
//            ApplicationsUtil.showMessage(context, R.string.text_network_not_connected);
            return;
        }

        mSubscription.add(mDataManager.getQianFeiXXs(duQianFeiXXInfo, isLocal)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<DUQianFeiXX>>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i(TAG, "---getQianFeiXXs onCompleted---");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i(TAG, String.format("---getQianFeiXXs onError: %s---", e.getMessage()));
                                getMvpView().onError(e.getMessage());
                            }

                            @Override
                            public void onNext(List<DUQianFeiXX> duQianFeiXXList) {
                                LogUtil.i(TAG, "---getQianFeiXXs onNext---");
                                getMvpView().onGetQianFeiXXs(duQianFeiXXList);
                            }
                        })
        );
    }

}
