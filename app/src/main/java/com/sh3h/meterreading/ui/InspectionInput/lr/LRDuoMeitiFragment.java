package com.sh3h.meterreading.ui.InspectionInput.lr;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.reflect.TypeToken;
//import com.lzy.imagepicker.bean.ImageItem;
import com.sh3h.serverprovider.entity.ImageItem;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.images.entity.BaseEquipment;
import com.sh3h.meterreading.images.entity.Picture;
import com.sh3h.meterreading.images.view.PictureGalleryDialog;
import com.sh3h.meterreading.ui.InspectionInput.InspectionInputActivity;
import com.sh3h.meterreading.ui.InspectionInput.image.ImagePreviewDelActivity;
import com.sh3h.meterreading.ui.InspectionInput.image.LittleVideoParamConfig;
import com.sh3h.meterreading.ui.InspectionInput.image.PlayerRecordVideoActivity;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.widget.PopupWindowImageAdapter;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.squareup.otto.Bus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


/**
 * 录入多媒体
 *
 * @author xiaochao.dev@gmail.com
 * @date 2019/12/12 13:18
 */
public class LRDuoMeitiFragment extends ParentFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener, LRDuoMeitiMvpView, ImagePickerAdapter.OnRecyclerViewItemLongClickListener, View.OnClickListener {

    /**
     * 视频最大容量
     */
    private final static int VIDEOS_FULL_SIZE = 1;

    private final static int PHOTOS_FULL_SIZE = 3;//照片最大容量
    private static final int CAPTURE_ALBUM = 1001;
    /**
     * 视频最大时长
     */
    private final static int VIDEO_MAX = 30000;
    /**
     * 视频最短时长
     */
    private final static int VIDEO_MIN = 3000;

    private RecyclerView mRlHuanjing, mRlZhoubian, mRlShuibiao, mRlChaoma, recyclerVideo,mQuanjing;
    private ImagePickerAdapter mHuanjingAdapter, mZhoubianAdapter, mShuibiaoAdapter, mChaomaAdapter, mVideoAdapter,mQuanjingAdapter;
    private ArrayList<ImageItem> images;
    private ArrayList<ImageItem> mHuanJingList;
    private ArrayList<ImageItem> mZhouBianList;
    private ArrayList<ImageItem> mShuiBiaoList;
    private ArrayList<ImageItem> mChaomaList;
    private ArrayList<ImageItem> mQuanjingList;
    private List<String> list = new ArrayList<>();
  List<BaseEquipment> listEquipment = new ArrayList<BaseEquipment>();
    private PopupWindow mPopwindow;
  private PopupWindowImageAdapter mAdapter = null;
  private List<ImageView> mImageViewList = new ArrayList<>();
    /**
     * 录像集合
     */
    private List<ImageItem> mVideoList;
    private String strType;
    private String url;
    private boolean isHistory;
    private BiaoKaBean mBiaoKaBean;
    private BiaoKaWholeEntity biaoKaWholeEntity;
//    private ChouJianEntity mChouJianEntity;
    private TextView mTv1, mTv2, mTv3, mTv4, mTv5,mTv6;
    private Group groupBefore, groupWorking, groupAfter, groupOldTab;
    private int requestCode;
    private int[] resolutions = {AliyunSnapVideoParam.RESOLUTION_360P, AliyunSnapVideoParam.RESOLUTION_480P,
            AliyunSnapVideoParam.RESOLUTION_540P, AliyunSnapVideoParam.RESOLUTION_720P};
    private VideoQuality[] qualitys = {VideoQuality.SSD, VideoQuality.HD, VideoQuality.SD, VideoQuality.LD,
            VideoQuality.PD, VideoQuality.EPD};
    private String mImageName;
    private NestedScrollView mScrollView;
    private int ImageType;

    @Inject
    Bus mEventBus;

