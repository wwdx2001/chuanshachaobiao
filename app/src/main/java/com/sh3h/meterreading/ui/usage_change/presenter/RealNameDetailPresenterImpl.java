package com.sh3h.meterreading.ui.usage_change.presenter;

import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.sh3h.meterreading.ui.base.BasePresenter;
import com.sh3h.meterreading.ui.usage_change.contract.RealNameDetailContract;
import com.sh3h.meterreading.ui.usage_change.listener.RealNameDetailDataListener;
import com.sh3h.meterreading.ui.usage_change.model.RealNameDetailModelImpl;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.ArrayList;
import java.util.List;

public class RealNameDetailPresenterImpl extends BasePresenter<RealNameDetailContract.View>
        implements RealNameDetailContract.Presenter, RealNameDetailDataListener {

    private RealNameDetailContract.View view;
    private RealNameDetailContract.Model model;

    public RealNameDetailPresenterImpl(RealNameDetailContract.View view) {
        this.view = view;
        this.model = new RealNameDetailModelImpl();
    }

    @Override
    public void getUserType(String type) {
        model.getUserType(type, this);
    }

    @Override
    public void getSaveData(String s_cid) {
        model.getSaveData(s_cid, this);
    }

    @Override
    public void saveOrUpload(boolean isSave, RealNameWholeEntity entity) {
        model.saveOrUpload(isSave, entity, this);
    }

    @Override
    public void getUserTypeListener(List<XJXXWordBean> beans) {
        List<String> list = new ArrayList<>();
        for (XJXXWordBean bean : beans) {
            list.add(bean.getMVALUE() + "-" + bean.getMNAME());
        }
        view.getUserType(list);
    }

    @Override
    public void getSaveDataListener(RealNameWholeEntity bean) {
        view.getSaveData(bean);
    }

    @Override
    public void uploadSuccess(String s) {
        view.uploadSuccess(s);
    }

    @Override
    public void getResult(String msgInfo) {
        view.result(msgInfo);
    }

    @Override
    public void onFail(String result) {

    }

    @Override
    public void onError(Exception e) {

    }
}
