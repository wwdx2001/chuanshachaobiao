package com.sh3h.meterreading.ui.volume;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.record.RecordActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.maxwin.view.XListView;

/**
 * 册本列表
 */
public class VolumeListActivity extends ParentActivity implements VolumeListMvpView,
        View.OnClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = "VolumeListActivity";

    private static final int OFFSET = 10;
    private static final int MIN_CONUT = 9;

    private static final int FILTER_NORAML_INDEX = 0; // 只显示正常
    private static final int FILTER_UNNORMAL_INDEX = 1; // 只显示异常
    private static final int FILTER_ALL_INDEX = 2; // 显示全部
    private static final int FILTER_FINISH_INDEX = 3; // 只显示完成
    private static final int FILTER_UNFINISH_INDEX = 4; // 显示未完

    private static final String DEFAULT_KEY = "%%%%";
    private static final int DEFAULT_ORDER_NUMBER = -1;

    public static final int ADJUST_NUMBER_CODE = 1000;

    @Inject
    VolumeListPresenter mVolumeListPresenter;

    @Inject
    Bus mEventBus;

    @Inject
    ConfigHelper mConfigHelper;

    @Bind(R.id.avl_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    @Bind(R.id.fcbl_list)
    XListView mXListView;

    private String mCh;
    private int mTaskId;

    private MyListAdapter mMyListAdapter;
    private List<DUCard> mDUCardList;
    private List<DURecord> mDuRecordList;

    private long mLimit;
    private int mCurrentCount;

    private MenuItem searchItem;
    private MenuItem normalItem, unNormalItem, allItem;
    private MenuItem finishItem, unFinishItem, allFinishItem;
    private MenuItem jumpNumberMenuItem;
    private MenuItem filterMenuItem;
    private MenuItem adjustMenuItem;
    private MenuItem newCardMenuItem;
    private MenuItem refreshCardMenuItem;

    private List<String> filterList;
    private boolean isSearching;
    private int filterIndex;
    private String mKey;
    private int orderNumber;
    private boolean isReadyJumping;

    private int mYiChaoShu;
    private int mStartXH;
    private int mEndXH;
    private int mZhangWuNY;

    private boolean canUpload;

    public VolumeListActivity() {
        mCh = null;
        mTaskId = 0;
        mMyListAdapter = null;
        mDUCardList = null;
        mDuRecordList = null;
        mLimit = 0;
        mCurrentCount = 0;
        searchItem = null;
        jumpNumberMenuItem = null;
        filterMenuItem = null;
        adjustMenuItem = null;
        filterList = null;
        isSearching = false;
        filterIndex = FILTER_ALL_INDEX;
        mKey = DEFAULT_KEY;
        orderNumber = DEFAULT_ORDER_NUMBER;
        isReadyJumping = false;
        mYiChaoShu = 0;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        canUpload = false;
        mZhangWuNY = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setActionBarBackButtonEnable();
        mVolumeListPresenter.attachView(this);
        mEventBus.register(this);

        initSubTitle();
        initFilterList();
        initXListView();

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

        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mVolumeListPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_volume_list, menu);

        searchItem = menu.findItem(R.id.mvl_action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

//        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) searchView.findViewById(id);
//        textView.setTextColor(Color.WHITE);

//        jumpNumberMenuItem = menu.findItem(R.id.mvl_jump_number);
//        jumpNumberMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            private EditText editText;
//
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (!isMenuEnable) {
//                    return false;
//                }
//
//                searchItem.collapseActionView();
//                orderNumber = DEFAULT_ORDER_NUMBER;
//                editText = null;
//
//                if (isSearching
//                        || (filterIndex != FILTER_ALL_INDEX)) {
//                    isSearching = false;
//                    mKey = DEFAULT_KEY;
//                    filterIndex = FILTER_ALL_INDEX;
//                    isReadyJumping = false;
//                    updateListView();
//                } else {
//                    isSearching = false;
//                    mKey = DEFAULT_KEY;
//                    filterIndex = FILTER_ALL_INDEX;
//                }
//
//                boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
//                MaterialDialog dialog = new MaterialDialog.Builder(VolumeListActivity.this)
//                        .title(R.string.text_jump_number)
//                        .customView(R.layout.dialog_jump_number2, true)
//                        .positiveText(R.string.text_ok)
//                        .negativeText(android.R.string.cancel)
//                        .buttonsGravity(leftOrRight)
//                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                if (!isReadyJumping) {
//                                    LogUtil.i(TAG, "---jump---error");
//                                    ApplicationsUtil.showMessage(VolumeListActivity.this,
//                                            R.string.text_jump_number_error);
//                                    return;
//                                }
//
//                                if (editText != null) {
//                                    orderNumber = TextUtil.getInt(editText.getText().toString());
//                                    if (orderNumber != DEFAULT_ORDER_NUMBER) {
//                                        jump2OrderNumber();
//                                    }
//                                }
//                            }
//                        })
//                        .build();
//                if (dialog.getCustomView() != null) {
//                    editText = (EditText) dialog.getCustomView().findViewById(R.id.et_order_number);
//                    editText.setText("");
//                }
//
//                dialog.showLeftOrRight(leftOrRight);
//                dialog.show();
//                return true;
//            }
//        });

        normalItem = menu.findItem(R.id.action_show_normal);
        normalItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchItem.collapseActionView();
                isSearching = false;
                mKey = DEFAULT_KEY;
                normalItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_NORAML_INDEX;
                updateListView();
                return true;
            }
        });

        unNormalItem = menu.findItem(R.id.action_show_unnormal);
        unNormalItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchItem.collapseActionView();
                isSearching = false;
                mKey = DEFAULT_KEY;
                unNormalItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_UNNORMAL_INDEX;
                updateListView();
                return true;
            }
        });

        allItem = menu.findItem(R.id.action_show_all);
        allItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchItem.collapseActionView();
                isSearching = false;
                mKey = DEFAULT_KEY;
                allItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_ALL_INDEX;
                updateListView();
                return true;
            }
        });

        unFinishItem = menu.findItem(R.id.action_show_unfinished);
        unFinishItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchItem.collapseActionView();
                isSearching = false;
                mKey = DEFAULT_KEY;
                unFinishItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_UNFINISH_INDEX;
                updateListView();
                return true;
            }
        });

        finishItem = menu.findItem(R.id.action_show_finished);
        finishItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchItem.collapseActionView();
                isSearching = false;
                mKey = DEFAULT_KEY;
                finishItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_FINISH_INDEX;
                updateListView();
                return true;
            }
        });

        allFinishItem = menu.findItem(R.id.mul_action_show_all);
        allFinishItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchItem.collapseActionView();
                isSearching = false;
                mKey = DEFAULT_KEY;
                allFinishItem.setChecked(true);
                orderNumber = DEFAULT_ORDER_NUMBER;
                filterIndex = FILTER_ALL_INDEX;
                updateListView();
                return true;
            }
        });

