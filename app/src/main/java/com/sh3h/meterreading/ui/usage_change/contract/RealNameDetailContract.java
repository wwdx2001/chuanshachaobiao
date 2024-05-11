package com.sh3h.meterreading.ui.usage_change.contract;

import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.usage_change.listener.RealNameDetailDataListener;

import java.util.List;

public interface RealNameDetailContract {

    interface Model {
        void getUserType(String type, RealNameDetailDataListener listener);
        void getSaveData(String s_cid, RealNameDetailDataListener listener);
        void saveOrUpload(boolean isSave, RealNameWholeEntity entity, RealNameDetailDataListener listener);
    }

    interface Presenter {
        void getUserType(String type);
        void getSaveData(String s_cid);
        void saveOrUpload(boolean isSave, RealNameWholeEntity entity);
    }

    interface View extends BaseView, MvpView {
        void getUserType(List<String> strings);
        void getSaveData(RealNameWholeEntity bean);
        void uploadSuccess(String s);
    }

}
