package com.sh3h.meterreading.ui.InspectionInput.lr;

import android.graphics.Bitmap;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

public class LRDuoMeitiPresenter extends ParentPresenter<LRDuoMeitiMvpView>

  {
    private static final String TAG = "LROperatingPresenter";
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;

  @Inject
  public LRDuoMeitiPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper, ConfigHelper mConfigHelper) {
    super(dataManager);
    this.mPreferencesHelper = mPreferencesHelper;
    this.mConfigHelper = mConfigHelper;

  }

      public File getImagePath() {
        //可以直接把这个方法强制转换成date类型
        Date nowTime = new Date(System.currentTimeMillis());
        //设定显示格式
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM");
        //按指定格式转换
        String now = sdFormatter.format(nowTime);
        File folder = new File(mConfigHelper.getImageXunJianPath(), now);
        return folder;
        }


    public File Compressimage(Bitmap bitmap, File file) {
      if ((bitmap == null) || (file == null)) {
        return null;
      }

      try {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        int quality = mConfigHelper.isPhotoQualityNormal() ? 200 : 400;
        while (baos.toByteArray().length / 1024 > quality) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
          baos.reset();//重置baos即清空baos
          bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
          options /= 2;//每次减少一半
        }

        FileOutputStream fos = new FileOutputStream(file);
        baos.writeTo(fos);
        fos.flush();
        fos.close();
        return null;
      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    }
  }
