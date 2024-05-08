package com.sh3h.meterreading.ui.usage_change.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.meterreading.R;

import java.util.List;

public class UsageChangeBasicAdapter extends BaseQuickAdapter<JianHaoMX, BaseViewHolder> {

    public UsageChangeBasicAdapter(@Nullable List<JianHaoMX> data) {
        super(R.layout.item_jibenxx, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, JianHaoMX item) {
        View itemView = helper.itemView;

        TextView tvJiBen = (TextView) itemView.findViewById(R.id.jb_txt_jieti);
        TextView tvMingCheng = (TextView) itemView.findViewById(R.id.jb_txt_feiYongMC);
        TextView tvPrice = (TextView) itemView.findViewById(R.id.jb_txt_JiaGe);
        TextView tvBegin = (TextView) itemView.findViewById(R.id.jb_txt_kaiShiSL);
        TextView tvEnd = (TextView) itemView.findViewById(R.id.jb_txt_jieShuSL);


        tvJiBen.setText(String.valueOf(item.getJieTiJB()));
        tvMingCheng.setText(item.getFeiYongMC());
        tvPrice.setText(String.valueOf(item.getJiaGe()));
        tvBegin.setText(String.valueOf(item.getKaiShiSL()));
        tvEnd.setText(String.valueOf(item.getJieShuSL()));
    }
}
