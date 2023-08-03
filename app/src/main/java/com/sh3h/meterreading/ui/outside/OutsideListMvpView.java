package com.sh3h.meterreading.ui.outside;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/4/15.
 */
public interface OutsideListMvpView extends MvpView {
    void loadWaiFuCBSJS(List<DUWaiFuCBSJ> duWaiFuCBSJs);
    void onLoadCards(List<DUCard> duCardList);
    void onError(String message);
}
