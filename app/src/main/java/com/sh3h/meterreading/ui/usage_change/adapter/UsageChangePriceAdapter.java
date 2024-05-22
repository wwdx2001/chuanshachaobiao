package com.sh3h.meterreading.ui.usage_change.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dataprovider3.entity.UsageChangeInfoPriceEntity;
import com.sh3h.meterreading.R;

import java.util.List;

public class UsageChangePriceAdapter extends BaseQuickAdapter<UsageChangeInfoPriceEntity, BaseViewHolder> {

    public UsageChangePriceAdapter(@Nullable List<UsageChangeInfoPriceEntity> data) {
        super(R.layout.item_jibenxx, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UsageChangeInfoPriceEntity item) {
        View itemView = helper.itemView;

        TextView tvJiBen = (TextView) itemView.findViewById(R.id.jb_txt_jieti);
        TextView tvMingCheng = (TextView) itemView.findViewById(R.id.jb_txt_feiYongMC);
        TextView tvPrice = (TextView) itemView.findViewById(R.id.jb_txt_JiaGe);
        TextView tvBegin = (TextView) itemView.findViewById(R.id.jb_txt_kaiShiSL);
        TextView tvEnd = (TextView) itemView.findViewById(R.id.jb_txt_jieShuSL);


        tvJiBen.setText(String.valueOf(item.getI_JIETISX()));
        tvMingCheng.setText(item.getS_FEIYONGMC());
        tvPrice.setText(String.valueOf(item.getN_JIAGE()));
        tvBegin.setText(String.valueOf(item.getI_KAISHISL()));
        tvEnd.setText(String.valueOf(item.getI_JIESHUSL()));
    }
}
