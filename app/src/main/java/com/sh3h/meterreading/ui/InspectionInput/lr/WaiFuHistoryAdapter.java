package com.sh3h.meterreading.ui.InspectionInput.lr;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.meterreading.R;
import com.sh3h.serverprovider.entity.WaiFuHistoryBean;

import java.util.List;

/**
 * 历史外复
 *
 * @author Administrator
 */
public class WaiFuHistoryAdapter extends BaseQuickAdapter<WaiFuHistoryBean.DataBean, BaseViewHolder> {
    public WaiFuHistoryAdapter(int layoutResId, @Nullable List<WaiFuHistoryBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WaiFuHistoryBean.DataBean item) {
        helper.setText(R.id.tv_data, item.getWAIFURQ());
        helper.setText(R.id.tv_type, item.getLEIXING());
        helper.setText(R.id.tv_chulijg, item.getWAIFUJG());
        helper.setText(R.id.tv_reason, item.getWAIFUYY());
    }
}
