package com.sh3h.meterreading.ui.sampling;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/11/24.
 */
public interface SamplingListMvpView extends MvpView {
    void onLoadRecords(List<DUSampling> duSamplingList);
    void onLoadCards(List<DUCard> duCardList);
    void onError(String message);
}
