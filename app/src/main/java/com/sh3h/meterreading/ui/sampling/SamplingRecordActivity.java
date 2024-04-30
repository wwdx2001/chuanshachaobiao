package com.sh3h.meterreading.ui.sampling;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
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
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingInfo;
import com.sh3h.datautil.data.entity.DUSamplingResult;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SamplingRecordActivity extends ParentActivity implements
        View.OnClickListener, MenuItem.OnMenuItemClickListener, com.sh3h.meterreading.ui.sampling.SamplingRecordMvpView {
    private static final String TAG = "RecordActivity";
    private final static int DEFAULT_CHAOBIAO_ZTBM_0 = 0;
    private final static int DEFAULT_CHAOBIAO_ZTBM_1 = 1;
    private final static String NEW_CARD_NAME = "新表";
    private final static int LIANGGAOLDYYBM_LG = 999;
    private final static int LIANGGAOLDYYBM_LD = -999;

    @Inject
    Bus mEventBus;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    SamplingRecordPresenter samplingRecordPresenter;

    @BindView(R.id.inspect_record_lr)
    RadioButton mRecordLRRadioButton;

    @BindView(R.id.inspect_record_detail_info)
    RadioButton mDetailInfoRadioButton;

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

    // 本次抄表状态
    @BindView(R.id.fcblr_chaobiaoztbc)
    TextView mBCCBZTTextView;
    // 本次抄码
    @BindView(R.id.fcblr_bencicm)
    TextView mBCCBTextView;

    // 本次次抄见水量
    @BindView(R.id.fcblr_chaojianslbc)
    TextView mBCCJSLTextView;

    // 上次抄表状态
    @BindView(R.id.fcblr_chaobiaoztsc)
    TextView mSCCBZTTextView;

    // 上次抄码
    @BindView(R.id.fcblr_shangcicm)
    TextView mSCCBTextView;

    // 上次抄见水量
    @BindView(R.id.fcblr_chaojianslsc)
    TextView mSCCJSLTextView;

    // 稽查抄表状态
    @BindView(R.id.fcblr_chaobiaoztcj)
    TextView mJCCBZTTextView;

    // 稽查抄码
    @BindView(R.id.fcblr_choujiancm)
    TextView mJCCMTextView;

    // 稽查抄见水量
    @BindView(R.id.fcblr_chaojianslchoujian)
    TextView mJCCJSLTextView;

    // 子表（上次抄码 & 本次抄码）
    @BindView(R.id.fcblr_sub)
    RelativeLayout mCBLRSubLayout;

    @BindView(R.id.fcblr_chaoma1)
    TextView mChaoMaOneTextView;

    // 子表（上次抄码）
    @BindView(R.id.fcblr_shangcicm_sub)
    TextView mCNLRShangCicmSubTextView;

    // 子表（本次抄码）
    @BindView(R.id.fcblr_choujiancm_sub)
    TextView mCNLRBenCicmSubTextView;

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

    private static final int CAPTURE_IMAGE = 1000;
    private static final long NUMBER_MAX = 1000000000;

    private String _cH;
    private String _cID;
    private int _renWuBH;
    private int mCeNeiXH;
    private int mStartXH;
    private int mEndXH;
    private String mStartCID;
    private String mEndCID;
    private ArrayList<String> cids;
    private boolean isCanInput = false;

    private int _mLastReadingChild;
    private int _yiChaoShu;
    private boolean mIsFromTask;
    private boolean mIsModified;

    private boolean mIsMandatoryPhoto;
    private boolean isInitSuccess;
    private String mFileName = null;
    private DUSampling mDUSampling = null;
    private DUCard mDuCard = null;
    private DUChaoBiaoZT mChaoBiaoZT;

    private List<String> mImgPathList = null;
    private List<DUMedia> mDuoMeiTXXList = null;
    private List<ImageView> mImageViewList = null;
    private List<DUChaoBiaoZT> mChaoBiaoZTList = null;
    private List<DUCiYuXX> mLDCiYuXXList = null;
    private List<DUCiYuXX> mLGCiYuXXList = null;
    private List<DUCiYuXX> mBZCiYuXXList = null;

    private PopupWindowImageAdapter mAdapter = null;
    private ReadingParameter mReadingParameter = null;
    private String lastChaoBiaoInfo = null;
    private int mLiangGaoLDYYBM = 0;

    private Camera camera;
    private boolean isFlashLightOpen = false;

    private EditText editText = null;
    private String mBeizhu = "";

    public SamplingRecordActivity() {
        isInitSuccess = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        int viewLayout = mConfigHelper.isLeftOrRightOperation() ?
                R.layout.activity_inspect_sampling_left : R.layout.activity_inspect_sampling_right;
        setContentView(viewLayout);
        ButterKnife.bind(this);
        setActionBarBackButtonEnable();
        mEventBus.register(this);
        samplingRecordPresenter.attachView(this);

        cids = new ArrayList<>();
        getIntentData(savedInstanceState);
        initOnclickListener();

        mImgPathList = new ArrayList<>();
        mDuoMeiTXXList = new ArrayList<>();
        mReadingParameter = new ReadingParameter();

        isInitSuccess = false;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        samplingRecordPresenter.loadChaoBiaoZTList();

        postRecordingEvent(true);
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtil.i(TAG, "---onActivityResult---1");
        processResult(requestCode, resultCode, data);
        LogUtil.i(TAG, "---onActivityResult---2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "---onDestroy---");

        postRecordingEvent(false);
//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        samplingRecordPresenter.detachView();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!canLeave()) {
                return true;
            }
        }

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
                        TextUtil.getString(_cID));
                intent.putExtra(CustomerInformationActivity.S_CH,
                        TextUtil.getString(_cH));
                startActivity(intent);
                setRadioButtonChecked(true, false);
                break;
            case R.id.fcblr_dizhi2:
                jump2MapActivity(true);
                break;
            case R.id.fcblr_chaobiaoztcj:
                popupChaoBiaoZT();
                break;
            case R.id.fcblr_chaojianslchoujian:
                if (mJCCMTextView.isEnabled()) {
                    isCalculatingCMSL = false;
                }
                popupKeyboard(false, isCalculatingCMSL);
                break;
            case R.id.fcblr_choujiancm_sub:
                if (mJCCJSLTextView.isEnabled()) {
                    isCalculatingCMSL = false;
                }
                popupKeyboard(true, isCalculatingCMSL);
                break;
            case R.id.fcblr_choujiancm:
                if (_mLastReadingChild == 0) {
                    mChaoMaOneTextView.setText(R.string.text_chaoma);
                    mCBLRSubLayout.setVisibility(View.GONE);

//                    if (mJCCJSLTextView.isEnabled()) {
//                        isCalculatingCMSL = false;
//                    }
                    samplingRecordPresenter.isCanInput(getCurrentDate(), mDUSampling.getSchaobiaoy());
//                    popupKeyboard(true, isCalculatingCMSL);
                } else {
                    mChaoMaOneTextView.setText(R.string.text_chaoma_mu);
                    mCBLRSubLayout.setVisibility(View.VISIBLE);
                    popupKeyboard();
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
            case R.id.fcblr_flashlight:
                openFlashLight();
                break;
            case R.id.fcblr_chaobiaoqkbc:
                popupLastChaoBiaoInfo();
                break;
            case R.id.fcblr_map_address:
                jump2MapActivity(false);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void isCanInput(boolean isCan) {
        isCanInput = isCan;
        if (!isCanInput) {
            LogUtil.i(TAG, "---popupKeyboard---");
            ApplicationsUtil.showMessage(SamplingRecordActivity.this, R.string.text_syncjicharecord);
            return;
        }
        boolean isCalculatingCMSL = true;
        if (mJCCJSLTextView.isEnabled()) {
            isCalculatingCMSL = false;
        }
        popupKeyboard(true, isCalculatingCMSL);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (canLeave()) {
                    finish();
                }

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
        outState.putString(ConstDataUtil.S_CH, _cH);
        outState.putString(ConstDataUtil.S_CID, _cID);
        outState.putInt(ConstDataUtil.I_RENWUBH, _renWuBH);
        outState.putInt(ConstDataUtil.I_LASTREADINGCHILD, _mLastReadingChild);
        outState.putInt(ConstDataUtil.I_CENEIXH, mCeNeiXH);
        outState.putInt(ConstDataUtil.STARTXH, mStartXH);
        outState.putInt(ConstDataUtil.ENDXH, mEndXH);
        outState.putString(ConstDataUtil.STARTCID, mStartCID);
        outState.putString(ConstDataUtil.ENDCID, mEndCID);
//      outState.putInt(CeBenListActivity.DATA_TYPE, mDataType);
        outState.putInt(ConstDataUtil.YICHAOSHU, _yiChaoShu);
        outState.putBoolean(ConstDataUtil.FROM_TASK, mIsFromTask);
        outState.putBoolean(ConstDataUtil.IS_MODIFIED, mIsModified);

        LogUtil.i(TAG, "---onSaveInstanceState---");
    }

    @Subscribe
    public void onUpdateRecord(UIBusEvent.UpdateRecord updateRecord) {
        LogUtil.i(TAG, "---onUpdateRecord---");

        String customerId = updateRecord.getCustomerId();
        if (!TextUtil.isNullOrEmpty(customerId)) {
            _cID = customerId;
        }

        mCeNeiXH = updateRecord.getOrderNumber();
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
        samplingRecordPresenter.loadLiangGaoYYList();
    }

    @Override
    public void onLoadLiangGaoYY(List<DUCiYuXX> duCiYuXXList) {
        LogUtil.i(TAG, "---updateLiangGaoYY---");

        mLGCiYuXXList = duCiYuXXList; // may be null or size <= 0
        samplingRecordPresenter.loadLiangDiYYList();
    }

    @Override
    public void onLoadLiangDiYY(List<DUCiYuXX> duCiYuXXList) {
        LogUtil.i(TAG, "---updateLiangDiYY---");

        mLDCiYuXXList = duCiYuXXList;

//      samplingRecordPresenter.loadBeiZhuYYList();
        samplingRecordPresenter.loadRecordInfo(DUSamplingInfo.FilterType.ONE, _renWuBH, _cH,
                _cID, mCeNeiXH);
    }

    @Override
    public void onLoadBeiZhuYY(List<DUCiYuXX> duCiYuXXList) {
//        mBZCiYuXXList = duCiYuXXList;
//        samplingRecordPresenter.loadRecordInfo(DUSamplingInfo.FilterType.ONE, _renWuBH, _cH,
//                _cID, mCeNeiXH);
    }

    @Override
    public void onLoadRecordInfo(DUSampling duSampling) {
        LogUtil.i(TAG, "---updateRecordInfo---");
        mDUSampling = duSampling;
        if (mDUSampling != null) {
            mCeNeiXH = mDUSampling.getCeneixh();
            _cID = mDUSampling.getCid();
            samplingRecordPresenter.loadCardInfo(_cH, _cID);
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
            samplingRecordPresenter.loadImageInfo(_renWuBH, _cH, _cID);
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

//        if (isInitSuccess && (mDuCard.getBackflow() == DUCard.BACKFLOW_FIXED)) {
//            jump2AvoidBackFlowCheckActivity();
//        }
    }

    @Override
    public void onUpdateRecord(DUSamplingResult duSamplingResult) {
        LogUtil.i(TAG, "---onUpdateRecord---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duSamplingResult.getSuccessCount() == 1) {
            ApplicationsUtil.showMessage(this,
                    R.string.text_info_chaobiaolr_savesuccess);

            if (mIsFromTask) {
                for (int i = 0; i < cids.size(); i++) {
                    if (duSamplingResult.getCid().equals(cids.get(i)) && i != cids.size() - 1) {
//                        mStartCID = cids.get(i + 1);
                        cids.remove(i);
                        break;
                    }
                }
                mStartCID = cids.get(0);
                mEndCID = cids.get(cids.size() - 1);
            }

            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateSamplingList());//通知稽查列表界面更新列表
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateSamplingTask());//通知稽查任务界面更新列表
            resetCBBZViewsOfResultPanel(false);

            setIsModified(true);
        } else {
            ApplicationsUtil.showMessage(this,
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
            ApplicationsUtil.showMessage(this,
                    R.string.text_success_save_picture);

            setIsModified(true);
        } else {
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

            setIsModified(true);
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
            ApplicationsUtil.showMessage(this, message);
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

    @Override
    public void onUpdateCard(Boolean aBoolean) {
        LogUtil.i(TAG, "---onUpdateCard---");

        if (aBoolean) {
            ApplicationsUtil.showMessage(this,
                    R.string.text_info_chaobiaolr_savesuccess);

            mMapAddress.setVisibility(View.VISIBLE);
            uploadCardCoordinate(
                    _renWuBH,
                    _cH,
                    _cID,
                    TextUtil.getDouble(mDuCard.getX1()),
                    TextUtil.getDouble(mDuCard.getY1()));
        } else {
            ApplicationsUtil.showMessage(this,
                    R.string.text_info_chaobiaolr_savefailed);
        }
    }

    private void postRecordingEvent(boolean isRecording) {
        mEventPosterHelper.postEventSafely(new UIBusEvent.Recording(isRecording ? _cH : ""));
    }

    private void processResult(int requestCode, int resultCode, Intent data) {
        if (CAPTURE_IMAGE == requestCode &&
                _cH != null &&
                _cID != null &&
                mFileName != null) {
            LogUtil.i(TAG, "---processResult---");
            File folder = mConfigHelper.getImageFolderPath();
            File dir = new File(folder, _cH);
            if (!dir.exists()) {
                LogUtil.i(TAG, String.format("---processResult---dir:%s isn't existing",
                        _cH));
                return;
            }

            File file = new File(dir, mFileName);
            if (!file.exists()) {
                LogUtil.i(TAG, String.format("---processResult---file:%s isn't existing",
                        mFileName));
                return;
            }

            DUMedia duoMeiTXX = new DUMedia();
            duoMeiTXX.setCid(_cID);
            duoMeiTXX.setChaobiaoid(mDUSampling.getChaobiaoid());
            duoMeiTXX.setWenjianlx(DUMedia.WENJIANLX_PIC);
            duoMeiTXX.setWenjianmc(mFileName);
            duoMeiTXX.setType(DUMedia.MEDIA_TYPE_SAMPLING);
            duoMeiTXX.setRenwubh(_renWuBH);
            duoMeiTXX.setCh(_cH);
            duoMeiTXX.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
            duoMeiTXX.setCreaterq(MainApplication.get(this).getCurrentTime());
            duoMeiTXX.setWenjianlj(file.getAbsolutePath());
            duoMeiTXX.setAccount(mPreferencesHelper.getUserSession().getAccount());
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getAbsolutePath()}, null, null);

            mSmoothProgressBar.setVisibility(View.VISIBLE);
            samplingRecordPresenter.saveNewImage(duoMeiTXX);
        } else if ((requestCode == MapParamEx.REQUEST_CODE)
                && (resultCode == MapParamEx.RESULT_CODE)
                && (data != null)) {
            LogUtil.i(TAG, "---processResult---map");
            double longitude = data.getDoubleExtra(MapParamEx.LONGITUDE, 0);
            double latitude = data.getDoubleExtra(MapParamEx.LATITUDE, 0);
            if ((longitude <= MapParamEx.ERROR_VALUE)
                    || (latitude <= MapParamEx.ERROR_VALUE)) {
                ApplicationsUtil.showMessage(this,
                        R.string.text_select_coordinate_failure);
                return;
            }

            mDuCard.setX1(String.valueOf(longitude));
            mDuCard.setY1(String.valueOf(latitude));
            samplingRecordPresenter.updateCard(mDuCard);
        } else {
            LogUtil.i(TAG, "---processResult---parameter is null");
        }
    }

    private void loadImages() {
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
            samplingRecordPresenter.loadImageViews(mImgPathList, this);
        }
    }

    private boolean canLeave() {
        if (!mIsMandatoryPhoto) {
            return true;
        }

        if (mIsModified) {
            if (mDuoMeiTXXList.size() <= 0) {
                ApplicationsUtil.showMessage(this,
                        R.string.text_take_picture_firstly);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void switchPreviousOne() {
        LogUtil.i(TAG, "---switchPreviousOne---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---switchPreviousOne---error");
            return;
        }

        if (mCeNeiXH <= mStartXH) {
            LogUtil.i(TAG, "---switchPreviousOne---first one");
            return;
        }

        if (!canLeave()) {
            return;
        }

        isInitSuccess = false;
        mImageViewList = null;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        samplingRecordPresenter.loadRecordInfo(
                mIsFromTask ? DUSamplingInfo.FilterType.PREVIOUS_ONE_NOT_READING : DUSamplingInfo.FilterType.PREVIOUS_ONE,
                _renWuBH,
                _cH, _cID, mCeNeiXH);
    }

    private void switchNextOne() {
        LogUtil.i(TAG, "---switchNextOne---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---switchNextOne---error");
            return;
        }

        if (mCeNeiXH >= mEndXH) {
            LogUtil.i(TAG, "---switchPreviousOne---last one");
            return;
        }

        if (!canLeave()) {
            return;
        }

        isInitSuccess = false;
        mImageViewList = null;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        samplingRecordPresenter.loadRecordInfo(
                mIsFromTask ? DUSamplingInfo.FilterType.NEXT_ONE_NOT_READING : DUSamplingInfo.FilterType.NEXT_ONE,
                _renWuBH,
                _cH, _cID, mCeNeiXH);
    }

    private void displayImages() {
        mAdapter = new PopupWindowImageAdapter(mImageViewList);
        Window window = getWindow();
        PopupWindowMenu popupWindowMenuNJXX = new PopupWindowMenu(this, window);
        popupWindowMenuNJXX.popupWindowImageViwe(mJCCMTextView,
                PopupWindowMenu.ATLOCATION_TOP, AbsListView.LayoutParams.FILL_PARENT,
                AbsListView.LayoutParams.FILL_PARENT, "图片", mAdapter,
                mConfigHelper.getImageFolderPath(),
                _cH, mDuoMeiTXXList, new IBInvoke() {
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
                                samplingRecordPresenter.deleteImage(index, name, _cH);
                            }
                        }
                    }

                    @Override
                    public void after() {
                    }
                });
    }

    private void updateViews() {
        mChaoBiaoZT = getChaoBiaoZT();
        if (mChaoBiaoZT == null) {
            return;
        }

        //获取抄表事件
        resetRecordInfo();

        //子母表只计算抄表正常状态，无其他抄表状态
        if (_mLastReadingChild > 0) {
            mBCCBZTTextView.setClickable(false);
        } else {
            mBCCBZTTextView.setClickable(true);
        }

        if (isCoordinateValid()) {
            mMapAddress.setVisibility(View.VISIBLE);
        } else {
            mMapAddress.setVisibility(View.GONE);
        }

        if ((mDUSampling != null)
                && (mDuCard != null)
                && (mChaoBiaoZT != null)
                && (mReadingParameter != null)
                && (mImgPathList != null)
                && (mDuoMeiTXXList != null)) {
            isInitSuccess = true;
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateRecord(_cID, mCeNeiXH));
        } else {
            isInitSuccess = false;
        }

        setIsModified(false);

        mIsMandatoryPhoto = false;
