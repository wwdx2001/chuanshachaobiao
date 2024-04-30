package com.sh3h.meterreading.ui.urge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.urge.CallForPaymentArrearsFeesDetailActivity;
import com.sh3h.meterreading.util.Const;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class CallForPaymentWordOrderAdapter extends BaseQuickAdapter<CuijiaoEntity, BaseViewHolder> {

  private Context mContext;
  private List<CuijiaoEntity> mData;

  public CallForPaymentWordOrderAdapter(@Nullable List<CuijiaoEntity> data, Context context) {
    super(R.layout.item_call_for_pay_word_order, data);
    this.mData = data;
    this.mContext = context;
  }

  @SuppressLint("SetTextI18n")
  @Override
  protected void convert(@NonNull BaseViewHolder helper, CuijiaoEntity item) {
    View itemView = helper.itemView;

    TextView mItemCallForPayWorkOrderXghTv = itemView.findViewById(R.id.item_call_for_pay_work_order_xgh_tv);
    TextView mItemCallForPayWorkOrderDispatchDateTv = itemView.findViewById(R.id.item_call_for_pay_work_order_dispatch_date_tv);
    TextView mItemCallForPayWordOrderUserNameTv = itemView.findViewById(R.id.item_call_for_pay_word_order_user_name_tv);
    TextView mItemCallForPayWordOrderAddressTv = itemView.findViewById(R.id.item_call_for_pay_word_order_address_tv);
    TextView mItemCallForPayWordOrderArrearsFeesTotalTv = itemView.findViewById(R.id.item_call_for_pay_word_order_arrears_fees_total_tv);
    TextView mItemCallForPayWordOrderArrearsFeesMoneyTv = itemView.findViewById(R.id.item_call_for_pay_word_order_arrears_fees_money_tv);
    TextView mItemCallForPayWordOrderArrearsFeesTotalMoneyTv = itemView.findViewById(R.id.item_call_for_pay_word_order_arrears_fees_total_money_tv);
    TextView mItemCallForPayWordOrderArrearsFeesDateTv = itemView.findViewById(R.id.item_call_for_pay_word_order_arrears_fees_date_tv);
    TextView mItemCallForPayWordOrderArrearsFeesTotalMoneyLiquidatedTv = itemView.findViewById(R.id.item_call_for_pay_word_order_arrears_fees_total_money_liquidated_tv);

    mItemCallForPayWorkOrderXghTv.setText(item.getROWNUM() + "/" + item.getS_CID());
    mItemCallForPayWorkOrderDispatchDateTv.setText("派单时间：" + item.getD_PAIFARQ());
    mItemCallForPayWordOrderUserNameTv.setText("客户名称：" + item.getS_HM());
    mItemCallForPayWordOrderAddressTv.setText("用水地址：" + item.getS_DZ());
    mItemCallForPayWordOrderArrearsFeesTotalTv.setText("欠费总笔数：" + item.getI_QIANFEIZS());
    mItemCallForPayWordOrderArrearsFeesMoneyTv.setText("用水费欠费金额：" + item.getN_SHUIFEI());
    mItemCallForPayWordOrderArrearsFeesTotalMoneyTv.setText("欠费总金额：" + item.getN_QIANFEIZJE());
    mItemCallForPayWordOrderArrearsFeesDateTv.setText("欠费时间范围：" + item.getS_QIANFEISJFW());
    mItemCallForPayWordOrderArrearsFeesTotalMoneyLiquidatedTv.setText("欠费总金额（含违约金）：" + item.getN_QIANFEIJEWYJ());

    helper.addOnClickListener(R.id.tv_delay);
    helper.addOnClickListener(R.id.tv_back);

    setTextPartColor(mItemCallForPayWordOrderArrearsFeesMoneyTv, helper);
    setTextPartColor(mItemCallForPayWordOrderArrearsFeesTotalMoneyTv, helper);
    setTextPartColor(mItemCallForPayWordOrderArrearsFeesTotalMoneyLiquidatedTv, helper);
  }

  /**
   * 设置Text的部分颜色并且增加点击事件
   * @param textView
   * @param helper
   */
  private void setTextPartColor(TextView textView, final BaseViewHolder helper) {
    String text = textView.getText().toString();
    int indexStart = text.indexOf("：");

    SpannableStringBuilder spannableStr = new SpannableStringBuilder(text);
    spannableStr.setSpan(new ClickableSpan(){
      @Override
      public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.RED);

      }

      @Override
      public void onClick(@NonNull View widget) {
        Intent intent = new Intent(mContext, CallForPaymentArrearsFeesDetailActivity.class);
        intent.putExtra(Const.S_RENWUID, mData.get(helper.getLayoutPosition()).getS_RENWUID());
        intent.putExtra(Const.S_CID, mData.get(helper.getLayoutPosition()).getS_CID());
        mContext.startActivity(intent);
      }
    }, indexStart + 1, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setText(spannableStr);
  }
}
