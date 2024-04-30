package com.sh3h.meterreading.ui.urge.adapter;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.meterreading.R;
import com.sh3h.serverprovider.entity.KeyValueBean;

import java.util.List;

public class OrderDetailMessageAdapter extends BaseQuickAdapter<KeyValueBean, BaseViewHolder> {
  public OrderDetailMessageAdapter(@Nullable List<KeyValueBean> data) {
    super(R.layout.item_key_value_new, data);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void convert(@NonNull BaseViewHolder helper, KeyValueBean item) {
    TextView keyText = helper.itemView.findViewById(R.id.tv_key);
    TextView valueText = helper.itemView.findViewById(R.id.tv_value);

    keyText.setText(item.getKey());
    valueText.setText(item.getValue());

    if (item.getKey().equals("联系电话：")) {
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      params.setMarginEnd(15);
      ImageView phoneBtn = new ImageView(mContext);
      phoneBtn.setId(R.id.phoneBtn);
      phoneBtn.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_details_call));
      phoneBtn.setBackgroundColor(Color.TRANSPARENT);
      LinearLayout ll = helper.itemView.findViewById(R.id.ll);
      ll.addView(phoneBtn, params);
      helper.addOnClickListener(R.id.phoneBtn);
    }
  }
}
