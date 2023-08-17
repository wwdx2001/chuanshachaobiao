package com.sh3h.meterreading.ui.remoteinspection;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
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

public class RemoteinSpectionListPresenter extends ParentPresenter<RemoteinSpectionListMvpView> {
  private static final String TAG = "RemoteinSpectionListPresenter";
  private final PreferencesHelper mPreferencesHelper;
  private final ConfigHelper mConfigHelper;


  @Inject
  public RemoteinSpectionListPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper, ConfigHelper mConfigHelper) {
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

//  public void saveXunjianBKlist(List<BiaoKaListBean> biaoKaList) {
//    LogUtil.i(TAG, "---saveXunJianBK---");
//    if (biaoKaList == null) {
//      getMvpView().onError("---saveXunJianBK: biaoKaBean is null---");
//      return;
//    }
//
//    mSubscription.add(mDataManager.saveXunjianBKlist(biaoKaList)
//      .observeOn(AndroidSchedulers.mainThread())
//      .subscribeOn(Schedulers.io())
//      .subscribe(new Subscriber<Boolean>() {
//        @Override
//        public void onCompleted() {
//          LogUtil.i(TAG, "---saveNewImage onCompleted---");
//        }
//
//        @Override
//        public void onError(Throwable e) {
//          LogUtil.i(TAG, "---saveNewImage onError---" + e.getMessage());
//          getMvpView().onError(e.getMessage());
//        }
//
//        @Override
//        public void onNext(Boolean aBoolean) {
//          LogUtil.i(TAG, "---saveNewImage onNext---");
//          if (aBoolean) {
////            getMvpView().onSaveNewImage(duoMeiTXX);
//          } else {
//            LogUtil.i(TAG, "---saveNewImage onNext---false");
//            getMvpView().onError("saveNewImage is error");
//          }
//        }
//      }));
//  }

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

//    public void getisSaveBiaoKaWholeEntity() {
//        mSubscription.add(mDataManager.getisSaveBiaoKaWholeEntity()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<List<BiaoKaWholeEntity>>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.i(TAG, "---loadTasks onCompleted---");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.i(TAG, String.format("---loadTasks onError: %s---", e.getMessage()));
//                        getMvpView().onError("无上传数据"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(List<BiaoKaWholeEntity> biaoKaWholeEntities) {
//                        LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
//                        getMvpView().onIsSaveBiaoKaWholeEntity(biaoKaWholeEntities);
//                    }
//                }));
//    }

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

//  public void SaveXunJianTasks(List<XunJianTaskBean> xunjianTasks) {
//    LogUtil.i(TAG, "---SaveXunJianTasks---");
//    if (xunjianTasks == null) {
//      getMvpView().onError("---SaveXunJianTasks: xunjianTasks is null---");
//      return;
//    }
//    mSubscription.add(mDataManager.SaveXunJianTasks(xunjianTasks)
//      .observeOn(AndroidSchedulers.mainThread())
//      .subscribeOn(Schedulers.io())
//      .subscribe(new Subscriber<Boolean>() {
//        @Override
//        public void onCompleted() {
//          LogUtil.i(TAG, "---saveNewImage onCompleted---");
//        }
//
//        @Override
//        public void onError(Throwable e) {
//          LogUtil.i(TAG, "---saveNewImage onError---" + e.getMessage());
//          getMvpView().onError(e.getMessage());
//        }
//
//        @Override
//        public void onNext(Boolean aBoolean) {
//          LogUtil.i(TAG, "---saveNewImage onNext---");
//          if (aBoolean) {
////            getMvpView().onSaveNewImage(duoMeiTXX);
//          } else {
//            LogUtil.i(TAG, "---saveNewImage onNext---false");
//            getMvpView().onError("saveNewImage is error");
//          }
//        }
//      }));
//  }

  public List<XunJianTaskBean> getLocalXunJianTasks(String xunjiantaskType) {
    return mDataManager.getLocalXunJianTasks(xunjiantaskType);
  }

  public List<BiaoKaListBean> getWcorYcBiaoKalistbean(String renwumc, String type) {
      return mDataManager.getWcorYcBiaoKalistbean(renwumc,type);
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

  public void saveXunjianBKlistBean(BiaoKaListBean biaoKaListBean) {
    mDataManager.saveXunjianBKlistBean(biaoKaListBean);
  }

  public List<BiaoKaWholeEntity> getBiaoKaWholeEntity(String renwumc, int issave) {
    return mDataManager.getBiaoKaWholeEntity2(renwumc,issave);
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
}
