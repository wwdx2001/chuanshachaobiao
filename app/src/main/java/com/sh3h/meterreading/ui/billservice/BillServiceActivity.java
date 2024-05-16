package com.sh3h.meterreading.ui.billservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.dataprovider3.entity.DUBillServiceInfoResultBean;
import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.images.view.PopupWindowMenu;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.util.ApplicationsUtil;
import com.sh3h.meterreading.util.ZhangwuNYUtils;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.widget.IBInvoke;
import com.sh3h.mobileutil.widget.PopupWindowImageAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.zhouyou.http.exception.ApiException;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillServiceActivity extends ParentActivity implements BillServiceMvpView,
        MenuItem.OnMenuItemClickListener, BillServiceAdapter.OnItemClickListener {

    private final static String TAG = "BillServiceActivity";
    private static final int CAPTURE_IMAGE = 1000;

    @BindView(R.id.constrain_layout)
    ConstraintLayout constrainLayout;
    @BindView(R.id.loading_process)
    SmoothProgressBar mSmoothProgressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    ConfigHelper mConfigHelper;
    @Inject
    BillServicePresenter billServicePresenter;
    @Inject
    PreferencesHelper mPreferencesHelper;
    @Inject
    Bus mEventBus;

    private BillServiceAdapter adapter;
    private PopupWindowImageAdapter mAdapter = null;
    private List<DUBillServiceInfoResultBean> mListData;
    private List<DUMedia> mDuoMeiTXXList = null;
    private String mFileName = null;
    private DUBillServiceInfoResultBean chaoBiaoZD;
    private List<ImageView> mImageViewList = null;
    private List<String> mImgPathList = null;
    private MaterialDialog mMMaterialDialog;
    private boolean isLoading = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_service);
        initView();
        setListener();
        initData(savedInstanceState);
    }

    private void initView() {
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mEventBus.register(this);

        billServicePresenter.attachView(this);
        setActionBarBackButtonEnable();

        mSmoothProgressBar.setVisibility(View.VISIBLE);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mListData = new ArrayList<>();
        adapter = new BillServiceAdapter();
        adapter.setBillServiceList(mListData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void setListener() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (savedInstanceState != null) {
            initParams(savedInstanceState);
        } else if (intent != null) {
            initParams(intent.getExtras());
        } else {
            initParams(null);
        }

        if (checkPermissions()) {
            isLoading = false;
            initConfig();
        }

        mImgPathList = new ArrayList<>();
        mDuoMeiTXXList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bill_service, menu);
        MenuItem updateItem = menu.findItem(R.id.mtl_action_update);
        MenuItem commitItem = menu.findItem(R.id.mtl_action_commit);
        updateItem.setOnMenuItemClickListener(this);
        commitItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mtl_action_update:
                if (checkPermissions()) {
                    isLoading = true;
                    initConfig();
                }
//                billServicePresenter.getBillServiceBH(true);
                break;
            case R.id.mtl_action_commit:
                billServicePresenter.submitData2(mListData);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void notifyListData(DUBillServiceInfoResultBean resultBean) {
//        mListData.remove(resultBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int type, int position) {
        switch (type) {
            case BillServiceAdapter.TYPE_TAKE_PHOTO:
                takePicture(mListData.get(position));
                break;
            case BillServiceAdapter.TYPE_BROWSE_PHOTO:
                loadImages(mListData.get(position));
                break;
            default:
                break;
        }
    }

    /**
     * 拍照功能
     */
    private void takePicture(DUBillServiceInfoResultBean chaoBiaoZD) {
        this.chaoBiaoZD = chaoBiaoZD;
        if (chaoBiaoZD == null) {
            ApplicationsUtil.showMessage(this, "数据丢失,无法拍照");
            return;
        }

        try {
//            if (mDuoMeiTXXList.size() >= 6) {
//                ApplicationsUtil.showMessage(BillServiceActivity.this,
//                        R.string.text_pictures_full);
//                return;
//            }

            String mCh = chaoBiaoZD.getS_ZHUMA();
            if (mCh != null) {
                File folder = mConfigHelper.getImageFolderPath();
                File dir = new File(folder, mCh);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                mFileName = "账单送达_" + mCh + "_" + TextUtil.format(new Date(), "yyyyMMddHHmmss") + ".jpg";
                File file = new File(dir, mFileName);
                Uri fileUri = null;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {//7.0以上
                    fileUri = Uri.fromFile(file);
                } else {
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        fileUri = FileProvider.getUriForFile(BillServiceActivity.this, "com.sh3h.meterreading.fileprovider", file);
                    }
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMAGE);
            } else {
                LogUtil.i(TAG, "---takePicture---parameter is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---takePicture---" + e.getMessage());
        }
    }

    public void loadImages(DUBillServiceInfoResultBean chaoBiaoZD) {
        this.chaoBiaoZD = chaoBiaoZD;
        showProgressBar();
        billServicePresenter.loadImageInfo(chaoBiaoZD.getI_RENWUBH(), chaoBiaoZD.getS_ZHUMA(), chaoBiaoZD.getI_RENWUBH() + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        processResult(requestCode, resultCode, data);
    }

    public void processResult(int requestCode, int resultCode, Intent data) {
        String mCh = chaoBiaoZD.getS_ZHUMA();
//        String mCh = "GZD";

        if (CAPTURE_IMAGE == requestCode &&
                mCh != null &&
                mFileName != null) {
            File folder = mConfigHelper.getImageFolderPath();
            File dir = new File(folder, mCh);
            if (!dir.exists()) {
                LogUtil.i(TAG, String.format("---processResult---dir:%s isn't existing",
                        mCh));
                return;
            }

            File file = new File(dir, mFileName);
            if (!file.exists()) {
                LogUtil.i(TAG, String.format("---processResult---file:%s isn't existing",
                        mFileName));
                return;
            }

            DUMedia duoMeiTXX = new DUMedia();
            duoMeiTXX.setCid(chaoBiaoZD.getI_RENWUBH() + "");
            duoMeiTXX.setChaobiaoid(chaoBiaoZD.getI_RENWUBH());
            duoMeiTXX.setWenjianlx(DUMedia.WENJIANLX_PIC);
            duoMeiTXX.setWenjianmc(mFileName);
            duoMeiTXX.setType(DUMedia.MEDIA_TYPE_CHAOBIAO);
            duoMeiTXX.setRenwubh(chaoBiaoZD.getI_RENWUBH());
            duoMeiTXX.setCh(mCh);
            duoMeiTXX.setShangchuanbz(DUMedia.SHANGCHUANBZ_WEISHANGC);
            duoMeiTXX.setCreaterq(MainApplication.get(this).getCurrentTime());
            duoMeiTXX.setWenjianlj(file.getAbsolutePath());
            duoMeiTXX.setAccount(mPreferencesHelper.getUserSession().getAccount());
//            duoMeiTXX.setAccount(SPUtils.getInstance().getString(Const.S_YUANGONGH, ""));
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getAbsolutePath()}, null, null);

            showProgressBar();
            billServicePresenter.saveNewImage(duoMeiTXX);
        } else {
            LogUtil.i(TAG, "---processResult---parameter is null");
        }
    }

    private void displayImages() {
        final String mCh = chaoBiaoZD.getS_ZHUMA();
        mAdapter = new PopupWindowImageAdapter(mImageViewList);
        Window window = getWindow();
        PopupWindowMenu popupWindowMenuNJXX = new PopupWindowMenu(this, window);
        popupWindowMenuNJXX.popupWindowZDImageViwe(getWindow().getDecorView().getRootView(),
                PopupWindowMenu.ATLOCATION_TOP, AbsListView.LayoutParams.FILL_PARENT,
                AbsListView.LayoutParams.FILL_PARENT, "图片", mAdapter,
                mConfigHelper.getImageFolderPath(),
                mCh, mDuoMeiTXXList, new IBInvoke() {
                    @Override
                    public void before() {
                    }

                    @Override
                    public <T> void after(T object) {
                        if (object instanceof Integer) {
                            int index = (Integer) object;
                            if ((index >= 0) && (index < mDuoMeiTXXList.size())) {
                                String name = mDuoMeiTXXList.get(index).getWenjianmc();
                                showProgressBar();
                                billServicePresenter.deleteImage(index, name, mCh);
                            }
                        }
                    }

                    @Override
                    public void after() {
                    }
                });
    }

    //初始化结果
    @Subscribe
    public void onInitConfigResult(UIBusEvent.InitConfigResult initConfigResult) {
        LogUtil.i(TAG, "---onInitConfigResult---" + initConfigResult.isSuccess());
        if (initConfigResult.isSuccess()) {
            initUserConfig();
        } else {
            com.sh3h.mobileutil.util.ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    //初始化结果
    @Subscribe
    public void onInitUserConfigResult(UIBusEvent.InitUserConfigResult initUserConfigResult) {
        LogUtil.i(TAG, "---onInitResult---" + initUserConfigResult.isSuccess());
        if (initUserConfigResult.isSuccess()) {
            LitePal.initialize(getApplicationContext());
            billServicePresenter.getBillServiceBH(isLoading);
        } else {
            com.sh3h.mobileutil.util.ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    @Override
    public void onBillServiceListNext(List<DUBillServiceInfoResultBean> mListData) {
        hideProgressBar();
        Log.e("BillServiceActivity", "listsize=" + mListData.size());
        this.mListData = mListData;
        adapter.setBillServiceList(mListData);
        adapter.notifyDataSetChanged();
//        List<DUBillServiceInfoResultBean> newList = new ArrayList<>();
//        for (int i = 0; i < mListData.size(); i++) {
//            if (ZhangwuNYUtils.test(mListData.get(i).getI_ZHANGWUNY())) {
//                newList.add(mListData.get(i));
//            }
//        }
        List<DUBillServiceInfoResultBean> newList = ZhangwuNYUtils.isOverdue(mListData);
        if (newList != null && newList.size() > 0) {
//            mListData.removeAll(newList);
//            adapter.setBillServiceList(mListData);
//            adapter.notifyDataSetChanged();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < newList.size(); i++) {
                stringBuilder.append(newList.get(i).getS_ZHUMA() + "，");
            }
            stringBuilder.append(getString(R.string.text_zhangwuny_time_error));
            new AlertDialog.Builder(this)
                    .setMessage(stringBuilder.toString())
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LitePal.deleteAll(DUBillServiceInfoResultBean.class,
                                    "i_ZHANGWUNY < ?",
                                    String.valueOf(ZhangwuNYUtils.getCurrentZhagnwuNY()));
                            LitePal.findAllAsync(DUBillServiceInfoResultBean.class)
                                    .listen(new FindMultiCallback<DUBillServiceInfoResultBean>() {
                                        @Override
                                        public void onFinish(List<DUBillServiceInfoResultBean> list) {
                                            Log.e("helloworld", "listsize=" + list.size());
                                            adapter.setBillServiceList(list);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    }).create().show();
        }
    }

    @Override
    public void onLoadImgPathList(List<String> imgPathList) {
        mImgPathList = imgPathList;
        billServicePresenter.loadImageViews(mImgPathList, this);
    }

    @Override
    public void onLoadDuoMeiTXXList(List<DUMedia> duMediaList) {
        hideProgressBar();
        mDuoMeiTXXList = duMediaList;
    }

    @Override
    public void onSaveNewImage(DUMedia duMedia) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if ((duMedia != null)
                && (!TextUtil.isNullOrEmpty(duMedia.getWenjianlj()))
                && (!TextUtil.isNullOrEmpty(duMedia.getWenjianmc()))) {
            mImgPathList.add(duMedia.getWenjianlj());
            mDuoMeiTXXList.add(duMedia);
            ApplicationsUtil.showMessage(this,
                    R.string.text_success_save_picture);
        } else {
            LogUtil.i(TAG, "---onSaveNewImage---parameter is error");
            ApplicationsUtil.showMessage(this,
                    R.string.text_failure_save_picture);
        }
    }

    @Override
    public void onLoadImageViews(List<ImageView> imageViewList) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (imageViewList != null) {
            mImageViewList = imageViewList;
            displayImages();
        } else {
            LogUtil.i(TAG, "---onLoadImageViews---error");
        }
    }

    @Override
    public void onDeleteImage(int index) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);

        try {
            mImgPathList.remove(index);
            mDuoMeiTXXList.remove(index);
            if (mImageViewList != null) {
                mImageViewList.remove(index);
            }
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---onDeleteImage---" + e.getMessage());
        }
    }

    @Override
    public void onError(ApiException e) {
        hideProgressBar();
        if (isNeedDialog()) {
            setNeedDialog(false);
            hideProgress();
        }
    }

    @Override
    public void onFile(String info) {
        hideProgressBar();
        if (isNeedDialog()) {
            setNeedDialog(false);
            hideProgress();
        }
        if (!TextUtil.isNullOrEmpty(info)) {
            LogUtil.i(TAG, info);
            ApplicationsUtil.showMessage(this, info);
        }
    }

    @Override
    public void showProgressBar() {
        mSmoothProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressDialog(String msg) {
        mMMaterialDialog = new MaterialDialog.Builder(this)
                .content(msg)
                .progress(true, 100)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .build();
        mMMaterialDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mMMaterialDialog != null) {
            mMMaterialDialog.dismiss();
            mMMaterialDialog = null;
        }
    }

    @Override
    public void hideProgressBar() {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
        billServicePresenter.detachView();
    }
}
