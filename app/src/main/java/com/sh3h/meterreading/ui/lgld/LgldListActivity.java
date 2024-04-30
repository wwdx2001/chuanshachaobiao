package com.sh3h.meterreading.ui.lgld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DULgld;
import com.sh3h.datautil.data.entity.DULgldInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maxwin.view.XListView;

public class LgldListActivity  extends ParentActivity implements LgldListMvpView,
        View.OnClickListener{
    private static final String TAG = "LgldListActivity";
    private static final int DEFAULT_ORDER_NUMBER = -1;

    @Inject
    LgldListPresenter presenter;
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

    private LgldListActivity.MyListAdapter mMyListAdapter;
    private List<DUCard> mDUCardList;
    private List<DULgld> lglds;

    //private MenuItem searchItem;
    private MenuItem recordLgItem, recordLdItem, delayLgItem, delayLdItem;

    private int filterIndex;
    private int mStartXH;
    private int mEndXH;

    private boolean canUpload;

    public LgldListActivity() {
        mMyListAdapter = null;
        mDUCardList = null;
        lglds = null;
        filterIndex = DULgldInfo.FILTER_RECORD_LG_INDEX;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;
        canUpload = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgld_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setActionBarBackButtonEnable();
        presenter.attachView(this);
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
        presenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lgld, menu);
        recordLdItem = menu.findItem(R.id.mul_action_show_finished_ld);
        recordLgItem = menu.findItem(R.id.mul_action_show_finished_lg);
        delayLdItem = menu.findItem(R.id.mul_action_show_yanchi_ld);
        delayLgItem = menu.findItem(R.id.mul_action_show_yanchi_lg);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mul_action_show_finished_ld:
                recordLdItem.setChecked(true);
                filterIndex = DULgldInfo.FILTER_RECORD_Ld_INDEX;
                updateListView();
                break;
            case R.id.mul_action_show_finished_lg:
                recordLgItem.setChecked(true);
                filterIndex = DULgldInfo.FILTER_RECORD_LG_INDEX;
                updateListView();
                break;
            case R.id.mul_action_show_yanchi_ld:
                delayLdItem.setChecked(true);
                filterIndex = DULgldInfo.FILTER_DELAY_LD_INDEX;
                updateListView();
                break;
            case R.id.mul_action_show_yanchi_lg:
                delayLgItem.setChecked(true);
                filterIndex = DULgldInfo.FILTER_DELAY_LG_INDEX;
                updateListView();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoadRecords(List<DULgld> duRecordList) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duRecordList == null) {
            LogUtil.i(TAG, "---onLoadRecords---null or size <= 0");
            return;
        }

        lglds = duRecordList;
        mMyListAdapter.setDataList(lglds);
        mMyListAdapter.notifyDataSetChanged();

        canUpload = false;
        int size = lglds.size();
        if (size > 0) {
            if ((mStartXH == DEFAULT_ORDER_NUMBER) || (mEndXH == DEFAULT_ORDER_NUMBER)) {
                mStartXH = lglds.get(0).getCeNeiXH();
                mEndXH = lglds.get(size - 1).getCeNeiXH();
            }
        }

        ApplicationsUtil.showMessage(this, String.format(ConstDataUtil.LOCALE, "%s%d",
                getString(R.string.text_record_number), lglds.size()));
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
        presenter.loadRecordXXs(filterIndex);
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
        filterIndex = DULgldInfo.FILTER_DELAY_LG_INDEX;
        mStartXH = DEFAULT_ORDER_NUMBER;
        mEndXH = DEFAULT_ORDER_NUMBER;

        if (isActive) {
            needRefresh = false;
            if (needRefreshCards) {
                mSmoothProgressBar.setVisibility(View.VISIBLE);
                presenter.loadCardXXs();
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

    private void initXListView() {
        mDUCardList = new ArrayList<>();
        lglds = new ArrayList<>();

        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(true);
        mMyListAdapter = new LgldListActivity.MyListAdapter(this, R.layout.item_lgld_list, lglds, mDUCardList);
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

//                                //上传
//                                if (canUpload) {
//                                    if (!canSync(mConfigHelper.getUploadingTimeError())) {
//                                        return;
//                                    }
//
//                                    needDialog = true;
//                                    uploadDelay();
//                                } else {
//                                    ApplicationsUtil.showMessage(LgldListActivity.this,
//                                            R.string.text_volume_is_not_completed);
//                                }
                            }
                        }, delayMillis);
            }

            @Override
            public void onLoadMore() {
            }
        });

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        presenter.loadCardXXs();
    }

    private void updateListView() {
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        presenter.loadRecordXXs(filterIndex);
    }

    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int mResource;
        private List<DULgld> mListData;
        private List<DUCard> mBiaoKaXXList;

        public MyListAdapter(Context context,
                             int resource,
                             List<DULgld> listData,
                             List<DUCard> cardList) {
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            mListData = listData;
            mBiaoKaXXList = cardList;
        }

        public void setDataList(List<DULgld> list) {
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
            LgldListActivity.MyListHolder myListHolder;

            if (convertView == null) {
                convertView = mInflater.inflate(mResource, null);
                myListHolder = new LgldListActivity.MyListHolder();
                myListHolder.tv_xuhao = (TextView) convertView.findViewById(R.id.tv_xuhao);
                myListHolder.tv_cid = (TextView) convertView.findViewById(R.id.tv_cid);
                myListHolder.tv_yonghum = (TextView) convertView.findViewById(R.id.tv_yonghum);
                myListHolder.tv_yonghudz = (TextView) convertView.findViewById(R.id.tv_yonghudz);
                myListHolder.img = (ImageView) convertView.findViewById(R.id.img_state);
                myListHolder.tvShangCiCM = (TextView) convertView.findViewById(R.id.tv_shangcicm);
                myListHolder.tvBenCiCM = (TextView) convertView.findViewById(R.id.tv_bencicm);
                myListHolder.tvChaoJianSL = (TextView) convertView.findViewById(R.id.tv_chaojiansl);
                myListHolder.tvChaoBiaoZT = (TextView) convertView.findViewById(R.id.tv_chaobiaozt);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (LgldListActivity.MyListHolder) convertView.getTag();
                resetViewHolder(myListHolder);
            }

            // 获取数据
            DULgld lgld = mListData.get(position);
            DUCard duCard = getBiaoKaXX(lgld.getCid());
            if (duCard != null) {
                myListHolder.tv_xuhao.setText(String.valueOf(duCard.getCeneixh()));
                myListHolder.tv_cid.setText(duCard.getCid());
                myListHolder.tv_yonghum.setText(duCard.getKehumc());
                myListHolder.tv_yonghudz.setText(duCard.getDizhi());
                myListHolder.tvShangCiCM.setText(String.valueOf(lgld.getShangCiCM()));
                myListHolder.tvBenCiCM.setText(String.valueOf(lgld.getBenCiCM()));
                myListHolder.tvChaoJianSL.setText(String.valueOf(lgld.getChaoJianSL()));
                myListHolder.tvChaoBiaoZT.setText(lgld.getZhuangTaiMC());
                int chaoBiaoBZ = lgld.getChaoBiaoBZ();
                if (chaoBiaoBZ == 0){
                    myListHolder.img.setImageDrawable(null);
                }else {
                    myListHolder.img.setImageResource(R.mipmap.ic_ok);
                }
            }

            return convertView;
        }
    }

    protected void resetViewHolder(LgldListActivity.MyListHolder myListHolder) {
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
        private TextView tvChaoJianSL;
        private TextView tvChaoBiaoZT;
    }
}