//        filterMenuItem = menu.findItem(R.id.mvl_filter);
//        filterMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                if (filterList == null) {
//                    return true;
//                }
//
//                searchItem.collapseActionView();
//                isSearching = false;
//                mKey = DEFAULT_KEY;
//                orderNumber = DEFAULT_ORDER_NUMBER;
//                boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
//                new MaterialDialog.Builder(VolumeListActivity.this)
//                        .title(R.string.text_prompt)
//                        .items(filterList)
//                        .itemsCallbackSingleChoice(filterIndex,
//                                new MaterialDialog.ListCallbackSingleChoice() {
//                                    @Override
//                                    public boolean onSelection(MaterialDialog dialog, View view,
//                                                               int which, CharSequence text) {
//                                        if (text == null) {
//                                            return true;
//                                        } else if (text.toString().equals(getString(R.string.text_normal))) {
//                                            filterIndex = FILTER_NORAML_INDEX;
//                                        } else if (text.toString().equals(getString(R.string.text_unnormal))) {
//                                            filterIndex = FILTER_UNNORMAL_INDEX;
//                                        } else {
//                                            filterIndex = FILTER_ALL_INDEX;
//                                        }
//
//                                        updateListView();
//                                        return true;
//                                    }
//                                })
//                        .positiveText(R.string.text_ok)
//                        .negativeText(R.string.text_cancel)
//                        .buttonsGravity(leftOrRight)
//                        .show(leftOrRight);
//                return true;
//            }
//        });

//        adjustMenuItem = menu.findItem(R.id.mvl_adjust);
//        adjustMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (!isMenuEnable || !canOperate()) {
//                    return true;
//                }
//
//                searchItem.collapseActionView();
//                Intent intent = new Intent(VolumeListActivity.this, AdjustNumberActivity.class);
//                intent.putExtra(ConstDataUtil.S_CH, mCh);
//                intent.putExtra(ConstDataUtil.I_RENWUBH, mTaskId);
//                startActivityForResult(intent, ADJUST_NUMBER_CODE);
//                return true;
//            }
//        });

