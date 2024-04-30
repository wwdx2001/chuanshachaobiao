package com.sh3h.meterreading.ui.outside;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJInfo;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.images.view.ChaoBiaoZTChooseDialog;
import com.sh3h.meterreading.images.view.ChaoBiaoZTChooseListener;
import com.sh3h.meterreading.images.view.KeyboardDialog;
import com.sh3h.meterreading.images.view.KeyboardListener;
import com.sh3h.meterreading.images.view.PopupWindowMenu;
import com.sh3h.meterreading.location.GpsLocation;
import com.sh3h.meterreading.service.SyncDataInfo;
import com.sh3h.meterreading.service.SyncSubDataInfo;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.information.CustomerInformationActivity;
import com.sh3h.meterreading.ui.map.MapParamEx;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.meterreading.util.ReadCalculatorHelper;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.widget.IBInvoke;
import com.sh3h.mobileutil.widget.PopupWindowImageAdapter;
import com.sh3h.mralgorithm.controlcheck.CheckInPutControl;
import com.sh3h.mralgorithm.controlcheck.ControlItems;
import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;
import com.sh3h.mralgorithm.reading.algorithm.IReadAlgorithm;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutsideRecordActivity extends ParentActivity implements OutsideRecordMVPView, View.OnClickListener {

    private static final String TAG = "OutsideRecordActivity";
    private final static String NEW_CARD_NAME = "新表";
    public final static String OUT_SIDE_PATH = "outside";
    private final static int DEFAULT_CHAOBIAO_ZTBM = 0;
//    private final static int DEFAULT_OUTSIDE_TYPE = 0;

    @Inject
    Bus mEventBus;

    @Inject
    OutsideRecordPresenter mOutsideRecordPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @BindView(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    // 用户编号
    @BindView(R.id.fcblr_yonghubh)
    TextView mYHBHTextView;

    // 户名
    @BindView(R.id.fcblr_huming2)
    TextView mHMTextView;

    // 地址
    @BindView(R.id.fcblr_dizhi2)
    TextView mDZTextView;

    // 欠费信息
    @BindView(R.id.txt_qianfei)
    TextView mQFTextView;

    // 水表信息
    @BindView(R.id.fcblr_shuibiaoxx2)
    TextView mSBXXTextView;

    // 上次抄表状态
    @BindView(R.id.fcblr_chaobiaoztsc)
    TextView mSCCBZTTextView;

    // 上次抄码
    @BindView(R.id.fcblr_shangcicm)
    TextView mSCCBTextView;

    // 上次抄见水量
    @BindView(R.id.fcblr_chaojianslsc)
    TextView mSCCJSLTextView;

    // 本次抄表状态
    @BindView(R.id.fcblr_chaobiaoztbc)
    TextView mBCCBZTTextView;

    // 本次抄码
    @BindView(R.id.fcblr_bencicm)
    TextView mBCCMTextView;

    // 本次抄见水量
    @BindView(R.id.fcblr_chaojianslbc)
    TextView mBCCJSLTextView;

    // 子表（上次抄码 & 本次抄码）
    @BindView(R.id.fcblr_sub)
    RelativeLayout mCBLRSubLayout;

    @BindView(R.id.fcblr_chaoma1)
    TextView mChaoMaOneTextView;

    // 子表（上次抄码）
    @BindView(R.id.fcblr_shangcicm_sub)
    TextView mCNLRShangCicmSubTextView;

    // 子表（本次抄码）
    @BindView(R.id.fcblr_bencicm_sub)
    TextView mCNLRBenCicmSubTextView;

    @BindView(R.id.fcblr_check_out_type2)
    AppCompatSpinner mCheckOutsideTypeSpinner;

    //简号
//    @BindView(R.id.fcblr_cbjianhao)
//    TextView mCBJianHaoTextView;

    @BindView(R.id.fcblr_huanbiao)
    ImageView mHBImageView;

    @BindView(R.id.fcblr_qianfei)
    ImageView mQFImageView;

    @BindView(R.id.fcblr_tingshui)
    ImageView mTSImageView;

    @BindView(R.id.fcblr_bencicm_pen)
    ImageView mBCCMImageView;

    @BindView(R.id.fcblr_bencicm_pen_sub)
    ImageView mBCCMSubImageView;

    @BindView(R.id.fcblr_chaojianslbc_pen)
    ImageView mBCCJSLImageView;

    @BindView(R.id.fcblr_lgbzbc)
    ImageView mBCLGTmageView;

    @BindView(R.id.fcblr_ldbzbc)
    ImageView mBCLDImageView;

    @BindView(R.id.fcblr_chaobiaobzbc)
    ImageView mCBBZImageView;

    @BindView(R.id.fcblr_previous)
    ButtonFloatSmall mPrevButton;

    @BindView(R.id.fcblr_paizhao)
    ButtonRectangle mPZButton;

    @BindView(R.id.fcblr_upload)
    ButtonRectangle mUploadingButton;

    @BindView(R.id.fcblr_next)
    ButtonFloatSmall mNextButton;

    @BindView(R.id.fcblr_cbbeizhu)
    TextView mCBBeiZhuTextView;

    @BindView(R.id.fcblr_flashlight)
    ButtonRectangle mFlashLightButton;

    @BindView(R.id.fcblr_chaobiaoqkbc)
    TextView mCBQKBCTextView;

    @BindView(R.id.fcblr_map_address)
    ImageView mMapAddress;

    @BindView(R.id.inspect_record_lr)
    RadioButton mRecordLRRadioButton;

    @BindView(R.id.inspect_record_detail_info)
    RadioButton mDetailInfoRadioButton;


    private List<String> mImgPathList = null;
    private List<DUMedia> mDuoMeiTXXList = null;
    private List<ImageView> mImageViewList = null;
    private ReadingParameter mReadingParameter = null;
    private boolean isInitSuccess;

    private List<DUChaoBiaoZT> mChaoBiaoZTList = null;
    private List<DUCiYuXX> mLGCiYuXXList = null;
    private List<DUCiYuXX> mLDCiYuXXList = null;

    private DUWaiFuCBSJ mDUWaiFuCBSJ = null;
    private DUCard mDuCard = null;
    private DUChaoBiaoZT mChaoBiaoZT;

    private int mFlag = 0;//当显示该条记录时，记录下抄表记录的状态

    private MenuItem mImageMenuItem;
    private String mCh;
    private String mCid;
    private int mRenWuBH;
    private int mCeNeiXH;
    private int mStartXH;
    private int mEndXH;
    private int mLastReadingChild;
    private int mYiChaoShu;
    private boolean mIsFromTask;
//    private int mDataType;

    private int mLiangGaoLDYYBM = 0;
    private boolean mIsLocationValid = false;
    private String lastChaoBiaoInfo = null;

    private static final int CAPTURE_IMAGE = 1000;
    private static final long NUMBER_MAX = 1000000000;

    private OutsideRecordActivity mOutsideRecordActivity;
    private PopupWindowImageAdapter mAdapter = null;
    private ArrayAdapter<String> mCheckOutsideTypeAdapter = null;
    private List<String> mCheckOutsideTypeData = null;
    private int mSelectOutsideType;

    private boolean isFlashLightOpen = false;
    private Camera camera;

    private String mFileName = null;
    private String mBeizhu = "";
    private String mJianHao = "";
    private EditText editText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOutsideRecordActivity = OutsideRecordActivity.this;

        getActivityComponent().inject(this);

        if (mConfigHelper.isLeftOrRightOperation()) {
            setContentView(R.layout.activity_outside_record_left);
        } else {
            setContentView(R.layout.activity_outside_record_right);
        }


        ButterKnife.bind(this);
        setActionBarBackButtonEnable();
        mEventBus.register(this);
        mOutsideRecordPresenter.attachView(this);

        getIntentData(savedInstanceState);
        setViewsOnClickListener();
        mImgPathList = new ArrayList<>();
        mDuoMeiTXXList = new ArrayList<>();
        mReadingParameter = new ReadingParameter();

        isInitSuccess = false;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mOutsideRecordPresenter.loadChaoBiaoZTList();

        LogUtil.i(TAG, "---onCreate---");
    }

    private void setViewsOnClickListener() {
        mDZTextView.setOnClickListener(this);
        mBCCBZTTextView.setOnClickListener(this);
        mBCCJSLTextView.setOnClickListener(this);
        mCNLRBenCicmSubTextView.setOnClickListener(this);
        mBCCMTextView.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPZButton.setOnClickListener(this);
        mUploadingButton.setOnClickListener(this);
        mCBBeiZhuTextView.setOnClickListener(this);
        mFlashLightButton.setOnClickListener(this);
        mCBQKBCTextView.setOnClickListener(this);
        mMapAddress.setOnClickListener(this);
//        mCBJianHaoTextView.setOnClickListener(this);

        mRecordLRRadioButton.setOnClickListener(this);
        mDetailInfoRadioButton.setOnClickListener(this);
    }

    private void getIntentData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (savedInstanceState != null) {
            mCh = savedInstanceState.getString(ConstDataUtil.S_CH);
            mCid = savedInstanceState.getString(ConstDataUtil.S_CID);
            mRenWuBH = savedInstanceState.getInt(ConstDataUtil.I_RENWUBH, 0);
            mCeNeiXH = savedInstanceState.getInt(ConstDataUtil.I_CENEIXH, 0);
            mStartXH = savedInstanceState.getInt(ConstDataUtil.STARTXH, 0);
            mEndXH = savedInstanceState.getInt(ConstDataUtil.ENDXH, 0);
            mYiChaoShu = savedInstanceState.getInt(ConstDataUtil.YICHAOSHU, 0);
            mIsFromTask = savedInstanceState.getBoolean(ConstDataUtil.FROM_TASK, false);
            if (intent != null) {
                intent.putExtra(ConstDataUtil.S_CID, mCid);
                intent.putExtra(ConstDataUtil.I_CENEIXH, mCeNeiXH);
            }

            LogUtil.i(TAG, "getIntentData savedInstanceState != null" + mCid);
        } else if (intent != null) {
            mCh = intent.getStringExtra(ConstDataUtil.S_CH);
            mCid = intent.getStringExtra(ConstDataUtil.S_CID);
            mRenWuBH = intent.getIntExtra(ConstDataUtil.I_RENWUBH, 0);
            mCeNeiXH = intent.getIntExtra(ConstDataUtil.I_CENEIXH, 0);
            mStartXH = intent.getIntExtra(ConstDataUtil.STARTXH, 0);
            mEndXH = intent.getIntExtra(ConstDataUtil.ENDXH, 0);
            mYiChaoShu = intent.getIntExtra(ConstDataUtil.YICHAOSHU, 0);
            mIsFromTask = intent.getBooleanExtra(ConstDataUtil.FROM_TASK, false);

            LogUtil.i(TAG, "getIntentData intent != null" + mCid);
        } else {
            LogUtil.i(TAG, "getIntentData error!!!");
        }
    }


    private Bundle getBundle() {
        Bundle bundle = new Bundle();
//      bundle.putString(ChaoBiaoSJColumns.ST, _sT);
        bundle.putString(ConstDataUtil.S_CH, mCh);
        bundle.putString(ConstDataUtil.S_CID, mCid);
        bundle.putInt(ConstDataUtil.I_RENWUBH, mRenWuBH);
        bundle.putInt(ConstDataUtil.I_CENEIXH, mCeNeiXH);
        bundle.putInt(ConstDataUtil.STARTXH, mStartXH);
        bundle.putInt(ConstDataUtil.ENDXH, mEndXH);
//      bundle.putString("PreActivity", mPreActivity);
        bundle.putInt(ConstDataUtil.I_LASTREADINGCHILD, mLastReadingChild);
        bundle.putInt(ConstDataUtil.YICHAOSHU, mYiChaoShu);
        bundle.putBoolean(ConstDataUtil.FROM_TASK, mIsFromTask);
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i(TAG, "---onActivityResult---");
        processResult(requestCode, resultCode, data);
    }

    private boolean isCoordinateValid() {
        if (mDuCard == null) {
            return false;
        }

        String x1 = mDuCard.getX1();
        String y1 = mDuCard.getY1();
        double longitude = TextUtil.getDouble(x1);
        double latitude = TextUtil.getDouble(y1);
        if ((longitude <= MapParamEx.ERROR_VALUE)
                || (latitude <= MapParamEx.ERROR_VALUE)) {
            return false;
        }

        return true;
    }


    public void processResult(int requestCode, int resultCode, Intent data) {
        if (CAPTURE_IMAGE == requestCode &&
                mCh != null &&
                mCid != null &&
                mFileName != null) {
            LogUtil.i(TAG, "---processResult---");
            File folder = mConfigHelper.getImageFolderPath();
            File dir = new File(folder, OUT_SIDE_PATH);
            if (!dir.exists()) {
                LogUtil.i(TAG, String.format("---processResult---dir:%s isn't existing",
                        OUT_SIDE_PATH));
                return;
            }

            File file = new File(dir, mFileName);
            if (!file.exists()) {
                LogUtil.i(TAG, String.format("---processResult---file:%s isn't existing",
                        mFileName));
                return;
            }

            DUMedia duoMeiTXX = new DUMedia();
            duoMeiTXX.setCid(mCid);
            duoMeiTXX.setChaobiaoid(mDUWaiFuCBSJ.getChaobiaoid());
            duoMeiTXX.setWenjianlx(DUMedia.WENJIANLX_PIC);
            duoMeiTXX.setWenjianmc(mFileName);
            duoMeiTXX.setType(DUMedia.MEDIA_TYPE_WAIFU);
            duoMeiTXX.setRenwubh(mRenWuBH);
            duoMeiTXX.setCh(mCh);
            duoMeiTXX.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
            duoMeiTXX.setCreaterq(MainApplication.get(mOutsideRecordActivity).getCurrentTime());
            duoMeiTXX.setWenjianlj(file.getAbsolutePath());
            duoMeiTXX.setAccount(mPreferencesHelper.getUserSession().getAccount());
            MediaScannerConnection.scanFile(mOutsideRecordActivity,
                    new String[]{file.getAbsolutePath()}, null, null);

            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mOutsideRecordPresenter.saveNewImage(duoMeiTXX);
        } else if ((requestCode == MapParamEx.REQUEST_CODE)
                && (resultCode == MapParamEx.RESULT_CODE)
                && (data != null)) {
            LogUtil.i(TAG, "---processResult---baidumap");

            double longitude = data.getDoubleExtra(MapParamEx.LONGITUDE, 0);
            double latitude = data.getDoubleExtra(MapParamEx.LATITUDE, 0);
            uploadCardCoordinate(
                    mRenWuBH,
                    mCh,
                    mCid,
                    longitude,
                    latitude);
        } else {
            LogUtil.i(TAG, "---processResult---parameter is null");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "---onDestroy---");

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mOutsideRecordPresenter.detachView();

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

        mImageMenuItem = menu.findItem(R.id.action_image);
        mImageMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                loadImages();
                return true;
            }
        });

        return true;
    }

    public void loadImages() {
        LogUtil.i(TAG, "---loadImages---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---loadImages---error");
            return;
        }

        if ((mImageViewList != null)
                && (mImgPathList.size() == mImageViewList.size())) {
            displayImages();
        } else {
            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mOutsideRecordPresenter.loadImageViews(mImgPathList, mOutsideRecordActivity);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
                        TextUtil.getString(mCid));
                intent.putExtra(CustomerInformationActivity.S_CH,
                        TextUtil.getString(mCh));
                startActivity(intent);
                setRadioButtonChecked(true, false);
                break;
            case R.id.fcblr_dizhi2:
                jump2MapActivity(true);
                break;
            case R.id.fcblr_chaobiaoztbc:
                popupChaoBiaoZT();
                break;
            case R.id.fcblr_chaojianslbc:
                if (mBCCMTextView.isEnabled()) {
                    isCalculatingCMSL = false;
                }
                popupKeyboard(false, isCalculatingCMSL);
                break;
            case R.id.fcblr_bencicm_sub:
                if (mBCCJSLTextView.isEnabled()) {
                    isCalculatingCMSL = false;
                }
                popupKeyboard(true, isCalculatingCMSL);
                break;
            case R.id.fcblr_bencicm:
                if (mLastReadingChild == 0) {
                    mChaoMaOneTextView.setText(R.string.text_chaoma);
                    mCBLRSubLayout.setVisibility(View.GONE);

                    if (mBCCJSLTextView.isEnabled()) {
                        isCalculatingCMSL = false;
                    }

                    popupKeyboard(true, isCalculatingCMSL);
                } else {
                    mChaoMaOneTextView.setText(R.string.text_chaoma_mu);
                    mCBLRSubLayout.setVisibility(View.VISIBLE);
                    popupKeyboard(true, isCalculatingCMSL);
                }
                break;
            case R.id.fcblr_previous:
                switchPreviousOne();
                break;
            case R.id.fcblr_paizhao:
                takePicture();
                break;
            case R.id.fcblr_upload:
                upload();
                break;
            case R.id.fcblr_next:
                switchNextOne();
                break;
            case R.id.fcblr_cbbeizhu:
                popChaoBiaoBeiZhuDialog();
                break;
