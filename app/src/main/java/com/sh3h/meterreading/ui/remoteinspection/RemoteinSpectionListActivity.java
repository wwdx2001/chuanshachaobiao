package com.sh3h.meterreading.ui.remoteinspection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.bean.ImageItem;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.RemoteinSpectionListAdapter;
import com.sh3h.meterreading.event.BiaoKaListSaveEvent;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.event.XunJianDataUploadEvent;
import com.sh3h.meterreading.ui.InspectionInput.InspectionInputActivity;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.ResultBean;
import com.sh3h.serverprovider.entity.ResultEntity;
import com.sh3h.serverprovider.entity.XunJianTaskBean;
import com.sh3h.thirdparty.gesturelock.commonutil.Constants;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RemoteinSpectionListActivity extends ParentActivity
  implements View.OnClickListener, RemoteinSpectionListMvpView, RemoteinSpectionListAdapter.OnItemClickListener {
  private static final String TAG = "RemoteinSpectionListActivity";
  @Inject
  RemoteinSpectionListPresenter mRemoteinSpectionListPresenter;
  @Inject
  Bus mEventBus;
  @Inject
  ConfigHelper mConfigHelper;

  @Bind(R.id.loading_process)
  SmoothProgressBar mSmoothProgressBar;
  @Bind(R.id.recycler)
  RecyclerView recyclerView;
  @Bind(R.id.avl_toolbar)
  Toolbar mToolbar;
  private MenuItem mAll, mInspected, mNoInspected,mAddUp,mAddDown;
  private List<BiaoKaBean> mBiaoKaBeans;
  private List<BiaoKaListBean> mBiaoKaListBeans;
  private RemoteinSpectionListAdapter mRemoteinSpectionListAdapter;
  private SwipeRefreshLayout mRefreshLayout;
  private int commited;
  private ProgressBar mProgressBar;
  private AlertDialog mAlertDialog;
  private TextView mTvTitle;
  private TextView mTvProcess;
  private boolean isHistory=false;
  private String mType;
  public static final String XUNJIANTASK = "chaobiaorw";
  public static final String BIAOKABEAN = "chaobiaoyc";
  public static final String BIAOKALIAT = "chaobiaosj";
  public static final String SAVECHAOBIAOBKXX = "chaobiaobkxx";
  private String screenType;
  private List<XunJianTaskBean> mLocalXunJianTasks;



  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_remote_inspection, menu);
    mAll = menu.findItem(R.id.mul_action_remote_all);
    mInspected = menu.findItem(R.id.mul_action_remote_yes);
    mNoInspected = menu.findItem(R.id.mul_action_remote_no);
    mAddUp = menu.findItem(R.id.mul_action_remote_up);
    mAddDown = menu.findItem(R.id.mul_action_remote_down);
    mAddDown.setVisible(false);
    mAddUp.setVisible(false);
    return true;
  }

  @Subscribe
  public void onInitUserConfigResult(UIBusEvent.InitUserConfigResult initUserConfigResult) {
    LogUtil.i(TAG, "---onInitResult---" + initUserConfigResult.isSuccess());
    if (initUserConfigResult.isSuccess()) {
    } else {
      ApplicationsUtil.showMessage(this, R.string.text_init_failure);
    }
  }

  private void initRecyclerView() {
    mRemoteinSpectionListAdapter = new RemoteinSpectionListAdapter(RemoteinSpectionListActivity.this);
    mRemoteinSpectionListAdapter.setTaskList(mBiaoKaBeans,mBiaoKaListBeans);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(mRemoteinSpectionListAdapter);
    mRemoteinSpectionListAdapter.setOnItemClickListener(this);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.mtl_action_update:
        showProgressDialog();
        //提交全部数据
        new Thread(new Runnable() {
          @Override
          public void run() {
            uploadAllData();
          }
        }).start();

        break;
      case R.id.mul_action_remote_all:
        screenType = "all";
        //全部
        mRemoteinSpectionListPresenter.loadXunJianListData2("all");
        break;
      case R.id.mul_action_remote_yes:
        screenType = "yichao";
        mRemoteinSpectionListPresenter.loadXunJianListData2("yes");
        break;
      case R.id.mul_action_remote_no:
        screenType = "weichao";
        mRemoteinSpectionListPresenter.loadXunJianListData2("no");
        break;
      case R.id.mul_action_remote_up:
        mRemoteinSpectionListPresenter.loadXunJianListData2("up");
        break;
      case R.id.mul_action_remote_down:
        mRemoteinSpectionListPresenter.loadXunJianListData2("down");
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
  boolean isSuccess = false;



  private void uploadAllData() {

//    mSmoothProgressBar.setVisibility(View.VISIBLE);
    commited = 0;
    List<BiaoKaWholeEntity> biaoKaWholeEntities = new ArrayList<>();
    for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
      List<BiaoKaWholeEntity> localLists = mRemoteinSpectionListPresenter.getBiaoKaWholeEntity(mLocalXunJianTasks.get(i).getRENWUMC(),1);

      biaoKaWholeEntities.addAll(localLists);
    }

    if (biaoKaWholeEntities != null && biaoKaWholeEntities.size() > 0) {

      int size = biaoKaWholeEntities.size();
      if (isHistory) {
        for (int i = 0; i < size; i++) {
          BiaoKaWholeEntity biaoKaWholeEntity = biaoKaWholeEntities.get(i);
          uploadImage(biaoKaWholeEntity, i + 1, size);
        }

      } else {
        List<io.reactivex.Observable<String>> observableList = new ArrayList<>();
        for (int j = 0; j < size; j++) {
          BiaoKaWholeEntity biaoKaWholeEntity = biaoKaWholeEntities.get(j);
//                    uploadImageData(biaoKaWholeEntity, i + 1, size);
//                    if (!biaoKaWholeEntity.getIsUploadImage()) {
          Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
          }.getType();
          List<ImageItem> list1 = GsonUtils.fromJson(biaoKaWholeEntity.getImages1(), imageListType);
          List<ImageItem> list2 = GsonUtils.fromJson(biaoKaWholeEntity.getImages2(), imageListType);
          List<ImageItem> list3 = GsonUtils.fromJson(biaoKaWholeEntity.getImages3(), imageListType);
          List<ImageItem> list4 = GsonUtils.fromJson(biaoKaWholeEntity.getImages4(), imageListType);
          List<ImageItem> list6 = GsonUtils.fromJson(biaoKaWholeEntity.getImages6(), imageListType);
          list1.addAll(list2);
          list1.addAll(list3);
          list1.addAll(list4);
          if (list6 != null) {
            list1.addAll(list6);
          }

          List<File> files = new ArrayList<>();
          for (int i = 0; i < list1.size(); i++) {
            File file = new File(list1.get(i).path);
            if (file.exists()) {
              files.add(file);
            }
          }

          List<File> vedioFile = new ArrayList<>();
          if (biaoKaWholeEntity.getVedioUrl() != null && !"".equals(biaoKaWholeEntity.getVedioUrl())) {
            File file = new File(biaoKaWholeEntity.getVedioUrl());
            if (file.exists()) {
              vedioFile.add(file);
            }
          }

          final io.reactivex.Observable<String> upImageObservable = EasyHttp.post(URL.BASE_CS_URL+URL.UploadFile)
            .params("S_LEIXING", "XUNJIAN")
            .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
            .addFileParams("S_ZHAOPIANLJ", files, null)
            .cacheMode(CacheMode.NO_CACHE)
            .retryCount(0)
            .sign(true)
            .timeStamp(true)
            .execute(String.class);

          io.reactivex.Observable<String> upDataObservable = EasyHttp.post(URL.BASE_CS_URL+URL.setChaoBiaoInfo_CB)
            .params("RENWUID", biaoKaWholeEntity.getRENWUID())
            .params("XIAOGENH", biaoKaWholeEntity.getXIAOGENH())
            .params("XUNJIANCM", biaoKaWholeEntity.getXUNJIANCM())
            .params("CHAOBIAOZT", biaoKaWholeEntity.getCbzt())
            .params("TXM", biaoKaWholeEntity.getTXM())
            .params("XUNJIANYL", biaoKaWholeEntity.getXUNJIANYL())
            .params("YLSM", biaoKaWholeEntity.getYLSM())
            .params("BEIZHU", biaoKaWholeEntity.getBEIZHU())
            .params("GONGDANDJ", biaoKaWholeEntity.getGONGDANDJ() == null ? "0" : biaoKaWholeEntity.getGONGDANDJ())
            .params("GONGDANBZ", biaoKaWholeEntity.getGONGDANDJBZ() == null ? "" : biaoKaWholeEntity.getGONGDANDJBZ())
            .params("BIAOWU_GZYY", biaoKaWholeEntity.getBIAOWU_GZYY() == null ? "" : biaoKaWholeEntity.getBIAOWU_GZYY())
            .params("MOKUAI_GZYY", biaoKaWholeEntity.getMOKUAI_GZYY() == null ? "" : biaoKaWholeEntity.getMOKUAI_GZYY())
            .params("SFGHBP", biaoKaWholeEntity.getSFGHBP() == null ? "" : biaoKaWholeEntity.getSFGHBP())
            .sign(true)
            .timeStamp(true)
            .retryCount(0)
            .execute(String.class);

          io.reactivex.Observable<String> uploadObservable = null;
          if (vedioFile.size() > 0) {
            uploadObservable = EasyHttp.post(URL.BASE_CS_URL+URL.UploadFile)
              .params("S_LEIXING", "SP")
              .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
              .addFileParams("S_ZHAOPIANLJ", vedioFile, null)
              .cacheMode(CacheMode.NO_CACHE)
              .retryCount(0)
              .sign(true)
              .timeStamp(true)
              .execute(String.class)
              .flatMap(new io.reactivex.functions.Function<String, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(String s) throws Exception {
                  ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                  if ("00".equals(resultEntity.getMsgCode())) {
                    return upImageObservable;
                  } else {
                    resultEntity.setMsgCode(Const.UPLOADDATA_ERROR_CODE);
                    return io.reactivex.Observable.just(GsonUtils.toJson(resultEntity));
                  }
                }
              });
          } else {
            uploadObservable = upImageObservable;
          }
          final io.reactivex.Observable<String> finalUpDataObservable = upDataObservable;
          io.reactivex.Observable<String> stringObservable = uploadObservable
            .flatMap(new io.reactivex.functions.Function<String, ObservableSource<String>>() {
              @Override
              public ObservableSource<String> apply(String s) throws Exception {
                ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                if (Const.UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                  resultEntity.setMsgCode(Const.UPLOADDATA_ERROR_CODE);
                  return io.reactivex.Observable.just(GsonUtils.toJson(resultEntity));
                } else {
                  if ("00".equals(resultEntity.getMsgCode())) {
                    isSuccess = true;
                    return finalUpDataObservable;
                  } else {
                    resultEntity.setMsgCode(Const.UPLOADDATA_ERROR_CODE);
                    return io.reactivex.Observable.just(GsonUtils.toJson(resultEntity));
                  }
                }
              }
            });
          observableList.add(stringObservable);

        }

        int[] successCount = {0};
        uploadWholeData(observableList, biaoKaWholeEntities, successCount);
      }

    } else {
      Log.e("SingleClick", "------------------------------------------点击了");
      ToastUtils.showLong("没有待提交数据");
      dissProgressDialog();
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activit_remote_list);
    getActivityComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setActionBarBackButtonEnable();
    mRemoteinSpectionListPresenter.attachView(this);
    mEventBus.register(this);
    mSmoothProgressBar.setVisibility(View.INVISIBLE);