//        newCardMenuItem = menu.findItem(R.id.mvl_new_card);
//        newCardMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (!isMenuEnable || !canOperate()) {
//                    return true;
//                }
//
//                newCardMenuItem.collapseActionView();
//                Intent intent = new Intent(VolumeListActivity.this, AdjustTemporaryActivity.class);
//                intent.putExtra(ConstDataUtil.S_CH, mCh);
//                startActivityForResult(intent, ADJUST_NUMBER_CODE);
//                return true;
//            }
//        });

//        refreshCardMenuItem = menu.findItem(R.id.mvl_refresh_card);
//        refreshCardMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (!isMenuEnable || !canOperate()) {
//                    return true;
//                }
//
//                refreshCardMenuItem.collapseActionView();
//                mVolumeListPresenter.checkForUpdatingCard(mTaskId, mCh);
//                return true;
//            }
//        });

//        if (mConfigHelper.getRegion().equals(SystemConfig.REGION_WENZHOU)) {
//            adjustMenuItem.setVisible(true);
//            newCardMenuItem.setVisible(true);
//        } else {
//            adjustMenuItem.setVisible(false);
//            newCardMenuItem.setVisible(false);
//        }


        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null) {
            isSearching = true;
            filterIndex = FILTER_ALL_INDEX;
            mKey = String.format("%%%s%%", query);
            orderNumber = DEFAULT_ORDER_NUMBER;
            updateListView();
        }

        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && (searchItem != null)
                && (searchItem.isActionViewExpanded())) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ADJUST_NUMBER_CODE) {
            LogUtil.i(TAG, "---onActivityResult---");

            initPartVariables();
            searchItem.collapseActionView();
            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mVolumeListPresenter.loadCardXXs(mCh);
        }
    }

    @Override
    public void onLoadRecords(List<DURecord> duRecordList) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duRecordList == null) {
            LogUtil.i(TAG, "---onLoadRecords---null or size <= 0");
            return;
        }

        mDuRecordList = duRecordList;
        mMyListAdapter.setDataList(mDuRecordList);
        mMyListAdapter.notifyDataSetChanged();

        canUpload = false;
        int size = mDuRecordList.size();
        if (size > 0) {
            if ((mStartXH == DEFAULT_ORDER_NUMBER) || (mEndXH == DEFAULT_ORDER_NUMBER)) {
                mStartXH = mDuRecordList.get(0).getCeneixh();
                mEndXH = mDuRecordList.get(size - 1).getCeneixh();

                mYiChaoShu = 0;
                for (DURecord duRecord : duRecordList) {
                    if (duRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_YICHAO) {
                        mYiChaoShu++;
                    }
                }
            }

            if (filterIndex == FILTER_ALL_INDEX){
                boolean isRecordOver = true;
                for (DURecord duRecord : duRecordList) {
                    if (duRecord.getIchaobiaobz() == DURecord.CHAOBIAOBZ_WEICHAO) {
                        isRecordOver = false;
                        break;
                    }
                }

                showOrHideMenuItem(isRecordOver);
            }

            isReadyJumping = true;
        }

        if (mYiChaoShu > 0) {
            canUpload = true;
        }

        ApplicationsUtil.showMessage(this, String.format(ConstDataUtil.LOCALE, "%s%d",
                getString(R.string.text_record_number), mDuRecordList.size()));
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
        mVolumeListPresenter.loadRecordXXs(DURecordInfo.FilterType.ALL, mTaskId, mCh, mKey, mLimit);
    }

    @Override
    public void onError(String message) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    public void onCheckForUpdatingCard(boolean canUpdate) {
        if (canUpdate) {
            needDialog = true;
            updateVolumeCards(mTaskId, mCh);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_uploadCards_firstly);
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
            case UPLOADING_VOLUME:
                refresh(false);
                popupSyncDataInfo(syncDataEnd);
                break;
            case UPDATING_VOLUME_CARD:
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
        isSearching = false;
        filterIndex = FILTER_ALL_INDEX;
        mKey = DEFAULT_KEY;
        orderNumber = DEFAULT_ORDER_NUMBER;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        if (searchItem != null) {
            searchItem.collapseActionView();
        }

        if (isActive) {
            needRefresh = false;
            if (needRefreshCards) {
                mSmoothProgressBar.setVisibility(View.VISIBLE);
                mVolumeListPresenter.loadCardXXs(mCh);
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
        isSearching = false;
        filterIndex = FILTER_ALL_INDEX;
        mKey = DEFAULT_KEY;
        orderNumber = DEFAULT_ORDER_NUMBER;
        isReadyJumping = false;
        mYiChaoShu = 0;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        canUpload = false;
    }

    private void initSubTitle() {
        Intent intent = getIntent();
        mTaskId = intent.getIntExtra(ConstDataUtil.I_RENWUBH, 0);
        mCh = TextUtil.getString(intent.getStringExtra(ConstDataUtil.S_CH));
        mYiChaoShu = intent.getIntExtra(ConstDataUtil.YICHAOSHU, 0);
        mZhangWuNY = intent.getIntExtra(ConstDataUtil.ZHANGWUNY, 0);
        setActionBarSubTitle(mCh);
    }

    private void initFilterList() {
        filterList = new ArrayList<>();
        filterList.add(getString(R.string.text_normal));
        filterList.add(getString(R.string.text_unnormal));
        filterList.add(getString(R.string.text_all));
    }

    private void initXListView() {
        mDUCardList = new ArrayList<>();
        mDuRecordList = new ArrayList<>();

        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(true);
        mMyListAdapter = new MyListAdapter(this, R.layout.item_cebenlist, mDuRecordList, mDUCardList);
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
                                    if (!canOperate()
                                            || !canSync(mConfigHelper.getUploadingTimeError())) {
                                        return;
                                    }

                                    needDialog = true;
                                    uploadVolume(mTaskId, mCh);
                                } else {
                                    ApplicationsUtil.showMessage(VolumeListActivity.this,
                                            R.string.text_volume_is_not_completed);
                                }
                            }
                        }, delayMillis);
            }

            @Override
            public void onLoadMore() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLimit += OFFSET;//每次取10条数据
