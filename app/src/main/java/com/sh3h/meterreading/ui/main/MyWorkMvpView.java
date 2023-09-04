package com.sh3h.meterreading.ui.main;

import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/2/20.
 */
public interface MyWorkMvpView extends MvpView {
    void initStyle(boolean isGrid);
    void onError(String message);
    void onGetAllTasks(List<DUTask> duTasks);
    void onGetSamplingTasks(List<DUSamplingTask> duSamplingTasks);
    void onInitRegion(String region);
}
