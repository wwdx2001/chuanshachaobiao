package com.sh3h.meterreading.ui.RemoteinSpectionOHistoryListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.RemoteinSpectionListAdapter;
import com.sh3h.meterreading.adapter.RemoteinSpectionhistoryListAdapter;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.InspectionInput.InspectionInputActivity;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.XunJianTaskBean;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 远传巡检历史
 */
public class RemoteinSpectionOHistoryListActivity extends ParentActivity
        implements View.OnClickListener, RemoteinSpectionOHistoryListMvpView, RemoteinSpectionListAdapter.OnItemClickListener, RemoteinSpectionhistoryListAdapter.OnItemClickListener {
  private static final String TAG = "RemoteinSpectionListActivity";
  @Inject
  RemoteinSpectionOHistoryListPresenter mRemoteinSpectionOHistoryListPresenter;
  @Inject
  Bus mEventBus;
  @Inject
  ConfigHelper mConfigHelper;

  @BindView(R.id.loading_process)
  SmoothProgressBar mSmoothProgressBar;
  @BindView(R.id.recycler)
  RecyclerView recyclerView;
  @BindView(R.id.avl_toolbar)
  Toolbar mToolbar;
  private MenuItem mAll, mInspected, mNoInspected, mAddUp, mAddDown;
  private List<BiaoKaBean> mBiaoKaBeans;
  private List<BiaoKaListBean> mDataList;
  private RemoteinSpectionhistoryListAdapter mRemoteinSpectionhistoryListAdapter;


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.menu_remote_inspection, menu);
//    mAll = menu.findItem(R.id.mul_action_remote_all);
//    mInspected = menu.findItem(R.id.mul_action_remote_yes);
//    mNoInspected = menu.findItem(R.id.mul_action_remote_no);
//    mAddUp = menu.findItem(R.id.mul_action_remote_up);
//    mAddDown = menu.findItem(R.id.mul_action_remote_down);
    return true;
  }

  @Subscribe
  public void onInitUserConfigResult(UIBusEvent.InitUserConfigResult initUserConfigResult) {
    LogUtil.i(TAG, "---onInitResult---" + initUserConfigResult.isSuccess());
    if (initUserConfigResult.isSuccess()) {
      initData();
    } else {
      ApplicationsUtil.showMessage(this, R.string.text_init_failure);
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

  private void initRecyclerView() {
    mRemoteinSpectionhistoryListAdapter = new RemoteinSpectionhistoryListAdapter(RemoteinSpectionOHistoryListActivity.this);
    mRemoteinSpectionhistoryListAdapter.setTaskList( mDataList);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(mRemoteinSpectionhistoryListAdapter);
    mRemoteinSpectionhistoryListAdapter.setOnItemClickListener(this);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.mtl_action_update:

        break;
      case R.id.mul_action_remote_all:
        //全部
        break;
      case R.id.mul_action_remote_yes:

        break;
      case R.id.mul_action_remote_no:

        break;
      case R.id.mul_action_remote_up:

        break;
      case R.id.mul_action_remote_down:

        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fuhe_xun_jian_history);
    getActivityComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setActionBarBackButtonEnable();
    mRemoteinSpectionOHistoryListPresenter.attachView(this);
    mEventBus.register(this);
    mSmoothProgressBar.setVisibility(View.INVISIBLE);

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

    initRecyclerView();

//    mRemoteinSpectionListPresenter.loadXunJianData();
  }

  protected void initData() {
    mDataList = new ArrayList<>();
//        区分历史
    mRemoteinSpectionhistoryListAdapter.setHistory(true);
    List<XunJianTaskBean> list = mRemoteinSpectionOHistoryListPresenter.getXunJianFuHeTaskBean(Const.XUNJIANTASK_TYPE);
    for (int i = 0; i < list.size(); i++) {
      List<BiaoKaListBean> biaoKaListBeanList =mRemoteinSpectionOHistoryListPresenter.getTiJiaoBiaoKaListBean(list.get(i).getRENWUMC(),1);
//        GreenDaoUtils.getInstance().getDaoSession(BaseApplication.getInstance())
//        .getBiaoKaListBeanDao().queryBuilder()
//        .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(list.get(i).getRENWUMC()))
//        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(1))
//        .list();
      mDataList.addAll(biaoKaListBeanList);
    }
    mRemoteinSpectionhistoryListAdapter.setTaskList(mDataList);
  }




  @Override
  public void onClick(View view) {

  }







  @Override
  public void onError(String message) {
    mSmoothProgressBar.setVisibility(View.INVISIBLE);
    if (!TextUtil.isNullOrEmpty(message)) {
      LogUtil.i(TAG, message);
      ApplicationsUtil.showMessage(this, message);
    }
  }

  @Override
  public void onSuccess(String message) {
    mSmoothProgressBar.setVisibility(View.INVISIBLE);
    if (!TextUtil.isNullOrEmpty(message)) {
      LogUtil.i(TAG, message);
      ApplicationsUtil.showMessage(this, message);
    }
  }


  @Override
  public void onItemClick(int type, int position) {
    BiaoKaBean item = mBiaoKaBeans.get(position);
    Intent intent = new Intent(this, InspectionInputActivity.class);

    startActivity(intent);

  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {

  }
}
