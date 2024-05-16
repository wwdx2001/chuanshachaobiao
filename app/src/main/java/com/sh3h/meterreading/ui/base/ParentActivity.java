package com.sh3h.meterreading.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.Coordinate;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.service.SyncService;
import com.sh3h.meterreading.service.SyncType;
import com.sh3h.meterreading.ui.map.MapParamEx;
import com.sh3h.meterreading.ui.map.MapStatusEx;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.XJXXWordBean;
import com.sh3h.serverprovider.entity.XunJianXXWords;
import com.sh3h.thirdparty.gesturelock.commonutil.Constants;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class ParentActivity extends BaseActivity implements SwipeBackActivityBase {
    private static final String TAG = "ParentActivity";

    private static final String ACCOUNT = "account";
    //向子程序发送用户id
    private static final String USER_ID = "userId";
    //向子程序发送用户名
    protected static final String USER_NAME = "userName";

    private static final String EXTENDED_INFO = "extendedInfo";

    private static final String PHOTO_QUALITY = "photoQuality";

    private static final String NETWORK = "network";


    private SwipeBackActivityHelper mHelper;
    protected boolean needRefresh;
    protected boolean isActive;
    protected boolean needDialog;
    private long lastSyncTime;
    private static String account;
    private static int userId;
    private static String userName;
    private static String extendedInfo;
    private boolean netWork;
    private String photoQuality;
    private boolean isFirst = true;
    private boolean isSuccess = false;

    protected CompositeSubscription mSubscription;
    @Inject
    DataManager mDataManager;
    @Inject
    ConfigHelper mConfigHelper;


    private static final int PERMISSION_REQUEST_CODE = 1000; // 系统权限管理页面的参数
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA
    };

    public ParentActivity() {
        super();

        needRefresh = false;
        isActive = false;
        needDialog = false;
        lastSyncTime = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
        }
        setBothAnimation();

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        MainApplication.get(this).bindHostService();
        mSubscription = new CompositeSubscription();

        initView1();
        initData1();
        initAdapter1();
        initListener1();
        requestData1();
    }
    protected int getLayoutId() {
        return -1;
    }


    protected void requestData1() {
    }

    protected void initListener1() {
    }

    protected void initAdapter1() {
    }

    protected void initData1() {

    }

    protected void initView1() {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //MainApplication.get(this).destroyGpsLocation();

        mSubscription.unsubscribe();
        mSubscription = null;
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if ((v == null) && (mHelper != null)) {
            return mHelper.findViewById(id);
        }
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    protected void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setActionBarBackButtonEnable() {
        ActionBar actionBar = getSupportActionBar();
        //设置ActionBar左边默认的图标是否可用
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
    }

    protected void setActionBarSubTitle(String title) {
        if (!TextUtil.isNullOrEmpty(title)) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setSubtitle(title);
        }
    }

    public void initParams(Bundle bundle) {
        if (bundle != null) {
            account = TextUtil.getString(bundle.getString(ACCOUNT));
            userId = bundle.getInt(USER_ID);
            userName = TextUtil.getString(bundle.getString(USER_NAME));
            SPUtils.getInstance().put(Constants.USERNAME, userName);
            SPUtils.getInstance().put(Const.S_YUANGONGH, account);

            extendedInfo = TextUtil.getString(bundle.getString(EXTENDED_INFO));
            try {
                JSONObject jsonObject = new JSONObject(extendedInfo);
                netWork = !jsonObject.getBoolean(NETWORK);
                photoQuality = jsonObject.getString(PHOTO_QUALITY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            account = "0018";
            userId = 18;
            userName = "高富帅";
            extendedInfo = "";
        }

        if (!isSuccess) {
            getWordsList(new SimpleCallBack<XunJianXXWords>() {
                @Override
                public void onError(ApiException e) {

                }

                @Override
                public void onSuccess(XunJianXXWords xunJianXXWords) {
                    isSuccess = true;
                }
            });
        }

        if (isFirst) {
            //删除过期的文件
            deleteFile();
            isFirst = false;
        }
    }

    public boolean isNeedDialog() {
        return needDialog;
    }

    public void setNeedDialog(boolean needDialog) {
        this.needDialog = needDialog;
    }

    public void uploadRecordAndMedias(int taskId,
                                      String volume,
                                      String customerId,
                                      int orderNumber) {
        if ((taskId <= 0)
                || TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_RECORD_MEDIAS.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        intent.putExtra(SyncService.CUSTOMER_ID, customerId);
        intent.putExtra(SyncService.ORDER_NUMBER, orderNumber);
        startService(intent);
    }

    public void uploadOutRecordAndMedias(int taskId,
                                         String volume,
                                         String customerId,
                                         int orderNumber) {
        if ((taskId <= 0)
                || TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_OUT_RECORD_MEDIAS.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        intent.putExtra(SyncService.CUSTOMER_ID, customerId);
        intent.putExtra(SyncService.ORDER_NUMBER, orderNumber);
        startService(intent);
    }

    public void uploadSamplingAndMedias(int taskId,
                                        String volume,
                                        String customerId,
                                        int orderNumber) {
        if ((taskId < 0)
                || TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_SAMPLING_MEDIAS.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        intent.putExtra(SyncService.CUSTOMER_ID, customerId);
        intent.putExtra(SyncService.ORDER_NUMBER, orderNumber);
        startService(intent);
    }

    protected void uploadVolume(int taskId, String volume) {
        if ((taskId <= 0) || TextUtil.isNullOrEmpty(volume)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_VOLUME.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        startService(intent);
    }

    protected void uploadDownloadDelay() {
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_DOWNLOADING_DELAY_ALL.ordinal());
        startService(intent);
    }

    protected void updateVolumeCards(int taskId, String volume) {
        if ((taskId <= 0) || TextUtil.isNullOrEmpty(volume)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPDATING_VOLUME_CARD.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        startService(intent);
    }

    protected void downloadVolume(int taskId, String volume) {
        if ((taskId <= 0) || TextUtil.isNullOrEmpty(volume)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.DOWNLOADING_VOLUME.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        startService(intent);
    }

    protected void downloadSamplingVolume(int taskId) {
        if (taskId <= 0) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.DOWNLOADING_SAMPLING.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        startService(intent);
    }

    public void uploadAndDownloadAllSamplingTasks() {
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_DOWNLOADING_ALL_SAMPLING_TASKS.ordinal());
        startService(intent);
    }

    protected void uploadSamplingVolume(int taskId) {
        if (taskId <= 0) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_SAMPLING.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        startService(intent);
    }

    public void syncWaiFuCBSJS() {
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.SYNC_WAIFUCBSJS.ordinal());
        startService(intent);
    }

    public void uploadWaifuCBSJSAndMedias() {
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_WAIFUCBSJS_MEDIAS.ordinal());
        startService(intent);
    }


    public void downloadDetailInfo(int taskId,
                                   String volume,
                                   String customerId) {
        if (TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.DOWNLOADING_DETAIL_INFO.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        intent.putExtra(SyncService.CUSTOMER_ID, customerId);
        startService(intent);
    }

//    public void uploadAndDownloadAllData() {
//        Intent intent = SyncService.getStartIntent(this);
//        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_DOWNLOADING_ALL_DATA.ordinal());
//        startService(intent);
//    }

//    private void uploadVolumeRecords(int taskId, String volume) {
//        if ((taskId <= 0) || TextUtil.isNullOrEmpty(volume)) {
//            return;
//        }
//
//        Intent intent = SyncService.getStartIntent(this);
//        intent.putExtra(SyncService.SYNC_TYPE, SyncService.SyncType.VOLUME_RECORDS.ordinal());
//        intent.putExtra(SyncService.TASK_ID, taskId);
//        intent.putExtra(SyncService.VOLUME, volume);
//        startService(intent);
//    }
//
//    private void uploadVolumeMedias(int taskId, String volume) {
//        if ((taskId <= 0) || TextUtil.isNullOrEmpty(volume)) {
//            return;
//        }
//
//        Intent intent = SyncService.getStartIntent(this);
//        intent.putExtra(SyncService.SYNC_TYPE, SyncService.SyncType.VOLUME_MEDIAS.ordinal());
//        intent.putExtra(SyncService.TASK_ID, taskId);
//        intent.putExtra(SyncService.VOLUME, volume);
//        startService(intent);
//    }

    public void queryQianFei(String customerId) {
        if (TextUtil.isNullOrEmpty(customerId)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.QUERYING_QIANFEI.ordinal());
        intent.putExtra(SyncService.CUSTOMER_ID, customerId);
        startService(intent);
    }

    protected boolean canSync(int errorTime) {
        if (lastSyncTime == 0) {
            lastSyncTime = MainApplication.get(this).getCurrentTime();
            return true;
        }

        long time = MainApplication.get(this).getCurrentTime();
        long error = time - lastSyncTime;
        if (error >= errorTime) {
            lastSyncTime = time;
            return true;
        }

        ApplicationsUtil.showMessage(this, R.string.text_wait_for_sync);
        return false;
    }


    /**
     * 跳转至BaiduMapActivityt采集信息
     *
     * @param longitude
     * @param latitude
     */
    public void markMap(double longitude, double latitude) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName cn = new ComponentName("com.sh3h.citymap", "com.sh3h.citymap.ui.map.BaiduMapActivity");
            intent.setComponent(cn);
            intent.putExtra(MapParamEx.LONGITUDE, longitude);
            intent.putExtra(MapParamEx.LATITUDE, latitude);
            intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.MARK_MAP.ordinal());
            startActivityForResult(intent, MapParamEx.REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "---markMap---" + e.getMessage());
        }
    }

    /**
     * 跳转至BaiduMapActivityt采集信息
     */
    public void markMap() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ComponentName cn = new ComponentName("com.sh3h.citymap", "com.sh3h.citymap.ui.map.BaiduMapActivity");
        intent.setComponent(cn);
        intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.MARK_MAP.ordinal());
        startActivityForResult(intent, MapParamEx.REQUEST_CODE);
    }

    /**
     * 跳转至BaiduMapActivity定位到表卡位置
     *
     * @param longitude
     * @param latitude
     * @param address
     */
    public void locateMap(double longitude, double latitude, String address) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName cn = new ComponentName("com.sh3h.citymap", "com.sh3h.citymap.ui.map.BaiduMapActivity");
            intent.setComponent(cn);
            intent.putExtra(MapParamEx.LONGITUDE, longitude);
            intent.putExtra(MapParamEx.LATITUDE, latitude);
            intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.LOCATE_MAP.ordinal());
            intent.putExtra(MapParamEx.ADDRESS, TextUtil.getString(address));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "---locateMap---" + e.getMessage());
        }
    }

    public void trackMap(List<Coordinate> duCoordinates) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName cn = new ComponentName("com.sh3h.citymap", "com.sh3h.citymap.ui.map.BaiduMapActivity");
            intent.setComponent(cn);
            intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.TRACK_MAP.ordinal());
            intent.putExtra(MapParamEx.COORDINATES, (Serializable) duCoordinates);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "---trackMap---" + e.getMessage());
        }
    }


    public void uploadCardCoordinate(int taskId,
                                     String volume,
                                     String customerId,
                                     double longitude,
                                     double latitude) {
        if (TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_CARD_COORDINATE.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.VOLUME, volume);
        intent.putExtra(SyncService.CUSTOMER_ID, customerId);
        intent.putExtra(SyncService.LONGITUDE, longitude);
        intent.putExtra(SyncService.LATITUDE, latitude);
        startService(intent);
    }

    public void uploadRushPayTaskAndMedias(int taskId,
                                           String customerId) {
        if ((taskId < 0)
                || TextUtil.isNullOrEmpty(customerId)) {
            return;
        }

        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_RUSH_PAY_MEDIAS.ordinal());
        intent.putExtra(SyncService.TASK_ID, taskId);
        intent.putExtra(SyncService.CUSTOMER_ID, customerId);
        startService(intent);
    }

    public void uploadAndDownloadAllRushPayTasks() {
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_DOWNLOADING_ALL_RUSH_PAY_TASKS.ordinal());
        startService(intent);
    }

    public void markMap(boolean isBaiduMap, double longitude, double latitude) {
//        Intent intent =
//                isBaiduMap ? new Intent(this, BaiduMapActivity.class) : new Intent(this, BaiduMapActivity.class);
//        intent.putExtra(MapParamEx.LONGITUDE, longitude);
//        intent.putExtra(MapParamEx.LATITUDE, latitude);
//        intent.putExtra(MapParamEx.MAP_STATUS, MapStatusEx.MARK_MAP.ordinal());
//        startActivityForResult(intent, MapParamEx.REQUEST_CODE);
    }

    public void uploadAllMediaRepeatedly() {
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.SYNC_TYPE, SyncType.UPLOADING_ALL_MEDIA_REPEATEDLY.ordinal());
        startService(intent);
    }

    protected void initConfig() {
        MainApplication.get(this).initConfig();
    }

    protected void initUserConfig() {
        MainApplication.get(this).initUserConfig(account, userId, userName, netWork, photoQuality);
    }

    @SuppressLint("NewApi")
    protected boolean checkPermissions() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && lackPermissions(PERMISSIONS)) {
            requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            onRequestPermissionsResult(true);
        } else {
            popupPermissionDialog();
        }
    }

    protected void onRequestPermissionsResult(boolean isSuccess) {
        LogUtil.i(TAG, "onRequestPermissionsResult " + isSuccess);
        if (isSuccess) {
            initConfig();
        } else {
            finish();
        }
    }

    private boolean lackPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lackPermissions(permission)) {
                return true;
            }
        }
        return false;
    }

    //判断是否缺少权限
    private boolean lackPermissions(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private void popupPermissionDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_lack_permissions)
                .positiveText(R.string.text_ok)
                .cancelable(false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }



  private void getWordsList(final SimpleCallBack<XunJianXXWords> callBack) {
    EasyHttp.post(URL.BASE_XUNJIAN_URL+ URL.GetWords)
      .cacheKey(URL.GetWords)
      .cacheTime(-1)
      .execute(new SimpleCallBack<String>() {
        @Override
        public void onStart() {
          super.onStart();
          if (callBack != null) {
            callBack.onStart();
          }
        }

        @Override
        public void onError(ApiException e) {
          if (callBack != null) {
            callBack.onError(e);
          }
        }

        @Override
        public void onSuccess(String s) {
          XunJianXXWords wordsList = GsonUtils.fromJson(s, XunJianXXWords.class);
          if ("00".equals(wordsList.getMsgCode())) {
            if (wordsList.getData() != null && wordsList.getData().size() > 0) {
              deleteWord(wordsList.getData());
//              GreenDaoUtils.getInstance().getDaoSession(instance)
//                .getXJXXWordBeanDao().deleteAll();
//
//              GreenDaoUtils.getInstance().getDaoSession(instance)
//                .getXJXXWordBeanDao()
//                .insertOrReplaceInTx(wordsList.getData());
            }
            if (callBack != null) {
              callBack.onSuccess(wordsList);
            }
          } else {
            ToastUtils.showLong(wordsList.getMsgInfo());
          }
        }
      });
  }

  public void onDeleteWord(List<XJXXWordBean> data) {

    saveWord(data);

  }

  public void saveWord(List<XJXXWordBean> data) {
    LogUtil.i(TAG, "---saveXunJianBK---");
    if (data == null) {
      Log.e(TAG, "---saveXunJianBK: biaoKaBean is null---");
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
        }

        @Override
        public void onNext(Boolean aBoolean) {
          LogUtil.i(TAG, "---saveNewImage onNext---");
          if (aBoolean) {
//            getMvpView().onSaveNewImage(duoMeiTXX);
          } else {
            LogUtil.i(TAG, "---saveNewImage onNext---false");
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
        }

        @Override
        public void onNext(Boolean aBoolean) {
          LogUtil.i(TAG, "---saveNewImage onNext---");
          if (aBoolean) {
            onDeleteWord(data);
          } else {
            LogUtil.i(TAG, "---saveNewImage onNext---false");
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
        }

        @Override
        public void onNext(Boolean aBoolean) {
          LogUtil.i(TAG, "---deleteFile onNext---");
          if (aBoolean) {
          } else {
            LogUtil.i(TAG, "---deleteFile onNext---false");
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
