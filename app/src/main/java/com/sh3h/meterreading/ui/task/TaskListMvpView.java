package com.sh3h.meterreading.ui.task;


import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.TraceInfo;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.util.TransformUtil;

import java.util.List;

public interface TaskListMvpView extends MvpView {
    void onLoadTasks(List<DUTask> duTaskList);
    void onGetNextReadingRecord(DUTask duTask, DURecord duRecord, int startOrderNumber, int endOrderNumber);
    void onDeleteTask(Boolean aBoolean);
    void onError(String message);
    void onIsTaskContainingFullData(Boolean aBoolean);
    void onLoadCards(List<DUCard> duCardList);
    void onLoadTrance(List<TraceInfo> list);
}
