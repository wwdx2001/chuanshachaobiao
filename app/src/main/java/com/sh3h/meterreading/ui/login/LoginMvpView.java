package com.sh3h.meterreading.ui.login;


import com.sh3h.meterreading.ui.base.MvpView;

public interface LoginMvpView extends MvpView {
    enum ErrorCode {
        NO_NETWORK_NO_ACCOUNT,
        NO_NETWORK_ACCOUNT_PASSWORD_ERROR,
        USER_SESSION_SAVING_ERROR,
        LOGIN_FAILURE,
        CHANGE_NETWORK_FAILURE,
        FIRST_TIME_FAILURE_AND_NO_LOCAL_ACCOUNT,
        FIRST_TIME_FAILURE_AND_LOCAL_ACCOUNT_ERROR
    }

    void updateUserInfo(String account, String password);
    void onCompleted();
    void onError(String message);
    void onError(ErrorCode errorCode);
    void onLoginWithoutNetwork(boolean isFirstTimeFailure);
    void onSaveNetwork();
}
