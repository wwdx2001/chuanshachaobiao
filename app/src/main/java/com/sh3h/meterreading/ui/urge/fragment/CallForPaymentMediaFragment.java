package com.sh3h.meterreading.ui.urge.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.google.common.eventbus.Subscribe;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.YasuoSuccessEntity;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.serverprovider.entity.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.VoiceAdapter;
import com.sh3h.meterreading.aliyun.AlivcRecordInputParam;
import com.sh3h.meterreading.aliyun.AlivcSvideoRecordActivity;
import com.sh3h.meterreading.annotation.SingleClick;
import com.sh3h.serverprovider.entity.VoiceItem;
import com.sh3h.meterreading.ui.InspectionInput.image.LittleVideoParamConfig;
import com.sh3h.meterreading.ui.InspectionInput.image.PlayerRecordVideoActivity;
import com.sh3h.meterreading.ui.InspectionInput.lr.ImagePickerAdapter;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.custom_view.VoiceView;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

//import androidx.core.content.FileProvider;

public class CallForPaymentMediaFragment extends ParentFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener, VoiceAdapter.OnRecyclerViewItemClickListener,
  VoiceView.VoiceListener, VoiceAdapter.OnRecyclerViewItemLongClickListener {


  public CallForPaymentMediaFragment() {
    // Required empty public constructor
  }

  public static final int SNAP_VIDEO = 2001;
  /**
   * 视频最大容量
   */
  private final static int VIDEOS_FULL_SIZE = 1;
  /**
   * 录音最大容量
   */
  private final static int VOICE_FULL_SIZE = 1;
  /**
   * 视频最大时长
   */
  private final static int VIDEO_MAX = 30000;
  /**
   * 视频最短时长
   */
  private final static int VIDEO_MIN = 3000;

  private RecyclerView mRlCuijiao, recyclerVideo, mRlVoice, mOther;
  private ImagePickerAdapter mCuijiaoAdapter, mVideoAdapter ,mOtherAdapter;
  private VoiceAdapter mVoiceAdapter;
  private ArrayList<ImageItem> images;
  private ArrayList<ImageItem> mCuijiaoList;
  private ArrayList<VoiceItem> mVoiceList;
  private ArrayList<ImageItem> mOtherList;

//  @BindViewView(R.id.voice_view)
  private VoiceView mVoiceView;


  @Inject
  Bus mEventBus;
  /**
   * 录像集合
   */
  private ArrayList<ImageItem> mVideoList;
  private String strType;
  private String url;
  private boolean isHistory;
  private CallForPaymentBackFillDataBean mDataBean;
  private TextView mTv1, mTv2, mTv5, mTvNew6;

  private MediaPlayer mMediaPlayer;

  private int[] resolutions = {AliyunSnapVideoParam.RESOLUTION_360P, AliyunSnapVideoParam.RESOLUTION_480P,
    AliyunSnapVideoParam.RESOLUTION_540P, AliyunSnapVideoParam.RESOLUTION_720P};
  private VideoQuality[] qualitys = {VideoQuality.SSD, VideoQuality.HD, VideoQuality.SD, VideoQuality.LD,
    VideoQuality.PD, VideoQuality.EPD};

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_call_for_payment_media;
  }

  @Override
  protected void initView1(View view) {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);

    mEventBus.register(this);


    mTv1 = view.findViewById(R.id.tv1);
    mTv2 = view.findViewById(R.id.tv2);
    mTv5 = view.findViewById(R.id.tv5);
    mTvNew6 = view.findViewById(R.id.tv_new_6);

    mRlCuijiao = (RecyclerView) view.findViewById(R.id.recycler_type1);
    mRlVoice = (RecyclerView) view.findViewById(R.id.recycler_voice);
    mOther = (RecyclerView) view.findViewById(R.id.recycler_new_other);
    recyclerVideo = (RecyclerView) view.findViewById(R.id.recycler_video);

    mVoiceView = view.findViewById(R.id.voice_view);

    mCuijiaoList = new ArrayList<>();
    mVideoList = new ArrayList<>();
    mVoiceList = new ArrayList<>();
    mOtherList = new ArrayList<>();

    mRlCuijiao.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    mRlCuijiao.setHasFixedSize(true);
    mRlCuijiao.setNestedScrollingEnabled(false);
    mCuijiaoAdapter = new ImagePickerAdapter(getActivity(), mCuijiaoList, 3, Const.PHOTO_TYP_CALLPAY);
    mRlCuijiao.setAdapter(mCuijiaoAdapter);

    mOther.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    mOther.setHasFixedSize(true);
    mOther.setNestedScrollingEnabled(false);
    mOtherAdapter = new ImagePickerAdapter(getActivity(), mOtherList, 3, Const.PHOTO_TYP_OTHER);
    mOther.setAdapter(mOtherAdapter);

    mRlVoice.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    mRlVoice.setHasFixedSize(true);
    mRlVoice.setNestedScrollingEnabled(false);
    mVoiceAdapter = new VoiceAdapter(getActivity(), mVoiceList, VOICE_FULL_SIZE, Const.SOUND_RECORD);
    mRlVoice.setAdapter(mVoiceAdapter);

    recyclerVideo.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    recyclerVideo.setHasFixedSize(true);
    recyclerVideo.setNestedScrollingEnabled(false);
    mVideoAdapter = new ImagePickerAdapter(getActivity(), mVideoList, VIDEOS_FULL_SIZE, Const.PHOTO_TYP_VIDEO);
    recyclerVideo.setAdapter(mVideoAdapter);

    mCuijiaoAdapter.setOnItemClickListener(this);
    mVoiceAdapter.setOnItemClickListener(this);
    mOtherAdapter.setOnItemClickListener(this);
    mVideoAdapter.setOnItemClickListener(this);
    mVideoAdapter.setOnItemLongClickListener(onItemLongClick2);
    mVoiceAdapter.setOnItemLongClickListener(this);
    mVoiceView.setOnVoiceListener(this);

  }

  @Override
  public void initData1() {
    super.initData1();
    Bundle bundle = getArguments();
    if (bundle != null) {
      mDataBean = bundle.getParcelable(Const.CALLFORPAYMENTBACKFILLDATABEAN);
      if (mDataBean != null) {
        Type listType = new TypeToken<ArrayList<ImageItem>>() {
        }.getType();
        Type voiceListType = new TypeToken<ArrayList<VoiceItem>>() {
        }.getType();

        ArrayList<ImageItem> mChoujians = GsonUtils.getGson().fromJson(mDataBean.getCallForPayImages(), listType);
        if (mChoujians != null && mChoujians.size() > 0) {
          mCuijiaoList.clear();
          mCuijiaoList.addAll(mChoujians);
          mCuijiaoAdapter.setImages(mCuijiaoList);
        } else {
          mCuijiaoList.clear();
          mCuijiaoAdapter.setImages(mCuijiaoList);
        }

        ArrayList<VoiceItem> mVoices = GsonUtils.getGson().fromJson(mDataBean.getVoices(), voiceListType);
        if (mVoices != null && mVoices.size() > 0) {
          mVoiceList.clear();
          mVoiceList.addAll(mVoices);
          mVoiceAdapter.setVoice(mVoiceList);
        } else {
          mVoiceList.clear();
          mVoiceAdapter.setVoice(mVoiceList);
        }

        ArrayList<ImageItem> mOthers = GsonUtils.getGson().fromJson(mDataBean.getOtherImages(), listType);
        if (mOthers != null && mOthers.size() > 0) {
          mOtherList.clear();
          mOtherList.addAll(mOthers);
          mOtherAdapter.setImages(mOtherList);
        } else {
          mOtherList.clear();
          mOtherAdapter.setImages(mOtherList);
        }


        ArrayList<ImageItem> mVideos = GsonUtils.getGson().fromJson(mDataBean.getVideos(), listType);
        if (mVideos != null && mVideos.size() > 0) {
          mVideoList.clear();
          mVideoList.addAll(mVideos);
          mVideoAdapter.setImages(mVideoList);
          url = mDataBean.getVideoUrl();
        } else {
          mVideoList.clear();
          mVideoAdapter.setImages(mVideoList);
        }
      }

      Log.e("hellochao", "表类型：");
    }
  }

  private int requestCode;

  @SuppressLint("WrongConstant")
  private void takePhoto(int type) {
    PermissionUtils.permission(Manifest.permission.CAMERA)
      .callback(new PermissionUtils.SimpleCallback() {
        @Override
        public void onGranted() {
          switch (type) {
            case Const.PHOTO_TYP_CALLPAY:
              strType = mTv1.getText().toString() + "_";
              break;
            case Const.PHOTO_TYP_OTHER:
              strType = mTvNew6.getText().toString() + "_";
              break;
            default:
              break;
          }
          Intent cameraIntent = new Intent(getActivity(), ImageGridActivity.class);
//          cameraIntent.putExtra(ImageGridActivity.XIAO_GEN_HAO, "1");

          cameraIntent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
          cameraIntent.putExtra(ImageGridActivity.PHOTO_TYPE, strType);
          cameraIntent.putExtra(ImageGridActivity.CAMERA_TYPE, ImageGridActivity.CAMERA_TYPE_PHOTO);
          requestCode = type;
          startActivityForResult(cameraIntent, type);
        }

        @Override
        public void onDenied() {
          ToastUtils.showLong("拍照权限被拒，无法进行拍照");
        }
      }).request();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @SuppressLint("WrongConstant")
  public void takeVideo() {
    PermissionUtils.permission(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
      .callback(new PermissionUtils.SimpleCallback() {
        @Override
        public void onGranted() {
          //判断视频是否已满
          if (mVideoList.size() >= VIDEOS_FULL_SIZE) {
            ToastUtils.showShort("视频已满");
            return;
          }

          LogUtils.e("video", "开始录制");

          Uri uri = null;
          Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
          try {
            if (Build.VERSION.SDK_INT >= 24) {//7.0 Android N
              //com.xxx.xxx.fileprovider为上述manifest中provider所配置相同
              uri = FileProvider.getUriForFile(getActivity(), "${applicationId}.fileprovider", createMediaFile());
              intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//7.0以后，系统要求授予临时uri读取权限，安装完毕以后，系统会自动收回权限，该过程没有用户交互
            } else {//7.0以下
              uri = Uri.fromFile(createMediaFile()); // create a file to save the video
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);
          } catch (IllegalArgumentException e) {
            e.printStackTrace();
          } catch (ActivityNotFoundException e) {
            e.printStackTrace();
          } catch (Exception e) {
            e.printStackTrace();
          }
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);  // set the image file name
          intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3000);
          intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
          // start the Video Capture Intent
          startActivityForResult(intent, SNAP_VIDEO);
        }

        @Override
        public void onDenied() {
          ToastUtils.showLong("录制权限被拒，无法进行拍照");
        }
      }).request();
  }

  private File createMediaFile() throws IOException {
    String path = Environment.getExternalStorageDirectory() + "sh3h/meterreading/images/";
    File mediaStorageDir = new File(path);
    if (!mediaStorageDir.exists()) {
      if (!mediaStorageDir.mkdirs()) {
        return null;
      }
    }

    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "催缴_" + timeStamp;
    String suffix = ".mp4";
    File mediaFile = new File(path + File.separator + imageFileName + suffix);
    return mediaFile;
  }

  private File createVoiceFile() throws IOException {
    String path = Environment.getExternalStorageDirectory() + "/sh3h/meterreading/voices/";
    File mediaStorageDir = new File(path);
    if (!mediaStorageDir.exists()) {
      if (!mediaStorageDir.mkdirs()) {
        return null;
      }
    }

    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "VoiceID_" + timeStamp;
    String suffix = ".wav";
    File mediaFile = new File(path, imageFileName + suffix);
    mediaFile.createNewFile();
    return mediaFile;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
      Log.e("paizhao", "多媒体照片");
      if (data != null) {
        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        takeSuccess(images);
      }
    } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
      //预览图片返回
      if (data != null) {
        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        previewPhoto(requestCode, images);
      }
    } else if (requestCode == SNAP_VIDEO) {
      if (data != null) {
        url = data.getStringExtra("url");
        LogUtils.e("video", url);
        if (url != null) {
          String vdeoThum = bitmap2File(getVideoThumbnail(url, 400,
            400, MediaStore.Video.Thumbnails.MICRO_KIND), "催缴_" + System.currentTimeMillis());
          ImageItem imageItem = new ImageItem();
          imageItem.path = vdeoThum;
          mVideoList.add(imageItem);
          mVideoAdapter.setImages(mVideoList);

        }
      }
    }
  }

  private void previewPhoto(int requestCode, ArrayList<ImageItem> images) {
    if (images != null) {
      switch (requestCode) {
        case Const.PHOTO_TYP_CALLPAY:
          addCamera(mCuijiaoList, mCuijiaoAdapter, images);
          break;
        case Const.PHOTO_TYP_OTHER:
          addCamera(mOtherList, mOtherAdapter, images);
          break;
        default:
          break;
      }
    }
  }

  @Subscribe
  public void yasuoCallBack(YasuoSuccessEntity yasuoSuccessEntity) {
    if (ImageGridActivity.CAMERA_TYPE_PHOTO.equals(yasuoSuccessEntity.getCameraType())) {
      images = yasuoSuccessEntity.getImages();
      takeSuccess(images);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mEventBus.unregister(this);

  }

  private void takeSuccess(ArrayList<ImageItem> images) {
    if (images != null) {
      switch (requestCode) {
        case Const.PHOTO_TYP_CALLPAY:
          mCuijiaoList.addAll(images);
          mCuijiaoAdapter.setImages(mCuijiaoList);
          break;
        case Const.PHOTO_TYP_OTHER:
          mOtherList.addAll(images);
          mOtherAdapter.setImages(mOtherList);
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void onItemLongClick(View view, int position, int type) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("提示")
      .setMessage("你确定要删除该录音吗？")
      .setCancelable(true)
      .setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
        }
      })
      .setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          mVoiceList.remove(position);
          mVoiceAdapter.setVoice(mVoiceList);
        }
      });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  ImagePickerAdapter.OnRecyclerViewItemLongClickListener onItemLongClick2 = new ImagePickerAdapter.OnRecyclerViewItemLongClickListener() {
    @Override
    public void onItemLongClick(View view, int position, int type) {
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



  @SingleClick
  @SuppressLint("WrongConstant")
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
            resolutionMode = LittleVideoParamConfig.Recorder.RESOLUTION_MODE;
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
            + "sh3h/meterreading/images"
            + File.separator
            + "催缴_"
            + System.currentTimeMillis()
            + ".mp4";
          AlivcRecordInputParam recordInputParam = new AlivcRecordInputParam.Builder()
            .setResolutionMode(resolutionMode)
            .setRatioMode(LittleVideoParamConfig.Recorder.RATIO_MODE)
            .setMaxDuration(LittleVideoParamConfig.Recorder.MAX_DURATION)
            .setMinDuration(LittleVideoParamConfig.Recorder.MIN_DURATION)
            .setVideoQuality(qualityMode)
            .setVideoQuality(LittleVideoParamConfig.Recorder.VIDEO_QUALITY)
//                            .setVideoQuality(VideoQuality.PD)
            .setGop(LittleVideoParamConfig.Recorder.GOP)
            .setVideoCodec(LittleVideoParamConfig.Recorder.VIDEO_CODEC)
            .setVideoOutputPath(OUTPUT_PATH)
            .build();
          AlivcSvideoRecordActivity.startRecord(this, recordInputParam);
        }

      } else {
        if (url != null) {
          startActivity(new Intent(getActivity(), PlayerRecordVideoActivity.class)
            .putExtra("path", url));
        }
      }
    } else if (type == Const.SOUND_RECORD){
      try {
        playVoice(position);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      switch (position) {
        //拍摄照片
        case Const.IMAGE_ITEM_ADD:
          if (!isHistory) {
            Log.e("takePhoto", "开始拍照");
            takePhoto(type);
          }
          break;
        default: //打开预览
          Intent preViewIntent = new Intent(getActivity(), ImagePreviewDelActivity.class);
          switch (type) {
            case Const.PHOTO_TYP_CALLPAY:
              preViewIntent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) mCuijiaoAdapter.getImages());
              break;
            case Const.PHOTO_TYP_OTHER:
              preViewIntent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) mOtherAdapter.getImages());
              break;
            default:
              break;
          }
          preViewIntent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
          preViewIntent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
          //preViewIntent.putExtra("isEditable", isEditable());
          startActivityForResult(preViewIntent, type);
          break;
      }
    }
  }

  private void playVoice(int position) throws IOException {
    if (mVoiceList.size() == 0) {
      return;
    }
    VoiceItem item = mVoiceList.get(position);
    if (TextUtil.isNullOrEmpty(item.path)) {
      ToastUtils.showLong("当前语音不存在，无法播放");
      return;
    }
    if (mMediaPlayer != null) {
      mMediaPlayer.stop();
      mMediaPlayer.release();
      mMediaPlayer = null;
    }

    mMediaPlayer = new MediaPlayer();
//                            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                @Override
//                                public void onCompletion(MediaPlayer mp) {
//                                    mp.release();
//                                    mMediaPlayer = null;
//                                }
//                            });

    mMediaPlayer.setDataSource(item.path);
    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    mMediaPlayer.prepare();
    mMediaPlayer.start();
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
    File f = new File(Environment.getExternalStorageDirectory() + "/sh3h/meterreading/images/" + name + ".mp4");
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
//    if(mBiaoKaBean!=null) {
//      String shuibiaozl = mBiaoKaBean.getI_SHUIBIAOZL();
//      if (mBiaoKaBean != null && "2".equals(mBiaoKaBean.getXJLX())) {
        if (mCuijiaoAdapter.getImages().size() == 0) {
          if (isToast) {
            ToastUtils.showShort(str + mTv1.getText().toString());
          }
          return false;
        }
//      }
//    }
    return true;
  }

  public boolean isNeedSave() {
//    if (mBiaoKaBean != null && "2".equals(mBiaoKaBean.getXJLX())) {
      if (mCuijiaoAdapter.getImages().size() == 0) {
        return false;
      }
//    }
    return true;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  /**
   * 开始录音
   * @param isFull
   */
  @SuppressLint("WrongConstant")
  @Override
  public void startRecord(boolean isFull) {
    // 申请麦克风权限
    if (!com.sh3h.meterreading.util.PermissionUtils.checkPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
      com.sh3h.meterreading.util.PermissionUtils.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1001);
      mVoiceView.release();
      return;
    }
    try {
      File voiceFile = createVoiceFile();
      VoiceItem item = new VoiceItem();
      item.path = voiceFile.getAbsolutePath();
      item.name = voiceFile.getName();
      mVoiceView.setVoiceItem(item);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param fileName 保存的文件名
   * @param time     录音时长
   * @param voiceItem 当前录音详情
   */
  @Override
  public void endRecord(String fileName, long time, VoiceItem voiceItem) {
    voiceItem.size = time;
    mVoiceList.add(voiceItem);
    mVoiceAdapter.setVoice(mVoiceList);
  }


  public CallForPaymentBackFillDataBean getMediaInfo(CallForPaymentBackFillDataBean dataBean) {
    if (mCuijiaoAdapter.getImages().size() == 0) {
      ToastUtils.showLong("请拍摄催缴照片");
      return null;
    }

    String cuijiaoImgJson = GsonUtils.getGson().toJson(mCuijiaoAdapter.getImages());
    String otherImgJson = GsonUtils.getGson().toJson(mOtherAdapter.getImages());
    String voices = GsonUtils.getGson().toJson(mVoiceAdapter.getVoices());
    String videos = GsonUtils.getGson().toJson(mVideoAdapter.getImages());

    dataBean.setCallForPayImages(cuijiaoImgJson);
    dataBean.setOtherImages(otherImgJson);
    dataBean.setVoices(voices);
    dataBean.setVideos(videos);
    dataBean.setVideoUrl(url);

    return dataBean;
  }

  public ArrayList<ImageItem> getCuijiaoImages() {
    return mCuijiaoList;
  }

  public ArrayList<ImageItem> getOtherImages() {
    return mOtherList;
  }

  public ArrayList<VoiceItem> getVoices() {
    return mVoiceList;
  }

  public ArrayList<ImageItem> getVideos() {
    return mVideoList;
  }

  @Override
  protected void lazyLoad() {

  }
}
