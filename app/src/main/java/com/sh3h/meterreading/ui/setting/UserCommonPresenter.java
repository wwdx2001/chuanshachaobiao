package com.sh3h.meterreading.ui.setting;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2016/2/25.
 */
public class UserCommonPresenter  extends ParentPresenter<UserCommonMvpView> {

    private static final String TAG = "UserCommonPresenter";
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;

    @Inject
    public UserCommonPresenter(DataManager dataManager,ConfigHelper configHelper, PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        this.mPreferencesHelper = preferencesHelper;
    }


    public String getUserChangYong(){
        return mConfigHelper.getUserChangYong();
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


    public void saveUserChangYong(String zhuangTaBM) {
        LogUtil.i(TAG, String.format("---saveUserChangYong---"));

        if (zhuangTaBM.trim().equals("")) {
            LogUtil.i(TAG, String.format("---saveUserChangYong fail---"));
            return;
        }

        mSubscription.add(mConfigHelper.saveUserChangYong(zhuangTaBM)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveStyle onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveStyle onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveStyle onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

}
