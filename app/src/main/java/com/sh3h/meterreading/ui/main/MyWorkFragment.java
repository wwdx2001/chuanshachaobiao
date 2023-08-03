package com.sh3h.meterreading.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.MainInfoViewAdapter;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.check.CheckActivity;
import com.sh3h.meterreading.ui.delay.DelayListActivity;
import com.sh3h.meterreading.ui.lgld.LgldListActivity;
import com.sh3h.meterreading.ui.outside.OutsideListActivity;
import com.sh3h.meterreading.ui.sampling.SamplingTaskActivity;
import com.sh3h.meterreading.ui.search.CombinedSearchActivity;
import com.sh3h.meterreading.ui.setting.SettingActivity;
import com.sh3h.meterreading.ui.task.TaskListActivity;
import com.sh3h.mobileutil.util.LogUtil;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MyWorkFragment extends ParentFragment implements MyWorkMvpView,
        View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "MyWorkFragment";
    // 抄表任务
    public static final int ACTION_CHAOBIAORW = 1001;
    // 临时册本
//    public static final int ACTION_CEBENTZ = 1002;
    // 册内检查
    public static final int ACTION_CEBENJC = 1003;
    // 组合查询
    public static final int ACTION_CHAXUN = 1004;
    // 地图
    public static final int ACTION_DITU = 1005;
    //    //重点户
//    public static final int ACTION_VIPTASK = 1006;
    //设置
    public static final int ACTION_SETTING = 1007;
    //催缴
