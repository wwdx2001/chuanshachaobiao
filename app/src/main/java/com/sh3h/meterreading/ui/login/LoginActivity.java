package com.sh3h.meterreading.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.main.MainActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.meterreading.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity
        implements LoginMvpView, View.OnClickListener {
    @Inject
    LoginPresenter mLoginPresenter;

    @Inject
    ConfigHelper mConfigHelper;

    @Bind(R.id.et_username)
    EditText mUserNameEditText;

    @Bind(R.id.et_password)
    EditText mPasswordEditText;

    @Bind(R.id.btn_submit)
    Button mSubmitButton;

    @Bind(R.id.tv_version)
    TextView mVersionTextView;

    private String TAG = "LoginActivity";
    private boolean isExit;

    private String baseUri;
    private String reservedBaseUri;
    private String fileUrl;
    private String reservedFileUrl;
    private boolean isReservedAddress;
    private String mapUrl;//地图
    private EditText baseUriTextView;
    private EditText reservedBaseUriTextView;
    private EditText fileUrlTextView;
    private EditText reservedFileUrlTextView;
    private CheckBox reservedAddressCheckBox;
    private EditText arcgisUrlTextView;//地图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        mSubmitButton.setOnClickListener(this);
        String version = SystemUtil.getVersionName(MainApplication.get(this));
        if (!TextUtil.isNullOrEmpty(version)) {
            mVersionTextView.setText(String.format("Ver: %s", version));
        }

        isExit = false;
        mLoginPresenter.attachView(this);
        mLoginPresenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        mLoginPresenter.detachView();

        if (isExit) {
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String account = mUserNameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if (TextUtil.isNullOrEmpty(account)) {
                    ApplicationsUtil.showMessage(this, R.string.text_username_not_null);
                    return;
                }

                if (TextUtil.isNullOrEmpty(password)) {
                    ApplicationsUtil.showMessage(this, R.string.text_password_not_null);
                    return;
                }

                if (popupNetWorkDialog(account, password)) {
                    return;
                }

                showProgress(R.string.dialog_login);
                mLoginPresenter.login(account, password,
                        NetworkUtil.isNetworkConnected(MainApplication.get(this)));
                break;
        }
    }

    @Override
    public void updateUserInfo(String account, String password) {
        if ((!TextUtil.isNullOrEmpty(account)) && (!TextUtil.isNullOrEmpty(password))) {
            mUserNameEditText.setText(account);
            mUserNameEditText.setSelection(account.length());
            mPasswordEditText.setText(password);
        }
    }

    @Override
    public void onCompleted() {
        hideProgress();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ConstDataUtil.FROM_LOGIN, true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String message) {
        hideProgress();
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    public void onError(ErrorCode errorCode) {
        hideProgress();

        int resId = 0;
        switch (errorCode) {
            case NO_NETWORK_NO_ACCOUNT:
                resId = R.string.error_account_not_exists;
                break;
            case NO_NETWORK_ACCOUNT_PASSWORD_ERROR:
                resId = R.string.text_no_network_account_password_error;
                break;
            case USER_SESSION_SAVING_ERROR:
                resId = R.string.text_saving_failure;
                break;
            case LOGIN_FAILURE:
                resId = R.string.text_failure_to_login;
                break;
            case CHANGE_NETWORK_FAILURE:
                resId = R.string.text_saving_failure;
                break;
            case FIRST_TIME_FAILURE_AND_NO_LOCAL_ACCOUNT:
                resId = R.string.text_first_time_failure_and_no_local_account;
                break;
            case FIRST_TIME_FAILURE_AND_LOCAL_ACCOUNT_ERROR:
                resId = R.string.text_first_time_failure_and_local_account_error;
                break;
            default:
                return;
        }

        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onLoginWithoutNetwork(boolean isFirstTimeFailure) {
        if (isFirstTimeFailure) {
            ApplicationsUtil.showMessage(this, R.string.text_first_time_failure_and_login_offline);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_login_offline);
        }
    }

    @Override
    public void onSaveNetwork() {
        isExit = true;
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

    private boolean popupNetWorkDialog(String account, String password) {
        if (account.equals("sh3h") && password.equals("sh3h")) {
            baseUri = TextUtil.getString(mConfigHelper.getBaseUri());
            reservedBaseUri = TextUtil.getString(mConfigHelper.getReservedBaseUri());
            fileUrl = TextUtil.getString(mConfigHelper.getFileUrl());
            reservedFileUrl = TextUtil.getString(mConfigHelper.getFileReservedUrl());
            isReservedAddress = mConfigHelper.isReservedAddress();
            mapUrl = TextUtil.getString(mConfigHelper.getMapServerUrl());

            baseUriTextView = null;
            reservedBaseUriTextView = null;
            reservedAddressCheckBox = null;
            arcgisUrlTextView = null;
            boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.text_setting_network)
                    .customView(R.layout.dialog_setting_network, true)
                    .positiveText(R.string.text_ok)
                    .negativeText(android.R.string.cancel)
                    .buttonsGravity(leftOrRight)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if ((baseUriTextView != null)
                                    && (reservedBaseUriTextView != null)
                                    && (fileUrlTextView != null)
                                    && (reservedFileUrlTextView != null)
                                    && (arcgisUrlTextView != null)) {
                                mLoginPresenter.saveNetwork(baseUriTextView.getText().toString(),
                                        reservedBaseUriTextView.getText().toString(),
                                        fileUrlTextView.getText().toString(),
                                        reservedFileUrlTextView.getText().toString(),
                                        reservedAddressCheckBox.isChecked(),
                                        arcgisUrlTextView.getText().toString());
                            }
                        }
                    }).build();
            if (dialog.getCustomView() != null) {
                baseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_data_address);
                reservedBaseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_data_address_reserved);
                fileUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_file_address);
                reservedFileUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_file_address_reserved);
                reservedAddressCheckBox = (CheckBox) dialog.getCustomView().findViewById(R.id.cb_is_reserved_address);
                arcgisUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_arcgis_map_address);
                baseUriTextView.setText(baseUri);
                baseUriTextView.setSelection(baseUri.length());
                reservedBaseUriTextView.setText(reservedBaseUri);
                reservedBaseUriTextView.setSelection(reservedBaseUri.length());
                fileUrlTextView.setText(fileUrl);
                fileUrlTextView.setSelection(fileUrl.length());
                reservedFileUrlTextView.setText(reservedFileUrl);
                reservedFileUrlTextView.setSelection(reservedFileUrl.length());
                reservedAddressCheckBox.setChecked(isReservedAddress);
                //地图服务地址
                arcgisUrlTextView.setText(mapUrl);
                arcgisUrlTextView.setSelection(mapUrl.length());
            }

            dialog.showLeftOrRight(leftOrRight);
            dialog.show();
            return true;
        }

        return false;
    }
}
