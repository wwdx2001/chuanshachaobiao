package com.sh3h.meterreading.ui.remoteinspection_task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.XunjianTaskAdapter;
import com.sh3h.meterreading.event.BiaoKaListSaveEvent;
import com.sh3h.meterreading.ui.InspectionInput.tools.ClickUtils;
import com.sh3h.meterreading.ui.InspectionInput.tools.PickerViewUtils;
import com.sh3h.meterreading.ui.RemoteinSpectionOHistoryListActivity.RemoteinSpectionOHistoryListActivity;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.remoteinspection.RemoteinSpectionListActivity;
import com.sh3h.meterreading.ui.remoteinspection.RemoteinSpectionListMvpView;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.ResultBean;
import com.sh3h.serverprovider.entity.XunJianTaskBean;
import com.sh3h.thirdparty.gesturelock.commonutil.Constants;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.subsciber.BaseSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 巡检任务列表
 *
 * @author xiaochao.dev@gmail.com
 * @date 2019/12/11 14:50
 */
public class RemoteinSpectionTaskHistoryActivity extends ParentActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, RemoteinSpectionListMvpView {

    private final String TAG = "FuheXunjianTaskActivity";

    public static final String XUNJIANTASK = "chaobiaorw";
    public static final String BIAOKABEAN = "chaobiaoyc";
    public static final String BIAOKALIAT = "chaobiaosj";
    public static final String SAVECHAOBIAOBKXX = "chaobiaobkxx";

    private RecyclerView mRecyclerView;
    private TextView mTvPaiFaTime, mTvAmount;
    private EditText mEtSearch;
    private MenuItem mItem;
    private XunjianTaskAdapter mAdapter;
    private List<XunJianTaskBean> mDataList;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isHistory;
    private String type;
    //    private ProgressDialog iProgressDialog;
    private MenuItem menuItem;
    private String deviceId;
    private String mType;
    private List<XunJianTaskBean> mLocalXunJianTasks;
    private List<BiaoKaListBean> mBiaoKaListBeans;

    @Inject
    RemoteinSpectionTaskPresenter mRemoteinSpectionTaskPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xunjian_task;
    }

    @Override
    protected void initView1() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        deviceId = DeviceUtils.getAndroidID();
        if (StringUtils.isEmpty(deviceId)) {
            deviceId = DeviceUtils.getUniqueDeviceId() + "";
        }
        isHistory = getIntent().getBooleanExtra(Const.IS_HISTORY, true);
        getSupportActionBar().setTitle(isHistory ? "远传巡检历史" : "远传巡检任务列表");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_xujian_task);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mTvPaiFaTime = (TextView) findViewById(R.id.tv_xunjian_task_time);
        mTvAmount = (TextView) findViewById(R.id.tv_xunjian_task_amount);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mEtSearch.setVisibility(View.GONE);
//        mTvPaiFaTime.setOnClickListener(this);
        mEtSearch.setOnEditorActionListener(enterListenter);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRefreshLayout.setEnabled(!isHistory);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isHistory) {
                    getXunJianTask(true);
                } /*else {
                    // 历史中刷新获取本地数据
                    List<XunJianTaskBean> xunjianTasks = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
                            .getXunJianTaskBeanDao().loadAll();
                    for (XunJianTaskBean xunJianTaskBean : xunjianTasks) {
                        long count = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
                                .getBiaoKaListBeanDao().queryBuilder()
                                .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(xunJianTaskBean.getRENWUMC()))
                                .whereOr(BiaoKaListBeanDao.Properties.IsCommit.eq(1), BiaoKaListBeanDao.Properties.IsUploadImage.eq(1))
                                .count();

                        if (count > 0) {
                            mDataList.add(xunJianTaskBean);
                        }
                    }*/

                //排序
