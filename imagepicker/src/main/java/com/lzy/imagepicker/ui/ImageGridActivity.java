package com.lzy.imagepicker.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.imagepicker.DataHolder;
import com.lzy.imagepicker.ImageDataSource;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.YasuoSuccessEntity;
import com.lzy.imagepicker.adapter.ImageFolderAdapter;
import com.lzy.imagepicker.adapter.ImageRecyclerAdapter;
import com.lzy.imagepicker.adapter.ImageRecyclerAdapter.OnImageItemClickListener;
import com.lzy.imagepicker.bean.ImageFolder;
import com.sh3h.serverprovider.entity.ImageItem;
import com.lzy.imagepicker.util.FileUtils;
import com.lzy.imagepicker.util.Utils;
import com.lzy.imagepicker.view.FolderPopUpWindow;
import com.lzy.imagepicker.view.GridSpacingItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * 2017-03-17
 *
 * @author nanchen
 * 新增可直接传递是否裁剪参数，以及直接拍照
 * ================================================
 */
public class ImageGridActivity extends ImageBaseActivity implements ImageDataSource.OnImagesLoadedListener, OnImageItemClickListener, ImagePicker.OnImageSelectedListener, View.OnClickListener {

    public static final int REQUEST_PERMISSION_STORAGE = 0x01;
    public static final int REQUEST_PERMISSION_CAMERA = 0x02;
    public static final String EXTRAS_TAKE_PICKERS = "TAKE";
    public static final String XIAO_GEN_HAO = "XIAO_GEN_HAO";
    public static final String NB_PHOTO = "NBPhoto";
    public static final String PHOTO_TYPE = "TYPE";
    public static final String EXTRAS_IMAGES = "IMAGES";
    public static final String REWUID = "REWUID";

    public static final String CAMERA_TYPE = "cameraType";
    public static final String CAMERA_TYPE_GOANDANBH = "gongdanBH";
    public static final String CAMERA_TYPE_CENBENBH = "cebenBH";
    public static final String CAMERA_TYPE_PHOTO = "photo";
    public static final String CAMERA_TYPE_OCR = "ocr";
    public static final String CAMERA_TYPE_OCR_BEFORE = "ocr_before";
    public static final String CAMERA_TYPE_OCR_AFTER = "ocr_after";

    private String cameraType = "";
    private String gongdanBH = "";
    private String cebenBH = "";

    private ImagePicker imagePicker;