//            case R.id.fcblr_cbjianhao:
//                popChaoBiaoJianHaoDialog();
//                break;
            case R.id.fcblr_flashlight:
                openFlashLight();
                break;
            case R.id.fcblr_chaobiaoqkbc:
                popupLastChaoBiaoInfo();
                break;
            case R.id.fcblr_map_address:
                jump2MapActivity(false);
            default:
                break;
        }
    }

    private void setRadioButtonChecked(boolean isRecodLRChecked, boolean isDetailInfoChecked) {
        mRecordLRRadioButton.setChecked(isRecodLRChecked);
        mDetailInfoRadioButton.setChecked(isDetailInfoChecked);
    }

    private void popupLastChaoBiaoInfo() {
        if (!TextUtil.isNullOrEmpty(lastChaoBiaoInfo)) {
            new MaterialDialog.Builder(mOutsideRecordActivity)
                    .title(R.string.text_prompt)
                    .content(lastChaoBiaoInfo)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .show();
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


//    private void popChaoBiaoJianHaoDialog() {
//
//        LogUtil.i(TAG, "---popChaoBiaoJianHaoDialog---");
//
//        if (!isInitSuccess) {
//            LogUtil.i(TAG, "---popChaoBiaoJianHaoDialog---error");
//            return;
//        }
//
//        mJianHao = TextUtil.getString(mDUWaiFuCBSJ.getJianhao());
//        MaterialDialog dialog = new MaterialDialog.Builder(mOutsideRecordActivity)
//                .title(R.string.datachange_label_jianhao)
//                .customView(R.layout.dialog_input_beizhu, true)
//                .positiveText(R.string.text_ok)
//                .negativeText(android.R.string.cancel)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        if (editText != null) {
//                            mCBJianHaoTextView.setText(editText.getText().toString().trim());
//                            save();
//                        }
//                    }
//                })
//                .build();
//
//        if (dialog.getCustomView() != null) {
//            editText = (EditText) dialog.getCustomView().findViewById(R.id.et_input_beizhu);
//            editText.setText(mJianHao);
//            editText.setSelection(mJianHao.length());
//        }
//
//        dialog.show();
//    }


    private void popChaoBiaoBeiZhuDialog() {
        LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---error");
            return;
        }
//        else {
//            LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---3");
//            boolean isNotRead = (mDUWaiFuCBSJ.getIchaobiaobz() == DUWaiFuCBSJ.CHAOBIAOBZ_WEICHAO);
//            if (isNotRead) {
//                LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---4");
//                ApplicationsUtil.showMessage(mOutsideRecordActivity, R.string.text_record_firstly);
//                return;
//            }
//        }

        mBeizhu = TextUtil.getString(mDUWaiFuCBSJ.getSchaobiaobz());
        MaterialDialog dialog = new MaterialDialog.Builder(mOutsideRecordActivity)
                .title(R.string.datachange_label_beizhu)
                .customView(R.layout.dialog_input_beizhu, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (editText != null) {
                            mCBBeiZhuTextView.setText(editText.getText().toString().trim());
                            save();
                        }
                    }
                })
                .build();

        if (dialog.getCustomView() != null) {
            editText = (EditText) dialog.getCustomView().findViewById(R.id.et_input_beizhu);
            editText.setText(mBeizhu);
            editText.setSelection(mBeizhu.length());
        }

        dialog.show();
    }

    public void switchNextOne() {
        LogUtil.i(TAG, "---switchNextOne---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---switchNextOne---error");
            return;
        }

        if (mCeNeiXH >= mEndXH) {
            LogUtil.i(TAG, "---switchPreviousOne---last one");
            return;
        }

        isInitSuccess = false;
        mImageViewList = null;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mOutsideRecordPresenter.loadWaiFuCBSJInfo(
                mIsFromTask ? DUWaiFuCBSJInfo.FilterType.NEXT_ONE_NOT_READING : DUWaiFuCBSJInfo.FilterType.NEXT_ONE,
                mRenWuBH,
                mCh, mCid, mCeNeiXH);
    }

    private void upload() {
        LogUtil.i(TAG, "---upload---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---upload---failure");
            return;
        }

        boolean isRead = (mDUWaiFuCBSJ.getIchaobiaobz() == DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO);
        boolean hasNotUploadedRecord = (mDUWaiFuCBSJ.getShangchuanbz() == DUWaiFuCBSJ.SHANGCHUANBZ_WEISHANGC);
        boolean hasNotUploadedMedia = false;
        for (DUMedia duMedia : mDuoMeiTXXList) {
            if (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_WEISHANGC) {
                hasNotUploadedMedia = true;
                break;
            }
        }

        if ((isRead && hasNotUploadedRecord) || (isRead && hasNotUploadedMedia)) {
            new MaterialDialog.Builder(mOutsideRecordActivity)
                    .title(R.string.text_prompt)
                    .content(R.string.text_is_uploading_data)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mOutsideRecordActivity.setNeedDialog(true);
                            mOutsideRecordActivity.uploadOutRecordAndMedias(mRenWuBH, mCh, mCid, mCeNeiXH);
                        }
                    })
                    .show();
        } else {
            ApplicationsUtil.showMessage(mOutsideRecordActivity,
                    R.string.text_chaobiao_empty_err);
        }
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
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---takePicture---failure");
            return;
        }

        try {
            if (mDuoMeiTXXList.size() >= 6) {
                LogUtil.i(TAG, "---takePicture---full");
                ApplicationsUtil.showMessage(mOutsideRecordActivity,
                        R.string.text_pictures_full);
                return;
            }

            if ((mCh != null) && (mCid != null)) {
                File folder = mConfigHelper.getImageFolderPath();
                File dir = new File(folder, OUT_SIDE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                mFileName = String.format("%s_%s.jpg", mCid,
                        TextUtil.format(MainApplication.get(mOutsideRecordActivity).getCurrentDate(), "yyyyMMddHHmmss"));
                File file = new File(dir, mFileName);
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                mOutsideRecordActivity.startActivityForResult(intent, CAPTURE_IMAGE);
            } else {
                LogUtil.i(TAG, "---takePicture---parameter is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---takePicture---" + e.getMessage());
        }
    }

    public void switchPreviousOne() {
        LogUtil.i(TAG, "---switchPreviousOne---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---switchPreviousOne---error");
            return;
        }

        if (mCeNeiXH <= mStartXH) {
            LogUtil.i(TAG, "---switchPreviousOne---first one");
            return;
        }

        isInitSuccess = false;
        mImageViewList = null;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mOutsideRecordPresenter.loadWaiFuCBSJInfo(
                mIsFromTask ? DUWaiFuCBSJInfo.FilterType.PREVIOUS_ONE_NOT_READING : DUWaiFuCBSJInfo.FilterType.PREVIOUS_ONE,
                mRenWuBH,
                mCh, mCid, mCeNeiXH);
    }

    private void displayImages() {
        mAdapter = new PopupWindowImageAdapter(mImageViewList);
        Window window = mOutsideRecordActivity.getWindow();
        PopupWindowMenu popupWindowMenuNJXX = new PopupWindowMenu(mOutsideRecordActivity, window);
        popupWindowMenuNJXX.popupWindowImageViwe(mBCCMTextView,
                PopupWindowMenu.ATLOCATION_TOP, AbsListView.LayoutParams.FILL_PARENT,
                AbsListView.LayoutParams.FILL_PARENT, "图片", mAdapter,
                mConfigHelper.getImageFolderPath(),
                mCh, mDuoMeiTXXList, new IBInvoke() {
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
                                mOutsideRecordPresenter.deleteImage(index, name, mCh);
                            }
                        }
                    }

                    @Override
                    public void after() {
                    }
                });
    }

    /**
     * @param isChaoMa
     * @param isCalcutingCMSL
     */
    private void popupKeyboard(final boolean isChaoMa,
                               final boolean isCalcutingCMSL) {
        LogUtil.i(TAG, "---popupKeyboard---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popupKeyboard---failure");
            return;
        }

        String subtitle = null;

        //不分子母表时，一切按以往方式处理
        subtitle = String.format(ConstDataUtil.LOCALE,
                "上次抄码: %d 上次水量: %d 抄表状态: %s",
                mDUWaiFuCBSJ.getShangcicm(), mDUWaiFuCBSJ.getShangcicjsl(),
                mChaoBiaoZT.getZhuangtaimc());
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        KeyboardDialog dialog = new KeyboardDialog(
                mOutsideRecordActivity,
                new KeyboardListener() {
                    @Override
                    public void getNumber(long number) {
                        if (number >= NUMBER_MAX) {
                            ApplicationsUtil.showMessage(mOutsideRecordActivity,
                                    R.string.text_chaomatc);
                            return;
                        }

                        if (isChaoMa) {
                            mBCCMTextView.setText(String.valueOf(number));
                        } else {
                            mBCCJSLTextView.setText(String.valueOf(number));
                        }

                        if (isCalcutingCMSL) {
                            calcuteCMSL();
                        }
                    }
                }).setSubtitile(subtitle).init(leftOrRight);
        dialog.showLeftOrRight(leftOrRight);
        dialog.show();
    }

    private void calcuteCMSL() {
        LogUtil.i(TAG, "---calcuteCMSL---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---calcuteCMSL---failure");
            return;
        }

        mReadingParameter.reset();
        mReadingParameter.set_huanbiaohtqrsj(TextUtil.format(mDUWaiFuCBSJ.getShangcicbrq()));
        mReadingParameter.set_shangcicbrq(mDUWaiFuCBSJ.getShangcicbrq());
        mReadingParameter.set_xinbiaodm(mDUWaiFuCBSJ.getXinbiaodm());
        mReadingParameter.set_jiubiaocm(mDUWaiFuCBSJ.getJiubiaocm());
        mReadingParameter.set_shangcicm(mDUWaiFuCBSJ.getShangcicm());
        mReadingParameter.set_bencicm(TextUtil.getInt(mBCCMTextView.getText().toString().trim()));
        mReadingParameter.set_bencicbrq(MainApplication.get(mOutsideRecordActivity).getCurrentTime());
        mReadingParameter.set_bencisl(TextUtil.getInt(mBCCJSLTextView.getText().toString().trim()));
        mReadingParameter.set_chaobiaoztbm(String.valueOf(mChaoBiaoZT.getZhuangtaibm()));
        mReadingParameter.set_liangcheng(mDuCard.getLiangcheng());
        mReadingParameter.set_shuibiaobeil(mDuCard.getShuibiaobl());
        mReadingParameter.set_shuibiaozt(mDuCard.getShuibiaozt());
        mReadingParameter.set_shangcicjsl(mDUWaiFuCBSJ.getShangcicjsl());

        ReadingResult result = ReadCalculatorHelper.calculate(mOutsideRecordActivity, mReadingParameter);
        if (result.isSuccess()) {
            mReadingParameter = result.outEntity();
            mBCCMTextView.setText(String.valueOf(mReadingParameter.get_bencicm()));
//            if (mDuRecord.getLastReadingChild() != 0) {
//                mCNLRBenCicmSubTextView.setText(String.valueOf(mReadingParameter.get_readingchild()));
//            }

            mBCCJSLTextView.setText(String.valueOf(mReadingParameter.get_bencisl()));

            // 设置量高量低图片 & 抄表标志
            resetLGLDViewsOfResultPanel(false);
            resetCBBZViewsOfResultPanel(true);
//            if (!TextUtil.isNullOrEmpty(sysVersion)) {
//                if (sysVersion.equals(ConfigHelper.SystemSetting.SYS_VERSION_JIADING)) {
//                    save();
//                } else {
//                    popupLiangGaoLDYY();
//                }
//            } else {
//                popupLiangGaoLDYY();
//            }
            popupLiangGaoLDYY();
        } else {
            ApplicationsUtil.showMessage(mOutsideRecordActivity, result.outMsg());
//            if (mDuRecord.getLastReadingChild() != 0) {
//                mCNLRBenCicmSubTextView.setText("");
//                //  mBCCMTextView.setText("");
//                mBCCJSLTextView.setText("");
//            } else {
            mBCCMTextView.setText("");
            mBCCJSLTextView.setText("");
//            }
            // 设置量高量低图片 & 抄表标志
            resetLGLDViewsOfResultPanel(true);
            resetCBBZViewsOfResultPanel(true);
        }
    }

    private void popupLiangGaoLDYY() {
        LogUtil.i(TAG, "---popupLiangGaoLDYY---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popupLiangGaoLDYY---failure");
            return;
        }

        // 这里判断是否弹出量高量低原因选择框
        boolean needLGLDYY =
                mConfigHelper.getSystemConfig().getBoolean(SystemConfig.PARAM_CB_INPUT_LGLDYY, false);

        // 此处增加一个判断，只有特定的状态分类才弹出提醒
        if (needLGLDYY
        /*
         * && LIANGGLD_TIXING_ZTFLBM.indexOf(String.valueOf(mChaoBiaoZT.
		 * getZhuangTaiFLBM())) > -1
		 */
                && mLiangGaoLDYYBM != 0) {

            final List<DUCiYuXX> ciYuXXList;
            int title = R.string.listdialog_title_lianggao;
            if (mLiangGaoLDYYBM == 999) {
                // 量高选择框
                title = R.string.listdialog_title_lianggao;
                ciYuXXList = mLGCiYuXXList;
            } else if (mLiangGaoLDYYBM == -999) {
                // 量低选择框
                title = R.string.listdialog_title_liangdi;
                ciYuXXList = mLDCiYuXXList;
            } else {
                ciYuXXList = null;
            }

            if (ciYuXXList == null) {
                LogUtil.i(TAG, "---popupLiangGaoLDYY---ciYuXXList is null");
                save();
                return;
            }

            LogUtil.i(TAG, "---popupLiangGaoLDYY---dialog");
            new MaterialDialog.Builder(mOutsideRecordActivity)
                    .title(title)
                    .items(ciYuXXList)
                    .cancelable(false)
                    .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view,
                                                   int which, CharSequence text) {
                            // cancel or no selection
                            LogUtil.i(TAG, "---popupLiangGaoLDYY---selection");
                            if ((which >= 0) && (which < ciYuXXList.size())) {
                                DUCiYuXX ciyu = ciYuXXList.get(which);
                                mLiangGaoLDYYBM = TextUtil.getInt(ciyu.getWordsvalue());
                                save();
                            } else {
                                LogUtil.i(TAG, "---popupLiangGaoLDYY---selection index is error");
                            }

                            return true; // allow selection
                        }
                    })
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .buttonsGravity(GravityEnum.END)
                    .show();
        } else {
            LogUtil.i(TAG, "---popupLiangGaoLDYY---save");
            save();
        }
    }

    private void save() {
        LogUtil.i(TAG, "---save---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---save---failure");
            return;
        }

        double longitude = 0.0;
        double latitude = 0.0;
