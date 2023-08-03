package com.sh3h.meterreading.util;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.sh3h.meterreading.images.entity.BaseEquipment;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.meterreading.images.view.PictureGalleryDialog;

import java.io.IOException;
import java.util.List;

public class SystemEquipmentUtil {

    public static final int TEXT_HEIGHT = 260;
    public static final int TEXT_WIDTH = 260;

    private static MediaPlayer _mplayer = null;

    public static void palySound(Context context, String fileName) {

        if (_mplayer == null || !_mplayer.isPlaying())
            return;

        AssetManager am = context.getAssets();
        try {
            String path = String.format("ring/%s.mp3", fileName);
            AssetFileDescriptor afd = am.openFd(path);
            _mplayer = new MediaPlayer();
            _mplayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            _mplayer.prepare();
            _mplayer.start();
        } catch (IOException e) {
            LogUtil.i("SEU", "not found file");
        }

    }

    private static long _lastVibrateTime = 0;

    public static void palyVibrator(Context context) {
        if (_lastVibrateTime != 0) {

            long tmp = System.currentTimeMillis();
            long tmp1 = tmp - _lastVibrateTime;
            _lastVibrateTime = tmp;

            if (tmp1 < 2000) {
                return;
            }
        }

        Vibrator vibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);// 振动2秒
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

    /**
     * 生成缩略图
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Bitmap decodeBitmap(String path) throws Exception {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        Bitmap bmp;
        op.inJustDecodeBounds = false;
        op.inSampleSize = 4;
        bmp = BitmapFactory.decodeFile(path, op);
        // bmp=BitmapFactory.decodeFile(pathName, opts)
        if (bmp == null) {
            throw new Exception();
        }
        int width = bmp.getWidth();
        int height = bmp.getHeight();
//        float fwidth = (float) AttachmentView.TEXT_WIDTH / width;
//        float fheight = (float) AttachmentView.TEXT_HEIGHT / height;
        float fwidth = (float) TEXT_WIDTH / width;
        float fheight = (float) TEXT_HEIGHT / height;
        LogUtil.i("imtgsize", String.valueOf(width));
        Matrix matrix = new Matrix();
        matrix.postScale(fwidth, fheight);
        Bitmap fBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,
                true);
        // prototype
        return fBitmap;
    }

    /**
     *
     *
     */
    public static void openImage(Context context, BaseEquipment picture,
                                 List<BaseEquipment> pictureList, int index) {
        // PictureDialog pictureDialog = new PictureDialog(context, picture);
        // pictureDialog.setPictureList(pictureList);
        // pictureDialog.show();

        LogUtil.i("pictureList.sizepictureList.size", String.valueOf(pictureList.size()));
        PictureGalleryDialog pictureGalleryDialog = new PictureGalleryDialog(context, picture,
                pictureList, index);
        pictureGalleryDialog.show();
    }
}
