package com.sh3h.meterreading.ui.usage_change.model;

import com.blankj.utilcode.util.GsonUtils;
import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.example.dataprovider3.greendaoDao.RealNameWholeEntityDao;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.reflect.TypeToken;
import com.sh3h.dataprovider.DBManager;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.usage_change.contract.RealNameDetailContract;
import com.sh3h.meterreading.ui.usage_change.listener.RealNameDetailDataListener;
import com.sh3h.serverprovider.entity.ImageItem;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RealNameDetailModelImpl implements RealNameDetailContract.Model {

    @Override
    public void getUserType(String type, RealNameDetailDataListener listener) {
        List<XJXXWordBean> wordBeans = DBManager.getInstance().getQFYYWordData(type, null);
        listener.getUserTypeListener(wordBeans);
    }

    @Override
    public void getSaveData(String s_cid, RealNameDetailDataListener listener) {

        List<RealNameWholeEntity> list = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getRealNameWholeEntityDao()
                .queryBuilder()
                .where(RealNameWholeEntityDao.Properties.S_CID.eq(s_cid))
                .list();

        if (list != null && list.size() > 0) {
            listener.getSaveDataListener(list.get(0));
        }
    }

    @Override
    public void saveOrUpload(boolean isSave, RealNameWholeEntity entity, RealNameDetailDataListener listener) {

        if (isSave) {
            GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getRealNameWholeEntityDao()
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