//    mRemoteinSpectionListPresenter.loadXunJianData();
    initRecyclerView();
    initData();
    requestData();
  }

  private void initData() {
    screenType = "all";
    mBiaoKaListBeans = new ArrayList<>();
    mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
    mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

        getXunJianTask(true);
      }
    });
  }
  private void requestData() {
    mBiaoKaListBeans.clear();
    mLocalXunJianTasks = mRemoteinSpectionListPresenter.getLocalXunJianTasks(Const.XUNJIANTASK_TYPE);

    switch (screenType) {
      case "yichao":
        //显示本地数据
        for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
          List<BiaoKaListBean> yccBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"yc");
          mBiaoKaListBeans.addAll(yccBiaoKaListBeans);
        }
        break;
      case "weichao":
        for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
          List<BiaoKaListBean> wcBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"wc");
          mBiaoKaListBeans.addAll(wcBiaoKaListBeans);
        }
        break;
      case "all":
      default:
        for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
          List<BiaoKaListBean> wcBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"wc");
          List<BiaoKaListBean> yccBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"yc");
          mBiaoKaListBeans.addAll(wcBiaoKaListBeans);
          mBiaoKaListBeans.addAll(yccBiaoKaListBeans);

        }
        filterSort();
        break;
    }
    mRemoteinSpectionListAdapter.setTaskList(mBiaoKaBeans,mBiaoKaListBeans);
    if (mBiaoKaListBeans.size() <= 0){
      getXunJianTask(true);
    }
