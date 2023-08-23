package com.sh3h.meterreading.ui.InspectionInput.lr;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.meterreading.R;
import com.sh3h.serverprovider.entity.QianFeiXXBean;

import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/12 15:03
 */
  public class QianFeiXXAdapter extends BaseQuickAdapter<QianFeiXXBean.DataBean, BaseViewHolder> {

  public QianFeiXXAdapter(int layoutResId, @Nullable List<QianFeiXXBean.DataBean> data) {
    super(layoutResId, data);
  }

  @Override
  protected void convert(@NonNull BaseViewHolder helper, QianFeiXXBean.DataBean item) {
    helper.setText(R.id.tv_zdny, item.getZHANGDANNY());
    helper.setText(R.id.tv_zdje, String.valueOf(item.getYSJE()));
    helper.setText(R.id.tv_weiyuejin, String.valueOf(item.getWEIYUEJ()));
    helper.setText(R.id.tv_shuiliang, String.valueOf(item.getSHUIL()));
    helper.setText(R.id.tv_yongshuifei, String.valueOf(item.getSHUIFEI()));
    helper.setText(R.id.tv_paishuifei, String.valueOf(item.getPAISHUIF()));
  }
}
