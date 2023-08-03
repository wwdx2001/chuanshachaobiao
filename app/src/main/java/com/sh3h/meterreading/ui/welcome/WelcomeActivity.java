package com.sh3h.meterreading.ui.welcome;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.login.LoginActivity;
import com.sh3h.meterreading.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView {
    @Inject
    WelcomePresenter mWelcomePresenter;

    @Bind(R.id.tv_state)
    TextView mStateTextView;

    @Bind(R.id.pb_loading)
    ProgressBar mProgressBar;

    @Bind(R.id.tv_version)
    TextView mVersionTextView;

    private boolean isExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        String version = SystemUtil.getVersionName(MainApplication.get(this));
        if (!TextUtil.isNullOrEmpty(version)) {
            mVersionTextView.setText(version);
        }

        isExit = false;
        mWelcomePresenter.attachView(this);
        mWelcomePresenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        mWelcomePresenter.detachView();

        if (isExit) {
            System.exit(0);
        }
    }

    @Override
    public void showProgress(int length) {

    }

    @Override
    public void onError(Operation operation, String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }

        isExit = true;
        finish();
    }

    @Override
    public void onFinished(Operation operation) {
        switch (operation) {
            case INIT:
                authorize();
                break;
            case AUTHORIZE:
                jumpActivity();
                break;
        }
    }

    private void authorize() {
        mWelcomePresenter.authorize(MainApplication.get(this));
    }

    private void jumpActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            isExit = true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
