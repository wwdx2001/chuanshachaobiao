package com.sh3h.meterreading.ui.urge.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dataprovider3.entity.CallForPaymentBackFillBean;
import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.custom_view.SelectSpinnerView;

import java.util.List;

public class CallForPaymentBackFillAdapter extends BaseQuickAdapter<CallForPaymentBackFillBean, BaseViewHolder> {
  private RecyclerView mRecyclerView;
  private CallForPaymentBackFillBean item1 = null;
  private CallForPaymentBackFillDataBean mDataBean;

  public CallForPaymentBackFillAdapter(@Nullable List<CallForPaymentBackFillBean> data) {
    super(R.layout.item_call_for_ay_back_fill, data);
  }

  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    this.mRecyclerView = recyclerView;
  }

  @Override
  public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
    this.mRecyclerView = null;
  }

  @Override
  protected void convert(@NonNull BaseViewHolder helper, CallForPaymentBackFillBean item) {
    TextView tvKey = helper.itemView.findViewById(R.id.tv_key);
    SelectSpinnerView spinnerValue = helper.itemView.findViewById(R.id.spinner_value);

    tvKey.setText(item.getKey());
    setValue(spinnerValue, item, 0);
//    helper.addOnClickListener(R.id.spinner_value);
    if (tvKey.getText().toString().equals("2级欠费原因：")) {
      item1 = item;
      spinnerValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
      });
    } else if (tvKey.getText().toString().equals("1级欠费原因：")) {
      spinnerValue.setOnItemSelectedListener(null);

//      spinnerValue.setItemClick(position -> {
//        SelectSpinnerView spinner = (SelectSpinnerView) getViewByPosition(mRecyclerView, 1, R.id.spinner_value);
//          setValue(spinner, item1, position);
//      });

      spinnerValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          Log.i(TAG, "onItemSelected: " + position);
          SelectSpinnerView spinner = (SelectSpinnerView) getViewByPosition(mRecyclerView, 1, R.id.spinner_value);
          setValue(spinner, item1, position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
      });
    } else {
      spinnerValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          Log.i(TAG, "onItemSelected: " + position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
      });
    }
  }

  private void setValue(SelectSpinnerView spinnerValue, CallForPaymentBackFillBean item, int i) {
    if (item == null) {
      return;
    }

    List<String> strings;
    if (item.getStrings().size() <= i) {
      strings = item.getStrings().get(0);
    } else {
      strings = item.getStrings().get(i);
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, strings);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerValue.setAdapter(adapter);
  }

  protected CallForPaymentBackFillDataBean getDataBean() {
    return mDataBean;
  }
}
