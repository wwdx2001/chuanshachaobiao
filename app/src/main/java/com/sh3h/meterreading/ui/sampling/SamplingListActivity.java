package com.sh3h.meterreading.ui.sampling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.maxwin.view.XListView;


public class SamplingListActivity extends ParentActivity implements SamplingListMvpView,
        View.OnClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = "SamplingListActivity";
    private MyListAdapter mMyListAdapter;
    private List<DUCard> mDUCardList;
    private List<DUSampling> mDuRecordList;
    private static final String DEFAULT_KEY = "%%%%";
    private static final int DEFAULT_ORDER_NUMBER = -1;
    private static final int FILTER_FINISHED_INDEX = 0; // 只显示已抄
    private static final int FILTER_UNFINISHED_INDEX = 1; // 只显示未抄
    private static final int FILTER_ALL_INDEX = 2; // 显示全部
    public static final int ADJUST_NUMBER_CODE = 1000;
    @Inject
    ConfigHelper mConfigHelper;
    @Inject
    SamplingListPresenter samplingListPresenter;
    @Inject
    EventPosterHelper mEventPosterHelper;
    @Inject
    Bus mEventBus;
    @Bind(R.id.choujian_list)
    XListView mXListView;
    @Bind(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    private String mCh;
    private int mTaskId;
    private long mLimit;
    private boolean isMenuEnable;
    private int orderNumber;
    private boolean isReadyJumping;

    private MenuItem searchItem;
    private MenuItem filterMenuItem;
    private MenuItem jumpNumberMenuItem;
    private MenuItem sortMenuItem;

    private List<String> filterList;
    private boolean isSearching;
    private int filterIndex;
    private String mKey;
    private int mYiChaoShu;
    private int mStartXH;
    private int mEndXH;
    private String mStartCID;
    private String mEndCID;
    private boolean canUpload;

    public SamplingListActivity() {
        mCh = null;
        mTaskId = 0;
        mMyListAdapter = null;
        mDUCardList = null;
        mDuRecordList = null;
        mLimit = 0;
        isMenuEnable = true;
        searchItem = null;
        filterMenuItem = null;
        filterList = null;
        isSearching = false;
        mKey = DEFAULT_KEY;
        mYiChaoShu = 0;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        canUpload = false;
        isReadyJumping = false;
        filterIndex = FILTER_UNFINISHED_INDEX;
        orderNumber = DEFAULT_ORDER_NUMBER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mEventBus.register(this);
        samplingListPresenter.attachView(this);
        setActionBarBackButtonEnable();
        initFilterList();
        initSubTitle();
        initXListView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        samplingListPresenter.detachView();
    }

    @Override
    public void onError(String message) {
        LogUtil.i(TAG, "---onError---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sampling_list, menu);
        searchItem = menu.findItem(R.id.mvl_action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        jumpNumberMenuItem = menu.findItem(R.id.mvl_jump_number);
        jumpNumberMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            private EditText editText;

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (!isMenuEnable) {
                    return false;
                }

                searchItem.collapseActionView();
                orderNumber = DEFAULT_ORDER_NUMBER;
                editText = null;

                if (isSearching
                        || (filterIndex != FILTER_ALL_INDEX)) {
                    isSearching = false;
                    mKey = DEFAULT_KEY;
                    filterIndex = FILTER_ALL_INDEX;
                    isReadyJumping = false;
                    updateListView();
                } else {
                    isSearching = false;
                    mKey = DEFAULT_KEY;
                    filterIndex = FILTER_ALL_INDEX;
                }

                MaterialDialog dialog = new MaterialDialog.Builder(SamplingListActivity.this)
                        .title(R.string.text_jump_number)
                        .customView(R.layout.dialog_jump_number2, true)
                        .positiveText(R.string.text_ok)
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (!isReadyJumping) {
                                    LogUtil.i(TAG, "---jump---error");
                                    ApplicationsUtil.showMessage(SamplingListActivity.this,
                                            R.string.text_jump_number_error);
                                    return;
                                }

                                if (editText != null) {
                                    orderNumber = TextUtil.getInt(editText.getText().toString());
                                    if (orderNumber != DEFAULT_ORDER_NUMBER) {
                                        jump2OrderNumber();
                                    }
                                }
                            }
                        })
                        .build();
                if (dialog.getCustomView() != null) {
                    editText = (EditText) dialog.getCustomView().findViewById(R.id.et_order_number);
                    editText.setText("");
                }

                dialog.show();
                return true;
            }
        });

        sortMenuItem = menu.findItem(R.id.mvl_sort);
        sortMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (!isMenuEnable) {
                    return false;
                }
                searchItem.collapseActionView();
                Collections.reverse(mDuRecordList);
                mMyListAdapter.notifyDataSetChanged();
                return true;
            }
        });

        filterMenuItem = menu.findItem(R.id.mvl_filter);
        filterMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if ((!isMenuEnable) || (filterList == null)) {
                    return true;
                }
                searchItem.collapseActionView();
                isSearching = false;
                mKey = DEFAULT_KEY;
                orderNumber = DEFAULT_ORDER_NUMBER;
                new MaterialDialog.Builder(SamplingListActivity.this)
                        .title(R.string.text_prompt)
                        .items(filterList)
                        .itemsCallbackSingleChoice(filterIndex,
                                new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view,
                                                               int which, CharSequence text) {
                                        switch (which) {
                                            case FILTER_FINISHED_INDEX: // 只显示已抄
                                                filterIndex = FILTER_FINISHED_INDEX;
                                                break;
                                            case FILTER_UNFINISHED_INDEX: // 只显示未抄
                                                filterIndex = FILTER_UNFINISHED_INDEX;
                                                break;
                                            case FILTER_ALL_INDEX: // 显示全部
                                                filterIndex = FILTER_ALL_INDEX;
                                                break;
                                            default:
                                                return true;
                                        }
                                        updateListView();
                                        return true;
                                    }
                                })
                        .positiveText(R.string.text_ok)
                        .negativeText(R.string.text_cancel)
                        .show();
                return true;
            }
        });
        return true;
    }

    @Override
    public void onLoadCards(List<DUCard> duCardList) {
        if ((duCardList == null) || (duCardList.size() <= 0)) {
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            LogUtil.i(TAG, "---onLoadCards---null or size <= 0");
            return;
        }
        mDUCardList = duCardList;
        mMyListAdapter.setBiaoKaXXList(duCardList);
        samplingListPresenter.loadRecordXXs(DUSamplingInfo.FilterType.UNFINISHING, mTaskId, mKey, mLimit);
    }

    @Override
    public void onLoadRecords(List<DUSampling> duSamplingList) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duSamplingList == null) {
            LogUtil.i(TAG, "---onLoadRecords---null or size <= 0");
            return;
        }
        mDuRecordList = duSamplingList;
        mMyListAdapter.setDataList(mDuRecordList);
        mMyListAdapter.notifyDataSetChanged();
        canUpload = false;
        int size = mDuRecordList.size();
        if (size > 0) {
            if ((mStartXH == DEFAULT_ORDER_NUMBER) || (mEndXH == DEFAULT_ORDER_NUMBER)) {
                mStartXH = mDuRecordList.get(0).getCeneixh();
                mEndXH = mDuRecordList.get(size - 1).getCeneixh();

                mStartCID = mDuRecordList.get(0).getCid();
                mEndCID = mDuRecordList.get(size - 1).getCid();

                mYiChaoShu = 0;
                for (DUSampling duSampling : duSamplingList) {
                    if (duSampling.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO) {
                        mYiChaoShu++;
                    }
                }
            }
            isReadyJumping = true;
        }
        if (mYiChaoShu > 0) {
            canUpload = true;
        }

        ApplicationsUtil.showMessage(this, String.format(ConstDataUtil.LOCALE, "%s%d",
                getString(R.string.text_record_number), duSamplingList.size()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ADJUST_NUMBER_CODE) {
            LogUtil.i(TAG, "---onActivityResult---");

            initPartVariables();
            searchItem.collapseActionView();
            mSmoothProgressBar.setVisibility(View.VISIBLE);
            samplingListPresenter.loadCardXXs(mTaskId);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (isMenuEnable && (query != null)) {
            isSearching = true;
            filterIndex = FILTER_ALL_INDEX;
            mKey = String.format("%%%s%%", query);
            orderNumber = DEFAULT_ORDER_NUMBER;
            updateListView();
        }
        return true;
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
            case UPLOADING_SAMPLING:
                refresh();
                popupSyncDataInfo(syncDataEnd);
                break;
            case UPLOADING_SAMPLING_MEDIAS:
                refresh();
            default:
                break;
        }
    }

    @Subscribe
    public void onUpdateSamplingList(UIBusEvent.UpdateSamplingList updateSamplingList) {
        LogUtil.i(TAG, "---onUpdateSamplingList---");
        refresh();
    }

    private void initPartVariables() {
        mDUCardList = null;
        mDuRecordList = null;
        mLimit = 0;
        isSearching = false;
        isReadyJumping = false;
        mKey = DEFAULT_KEY;
        mYiChaoShu = 0;
        orderNumber = DEFAULT_ORDER_NUMBER;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        canUpload = false;
    }

    private void initFilterList() {
        filterList = new ArrayList<>();
        filterList.add(getString(R.string.text_finished));
        filterList.add(getString(R.string.text_unfinished));
        filterList.add(getString(R.string.text_all));
    }

    private void refresh() {
        isSearching = false;
        filterIndex = FILTER_UNFINISHED_INDEX;
        mKey = DEFAULT_KEY;
        orderNumber = DEFAULT_ORDER_NUMBER;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        if (searchItem != null) {
            searchItem.collapseActionView();
        }
        if (isActive) {
            needRefresh = false;
            updateListView();
        } else {
            needRefresh = true;
        }
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

    private void jump2OrderNumber() {
        int index = 0;
        boolean found = false;
        for (DUSampling duSampling : mDuRecordList) {
            if (duSampling.getCeneixh() == orderNumber) {
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

    private void updateListView() {
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        if (isSearching) {
            samplingListPresenter.loadRecordXXs(DUSamplingInfo.FilterType.SEARCH,
                    mTaskId, mKey, mLimit);
        } else {
            switch (filterIndex) {
                case FILTER_FINISHED_INDEX:
                    samplingListPresenter.loadRecordXXs(DUSamplingInfo.FilterType.FINISHING,
                            mTaskId, mKey, mLimit);
                    break;
                case FILTER_UNFINISHED_INDEX:
                    samplingListPresenter.loadRecordXXs(DUSamplingInfo.FilterType.UNFINISHING,
                            mTaskId, mKey, mLimit);
                    break;
                case FILTER_ALL_INDEX:
                    samplingListPresenter.loadRecordXXs(DUSamplingInfo.FilterType.ALL,
                            mTaskId, mKey, mLimit);
                    break;
            }
        }
    }

    private void initSubTitle() {
        Intent intent = getIntent();
        mTaskId = intent.getIntExtra(ConstDataUtil.I_RENWUBH, 0);
//        mCh = TextUtil.getString(intent.getStringExtra(ConstDataUtil.S_CH));
        mYiChaoShu = intent.getIntExtra(ConstDataUtil.YICHAOSHU, 0);
        setActionBarSubTitle(String.valueOf(mTaskId));
    }

    private void initXListView() {
        mDUCardList = new ArrayList<>();
        mDuRecordList = new ArrayList<>();

        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(true);
        mMyListAdapter = new MyListAdapter(this, R.layout.item_samplinglist, mDuRecordList, mDUCardList);
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

                                //上传
                                if (canUpload) {
                                    needDialog = true;
                                    uploadSamplingVolume(mTaskId);
                                } else {
                                    ApplicationsUtil.showMessage(SamplingListActivity.this,
                                            R.string.text_volume_is_not_completed);
                                }
                            }
                        }, delayMillis);
            }

            @Override
            public void onLoadMore() {

            }
        });

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        samplingListPresenter.loadCardXXs(mTaskId);
    }

    private void popupSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = MainApplication.get(this).parseSyncSamplingDataInfo(syncDataEnd);
        if (!TextUtil.isNullOrEmpty(result)) {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(result)
                    .positiveText(R.string.text_ok)
                    .show();
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(R.string.text_not_synchronizing_data)
                    .positiveText(R.string.text_ok)
                    .show();
        }
    }

    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInfater = null;
        private int mResource;
        private List<DUSampling> mListData;
        private List<DUCard> mBiaoKaXXList;

        public MyListAdapter(Context context,
                             int resource,
                             List<DUSampling> listData,
                             List<DUCard> cardList) {
            mResource = resource;
            mInfater = LayoutInflater.from(context);
            mListData = listData;
            mBiaoKaXXList = cardList;
        }

        public void setDataList(List<DUSampling> duSamplingList) {
            mListData = duSamplingList;
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
            return mListData.size();
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
                convertView = mInfater.inflate(mResource, null);
                myListHolder = new MyListHolder();
                myListHolder.tv_xuhao = (TextView) convertView.findViewById(R.id.tv_xuhao);
                myListHolder.tv_cid = (TextView) convertView.findViewById(R.id.tv_cid);
                myListHolder.tv_yonghum = (TextView) convertView.findViewById(R.id.tv_yonghum);
                myListHolder.tv_yonghudz = (TextView) convertView.findViewById(R.id.tv_yonghudz);
                myListHolder.img = (ImageView) convertView.findViewById(R.id.img_state);
                myListHolder.tvImageCount = (TextView) convertView.findViewById(R.id.tv_state);
                myListHolder.row1 = (LinearLayout) convertView.findViewById(R.id.sub_row1);
                myListHolder.row2 = (LinearLayout) convertView.findViewById(R.id.sub_row2);
                myListHolder.row3 = (LinearLayout) convertView.findViewById(R.id.sub_row3);
                myListHolder.tvShangCiCM = (TextView) convertView.findViewById(R.id.tv_shangcicm);
                myListHolder.tvBenCiCM = (TextView) convertView.findViewById(R.id.tv_bencicm);
                myListHolder.tvShangCiCMM = (TextView) convertView.findViewById(R.id.tv_shangcicmm);
                myListHolder.tvBenCiCMM = (TextView) convertView.findViewById(R.id.tv_bencicmm);
                myListHolder.tvShangCiCMZ = (TextView) convertView.findViewById(R.id.tv_shangcicmz);
                myListHolder.tvBenCiCMZ = (TextView) convertView.findViewById(R.id.tv_bencicmz);
                myListHolder.tvChaoJianSL = (TextView) convertView.findViewById(R.id.tv_chaojiansl);
                myListHolder.tvChaoBiaoZT = (TextView) convertView.findViewById(R.id.tv_chaobiaozt);
                myListHolder.tvShuiBiaoGYH = (TextView) convertView.findViewById(R.id.tv_shuibiaogyh);
                myListHolder.tvKouJing = (TextView) convertView.findViewById(R.id.tv_koujing);
                myListHolder.tvJiChaSL = (TextView) convertView.findViewById(R.id.tv_jcchaojiansl);
                myListHolder.tvJiChaCM = (TextView) convertView.findViewById(R.id.tv_jichacm);
                myListHolder.mCardView = (CardView) convertView.findViewById(R.id.fs_cardview);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
                resetViewHolder(myListHolder);
            }

            // 获取数据
            DUSampling duRecord = mListData.get(position);
            DUCard duCard = getBiaoKaXX(duRecord.getCid());
            if (duCard != null) {
                // number
                myListHolder.tv_xuhao.setText(String.valueOf(duRecord.getCeneixh()));

                // cid
                myListHolder.tv_cid.setText(duCard.getCid());

                // name
                myListHolder.tv_yonghum.setText(duCard.getKehumc());

                // address
                myListHolder.tv_yonghudz.setText(duCard.getDizhi());

                // chaobiaobz
                int chaoBiaoBZ = duRecord.getIchaobiaobz();
                int shangChuanBZ = duRecord.getShangchuanbz();
                if (chaoBiaoBZ > 0) {
                    int icon;
                    switch (chaoBiaoBZ) {
                        case DURecord.CHAOBIAOBZ_YICHAO: // 已抄
                            // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                            // R.drawable.ic_ok
                            // : R.drawable.ic_shangchuan;
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

                    if (duRecord.getLastReadingChild() == 0) { // one card
                        myListHolder.row1.setVisibility(View.VISIBLE);
                        myListHolder.row2.setVisibility(View.GONE);
                        myListHolder.row3.setVisibility(View.GONE);

                        myListHolder.tvShangCiCM.setText(String.valueOf(duRecord.getShangcicm()));
                        myListHolder.tvBenCiCM.setText(String.valueOf(duRecord.getBencicm()));
                    } else { // two cards
                        myListHolder.row1.setVisibility(View.GONE);
                        myListHolder.row2.setVisibility(View.VISIBLE);
                        myListHolder.row3.setVisibility(View.VISIBLE);

                        // parent
                        myListHolder.tvShangCiCMM.setText(String.valueOf(duRecord.getShangcicm()));
                        myListHolder.tvBenCiCMM.setText(String.valueOf(duRecord.getBencicm()));

                        // child
                        myListHolder.tvShangCiCMZ.setText(String.valueOf(duRecord.getLastReadingChild()));
                        myListHolder.tvBenCiCMZ.setText(String.valueOf(duRecord.getReadingChild()));
                    }

                    myListHolder.tvChaoJianSL.setText(String.valueOf(duRecord.getChaojiansl()));
                    myListHolder.tvChaoBiaoZT.setText(duRecord.getJiChaZTMC());
                } else {
                    if (duRecord.getLastReadingChild() == 0) { // one card
                        myListHolder.row1.setVisibility(View.VISIBLE);
                        myListHolder.row2.setVisibility(View.GONE);
                        myListHolder.row3.setVisibility(View.GONE);

                        myListHolder.tvShangCiCM.setText(String.valueOf(duRecord.getShangcicm()));
                        myListHolder.tvBenCiCM.setText(duRecord.getBencicm() == 0 ? "" : String.valueOf(duRecord.getBencicm()));
                    } else { // two cards
                        myListHolder.row1.setVisibility(View.GONE);
                        myListHolder.row2.setVisibility(View.VISIBLE);
                        myListHolder.row3.setVisibility(View.VISIBLE);

                        // parent
                        myListHolder.tvShangCiCMM.setText(String.valueOf(duRecord.getShangcicm()));
                        myListHolder.tvBenCiCMM.setText(TextUtil.EMPTY);

                        // child
                        myListHolder.tvShangCiCMZ.setText(String.valueOf(duRecord.getLastReadingChild()));
                        myListHolder.tvBenCiCMZ.setText(TextUtil.EMPTY);
                    }

                    myListHolder.tvChaoJianSL.setText(duRecord.getChaojiansl() == 0 ? "" : String.valueOf(duRecord.getChaojiansl()));
                    myListHolder.tvChaoBiaoZT.setText(duRecord.getJiChaZTMC());
                    myListHolder.img.setImageDrawable(null);
                }

                myListHolder.tvShuiBiaoGYH.setText(TextUtil.getString(duCard.getShuibiaogyh()));
                myListHolder.tvKouJing.setText(TextUtil.getString(duCard.getKoujingmc()));

                if(duRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO){
                    myListHolder.tvJiChaSL.setText(String.valueOf(duRecord.getJiChaSL()));
                    myListHolder.tvJiChaCM.setText(String.valueOf(duRecord.getJiChaCM()));
                }else {
                    myListHolder.tvJiChaSL.setText("");
                    myListHolder.tvJiChaCM.setText("");
                }

                myListHolder.tvImageCount.setText("0");
                myListHolder.mCardView.setTag(duRecord);
                myListHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object object = v.getTag();
                        if (object instanceof DUSampling) {
                            DUSampling item = (DUSampling) object;
                            Intent intent = new Intent(SamplingListActivity.this, SamplingRecordActivity.class);
                            putIntentArguments(intent, item);
                            startActivity(intent);
                        }
                    }
                });
            }

            return convertView;
        }

    }

    private void putIntentArguments(Intent intent, DUSampling item) {
        intent.putExtra(ConstDataUtil.S_CH, item.getCh());
        intent.putExtra(ConstDataUtil.S_CID, item.getCid());
        intent.putExtra(ConstDataUtil.I_RENWUBH, item.getRenwubh());
        intent.putExtra(ConstDataUtil.I_CENEIXH, item.getCeneixh());
        intent.putExtra(ConstDataUtil.I_LASTREADINGCHILD, item.getLastReadingChild());
        intent.putExtra(ConstDataUtil.YICHAOSHU, mYiChaoShu);
        intent.putExtra(ConstDataUtil.STARTXH, mStartXH);
        intent.putExtra(ConstDataUtil.ENDXH, mEndXH);
        intent.putExtra(ConstDataUtil.STARTCID,mStartCID);
        intent.putExtra(ConstDataUtil.ENDCID,mEndCID);
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
        myListHolder.tvShangCiCMM.setText(TextUtil.EMPTY);
        myListHolder.tvBenCiCMM.setText(TextUtil.EMPTY);
        myListHolder.tvShangCiCMZ.setText(TextUtil.EMPTY);
        myListHolder.tvBenCiCMZ.setText(TextUtil.EMPTY);
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
        private TextView tvImageCount;

        //last and current reading
        private LinearLayout row1;
        private LinearLayout row2;
        private LinearLayout row3;

        // one card
        private TextView tvShangCiCM;
        private TextView tvBenCiCM;

        // two cards
        private TextView tvShangCiCMM;
        private TextView tvBenCiCMM;
        private TextView tvShangCiCMZ;
        private TextView tvBenCiCMZ;

        // shuiliang & zhuangtai
        private TextView tvChaoJianSL;
        private TextView tvChaoBiaoZT;

        private TextView tvShuiBiaoGYH;
        private TextView tvKouJing;

        private TextView tvJiChaSL;
        private TextView tvJiChaCM;

        private CardView mCardView;
    }
}