//

  }

  private void getXunJianTask(boolean isNeedBiaoKa) {
    if (!mRefreshLayout.isRefreshing()) {
      mRefreshLayout.setRefreshing(true);
    }
    Log.i("xnhh", "getXunJianTask: " + SPUtils.getInstance().getString(Constants.USERNAME));
    EasyHttp.post(URL.BASE_CS_URL + URL.getXunJianTask_cb)
      .params("S_YUANGONGH", SPUtils.getInstance().getString(Constants.USERNAME))
      .execute(new ProgressDialogCallBack<String>(null,true,false) {

        @Override
        public void onError(ApiException e) {
          super.onError(e);
          Log.d( "onError 423: " , e.getMessage());
          ToastUtils.showShort(e.getMessage());
          mRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onSuccess(String s) {
          ResultBean resultBean = GsonUtils.fromJson(s, ResultBean.class);
          if ("00".equals(resultBean.getMsgCode())) {
            Type xunjianTaskType = new TypeToken<ArrayList<XunJianTaskBean>>() {
            }.getType();
            ArrayList<XunJianTaskBean> xunjianTasks = GsonUtils.fromJson(GsonUtils.toJson(resultBean.getData()), xunjianTaskType);
            if (xunjianTasks != null && xunjianTasks.size() > 0) {

                getBiaoKaList(xunjianTasks);

            } else {
              // TODO 不能删除任务，删除任务会导致历史中的数据查不到
//                            GreenDaoUtils.getInstance().getDaoSession(BaseApplication.getInstance())
//                                    .getXunJianTaskBeanDao().deleteInTx(mLocalXunJianTasks);
              mRefreshLayout.setRefreshing(false);
//              mAdapter.setNewData(new ArrayList<>());
//              mTvZongShu.setText("表卡数：" + mAdapter.getData().size());

            }
          } else {
            ToastUtils.showLong(resultBean.getMsgInfo());
            mRefreshLayout.setRefreshing(false);
          }
        }
      });
  }


  /**
   * 从网络加载表卡列表数据
   *
   * @param xunjianTasks
   */
  @SuppressLint("CheckResult")
  private void getBiaoKaList(ArrayList<XunJianTaskBean> xunjianTasks) {
    List<Observable<String>> list = new ArrayList<>();
    for (XunJianTaskBean xunJianTaskBean : xunjianTasks) {
      xunJianTaskBean.setID((long) xunJianTaskBean.getRENWUMC().hashCode());
      Observable<String> mobileObservable = EasyHttp.post(URL.BASE_CS_URL + URL.getbiaoka_CB)
        .params("RENWUMC", xunJianTaskBean.getRENWUMC())
        .execute(new CallClazzProxy<ApiResult<String>, String>(String.class) {
        });
      list.add(mobileObservable);
    }
    List<List<Observable<String>>> observableList = Lists.partition(list, 50);
    int[] count = {0};
    List<BiaoKaListBean> biaoKaListBeanList = new ArrayList<>();
    getSplitBiaoKaList(xunjianTasks, observableList, count, biaoKaListBeanList);

  }

  private void getSplitBiaoKaList(final ArrayList<XunJianTaskBean> xunjianTasks,
                                  final List<List<Observable<String>>> list, final int[] count,
                                  final List<BiaoKaListBean> biaoKaListBeanList) {

    Observable.zip(list.get(count[0]), new Function<Object[], List<String>>() {
      @Override
      public List<String> apply(Object[] objects) throws Exception {
        List<String> list = new ArrayList<>();
        for (Object object : objects) {
          list.add((String) object);
        }
        return list;
      }
    })
      .subscribe(new BaseSubscriber<List<String>>() {
        @Override
        protected void onStart() {
          super.onStart();
//          presenter.showProgress("提交中");
        }

        @Override
        public void onError(ApiException e) {
          Log.d( "onError 502: " , e.getMessage());
          ToastUtils.showShort(e.getMessage());
          mRefreshLayout.setRefreshing(false);
//          presenter.dismissProgress();
//          presenter.commitAllBackfillInfoError(e.getCode() + e.getMessage());
        }

        @Override
        public void onNext(List<String> strings) {
          super.onNext(strings);
          List<BiaoKaListBean> beans = new ArrayList<>();
          for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            ResultBean resultBean = GsonUtils.fromJson(s, ResultBean.class);
            if ("00".equals(resultBean.getMsgCode())) {
              Type biaoKaListType = new TypeToken<ArrayList<BiaoKaListBean>>() {
              }.getType();
              ArrayList<BiaoKaListBean> biaoKaList = GsonUtils.fromJson(GsonUtils.toJson(resultBean.getData()), biaoKaListType);
              if (biaoKaList != null) {
                beans.addAll(biaoKaList);

              }
            } else {
              mRefreshLayout.setRefreshing(false);
              mSmoothProgressBar.setVisibility(View.INVISIBLE);
            }
          }

          if (beans != null && beans.size() > 0) {
            count[0] = count[0] + 1;
            biaoKaListBeanList.addAll(beans);
            if (count[0] < list.size()) {
              getSplitBiaoKaList(xunjianTasks, list, count, beans);
            } else {
              getBiaoKa(xunjianTasks, biaoKaListBeanList);
            }
          } else {
            mRefreshLayout.setRefreshing(false);
          }
        }
      });

  }