    @Inject
    LRDuoMeitiPresenter mLrDuoMeitiPresenter;
    protected Activity mActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mActivity = getActivity();
      ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_lrduo_meiti, container, false);
      mLrDuoMeitiPresenter.attachView(this);
        mEventBus.register(this);
      initView(view);
      initData();
      return view;
    }

    public void initData() {
      Bundle bundle = getArguments();
      if (bundle != null) {
        try {
          mBiaoKaBean = bundle.getParcelable(Const.BEAN);
        } catch (ClassCastException e) {
        }
        if (mBiaoKaBean != null) {

        }
        biaoKaWholeEntity = bundle.getParcelable(Const.BIAOKAWHOLEBEAN);
        if (biaoKaWholeEntity != null) {
          Type listType = new TypeToken<ArrayList<ImageItem>>() {
          }.getType();

          ArrayList<ImageItem> mHuanJings = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages1(), listType);
          if (mHuanJings != null && mHuanJings.size() > 0) {
            mHuanJingList.clear();
            mHuanJingList.addAll(mHuanJings);
            mHuanjingAdapter.setImages(mHuanJingList);
          } else {
            mHuanJingList.clear();
            mHuanjingAdapter.setImages(mHuanJingList);
          }

          ArrayList<ImageItem> mZhouBians = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages2(), listType);
          if (mZhouBians != null && mZhouBians.size() > 0) {
            mZhouBianList.clear();
            mZhouBianList.addAll(mZhouBians);
            mZhoubianAdapter.setImages(mZhouBianList);
          } else {
            mZhouBianList.clear();
            mZhoubianAdapter.setImages(mZhouBianList);
          }

          ArrayList<ImageItem> mShuiBiaos = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages3(), listType);
          if (mShuiBiaos != null && mShuiBiaos.size() > 0) {
            mShuiBiaoList.clear();
            mShuiBiaoList.addAll(mShuiBiaos);
            mShuibiaoAdapter.setImages(mShuiBiaoList);
          } else {
            mShuiBiaoList.clear();
            mShuibiaoAdapter.setImages(mShuiBiaoList);
          }

          ArrayList<ImageItem> mChaomas = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages4(), listType);
          if (mChaomas != null && mChaomas.size() > 0) {
            mChaomaList.clear();
            mChaomaList.addAll(mChaomas);
            mChaomaAdapter.setImages(mChaomaList);
          } else {
            mChaomaList.clear();
            mChaomaAdapter.setImages(mChaomaList);
          }

          ArrayList<ImageItem> mQuanjings = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages6(), listType);
          if (mQuanjings != null && mQuanjings.size() > 0) {
            mQuanjingList.clear();
            mQuanjingList.addAll(mQuanjings);
            mQuanjingAdapter.setImages(mQuanjingList);
          } else {
            mQuanjingList.clear();
            mQuanjingAdapter.setImages(mQuanjingList);
          }



          ArrayList<ImageItem> mVideos = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages5(), listType);
          if (mVideos != null && mVideos.size() > 0) {
            mVideoList.clear();
            mVideoList.addAll(mVideos);
            mVideoAdapter.setImages(mVideoList);
            url = biaoKaWholeEntity.getVedioUrl();
          } else {
            mVideoList.clear();
            mVideoAdapter.setImages(mVideoList);
          }
        }

      }
    }

  public void initData2(Bundle bundle) {
    if (bundle != null) {
      try {
        mBiaoKaBean = bundle.getParcelable(Const.BEAN);
      } catch (ClassCastException e) {
      }
      if (mBiaoKaBean != null) {

      }
      biaoKaWholeEntity = bundle.getParcelable(Const.BIAOKAWHOLEBEAN);
      if (biaoKaWholeEntity != null) {
        Type listType = new TypeToken<ArrayList<ImageItem>>() {
        }.getType();

        ArrayList<ImageItem> mHuanJings = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages1(), listType);
        if (mHuanJings != null && mHuanJings.size() > 0) {
          mHuanJingList.clear();
          mHuanJingList.addAll(mHuanJings);
          mHuanjingAdapter.setImages(mHuanJingList);
        } else {
          mHuanJingList.clear();
          mHuanjingAdapter.setImages(mHuanJingList);
        }

        ArrayList<ImageItem> mZhouBians = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages2(), listType);
        if (mZhouBians != null && mZhouBians.size() > 0) {
          mZhouBianList.clear();
          mZhouBianList.addAll(mZhouBians);
          mZhoubianAdapter.setImages(mZhouBianList);
        } else {
          mZhouBianList.clear();
          mZhoubianAdapter.setImages(mZhouBianList);
        }

        ArrayList<ImageItem> mShuiBiaos = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages3(), listType);
        if (mShuiBiaos != null && mShuiBiaos.size() > 0) {
          mShuiBiaoList.clear();
          mShuiBiaoList.addAll(mShuiBiaos);
          mShuibiaoAdapter.setImages(mShuiBiaoList);
        } else {
          mShuiBiaoList.clear();
          mShuibiaoAdapter.setImages(mShuiBiaoList);
        }

        ArrayList<ImageItem> mChaomas = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages4(), listType);
        if (mChaomas != null && mChaomas.size() > 0) {
          mChaomaList.clear();
          mChaomaList.addAll(mChaomas);
          mChaomaAdapter.setImages(mChaomaList);
        } else {
          mChaomaList.clear();
          mChaomaAdapter.setImages(mChaomaList);
        }
        ArrayList<ImageItem> mQuanjings = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages6(), listType);
        if (mQuanjings != null && mQuanjings.size() > 0) {
          mQuanjingList.clear();
          mQuanjingList.addAll(mQuanjings);
          mQuanjingAdapter.setImages(mQuanjingList);
        } else {
          mQuanjingList.clear();
          mQuanjingAdapter.setImages(mQuanjingList);
        }

        ArrayList<ImageItem> mVideos = GsonUtils.getGson().fromJson(biaoKaWholeEntity.getImages5(), listType);
        if (mVideos != null && mVideos.size() > 0) {
          mVideoList.clear();
          mVideoList.addAll(mVideos);
          mVideoAdapter.setImages(mVideoList);
          url = biaoKaWholeEntity.getVedioUrl();
        } else {
          mVideoList.clear();
          mVideoAdapter.setImages(mVideoList);
        }
      }

    }
  }

    private void initView(View view) {
        mTv1 = view.findViewById(R.id.tv1);
        mTv2 = view.findViewById(R.id.tv2);
        mTv3 = view.findViewById(R.id.tv3);
        mTv4 = view.findViewById(R.id.tv4);
        mTv5 = view.findViewById(R.id.tv5);
        mTv6 = view.findViewById(R.id.tv6);
        mScrollView= view.findViewById(R.id.scroll);

        mRlHuanjing = (RecyclerView) view.findViewById(R.id.recycler_type1);
        mRlZhoubian = (RecyclerView) view.findViewById(R.id.recycler_zhoubian);
        mRlShuibiao = (RecyclerView) view.findViewById(R.id.recycler_shuibiao);
        mRlChaoma = (RecyclerView) view.findViewById(R.id.recycler_chaoma);
        recyclerVideo = (RecyclerView) view.findViewById(R.id.recycler_video);
        mQuanjing = (RecyclerView) view.findViewById(R.id.recycler_quanjinzhaopian);



        mHuanJingList = new ArrayList<>();
        mZhouBianList = new ArrayList<>();
        mShuiBiaoList = new ArrayList<>();
        mChaomaList = new ArrayList<>();
        mVideoList = new ArrayList<>();
        mQuanjingList=new ArrayList<>();

        mRlHuanjing.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRlHuanjing.setHasFixedSize(true);
        mRlHuanjing.setNestedScrollingEnabled(false);
        mHuanjingAdapter = new ImagePickerAdapter(getActivity(), mHuanJingList, 3, Const.PHOTO_TYPE_HUANJING);
        mRlHuanjing.setAdapter(mHuanjingAdapter);

        mRlZhoubian.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRlZhoubian.setHasFixedSize(true);
        mRlZhoubian.setNestedScrollingEnabled(false);
        mZhoubianAdapter = new ImagePickerAdapter(getActivity(), mZhouBianList, 3, Const.PHOTO_TYPE_ZHOUBIAN);
        mRlZhoubian.setAdapter(mZhoubianAdapter);

        mRlShuibiao.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRlShuibiao.setHasFixedSize(true);
        mRlShuibiao.setNestedScrollingEnabled(false);
        mShuibiaoAdapter = new ImagePickerAdapter(getActivity(), mShuiBiaoList, 3, Const.PHOTO_TYPE_SHUIBIAO);
        mRlShuibiao.setAdapter(mShuibiaoAdapter);

        mRlChaoma.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRlChaoma.setHasFixedSize(true);
        mRlChaoma.setNestedScrollingEnabled(false);
        mChaomaAdapter = new ImagePickerAdapter(getActivity(), mChaomaList, 3, Const.PHOTO_TYP_CHAOMA);
        mRlChaoma.setAdapter(mChaomaAdapter);

        mQuanjing.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mQuanjing.setHasFixedSize(true);
        mQuanjing.setNestedScrollingEnabled(false);
        mQuanjingAdapter = new ImagePickerAdapter(getActivity(), mQuanjingList, 3, Const.PHOTO_TYP_QUANGJING);
        mQuanjing.setAdapter(mQuanjingAdapter);

        recyclerVideo.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerVideo.setHasFixedSize(true);
        recyclerVideo.setNestedScrollingEnabled(false);
        mVideoAdapter = new ImagePickerAdapter(getActivity(), mVideoList, VIDEOS_FULL_SIZE, Const.PHOTO_TYP_VIDEO);
        recyclerVideo.setAdapter(mVideoAdapter);

        mHuanjingAdapter.setOnItemClickListener(this);
        mZhoubianAdapter.setOnItemClickListener(this);
        mShuibiaoAdapter.setOnItemClickListener(this);
        mChaomaAdapter.setOnItemClickListener(this);
        mVideoAdapter.setOnItemClickListener(this);
        mQuanjingAdapter.setOnItemClickListener(this);
        mHuanjingAdapter.setOnItemLongClickListener(this);
        mZhoubianAdapter.setOnItemLongClickListener(this);
        mShuibiaoAdapter.setOnItemLongClickListener(this);
        mChaomaAdapter.setOnItemLongClickListener(this);
        mQuanjingAdapter.setOnItemLongClickListener(this);
        mVideoAdapter.setOnItemLongClickListener(onItemLongClick2);

      if (getActivity() instanceof InspectionInputActivity) {
        InspectionInputActivity activity2 = (InspectionInputActivity) getActivity();
        isHistory = activity2.isHistory;
      }
    }

    ImagePickerAdapter.OnRecyclerViewItemLongClickListener onItemLongClick2 = new ImagePickerAdapter.OnRecyclerViewItemLongClickListener() {
        @Override
        public void onItemLongClick(View view, final int position, int type) {
            if (!isHistory) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示")
                        .setMessage("你确定要删除该视频吗？")
                        .setCancelable(true)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mVideoList.remove(position);
                                mVideoAdapter.setImages(mVideoList);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }




    private File createMediaFile() throws IOException {
        String path = Environment.getExternalStorageDirectory() + "/DCIM/xunjianVideo/";
        File mediaStorageDir = new File(path);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(path + File.separator + imageFileName + suffix);
        return mediaFile;
    }



    private void previewPhoto(int requestCode, ArrayList<ImageItem> images) {
        if (images != null) {
            switch (requestCode) {
                case Const.PHOTO_TYPE_HUANJING:
                    addCamera(mHuanJingList, mHuanjingAdapter, images);
                    break;
                case Const.PHOTO_TYPE_ZHOUBIAN:
                    addCamera(mZhouBianList, mZhoubianAdapter, images);
                    break;
                case Const.PHOTO_TYPE_SHUIBIAO:
                    addCamera(mShuiBiaoList, mShuibiaoAdapter, images);
                    break;
                case Const.PHOTO_TYP_CHAOMA:
                    addCamera(mChaomaList, mChaomaAdapter, images);
                    break;
                case Const.PHOTO_TYP_QUANGJING:
                    addCamera(mQuanjingList, mQuanjingAdapter, images);
                    break;
                default:
                    break;
            }
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void takeSuccess(ArrayList<ImageItem> images) {
        if (images != null) {
            switch (requestCode) {
                case Const.PHOTO_TYPE_HUANJING:
                    mHuanJingList.addAll(images);
                    mHuanjingAdapter.setImages(mHuanJingList);
                    break;
                case Const.PHOTO_TYPE_ZHOUBIAN:
                    mZhouBianList.addAll(images);
                    mZhoubianAdapter.setImages(mZhouBianList);
                    break;
                case Const.PHOTO_TYPE_SHUIBIAO:
                    mShuiBiaoList.addAll(images);
                    mShuibiaoAdapter.setImages(mShuiBiaoList);
                    break;
                case Const.PHOTO_TYP_CHAOMA:
                    mChaomaList.addAll(images);
                    mChaomaAdapter.setImages(mChaomaList);
                    break;
                case Const.PHOTO_TYP_QUANGJING:
                    mQuanjingList.addAll(images);
                    mQuanjingAdapter.setImages(mQuanjingList);
                    break;
                default:
                    break;
            }
        }
    }





    private void addCamera(ArrayList<ImageItem> selImageList, ImagePickerAdapter
            adapter, ArrayList<ImageItem> images) {
        selImageList.clear();
        selImageList.addAll(images);
        adapter.setImages(selImageList);
    }


    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images(Video).Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        if (bitmap != null) {
            //調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * Bitmap保存成File
     *
     * @param bitmap input bitmap
     * @param name   output file's name
     * @return String output file's path
     */

    public static String bitmap2File(Bitmap bitmap, String name) {
        File f = new File(Environment.getExternalStorageDirectory() + "/DCIM/巡检视频/" + name + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return null;
        }
        return f.getAbsolutePath();
    }

    public int getVirtualBarHeight() {
        int vh = 0;
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    public boolean isCanSave(boolean isToast) {
      String str = "请添加";
      if(mBiaoKaBean!=null) {
        String shuibiaozl = mBiaoKaBean.getI_SHUIBIAOZL();
        if (mBiaoKaBean != null && "2".equals(mBiaoKaBean.getXJLX())) {
          if (mHuanjingAdapter.getImages().size() == 0) {
            if (isToast) {
              ToastUtils.showShort(str + mTv1.getText().toString());
            }
            return false;
          }
        } else {
          if (mHuanjingAdapter.getImages().size() == 0 && !"龙头表".equals(shuibiaozl)) {
            if (isToast) {
              ToastUtils.showShort(str + mTv1.getText().toString());
            }
            return false;
          }
//          if (mZhoubianAdapter.getImages().size() == 0) {
//            if (isToast) {
//              ToastUtils.showShort(str + mTv2.getText().toString());
//            }
//            return false;
//          }
//          if (mShuibiaoAdapter.getImages().size() == 0) {
//            if (isToast) {
//              ToastUtils.showShort(str + mTv3.getText().toString());
//            }
//            return false;
//          }
//          if (mChaomaAdapter.getImages().size() == 0 && !"龙头表".equals(shuibiaozl)) {
//            if (isToast) {
//              ToastUtils.showShort(str + mTv4.getText().toString());
//            }
//            return false;
//          }
//        if (mVideoAdapter.getImages().size() == 0) {
//          if (isToast) {
//              ToastUtils.showShort(str + mTv5.getText().toString());
//          }
//            return false;
//        }
        }
      }
        return true;
    }

    public boolean isNeedSave() {
        if (mBiaoKaBean != null && "2".equals(mBiaoKaBean.getXJLX())) {
            if (mHuanjingAdapter.getImages().size() == 0 ) {
                return false;
            }
        } else {
            if (mHuanjingAdapter.getImages().size() == 0 ) {
                return false;
            }
        }
        return true;
    }




  private void dismissPop() {
    if (mPopwindow != null && mPopwindow.isShowing()) {
      mPopwindow.dismiss();
    }
  }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

  @Override
  public void onItemClick(View view, int position, int type) {
      if (type == Const.PHOTO_TYP_VIDEO) {
          if (position == Const.IMAGE_ITEM_ADD) {
              LogUtils.e("video", type);
              if (!isHistory) {
                  int resolution = SPUtils.getInstance().getInt("resolution", -1);
                  int resolutionMode;
                  if (resolution != -1) {
                      resolutionMode = resolutions[resolution];
                  } else {
//                      resolutionMode = LittleVideoParamConfig.Recorder.RESOLUTION_MODE;
                  }

                  int quality = SPUtils.getInstance().getInt("quality", -1);
                  VideoQuality qualityMode;
                  if (quality != -1) {
                      qualityMode = qualitys[quality];
                  } else {
                      qualityMode = LittleVideoParamConfig.Recorder.VIDEO_QUALITY;
                  }

                  LogUtils.e(resolution + "------" + quality);
                  String OUTPUT_PATH = Environment.getExternalStorageDirectory()
                          + File.separator
                          + Environment.DIRECTORY_DCIM
                          + File.separator
                          + "巡检视频"
                          + File.separator
                          + "VIO_"
                          + System.currentTimeMillis()
                          + ".mp4";
//                  AlivcRecordInputParam recordInputParam = new AlivcRecordInputParam.Builder()
//                          .setResolutionMode(resolutionMode)
//                          .setRatioMode(LittleVideoParamConfig.Recorder.RATIO_MODE)
//                          .setMaxDuration(LittleVideoParamConfig.Recorder.MAX_DURATION)
//                          .setMinDuration(LittleVideoParamConfig.Recorder.MIN_DURATION)
//                          .setVideoQuality(qualityMode)
//                          .setVideoQuality(LittleVideoParamConfig.Recorder.VIDEO_QUALITY)
////                            .setVideoQuality(VideoQuality.PD)
//                          .setGop(LittleVideoParamConfig.Recorder.GOP)
//                          .setVideoCodec(LittleVideoParamConfig.Recorder.VIDEO_CODEC)
//                          .setVideoOutputPath(OUTPUT_PATH)
//                          .build();
//                  AlivcSvideoRecordActivity.startRecord(this, recordInputParam);
              }

          } else {
              if (url != null) {
                  startActivity(new Intent(getActivity(), PlayerRecordVideoActivity.class)
                          .putExtra("path", url));
              }
          }
      } else {
          switch (position) {
              //拍摄照片
              case Const.IMAGE_ITEM_ADD:
                  if (!isHistory) {
                      Log.e("takePhoto", "开始拍照");
//                    View popupView = getActivity().getLayoutInflater().inflate(R.layout.pop_choose_picture, null);
//                    Button takePhoto = (Button) popupView.findViewById(R.id.button_take_photo);
//                    Button pickPhoto = (Button) popupView.findViewById(R.id.button_choose_photo);
//                    Button cancel = (Button) popupView.findViewById(R.id.button_choose_cancel);
////        mPopwindow = new PopupWindow(popupView, mMainLayout.getWidth(),
////                600);
//                    mPopwindow = new PopupWindow(popupView,
//                      ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
//                    mPopwindow.setAnimationStyle(R.style.txt_camera_pop_menu);
//                    mPopwindow.setFocusable(true);
////        mPopwindow.setBackgroundDrawable(new ColorDrawable());
//                    mPopwindow.showAtLocation(mScrollView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                    takePhoto.setOnClickListener(this);
//                    pickPhoto.setOnClickListener(this);
//                    cancel.setOnClickListener(this);
                    takePhoto(type);
                  }
                  break;
              default: //打开预览
                  Intent preViewIntent = new Intent(getActivity(), ImagePreviewDelActivity.class);
                  switch (type) {
                      case Const.PHOTO_TYPE_HUANJING:
                          listEquipment.clear();
                          if(mHuanJingList != null){
                            for (int i =  mHuanJingList.size()-1; i >= 0; i--){
                              Picture picture = new Picture(null, mHuanJingList.get(i).path, i, null);
                              listEquipment.add(picture);
//                              list.add(mHuanJingList.get(i).path);
//                              Bitmap bitmap = null;
//                              ImageView imageView = new ImageView(getActivity());
//                              String wenjianlj = mHuanJingList.get(i).path;
//                              try {
//                                bitmap = SystemEquipmentUtil.decodeBitmap(wenjianlj);
//                                imageView.setImageBitmap(bitmap);
//                                mImageViewList.add(imageView);
//                              } catch (Exception e) {
//                                e.printStackTrace();
//                              }

                            }
                          }

                          break;
                      case Const.PHOTO_TYPE_ZHOUBIAN:
                        listEquipment.clear();
                        if(mZhouBianList != null){
                          for (int i =  mZhouBianList.size()-1; i >= 0; i--){
                            Picture picture = new Picture(null, mZhouBianList.get(i).path, i, null);
                            listEquipment.add(picture);
                          }
                        }
                          break;
                      case Const.PHOTO_TYPE_SHUIBIAO:
                        listEquipment.clear();
                        if(mShuiBiaoList != null){
                          for (int i =  mShuiBiaoList.size()-1; i >= 0; i--){
                            Picture picture = new Picture(null, mShuiBiaoList.get(i).path, i, null);
                            listEquipment.add(picture);
                          }
                        }
                          break;
                      case Const.PHOTO_TYP_CHAOMA:
                        listEquipment.clear();
                        if(mChaomaList != null){
                          for (int i =  mChaomaList.size()-1; i >= 0; i--){
                            Picture picture = new Picture(null, mChaomaList.get(i).path, i, null);
                            listEquipment.add(picture);
                          }
                        }
                          break;
                    case Const.PHOTO_TYP_QUANGJING:
                      listEquipment.clear();
                      if(mQuanjingList != null){
                        for (int i =  mQuanjingList.size()-1; i >= 0; i--){
                          Picture picture = new Picture(null, mQuanjingList.get(i).path, i, null);
                          listEquipment.add(picture);
                        }
                      }
                      default:
                          break;
                  }

                PictureGalleryDialog pictureGalleryDialog = new PictureGalleryDialog(getActivity(), null,
                  listEquipment, position-1);
                pictureGalleryDialog.show();
//                  mAdapter = new PopupWindowImageAdapter(mImageViewList);
//                Window window = getActivity().getWindow();
//                PopupWindowMenu popupWindowMenuNJXX = new PopupWindowMenu(getActivity(), window);
//                popupWindowMenuNJXX.popupWindowXunJianImageViwe(mRlHuanjing,
//                  PopupWindowMenu.ATLOCATION_TOP, AbsListView.LayoutParams.FILL_PARENT,
//                  AbsListView.LayoutParams.FILL_PARENT, "图片", mAdapter,
//                  list, new IBInvoke() {
//                    @Override
//                    public void before() {
//                    }
//
//                    @Override
//                    public <T> void after(T object) {
////                      if (object instanceof Integer) {
////                        int index = (Integer) object;
////                        if ((index >= 0) && (index < newDuoMeiTXXList.size())) {
////                          String name = newDuoMeiTXXList.get(index).getWenjianmc();
////                          mSmoothProgressBar.setVisibility(View.VISIBLE);
////                          mRecordLRPresenter.deleteImage(index, name, mCh);
////                        }
////                      }
//                    }
//
//                    @Override
//                    public void after() {
//                    }
//                  });

//                  if (isHistory) {
//                      preViewIntent.putExtra("commitAllSuccessDate", 1L);
//                  }
//                  preViewIntent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
//                  preViewIntent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//                  startActivityForResult(preViewIntent, type);
                  break;
          }
      }
  }

  @Override
  protected void lazyLoad() {

  }

    @SuppressLint("WrongConstant")
    private void takePhoto(final int type) {
        PermissionUtils.permission(Manifest.permission.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        switch (type) {
                            case Const.PHOTO_TYPE_HUANJING:
                                strType = mTv1.getText().toString() + "_";
                                requestCode = type;
                                takePhoto(false,mHuanJingList);
                                break;
                            case Const.PHOTO_TYPE_ZHOUBIAN:
                                strType = mTv2.getText().toString() + "_";
                                requestCode = type;
                                takePhoto(false,mZhouBianList);
                                break;
                            case Const.PHOTO_TYPE_SHUIBIAO:
                                strType = mTv3.getText().toString() + "_";
                                requestCode = type;
                                takePhoto(false,mShuiBiaoList);
                                break;
                            case Const.PHOTO_TYP_CHAOMA:
                                strType = mTv4.getText().toString() + "_";
                                requestCode = type;
                                takePhoto(false,mChaomaList);
//                                if (mBiaoKaBean!=null && "2".equals(mBiaoKaBean.getXJLX())) {
//                                    strType = "其他照片_";
//                                } else {
//                                    strType = "抄码照片_";
//                                }
                                break;
                          case Const.PHOTO_TYP_VIDEO:
                            strType = mTv5.getText().toString() + "_";
                            requestCode = type;
                            takePhoto(false,mShuiBiaoList);
                            break;
                          case Const.PHOTO_TYP_QUANGJING:
                            strType = mTv6.getText().toString() + "_";
                            requestCode = type;
                            takePhoto(false,mQuanjingList);
                            break;
                            default:
                                break;
                        }

                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showLong("拍照权限被拒，无法进行拍照");
                    }
                }).request();
    }

    private void takePhoto(boolean isFromAlbums, ArrayList<ImageItem> imageList) {
        try {
            //判断照片是否已满
            if (imageList.size() >= PHOTOS_FULL_SIZE) {
                ToastUtils.showShort(
                        R.string.text_pictures_full);
                return;
            }

            File folder = mLrDuoMeitiPresenter.getImagePath();
            if (!folder.exists()) {
                folder.mkdirs();
            }


            mImageName = strType+mBiaoKaBean.getXIAOGENH()+ "_" + System.currentTimeMillis() + ".jpg";

            if (!isFromAlbums) {
                File file = new File(folder, mImageName);
                Uri uri = null;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {//7.0以上
                    uri = Uri.fromFile(file);
                } else {
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        uri = FileProvider.getUriForFile(getContext(), "com.sh3h.meterreading.fileprovider", file);
                    }
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                mActivity.startActivityForResult(intent, 1000);
            } else {
                //相册
              Intent intent = new Intent(Intent.ACTION_PICK, null);
              intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, CAPTURE_ALBUM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BiaoKaWholeEntity getImagesInfo(BiaoKaWholeEntity biaoKaWholeEntity) {
        String images1 = GsonUtils.getGson().toJson(mHuanjingAdapter.getImages());
        String images2 = GsonUtils.getGson().toJson(mZhoubianAdapter.getImages());
        String images3 = GsonUtils.getGson().toJson(mShuibiaoAdapter.getImages());
        String images4 = GsonUtils.getGson().toJson(mChaomaAdapter.getImages());
        String images5 = GsonUtils.getGson().toJson(mVideoAdapter.getImages());
        String images6 = GsonUtils.getGson().toJson(mQuanjingAdapter.getImages());
        biaoKaWholeEntity.setImages1(images1);
        biaoKaWholeEntity.setImages2(images2);
        biaoKaWholeEntity.setImages3(images3);
        biaoKaWholeEntity.setImages4(images4);
        biaoKaWholeEntity.setImages5(images5);
        biaoKaWholeEntity.setImages6(images6);
        biaoKaWholeEntity.setVedioUrl(url);
        return biaoKaWholeEntity;
    }
    private void  compressimage(final String mImageName, final File file){




      try {

        if (!file.exists()) {

          return;
        }

        //缩放的比例，缩放是很难按准备的比例进行缩放的，其值表明缩放的倍数，SDK中建议其值是2的指数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap fBitmap = BitmapFactory.decodeFile(file.getPath(), options);
        if (fBitmap == null) {

          return;
        }

        // rotate the bitmap
//        int degree = getBitmapDegree(file.getPath());
//        if (degree != 0) {
//          fBitmap = rotateBitmapByDegree(fBitmap, degree);
//        }

        // add the stamp
//        fBitmap = addWatermark(fBitmap);

        // save image
        mLrDuoMeitiPresenter.Compressimage(fBitmap, file);
        images=new ArrayList<>();
        ImageItem imageItem=new ImageItem();
        imageItem.setName(mImageName);
        imageItem.setPath(file.getPath());
        images.add(imageItem);
        takeSuccess(images);
      } catch (Exception e) {

      }


    }


  private Bitmap addWatermark(Bitmap bitmap) {
        Bitmap icon = null;
        try {
            int width = bitmap.getWidth();
            int hight = bitmap.getHeight();
            icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(icon);
            Paint photoPaint = new Paint();
            photoPaint.setDither(true);
            photoPaint.setFilterBitmap(true);
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, dst, photoPaint);
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
            textPaint.setTextSize(15.0f);
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            textPaint.setColor(Color.RED);
            textPaint.setAlpha(150);
            String t = TextUtil.format(System.currentTimeMillis(), TextUtil.FORMAT_FULL_DATETIME);
            //Date date = new Date(System.currentTimeMillis());
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //canvas.drawText(sdf.format(date), (float) (width * 0.19), (float) (hight * 0.97), textPaint);
            // canvas.drawText(t, (float) (width * 0.19), (float) (hight * 0.04), textPaint);
            canvas.drawText(t, (float) (width * 0.19), (float) (hight * 0.03), textPaint);
            canvas.save();
            canvas.restore();


        } catch (Exception e) {
            e.printStackTrace();
            icon = bitmap;
        }

        return icon;
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSaveNewImage(Boolean aBoolean) {

    }

    public void processResult(int requestCode, int resultCode, Intent data) {
        if ((1000 == requestCode)
//                && (data != null)
                && (mImageName != null)) {
            File folder = mLrDuoMeitiPresenter.getImagePath();
            File file = new File(folder, mImageName);
            if (!file.exists()) {
                return;
            }
            //保存照片-压缩
//            mMultimediaPresenter.saveNewImage(taskId, taskType, taskState, mImageName, file, false, null);
            compressimage(mImageName, file);
        } else if (requestCode == CAPTURE_ALBUM
                && data != null) {
          Uri originalUri = data.getData();
          ContentResolver resolver = getActivity().getContentResolver();
          try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
            File file = new File(mLrDuoMeitiPresenter.getImagePath(), mImageName);
            if (!file.exists()) {
              return;
            }
            //保存照片-压缩
            compressimage(mImageName, file);
//            mMultimediaPresenter.saveNewImage(taskId + currentTime, taskType, taskState, mImageName, file, true, bm);
          } catch (IOException e) {
            e.printStackTrace();
          }
//            Uri originalUri = data.getData();
//            ContentResolver resolver = getActivity().getContentResolver();
//            try {
//                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
//                File file = new File(mMultimediaPresenter.getImagePath(), mImageName);
//                //保存照片-压缩
//                mMultimediaPresenter.saveNewImage(taskId + currentTime, taskType, taskState, mImageName, file, true, bm);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void onItemLongClick(View view, final int position, int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                  switch (type) {
                      case Const.PHOTO_TYPE_HUANJING:
                          Log.d( "onItemLongClick: ","");
                          builder.setTitle("提示")
                                  .setMessage("你确定要删除该图片吗？")
                                  .setCancelable(true)
                                  .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                      }
                                  })
                                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                          mHuanJingList.remove(position);
                                          mHuanjingAdapter.setImages(mHuanJingList);

                                            //ui更新


                                      }
                                  });
                          break;
                      case Const.PHOTO_TYPE_ZHOUBIAN:
                          Log.d( "onItemLongClick: ","");
                          builder.setTitle("提示")
                                  .setMessage("你确定要删除该图片吗？")
                                  .setCancelable(true)
                                  .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                      }
                                  })
                                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                        mZhouBianList.remove(position);
                                        mZhoubianAdapter.setImages(mZhouBianList);
                                      }
                                  });
                          break;
                      case Const.PHOTO_TYPE_SHUIBIAO:
                          Log.d( "onItemLongClick: ","");
                          builder.setTitle("提示")
                                  .setMessage("你确定要删除该图片吗？")
                                  .setCancelable(true)
                                  .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                      }
                                  })
                                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                        mShuiBiaoList.remove(position);
                                        mShuibiaoAdapter.setImages(mShuiBiaoList);
                                      }
                                  });
                          break;
                      case Const.PHOTO_TYP_CHAOMA:
                     Log.d( "onItemLongClick: ","");
                          builder.setTitle("提示")
                                  .setMessage("你确定要删除该图片吗？")
                                  .setCancelable(true)
                                  .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                      }
                                  })
                                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                        mChaomaList.remove(position);
                                        mChaomaAdapter.setImages(mChaomaList);
                                      }
                                  });
                          break;
                    case Const.PHOTO_TYP_QUANGJING:
                      Log.d( "onItemLongClick: ","");
                      builder.setTitle("提示")
                        .setMessage("你确定要删除该图片吗？")
                        .setCancelable(true)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                          }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                            mQuanjingList.remove(position);
                            mQuanjingAdapter.setImages(mQuanjingList);
                          }
                        });
                      break;
                      default:
                          break;
                  }
                AlertDialog dialog = builder.create();
                dialog.show();

    }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.button_take_photo:
//        takePhoto(ImageType,false);
        break;
      case R.id.button_choose_photo:
//        takePhoto(ImageType,true);
        break;
      case R.id.button_choose_cancel:
        dismissPop();
        break;
      default:
        break;
    }
  }
}