//        if (mIsGpsLocated && (mGpsLocation != null)) {
//            mLocationClient = mMainApplication.getmLocationClient();
//            if (mLocationClient != null) {
//                longitude = mLocationClient.getLastKnownLocation().getLongitude();
//                latitude = mLocationClient.getLastKnownLocation().getLatitude();
//            } else {
//                longitude = 0.0;
//                latitude = 0.0;
//            }
////            longitude = mGpsLocation.getLongitude();
////            latitude = mGpsLocation.getLatitude();
//        } else {
//            longitude = 0.0;
//            latitude = 0.0;
//        }

        mDUWaiFuCBSJ.setZhuangtaibm(mChaoBiaoZT.getZhuangtaibm());
        mDUWaiFuCBSJ.setZhuangtaimc(mChaoBiaoZT.getZhuangtaimc());
        mDUWaiFuCBSJ.setBencicm(TextUtil.getInt(mBCCMTextView.getText().toString().trim()));
        mDUWaiFuCBSJ.setChaojiansl(TextUtil.getInt(mBCCJSLTextView.getText().toString().trim()));
        mDUWaiFuCBSJ.setChaobiaorq(MainApplication.get(mOutsideRecordActivity).getCurrentTime());
        mDUWaiFuCBSJ.setJe(0.0);
        mDUWaiFuCBSJ.setLianggaoldyybm(mLiangGaoLDYYBM);
        mDUWaiFuCBSJ.setX1(String.valueOf(longitude));
        mDUWaiFuCBSJ.setY1(String.valueOf(latitude));
        if (mBCCMTextView.getText().toString().trim().equals("")) {
            mDUWaiFuCBSJ.setIchaobiaobz(DUWaiFuCBSJ.CHAOBIAOBZ_WEICHAO);
        } else {
            mDUWaiFuCBSJ.setIchaobiaobz(DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO);
        }