//    public static final int ACTION_RUSHPAY = 1008;
    //稽查
    public static final int ACTION_SAMPLING = 1009;
    //外复工单
    public static final int ACTION_OUTSIDE = 1010;
    //统计
    public static final int ACTION_STATISTICS = 1011;
    //延迟抄表
    public static final int ACTION_DELAY = 1012;
    //量高量低
    public static final int ACTION_LGLD = 1013;


    @Inject
    MyWorkPresenter mMyWorkPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Bind(R.id.fm_grid)
    GridView mGridView;

    @Bind(R.id.fm_list)
    ListView mListView;

    private MainActivity mMainActivity;
    private MainInfoViewAdapter mainInfoViewAdapter;
    private boolean isPrepared;
    private boolean isGrid;
    private boolean isDestroyed;

    private String regionStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.getActivityComponent().inject(this);
        LogUtil.i(TAG, "---onCreate---" + this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mywork, container, false);

        ButterKnife.bind(this, rootView);
        mMyWorkPresenter.attachView(this);

        isPrepared = true;
        initView(inflater);
        lazyLoad();

        isDestroyed = false;
        LogUtil.i(TAG, "---onCreateView---" + this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
        mMyWorkPresenter.detachView();
        isDestroyed = true;
        LogUtil.i(TAG, "---onDestroyView---");
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    private void initView(LayoutInflater inflater) {
        mMyWorkPresenter.initRegion();

        if (mainInfoViewAdapter == null) {
            switch (regionStr) {
                case SystemConfig.REGION_WENZHOU:
                    mainInfoViewAdapter = new MainInfoViewAdapter(getActivity(), inflater,
                            R.array.main_ids_wenzhou, R.array.main_names_wenzhou,
                            R.array.main_icons_wenzhou, MainInfoViewAdapter.TYPE_LIST);
                    break;
                case SystemConfig.REGION_JIADING:
                    mainInfoViewAdapter = new MainInfoViewAdapter(getActivity(), inflater,
                            R.array.main_ids_jiading, R.array.main_names_jiading,
                            R.array.main_icons_jiading, MainInfoViewAdapter.TYPE_LIST);
                    break;
                case SystemConfig.REGION_YIWU:
                    mainInfoViewAdapter = new MainInfoViewAdapter(getActivity(), inflater,
                            R.array.main_ids_wenzhou, R.array.main_names_wenzhou,
                            R.array.main_icons_wenzhou, MainInfoViewAdapter.TYPE_LIST);
                    break;
                default:
                    mainInfoViewAdapter = new MainInfoViewAdapter(getActivity(), inflater,
                            R.array.main_ids_jiading, R.array.main_names_jiading,
                            R.array.main_icons_jiading, MainInfoViewAdapter.TYPE_LIST);
                    break;
            }
        }

        mMyWorkPresenter.checkStyle();
        //refresh();
    }

    public void refresh() {
        if (isPrepared) {
            //mMyWorkPresenter.getAllTasks(true);
        }
    }

    @Override
    public void initStyle(boolean isGrid) {
        this.isGrid = isGrid;

        changeDisplayStyle();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGetAllTasks(List<DUTask> duTasks) {
        if (mainInfoViewAdapter == null) {
            return;
        }
        int[] unReads = mainInfoViewAdapter.getmUnReads();
        int unReadRw = 0;
        for (DUTask duTask : duTasks) {
            int ycsNumber = duTask.getYiChaoShu();
            int zsNumber = duTask.getZongShu();
            if (ycsNumber < zsNumber) {
                unReadRw++;
            }
        }

        if (unReadRw != unReads[0]) {
            unReads[0] = unReadRw;
            mainInfoViewAdapter.setmUnReads(unReads);
            mainInfoViewAdapter.refreshData();
        }

        //mMyWorkPresenter.getAllSamplingTasks(true);
    }

    @Override
    public void onGetSamplingTasks(List<DUSamplingTask> duSamplingTasks) {
        int[] unReads = mainInfoViewAdapter.getmUnReads();
        int unReadRw = 0;
        for (DUSamplingTask duSamplingTask : duSamplingTasks) {
            int ycsNumber = duSamplingTask.getYiChaoShu();
            int zsNumber = duSamplingTask.getZongShu();
            if (ycsNumber < zsNumber) {
                unReadRw++;
            }
        }

        if (unReadRw != unReads[1]) {
            unReads[1] = unReadRw;
            mainInfoViewAdapter.setmUnReads(unReads);
            mainInfoViewAdapter.refreshData();
        }

    }

    @Override
    public void onInitRegion(String region) {
        regionStr = region;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.myWork:
//                Intent intent = new Intent(mMainActivity, TaskListActivity.class);
//                mMainActivity.startActivity(intent);
//                break;
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentMsg = null;
        switch ((int) id) {
            case ACTION_CHAOBIAORW:
                intentMsg = new Intent(mMainActivity, TaskListActivity.class);
                break;
//            case ACTION_CEBENTZ:
//                intentMsg = new Intent(mMainActivity, AdjustTemporaryActivity.class);
//                break;
            case ACTION_CEBENJC:
                intentMsg = new Intent(mMainActivity, CheckActivity.class);
                break;
            case ACTION_CHAXUN:
                intentMsg = new Intent(mMainActivity, CombinedSearchActivity.class);
                break;
            case ACTION_DITU:
//                intentMsg = new Intent(mMainActivity, ArcGisMapActivity.class);
                break;
//          case ACTION_RUSHPAY:
//              intentMsg = new Intent(mMainActivity, RushPayTaskActivity.class);
//              break;
            case ACTION_SAMPLING:
                intentMsg = new Intent(mMainActivity, SamplingTaskActivity.class);
                break;
            case ACTION_OUTSIDE:
                intentMsg = new Intent(mMainActivity, OutsideListActivity.class);
                break;
            case ACTION_SETTING:
                intentMsg = new Intent(mMainActivity, SettingActivity.class);
                break;
            case ACTION_STATISTICS:
                intentMsg = new Intent(mMainActivity, StatisticsActivity.class);
                break;
            case ACTION_DELAY:
                intentMsg = new Intent(mMainActivity, DelayListActivity.class);
                break;
            case ACTION_LGLD:
                intentMsg = new Intent(mMainActivity, LgldListActivity.class);
                break;
            default:
                LogUtil.i(TAG, String.format(Locale.CHINA, "---onItemClick---error: %d", id));
                break;
        }

        if (intentMsg != null) {
            mMainActivity.startActivity(intentMsg);
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(MyModule.PACKAGE_NAME, mMainActivity.getPackageName());
            jsonObject.put(MyModule.ACTIVITY_NAME, TaskListActivity.class.getName());

            JSONObject subJsonObject = new JSONObject();
            subJsonObject.put(MyModule.COUNT,5);
            jsonObject.put(MyModule.DATA, subJsonObject);

            MyModule myModule = new MyModule(jsonObject.toString());
            MainApplication.get(mMainActivity).setMyModule(myModule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

    }

    public void changeDisplayStyle() {
        if (mainInfoViewAdapter == null) {
            return;
        }

        if (isGrid) {
            mainInfoViewAdapter.setmType(MainInfoViewAdapter.TYPE_GRID);
            mListView.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
            mGridView.setAdapter(mainInfoViewAdapter);
            mGridView.setOnItemClickListener(this);
        } else {
            mainInfoViewAdapter.setmType(MainInfoViewAdapter.TYPE_LIST);
            mGridView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListView.setAdapter(mainInfoViewAdapter);
            mListView.setOnItemClickListener(this);
        }
    }

}
