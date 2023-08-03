package com.sh3h.meterreading.ui.volume;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by zhangzhe on 2016/2/1.
 */
public interface VolumeListMvpView extends MvpView {
    void onLoadRecords(List<DURecord> duRecordList);
    void onLoadCards(List<DUCard> duCardList);
    void onError(String message);
    void onCheckForUpdatingCard(boolean canUpdate);
}
