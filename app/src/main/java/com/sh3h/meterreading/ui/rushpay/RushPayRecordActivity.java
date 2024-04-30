package com.sh3h.meterreading.ui.rushpay;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DURushPayTask;
import com.sh3h.datautil.data.entity.DURushPayTaskInfo;
import com.sh3h.datautil.data.entity.DURushPayTaskResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.images.view.PopupWindowMenu;
import com.sh3h.meterreading.service.SyncDataInfo;
import com.sh3h.meterreading.service.SyncSubDataInfo;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.information.CustomerInformationActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.widget.IBInvoke;
import com.sh3h.mobileutil.widget.PopupWindowImageAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RushPayRecordActivity extends ParentActivity implements
        View.OnClickListener, MenuItem.OnMenuItemClickListener, RushPayRecordMvpView {
    private static final String TAG = "RushPayRecordActivity";
    private static final int CAPTURE_IMAGE = 1000;

    @Inject
    Bus mEventBus;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    RushPayRecordPresenter mRushPayRecordPresenter;

    @BindView(R.id.inspect_record_lr)
    RadioButton mRecordLRRadioButton;

    @BindView(R.id.inspect_record_detail_info)
    RadioButton mDetailInfoRadioButton;

    @BindView(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    // 用户编号
    @BindView(R.id.fcblr_cardId)
    TextView mCardIdTextView;

    // 户名
    @BindView(R.id.fcblr_huming2)
    TextView mHMTextView;

    // 地址
    @BindView(R.id.fcblr_dizhi2)
    TextView mDZTextView;

    @BindView(R.id.fcblr_subcomcode2)
    TextView mSubComCodeTextView;

    @BindView(R.id.fcblr_qf_months2)
    TextView mMonthsTextView;

    @BindView(R.id.fcblr_qf_money2)
    TextView mMoneyTextView;

    @BindView(R.id.fcblr_beizhu2)
    TextView mRemarkTextView;

    @BindView(R.id.fcblr_previous)
    ButtonFloatSmall mPrevButton;

    @BindView(R.id.fcblr_paizhao)
    ButtonRectangle mPZButton;

    @BindView(R.id.fcblr_upload)
    ButtonRectangle mUploadingButton;

    @BindView(R.id.fcblr_next)
    ButtonFloatSmall mNextButton;

    @BindView(R.id.fcblr_flashlight)
    ButtonRectangle mFlashLightButton;

//    @BindView(R.id.fcblr_map_address)
//    ImageView mMapAddress;

    private List<DUMedia> mDuoMeiTXXList = null;
    private List<ImageView> mImageViewList = null;
    private List<String> mImgPathList = null;
    private int mTaskId;
    private String mCardId;
    private String mCardName;
    private String mCardAddress;
    private String mSubComCode;
    private int mQFMonths;
    private double mQFMoney;
    private String mRemark;
    private String mMeterReader;
    private int mIsComplete;
    private int mIsUpload;

    private EditText editText = null;
    private boolean isFlashLightOpen = false;
    private Camera camera;
    private String mFileName = null;
    private PopupWindowImageAdapter mAdapter = null;

    public RushPayRecordActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        int viewLayout = mConfigHelper.isLeftOrRightOperation() ?
                R.layout.activity_inspect_rush_pay_left : R.layout.activity_inspect_rush_pay_right;
        setContentView(viewLayout);
        ButterKnife.bind(this);
        setActionBarBackButtonEnable();
        mEventBus.register(this);
        mRushPayRecordPresenter.attachView(this);

        mDuoMeiTXXList = new ArrayList<>();
        mImgPathList = new ArrayList<>();

        getIntentData(savedInstanceState);
        initOnclickListener();
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mRushPayRecordPresenter.loadImageInfo(mTaskId, mCardId);
        LogUtil.i(TAG, "---onCreate---");
    }

    private void initTextView() {
        if (mCardIdTextView != null) {
            mCardIdTextView.setText(mCardId);
        }
        if (mHMTextView != null) {
            mHMTextView.setText(mCardName);
        }
        if (mDZTextView != null) {
            mDZTextView.setText(mCardAddress);
        }
        if (mSubComCodeTextView != null) {
            mSubComCodeTextView.setText(mSubComCode);
        }
        if (mMonthsTextView != null) {
            mMonthsTextView.setText(TextUtil.getString(String.valueOf(mQFMonths)));
        }
        if (mMoneyTextView != null) {
            mMoneyTextView.setText(TextUtil.getString(String.valueOf(mQFMoney)));
        }
        if (mRemarkTextView != null) {
            if (mRemark != null && !mRemark.trim().equals("")) {
                mRemarkTextView.setText(mRemark);
            }
        }

    }

    private void initOnclickListener() {
        mRemarkTextView.setOnClickListener(this);
        mRecordLRRadioButton.setOnClickListener(this);
        mDetailInfoRadioButton.setOnClickListener(this);
        mDZTextView.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPZButton.setOnClickListener(this);
        mUploadingButton.setOnClickListener(this);
        mFlashLightButton.setOnClickListener(this);
    }

    private void getIntentData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (savedInstanceState != null) {
            mTaskId = savedInstanceState.getInt(ConstDataUtil.I_TASKID);
            mCardId = savedInstanceState.getString(ConstDataUtil.S_CARDID);
            mCardName = savedInstanceState.getString(ConstDataUtil.S_CARDNAME);
            mCardAddress = savedInstanceState.getString(ConstDataUtil.S_CARDADDRESS);
            mSubComCode = savedInstanceState.getString(ConstDataUtil.S_SUBCOMCODE);
            mQFMonths = savedInstanceState.getInt(ConstDataUtil.I_QFMONTHS, 0);
            mQFMoney = savedInstanceState.getDouble(ConstDataUtil.I_QFMONEY, 0);
            mRemark = savedInstanceState.getString(ConstDataUtil.S_REMARK);
            mMeterReader = savedInstanceState.getString(ConstDataUtil.S_METERREADER);
            mIsComplete = savedInstanceState.getInt(ConstDataUtil.I_ISCOMPLETE);
            mIsUpload = savedInstanceState.getInt(ConstDataUtil.I_ISUPLOAD);
            LogUtil.i(TAG, "getIntentData savedInstanceState != null" + mCardId);
        } else if (intent != null) {
            mTaskId = intent.getIntExtra(ConstDataUtil.I_TASKID, 0);
            mCardId = intent.getStringExtra(ConstDataUtil.S_CARDID);
            mCardName = intent.getStringExtra(ConstDataUtil.S_CARDNAME);
            mCardAddress = intent.getStringExtra(ConstDataUtil.S_CARDADDRESS);
            mSubComCode = intent.getStringExtra(ConstDataUtil.S_SUBCOMCODE);
            mQFMonths = intent.getIntExtra(ConstDataUtil.I_QFMONTHS, 0);
            mQFMoney = intent.getDoubleExtra(ConstDataUtil.I_QFMONEY, 0);
            mRemark = intent.getStringExtra(ConstDataUtil.S_REMARK);
            mMeterReader = intent.getStringExtra(ConstDataUtil.S_METERREADER);
            mIsComplete = intent.getIntExtra(ConstDataUtil.I_ISCOMPLETE, 0);
            mIsUpload = intent.getIntExtra(ConstDataUtil.I_ISUPLOAD, 0);
            LogUtil.i(TAG, "getIntentData intent != null" + mTaskId);
        } else {
            LogUtil.i(TAG, "getIntentData error!!!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "---onDestroy---");

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mRushPayRecordPresenter.detachView();

        if (isFlashLightOpen) {
            camera.stopPreview(); // 关掉亮灯
            camera.release();// 关掉照相机
            camera = null;
            isFlashLightOpen = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_lr, menu);

        menu.findItem(R.id.action_image).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LogUtil.i(TAG, "---onCreateOptionsMenu 1---");
                loadImages();
                LogUtil.i(TAG, "---onCreateOptionsMenu 2---");
                return true;
            }
        });

        return true;
    }

    private void loadImages() {
        LogUtil.i(TAG, "---loadImages---");
        if ((mImageViewList != null)
                && (mImgPathList.size() == mImageViewList.size())) {
            displayImages();
        } else {
            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mRushPayRecordPresenter.loadImageViews(mImgPathList, this);
        }
    }

    private void displayImages() {
        mAdapter = new PopupWindowImageAdapter(mImageViewList);
        Window window = getWindow();
        PopupWindowMenu popupWindowMenuNJXX = new PopupWindowMenu(this, window);
        popupWindowMenuNJXX.popupWindowImageViwe(mRemarkTextView,
                PopupWindowMenu.ATLOCATION_TOP, AbsListView.LayoutParams.FILL_PARENT,
                AbsListView.LayoutParams.FILL_PARENT, "图片", mAdapter,
                mConfigHelper.getImageFolderPath(),
                "RushPay", mDuoMeiTXXList, new IBInvoke() {
                    @Override
                    public void before() {
                    }

                    @Override
                    public <T> void after(T object) {
                        if (object instanceof Integer) {
                            int index = (Integer) object;
                            if ((index >= 0) && (index < mDuoMeiTXXList.size())) {
                                String name = mDuoMeiTXXList.get(index).getWenjianmc();
                                mSmoothProgressBar.setVisibility(View.VISIBLE);
                                mRushPayRecordPresenter.deleteImage(index, name, "RushPay");
                            }
                        }
                    }

                    @Override
                    public void after() {
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            if (!canLeave()) {
//                return true;
//            }
//        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        boolean isCalculatingCMSL = true;
        switch (v.getId()) {
            case R.id.inspect_record_lr:
                break;
            case R.id.inspect_record_detail_info:
                Intent intent = new Intent(this,
                        CustomerInformationActivity.class);
                intent.putExtra(CustomerInformationActivity.S_CID,
                        TextUtil.getString(mCardId));
                startActivity(intent);
                setRadioButtonChecked(true, false);
                break;
            case R.id.fcblr_dizhi2:
//                jump2MapActivity(true);
                break;
            case R.id.fcblr_previous:
                switchPreviousOne();
                break;
            case R.id.fcblr_paizhao:
                takePicture();
                break;
            case R.id.fcblr_flashlight:
                openFlashLight();
                break;
            case R.id.fcblr_upload:
                upload();
                break;
            case R.id.fcblr_next:
                switchNextOne();
                break;
            case R.id.fcblr_beizhu2:
                popRushPayRemarkDialog();
                break;
            default:
                break;
        }
    }


    private void upload() {
        LogUtil.i(TAG, "---upload---");
        if (mIsComplete == 1 && mIsUpload == 0) {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(R.string.text_is_uploading_data)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            setNeedDialog(true);
                            uploadRushPayTaskAndMedias(mTaskId, mCardId);
                        }
                    })
                    .show();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_rush_pay_empty_err);
        }
    }

    private void switchNextOne() {
        LogUtil.i(TAG, "---switchNextOne---");
        mImageViewList = null;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mRushPayRecordPresenter.loadRushPayTask(
                DURushPayTaskInfo.FilterType.NEXT_ONE, mTaskId);
    }

    private void switchPreviousOne() {
        mImageViewList = null;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mRushPayRecordPresenter.loadRushPayTask(
                DURushPayTaskInfo.FilterType.PREVIOUS_ONE, mTaskId);
    }

    private void setRadioButtonChecked(boolean isRecodLRChecked, boolean isDetailInfoChecked) {
        mRecordLRRadioButton.setChecked(isRecodLRChecked);
        mDetailInfoRadioButton.setChecked(isDetailInfoChecked);
    }

    /**
     * 拍照功能
     */
    private void takePicture() {
        if (isFlashLightOpen) {
            camera.stopPreview(); // 关掉亮灯
            camera.release(); // 关掉照相机
            camera = null;
            isFlashLightOpen = false;
        }
        LogUtil.i(TAG, "---takePicture---");
        try {
            if (mDuoMeiTXXList.size() >= 6) {
                LogUtil.i(TAG, "---takePicture---full");
                ApplicationsUtil.showMessage(this, R.string.text_pictures_full);
                return;
            }

            if (mCardId != null) {
                File folder = mConfigHelper.getImageFolderPath();
                File dir = new File(folder, "RushPay");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                mFileName = String.format("%s_%s.jpg", mCardId,
                        TextUtil.format(MainApplication.get(this).getCurrentDate(), "yyyyMMddHHmmss"));
                File file = new File(dir, mFileName);
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAPTURE_IMAGE);
            } else {
                LogUtil.i(TAG, "---takePicture---parameter is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---takePicture---" + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtil.i(TAG, "---onActivityResult---1");
        processResult(requestCode, resultCode, data);
        LogUtil.i(TAG, "---onActivityResult---2");
    }

    private void processResult(int requestCode, int resultCode, Intent data) {
        if (CAPTURE_IMAGE == requestCode &&
                mCardId != null &&
                mFileName != null) {
            LogUtil.i(TAG, "---processResult---");
            File folder = mConfigHelper.getImageFolderPath();
            File dir = new File(folder, "RushPay");
            if (!dir.exists()) {
                LogUtil.i(TAG, String.format("---processResult---dir:%s isn't existing",
                        "RushPay"));
                return;
            }

            File file = new File(dir, mFileName);
            if (!file.exists()) {
                LogUtil.i(TAG, String.format("---processResult---file:%s isn't existing",
                        mFileName));
                return;
            }

            DUMedia duoMeiTXX = new DUMedia();
            duoMeiTXX.setCid(mCardId);
            duoMeiTXX.setWenjianlx(DUMedia.WENJIANLX_PIC);
            duoMeiTXX.setWenjianmc(mFileName);
            duoMeiTXX.setType(DUMedia.MEDIA_TYPE_RUSH_PAY);
            duoMeiTXX.setRenwubh(mTaskId);
            duoMeiTXX.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
            duoMeiTXX.setCreaterq(MainApplication.get(this).getCurrentTime());
            duoMeiTXX.setWenjianlj(file.getAbsolutePath());
            duoMeiTXX.setAccount(mPreferencesHelper.getUserSession().getAccount());
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getAbsolutePath()}, null, null);

            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mRushPayRecordPresenter.saveNewImage(duoMeiTXX);
        } else {
            LogUtil.i(TAG, "---processResult---parameter is null");
        }
    }

    private void openFlashLight() {
        if (isFlashLightOpen) {
            camera.stopPreview(); // 关掉亮灯
            camera.release(); // 关掉照相机
            camera = null;
            isFlashLightOpen = false;
        } else {
            camera = Camera.open();
            Camera.Parameters params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview(); // 开始亮灯
            isFlashLightOpen = true;
        }
    }

    private void popRushPayRemarkDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.datachange_label_beizhu)
                .customView(R.layout.dialog_input_beizhu, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .buttonsGravity(mConfigHelper.isLeftOrRightOperation() ? GravityEnum.END : GravityEnum.START)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (editText != null) {

                            if (editText.getText().toString().trim().equals("")) {
                                ApplicationsUtil.showMessage(RushPayRecordActivity.this, "输入为空,保存失败!");
                                return;
                            }
                            mRemark = editText.getText().toString().trim();
                            mRemarkTextView.setText(mRemark);
                            save();
                        }
                    }
                })
                .build();

        if (dialog.getCustomView() != null) {
            editText = (EditText) dialog.getCustomView().findViewById(R.id.et_input_beizhu);
            editText.setText(mRemark);
            editText.setSelection(mRemark.length());
        }
        dialog.show();
    }

    private void save() {
        DURushPayTask duRushPayTask = new DURushPayTask();
        duRushPayTask.setI_TaskId(mTaskId);
        duRushPayTask.setL_ReceiptTime(new Date().getTime());
        duRushPayTask.setS_ReceiptRemark(mRemark);
        duRushPayTask.setS_MeterReader(mMeterReader);
        mRushPayRecordPresenter.updateRushPayTask(duRushPayTask);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                if (canLeave()) {
//                    finish();
//                }

                return true;

            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                if (mRecordLRRadioButton.isChecked()) {
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_UP 1---");
                    switchPreviousOne();
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_UP 2---");
                    return true;
                }
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                if (mRecordLRRadioButton.isChecked()) {
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN 1---");
                    switchNextOne();
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN 2---");
                    return true;
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstDataUtil.I_TASKID, mTaskId);
        outState.putString(ConstDataUtil.S_CARDID, mCardId);
        outState.putString(ConstDataUtil.S_CARDNAME, mCardName);
        outState.putString(ConstDataUtil.S_CARDADDRESS, mCardAddress);
        outState.putString(ConstDataUtil.S_SUBCOMCODE, mSubComCode);
        outState.putDouble(ConstDataUtil.I_QFMONTHS, mQFMonths);
        outState.putDouble(ConstDataUtil.I_QFMONEY, mQFMoney);
        outState.putString(ConstDataUtil.S_REMARK, mRemark);
        outState.putString(ConstDataUtil.S_METERREADER, mMeterReader);
        outState.putInt(ConstDataUtil.I_ISUPLOAD, mIsUpload);
        outState.putInt(ConstDataUtil.I_ISCOMPLETE, mIsComplete);
        LogUtil.i(TAG, "---onSaveInstanceState---");
    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncDataEnd---");
        if (RushPayRecordActivity.this.isNeedDialog()) {
            RushPayRecordActivity.this.setNeedDialog(false);
            RushPayRecordActivity.this.hideProgress();
        }

        if (syncDataEnd.getSyncType() != SyncType.UPLOADING_RUSH_PAY_MEDIAS) {
            return;
        }

        Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
        if (syncDataInfoMap == null) {
            return;
        }

        // record
        List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_RUSH_PAY_TASK.getName());
        if ((syncDataInfoList == null) || (syncDataInfoList.size() != 1)) {
            return;
        }

        SyncDataInfo syncDataInfo = syncDataInfoList.get(0);
        if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
            ApplicationsUtil.showMessage(RushPayRecordActivity.this, R.string.text_uploadRushPayTasks_success);
        } else if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
            ApplicationsUtil.showMessage(RushPayRecordActivity.this, R.string.text_uploadRushPayTasks_failure);
        }

        // medias
        syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_MEDIAS.getName());
        if ((syncDataInfoList == null)
                || (syncDataInfoList.size() != 1)
                || (syncDataInfoList.get(0).getSyncSubDataInfoList() == null)
                || (syncDataInfoList.get(0).getSyncSubDataInfoList().size() <= 0)) {
            return;
        }

        syncDataInfo = syncDataInfoList.get(0);
        List<SyncSubDataInfo> syncSubDataInfoList = syncDataInfo.getSyncSubDataInfoList();
        int successCount, failureCount;
        successCount = failureCount = 0;
        for (SyncSubDataInfo syncSubDataInfo : syncSubDataInfoList) {
            if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.SUCCESS) {
                successCount++;
            } else if (syncSubDataInfo.getOperationResult() == SyncSubDataInfo.OperationResult.FAILURE) {
                failureCount++;
            }
        }

        int totalCount = mDuoMeiTXXList.size();
        if (successCount > totalCount) {
            successCount = totalCount;
        }

        if (failureCount > totalCount) {
            failureCount = totalCount;
        }

        if (successCount + failureCount > totalCount) {
            return;
        }

        String str = String.format(ConstDataUtil.LOCALE, "%s[%s: %d, %s: %d]",
                RushPayRecordActivity.this.getResources().getString(R.string.text_uploadMedia),
                RushPayRecordActivity.this.getResources().getString(R.string.text_success),
                successCount,
                RushPayRecordActivity.this.getResources().getString(R.string.text_failure),
                failureCount);
        ApplicationsUtil.showMessage(RushPayRecordActivity.this, str);
    }

    @Override
    public void onLoadImgPathList(List<String> imgPathList) {
        LogUtil.i(TAG, "---onLoadImgPathList---");
        mImgPathList = imgPathList;
    }

    @Override
    public void onLoadDuoMeiTXXList(List<DUMedia> duMediaList) {
        LogUtil.i(TAG, "---onLoadDuoMeiTXXList---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        mDuoMeiTXXList = duMediaList;
        initTextView();
    }

    @Override
    public void onUpdateRushPayTask(DURushPayTaskResult duRushPayTaskResult) {
        LogUtil.i(TAG, "---onUpdateRecord---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duRushPayTaskResult.getSuccessCount() == 1) {
            ApplicationsUtil.showMessage(this,
                    R.string.text_info_chaobiaolr_savesuccess);
            mIsUpload = duRushPayTaskResult.getDURushPayTaskList().get(0).getI_IsUpload();
            mIsComplete = duRushPayTaskResult.getDURushPayTaskList().get(0).getI_ISComplete();
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateRushPayTask());//通知催缴列表界面更新列表
//          resetCBBZViewsOfResultPanel(false);
//          setIsModified(true);
        } else {
            ApplicationsUtil.showMessage(this,
                    R.string.text_info_chaobiaolr_savefailed);
        }
    }

    @Override
    public void onSaveNewImage(DUMedia duMedia) {
        LogUtil.i(TAG, "---onSaveNewImage---");
        if ((duMedia != null)
                && (!TextUtil.isNullOrEmpty(duMedia.getWenjianlj()))
                && (!TextUtil.isNullOrEmpty(duMedia.getWenjianmc()))) {
            mImgPathList.add(duMedia.getWenjianlj());
            mDuoMeiTXXList.add(duMedia);
            ApplicationsUtil.showMessage(this,
                    R.string.text_success_save_picture);
            save();
//            setIsModified(true);
        } else {
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            LogUtil.i(TAG, "---onSaveNewImage---parameter is error");
            ApplicationsUtil.showMessage(this,
                    R.string.text_failure_save_picture);
        }
    }

    @Override
    public void onLoadImageViews(List<ImageView> imageViewList) {
        LogUtil.i(TAG, "---onLoadImageViews---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (imageViewList != null) {
            mImageViewList = imageViewList;
            displayImages();
        } else {
            LogUtil.i(TAG, "---onLoadImageViews---error");
        }
    }

    @Override
    public void onDeleteImage(int index) {
        LogUtil.i(TAG, "---onDeleteImage---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);

        try {
            mImgPathList.remove(index);
            mDuoMeiTXXList.remove(index);
            if (mImageViewList != null) {
                mImageViewList.remove(index);
            }
            mAdapter.notifyDataSetChanged();
            save();
//            setIsModified(true);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---onDeleteImage---" + e.getMessage());
        }
    }

    @Override
    public void onError(String message) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
//        if (!TextUtil.isNullOrEmpty(message)) {
//            LogUtil.i(TAG, message);
//            ApplicationsUtil.showMessage(this, message);
//        }
    }

    @Override
    public void onSwitchRecordError(boolean isNext) {
        if (isNext) {
            ApplicationsUtil.showMessage(this, "已经是最后一条");
        } else {
            ApplicationsUtil.showMessage(this, "已经是第一条");
        }
    }

    @Override
    public void onLoadRushPayTask(DURushPayTask duRushPayTask) {
        LogUtil.i(TAG, "---onLoadRushPayTask---");
        if (duRushPayTask != null) {
            mTaskId = duRushPayTask.getI_TaskId();
            mCardId = duRushPayTask.getS_CardId();
            mCardName = duRushPayTask.getS_CardName();
            mCardAddress = duRushPayTask.getS_CardAddress();
            mSubComCode = duRushPayTask.getS_SubComCode();
            mQFMonths = (int) duRushPayTask.getD_QfMonths();
            mQFMoney = duRushPayTask.getD_QfMoney();
            mRemark = duRushPayTask.getS_ReceiptRemark();
            mRushPayRecordPresenter.loadImageInfo(mTaskId, mCardId);
        } else {
            LogUtil.i(TAG, "---updateRecordInfo---error");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
