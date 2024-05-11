package com.sh3h.meterreading.ui.usage_change.model;

import com.blankj.utilcode.util.GsonUtils;
import com.example.dataprovider3.entity.UsageChangeUploadWholeEntity;
import com.example.dataprovider3.greendaoDao.UsageChangeUploadWholeEntityDao;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.reflect.TypeToken;
import com.sh3h.dataprovider.DBManager;
import com.sh3h.dataprovider.greendaoEntity.JianHao;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.usage_change.contract.UsageChangeUploadContract;
import com.sh3h.meterreading.ui.usage_change.listener.UsageChangeUploadDataListener;
import com.sh3h.serverprovider.entity.ImageItem;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsageChangeUploadModelImpl implements UsageChangeUploadContract.Model {

    @Override
    public void getCode(String type, UsageChangeUploadDataListener listener) {
        List<XJXXWordBean> wordBeans = DBManager.getInstance().getQFYYWordData(type, null);
        listener.getCode(wordBeans);
    }

    @Override
    public void getJianhaoList(UsageChangeUploadDataListener listener) {

        List<JianHao> wordBeans = DBManager.getInstance().getJianHaoList();
        listener.getJianHaoList(wordBeans);
    }

    @Override
    public void getSaveData(String s_cid, UsageChangeUploadDataListener listener) {

        List<UsageChangeUploadWholeEntity> list = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getUsageChangeUploadWholeEntityDao()
                .queryBuilder()
                .where(UsageChangeUploadWholeEntityDao.Properties.S_CID.eq(s_cid))
                .list();

        if (list != null && list.size() > 0) {
            listener.getSaveDataListener(list.get(0));
        }

    }

    @Override
    public void saveOrUpload(boolean isSave, UsageChangeUploadWholeEntity entity, UsageChangeUploadDataListener listener) {

        if (isSave) {
            GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getUsageChangeUploadWholeEntityDao()
                    .insertOrReplace(entity);
        } else {
            Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
            }.getType();
            List<ImageItem> list1 = GsonUtils.fromJson(entity.getImages1(), imageListType);

            List<File> files = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                File file = new File(list1.get(i).path);
                if (file.exists()) {
                    files.add(file);
                }
            }
        }



    }
}
