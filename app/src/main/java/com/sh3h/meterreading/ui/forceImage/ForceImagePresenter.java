package com.sh3h.meterreading.ui.forceImage;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiMeng on 2017/9/25.
 */

public class ForceImagePresenter extends ParentPresenter<ForceImageMvpView>{
    private static final String TAG = "ForceImagePresenter";
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;

    @Inject
    public ForceImagePresenter(DataManager dataManager,ConfigHelper configHelper, PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public String getForceImage(){
        return "1";
    }

    public void loadAllChaobiaoZTFL() {
        LogUtil.i(TAG, "---loadAllChaobiaoZTFL start---");
        mSubscription.add(mDataManager.getChaoBiaoZTFLList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUChaoBiaoZTFL>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadAllChaobiaoZTFL onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadAllChaobiaoZTFL onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUChaoBiaoZTFL> duChaoBiaoZTFLList) {
                        LogUtil.i(TAG, "---loadAllChaobiaoZTFL: loadAllChaobiaoZTFL---");
                        getMvpView().loadAllChaobiaoZTFL(duChaoBiaoZTFLList);
                    }
                })
        );
    }

    public void loadAllChaobiaoZT() {
        LogUtil.i(TAG, "---loadAllChaobiaoZT start---");
        mSubscription.add(mDataManager.getChaoBiaoZTList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUChaoBiaoZT>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadAllChaobiaoZT onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadAllChaobiaoZT onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUChaoBiaoZT> duChaoBiaoZTList) {
                        LogUtil.i(TAG, "---loadAllChaobiaoZT: onNext---");
                        getMvpView().loadAllChaobiaozt(duChaoBiaoZTList);
                    }
                })
        );
    }

}
