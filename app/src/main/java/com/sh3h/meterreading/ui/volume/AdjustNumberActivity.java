package com.sh3h.meterreading.ui.volume;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardResult;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DURecordResult;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperAdapter;
import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperViewHolder;
import co.paulburke.android.itemtouchhelper.helper.SimpleItemTouchHelperCallback;

/**
 * Created by xulongjun on 2016/2/4.
 */
public class AdjustNumberActivity extends ParentActivity implements AdjustNumberMvpView,
        MenuItem.OnMenuItemClickListener {
    private static final String TAG = "AdjustNumberActivity";
    private static final String POSITIVE = "POSITIVE";

    @Inject
    AdjustNumberPresenter mAdjustNumberPresenter;

    @Inject
    ConfigHelper mConfigHelper;

    @BindView(R.id.adjust_number_list)
    RecyclerView mAdjustNumberList;

    @BindView(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    private MenuItem okMenuItem;
    private MenuItem adjustVolumeMenuItem;
    private MenuItem tableNumberMenuItem;

    private String mCeBenBH;
    private int mRenWuBH;

    private List<DUTask> mTaskList;
    private List<DURecord> mListData;
    private List<DUCard> mDuCardList;
    private List<DURecord> mRemovedVolumeListData;
    private List<DUCard> mRemovedVolumeDuCardList;
    private RecyclerListAdapter recyclerListAdapter;
    private ItemTouchHelper mItemTouchHelper;

    private EditText editText;
    private String tableNumber;

    public AdjustNumberActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_number);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        setActionBarBackButtonEnable();
        mAdjustNumberPresenter.attachView(this);

        initSubTitle();
        initRecyclerView();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adjust_number_lr, menu);
        adjustVolumeMenuItem = menu.findItem(R.id.action_style_adjust_volume);
        okMenuItem = menu.findItem(R.id.action_style_ok);
        tableNumberMenuItem = menu.findItem(R.id.action_style_table_number);
        adjustVolumeMenuItem.setOnMenuItemClickListener(this);
        okMenuItem.setOnMenuItemClickListener(this);
        tableNumberMenuItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onLoadTasks(List<DUTask> duTaskList) {

        if ((duTaskList == null) || (duTaskList.size() <= 0)) {
            LogUtil.i(TAG, "---onLoadTasks--null or size <= 0");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            return;
        }

        mTaskList = new ArrayList<>();
        for (DUTask duTask : duTaskList) {
//            if ((duTask.getRenWuBH() == mRenWuBH)
//                    && duTask.getcH().equals(mCeBenBH)) {
//                continue;
//            }
            mTaskList.add(duTask);
        }

        if (mTaskList.size() <= 0) {
            LogUtil.i(TAG, "---onLoadTasks--size <= 0");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            return;
        }

        mAdjustNumberPresenter.loadCardXXs(mCeBenBH);
    }

    @Override
    public void onLoadCards(List<DUCard> duCardList) {
        if ((duCardList == null) || (duCardList.size() <= 0)) {
            LogUtil.i(TAG, "---onLoadCards--null or size <= 0");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            return;
        }

        mDuCardList = duCardList;
        recyclerListAdapter.setBiaoKaXXList(duCardList);
        mAdjustNumberPresenter.loadRecordXXs(DURecordInfo.FilterType.ALL, mRenWuBH,
                mCeBenBH, "", 0);
    }

    @Override
    public void onLoadRecords(List<DURecord> duRecordList) {
        LogUtil.i(TAG, "---onLoadRecords---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duRecordList == null) {
            LogUtil.i(TAG, "---onLoadRecords---null or size <= 0");
            return;
        }

        mListData = duRecordList;
        recyclerListAdapter.setDataList(duRecordList);
        recyclerListAdapter.notifyDataSetChanged();
        adjustVolumeMenuItem.setVisible(false);
        tableNumberMenuItem.setVisible(true);

        ApplicationsUtil.showMessage(this, R.string.text_refresh_cards_firstly);
    }

    @Override
    public void onAdjustCards(DUCardResult duCardResult) {
        LogUtil.i(TAG, "---onAdjustCards---");
        mAdjustNumberPresenter.adjustRecordXXs(mListData);
    }

    @Override
    public void onAdjustRecords(DURecordResult duRecordResult) {
        LogUtil.i(TAG, "---onAdjustRecords 1---");
        mAdjustNumberPresenter.adjustTask(mRenWuBH, mCeBenBH);
    }

    @Override
    public void onAdjustTask(Boolean aBoolean) {
        LogUtil.i(TAG, "---onAdjustTask 1---");

        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (aBoolean) {
            LogUtil.i(TAG, "---onAdjustRecords 2---");
            ApplicationsUtil.showMessage(this, R.string.text_adjust_number_success);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_failure_to_adjust_number);
        }

        setResult(VolumeListActivity.ADJUST_NUMBER_CODE);
        finish();
    }

    @Override
    public void onAdjustVolume() {
        LogUtil.i(TAG, "---onAdjustVolume 1---");
        setResult(VolumeListActivity.ADJUST_NUMBER_CODE);
        finish();
    }

    @Override
    public void onError(String message) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        ButterKnife.unbind(this);
        mAdjustNumberPresenter.detachView();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_style_ok:
                onOkMenu();
                break;
            case R.id.action_style_adjust_volume:
                onAdjustVolumeMenu();
                break;
            case R.id.action_style_table_number:
                onTableNumberMenu();
                break;
        }

        return true;
    }

    private void onTableNumberMenu() {
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        MaterialDialog dialog = new MaterialDialog.Builder(AdjustNumberActivity.this)
                .title(R.string.text_table_number)
                .customView(R.layout.dialog_jump_number, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .buttonsGravity(leftOrRight)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if (editText != null) {
                            tableNumber = editText.getText().toString();
                            if (!TextUtil.isNullOrEmpty(tableNumber)) {
                                jumpTableNumber();
                            }
                        }
                    }
                })
                .build();
        if (dialog.getCustomView() != null) {
            editText = (EditText) dialog.getCustomView().findViewById(R.id.et_order_number);
            editText.setText("");
        }
        dialog.showLeftOrRight(leftOrRight);
        dialog.show();
    }

    private void jumpTableNumber() {
        int index = 0;
        boolean found = false;
        for (DURecord duRecord : mListData) {
            if (duRecord.getCid().equals(tableNumber)) {
                found = true;
                break;
            }

            index++;
        }

        if (found) {
            mAdjustNumberList.scrollToPosition(index);
        } else {
            for (DUCard duCard : mDuCardList) {
                String shuibiaogyh = duCard.getShuibiaogyh();
                if (TextUtil.isNullOrEmpty(shuibiaogyh)) {
                    continue;
                }

                if (shuibiaogyh.equals(tableNumber)) {
                    index = 0;
                    for (DURecord duRecord : mListData) {
                        if (duRecord.getCid().equals(duCard.getCid())) {
                            found = true;
                            break;
                        }

                        index++;
                    }

                    break;
                }
            }

            if (found) {
                mAdjustNumberList.scrollToPosition(index);
            } else {
                ApplicationsUtil.showMessage(this, R.string.text_not_find_card);
            }
        }
    }

    private void initSubTitle() {
        Intent intent = getIntent();
        mCeBenBH = TextUtil.getString(intent.getStringExtra(ConstDataUtil.S_CH));
        mRenWuBH = intent.getIntExtra(ConstDataUtil.I_RENWUBH, 0);
        setActionBarSubTitle(mCeBenBH);
    }

    private void initRecyclerView() {
        mListData = new ArrayList<>();
        mDuCardList = new ArrayList<>();
        mRemovedVolumeListData = new ArrayList<>();
        mRemovedVolumeDuCardList = new ArrayList<>();

        recyclerListAdapter = new RecyclerListAdapter(this, R.layout.item_adjust_number,
                mListData, mDuCardList, mRemovedVolumeListData, mRemovedVolumeDuCardList);
        mAdjustNumberList.setHasFixedSize(true);
        mAdjustNumberList.setAdapter(recyclerListAdapter);
        mAdjustNumberList.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(recyclerListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mAdjustNumberList);
    }

    private void loadData() {
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mAdjustNumberPresenter.loadChaoBiaoRWs();
    }

    private void setOkMenuItemVisible() {
        adjustVolumeMenuItem.setVisible(false);
        okMenuItem.setVisible(true);
    }

    private void adjustItemSequence() {
        for (DURecord duRecord : mListData) {
            for (DUCard duCard : mDuCardList) {
                if (duCard.getCid().equals(duRecord.getCid())) {
                    int ceneixh = duCard.getCeneixh();
                    duRecord.setCeneixh(ceneixh);
                    duRecord.setCeneipx(ceneixh);
                    break;
                }
            }
        }

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mAdjustNumberPresenter.adjustCardXXs(mDuCardList);
    }

    private void onOkMenu() {
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        if (recyclerListAdapter.isCheckboxVisible()) {
            if ((mRemovedVolumeListData.size() <= 0)
                    || (mRemovedVolumeDuCardList.size() <= 0)) {
                okMenuItem.setVisible(false);
                adjustVolumeMenuItem.setVisible(false);
                recyclerListAdapter.setCheckBoxVisible(false);
                return;
            }

            List<String> taskIdList = new ArrayList<>();
            for (DUTask duTask : mTaskList) {
                taskIdList.add(duTask.getcH());
            }

            new MaterialDialog.Builder(this)
                    .title(R.string.move_prompt)
                    .items(taskIdList)
                    .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            DUTask removeDuTask = mTaskList.get(which);
                            mAdjustNumberPresenter.adjustVolume(removeDuTask, mListData, mDuCardList,
                                    mRemovedVolumeListData, mRemovedVolumeDuCardList);
                            return true; // allow selection
                        }
                    })
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .buttonsGravity(leftOrRight)
                    .show(leftOrRight);
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(R.string.text_is_changing_number)
                    //.cancelable(false)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .buttonsGravity(leftOrRight)
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog,
                                            @NonNull DialogAction which) {
                            if (which.name().equals(POSITIVE)) {
                                adjustItemSequence();
                            }
                        }
                    })
                    .show(leftOrRight);
        }
    }

    private void onAdjustVolumeMenu() {
        if (!recyclerListAdapter.isCheckboxVisible()) {
            okMenuItem.setVisible(true);
            adjustVolumeMenuItem.setVisible(false);
            recyclerListAdapter.setCheckBoxVisible(true);
        } else {
            okMenuItem.setVisible(false);
            adjustVolumeMenuItem.setVisible(false);
            recyclerListAdapter.setCheckBoxVisible(false);
        }
    }

    public class RecyclerListAdapter extends RecyclerView.Adapter<ItemViewHolder>
            implements ItemTouchHelperAdapter {
        private LayoutInflater mInflater;
        private int mResource;
        private List<DURecord> mListData;
        private List<DUCard> mBiaoKaXXList;
        private List<DURecord> mRemovedVolumeListData;
        private List<DUCard> mRemovedVolumeBiaoKaXXList;
        private boolean isCheckboxVisible;

        public RecyclerListAdapter(Context context,
                                   int resource,
                                   List<DURecord> duRecordList,
                                   List<DUCard> duCardList,
                                   List<DURecord> removedVolumeDuRecordList,
                                   List<DUCard> removedVolumeDuCardList) {
            mResource = resource;
            mListData = duRecordList;
            mBiaoKaXXList = duCardList;
            mRemovedVolumeListData = removedVolumeDuRecordList;
            mRemovedVolumeBiaoKaXXList = removedVolumeDuCardList;
            mInflater = LayoutInflater.from(context);
            isCheckboxVisible = false;
        }

        public void setDataList(List<DURecord> duRecordList) {
            mListData = duRecordList;
        }

        public void setCheckBoxVisible(boolean visible) {
            isCheckboxVisible = visible;
            notifyDataSetChanged();
        }

        public boolean isCheckboxVisible() {
            return isCheckboxVisible;
        }

        public void setBiaoKaXXList(List<DUCard> duCardList) {
            mBiaoKaXXList = duCardList;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, final int position) {
            DURecord duRecord = mListData.get(position);
            if (duRecord != null) {
                DUCard duCard = getBiaoKaXX(duRecord.getCid());
                if (duCard != null) {
                    holder.tvXuHao.setText(String.valueOf(duCard.getCeneixh()));
                    holder.tvCid.setText(duCard.getCid());
                    //holder.tv_cid.setTag(duRecord);
                    holder.tvYongHuM.setText(duCard.getKehumc());
                    holder.tvYongHuDZ.setText(duCard.getDizhi());

                    int chaoBiaoBZ = duRecord.getIchaobiaobz();
                    int shangChuanBZ = duRecord.getShangchuanbz();
                    if (chaoBiaoBZ > 0) {
                        int icon;
                        switch (chaoBiaoBZ) {
                            case 1: //
                                // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                                // R.drawable.ic_ok
                                // : R.drawable.ic_shangchuan;
                                if (shangChuanBZ == DURecord.SHANGCHUANBZ_WEISHANGC) {
                                    icon = R.mipmap.ic_shangchuan;
                                } else if (shangChuanBZ == DURecord.SHANGCHUANBZ_ZHENGZAISC) {
                                    icon = R.mipmap.ic_remote_bill;
                                } else {
                                    icon = R.mipmap.ic_ok;
                                }
                                break;
                            case 2: //                                 // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                                // R.drawable.ic_remote_bill
                                // : R.drawable.ic_local_bill;
                                icon = R.mipmap.ic_remote_bill;
                                break;
                            case 3: //
                                // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                                // R.drawable.ic_remote_delay
                                // : R.drawable.ic_local_delay;
                                icon = R.mipmap.ic_remote_delay;
                                break;
                            case 4: //
                                // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                                // R.drawable.ic_remote_ex_delay
                                // : R.drawable.ic_local_ex_delay;
                                icon = R.mipmap.ic_remote_ex_delay;
                                break;
                            default:
                                // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
                                // R.drawable.ic_ok
                                // : R.drawable.ic_shangchuan;
                                icon = R.mipmap.ic_ok;
                                break;
                        }

                        if (duRecord.getLastReadingChild() == 0) {
                            holder.row1.setVisibility(View.VISIBLE);
                            holder.row2.setVisibility(View.GONE);
                            holder.row3.setVisibility(View.GONE);

                            holder.tvShangCiCM.setText(String.valueOf(duRecord.getShangcicm()));
                            holder.tvBenCiCM.setText(String.valueOf(duRecord.getBencicm()));
                        } else {
                            holder.row1.setVisibility(View.GONE);
                            holder.row2.setVisibility(View.VISIBLE);
                            holder.row3.setVisibility(View.VISIBLE);

                            holder.tvShangCiCMM.setText(String.valueOf(duRecord.getShangcicm()));
                            holder.tvBenCiCMM.setText(String.valueOf(duRecord.getBencicm()));

                            holder.tvShangCiCMZ.setText(String.valueOf(duRecord.getLastReadingChild()));
                            holder.tvBenCiCMZ.setText(String.valueOf(duRecord.getReadingChild()));
                        }

                        holder.img.setImageDrawable(null); //AdjustNumberActivity.this.getResources().getDrawable(icon)
                        holder.tvChaoJianSL.setText(String.valueOf(duRecord.getChaojiansl()));
                        holder.tvChaoBiaoZT.setText(duRecord.getZhuangtaimc());
                    } else {
                        if (duRecord.getLastReadingChild() == 0) { // one card
                            holder.row1.setVisibility(View.VISIBLE);
                            holder.row2.setVisibility(View.GONE);
                            holder.row3.setVisibility(View.GONE);

                            holder.tvShangCiCM.setText(String.valueOf(duRecord.getShangcicm()));
                            holder.tvBenCiCM.setText(TextUtil.EMPTY);
                        } else { // two cards
                            holder.row1.setVisibility(View.GONE);
                            holder.row2.setVisibility(View.VISIBLE);
                            holder.row3.setVisibility(View.VISIBLE);

                            // parent
                            holder.tvShangCiCMM.setText(String.valueOf(duRecord.getShangcicm()));
                            holder.tvBenCiCMM.setText(TextUtil.EMPTY);

                            // child
                            holder.tvShangCiCMZ.setText(String.valueOf(duRecord.getLastReadingChild()));
                            holder.tvBenCiCMZ.setText(TextUtil.EMPTY);
                        }

                        holder.tvChaoJianSL.setText(TextUtil.EMPTY);
                        holder.tvChaoBiaoZT.setText(TextUtil.EMPTY);
                        holder.img.setImageDrawable(null);
                    }

                    holder.tvShuiBiaoGYH.setText(TextUtil.getString(duCard.getShuibiaogyh()));
                    holder.tvKouJing.setText(TextUtil.getString(duCard.getKoujingmc()));
                }
            }

            holder.checkBox.setChecked(mListData.get(position).getIsChecked());

            if (isCheckboxVisible) {
                holder.checkBox.setVisibility(View.VISIBLE);
            } else {
                holder.checkBox.setVisibility(View.GONE);
            }

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DURecord duRecord = mListData.get(position);
                    DUCard duCard = getBiaoKaXX(duRecord.getCid());
                    if (duCard == null) {
                        return;
                    }

                    if (isChecked) {
                        mRemovedVolumeListData.add(duRecord);
                        mRemovedVolumeBiaoKaXXList.add(duCard);
                    } else {
                        mRemovedVolumeListData.remove(duRecord);
                        mRemovedVolumeBiaoKaXXList.remove(duCard);
                    }
                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListData.get(position).setIsChecked(!mListData.get(position).getIsChecked());
                }
            });

        }

        @Override
        public void onItemDismiss(int position) {
            //mListData.remove(position);
            //notifyItemRemoved(position);
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            if (swap(fromPosition, toPosition)) {
                notifyItemMoved(fromPosition, toPosition);
                setOkMenuItemVisible();
                return true;
            }

            return false;
        }

        @Override
        public int getItemCount() {
            return mListData.size();
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

//        private boolean swapCards(int fromPosition, int toPosition) {
//            DURecord duRecord1 = mListData.get(fromPosition);
//            DURecord duRecord2 = mListData.get(toPosition);
//            if ((duRecord1 == null) || (duRecord2 == null)) {
//                return false;
//            }
//
//            DUCard duCard1 = getBiaoKaXX(duRecord1.getCid());
//            DUCard duCard2 = getBiaoKaXX(duRecord2.getCid());
//            if ((duCard1 == null) || (duCard2 == null)) {
//                return false;
//            }
//
//            int ceneixh = duCard1.getCeneixh();
//            duCard1.setCeneixh(duCard2.getCeneixh());
//            duCard2.setCeneixh(ceneixh);
//            return true;
//        }
//
//        private boolean swapRecords(int fromPosition, int toPosition) {
//            DURecord duRecord1 = mListData.get(fromPosition);
//            DURecord duRecord2 = mListData.get(toPosition);
//            int ceneixh = duRecord1.getCeneixh();
//            int ceneipx = duRecord1.getCeneipx();
//            duRecord1.setCeneixh(duRecord2.getCeneixh());
//            duRecord1.setCeneipx(duRecord2.getCeneipx());
//            duRecord2.setCeneixh(ceneixh);
//            duRecord2.setCeneipx(ceneipx);
//            duRecord1.setIsAdjustingSequence(true);
//            duRecord2.setIsAdjustingSequence(true);
//            Collections.swap(mListData, fromPosition, toPosition);
//            return true;
//        }

        private boolean swap(int fromPosition, int toPosition) {
            if (fromPosition <= toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    swapItems(i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    swapItems(i, i - 1);
                }
            }

            return true;
        }

        private void swapItems(int index1, int index2) {
            DURecord duRecord1 = mListData.get(index1);
            DURecord duRecord2 = mListData.get(index2);
            if ((duRecord1 == null) || (duRecord2 == null)) {
                return;
            }

            DUCard duCard1 = getBiaoKaXX(duRecord1.getCid());
            DUCard duCard2 = getBiaoKaXX(duRecord2.getCid());
            if ((duCard1 == null) || (duCard2 == null)) {
                return;
            }

            int ceneixh = duCard1.getCeneixh();
            duCard1.setCeneixh(duCard2.getCeneixh());
            duCard2.setCeneixh(ceneixh);

            ceneixh = duRecord1.getCeneixh();
            int ceneipx = duRecord1.getCeneipx();
            duRecord1.setCeneixh(duRecord2.getCeneixh());
            duRecord1.setCeneipx(duRecord2.getCeneipx());
            duRecord2.setCeneixh(ceneixh);
            duRecord2.setCeneipx(ceneipx);
            duRecord1.setIsAdjustingSequence(true);
            duRecord2.setIsAdjustingSequence(true);

            Collections.swap(mListData, index1, index2);
            swapCards(duCard1, duCard2);
        }

        private void swapCards(DUCard duCard1, DUCard duCard2) {
            if ((mBiaoKaXXList == null) || (duCard1 == null) || (duCard2 == null)) {
                return ;
            }

            int index1, index2, i;
            index1 = index2 = i = 0;
            for (DUCard duCard : mBiaoKaXXList) {
                if (duCard.getCid().equals(duCard1.getCid())) {
                    index1 = i;
                    continue;
                }

                if (duCard.getCid().equals(duCard2.getCid())) {
                    index2 = i;
                    continue;
                }

                ++i;
            }

            if ((index1 <= mBiaoKaXXList.size() - 1) && (index2 <= mBiaoKaXXList.size() - 1)) {
                Collections.swap(mBiaoKaXXList, index1, index2);
            }
        }
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {
        private TextView tvXuHao;
        private TextView tvCid;
        private TextView tvYongHuM;
        private TextView tvYongHuDZ;
        private TextView tvShangCiCM;
        private TextView tvBenCiCM;
        private TextView tvChaoJianSL;
        private TextView tvChaoBiaoZT;
        private TextView tvShangCiCMM;
        private TextView tvBenCiCMM;
        private TextView tvShangCiCMZ;
        private TextView tvBenCiCMZ;
        private TextView tvShuiBiaoGYH;
        private TextView tvKouJing;
        private ImageView img;
        private LinearLayout row1;
        private LinearLayout row2;
        private LinearLayout row3;
        private CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvXuHao = (TextView) itemView.findViewById(R.id.tv_xuhao);
            tvCid = (TextView) itemView.findViewById(R.id.tv_cid);
            tvYongHuM = (TextView) itemView.findViewById(R.id.tv_yonghum);
            tvYongHuDZ = (TextView) itemView.findViewById(R.id.tv_yonghudz);
            img = (ImageView) itemView.findViewById(R.id.img_state);
            tvBenCiCM = (TextView) itemView.findViewById(R.id.tv_bencicm);
            tvChaoJianSL = (TextView) itemView.findViewById(R.id.tv_chaojiansl);
            tvChaoBiaoZT = (TextView) itemView.findViewById(R.id.tv_chaobiaozt);
            row1 = (LinearLayout) itemView.findViewById(R.id.sub_row1);
            row2 = (LinearLayout) itemView.findViewById(R.id.sub_row2);
            row3 = (LinearLayout) itemView.findViewById(R.id.sub_row3);
            tvShangCiCM = (TextView) itemView.findViewById(R.id.tv_shangcicm);
            tvShangCiCMM = (TextView) itemView.findViewById(R.id.tv_shangcicmm);
            tvBenCiCMM = (TextView) itemView.findViewById(R.id.tv_bencicmm);
            tvShangCiCMZ = (TextView) itemView.findViewById(R.id.tv_shangcicmz);
            tvBenCiCMZ = (TextView) itemView.findViewById(R.id.tv_bencicmz);
            tvShuiBiaoGYH = (TextView) itemView.findViewById(R.id.tv_chaobiaobh);
            tvKouJing = (TextView) itemView.findViewById(R.id.tv_chaobiaokj);
            checkBox = (CheckBox) itemView.findViewById(R.id.list_select);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
