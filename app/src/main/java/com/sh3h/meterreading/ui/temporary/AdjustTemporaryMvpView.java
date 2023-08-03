package com.sh3h.meterreading.ui.temporary;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/2/22.
 */
public interface AdjustTemporaryMvpView extends MvpView {
    void onLoadTasks(List<DUTask> duTaskList);
    void onLoadCards(List<DUCard> duCardList);
    void onLoadRecords(List<DURecord> duRecordList);
    void onLoadDestinationCards(List<DUCard> duCardList);
    void onAdjustCardAndRecords(Boolean aBoolean);
    void onError(String message);
}
