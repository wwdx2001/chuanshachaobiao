package com.sh3h.meterreading.ui.urge.fragment;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.urge.adapter.OrderDetailMessageAdapter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.KeyValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderElseMessageFragment extends ParentFragment {

  private final String TAG = "OrderElseMessageFragment";

  private RecyclerView mOrderElseRv;
  private List<KeyValueBean> keyValueBeans;

  private OrderDetailMessageAdapter mAdapter;

  private final String[] keys = {"涉水代码：", "简号：", "　位置分类：", "　安装位置：",
    "水表条形码：", "是否实名制：", "口径：", "收费方式：",
    "账单类型：", "在装表安装时间："};

  private String[] values;

  public OrderElseMessageFragment() {
    // Required empty public constructor
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_order_else_message;
  }

  @Override
  protected void initView1(View view) {
    mOrderElseRv = view.findViewById(R.id.order_else_rv);
    mOrderElseRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
  }

  @Override
  protected void initData1() {
    super.initData1();
    if (getArguments() == null) {
      ToastUtils.showLong(R.string.get_data_err_text);
      LogUtil.e(TAG, "initData：Bundle is null" + R.string.get_data_err_text);

      return;
    }

    CuijiaoEntity data = getArguments().getParcelable("data");
    if (data == null) {
      ToastUtils.showLong(R.string.get_data_err_text);
      LogUtil.e(TAG, "initData：data is null" + R.string.get_data_err_text);
      return;
    }
    values = new String[]{data.getS_SHESHUIID(), data.getS_JH(), data.getS_WEIZHIFL(), data.getS_ANZHUANGWZ(), data.getS_SHUIBIAOTXM(),
      data.getISSHIM(), data.getKOUJINGMC(), data.getI_SFFS(), data.getI_DIANZIZD(), data.getD_HUANBIAO()};

    keyValueBeans = new ArrayList<>();

    for (int i = 0; i < keys.length; i++) {
      KeyValueBean bean = new KeyValueBean();
      bean.setKey(keys[i]);
      bean.setValue(values[i]);
      keyValueBeans.add(bean);
    }

    mAdapter = new OrderDetailMessageAdapter(keyValueBeans);
    mOrderElseRv.setAdapter(mAdapter);
  }

  @Override
  protected void lazyLoad() {

  }
}