//        mDUWaiFuCBSJ.setIchaobiaobz(DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO);
        mDUWaiFuCBSJ.setShangchuanbz(DUWaiFuCBSJ.SHANGCHUANBZ_WEISHANGC);
        mDUWaiFuCBSJ.setSchaobiaobz(TextUtil.getString(mCBBeiZhuTextView.getText().toString().trim()));
//      mDUWaiFuCBSJ.setJianhao(TextUtil.getString(mCBJianHaoTextView.getText().toString().trim()));
//      boolean isNotRead = (mDUWaiFuCBSJ.getIchaobiaobz() == DUWaiFuCBSJ.CHAOBIAOBZ_WEICHAO);
        mDUWaiFuCBSJ.setCheckOutsideType(mSelectOutsideType);
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mOutsideRecordPresenter.updateWaiFuCBSJ(mDUWaiFuCBSJ);
    }

    private void popupChaoBiaoZT() {
        LogUtil.i(TAG, "---popupChaoBiaoZT---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popupChaoBiaoZT---failure");
            return;
        }

        if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH) {
            LogUtil.i(TAG, "---popupChaoBiaoZT---new card");
            ApplicationsUtil.showMessage(this, R.string.text_new_card);
            return;
        }

        new ChaoBiaoZTChooseDialog(
                mConfigHelper,
                mOutsideRecordActivity,
                new ChaoBiaoZTChooseListener() {
                    @Override
                    public void onResult(Bundle bundle) {
                        if (bundle == null) {
                            return;
                        }

                        String zhuangtaimc = bundle.getString(ChaoBiaoZTChooseDialog.XIAOLET_MC);
                        if (TextUtil.isNullOrEmpty(zhuangtaimc)) {
                            return;
                        }

                        if (zhuangtaimc.equals(NEW_CARD_NAME)
                                && (mDuCard.getShuibiaozt() != DUCard.SHUIBIAOZT_SWITCH)) {
                            ApplicationsUtil.showMessage(mOutsideRecordActivity, R.string.text_not_new_card);
                            return;
                        }

                        if ((mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH)
                                && (!zhuangtaimc.equals(NEW_CARD_NAME))) {
                            ApplicationsUtil.showMessage(mOutsideRecordActivity, R.string.text_new_card);
                            return;
                        }

//                        if (zhuangtaimc.equals("馈通")
//                                || zhuangtaimc.equals("无水")) {
//                            if (!mDuCard.getShuibiaoflmc().equals("消防表")) {
//                                ApplicationsUtil.showMessage(mRecordActivity,
//                                        "该表不属于消防表，不能选择" + zhuangtaimc + "状态");
//                                return;
//                            }
//                        }

                        int zhuangtaibm = bundle.getInt(ChaoBiaoZTChooseDialog.XIAOLET_BM);
                        DUChaoBiaoZT chaoBiaoZT = getChaoBiaoZT(zhuangtaibm);
                        if ((chaoBiaoZT != null)
                                && (chaoBiaoZT.getZhuangtaimc().equals(zhuangtaimc))) {
                            mBCCBZTTextView.setText(zhuangtaimc);
                            mChaoBiaoZT = chaoBiaoZT;
                            int bencicm = TextUtil.getInt(mBCCMTextView.getText().toString().trim());
                            int chaojiansl = TextUtil.getInt(mBCCJSLTextView.getText().toString().trim());
                            resetViewsOfInputPanel(true);
                            resetLGLDViewsOfResultPanel(true);
                            resetCBBZViewsOfResultPanel(true);

                            if (mDUWaiFuCBSJ.getIchaobiaobz() == DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO) {
                                mBCCMTextView.setText(String.valueOf(bencicm));
                                mBCCJSLTextView.setText(String.valueOf(chaojiansl));
                                calcuteCMSL();
                            } else if (chaoBiaoZT.getShuiliangsfbm() == IReadAlgorithm.ALGORITHM_CHAOMABUBIAN) {
                                calcuteCMSL();
                            }
                        }
                    }
                }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//                if (mRecordLRRadioButton.isChecked()) {
//                    LogUtil.i(TAG, "---KEYCODE_VOLUME_UP 1---");
//                    Object object = mMyPagerAdapter.instantiateItem(mViewPager, 0);
//                    if (object instanceof RecordLRFragment) {
//                        LogUtil.i(TAG, "---KEYCODE_VOLUME_UP---" + object);
//                        RecordLRFragment recordLRFragment = (RecordLRFragment)object;
//                        recordLRFragment.switchPreviousOne();
//                    }
//                    LogUtil.i(TAG, "---KEYCODE_VOLUME_UP 2---");
//                    return true;
//                }
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//                if (mRecordLRRadioButton.isChecked()) {
//                    LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN 1---");
//                    Object object = mMyPagerAdapter.instantiateItem(mViewPager, 0);
//                    if (object instanceof RecordLRFragment) {
//                        LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN---" + object);
//                        RecordLRFragment recordLRFragment = (RecordLRFragment)object;
//                        recordLRFragment.switchNextOne();
//                    }
//                    LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN 2---");
//                    return true;
//                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//      outState.putString(ChaoBiaoSJColumns.ST, _sT);
        outState.putString(ConstDataUtil.S_CH, mCh);
        outState.putString(ConstDataUtil.S_CID, mCid);
        outState.putInt(ConstDataUtil.I_RENWUBH, mRenWuBH);
        outState.putInt(ConstDataUtil.I_LASTREADINGCHILD, mLastReadingChild);
//      outState.putString("PreActivity", mPreActivity);
//      outState.putBoolean("isfinished", mIsfinished);
        outState.putInt(ConstDataUtil.I_CENEIXH, mCeNeiXH);
        outState.putInt(ConstDataUtil.STARTXH, mStartXH);
        outState.putInt(ConstDataUtil.ENDXH, mEndXH);
//      outState.putInt(CeBenListActivity.DATA_TYPE, mDataType);
        outState.putInt(ConstDataUtil.YICHAOSHU, mYiChaoShu);
        outState.putBoolean(ConstDataUtil.FROM_TASK, mIsFromTask);

        LogUtil.i(TAG, "---onSaveInstanceState---");
    }

    private void updateViews() {
        mChaoBiaoZT = getChaoBiaoZT();
        if (mChaoBiaoZT == null) {
            return;
        }

        //获取抄表事件
        resetRecordInfo();

        //子母表只计算抄表正常状态，无其他抄表状态
        if (mLastReadingChild > 0) {
            mBCCBZTTextView.setClickable(false);
        } else {
            mBCCBZTTextView.setClickable(true);
        }

        if (isCoordinateValid()) {
            mMapAddress.setVisibility(View.VISIBLE);
        } else {
            mMapAddress.setVisibility(View.GONE);
        }

        if ((mDUWaiFuCBSJ != null)
                && (mDuCard != null)
                && (mChaoBiaoZT != null)
                && (mReadingParameter != null)
                && (mImgPathList != null)
                && (mDuoMeiTXXList != null)) {
            isInitSuccess = true;
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateRecord(mCid, mCeNeiXH));
        } else {
            isInitSuccess = false;
        }
    }

    private void jump2MapActivity(boolean isMarkMap) {
        if (isMarkMap) {
            double longitude, latitude;
            if (isCoordinateValid()) {
                longitude = TextUtil.getDouble(mDuCard.getX1());
                latitude = TextUtil.getDouble(mDuCard.getY1());
            } else {
                boolean isGpsLocated = MainApplication.get(this).isGpsLocated();
                GpsLocation.MRLocation mrLocation = MainApplication.get(mOutsideRecordActivity).getMRLocation();
                if (isGpsLocated && (mrLocation != null)) {
                    longitude = mrLocation.getLongitude();
                    latitude = mrLocation.getLatitude();
                } else {
                    longitude = mConfigHelper.getDefaultLongitude();
                    latitude = mConfigHelper.getDefaultLatitude();
                }
            }

//            Intent intent = new Intent(mOutsideRecordActivity, BaiduMapActivity.class);
//            intent.putExtra(MapParamEx.LONGITUDE, longitude);
//            intent.putExtra(MapParamEx.LATITUDE, latitude);
//            intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.MARK_MAP.ordinal());
//            mOutsideRecordActivity.startActivityForResult(intent, MapParamEx.REQUEST_CODE);
            return;
        }

        if (!isCoordinateValid()) {
            ApplicationsUtil.showMessage(mOutsideRecordActivity, R.string.text_coordinate_invalid);
            return;
        }

        String x1 = mDuCard.getX1();
        String y1 = mDuCard.getY1();
        String address = mDuCard.getDizhi();

//        Intent intent = new Intent(mOutsideRecordActivity, BaiduMapActivity.class);
//        intent.putExtra(MapParamEx.LONGITUDE, TextUtil.getDouble(x1));
//        intent.putExtra(MapParamEx.LATITUDE, TextUtil.getDouble(y1));
//        intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.LOCATE_MAP.ordinal());
//        intent.putExtra(MapParamEx.ADDRESS, TextUtil.getString(address));
//        mOutsideRecordActivity.startActivity(intent);
    }

    private void resetRecordInfo() {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        resetHBQFImageViews();

        resetTextViews();

        if (mDUWaiFuCBSJ.getIchaobiaobz() == 0) {
            resetViewsOfInputPanel(true);
            resetLGLDViewsOfResultPanel(true);
            resetCBBZViewsOfResultPanel(true);
        } else {
            resetViewsOfInputPanel(false);
            resetLGLDViewsOfResultPanel(false);
            resetCBBZViewsOfResultPanel(false);
        }

        resetButtons();
    }

    private void resetButtons() {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (mPrevButton != null) {
            if (mStartXH < mCeNeiXH) {
                mPrevButton.setEnabled(true);
                mPrevButton.setVisibility(View.VISIBLE);
            } else {
                mPrevButton.setEnabled(false);
                mPrevButton.setVisibility(View.INVISIBLE);
            }
        }

        if (mNextButton != null) {
            if (mCeNeiXH < mEndXH) {
                mNextButton.setEnabled(true);
                mNextButton.setVisibility(View.VISIBLE);
            } else {
                mNextButton.setEnabled(false);
                mNextButton.setVisibility(View.INVISIBLE);
            }
        }

//        resetUploadingButton();
    }

    private void resetCBBZViewsOfResultPanel(boolean clean) {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (!clean) {
            int chaobiaobz = 0;
            int shangchuan = 0;

            chaobiaobz = mDUWaiFuCBSJ.getIchaobiaobz();
            shangchuan = mDUWaiFuCBSJ.getShangchuanbz();

            if (chaobiaobz > 0) {
                int icon;
                switch (chaobiaobz) {
                    case 1: // 已抄
                        // icon = mChaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                        // R.drawable.ic_ok
                        // : R.drawable.ic_shangchuan;
                        icon = R.mipmap.ic_ok;
                        break;
//                    case 2: // 开账
                    // icon = mChaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                    // R.drawable.ic_remote_bill
                    // : R.drawable.ic_local_bill;
//                        icon = R.drawable.ic_remote_bill;
//                        break;
//                    case 3: // 延迟抄表
                    // icon = mChaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                    // R.drawable.ic_remote_delay
                    // : R.drawable.ic_local_delay;
//                        icon = R.drawable.ic_remote_delay;
//                        break;
//                    case 4: // 外复延迟
                    // icon = mChaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                    // R.drawable.ic_remote_ex_delay
                    // : R.drawable.ic_local_ex_delay;
//                        icon = R.drawable.ic_remote_ex_delay;
//                        break;
                    default:
                        // icon = mChaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                        // R.drawable.ic_ok
                        // : R.drawable.ic_shangchuan;
                        icon = R.mipmap.ic_ok;
                        break;
                }

                if (mCBBZImageView != null) {
                    mCBBZImageView.setImageDrawable(getResources().getDrawable(
                            icon));
                    mCBBZImageView.setVisibility(View.VISIBLE);
                }

//                mCBBZImageView.setVisibility(View.VISIBLE);
                if (shangchuan == DUWaiFuCBSJ.SHANGCHUANBZ_YISHANGC) {
                    mCBBZImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_ok));
                } else {
                    mCBBZImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_shangchuan));
                }
            } else {
                if (mCBBZImageView != null) {
                    mCBBZImageView.setVisibility(View.GONE);
                }
            }
        } else {
            if (mCBBZImageView != null) {
                mCBBZImageView.setVisibility(View.GONE);
            }
        }
    }

    private void resetLGLDViewsOfResultPanel(boolean clean) {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (!clean) {
//            int lianggaosl = 0;
//            int liangdisl = 0;
//            lianggaosl = mDUWaiFuCBSJ.getLianggaosl();
//            liangdisl = mDUWaiFuCBSJ.getLiangdisl();
            int chaojiansl = TextUtil.getInt(mBCCJSLTextView.getText().toString().trim());
            int averageNumber = mDUWaiFuCBSJ.getPingjunl1();
            if (isWaterHigh(chaojiansl, averageNumber)) {
                mLiangGaoLDYYBM = 999;// 量高

                if (mBCLGTmageView != null) {
                    mBCLGTmageView.setVisibility(View.VISIBLE);
                }

                if (mBCLDImageView != null) {
                    mBCLDImageView.setVisibility(View.GONE);
                }
            }
//            else if (chaojiansl < liangdisl) {
//                mLiangGaoLDYYBM = -999;// 量低
//
//                if (mBCLGTmageView != null) {
//                    mBCLGTmageView.setVisibility(View.GONE);
//                }
//
//                if (mBCLDImageView != null) {
//                    mBCLDImageView.setVisibility(View.VISIBLE);
//                }
//            }
            else {
                mLiangGaoLDYYBM = 0; // 正常

                if (mBCLGTmageView != null) {
                    mBCLGTmageView.setVisibility(View.GONE);
                }

                if (mBCLDImageView != null) {
                    mBCLDImageView.setVisibility(View.GONE);
                }
            }
        } else {
            if (mBCLGTmageView != null) {
                mBCLGTmageView.setVisibility(View.GONE);
            }

            if (mBCLDImageView != null) {
                mBCLDImageView.setVisibility(View.GONE);
            }
        }
    }

    private boolean isWaterHigh(int waterNumber, int averageNumber) {
        double N = mConfigHelper.getWaterHighNumberPro();
        if (0 <= waterNumber && waterNumber < 50) {
            if (waterNumber - averageNumber >= 35) {
                return true;
            }
        } else if (50 <= waterNumber && waterNumber < 100) {
            if (waterNumber - averageNumber >= 50) {
                return true;
            }
        } else if (100 <= waterNumber && waterNumber <= 200) {
            if (waterNumber - averageNumber >= 70) {
                return true;
            }
        } else if (waterNumber > 200) {
            if (waterNumber / averageNumber > N) {
                return true;
            }
        }
        return false;
    }

    private void resetViewsOfInputPanel(boolean clean) {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        ControlItems item = CheckInPutControl.ControlChecked(mChaoBiaoZT.getShuiliangsfbm());
        if (item != null) {
            if (mBCCMTextView != null) {
                mBCCMTextView.setEnabled(item.getChaoMaInPut());
                mCNLRBenCicmSubTextView.setEnabled(item.getChaoMaInPut());
            }

            if (mBCCJSLTextView != null) {
                mBCCJSLTextView.setEnabled(item.getShuiLiangInPut());
            }

            if (mBCCMImageView != null) {
                mBCCMImageView
                        .setVisibility(item.getChaoMaInPut() ? View.VISIBLE
                                : View.INVISIBLE);
            }

            if (mBCCMSubImageView != null) {
                mBCCMSubImageView
                        .setVisibility(item.getChaoMaInPut() ? View.VISIBLE
                                : View.INVISIBLE);
            }

            if (mBCCJSLImageView != null) {
                mBCCJSLImageView
                        .setVisibility(item.getShuiLiangInPut() ? View.VISIBLE
                                : View.INVISIBLE);
            }
        }

//        if (clean) {
//            if (mBCCMTextView != null) {
//                mBCCMTextView.setText("");
//                mCNLRBenCicmSubTextView.setText("");
//            }
//
//            if (mBCCJSLTextView != null) {
//                mBCCJSLTextView.setText("");
//            }
//        }
    }

    private void resetHBQFImageViews() {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if ((mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH)
                /*|| (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_REMOVE)*/) {
            mHBImageView.setVisibility(View.VISIBLE);
        } else {
            mHBImageView.setVisibility(View.INVISIBLE);
        }

        if (mDuCard.getQianfei() == 1) {
            // 获取欠费信息，欠费条数
//            double mQianFeiZJE = 0;
//            for (DUQianFeiXX qianFeiXX : mQianFeiXXList) {
//                mQianFeiZJE += qianFeiXX.getJe();
//            }
//            mTxt_qianfei.setVisibility(View.VISIBLE);
//            mTxt_qianfei.setText(String.valueOf(mQianFeiXXList.size()) + "/"
//                    + mNumberFormat.format(mQianFeiZJE));
            mQFImageView.setVisibility(View.VISIBLE);
        } else {
            mQFImageView.setVisibility(View.GONE);
//            //mTxt_qianfei.setVisibility(View.GONE);
        }

        if (mDuCard.getYonghuzt() == DUCard.JM_YONGHUZT_STOP) {
            mTSImageView.setVisibility(View.VISIBLE);
        } else {
            mTSImageView.setVisibility(View.GONE);
        }
    }

    private void resetTextViews() {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (mCNLRShangCicmSubTextView != null && mCNLRBenCicmSubTextView != null) {
//            if (mDUWaiFuCBSJ.getLastReadingChild() != 0) {
//                mCBLRSubLayout.setVisibility(View.VISIBLE);
//                mChaoMaOneTextView.setText(R.string.text_chaoma_mu);
//                if (mDuRecord.getReadingChild() == 0) {
//                    mCNLRBenCicmSubTextView.setText("");
//                } else {
//                    mCNLRBenCicmSubTextView.setText(String.valueOf(mDuRecord.getReadingChild()));
//                }
//
//                mCNLRShangCicmSubTextView.setText(String.valueOf(mDuRecord.getLastReadingChild()));
//            } else
//            {
            mChaoMaOneTextView.setText(R.string.text_chaoma);
            mCBLRSubLayout.setVisibility(View.GONE);
//            }
        }

        if (mYHBHTextView != null) {
            String cardState = "";
            if (mDuCard.getYonghuzt() == DUCard.JM_YONGHUZT_NORMAL) {
                cardState = getString(R.string.text_meter_state_normal);
            } else if (mDuCard.getYonghuzt() == DUCard.JM_YONGHUZT_CANCLE) {
                cardState = getString(R.string.text_meter_state_cancel);
            } else if (mDuCard.getYonghuzt() == DUCard.JM_YONGHUZT_REMOVE) {
                cardState = getString(R.string.text_meter_state_remove);
            } else if (mDuCard.getYonghuzt() == DUCard.JM_YONGHUZT_STOP) {
                cardState = getString(R.string.text_meter_state_stop);
            }

            String meterState = "";
            if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_NORMAL) {
                meterState = getString(R.string.text_meter_state_normal);
            } else if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_NEW) {
                meterState = getString(R.string.text_meter_state_new);
            } else if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH) {
                meterState = getString(R.string.text_meter_state_switch);
            }

            mYHBHTextView.setText(String.format(ConstDataUtil.LOCALE, "%d/%s/%s/%s",
                    mCeNeiXH, TextUtil.getString(mCid), cardState, meterState));
        }

        if (mHMTextView != null) {
            mHMTextView.setText(TextUtil.getString(mDuCard.getKehumc()));
        }

        if (mDZTextView != null) {
            mDZTextView.setText(TextUtil.getString(mDuCard.getDizhi()));

            /*mIsLocationValid = false;
            String x = mDuCard.getX();
            String y = mDuCard.getY();
            if ((x == null)
                    || (x.equals(TextUtil.EMPTY))
                    || (y == null)
                    || (y.equals(TextUtil.EMPTY))) {
                mIsLocationValid = false;
            } else {
                double longitude = Double.parseDouble(x);
                double latitude = Double.parseDouble(y);
                if ((longitude > 1e-10) && (latitude > 1e-10)) {
                    mIsLocationValid = true;
                } else {
                    mIsLocationValid = false;
                }
            }

            mDZTextView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    mIsLocationValid ? getResources().getDrawable(
                            R.mipmap.ic_btn_address) : null, null);*/
        }

        if (mSBXXTextView != null) {
//            String date = new SimpleDateFormat("yyyy-MM-dd",
//                    Locale.getDefault()).format(mDuCard.getHuanbiaorq());
//            String date;
//            if (mDuCard.getHuanbiaorq() > 0) {
//                date = TextUtil.format(mDuCard.getHuanbiaorq(), TextUtil.FORMAT_DATE);
//            } else if (mDuCard.getZhuangbiaorq() > 0) {
//                date = TextUtil.format(mDuCard.getZhuangbiaorq(), TextUtil.FORMAT_DATE);
//            } else {
//                date = "";
//            }

            String str = String.format("%s/%s/%s/%s/%s/%s",
                    TextUtil.getString(mDuCard.getShuibiaogyh()),
                    TextUtil.getString(mDuCard.getKoujingmc()),
                    mDuCard.getBiaowei(), mDuCard.getJianhao(),
                    mDuCard.getBiaoxing(), mDuCard.getShuibiaocj());
            mSBXXTextView.setText(str);
        }

        if (mSCCBZTTextView != null) {
            mSCCBZTTextView.setText(TextUtil.getString(mDUWaiFuCBSJ.getShangciztmc()));
        }

        if (mSCCBTextView != null) {
            mSCCBTextView.setText(String.valueOf(mDUWaiFuCBSJ.getShangcicm()));
        }

        if (mSCCJSLTextView != null) {
            mSCCJSLTextView.setText(String.valueOf(mDUWaiFuCBSJ.getShangcicjsl()));
        }

        if (mBCCBZTTextView != null) {
            mBCCBZTTextView.setText(TextUtil.getString(mChaoBiaoZT.getZhuangtaimc()));
        }

        if (mBCCMTextView != null) {
//            if (mDUWaiFuCBSJ.getIchaobiaobz() == 0) {
//                mBCCMTextView.setText("");
//            } else {
            mBCCMTextView.setText(String.valueOf(mDUWaiFuCBSJ.getBencicm()));
//            }
        }

        if (mBCCJSLTextView != null) {
//            if (mDUWaiFuCBSJ.getIchaobiaobz() == 0) {
//                mBCCJSLTextView.setText("");
//            } else {
            mBCCJSLTextView.setText(String.valueOf(mDUWaiFuCBSJ.getChaojiansl()));
//            }
        }

        mCheckOutsideTypeData = new ArrayList<>();
        String checkOutsideType = mConfigHelper.getCheckOutsideType();
        String[] checkOutsideTypes = checkOutsideType.split(",");
        if (checkOutsideTypes.length > 0) {
            for (int i = 0; i < checkOutsideTypes.length; i++) {
                mCheckOutsideTypeData.add(checkOutsideTypes[i]);
            }
            mCheckOutsideTypeAdapter = new ArrayAdapter(mOutsideRecordActivity, R.layout.item_check_outside, mCheckOutsideTypeData);
            mCheckOutsideTypeSpinner.setAdapter(mCheckOutsideTypeAdapter);
            mCheckOutsideTypeSpinner.setSelection(mDUWaiFuCBSJ.getCheckOutsideType());
            mCheckOutsideTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mSelectOutsideType = position;
                    if (mSelectOutsideType != mDUWaiFuCBSJ.getCheckOutsideType()){
                        save();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        if (mCBBeiZhuTextView != null) {
            mCBBeiZhuTextView.setText(TextUtils.isEmpty(mDUWaiFuCBSJ.getSchaobiaobz()) ? "" : mDUWaiFuCBSJ.getSchaobiaobz().trim());
        }

//      if (mCBJianHaoTextView != null) {
//          mCBJianHaoTextView.setText(TextUtils.isEmpty(mDUWaiFuCBSJ.getJianhao()) ? "" : mDUWaiFuCBSJ.getJianhao().trim());
//      }

        if (mDUWaiFuCBSJ.getIchaobiaobz() == DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO) {
            lastChaoBiaoInfo = String.format(ConstDataUtil.LOCALE,
                    "%s: %s\n%s: %d\n%s: %s",
                    getString(R.string.label_chaobiaozt),
                    mChaoBiaoZT.getZhuangtaimc(),
                    getString(R.string.text_chaoma),
                    mDUWaiFuCBSJ.getBencicm(),
                    getString(R.string.label_bencisl),
                    mDUWaiFuCBSJ.getChaojiansl());
        } else {
            lastChaoBiaoInfo = null;
        }
    }


    private DUChaoBiaoZT getChaoBiaoZT() {
        if ((mDUWaiFuCBSJ == null)
                || (mDuCard == null)) {
            return null;
        }

//        if (mDUWaiFuCBSJ.getIchaobiaobz() > 0) {
        return getChaoBiaoZT(mDUWaiFuCBSJ.getZhuangtaibm());
//        } else if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH) {
//            return getChaoBiaoZT(NEW_CARD_NAME);
//        } else {
//            return getChaoBiaoZT(DEFAULT_CHAOBIAO_ZTBM);
//        }
    }

    private DUChaoBiaoZT getChaoBiaoZT(int zhuangtaibm) {
        if (mChaoBiaoZTList == null) {
            return null;
        }

        for (DUChaoBiaoZT chaoBiaoZT : mChaoBiaoZTList) {
            if (chaoBiaoZT.getZhuangtaibm() == zhuangtaibm) {
                return chaoBiaoZT;
            }
        }

        return null;
    }

    private DUChaoBiaoZT getChaoBiaoZT(String zhuangtaimc) {
        if ((mChaoBiaoZTList == null)
                || TextUtil.isNullOrEmpty(zhuangtaimc)) {
            return null;
        }

        for (DUChaoBiaoZT chaoBiaoZT : mChaoBiaoZTList) {
            if (chaoBiaoZT.getZhuangtaimc().equals(zhuangtaimc)) {
                return chaoBiaoZT;
            }
        }

        return null;
    }

    @Subscribe
    public void onUpdateRecord(UIBusEvent.UpdateRecord updateRecord) {
        LogUtil.i(TAG, "---onUpdateRecord---");

        String customerId = updateRecord.getCustomerId();
        if (!TextUtil.isNullOrEmpty(customerId)) {
            mCid = customerId;
        }

        mCeNeiXH = updateRecord.getOrderNumber();
    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncDataEnd---");
        if (OutsideRecordActivity.this.isNeedDialog()) {
            OutsideRecordActivity.this.setNeedDialog(false);
            OutsideRecordActivity.this.hideProgress();
        }

        if (syncDataEnd.getSyncType() != SyncType.UPLOADING_OUT_RECORD_MEDIAS) {
            return;
        }

        Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
        if (syncDataInfoMap == null) {
            return;
        }

        // record
        List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_RECORD.getName());
        if ((syncDataInfoList == null) || (syncDataInfoList.size() != 1)) {
            return;
        }

        SyncDataInfo syncDataInfo = syncDataInfoList.get(0);
        if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
            mDUWaiFuCBSJ.setShangchuanbz(DURecord.SHANGCHUANBZ_YISHANGC);
            ApplicationsUtil.showMessage(OutsideRecordActivity.this, R.string.text_uploadRecords_success);
        } else if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
            ApplicationsUtil.showMessage(OutsideRecordActivity.this, R.string.text_uploadRecords_failure);
        }
        resetCBBZViewsOfResultPanel(false);

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
                OutsideRecordActivity.this.getResources().getString(R.string.text_uploadMedia),
                OutsideRecordActivity.this.getResources().getString(R.string.text_success),
                successCount,
                OutsideRecordActivity.this.getResources().getString(R.string.text_failure),
                failureCount);
        ApplicationsUtil.showMessage(OutsideRecordActivity.this, str);
    }

    @Override
    public void onLoadChaoBiaoZTList(List<DUChaoBiaoZT> duChaoBiaoZTList) {

        LogUtil.i(TAG, "---updateChaoBiaoZTList---");
        if ((duChaoBiaoZTList == null) || (duChaoBiaoZTList.size() <= 0)) {
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            LogUtil.i(TAG, "---updateChaoBiaoZTList---error");
            return;
        }

        mChaoBiaoZTList = duChaoBiaoZTList;
        mOutsideRecordPresenter.loadLiangGaoYYList();
    }

    @Override
    public void onLoadLiangGaoYY(List<DUCiYuXX> duCiYuXXList) {

        LogUtil.i(TAG, "---updateLiangGaoYY---");
        mLGCiYuXXList = duCiYuXXList; // may be null or size <= 0
        mOutsideRecordPresenter.loadLiangDiYYList();

    }

    @Override
    public void onLoadLiangDiYY(List<DUCiYuXX> duCiYuXXList) {

        LogUtil.i(TAG, "---updateLiangDiYY---");
        mLDCiYuXXList = duCiYuXXList;
        mOutsideRecordPresenter.loadWaiFuCBSJInfo(DUWaiFuCBSJInfo.FilterType.ONE, mRenWuBH, mCh,
                mCid, mCeNeiXH);
    }

    @Override
    public void onLoadWaiFuCBSJInfo(DUWaiFuCBSJ duWaiFuCBSJ) {

        LogUtil.i(TAG, "---updateRecordInfo---");
        mDUWaiFuCBSJ = duWaiFuCBSJ;
        if (mDUWaiFuCBSJ != null) {
            mFlag = mDUWaiFuCBSJ.getIchaobiaobz();
            mCeNeiXH = mDUWaiFuCBSJ.getCeneixh();
            mCid = mDUWaiFuCBSJ.getCid();
            mOutsideRecordPresenter.loadCardInfo(mCh, mCid);
        } else {
            LogUtil.i(TAG, "---updateRecordInfo---error");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoadCardInfo(DUCard duCard) {

        LogUtil.i(TAG, "---updateCardInfo---");
        mDuCard = duCard;
        if (mDuCard != null) {
            mOutsideRecordPresenter.loadImageInfo(mRenWuBH, mCh, mCid);
        } else {
            LogUtil.i(TAG, "---updateCardInfo---error");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
        }

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
        updateViews();
    }

    @Override
    public void onUpdateWaiFuCBSJ(DUWaiFuCBSJResult duWaiFuCBSJResult) {

        LogUtil.i(TAG, "---onUpdateWaiFuCBSJ---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duWaiFuCBSJResult.getSuccessCount() == 1) {
            ApplicationsUtil.showMessage(mOutsideRecordActivity,
                    R.string.text_info_chaobiaolr_savesuccess);

            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateVolumeList());//通知册本界面更新列表
//            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateTaskList());//通知抄表任务界面更新列表
            resetCBBZViewsOfResultPanel(false);
//            resetUploadingButton();
        } else {
            ApplicationsUtil.showMessage(mOutsideRecordActivity,
                    R.string.text_info_chaobiaolr_savefailed);
        }
    }

    @Override
    public void onSaveNewImage(DUMedia duMedia) {

        LogUtil.i(TAG, "---onSaveNewImage---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if ((duMedia != null)
                && (!TextUtil.isNullOrEmpty(duMedia.getWenjianlj()))
                && (!TextUtil.isNullOrEmpty(duMedia.getWenjianmc()))) {
            mImgPathList.add(duMedia.getWenjianlj());
            mDuoMeiTXXList.add(duMedia);
            ApplicationsUtil.showMessage(mOutsideRecordActivity,
                    R.string.text_success_save_picture);
        } else {
            LogUtil.i(TAG, "---onSaveNewImage---parameter is error");
            ApplicationsUtil.showMessage(mOutsideRecordActivity,
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
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---onDeleteImage---" + e.getMessage());
        }
    }

    @Override
    public void onError(String message) {

        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (!TextUtil.isNullOrEmpty(message)) {
            LogUtil.i(TAG, message);
            ApplicationsUtil.showMessage(mOutsideRecordActivity, message);
        }

    }

    @Override
    public void onSwitchRecordError(boolean isNext) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (isNext) {
            mEndXH = mCeNeiXH;
        } else {
            mStartXH = mCeNeiXH;
        }

        updateViews();
    }

    @Subscribe
    public void onUploadCardCoordinateResult(UIBusEvent.UploadCardCoordinateResult uploadCardCoordinateResult) {
        LogUtil.i(TAG, "---onUploadCardCoordinateResult 1---");
        if (uploadCardCoordinateResult == null) {
            return;
        }

        int taskId = uploadCardCoordinateResult.getTaskId();
        String volume = uploadCardCoordinateResult.getVolume();
        String customerId = uploadCardCoordinateResult.getCustomerId();
        boolean isSuccess = uploadCardCoordinateResult.isSuccess();
        if (!TextUtil.isNullOrEmpty(customerId)) {
            ApplicationsUtil.showMessage(this,
                    String.format("%s: %s %s",
                            getResources().getString(R.string.text_card_id),
                            customerId,
                            isSuccess ? getResources().getString(R.string.text_success_to_change_coordinate)
                                    : getResources().getString(R.string.text_failure_to_change_coordinate)));
        }
    }
}
