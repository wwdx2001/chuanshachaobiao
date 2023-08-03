package com.sh3h.meterreading.ui.setting;

import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/2/25.
 */
public interface UserCommonMvpView extends MvpView {
    void loadAllChaobiaoZTFL(List<DUChaoBiaoZTFL> duChaoBiaoZTFLs);
    void loadAllChaobiaozt(List<DUChaoBiaoZT> duChaoBiaoZTs);
    void onError(String message);
    void showMessage(String message);
    void showMessage(int message);
}
