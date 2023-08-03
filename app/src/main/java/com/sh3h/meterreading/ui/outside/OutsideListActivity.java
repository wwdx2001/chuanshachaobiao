package com.sh3h.meterreading.ui.outside;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
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
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.ipc.module.MyModule;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.maxwin.view.XListView;


public class OutsideListActivity extends ParentActivity implements OutsideListMvpView,
        View.OnClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = "OutsideListActivity";

    private static final int OFFSET = 10;
    private static final int MIN_CONUT = 9;

    private static final int FILTER_FINISHED_INDEX = 0; // 只显示已抄
    private static final int FILTER_UNFINISHED_INDEX = 1; // 只显示未抄
    private static final int FILTER_ALL_INDEX = 2; // 显示全部

    private static final String DEFAULT_KEY = "%%%%";
    private static final int DEFAULT_ORDER_NUMBER = -1;

    public static final int ADJUST_NUMBER_CODE = 1000;

    @Inject
    OutsideListPresenter mOutsideListPresenter;

    @Inject
    Bus mEventBus;

    @Inject
    ConfigHelper mConfigHelper;

    @Bind(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    @Bind(R.id.fcbl_list)
    XListView mXListView;

    private String mCh;
    private int mTaskId;

    private MyListAdapter mMyListAdapter;
    private List<DUCard> mDUCardList;
    private List<DUWaiFuCBSJ> mDUWaiFuCBSJList;

    private long mLimit;
    private int mCurrentCount;

    private boolean isMenuEnable;
    private MenuItem searchItem;
    private MenuItem refreshItem;
    private MenuItem jumpNumberMenuItem;
    private MenuItem filterMenuItem;

    private List<String> filterList;
    private boolean isSearching;
    private int filterIndex;
    private String mKey;
    private int orderNumber;
    private boolean isReadyJumping;

    private int mYiChaoShu;
    private int mStartXH;
    private int mEndXH;

    private boolean canUpload;
    private boolean mMenuItemVisible;

    public OutsideListActivity() {
        mCh = null;
        mTaskId = 0;
        mMyListAdapter = null;
        mDUCardList = null;
        mDUWaiFuCBSJList = null;
        mLimit = 0;
        mCurrentCount = 0;
        isMenuEnable = true;
        searchItem = null;
        jumpNumberMenuItem = null;
        filterMenuItem = null;
        filterList = null;
        isSearching = false;
        filterIndex = FILTER_UNFINISHED_INDEX;
        mKey = DEFAULT_KEY;
        orderNumber = DEFAULT_ORDER_NUMBER;
        isReadyJumping = false;
        mYiChaoShu = 0;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        canUpload = false;
        mMenuItemVisible = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        setActionBarBackButtonEnable();
        mOutsideListPresenter.attachView(this);
        mEventBus.register(this);
        initFilterList();

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
        mOutsideListPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_outside_list, menu);

        searchItem = menu.findItem(R.id.mvl_action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
//      int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//      TextView textView = (TextView) searchView.findViewById(id);
//      textView.setTextColor(Color.WHITE);
        refreshItem = menu.findItem(R.id.mtl_action_refresh);
        refreshItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LogUtil.i(TAG, "---startVersionService---");
                needDialog = true;
                syncWaiFuCBSJS();
                return true;
            }
        });

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

                MaterialDialog dialog = new MaterialDialog.Builder(OutsideListActivity.this)
                        .title(R.string.text_jump_number)
                        .customView(R.layout.dialog_jump_number, true)
                        .positiveText(R.string.text_ok)
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (!isReadyJumping) {
                                    LogUtil.i(TAG, "---jump---error");
                                    ApplicationsUtil.showMessage(OutsideListActivity.this,
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
                new MaterialDialog.Builder(OutsideListActivity.this)
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


//        if (mConfigHelper.getRegion().equals(SystemConfig.REGION_WENZHOU)) {
//            adjustMenuItem.setVisible(true);
//            newCardMenuItem.setVisible(true);
//        } else {
//            adjustMenuItem.setVisible(false);
//            newCardMenuItem.setVisible(false);
//        }
        setMenuItemVisible(mMenuItemVisible);
        return true;
    }

    private void setMenuItemVisible(boolean isVisible) {
        searchItem.setVisible(isVisible);
        jumpNumberMenuItem.setVisible(isVisible);
        filterMenuItem.setVisible(isVisible);
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
        if (isMenuEnable && (query != null)) {
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
            mOutsideListPresenter.loadCardXXs();
        }
    }

    @Override
    public void loadWaiFuCBSJS(List<DUWaiFuCBSJ> duWaiFuCBSJs) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duWaiFuCBSJs == null) {
            LogUtil.i(TAG, "---loadWaiFuCBSJS---null");
            return;
        }

        mDUWaiFuCBSJList = duWaiFuCBSJs;
        mMyListAdapter.setDataList(mDUWaiFuCBSJList);
        mMyListAdapter.notifyDataSetChanged();

        canUpload = false;
        int size = mDUWaiFuCBSJList.size();
        if (size > 0) {
            if ((mStartXH == DEFAULT_ORDER_NUMBER) || (mEndXH == DEFAULT_ORDER_NUMBER)) {
                mStartXH = mDUWaiFuCBSJList.get(0).getCeneixh();
                mEndXH = mDUWaiFuCBSJList.get(size - 1).getCeneixh();
            }

            mYiChaoShu = 0;
            for (DUWaiFuCBSJ duWaiFuCBSJ : duWaiFuCBSJs) {
                if (duWaiFuCBSJ.getIchaobiaobz() == DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO) {
                    mYiChaoShu++;
                }
            }

            isReadyJumping = true;
        }

        if (mYiChaoShu > 0) {
            canUpload = true;
        }

        ApplicationsUtil.showMessage(this, String.format(ConstDataUtil.LOCALE, "%s%d",
                getString(R.string.text_record_number), duWaiFuCBSJs.size()));

        //主程序下标数字
