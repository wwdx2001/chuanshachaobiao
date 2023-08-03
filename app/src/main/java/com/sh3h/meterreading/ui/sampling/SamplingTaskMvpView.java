package com.sh3h.meterreading.ui.sampling;


import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface SamplingTaskMvpView extends MvpView {
    void onLoadSamplingTasks(List<DUSamplingTask> duSamplingTasks);

    void onGetNextReadingRecord(DUSamplingTask duSamplingTask,
                                DUSampling duSampling,
                                int startOrderNumber,
                                int endOrderNumber,
                                String startCID,
                                String endCID,
                                ArrayList<String> CIDS);

    void onDeleteSamplingTask(Boolean aBoolean);

    void onError(String message);

    void onIsTaskContainingFullData(Boolean aBoolean);
}
