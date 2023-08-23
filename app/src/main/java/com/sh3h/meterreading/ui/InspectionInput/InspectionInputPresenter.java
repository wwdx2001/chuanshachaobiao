package com.sh3h.meterreading.ui.InspectionInput;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InspectionInputPresenter extends ParentPresenter<InspectionInputMvpView> {
  private static final String TAG = "InspectionInputPresenter";
  private final PreferencesHelper mPreferencesHelper;
  private final ConfigHelper mConfigHelper;

  @Inject
  public InspectionInputPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper, ConfigHelper mConfigHelper) {
    super(dataManager);
    this.mPreferencesHelper = mPreferencesHelper;
    this.mConfigHelper = mConfigHelper;

  }

  public void loadBiaoka(String xiaoGenhao , final boolean isnext) {
    if (xiaoGenhao == null){
      return;
    }
    mSubscription.add(mDataManager.getXunJianBK(xiaoGenhao)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
      .subscribe(new Subscriber<List<BiaoKaBean>>() {
        @Override
        public void onCompleted() {
          LogUtil.i(TAG, "---loadTasks onCompleted---");
        }

        @Override
        public void onError(Throwable e) {
          LogUtil.i(TAG, String.format("---loadTasks onError: %s---", e.getMessage()));
          getMvpView().onError(e.getMessage());
        }

        @Override
        public void onNext(List<BiaoKaBean> biaoKaBeans) {
          LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
          getMvpView().onloadBiaokaList(biaoKaBeans,isnext);
        }
      }));
  }

  public void loadBiaokaList(String xiaoGenhao, final boolean isnext) {
    if (xiaoGenhao == null){
      return;
    }
    mSubscription.add(mDataManager.getXunJianListData(xiaoGenhao)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
      .subscribe(new Subscriber<List<BiaoKaListBean>>() {
        @Override
        public void onCompleted() {
          LogUtil.i(TAG, "---loadTasks onCompleted---");
        }

        @Override
        public void onError(Throwable e) {
          LogUtil.i(TAG, String.format("---loadTasks onError: %s---", e.getMessage()));
          getMvpView().onError(e.getMessage());
        }

        @Override
        public void onNext(List<BiaoKaListBean> biaoKaBeans) {
          LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
          getMvpView().onloadBiaokaListData(biaoKaBeans,isnext);
        }
      }));
  }

    public void SaveBiaoKaWholeEntity(BiaoKaWholeEntity newWholeEntity, final boolean isfinish) {
        LogUtil.i(TAG, "---saveXunJianBK---");
        if (newWholeEntity == null) {
            getMvpView().onError("---saveXunJianBK: biaoKaBean is null---");
            return;
        }

        mSubscription.add(mDataManager.SaveBiaoKaWholeEntity(newWholeEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveNewImage onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveNewImage onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveNewImage onNext---");
                        if (aBoolean) {
                            getMvpView().onSaveBiaoKaWholeEntity(aBoolean,isfinish);
                        } else {
                            LogUtil.i(TAG, "---saveNewImage onNext---false");
                            getMvpView().onError("saveNewImage is error");
                        }
                    }
                }));
    }

    public void loadBiaokaListBeans(String renwuh, final String type) {
        if (renwuh == null || type==null){
            return;
        }
        mSubscription.add(mDataManager.getBiaokaListBeans(renwuh,type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<BiaoKaListBean>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<BiaoKaListBean> biaoKaBeans) {
                        LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
                        getMvpView().loadBiaokaListBeans(biaoKaBeans,type);
                    }
                }));
    }

    public void loadbiaoKaWholeEntitys(long ID) {

        mSubscription.add(mDataManager.getbiaoKaWholeEntitys(ID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<BiaoKaWholeEntity>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<BiaoKaWholeEntity> biaoKaBeans) {
                        LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
                        getMvpView().loadBiaokaWholeEntBeans(biaoKaBeans);
                    }
                }));
    }

  public List<BiaoKaListBean> getWcOrYcBiaoKaListBean(String renwumc, String type) {
    return mDataManager.getWcorYcBiaoKalistbean2(renwumc,type);
  }

    public List<BiaoKaBean> getBiaoKaBean(String xiaoGenhao) {
        return mDataManager.getBiaoKaBean(xiaoGenhao);
    }

    public List<BiaoKaWholeEntity> getBiaoKaWholeEntity(String renWuId, String xiaoGenhao) {
        return mDataManager.getBiaoKaWholeEntity(renWuId,xiaoGenhao);
    }

    public List<BiaoKaWholeEntity> getBiaoKaWholeEntity(long id) {
      return mDataManager.getBiaoKaWholeEntity(id);
    }

  public void SaveBiaoKaWholeEntity(BiaoKaWholeEntity newWholeEntity) {
    mDataManager.SaveBiaoKaWholeEntity(newWholeEntity);
  }

  public void SaveBiaoKaWholeEntity2(BiaoKaWholeEntity newWholeEntity) {
    mDataManager.SaveBiaoKaWholeEntity2(newWholeEntity);
  }
}
