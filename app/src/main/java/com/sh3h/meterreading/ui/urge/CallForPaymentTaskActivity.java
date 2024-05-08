package com.sh3h.meterreading.ui.urge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.google.common.eventbus.Subscribe;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.urge.adapter.CallForPaymentTaskAdapter;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentTaskContract;
import com.sh3h.meterreading.ui.urge.presenter.CallForPaymentTaskPresenterImpl;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.RecyclerSpacingItemDecoration;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("Registered")
public class CallForPaymentTaskActivity extends ParentActivity
  implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, CallForPaymentTaskContract.CallForPaymentTaskView, View.OnClickListener {


  @BindView(R.id.call_for_pay_srl)
  SwipeRefreshLayout mCallForPaySrl;

  @BindView(R.id.call_for_pay_task_rv)
  RecyclerView mCallForPayTaskRv;

  @BindView(R.id.call_for_pay_task_search_et)
  EditText mCallForPayTaskSearchEt;

  @BindView(R.id.call_for_pay_task_search_btn)
  TextView mCallForPayTaskSearchBtn;

  @BindView(R.id.call_for_pay_task_resetting_btn)
  TextView mCallForPayTaskResettingBtn;

  private CallForPaymentTaskAdapter mTaskAdapter;

  private List<CallForPaymentTaskBean> mTaskList;

  CallForPaymentTaskPresenterImpl mPresenter;

  @Inject
  Bus mEventBus;

  private String searchText = "";


  @Override
  protected int getLayoutId() {
    return R.layout.activity_call_for_payment_task;
  }

  @Override
  protected void initView1() {
    ButterKnife.bind(this);
    getSupportActionBar().setTitle(getResources().getString(R.string.call_for_pay_task_title));
    setActionBarBackButtonEnable();
    mCallForPayTaskRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    mCallForPayTaskRv.addItemDecoration(new RecyclerSpacingItemDecoration(0, 6, 0, 0));
  }

  @Override
  protected void initData1() {
    super.initData1();
    getActivityComponent().inject(this);
    mEventBus.register(this);

    mPresenter = new CallForPaymentTaskPresenterImpl(this);
    mPresenter.attachView(this);
    mTaskList = new ArrayList<>();
    mTaskAdapter = new CallForPaymentTaskAdapter(mTaskList);
    mCallForPayTaskRv.setAdapter(mTaskAdapter);
  }

  @Override
  protected void initAdapter1() {
    super.initAdapter1();

  }

  @Override
  protected void requestData1() {
    super.requestData1();
    mPresenter.getTaskList("");
  }

  private void getTaskListData() {

  }

  @Override
  protected void initListener1() {
    super.initListener1();

    mCallForPaySrl.setOnRefreshListener(this);
    mTaskAdapter.setOnItemClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.call_for_pay_task_search_btn:
        searchText = mCallForPayTaskSearchEt.getText().toString().trim();
        mPresenter.getTaskList(searchText);
        break;

      case R.id.call_for_pay_task_resetting_btn:
        mCallForPayTaskSearchEt.setText("");
        searchText = "";
        mPresenter.getTaskList("");
        break;
    }
  }

  @Override
  public void onRefresh() {
    mPresenter.getTaskList("");
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    Intent intent = new Intent(this, CallForPaymentWordOrderActivity.class);
    intent.putExtra(Const.S_CH, mTaskList.get(position).getS_CEBENH());
    if (!TextUtil.isNullOrEmpty(searchText)) {
      intent.putExtra(Const.SEARCHTEXT, searchText);
    }
    startActivity(intent);
  }

  @Subscribe
  public void CuijiaoRefreshEvent(CuijiaoEntity cuijiaoEntity) {
    requestData1();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mEventBus.unregister(this);
    mPresenter.detachView();
  }

  @Override
  public void success(final Object o) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        List<CallForPaymentTaskBean> data = (List<CallForPaymentTaskBean>) o;
        mTaskList.clear();
        mTaskList.addAll(data);
        if (mCallForPaySrl.isRefreshing()) {
          mCallForPaySrl.setRefreshing(false);
        }
        mTaskAdapter.setNewData(mTaskList);
      }
    });
  }

  @Override
  public void failed(String result) {
    if (result != null) {
      ToastUtils.showLong(result);
    }
    ToastUtils.showLong(R.string.get_local_data_is_null_retry_refresh_text);
    if (mCallForPaySrl.isRefreshing()) {
      mCallForPaySrl.setRefreshing(false);
    }
  }

  @Override
  public void error(String s) {
    if (mCallForPaySrl.isRefreshing()) {
      mCallForPaySrl.setRefreshing(false);
    }
    ToastUtils.showLong(s);
  }

}
