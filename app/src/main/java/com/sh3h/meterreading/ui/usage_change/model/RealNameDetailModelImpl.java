package com.sh3h.meterreading.ui.usage_change.model;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.example.dataprovider3.greendaoDao.RealNameWholeEntityDao;
import com.example.dataprovider3.utils.GreenDaoUtils;
import com.google.gson.reflect.TypeToken;
import com.sh3h.dataprovider.DBManager;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.ui.usage_change.contract.RealNameDetailContract;
import com.sh3h.meterreading.ui.usage_change.listener.RealNameDetailDataListener;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.ImageItem;
import com.sh3h.serverprovider.entity.ResultEntity;
import com.sh3h.serverprovider.entity.XJXXWordBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.subsciber.BaseSubscriber;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;

import static com.sh3h.meterreading.util.Const.UPLOADDATA_ERROR_CODE;

public class RealNameDetailModelImpl implements RealNameDetailContract.Model {
    private boolean isSuccess = false;

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
    public void saveOrUpload(boolean isSave, RealNameWholeEntity bean, RealNameDetailDataListener listener) {

        if (isSave) {
            AsyncSession asyncSession = GreenDaoUtils.getInstance().getAsyncSession(MainApplication.getInstance());
            asyncSession.insertOrReplaceInTx(RealNameWholeEntity.class, bean);
            asyncSession.setListenerMainThread(new AsyncOperationListener() {
                @Override
                public void onAsyncOperationCompleted(AsyncOperation operation) {
                    listener.getResult("保存成功");
                }
            });
        } else {
            Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
            }.getType();
            List<ImageItem> list1 = GsonUtils.fromJson(bean.getImages1(), imageListType);

            List<File> files = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                File file = new File(list1.get(i).path);
                if (file.exists()) {
                    files.add(file);
                }
            }

            io.reactivex.Observable<String> upImageObservable = EasyHttp.post(URL.BASE_URGE_URL1 + URL.AppReturnOrderUploadFileCuiJiao)
                    .params("S_GONGDANBH", bean.getS_CID())
                    .addFileParams("S_ZHAOPIANLJ", files, null)
                    .cacheMode(CacheMode.NO_CACHE)
                    .retryCount(0)
                    .sign(true)
                    .timeStamp(true)
                    .execute(String.class);

            io.reactivex.Observable<String> upDataObservable = EasyHttp.post(URL.BASE_URGE_URL1 + URL.StartWFJuMingZhi)
                    .params("S_YONGHULB", bean.getUserType())
                    .params("S_LIANXIR", bean.getContactPerson())
                    .params("S_LIANXISJ", bean.getPhoneNum())
                    .params("S_LIANXIDH", bean.getPhone())
                    .params("S_EMAIL", String.valueOf(bean.getEmail()))
                    .params("S_BZ", String.valueOf(bean.getRemarks()))
                    .params("S_CID", String.valueOf(bean.getS_CID()))
                    .params("s_cby", SPUtils.getInstance().getString(com.sh3h.serverprovider.rpc.util.Const.S_YUANGONGH))
                    .sign(true)
                    .timeStamp(true)
                    .retryCount(0)
                    .execute(String.class);


            upImageObservable
                    .flatMap(new io.reactivex.functions.Function<String, io.reactivex.ObservableSource<String>>() {
                        @Override
                        public ObservableSource<String> apply(String s) throws Exception {
                            ResultEntity resultEntity = com.blankj.utilcode.util.GsonUtils.fromJson(s, ResultEntity.class);
                            if (UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                                resultEntity.setMsgCode(UPLOADDATA_ERROR_CODE);
                                return io.reactivex.Observable.just(com.blankj.utilcode.util.GsonUtils.toJson(resultEntity));
                            } else {
                                if ("true".equals(resultEntity.getMsgCode())) {
                                    isSuccess = true;
                                    return upDataObservable;
                                } else {
                                    resultEntity.setMsgCode(UPLOADDATA_ERROR_CODE);
                                    return io.reactivex.Observable.just(com.blankj.utilcode.util.GsonUtils.toJson(resultEntity));
                                }
                            }
                        }
                    }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<String>() {
                        @Override
                        public void onError(com.zhouyou.http.exception.ApiException e) {
                            e.getCause().printStackTrace();
                            //失败回调
                            LogUtils.e("----------getNBModuleDZWFGDInfo：" + e.getCode() + "---"
                                    + e.getMessage());
                            ToastUtils.showLong(e.getCode() + "---"
                                    + e.getMessage());
                            if (isSuccess) {
                                //图片上传成功，数据上传失败
                                ToastUtils.showLong("图片上传成功，" + e.getCode() + "---"
                                        + e.getMessage());
                            } else {
                                ToastUtils.showLong(e.getCode() + "---"
                                        + e.getMessage());
                            }
                            ToastUtils.showLong(e.getCause().toString());
                        }

                        @Override
                        public void onNext(String s) {
                            super.onNext(s);
                            //成功回调
                            ResultEntity resultEntity = com.blankj.utilcode.util.GsonUtils.fromJson((String) s, ResultEntity.class);
                            if (UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                                //图片上传失败
                                ToastUtils.showLong(resultEntity.getMsgInfo());
                            } else if ("true".equals(resultEntity.getMsgCode())) {
                                //数据图片都上传成功
                                listener.getResult("");
                                //删除回填数据
                                GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
                                        .getRealNameWholeEntityDao().deleteInTx(bean);
                            } else {
                                //图片上传成功，数据上传失败
                                ToastUtils.showLong("图片上传成功，" + resultEntity.getMsgInfo());
                            }
                        }
                    });
        }



    }
}
