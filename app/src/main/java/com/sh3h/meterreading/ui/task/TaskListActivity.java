package com.sh3h.meterreading.ui.task;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.Coordinate;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.TraceInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.TaskListAdapter;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.service.SyncService;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.map.MapParamEx;
import com.sh3h.meterreading.ui.map.MapStatusEx;
import com.sh3h.meterreading.ui.record.RecordActivity;
import com.sh3h.meterreading.ui.volume.VolumeListActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskListActivity extends ParentActivity implements TaskListMvpView,
        View.OnClickListener, MenuItem.OnMenuItemClickListener, TaskListAdapter.OnItemClickListener{
    private static final String TAG = "TaskListActivity";

    @Inject
    TaskListPresenter mTaskListPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    ConfigHelper mConfigHelper;

    @Bind(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;

    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    Bus mEventBus;

    private TaskListAdapter taskListAdapter;
    private boolean isToVolumeListActivity;
    private DUTask selectedDUTask;
    private List<DUTask> mTaskList;
    private boolean isCurrentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mEventBus.register(this);

        mTaskListPresenter.attachView(this);
        setActionBarBackButtonEnable();

        needRefresh = false;
        isToVolumeListActivity = true;
        selectedDUTask = null;
        mTaskList = null;
        isCurrentActivity = true;
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
            mTaskListPresenter.loadTasks();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isCurrentActivity = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mTaskListPresenter.detachView();

        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onLoadTasks(List<DUTask> duTaskList) {
        LogUtil.i(TAG, "---onLoadTasks---");

        if (duTaskList != null) {
            int unReadRw = 0;
            for (DUTask duTask : duTaskList) {
                int ycsNumber = duTask.getYiChaoShu();
                int zsNumber = duTask.getZongShu();
                if (ycsNumber < zsNumber) {
                    unReadRw++;
                }
            }

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getApplicationContext().getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, TaskListActivity.class.getName());
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
        initRecyclerView(duTaskList);
    }

    @Override
    public void onDeleteTask(Boolean aBoolean) {
        LogUtil.i(TAG, "---onDeleteTask---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (aBoolean) {
            ApplicationsUtil.showMessage(this, R.string.text_deleteTasks_success);
            mEventPosterHelper.postEventSafely(new UIBusEvent.UpdateTaskList());//通知抄表任务界面更新列表
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_deleteTasks_error);
        }
    }

    @Override
    public void onGetNextReadingRecord(DUTask duTask, DURecord duRecord,
                                       int startOrderNumber, int endOrderNumber) {
        LogUtil.i(TAG, "---onGetNextReadingRecord---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if ((duTask == null) || (duRecord == null) || (startOrderNumber > endOrderNumber)) {
            LogUtil.i(TAG, "---onGetNextReadingRecord---error");
            return;
        }

        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra(ConstDataUtil.S_CH, duRecord.getCh());
        intent.putExtra(ConstDataUtil.S_CID, duRecord.getCid());
        intent.putExtra(ConstDataUtil.I_RENWUBH, duRecord.getRenwubh());
        intent.putExtra(ConstDataUtil.I_CENEIXH, duRecord.getCeneixh());
        intent.putExtra(ConstDataUtil.I_LASTREADINGCHILD, duRecord.getLastReadingChild());
        intent.putExtra(ConstDataUtil.YICHAOSHU, duTask.getYiChaoShu());
        intent.putExtra(ConstDataUtil.STARTXH, startOrderNumber);
        intent.putExtra(ConstDataUtil.ENDXH, endOrderNumber);
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
            //根据任务编号，册本号下载册本列表
            downloadVolume(selectedDUTask.getRenWuBH(), selectedDUTask.getcH());
        }
    }

    @Override
    public void onLoadCards(List<DUCard> duCardList) {
        LogUtil.i(TAG, "---onLoadCards---");
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (duCardList == null || duCardList.size() <= 0) {
            ApplicationsUtil.showMessage(this, R.string.text_dot_have_xy);
            return;
        }
        List<Coordinate> duCoordinates = new ArrayList<>();
        for (DUCard duCard : duCardList) {
            if (duCard.getX1() != null && !duCard.getX1().trim().equals("")
                    && !duCard.getX1().trim().equals("0")) {
                Coordinate duCoordinate = new Coordinate(Double.valueOf(duCard.getX1()),
                        Double.valueOf(duCard.getY1()), duCard.getCeneixh());
                duCoordinates.add(duCoordinate);
            }
        }
        if (duCoordinates.size() <= 0) {
            ApplicationsUtil.showMessage(this, R.string.text_dot_have_xy);
            return;
        }
        trackMap(duCoordinates);
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
    public void onLoadTrance(List<TraceInfo> list) {
        Intent intent = getMapIntent();
        if (intent == null){
            ApplicationsUtil.showMessage(this, R.string.toast_not_install_map_application);
            return;
        }

        if (list == null || list.size() == 0){
            intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.VIEW_MAP.ordinal());
        }else {
            intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.TRACK_MAP.ordinal());
            intent.putExtra(MapParamEx.TRACE_INFO_LIST, (Serializable) list);
        }
        startActivity(intent);
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
//                if ((!isExceedingZWNY()) && canSync(mConfigHelper.getUploadingTimeError())) {
//                    needDialog = true;
//                    startService(SyncService.getStartIntent(this));
//                }
                //TODO LIBAO 2018.7.29 去除时间差判断
                if (!isExceedingZWNY()) {
                    needDialog = true;
                    startService(SyncService.getStartIntent(this));
                }
                break;
        }
        return true;
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
            mTaskListPresenter.loadTasks();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    @Subscribe
    public void onSyncDataStart(UIBusEvent.SyncDataStart syncDataStart) {
        LogUtil.i(TAG, "---onSyncDataStart---");
        if (needDialog) {
            if (syncDataStart.getSyncType()==SyncType.UPLOADING_DOWNLOADING_ALL) {
                showProgress(R.string.dialog_download_data);
            }else if (syncDataStart.getSyncType()==SyncType.UPLOADING_VOLUME){
                showProgress(R.string.dialog_upload_data);
            }else {
                showProgress(R.string.dialog_sync_data);
            }
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
            case UPLOADING_DOWNLOADING_ALL:
                refresh();
                //TODO 2018.7.22 LIBAO 不提示
                popupSyncDataInfo(syncDataEnd);
                break;
            case DOWNLOADING_VOLUME:
                needRefresh = true;
                jump2Activity();
                break;
            case UPLOADING_VOLUME:
                refresh();
//                if (isCurrentActivity){
                    popupSyncDataInfo(syncDataEnd);
//                }
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onUpdateTaskList(UIBusEvent.UpdateTaskList updateTaskList) {
        LogUtil.i(TAG, "---onUpdateTaskList---");
        refresh();
    }

    private void refresh() {
        if (isActive) {
            needRefresh = false;
            mTaskListPresenter.loadTasks();
        } else {
            needRefresh = true;
        }
    }

    private void popupSyncDataInfo(UIBusEvent.SyncDataEnd syncDataEnd) {
        String result = MainApplication.get(this).parseSyncDataInfo(syncDataEnd);
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

    private void initRecyclerView(final List<DUTask> duTaskList) {
        if (duTaskList == null) {
            return;
        }

        mTaskList = duTaskList;
        taskListAdapter = new TaskListAdapter();
        taskListAdapter.setTaskList(duTaskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskListAdapter);
        taskListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int type, int position) {
        final DUTask item = mTaskList.get(position);
        switch (type){
            case TaskListAdapter.TYPE_ITEM:
                if (item.getTongBuBZ() != 0){
                    return;
                }

                jump2VolumeListActivity(item);
                break;
            case TaskListAdapter.TYPE_STATUS://上传数据
                if (item.getTongBuBZ() == 2){
                    return;
                }

                if (item.getZongShu() != item.getYiChaoShu()){
                    jump2RecordActivity(item);
                    return;
                }
                //TODO LIBAO 2018.7.27 添加提示框
             AlertDialog dialog=   new AlertDialog.Builder(this)
                        .setTitle("确认上传？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                needDialog = true;
                                uploadVolume(item.getRenWuBH(), item.getcH());
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                //修改“确认”、“取消”按钮的字体大小
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(26);
//                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(26);

                break;
            case TaskListAdapter.TYPE_GUIJI:
                mTaskListPresenter.getChaoBiaoTrance(item.getRenWuBH(), item.getcH());
                break;
            default:
                break;
        }
    }

    private void jump2RecordActivity(DUTask duTask) {
        isToVolumeListActivity = false;
        selectedDUTask = duTask;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mTaskListPresenter.isTaskContainingFullData(duTask);
    }

    private Intent getMapIntent(){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setClassName("com.sh3h.citymap", "com.sh3h.citymap.ui.map.BaiduMapActivity");
        PackageManager pm = getPackageManager();

        List<ResolveInfo> resolveInfo = pm.queryIntentActivities(intent, 0);
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        ResolveInfo info = resolveInfo.get(0);
        String packageName = info.activityInfo.packageName;
        String className = info.activityInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        Intent explicitIntent = new Intent(intent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    private void jump2VolumeListActivity(DUTask duTask) {
        isToVolumeListActivity = true;
        selectedDUTask = duTask;
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mTaskListPresenter.isTaskContainingFullData(duTask);
    }

    private void jump2Activity() {
        if (selectedDUTask == null) {
            return;
        }

        if (isToVolumeListActivity) {
            Intent intent = new Intent(TaskListActivity.this, VolumeListActivity.class);
            intent.putExtra(ConstDataUtil.I_RENWUBH, selectedDUTask.getRenWuBH());
            intent.putExtra(ConstDataUtil.S_CH, TextUtil.getString(selectedDUTask.getcH()));
            intent.putExtra(ConstDataUtil.YICHAOSHU, selectedDUTask.getYiChaoShu());
            intent.putExtra(ConstDataUtil.ZHANGWUNY, selectedDUTask.getZhangWuNY());
            startActivity(intent);
        } else {
            mSmoothProgressBar.setVisibility(View.VISIBLE);
            mTaskListPresenter.getNextReadingRecord(selectedDUTask);
        }
    }

    private void deleteTask(final int taskId, final String volume) {
        LogUtil.i(TAG, "---delete task 1---");

        boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_deleteTasks)
                .cancelable(false)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .buttonsGravity(leftOrRight)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        LogUtil.i(TAG, "---delete task 2---");
                        mSmoothProgressBar.setVisibility(View.VISIBLE);
                        mTaskListPresenter.deleteTask(taskId, volume);
                    }
                })
                .show(leftOrRight);
    }
    /**
     * 判断（201808），当前年月不等于，提示删除
     * @return
     */
    private boolean isExceedingZWNY() {
        if ((mTaskList == null) || (mTaskList.size() <= 0)) {
            return false;
        }

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int zhangWuNY = year * 100 + month;
        StringBuilder sb = new StringBuilder();
        for (DUTask duTask : mTaskList) {
            if (duTask.getZhangWuNY() != zhangWuNY) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(duTask.getcH());
            }
        }

        if (sb.length() <= 0) {
            return false;
        }

        sb.append("\n");
        sb.append(getResources().getString(R.string.text_zhangwuny_time_error));
        ApplicationsUtil.showMessage(this, sb.toString());
        return true;
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

    private class MyListAdapter extends BaseAdapter {
        private int mResource;
        private List<DUTask> mTaskList = null;
        private LayoutInflater mInfater = null;

        public MyListAdapter(Context context,
                             int resource,
                             List<DUTask> list) {
            mResource = resource;
            mInfater = LayoutInflater.from(context);
            mTaskList = list;
        }

        public void setDataList(List<DUTask> list) {
            mTaskList = list;
        }

        @Override
        public int getCount() {
            return mTaskList.size();
        }

        @Override
        public Object getItem(int position) {
            return mTaskList.get(position);
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
                myListHolder.yichaoshu = (TextView) convertView.findViewById(R.id.yichaoshu);
                myListHolder.tv_zhangWuNY = (TextView) convertView.findViewById(R.id.tv_zhangWuNY);
                myListHolder.tvPeriod = (TextView) convertView.findViewById(R.id.tv_chaobiaoZQ);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
            }

            // 获取数据
            DUTask duTask = mTaskList.get(position);
            if (duTask != null) {
                myListHolder.tv_ch.setText(String.format(ConstDataUtil.LOCALE, "%s/%d",
                        TextUtil.getString(duTask.getcH()), duTask.getGongCi()));
                myListHolder.tv_ch.setTag(duTask);
                myListHolder.tv_count.setText(String.valueOf(duTask.getZongShu()));
                myListHolder.yichaoshu.setText(String.valueOf(duTask.getYiChaoShu()));
                myListHolder.tv_zhangWuNY.setText(String.valueOf(duTask.getZhangWuNY()));
                myListHolder.tvPeriod.setText(TextUtil.getString(duTask.getChaoBiaoZQ()));
            }

            return convertView;
        }
    }

    private class MyListHolder {
        private TextView tv_ch;
        private TextView tv_count;
        private TextView yichaoshu;
        private TextView tv_zhangWuNY;
        private TextView tvPeriod;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
