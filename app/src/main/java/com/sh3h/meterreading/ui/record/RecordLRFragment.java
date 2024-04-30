package com.sh3h.meterreading.ui.record;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.entity.DUBillPreview;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUQianFeiXX;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DURecordResult;
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
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.map.MapParamEx;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.meterreading.util.FlashLightManagerUtil;
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
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * 抄表录入
 */
public class RecordLRFragment extends ParentFragment implements RecordLRMVPView,
        View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private final static String TAG = "RecordLRFragment";
    private final static int DEFAULT_CHAOBIAO_ZTBM = 1;
    private final static int DEFAULT_SWITCH_ZTBM = 11;
    private final static String NEW_CARD_NAME = "新表";
    private final static String SWITCH_CARD_NAME = "调表";
    private final static String UNREAD_REASON_EMPTY = "空件";
    private final static String UNREAD_REASON_PAUSE = "暂停件";
    private final static int LIANGGAOLDYYBM_LG = 999;
    private final static int LIANGGAOLDYYBM_LD = -999;
    private final static int NO_READ_CARD_REASON_EMPTY = 888;//未抄原因选择(空件)
    private final static int NO_READ_CARD_REASON_PAUSE = -888;//未抄原因选择(暂停件)
    private final static int NO_READ_CARD = 0;//未抄
    private final static int HAS_READ_CARD = 1;//已抄
    private final static int NO_READ_CARD_REASON_DEFAULT = 0;//未抄原因默认值（无）

    @Inject
    RecordLRPresenter mRecordLRPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mEventBus;

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

    @BindView(R.id.fcblr_lgld)
    ImageView mLGLDImageView;

    @BindView(R.id.fcblr_scbz)
    ImageView mSCBZImageView;

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

    //地图定位图标
    @BindView(R.id.fcblr_map_address)
    ImageView mMapAddress;

    @BindView(R.id.tv_jie_ti_ts)
    TextView JieTiTS;//阶梯数

    private static final int CAPTURE_IMAGE = 1000;
    private static final int CAPTURE_IMAGE_BIAOPAN = 1111;
    private static final long NUMBER_MAX = 1000000000;

    private RecordActivity mRecordActivity;

    private String mCh = null;
    private String mCid = null;
    private int mRenWuBH = 0;
    private int mCeNeiXH = 0;
    private int mLastReadingChild = 0;

    private int mStartXH = 0;
    private int mEndXH = 0;

    private List<DUChaoBiaoZT> mChaoBiaoZTList = null;
    private List<DUCiYuXX> mLGCiYuXXList = null;
    private List<DUCiYuXX> mLDCiYuXXList = null;

    private List<String> mImgPathList = null;
    private List<DUMedia> mDuoMeiTXXList = null;
    private List<ImageView> mImageViewList = null;

    private DURecord mDuRecord = null;
    private DUCard mDuCard = null;
    private DUChaoBiaoZT mChaoBiaoZT;

    private String mFileName = null;

    private PopupWindowImageAdapter mAdapter = null;

    private ReadingParameter mReadingParameter = null;

    private int mLiangGaoLDYYBM = 0;

    private int mFlag = 0;//当显示该条记录时，记录下抄表记录的状态
    private int mYiChaoShu = 0;
    private boolean mIsFromTask = false;

    private boolean isInitSuccess;

    private EditText editText = null;
    private Spinner spOne, spTwo;
    private String mBeizhu = "";

    private Camera camera;
    private boolean isFlashLightOpen = false;

    private String lastChaoBiaoInfo = null;
    private MonthWaterAdapter monthWaterAdapter;
    //抄码
    private int selectIndex = 0;

    //private String newCardName;
    private List<JianHaoMX> jianHaoList;
    private List<DUQianFeiXX> qianFeiXXes;

    private FlashLightManagerUtil mFlashLightManagerUtil;
    private List<String> oneList, twoList;//一级备注、二级备注
    private ArrayAdapter oneAdapter, twoAdapter;


    public RecordLRFragment() {
        isInitSuccess = false;
        //newCardName = NEW_CARD_NAME;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecordActivity = (RecordActivity) getActivity();
        mRecordActivity.getActivityComponent().inject(this);
        mFlashLightManagerUtil = new FlashLightManagerUtil(mRecordActivity);
        LogUtil.i(TAG, "---onCreate---" + this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = checkRegion(inflater, container);
        ButterKnife.bind(this, rootView);
        mEventBus.register(this);
        mRecordLRPresenter.attachView(this);
        getIntentArguments(mRecordActivity.getIntent().getExtras());
        setViewsOnClickListener();

        mImgPathList = new ArrayList<>();
        mDuoMeiTXXList = new ArrayList<>();
        mReadingParameter = new ReadingParameter();

        isInitSuccess = false;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mRecordLRPresenter.loadChaoBiaoZTList();

        LogUtil.i(TAG, "---onCreateView---" + this);
        return rootView;
    }

    private View checkRegion(LayoutInflater inflater, ViewGroup container) {
        if (mConfigHelper.isLeftOrRightOperation()) {
            return inflater.inflate(R.layout.fragment_record_lr_left, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_record_lr_right, container, false);
        }
    }

    private void getIntentArguments(Bundle bundle) {
        mCh = bundle.getString(ConstDataUtil.S_CH);
        mCid = bundle.getString(ConstDataUtil.S_CID);
        mRenWuBH = bundle.getInt(ConstDataUtil.I_RENWUBH, 0);
        mCeNeiXH = bundle.getInt(ConstDataUtil.I_CENEIXH, 0);
        mLastReadingChild = bundle.getInt(ConstDataUtil.I_LASTREADINGCHILD, 0);
        mStartXH = bundle.getInt(ConstDataUtil.STARTXH, 0);
        mEndXH = bundle.getInt(ConstDataUtil.ENDXH, 0);
        mYiChaoShu = bundle.getInt(ConstDataUtil.YICHAOSHU, 0);
        mIsFromTask = bundle.getBoolean(ConstDataUtil.FROM_TASK, false);

        LogUtil.i(TAG, "---getIntentArguments---" + mCid);
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
        mMapAddress.setOnClickListener(this);//定位图标事件
        JieTiTS.setOnClickListener(this);//未抄原因事件
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mRecordLRPresenter.detachView();

        if (isFlashLightOpen) {
            mFlashLightManagerUtil.turnOff();
            isFlashLightOpen = false;
        }
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
        mRecordLRPresenter.loadLiangGaoYYList();
    }

    @Override
    public void onLoadLiangGaoYY(List<DUCiYuXX> duCiYuXXList) {
        LogUtil.i(TAG, "---updateLiangGaoYY---");

        mLGCiYuXXList = duCiYuXXList; // may be null or size <= 0
        mRecordLRPresenter.loadLiangDiYYList();
    }

    @Override
    public void onLoadLiangDiYY(List<DUCiYuXX> duCiYuXXList) {
        LogUtil.i(TAG, "---updateLiangDiYY---");

        mLDCiYuXXList = duCiYuXXList;
        mRecordLRPresenter.loadRecordInfo(DURecordInfo.FilterType.ONE, mRenWuBH, mCh,
                mCid, mCeNeiXH);
    }

    @Override
    public void onLoadRecordInfo(DURecord duRecord) {
        LogUtil.i(TAG, "---updateRecordInfo---");
        mDuRecord = duRecord;
        if (mDuRecord != null) {
            mFlag = mDuRecord.getIchaobiaobz();
            mCeNeiXH = mDuRecord.getCeneixh();
            mCid = mDuRecord.getCid();
            mRecordLRPresenter.loadCardInfo(mCh, mCid);
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
            mRecordLRPresenter.loadImageInfo(mRenWuBH, mCh, mCid);
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
        mRecordLRPresenter.getJianJianHaoMX(mDuCard.getJianhao());
    }

    @Override
    public void onLoadJianHaoMXList(List<JianHaoMX> jianHaoMXList) {
        this.jianHaoList = jianHaoMXList;
        mRecordLRPresenter.getQianFeiList(mDuCard.getCid());
    }

    @Override
    public void onLoadQianFeiXXList(List<DUQianFeiXX> qianFeiXXList) {
        this.qianFeiXXes = qianFeiXXList;
        updateViews();
    }

    @Override
    public void onUpdateRecord(DURecordResult duRecordResult) {
        LogUtil.i(TAG, "---onUpdateRecord---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duRecordResult.getSuccessCount() == 1) {
            ApplicationsUtil.showMessage(mRecordActivity,
                    R.string.text_info_chaobiaolr_savesuccess);

            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateVolumeList());//通知册本界面更新列表
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateTaskList());//通知抄表任务界面更新列表
            resetCBBZViewsOfResultPanel(false);
            resetUploadingButton();
        } else {
            ApplicationsUtil.showMessage(mRecordActivity,
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
            ApplicationsUtil.showMessage(mRecordActivity,
                    R.string.text_success_save_picture);
        } else {
            LogUtil.i(TAG, "---onSaveNewImage---parameter is error");
            ApplicationsUtil.showMessage(mRecordActivity,
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
    public void onDeleteImage(int index, String name) {
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
        if (mRecordActivity.isNeedDialog()) {
            mRecordActivity.setNeedDialog(false);
            mRecordActivity.hideProgress();
        }
        if (!TextUtil.isNullOrEmpty(message)) {
            LogUtil.i(TAG, message);
            ApplicationsUtil.showMessage(mRecordActivity, message);
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
            ApplicationsUtil.showMessage(mRecordActivity,
                    R.string.text_info_chaobiaolr_savesuccess);

            mMapAddress.setVisibility(View.VISIBLE);
            //// TODO: 2016/6/21  
            mRecordActivity.uploadCardCoordinate(
                    mRenWuBH,
                    mCh,
                    mCid,
                    TextUtil.getDouble(mDuCard.getX1()),
                    TextUtil.getDouble(mDuCard.getY1()));
        } else {
            ApplicationsUtil.showMessage(mRecordActivity,
                    R.string.text_info_chaobiaolr_savefailed);
        }
    }

    @Override
    public void onClick(View v) {
        boolean isCalculatingCMSL = true;
        DURecordInfo duRecordInfo = null;
        switch (v.getId()) {
            case R.id.fcblr_dizhi2:
                jump2ArcGisActivity(true);
                break;
            case R.id.fcblr_chaobiaoztbc:
                popupChaoBiaoZT();
                break;
            case R.id.fcblr_chaojianslbc:
                if (needPhotoFirstly()) {
                    return;
                }

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
                if (needPhotoFirstly()) {
                    return;
                }

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
                //calculateCash(); // modify 2017/10/9
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
                jump2ArcGisActivity(false);
                break;
            default:
                break;
        }
    }

    /**
     * 计算金额
     */
    private void calculateCash() {
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---calcuteCash---failure");
            return;
        }

        if (mDuRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO) {//未抄
            ApplicationsUtil.showMessage(getActivity(), "请先抄码!");
            return;
        }

        mRecordActivity.setNeedDialog(true);
        mRecordActivity.showProgress(R.string.dialog_sync_data);
        mRecordLRPresenter.calculateCash(mDuRecord, mDuCard == null ? 0 :
                mDuCard.getShuibiaofli());
    }

    /**
     * 跳转至ArcGisMapActivity
     *
     * @param isMarkMap 是否需要mark
     */
    private void jump2ArcGisActivity(boolean isMarkMap) {
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---jump2MapActivity---error");
            return;
        }

        if (isMarkMap) {
            double longitude, latitude;
            if (isCoordinateValid()) {
                longitude = TextUtil.getDouble(mDuCard.getX1());
                latitude = TextUtil.getDouble(mDuCard.getY1());
            } else {
                boolean isGpsLocated = MainApplication.get(mRecordActivity).isGpsLocated();
                GpsLocation.MRLocation mrLocation = MainApplication.get(mRecordActivity).getMRLocation();
                if (isGpsLocated && (mrLocation != null)) {
                    longitude = mrLocation.getLongitude();
                    latitude = mrLocation.getLatitude();
                } else {
                    longitude = mConfigHelper.getDefaultLongitude();
                    latitude = mConfigHelper.getDefaultLatitude();
                }
            }

            mRecordActivity.markMap(longitude, latitude);
            return;
        }

        if (!isCoordinateValid()) {
            ApplicationsUtil.showMessage(mRecordActivity, "坐标无效");
            return;
        }

        String x1 = TextUtil.getString(mDuCard.getX1());
        String y1 = TextUtil.getString(mDuCard.getY1());
        String address = TextUtil.getString(mDuCard.getDizhi()).trim();
        mRecordActivity.locateMap(TextUtil.getDouble(x1), TextUtil.getDouble(y1), address);
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

    private void popChaoBiaoBeiZhuDialog() {
        LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---error");
            return;
        }
//        else {
//            LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---3");
//            boolean isNotRead = (mDuRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO);
//            if (isNotRead) {
//                LogUtil.i(TAG, "---popChaoBiaoBeiZhuDialog---4");
//                ApplicationsUtil.showMessage(mRecordActivity, R.string.text_record_firstly);
//                return;
//            }
//        }

        mBeizhu = TextUtil.getString(mDuRecord.getSchaobiaobz());
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        MaterialDialog dialog = new MaterialDialog.Builder(mRecordActivity)
                .title(R.string.datachange_label_choose_beizhu)
                .customView(R.layout.dialog_beizhu, true)
                .positiveText(R.string.text_save)
                .negativeText(R.string.text_abandon)
                .buttonsGravity(leftOrRight)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (editText == null) {
                            return;
                        }

                        String remark = editText.getText().toString();
                        if (oneList == null || twoList == null
                                || oneList.size() <= spOne.getSelectedItemPosition()
                                || twoList.size() <= spTwo.getSelectedItemPosition()
                                || !TextUtil.isNullOrEmpty(remark)) {
                            mCBBeiZhuTextView.setText(remark);
                        } else {
                            mCBBeiZhuTextView.setText(oneList.get(spOne.getSelectedItemPosition())
                                    + "," + twoList.get(spTwo.getSelectedItemPosition()));
                        }
                        save();
                    }
                })
                .build();

        View view = dialog.getCustomView();
        if (oneList == null) {
            oneList = new ArrayList<>();
            oneList.add(getString(R.string.label_lianggao));
            oneList.add(getString(R.string.label_liangdi));
        }

        twoList = twoList == null ? new ArrayList<String>() : twoList;
        if (view != null) {
            spOne = (Spinner) view.findViewById(R.id.oneSpinner);
            oneAdapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, oneList);
            spOne.setAdapter(oneAdapter);
            spOne.setOnItemSelectedListener(this);

            spTwo = (Spinner) view.findViewById(R.id.two_spinner);
            twoAdapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, twoList);
            spTwo.setAdapter(twoAdapter);

            editText = (EditText) view.findViewById(R.id.shoushu_beizhu);
        }

        dialog.showLeftOrRight(leftOrRight);
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_month_water:
                selectIndex = position;
                monthWaterAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.oneSpinner:
                if (twoList == null || mLGCiYuXXList == null || mLDCiYuXXList == null) {
                    return;
                }
                twoList.clear();
                String text = oneList.get(position);
                List<DUCiYuXX> list = text.equals(getString(R.string.label_lianggao)) ?
                        mLGCiYuXXList : mLDCiYuXXList;
                for (DUCiYuXX ciYuXX : list) {
                    twoList.add(ciYuXX.getWordscontent());
                }
                twoAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void lazyLoad() {
    }

    /**
     * 存在子母表时，母表抄码输入框
     */
    private void popupKeyboard() {
        boolean isReturn = false;
        if (mDuRecord != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
                String dateStr = dateFormat.format(new Date());
                int currentN = Integer.parseInt(dateStr.substring(0, 4));
                int currentY = Integer.parseInt(dateStr.substring(4));
                int chaobiaoN = mDuRecord.getChaobiaon();
                int chaobiaoY = mDuRecord.getIchaobiaoy();
                if (currentN != chaobiaoN || currentY != chaobiaoY) {
                    Toast.makeText(mRecordActivity,
                            "当前抄表数据年月与系统年月不一致，无法进行抄表，当前抄表数据年月为："
                                    + chaobiaoN + "" + chaobiaoY,
                            Toast.LENGTH_SHORT).show();
                    isReturn = true;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                isReturn = true;
            }
        }
        if (isReturn) {
            return;
        }
        LogUtil.i(TAG, "---popupKeyboard---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popupKeyboard---failure");
            return;
        }

        String subtitle = String.format(ConstDataUtil.LOCALE,
                "母表上次抄码: %d 子表上次抄码：%d 上次水量: %d 抄表状态: %s",
                mDuRecord.getShangcicm(), mDuRecord.getLastReadingChild(), mDuRecord.getShangcicjsl(),
                mChaoBiaoZT.getZhuangtaimc());

        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        KeyboardDialog dialog = new KeyboardDialog(
                mRecordActivity,
                new KeyboardListener() {
                    @Override
                    public void getNumber(long number) {
                        if (number >= NUMBER_MAX) {
                            ApplicationsUtil.showMessage(mRecordActivity,
                                    R.string.text_chaomatc);
                            return;
                        }

//                        if (number == mDuRecord.getShangcicm()) {
//                            ApplicationsUtil.showMessage(mRecordActivity, R.string.text_please_input_wei_chao_reason);
//                            mBCCMTextView.setText("");
//                            return;
//                        }

                        mBCCMTextView.setText(String.valueOf(number));
                        mCNLRBenCicmSubTextView.setText("");
                        mBCCJSLTextView.setText("");
                    }
                }).setSubtitile(subtitle).init(leftOrRight);
        dialog.showLeftOrRight(leftOrRight);
        dialog.show();
    }

    /**
     * @param isChaoMa
     * @param isCalcutingCMSL
     */
    private void popupKeyboard(final boolean isChaoMa,
                               final boolean isCalcutingCMSL) {
        boolean isReturn = false;
        if (mDuRecord != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
                String dateStr = dateFormat.format(new Date());
                int currentN = Integer.parseInt(dateStr.substring(0, 4));
                int currentY = Integer.parseInt(dateStr.substring(4));
                int chaobiaoN = mDuRecord.getChaobiaon();
                int chaobiaoY = mDuRecord.getIchaobiaoy();
                if (currentN != chaobiaoN || currentY != chaobiaoY) {
                    Toast.makeText(mRecordActivity,
                            "当前抄表数据年月与系统年月不一致，无法进行抄表，当前抄表数据年月为："
                                    + chaobiaoN + "" + chaobiaoY,
                            Toast.LENGTH_SHORT).show();
                    isReturn = true;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                isReturn = true;
            }
        }
        if (isReturn) {
            return;
        }

        LogUtil.i(TAG, "---popupKeyboard---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popupKeyboard---failure");
            return;
        }

        String subtitle = null;

        //不分子母表时，一切按以往方式处理
        KeyboardDialog dialog;
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        if (mDuRecord.getLastReadingChild() == 0) {
            subtitle = String.format(ConstDataUtil.LOCALE,
                    "上次抄码: %d 上次水量: %d 抄表状态: %s 表号: %s 口径: %s",
                    mDuRecord.getShangcicm(),
                    mDuRecord.getShangcicjsl(),
                    TextUtil.getString(mChaoBiaoZT.getZhuangtaimc()),
                    TextUtil.getString(mDuCard.getShuibiaogyh()),
                    TextUtil.getString(mDuCard.getKoujingmc()));

            dialog = new KeyboardDialog(
                    mRecordActivity,
                    new KeyboardListener() {
                        @Override
                        public void getNumber(long number) {
                            if (number >= NUMBER_MAX) {
                                ApplicationsUtil.showMessage(mRecordActivity, R.string.text_chaomatc);
                                return;
                            }

//                            if ((isChaoMa && number == mDuRecord.getShangcicm())
//                                    || (!isChaoMa && number == 0)){
//                                ApplicationsUtil.showMessage(mRecordActivity, R.string.text_please_input_wei_chao_reason);
//                                return;
//                            }

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
        } else {
            //子表抄表框
            subtitle = String.format(ConstDataUtil.LOCALE,
                    "母表上次抄码: %d 子表上次抄码：%d 上次水量: %d 抄表状态: %s",
                    mDuRecord.getShangcicm(), mDuRecord.getLastReadingChild(), mDuRecord.getShangcicjsl(),
                    mChaoBiaoZT.getZhuangtaimc());

            dialog = new KeyboardDialog(
                    mRecordActivity,
                    new KeyboardListener() {
                        @Override
                        public void getNumber(long number) {
                            if (number >= NUMBER_MAX) {
                                ApplicationsUtil.showMessage(mRecordActivity,
                                        R.string.text_chaomatc);
                                return;
                            }

//                            if ((isChaoMa && number == mDuRecord.getShangcicm())
//                                    || (!isChaoMa && number == 0)){
//                                ApplicationsUtil.showMessage(mRecordActivity, R.string.text_please_input_wei_chao_reason);
//                                return;
//                            }

                            if (isChaoMa) {
                                mCNLRBenCicmSubTextView.setText(String.valueOf(number));
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
    }

    private void popupChaoBiaoZT() {
        LogUtil.i(TAG, "---popupChaoBiaoZT---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---popupChaoBiaoZT---failure");
            return;
        }

//        if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH) {
//            LogUtil.i(TAG, "---popupChaoBiaoZT---new card");
//            if (newCardName.equals(NEW_CARD_NAME)) {
//                ApplicationsUtil.showMessage(mRecordActivity, R.string.text_new_card1);
//            } else {
//                ApplicationsUtil.showMessage(mRecordActivity, R.string.text_new_card2);
//            }
//            return;
//        }

        ChaoBiaoZTChooseDialog dialog = new ChaoBiaoZTChooseDialog(
                mConfigHelper,
                mRecordActivity,
                new ChaoBiaoZTChooseListener() {
                    @Override
                    public void onResult(Bundle bundle) {
                        if (bundle == null) {
                            return;
                        }


                        if (bundle.getString(ChaoBiaoZTChooseDialog.XIAOLET_MC) != null) {
                            String zhuangtaimc = bundle.getString(ChaoBiaoZTChooseDialog.XIAOLET_MC);
                            int zhuangtaibm = bundle.getInt(ChaoBiaoZTChooseDialog.XIAOLET_BM);
                            if ("馈通".equals(zhuangtaimc)
                                    || "无水".equals(zhuangtaimc)) {
                                if (!mDuCard.getShuibiaoflmc().equals("消防表")) {
                                    ApplicationsUtil.showMessage(getContext(), "该表不属于消防表，不能选择"
                                            + zhuangtaimc + "状态");
                                    return;
                                }
                            }
//                            String dlmc = bundle.getString(ChaoBiaoZTChooseDialog.DALET_MC) + "";
//                            if ((mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH ||
//                                    "新表实发".equals(mChaoBiaoZT.getZhuangtaimc())) && !"延迟".equals(dlmc)) {
//                                if ("暂估".equals(zhuangtaimc)) {
//                                    ApplicationsUtil.showMessage(getContext(), "该表水表状态为换表，不能选择"
//                                            + zhuangtaimc + "状态");
//                                    return;
//                                }
//                            }
                            DUChaoBiaoZT chaoBiaoZT = getChaoBiaoZT(zhuangtaibm);
                            if ((chaoBiaoZT != null) && (chaoBiaoZT.getZhuangtaimc().equals(zhuangtaimc))) {
                                mBCCBZTTextView.setText(zhuangtaimc);
                                mChaoBiaoZT = chaoBiaoZT;
                                resetViewsOfInputPanel(true);
                                resetLGLDViewsOfResultPanel(true);
                                resetCBBZViewsOfResultPanel(true);
//                                resetJieTiTSView(true);
//                                resetBeiZhuView(true);
                            }
                        }
                    }
                });
        dialog.showLeftOrRight(mConfigHelper.isLeftOrRightOperation());
        dialog.show();
    }

    private void calcuteCMSL() {
        LogUtil.i(TAG, "---calcuteCMSL---");
        if (!isInitSuccess) {
            LogUtil.i(TAG, "---calcuteCMSL---failure");
            return;
        }

        mReadingParameter.reset();
        mReadingParameter.set_huanbiaohtqrsj(TextUtil.format(mDuRecord.getShangcicbrq()));
        mReadingParameter.set_shangcicbrq(mDuRecord.getShangcicbrq());
        mReadingParameter.set_xinbiaodm(mDuRecord.getXinbiaodm());
        mReadingParameter.set_jiubiaocm(mDuRecord.getJiubiaocm());
//        if ((mChaoBiaoZT.getZhuangtaimc() != null)
//                && mChaoBiaoZT.getZhuangtaimc().equals(newCardName)) {
//            mReadingParameter.set_jiubiaocm(mDuCard.getJiubiaocm());
//        }
        mReadingParameter.set_shangcicm(mDuRecord.getShangcicm());
        mReadingParameter.set_bencicm(TextUtil.getInt(mBCCMTextView.getText().toString().trim()));
        mReadingParameter.set_bencicbrq(MainApplication.get(mRecordActivity).getCurrentTime());
        mReadingParameter.set_bencisl(TextUtil.getInt(mBCCJSLTextView.getText().toString().trim()));
        mReadingParameter.set_chaobiaoztbm(String.valueOf(mChaoBiaoZT.getZhuangtaibm()));
        mReadingParameter.set_liangcheng(mDuCard.getLiangcheng());
        mReadingParameter.set_shuibiaobeil(mDuCard.getShuibiaobl());
        mReadingParameter.set_shuibiaozt(mDuCard.getShuibiaozt());
        mReadingParameter.set_shanggedbzqts(mDuRecord.getI_SHANGGEDBZQTS());
        mReadingParameter.set_shangshanggycbrq(mDuRecord.getD_SHANGSHANGGYCBRQ());
        mReadingParameter.set_shangcicjsl(mDuRecord.getShangcicjsl());

        if (mDuRecord.getLastReadingChild() != 0) {
            mReadingParameter.set_readingchild(TextUtil.getInt(mCNLRBenCicmSubTextView.getText().toString()));
            mReadingParameter.set_lastreadingchild(mDuRecord.getLastReadingChild());
        }

        ReadingResult result = ReadCalculatorHelper.calculate(mRecordActivity, mReadingParameter);
        if (result.isSuccess()) {
            mReadingParameter = result.outEntity();
            if (needPhotoFirstly()) {
                if (mDuRecord.getLastReadingChild() != 0) {
                    mCNLRBenCicmSubTextView.setText("");
                    //  mBCCMTextView.setText("");
                    mBCCJSLTextView.setText("");
                } else {
                    mBCCMTextView.setText("");
                    mBCCJSLTextView.setText("");
                }

                JieTiTS.setText("");
                // 设置量高量低图片 & 抄表标志
                resetLGLDViewsOfResultPanel(true);
                resetCBBZViewsOfResultPanel(true);
                return;
            }

            mBCCMTextView.setText(String.valueOf(mReadingParameter.get_bencicm()));
            if (mDuRecord.getLastReadingChild() != 0) {
                mCNLRBenCicmSubTextView.setText(String.valueOf(mReadingParameter.get_readingchild()));
            }

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
            if (!needPhotoFirstly()) {
                ApplicationsUtil.showMessage(mRecordActivity, result.outMsg());
            }

            if (mDuRecord.getLastReadingChild() != 0) {
                mCNLRBenCicmSubTextView.setText("");
                //  mBCCMTextView.setText("");
                mBCCJSLTextView.setText("");
            } else {
                mBCCMTextView.setText("");
                mBCCJSLTextView.setText("");
            }
            JieTiTS.setText("");
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

            if (ciYuXXList == null) {
                LogUtil.i(TAG, "---popupLiangGaoLDYY---ciYuXXList is null");
                initLGLDImage();
                save();
                return;
            }

            LogUtil.i(TAG, "---popupLiangGaoLDYY---dialog");
            boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
            MaterialDialog dialog = new MaterialDialog.Builder(mRecordActivity)
                    .title(title)
                    .customView(R.layout.md_dialog_month_water, false)
                    //.items(ciYuXXList)
                    .cancelable(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (monthWaterAdapter != null) {
                                LogUtil.i(TAG, "---popupLiangGaoLDYY---selection");
                                if ((selectIndex >= 0) && (selectIndex < ciYuXXList.size())) {
                                    DUCiYuXX ciyu = ciYuXXList.get(selectIndex);
                                    mLiangGaoLDYYBM = TextUtil.getInt(ciyu.getWordsvalue());
                                    initLGLDImage();
                                    save();
                                } else {
                                    LogUtil.i(TAG, "---popupLiangGaoLDYY---selection index is error");
                                }
                            }
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (monthWaterAdapter != null) {
                                LogUtil.i(TAG, "---popupLiangGaoLDYY---selection");
                                if ((selectIndex >= 0) && (selectIndex < ciYuXXList.size())) {
                                    DUCiYuXX ciyu = ciYuXXList.get(selectIndex);
                                    mLiangGaoLDYYBM = TextUtil.getInt(ciyu.getWordsvalue());
                                    initLGLDImage();
                                    save();
                                } else {
                                    LogUtil.i(TAG, "---popupLiangGaoLDYY---selection index is error");
                                }
                            }
                        }
                    })
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .buttonsGravity(leftOrRight)
                    //.buttonsGravity(GravityEnum.END)
                    .build();

            View customView = dialog.getCustomView();
            if (customView != null) {
                TextView tvCurrent = (TextView) customView.findViewById(R.id.tv_current_month_water);
                TextView tvLast = (TextView) customView.findViewById(R.id.tv_last_month_water);
                tvCurrent.setText(String.format(Locale.CHINESE, "%d%s",
                        mReadingParameter.get_bencisl(),
                        mRecordActivity.getResources().getString(R.string.text_ton)));
                tvLast.setText(String.format(Locale.CHINESE, "%d%s",
                        mDuRecord.getShangcicjsl(),
                        mRecordActivity.getResources().getString(R.string.text_ton)));

                ListView listView = (ListView) customView.findViewById(R.id.lv_month_water);
                monthWaterAdapter = new MonthWaterAdapter(ciYuXXList);
                listView.setOnItemClickListener(this);
                listView.setAdapter(monthWaterAdapter);
            }
            dialog.show();
        } else {
            LogUtil.i(TAG, "---popupLiangGaoLDYY---save");
            initLGLDImage();
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
        boolean isGpsLocated = MainApplication.get(mRecordActivity).isGpsLocated();
        GpsLocation.MRLocation mrLocation = MainApplication.get(mRecordActivity).getMRLocation();
        if (isGpsLocated && (mrLocation != null)) {
            longitude = mrLocation.getLongitude();
            latitude = mrLocation.getLatitude();
        }

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

        boolean isNotRead = (mDuRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO);
        mDuRecord.setZhuangtaibm(mChaoBiaoZT.getZhuangtaibm());
        mDuRecord.setZhuangtaimc(mChaoBiaoZT.getZhuangtaimc());
        mDuRecord.setBencicm(TextUtil.getInt(mBCCMTextView.getText().toString().trim()));
        mDuRecord.setChaojiansl(TextUtil.getInt(mBCCJSLTextView.getText().toString().trim()));
        mDuRecord.setChaobiaorq(MainApplication.get(mRecordActivity).getCurrentTime());
        mDuRecord.setJe(0.0);
        if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LG) {
            mLiangGaoLDYYBM = 1;
        } else if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LD) {
            mLiangGaoLDYYBM = -1;
        }
        mDuRecord.setLianggaoldyybm(mLiangGaoLDYYBM);
        mDuRecord.setX1(String.valueOf(longitude));
        mDuRecord.setY1(String.valueOf(latitude));
        if (mDuRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO
                && mBCCMTextView.getText().toString().trim().equals("")) {
            mDuRecord.setIchaobiaobz(DURecord.CHAOBIAOBZ_WEICHAO);
        } else {
            mDuRecord.setIchaobiaobz(DURecord.CHAOBIAOBZ_YICHAO);
        }
        mDuRecord.setShangchuanbz(DURecord.SHANGCHUANBZ_WEISHANGC);
        mDuRecord.setSchaobiaobz(TextUtil.getString(mCBBeiZhuTextView.getText().toString().trim()));
        if (mDuRecord.getLastReadingChild() != 0) {
            mDuRecord.setReadingChild(TextUtil.getInt(mCNLRBenCicmSubTextView.getText().toString().trim()));
        }

        mDuRecord.setIsRead(HAS_READ_CARD);
        mDuRecord.setReasonUnRead(NO_READ_CARD_REASON_DEFAULT);

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mRecordLRPresenter.updateRecord(mDuRecord, isNotRead);
    }

    int biaopanIndex = -1;

    /**
     * 拍照功能
     */
    private void takePicture() {
        if (isFlashLightOpen) {
            mFlashLightManagerUtil.turnOff();
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
                ApplicationsUtil.showMessage(mRecordActivity,
                        R.string.text_pictures_full);
                return;
            }

            if ((mCh != null) && (mCid != null)) {
                View view = View.inflate(mRecordActivity, R.layout.item_biaopan, null);
                com.gc.materialdesign.views.ButtonRectangle btnBiaopan = (com.gc.materialdesign.views.ButtonRectangle) view.findViewById(R.id.btn_biaopan);
                com.gc.materialdesign.views.ButtonRectangle btnOther = (com.gc.materialdesign.views.ButtonRectangle) view.findViewById(R.id.btn_other);
                final boolean[] isContainerBiaoPan = {false};
                final AlertDialog alertDialog = new AlertDialog.Builder(mRecordActivity)
                        .setView(view)
                        .create();
                alertDialog.show();
                biaopanIndex = -1;
                btnBiaopan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        mFileName = String.format("%s_%s_%s.jpg", mCid,
                                TextUtil.format(MainApplication.get(mRecordActivity).getCurrentDate(), "yyyyMMddHHmmss"), "表盘");
                        for (int j = 0; j < mDuoMeiTXXList.size(); j++) {
                            Log.e("fileName", "filename=" + mDuoMeiTXXList.get(j).getWenjianmc());
                            if (mDuoMeiTXXList.get(j).getWenjianmc().contains("表盘")) {
//                                                Toast.makeText(mRecordActivity, "此表卡已包含表盘照片，请删除原表盘照片再进行拍摄", Toast.LENGTH_SHORT).show();
                                biaopanIndex = j;
                                isContainerBiaoPan[0] = true;
                            }
                        }
                        if (isContainerBiaoPan[0]) {
                            new AlertDialog.Builder(mRecordActivity)
                                    .setTitle("提示")
                                    .setMessage("已存在表盘照片，是否需要重拍并覆盖原照片")
                                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            return;
                                        }
                                    })
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            // 重新拍照前删除原来照片
//                                            String name = mDuoMeiTXXList.get(biaopanIndex).getWenjianmc();
////                                                    mSmoothProgressBar.setVisibility(View.VISIBLE);
//                                            mRecordLRPresenter.deleteImage(biaopanIndex, name, mCh);

                                            File folder = mConfigHelper.getImageFolderPath();
                                            File dir = new File(folder, mCh);
                                            if (!dir.exists()) {
                                                dir.mkdirs();
                                            }
                                            File file = new File(dir, mFileName);
                                            Uri uri = Uri.fromFile(file);
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                            mRecordActivity.startActivityForResult(intent, CAPTURE_IMAGE_BIAOPAN);
                                            Toast.makeText(mRecordActivity, "请拍摄表盘照片", Toast.LENGTH_LONG).show();
                                        }
                                    }).create().show();
                        } else {
                            File folder = mConfigHelper.getImageFolderPath();
                            File dir = new File(folder, mCh);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File file = new File(dir, mFileName);
                            Uri uri = Uri.fromFile(file);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            mRecordActivity.startActivityForResult(intent, CAPTURE_IMAGE);
                            Toast.makeText(mRecordActivity, "请拍摄表盘照片", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                btnOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        mFileName = String.format("%s_%s_%s.jpg", mCid,
                                TextUtil.format(MainApplication.get(mRecordActivity).getCurrentDate(), "yyyyMMddHHmmss"), "其他");
                        File folder = mConfigHelper.getImageFolderPath();
                        File dir = new File(folder, mCh);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File file = new File(dir, mFileName);
                        Uri uri = Uri.fromFile(file);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        mRecordActivity.startActivityForResult(intent, CAPTURE_IMAGE);
                    }
                });
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

        boolean isRead = (mDuRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO);
        boolean hasNotUploadedRecord = (mDuRecord.getShangchuanbz() == DURecord.SHANGCHUANBZ_WEISHANGC);
        boolean hasNotUploadedMedia = false;
        for (DUMedia duMedia : mDuoMeiTXXList) {
            if (duMedia.getShangchuanbz() == DUMedia.SHANGCHUANBZ_WEISHANGC) {
                hasNotUploadedMedia = true;
                break;
            }
        }

        if ((isRead && hasNotUploadedRecord) || (isRead && hasNotUploadedMedia)
                || (isRead && mDuRecord.getIsRead() == NO_READ_CARD)) {
            boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
            new MaterialDialog.Builder(mRecordActivity)
                    .title(R.string.text_prompt)
                    .content(R.string.text_is_uploading_data)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .buttonsGravity(leftOrRight)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mRecordActivity.setNeedDialog(true);
                            mRecordActivity.uploadRecordAndMedias(mRenWuBH, mCh, mCid, mCeNeiXH);
                        }
                    })
                    .show(leftOrRight);
        } else {
            ApplicationsUtil.showMessage(mRecordActivity,
                    R.string.text_chaobiao_empty_err);
        }
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

        mSCBZImageView.setImageResource(
                mDuRecord.getShangchuanbz() == DURecord.SHANGCHUANBZ_YISHANGC
                        ? R.mipmap.ic_ok : R.mipmap.ic_shangchuan);

        initLGLDImage();

        if ((mDuRecord != null)
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

        calJieTi();
    }

    private void resetRecordInfo() {
        if ((mDuRecord == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        resetHBQFImageViews();

        resetTextViews();

        if (mDuRecord.getIchaobiaobz() == 0) {
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

    private DUChaoBiaoZT getChaoBiaoZT() {
        if ((mDuRecord == null)
                || (mDuCard == null)) {
            return null;
        }

        if (mDuRecord.getIchaobiaobz() > 0) {
            return getChaoBiaoZT(mDuRecord.getZhuangtaibm());
        } /*else if (mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH) {
            return getChaoBiaoZT(newCardName);
        } */ else {
            return getChaoBiaoZT(mDuCard.getShuibiaozt() == DUCard.SHUIBIAOZT_SWITCH ?
                    DEFAULT_SWITCH_ZTBM : DEFAULT_CHAOBIAO_ZTBM);
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

    private void resetButtons() {
        if ((mDuRecord == null)
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

        resetUploadingButton();
    }

    private void resetUploadingButton() {
//        if (mUploadingButton != null) {
//            boolean isRead = (mDuRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO);
//            boolean isUploaded = mDuRecord.getShangchuanbz() == DURecord.SHANGCHUANBZ_YISHANGC;
//            if (isRead && (!isUploaded)) {
//                mUploadingButton.setVisibility(View.VISIBLE);
//            } else {
//                mUploadingButton.setVisibility(View.GONE);
//            }
//        }
    }

    private void resetCBBZViewsOfResultPanel(boolean clean) {
        if ((mDuRecord == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (!clean) {
            int chaobiaobz = 0;
            int shangchuan = 0;

            chaobiaobz = mDuRecord.getIchaobiaobz();
            shangchuan = mDuRecord.getShangchuanbz();

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

                if (mSCBZImageView != null) {
                    mSCBZImageView.setImageDrawable(getResources().getDrawable(
                            icon));
                    mSCBZImageView.setVisibility(View.VISIBLE);
                }

//                mCBBZImageView.setVisibility(View.VISIBLE);
                if (shangchuan == DURecord.SHANGCHUANBZ_YISHANGC) {
                    mSCBZImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_ok));
                } else {
                    mSCBZImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_shangchuan));
                }
            } else {
                if (mSCBZImageView != null) {
                    mSCBZImageView.setVisibility(View.GONE);
                }
            }

            calJieTi();
        } else {
            if (mSCBZImageView != null) {
                mSCBZImageView.setVisibility(View.GONE);
            }
        }
    }

    private void resetLGLDViewsOfResultPanel(boolean clean) {
        if ((mDuRecord == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (!clean) {
            int lianggaosl = 0;
            int liangdisl = 0;
            lianggaosl = mDuRecord.getLianggaosl();
            liangdisl = mDuRecord.getLiangdisl();
//            int averageNumber = mDuRecord.getPingjunl1();
            int chaojiansl = TextUtil.getInt(mBCCJSLTextView.getText().toString().trim());
            if ((chaojiansl > lianggaosl)) {
                mLiangGaoLDYYBM = LIANGGAOLDYYBM_LG;// 量高

                if (mLGLDImageView != null) {
                    mLGLDImageView.setVisibility(View.VISIBLE);
                }

                if (mSCBZImageView != null) {
                    mSCBZImageView.setVisibility(View.GONE);
                }
            } else if (chaojiansl < liangdisl) {
                mLiangGaoLDYYBM = LIANGGAOLDYYBM_LD;// 量低

                if (mLGLDImageView != null) {
                    mLGLDImageView.setVisibility(View.GONE);
                }

                if (mSCBZImageView != null) {
                    mSCBZImageView.setVisibility(View.VISIBLE);
                }
            } else {
                mLiangGaoLDYYBM = 0; // 正常
                if (mLGLDImageView != null) {
                    mLGLDImageView.setVisibility(View.GONE);
                }

                if (mSCBZImageView != null) {
                    mSCBZImageView.setVisibility(View.GONE);
                }
            }
        } else {
            if (mLGLDImageView != null) {
                mLGLDImageView.setVisibility(View.GONE);
            }

            if (mSCBZImageView != null) {
                mSCBZImageView.setVisibility(View.GONE);
            }
        }
    }

    private void resetViewsOfInputPanel(boolean clean) {
        if ((mDuRecord == null)
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

        if (clean) {
            if (mBCCMTextView != null) {
                mBCCMTextView.setText("");
                mCNLRBenCicmSubTextView.setText("");
            }

            if (mBCCJSLTextView != null) {
                mBCCJSLTextView.setText("");
            }
        }
    }

    private void resetTextViews() {
        if ((mDuRecord == null)
                || (mDuCard == null)
                || (mChaoBiaoZT == null)) {
            return;
        }

        if (mCNLRShangCicmSubTextView != null && mCNLRBenCicmSubTextView != null) {
            if (mDuRecord.getLastReadingChild() != 0) {
                mCBLRSubLayout.setVisibility(View.VISIBLE);
                mChaoMaOneTextView.setText(R.string.text_chaoma_mu);
                if (mDuRecord.getReadingChild() == 0) {
                    mCNLRBenCicmSubTextView.setText("");
                } else {
                    mCNLRBenCicmSubTextView.setText(String.valueOf(mDuRecord.getReadingChild()));
                }

                mCNLRShangCicmSubTextView.setText(String.valueOf(mDuRecord.getLastReadingChild()));
            } else {
                mChaoMaOneTextView.setText(R.string.text_chaoma);
                mCBLRSubLayout.setVisibility(View.GONE);
            }
        }

        if (mYHBHTextView != null) {
            mYHBHTextView.setText(mCid);
        }

        if (mQFTextView != null) {
            if (/*mDuCard.getQianfei() == 1*/ qianFeiXXes != null && qianFeiXXes.size() > 0) {
                // 获取欠费信息，欠费条数
                double mQianFeiZJE = 0;
                double mWuShuiFeiZJE = 0;
                for (DUQianFeiXX qianFeiXX : qianFeiXXes) {
                    mQianFeiZJE += qianFeiXX.getJe();
                    mWuShuiFeiZJE += qianFeiXX.getShuifei();
                }
                mQFTextView.setVisibility(View.VISIBLE);
                mQFTextView.setText(String.valueOf(qianFeiXXes.size()) + "/"
                        + NumberFormat.getInstance().format(mWuShuiFeiZJE) + "/" + NumberFormat.getInstance().format(mQianFeiZJE));
            } else {
                mQFTextView.setVisibility(View.GONE);
            }
        }

        if (mHMTextView != null) {
            mHMTextView.setText(TextUtil.getString(mDuCard.getKehumc()));
        }

        if (mDZTextView != null) {
            mDZTextView.setText(TextUtil.getString(mDuCard.getDizhi()).trim());
        }

        if (mSBXXTextView != null) {
            String date = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault()).format(mDuCard.getZhuangbiaorq());
            String str = String.format("%s/%s/%s",
                    mDuCard.getBiaowei(), mDuCard.getBiaowei(), date);
            mSBXXTextView.setText(str);
        }

        if (mSCCBZTTextView != null) {
            mSCCBZTTextView.setText(TextUtil.getString(mDuRecord.getShangciztmc()));
        }

        if (mSCCBTextView != null) {
            mSCCBTextView.setText(String.valueOf(mDuRecord.getShangcicm()));
        }

        if (mSCCJSLTextView != null) {
            mSCCJSLTextView.setText(String.valueOf(mDuRecord.getShangcicjsl()));
        }

        if (mBCCBZTTextView != null) {
            mBCCBZTTextView.setText(TextUtil.getString(mChaoBiaoZT.getZhuangtaimc()));
        }

        if (mBCCMTextView != null) {
            if (mDuRecord.getIchaobiaobz() == 0) {
                mBCCMTextView.setText("");
            } else {
                mBCCMTextView.setText(String.valueOf(mDuRecord.getBencicm()));
            }
        }

        if (mBCCJSLTextView != null) {
            if (mDuRecord.getIchaobiaobz() == 0) {
                mBCCJSLTextView.setText("");
            } else {
                mBCCJSLTextView.setText(String.valueOf(mDuRecord.getChaojiansl()));
            }
        }

        if (mCBBeiZhuTextView != null) {
            mCBBeiZhuTextView.setText(TextUtils.isEmpty(mDuRecord.getSchaobiaobz()) ? "" : mDuRecord.getSchaobiaobz().trim());
        }

        if (mDuRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO) {
            lastChaoBiaoInfo = String.format(ConstDataUtil.LOCALE,
                    "%s: %s\n%s: %d\n%s: %s",
                    mRecordActivity.getString(R.string.label_chaobiaozt),
                    mChaoBiaoZT.getZhuangtaimc(),
                    mRecordActivity.getString(R.string.text_chaoma),
                    mDuRecord.getBencicm(),
                    mRecordActivity.getString(R.string.label_bencisl),
                    mDuRecord.getChaojiansl());
        } else {
            lastChaoBiaoInfo = null;
        }

        JieTiTS.setText("");
    }

    private void resetHBQFImageViews() {
        if ((mDuRecord == null)
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

        if (mDuCard.getQianfeizbs() > 0) {
            mQFImageView.setVisibility(View.VISIBLE);
        } else {
            mQFImageView.setVisibility(View.GONE);
            //mTxt_qianfei.setVisibility(View.GONE);
        }

        if (mDuCard.getYonghuzt() == DUCard.JM_YONGHUZT_STOP) {
            mTSImageView.setVisibility(View.VISIBLE);
        } else {
            mTSImageView.setVisibility(View.GONE);
        }
    }

    public void processResult(int requestCode, int resultCode, Intent data) {
        if ((CAPTURE_IMAGE == requestCode || CAPTURE_IMAGE_BIAOPAN == requestCode) &&
                mCh != null &&
                mCid != null &&
                mFileName != null &&
                !TextUtil.isNullOrEmpty(mPreferencesHelper.getUserSession().getAccount())) {

            if (CAPTURE_IMAGE_BIAOPAN == requestCode && resultCode == RESULT_OK) {
                try {
                    // 重新拍照完后删除原来照片
                    String name = mDuoMeiTXXList.get(biaopanIndex).getWenjianmc();
//                                                    mSmoothProgressBar.setVisibility(View.VISIBLE);
                    mRecordLRPresenter.deleteImage(biaopanIndex, name, mCh);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            LogUtil.i(TAG, "---processResult---");
            File folder = mConfigHelper.getImageFolderPath();
            File dir = new File(folder, mCh);
            if (!dir.exists()) {
                LogUtil.i(TAG, String.format("---processResult---dir:%s isn't existing",
                        mCh));
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
            duoMeiTXX.setChaobiaoid(mDuRecord.getChaobiaoid());
            duoMeiTXX.setWenjianlx(DUMedia.WENJIANLX_PIC);
            duoMeiTXX.setWenjianmc(mFileName);
            duoMeiTXX.setType(DUMedia.MEDIA_TYPE_CHAOBIAO);
            duoMeiTXX.setRenwubh(mRenWuBH);
            duoMeiTXX.setCh(mCh);
            duoMeiTXX.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
            duoMeiTXX.setCreaterq(MainApplication.get(mRecordActivity).getCurrentTime());
            duoMeiTXX.setWenjianlj(file.getAbsolutePath());
            duoMeiTXX.setAccount(mPreferencesHelper.getUserSession().getAccount());
            MediaScannerConnection.scanFile(mRecordActivity,
                    new String[]{file.getAbsolutePath()}, null, null);

            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mRecordLRPresenter.saveNewImage(duoMeiTXX);
        } else if ((requestCode == MapParamEx.REQUEST_CODE)
                && (resultCode == MapParamEx.RESULT_CODE)
                && (data != null)) {
            LogUtil.i(TAG, "---processResult---map");
            double longitude = data.getDoubleExtra(MapParamEx.LONGITUDE, 0);
            double latitude = data.getDoubleExtra(MapParamEx.LATITUDE, 0);
            if ((longitude <= MapParamEx.ERROR_VALUE)
                    || (latitude <= MapParamEx.ERROR_VALUE)) {
                ApplicationsUtil.showMessage(mRecordActivity,
                        "选取坐标失败");
                return;
            }

            mDuCard.setX1(String.valueOf(longitude));
            mDuCard.setY1(String.valueOf(latitude));
            mRecordLRPresenter.updateCard(mDuCard);
        } else {
            LogUtil.i(TAG, "---processResult---parameter is null");
        }
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
            mRecordLRPresenter.loadImageViews(mImgPathList, mRecordActivity);
        }
    }

    private void displayImages() {
        mAdapter = new PopupWindowImageAdapter(mImageViewList);
        Window window = mRecordActivity.getWindow();
        PopupWindowMenu popupWindowMenuNJXX = new PopupWindowMenu(mRecordActivity, window);
        popupWindowMenuNJXX.popupWindowImageViwe(mHMTextView,
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
                                mRecordLRPresenter.deleteImage(index, name, mCh);
                            }
                        }
                    }

                    @Override
                    public void after() {
                    }
                });
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
        mRecordLRPresenter.loadRecordInfo(
                mIsFromTask ? DURecordInfo.FilterType.PREVIOUS_ONE_NOT_READING : DURecordInfo.FilterType.PREVIOUS_ONE,
                mRenWuBH,
                mCh, mCid, mCeNeiXH);
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
        mRecordLRPresenter.loadRecordInfo(
                mIsFromTask ? DURecordInfo.FilterType.NEXT_ONE_NOT_READING : DURecordInfo.FilterType.NEXT_ONE,
                mRenWuBH,
                mCh, mCid, mCeNeiXH);
    }

    private void openFlashLight() {
        if (isFlashLightOpen) {
            mFlashLightManagerUtil.turnOff();
            isFlashLightOpen = false;
        } else {
            mFlashLightManagerUtil.init();
            mFlashLightManagerUtil.turnOn();
            isFlashLightOpen = true;
        }
    }

    private void popupLastChaoBiaoInfo() {
        if (!TextUtil.isNullOrEmpty(lastChaoBiaoInfo)) {
            boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
            new MaterialDialog.Builder(mRecordActivity)
                    .title(R.string.text_prompt)
                    .content(lastChaoBiaoInfo)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .buttonsGravity(leftOrRight)
                    .show(leftOrRight);
        }
    }

    @Subscribe
    public void onSyncDataStart(UIBusEvent.SyncDataStart syncDataStart) {
        LogUtil.i(TAG, "---onSyncDataStart---");
        if (mRecordActivity.isNeedDialog()) {
            mRecordActivity.showProgress(R.string.dialog_sync_data);
        }
    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncDataEnd---");
        if (mRecordActivity.isNeedDialog()) {
            mRecordActivity.setNeedDialog(false);
            mRecordActivity.hideProgress();
        }

        if (syncDataEnd.getSyncType() != SyncType.UPLOADING_RECORD_MEDIAS) {
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
            mDuRecord.setShangchuanbz(DURecord.SHANGCHUANBZ_YISHANGC);
            ApplicationsUtil.showMessage(mRecordActivity, R.string.text_uploadRecords_success);
        } else if (syncDataInfo.getOperationResult() == SyncDataInfo.OperationResult.FAILURE) {
            ApplicationsUtil.showMessage(mRecordActivity, R.string.text_uploadRecords_failure);
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
                mRecordActivity.getResources().getString(R.string.text_uploadMedia),
                mRecordActivity.getResources().getString(R.string.text_success),
                successCount,
                mRecordActivity.getResources().getString(R.string.text_failure),
                failureCount);
        ApplicationsUtil.showMessage(mRecordActivity, str);
    }

    @Subscribe
    public void onQianFeiInfo(UIBusEvent.QianFeiInfo qianFeiInfo) {
        LogUtil.i(TAG, "---onQianFeiInfo 1---");
        if ((qianFeiInfo == null)
                || (TextUtil.isNullOrEmpty(qianFeiInfo.getCid()))) {
            LogUtil.i(TAG, "---onQianFeiInfo 2---");
            return;
        }
        LogUtil.i(TAG, "---onQianFeiInfo 3---");

        if (!qianFeiInfo.getCid().equals(mCid)) {
            LogUtil.i(TAG, "---onQianFeiInfo 4---");
            return;
        }

        if (qianFeiInfo.isQianFei()) {
            mQFImageView.setVisibility(View.VISIBLE);
        } else {
            mQFImageView.setVisibility(View.GONE);
        }
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
            ApplicationsUtil.showMessage(mRecordActivity,
                    String.format("%s: %s %s",
                            mRecordActivity.getResources().getString(R.string.text_card_id),
                            customerId,
                            isSuccess ? mRecordActivity.getResources().getString(R.string.text_success_to_change_coordinate)
                                    : mRecordActivity.getResources().getString(R.string.text_failure_to_change_coordinate)));
        }
    }

    private class MonthWaterAdapter extends BaseAdapter {
        private List<DUCiYuXX> mListData;

        public MonthWaterAdapter(List<DUCiYuXX> listData) {
            this.mListData = listData;
            selectIndex = 0;
        }

        @Override
        public int getCount() {
            return mListData == null ? 0 : mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(com.afollestad.materialdialogs.commons.R.layout.md_listitem_singlechoice, parent, false);
            }
            RadioButton radio = (RadioButton) convertView.findViewById(com.afollestad.materialdialogs.commons.R.id.control);
            radio.setChecked(selectIndex == position);
            TextView tv = (TextView) convertView.findViewById(com.afollestad.materialdialogs.commons.R.id.title);
            tv.setText(mListData.get(position).getWordscontent());
            return convertView;
        }
    }

    @Override
    public void onLoadCashResult(List<DUBillPreview> duBillPreviews) {
        if (mRecordActivity.isNeedDialog()) {
            mRecordActivity.setNeedDialog(false);
            mRecordActivity.hideProgress();
        }

        if (duBillPreviews == null || duBillPreviews.size() != 1) {
            ApplicationsUtil.showMessage(getActivity(), R.string.text_calculate_cash_failure);
            return;
        }
        if (duBillPreviews.get(0).getAccMoney() == 0.0) {
            ApplicationsUtil.showMessage(getActivity(), duBillPreviews.get(0).getMessage());
            return;
        }

        ApplicationsUtil.showMessage(getActivity(), R.string.text_calculate_cash_success);
        double cash = duBillPreviews.get(0).getAccMoney();
        mDuRecord.setJe(cash);
        mRecordLRPresenter.updateRecord(mDuRecord, false);
    }

    private boolean needPhotoFirstly() {
        if (mDuoMeiTXXList == null || mDuoMeiTXXList.size() <= 0) {
            ApplicationsUtil.showMessage(mRecordActivity, R.string.text_take_picture_firstly);
            return true;
        }

        return false;
    }

    private void calJieTi() {
        int nianLeiJ = mDuCard.getNianleij();
        String chaoJian = mBCCJSLTextView.getText().toString();
        if (!judgeNumber(chaoJian)) {
            return;
        }
        int chaoJianSL = Integer.parseInt(chaoJian);
        int SL = nianLeiJ + chaoJianSL;
        int mShengYuSL;
        String mJTStr = TextUtil.EMPTY;
        String duoRenKFA = mDuCard.getDuorenkfa();
        for (JianHaoMX jianHaoMX : jianHaoList) {
            int jieTiJB = jianHaoMX.getJieTiJB();
            int jieShuSL = jianHaoMX.getJieShuSL();
            int kaiShiSL = jianHaoMX.getKaiShiSL();
            if (duoRenKFA != null && !duoRenKFA.equals("")) {
                if (duoRenKFA.equals("1")) {
                    jieShuSL = jieShuSL + 100;
                    kaiShiSL = kaiShiSL + 100;
                }
            }
            if (jieTiJB == 1) {
                if (jieShuSL >= SL) {
                    mShengYuSL = jianHaoMX.getJieShuSL() - SL;
                    mJTStr = "第一阶梯/" + mShengYuSL;
                    break;
                }
            } else if (jieTiJB == 2) {
                if (jieShuSL >= SL) {
                    mShengYuSL = jianHaoMX.getJieShuSL() - SL;
                    mJTStr = "第二阶梯/" + mShengYuSL;
                    break;
                }
            } else if (jieTiJB == 3) {
                if (kaiShiSL < SL) {
                    mJTStr = "第三阶梯";
                    break;
                }
            }
        }

        JieTiTS.setText(mJTStr);
    }

    private boolean judgeNumber(String text) {
        if (TextUtil.isNullOrEmpty(text)) {
            return false;
        }

        if (text.length() > 9) {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(text);
        return isNum.matches();
    }

    private void initLGLDImage() {
        if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LG) {
            mLGLDImageView.setImageResource(R.mipmap.ic_line_up);
            mLGLDImageView.setVisibility(View.VISIBLE);
        } else if (mLiangGaoLDYYBM == LIANGGAOLDYYBM_LD) {
            mLGLDImageView.setImageResource(R.mipmap.ic_line_down);
            mLGLDImageView.setVisibility(View.VISIBLE);
        } else {
            mLGLDImageView.setVisibility(View.INVISIBLE);
        }
    }

}