//  /**
//   * 从网络加载表卡列表数据
//   *
//   * @param xunjianTasks
//   */
//  @SuppressLint("CheckResult")
//  private void getBiaoKaList(final ArrayList<XunJianTaskBean> xunjianTasks) {
//    List<ObservableSource<String>> observableList = new ArrayList<>();
//    ObservableSource<String>[] observableSources = null;
//    for (XunJianTaskBean xunJianTaskBean : xunjianTasks) {
//      xunJianTaskBean.setID((long) xunJianTaskBean.getRENWUMC().hashCode());
//      ObservableSource<String> mobileObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getbiaoka_CB)
//        .params("RENWUMC", xunJianTaskBean.getRENWUMC()).execute(new CallClazzProxy<ApiResult<String>, String>(String.class) {
//        });
//      observableList.add(mobileObservable);
//      ObservableSource<String>[] ss = new ObservableSource[observableList.size()];
//      observableSources = observableList.toArray(ss);
//    }
////    List<List<Observable<String>>> observableList = Lists.partition(list, 50);
//    int[] count = {0};
//
////    getSplitBiaoKaList(xunjianTasks, observableList, count, new ArrayList<>());
//    Observable.zipArray(new io.reactivex.functions.Function<Object[], List<String>>() {
//      @Override
//      public List<String> apply(Object[] objects) throws Exception {
//        List<String> list = new ArrayList<>();
//        for (Object object : objects) {
//          list.add((String) object);
//        }
//        return list;
//      }
//    }, false, 1024 * 1024, observableSources)
//      .subscribe(new BaseSubscriber<List<String>>() {
//        @Override
//        protected void onStart() {
//          super.onStart();
////          presenter.showProgress("提交中");
//        }
//
//        @Override
//        public void onError(ApiException e) {
//          ToastUtils.showShort(e.getMessage());
//          mRefreshLayout.setRefreshing(false);
////          presenter.dismissProgress();
////          presenter.commitAllBackfillInfoError(e.getCode() + e.getMessage());
//        }
//
//        @Override
//        public void onNext(List<String> strings) {
//          super.onNext(strings);
//          List<BiaoKaListBean> beans = new ArrayList<>();
//          for (int i = 0; i < strings.size(); i++) {
//            String s = strings.get(i);
//            ResultBean resultBean = GsonUtils.fromJson(s, ResultBean.class);
//            if ("00".equals(resultBean.getMsgCode())) {
//              Type biaoKaListType = new TypeToken<ArrayList<BiaoKaListBean>>() {
//              }.getType();
//              ArrayList<BiaoKaListBean> biaoKaList = GsonUtils.fromJson(GsonUtils.toJson(resultBean.getData()), biaoKaListType);
//              if (biaoKaList != null) {
//                beans.addAll(biaoKaList);
//
//              }
//            } else {
//              mRefreshLayout.setRefreshing(false);
//              mSmoothProgressBar.setVisibility(View.INVISIBLE);
//            }
//          }
//          getBiaoKa(xunjianTasks,beans);
//        }
//      });
//
//  }
  /**
   * 从网络加载对应表卡详情
   *
   * @param xunjianTasks
   * @param biaoKaList
   */
  private void getBiaoKa(ArrayList<XunJianTaskBean> xunjianTasks, List<BiaoKaListBean> biaoKaList){
//    List<Observable<String>> list = new ArrayList<>();
//    ObservableSource<String>[] observableSources = null;
//    for (BiaoKaListBean biaoKaListBean : biaoKaList) {
//      biaoKaListBean.setID((long) biaoKaListBean.getS_RENWUID().hashCode());
//      Observable<String> mobileObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getBiaoKaList_CB)
//        .params("RENWUID", biaoKaListBean.getS_RENWUID())
//        .execute(new CallClazzProxy<ApiResult<String>, String>(String.class) {
//        });
//      list.add(mobileObservable);
//      ObservableSource<String>[] ss = new ObservableSource[list.size()];
//      observableSources = list.toArray(ss);
//    }
    List<Observable<String>> list = new ArrayList<>();
    for (BiaoKaListBean biaoKaListBean : biaoKaList) {
      biaoKaListBean.setID((long) biaoKaListBean.getS_RENWUID().hashCode());
      Observable<String> mobileObservable = EasyHttp.post(URL.BASE_CS_URL+URL.getBiaoKaList_CB)
        .params("RENWUID", biaoKaListBean.getS_RENWUID())
        .execute(new CallClazzProxy<ApiResult<String>, String>(String.class) {
        });
      list.add(mobileObservable);
    }

    List<List<Observable<String>>> observableList = Lists.partition(list, 50);
    int[] count = {0};
    getBiaoKaInfo(xunjianTasks,biaoKaList,observableList,count,new ArrayList<BiaoKaBean>());
  }

  private void getBiaoKaInfo(final ArrayList<XunJianTaskBean> xunjianTasks, final List<BiaoKaListBean> biaoKaList, final List<List<Observable<String>>> observableItemMap, final int[] count, final ArrayList<BiaoKaBean> biaoKaBeans) {
    Observable.zip(observableItemMap.get(count[0]), new Function<Object[], List<String>>() {
      @Override
      public List<String> apply(Object[] objects) throws Exception {
        List<String> list = new ArrayList<>();
        for (Object object : objects) {
          list.add((String) object);
        }
        return list;
      }
    })
      .subscribe(new BaseSubscriber<List<String>>() {
        @Override
        protected void onStart() {
          super.onStart();
        }

        @Override
        public void onError(ApiException e) {
          Log.d( "onError 674: " , e.getMessage());
          ToastUtils.showShort(e.getMessage());
          mRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onNext(List<String> strings) {
          super.onNext(strings);
          List<BiaoKaBean> beans = new ArrayList<>();
          for (String s : strings) {
            ResultBean resultBean = GsonUtils.fromJson(s, ResultBean.class);
            if ("00".equals(resultBean.getMsgCode())) {
              Type biaoKaType = new TypeToken<ArrayList<BiaoKaBean>>() {
              }.getType();
              ArrayList<BiaoKaBean> biaoKa = GsonUtils.fromJson(GsonUtils.toJson(resultBean.getData()), biaoKaType);
              for (BiaoKaBean biaoKaBean : biaoKa) {
                biaoKaBean.setID((long) (biaoKaBean.getXIAOGENH() + biaoKaBean.getBIAOHAO()).hashCode());
                beans.add(biaoKaBean);
              }
            } else {
              mRefreshLayout.setRefreshing(false);
              mSmoothProgressBar.setVisibility(View.INVISIBLE);
            }
          }

          if (beans != null && beans.size() > 0) {
            count[0] = count[0] + 1;
            biaoKaBeans.addAll(beans);
            if (count[0] < observableItemMap.size()) {
              getBiaoKaInfo(xunjianTasks, biaoKaList, observableItemMap, count, biaoKaBeans);
            } else {
              saveBiaoka(xunjianTasks, biaoKaList, biaoKaBeans);
            }
          }

        }
      });
  }


  private void saveBiaoka(ArrayList<XunJianTaskBean> xunjianTasks, List<BiaoKaListBean> biaoKaList, List<BiaoKaBean> biaoKaBeans) {



    for (int i = 0; i < xunjianTasks.size(); i++) {
      XunJianTaskBean xunJianTaskBean = xunjianTasks.get(i);
      // 判断是否保存在数据库
      List<XunJianTaskBean> xjTasks = mRemoteinSpectionListPresenter.getXunJianTaskBean(xunJianTaskBean.getRENWUMC());
      //设置该巡检任务类型为复核类型
      xunJianTaskBean.setXunjianTaskType(Const.XUNJIANTASK_TYPE);
      if (xjTasks != null && xjTasks.size() > 0) {
        XunJianTaskBean xjTaskBean = xjTasks.get(0);
        // 如果数据库存在，将赋值已抄表完成标志
        xunJianTaskBean.setIsFinish(xjTaskBean.getIsFinish());
      }
    }
    // 保存巡检任务
    mRemoteinSpectionListPresenter.SaveXunJianTasks2(xunjianTasks);

    mType = XUNJIANTASK;
    switch (mType) {
      case XUNJIANTASK:
        mType = BIAOKALIAT;
        //保存表卡列表

        for (BiaoKaListBean biaoKaListBean : biaoKaList) {
          biaoKaListBean.setTYPE("远传巡检");
          List<BiaoKaListBean> bkLists = mRemoteinSpectionListPresenter.getBiaoKaListBean(biaoKaListBean.getS_RENWUID());
          if (bkLists != null && bkLists.size() > 0) {
            Log.e("helloworldID", "id=" + biaoKaListBean.getS_RENWUID());
            BiaoKaListBean bkListBean = bkLists.get(0);
            //没有提交的工单才赋值状态，已经提交的直接用接口获取的状态
            if (!bkListBean.getIsCommit()) {
              biaoKaListBean.setXUNJIANZT(bkListBean.getXUNJIANZT());
            }
            biaoKaListBean.setIsSave(bkListBean.getIsSave());
            biaoKaListBean.setLat(bkListBean.getLat());
            biaoKaListBean.setLon(bkListBean.getLon());
//                                                        biaoKaListBean.setIsUploadImage(bkListBean.getIsUploadImage());
//                                                        biaoKaListBean.setIsCommit(bkListBean.getIsCommit());
          }
        }
        //删除本地表卡数据
        mRemoteinSpectionListPresenter.deleteBiaoKaListBean();

        mRemoteinSpectionListPresenter.saveXunjianBKlist2(biaoKaList);
        break;
      case BIAOKALIAT:
        mType = BIAOKABEAN;
        // 保存表卡详情
        Log.e("helloworld", "详情数据" + biaoKaBeans.size());
        mRemoteinSpectionListPresenter.saveXunJianBK(biaoKaBeans);
        break;
      case BIAOKABEAN:
        //保存完成后查询巡检任务显示在列表中
        break;
      default:
        break;
    }
    mRemoteinSpectionListPresenter.saveBiaoKaBean(biaoKaBeans);
    mBiaoKaListBeans.clear();
    mLocalXunJianTasks.clear();
    mLocalXunJianTasks = mRemoteinSpectionListPresenter.getLocalXunJianTasks(Const.XUNJIANTASK_TYPE);
    for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
      List<BiaoKaListBean> wcBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"wc");
      List<BiaoKaListBean> yccBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"yc");
      mBiaoKaListBeans.addAll(wcBiaoKaListBeans);
      mBiaoKaListBeans.addAll(yccBiaoKaListBeans);
    }
    filterSort();
    mRemoteinSpectionListAdapter.setTaskList(null,mBiaoKaListBeans);
    mRefreshLayout.setRefreshing(false);

  }

  @Override
  public void onClick(View view) {

  }

  @Override
  public void onSaveXunJianBK(List<BiaoKaBean> biaoKaBean) {

  }

  @Override
  public void onloadXunJianData(List<BiaoKaBean> biaoKaBean) {


  }

  @Override
  public void onloadXunJianListData(List<BiaoKaListBean> biaoKaListBeans) {
//

  }
  private void showProgressDialog() {
    RemoteinSpectionListActivity.this.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        View processDialogView = View.inflate(RemoteinSpectionListActivity.this, R.layout.dialog_progress, null);
        mTvTitle = (TextView) processDialogView.findViewById(R.id.tv_title);
        mTvProcess = (TextView) processDialogView.findViewById(R.id.tv_process);
        mProgressBar = (ProgressBar) processDialogView.findViewById(R.id.pb_process);
        AlertDialog.Builder builder = new AlertDialog.Builder(RemoteinSpectionListActivity.this);
        builder.setView(processDialogView);
        builder.setCancelable(false);
        mTvTitle.setText("正在上传数据...");
        mAlertDialog = builder.create();
        mAlertDialog.show();
      }
    });
  }
  @Override
  public void onIsSaveBiaoKaWholeEntity(List<BiaoKaWholeEntity> biaoKaWholeEntitie) {

  }
  private void uploadWholeData
          (final List<io.reactivex.Observable<String>> observableList, final List<BiaoKaWholeEntity> biaoKaWholeEntities,
           int[] position) {
    final int[] newPosition = position;
    if (observableList.size() > 0) {
      observableList.get(newPosition[0]).subscribeOn(io.reactivex.schedulers.Schedulers.io())
              .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
              .subscribe(new BaseSubscriber<String>() {
                @Override
                public void onError(ApiException e) {
                  Log.e("xiaochaos", "onError" + newPosition[0] + "--------" + observableList.size() + "-----------------" + biaoKaWholeEntities.size());
                  if (isSuccess) {
                    //数据上传成功，图片上传失败
                    biaoKaWholeEntities.get(newPosition[0]).setIsCommit(false);
                    biaoKaWholeEntities.get(newPosition[0]).setIsSave(true);
                    biaoKaWholeEntities.get(newPosition[0]).setIsUploadImage(true);
                    mRemoteinSpectionListPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntities.get(newPosition[0]));
                    mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_COMMITED, biaoKaWholeEntities.get(newPosition[0])));
                  } else {
                    ToastUtils.showLong(e.getMessage());
                  }
                  isSuccess = false;
                  ++newPosition[0];
                  if (newPosition[0] >= observableList.size()) {
                    mProgressBar.setProgress(100);
                    mTvProcess.setText("当前进度\n" + 100 + "%");
                    dissProgressDialog();
                  } else {
                    uploadWholeData(observableList, biaoKaWholeEntities, newPosition);
                    mProgressBar.setProgress(newPosition[0] * 100 / observableList.size());
                    mTvProcess.setText("当前进度\n" + newPosition[0] * 100 / observableList.size() + "%");
                  }
                }

                @Override
                public void onNext(String s) {
                  super.onNext(s);
                  ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                  if (Const.UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                    //数据上传失败
//                    ToastUtils.showLong(resultEntity.getMsgInfo());
                  } else if ("00".equals(resultEntity.getMsgCode())) {
//                    uploadWeiZhi(biaoKaWholeEntities.get(newPosition[0]), biaoKaWholeEntities.get(newPosition[0]).getRENWUID());

                    //数据图片都上传成功
                    biaoKaWholeEntities.get(newPosition[0]).setIsCommit(true);
                    biaoKaWholeEntities.get(newPosition[0]).setIsUploadImage(true);
                    biaoKaWholeEntities.get(newPosition[0]).setIsSave(false);
                    mRemoteinSpectionListPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntities.get(newPosition[0]));
                    mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_IMAGE_UPLOADED, biaoKaWholeEntities.get(newPosition[0])));
                  } else {
                    //数据上传成功，图片上传失败
                    biaoKaWholeEntities.get(newPosition[0]).setIsCommit(false);
                    biaoKaWholeEntities.get(newPosition[0]).setIsSave(true);
                    biaoKaWholeEntities.get(newPosition[0]).setIsUploadImage(true);
                    mRemoteinSpectionListPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntities.get(newPosition[0]));
                    mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_COMMITED, biaoKaWholeEntities.get(newPosition[0])));
                  }
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  isSuccess = false;
                  Log.e("xiaochaos", "onCompleted" + newPosition[0] + "----------" + observableList.size() + "-------------" + biaoKaWholeEntities.size());
                  ++newPosition[0];
                  if (newPosition[0] >= biaoKaWholeEntities.size()) {
                    mProgressBar.setProgress(100);
                    mTvProcess.setText("当前进度\n" + 100 + "%");
                    dissProgressDialog();
                  } else {
                    uploadWholeData(observableList, biaoKaWholeEntities, newPosition);
                    mProgressBar.setProgress(newPosition[0] * 100 / biaoKaWholeEntities.size());
                    mTvProcess.setText("当前进度\n" + newPosition[0] * 100 / biaoKaWholeEntities.size() + "%");
                  }
                }
              });
    }
  }
  private void dissProgressDialog() {
    RemoteinSpectionListActivity.this.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (mAlertDialog != null) {
          mAlertDialog.dismiss();

        }
      }
    });
