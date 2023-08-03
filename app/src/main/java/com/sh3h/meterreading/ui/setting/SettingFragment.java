package com.sh3h.meterreading.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.datautil.data.entity.DUUploadingFileResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.forceImage.ForceImageActivity;
import com.sh3h.meterreading.ui.main.MainActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liurui on 2016/1/27.
 */
public class SettingFragment extends ParentFragment implements SettingMvpView,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "SettingFragment";
    private static final String POSITIVE = "POSITIVE";
    private static final String NEGATIVE = "NEGATIVE";

    @Inject
    SettingPresenter mSettingPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mEventBus;

    @Inject
    ConfigHelper mConfigHelper;

    @Bind(R.id.fs_cv_style)
    CardView mStyleCardView;

    @Bind(R.id.fs_cv_image_quality)
    CardView mQualityCardView;

    @Bind(R.id.fs_cv_reading_setting)
    CardView mReadingCardView;

    @Bind(R.id.fs_cv_image_setting)
    CardView mImageCardView;

    @Bind(R.id.fs_cv_data_sync)
    CardView mSyncCardView;

    @Bind(R.id.fs_cv_log_out)
    CardView mLogoutCardView;

    @Bind(R.id.fs_cv_clearing_history)
    CardView mClearingCardView;

    @Bind(R.id.fs_cv_restoring_factory)
    CardView mRestoringCardView;

    @Bind(R.id.fs_cv_version)
    CardView mVersionCardView;

    @Bind(R.id.fs_sw_style_r)
    Switch mStyleSwitch;

    @Bind(R.id.fs_tv_style_r)
    TextView mStyleTextView;

    @Bind(R.id.fs_sw_quality_r)
    Switch mQualitySwitch;

    @Bind(R.id.fs_tv_quality_r)
    TextView mQualityTextView;

    @Bind(R.id.fs_tv_version_r)
    TextView mVersionTextView;

    @Bind(R.id.fs_cv_network)
    CardView mNetworkCardView;

    @Bind(R.id.fs_cv_period)
    CardView mPeriodCardView;

    @Bind(R.id.fs_cv_update)
    CardView mUpdateCardView;

    @Bind(R.id.fs_cv_singledata_upload)
    CardView mSingleUploadCardView;

    @Bind(R.id.fs_cv_afterceben_upload)
    CardView mAfterCBUploadCardView;

    @Bind(R.id.fs_cv_upload_all)
    CardView mUploadAllCardView;

    @Bind(R.id.fs_cv_download_all)
    CardView mDownloadAllCardView;

    @Bind(R.id.fs_cv_leftorright)
    CardView mLeftOrRightCardView;

    @Bind(R.id.fs_cv_upload_file)
    CardView mUploadFileCardView;

    @Bind(R.id.fs_tv_update_r)
    TextView mUpdateTextView;

    @Bind(R.id.fs_tv_singledata_upload)
    TextView mSingleUploadTextView;

    @Bind(R.id.fs_tv_afterceben_upload)
    TextView mAfterCBUploadTextView;

    @Bind(R.id.fs_tv_upload_all)
    TextView mUploadAllTextView;

    @Bind(R.id.fs_tv_download_all)
    TextView mDownloadAllTextView;

    @Bind(R.id.fs_tv_data_sync)
    TextView mSyncDataTextView;

    @Bind(R.id.fs_tv_leftorright)
    TextView mLeftOrRightTextView;

    @Bind(R.id.fs_sw_update_r)
    Switch mUpdateSwitch;

    @Bind(R.id.fs_sw_singledata_upload)
    Switch mSingleUpdateSwitch;

    @Bind(R.id.fs_sw_afterceben_upload)
    Switch mAfterCBUploadSwitch;

    @Bind(R.id.fs_sw_upload_all)
    Switch mUploadAllSwitch;

    @Bind(R.id.fs_sw_download_all)
    Switch mDownAllSwitch;

    @Bind(R.id.fs_sw_data_sync)
    Switch mSyneDataSwitch;

    @Bind(R.id.fs_sw_leftorright)
    Switch mLeftOrRightSwitch;

    private String baseURI;

    private String reservedBaseURI;

    private String fileUrl;

    private String reservedFileUrl;

    private boolean isReservedAddress;

    //地图服务地址
    private String arcgisMapURL;

    private EditText baseUriTextView;

    private EditText reservedBaseUriTextView;

    private EditText fileUrlTextView;

    private EditText reservedFileUrlTextView;

    private CheckBox reservedAddressCheckBox;

    //地图服务地址
    private EditText arcgisMapUrlTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        LogUtil.i(TAG, "---onCreate---" + this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "---onResume---");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i(TAG, "---onPause---");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mSettingPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        ButterKnife.bind(this, rootView);
        mEventBus.register(this);

        mStyleCardView.setOnClickListener(this);
        mQualityCardView.setOnClickListener(this);
        mSyncCardView.setOnClickListener(this);
        mLogoutCardView.setOnClickListener(this);
        mClearingCardView.setOnClickListener(this);
        mRestoringCardView.setOnClickListener(this);
        mVersionCardView.setOnClickListener(this);
        mStyleSwitch.setOnCheckedChangeListener(this);
        mQualitySwitch.setOnCheckedChangeListener(this);
        mReadingCardView.setOnClickListener(this);
        mImageCardView.setOnClickListener(this);
        mNetworkCardView.setOnClickListener(this);
        mPeriodCardView.setOnClickListener(this);
        mUpdateCardView.setOnClickListener(this);
        mSingleUploadCardView.setOnClickListener(this);
        mAfterCBUploadCardView.setOnClickListener(this);
        mUploadAllCardView.setOnClickListener(this);
        mDownloadAllCardView.setOnClickListener(this);
        mLeftOrRightCardView.setOnClickListener(this);
        mUploadFileCardView.setOnClickListener(this);
        mUpdateSwitch.setOnCheckedChangeListener(this);
        mSingleUpdateSwitch.setOnCheckedChangeListener(this);
        mAfterCBUploadSwitch.setOnCheckedChangeListener(this);
        mUploadAllSwitch.setOnCheckedChangeListener(this);
        mDownAllSwitch.setOnCheckedChangeListener(this);
        mSyneDataSwitch.setOnCheckedChangeListener(this);
        mLeftOrRightSwitch.setOnCheckedChangeListener(this);

        mSettingPresenter.attachView(this);
        mSettingPresenter.init(MainApplication.get(getActivity()));
        LogUtil.i(TAG, "---onCreateView---" + this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fs_cv_style:
                updateStyle(!mStyleSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_image_quality:
                updateQuality(!mQualitySwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_log_out:
                logout();
                break;
            case R.id.fs_cv_clearing_history:
                clearHistory();
                break;
            case R.id.fs_cv_restoring_factory:
                restoreFactory();
                break;
            case R.id.fs_cv_reading_setting:
                jump2UserCommonActivity();
                break;
            case R.id.fs_cv_image_setting:
                jump2ForceImageActivity();
                break;
            case R.id.fs_cv_network:
                setNetWork();
                break;
            case R.id.fs_cv_period:
                showPeriod();
                break;
            case R.id.fs_cv_update:
                updateUpdate(!mUpdateSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_data_sync:
                updateSyncData(!mSyneDataSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_singledata_upload:
                updateSingleData(!mSingleUpdateSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_afterceben_upload:
                updateAfterCBUpload(!mAfterCBUploadSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_upload_all:
                updateUploadAll(!mUploadAllSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_download_all:
                updateDownloadAll(!mDownAllSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_version:
//              checkVersion();
                break;
            case R.id.fs_cv_leftorright:
                updateLeftOrRightOperation(!mLeftOrRightSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_upload_file:
                uploadFile();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fs_sw_style_r:
                updateStyle(isChecked, false, true);
                break;
            case R.id.fs_sw_quality_r:
                updateQuality(isChecked, false, true);
                break;
            case R.id.fs_sw_update_r:
                updateUpdate(isChecked, false, true);
                break;
            case R.id.fs_sw_data_sync:
                updateSyncData(isChecked, false, true);
                break;
            case R.id.fs_sw_singledata_upload:
                updateSingleData(isChecked, false, true);
                break;
            case R.id.fs_sw_afterceben_upload:
                updateAfterCBUpload(isChecked, false, true);
                break;
            case R.id.fs_sw_upload_all:
                updateUploadAll(isChecked, false, true);
                break;
            case R.id.fs_sw_download_all:
                updateDownloadAll(isChecked, false, true);
                break;
            case R.id.fs_sw_leftorright:
                updateLeftOrRightOperation(isChecked, false, true);
                break;
        }
    }

    @Override
    public void initStyle(boolean isGrid) {
        updateStyle(isGrid, true, false);
    }

    private void updateStyle(boolean isGrid, boolean isChangingSwitch, boolean needSaving) {
        if (isGrid) {
            mStyleTextView.setText(R.string.text_system_style_grid);
            if (isChangingSwitch) {
                mStyleSwitch.setChecked(true);
            }
        } else {
            mStyleTextView.setText(R.string.text_system_style_list);
            if (isChangingSwitch) {
                mStyleSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveStyle(isGrid);
        }
    }

    @Override
    public void initQuality(boolean isNormal) {
        updateQuality(isNormal, true, false);
    }

    private void updateQuality(boolean isNormal, boolean isChangingSwitch, boolean needSaving) {
        if (isNormal) {
            mQualityTextView.setText(R.string.text_image_quality_normal);
            if (isChangingSwitch) {
                mQualitySwitch.setChecked(true);
            }
        } else {
            mQualityTextView.setText(R.string.text_image_quality_high);
            if (isChangingSwitch) {
                mQualitySwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveQuality(isNormal);
        }
    }

    @Override
    public void initVersion(String version) {
        if (!TextUtil.isNullOrEmpty(version)) {
            mVersionTextView.setText(version);
        }
    }

    @Override
    public void initUpdateVersion(boolean flag) {
        updateUpdate(flag, true, false);
    }

    @Override
    public void initSyncData(boolean flag) {
        updateSyncData(flag, true, false);
    }

    @Override
    public void initSingleUpload(boolean flag) {
        updateSingleData(flag, true, false);
    }

    @Override
    public void initUploadAfterCeben(boolean flag) {
        updateAfterCBUpload(flag, true, false);
    }

    @Override
    public void initUploadAll(boolean flag) {
        updateUploadAll(flag, true, false);
    }

    @Override
    public void initDownloadAll(boolean flag) {
        updateDownloadAll(flag, true, false);
    }

    @Override
    public void initLeftOrRightOperation(boolean isLeft) {
        updateLeftOrRightOperation(isLeft, true, false);
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(getActivity(), message);
        }
    }

    @Override
    public void showMessage(int message) {
        ApplicationsUtil.showMessage(getActivity(), message);
    }

    @Override
    public void exitSystem() {
        mEventPosterHelper.postEventSafely(new UIBusEvent.ExitingSystem());
    }

    public void onUploadFile(DUUploadingFileResult duUploadingFileResult) {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).hideProgress();
        }

        if (duUploadingFileResult.isSuccess()) {
            ApplicationsUtil.showMessage(getActivity(), R.string.text_upload_success);
        } else {
            ApplicationsUtil.showMessage(getActivity(), R.string.text_upload_failure);
        }
    }

    private void setNetWork() {
        baseURI = TextUtil.getString(mSettingPresenter.getBaseUri());
        reservedBaseURI = TextUtil.getString(mSettingPresenter.getReservedBaseUri());
        fileUrl = TextUtil.getString(mSettingPresenter.getFileUrl());
        reservedFileUrl = TextUtil.getString(mSettingPresenter.getReservedFileUrl());
        isReservedAddress = mConfigHelper.isReservedAddress();
        arcgisMapURL = TextUtil.getString(mSettingPresenter.getMapUrl());
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.text_setting_network)
                .customView(R.layout.dialog_setting_network, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .buttonsGravity(leftOrRight)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.saveNetWorkSetting(baseUriTextView.getText().toString(),
                                    reservedBaseUriTextView.getText().toString(),
                                    fileUrlTextView.getText().toString(),
                                    reservedFileUrlTextView.getText().toString(),
                                    reservedAddressCheckBox.isChecked(),
                                    arcgisMapUrlTextView.getText().toString());
                        }
                    }
                }).build();
        if (dialog.getCustomView() != null) {
            baseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_data_address);
            reservedBaseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_data_address_reserved);
            fileUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_file_address);
            reservedFileUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_file_address_reserved);
            reservedAddressCheckBox = (CheckBox) dialog.getCustomView().findViewById(R.id.cb_is_reserved_address);
            arcgisMapUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_arcgis_map_address);
            baseUriTextView.setText(baseURI);
            baseUriTextView.setSelection(baseURI.length());
            reservedBaseUriTextView.setText(reservedBaseURI);
            reservedBaseUriTextView.setSelection(reservedBaseURI.length());
            fileUrlTextView.setText(fileUrl);
            fileUrlTextView.setSelection(fileUrl.length());
            reservedFileUrlTextView.setText(reservedFileUrl);
            reservedFileUrlTextView.setSelection(reservedFileUrl.length());
            reservedAddressCheckBox.setChecked(isReservedAddress);
            //地图服务地址
            arcgisMapUrlTextView.setText(arcgisMapURL);
            arcgisMapUrlTextView.setSelection(arcgisMapURL.length());
        }

        dialog.showLeftOrRight(leftOrRight);
        dialog.show();
    }

    private void updateUpdate(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mUpdateTextView.setText(R.string.text_setting_automatic);
            if (isChangingSwitch) {
                mUpdateSwitch.setChecked(true);
            }
        } else {
            mUpdateTextView.setText(R.string.text_system_byhand);
            if (isChangingSwitch) {
                mUpdateSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveUpdateVersion(isChecked);
        }
    }

    private void updateSyncData(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mSyncDataTextView.setText(R.string.text_setting_automatic);
            if (isChangingSwitch) {
                mSyneDataSwitch.setChecked(true);
            }
        } else {
            mSyncDataTextView.setText(R.string.text_system_byhand);
            if (isChangingSwitch) {
                mSyneDataSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveSyncData(isChecked);
        }
    }

    private void updateSingleData(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mSingleUploadTextView.setText(R.string.text_yes);
            if (isChangingSwitch) {
                mSingleUpdateSwitch.setChecked(true);
            }
        } else {
            mSingleUploadTextView.setText(R.string.text_no);
            if (isChangingSwitch) {
                mSingleUpdateSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveSingleUpload(isChecked);
        }
    }

    private void updateAfterCBUpload(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mAfterCBUploadTextView.setText(R.string.text_yes);
            if (isChangingSwitch) {
                mAfterCBUploadSwitch.setChecked(true);
            }
        } else {
            mAfterCBUploadTextView.setText(R.string.text_no);
            if (isChangingSwitch) {
                mAfterCBUploadSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveAfterCeBenUpload(isChecked);
        }
    }

    private void updateUploadAll(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mUploadAllTextView.setText(R.string.text_yes);
            if (isChangingSwitch) {
                mUploadAllSwitch.setChecked(true);
            }
        } else {
            mUploadAllTextView.setText(R.string.text_no);
            if (isChangingSwitch) {
                mUploadAllSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveUploadAll(isChecked);
        }
    }

    private void updateLeftOrRightOperation(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mLeftOrRightTextView.setText(R.string.text_lefthand);
            if (isChangingSwitch) {
                mLeftOrRightSwitch.setChecked(true);
            }
        } else {
            mLeftOrRightTextView.setText(R.string.text_righthand);
            if (isChangingSwitch) {
                mLeftOrRightSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveLeftOrRightOperation(isChecked);
        }
    }

    private void updateDownloadAll(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mDownloadAllTextView.setText(R.string.text_yes);
            if (isChangingSwitch) {
                mDownAllSwitch.setChecked(true);
            }
        } else {
            mDownloadAllTextView.setText(R.string.text_no);
            if (isChangingSwitch) {
                mDownAllSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveDownloadAll(isChecked);
        }
    }

    @Override
    protected void lazyLoad() {

    }

    private void logout() {
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_prompt)
                .content(R.string.text_log_out_account)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(leftOrRight)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.logout();
                        }
                    }
                })
                .show(leftOrRight);
