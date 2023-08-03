package com.sh3h.meterreading.ui.lgld;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DULgld;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by LiMeng on 2017/10/30.
 */
interface LgldListMvpView extends MvpView{
    void onLoadCards(List<DUCard> duCardList);
    void onLoadRecords(List<DULgld> duLglds);
    void onError(String message);
}
