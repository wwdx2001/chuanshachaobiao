package com.sh3h.meterreading.ui.sampling;


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
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.util.EventPosterHelper;
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
import righttodelete.SwipeMenu;
import righttodelete.SwipeMenuCreator;
import righttodelete.SwipeMenuItem;
import righttodelete.SwipeMenuListView;


public class SamplingTaskActivity extends ParentActivity implements SamplingTaskMvpView,
        View.OnClickListener, MenuItem.OnMenuItemClickListener {
    private static final String TAG = "SamplingTaskActivity";

    @Inject
    SamplingTaskPresenter mSamplingTaskPresenter;

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
    private boolean isToVolumeListActivity;
    private DUSamplingTask selectedDUTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_task);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mEventBus.register(this);

        mSamplingTaskPresenter.attachView(this);
        setActionBarBackButtonEnable();

        needRefresh = false;
        isToVolumeListActivity = true;
        selectedDUTask = null;
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
            mSamplingTaskPresenter.loadSamplingTask();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mSamplingTaskPresenter.detachView();

        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onLoadSamplingTasks(List<DUSamplingTask> duSamplingTasks) {
        LogUtil.i(TAG, "---onLoadTasks---");

        if (duSamplingTasks != null && duSamplingTasks.size() > 0) {
            int unReadRw = 0;
            for (DUSamplingTask duSamplingTask : duSamplingTasks) {
                int ycsNumber = duSamplingTask.getYiChaoShu();
                int zsNumber = duSamplingTask.getZongShu();
                if (ycsNumber < zsNumber) {
                    unReadRw++;
                }
            }

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getApplicationContext().getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, SamplingTaskActivity.class.getName());
                JSONArray subJSONArray = new JSONArray();
                subJSONArray.put("count#" + unReadRw);
                jsonObject.put(MyModule.DATA, subJSONArray);
                MyModule myModule = new MyModule(jsonObject.toString());
                MainApplication.get(getApplicationContext()).setMyModule(myModule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        initSwipeMenuListView(duSamplingTasks);
    }

    @Override
    public void onDeleteSamplingTask(Boolean aBoolean) {
        LogUtil.i(TAG, "---onDeleteTask---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (aBoolean) {
            ApplicationsUtil.showMessage(this, R.string.text_deleteTasks_success);
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateSamplingTask());//通知稽查任务界面更新列表
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_deleteTasks_error);
        }
    }

    @Override
    public void onGetNextReadingRecord(DUSamplingTask duSamplingTask, DUSampling duSampling,
                                       int startOrderNumber, int endOrderNumber,
                                       String startCID, String endCID, ArrayList<String> cids) {
        LogUtil.i(TAG, "---onGetNextReadingRecord---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if ((duSamplingTask == null) || (duSampling == null) || (startOrderNumber > endOrderNumber)) {
            LogUtil.i(TAG, "---onGetNextReadingRecord---error");
            return;
        }

        Intent intent = new Intent(this, SamplingRecordActivity.class);
        intent.putExtra(ConstDataUtil.S_CH, duSampling.getCh());
        intent.putExtra(ConstDataUtil.S_CID, duSampling.getCid());
        intent.putExtra(ConstDataUtil.I_RENWUBH, duSampling.getRenwubh());
        intent.putExtra(ConstDataUtil.I_CENEIXH, duSampling.getCeneixh());
        intent.putExtra(ConstDataUtil.I_LASTREADINGCHILD, duSampling.getLastReadingChild());
        intent.putExtra(ConstDataUtil.YICHAOSHU, duSamplingTask.getYiChaoShu());
        intent.putExtra(ConstDataUtil.STARTXH, startOrderNumber);
        intent.putExtra(ConstDataUtil.ENDXH, endOrderNumber);
        intent.putExtra(ConstDataUtil.STARTCID, startCID);
        intent.putExtra(ConstDataUtil.ENDCID, endCID);
        intent.putStringArrayListExtra(ConstDataUtil.CIDS, cids);
        intent.putExtra(ConstDataUtil.FROM_TASK, true);
        startActivity(intent);
    }

    @Override
    public void onIsTaskContainingFullData(Boolean aBoolean) {
        LogUtil.i(TAG, "---onIsTaskContainingFullData---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (aBoolean) {
            jump2Activity();
        } else if (selectedDUTask != null) {
            needDialog = true;
            downloadSamplingVolume(selectedDUTask.getRenWuBH());
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
                uploadAndDownloadAllSamplingTasks();
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
            mSamplingTaskPresenter.loadSamplingTask();
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
            case UPLOADING_DOWNLOADING_ALL_SAMPLING_TASKS:
                refresh();
                popupSyncDataInfo(syncDataEnd);
                break;
            case DOWNLOADING_SAMPLING:
                needRefresh = true;
                jump2Activity();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onUpdateSamplingTask(UIBusEvent.UpdateSamplingTask updateSamplingTask) {
        LogUtil.i(TAG, "---onUpdateSamplingTask---");
        refresh();
    }

    private void refresh() {
        if (isActive) {
            needRefresh = false;
            mSamplingTaskPresenter.loadSamplingTask();
        } else {
            needRefresh = true;
        }
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

    private void initSwipeMenuListView(final List<DUSamplingTask> duSamplingTaskList) {
        if (duSamplingTaskList == null) {
            return;
        }

        if (mConfigHelper.getRegion().equals(SystemConfig.REGION_SUZHOU)) {
            mMyListAdapter = new MyListAdapter(this, R.layout.item_chaobiaorw_suzhou, duSamplingTaskList);
        } else {
            mMyListAdapter = new MyListAdapter(this, R.layout.item_chaobiaorw, duSamplingTaskList);
        }

        mSwipeMenuListView.setAdapter(mMyListAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(int position, SwipeMenu menu) {
                // 获取数据
                DUSamplingTask duSamplingTask = duSamplingTaskList.get(position);
                if (duSamplingTask == null) {
                    return;
                }

                int yichaoshu = duSamplingTask.getYiChaoShu();
                int zongshu = duSamplingTask.getZongShu();

                SwipeMenuItem swipeMenuItem1 = new SwipeMenuItem(SamplingTaskActivity.this);
                // set item width
                swipeMenuItem1.setWidth(dp2px(80));

                if (yichaoshu < zongshu) {
                    // set item title
                    swipeMenuItem1.setTitle(getResources().getString(R.string.jicharw_button_continue));
                } else {
                    // set item title
                    swipeMenuItem1.setTitle(getResources().getString(R.string.jicharw_button_finish));
                }

                swipeMenuItem1.setBackground(new ColorDrawable(Color.rgb(0x31, 0xba, 0x7d)));
                // set item title fontsize
                swipeMenuItem1.setTitleSize(15);
                // set item title font color
                swipeMenuItem1.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(swipeMenuItem1);

                // 删除
                SwipeMenuItem swipeMenuItem2 = new SwipeMenuItem(SamplingTaskActivity.this);
                // set item background
                swipeMenuItem2.setBackground(new ColorDrawable(Color.rgb(0xeb, 0x59, 0x46)));
                // set item width
                swipeMenuItem2.setWidth(dp2px(80));
                // set a icon
                swipeMenuItem2.setTitle(getResources().getString(R.string.text_shanchu));
                swipeMenuItem2.setTitleSize(15);
                // set item title font color
                swipeMenuItem2.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(swipeMenuItem2);
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

        // step 2. listener item click event
        mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                DUSamplingTask duSamplingTask = duSamplingTaskList.get(position);
                if (duSamplingTask == null) {
                    return;
                }

                switch (index) {
                    case 0:
                        int yichaoshu = duSamplingTask.getYiChaoShu();
                        int zongshu = duSamplingTask.getZongShu();
                        if (yichaoshu < zongshu) {
                            jump2RecordActivity(duSamplingTask);
                        }
                        break;
                    case 1:
                        deleteJiChaTask(duSamplingTask.getRenWuBH());
                        break;
                }
            }
        });

        mSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DUSamplingTask item = (DUSamplingTask) view.findViewById(R.id.ch).getTag();
                if (item != null) {
                    if (!mConfigHelper.isCurrentReadingDateValid()) {
                        ApplicationsUtil.showMessage(SamplingTaskActivity.this,
                                R.string.text_exceed_time_limit);
                        return;
                    }

                    jump2VolumeListActivity(item);
                }
            }
        });
    }

    private void jump2RecordActivity(DUSamplingTask duSamplingTask) {
        isToVolumeListActivity = false;
        selectedDUTask = duSamplingTask;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mSamplingTaskPresenter.isTaskContainingFullData(duSamplingTask);
    }

    private void jump2VolumeListActivity(DUSamplingTask duSamplingTask) {
        isToVolumeListActivity = true;
        selectedDUTask = duSamplingTask;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mSamplingTaskPresenter.isTaskContainingFullData(duSamplingTask);
    }

    private void jump2Activity() {
        if (selectedDUTask == null) {
            return;
        }

        if (isToVolumeListActivity) {
            Intent intent = new Intent(SamplingTaskActivity.this, SamplingListActivity.class);
            intent.putExtra(ConstDataUtil.I_RENWUBH, selectedDUTask.getRenWuBH());
//            intent.putExtra(ConstDataUtil.S_CH, TextUtil.getString(selectedDUTask.getcH()));
            intent.putExtra(ConstDataUtil.YICHAOSHU, selectedDUTask.getYiChaoShu());
            startActivity(intent);
        } else {
            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mSamplingTaskPresenter.getNextReadingRecord(selectedDUTask);
        }
    }

    private void deleteJiChaTask(final int taskId) {
        LogUtil.i(TAG, "---delete task 1---");

        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_delete_sampling_Tasks)
                .cancelable(false)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        LogUtil.i(TAG, "---delete task 2---");
                        mSmoothProgressBar.setVisibility(View.VISIBLE);
                        mSamplingTaskPresenter.deleteSamplingTask(taskId);
                    }
                })
                .show();
    }

    private class MyListAdapter extends BaseAdapter {
        private int mResource;
        private List<DUSamplingTask> mSamplingTaskList = null;
        private LayoutInflater mInfater = null;

        public MyListAdapter(Context context,
                             int resource,
                             List<DUSamplingTask> list) {
            mResource = resource;
            mInfater = LayoutInflater.from(context);
            mSamplingTaskList = list;
        }

        public void setDataList(List<DUSamplingTask> list) {
            mSamplingTaskList = list;
        }

        @Override
        public int getCount() {
            return mSamplingTaskList.size();
        }

        @Override
        public Object getItem(int position) {
            return mSamplingTaskList.get(position);
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
                myListHolder.tv_ch = (TextView) convertView.findViewById(R.id.ch);
                myListHolder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
                myListHolder.tv_zhangWuNY = (TextView) convertView.findViewById(R.id.tv_zhangWuNY);
                myListHolder.tvPeriod = (TextView) convertView.findViewById(R.id.tv_chaobiaoZQ);

                if (mConfigHelper.getRegion().equals(SystemConfig.REGION_SUZHOU)) {
                    myListHolder.tv_yichangshu = (TextView) convertView.findViewById(R.id.yichangshu);
                    myListHolder.tv_weichaoshu = (TextView) convertView.findViewById(R.id.weichaoshu);
                } else {
                    myListHolder.tv_yichaoshu = (TextView) convertView.findViewById(R.id.yichaoshu);
                }
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
            }

            // 获取数据
            DUSamplingTask duSamplingTask = mSamplingTaskList.get(position);
            if (duSamplingTask != null) {
                myListHolder.tv_ch.setText(TextUtil.getString(String.valueOf(duSamplingTask.getRenWuBH())));
                myListHolder.tv_ch.setTag(duSamplingTask);
                myListHolder.tv_count.setText(String.valueOf(duSamplingTask.getZongShu()));
                myListHolder.tv_zhangWuNY.setText(String.valueOf(duSamplingTask.getZhangWuNY()));
//                myListHolder.tvPeriod.setText(TextUtil.getString(duJiChaTask.getChaoBiaoZQ()));

                if (mConfigHelper.getRegion().equals(SystemConfig.REGION_SUZHOU)) {
                    myListHolder.tv_weichaoshu.setText(String.valueOf(duSamplingTask.getZongShu() - duSamplingTask.getYiChaoShu()));
                    myListHolder.tv_yichangshu.setText(String.valueOf(duSamplingTask.getAbnormalNumber()));
                } else {
                    myListHolder.tv_yichaoshu.setText(String.valueOf(duSamplingTask.getYiChaoShu()));
                }
            }

            return convertView;
        }
    }

    private class MyListHolder {
        private TextView tv_ch;
        private TextView tv_count;
        private TextView tv_yichaoshu;
        private TextView tv_zhangWuNY;
        private TextView tv_weichaoshu;
        private TextView tv_yichangshu;
        private TextView tvPeriod;

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