//                    ComparatorDate c = new ComparatorDate();
//                    Collections.sort(mDataList, c);
//
//                    mAdapter.notifyDataSetChanged();
//                    mTvAmount.setText("任务数量：" + mAdapter.getData().size());
//                }
            }
        });
    }

    @Override
    protected void initData1() {
        getActivityComponent().inject(this);
        mRemoteinSpectionTaskPresenter.attachView(this);
        mDataList = new ArrayList<>();
        mLocalXunJianTasks = new ArrayList<>();
        mBiaoKaListBeans = new ArrayList<>();
        mAdapter = new XunjianTaskAdapter(R.layout.item_xunjian_task, mDataList);
        mAdapter.setIsHistory(isHistory);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mTvAmount.setText("任务数量：" + mAdapter.getData().size());
        Intent intent = getIntent();
        initParams(intent.getExtras());

        if (checkPermissions()) {
            initConfig();
        }
    }

    ArrayList<String> renwumcLists = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void requestData1() {
        super.requestData1();
        List<XunJianTaskBean> xunjianTasks = mRemoteinSpectionTaskPresenter.getXunJianFuHeTaskBean(Const.XUNJIANTASK_TYPE);
        if (xunjianTasks.size() <= 0) {
            // 如果本地数据为空则从网络获取数据
            if (!isHistory) {
//                iProgressDialog = new ProgressDialog(XunjianTaskActivity.this);
//                iProgressDialog.setMessage("正在获取巡检数据...");
                mRefreshLayout.setRefreshing(true);
                getXunJianTask(true);
            }
        } else {
            mDataList.clear();
            if (isHistory) {
                // 如果本地数据不为空，且在历史中则加载含有已提交的任务
                for (XunJianTaskBean xunJianTaskBean : xunjianTasks) {
                    renwumcLists.add(xunJianTaskBean.getRENWUMC());
                    List<BiaoKaListBean> biaoKaListBean = mRemoteinSpectionTaskPresenter.getTiJiaoBiaoKaListBean(xunJianTaskBean.getRENWUMC(), 1);
                    long count = biaoKaListBean != null ? biaoKaListBean.size() : 0;

                    if (count > 0) {
                        long ytj = mRemoteinSpectionTaskPresenter.getZCBiaoKaListBeanCount(xunJianTaskBean.getRENWUMC(), "ytj");
                        long ybc = mRemoteinSpectionTaskPresenter.getZCBiaoKaListBeanCount(xunJianTaskBean.getRENWUMC(), "ybc");
                        xunJianTaskBean.setYIWANC((int) ytj);
                        xunJianTaskBean.setYIDENGJ((int) ybc);
                        mDataList.add(xunJianTaskBean);
                    }
                }

                //排序
                ComparatorDate c = new ComparatorDate();
                Collections.sort(mDataList, c);

                mAdapter.notifyDataSetChanged();
                mTvAmount.setText("任务数量：" + mAdapter.getData().size());
            } else {
                // 如果本地数据不为空，且不在历史中则加载含有未提交表卡的任务
                for (XunJianTaskBean xunJianTaskBean : xunjianTasks) {
                    renwumcLists.add(xunJianTaskBean.getRENWUMC());
                    if (!xunJianTaskBean.getIsFinish()) {
                        mDataList.add(xunJianTaskBean);
                    }
                }

                //排序
                ComparatorDate c = new ComparatorDate();
                Collections.sort(mDataList, c);

                mAdapter.notifyDataSetChanged();
                mTvAmount.setText("任务数量：" + mAdapter.getData().size());
//                iProgressDialog = new ProgressDialog(XunjianTaskActivity.this);
//                iProgressDialog.setMessage("正在刷新巡检数据...");
                mRefreshLayout.setRefreshing(true);
                getXunJianTask(true);
            }

        }
    }

    private void getXunJianTask(boolean isNeedBiaoKa) {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
        EasyHttp.post(URL.BASE_XUNJIAN_URL + URL.getXunJianTask_cb)
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
            Observable<String> mobileObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL + URL.getbiaoka_CB)
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
            Observable<String> mobileObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getBiaoKaList_CB)
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
            List<XunJianTaskBean> xjTasks = mRemoteinSpectionTaskPresenter.getXunJianTaskBean(xunJianTaskBean.getRENWUMC());
            //设置该巡检任务类型为复核类型
            xunJianTaskBean.setXunjianTaskType(Const.XUNJIANTASK_TYPE);
            if (xjTasks != null && xjTasks.size() > 0) {
                XunJianTaskBean xjTaskBean = xjTasks.get(0);
                // 如果数据库存在，将赋值已抄表完成标志
                xunJianTaskBean.setIsFinish(xjTaskBean.getIsFinish());
            }
        }
        // 保存巡检任务
        mRemoteinSpectionTaskPresenter.SaveXunJianTasks2(xunjianTasks);

        mType = XUNJIANTASK;
        switch (mType) {
            case XUNJIANTASK:
                mType = BIAOKALIAT;
                //保存表卡列表

                for (BiaoKaListBean biaoKaListBean : biaoKaList) {
                    biaoKaListBean.setTYPE("远传巡检");
                    List<BiaoKaListBean> bkLists = mRemoteinSpectionTaskPresenter.getBiaoKaListBean(biaoKaListBean.getS_RENWUID());
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
                mRemoteinSpectionTaskPresenter.deleteBiaoKaListBean();

                mRemoteinSpectionTaskPresenter.saveXunjianBKlist2(biaoKaList);
                break;
            case BIAOKALIAT:
                mType = BIAOKABEAN;
                // 保存表卡详情
                Log.e("helloworld", "详情数据" + biaoKaBeans.size());
                mRemoteinSpectionTaskPresenter.saveXunJianBK(biaoKaBeans);
                break;
            case BIAOKABEAN:
                //保存完成后查询巡检任务显示在列表中
                break;
            default:
                break;
        }
        mRemoteinSpectionTaskPresenter.saveBiaoKaBean(biaoKaBeans);
        mBiaoKaListBeans.clear();
        mLocalXunJianTasks.clear();
        mLocalXunJianTasks = mRemoteinSpectionTaskPresenter.getLocalXunJianTasks(Const.XUNJIANTASK_TYPE);

        mAdapter.setNewData(xunjianTasks);
        mTvAmount.setText("任务数量：" + xunjianTasks.size());
//    for (int i = 0; i < mLocalXunJianTasks.size(); i++) {
//      List<BiaoKaListBean> wcBiaoKaListBeans = mRemoteinSpectionTaskPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"wc");
//      List<BiaoKaListBean> yccBiaoKaListBeans = mRemoteinSpectionTaskPresenter.getWcorYcBiaoKalistbean(mLocalXunJianTasks.get(i).getRENWUMC(),"yc");
//      mBiaoKaListBeans.addAll(wcBiaoKaListBeans);
//      mBiaoKaListBeans.addAll(yccBiaoKaListBeans);
//    }
//    mRemoteinSpectionListAdapter.setTaskList(null,mBiaoKaListBeans);
        mRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_xunjian_task_time:
//                showPaiFaTimeDialog();
//                break;
            default:
                break;
        }
    }

    /**
     * 弹出派发时间选择框
     */
    private void showPaiFaTimeDialog() {
        KeyboardUtils.hideSoftInput(this);
        PickerViewUtils.getInstance().showTimeDialog("选择派发时间", new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvPaiFaTime.setText(TimeUtils.date2String(date, "yyyy-MM"));
                if (menuItem != null) {
                    menuItem.setVisible(true);
                }
//                queryData(TimeUtils.date2String(date, "yyyy-MM"), "date");
            }
        }, new boolean[]{true, true, false, false, false, false});
    }

    /**
     * 弹出派发时间选择框
     */
    private void showPaiFaTimeDialogHistory() {
        KeyboardUtils.hideSoftInput(this);
        PickerViewUtils.getInstance().showTimeDialog("选择派发时间", new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvPaiFaTime.setText(TimeUtils.date2String(date, "yyyy-MM"));
                if (menuItem != null) {
                    menuItem.setVisible(true);
                }
                queryData(TimeUtils.date2String(date, "yyyy-MM"), "date");
            }
        }, new boolean[]{true, true, false, false, false, false});
    }

    /**
     * 按条件查询数据
     *
     * @param cond
     * @param type
     */
    private void queryData(String cond, String type) {
        switch (type) {
//            case "date":
//                if (!isHistory) {
//                    List<XunJianTaskBean> xunJianTaskBeans = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                            .getXunJianTaskBeanDao().queryBuilder()
//                            .where(XunJianTaskBeanDao.Properties.IsFinish.eq(false))
//                            .where(XunJianTaskBeanDao.Properties.PAIFASJ.like(cond + "%"))
//                            .where(XunJianTaskBeanDao.Properties.XunjianTaskType.eq(Const.XUNJIANTASK_TYPE))
//                            .orderDesc(XunJianTaskBeanDao.Properties.PAIFASJ)
//                            .list();
////                if (xunJianTaskBeans.size() > 0) {
//                    mDataList.clear();
//                    mDataList.addAll(xunJianTaskBeans);
//                    mAdapter.notifyDataSetChanged();
//                    mTvAmount.setText("任务数量：" + mAdapter.getData().size());
////                } else {
////                    ToastUtils.showLong("未查询到相关数据");
////                }
//                } else {
//                    List<XunJianTaskBean> xunjianTasks = GreenDaoUtils.getInstance()
//                            .getDaoSession(MainApplication.getInstance())
//                            .getXunJianTaskBeanDao()
//                            .queryBuilder()
//                            .where(XunJianTaskBeanDao.Properties.PAIFASJ.like(cond + "%"))
//                            .where(XunJianTaskBeanDao.Properties.XunjianTaskType.eq(Const.XUNJIANTASK_TYPE))
//                            .orderDesc(XunJianTaskBeanDao.Properties.PAIFASJ)
//                            .list();
//                    mDataList.clear();
//                    for (XunJianTaskBean xunJianTaskBean : xunjianTasks) {
//                        long count = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                                .getBiaoKaListBeanDao().queryBuilder()
//                                .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(xunJianTaskBean.getRENWUMC()))
//                                .whereOr(BiaoKaListBeanDao.Properties.IsCommit.eq(1), BiaoKaListBeanDao.Properties.IsUploadImage.eq(1))
//                                .count();
//
//                        if (count > 0) {
//                            mDataList.add(xunJianTaskBean);
//                        }
//                    }
//                    mAdapter.notifyDataSetChanged();
//                    mTvAmount.setText("任务数量：" + mAdapter.getData().size());
//                }
//                break;
            case "xiaogenhao":
                List<BiaoKaListBean> biaoKaListBeans = mRemoteinSpectionTaskPresenter.getSearchBiaoKaListBean(cond, isHistory);
                if (biaoKaListBeans.size() > 0) {
                    List<XunJianTaskBean> xunJianTaskList = mRemoteinSpectionTaskPresenter.getXunJianTaskBean(biaoKaListBeans.get(0).getS_RENWUMC());
                    if (xunJianTaskList.size() > 0) {
                        mDataList.clear();
                        mDataList.addAll(xunJianTaskList);
                        mAdapter.setNewData(mDataList);
                        mTvAmount.setText("任务数量：" + mAdapter.getData().size());
                    } else {
                        ToastUtils.showLong("未查询到相关数据");
                    }
                } else {
                    ToastUtils.showLong("未查询到相关数据");
                }
//                Intent intent = new Intent(RemoteinSpectionTaskActivity.this, RemoteinSpectionListActivity.class);
//                intent.putExtra(Const.IS_HISTORY, isHistory);
//                intent.putExtra(Const.XIAOGENHAO, cond);
//                intent.putStringArrayListExtra("renwumclist", renwumcLists);
//                intent.putExtra("type", 1);
//                startActivity(intent);
                break;
//            default:
//                if (!isHistory) {
//                    List<XunJianTaskBean> xunJianTaskBeans = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                            .getXunJianTaskBeanDao().queryBuilder()
//                            .where(XunJianTaskBeanDao.Properties.IsFinish.eq(false))
//                            .where(XunJianTaskBeanDao.Properties.XunjianTaskType.eq(Const.XUNJIANTASK_TYPE))
//                            .orderDesc(XunJianTaskBeanDao.Properties.PAIFASJ)
//                            .list();
//                    mDataList.clear();
//                    mDataList.addAll(xunJianTaskBeans);
//                    mAdapter.notifyDataSetChanged();
//                    mTvAmount.setText("任务数量：" + mAdapter.getData().size());
//                } else {
//                    List<XunJianTaskBean> xunjianTasks = GreenDaoUtils.getInstance()
//                            .getDaoSession(MainApplication.getInstance())
//                            .getXunJianTaskBeanDao()
//                            .queryBuilder()
//                            .where(XunJianTaskBeanDao.Properties.XunjianTaskType.eq(Const.XUNJIANTASK_TYPE))
//                            .orderDesc(XunJianTaskBeanDao.Properties.PAIFASJ)
//                            .list();
//                    mDataList.clear();
//                    for (XunJianTaskBean xunJianTaskBean : xunjianTasks) {
//                        long count = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                                .getBiaoKaListBeanDao().queryBuilder()
//                                .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(xunJianTaskBean.getRENWUMC()))
//                                .whereOr(BiaoKaListBeanDao.Properties.IsCommit.eq(1), BiaoKaListBeanDao.Properties.IsUploadImage.eq(1))
//                                .count();
//
//                        if (count > 0) {
//                            mDataList.add(xunJianTaskBean);
//                        }
//                    }
//                    mAdapter.notifyDataSetChanged();
//                    mTvAmount.setText("任务数量：" + mAdapter.getData().size());
//                }
//                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right_text, menu);
//        menuItem = menu.findItem(R.id.menu_wenti_shangbao);
//        menuItem.setTitle("重置派发时间");
//        menuItem.setVisible(false);

        mItem = menu.findItem(R.id.menu_right_text);
//        if (isHistory) {
//            mItem.setTitle("清除缓存");
//            mItem.setVisible(true);
//        } else {
        mItem.setVisible(false);
//        }
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (!ClickUtils.singleClick(System.currentTimeMillis())) {
//            return true;
//        }
//        if (item.getItemId() == R.id.menu_right_text) {
//            if (mDataList != null && mDataList.size() > 0) {
//                boolean isNeedDialog = false;
//                for (XunJianTaskBean xunJianTaskBean : mDataList) {
//                    long imageUnLoadCount = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                            .getBiaoKaListBeanDao().queryBuilder()
//                            .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(xunJianTaskBean.getRENWUMC()))
//                            .where(BiaoKaListBeanDao.Properties.IsCommit.eq(1))
//                            .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
//                            .count();
//                    if (imageUnLoadCount > 0) {
//                        isNeedDialog = true;
//                        break;
//                    }
//                }
//
//                if (isNeedDialog) {
//                    new AlertDialog.Builder(this)
//                            .setTitle("温馨提示")
//                            .setMessage("检测到部分表卡图片未提交成功，确定要清除缓存吗？")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    cleanData();
//                                }
//                            })
//                            .setNegativeButton("取消", null)
//                            .create().show();
//                } else {
//                    cleanData();
//                }
//
//            } else {
//                ToastUtils.showLong("暂无缓存数据");
//            }
//        } else if (item.getItemId() == R.id.menu_wenti_shangbao) {
//            mTvPaiFaTime.setText("");
//            if (menuItem != null) {
//                menuItem.setVisible(false);
//            }
//            //TODO 重置数据
//            queryData("", "");
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void cleanData() {
//        new AlertDialog.Builder(this).setTitle("提示")
//                .setMessage("是否确定清除本地缓存？清除缓存后，已完成表卡数据将被清除。")
//                .setNegativeButton("取消", null)
//                .setPositiveButton("我已了解，确定清除", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int j) {
//                        List<XunJianTaskBean> xunJianTaskBeans = new ArrayList<>();
//                        List<BiaoKaListBean> biaoKaList = new ArrayList<>();
//                        List<BiaoKaBean> biaoKaBeans = new ArrayList<>();
//                        List<BiaoKaWholeEntity> biaoKaWholeEntityList = new ArrayList<>();
//
//                        for (XunJianTaskBean xunJianTaskBean : mDataList) {
//                            long bkCount = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                                    .getBiaoKaListBeanDao().queryBuilder()
//                                    .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(xunJianTaskBean.getRENWUMC()))
//                                    .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0)).count();
//                            if (bkCount <= 0) {
//                                xunJianTaskBeans.add(xunJianTaskBean);
//                            }
//
//                            List<BiaoKaListBean> biaoKaListBeans = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                                    .getBiaoKaListBeanDao().queryBuilder()
//                                    .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(xunJianTaskBean.getRENWUMC()))
//                                    .whereOr(BiaoKaListBeanDao.Properties.IsCommit.eq(1), BiaoKaListBeanDao.Properties.IsUploadImage.eq(1)).list();
//                            biaoKaList.addAll(biaoKaListBeans);
//                        }
//
//                        for (BiaoKaListBean biaoKaListBean : biaoKaList) {
//                            List<BiaoKaBean> biaoKaBeanList = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                                    .getBiaoKaBeanDao().queryBuilder()
//                                    .where(BiaoKaBeanDao.Properties.XIAOGENH.eq(biaoKaListBean.getS_CID())).list();
//                            biaoKaBeans.addAll(biaoKaBeanList);
//
//                            List<BiaoKaWholeEntity> biaoKaWholeEntitys = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                                    .getBiaoKaWholeEntityDao().queryBuilder()
//                                    .where(BiaoKaWholeEntityDao.Properties.ID.eq((long) (biaoKaListBean.getS_RENWUID() + biaoKaListBean.getS_CID()).hashCode())).list();
//                            biaoKaWholeEntityList.addAll(biaoKaWholeEntitys);
//                        }
//
//                        Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
//                        }.getType();
//
//                        for (BiaoKaWholeEntity biaoKaWholeEntity : biaoKaWholeEntityList) {
//                            List<ImageItem> list1 = GsonUtils.fromJson(biaoKaWholeEntity.getImages1(), imageListType);
//                            List<ImageItem> list2 = GsonUtils.fromJson(biaoKaWholeEntity.getImages2(), imageListType);
//                            List<ImageItem> list3 = GsonUtils.fromJson(biaoKaWholeEntity.getImages3(), imageListType);
//                            List<ImageItem> list4 = GsonUtils.fromJson(biaoKaWholeEntity.getImages4(), imageListType);
//                            List<ImageItem> list5 = GsonUtils.fromJson(biaoKaWholeEntity.getImages5(), imageListType);
//                            list5.addAll(list4);
//                            list5.addAll(list3);
//                            list5.addAll(list2);
//                            list5.addAll(list1);
//
//                            for (int i = 0; i < list5.size(); i++) {
//                                File file = new File(list5.get(i).path);
//                                if (file.exists()) {
//                                    FileUtils.delete(file);
//                                }
//                            }
//
//                            if (biaoKaWholeEntity.getVedioUrl() != null && !"".equals(biaoKaWholeEntity.getVedioUrl())) {
//                                File file = new File(biaoKaWholeEntity.getVedioUrl());
//                                if (file.exists()) {
//                                    FileUtils.delete(file);
//                                }
//                            }
//                        }
//
//                        GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getBiaoKaWholeEntityDao().deleteInTx(biaoKaWholeEntityList);
//                        GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getBiaoKaBeanDao().deleteInTx(biaoKaBeans);
//                        GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getBiaoKaListBeanDao().deleteInTx(biaoKaList);
//                        GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance()).getXunJianTaskBeanDao().deleteInTx(xunJianTaskBeans);
//
//                        mDataList.clear();
//                        mAdapter.notifyDataSetChanged();
//                        mTvAmount.setText("任务数量：" + mAdapter.getData().size());
//                    }
//                }).create().show();
//    }

    //    @SingleClick
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!ClickUtils.singleClick(System.currentTimeMillis())) {
            return;
        }
//        if (mRefreshLayout != null && !mRefreshLayout.isRefreshing()) {
        Intent intent;
        if (isHistory) {
            intent = new Intent(this, RemoteinSpectionOHistoryListActivity.class);
        } else {
            intent = new Intent(this, RemoteinSpectionListActivity.class);
        }
        intent.putExtra(Const.IS_HISTORY, isHistory);
        intent.putExtra(Const.RENWUMC, mAdapter.getData().get(position).getRENWUMC());
        XunJianTaskBean bean = mAdapter.getData().get(position);
        intent.putExtra(Const.BEAN, bean);
        intent.putExtra("type", 1);
        startActivity(intent);
//        } else {
//            ToastUtils.showShort("正在刷新数据，请稍后...");
//        }
    }

    private TextView.OnEditorActionListener enterListenter = new TextView.OnEditorActionListener() {
        /**
         * 参数说明
         * @param v 被监听的对象
         * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
         * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
         * @return 返回你的动作
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                ToastUtils.showLong("根据销根号查询");
                if (!"".equals(mEtSearch.getText().toString().trim())) {
                    queryData(mEtSearch.getText().toString().trim(), "xiaogenhao");
                } else {
                    ToastUtils.showLong("销根号不能为空");
                }

            }
            return false;
        }
    };

    /**
     * 表卡提交后刷新对应巡检任务
     *
     * @param biaoKaListSaveEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBiaoKaListSaveEvent(BiaoKaListSaveEvent biaoKaListSaveEvent) {
        List<XunJianTaskBean> xunJianTaskBeans = mRemoteinSpectionTaskPresenter.getXunJianTaskBean(biaoKaListSaveEvent.getRENWUMC());
        if (xunJianTaskBeans != null && xunJianTaskBeans.size() > 0) {
            XunJianTaskBean xunJianTaskBean = xunJianTaskBeans.get(0);
            List<BiaoKaListBean> listBean = mRemoteinSpectionTaskPresenter.getBiaoKaListBean(biaoKaListSaveEvent.getRENWUID());

            long zongshu = listBean != null ? listBean.size() : 0;
//            long saveCount = GreenDaoUtils.getInstance().getDaoSession(MainApplication.getInstance())
//                    .getBiaoKaListBeanDao().queryBuilder()
//                    .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(biaoKaListSaveEvent.getRENWUMC()))
//                    .where(BiaoKaListBeanDao.Properties.IsSave.eq(1))
//                    .count();
            List<BiaoKaListBean> biaoKaListBean = mRemoteinSpectionTaskPresenter.getTiJiaoBiaoKaListBean(biaoKaListSaveEvent.getRENWUMC(), 1);
            long commitCount = biaoKaListBean != null ? biaoKaListBean.size() : 0;

            if (commitCount >= zongshu) {
                xunJianTaskBean.setIsFinish(true);
            } else {
                xunJianTaskBean.setIsFinish(false);
            }

//            xunJianTaskBean.setYIWANC((int) (saveCount + commitCount));
            mRemoteinSpectionTaskPresenter.SaveXunJianTasks2((ArrayList<XunJianTaskBean>) xunJianTaskBeans);
            mRefreshLayout.setRefreshing(true);
            getXunJianTask(false);
//            requestData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onSaveXunJianBK(List<BiaoKaBean> biaoKaBean) {

    }

    @Override
    public void onloadXunJianData(List<BiaoKaBean> biaoKaBean) {

    }

    @Override
    public void onloadXunJianListData(List<BiaoKaListBean> biaoKaBean) {

    }

    @Override
    public void onIsSaveBiaoKaWholeEntity(List<BiaoKaWholeEntity> biaoKaWholeEntities) {

    }

    @Override
    public void onError(String s) {

    }

    @Override
    public void onSuccess(String s) {

    }

    @Override
    public void onsaveXunJianBK(Boolean aBoolean) {

    }

    @Override
    public void onloadXunJianListData2(List<BiaoKaListBean> biaoKaBeans, String type) {

    }

    /**
     * 按时间排序，最近的日期显示在上面
     */
    public static class ComparatorDate implements Comparator {

        public static final String TAG = "ComparatorDate";

        @SuppressLint("SimpleDateFormat")
        private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public int compare(Object obj1, Object obj2) {
            XunJianTaskBean t1 = (XunJianTaskBean) obj1;
            XunJianTaskBean t2 = (XunJianTaskBean) obj2;
//        //   return t1.getTradetime().compareTo(t2.getTradetime());  // 时间格式不好，不然可以直接这样比较
//        Date d1, d2;
//        try {
//            d1 = format.parse(t1.getDENGJIRQ());
//            d2 = format.parse(t2.getDENGJIRQ());
//        } catch (ParseException e) {
//            // 解析出错，则不进行排序
//            LogUtils.e(TAG, "ComparatorDate--compare--SimpleDateFormat.parse--error");
//            return 0;
//        }
//        if (d1.before(d2)) {
//            return 1;
//        } else {
//            return -1;
//        }
            try {
                Date dt1 = format.parse(t1.getPAIFASJ());
                Date dt2 = format.parse(t2.getPAIFASJ());
                if (dt1.getTime() > dt2.getTime()) {
                    return -1;
                } else if (dt1.getTime() < dt2.getTime()) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
}
