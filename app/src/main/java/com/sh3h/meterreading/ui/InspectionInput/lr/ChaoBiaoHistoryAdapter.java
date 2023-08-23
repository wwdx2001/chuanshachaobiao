package com.sh3h.meterreading.ui.InspectionInput.lr;





import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.meterreading.R;
import com.sh3h.serverprovider.entity.ChaoBiaoHistoryBean;

import java.util.List;

/**
 * 历史抄表
 *
 * @author Administrator
 */
public class ChaoBiaoHistoryAdapter
  extends BaseQuickAdapter<ChaoBiaoHistoryBean.DataBean, BaseViewHolder>
{
    public ChaoBiaoHistoryAdapter(int layoutResId, @Nullable List<ChaoBiaoHistoryBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ChaoBiaoHistoryBean.DataBean item) {
        helper.setText(R.id.tv_zhuangtai, item.getCHAOBIAOZTMC());
        helper.setText(R.id.tv_yongliang, String.valueOf(item.getYONGSHUIL()));
        helper.setText(R.id.tv_nianyue, item.getCHAOBIAONY());
        helper.setText(R.id.tv_chaoma, String.valueOf(item.getCHAOMA()));
        helper.setText(R.id.tv_beizhu, item.getBEIZHU());
        helper.addOnClickListener(R.id.picture_preview);
    }
}
