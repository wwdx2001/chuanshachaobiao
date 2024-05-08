package com.sh3h.meterreading.ui.urge.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.CommonFragementPagerAdapter;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.NoScrollViewPager;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class CallForPayDetailFragment extends ParentFragment implements TabLayout.OnTabSelectedListener {
  private final String TAG = "CallForPayDetailFragment";

  private TabLayout mCallForPayDetailTl;
  private NoScrollViewPager mCallForPayDetailVp;

  private CommonFragementPagerAdapter mFragmentAdapter;


  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  public CallForPayDetailFragment() {
    // Required empty public constructor
  }
  public static CallForPayDetailFragment newInstance(String param1, String param2) {
    CallForPayDetailFragment fragment = new CallForPayDetailFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_call_for_pay_detail;
  }

  @Override
  protected void initView1(View view) {
    mCallForPayDetailTl = view.findViewById(R.id.call_for_pay_detail_tl);
    mCallForPayDetailVp = view.findViewById(R.id.call_for_pay_detail_vp);
  }

  @Override
  protected void initData1() {
    super.initData1();
    if (getArguments() == null) {
      ToastUtils.showLong(R.string.get_data_err_text);
      LogUtil.e(TAG, "initData：" + R.string.get_data_err_text);
      return;
    }
    CuijiaoEntity data = getArguments().getParcelable(Const.BEAN);

    List<Fragment> fragments = new ArrayList<>();
    OrderDetailMessageFragment detailMessageFragment = new OrderDetailMessageFragment();
    OrderElseMessageFragment elseMessageFragment = new OrderElseMessageFragment();
    fragments.add(detailMessageFragment);
    fragments.add(elseMessageFragment);

    mFragmentAdapter = new CommonFragementPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
    mCallForPayDetailVp.setAdapter(mFragmentAdapter);
    mCallForPayDetailVp.setOffscreenPageLimit(fragments.size());

    TabLayout.Tab tab1 = mCallForPayDetailTl.newTab();
    tab1.setText("详细信息");
    TabLayout.Tab tab2 = mCallForPayDetailTl.newTab();
    tab2.setText("其他信息");

    mCallForPayDetailTl.addTab(tab1);
    mCallForPayDetailTl.addTab(tab2);

    Bundle bundle = new Bundle();
    bundle.putParcelable("data", data);
    detailMessageFragment.setArguments(bundle);
    elseMessageFragment.setArguments(bundle);

  }

  @Override
  protected void initListener1() {
    mCallForPayDetailTl.setOnTabSelectedListener(this);
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    mCallForPayDetailVp.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }

  @Override
  protected void lazyLoad() {

  }
}