    private boolean isOrigin = false;  //是否选中原图
    private View mFooterBar;     //底部栏
    private Button mBtnOk;       //确定按钮
    private View mllDir; //文件夹切换按钮
    private TextView mtvDir; //显示当前文件夹
    private TextView mBtnPre;      //预览按钮
    private ImageFolderAdapter mImageFolderAdapter;    //图片文件夹的适配器
    private FolderPopUpWindow mFolderPopupWindow;  //ImageSet的PopupWindow
    private List<ImageFolder> mImageFolders;   //所有的图片文件夹
    //    private ImageGridAdapter mImageGridAdapter;  //图片九宫格展示的适配器
    private boolean directPhoto = false; // 默认不是直接调取相机
    private String photoType;
    private RecyclerView mRecyclerView;
    private ImageRecyclerAdapter mRecyclerAdapter;
    private String mFilename;
    private String mXiaoGenHao;
    private String mRewuId;
    private boolean nbPhoto;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mXiaoGenHao = savedInstanceState.getString(XIAO_GEN_HAO, "");
        directPhoto = savedInstanceState.getBoolean(EXTRAS_TAKE_PICKERS, false);
        nbPhoto = savedInstanceState.getBoolean(NB_PHOTO, false);
        photoType = savedInstanceState.getString(PHOTO_TYPE);
        mRewuId = savedInstanceState.getString(REWUID);
        cameraType = savedInstanceState.getString(CAMERA_TYPE);
        gongdanBH = savedInstanceState.getString(CAMERA_TYPE_GOANDANBH);
        cebenBH = savedInstanceState.getString(CAMERA_TYPE_CENBENBH);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRAS_TAKE_PICKERS, directPhoto);
        outState.putBoolean(NB_PHOTO, nbPhoto);
        outState.putString(PHOTO_TYPE, photoType);
        outState.putString(CAMERA_TYPE, cameraType);
        outState.putString(CAMERA_TYPE_GOANDANBH, gongdanBH);
        outState.putString(CAMERA_TYPE_CENBENBH, cebenBH);
        outState.putString(REWUID, mRewuId);
        if (!TextUtils.isEmpty(mXiaoGenHao)) {
          outState.putString(XIAO_GEN_HAO, mXiaoGenHao);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);

        imagePicker = ImagePicker.getInstance();
        imagePicker.clear();
        imagePicker.addOnImageSelectedListener(this);

        Intent data = getIntent();
        // 新增可直接拍照
        if (data != null && data.getExtras() != null) {
            mXiaoGenHao = data.getStringExtra(XIAO_GEN_HAO);
            directPhoto = data.getBooleanExtra(EXTRAS_TAKE_PICKERS, false); // 默认不是直接打开相机
            photoType = data.getStringExtra(PHOTO_TYPE);
            nbPhoto = data.getBooleanExtra(NB_PHOTO, false);
            cameraType = data.getStringExtra(CAMERA_TYPE);
            gongdanBH = data.getStringExtra(CAMERA_TYPE_GOANDANBH);
            cebenBH = data.getStringExtra(CAMERA_TYPE_CENBENBH);
            mRewuId = data.getStringExtra(REWUID);
            if (directPhoto) {
                if (!(checkPermission(Manifest.permission.CAMERA))) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ImageGridActivity.REQUEST_PERMISSION_CAMERA);
                } else {
                    imagePicker.takePicture(nbPhoto, cebenBH + "_" + gongdanBH, cameraType, photoType, this, ImagePicker.REQUEST_CODE_TAKE, mRewuId);
//                    finish();
                }
            }
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(EXTRAS_IMAGES);
            imagePicker.setSelectedImages(images);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        findViewById(R.id.btn_back).setOnClickListener(this);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
        mBtnPre = (TextView) findViewById(R.id.btn_preview);
        mBtnPre.setOnClickListener(this);
        mFooterBar = findViewById(R.id.footer_bar);
        mllDir = findViewById(R.id.ll_dir);
        mllDir.setOnClickListener(this);
        mtvDir = (TextView) findViewById(R.id.tv_dir);
        if (imagePicker.isMultiMode()) {
            mBtnOk.setVisibility(View.VISIBLE);
            mBtnPre.setVisibility(View.VISIBLE);
        } else {
            mBtnOk.setVisibility(View.GONE);
            mBtnPre.setVisibility(View.GONE);
        }