//        String region = mConfigHelper.getRegion();
//        if ((!TextUtil.isNullOrEmpty(region)) && region.equals(SystemConfig.REGION_CHANGSHU)) {
//            if (mDuCard.getBackflow() == DUCard.BACKFLOW_FIXED) {
//                mAvoidBackflowButton.setVisibility(View.VISIBLE);
//            } else {
//                mAvoidBackflowButton.setVisibility(View.GONE);
//            }
//
//            if (mDuCard.getUsertype() == DUCard.USER_TYPE_VIP) {
//                mIsMandatoryPhoto = true;
//            }
//        } else {
//            mAvoidBackflowButton.setVisibility(View.GONE);
//        }
//        mAvoidBackflowButton.setVisibility(View.GONE);
    }

    private void setIsModified(boolean isModified) {
        mIsModified = isModified;

        if (mIsModified && mIsMandatoryPhoto) {
            if (mDuoMeiTXXList.size() <= 0) {
                setSwipeBackEnable(false);
            } else {
                setSwipeBackEnable(true);
            }
        } else {
            setSwipeBackEnable(true);
        }
    }

    private void resetRecordInfo() {
        if ((mDUSampling == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        resetHBQFImageViews();

        resetTextViews();

        if (mDUSampling.getIchaobiaobz() == 0) {
            resetViewsOfInputPanel(true);
//            resetLGLDViewsOfResultPanel(true);
            resetCBBZViewsOfResultPanel(true);
        } else {
            resetViewsOfInputPanel(false);
//            resetLGLDViewsOfResultPanel(false);
            resetCBBZViewsOfResultPanel(false);
        }

        resetButtons();
    }

    private void resetButtons() {
        if ((mDUSampling == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        mPrevButton.setEnabled(true);
        mPrevButton.setVisibility(View.VISIBLE);
        mNextButton.setEnabled(true);
        mNextButton.setVisibility(View.VISIBLE);


        if (mPrevButton != null) {
            if (mStartCID.equals(_cID)) {
                mPrevButton.setEnabled(false);
                mPrevButton.setVisibility(View.INVISIBLE);
                mNextButton.setEnabled(true);
                mNextButton.setVisibility(View.VISIBLE);
            }
        }

        if (mNextButton != null) {
            if (mEndCID.equals(_cID)) {
                mPrevButton.setEnabled(true);
                mPrevButton.setVisibility(View.VISIBLE);
                mNextButton.setEnabled(false);
                mNextButton.setVisibility(View.INVISIBLE);
            }
        }

        if (mPrevButton != null
                && mNextButton != null
                && mStartCID.equals(_cID)
                && mEndCID.equals(_cID)) {
            mPrevButton.setEnabled(false);
            mPrevButton.setVisibility(View.INVISIBLE);
            mNextButton.setEnabled(false);
            mNextButton.setVisibility(View.INVISIBLE);
        }
    }

    private void resetCBBZViewsOfResultPanel(boolean clean) {
        if ((mDUSampling == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (!clean) {
            int chaobiaobz;
            int shangchuan;

            chaobiaobz = mDUSampling.getIchaobiaobz();
            shangchuan = mDUSampling.getShangchuanbz();

            if (chaobiaobz > 0) {
                int icon;
                switch (chaobiaobz) {
                    case 1: // 已抄
                        icon = R.mipmap.ic_ok;
                        break;
                    default:
                        icon = R.mipmap.ic_ok;
                        break;
                }

                if (mCBBZImageView != null) {
                    mCBBZImageView.setImageDrawable(getResources().getDrawable(
                            icon));
                    mCBBZImageView.setVisibility(View.VISIBLE);
                }

                if (shangchuan == DURecord.SHANGCHUANBZ_YISHANGC) {
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
        if ((mDUSampling == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (!clean) {
            int lianggaosl;
            int liangdisl;
            lianggaosl = mDUSampling.getLianggaosl();
            liangdisl = mDUSampling.getLiangdisl();
            int chaojiansl = TextUtil.getInt(mBCCJSLTextView.getText().toString().trim());

            if (chaojiansl > lianggaosl) {
                mLiangGaoLDYYBM = LIANGGAOLDYYBM_LG;// 量高

                if (mBCLGTmageView != null) {
                    mBCLGTmageView.setVisibility(View.VISIBLE);
                }

                if (mBCLDImageView != null) {
                    mBCLDImageView.setVisibility(View.GONE);
                }
            } else if (chaojiansl < liangdisl) {
                mLiangGaoLDYYBM = LIANGGAOLDYYBM_LD;// 量低

                if (mBCLGTmageView != null) {
                    mBCLGTmageView.setVisibility(View.GONE);
                }

                if (mBCLDImageView != null) {
                    mBCLDImageView.setVisibility(View.VISIBLE);
                }
            } else {
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

    private void resetViewsOfInputPanel(boolean clean) {
        if ((mDUSampling == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        ControlItems item = CheckInPutControl.ControlChecked(mChaoBiaoZT.getShuiliangsfbm());
        if (item != null) {
            if (mJCCMTextView != null) {
                mJCCMTextView.setEnabled(item.getChaoMaInPut());
                mCNLRBenCicmSubTextView.setEnabled(item.getChaoMaInPut());
            }

            if (mJCCJSLTextView != null) {
                mJCCJSLTextView.setEnabled(item.getShuiLiangInPut());
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

        if (clean) {
            if (mJCCMTextView != null) {
                mJCCMTextView.setText("");
                mCNLRBenCicmSubTextView.setText("");
            }

            if (mJCCJSLTextView != null) {
                mJCCJSLTextView.setText("");
            }
        }
    }

    private void resetTextViews() {
        if ((mDUSampling == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (mCNLRShangCicmSubTextView != null && mCNLRBenCicmSubTextView != null) {
            if (mDUSampling.getLastReadingChild() != 0) {
                mCBLRSubLayout.setVisibility(View.VISIBLE);
                mChaoMaOneTextView.setText(R.string.text_chaoma_mu);
                if (mDUSampling.getReadingChild() == 0) {
                    mCNLRBenCicmSubTextView.setText("");
                } else {
                    mCNLRBenCicmSubTextView.setText(String.valueOf(mDUSampling.getReadingChild()));
                }

                mCNLRShangCicmSubTextView.setText(String.valueOf(mDUSampling.getLastReadingChild()));
            } else {
                mChaoMaOneTextView.setText(R.string.text_chaoma);
                mCBLRSubLayout.setVisibility(View.GONE);
            }
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
                    mCeNeiXH, TextUtil.getString(_cID), cardState, meterState));
        }

        if (mHMTextView != null) {
            mHMTextView.setText(TextUtil.getString(mDuCard.getKehumc()));
        }

        if (mDZTextView != null) {
            mDZTextView.setText(TextUtil.getString(mDuCard.getDizhi()));
        }

        if (mSBXXTextView != null) {
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
        if (mBCCBZTTextView != null) {
            mBCCBZTTextView.setText(TextUtil.getString(mDUSampling.getZhuangtaimc()));
        }
        if (mBCCBTextView != null) {
            mBCCBTextView.setText(String.valueOf(mDUSampling.getBencicm()));
        }
        if (mBCCJSLTextView != null) {
            mBCCJSLTextView.setText(String.valueOf(mDUSampling.getChaojiansl()));
        }
        if (mSCCBTextView != null) {
            mSCCBTextView.setText(String.valueOf(mDUSampling.getShangcicm()));
        }
        if (mSCCBZTTextView != null) {
            mSCCBZTTextView.setText(TextUtil.getString(mDUSampling.getShangciztmc()));
        }
        if (mSCCJSLTextView != null) {
            mSCCJSLTextView.setText(String.valueOf(mDUSampling.getShangcicjsl()));
        }

        if (mJCCBZTTextView != null) {

            if (mDUSampling.getIchaobiaobz() == 0) {
                mJCCBZTTextView.setText(mChaoBiaoZT.getZhuangtaimc());
            } else {
                mJCCBZTTextView.setText(String.valueOf(mDUSampling.getJiChaZTMC()));
            }
        }

        if (mJCCMTextView != null) {
            if (mDUSampling.getIchaobiaobz() == 0) {
                mJCCMTextView.setText("");
            } else {
                mJCCMTextView.setText(String.valueOf(mDUSampling.getJiChaCM()));
            }
        }

        if (mJCCJSLTextView != null) {
            if (mDUSampling.getIchaobiaobz() == 0) {
                mJCCJSLTextView.setText("");
            } else {
                mJCCJSLTextView.setText(String.valueOf(mDUSampling.getJiChaSL()));
            }
        }

        if (mCBBeiZhuTextView != null) {
            mCBBeiZhuTextView.setText(TextUtils.isEmpty(mDUSampling.getSchaobiaobz()) ? "" : mDUSampling.getSchaobiaobz().trim());
        }

        if (mDUSampling.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO) {
            lastChaoBiaoInfo = String.format(ConstDataUtil.LOCALE,
                    "%s: %s\n%s: %d\n%s: %s",
                    getString(R.string.label_chaobiaozt),
                    mChaoBiaoZT.getZhuangtaimc(),
                    getString(R.string.text_chaoma),
                    mDUSampling.getBencicm(),
                    getString(R.string.label_bencisl),
                    mDUSampling.getChaojiansl());
        } else {
            lastChaoBiaoInfo = null;
        }
    }

    private void resetHBQFImageViews() {
        if ((mDUSampling == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH) {
            mHBImageView.setVisibility(View.VISIBLE);
        } else {
            mHBImageView.setVisibility(View.INVISIBLE);
        }

        if (mDuCard.getQianfeizbs() > 0) {
            mQFImageView.setVisibility(View.VISIBLE);
        } else {
            mQFImageView.setVisibility(View.GONE);
        }

        if (mDuCard.getYonghuzt() == DUCard.JM_YONGHUZT_STOP) {
            mTSImageView.setVisibility(View.VISIBLE);
        } else {
            mTSImageView.setVisibility(View.GONE);
        }
    }

    private void popupLastChaoBiaoInfo() {
        if (!TextUtil.isNullOrEmpty(lastChaoBiaoInfo)) {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(lastChaoBiaoInfo)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .show();
        }
    }

    private boolean isCoordinateValid() {
        if (mDuCard == null) {
            return false;
        }

        String x1 = mDuCard.getX1();
        String y1 = mDuCard.getY1();
        double longitude = TextUtil.getDouble(x1);
        double latitude = TextUtil.getDouble(y1);
        return (longitude <= MapParamEx.ERROR_VALUE)
                && (latitude <= MapParamEx.ERROR_VALUE);
    }

    private DUChaoBiaoZT getChaoBiaoZT() {
        if ((mDUSampling == null)
                || (mDuCard == null)) {
            return null;
        }

        if (mDUSampling.getIchaobiaobz() > 0) {
            return getChaoBiaoZT(mDUSampling.getZhuangtaibm());
        } else if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH) {
            return getChaoBiaoZT(NEW_CARD_NAME);
        } else {
            String region = mConfigHelper.getRegion();
            switch (region) {
                case SystemConfig.REGION_CHANGSHU:
                    return getChaoBiaoZT(DEFAULT_CHAOBIAO_ZTBM_1);
                default:
                    return getChaoBiaoZT(DEFAULT_CHAOBIAO_ZTBM_1);
            }
        }
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

    private void jump2MapActivity(boolean isMarkMap) {
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---jump2MapActivity---error");
            return;
        }

//        String region = mConfigHelper.getRegion();
//        boolean isBaiduMap = (!TextUtil.isNullOrEmpty(region)) && region.equals(SystemConfig.REGION_SUZHOU);

        if (isMarkMap) {
            double longitude, latitude;
            if (isCoordinateValid()) {
                longitude = TextUtil.getDouble(mDuCard.getX1());
                latitude = TextUtil.getDouble(mDuCard.getY1());
            } else {
                boolean isGpsLocated = MainApplication.get(this).isGpsLocated();
                GpsLocation.MRLocation mrLocation = MainApplication.get(this).getMRLocation();
                if (isGpsLocated && (mrLocation != null)) {
                    longitude = mrLocation.getLongitude();
                    latitude = mrLocation.getLatitude();
                } else {
                    longitude = mConfigHelper.getDefaultLongitude();
                    latitude = mConfigHelper.getDefaultLatitude();
                }
            }

            markMap(longitude, latitude);
            return;
        }

        if (!isCoordinateValid()) {
            ApplicationsUtil.showMessage(this, R.string.text_coordinate_invalid);
            return;
        }

        String x1 = mDuCard.getX1();
        String y1 = mDuCard.getY1();
        String address = mDuCard.getDizhi();
        locateMap(TextUtil.getDouble(x1), TextUtil.getDouble(y1), address);
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
                this,
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
                            ApplicationsUtil.showMessage(SamplingRecordActivity.this, R.string.text_not_new_card);
                            return;
                        }

                        if ((mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH)
                                && (!zhuangtaimc.equals(NEW_CARD_NAME))) {
                            ApplicationsUtil.showMessage(SamplingRecordActivity.this, R.string.text_new_card);
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

                            mJCCBZTTextView.setText(zhuangtaimc);
                            mChaoBiaoZT = chaoBiaoZT;
                            int bencicm = TextUtil.getInt(mJCCMTextView.getText().toString().trim());
                            int chaojiansl = TextUtil.getInt(mJCCJSLTextView.getText().toString().trim());
                            resetViewsOfInputPanel(true);
//                            resetLGLDViewsOfResultPanel(true);
                            resetCBBZViewsOfResultPanel(true);

                            if (mDUSampling.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO) {
                                mJCCMTextView.setText(String.valueOf(bencicm));
                                mJCCJSLTextView.setText(String.valueOf(chaojiansl));
                                calcuteCMSL();
                            } else if (chaoBiaoZT.getShuiliangsfbm() == IReadAlgorithm.ALGORITHM_CHAOMABUBIAN) {
                                calcuteCMSL();
                            }
                        }
                    }
                }).show();
    }

    private void calcuteCMSL() {
        LogUtil.i(TAG, "---calcuteCMSL---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---calcuteCMSL---failure");
            return;
        }

        mReadingParameter.reset();
        mReadingParameter.set_huanbiaohtqrsj(TextUtil.format(mDUSampling.getShangcicbrq()));
        mReadingParameter.set_shangcicbrq(mDUSampling.getShangcicbrq());
        mReadingParameter.set_xinbiaodm(mDUSampling.getXinbiaodm());
        mReadingParameter.set_jiubiaocm(mDUSampling.getJiubiaocm());
        mReadingParameter.set_shangcicm(mDUSampling.getShangcicm());
        mReadingParameter.set_bencicm(TextUtil.getInt(mJCCMTextView.getText().toString().trim()));
        mReadingParameter.set_bencicbrq(mDUSampling.getChaobiaorq());
        mReadingParameter.set_bencisl(mDUSampling.getChaojiansl());
        mReadingParameter.set_chaobiaoztbm(String.valueOf(mDUSampling.getZhuangtaibm()));
        mReadingParameter.setSamplingCM(TextUtil.getInt(mJCCMTextView.getText().toString().trim()));
        mReadingParameter.setSamplingRQ(MainApplication.get(this).getCurrentTime());
        mReadingParameter.setSamplingSL(TextUtil.getInt(mJCCJSLTextView.getText().toString().trim()));
        mReadingParameter.setSamplingZTBM(mChaoBiaoZT.getZhuangtaibm());
        mReadingParameter.setSamplingZTMC(mChaoBiaoZT.getZhuangtaimc());
        mReadingParameter.set_liangcheng(mDuCard.getLiangcheng());
        mReadingParameter.set_shuibiaobeil(mDuCard.getShuibiaobl());
        mReadingParameter.set_shuibiaozt(mDuCard.getShuibiaozt());
        mReadingParameter.set_region(mConfigHelper.getRegion());

        if (mDUSampling.getLastReadingChild() != 0) {
            mReadingParameter.set_readingchild(TextUtil.getInt(mCNLRBenCicmSubTextView.getText().toString()));
            mReadingParameter.set_lastreadingchild(mDUSampling.getLastReadingChild());
        }

        ReadingResult result = ReadCalculatorHelper.calculate(this, mReadingParameter);
        if (result.isSuccess()) {
            mReadingParameter = result.outEntity();
            mJCCMTextView.setText(String.valueOf(mReadingParameter.getSamplingCM()));
            if (mDUSampling.getLastReadingChild() != 0) {
                mCNLRBenCicmSubTextView.setText(String.valueOf(mReadingParameter.get_readingchild()));
            }

            mJCCJSLTextView.setText(String.valueOf(mReadingParameter.getSamplingSL()));

            // 设置量高量低图片 & 抄表标志
//            resetLGLDViewsOfResultPanel(false);
            resetCBBZViewsOfResultPanel(true);
//            popupLiangGaoLDYY();
            save();
        } else {
            ApplicationsUtil.showMessage(this, result.outMsg());
            if (mDUSampling.getLastReadingChild() != 0) {
                mCNLRBenCicmSubTextView.setText("");
                //  mBCCMTextView.setText("");
                mJCCJSLTextView.setText("");
            } else {
                mJCCMTextView.setText("");
                mJCCJSLTextView.setText("");
            }
            // 设置量高量低图片 & 抄表标志
//            resetLGLDViewsOfResultPanel(true);
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
        if (needLGLDYY && mLiangGaoLDYYBM != 0) {

            final List<DUCiYuXX> ciYuXXList;
            int title = R.string.listdialog_title_lianggao;
            if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LG) {
                // 量高选择框
                title = R.string.listdialog_title_lianggao;
                ciYuXXList = mLGCiYuXXList;
            } else if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LD) {
                // 量低选择框
                title = R.string.listdialog_title_liangdi;
                ciYuXXList = mLDCiYuXXList;
            } else {
                ciYuXXList = null;
            }

            if ((ciYuXXList == null)
                    || (ciYuXXList.size() <= 0)) {
                LogUtil.i(TAG, "---popupLiangGaoLDYY---ciYuXXList is null or size <= 0");
                save();
                return;
            }

            LogUtil.i(TAG, "---popupLiangGaoLDYY---dialog");
            new MaterialDialog.Builder(this)
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
        boolean isGpsLocated = MainApplication.get(this).isGpsLocated();
        GpsLocation.MRLocation mrLocation = MainApplication.get(this).getMRLocation();
        if (isGpsLocated && (mrLocation != null)) {
            longitude = mrLocation.getLongitude();
            latitude = mrLocation.getLatitude();
        }

        boolean isNotRead = (mDUSampling.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO);

        mDUSampling.setZhuangtaibm(mDUSampling.getZhuangtaibm());
        mDUSampling.setZhuangtaimc(mDUSampling.getZhuangtaimc());
        mDUSampling.setBencicm(mDUSampling.getBencicm());
        mDUSampling.setChaojiansl(mDUSampling.getChaojiansl());
        mDUSampling.setChaobiaorq(mDUSampling.getChaobiaorq());

        mDUSampling.setJiChaZTBM(mChaoBiaoZT.getZhuangtaibm());
        mDUSampling.setJiChaZTMC(mChaoBiaoZT.getZhuangtaimc());
        mDUSampling.setJiChaCM(TextUtil.getInt(mJCCMTextView.getText().toString().trim()));
        mDUSampling.setJiChaSL(TextUtil.getInt(mJCCJSLTextView.getText().toString().trim()));
        mDUSampling.setJiChaRQ(MainApplication.get(this).getCurrentTime());


        mDUSampling.setJe(0.0);
        if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LG) {
            mLiangGaoLDYYBM = 1;
        } else if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LD) {
            mLiangGaoLDYYBM = -1;
        }
        mDUSampling.setLianggaoldyybm(mLiangGaoLDYYBM);
        mDUSampling.setX1(String.valueOf(longitude));
        mDUSampling.setY1(String.valueOf(latitude));
        if (mJCCMTextView.getText().toString().trim().equals("")) {
            mDUSampling.setIchaobiaobz(DURecord.CHAOBIAOBZ_WEICHAO);
        } else {
            mDUSampling.setIchaobiaobz(DURecord.CHAOBIAOBZ_YICHAO);
        }
//        mDUSampling.setIchaobiaobz(DURecord.CHAOBIAOBZ_YICHAO);
        mDUSampling.setShangchuanbz(DURecord.SHANGCHUANBZ_WEISHANGC);
        mDUSampling.setSchaobiaobz(TextUtil.getString(mCBBeiZhuTextView.getText().toString().trim()));
        if (mDUSampling.getLastReadingChild() != 0) {
            mDUSampling.setReadingChild(TextUtil.getInt(mCNLRBenCicmSubTextView.getText().toString().trim()));
        }

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        samplingRecordPresenter.updateRecord(mDUSampling, isNotRead);
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
                ApplicationsUtil.showMessage(this, R.string.text_pictures_full);
                return;
            }

            if ((_cH != null) && (_cID != null)) {
                File folder = mConfigHelper.getImageFolderPath();
                File dir = new File(folder, _cH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                mFileName = String.format("%s_%s.jpg", _cID,
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

    private void upload() {
        LogUtil.i(TAG, "---upload---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---upload---failure");
            return;
        }

        boolean isRead = (mDUSampling.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO);
        boolean hasNotUploadedRecord = (mDUSampling.getShangchuanbz() == DURecord.SHANGCHUANBZ_WEISHANGC);
        boolean hasNotUploadedMedia = false;
        for (DUMedia duMedia : mDuoMeiTXXList) {
            if (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_WEISHANGC) {
                hasNotUploadedMedia = true;
                break;
            }
        }

        if ((isRead && hasNotUploadedRecord) || (isRead && hasNotUploadedMedia)) {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(R.string.text_is_uploading_data)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            setNeedDialog(true);
                            uploadSamplingAndMedias(_renWuBH, _cH, _cID, mCeNeiXH);
                        }
                    })
                    .show();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_chaobiao_empty_err);
        }
    }

    /**
     * 存在子母表时，母表抄码输入框
     */
    private void popupKeyboard() {
        LogUtil.i(TAG, "---popupKeyboard---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popupKeyboard---failure");
            return;
        }

        String subtitle = String.format(ConstDataUtil.LOCALE,
                "母表上次抄码: %d 子表上次抄码：%d 上次水量: %d 抄表状态: %s",
                mDUSampling.getShangcicm(), mDUSampling.getLastReadingChild(), mDUSampling.getShangcicjsl(),
                mChaoBiaoZT.getZhuangtaimc());
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        new KeyboardDialog(
                this,
                new KeyboardListener() {
                    @Override
                    public void getNumber(long number) {
                        if (number >= NUMBER_MAX) {
                            ApplicationsUtil.showMessage(SamplingRecordActivity.this,
                                    R.string.text_chaomatc);
                            return;
                        }

                        if (number < mDUSampling.getShangcicm()) {
                            ApplicationsUtil.showMessage(SamplingRecordActivity.this, "母表本次抄码不能小于上次抄码");
                            mJCCMTextView.setText("");
                            return;
                        }

                        mJCCMTextView.setText(String.valueOf(number));
                        mCNLRBenCicmSubTextView.setText("");
                        mJCCJSLTextView.setText("");
                    }
                }).setSubtitile(subtitle).init(leftOrRight).show();
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

        String subtitle;
        KeyboardDialog dialog;
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        //不分子母表时，一切按以往方式处理
        if (mDUSampling.getLastReadingChild() == 0) {
            subtitle = String.format(ConstDataUtil.LOCALE,
                    "上次抄码: %d 上次水量: %d 抄表状态: %s",
                    mDUSampling.getShangcicm(), mDUSampling.getShangcicjsl(),
                    mChaoBiaoZT.getZhuangtaimc());

            dialog = new KeyboardDialog(
                    this,
                    new KeyboardListener() {
                        @Override
                        public void getNumber(long number) {
                            if (number >= NUMBER_MAX) {
                                ApplicationsUtil.showMessage(SamplingRecordActivity.this,
                                        R.string.text_chaomatc);
                                return;
                            }

                            if (isChaoMa) {
                                mJCCMTextView.setText(String.valueOf(number));
                            } else {
                                mJCCJSLTextView.setText(String.valueOf(number));
                            }

                            if (isCalcutingCMSL) {
                                calcuteCMSL();
                            }
                        }
                    }).setSubtitile(subtitle).init(leftOrRight);
        } else {
            //子表抄表框
            subtitle = String.format(ConstDataUtil.LOCALE,
                    "母表上次抄码: %d 子表上次抄码：%d 上次水量: %d 抄表状态: %s",
                    mDUSampling.getShangcicm(), mDUSampling.getLastReadingChild(), mDUSampling.getShangcicjsl(),
                    mChaoBiaoZT.getZhuangtaimc());

            dialog = new KeyboardDialog(
                    this,
                    new KeyboardListener() {
                        @Override
                        public void getNumber(long number) {
                            if (number >= NUMBER_MAX) {
                                ApplicationsUtil.showMessage(SamplingRecordActivity.this, R.string.text_chaomatc);
                                return;
                            }

                            if (isChaoMa) {
                                mCNLRBenCicmSubTextView.setText(String.valueOf(number));
                            } else {
                                mJCCJSLTextView.setText(String.valueOf(number));
                            }

                            if (isCalcutingCMSL) {
                                calcuteCMSL();
                            }
                        }
                    }).setSubtitile(subtitle).init(leftOrRight);
        }

        dialog.showLeftOrRight(leftOrRight);
        dialog.show();
    }

    private void popChaoBiaoBeiZhuDialog() {
        LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---error");
            return;
        }

//        else {
//            LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---3");
//            boolean isNotRead = (mDUSampling.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO);
//            if (isNotRead) {
//                LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---4");
//                ApplicationsUtil.showMessage(this, R.string.text_record_firstly);
//                return;
//            }
//        }

        mBeizhu = TextUtil.getString(mDUSampling.getSchaobiaobz());
        if (mConfigHelper.getRegion().equals(SystemConfig.REGION_CHANGSHU)) {
            popupChangShuBZ();
        } else {
            popupBZ();
        }
    }

    private void popupChangShuBZ() {
        if ((mBeizhu == null)
                || (mBZCiYuXXList == null)
                || (mBZCiYuXXList.size() <= 0)) {
            return;
        }

        int selectOption = 0;
        for (int i = 0; i < mBZCiYuXXList.size(); i++) {
            if (mBZCiYuXXList.get(i).getWordscontent().trim().equals(mBeizhu)) {
                selectOption = i;
                break;
            }
        }

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.text_beizhu)
                .items(mBZCiYuXXList)
                .cancelable(false)
                .itemsCallbackSingleChoice(selectOption, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view,
                                               int which, CharSequence text) {
                        // cancel or no selection
                        LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---selection");
                        if ((which >= 0) && (which < mBZCiYuXXList.size())) {
                            DUCiYuXX ciyu = mBZCiYuXXList.get(which);
                            mCBBeiZhuTextView.setText(ciyu.getWordscontent());
                            save();
                        } else {
                            LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---selection index is error");
                        }

                        return true; // allow selection
                    }
                })
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(GravityEnum.END)
                .build();

        dialog.show();
    }

    private void popupBZ() {
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
                                ApplicationsUtil.showMessage(SamplingRecordActivity.this, "输入为空,保存失败!");
                                return;
                            }
                            mCBBeiZhuTextView.setText(editText.getText().toString().trim());
                            save();
                        }
                    }
                })
                .build();

        if (dialog.getCustomView() != null) {
            editText = (EditText) dialog.getCustomView().findViewById(R.id.et_input_beizhu);
            editText.setText(mBeizhu);
        }
        dialog.show();
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

    private void getIntentData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (savedInstanceState != null) {
            _cH = savedInstanceState.getString(ConstDataUtil.S_CH);
            _cID = savedInstanceState.getString(ConstDataUtil.S_CID);
            _renWuBH = savedInstanceState.getInt(ConstDataUtil.I_RENWUBH, 0);
            mCeNeiXH = savedInstanceState.getInt(ConstDataUtil.I_CENEIXH, 0);
            mStartXH = savedInstanceState.getInt(ConstDataUtil.STARTXH, 0);
            mEndXH = savedInstanceState.getInt(ConstDataUtil.ENDXH, 0);
            mStartCID = savedInstanceState.getString(ConstDataUtil.STARTCID);
            mEndCID = savedInstanceState.getString(ConstDataUtil.ENDCID);
            cids = savedInstanceState.getStringArrayList(ConstDataUtil.CIDS);
            _mLastReadingChild = savedInstanceState.getInt(ConstDataUtil.I_LASTREADINGCHILD, 0);
            _yiChaoShu = savedInstanceState.getInt(ConstDataUtil.YICHAOSHU, 0);
            mIsFromTask = savedInstanceState.getBoolean(ConstDataUtil.FROM_TASK, false);
            mIsModified = savedInstanceState.getBoolean(ConstDataUtil.IS_MODIFIED, false);
            if (intent != null) {
                intent.putExtra(ConstDataUtil.S_CID, _cID);
                intent.putExtra(ConstDataUtil.I_CENEIXH, mCeNeiXH);
                intent.putExtra(ConstDataUtil.IS_MODIFIED, mIsModified);
            }

            LogUtil.i(TAG, "getIntentData savedInstanceState != null" + _cID);
        } else if (intent != null) {
            _cH = intent.getStringExtra(ConstDataUtil.S_CH);
            _cID = intent.getStringExtra(ConstDataUtil.S_CID);
            _renWuBH = intent.getIntExtra(ConstDataUtil.I_RENWUBH, 0);
            mCeNeiXH = intent.getIntExtra(ConstDataUtil.I_CENEIXH, 0);
            mStartXH = intent.getIntExtra(ConstDataUtil.STARTXH, 0);
            mEndXH = intent.getIntExtra(ConstDataUtil.ENDXH, 0);
            mStartCID = intent.getStringExtra(ConstDataUtil.STARTCID);
            mEndCID = intent.getStringExtra(ConstDataUtil.ENDCID);
            cids = intent.getStringArrayListExtra(ConstDataUtil.CIDS);
            _mLastReadingChild = intent.getIntExtra(ConstDataUtil.I_LASTREADINGCHILD, 0);
            _yiChaoShu = intent.getIntExtra(ConstDataUtil.YICHAOSHU, 0);
            mIsFromTask = intent.getBooleanExtra(ConstDataUtil.FROM_TASK, false);

            LogUtil.i(TAG, "getIntentData intent != null" + _cID);
        } else {
            LogUtil.i(TAG, "getIntentData error!!!");
        }
    }

    private void initOnclickListener() {
        mRecordLRRadioButton.setOnClickListener(this);
        mDetailInfoRadioButton.setOnClickListener(this);

        mDZTextView.setOnClickListener(this);
        mJCCBZTTextView.setOnClickListener(this);
        mJCCJSLTextView.setOnClickListener(this);
        mCNLRBenCicmSubTextView.setOnClickListener(this);
        mJCCMTextView.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPZButton.setOnClickListener(this);
        mUploadingButton.setOnClickListener(this);
        mCBBeiZhuTextView.setOnClickListener(this);
        mFlashLightButton.setOnClickListener(this);
        mCBQKBCTextView.setOnClickListener(this);
        mMapAddress.setOnClickListener(this);
//      mAvoidBackflowButton.setOnClickListener(this);
    }

    private void setRadioButtonChecked(boolean isRecodLRChecked, boolean isDetailInfoChecked) {
        mRecordLRRadioButton.setChecked(isRecodLRChecked);
        mDetailInfoRadioButton.setChecked(isDetailInfoChecked);
    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncDataEnd---");
        if (SamplingRecordActivity.this.isNeedDialog()) {
            SamplingRecordActivity.this.setNeedDialog(false);
            SamplingRecordActivity.this.hideProgress();
        }

        if (syncDataEnd.getSyncType() != SyncType.UPLOADING_SAMPLING_MEDIAS) {
            return;
        }

        Map<String, List<SyncDataInfo>> syncDataInfoMap = syncDataEnd.getSyncDataInfoMap();
        if (syncDataInfoMap == null) {
            return;
        }

        // record
        List<SyncDataInfo> syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING.getName());
        if ((syncDataInfoList == null) || (syncDataInfoList.size() != 1)) {
            return;
        }

        SyncDataInfo syncDataInfo = syncDataInfoList.get(0);
        if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.SUCCESS) {
            mDUSampling.setShangchuanbz(DURecord.SHANGCHUANBZ_YISHANGC);
            ApplicationsUtil.showMessage(SamplingRecordActivity.this, R.string.text_uploadRecords_success);
        } else if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
            ApplicationsUtil.showMessage(SamplingRecordActivity.this, R.string.text_uploadRecords_failure);
        }
        resetCBBZViewsOfResultPanel(false);

        // medias
        syncDataInfoList =
                syncDataInfoMap.get(SyncDataInfo.OperationType.UPLOADING_SINGLE_SAMPLING_MEDIAS.getName());
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
                SamplingRecordActivity.this.getResources().getString(R.string.text_uploadMedia),
                SamplingRecordActivity.this.getResources().getString(R.string.text_success),
                successCount,
                SamplingRecordActivity.this.getResources().getString(R.string.text_failure),
                failureCount);
        ApplicationsUtil.showMessage(SamplingRecordActivity.this, str);
    }

    /*
   * 获取当天12点的时间
   * */
    private long getCurrentDate() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) + 4 * 60 * 60 * 1000);

    }
}
