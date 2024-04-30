package com.sh3h.meterreading.ui.delay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.datautil.data.entity.DUDelayRecordInfo;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.delayRecord.DelayRecordActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maxwin.view.XListView;

/**
 * 延迟列表
 */
public class DelayListActivity extends ParentActivity implements DelayListMvpView,
        View.OnClickListener {
    private static final String TAG = "VolumeListActivity";

    private static final int FILTER_FINISH_INDEX = 0; // 只显示完成
    private static final int FILTER_UNFINISH_INDEX = 1; // 只显示未完成
    private static final int FILTER_ALL_INDEX = 2; // 显示全部

    private static final String DEFAULT_KEY = "%%%%";
    private static final int DEFAULT_ORDER_NUMBER = -1;
    public static final int ADJUST_NUMBER_CODE = 1000;

    @Inject
    DelayListPresenter mDelayListPresenter;
    @Inject
    Bus mEventBus;
    @Inject
    ConfigHelper mConfigHelper;
    @BindView(R.id.avl_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;
    @BindView(R.id.fcbl_list)
    XListView mXListView;
    @BindView(R.id.btn_upload_image)
    Button btnUploadImage;//补传图片按钮

    private MyListAdapter mMyListAdapter;
    private List<DUCard> mDUCardList;
    private List<DUDelayRecord> mDuRecordList;

    private long mLimit;
    private int mCurrentCount;

    //private MenuItem searchItem;
    private MenuItem finishItem, unFinishItem, allItem;
    private MenuItem jumpNumberMenuItem;
    private MenuItem filterMenuItem;
    private MenuItem refreshCardMenuItem;

    //private List<String> filterList;
    //private boolean isSearching;
    private int filterIndex;
    private int orderNumber;
    private boolean isReadyJumping;
    private int mStartXH;
    private int mEndXH;

    public DelayListActivity() {
        mMyListAdapter = null;
        mDUCardList = null;
        mDuRecordList = null;
        mLimit = 0;
        mCurrentCount = 0;
        jumpNumberMenuItem = null;
        filterMenuItem = null;
        //filterList = null;
        filterIndex = FILTER_ALL_INDEX;
        orderNumber = DEFAULT_ORDER_NUMBER;
        isReadyJumping = false;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setActionBarBackButtonEnable();
        mDelayListPresenter.attachView(this);
        mEventBus.register(this);

        Intent intent = getIntent();
        if (savedInstanceState != null) {
            initParams(savedInstanceState);
        } else if (intent != null) {
            initParams(intent.getExtras());
        } else {
            initParams(null);
        }

        if (checkPermissions()) {
            initConfig();
        }

        btnUploadImage.setOnClickListener(this);
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    protected void onResume() {
        super.onResume();

        LogUtil.i(TAG, "---onResume---1");
        if (needRefresh) {
            LogUtil.i(TAG, "---onResume---2");
            needRefresh = false;
            updateListView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mDelayListPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delay, menu);
        finishItem = menu.findItem(R.id.mul_action_show_finished);
        unFinishItem = menu.findItem(R.id.mul_action_show_unfinished);
        allItem = menu.findItem(R.id.mul_action_show_all);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mtl_action_update:
                if (canSync(mConfigHelper.getUploadingTimeError())) {
                    needDialog = true;
                    uploadDownloadDelay();
                }
                break;
            case R.id.mul_action_show_unfinished:
                unFinishItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_UNFINISH_INDEX;
                updateListView();
                break;
            case R.id.mul_action_show_finished:
                finishItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_FINISH_INDEX;
                updateListView();
                break;
            case R.id.mul_action_show_all:
                allItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_ALL_INDEX;
                updateListView();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_image:
                if (canSync(mConfigHelper.getUploadingTimeError())) {
                    needDialog = true;
                    uploadDownloadDelay();
                }
                break;
        }

    }

    @Override
    public void onLoadRecords(List<DUDelayRecord> duRecordList) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duRecordList == null) {
            LogUtil.i(TAG, "---onLoadRecords---null or size <= 0");
            return;
        }

        mDuRecordList = duRecordList;
        mMyListAdapter.setDataList(mDuRecordList);
        mMyListAdapter.notifyDataSetChanged();

        int size = mDuRecordList.size();
        if (size > 0) {
            if ((mStartXH == DEFAULT_ORDER_NUMBER) || (mEndXH == DEFAULT_ORDER_NUMBER)) {
                mStartXH = mDuRecordList.get(0).getI_CENEIXH();
                mEndXH = mDuRecordList.get(size - 1).getI_CENEIXH();
            }

            isReadyJumping = true;
        }

        ApplicationsUtil.showMessage(this, String.format(ConstDataUtil.LOCALE, "%s%d",
                getString(R.string.text_record_number), mDuRecordList.size()));

        /**
         * 需求：延迟上传的问题，现在缺少续传照片的功能。希望做到像正常抄表一样，可以补传照片。
         * TODO 2018.7.22 LIBAO
         * 解决：进入页面查询延迟的未上传图片，根据条件显示右下角上传图片按钮
         */
        mDelayListPresenter.getDelayImageSize();
    }

    /**
     * 2018.7.22 libao 查询未上传图片结果
     *
     * @param size
     */
    @Override
    public void onGetImageSize(int size) {
        btnUploadImage.setVisibility(View.GONE);
        boolean isUpload = true;
        for (DUDelayRecord duDelayRecord : mDuRecordList) {
            if (duDelayRecord.getI_ShangChuanBZ() != 2) {
                isUpload = false;
            }
        }
        if (isUpload) {
            if (size > 0) {
                btnUploadImage.setVisibility(View.VISIBLE);
            } else {
                btnUploadImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoadCards(List<DUCard> duCardList) {
        if ((duCardList == null) || (duCardList.size() <= 0)) {
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            mMyListAdapter.setBiaoKaXXList(duCardList);
            mDuRecordList=new ArrayList<DUDelayRecord>();
            mMyListAdapter.setDataList(mDuRecordList);
            mMyListAdapter.notifyDataSetChanged();
            mDelayListPresenter.getDelayImageSize();
            LogUtil.i(TAG, "---onLoadCards---null or size <= 0");
            return;
        }

        mDUCardList = duCardList;
        mMyListAdapter.setBiaoKaXXList(duCardList);
        mDelayListPresenter.loadRecordXXs(DUDelayRecordInfo.FilterType.ALL, mLimit);
    }

    @Override
    public void onError(String message) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Subscribe
    public void onInitConfigResult(UIBusEvent.InitConfigResult initConfigResult) {
        LogUtil.i(TAG, "---onInitConfigResult---" + initConfigResult.isSuccess());
        if (initConfigResult.isSuccess()) {
            initUserConfig();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    @Subscribe
    public void onInitUserConfigResult(UIBusEvent.InitUserConfigResult initUserConfigResult) {
        LogUtil.i(TAG, "---onInitResult---" + initUserConfigResult.isSuccess());
        if (initUserConfigResult.isSuccess()) {
            initXListView();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    @Subscribe
    public void onSyncDataStart(UIBusEvent.SyncDataStart syncDataStart) {
        LogUtil.i(TAG, "---onSyncDataStart---");
        if (needDialog) {
            showProgress(R.string.dialog_sync_data);
        }
    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncDataEnd---");
        if (needDialog) {
            needDialog = false;
            hideProgress();
        }

        SyncType syncType = syncDataEnd.getSyncType();
        switch (syncType) {
            case UPLOADING_DOWNLOADING_DELAY_ALL:
                refresh(true);
                popupSyncDataInfo(syncDataEnd);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onUpdateVolumeList(UIBusEvent.UpdateVolumeList updateVolumeList) {
        LogUtil.i(TAG, "---onUpdateVolumeList---");
        refresh(false);
    }

    private void refresh(boolean needRefreshCards) {
        filterIndex = FILTER_ALL_INDEX;
        orderNumber = DEFAULT_ORDER_NUMBER;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;

        if (isActive) {
            needRefresh = false;
            if (needRefreshCards) {
                mSmoothProgressBar.setVisibility(View.VISIBLE);
                mDelayListPresenter.loadCardXXs();
            } else {
                updateListView();
            }
        } else {
            needRefresh = true;
        }
    }

    private void popupSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = MainApplication.get(this).parseSpecialSyncDataInfo(syncDataEnd);
        if (result == null) {
            result = MainApplication.get(this).parseSyncDataInfo(syncDataEnd);
        }

        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        if (!TextUtil.isNullOrEmpty(result)) {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(result)
                    .positiveText(R.string.text_ok)
                    .buttonsGravity(leftOrRight)
                    .show(leftOrRight);
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(R.string.text_not_synchronizing_data)
                    .positiveText(R.string.text_ok)
                    .buttonsGravity(leftOrRight)
                    .show(leftOrRight);
        }
    }

    private void initPartVariables() {
        mDUCardList = null;
        mDuRecordList = null;
        mLimit = 0;
        mCurrentCount = 0;
        filterIndex = FILTER_ALL_INDEX;
        orderNumber = DEFAULT_ORDER_NUMBER;
        isReadyJumping = false;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
    }

    private void initXListView() {
        mDUCardList = new ArrayList<>();
        mDuRecordList = new ArrayList<>();

        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(true);
        mMyListAdapter = new MyListAdapter(this, R.layout.item_delay_list, mDuRecordList, mDUCardList);
        mXListView.setAdapter(mMyListAdapter);

        // 设置回调函数
        mXListView.setXListViewListener(new XListView.IXListViewListener() {
            private Handler handler = new Handler();
            private final long delayMillis = 1000;

            @Override
            public void onRefresh() {
                // 模拟刷新数据，1s之后停止刷新
                handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                String time = TextUtil.format(System.currentTimeMillis(),
                                        TextUtil.FORMAT_DATE_NO_SECOND);
                                mXListView.setRefreshTime(time);
                                mXListView.stopRefresh();

                                if (!canSync(mConfigHelper.getUploadingTimeError())) {
                                    return;
                                }

                                needDialog = true;
                                uploadDownloadDelay();
                            }
                        }, delayMillis);
            }

            @Override
            public void onLoadMore() {
            }
        });

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mDelayListPresenter.loadCardXXs();
    }

    private void updateListView() {
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        switch (filterIndex) {
            case FILTER_FINISH_INDEX:
                mDelayListPresenter.loadRecordXXs(DUDelayRecordInfo.FilterType.FINISHING, mLimit);
                break;
            case FILTER_UNFINISH_INDEX:
                mDelayListPresenter.loadRecordXXs(DUDelayRecordInfo.FilterType.UNFINISHING, mLimit);
                break;
            case FILTER_ALL_INDEX:
                mDelayListPresenter.loadRecordXXs(DUDelayRecordInfo.FilterType.ALL, mLimit);
                break;
            default:
                break;
        }
    }

    private void jump2OrderNumber() {
        int index = 0;
        boolean found = false;
        for (DUDelayRecord duRecord : mDuRecordList) {
            if (duRecord.getI_CENEIXH() == orderNumber) {
                found = true;
                break;
            }

            index++;
        }

        if (found) {
            mXListView.setSelection(index);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_not_find_card);
        }
        orderNumber = DEFAULT_ORDER_NUMBER;
    }

    private boolean canOperate() {
        if (!mConfigHelper.isCurrentReadingDateValid()) {
            ApplicationsUtil.showMessage(this, R.string.text_exceed_time_limit);
            return false;
        }

        return true;
    }

    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int mResource;
        private List<DUDelayRecord> mListData;
        private List<DUCard> mBiaoKaXXList;

        public MyListAdapter(Context context,
                             int resource,
                             List<DUDelayRecord> listData,
                             List<DUCard> cardList) {
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            mListData = listData;
            mBiaoKaXXList = cardList;
        }

        public void setDataList(List<DUDelayRecord> list) {
            mListData = list;
        }

        public void setBiaoKaXXList(List<DUCard> cardList) {
            mBiaoKaXXList = cardList;
        }

        private DUCard getBiaoKaXX(String cid) {
            if ((mBiaoKaXXList == null) || (cid == null)) {
                return null;
            }

            for (DUCard duCard : mBiaoKaXXList) {
                if (duCard.getCid() == null) {
                    continue;
                }

                if (duCard.getCid().equals(cid)) {
                    return duCard;
                }
            }

            return null;
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
            MyListHolder myListHolder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(mResource, null);
                myListHolder = new MyListHolder();
                myListHolder.tv_xuhao = (TextView) convertView.findViewById(R.id.tv_xuhao);
                myListHolder.tv_cid = (TextView) convertView.findViewById(R.id.tv_cid);
                myListHolder.tv_yonghum = (TextView) convertView.findViewById(R.id.tv_yonghum);
                myListHolder.tv_yonghudz = (TextView) convertView.findViewById(R.id.tv_yonghudz);
                myListHolder.img = (ImageView) convertView.findViewById(R.id.img_state);
                myListHolder.tvShangCiCM = (TextView) convertView.findViewById(R.id.tv_shangcicm);
                myListHolder.tvBenCiCM = (TextView) convertView.findViewById(R.id.tv_bencicm);
                myListHolder.tvChaoJianSL = (TextView) convertView.findViewById(R.id.tv_chaojiansl);
                myListHolder.tvChaoBiaoZT = (TextView) convertView.findViewById(R.id.tv_chaobiaozt);
                myListHolder.tvShuiBiaoGYH = (TextView) convertView.findViewById(R.id.tv_shuibiaogyh);
                myListHolder.tvKouJing = (TextView) convertView.findViewById(R.id.tv_koujing);
                myListHolder.mCardView = (CardView) convertView.findViewById(R.id.fs_cardview);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
                resetViewHolder(myListHolder);
            }

            // 获取数据
            DUDelayRecord duRecord = mListData.get(position);
            DUCard duCard = getBiaoKaXX(duRecord.getS_CID());
            if (duCard != null) {
                myListHolder.tv_xuhao.setText(String.valueOf(duCard.getCeneixh()));
                myListHolder.tv_cid.setText(duCard.getCid());
                myListHolder.tv_yonghum.setText(duCard.getKehumc());
                myListHolder.tv_yonghudz.setText(duCard.getDizhi());
                int chaoBiaoBZ = duRecord.getI_CHAOBIAOBZ();
                int shangChuanBZ = duRecord.getI_ShangChuanBZ();
                if (chaoBiaoBZ > 0) {
                    int icon;
                    switch (chaoBiaoBZ) {
                        case DURecord.CHAOBIAOBZ_YICHAO: // 已抄
                            if (shangChuanBZ == DURecord.SHANGCHUANBZ_WEISHANGC) {
                                icon = R.mipmap.ic_shangchuan;
                            } else if (shangChuanBZ == DURecord.SHANGCHUANBZ_ZHENGZAISC) {
                                icon = R.mipmap.ic_shangchuan;
                            } else {
                                icon = R.mipmap.ic_ok;
                            }
                            break;
                        default:
                            icon = R.mipmap.ic_ok;
                            break;
                    }

                    myListHolder.img.setImageDrawable(getResources().getDrawable(icon));
                    myListHolder.tvShangCiCM.setText(String.valueOf(duRecord.getI_SHANGCICM()));
                    myListHolder.tvBenCiCM.setText(String.valueOf(duRecord.getI_CHAOHUICM()));
                    myListHolder.tvChaoJianSL.setText(String.valueOf(duRecord.getI_CHAOJIANSL()));
                    myListHolder.tvChaoBiaoZT.setText(String.valueOf(duRecord.getS_ZHUANGTAIMC()));
                } else {
                    myListHolder.tvShangCiCM.setText(String.valueOf(duRecord.getI_SHANGCICM()));
                    myListHolder.tvBenCiCM.setText(TextUtil.EMPTY);
                    myListHolder.tvChaoJianSL.setText(TextUtil.EMPTY);
                    myListHolder.tvChaoBiaoZT.setText(TextUtil.EMPTY);
                    myListHolder.img.setImageDrawable(null);
                }

                myListHolder.tvShuiBiaoGYH.setText(TextUtil.getString(duCard.getShuibiaogyh()));
                myListHolder.tvKouJing.setText(TextUtil.getString(duCard.getKoujingmc()));
                myListHolder.mCardView.setTag(duRecord);
                myListHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object object = v.getTag();
                        if (object instanceof DUDelayRecord) {
                            DUDelayRecord item = (DUDelayRecord) object;
                            Intent intent = new Intent(DelayListActivity.this, DelayRecordActivity.class);
                            putIntentArguments(intent, item);
                            startActivity(intent);
                        }
                    }
                });
            }

            return convertView;
        }
    }

    private void putIntentArguments(Intent intent, DUDelayRecord item) {
        intent.putExtra(ConstDataUtil.S_CH, item.getS_CH());
        intent.putExtra(ConstDataUtil.S_CID, item.getS_CID());
        intent.putExtra(ConstDataUtil.I_RENWUBH, item.getI_RENWUBH());
        intent.putExtra(ConstDataUtil.I_CENEIXH, item.getI_CENEIXH());
        intent.putExtra(ConstDataUtil.I_LASTREADINGCHILD, 0);
        intent.putExtra(ConstDataUtil.STARTXH, mStartXH);
        intent.putExtra(ConstDataUtil.ENDXH, mEndXH);
        intent.putExtra(ConstDataUtil.FROM_TASK, false);
    }

    protected void resetViewHolder(MyListHolder myListHolder) {
        myListHolder.tv_xuhao.setText(TextUtil.EMPTY);
        myListHolder.tv_cid.setText(TextUtil.EMPTY);
        myListHolder.tv_yonghum.setText(TextUtil.EMPTY);
        myListHolder.tv_yonghudz.setText(TextUtil.EMPTY);
        myListHolder.img.setImageDrawable(null);
        myListHolder.tvShangCiCM.setText(TextUtil.EMPTY);
        myListHolder.tvBenCiCM.setText(TextUtil.EMPTY);
        myListHolder.tvChaoJianSL.setText(TextUtil.EMPTY);
        myListHolder.tvChaoBiaoZT.setText(TextUtil.EMPTY);
    }

    private class MyListHolder {
        private TextView tv_xuhao;
        private TextView tv_cid;
        private TextView tv_yonghum;
        private TextView tv_yonghudz;
        //  uploading flag
        private ImageView img;
        // one card
        private TextView tvShangCiCM;
        private TextView tvBenCiCM;

        // shuiliang & zhuangtai
        private TextView tvChaoJianSL;
        private TextView tvChaoBiaoZT;

        private TextView tvShuiBiaoGYH;
        private TextView tvKouJing;

        private CardView mCardView;
    }
}
