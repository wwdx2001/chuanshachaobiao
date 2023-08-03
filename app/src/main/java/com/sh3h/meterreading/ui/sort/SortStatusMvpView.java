package com.sh3h.meterreading.ui.sort;

import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by limeng on 2016/4/26.
 */
public interface SortStatusMvpView extends MvpView {
    public abstract void initRecyclerView(List<ChaoBiaoZT> duChaoBiaoZT);
    public abstract void showMessage(int resource);
    public abstract void showMessage(String message);
}
