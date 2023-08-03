package com.sh3h.meterreading.ui.information;

import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUHuanBiaoJL;
import com.sh3h.datautil.data.entity.DUHuanBiaoJLInfo;
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
public class ChangeWaterPresenter extends ParentPresenter<ChangeWaterMvpView> {

    private static final String TAG = "ChangeWaterPresenter";

    @Inject
    public ChangeWaterPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getHuanBiaoXXs(DUHuanBiaoJLInfo duHuanBiaoJLInfo,boolean isLocal, Context context){
        if (!isLocal && !NetworkUtil.isNetworkConnected(context)) {
            getMvpView().onError(context.getString(R.string.text_network_not_connected));
            return;
        }
        mSubscription.add(mDataManager.getHuanBiaoXXs(duHuanBiaoJLInfo, isLocal)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<DUHuanBiaoJL>>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i(TAG, "---getHuanBiaoXXs onCompleted---");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i(TAG, String.format("---getHuanBiaoXXs onError: %s---", e.getMessage()));
                                getMvpView().onError(e.getMessage());
                            }

                            @Override
                            public void onNext(List<DUHuanBiaoJL> duChaoBiaoJLs) {
                                LogUtil.i(TAG, "---getHuanBiaoXXs onNext---");
                                getMvpView().onGetHuanBiaoXXs(duChaoBiaoJLs);
                            }
                        })
        );
    }
}
