package com.lzy.imagepicker;

import com.sh3h.serverprovider.entity.ImageItem;

import java.util.ArrayList;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/1/23 18:04
 */
public class YasuoSuccessEntity {
    private ArrayList<ImageItem> images;
    private String cameraType;

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public YasuoSuccessEntity(ArrayList<ImageItem> images, String cameraType) {
        this.images = images;
        this.cameraType = cameraType;
    }

    public ArrayList<ImageItem> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageItem> images) {
        this.images = images;
    }
}
