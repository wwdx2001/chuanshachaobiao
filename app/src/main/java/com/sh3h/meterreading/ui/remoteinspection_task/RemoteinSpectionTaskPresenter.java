package com.sh3h.meterreading.ui.remoteinspection_task;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.meterreading.ui.remoteinspection.RemoteinSpectionListMvpView;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.XunJianTaskBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RemoteinSpectionTaskPresenter extends ParentPresenter<RemoteinSpectionListMvpView> {
  private static final String TAG = "RemoteinSpectionListPresenter";
  private final PreferencesHelper mPreferencesHelper;
  private final ConfigHelper mConfigHelper;


  @Inject
  public RemoteinSpectionTaskPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper, ConfigHelper mConfigHelper) {
    super(dataManager);
    this.mPreferencesHelper = mPreferencesHelper;
    this.mConfigHelper = mConfigHelper;

  }
  public void saveXunJianBK(final List<BiaoKaBean> biaoKaBean){
    LogUtil.i(TAG, "---saveXunJianBK---");
    if (biaoKaBean == null) {
      getMvpView().onError("---saveXunJianBK: biaoKaBean is null---");
      return;
    }

    mSubscription.add(mDataManager.saveXunJianBK(biaoKaBean)
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

//            getMvpView().onsaveXunJianBK(aBoolean);
          } else {
            LogUtil.i(TAG, "---saveNewImage onNext---false");
            getMvpView().onError("saveNewImage is error");
          }
        }
      }));
  }


  public void loadXunJianData() {
    mSubscription.add(mDataManager.getXunJianBK(null, true)
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
          getMvpView().onloadXunJianData(biaoKaBeans);
        }
      }));
  }


  public void loadXunJianListData() {
    mSubscription.add(mDataManager.getXunJianListData(null, true)
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
          getMvpView().onloadXunJianListData(biaoKaBeans);
        }
      }));
  }

    public void getXunJianListData() {
        mSubscription.add(mDataManager.getXunJianListData(null, true)
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
                        getMvpView().onloadXunJianListData(biaoKaBeans);
                    }
                }));
    }

    public void SaveBiaoKaWholeEntity(BiaoKaWholeEntity biaoKaWholeEntity) {
        LogUtil.i(TAG, "---saveXunJianBK---");
        if (biaoKaWholeEntity == null) {
            getMvpView().onError("---saveXunJianBK: biaoKaBean is null---");
            return;
        }
      mDataManager.SaveBiaoKaWholeEntity2(biaoKaWholeEntity);

    }

    public void loadXunJianListData2(final String type) {
        mSubscription.add(mDataManager.getXunJianListData(type)
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
                        getMvpView().onloadXunJianListData2(biaoKaBeans,type);
                    }
                }));
    }


  public List<XunJianTaskBean> getLocalXunJianTasks(String xunjiantaskType) {
    return mDataManager.getLocalXunJianTasks(xunjiantaskType);
  }



  public List<XunJianTaskBean> getXunJianTaskBean(String renwumc) {
    return mDataManager.getXunJianTaskBean(renwumc);
  }

  public List<BiaoKaListBean> getBiaoKaListBean(String s_renwuid) {
    return mDataManager.getBiaoKaListBean(s_renwuid);
  }

  public void deleteBiaoKaListBean() {
    mDataManager.deleteBiaoKaListBean();
  }


  public boolean SaveXunJianTasks2(ArrayList<XunJianTaskBean> xunjianTasks) {
    return mDataManager.SaveXunJianTasks2(xunjianTasks);
  }

  public boolean saveXunjianBKlist2(List<BiaoKaListBean> biaoKaList) {
    return mDataManager.saveXunjianBKlist2(biaoKaList);
  }

  public boolean saveBiaoKaBean(List<BiaoKaBean> biaoKaBeans) {
    return mDataManager.saveBiaoKaBean(biaoKaBeans);
  }

  public boolean saveBiaoKaWholeEntityDao(BiaoKaWholeEntity biaoKaWholeEntity) {
    try {
      mDataManager.saveBiaoKaWholeEntityDao(biaoKaWholeEntity);
      return true;
    }catch (Exception e){
      return false;
    }

  }

    public List<XunJianTaskBean> getXunJianFuHeTaskBean(String xunjiantaskType) {
        return mDataManager.getXunJianFuHeTaskBean(xunjiantaskType);
    }

    public List<BiaoKaListBean> getTiJiaoBiaoKaListBean(String renwumc, int i) {
        return mDataManager.getTiJiaoBiaoKaListBean(renwumc,i);
    }

    public List<BiaoKaListBean> getSearchBiaoKaListBean(String text, boolean isHistory) {
      return mDataManager.getSearchBiaoKaListBean(text, isHistory);
    }
    public long getZCBiaoKaListBeanCount(String renWuMc, String type) {
        return mDataManager.getZCBiaoKaListBeanCount(renWuMc,type);
    }
}
