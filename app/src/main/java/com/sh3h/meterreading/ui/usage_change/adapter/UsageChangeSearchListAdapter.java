package com.sh3h.meterreading.ui.usage_change.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.meterreading.R;

import java.util.List;

public class UsageChangeSearchListAdapter extends BaseQuickAdapter<DUCard, BaseViewHolder> {
    public UsageChangeSearchListAdapter(@Nullable List<DUCard> data) {
        super(R.layout.item_usage_change_search_list_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DUCard item) {
        View itemView = helper.itemView;

        TextView itemUsageChangeSearchListZdTv = itemView.findViewById(R.id.item_usage_change_search_list_zd_tv);
        TextView itemUsageChangeSearchListCbTv = itemView.findViewById(R.id.item_usage_change_search_list_cb_tv);
        TextView itemUsageChangeSearchListYhhTv = itemView.findViewById(R.id.item_usage_change_search_list_yhh_tv);
        TextView itemUsageChangeSearchListAddressTv = itemView.findViewById(R.id.item_usage_change_search_list_address_tv);

        itemUsageChangeSearchListZdTv.setText(item.getSt());
        itemUsageChangeSearchListCbTv.setText(item.getCh());
        itemUsageChangeSearchListYhhTv.setText(item.getCid());
        itemUsageChangeSearchListAddressTv.setText(item.getDizhi());
    }
}
