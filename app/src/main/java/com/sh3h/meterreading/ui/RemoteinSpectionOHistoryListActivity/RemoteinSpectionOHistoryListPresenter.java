package com.sh3h.meterreading.ui.RemoteinSpectionOHistoryListActivity;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.XunJianTaskBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RemoteinSpectionOHistoryListPresenter extends ParentPresenter<RemoteinSpectionOHistoryListMvpView> {
  private static final String TAG = "RemoteinSpectionListPresenter";
  private final PreferencesHelper mPreferencesHelper;
  private final ConfigHelper mConfigHelper;


  @Inject
  public RemoteinSpectionOHistoryListPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper, ConfigHelper mConfigHelper) {
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
//            getMvpView().onSaveNewImage(duoMeiTXX);
          } else {
            LogUtil.i(TAG, "---saveNewImage onNext---false");
            getMvpView().onError("saveNewImage is error");
          }
        }
      }));
  }


  public void loadXunJianData() {
    mSubscription.add(mDataManager.getXunJianhistoryBK()
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
//          getMvpView().onloadXunJianData(biaoKaBeans);
        }
      }));
  }

  public void saveXunjianBKlist(List<BiaoKaListBean> biaoKaList) {
    LogUtil.i(TAG, "---saveXunJianBK---");
    if (biaoKaList == null) {
      getMvpView().onError("---saveXunJianBK: biaoKaBean is null---");
      return;
    }

    mSubscription.add(mDataManager.saveXunjianBKlist(biaoKaList)
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
//            getMvpView().onSaveNewImage(duoMeiTXX);
          } else {
            LogUtil.i(TAG, "---saveNewImage onNext---false");
            getMvpView().onError("saveNewImage is error");
          }
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
//          getMvpView().onloadXunJianListData(biaoKaBeans);
        }
      }));
  }

  public List<XunJianTaskBean> getXunJianFuHeTaskBean(String xunjiantaskType) {
    return mDataManager.getXunJianFuHeTaskBean(xunjiantaskType);
  }

  public List<BiaoKaListBean> getTiJiaoBiaoKaListBean(String renwumc, int i) {
    return mDataManager.getTiJiaoBiaoKaListBean(renwumc,i);
  }
}
