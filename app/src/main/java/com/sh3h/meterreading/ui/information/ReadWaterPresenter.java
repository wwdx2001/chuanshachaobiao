package com.sh3h.meterreading.ui.information;

import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUChaoBiaoJL;
import com.sh3h.datautil.data.entity.DUChaoBiaoJLInfo;
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
public class ReadWaterPresenter extends ParentPresenter<ReadWaterMvpView> {
    private static final String TAG = "ReadWaterPresenter";

    @Inject
    public ReadWaterPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getChaoBiaoJL(DUChaoBiaoJLInfo duChaoBiaoJLInfo, boolean isLocal, Context context){
        if (!isLocal && !NetworkUtil.isNetworkConnected(context)) {
            getMvpView().onError(context.getString(R.string.text_network_not_connected));
            return;
        }
        mSubscription.add(mDataManager.getChaoBiaoJLs(duChaoBiaoJLInfo, isLocal)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<DUChaoBiaoJL>>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i(TAG, "---getChaoBiaoJL onCompleted---");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i(TAG, String.format("---getChaoBiaoJL onError: %s---", e.getMessage()));
                                getMvpView().onError(e.getMessage());
                            }

                            @Override
                            public void onNext(List<DUChaoBiaoJL> duChaoBiaoJLs) {
                                LogUtil.i(TAG, "---getChaoBiaoJL onNext---");
                                getMvpView().onGetChaoBiaoJLFinish(duChaoBiaoJLs);
                            }
                        })
        );
    }
}
