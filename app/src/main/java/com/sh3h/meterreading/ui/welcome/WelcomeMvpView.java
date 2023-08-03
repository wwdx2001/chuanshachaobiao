package com.sh3h.meterreading.ui.welcome;


import com.sh3h.meterreading.ui.base.MvpView;

public interface WelcomeMvpView extends MvpView {
    public enum Operation {
        INIT,
        AUTHORIZE
    }

    void showProgress(int length);
    void onError(Operation operation, String message);
    void onFinished(Operation operation);
}
