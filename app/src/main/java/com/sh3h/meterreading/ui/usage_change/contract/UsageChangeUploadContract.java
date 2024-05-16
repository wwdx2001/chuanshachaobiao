package com.sh3h.meterreading.ui.usage_change.contract;

import com.example.dataprovider3.entity.UsageChangeUploadWholeEntity;
import com.sh3h.meterreading.ui.base.BaseView;
import com.sh3h.meterreading.ui.base.MvpView;
import com.sh3h.meterreading.ui.usage_change.listener.UsageChangeUploadDataListener;

import java.util.List;

public interface UsageChangeUploadContract {

    interface Model {
        void getJianhaoList(UsageChangeUploadDataListener listener);
        void getCode(String type, UsageChangeUploadDataListener listener);
        void getSaveData(String s_cid, UsageChangeUploadDataListener listener);
        void saveOrUpload(boolean isSave, UsageChangeUploadWholeEntity entity, UsageChangeUploadDataListener listener);
    }

    interface Presenter {
        void getJianhaoList();
        void getCode(String type);
        void getSaveData(String s_cid);
        void saveOrUpload(boolean isSave, UsageChangeUploadWholeEntity entity);
    }

    interface View extends BaseView, MvpView {
        void getJianhaoList(List<String> list);
        void getCode(List<String> strings);
        void getSaveData(UsageChangeUploadWholeEntity entity);
        void uploadSuccess(String s);
        void result(String s);
    }

}
