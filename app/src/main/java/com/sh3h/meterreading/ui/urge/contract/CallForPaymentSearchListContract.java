package com.sh3h.meterreading.ui.urge.contract;

import com.example.dataprovider3.entity.CallForPaymentSearchBean;
import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.urge.listener.OnSearchListListener;

import java.util.List;

public interface CallForPaymentSearchListContract {

    interface Model {
        void getData(String cid, String address, OnSearchListListener listener);
        void submitData(List<CallForPaymentSearchBean> bean, OnSearchListListener listener);
    }

    interface Presenter {
        void getSearchListData(String cid, String address);
        void submitData(List<CallForPaymentSearchBean> bean);
    }

    interface View extends MvpView, BaseView {

    }

}
