package com.sh3h.meterreading.ui.InspectionInput.lr;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LROperatingPresenter  extends ParentPresenter<LROperatingMvpView> {
  private static final String TAG = "LROperatingPresenter";
  private final PreferencesHelper mPreferencesHelper;
  private final ConfigHelper mConfigHelper;

  @Inject
  public LROperatingPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper, ConfigHelper mConfigHelper) {
    super(dataManager);
    this.mPreferencesHelper = mPreferencesHelper;
    this.mConfigHelper = mConfigHelper;

  }

  public void loadBiaoka(String xiaoGenhao) {
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
          getMvpView().onloadBiaokaList(biaoKaBeans);
        }
      }));
  }

  public void loadBiaokaList(String xiaoGenhao) {
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
          getMvpView().onloadBiaokaListData(biaoKaBeans);
        }
      }));
  }

    public void loadWordData(final String type) {
        if (type == null){
            return;
        }
        mSubscription.add(mDataManager.getXunJianWord(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<XJXXWordBean>>() {
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
                    public void onNext(List<XJXXWordBean> xjxxWordBeans) {
                        LogUtil.i(TAG, "---loadTasks: onLoadTasks---");
                        getMvpView().onloadWordData(xjxxWordBeans,type);
                    }
                }));
    }

    public List<XJXXWordBean> getHotlineWordData(String type) {
        return mDataManager.getHotlineWordData(type);
    }

    public List<XJXXWordBean> getHotlineWordData(String type, String yongshuixz) {
        return mDataManager.getHotlineWordData(type,yongshuixz);
    }

    public void saveBiaoKaWholeEntityDao(BiaoKaWholeEntity biaoKaWholeEntity) {
        mDataManager.saveBiaoKaWholeEntityDao(biaoKaWholeEntity);
    }
}