//        mImageGridAdapter = new ImageGridAdapter(this, null);
        mImageFolderAdapter = new ImageFolderAdapter(this, null);
        mRecyclerAdapter = new ImageRecyclerAdapter(nbPhoto, photoType, this, null, mRewuId);

        onImageSelected(0, null, false);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new ImageDataSource(this, nbPhoto ? null : getPath(), this);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
            }
        } else {
            new ImageDataSource(this, nbPhoto ? null : getPath(), this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new ImageDataSource(this, null, this);
            } else {
                showToast("权限被禁止，无法选择本地图片");
            }
        } else if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imagePicker.takePicture(nbPhoto, cebenBH + "_" + gongdanBH, cameraType, photoType, this, ImagePicker.REQUEST_CODE_TAKE, mRewuId);
            } else {
                showToast("权限被禁止，无法打开相机");
            }
        }
    }

    @Override
    protected void onDestroy() {
        imagePicker.removeOnImageSelectedListener(this);
        super.onDestroy();
    }

    private SimpleDateFormat nbDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat xunjianFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_ok) {
            Log.e("btnOk", "btnOk");
//            if (!nbPhoto) {
//                Intent intent = new Intent();
//                intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
//                intent.putExtra("btnOk", true);
//                setResult(ImagePicker.RESULT_CODE_ITEMS, intent);  //多选不允许裁剪裁剪，返回数据
//                finish();
//            } else {
            //从图库选择的照片
            ArrayList<ImageItem> selectedImages = imagePicker.getSelectedImages();
            List<String> pathList = new ArrayList<>();
            for (int i = 0; i < selectedImages.size(); i++) {
                String path = getPath() + nbDateFormat.format(new Date()) + "_" + System.currentTimeMillis() + "_" + photoType + "_" + mRewuId + ".jpg";
                if (!selectedImages.get(i).path.contains(getPath())) {
                    pathList.add(selectedImages.get(i).path);
                } else {
                  try{
                    boolean isSuccess = FileUtils.copyFile(selectedImages.get(i).path, path);
                    if (isSuccess) {
                      pathList.add(path);
                    }
                  }catch (Exception e){
                  }
                }
            }

//                try {
            // 使用鲁班压缩图片
            Luban.with(this)
                    .load(pathList)
                    .ignoreBy(100)
                    .setTargetDir(getPath())
                    .setRenameListener(new OnRenameListener() {
                        @Override
                        public String rename(String filePath) {
                            if (nbPhoto) {
//                                mFilename = photoType + System.currentTimeMillis() + ".jpg";
                                mFilename = nbDateFormat.format(new Date()) + "_" + System.currentTimeMillis() + "_" + photoType + "_" + mRewuId + ".jpg";
                            } else {
//                                mFilename = photoType + System.currentTimeMillis() + ".jpg";
                                mFilename = xunjianFormat.format(new Date()) + "_" + photoType + mRewuId + ".jpg";
                            }
                            Log.e("prefix,rename", "重命名：" + mFilename);
                            return mFilename;
                        }
                    })
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            finishPhoto(mFilename, mFilename);
                        }

                        @Override
                        public void onSuccess(File file) {
                            Log.e("lubans,renameafter", "压缩成功" + file.getAbsolutePath());
                            if (file != null) {
                                // 压缩后的图片
                                //发送广播通知图片增加了
//                                ImagePicker.galleryAddPic(ImageGridActivity.this, file);
//                                //删除系统缩略图
//                                getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{image.getAbsolutePath()});
                                //删除SD中图片
                                //删除成功后通知图库更新
                                finishPhoto(file.getAbsolutePath(), mFilename);
                            } else {
                                finishPhoto(file.getAbsolutePath(), mFilename);
                            }
                            EventBus.getDefault().post(new YasuoSuccessEntity(imagePicker.getSelectedImages(), cameraType));
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("lubans", "压缩失败");
                            // TODO 当压缩过程出现问题时调用
//                                    finishPhoto(path);
                            EventBus.getDefault().post(new YasuoSuccessEntity(imagePicker.getSelectedImages(), cameraType));
                        }
                    }).launch();
