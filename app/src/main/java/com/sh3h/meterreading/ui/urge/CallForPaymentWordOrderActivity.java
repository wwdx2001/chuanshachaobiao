package com.sh3h.meterreading.ui.urge;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.google.common.eventbus.Subscribe;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.urge.adapter.CallForPaymentWordOrderAdapter;
import com.sh3h.meterreading.ui.urge.back.CallForPaymentOrderBackActivity;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentWordOrderContract;
import com.sh3h.meterreading.ui.urge.delay.CallForPaymentOrderDelayActivity;
import com.sh3h.meterreading.ui.urge.presenter.CallForPaymentWordOrderPresenterImpl;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.RecyclerSpacingItemDecoration;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallForPaymentWordOrderActivity extends ParentActivity
  implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener,
        CallForPaymentWordOrderContract.View, BaseQuickAdapter.OnItemChildClickListener {

  @BindView(R.id.call_for_pay_word_order_srl)
  SwipeRefreshLayout mCallForPayWordOrderSrl;

  @BindView(R.id.call_for_pay_word_order_rv)
  RecyclerView mCallForPayWordOrderRv;

  @BindView(R.id.call_for_pay_word_order_search_et)
  EditText mCallForPayWordOrderSearchEt;

  @BindView(R.id.call_for_pay_word_order_search_btn)
  TextView mCallForPayWordOrderSearchBtn;

  @BindView(R.id.call_for_pay_word_order_resetting_btn)
  TextView mCallForPayWordOrderResettingBtn;

  private CallForPaymentWordOrderAdapter mAdapter;
  private MenuItem mMenuItemSort;

  private List<CuijiaoEntity> mWordOrderList;

  private String mS_ch;
  private Boolean isGradeDown = false;
  private Boolean isRefreshSort = true;
  private String mSortWay = "ch";
  private String mSearchText = "";

  CallForPaymentWordOrderPresenterImpl mPresenter;

  @Inject
  Bus mEventBus;

  private boolean isSearch = false;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_call_for_payment_word_order;
  }

  @Override
  protected void initView1() {
    ButterKnife.bind(this);
    getSupportActionBar().setTitle(getResources().getString(R.string.call_for_pay_word_order_title));
    mCallForPayWordOrderRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    mCallForPayWordOrderRv.addItemDecoration(new RecyclerSpacingItemDecoration(0, 6, 0, 0));
  }

  @Override
  protected void initData1() {
    super.initData1();
    getActivityComponent().inject(this);
    mEventBus.register(this);

    mPresenter = new CallForPaymentWordOrderPresenterImpl(this);
    mPresenter.attachView(this);
    Intent intent = getIntent();
    mS_ch = intent.getStringExtra(Const.S_CH);
    mSearchText = intent.getStringExtra(Const.SEARCHTEXT);


    mWordOrderList = new ArrayList<>();
    mAdapter = new CallForPaymentWordOrderAdapter(mWordOrderList, this);
    mCallForPayWordOrderRv.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener(this);
  }

  @Override
  protected void requestData1() {
    super.requestData1();
    mPresenter.getWorkOrderList(mS_ch);
  }

  @Override
  protected void initListener1() {
    super.initListener1();
    mCallForPayWordOrderSearchBtn.setOnClickListener(this);
    mCallForPayWordOrderResettingBtn.setOnClickListener(this);
    mCallForPayWordOrderSrl.setOnRefreshListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_call_for_pay_word_order, menu);
    mMenuItemSort = menu.findItem(R.id.menu_item_sort);
    return super.onCreateOptionsMenu(menu);
  }


  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    isRefreshSort = false;
    item.setChecked(true);
    switch (item.getItemId()) {
      case R.id.menu_item_ascending_order:
        mMenuItemSort.setTitle(getResources().getString(R.string.ascending_order));
        isGradeDown = false;
        mPresenter.listSort(isGradeDown, mSortWay, mAdapter.getData());
        return true;
      case R.id.menu_item_descending_order:
        mMenuItemSort.setTitle(getResources().getString(R.string.descending_order));
        isGradeDown = true;
        mPresenter.listSort(isGradeDown, mSortWay, mAdapter.getData());
        return true;
      case R.id.menu_item_ch_sort:
        mSortWay = "ch";
        mPresenter.listSort(isGradeDown, mSortWay, mAdapter.getData());
        return true;
      case R.id.menu_item_address_sort:
        mSortWay = "address";
        mPresenter.listSort(isGradeDown, mSortWay, mAdapter.getData());
        return true;
      case R.id.menu_item_amount_sort:
        mSortWay = "amount";
        mPresenter.listSort(isGradeDown, mSortWay, mAdapter.getData());
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onRefresh() {
    mPresenter.getWorkOrderList(mS_ch);
    mCallForPayWordOrderSearchEt.setText("");
    isRefreshSort = true;
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    Intent intent = new Intent(this, CallForPaymentOrderDetailActivity.class);
    intent.putExtra(Const.S_CID, mWordOrderList.get(position).getS_CID());
    intent.putExtra(Const.RENWUID, mWordOrderList.get(position).getS_RENWUID());
    intent.putExtra(Const.CUIJIAODATA, mWordOrderList.get(position));
    startActivity(intent);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.call_for_pay_word_order_search_btn:
        isSearch = true;
        String searchText = mCallForPayWordOrderSearchEt.getText().toString().trim();
        if (!searchText.equals(mSearchText)) {
          mSearchText = "";
        }
        mPresenter.searchData(searchText, mWordOrderList);
        break;

      case R.id.call_for_pay_word_order_resetting_btn:
        isSearch = false;
        mCallForPayWordOrderSearchEt.setText("");
        mSearchText = "";
        mPresenter.searchData("", mWordOrderList);
        break;
    }
  }


  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public void success(final Object o) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        List<CuijiaoEntity> data = (List<CuijiaoEntity>) o;
        mWordOrderList.clear();
        mWordOrderList.addAll(data);
        if (mCallForPayWordOrderSrl.isRefreshing()) {
          mCallForPayWordOrderSrl.setRefreshing(false);
        }
        if (!TextUtil.isNullOrEmpty(mSearchText)) {
          mCallForPayWordOrderSearchEt.setText(mSearchText);
          mPresenter.searchData(mSearchText, mWordOrderList);
        }
        if (isRefreshSort) {
          isRefreshSort = false;
          mPresenter.listSort(isGradeDown, mSortWay, data);
        }
        mAdapter.setNewData(data);
      }
    });
  }

  @Override
  public void failed(String result) {
    if (result != null) {
      ToastUtils.showShort(result);
    }
    if (mCallForPayWordOrderSrl.isRefreshing()) {
      mCallForPayWordOrderSrl.setRefreshing(false);
    }
    ToastUtils.showLong("当前册本已经没有要处理的工单了");
    finish();
  }

  @Override
  public void error(String s) {
    if (mCallForPayWordOrderSrl.isRefreshing()) {
      mCallForPayWordOrderSrl.setRefreshing(false);
    }
  }
  @Subscribe
  public void CuijiaoRefreshEvent(CuijiaoEntity cuijiaoEntity) {
    mCallForPayWordOrderSrl.setRefreshing(true);
    requestData1();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mWordOrderList = null;
    mEventBus.unregister(this);

    mPresenter.detachView();
  }

  @Override
  public void searchDataNotify(List<CuijiaoEntity> mWordOrderList) {
    mAdapter.setNewData(mWordOrderList);
  }

  @Override
  public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    switch (view.getId()) {
      case R.id.tv_delay:
        startActivityForResult(new Intent(this, CallForPaymentOrderDelayActivity.class)
                .putExtra(Const.BEAN, mAdapter.getData().get(position)), Const.RESULT_CODE);
        break;
      case R.id.tv_back:
        startActivityForResult(new Intent(this, CallForPaymentOrderBackActivity.class)
                .putExtra(Const.BEAN, mAdapter.getData().get(position)), Const.RESULT_CODE);
        break;
    }
  }
}
