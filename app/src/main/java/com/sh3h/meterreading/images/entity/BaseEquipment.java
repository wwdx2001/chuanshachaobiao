package com.sh3h.meterreading.images.entity;

import android.widget.ImageView;

/**
 * Created by zhangzhe on 2016/2/19.
 */
public abstract class BaseEquipment {

    /**
     * 存储类型,图片
     */
    public static final int TYPE_IMAGE = 1;
    /**
     * 存储类型,声音
     */
    public static final int TYPE_SOUND = 2;
    /**
     * 编号
     */
    private int _id;
    /**
     * 名称
     */
    private String _name;
    /**
     * 存储路径
     */
    private String _url;
    /**
     * 存储时间
     */
    private long _date;
    /**
     * 标题
     */
    private String _title;
    /**
     * 长度
     */
    private String _size;
    /**
     * 类型
     */
    private int _type;

    private ImageView _imageView;

    /**
     * @return the imageView
     */
    public ImageView getImageView() {
        return _imageView;
    }

    /**
     * @param imageView
     *            the imageView to set
     */
    public void setImageView(ImageView imageView) {
        this._imageView = imageView;
    }

    /**
     * 录音时间
     */
    private long _recordingTime;

    private String _typeInfo;

    public String getTypeInfo() {
        return _typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this._typeInfo = typeInfo;
    }

    BaseEquipment() {

    }

    BaseEquipment(String name, String url, int id, ImageView iamgeView) {
        this._name = name;
        this._url = url;
        this._id = id;
        this._imageView = iamgeView;
    }

    public void setType(int type) {
        this._type = type;
    }

    public int getType() {
        return this._type;
    }

    public void setSize(String size) {
        this._size = size;
    }

    public String getSize() {
        return this._size;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getTitle() {
        return this._title;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getUrl() {
        return this._url;
    }

    public void setUrl(String url) {
        this._url = url;
    }

    public long getDate() {
        return this._date;
    }

    public void setDate(long date) {
        this._date = date;
    }

    public long getRecordingTime() {
        return this._recordingTime;
    }

    public void setRecordingTime(long recordingTime) {
        this._recordingTime = recordingTime;
    }

    @Override
    public String toString() {
        return this._name;
    }
}
