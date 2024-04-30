package com.sh3h.meterreading.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  @SuppressLint("SimpleDateFormat")
  public static String getCurrentTime() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());
    return simpleDateFormat.format(date);
  }

}
