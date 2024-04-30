package com.sh3h.meterreading.ui.urge.delay;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.YasuoSuccessEntity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.aliyun.AlivcRecordInputParam;
import com.sh3h.meterreading.aliyun.AlivcSvideoRecordActivity;
import com.sh3h.meterreading.aliyun.LittleVideoParamConfig;
import com.sh3h.meterreading.ui.InspectionInput.image.PlayerRecordVideoActivity;
import com.sh3h.meterreading.ui.InspectionInput.lr.ImagePickerAdapter;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.CountDownTimer;
import com.sh3h.meterreading.util.TimerListener;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.ImageItem;
import com.sh3h.serverprovider.entity.ResultBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.subsciber.ProgressSubscriber;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static com.sh3h.meterreading.aliyun.AlivcSvideoRecordActivity.SNAP_VIDEO;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDelayMultiMediaFragment extends ParentFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /**
     * 视频最大容量
     */
    private final static int VIDEOS_FULL_SIZE = 1;
    /**
     * 视频最大时长
     */
    private final static int VIDEO_MAX = 30000;
    /**
     * 视频最短时长
     */
    private final static int VIDEO_MIN = 3000;

    private String mParam1;
    private String mParam2;

    private TextView mTv1, mTv2, mTv3;
    private RecyclerView mPhotoRecyclerView;
    private ImagePickerAdapter mPhotoAdapter;
    private ArrayList<ImageItem> mPhotoList;
    private RecyclerView mVoiceRecyclerView;
    private ImagePickerAdapter mVoiceAdapter;
    private ArrayList<ImageItem> mVoiceList;
    private RecyclerView mVideoRecyclerView;
    private ImagePickerAdapter mVideoAdapter;
    private List<ImageItem> mVideoList;
    private ArrayList<ImageItem> images;

    private String strType;
    private String url;

    private int[] resolutions = {AliyunSnapVideoParam.RESOLUTION_360P, AliyunSnapVideoParam.RESOLUTION_480P,
            AliyunSnapVideoParam.RESOLUTION_540P, AliyunSnapVideoParam.RESOLUTION_720P};
    private VideoQuality[] qualitys = {VideoQuality.SSD, VideoQuality.HD, VideoQuality.SD, VideoQuality.LD,
            VideoQuality.PD, VideoQuality.EPD};

    public static OrderDelayMultiMediaFragment newInstance(String param1, String param2) {
        OrderDelayMultiMediaFragment fragment = new OrderDelayMultiMediaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("helloworld222", "OrderDelayMultiMediaFragment");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        String audioPath = Environment.getExternalStorageDirectory()
                + File.separator + "sh3h" + File.separator + "meterreading"
                + File.separator + "audio" + File.separator + "delay" + File.separator;
        RecordManager.getInstance().changeRecordDir(audioPath);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_delay_multi_media;
    }

    @Override
    protected void initView1(View view) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mPhotoList = new ArrayList<>();
        mVoiceList = new ArrayList<>();
        mVideoList = new ArrayList<>();

        mTv1 = view.findViewById(R.id.tv1);
        mTv2 = view.findViewById(R.id.tv2);
        mTv3 = view.findViewById(R.id.tv3);

        mPhotoRecyclerView = view.findViewById(R.id.recycler_type1);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPhotoRecyclerView.setHasFixedSize(true);
        mPhotoRecyclerView.setNestedScrollingEnabled(false);
        mPhotoAdapter = new ImagePickerAdapter(getActivity(), mPhotoList, 3, Const.PHOTO_TYPE1, Const.TYPE_PHOTO);
        mPhotoRecyclerView.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setOnItemClickListener(this);


        mVoiceRecyclerView = view.findViewById(R.id.recycler_type2);
        mVoiceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mVoiceRecyclerView.setHasFixedSize(true);
        mVoiceRecyclerView.setNestedScrollingEnabled(false);
        mVoiceAdapter = new ImagePickerAdapter(true, getActivity(), mVoiceList, 1, Const.PHOTO_TYPE_VOICE, Const.TYPE_VOICE);
        mVoiceRecyclerView.setAdapter(mVoiceAdapter);
        mVoiceAdapter.setOnItemClickListener(this);
        mVoiceAdapter.setOnItemLongClickListener(onItemLongClick1);

        mVideoRecyclerView = view.findViewById(R.id.recycler_type3);
        mVideoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setNestedScrollingEnabled(false);
        mVideoAdapter = new ImagePickerAdapter(getActivity(), mVideoList, VIDEOS_FULL_SIZE, Const.PHOTO_TYPE_VIDEO, Const.TYPE_VIDEO);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
        mVideoAdapter.setOnItemClickListener(this);
        mVideoAdapter.setOnItemLongClickListener(onItemLongClick2);
    }

    private int requestCode;

    @SuppressLint("WrongConstant")
    private void takePhoto(int type) {
        PermissionUtils.permission(Manifest.permission.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        switch (type) {
                            case Const.PHOTO_TYPE1:
                                ImagePicker.getInstance().setSelectLimit(3 - mPhotoAdapter.getImages().size());
                                strType = "延期_";
                                break;
                            default:
                                break;
                        }
                        Intent cameraIntent = new Intent(getActivity(), ImageGridActivity.class);
                        cameraIntent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, false);
                        cameraIntent.putExtra(ImageGridActivity.NB_PHOTO, true);
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

    @Override
    protected void lazyLoad() {

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
        String path = Environment.getExternalStorageDirectory() + "/sh3h/meterreading/video/";
        File mediaStorageDir = new File(path);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "延期_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(path + File.separator + imageFileName + suffix);
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            Log.e("paizhao", "多媒体照片");
            if (data != null) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
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
                    videoPath = url;
                    String vdeoThum = bitmap2File(getVideoThumbnail(url, 400,
                            400, MediaStore.Video.Thumbnails.MICRO_KIND), "延期_" + System.currentTimeMillis());
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = vdeoThum;
                    mVideoList.clear();
                    mVideoList.add(imageItem);
                    mVideoAdapter.setImages(mVideoList);
                }
            }
        } else if (requestCode == Const.REQUEST_CODE_VOICE) {
            if (data != null) {
                url = data.getStringExtra("url");
                LogUtils.e("voice", url);
                if (url != null) {
                    ImageItem imageItem = new ImageItem();
                    mVideoList.add(imageItem);
                    mVideoAdapter.setImages(mVideoList);
                }
            }
        }
    }

    private void previewPhoto(int requestCode, ArrayList<ImageItem> images) {
        if (images != null) {
            switch (requestCode) {
                case Const.PHOTO_TYPE1:
                    addCamera(mPhotoList, mPhotoAdapter, images);
                    break;
                default:
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void yasuoCallBack(YasuoSuccessEntity yasuoSuccessEntity) {
        if (ImageGridActivity.CAMERA_TYPE_PHOTO.equals(yasuoSuccessEntity.getCameraType())) {
            images = yasuoSuccessEntity.getImages();
            takeSuccess(images);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void takeSuccess(ArrayList<ImageItem> images) {
        if (images != null) {
            switch (requestCode) {
                case Const.PHOTO_TYPE1:
                    mPhotoList.addAll(images);
                    mPhotoAdapter.setImages(mPhotoList);
                    break;
                default:
                    break;
            }
        }
    }

    ImagePickerAdapter.OnRecyclerViewItemLongClickListener onItemLongClick1 = new ImagePickerAdapter.OnRecyclerViewItemLongClickListener() {
        @Override
        public void onItemLongClick(View view, int position, int type) {
            if (position != Const.IMAGE_ITEM_ADD) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示")
                        .setMessage("你确定要删除该音频吗？")
                        .setCancelable(true)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mVoiceList = new ArrayList<>();
                                mVoiceAdapter = new ImagePickerAdapter(true, getActivity(), mVoiceList, 1, Const.PHOTO_TYPE_VOICE, Const.TYPE_VOICE);
                                mVoiceRecyclerView.setAdapter(mVoiceAdapter);
                                mVoiceAdapter.setOnItemClickListener(OrderDelayMultiMediaFragment.this);
                                mVoiceAdapter.setOnItemLongClickListener(onItemLongClick1);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    };

    ImagePickerAdapter.OnRecyclerViewItemLongClickListener onItemLongClick2 = new ImagePickerAdapter.OnRecyclerViewItemLongClickListener() {
        @Override
        public void onItemLongClick(View view, int position, int type) {
            if (position != Const.IMAGE_ITEM_ADD) {
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
    public void onItemClick(View view, int position, int type) {
        if (type == Const.PHOTO_TYPE_VIDEO) {
            if (position == Const.IMAGE_ITEM_ADD) {
                LogUtils.e("video", type);
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
                        + File.separator + "sh3h" + File.separator + "meterreading"
                        + File.separator + "video" + File.separator
                        + "延期_" + System.currentTimeMillis() + ".mp4";

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

            } else {
                if (url != null) {
                    startActivity(new Intent(getActivity(), PlayerRecordVideoActivity.class)
                            .putExtra("path", url));
                }
            }
        } else if (type == Const.PHOTO_TYPE_VOICE) {
            switch (position) {
                case Const.IMAGE_ITEM_ADD:
                    startRecord();
                    break;
                default: // 播放音频
                    playVoiceFile();
                    break;
            }

        } else {
            switch (position) {
                //拍摄照片
                case Const.IMAGE_ITEM_ADD:
                    takePhoto(type);
                    break;
                default: //打开预览
                    Intent preViewIntent = new Intent(getActivity(), ImagePreviewDelActivity.class);
                    switch (type) {
                        case Const.PHOTO_TYPE1:
                            preViewIntent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) mPhotoAdapter.getImages());
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

    private MediaPlayer mediaPlayer;

    private void playVoiceFile() {
        View recordView = View.inflate(getContext(), R.layout.record_dialog, null);
        ImageView ivAnimation = recordView.findViewById(R.id.iv_animation);
        TextView tvTime = recordView.findViewById(R.id.tv_time);
        Button btnStartPause = recordView.findViewById(R.id.btn_start_pause);
        tvTime.setVisibility(View.GONE);
        btnStartPause.setVisibility(View.GONE);
        Button btnStop = recordView.findViewById(R.id.btn_stop);
        btnStop.setEnabled(true);
        btnStop.setText("停止播放");
        AnimationDrawable animation = (AnimationDrawable) ivAnimation.getBackground();
        animation.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(recordView);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //1.初始化midiaplayer
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.reset();
            //2.设置要播放的资源位置  path 可以是网络路径 也可以是本地路径
            mediaPlayer.setDataSource(mVoiceAdapter.getImages().get(0).path);
            //3.准备播放
            mediaPlayer.prepare();
            mediaPlayer.setLooping(false);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    alertDialog.dismiss();
                    ToastUtils.showShort("音频播放完毕");
                }
            });
            //4.开始播放
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                alertDialog.dismiss();
            }
        });
    }

    boolean isPause = false;
    boolean isFirst = true;
    boolean isResume = false;

    /**
     * 开始录音
     */
    private void startRecord() {
        RecordManager.getInstance().setRecordResultListener(new RecordResultListener() {
            @Override
            public void onResult(File result) {
                ImageItem imageItem = new ImageItem();
                imageItem.path = result.getAbsolutePath();
                mVoiceList.add(imageItem);
                mVoiceAdapter.setImages(mVoiceList);
                Log.e("helloworld", "录音完成" + result.getAbsolutePath());
            }
        });
        isFirst = true;
        isPause = false;
        isResume = false;
        View recordView = View.inflate(getContext(), R.layout.record_dialog, null);
        ImageView ivAnimation = recordView.findViewById(R.id.iv_animation);
        TextView tvTime = recordView.findViewById(R.id.tv_time);
        Button btnStartPause = recordView.findViewById(R.id.btn_start_pause);
        Button btnStop = recordView.findViewById(R.id.btn_stop);
        AnimationDrawable animation = (AnimationDrawable) ivAnimation.getBackground();
//        animation.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(recordView);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000);
        countDownTimer.setCountDownListener(new TimerListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onTick(long millLong) {
                String value = String.valueOf((int) (millLong / 1000));
                tvTime.setText("剩余时长：" + value + "s");
            }

            @Override
            public void onFinish() {
                tvTime.setText("音频录制完成");
                btnStartPause.setText("开始");
                btnStop.setEnabled(false);
                isFirst = true;
                isPause = false;
                isResume = false;
                animation.stop();
                RecordManager.getInstance().stop();
                countDownTimer.cancel();
                alertDialog.dismiss();
            }
        });

//        countDownTimer.start();
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStop.setEnabled(true);
                if (isFirst) { // 开始
                    isFirst = false;
                    isPause = true;
                    isResume = false;
                    btnStartPause.setText("暂停");
                    animation.start();
                    RecordManager.getInstance().start();

                    countDownTimer.start();
                    return;
                }
                if (isPause) { // 暂停
                    isFirst = false;
                    isPause = false;
                    isResume = true;
                    btnStartPause.setText("继续");
                    animation.stop();
                    RecordManager.getInstance().pause();
                    countDownTimer.pause();
                    return;
                }
                if (isResume) {
                    isFirst = false;
                    isPause = true;
                    isResume = false;
                    btnStartPause.setText("暂停");
                    animation.start();
                    RecordManager.getInstance().resume();
                    countDownTimer.resume();
                    return;
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStartPause.setText("开始");
                btnStop.setEnabled(false);
                isFirst = true;
                isPause = false;
                isResume = false;
                animation.stop();
                RecordManager.getInstance().stop();
                countDownTimer.cancel();
                alertDialog.dismiss();
//                countDownTimer.cancel();
            }
        });
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
        File f = new File(Environment.getExternalStorageDirectory() + "/sh3h/meterreading/video/" + name + ".jpg");
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

    private String videoPath;

    private boolean isError;

    protected boolean isError() {
        return this.isError;
    }

    /**
     * 提交多媒体数据
     */
    protected void commitMultiMediaData(String gongdanbh, CallBack<String> callBack) {
        List<ImageItem> photoImages = mPhotoAdapter.getImages();
        List<ImageItem> voiceImages = mVoiceAdapter.getImages();
        List<File> videoFiles = new ArrayList<>();
        if (!StringUtils.isTrimEmpty(videoPath)) {
            File videoFile = new File(videoPath);
            if (videoFile.exists()) {
                videoFiles.add(videoFile);
            }
        }
        List<File> photoFiles = new ArrayList<>();
        for (int i = 0; i < photoImages.size(); i++) {
            File photoFile = new File(photoImages.get(i).path);
            if (photoFile.exists()) {
                photoFiles.add(photoFile);
            }
        }

        List<File> audioFiles = new ArrayList<>();
        for (int i = 0; i < voiceImages.size(); i++) {
            File audioFile = new File(voiceImages.get(i).path);
            if (audioFile.exists()) {
                audioFiles.add(audioFile);
            }
        }
        IProgressDialog iProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("正在上传文件...");
                return progressDialog;
            }
        };
        List<Observable<String>> observableList = new ArrayList<>();
        if (photoFiles.size() > 0) {
            //上传照片
            Observable<String> executePhoto = EasyHttp.post(URL.BASE_XUNJIAN_URL + URL.UploadFile)
//        EasyHttp.post("http://10.6.87.32:3333/XUNJIAN/UploadFile")
                    .params("S_LEIXING", "PHOTO_DELAY")
                    .params("S_GONGDANBH", gongdanbh)
                    .addFileParams("S_ZHAOPIANLJ", photoFiles, null)
                    .cacheMode(CacheMode.NO_CACHE)
                    .retryCount(0)
                    .sign(true)
                    .timeStamp(true)
                    .execute(String.class);
            observableList.add(executePhoto);
        }
        if (audioFiles.size() > 0) {
            //上传音频
            Observable<String> executeVoice = EasyHttp.post(URL.BASE_XUNJIAN_URL + URL.UploadFile)
//                            EasyHttp.post("http://10.6.87.32:3333/XUNJIAN/UploadFile")
                    .params("S_LEIXING", "YP_DELAY")
                    .params("S_GONGDANBH", gongdanbh)
                    .addFileParams("S_ZHAOPIANLJ", audioFiles, null)
                    .cacheMode(CacheMode.NO_CACHE)
                    .sign(true)
                    .timeStamp(true)
                    .execute(String.class);
            observableList.add(executeVoice);
        }
        if (videoFiles.size() > 0) {
            //上传视频
            Observable<String> executeVideo = EasyHttp.post(URL.BASE_XUNJIAN_URL + URL.UploadFile)
//                            EasyHttp.post("http://10.6.87.32:3333/XUNJIAN/UploadFile")
                    .params("S_LEIXING", "SP_DELAY")
                    .params("S_GONGDANBH", gongdanbh)
                    .addFileParams("S_ZHAOPIANLJ", videoFiles, null)
                    .cacheMode(CacheMode.NO_CACHE)
                    .sign(true)
                    .timeStamp(true)
                    .execute(String.class);
            observableList.add(executeVideo);
        }
        if (observableList.size() <= 0) {
            callBack.onSuccess("延期申请成功");
            return;
        }

        Observable.zip(observableList, new Function<Object[], List<String>>() {
            @Override
            public List<String> apply(Object[] objects) throws Exception {
                List<String> results = new ArrayList<>();
                for (int i = 0; i < objects.length; i++) {
                    results.add(objects[i].toString());
                }
                return results;
            }
        }).subscribe(new ProgressSubscriber<List<String>>(getContext(), iProgressDialog) {
            @Override
            public void onComplete() {
                super.onComplete();
            }

            @Override
            public void onError(ApiException e) {
                super.onError(e);
                isError = true;
                callBack.onError(e);
            }

            @Override
            public void onNext(List<String> s) {
                super.onNext(s);
                String errorMsg = "";
                for (int i = 0; i < s.size(); i++) {
                    ResultBean baseBean = GsonUtils.fromJson(s.get(i), ResultBean.class);
                    if (!"1".equals(baseBean.getMsgCode())) {
                        isError = true;
                        if (StringUtils.isTrimEmpty(baseBean.getMsgInfo())) {
                            errorMsg = baseBean.getMsgInfo();
                        }
                    }
                }
                if (isError && !StringUtils.isTrimEmpty(errorMsg)) {
                    ToastUtils.showShort("延期申请成功，文件上传失败：" + errorMsg);
                }
                if (!isError) {
                    callBack.onSuccess("延期申请成功，文件上传成功");
                }
                Log.e("helloworldaaaaa", "最终结果---" + s);
            }
        });
    }
}
