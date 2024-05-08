package com.sh3h.meterreading.ui.urge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.CommonFragementPagerAdapter;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentOrderDetailContract;
import com.sh3h.meterreading.ui.urge.fragment.CallForPayDetailFragment;
import com.sh3h.meterreading.ui.urge.fragment.CallForPaymentBackfillFragment;
import com.sh3h.meterreading.ui.urge.fragment.CallForPaymentMediaFragment;
import com.sh3h.meterreading.ui.urge.presenter.CallForPaymentOrderDetailPresenterImpl;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.DateUtils;
import com.sh3h.meterreading.util.NoScrollViewPager;
import com.squareup.otto.Bus;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CallForPaymentOrderDetailActivity extends ParentActivity
  implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener, CallForPaymentOrderDetailContract.View {

  CallForPaymentOrderDetailPresenterImpl mPresenter;

  @BindView(R.id.call_for_pay_order_detail_vp)
  NoScrollViewPager mCallForPayOrderDetailVp;

  @BindView(R.id.call_for_pay_order_detail_tl)
  TabLayout mCallForPayOrderDetailTl;

  private CommonFragementPagerAdapter mFragmentAdapter;
  private ParentFragment detailFragment, backfillFragment, mediaFragment;
  private List<Fragment> mFragments;

  private MenuItem mItem1;
  private MenuItem mItem2;

  private String mS_CID, mRENWUID;
  private CuijiaoEntity mDetailBean;
  private boolean isSave;

  @Inject
  Bus mEventBus;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_call_for_payment_order_detail;
  }

  @Override
  protected void initView1() {
    ButterKnife.bind(this);
    getSupportActionBar().setTitle(R.string.call_for_pay_order_detail_title);
  }

  @Override
  protected void initData1() {
    super.initData1();
    getActivityComponent().inject(this);
    mEventBus.register(this);

    mPresenter = new CallForPaymentOrderDetailPresenterImpl(this);

    Intent intent = getIntent();
    mS_CID = intent.getStringExtra(Const.S_CID);
    mRENWUID = intent.getStringExtra(Const.RENWUID);

    detailFragment = new CallForPayDetailFragment();
    backfillFragment = new CallForPaymentBackfillFragment();
    mediaFragment = new CallForPaymentMediaFragment();
    mFragments = new ArrayList<>();
    mFragments.add(detailFragment);
    mFragments.add(backfillFragment);
    mFragments.add(mediaFragment);

//    FragmentManager fragmentManager = getSupportFragmentManager();
//    fragmentManager.beginTransaction()
//      .add(detailFragment, "f1")
//      .add(backfillFragment, "f2")
//      .add(mediaFragment, "f3")
//      .commit();




//    mCallForPayOrderDetailTl.setupWithViewPager(mCallForPayOrderDetailVp);
  }

  @Override
  protected void requestData1() {
    super.requestData1();
    mPresenter.getOrderDetail(mRENWUID, mS_CID);
  }

  @Override
  protected void initListener1() {
    super.initListener1();
    mCallForPayOrderDetailTl.setOnTabSelectedListener(this);
    mCallForPayOrderDetailVp.addOnPageChangeListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_right_text, menu);
    mItem1 = menu.findItem(R.id.menu_wenti_shangbao);
    mItem1.setVisible(true);
    mItem1.setTitle("保存");
    mItem2 = menu.findItem(R.id.menu_right_text);
    mItem2.setTitle("上传");
    mItem2.setVisible(true);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_wenti_shangbao:
        isSave = true;
        setSaveOrUploadData();
        break;
      case R.id.menu_right_text:
        isSave = false;
        setSaveOrUploadData();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * 保存或上传数据
   *
   */
  private void setSaveOrUploadData() {
    if (mDetailBean == null) {
      ToastUtils.showLong(R.string.getting_data_text);
      return;
    }
    CallForPaymentBackFillDataBean backFillData = ((CallForPaymentBackfillFragment) backfillFragment).getBackFillData();
    if (backFillData == null) {
//      ToastUtils.showLong("请填写修改信息再进行保存或上传");
      return;
    }
    CallForPaymentBackFillDataBean dataBean = ((CallForPaymentMediaFragment) mediaFragment).getMediaInfo(backFillData);
    if (dataBean == null) {
      return;
    }
    dataBean.setV_CAOZUOR("0018");
//    dataBean.setV_CAOZUOR(SPUtils.getInstance().getString(com.sh3h.serverprovider.rpc.util.Const.S_YUANGONGH));
    dataBean.setV_CAOZUOSJ(DateUtils.getCurrentTime());
    dataBean.setV_RENWUID(mRENWUID);
    dataBean.setV_RENWUM(mDetailBean.getS_RENWUMC());
    dataBean.setId((long) (mDetailBean.getS_CID() + mDetailBean.getS_RENWUID()).hashCode());
    mPresenter.saveOrUploadData(dataBean, isSave);
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    mCallForPayOrderDetailVp.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    mCallForPayOrderDetailTl.getTabAt(position).select();
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  @Override
  public void success(Object o) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        mDetailBean = (CuijiaoEntity) o;
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.BEAN, mDetailBean);
        mDetailBean.setS_RENWUID(mRENWUID);
        detailFragment.setArguments(bundle);
        bindFragment();
      }
    });
  }

  private void bindFragment() {
    mFragmentAdapter = new CommonFragementPagerAdapter(getSupportFragmentManager(), mFragments);
    mCallForPayOrderDetailVp.setAdapter(mFragmentAdapter);
    mCallForPayOrderDetailVp.setOffscreenPageLimit(mFragments.size());

    TabLayout.Tab tab1 = mCallForPayOrderDetailTl.newTab();
    tab1.setText("详情");
    TabLayout.Tab tab2 = mCallForPayOrderDetailTl.newTab();
    tab2.setText("回填");
    TabLayout.Tab tab3 = mCallForPayOrderDetailTl.newTab();
    tab3.setText("多媒体");
    mCallForPayOrderDetailTl.addTab(tab1);
    mCallForPayOrderDetailTl.addTab(tab2);
    mCallForPayOrderDetailTl.addTab(tab3);
  }

  @Override
  public void getBackFillData(CallForPaymentBackFillDataBean bean) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.CALLFORPAYMENTBACKFILLDATABEAN, bean);
        bundle.putString(Const.RENWUID, mRENWUID);
        backfillFragment.setArguments(bundle);
        mediaFragment.setArguments(bundle);
      }
    });
  }

  @Override
  public void failed(String result) {
    if (result != null) {
      ToastUtils.showLong(result);
    }
    ToastUtils.showLong(R.string.get_local_data_is_null_retry_refresh_text);
  }

  @Override
  public void error(String s) {

  }

  @Override
  public void getResult(String s) {
    if (!isSave) {
      mEventBus.post(new CuijiaoEntity());
    }
    ToastUtils.showLong(s);
    finish();
  }

  @Subscribe
  public void getNotify(String responce) {
    getResult(responce);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mEventBus.unregister(this);

  }
}
