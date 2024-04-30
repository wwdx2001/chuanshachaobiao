package com.sh3h.serverprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 语音信息
 */
public class VoiceItem implements Serializable, Parcelable {

    public String name;       //语音的名字
    public String path;       //语音的路径
    public long size;         //语音的大小
    public String mimeType;   //语音的类型
    public long addTime;      //语音的创建时间

    /** 图片的路径和创建时间相同就认为是同一张图片 */
    @Override
    public boolean equals(Object o) {
        if (o instanceof VoiceItem) {
            VoiceItem item = (VoiceItem) o;
            return this.path.equalsIgnoreCase(item.path) && this.addTime == item.addTime;
        }

        return super.equals(o);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeString(this.mimeType);
        dest.writeLong(this.addTime);
    }

    public VoiceItem() {
    }

    protected VoiceItem(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.mimeType = in.readString();
        this.addTime = in.readLong();
    }

    public static final Creator<VoiceItem> CREATOR = new Creator<VoiceItem>() {
        @Override
        public VoiceItem createFromParcel(Parcel source) {
            return new VoiceItem(source);
        }

        @Override
        public VoiceItem[] newArray(int size) {
            return new VoiceItem[size];
        }
    };
}
