package com.sh3h.meterreading.ui.delay;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by LiMeng on 2017/10/23.
 */

interface DelayListMvpView extends MvpView {
    void onLoadCards(List<DUCard> duCardList);
    void onLoadRecords(List<DUDelayRecord> duRecordList);
    void onGetImageSize(int size);
    void onError(String message);
}
