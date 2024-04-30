package com.sh3h.meterreading.util;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2021/12/20 11:31
 */
public interface TimerListener {
    void onStart();
    void onTick(long millLong);
    void onFinish();
}
