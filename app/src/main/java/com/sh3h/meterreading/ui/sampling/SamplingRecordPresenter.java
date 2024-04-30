package com.sh3h.meterreading.ui.sampling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUCiYuXXInfo;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUMediaInfo;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingInfo;
import com.sh3h.datautil.data.entity.DUSamplingResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.meterreading.util.SystemEquipmentUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2016/11/24.
 */
public class SamplingRecordPresenter extends ParentPresenter<SamplingRecordMvpView> {
    private static final String TAG = "RecordLRPresenter";

    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;

    @Inject
    public SamplingRecordPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper,
                                   ConfigHelper mConfigHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
        this.mConfigHelper = mConfigHelper;
    }

    public void loadChaoBiaoZTList() {
        LogUtil.i(TAG, "---loadChaoBiaoZTList---");
        mSubscription.add(mDataManager.getChaoBiaoZTList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUChaoBiaoZT>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadChaoBiaoZTList onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadChaoBiaoZTList onError : " + e.getMessage() + "---");
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUChaoBiaoZT> duChaoBiaoZTs) {
                        LogUtil.i(TAG, "---loadChaoBiaoZTList onNext---");
                        getMvpView().onLoadChaoBiaoZTList(duChaoBiaoZTs);
                    }
                }));
    }

    public void loadLiangGaoYYList() {
        LogUtil.i(TAG, "---loadLiangGaoYYList---");
        mSubscription.add(mDataManager.getCiYuXXList(new DUCiYuXXInfo(DUCiYuXXInfo.FilterType.LIANG_GAO_YY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCiYuXX>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadLiangGaoYYList onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadLiangGaoYYList onError: " + e.getMessage() + "---");
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCiYuXX> duCiYuXXes) {
                        LogUtil.i(TAG, "---loadLiangGaoYYList onNext---");
                        getMvpView().onLoadLiangGaoYY(duCiYuXXes);
                    }
                }));
    }

    public void loadLiangDiYYList() {
        LogUtil.i(TAG, "---loadLiangDiYYList---");
        mSubscription.add(mDataManager.getCiYuXXList(new DUCiYuXXInfo(DUCiYuXXInfo.FilterType.LIANG_DI_YY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCiYuXX>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadLiangDiYYList onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadLiangDiYYList onError" + e.getMessage() + "---");
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCiYuXX> duCiYuXXes) {
                        LogUtil.i(TAG, "---loadLiangDiYYList onNext---");
                        getMvpView().onLoadLiangDiYY(duCiYuXXes);
                    }
                }));
    }

