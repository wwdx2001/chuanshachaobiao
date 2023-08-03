package com.sh3h.meterreading.ui.temporary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUAdjustingCard;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.volume.VolumeListActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperAdapter;
import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperViewHolder;
import co.paulburke.android.itemtouchhelper.helper.OnStartDragListener;
import co.paulburke.android.itemtouchhelper.helper.SimpleItemTouchHelperCallback;

/**
 * Created by xulongjun on 2016/2/4.
 */
public class AdjustTemporaryActivity extends ParentActivity implements AdjustTemporaryMvpView,
        OnStartDragListener, MenuItem.OnMenuItemClickListener {

    private static final String TAG = "AdjustTemporaryActivity";

    @Inject
    AdjustTemporaryPresenter mAdjustTemporaryPresenter;

    @Inject
    ConfigHelper mConfigHelper;
    private static final String POSITIVE = "POSITIVE";

    @Bind(R.id.adjust_number_list)
    RecyclerView mAdjustNumberList;

    @Bind(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mEventBus;

    private List<DURecord> mListData;
    private List<DUCard> mDuCardList;
    private List<DURecord> mRemovedVolumeListData;
    private List<DUCard> mRemovedVolumeDuCardList;
    private List<DUTask> mTaskList;
    private RecyclerListAdapter recyclerListAdapter;
    private ItemTouchHelper mItemTouchHelper;

    private MenuItem okMenuItem;
    private MenuItem adjustVolumeMenuItem;
    private MenuItem tableNumberMenuItem;

    private DUTask removedDuTask;
    private List<DUAdjustingCard> mDUAdjustingCardList;
    private String mCh;

    private EditText editText;
    private String tableNumber;


    public AdjustTemporaryActivity() {
        tableNumber = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_number);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mEventBus.register(this);
        setActionBarBackButtonEnable();
        mAdjustTemporaryPresenter.attachView(this);

        isNewCard();
        initRecyclerView();

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
    }

    private void isNewCard() {
        Intent intent = getIntent();
        mCh = intent.getStringExtra(ConstDataUtil.S_CH);
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
        mAdjustTemporaryPresenter.loadChaoBiaoRWs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adjust_number_lr, menu);
        tableNumberMenuItem = menu.findItem(R.id.action_style_table_number);
        adjustVolumeMenuItem = menu.findItem(R.id.action_style_adjust_volume);
        okMenuItem = menu.findItem(R.id.action_style_ok);
        tableNumberMenuItem.setOnMenuItemClickListener(this);
        adjustVolumeMenuItem.setOnMenuItemClickListener(this);
        okMenuItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mAdjustTemporaryPresenter.detachView();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_style_table_number:
                onTableNumberMenu();
                break;
            case R.id.action_style_ok:
                onOkMenu();
                break;
            case R.id.action_style_adjust_volume:
                onAdjustVolumeMenu();
                break;
        }

        return true;
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
            loadData();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    private void onTableNumberMenu() {
        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        MaterialDialog dialog = new MaterialDialog.Builder(AdjustTemporaryActivity.this)
                .title(R.string.text_table_number)
                .customView(R.layout.dialog_jump_number, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .buttonsGravity(leftOrRight)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        if (!isReadyJumping) {
//                            LogUtil.i(TAG, "---jump---error");
//                            ApplicationsUtil.showMessage(AdjustTemporaryActivity.this,
//                                    R.string.text_jump_number_error);
//                            return;
//                        }

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

    private void onAdjustVolumeMenu() {
        if (!recyclerListAdapter.isCheckboxVisible()) {
            okMenuItem.setVisible(true);
            adjustVolumeMenuItem.setVisible(false);
            recyclerListAdapter.setCheckBoxVisible(true);
        } else {
            okMenuItem.setVisible(false);
            adjustVolumeMenuItem.setVisible(true);
            recyclerListAdapter.setCheckBoxVisible(false);
        }
    }

    private void onOkMenu() {
        if (recyclerListAdapter.isCheckboxVisible()) {
            if ((mRemovedVolumeListData.size() <= 0)
                    || (mRemovedVolumeDuCardList.size() <= 0)) {
                okMenuItem.setVisible(false);
                adjustVolumeMenuItem.setVisible(true);
                recyclerListAdapter.setCheckBoxVisible(false);
                return;
            }

            List<String> taskIdList = new ArrayList<>();
            for (DUTask duTask : mTaskList) {
                taskIdList.add(duTask.getcH());
            }

            removedDuTask = null;
            if (mCh != null) {
                for (DUTask duTask : mTaskList) {
                    if (duTask.getcH().equals(mCh)) {
                        removedDuTask = duTask;
                        break;
                    }
                }

                if (removedDuTask != null) {
                    loadDestinationCards();
                }
            } else {
                boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
                new MaterialDialog.Builder(this)
                        .title(R.string.move_prompt)
                        .items(taskIdList)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view,
                                                       int which, CharSequence text) {
                                DUTask duTask = mTaskList.get(which);
                                if (!mConfigHelper.isCurrentReadingDateValid()) {
                                    ApplicationsUtil.showMessage(AdjustTemporaryActivity.this,
                                            R.string.text_exceed_time_limit);
                                    return true;
                                }

                                if (isExceedingZWNY(duTask)) {
                                    return true;
                                }

                                removedDuTask = duTask;
                                loadDestinationCards();
                                return true; // allow selection
                            }
                        })
                        .positiveText(R.string.text_ok)
                        .negativeText(R.string.text_cancel)
                        .buttonsGravity(leftOrRight)
                        .show(leftOrRight);
            }

        } else {
//            new MaterialDialog.Builder(this)
//                    .title(R.string.text_prompt)
//                    .content(R.string.text_is_changing_number)
//                    //.cancelable(false)
//                    .positiveText(R.string.text_ok)
//                    .negativeText(R.string.text_cancel)
//                    .onAny(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(@NonNull MaterialDialog dialog,
//                                            @NonNull DialogAction which) {
//                            if (which.name().equals(POSITIVE)) {
////                                adjustItemSequence();
//                            }
//                        }
//                    })
//                    .show();
        }
    }

    private void loadDestinationCards() {
        if ((removedDuTask == null)
                || TextUtil.isNullOrEmpty(removedDuTask.getcH())) {
            return;
        }

        DUCardInfo duCardInfo = new DUCardInfo(
                removedDuTask.getcH(),
                DUCardInfo.FilterType.SEARCHING_ALL);
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        mAdjustTemporaryPresenter.loadDestinationCards(duCardInfo);
    }

    private void popupAdjustingCardDialog(List<DUCard> duCardList) {
        if ((duCardList == null) || (duCardList.size() <= 0)) {
            return;
        }

        mDUAdjustingCardList = new ArrayList<>();
        for (DUCard duCard : duCardList) {
            DUAdjustingCard duAdjustingCard = new DUAdjustingCard(
                    false,
                    duCard.getCeneixh(),
                    duCard.getCid());
            mDUAdjustingCardList.add(duAdjustingCard);
        }

        new AdjustingCardDialog(this, mDUAdjustingCardList).show();
    }

    private boolean isExceedingZWNY(DUTask duTask) {
        if (duTask == null) {
            return false;
        }

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int zhangWuNY = year * 100 + month;
        StringBuilder sb = new StringBuilder();
        if (duTask.getZhangWuNY() != zhangWuNY) {
            sb.append(duTask.getcH());
        } else {
            return false;
        }

        sb.append("\n");
        sb.append(getResources().getString(R.string.text_zhangwuny_time_error));
        ApplicationsUtil.showMessage(this, sb.toString());
        return true;
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
            mTaskList.add(duTask);
        }

        if (mTaskList.size() <= 0) {
            LogUtil.i(TAG, "---onLoadTasks--size <= 0");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            return;
        }

        mAdjustTemporaryPresenter.loadRecordXXs();
    }

    @Override
    public void onLoadRecords(List<DURecord> duRecordList) {
        LogUtil.i(TAG, "---onLoadRecords---");
        if ((duRecordList == null) || (duRecordList.size() <= 0)) {
            LogUtil.i(TAG, "---onLoadRecords---null or size <= 0");
            mSmoothProgressBar.setVisibility(View.INVISIBLE);
            return;
        }

        mListData = duRecordList;
        recyclerListAdapter.setDataList(duRecordList);
        mAdjustTemporaryPresenter.loadCardXXs();
    }

    @Override
    public void onLoadCards(List<DUCard> duCardList) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if ((duCardList == null) || (duCardList.size() <= 0)) {
            LogUtil.i(TAG, "---onLoadCards--null or size <= 0");
            return;
        }

        mDuCardList = duCardList;
        recyclerListAdapter.setBiaoKaXXList(duCardList);

        recyclerListAdapter.notifyDataSetChanged();
        adjustVolumeMenuItem.setVisible(true);
        tableNumberMenuItem.setVisible(true);

        ApplicationsUtil.showMessage(this, R.string.text_refresh_cards_firstly);
    }

    @Override
    public void onAdjustCardAndRecords(Boolean aBoolean) {
        LogUtil.i(TAG, "---onAdjustCardAndRecords 1---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        hideProgress();
        ApplicationsUtil.showMessage(this, R.string.text_adjust_temporary_volume_success);
        if (mCh != null) {
            setResult(VolumeListActivity.ADJUST_NUMBER_CODE);
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateTaskList());
        }
        finish();
    }

    @Override
    public void onError(String message) {
        LogUtil.i(TAG, "---onError---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        hideProgress();
        if (!TextUtil.isNullOrEmpty(message)) {
            LogUtil.i(TAG, "---onError---" + message);
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    public void onLoadDestinationCards(List<DUCard> duCardList) {
        LogUtil.i(TAG, "---onLoadDestinationCards---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        popupAdjustingCardDialog(duCardList);
    }

    private class AdjustingCardDialog {
        private List<DUAdjustingCard> mDUAdjustingCardList;
        private MaterialDialog materialDialog;
        private EditText mKeyText;
        private ListView mCardListView;
        private CardListAdapter mCardListAdapter;
        private RadioGroup mRadioGroup1;
        private RadioGroup mRadioGroup2;
        private View mPositiveAction;
        private boolean isCardId;
        private boolean isFront;

        public AdjustingCardDialog(Context context, List<DUAdjustingCard> duAdjustingCardList) {
            isCardId = true;
            isFront = true;

            mDUAdjustingCardList = duAdjustingCardList;
            materialDialog = new MaterialDialog.Builder(context)
                    .title(R.string.text_moving_to)
                    .customView(R.layout.dialog_check_record, false)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .buttonsGravity(mConfigHelper.isLeftOrRightOperation())
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            DUAdjustingCard duAdjustingCard = mCardListAdapter.getSelectedItem();
                            if (duAdjustingCard == null) {
                                ApplicationsUtil.showMessage(AdjustTemporaryActivity.this,
                                        R.string.text_selecting_item);
                                return;
                            }


                            mSmoothProgressBar.setVisibility(View.VISIBLE);
                            AdjustTemporaryActivity.this.showProgress(R.string.text_dealing_data);
                            mAdjustTemporaryPresenter.adjustCardAndRecords(mRemovedVolumeDuCardList,
                                    mRemovedVolumeListData, removedDuTask, duAdjustingCard.getCardId(),
                                    duAdjustingCard.getOrderNumber(), isCardId, isFront);
                        }
                    }).build();

            mKeyText = (EditText) materialDialog.getCustomView().findViewById(R.id.dcr_et_key);

            mRadioGroup1 = (RadioGroup) materialDialog.getCustomView().findViewById(R.id.dcr_rg_1);
            mRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.dcr_rb_cardId:
                            isCardId = true;
                            break;
                        case R.id.dcr_rb_order_number:
                            isCardId = false;
                            break;
                        default:
                            return;
                    }

                    mCardListAdapter.setIsCardId(isCardId);
                    mKeyText.setText("");
                    mPositiveAction.setEnabled(false);
                    hideKeyboard();
                }
            });

            mRadioGroup2 = (RadioGroup) materialDialog.getCustomView().findViewById(R.id.dcr_rg_2);
            mRadioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.dcr_rb_front:
                            isFront = true;
                            break;
                        case R.id.dcr_rb_back:
                            isFront = false;
                            break;
                        default:
                            break;
                    }
                }
            });

            mCardListView = (ListView) materialDialog.getCustomView().findViewById(R.id.dcr_list);
            mCardListAdapter = new CardListAdapter(context, R.layout.item_check_record,
                    mDUAdjustingCardList, isCardId);
            mCardListView.setAdapter(mCardListAdapter);
            mKeyText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCardListAdapter.filter(s.toString());
                    mCardListAdapter.notifyDataSetChanged();
                    mPositiveAction.setEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            mPositiveAction = materialDialog.getActionButton(DialogAction.POSITIVE);
            mPositiveAction.setEnabled(false);
        }

        public void show() {
            if (materialDialog != null) {
                materialDialog.showLeftOrRight(mConfigHelper.isLeftOrRightOperation());
                materialDialog.show();
            }
        }

        private void hideKeyboard() {
            AdjustTemporaryActivity.this.hideKeyboard(mKeyText.getWindowToken(), 0);
        }

        private class CardListAdapter extends BaseAdapter {
            private LayoutInflater mInflater;
            private int mResource;
            private List<DUAdjustingCard> mListData;
            private List<DUAdjustingCard> mFilterListData;
            private int lastPosition;
            private boolean mIsCardId;

            public CardListAdapter(Context context,
                                   int resource,
                                   List<DUAdjustingCard> listData,
                                   boolean isCardId) {
                mResource = resource;
                mListData = listData;
                mFilterListData = listData;
                mInflater = LayoutInflater.from(context);
                lastPosition = -1;
                mIsCardId = isCardId;
                sort();
            }

            public void setIsCardId(boolean isCardId) {
                mIsCardId = isCardId;
            }

            public void filter(String text) {
                if (lastPosition != -1) {
                    mFilterListData.get(lastPosition).setChecked(false);
                    lastPosition = -1;
                }

                if (TextUtil.isNullOrEmpty(text)) {
                    mFilterListData = mListData;
                } else {
                    mFilterListData = new ArrayList<>();
                    for (DUAdjustingCard duAdjustingCard : mListData) {
                        if (isCardId) {
                            if (duAdjustingCard.getCardId().contains(text)) {
                                mFilterListData.add(duAdjustingCard);
                            }
                        } else {
                            if (String.valueOf(duAdjustingCard.getOrderNumber()).equals(text)) {
                                mFilterListData.add(duAdjustingCard);
                            }
                        }
                    }
                }

                sort();
            }

            private void sort() {
                if (mIsCardId) {
                    Collections.sort(mFilterListData, new Comparator<DUAdjustingCard>() {
                        public int compare(DUAdjustingCard arg0, DUAdjustingCard arg1) {
                            return arg0.getCardId().compareTo(arg1.getCardId());
                        }
                    });
                } else {
                    Collections.sort(mFilterListData, new Comparator<DUAdjustingCard>() {
                        public int compare(DUAdjustingCard arg0, DUAdjustingCard arg1) {
                            return arg0.getOrderNumber() - arg1.getOrderNumber();
                        }
                    });
                }
            }

            public DUAdjustingCard getSelectedItem() {
                if ((lastPosition >= 0) && (lastPosition < mFilterListData.size())) {
                    return mFilterListData.get(lastPosition);
                } else {
                    return null;
                }
            }

            @Override
            public int getCount() {
                return mFilterListData.size();
            }

            @Override
            public Object getItem(int position) {
                return mFilterListData.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView == null ? mInflater.inflate(mResource, null) : convertView;
                RadioButton radioButton = (RadioButton) view.findViewById(R.id.rb_tb_item);
                DUAdjustingCard duAdjustingCard = mFilterListData.get(position);
                radioButton.setChecked(duAdjustingCard.isChecked());
                if (mIsCardId) {
                    radioButton.setText(String.format(ConstDataUtil.LOCALE, "%s/%d",
                            duAdjustingCard.getCardId(), duAdjustingCard.getOrderNumber()));
                } else {
                    radioButton.setText(String.format(ConstDataUtil.LOCALE, "%d/%s",
                            duAdjustingCard.getOrderNumber(), duAdjustingCard.getCardId()));
                }
                radioButton.setTag(position);

                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((lastPosition >= 0) && (lastPosition < mFilterListData.size())) {
                            DUAdjustingCard duAdjustingCard = mFilterListData.get(lastPosition);
                            duAdjustingCard.setChecked(false);
                        }

                        Object object = v.getTag();
                        if (object instanceof Integer) {
                            int pos = (Integer) object;
                            if ((pos >= 0) && (pos < mFilterListData.size())) {
                                DUAdjustingCard duAdjustingCard = mFilterListData.get(pos);
                                duAdjustingCard.setChecked(true);
                                lastPosition = pos;
                            }
                        }

                        hideKeyboard();
                        notifyDataSetChanged();
                        mPositiveAction.setEnabled(true);
                    }
                });

                return view;
            }
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
                    holder.tvXuHao.setText(String.valueOf(duRecord.getCeneixh()));
                    holder.tvCid.setText(duCard.getCid());
                    //holder.tv_cid.setTag(duRecord);
                    holder.tvYongHuM.setText(duCard.getKehumc());
                    holder.tvYongHuDZ.setText(duCard.getDizhi());
                    holder.tvChaoBiaoBH.setText(duCard.getShuibiaogyh());
                    holder.tvChaoBiaoKJ.setText(duCard.getKoujingmc());
                    if (!TextUtil.isNullOrEmpty(duCard.getLianxisj())) {
                        holder.tvLianXiDH.setText(duCard.getLianxisj());
                    } else if (!TextUtil.isNullOrEmpty(duCard.getLianxidh())) {
                        holder.tvLianXiDH.setText(duCard.getLianxidh());
                    } else {
                        holder.tvLianXiDH.setText("");
                    }

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
                            case 2: //                            // icon = chaoBiaoSJ.getI_ShangChuanBZ() == 1 ?
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
                            holder.row1.setVisibility(View.GONE);
                            holder.row2.setVisibility(View.GONE);
                            holder.row3.setVisibility(View.GONE);

                            holder.tvShangCiCM.setText(String.valueOf(duRecord.getShangcicm()));
                            holder.tvBenCiCM.setText(String.valueOf(duRecord.getBencicm()));

                        } else {
                            holder.row1.setVisibility(View.GONE);
                            holder.row2.setVisibility(View.GONE);
                            holder.row3.setVisibility(View.GONE);

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
                            holder.row1.setVisibility(View.GONE);
                            holder.row2.setVisibility(View.GONE);
                            holder.row3.setVisibility(View.GONE);

                            holder.tvShangCiCM.setText(String.valueOf(duRecord.getShangcicm()));
                            holder.tvBenCiCM.setText(TextUtil.EMPTY);
                        } else { // two cards
                            holder.row1.setVisibility(View.GONE);
                            holder.row2.setVisibility(View.GONE);
                            holder.row3.setVisibility(View.GONE);

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
//            if (swapCards(fromPosition, toPosition) && swapRecords(fromPosition, toPosition)) {
//                notifyItemMoved(fromPosition, toPosition);
//                setOkMenuItemVisible();
//                return true;
//            }

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

        private boolean swapCards(int fromPosition, int toPosition) {
            DURecord duRecord1 = mListData.get(fromPosition);
            DURecord duRecord2 = mListData.get(toPosition);
            if ((duRecord1 == null) || (duRecord2 == null)) {
                return false;
            }

            DUCard duCard1 = getBiaoKaXX(duRecord1.getCid());
            DUCard duCard2 = getBiaoKaXX(duRecord2.getCid());
            if ((duCard1 == null) || (duCard2 == null)) {
                return false;
            }

            int ceneixh = duCard1.getCeneixh();
            duCard1.setCeneixh(duCard2.getCeneixh());
            duCard2.setCeneixh(ceneixh);
            return true;
        }

        private boolean swapRecords(int fromPosition, int toPosition) {
            DURecord duRecord1 = mListData.get(fromPosition);
            DURecord duRecord2 = mListData.get(toPosition);
            int ceneixh = duRecord1.getCeneixh();
            int ceneipx = duRecord1.getCeneipx();
            duRecord1.setCeneixh(duRecord2.getCeneixh());
            duRecord1.setCeneipx(duRecord2.getCeneipx());
            duRecord2.setCeneixh(ceneixh);
            duRecord2.setCeneipx(ceneipx);
            duRecord1.setIsAdjustingSequence(true);
            duRecord2.setIsAdjustingSequence(true);
            Collections.swap(mListData, fromPosition, toPosition);
            return true;
        }
    }


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
        private ImageView img;
        private LinearLayout row1;
        private LinearLayout row2;
        private LinearLayout row3;
        private CheckBox checkBox;
        private TextView tvChaoBiaoBH;
        private TextView tvChaoBiaoKJ;
        private TextView tvLianXiDH;

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
            checkBox = (CheckBox) itemView.findViewById(R.id.list_select);
            tvChaoBiaoBH = (TextView) itemView.findViewById(R.id.tv_chaobiaobh);
            tvChaoBiaoKJ = (TextView) itemView.findViewById(R.id.tv_chaobiaokj);
            tvLianXiDH = (TextView) itemView.findViewById(R.id.tv_lianxidh);
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

    private void setOkMenuItemVisible() {
        adjustVolumeMenuItem.setVisible(false);
        okMenuItem.setVisible(true);
    }

}
