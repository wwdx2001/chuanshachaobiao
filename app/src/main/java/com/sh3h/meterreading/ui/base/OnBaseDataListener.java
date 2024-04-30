package com.sh3h.meterreading.ui.base;

/**
 * model与presenter之间回调接口基类
 */
public interface OnBaseDataListener {

    void onFail(String result);

    void onError(Exception e);
}
