package com.sh3h.meterreading.ui.search;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/2/2.
 */
public interface CombinedSearchResultMvpView extends MvpView {
    void onCompleted(List<DUCard> duCards);
    void onError(String message);
}