//      if (size > 0) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(MyModule.PACKAGE_NAME, getApplicationContext().getPackageName());
            jsonObject.put(MyModule.ACTIVITY_NAME, OutsideListActivity.class.getName());
            JSONArray subJSONArray = new JSONArray();
            subJSONArray.put("count#" + (size - mYiChaoShu));
            jsonObject.put(MyModule.DATA, subJSONArray);
            MyModule myModule = new MyModule(jsonObject.toString());
            MainApplication.get(getApplicationContext()).setMyModule(myModule);
        } catch (Exception e) {
            e.printStackTrace();
        }

//      }

    }

    @Override
    public void onLoadCards(List<DUCard> duCardList) {
        if (duCardList == null) {
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            mMenuItemVisible = false;
            LogUtil.i(TAG, "---onLoadCards---null");
            return;
        }

        mDUCardList = duCardList;
        mMyListAdapter.setBiaoKaXXList(duCardList);
        mOutsideListPresenter.loadWaiFuCBSJS(DUWaiFuCBSJInfo.FilterType.UNFINISHING, mKey, mLimit);
    }

    @Override
    public void onError(String message) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
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
            case SYNC_WAIFUCBSJS:
                initXListView();
                popupSyncDataInfo(syncDataEnd);
                break;
            case UPLOADING_WAIFUCBSJS_MEDIAS:
                refresh();
                popupSyncDataInfo(syncDataEnd);
                break;
            case UPLOADING_OUT_RECORD_MEDIAS:
                refresh();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onInitConfigResult(UIBusEvent.InitConfigResult initConfigResult) {
        LogUtil.i(TAG, "---onInitResult---" + initConfigResult.isSuccess());
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
    public void onUpdateVolumeList(UIBusEvent.UpdateVolumeList updateVolumeList) {
        LogUtil.i(TAG, "---onUpdateVolumeList---");
        refresh();
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

    private void popupSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = MainApplication.get(this).parseOutRecordSyncDataInfo(syncDataEnd);
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

    private void initPartVariables() {
        mDUCardList = null;
        mDUWaiFuCBSJList = null;
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


    private void initFilterList() {
        filterList = new ArrayList<>();
        filterList.add(getString(R.string.text_finished));
        filterList.add(getString(R.string.text_unfinished));
        filterList.add(getString(R.string.text_all));
    }

    private void initXListView() {
        mDUCardList = new ArrayList<>();
        mDUWaiFuCBSJList = new ArrayList<>();

        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(false);
        mMyListAdapter = new MyListAdapter(this, R.layout.item_cebenlist_outside, mDUWaiFuCBSJList, mDUCardList);
        mXListView.setAdapter(mMyListAdapter);

        // 设置回调函数
//        mXListView.setXListViewListener(new XListView.IXListViewListener() {
//            private Handler handler = new Handler();
//            private final long delayMillis = 1000;
//
//            @Override
//            public void onRefresh() {
                // 模拟刷新数据，1s之后停止刷新
//                handler.postDelayed(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                String time = TextUtil.format(System.currentTimeMillis(),
//                                        TextUtil.FORMAT_DATE_NO_SECOND);
//                                mXListView.setRefreshTime(time);
//                                mXListView.stopRefresh();
//
//                                //上传
//                                if (canUpload) {
//                                    needDialog = true;
//                                    uploadWaifuCBSJSAndMedias();
//                                } else {
//                                    ApplicationsUtil.showMessage(OutsideListActivity.this,
//                                            R.string.text_out_side_is_not_completed);
//                                }
//                            }
//                        }, delayMillis);
//            }

//            @Override
//            public void onLoadMore() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLimit += OFFSET;//每次取10条数据
//                        resetConditions(mKey, mFilterType);
//
//                    }
//                }, delayMillis);
//            }
//        });

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mOutsideListPresenter.loadCardXXs();
    }

    private void updateListView() {
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        if (isSearching) {
            mOutsideListPresenter.loadWaiFuCBSJS(DUWaiFuCBSJInfo.FilterType.SEARCH, mKey, mLimit);
        } else {
            switch (filterIndex) {
                case FILTER_FINISHED_INDEX:
                    mOutsideListPresenter.loadWaiFuCBSJS(DUWaiFuCBSJInfo.FilterType.FINISHING, mKey, mLimit);
                    break;
                case FILTER_UNFINISHED_INDEX:
                    mOutsideListPresenter.loadWaiFuCBSJS(DUWaiFuCBSJInfo.FilterType.UNFINISHING, mKey, mLimit);
                    break;
                case FILTER_ALL_INDEX:
                    mOutsideListPresenter.loadWaiFuCBSJS(DUWaiFuCBSJInfo.FilterType.SEARCH_ALL, mKey, mLimit);
                    break;
            }
        }
    }

    private void jump2OrderNumber() {
        int index = 0;
        boolean found = false;
        for (DUWaiFuCBSJ duWaiFuCBSJ : mDUWaiFuCBSJList) {
            if (duWaiFuCBSJ.getCeneixh() == orderNumber) {
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

    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int mResource;
        private List<DUWaiFuCBSJ> mListData;
        private List<DUCard> mBiaoKaXXList;

        public MyListAdapter(Context context,
                             int resource,
                             List<DUWaiFuCBSJ> listData,
                             List<DUCard> cardList) {
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            mListData = listData;
            mBiaoKaXXList = cardList;
        }

        public void setDataList(List<DUWaiFuCBSJ> list) {
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
                myListHolder.tvShuiBiaoGYH = (TextView) convertView.findViewById(R.id.tv_shuibiaogyh);
                myListHolder.tvKouJing = (TextView) convertView.findViewById(R.id.tv_koujing);
                myListHolder.mCardView = (CardView) convertView.findViewById(R.id.fs_cardview);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
                resetViewHolder(myListHolder);
            }

            // 获取数据
            DUWaiFuCBSJ duWaiFuCBSJ = mListData.get(position);
            DUCard duCard = getBiaoKaXX(duWaiFuCBSJ.getCid());
            if (duCard != null) {
                // number
                myListHolder.tv_xuhao.setText(String.valueOf(duWaiFuCBSJ.getCeneixh()));

                // cid
                myListHolder.tv_cid.setText(duCard.getCid());

                // name
                myListHolder.tv_yonghum.setText(duCard.getKehumc());

                // address
                myListHolder.tv_yonghudz.setText(duCard.getDizhi());

                // chaobiaobz
                int chaoBiaoBZ = duWaiFuCBSJ.getIchaobiaobz();
                int shangChuanBZ = duWaiFuCBSJ.getShangchuanbz();
                if (chaoBiaoBZ == DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO) {
                    int icon;
                    switch (chaoBiaoBZ) {
                        case DUWaiFuCBSJ.CHAOBIAOBZ_YICHAO: // 已抄
                            // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                            // R.drawable.ic_ok
                            // : R.drawable.ic_shangchuan;
                            if (shangChuanBZ == DUWaiFuCBSJ.SHANGCHUANBZ_WEISHANGC) {
                                icon = R.mipmap.ic_shangchuan;
                            } else if (shangChuanBZ == DUWaiFuCBSJ.SHANGCHUANBZ_ZHENGZAISC) {
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

                    myListHolder.row1.setVisibility(View.VISIBLE);
                    myListHolder.row2.setVisibility(View.GONE);
                    myListHolder.row3.setVisibility(View.GONE);
                    myListHolder.tvShangCiCM.setText(String.valueOf(duWaiFuCBSJ.getShangcicm()));
                    myListHolder.tvBenCiCM.setText(String.valueOf(duWaiFuCBSJ.getBencicm()));

                    myListHolder.tvChaoJianSL.setText(String.valueOf(duWaiFuCBSJ.getChaojiansl()));
                    myListHolder.tvChaoBiaoZT.setText(duWaiFuCBSJ.getZhuangtaimc());
                } else {

                    myListHolder.row1.setVisibility(View.VISIBLE);
                    myListHolder.row2.setVisibility(View.GONE);
                    myListHolder.row3.setVisibility(View.GONE);
                    myListHolder.tvShangCiCM.setText(String.valueOf(duWaiFuCBSJ.getShangcicm()));
                    myListHolder.tvBenCiCM.setText(TextUtil.EMPTY);

                    myListHolder.tvChaoJianSL.setText(TextUtil.EMPTY);
                    myListHolder.tvChaoBiaoZT.setText(TextUtil.EMPTY);
                    myListHolder.img.setImageDrawable(null);
                }
                myListHolder.tvBenCiCM.setText(String.valueOf(duWaiFuCBSJ.getBencicm()));
                myListHolder.tvChaoBiaoZT.setText(duWaiFuCBSJ.getZhuangtaimc());
                myListHolder.tvShuiBiaoGYH.setText(TextUtil.getString(duCard.getShuibiaogyh()));
                myListHolder.tvKouJing.setText(TextUtil.getString(duCard.getKoujingmc()));
                myListHolder.tvImageCount.setText("0");
                myListHolder.mCardView.setTag(duWaiFuCBSJ);
                myListHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object object = v.getTag();
                        if (object instanceof DUWaiFuCBSJ) {
                            DUWaiFuCBSJ item = (DUWaiFuCBSJ) object;
                            Intent intent = new Intent(OutsideListActivity.this, OutsideRecordActivity.class);
                            putIntentArguments(intent, item);
                            startActivity(intent);
                        }
                    }
                });
            }

            return convertView;
        }
    }

    private void putIntentArguments(Intent intent, DUWaiFuCBSJ item) {
        intent.putExtra(ConstDataUtil.S_CH, item.getCh());
        intent.putExtra(ConstDataUtil.S_CID, item.getCid());
        intent.putExtra(ConstDataUtil.I_RENWUBH, item.getRenwubh());
        intent.putExtra(ConstDataUtil.I_CENEIXH, item.getCeneixh());
//        intent.putExtra(ConstDataUtil.I_LASTREADINGCHILD, item.getLastReadingChild());
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

        private CardView mCardView;
    }
}
