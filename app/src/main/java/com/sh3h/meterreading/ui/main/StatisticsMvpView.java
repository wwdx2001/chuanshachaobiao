package com.sh3h.meterreading.ui.main;

import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by liurui on 2016/2/19.
 */
public interface StatisticsMvpView extends MvpView {
    void onError(String message);
    void onGetTask(DUTask duTask);
    void onGetAllTasks(List<DUTask> duTaskList);
    void onGetChaoJianS(Integer integer);
}
