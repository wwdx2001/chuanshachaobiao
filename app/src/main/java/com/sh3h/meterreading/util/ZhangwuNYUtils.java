package com.sh3h.meterreading.util;

import com.example.dataprovider3.entity.DUBillServiceInfoResultBean;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/3/22 13:13
 */
public class ZhangwuNYUtils {

    private static final String TAG = "ZhangwuNYUtils";

    public static int getCurrentZhagnwuNY() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;

        int date = c.get(Calendar.DATE);
        LogUtil.e("test", "year=" + year + "   month=" + month + "   date=" + date);
        /**
         * TODO 2018.8.1 libao 修改
         */
        if (date > 28) {
            if (month == 12) {
                year += 1;
                month = 1;
            } else {
                month += 1;
            }
        }
        int zhangWuNY = year * 100 + month;
        return zhangWuNY;
    }

    /**
     * 根据账务年月判断是否超期，需要删除
     *
     * @param list
     */
    public static List<DUBillServiceInfoResultBean> isOverdue(List<DUBillServiceInfoResultBean> list) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;

        int date = c.get(Calendar.DATE);
        LogUtil.e("test", "year=" + year + "   month=" + month + "   date=" + date);
        /**
         * TODO 2018.8.1 libao 修改
         */
        if (date > 28) {
            if (month == 12) {
                year += 1;
                month = 1;
            } else {
                month += 1;
            }
        }
        int zhangWuNY = year * 100 + month;
//        StringBuilder sb = new StringBuilder();
//        sb.append("|");
        List<DUBillServiceInfoResultBean> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getI_ZHANGWUNY() < zhangWuNY) {
                newList.add(list.get(i));
            } else {
            }
        }

//        LogUtil.e(TAG, " 账务年月：" + list.get(0).getI_ZHANGWUNY() + "    当前年月：" + zhangWuNY);
//        sb.append("\n");
//        sb.append(MainApplication.getInstance().getString(R.string.text_zhangwuny_time_error));
//        ApplicationsUtil.showMessage(MainApplication.getInstance(), sb.toString());
        return newList;
    }

}
