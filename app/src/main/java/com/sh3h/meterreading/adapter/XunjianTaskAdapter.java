package com.sh3h.meterreading.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sh3h.meterreading.R;
import com.sh3h.serverprovider.entity.XunJianTaskBean;

import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/11 16:14
 */
public class XunjianTaskAdapter extends BaseQuickAdapter<XunJianTaskBean, BaseViewHolder> {
    public XunjianTaskAdapter(int layoutResId, @Nullable List<XunJianTaskBean> data) {
        super(layoutResId, data);
    }

    private boolean isHistory;

    public void setIsHistory(boolean isHistory) {
        this.isHistory = isHistory;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, XunJianTaskBean item) {
        helper.setText(R.id.tv_task_name, item.getRENWUMC());
        int total = item.getZONGHS();
        int finish = item.getYIWANC();
//        helper.setText(R.id.tv_zonghushu, String.valueOf(item.getZONGHS()));
        helper.setText(R.id.tv_zonghushu, String.valueOf(total));
        helper.setText(R.id.tv_paifashijian, item.getPAIFASJ());
//        helper.setText(R.id.tv_yiwancheng, String.valueOf(item.getYIWANC()));
        helper.setText(R.id.tv_yiwancheng, String.valueOf(finish));

//        String xjlx = item.getXJLX();
//        if (xjlx != null) {
//            if (!"".equals(xjlx)) {
//                List<XJXXWordBean> wordBeanList = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                        .getXJXXWordBeanDao().queryBuilder()
//                        .where(XJXXWordBeanDao.Properties.MMODULE.eq("巡检类型"))
//                        .where(XJXXWordBeanDao.Properties.MVALUE.eq(item.getXJLX()))
//                        .list();
//                if (wordBeanList != null && wordBeanList.size() > 0) {
//                    xjlx = wordBeanList.get(0).getMNAME();
//                }
//            }
//        }
        helper.setText(R.id.tv_renwulx, "远传巡检");
        if (isHistory) {
            helper.setGone(R.id.tv8, true);
            helper.setGone(R.id.tv_yidengji, true);
            helper.setText(R.id.tv_yidengji, String.valueOf(item.getYIDENGJ()));
        }

    }
}