//    requestData();
  }


  @Override
  public void onError(String message) {
    mSmoothProgressBar.setVisibility(View.INVISIBLE);
    if (!TextUtil.isNullOrEmpty(message)) {
      LogUtil.i(TAG, message);
      ApplicationsUtil.showMessage(this, message);
    }
  }

  @Override
  public void onSuccess(String message) {
    mSmoothProgressBar.setVisibility(View.INVISIBLE);
    if (!TextUtil.isNullOrEmpty(message)) {
      LogUtil.i(TAG, message);
      ApplicationsUtil.showMessage(this, message);
    }
  }

  @Override
  public void onsaveXunJianBK(Boolean aBoolean) {
    if (aBoolean ==true){
//      mRemoteinSpectionListPresenter.loadXunJianData();
    }

  }

  @Override
  public void onloadXunJianListData2(List<BiaoKaListBean> biaoKaBeans, String type) {

    if (biaoKaBeans != null){
      mBiaoKaListBeans=biaoKaBeans;
      switch (type){
        case "all":
          mRemoteinSpectionListAdapter.setTaskList(mBiaoKaBeans,mBiaoKaListBeans);
          break;
        case "yes":
          mRemoteinSpectionListAdapter.setTaskList(mBiaoKaBeans,mBiaoKaListBeans);
          break;
        case "no":
        
          mRemoteinSpectionListAdapter.setTaskList(mBiaoKaBeans,mBiaoKaListBeans);
          break;
        case "up":

          break;
        case "down":

          break;
      }
    }
  }


  @Override
  public void onItemClick(int type, int position) {
    BiaoKaBean item = mBiaoKaBeans.get(position);
    Intent intent=new Intent(this, InspectionInputActivity.class);

    startActivity(intent);

  }

  /**
   * 表卡操作成功刷新对应表卡
   *
   * @param xunJianDataUploadEvent
   */
  @Subscribe
    public void onXunJianDataUploadEvent(XunJianDataUploadEvent xunJianDataUploadEvent) {
    List<BiaoKaListBean> biaoKaListBeans = mRemoteinSpectionListPresenter.getBiaoKaListBean(xunJianDataUploadEvent.getBiaoKaWholeEntity().getRENWUID());
    if (XunJianDataUploadEvent.TYPE_COMMITED.equals(xunJianDataUploadEvent.getType())) {
      // 图片上传成功，数据上传失败
      if (biaoKaListBeans != null && biaoKaListBeans.size() > 0) {
        BiaoKaListBean biaoKaListBean = biaoKaListBeans.get(0);
        biaoKaListBean.setIsSave(true);
        biaoKaListBean.setIsUploadImage(true);
        biaoKaListBean.setIsCommit(false);
        mRemoteinSpectionListPresenter.saveXunjianBKlistBean(biaoKaListBean);
        requestData();
        if (!isHistory) {
          mEventBus.post(new BiaoKaListSaveEvent(xunJianDataUploadEvent.getBiaoKaWholeEntity().getRENWUMC(),
            xunJianDataUploadEvent.getBiaoKaWholeEntity().getRENWUID(), true));
        }
      }
    } else if (XunJianDataUploadEvent.TYPE_SAVED.equals(xunJianDataUploadEvent.getType())) {
      // 表卡保存成功
      if (biaoKaListBeans != null && biaoKaListBeans.size() > 0) {
        BiaoKaListBean biaoKaListBean = biaoKaListBeans.get(0);
        biaoKaListBean.setIsSave(true);
        biaoKaListBean.setIsUploadImage(false);
        biaoKaListBean.setIsCommit(false);
        biaoKaListBean.setXUNJIANZT("已巡检");
        mRemoteinSpectionListPresenter.saveXunjianBKlistBean(biaoKaListBean);


        //TODO modify
        mBiaoKaListBeans.clear();
        mLocalXunJianTasks = mRemoteinSpectionListPresenter.getLocalXunJianTasks(Const.XUNJIANTASK_TYPE);

        switch (screenType) {
          case "yichao":
            //显示本地数据
            for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
              List<BiaoKaListBean> yccBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"yc");

              mBiaoKaListBeans.addAll(yccBiaoKaListBeans);
            }
            break;
          case "weichao":
            for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
              List<BiaoKaListBean> wcBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"wc");
              mBiaoKaListBeans.addAll(wcBiaoKaListBeans);
            }
            break;
          case "all":
          default:
            for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
              List<BiaoKaListBean> wcBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"wc");
              List<BiaoKaListBean> yccBiaoKaListBeans = mRemoteinSpectionListPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"yc");
              mBiaoKaListBeans.addAll(wcBiaoKaListBeans);
              mBiaoKaListBeans.addAll(yccBiaoKaListBeans);
            }
            filterSort();
            break;
        }
        mRemoteinSpectionListAdapter.setTaskList(mBiaoKaBeans,mBiaoKaListBeans);


        /*这里不能在requestData，因为这样保存和提交成功后都会去请求接口，而保存的时候请求到的数据和提交后
         * 请求到的数据不一样，后面保存后请求到的数据又会覆盖掉提交后获取到的数据*/
