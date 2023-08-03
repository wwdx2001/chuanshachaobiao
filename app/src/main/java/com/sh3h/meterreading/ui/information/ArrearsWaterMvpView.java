package com.sh3h.meterreading.ui.information;

import com.sh3h.datautil.data.entity.DUQianFeiXX;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by liurui on 2016/2/18.
 */
public interface ArrearsWaterMvpView extends MvpView {
    void onError(String message);
    void onGetQianFeiXXs(List<DUQianFeiXX> duQianFeiXXList);
}
