package com.sh3h.meterreading.ui.urge.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.urge.CallForPaymentArrearsFeesDetailActivity;
import com.sh3h.meterreading.ui.urge.adapter.OrderDetailMessageAdapter;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.KeyValueBean;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailMessageFragment extends ParentFragment implements BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {
  private final String TAG = "OrderDetailMessageFragment";

  private RecyclerView mOrderDetailRv;
  private TextView mOrderDetailCheckArrearage;

  private List<KeyValueBean> keyValueBeans;
  private CuijiaoEntity mData;

  private OrderDetailMessageAdapter mAdapter;

  private Dialog mDialog;
  private String mPhoneNum;

  private final String[] keys = {"派单时间：", "站点：", "　册本号：", "　销根号：",
    "客户名称：", "用水地址：", "表卡状态：", "当前联系人：",
    "联系电话：", "欠费总笔数：", "欠费时间范围：", "用水费欠费金额：", "欠费总金额：",
    "欠费总金额（含违约金）：", "一级欠费原因：", "二级欠费原因：", "最后一次付费时间：", "最后一次付费金额：",
    "最后一次开账抄码：", "最后一次开账水量：", "最后一次开账时间：", "核对单发放次数： ", "催缴单发放次数：",
    "催缴单最后一次回填时间：", };

  private String[] values;


  public OrderDetailMessageFragment() {
    // Required empty public constructor
  }


  @Override
  protected int getLayoutId() {
    return R.layout.fragment_order_detail_message;
  }

  @Override
  protected void initView1(View view) {
    mOrderDetailRv = view.findViewById(R.id.order_detail_rv);
    mOrderDetailCheckArrearage = view.findViewById(R.id.order_detail_check_arrearage);
    mOrderDetailRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    initDialog();
  }

  private void initDialog() {
    mDialog = new Dialog(getContext());
    View dialogView = View.inflate(getContext(), R.layout.dialog_phone, null);
    mDialog.setContentView(dialogView);
    dialogView.findViewById(R.id.button_calling).setOnClickListener(this);
    dialogView.findViewById(R.id.button_choose_cancel).setOnClickListener(this);
  }

  @Override
  protected void initData1() {
    super.initData1();

    if (getArguments() == null) {
      ToastUtils.showLong(R.string.get_data_err_text);
      LogUtil.e(TAG, "initData：Bundle is null" + R.string.get_data_err_text);

      return;
    }

    mData = getArguments().getParcelable("data");
    if (mData == null) {
      ToastUtils.showLong(R.string.get_data_err_text);
      LogUtil.e(TAG, "initData：data is null" + R.string.get_data_err_text);
      return;
    }

    values = new String[]{mData.getD_PAIFARQ(), mData.getS_ST(), mData.getS_CEBENH(), mData.getS_CID(), mData.getS_HM(), mData.getS_DZ(), mData.getS_BIAOKAZT(),
      mData.getS_LIANXIR(), mData.getS_LIANXIDH(), mData.getS_QIANFEIZS(), mData.getS_QIANFEISJFW(), mData.getN_SHUIFEI(), mData.getN_QIANFEIZJE(),
      mData.getN_QIANFEIJEWYJ() + "，其中含违约金" + mData.getN_WEIYUEJ(), mData.getS_QIANFEIYY1(), mData.getS_QIANFEIYY2(), mData.getZHFFSJ(),
      mData.getZHFFJE(), mData.getZHKZCM(), mData.getZHKZSL(), mData.getZHKZSJ(), mData.getHDDFF(), mData.getCJDFF(), mData.getCJDHTSJ()};

    keyValueBeans = new ArrayList<>();

    for (int i = 0; i < keys.length; i++) {
      KeyValueBean bean = new KeyValueBean();
      bean.setKey(keys[i]);
      bean.setValue(values[i]);
      keyValueBeans.add(bean);
    }

    mAdapter = new OrderDetailMessageAdapter(keyValueBeans);
    mOrderDetailRv.setAdapter(mAdapter);
  }

  @Override
  protected void initListener1() {
    super.initListener1();
    mAdapter.setOnItemChildClickListener(this);
    mOrderDetailCheckArrearage.setOnClickListener(this);
  }

  @Override
  public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    switch (view.getId()) {
      case R.id.phoneBtn:
        mPhoneNum = values[position];
        if (!TextUtil.isNullOrEmpty(mPhoneNum)) {
          mDialog.show();
        }
        break;
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button_calling:
        callPhone();
        mDialog.dismiss();
        break;

      case R.id.button_choose_cancel:
        mDialog.dismiss();
        break;

      case R.id.order_detail_check_arrearage:
        Intent intent = new Intent(getActivity(), CallForPaymentArrearsFeesDetailActivity.class);
        intent.putExtra(Const.S_RENWUID, mData.getS_RENWUID());
        intent.putExtra(Const.S_CID, mData.getS_CID());
        getActivity().startActivity(intent);
        break;
    }
  }

  /**
   * 打电话
   */
  private void callPhone() {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:" + mPhoneNum));
    startActivity(intent);
  }

  @Override
  protected void lazyLoad() {

  }
}
