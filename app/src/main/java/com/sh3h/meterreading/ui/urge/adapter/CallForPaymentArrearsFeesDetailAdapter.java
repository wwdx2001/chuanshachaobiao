package com.sh3h.meterreading.ui.urge.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.meterreading.R;

import java.util.List;

public class CallForPaymentArrearsFeesDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

  public CallForPaymentArrearsFeesDetailAdapter(@Nullable List<String> data) {
    super(R.layout.activity_call_for_payment_arrears_fees_detail, data);
  }

  @Override
  protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
