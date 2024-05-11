package com.sh3h.meterreading.ui.usage_change.presenter;

import com.example.dataprovider3.entity.UsageChangeUploadWholeEntity;
import com.sh3h.dataprovider.greendaoEntity.JianHao;
import com.sh3h.meterreading.ui.base.BasePresenter;
import com.sh3h.meterreading.ui.usage_change.contract.UsageChangeUploadContract;
import com.sh3h.meterreading.ui.usage_change.listener.UsageChangeUploadDataListener;
import com.sh3h.meterreading.ui.usage_change.model.UsageChangeUploadModelImpl;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.ArrayList;
import java.util.List;

public class UsageChangeUploadPresenterImpl extends BasePresenter<UsageChangeUploadContract.View>
        implements UsageChangeUploadContract.Presenter, UsageChangeUploadDataListener {

    private UsageChangeUploadContract.View view;
    private UsageChangeUploadContract.Model model;

    public UsageChangeUploadPresenterImpl(UsageChangeUploadContract.View view) {
        this.view = view;
        this.model = new UsageChangeUploadModelImpl();
    }

    @Override
    public void getJianhaoList() {
        model.getJianhaoList(this);
    }

    @Override
    public void getCode(String type) {

    }

    @Override
    public void getSaveData(String s_cid) {
        model.getSaveData(s_cid, this);
    }


    @Override
    public void saveOrUpload(boolean isSave, UsageChangeUploadWholeEntity entity) {
        model.saveOrUpload(isSave, entity, this);
    }


    @Override
    public void getSaveDataListener(UsageChangeUploadWholeEntity bean) {
        view.getSaveData(bean);
    }

    @Override
    public void getCode(List<XJXXWordBean> beans) {
        List<String> list = new ArrayList<>();
        for (XJXXWordBean bean : beans) {
            list.add(bean.getMNAME());
        }
        view.getCode(list);
    }


    @Override
    public void getJianHaoList(List<JianHao> list) {
        List<String> strings = new ArrayList<>();
        for (JianHao jianHao : list) {
            strings.add(jianHao.getS_ZHONGLEI());
        }
        view.getJianhaoList(strings);
    }

    @Override
    public void uploadSuccess(String s) {
        view.uploadSuccess(s);
    }

    @Override
    public void onFail(String result) {

    }

    @Override
    public void onError(Exception e) {

    }
}