//        new ConfirmDialog(getActivity(),
//                ConfirmDialog.BUTTON_YES_NO,
//                R.string.text_prompt,
//                R.string.text_log_out_account,
//                new ConfirmDialog.OnConfirmListener() {
//                    @Override
//                    public void onResult(int result) {
//                        if (result == ConfirmDialog.OnConfirmListener.RESULT_YES) {
//                            mSettingPresenter.logout();
//                        }
//                    }
//                }).show();
    }

    private void clearHistory() {
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_prompt)
                .content(R.string.text_clearing_all)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(leftOrRight)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.clearHistory();
                        }
                    }
                })
                .show(leftOrRight);
//        new ConfirmDialog(getActivity(),
//                ConfirmDialog.BUTTON_YES_NO,
//                R.string.text_prompt,
//                R.string.text_clearing_all,
//                new ConfirmDialog.OnConfirmListener() {
//                    @Override
//                    public void onResult(int result) {
//                        if (result == ConfirmDialog.OnConfirmListener.RESULT_YES) {
//                            mSettingPresenter.clearHistory();
//                        }
//                    }
//                }).show();
    }

    private void restoreFactory() {
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_prompt)
                .content(R.string.text_restoring_all)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(leftOrRight)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.restoreFactory();
                        }
                    }
                })
                .show(leftOrRight);
