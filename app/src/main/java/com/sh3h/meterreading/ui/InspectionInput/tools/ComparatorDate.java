package com.sh3h.meterreading.ui.InspectionInput.tools;

import android.annotation.SuppressLint;

import com.sh3h.serverprovider.entity.XunJianTaskBean;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ComparatorDate implements Comparator {

  public static final String TAG = "ComparatorDate";

  @SuppressLint("SimpleDateFormat")
  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public int compare(Object obj1, Object obj2) {
    XunJianTaskBean t1 = (XunJianTaskBean) obj1;
    XunJianTaskBean t2 = (XunJianTaskBean) obj2;
//        //   return t1.getTradetime().compareTo(t2.getTradetime());  // 时间格式不好，不然可以直接这样比较
//        Date d1, d2;
//        try {
//            d1 = format.parse(t1.getDENGJIRQ());
//            d2 = format.parse(t2.getDENGJIRQ());
//        } catch (ParseException e) {
//            // 解析出错，则不进行排序
//            LogUtils.e(TAG, "ComparatorDate--compare--SimpleDateFormat.parse--error");
//            return 0;
//        }
//        if (d1.before(d2)) {
//            return 1;
//        } else {
//            return -1;
//        }
    try {
      Date dt1 = format.parse(t1.getPAIFASJ());
      Date dt2 = format.parse(t2.getPAIFASJ());
      if (dt1.getTime() > dt2.getTime()) {
        return -1;
      } else if (dt1.getTime() < dt2.getTime()) {
        return 1;
      } else {
        return 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }
}
