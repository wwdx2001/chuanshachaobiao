package com.sh3h.meterreading.ui.urge.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.sh3h.meterreading.R;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class CallForPaymentTaskAdapter extends BaseQuickAdapter<CallForPaymentTaskBean, BaseViewHolder> {

  public CallForPaymentTaskAdapter(@Nullable List<CallForPaymentTaskBean> data) {
    super(R.layout.item_call_for_pay_task, data);
  }

  @SuppressLint("SetTextI18n")
  @Override
  protected void convert(@NonNull BaseViewHolder helper, CallForPaymentTaskBean item) {
    View itemView = helper.itemView;
    TextView mItemCallForPayTaskChTv = itemView.findViewById(R.id.item_call_for_pay_task_ch_tv);
    TextView mItemCallForPayTaskTotalTv = itemView.findViewById(R.id.item_call_for_pay_task_total_tv);
    TextView mItemCallForPayTaskCompleteTv = itemView.findViewById(R.id.item_call_for_pay_task_complete_tv);
    TextView mItemCallForPayTaskIncompleteTv = itemView.findViewById(R.id.item_call_for_pay_task_incomplete_tv);
    TextView mItemCallForPayTaskDateTv = itemView.findViewById(R.id.item_call_for_pay_task_date_tv);

    mItemCallForPayTaskChTv.setText("册本号：" + item.getS_CEBENH());
    mItemCallForPayTaskTotalTv.setText("工单总数：" + item.getHUSHU());
    mItemCallForPayTaskCompleteTv.setText("已完成数：" + item.getYIWANC());
    mItemCallForPayTaskIncompleteTv.setText("未完成数：" + item.getWEIWANC());
    mItemCallForPayTaskDateTv.setText("派单日期：" + item.getD_PAIFARQ());

  }

}
