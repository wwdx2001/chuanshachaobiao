package com.sh3h.meterreading.ui.forceImage;

import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by LiMeng on 2017/9/25.
 */

public interface ForceImageMvpView extends MvpView{
    void loadAllChaobiaoZTFL(List<DUChaoBiaoZTFL> duChaoBiaoZTFLs);
    void loadAllChaobiaozt(List<DUChaoBiaoZT> duChaoBiaoZTs);
    void onError(String message);
    void showMessage(String message);
    void showMessage(int message);
}