//                requestData();
      }
    } else if (XunJianDataUploadEvent.TYPE_IMAGE_UPLOADED.equals(xunJianDataUploadEvent.getType())) {
      // 表卡提交图片成功，提交数据成功
      if (biaoKaListBeans != null && biaoKaListBeans.size() > 0) {
        BiaoKaListBean biaoKaListBean = biaoKaListBeans.get(0);
        biaoKaListBean.setIsSave(false);
        biaoKaListBean.setIsUploadImage(true);
        biaoKaListBean.setIsCommit(true);
        biaoKaListBean.setXUNJIANZT("已巡检");
        mRemoteinSpectionListPresenter.saveXunjianBKlistBean(biaoKaListBean);
        requestData();
        if (!isHistory) {
          mEventBus.post(new BiaoKaListSaveEvent(xunJianDataUploadEvent.getBiaoKaWholeEntity().getRENWUMC(),
            xunJianDataUploadEvent.getBiaoKaWholeEntity().getRENWUID(), true));
        }
      }
    }
    //TODO
//        if (mAdapter.getData().size() == 0) {
//            finish();
//        }
  }
  private void uploadImage(final BiaoKaWholeEntity biaoKaWholeEntity, int position, final int size) {
    Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
    }.getType();

    List<ImageItem> list1 = GsonUtils.fromJson(biaoKaWholeEntity.getImages1(), imageListType);
    List<ImageItem> list2 = GsonUtils.fromJson(biaoKaWholeEntity.getImages2(), imageListType);
    List<ImageItem> list3 = GsonUtils.fromJson(biaoKaWholeEntity.getImages3(), imageListType);
    List<ImageItem> list4 = GsonUtils.fromJson(biaoKaWholeEntity.getImages4(), imageListType);
    List<ImageItem> list6 = GsonUtils.fromJson(biaoKaWholeEntity.getImages6(), imageListType);
    list1.addAll(list2);
    list1.addAll(list3);
    list1.addAll(list4);
    if (list6 != null) {
      list1.addAll(list6);
    }

    final List<File> files = new ArrayList<>();
    for (int i = 0; i < list1.size(); i++) {
      File file = new File(list1.get(i).path);
      if (file.exists()) {
        files.add(file);
      }
    }

    List<File> vedioFile = new ArrayList<>();
    if (biaoKaWholeEntity.getVedioUrl() != null && !"".equals(biaoKaWholeEntity.getVedioUrl())) {
      File file = new File(biaoKaWholeEntity.getVedioUrl());
      if (file.exists()) {
        vedioFile.add(file);
      }
    }

    if (vedioFile.size() > 0) {
      EasyHttp.post(URL.BASE_CS_URL+ URL.UploadFile)
        .params("S_LEIXING", "SP")
        .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
        .addFileParams("S_ZHAOPIANLJ", vedioFile, null)
        .cacheMode(CacheMode.NO_CACHE)
        .retryCount(0)
        .sign(true)
        .timeStamp(true)
        .execute(String.class)
        .flatMap(new io.reactivex.functions.Function<String, ObservableSource<String>>() {
          @Override
          public ObservableSource<String> apply(String s) throws Exception {
            ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
            if ("00".equals(resultEntity.getMsgCode())) {
              return EasyHttp.post(URL.BASE_CS_URL+URL.UploadFile)
                .params("S_LEIXING", "XUNJIAN")
                .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
                .addFileParams("S_ZHAOPIANLJ", files, null)
                .cacheMode(CacheMode.NO_CACHE)
                .sign(true)
                .timeStamp(true)
                .execute(String.class);
            } else {
              return io.reactivex.Observable.just(s);
            }
          }
        })
        .subscribe(new BaseSubscriber<String>() {
          @Override
          public void onError(ApiException e) {
            Log.d( "onError 1135: " , e.getMessage());
            ToastUtils.showLong(e.getMessage());
            commited++;
            if (commited >= size) {
              mProgressBar.setProgress(100);
              mTvProcess.setText("当前进度\n" + 100 + "%");
              dissProgressDialog();
            } else {
              mProgressBar.setProgress(commited * 100 / size);
              mTvProcess.setText("当前进度\n" + commited * 100 / size + "%");
            }
          }

          @Override
          public void onNext(String s) {
            super.onNext(s);
            ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
            if ("00".equals(resultEntity.getMsgCode())) {
              //图片上传成功
              biaoKaWholeEntity.setIsUploadImage(true);
              mRemoteinSpectionListPresenter.saveBiaoKaWholeEntityDao(biaoKaWholeEntity);
//              long is = GreenDaoUtils.getInstance().getDaoSession(getApplicationContext())
//                .getBiaoKaWholeEntityDao().insertOrReplace(biaoKaWholeEntity);
              mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_IMAGE_UPLOADED, biaoKaWholeEntity));
            } else {
              //图片上传失败
              ToastUtils.showLong(resultEntity.getMsgInfo());
            }
          }

          @Override
          public void onComplete() {
            super.onComplete();
            commited++;
            if (commited >= size) {
              mProgressBar.setProgress(100);
              mTvProcess.setText("当前进度\n" + 100 + "%");
              dissProgressDialog();
            } else {
              mProgressBar.setProgress(commited * 100 / size);
              mTvProcess.setText("当前进度\n" + commited * 100 / size + "%");
            }
          }
        });
    } else {
      EasyHttp.post(URL.BASE_CS_URL+URL.UploadFile)
        .params("S_LEIXING", "XUNJIAN")
        .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
        .addFileParams("S_ZHAOPIANLJ", files, null)
        .cacheMode(CacheMode.NO_CACHE)
        .execute(new SimpleCallBack<String>() {
          @Override
          public void onError(ApiException e) {
            Log.d( "onError 1188: " , e.getMessage());
            ToastUtils.showLong(e.getMessage());
            commited++;
            if (commited >= size) {
              mProgressBar.setProgress(100);
              mTvProcess.setText("当前进度\n" + 100 + "%");
              dissProgressDialog();
            } else {
              mProgressBar.setProgress(commited * 100 / size);
              mTvProcess.setText("当前进度\n" + commited * 100 / size + "%");
            }
          }

          @Override
          public void onSuccess(String s) {
            ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
            if ("00".equals(resultEntity.getMsgCode())) {
              //图片上传成功
              biaoKaWholeEntity.setIsUploadImage(true);
              mRemoteinSpectionListPresenter.saveBiaoKaWholeEntityDao(biaoKaWholeEntity);
              mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_IMAGE_UPLOADED, biaoKaWholeEntity));
            } else {
              //图片上传失败
              ToastUtils.showLong(resultEntity.getMsgInfo());
            }
          }

          @Override
          public void onCompleted() {
            super.onCompleted();
            commited++;
            if (commited >= size) {
              mProgressBar.setProgress(100);
              mTvProcess.setText("当前进度\n" + 100 + "%");
              dissProgressDialog();
            } else {
              mProgressBar.setProgress(commited * 100 / size);
              mTvProcess.setText("当前进度\n" + commited * 100 / size + "%");
            }
          }
        });
    }
  }

  private void filterSort(){
    if (mBiaoKaListBeans == null){
      return;
    }
    Collections.sort(mBiaoKaListBeans, new Comparator<BiaoKaListBean>() {
      @Override
      public int compare(BiaoKaListBean o1, BiaoKaListBean o2) {
        return Integer.parseInt(StringUtils.isEmpty(o1.getXUHAO()) ? "1" : o1.getXUHAO())
          - Integer.parseInt(StringUtils.isEmpty(o2.getXUHAO()) ? "1" : o2.getXUHAO());
//        return o1.getXUHAO().compareTo(o2.getXUHAO());
      }
    });

  }
}
