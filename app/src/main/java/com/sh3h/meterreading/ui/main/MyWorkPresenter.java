package com.sh3h.meterreading.ui.main;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUSamplingTask;
import com.sh3h.datautil.data.entity.DUSamplingTaskInfo;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sh3h.datautil.data.local.config.SystemConfig.REGION_WENZHOU;

/**
 * Created by Administrator on 2016/2/20.
 */
public class MyWorkPresenter extends ParentPresenter<MyWorkMvpView> {
    private static final String TAG = "MyWorkPresenter";

    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;


    @Inject
    public MyWorkPresenter(DataManager dataManager, ConfigHelper configHelper, PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public void initRegion() {
        getMvpView().onInitRegion(REGION_WENZHOU);
    }

    public void checkStyle() {
        getMvpView().initStyle(true);
    }

    public void getAllTasks(boolean isLocal) {
        mSubscription.add(mDataManager.getTasks(new DUTaskInfo(mPreferencesHelper.getUserSession().getAccount()), isLocal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getAllTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getAllTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUTask> duTasks) {
                        LogUtil.i(TAG, "---getAllTasks onNext---");
                        getMvpView().onGetAllTasks(duTasks);
                    }
                })
        );
    }

    public void getAllSamplingTasks(boolean isLocal) {
        mSubscription.add(mDataManager.getSamplingTasks(new DUSamplingTaskInfo(mPreferencesHelper.getUserSession().getAccount()), isLocal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUSamplingTask>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getAllSamplingTasks onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getAllSamplingTasks onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUSamplingTask> duSamplingTaskList) {
                        LogUtil.i(TAG, "---getAllSamplingTasks onNext---");
                        getMvpView().onGetSamplingTasks(duSamplingTaskList);
                    }
                })
        );
    }

  public void saveWord(List<XJXXWordBean> data) {
    LogUtil.i(TAG, "---saveXunJianBK---");
    if (data == null) {
      getMvpView().onError("---saveXunJianBK: biaoKaBean is null---");
      return;
    }

    mSubscription.add(mDataManager.saveXunjianWord(data)
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

  public void deleteWord(final List<XJXXWordBean> data) {


    mSubscription.add(mDataManager.deleteXunjianWord()
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
            getMvpView().onDeleteWord(data);
          } else {
            LogUtil.i(TAG, "---saveNewImage onNext---false");
            getMvpView().onError("saveNewImage is error");
          }
        }
      }));
  }

  public void deleteFile() {

    mSubscription.add(mDataManager.deleteFile()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
      .subscribe(new Subscriber<Boolean>() {
        @Override
        public void onCompleted() {
          LogUtil.i(TAG, "---deleteFile onCompleted---");
        }

        @Override
        public void onError(Throwable e) {
          LogUtil.i(TAG, "---deleteFile onError---" + e.getMessage());
          getMvpView().onError(e.getMessage());
        }

        @Override
        public void onNext(Boolean aBoolean) {
          LogUtil.i(TAG, "---deleteFile onNext---");
          if (aBoolean) {
          } else {
            LogUtil.i(TAG, "---deleteFile onNext---false");
            getMvpView().onError("deleteFile is error");
          }
        }
      }));
  }
  private void countDate(){
    Date now = new Date();
    SimpleDateFormat bfFormatter = new SimpleDateFormat("yyyy-MM");
    String nowTime = bfFormatter.format(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
    try {
      Date date = sdf.parse(nowTime);
      Calendar rightNow = Calendar.getInstance();
      rightNow.setTime(date);
      rightNow.add(Calendar.DAY_OF_YEAR, -60);
      Date time = rightNow.getTime();
      String format = sdf.format(time);
      File folder = new File(mConfigHelper.getImageFolderPath(), format);
      deleteDirWihtFile(folder);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //删除文件夹和文件夹里面的文件
  public static void deleteDirWihtFile(File dir) {
    if (dir == null || !dir.exists() || !dir.isDirectory())
      return;
    for (File file : dir.listFiles()) {
      if (file.isFile())
        file.delete(); // 删除所有文件
      else if (file.isDirectory())
        deleteDirWihtFile(file); // 递规的方式删除文件夹
    }
    dir.delete();// 删除目录本身
  }
}
