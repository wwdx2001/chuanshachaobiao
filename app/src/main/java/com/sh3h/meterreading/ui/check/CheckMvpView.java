package com.sh3h.meterreading.ui.check;

import com.sh3h.meterreading.ui.base.MvpView;

/**
 * Created by liurui on 2016/2/17.
 */
public interface CheckMvpView  extends MvpView {
    void onError(String message);
}
