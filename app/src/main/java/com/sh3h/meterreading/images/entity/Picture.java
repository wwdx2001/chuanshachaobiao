package com.sh3h.meterreading.images.entity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.sh3h.dataprovider.DBManager;
import com.sh3h.dataprovider.greendaoEntity.DuoMeiTXX;
import com.sh3h.datautil.data.local.config.ConfigHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhe on 2016/2/19.
 */

public class Picture extends BaseEquipment {
    /**
     * 定义图片检测后缀名
     */
    public static final String[] IMAGE_EXTENSION = {"jpg", "png"};

    public Picture() {
    }

    public Picture(String name, String url, int id, ImageView imageView) {
        super(name, url, id, imageView);
    }

    /**
     * 获取图片列表
     *
     * @return List<Picture
     * @throws Exception 路径不存在时 抛出异常
     */
    @SuppressLint("SdCardPath")
    public static List<Picture> getList(Context context, int chaoBiaoID,
                                        String cid) throws Exception {
        List<Picture> listPicture = new ArrayList<Picture>();
        /*
		 * // String payh = ConfigHelper.getImagePath(); LogUtil.e("path",
		 * ConfigHelper.getImagePath()); File[] file =
		 * FileUtil.getSpecifiedDirectory(ConfigHelper .getImagePath()); for
		 * (int i = 0; i < file.length; i++) { if (file[i].isFile()) { String
		 * Name = file[i].getName();
		 *
		 * Picture picture = new Picture(Name, file[i].getPath());
		 * picture.setDate(file[i].lastModified()); listPicture.add(picture); }
		 * }
		 */
//        IGreenDaoMeterReadingDataProvider dataProvider = MeterReadingDataManager
//                .getGreenDaoDataProvider();
//        List<DuoMeiTXX> list = DBManager.getInstance().getDuoMeiTXXList(chaoBiaoID, cid);
//        for (DuoMeiTXX duoMeiTXX : list) {
//            String wenjianLJ = imageFolder + "/"
//                    + duoMeiTXX.getS_WENJIANLJ();
//            Picture picture = new Picture(duoMeiTXX.getS_WENJIANMC(), wenjianLJ,
//                    0, null);
//            listPicture.add(picture);
//        }
        return listPicture;

    }


}