//                        resetConditions(mKey, mFilterType);
//
//                    }
//                }, delayMillis);
            }
        });

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mVolumeListPresenter.loadCardXXs(mCh);
    }

    private void updateListView() {
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        if (isSearching) {
            mVolumeListPresenter.loadRecordXXs(DURecordInfo.FilterType.SEARCH,
                    mTaskId, mCh, mKey, mLimit);
        } else {
            switch (filterIndex) {
                case FILTER_NORAML_INDEX:
                    mVolumeListPresenter.loadRecordXXs(DURecordInfo.FilterType.NORMAL,
                            mTaskId, mCh, mKey, mLimit);
                    break;
                case FILTER_UNNORMAL_INDEX:
                    mVolumeListPresenter.loadRecordXXs(DURecordInfo.FilterType.UNNORMAL,
                            mTaskId, mCh, mKey, mLimit);
                    break;
                case FILTER_ALL_INDEX:
                    mVolumeListPresenter.loadRecordXXs(DURecordInfo.FilterType.ALL,
                            mTaskId, mCh, mKey, mLimit);
                    break;
                case FILTER_UNFINISH_INDEX:
                    mVolumeListPresenter.loadRecordXXs(DURecordInfo.FilterType.UNFINISH,
                            mTaskId, mCh, mKey, mLimit);
                    break;
                case FILTER_FINISH_INDEX:
                    mVolumeListPresenter.loadRecordXXs(DURecordInfo.FilterType.FINISH,
                            mTaskId, mCh, mKey, mLimit);
                    break;
                default:
                    break;
            }
        }
    }

    private void jump2OrderNumber() {
        int index = 0;
        boolean found = false;
        for (DURecord duRecord : mDuRecordList) {
            if (duRecord.getCeneixh() == orderNumber) {
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
        if (isExceedingZWNY()) {
            return false;
        }

        if (!mConfigHelper.isCurrentReadingDateValid()) {
            ApplicationsUtil.showMessage(this, R.string.text_exceed_time_limit);
            return false;
        }

        return true;
    }

    private boolean isExceedingZWNY() {
        return false;
//        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH) + 1;
//        int zhangWuNY = year * 100 + month;
//        StringBuilder sb = new StringBuilder();
//        if (mZhangWuNY != zhangWuNY) {
//            sb.append(mCh);
//        } else {
//            return false;
//        }
//
//        sb.append("\n");
//        sb.append(getResources().getString(R.string.text_zhangwuny_time_error));
//        ApplicationsUtil.showMessage(this, sb.toString());
//        return true;
    }

    private boolean isArrearOrLowIncome(DURecord duRecord, boolean isArrear) {
        for (DUCard duCard : mDUCardList) {
            boolean result = false;
            if (duCard.getCid() == null) {
                continue;
            }

            if (duCard.getCid().equals(duRecord.getCid())) {
                if (isArrear) {
                    if (duCard.getQianfeizbs() > 0) {
                        result = true;
                    }
                } else {
                    if (duCard.getDibaoyhsl() > 0) {
                        result = true;
                    }
                }

                return result;
            }
        }

        return false;
    }

    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int mResource;
        private List<DURecord> mListData;
        private List<DUCard> mBiaoKaXXList;

        public MyListAdapter(Context context,
                             int resource,
                             List<DURecord> listData,
                             List<DUCard> cardList) {
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            mListData = listData;
            mBiaoKaXXList = cardList;
        }

        public void setDataList(List<DURecord> list) {
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
                convertView = mInflater.inflate(mResource, null);
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
                myListHolder.mCardView = (CardView) convertView.findViewById(R.id.fs_cardview);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
                resetViewHolder(myListHolder);
            }

            // 获取数据
            DURecord duRecord = mListData.get(position);
            DUCard duCard = getBiaoKaXX(duRecord.getCid());
            if (duCard != null) {
                // number
                myListHolder.tv_xuhao.setText(String.valueOf(duCard.getCeneixh()));

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
/*                        case DURecord.CHAOBIAOBZ_KAIZHANG: // 开账
                            // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                            // R.drawable.ic_remote_bill
                            // : R.drawable.ic_local_bill;
                            icon = R.drawable.ic_remote_bill;
                            break;
                        case DURecord.CHAOBIAOBZ_YANCHI: // 延迟抄表
                            // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                            // R.drawable.ic_remote_delay
                            // : R.drawable.ic_local_delay;
                            icon = R.drawable.ic_remote_delay;
                            break;
                        case DURecord.CHAOBIAOBZ_WAIFUYC: // 外复延迟
                            // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                            // R.drawable.ic_remote_ex_delay
                            // : R.drawable.ic_local_ex_delay;
                            icon = R.drawable.ic_remote_ex_delay;
                            break;*/
                        default:
                            // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                            // R.drawable.ic_ok
                            // : R.drawable.ic_shangchuan;
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
                    myListHolder.tvChaoBiaoZT.setText(duRecord.getZhuangtaimc());
                } else {
                    if (duRecord.getLastReadingChild() == 0) { // one card
                        myListHolder.row1.setVisibility(View.VISIBLE);
                        myListHolder.row2.setVisibility(View.GONE);
                        myListHolder.row3.setVisibility(View.GONE);

                        myListHolder.tvShangCiCM.setText(String.valueOf(duRecord.getShangcicm()));
                        myListHolder.tvBenCiCM.setText(TextUtil.EMPTY);
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

                    myListHolder.tvChaoJianSL.setText(TextUtil.EMPTY);
                    myListHolder.tvChaoBiaoZT.setText(TextUtil.EMPTY);
                    myListHolder.img.setImageDrawable(null);
                }

                myListHolder.tvImageCount.setText("0");

                myListHolder.mCardView.setTag(duRecord);

                myListHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!canOperate()) {
                            return;
                        }

                        Object object = v.getTag();
                        if (object instanceof DURecord) {
                            DURecord item = (DURecord) object;
                            Intent intent = new Intent(VolumeListActivity.this, RecordActivity.class);
                            putIntentArguments(intent, item);
                            startActivity(intent);
                        }
                    }
                });
            }

            return convertView;
        }
    }

    private void putIntentArguments(Intent intent, DURecord item) {
        intent.putExtra(ConstDataUtil.S_CH, item.getCh());
        intent.putExtra(ConstDataUtil.S_CID, item.getCid());
        intent.putExtra(ConstDataUtil.I_RENWUBH, item.getRenwubh());
        intent.putExtra(ConstDataUtil.I_CENEIXH, item.getCeneixh());
        intent.putExtra(ConstDataUtil.I_LASTREADINGCHILD, item.getLastReadingChild());
        intent.putExtra(ConstDataUtil.YICHAOSHU, mYiChaoShu);
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
        myListHolder.tvShangCiCMM.setText(TextUtil.EMPTY);
        myListHolder.tvBenCiCMM.setText(TextUtil.EMPTY);
        myListHolder.tvShangCiCMZ.setText(TextUtil.EMPTY);
        myListHolder.tvBenCiCMZ.setText(TextUtil.EMPTY);
        myListHolder.tvChaoJianSL.setText(TextUtil.EMPTY);
        myListHolder.tvChaoBiaoZT.setText(TextUtil.EMPTY);
    }

    private void showOrHideMenuItem(boolean finish){
        if (finishItem == null || unFinishItem == null || allFinishItem == null
                || normalItem == null || unNormalItem == null || allItem == null){
            return;
        }

        finishItem.setVisible(!finish);
        unFinishItem.setVisible(!finish);
        allFinishItem.setVisible(!finish);

        normalItem.setVisible(finish);
        unNormalItem.setVisible(finish);
        allItem.setVisible(finish);
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

        private CardView mCardView;
    }
}
