package com.sh3h.meterreading.ui.login;


import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DULoginInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends ParentPresenter<LoginMvpView> {
    private static final String TAG = "LoginPresenter";
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;

    @Inject
    public LoginPresenter(DataManager dataManager,
                          PreferencesHelper preferencesHelper,
                          ConfigHelper configHelper) {
        super(dataManager);
        mPreferencesHelper = preferencesHelper;
        mConfigHelper = configHelper;
    }

    public void init() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        getMvpView().updateUserInfo(userSession.getAccount(), userSession.get_password());
        LogUtil.i(TAG, String.format("---account---%s",
                TextUtil.isNullOrEmpty(userSession.getAccount()) ? "null" : userSession.getAccount()));
    }

    public void login(final String account, final String password, boolean isNetworkConnected) {
        if (!isNetworkConnected) {
            loginWithoutNetwork(account, password, false);
            return;
        }

        DULoginInfo duLoginInfo = new DULoginInfo(account, password);
        mSubscription.add(mDataManager.login(duLoginInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---login onCompleted---");
                        //getMvpView().onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---login onError: %s---", e.getMessage()));
                        //getMvpView().onError(e.getMessage());
                        loginWithoutNetwork(account, password, true);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, String.format("---login onNext: %s---", aBoolean ? "true" : "false"));
                        if (aBoolean) {
                            initUserConfig();
                        } else {
                            getMvpView().onError(LoginMvpView.ErrorCode.LOGIN_FAILURE);
                        }
                    }
                }));
    }

    private void loginWithoutNetwork(String account, String password, boolean isFirstTimeFailure) {
        UserSession userSession = mPreferencesHelper.getUserSession();
        if (TextUtil.isNullOrEmpty(userSession.getAccount())
                || TextUtil.isNullOrEmpty(userSession.get_password())) {
            if (isFirstTimeFailure) {
                getMvpView().onError(LoginMvpView.ErrorCode.FIRST_TIME_FAILURE_AND_NO_LOCAL_ACCOUNT);
            } else {
                getMvpView().onError(LoginMvpView.ErrorCode.NO_NETWORK_NO_ACCOUNT);
            }
        } else {
            if (userSession.getAccount().equals(account)
                    && userSession.get_password().equals(password)) {
                getMvpView().onLoginWithoutNetwork(isFirstTimeFailure);
                initUserConfig();
            } else {
                if (isFirstTimeFailure) {
                    getMvpView().onError(LoginMvpView.ErrorCode.FIRST_TIME_FAILURE_AND_LOCAL_ACCOUNT_ERROR);
                } else {
                    getMvpView().onError(LoginMvpView.ErrorCode.NO_NETWORK_ACCOUNT_PASSWORD_ERROR);
                }
            }
        }
    }

    private void initUserConfig() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        mSubscription.add(mConfigHelper.initUserConfig(userSession.getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---initUserConfig onCompleted---");
                        getMvpView().onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---initUserConfig onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        LogUtil.i(TAG, String.format("---initUserConfig onNext---"));
                    }
                }));
    }

    public void saveNetwork(String baseUri,
                            String reservedBaseUri,
                            String fileUrl,
                            String reservedFileUrl,
                            boolean isReservedAddress,
                            String mapUrl) {
        if ((TextUtil.isNullOrEmpty(baseUri)
                || TextUtil.isNullOrEmpty(reservedBaseUri))
                || TextUtil.isNullOrEmpty(mapUrl)) {
            getMvpView().onError(LoginMvpView.ErrorCode.CHANGE_NETWORK_FAILURE);
            return;
        }

        LogUtil.i(TAG, String.format("---saveNetWork---baseuri: %s--reservedBaseUri:%s", baseUri, reservedBaseUri));
        mSubscription.add(mConfigHelper.saveNetWorkSetting(baseUri, reservedBaseUri, fileUrl,
                reservedFileUrl, isReservedAddress, mapUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveNetWork onCompleted---");
                        getMvpView().onSaveNetwork();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveNetWork onError---" + e.getMessage());
                        getMvpView().onError(LoginMvpView.ErrorCode.CHANGE_NETWORK_FAILURE);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveNetWork onNext---");
                    }
                }));
    }
}
