package com.sh3h.meterreading.ui.volume;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardResult;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordResult;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/2/4.
 */
public interface AdjustNumberMvpView extends MvpView {
    void onLoadTasks(List<DUTask> duTaskList);
    void onLoadCards(List<DUCard> duCardList);
    void onLoadRecords(List<DURecord> duRecordList);
    void onAdjustCards(DUCardResult duCardResult);
    void onAdjustRecords(DURecordResult duRecordResult);
    void onAdjustTask(Boolean aBoolean);
    void onAdjustVolume();
    void onError(String message);
}