//            }
        } else if (id == R.id.ll_dir) {
            if (mImageFolders == null) {
                Log.i("ImageGridActivity", "您的手机没有图片");
                return;
            }
            //点击文件夹按钮
            createPopupFolderList();
            mImageFolderAdapter.refreshData(mImageFolders);  //刷新数据
            if (mFolderPopupWindow.isShowing()) {
                mFolderPopupWindow.dismiss();
            } else {
                mFolderPopupWindow.showAtLocation(mFooterBar, Gravity.NO_GRAVITY, 0, 0);
                //默认选择当前选择的上一个，当目录很多时，直接定位到已选中的条目
                int index = mImageFolderAdapter.getSelectIndex();
                index = index == 0 ? index : index - 1;
                mFolderPopupWindow.setSelection(index);
            }
        } else if (id == R.id.btn_preview) {
            Intent intent = new Intent(ImageGridActivity.this, ImagePreviewActivity.class);
            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagePicker.getSelectedImages());
            intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
            intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
        } else if (id == R.id.btn_back) {
            //点击返回按钮
            finish();
        }
    }

    /**
     * 创建弹出的ListView
     */
    private void createPopupFolderList() {
        mFolderPopupWindow = new FolderPopUpWindow(this, mImageFolderAdapter);
        mFolderPopupWindow.setOnItemClickListener(new FolderPopUpWindow.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mImageFolderAdapter.setSelectIndex(position);
                imagePicker.setCurrentImageFolderPosition(position);
                mFolderPopupWindow.dismiss();
                ImageFolder imageFolder = (ImageFolder) adapterView.getAdapter().getItem(position);
                if (null != imageFolder) {
//                    mImageGridAdapter.refreshData(imageFolder.images);
                    mRecyclerAdapter.refreshData(imageFolder.images);
                    mtvDir.setText(imageFolder.name);
                }
            }
        });
        mFolderPopupWindow.setMargin(mFooterBar.getHeight());
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {
        this.mImageFolders = imageFolders;
        imagePicker.setImageFolders(imageFolders);
        if (imageFolders.size() == 0) {
//            mImageGridAdapter.refreshData(null);
            mRecyclerAdapter.refreshData(null);
        } else {
//            mImageGridAdapter.refreshData(imageFolders.get(0).images);
            mRecyclerAdapter.refreshData(imageFolders.get(0).images);
        }
//        mImageGridAdapter.setOnImageItemClickListener(this);
        mRecyclerAdapter.setOnImageItemClickListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dp2px(this, 2), false));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mImageFolderAdapter.refreshData(imageFolders);
    }

    @Override
    public void onImageItemClick(View view, ImageItem imageItem, int position) {
        //根据是否有相机按钮确定位置
        position = imagePicker.isShowCamera() ? position - 1 : position;
        if (imagePicker.isMultiMode()) {
            Intent intent = new Intent(ImageGridActivity.this, ImagePreviewActivity.class);
            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);

            /**
             * 2017-03-20
             *
             * 依然采用弱引用进行解决，采用单例加锁方式处理
             */

            // 据说这样会导致大量图片的时候崩溃
//            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagePicker.getCurrentImageFolderItems());

            // 但采用弱引用会导致预览弱引用直接返回空指针
            DataHolder.getInstance().save(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS, imagePicker.getCurrentImageFolderItems());
            intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);  //如果是多选，点击图片进入预览界面
        } else {
            imagePicker.clearSelectedImages();
            imagePicker.addSelectedImageItem(position, imagePicker.getCurrentImageFolderItems().get(position), true);
            if (imagePicker.isCrop()) {
                Intent intent = new Intent(ImageGridActivity.this, ImageCropActivity.class);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
            } else {
                Intent intent = new Intent();
                intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
                setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
                finish();
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onImageSelected(int position, ImageItem item, boolean isAdd) {
        if (imagePicker.getSelectImageCount() > 0) {
            mBtnOk.setText(getString(R.string.ip_select_complete, imagePicker.getSelectImageCount(), imagePicker.getSelectLimit()));
            mBtnOk.setEnabled(true);
            mBtnPre.setEnabled(true);
            mBtnPre.setText(getResources().getString(R.string.ip_preview_count, imagePicker.getSelectImageCount()));
            mBtnPre.setTextColor(ContextCompat.getColor(this, R.color.ip_text_primary_inverted));
            mBtnOk.setTextColor(ContextCompat.getColor(this, R.color.ip_text_primary_inverted));
        } else {
            mBtnOk.setText(getString(R.string.ip_complete));
            mBtnOk.setEnabled(false);
            mBtnPre.setEnabled(false);
            mBtnPre.setText(getResources().getString(R.string.ip_preview));
            mBtnPre.setTextColor(ContextCompat.getColor(this, R.color.ip_text_secondary_inverted));
            mBtnOk.setTextColor(ContextCompat.getColor(this, R.color.ip_text_secondary_inverted));
        }
//        mImageGridAdapter.notifyDataSetChanged();
//        mRecyclerAdapter.notifyItemChanged(position); // 17/4/21 fix the position while click img to preview
//        mRecyclerAdapter.notifyItemChanged(position + (imagePicker.isShowCamera() ? 1 : 0));// 17/4/24  fix the position while click right bottom preview button
        for (int i = imagePicker.isShowCamera() ? 1 : 0; i < mRecyclerAdapter.getItemCount(); i++) {
            if (mRecyclerAdapter.getItem(i).path != null && mRecyclerAdapter.getItem(i).path.equals(item.path)) {
                mRecyclerAdapter.notifyItemChanged(i);
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null) {
            if (resultCode == ImagePicker.RESULT_CODE_BACK) {
                isOrigin = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            } else {
                //从拍照界面返回
                //点击 X , 没有选择照片
                if (data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) == null) {
                    //什么都不做 直接调起相机
                } else {
                    //说明是从裁剪页面过来的数据，直接返回就可以
                    setResult(ImagePicker.RESULT_CODE_ITEMS, data);
                }
//                finish();
            }
        } else {
            //如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
            if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_TAKE) {
                //发送广播通知图片增加了
                ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());

                /**
                 * 2017-03-21 对机型做旋转处理
                 */
                final String path = imagePicker.getTakeImageFile().getAbsolutePath();
//                int degree = BitmapUtil.getBitmapDegree(path);
//                if (degree != 0){
//                    Bitmap bitmap = BitmapUtil.rotateBitmapByDegree(path,degree);
//                    if (bitmap != null){
//                        File file = new File(path);
//                        try {
//                            FileOutputStream bos = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                            bos.flush();
//                            bos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }

                // 拍照后得到的图片
                final File image = imagePicker.getTakeImageFile();

//                calculateInSampleSize(image.getPath(), image.getPath());

//                try {
                // 使用鲁班压缩图片
                Luban.with(this)
                        .load(image)
                        .ignoreBy(100)
                        .setTargetDir(getPath())
                        .setRenameListener(new OnRenameListener() {
                            @Override
                            public String rename(String filePath) {
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
//                                    mFilename = "IMG_" + dateFormat.format(new Date(System.currentTimeMillis())) + ".jpg";
//                                mFilename = System.currentTimeMillis() + ".jpg";
                                if (ImageGridActivity.CAMERA_TYPE_OCR_AFTER.equals(cameraType)
                                        || ImageGridActivity.CAMERA_TYPE_OCR_BEFORE.equals(cameraType)) {
                                    mFilename = image.getName();
                                } else {
                                    mFilename = image.getName().replace(".jpg", "") + ".jpg";
                                }
                                Log.e("prefix", "重命名222：" + mFilename);
                                return mFilename;
                            }
                        })
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                               // finishPhoto(mFilename, mFilename);
                            }

                            @Override
                            public void onSuccess(File file) {
                                if (file != null) {
                                    // 压缩后的图片
                                    //发送广播通知图片增加了
                                    ImagePicker.galleryAddPic(ImageGridActivity.this, file);
                                    //删除系统缩略图
                                    getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{image.getAbsolutePath()});
                                    //删除SD中图片
                                    image.delete();
                                    //删除成功后通知图库更新
//                                    if (!image.exists()) {
////                                        try {
////                                            String where = MediaStore.Audio.Media.DATA + " like \"" + image.getAbsolutePath() + "\"%" + "\"";
////                                            getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, where, null);
////                                        } catch (Exception e) {
////                                            e.printStackTrace();
////                                        }
//                                    }
                                    //EventBus.getDefault().post(new YasuoSuccessEntity(imagePicker.getSelectedImages(), cameraType));
                                    finishPhoto(file.getAbsolutePath(), mFilename);
                                } else {
                                    //EventBus.getDefault().post(new YasuoSuccessEntity(imagePicker.getSelectedImages(), cameraType));
                                    finishPhoto(path, mFilename);
                                }
                                EventBus.getDefault().post(new YasuoSuccessEntity(imagePicker.getSelectedImages(), cameraType));
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
//                                    finishPhoto(path);
                                EventBus.getDefault().post(new YasuoSuccessEntity(imagePicker.getSelectedImages(), cameraType));
                            }
                        }).launch();
            } else if (directPhoto) {
                finish();
            }
        }
    }

    public void calculateInSampleSize(String phoneFile, String imagPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(phoneFile, options);
        int compressSize = calculateInSampleSize(options, 400, 600);
        //为保证图片清晰 限制最大压缩值为4
        options.inSampleSize = compressSize >= 2 ? 2 : compressSize;
        options.inJustDecodeBounds = false;
        Bitmap fBitmap = BitmapFactory.decodeFile(phoneFile, options);
        FileOutputStream out;
        try {
            out = new FileOutputStream(phoneFile);
            fBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage(), e);
        } catch (NullPointerException ne) {
            Log.e("NullPointerException", ne.getMessage(), ne);
        }
    }

    /**
     * 压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private void finishPhoto(String absolutePath, String filename) {
        ImageItem imageItem = new ImageItem();
        imageItem.path = absolutePath;
        imageItem.name = filename;
        imagePicker.clearSelectedImages();
        imagePicker.addSelectedImageItem(0, imageItem, true);
        if (imagePicker.isCrop()) {
            Intent intent = new Intent(ImageGridActivity.this, ImageCropActivity.class);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
        } else {
            Intent intent = new Intent();
            intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
            setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
            finish();
        }
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/sh3h/meterreading/images/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

}