//    public void loadBeiZhuYYList() {
//        LogUtil.i(TAG, "---loadLiangDiYYList---");
//        mSubscription.add(mDataManager.getCiYuXXList(new DUCiYuXXInfo(DUCiYuXXInfo.FilterType.BEIZHU_YY))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<List<DUCiYuXX>>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.i(TAG, "---loadLiangDiYYList onCompleted---");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.i(TAG, "---loadLiangDiYYList onError" + e.getMessage() + "---");
//                        getMvpView().onError(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(List<DUCiYuXX> duCiYuXXes) {
//                        LogUtil.i(TAG, "---loadLiangDiYYList onNext---");
//                        getMvpView().onLoadBeiZhuYY(duCiYuXXes);
//                    }
//                }));
//    }

    /**
     * @param filterType
     * @param taskId
     * @param volume
     * @param customerId
     * @param orderNumber
     */
    public void loadRecordInfo(final DUSamplingInfo.FilterType filterType,
                               int taskId,
                               String volume,
                               String customerId,
                               int orderNumber) {
        LogUtil.i(TAG, "---loadRecordInfo---");
        if ((filterType == DUSamplingInfo.FilterType.NONE)
                || (taskId <= 0)
                || TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            getMvpView().onError("parameter is error");
            LogUtil.i(TAG, "---loadRecordInfo---parameter is error");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DUSamplingInfo duSamplingInfo = new DUSamplingInfo(filterType, userSession.getAccount(),
                taskId, volume, customerId, orderNumber);
        mSubscription.add(mDataManager.getSampling(duSamplingInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUSampling>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadRecordInfo onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadRecordInfo onError" + e.getMessage() + "---");
                        getMvpView().onError(e.getMessage());
                        switch (filterType) {
                            case PREVIOUS_ONE:
                            case PREVIOUS_ONE_NOT_READING:
                                getMvpView().onSwitchRecordError(false);
                                break;
                            case NEXT_ONE:
                            case NEXT_ONE_NOT_READING:
                                getMvpView().onSwitchRecordError(true);
                                break;
                        }
                    }

                    @Override
                    public void onNext(DUSampling duSampling) {
                        LogUtil.i(TAG, "---loadRecordInfo onNext---");
                        getMvpView().onLoadRecordInfo(duSampling);
                    }
                }));
    }

    /**
     * @param volume
     * @param customerId
     */
    public void loadCardInfo(String volume, String customerId) {
        LogUtil.i(TAG, "---loadCardInfo---");
        if (TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            getMvpView().onError("parameter is error");
            LogUtil.i(TAG, "---loadCardInfo---parameter is error");
            return;
        }

        DUCardInfo duCardInfo = new DUCardInfo(DUCardInfo.FilterType.SEARCHING_ONE,
                volume, customerId);
        mSubscription.add(mDataManager.getCard(duCardInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUCard>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadCardInfo onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadCardInfo onError" + e.getMessage() + "---");
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUCard duCard) {
                        LogUtil.i(TAG, "---loadCardInfo onNext---");
                        getMvpView().onLoadCardInfo(duCard);
                    }
                }));
    }

    /**
     * @param taskId
     * @param volume
     * @param customerId
     */
    public void loadImageInfo(int taskId, final String volume, String customerId) {
        LogUtil.i(TAG, "---loadImageInfo---");
        if ((taskId <= 0)
                || TextUtil.isNullOrEmpty(volume)
                || TextUtil.isNullOrEmpty(customerId)) {
            getMvpView().onError("parameter is error");
            LogUtil.i(TAG, "---loadImageInfo---parameter is error");
            return;
        }

        UserSession userSession = mPreferencesHelper.getUserSession();
        DUMedia duMedia = new DUMedia(
                userSession.getAccount(),
                taskId,
                volume,
                customerId);

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.SELECT,
                DUMediaInfo.MeterReadingType.SAMPLING,
                duMedia);
        mSubscription.add(mDataManager.getMediaList(duMediaInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUMedia>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getMediaList onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---getMediaList onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUMedia> duMediaList) {
                        LogUtil.i(TAG, "---getMediaList onNext---");

                        List<String> imgPathList = new ArrayList<>();
                        List<DUMedia> duoMeiTXXList = new ArrayList<>();
                        if (duMediaList != null && duMediaList.size() > 0) {
                            File dir = new File(mConfigHelper.getImageFolderPath(), volume);
                            if (!dir.exists()) {
                                dir.mkdir();
                            }

                            for (DUMedia duoMeiTXX : duMediaList) {
                                if ((duoMeiTXX == null) || (duoMeiTXX.getWenjianmc() == null)) {
                                    continue;
                                }

                                File file = new File(dir, duoMeiTXX.getWenjianmc());
                                if (!file.exists()) {
                                    continue;
                                }

                                imgPathList.add(file.getPath());//将图片路径保存在列表中
                                duoMeiTXXList.add(duoMeiTXX);
                            }
                        }

                        getMvpView().onLoadImgPathList(imgPathList);
                        getMvpView().onLoadDuoMeiTXXList(duoMeiTXXList);
                    }
                }));
    }

    /**
     * @param duSampling
     */
    public void updateRecord(DUSampling duSampling, boolean isUpdatingTask) {
        LogUtil.i(TAG, "---saveRecord---");
        if (duSampling == null) {
            getMvpView().onError("---saveRecord: duRecord is null");
            LogUtil.i(TAG, "---saveRecord---error");
            return;
        }

        mSubscription.add(mDataManager.updateSampling(duSampling, isUpdatingTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUSamplingResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveRecord onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveRecord onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUSamplingResult duSamplingResult) {
                        LogUtil.i(TAG, "---saveRecord onNext---");
                        getMvpView().onUpdateRecord(duSamplingResult);
                    }
                }));
    }

    /**
     * @param duoMeiTXX
     */
    public void saveNewImage(final DUMedia duoMeiTXX) {
        LogUtil.i(TAG, "---saveNewImage---");
        if (duoMeiTXX == null) {
            getMvpView().onError("---saveNewImage: duoMeiTXX is null---");
            return;
        }

        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.INSERT,
                DUMediaInfo.MeterReadingType.NORMAL,
                duoMeiTXX
        );
        mSubscription.add(mDataManager.saveNewImage(duMediaInfo)
                .concatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        return compressImageAndAddStamp(duoMeiTXX);
                    }
                })
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
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveNewImage onNext---");
                        if (aBoolean) {
                            getMvpView().onSaveNewImage(duoMeiTXX);
                        } else {
                            LogUtil.i(TAG, "---saveNewImage onNext---false");
                            getMvpView().onError("saveNewImage is error");
                        }
                    }
                }));
    }

    /**
     * @param duoMeiTXX
     * @return
     */
    private Observable<Boolean> compressImageAndAddStamp(final DUMedia duoMeiTXX) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if ((duoMeiTXX == null)
                        || TextUtil.isNullOrEmpty(duoMeiTXX.getWenjianlj())
                        || TextUtil.isNullOrEmpty(duoMeiTXX.getWenjianmc())) {
                    throw new NullPointerException("duMediaInfo contains null parameter");
                }

                try {
                    File file = new File(duoMeiTXX.getWenjianlj());
                    if (!file.exists()) {
                        subscriber.onError(new Throwable("file isn't existing"));
                        return;
                    }

                    //缩放的比例，缩放是很难按准备的比例进行缩放的，其值表明缩放的倍数，SDK中建议其值是2的指数
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getPath(), options);
                    options.inSampleSize = SystemEquipmentUtil.calculateInSampleSize(options, 210, 300);
                    options.inJustDecodeBounds = false;
                    Bitmap fBitmap = BitmapFactory.decodeFile(file.getPath(), options);
                    if (fBitmap == null) {
                        subscriber.onError(new Throwable("failure to decode the picture"));
                        return;
                    }

                    // rotate the bitmap
                    int degree = getBitmapDegree(file.getPath());
                    if (degree != 0) {
                        fBitmap = rotateBitmapByDegree(fBitmap, degree);
                    }

                    // add the stamp
                    fBitmap = addWatermark(fBitmap);

                    // save image
                    if (!saveImage(fBitmap, file.getPath())) {
                        subscriber.onError(new Throwable("failure to save the picture"));
                        return;
                    }

                    subscriber.onNext(true);
                } catch (Exception e) {
                    LogUtil.e("FileNotFoundException", e.getMessage(), e);
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * @param imgPathList
     * @param context
     */
    public void loadImageViews(final List<String> imgPathList, final Context context) {
        LogUtil.i(TAG, "---loadImageViews---1");
        Observable.create(new Observable.OnSubscribe<List<ImageView>>() {
            @Override
            public void call(Subscriber<? super List<ImageView>> subscriber) {
                LogUtil.i(TAG, "---loadImageViews---2");
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if ((imgPathList == null) || (context == null)) {
                    LogUtil.i(TAG, "---loadImageViews---parameter is null");
                    throw new NullPointerException("parameter is null");
                }

                try {
                    List<ImageView> imageViewList = new ArrayList<>();
                    for (String path : imgPathList) {
                        Bitmap bitmap = SystemEquipmentUtil.decodeBitmap(path);
                        ImageView imageView = new ImageView(context);
                        imageView.setImageBitmap(bitmap);
                        imageViewList.add(imageView);
                    }

                    subscriber.onNext(imageViewList);
                    LogUtil.i(TAG, "---loadImageViews---3");
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                    LogUtil.i(TAG, "---loadImageViews---" + e.getMessage());
                } finally {
                    subscriber.onCompleted();
                }
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ImageView>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadImageViews onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadImageViews onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ImageView> imageViews) {
                        LogUtil.i(TAG, "---loadImageViews onNext---");
                        getMvpView().onLoadImageViews(imageViews);
                    }
                });
    }

    /**
     * @param index
     * @param name
     * @param volume
     */
    public void deleteImage(final int index, final String name, final String volume) {
        LogUtil.i(TAG, "---loadImageViews---1");
        if (TextUtil.isNullOrEmpty(name) || TextUtil.isNullOrEmpty(volume)) {
            getMvpView().onError("parameter is null");
            return;
        } else {
            DUMedia duMedia = new DUMedia();
            duMedia.setWenjianmc(name);

            DUMediaInfo duMediaInfo = new DUMediaInfo(
                    DUMediaInfo.OperationType.DELETE,
                    DUMediaInfo.MeterReadingType.NORMAL,
                    duMedia);
            mSubscription.add(mDataManager.deleteImage(duMediaInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            LogUtil.i(TAG, "---deleteImage onCompleted---");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.i(TAG, "---deleteImage onError---" + e.getMessage());
                            getMvpView().onError(e.getMessage());
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            LogUtil.i(TAG, "---deleteImage onNext---");
                            if (aBoolean) {
                                File dir = new File(mConfigHelper.getImageFolderPath(), volume);
                                File file = new File(dir, name);
                                if (file.exists()) {
                                    file.delete();
                                }

                                getMvpView().onDeleteImage(index);
                            } else {
                                getMvpView().onError("deleteImage is error");
                            }
                        }
                    }));
        }
    }

    public void updateCard(DUCard duCard) {
        LogUtil.i(TAG, "---updateCard---1");
        if (duCard == null) {
            getMvpView().onError("parameter is null");
            return;
        }

        mSubscription.add(mDataManager.updateOneCard(duCard)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---updateCard onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---updateCard onError: %s---",
                                e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---updateCard onNext---");
                        getMvpView().onUpdateCard(aBoolean);
                    }
                }));
    }

    private long calculateFileSize(String path) {
        long fileSize = 0;
        File file = new File(path);
        if (!file.exists()) {
            return fileSize;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fileSize = fis.available();
        } catch (Exception e) {
            LogUtil.i(TAG, e.getMessage());
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LogUtil.i(TAG, e.getMessage());
                }
            }
        }
        return fileSize;
    }

    /**
     * save the image
     *
     * @param bitmap
     */
    private boolean saveImage(Bitmap bitmap, String filePath) {
        if ((bitmap == null) || (filePath == null)) {
            return false;
        }

        try {
            File file = new File(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            int quality;
            if (mConfigHelper.isPhotoQualityNormal()) {
                quality = 100;
            } else {
                quality = 50;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 读取图片旋转的角度
     *
     * @param path
     * @return
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */

    private Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }

        if (returnBm == null) {
            returnBm = bm;
        }

        if (bm != returnBm) {
            bm.recycle();
        }

        return returnBm;
    }

    /**
     * 添加水印
     *
     * @param bitmap
     * @return
     */

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

    public void isCanInput(final long time, final String account) {
        LogUtil.i(TAG, "---isCanInput---");
        mSubscription.add(mDataManager.isTimeSync(time, account,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---onError---" + e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean res) {
                        Date date = new Date();
                        if (res && date.getTime() > time) {
                            getMvpView().isCanInput(false);
                        } else {
                            getMvpView().isCanInput(true);
                        }
                    }
                }));

    }

}
