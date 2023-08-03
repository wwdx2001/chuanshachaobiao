package com.sh3h.meterreading.ui.rushpay;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DURushPayTask;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import righttodelete.SwipeMenu;
import righttodelete.SwipeMenuCreator;
import righttodelete.SwipeMenuItem;
import righttodelete.SwipeMenuListView;


public class RushPayTaskActivity extends ParentActivity implements RushPayTaskMvpView,
        View.OnClickListener, MenuItem.OnMenuItemClickListener {
    private static final String TAG = "SamplingTaskActivity";

    @Inject
    RushPayTaskPresenter mRushPayTaskPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    ConfigHelper mConfigHelper;

    @Bind(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    @Bind(R.id.fcbrw_list)
    SwipeMenuListView mSwipeMenuListView;

    @Inject
    Bus mEventBus;

    private MyListAdapter mMyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rushpay_task);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mEventBus.register(this);

        mRushPayTaskPresenter.attachView(this);
        setActionBarBackButtonEnable();

        needRefresh = false;
        mSmoothProgressBar.setVisibility(View.VISIBLE);

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

        if (needRefresh) {
            needRefresh = false;
            mRushPayTaskPresenter.loadRushPayTasks();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mRushPayTaskPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onLoadRushPayTasks(List<DURushPayTask> duRushPayTaskList) {
        LogUtil.i(TAG, "---onLoadTasks---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        initSwipeMenuListView(duRushPayTaskList);
    }

    @Override
    public void onDeleteRushPayTask(Boolean aBoolean) {
        LogUtil.i(TAG, "---onDeleteTask---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (aBoolean) {
            ApplicationsUtil.showMessage(this, R.string.text_deleteRushPayTasks_success);
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateRushPayTask());//通知催缴任务界面更新列表
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_deleteRushPayTasks_error);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task_list, menu);
        MenuItem menuItem = menu.findItem(R.id.mtl_action_update);
        menuItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mtl_action_update:
                LogUtil.i(TAG, "---startVersionService---");
                needDialog = true;
                uploadAndDownloadAllRushPayTasks();
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
            mRushPayTaskPresenter.loadRushPayTasks();
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
            case UPLOADING_DOWNLOADING_ALL_RUSH_PAY_TASKS:
                refresh();
                popupSyncDataInfo(syncDataEnd);
                break;
            case UPLOADING_RUSH_PAY_MEDIAS:
                refresh();
            default:
                break;
        }
    }

    @Subscribe
    public void onUpdateRushPayTask(UIBusEvent.UpdateRushPayTask updateRushPayTask) {
        LogUtil.i(TAG, "---onUpdateRushPayTask---");
        refresh();
    }

    private void refresh() {
        if (isActive) {
            needRefresh = false;
            mRushPayTaskPresenter.loadRushPayTasks();
        } else {
            needRefresh = true;
        }
    }

    private void popupSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = MainApplication.get(this).parseRushPaySyncDataInfo(syncDataEnd);
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

    private void initSwipeMenuListView(final List<DURushPayTask> duRushPayTaskList) {
        if (duRushPayTaskList == null) {
            return;
        }
        mMyListAdapter = new MyListAdapter(this, R.layout.item_rush_payrw, duRushPayTaskList);
        mSwipeMenuListView.setAdapter(mMyListAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(int position, SwipeMenu menu) {
                // 获取数据
                DURushPayTask duRushPayTask = duRushPayTaskList.get(position);
                if (duRushPayTask == null) {
                    return;
                }
                // 删除
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(RushPayTaskActivity.this);
                // set item background
                swipeMenuItem.setBackground(new ColorDrawable(Color.rgb(0xeb, 0x59, 0x46)));
                // set item width
                swipeMenuItem.setWidth(dp2px(80));
                // set a icon
                swipeMenuItem.setTitle(getResources().getString(R.string.text_shanchu));
                swipeMenuItem.setTitleSize(15);
                // set item title font color
                swipeMenuItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(swipeMenuItem);
            }
        };
        // set creator
        mSwipeMenuListView.setMenuCreator(creator);
        // set SwipeListener
        mSwipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                mSwipeMenuListView.setEnabled(false);
            }

            @Override
            public void onSwipeEnd(int position) {
                mSwipeMenuListView.setEnabled(true);
            }
        });
        // step 1. listener item click event
        mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                DURushPayTask duRushPayTask = duRushPayTaskList.get(position);
                if (duRushPayTask == null) {
                    return;
                }
                switch (index) {
                    case 0:
                        deleteRushPayTask(duRushPayTask.getI_TaskId());
                        break;
                }
            }
        });

        mSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DURushPayTask item = (DURushPayTask) view.findViewById(R.id.tv_task_id).getTag();
                if (item != null) {
//                    if (!mConfigHelper.isCurrentReadingDateValid()) {
//                        ApplicationsUtil.showMessage(RushPayTaskActivity.this,
//                                R.string.text_exceed_time_limit);
//                        return;
//                    }

                    jump2Activity(item);
                }
            }
        });
    }


    private void jump2Activity(DURushPayTask duRushPayTask) {
        if (duRushPayTask == null) {
            return;
        }
        Intent intent = new Intent(RushPayTaskActivity.this, RushPayRecordActivity.class);
        intent.putExtra(ConstDataUtil.I_TASKID, duRushPayTask.getI_TaskId());
        intent.putExtra(ConstDataUtil.S_CARDID, duRushPayTask.getS_CardId());
        intent.putExtra(ConstDataUtil.S_CARDNAME, duRushPayTask.getS_CardName());
        intent.putExtra(ConstDataUtil.S_CARDADDRESS, duRushPayTask.getS_CardAddress());
        intent.putExtra(ConstDataUtil.S_SUBCOMCODE, duRushPayTask.getS_SubComCode());
        intent.putExtra(ConstDataUtil.I_QFMONTHS, (int)duRushPayTask.getD_QfMonths());
        intent.putExtra(ConstDataUtil.I_QFMONEY,duRushPayTask.getD_QfMoney());
        intent.putExtra(ConstDataUtil.S_REMARK,duRushPayTask.getS_ReceiptRemark());
        intent.putExtra(ConstDataUtil.S_METERREADER,duRushPayTask.getS_MeterReader());
        intent.putExtra(ConstDataUtil.I_ISCOMPLETE,duRushPayTask.getI_ISComplete());
        intent.putExtra(ConstDataUtil.I_ISUPLOAD,duRushPayTask.getI_IsUpload());
        startActivity(intent);
    }

    private void deleteRushPayTask(final int taskId) {
        LogUtil.i(TAG, "---delete rushPay task 1---");

        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_delete_RushPayTasks)
                .cancelable(false)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        LogUtil.i(TAG, "---delete rushPay task 2---");
                        mSmoothProgressBar.setVisibility(View.VISIBLE);
                        mRushPayTaskPresenter.deleteRushPayTask(taskId);
                    }
                })
                .show();
    }

    private class MyListAdapter extends BaseAdapter {
        private int mResource;
        private List<DURushPayTask> mRushPayTaskList = null;
        private LayoutInflater mInfater = null;

        public MyListAdapter(Context context,
                             int resource,
                             List<DURushPayTask> list) {
            mResource = resource;
            mInfater = LayoutInflater.from(context);
            mRushPayTaskList = list;
        }

        public void setDataList(List<DURushPayTask> list) {
            mRushPayTaskList = list;
        }

        @Override
        public int getCount() {
            return mRushPayTaskList.size();
        }

        @Override
        public Object getItem(int position) {
            return mRushPayTaskList.get(position);
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
                myListHolder.tv_taskId = (TextView) convertView.findViewById(R.id.tv_task_id);
                myListHolder.tv_cardName = (TextView) convertView.findViewById(R.id.tv_card_name);
                myListHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
                myListHolder.iv_state = (ImageView) convertView.findViewById(R.id.img_state);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
            }
            // 获取数据
            DURushPayTask duRushPayTask = mRushPayTaskList.get(position);

            if (duRushPayTask != null) {
                myListHolder.tv_taskId.setTag(duRushPayTask);
                myListHolder.tv_taskId.setText(TextUtil.getString(String.valueOf(duRushPayTask.getI_TaskId())));
                myListHolder.tv_cardName.setText(duRushPayTask.getS_CardName());
                myListHolder.tv_address.setText(String.valueOf(duRushPayTask.getS_CardAddress()));

                if (duRushPayTask.getI_IsUpload() == 2 && duRushPayTask.getI_ISComplete() == 1){
                    myListHolder.iv_state.setImageDrawable(getResources().getDrawable(R.mipmap.ic_ok));
                }else if (duRushPayTask.getI_IsUpload() == 0 && duRushPayTask.getI_ISComplete() == 1){
                    myListHolder.iv_state.setImageDrawable(getResources().getDrawable(R.mipmap.ic_shangchuan));
                }else {
                    myListHolder.iv_state.setImageDrawable(null);
                }
            }

            return convertView;
        }
    }

    private class MyListHolder {
        private TextView tv_taskId;
        private TextView tv_cardName;
        private TextView tv_address;
        private ImageView iv_state;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