//        new ConfirmDialog(getActivity(),
//                ConfirmDialog.BUTTON_YES_NO,
//                R.string.text_prompt,
//                R.string.text_restoring_all,
//                new ConfirmDialog.OnConfirmListener() {
//                    @Override
//                    public void onResult(int result) {
//                        if (result == ConfirmDialog.OnConfirmListener.RESULT_YES) {
//                            mSettingPresenter.restoreFactory();
//                        }
//                    }
//                }).show();
    }

    private void checkVersion() {
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_prompt)
                .content(R.string.text_checking_version)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(leftOrRight)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        LogUtil.i(TAG, "---checkVersion---ok");
                        Activity activity = getActivity();
                        if (activity instanceof MainActivity) {
                            ((MainActivity) activity).checkVersion();
                        }
                    }
                })
                .show(leftOrRight);
    }

    private void showPeriod() {
        String startDate = mSettingPresenter.getReadingDate(true);
        if (TextUtil.isNullOrEmpty(startDate)) {
            startDate = "-";
        }

        String endDate = mSettingPresenter.getReadingDate(false);
        if (TextUtil.isNullOrEmpty(endDate)) {
            endDate = "-";
        }

        String period = String.format(ConstDataUtil.LOCALE, "%s: %s%s%s",
                getActivity().getResources().getString(R.string.text_setting_period),
                startDate,
                getActivity().getResources().getString(R.string.text_to),
                endDate);
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_prompt)
                .content(period)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(leftOrRight)
                .show(leftOrRight);
    }

    private void uploadFile() {
        List<String> contentList = new ArrayList<>();
        //contentList.add(getString(R.string.text_upload_log));
        contentList.add(getString(R.string.text_upload_image));

        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        int selectedIndex = 0;
        final int UPLOAD_LOG_INDEX = 0;
        final int UPLOAD_IMAGE_INDEX = 1;
        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_prompt)
                .items(contentList)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(leftOrRight)
                .itemsCallbackSingleChoice(selectedIndex,
                        new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView,
                                                       int which, CharSequence text) {
                                /*if (which == UPLOAD_LOG_INDEX) {
                                    Activity activity = getActivity();
                                    if (activity instanceof BaseActivity) {
                                        ((BaseActivity) activity).showProgress(R.string.text_feedback_shuangchuan);
                                    }

                                    if (!NetworkUtil.isNetworkConnected(activity)) {
                                        ApplicationsUtil.showMessage(activity, R.string.text_network_not_connected);
                                        return true;
                                    }

                                    mSettingPresenter.uploadFile();
                                } else if (which == UPLOAD_IMAGE_INDEX) {*/
                                    if (getActivity() instanceof ParentActivity) {
                                        ((ParentActivity)getActivity()).setNeedDialog(true);
                                        ((ParentActivity)getActivity()).uploadAllMediaRepeatedly();
                                    }
                               // }
                                return true;
                            }
                        })
                .show(leftOrRight);
    }

    private void jump2UserCommonActivity() {
        Intent intent = new Intent(getActivity(), UserCommonActivity.class);
        getActivity().startActivity(intent);
    }

    private void jump2ForceImageActivity() {
        Intent intent = new Intent(getActivity(), ForceImageActivity.class);
        getActivity().startActivity(intent);
    }

    @Subscribe
    public void onVersionServiceEnd(UIBusEvent.VersionServiceEnd versionServiceEnd) {
        LogUtil.i(TAG, "---onVersionServiceEnd 1---");
//        if (!versionServiceEnd.isNewApk()) {
//            ApplicationsUtil.showMessage(getActivity(), R.string.text_is_new_version);
//        }
    }
}

