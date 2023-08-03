package com.sh3h.meterreading.ui.rushpay;


import com.sh3h.datautil.data.entity.DURushPayTask;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

public interface RushPayTaskMvpView extends MvpView {
    void onLoadRushPayTasks(List<DURushPayTask> duRushPayTaskList);

    void onDeleteRushPayTask(Boolean aBoolean);

    void onError(String message);
}
