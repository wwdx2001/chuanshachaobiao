package com.sh3h.meterreading.ui.usage_change.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.YasuoSuccessEntity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.ui.MyImagePreviewActivity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.lr.ImagePickerAdapter;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.serverprovider.entity.ImageItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 实名制证件照
 */
public class RealNameMediaFragment extends ParentFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.recycler_type1)
    RecyclerView mRecyclerView1;
    Unbinder unbinder;

    private ImagePickerAdapter mAdapter1;
    private List<ImageItem> mImageItems1;

    private List<ImageItem> images;
    private boolean isCommit;

    private RealNameWholeEntity entity;
    private String mS_cid;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_real_name_media;
    }

    @Override
    protected void lazyLoad() {

    }


    private Activity currentActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentActivity = getActivity() == null ? ActivityUtils.getTopActivity() : getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            isCommit = bundle.getBoolean(Const.IS_COMMIT);
            entity = getArguments().getParcelable(Const.BEAN);
            mS_cid = getArguments().getString(Const.S_CID);
        }
    }

    @Override
    protected void initView1(View view) {
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mImageItems1 = new ArrayList<>();

        mAdapter1 = new ImagePickerAdapter(getActivity(), mImageItems1, 9, Const.PHOTO_TYPE1);
        mRecyclerView1.setAdapter(mAdapter1);
        mAdapter1.setOnItemClickListener(this);
    }

    @Override
    protected void initData1() {
        super.initData1();
        if (entity != null) {
            Type imageListType = new TypeToken<ArrayList<ImageItem>>() {
            }.getType();
            List<ImageItem> list1 = GsonUtils.fromJson(entity.getImages1(), imageListType);
            mImageItems1.addAll(list1);
            mAdapter1.setImages(mImageItems1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void yasuoCallBack(YasuoSuccessEntity yasuoSuccessEntity) {
        if (ImageGridActivity.CAMERA_TYPE_PHOTO.equals(yasuoSuccessEntity.getCameraType())) {
            images = yasuoSuccessEntity.getImages();
            takeSuccess(images);
        } else if ("SHIMINGZHI".equals(yasuoSuccessEntity.getCameraType())) {
            images = yasuoSuccessEntity.getImages();
            //实名制页面OCR识别图片
            mImageItems1.removeAll(images);
            mImageItems1.addAll(images);
            mAdapter1.setImages(mImageItems1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                previewPhoto(requestCode, images);
            }
        }
    }

    private void previewPhoto(int requestCode, List<ImageItem> images) {
        if (images != null) {
            switch (requestCode) {
                case Const.PHOTO_TYPE1:
                    addCamera(mImageItems1, mAdapter1, images);
                    break;
                default:
                    break;
            }
        }
    }

    private void addCamera(List<ImageItem> selImageList, ImagePickerAdapter
            adapter, List<ImageItem> images) {
        selImageList.clear();
        selImageList.addAll(images);
        adapter.setImages(selImageList);
    }


    private int requestCode;

    private void takeSuccess(List<ImageItem> images) {
        if (images != null) {
            switch (requestCode) {
                case Const.PHOTO_TYPE1:
                    mImageItems1.addAll(images);
                    mAdapter1.setImages(mImageItems1);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().removeAllStickyEvents();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onItemClick(View view, int position, int type) {
        switch (position) {
            //拍摄照片
            case Const.IMAGE_ITEM_ADD:
                if (!isCommit) {
                    takePhoto(type);
                }
                break;
            default: //打开预览
                Intent preViewIntent;
                if (isCommit) {
                    preViewIntent = new Intent(getActivity(), MyImagePreviewActivity.class);
                } else {
                    preViewIntent = new Intent(getActivity(), ImagePreviewDelActivity.class);
                }
                switch (type) {
                    case Const.PHOTO_TYPE1:
                        preViewIntent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (Serializable) mImageItems1);
                        break;
                    default:
                        break;
                }
                preViewIntent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                preViewIntent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(preViewIntent, type);
                break;
        }
    }

    @SuppressLint("WrongConstant")
    private void takePhoto(int type) {
        PermissionUtils.permission(Manifest.permission.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        String strType = "";
                        switch (type) {
                            case Const.PHOTO_TYPE1:
                                ImagePicker.getInstance().setSelectLimit(9 - mAdapter1.getImages().size());
                                strType = tv1.getText().toString() + "_";
                                break;
                        }
                        requestCode = type;
                        Intent cameraIntent = new Intent(getActivity(), ImageGridActivity.class);
//                        cameraIntent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
                        cameraIntent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, false);
                        cameraIntent.putExtra(ImageGridActivity.PHOTO_TYPE, strType);
                        cameraIntent.putExtra(ImageGridActivity.NB_PHOTO, true);
                        cameraIntent.putExtra(ImageGridActivity.CAMERA_TYPE, ImageGridActivity.CAMERA_TYPE_PHOTO);
                        cameraIntent.putExtra(ImageGridActivity.REWUID, mS_cid);
                        startActivityForResult(cameraIntent, type);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showLong("拍照权限被拒，无法进行拍照");
                    }
                }).request();
    }

    public RealNameWholeEntity getImagesInfo(RealNameWholeEntity wholeEntity) {
        String images1 = GsonUtils.getGson().toJson(mAdapter1.getImages());
        wholeEntity.setImages1(images1);
        return wholeEntity;
    }


}