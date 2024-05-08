package com.sh3h.meterreading.ui.usage_change.contract;

import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

public interface UsageChangeContract {

    interface SearchListModel {

    }

    interface Presenter {
        void getSearchListData(String s_cid, String address);
        void getBasicData();
    }

    interface View extends BaseView, MvpView {
        void onGetJianHaoMXFinish(List<JianHaoMX> duCard);
        void searchDataNotify(List<DUCard> duCard);
    }

}
