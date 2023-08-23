package com.sh3h.meterreading.ui.InspectionInput;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.bean.ImageItem;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.XunJianDataUploadEvent;
import com.sh3h.meterreading.ui.InspectionInput.lr.ChaoBiaoHistoryListFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.LRDuoMeitiFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.LROperatingFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.MyFragmentPagerAdapter;
import com.sh3h.meterreading.ui.InspectionInput.lr.QianFeiXXFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.WaiFuHistoryListFragment;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.ResultEntity;
import com.squareup.otto.Bus;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.subsciber.ProgressSubscriber;

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

/**
 * 辅助验收详情
 */
public class InspectionInputActivity  extends ParentActivity implements View.OnClickListener, InspectionInputMvpView, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
  private static final String TAG = "InspectionInputActivity";
  @Inject
  InspectionInputPresenter mInspectionInputPresenter;
  @Inject
  Bus mEventBus;
  @Bind(R.id.loading_process)
  SmoothProgressBar mSmoothProgressBar;
  @Bind(R.id.avl_toolbar)
  Toolbar mToolbar;


  @Bind(R.id.tablayout)
  TabLayout mTabLayout;
  @Bind(R.id.vp)
  ViewPager mViewPager;
//
//  @Bind(R.id.delay_order_details_rb)
//  RadioButton mDetailsRadioBtn;

  private RemoteLRFragment mRemoteLRFragment;
  private Fragment mCurrentFragment;
  private ParentFragment mJibenXXFragment, mQianFeiListFragment, mDuoMeitiFragment;
  private ParentFragment mXunjianLRFragment;
  private ParentFragment mChaoBiaoHistory, mWaifuHistory;
  private MyFragmentPagerAdapter mPagerAdapter;
  private List<ParentFragment> mFragmentList;
  private String renWuId;
  private String xiaoGenhao;
  private String renWuMc;
  public boolean isHistory;
  private TextView tvLast;
  private TextView tvNext;
  private int biaokaPosition;
  private String type;
  private BiaoKaBean biaoKaBean;
  private BiaoKaListBean biaoKaListBean;
  private MenuItem mSave, mCommit;
  private BiaoKaWholeEntity biaoKaWholeEntity = new BiaoKaWholeEntity();
  private ArrayList<BiaoKaListBean> biaokaListBeans = new ArrayList<>();
  private LinearLayout mLLWentiShangbao;
  private String title;


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ll_wentishangbao:
//        Intent intent = new Intent(this, WentiShangbaoActivity.class);
//        intent.putExtra(Const.BEAN, biaoKaBean);
//        startActivity(intent);
        break;
      case R.id.tv_last:
        if (!((LROperatingFragment) mXunjianLRFragment).isNeedSave() && !((LRDuoMeitiFragment) mDuoMeitiFragment).isNeedSave()) {
          // 没有任何回填数据和图片
          biaokaPosition = biaokaPosition - 1;
          int lastPosition = biaokaPosition + 1 - 1;
          lasChange(lastPosition);
          notifyFragment();
        } else {
          if (!isHistory) {
            if (!saveData(false)) {
              // 有完整回填数据和必要图片，且保存成功
              biaokaPosition = biaokaPosition - 1;
              int lastPosition = biaokaPosition + 1 - 1;
              lasChange(lastPosition);
              notifyFragment();
            } else {
              // 缺少回填数据或必要图片
              new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("此工单未回填完成，是否保存已回填的部分数据切换到上一条？")
                .setCancelable(true)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    //TODO 保存回填的部分数据
//                                            biaoKaWholeEntity.setIsSave(true);
                    biaoKaWholeEntity.setRENWUMC(renWuMc);
                    biaoKaWholeEntity.setDetailInfo(GsonUtils.toJson(biaoKaBean));
                    BiaoKaWholeEntity newBiaoKaWholeEntity = ((LROperatingFragment) mXunjianLRFragment).getLuRuData(biaoKaWholeEntity);
                    BiaoKaWholeEntity newWholeEntity = ((LRDuoMeitiFragment) mDuoMeitiFragment).getImagesInfo(newBiaoKaWholeEntity);
                    biaokaPosition = biaokaPosition - 1;
                    int lastPosition = biaokaPosition + 1 - 1;
                    lasChange(lastPosition);
                    notifyFragment();
                  }
                })
                .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    biaokaPosition = biaokaPosition - 1;
                    int lastPosition = biaokaPosition + 1 - 1;
                    lasChange(lastPosition);
                    notifyFragment();
                  }
                })
                .create().show();
            }
          } else {
            biaokaPosition = biaokaPosition - 1;
            int lastPosition = biaokaPosition + 1 - 1;
            lasChange(lastPosition);
            notifyFragment();
          }
        }
        break;
      case R.id.tv_next:
        if (!((LROperatingFragment) mXunjianLRFragment).isNeedSave() && !((LRDuoMeitiFragment) mDuoMeitiFragment).isNeedSave()) {
          // 没有任何回填数据和图片
          biaokaPosition = biaokaPosition + 1;
          int nextPosition1 = biaokaPosition + 1 + 1;
          nextchange(nextPosition1);
          notifyFragment();
        } else {
          if (!isHistory) {
            if (!saveData(false)) {
              // 有完整回填数据和必要图片，且保存成功
              biaokaPosition = biaokaPosition + 1;
              int nextPosition1 = biaokaPosition + 1 + 1;
              nextchange(nextPosition1);
              notifyFragment();
            } else {
              // 缺少回填数据或必要图片
              new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("此工单未回填完成，是否保存已回填的部分数据切换到下一条？")
                .setCancelable(true)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    //TODO 保存回填的部分数据
                    biaoKaWholeEntity.setRENWUMC(renWuMc);
                    biaoKaWholeEntity.setDetailInfo(GsonUtils.toJson(biaoKaBean));
                    BiaoKaWholeEntity newBiaoKaWholeEntity = ((LROperatingFragment) mXunjianLRFragment).getLuRuData(biaoKaWholeEntity);
                    BiaoKaWholeEntity newWholeEntity = ((LRDuoMeitiFragment) mDuoMeitiFragment).getImagesInfo(newBiaoKaWholeEntity);

                    biaokaPosition = biaokaPosition + 1;
                    int nextPosition1 = biaokaPosition + 1 + 1;
                    nextchange(nextPosition1);
                    notifyFragment();
                  }
                })
                .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    biaokaPosition = biaokaPosition + 1;
                    int nextPosition1 = biaokaPosition + 1 + 1;
                    nextchange(nextPosition1);
                    notifyFragment();
                  }
                })
                .create().show();
            }
          } else {
            biaokaPosition = biaokaPosition + 1;
            int nextPosition1 = biaokaPosition + 1 + 1;
            nextchange(nextPosition1);
            notifyFragment();
          }
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void onError(String s) {

  }

  @Override
  public void onSuccess(String s) {

  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (isHistory){

    }else {
      getMenuInflater().inflate(R.menu.menu_remote, menu);
      mSave = menu.findItem(R.id.mtl_action_save);
      mCommit = menu.findItem(R.id.mtl_action_commit);
    }


    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.mtl_action_save:
        //保存数据
        if (!isHistory) {
          if (saveData(true)) {
            return false;
          }
          upLoadDataImage();
        } else {
          upLoadImage();
        }
        break;
      case R.id.mtl_action_commit:
        //提交数据
        if (!isHistory) {
          if (saveData(true)) {
            return false;
          }
          upLoadDataImage();
        } else {
          upLoadImage();
        }
        break;

      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onloadBiaokaList(List<BiaoKaBean> biaoKaBeans,boolean isnext) {
    if (biaoKaBeans != null){
      biaoKaBean=biaoKaBeans.get(0);
      mInspectionInputPresenter.loadBiaokaList(xiaoGenhao,isnext);
    }
  }

  @Override
  public void onloadBiaokaListData(List<BiaoKaListBean> biaoKaBeans, boolean isnext) {


  }

  @Override
  public void onSaveBiaoKaWholeEntity(Boolean aBoolean,Boolean isfnish) {
    if (aBoolean){
      if (isfnish){
        finish();
      }
    }
  }

  @Override
  public void loadBiaokaListBeans(List<BiaoKaListBean> biaoKaBeans, String type) {


  }

  @Override
  public void loadBiaokaWholeEntBeans(List<BiaoKaWholeEntity> biaoKaBeans) {

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_remote_lr);
    getActivityComponent().inject(this);
    ButterKnife.bind(this);

    title = getIntent().getStringExtra(Const.TITLE);
    if (title != null && title.equals("抄表巡检")){
      mToolbar.setTitle("抄表巡检");
    }
    setSupportActionBar(mToolbar);
    setActionBarBackButtonEnable();
    mInspectionInputPresenter.attachView(this);
    mEventBus.register(this);

    mSmoothProgressBar.setVisibility(View.INVISIBLE);
    initView(savedInstanceState);
    initData();
  }






  /**
   * 切换Fragment
   *
   * @param fragment
   */
  private void changeFragment(Fragment fragment) {
    if (fragment == null) {
      return;
    }
    if (mCurrentFragment != fragment) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      if (mCurrentFragment != null) {
        transaction.hide(mCurrentFragment);
      }
      if (!fragment.isAdded()) { // 先判断是否被add过
        // 隐藏当前的fragment，add下一个到Activity中
        transaction.add(R.id.vp, fragment, fragment.getClass().getName()).commit();
      } else {
        transaction.hide(mCurrentFragment).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
      }
      mCurrentFragment = fragment;
    }
  }


  /**
   * 初始化控件
   */
  private void initView(Bundle savedInstanceState) {
    if (savedInstanceState == null){
      biaokaListBeans=new ArrayList<>();
      tvLast = (TextView) findViewById(R.id.tv_last);
      tvLast.setOnClickListener(this);
      tvNext = (TextView) findViewById(R.id.tv_next);
      tvNext.setOnClickListener(this);
      renWuId = getIntent().getStringExtra(Const.RENWUID);
      renWuMc = getIntent().getStringExtra(Const.RENWUMC);
      xiaoGenhao = getIntent().getStringExtra(Const.XIAOGENHAO);
      isHistory = getIntent().getBooleanExtra(Const.IS_HISTORY, false);
      type = getIntent().getStringExtra(Const.TYPE);
      biaokaPosition = getIntent().getIntExtra(Const.POSITION, 0);
      if (isHistory){
        tvNext.setVisibility(View.GONE);
        tvLast.setVisibility(View.GONE);
      }

      List<BiaoKaListBean> ycBiaoKaList = mInspectionInputPresenter.getWcOrYcBiaoKaListBean(renWuMc,"yc");
      List<BiaoKaListBean> wcBiaoKaList = mInspectionInputPresenter.getWcOrYcBiaoKaListBean(renWuMc,"wc");
      biaokaListBeans = new ArrayList<>();
      biaokaListBeans.addAll(ycBiaoKaList);
      biaokaListBeans.addAll(wcBiaoKaList);
      filterSort();
      mTabLayout = (TabLayout) findViewById(R.id.tablayout);
      mViewPager = (ViewPager) findViewById(R.id.vp);
      mLLWentiShangbao = (LinearLayout) findViewById(R.id.ll_wentishangbao);
//      mLLWentiShangbao.setVisibility(isHistory ? View.GONE : View.VISIBLE);
      mLLWentiShangbao.setOnClickListener(this);

      tvLast = (TextView) findViewById(R.id.tv_last);
      tvLast.setOnClickListener(this);
      tvNext = (TextView) findViewById(R.id.tv_next);
      tvNext.setOnClickListener(this);

      if (1 == biaokaListBeans.size()) {
        tvLast.setEnabled(false);
        tvLast.setTextColor(getResources().getColor(R.color.color_text_black));
        tvLast.setBackgroundResource(R.color.white2);
        tvNext.setEnabled(false);
        tvNext.setTextColor(getResources().getColor(R.color.color_text_black));
        tvNext.setBackgroundResource(R.color.white2);
      } else {
        lastOrNextChange(biaokaPosition + 1);
      }

    }


  }

  private void initData() {

    List<BiaoKaBean> list = mInspectionInputPresenter.getBiaoKaBean(xiaoGenhao);
    if (list.size() <= 0) {
      ToastUtils.showShort("没有获取到详情的表卡详情数据，请退出重新刷新试试");
      return;
    }
    biaoKaBean = list.get(list.size() - 1);//获取最新的表卡详情，之前可能会有重复的
//
    List<BiaoKaWholeEntity> biaoKaWholeEntitys = mInspectionInputPresenter.getBiaoKaWholeEntity(renWuId,xiaoGenhao);
    if (biaoKaWholeEntitys != null && biaoKaWholeEntitys.size() > 0) {
      biaoKaWholeEntity = biaoKaWholeEntitys.get(0);
    } else {
      biaoKaWholeEntity = new BiaoKaWholeEntity();
    }

    mFragmentList = new ArrayList<>();
//        mJibenXXFragment = new LRJibenXXFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(Const.POSITION, biaokaPosition);
    bundle.putString(Const.RENWUID, renWuId);
    bundle.putString(Const.RENWUMC, renWuMc);
    bundle.putString(Const.TYPE, type == null ? "" : type);
    bundle.putString(Const.XIAOGENHAO, biaoKaBean.getXIAOGENH());
    bundle.putParcelable(Const.BEAN, biaoKaBean);
    bundle.putParcelable(Const.BIAOKAWHOLEBEAN, biaoKaWholeEntity);
    bundle.putString(Const.TITLE, title);
    mXunjianLRFragment = new LROperatingFragment();
    mDuoMeitiFragment = new LRDuoMeitiFragment();
    mQianFeiListFragment = new QianFeiXXFragment();
    mChaoBiaoHistory = new ChaoBiaoHistoryListFragment();
    mWaifuHistory = new WaiFuHistoryListFragment();
    mXunjianLRFragment.setArguments(bundle);
    mDuoMeitiFragment.setArguments(bundle);
    mQianFeiListFragment.setArguments(bundle);
    mChaoBiaoHistory.setArguments(bundle);
    mWaifuHistory.setArguments(bundle);
//        mFragmentList.add(mJibenXXFragment);
    mFragmentList.add(mXunjianLRFragment);
    mFragmentList.add(mDuoMeitiFragment);
    mFragmentList.add(mQianFeiListFragment);
    mFragmentList.add(mChaoBiaoHistory);
    mFragmentList.add(mWaifuHistory);
    mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
    mViewPager.setAdapter(mPagerAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.getTabAt(0).setText("巡检录入");
    mTabLayout.getTabAt(1).setText("多媒体");
    mTabLayout.getTabAt(2).setText("欠费信息");
    mTabLayout.getTabAt(3).setText("历史抄表");
    mTabLayout.getTabAt(4).setText("历史外复");
    mViewPager.addOnPageChangeListener(this);
    mViewPager.setOffscreenPageLimit(4);
  }

  /**
   * 初始化上一个和下一个按钮
   *
   * @param position
   */
  private void lastOrNextChange(int position) {
    if (position <= 1) {
      tvLast.setEnabled(false);
      tvLast.setTextColor(getResources().getColor(R.color.color_text_black));
      tvLast.setBackgroundResource(R.color.white2);
      tvNext.setEnabled(true);
      tvNext.setTextColor(getResources().getColor(R.color.white));
      tvNext.setBackgroundResource(R.color.colorPrimary);
    } else if (position < biaokaListBeans.size()) {
      tvLast.setEnabled(true);
      tvLast.setTextColor(getResources().getColor(R.color.white));
      tvLast.setBackgroundResource(R.color.colorPrimary);
      tvNext.setEnabled(true);
      tvNext.setTextColor(getResources().getColor(R.color.white));
      tvNext.setBackgroundResource(R.color.colorPrimary);
    } else {
      tvLast.setEnabled(true);
      tvLast.setTextColor(getResources().getColor(R.color.white));
      tvLast.setBackgroundResource(R.color.colorPrimary);
      tvNext.setEnabled(false);
      tvNext.setTextColor(getResources().getColor(R.color.color_text_black));
      tvNext.setBackgroundResource(R.color.white2);
    }
  }
  @Override
  public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//    switch (checkedId) {
//      case R.id.record_lr://详细
//        initRemoteLRFragment();
//        break;
//
//      case R.id.record_media://处理
//        //来源 我的工单记录&&已上传 || 自开单记录&&已上传 intent to HistoryHandleOrderFragment
//        initRemoteInfromationFragment();
//        break;
//
//      case R.id.record_detail_info://多媒体
//        initMutimediaFragment();
//        break;
//
//    }
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {

  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    LogUtil.i(TAG, "---onActivityResult---1");
    if (resultCode == RESULT_OK) {
      if(requestCode == 1000 || requestCode == 1001){
        Object object = mPagerAdapter.instantiateItem(mViewPager, 1);
        if (object instanceof LRDuoMeitiFragment) {
          LRDuoMeitiFragment lrDuoMeitiFragment = (LRDuoMeitiFragment) object;
          LogUtil.i(TAG, "---onActivityResult---" + lrDuoMeitiFragment);
          lrDuoMeitiFragment.processResult(requestCode, resultCode, data);
        }
      }else {
        if (data!=null) {
          Bundle bundle = data.getExtras();
          if (bundle == null) {
            return;
          }
          if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
            //获取最终扫描数据
            String result = bundle.getString(CodeUtils.RESULT_STRING);
            ToastUtils.showLong(result.toString());
            Object object = mPagerAdapter.instantiateItem(mViewPager, 0);
            if (object instanceof LROperatingFragment) {
              LROperatingFragment lrOperatingFragment = (LROperatingFragment) object;
              LogUtil.i(TAG, "---onActivityResult---" + lrOperatingFragment);
              lrOperatingFragment.processResult(result);
            }
            Log.d(TAG, "onActivityResult: "+result);
          } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
            ToastUtils.showLong("识别错误");
          }
        }
      }

    }
    LogUtil.i(TAG, "---onActivityResult---2");
  }

  /**
   * 保存回填数据和图片
   *
   * @param isToast
   * @return
   */
  private boolean saveData(boolean isToast) {
    if (!((LROperatingFragment) mXunjianLRFragment).isCanSave(isToast)) {
      if (mViewPager.getCurrentItem() != 0) {
        mViewPager.setCurrentItem(0);
      }
      return true;
    }

    if (!((LRDuoMeitiFragment) mDuoMeitiFragment).isCanSave(isToast)) {
      if (mViewPager.getCurrentItem() != 1) {
        mViewPager.setCurrentItem(1);
      }
      return true;
    }


    biaoKaWholeEntity.setIsSave(true);
    biaoKaWholeEntity.setRENWUMC(renWuMc);
    biaoKaWholeEntity.setDetailInfo(GsonUtils.toJson(biaoKaBean));
    BiaoKaWholeEntity newBiaoKaWholeEntity = ((LROperatingFragment) mXunjianLRFragment).getLuRuData(biaoKaWholeEntity);
    BiaoKaWholeEntity newWholeEntity = ((LRDuoMeitiFragment) mDuoMeitiFragment).getImagesInfo(newBiaoKaWholeEntity);
//    long ls = GreenDaoUtils.getInstance().getDaoSession(BaseApplication.getInstance())
//            .getBiaoKaWholeEntityDao().insertOrReplace(newWholeEntity);
    mInspectionInputPresenter.SaveBiaoKaWholeEntity2(newWholeEntity);
//    long ls = GreenDaoUtils.getInstance().getDaoSession(BaseApplication.getInstance())
//      .getBiaoKaWholeEntityDao().insertOrReplace(newWholeEntity);

    mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_SAVED, biaoKaWholeEntity));

    return false;
  }
  boolean isSuccess = false;

  private ProgressResponseCallBack responseCallBack = new ProgressResponseCallBack() {
    @Override
    public void onResponseProgress(long bytesRead, long contentLength, boolean done) {

    }
  };
  /**
   * 提交图片和回填数据
   */
  private void upLoadDataImage() {
    IProgressDialog iProgressDialog = new IProgressDialog() {
      @Override
      public Dialog getDialog() {
        ProgressDialog progressDialog = new ProgressDialog(InspectionInputActivity.this);
        progressDialog.setMessage("正在上传巡检数据...");
        return progressDialog;
      }
    };

    Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
    }.getType();

    if (biaoKaWholeEntity != null) {
//            if (!biaoKaWholeEntity.getIsUploadImage()) {
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
        Log.e("EasyHttp---------", "filePath=" + list1.get(i).path);
        if (file.exists()) {
          files.add(file);
        }
      }

      List<File> vedioFile = new ArrayList<>();
      if (biaoKaWholeEntity.getVedioUrl() != null && !"".equals(biaoKaWholeEntity.getVedioUrl())) {
        File file = new File(biaoKaWholeEntity.getVedioUrl());
        Log.e("EasyHttp---------", "filePath=" + biaoKaWholeEntity.getVedioUrl());
        if (file.exists()) {
          vedioFile.add(file);
        }
      }

      final Observable<String> upImageObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.UploadFile)
              .params("S_LEIXING", "XUNJIAN")
              .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
              .addFileParams("S_ZHAOPIANLJ", files, responseCallBack)
              .cacheMode(CacheMode.NO_CACHE)
              .cacheKey(this.getClass().getSimpleName())
              .execute(String.class);

      Observable<String> upDataObservable = null;
      if (Const.XUNJIANTASK_TYPE.equals(type)) {
        upDataObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.setChaoBiaoInfo_CB)
                .params("RENWUID", biaoKaWholeEntity.getRENWUID())
                .params("XIAOGENH", biaoKaWholeEntity.getXIAOGENH())
                .params("XUNJIANCM", biaoKaWholeEntity.getXUNJIANCM())
                .params("TXM", biaoKaWholeEntity.getTXM())
                .params("CHAOBIAOZT", biaoKaWholeEntity.getCbzt())
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
                .execute(String.class);
      } else {
        upDataObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.setChaoBiaoInfo)
                .params("RENWUID", biaoKaWholeEntity.getRENWUID())
                .params("XIAOGENH", biaoKaWholeEntity.getXIAOGENH())
                .params("XUNJIANCM", biaoKaWholeEntity.getXUNJIANCM())
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
                .execute(String.class);
      }

      Observable<String> uploadObservable = null;
      if (vedioFile.size() > 0) {
        uploadObservable = EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.UploadFile)
                .params("S_LEIXING", "SP")
                .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
                .addFileParams("S_ZHAOPIANLJ", vedioFile, null)
                .cacheMode(CacheMode.NO_CACHE)
                .retryCount(0)
                .sign(true)
                .timeStamp(true)
                .execute(String.class)
                .flatMap(new Function<String, ObservableSource<String>>() {
                  @Override
                  public ObservableSource<String> apply(String s) throws Exception {
                    ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                    if ("00".equals(resultEntity.getMsgCode())) {
                      return upImageObservable;
                    } else {
                      resultEntity.setMsgCode(Const.UPLOADDATA_ERROR_CODE);
                      return Observable.just(GsonUtils.toJson(resultEntity));
                    }
                  }
                });
      } else {
        uploadObservable = upImageObservable;
      }

      final Observable<String> finalUpDataObservable = upDataObservable;
      uploadObservable
              .flatMap(new Function<String, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(String s) throws Exception {
                  ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                  if (Const.UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                    resultEntity.setMsgCode(Const.UPLOADDATA_ERROR_CODE);
                    return Observable.just(GsonUtils.toJson(resultEntity));
                  } else {
                    if ("00".equals(resultEntity.getMsgCode())) {
                      isSuccess = true;
                      return finalUpDataObservable;
                    } else {
                      resultEntity.setMsgCode(Const.UPLOADDATA_ERROR_CODE);
                      return Observable.just(GsonUtils.toJson(resultEntity));
                    }
                  }
                }
              })
              .subscribe(new ProgressSubscriber<String>(InspectionInputActivity.this, iProgressDialog) {
                @Override
                public void onStart() {
                  super.onStart();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                }

                @Override
                public void onNext(String s) {
                  super.onNext(s);
                  ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                  if (Const.UPLOADDATA_ERROR_CODE.equals(resultEntity.getMsgCode())) {
                    //图片上传失败
                    ToastUtils.showLong(resultEntity.getMsgInfo());
                  } else if ("00".equals(resultEntity.getMsgCode())) {

                    //数据图片都上传成功
                    biaoKaWholeEntity.setIsCommit(true);
                    biaoKaWholeEntity.setIsUploadImage(true);
                    biaoKaWholeEntity.setIsSave(false);
                    mInspectionInputPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntity);
                    mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_IMAGE_UPLOADED, biaoKaWholeEntity));
                    finish();
                  } else {
                    //图片上传成功，数据上传失败
                    ToastUtils.showLong("图片上传成功，" + resultEntity.getMsgInfo());
                    biaoKaWholeEntity.setIsCommit(false);
                    biaoKaWholeEntity.setIsSave(true);
                    biaoKaWholeEntity.setIsUploadImage(true);
                    mInspectionInputPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntity);
                    mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_COMMITED, biaoKaWholeEntity));
                    finish();
                  }
                }

                @Override
                public void onError(ApiException e) {
                  super.onError(e);
                  if (isSuccess) {
                    //图片上传成功，数据上传失败
                    ToastUtils.showLong("图片上传成功，" + e.getMessage());
                    biaoKaWholeEntity.setIsCommit(false);
                    biaoKaWholeEntity.setIsSave(true);
                    biaoKaWholeEntity.setIsUploadImage(true);
                    mInspectionInputPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntity);
                    mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_COMMITED, biaoKaWholeEntity));
                  } else {
                    ToastUtils.showLong(e.getMessage());
                  }
                  finish();
                }

                @Override
                public void onCancelProgress() {
                  super.onCancelProgress();
                }
              });


    }

  }
  private void upLoadImage() {
    IProgressDialog iProgressDialog = new IProgressDialog() {
      @Override
      public Dialog getDialog() {
        ProgressDialog progressDialog = new ProgressDialog(InspectionInputActivity.this);
        progressDialog.setMessage("正在上传巡检图片...");
        return progressDialog;
      }
    };

    Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
    }.getType();

    if (biaoKaWholeEntity != null) {
//            if (!biaoKaWholeEntity.getIsUploadImage()) {
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
        Log.e("EasyHttp---------", "filePath=" + list1.get(i).path);
        if (file.exists()) {
          files.add(file);
        }
      }

      List<File> vedioFile = new ArrayList<>();
      if (biaoKaWholeEntity.getVedioUrl() != null && !"".equals(biaoKaWholeEntity.getVedioUrl())) {
        File file = new File(biaoKaWholeEntity.getVedioUrl());
        Log.e("EasyHttp---------", "filePath=" + biaoKaWholeEntity.getVedioUrl());
        if (file.exists()) {
          vedioFile.add(file);
        }
      }

      if (vedioFile.size() > 0) {
        EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.UploadFile)
                .params("S_LEIXING", "SP")
                .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
                .addFileParams("S_ZHAOPIANLJ", vedioFile, null)
                .cacheMode(CacheMode.NO_CACHE)
                .retryCount(0)
                .sign(true)
                .timeStamp(true)
                .execute(String.class)
                .flatMap(new Function<String, ObservableSource<String>>() {
                  @Override
                  public ObservableSource<String> apply(String s) throws Exception {
                    ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                    if ("00".equals(resultEntity.getMsgCode())) {
                      return EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.UploadFile)
                              .params("S_LEIXING", "XUNJIAN")
                              .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
                              .addFileParams("S_ZHAOPIANLJ", files, null)
                              .cacheMode(CacheMode.NO_CACHE)
                              .sign(true)
                              .timeStamp(true)
                              .execute(String.class);
                    } else {
                      return Observable.just(s);
                    }
                  }
                })
                .subscribe(new ProgressSubscriber<String>(InspectionInputActivity.this, iProgressDialog) {
                  @Override
                  public void onStart() {
                    super.onStart();
                  }

                  @Override
                  public void onComplete() {
                    super.onComplete();
                  }

                  @Override
                  public void onNext(String s) {
                    super.onNext(s);
                    ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                    if ("00".equals(resultEntity.getMsgCode())) {
                      //图片上传成功
                      biaoKaWholeEntity.setIsUploadImage(true);
                      mInspectionInputPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntity);
                      mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_IMAGE_UPLOADED, biaoKaWholeEntity));
                    } else {
                      //图片上传失败
                      ToastUtils.showLong(resultEntity.getMsgInfo());
                    }
                    finish();
                  }

                  @Override
                  public void onError(ApiException e) {
                    super.onError(e);
                    ToastUtils.showLong(e.getMessage());
                    finish();
                  }

                  @Override
                  public void onCancelProgress() {
                    super.onCancelProgress();
                  }
                });
      } else {
        EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.UploadFile)
                .params("S_LEIXING", "XUNJIAN")
                .params("S_GONGDANBH", biaoKaWholeEntity.getRENWUID())
                .addFileParams("S_ZHAOPIANLJ", files, null)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new ProgressDialogCallBack<String>(iProgressDialog) {
                  @Override
                  public void onStart() {
                    super.onStart();
                  }

                  @Override
                  public void onSuccess(String s) {
                    ResultEntity resultEntity = GsonUtils.fromJson(s, ResultEntity.class);
                    if ("00".equals(resultEntity.getMsgCode())) {
                      //图片上传成功
                      biaoKaWholeEntity.setIsUploadImage(true);
                      mInspectionInputPresenter.SaveBiaoKaWholeEntity(biaoKaWholeEntity);
                      mEventBus.post(new XunJianDataUploadEvent(XunJianDataUploadEvent.TYPE_IMAGE_UPLOADED, biaoKaWholeEntity));
                    } else {
                      //图片上传失败
                      ToastUtils.showLong(resultEntity.getMsgInfo());
                    }
                    finish();
                  }

                  @Override
                  public void onError(ApiException e) {
                    super.onError(e);
                    ToastUtils.showLong(e.getMessage());
                    finish();
                  }

                  @Override
                  public void onCancelProgress() {
                    super.onCancelProgress();
                  }
                });
      }
    }
  }
  /**
   * 下一个按钮切换改变
   *
   * @param position
   */
  private void nextchange(int position) {
    if (position > 1 && position <= biaokaListBeans.size()) {
      tvLast.setEnabled(true);
      tvLast.setTextColor(getResources().getColor(R.color.white));
      tvLast.setBackgroundResource(R.color.colorPrimary);
      tvNext.setEnabled(true);
      tvNext.setTextColor(getResources().getColor(R.color.white));
      tvNext.setBackgroundResource(R.color.colorPrimary);
    }
    if (position > biaokaListBeans.size()) {
      tvLast.setEnabled(true);
      tvLast.setTextColor(getResources().getColor(R.color.white));
      tvLast.setBackgroundResource(R.color.colorPrimary);
      tvNext.setEnabled(false);
      tvNext.setTextColor(getResources().getColor(R.color.color_text_black));
      tvNext.setBackgroundResource(R.color.white2);
    }
  }
  /**
   * 上一个与下一个按钮切换后数据刷新
   */
  private void notifyFragment() {
    renWuId = biaokaListBeans.get(biaokaPosition).getS_RENWUID();
    renWuMc = biaokaListBeans.get(biaokaPosition).getS_RENWUMC();
    xiaoGenhao = biaokaListBeans.get(biaokaPosition).getS_CID();
//    mInspectionInputPresenter.loadBiaoka(xiaoGenhao,true);
    biaoKaBean = mInspectionInputPresenter.getBiaoKaBean(xiaoGenhao).get(0);

    List<BiaoKaWholeEntity> biaoKaWholeEntitys = mInspectionInputPresenter.getBiaoKaWholeEntity((long) (renWuId + xiaoGenhao).hashCode());
    if (biaoKaWholeEntitys != null && biaoKaWholeEntitys.size() > 0) {
      biaoKaWholeEntity = biaoKaWholeEntitys.get(0);
    } else {
      biaoKaWholeEntity = new BiaoKaWholeEntity();
    }

    Bundle bundle = new Bundle();
    bundle.putInt(Const.POSITION, biaokaPosition);
    bundle.putString(Const.RENWUID, renWuId);
    bundle.putString(Const.RENWUMC, renWuMc);
    bundle.putString(Const.XIAOGENHAO, biaoKaBean.getXIAOGENH());
    bundle.putParcelable(Const.BEAN, biaoKaBean);
    bundle.putParcelable(Const.BIAOKAWHOLEBEAN, biaoKaWholeEntity);
    bundle.putString(Const.TITLE, title);



//    mXunjianLRFragment.setArguments(bundle);
//    mDuoMeitiFragment.setArguments(bundle);
//    mQianFeiListFragment.setArguments(bundle);
//    mChaoBiaoHistory.setArguments(bundle);
//    mWaifuHistory.setArguments(bundle);

    if (mViewPager.getCurrentItem() != 0) {
      mViewPager.setCurrentItem(0);
    }
//    ((LROperatingFragment) mXunjianLRFragment).initData();
//    ((LRDuoMeitiFragment) mDuoMeitiFragment).initData();

    ((LROperatingFragment) mXunjianLRFragment).initData2(bundle);
    ((LRDuoMeitiFragment) mDuoMeitiFragment).initData2(bundle);
    ((QianFeiXXFragment) mQianFeiListFragment).initData2(bundle);
    ((ChaoBiaoHistoryListFragment) mChaoBiaoHistory).initData2(bundle);
    ((WaiFuHistoryListFragment) mWaifuHistory).initData2(bundle);


  }

  /**
   * 上一个按钮切换改变
   *
   * @param position
   */
  private void lasChange(int position) {
    if (position < 1) {
      tvLast.setEnabled(false);
      tvLast.setTextColor(getResources().getColor(R.color.color_text_black));
      tvLast.setBackgroundResource(R.color.white2);
      tvNext.setEnabled(true);
      tvNext.setTextColor(getResources().getColor(R.color.white));
      tvNext.setBackgroundResource(R.color.colorPrimary);
    }
    if (position >= 1 && position <= biaokaListBeans.size()) {
      tvLast.setEnabled(true);
      tvLast.setTextColor(getResources().getColor(R.color.white));
      tvLast.setBackgroundResource(R.color.colorPrimary);
      tvNext.setEnabled(true);
      tvNext.setTextColor(getResources().getColor(R.color.white));
      tvNext.setBackgroundResource(R.color.colorPrimary);
    }
  }

  private void filterSort(){
    if (biaokaListBeans == null){
      return;
    }
    Collections.sort(biaokaListBeans, new Comparator<BiaoKaListBean>() {
      @Override
      public int compare(BiaoKaListBean o1, BiaoKaListBean o2) {
        return Integer.parseInt(StringUtils.isEmpty(o1.getXUHAO()) ? "1" : o1.getXUHAO())
          - Integer.parseInt(StringUtils.isEmpty(o2.getXUHAO()) ? "1" : o2.getXUHAO());
      }
    });

  }
}
