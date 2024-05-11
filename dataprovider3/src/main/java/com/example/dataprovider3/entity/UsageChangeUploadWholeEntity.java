package com.example.dataprovider3.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class UsageChangeUploadWholeEntity implements Parcelable {
    @Id
    private long ID;
    private String S_CID;
    private String jh;
    private String ssdm;
    private String remarks;
    private String images1;
    private boolean isCommit;

    protected UsageChangeUploadWholeEntity(Parcel in) {
        ID = in.readLong();
        S_CID = in.readString();
        jh = in.readString();
        ssdm = in.readString();
        remarks = in.readString();
        images1 = in.readString();
        isCommit = in.readByte() != 0;
    }

    @Generated(hash = 789756243)
    public UsageChangeUploadWholeEntity(long ID, String S_CID, String jh, String ssdm, String remarks, String images1,
            boolean isCommit) {
        this.ID = ID;
        this.S_CID = S_CID;
        this.jh = jh;
        this.ssdm = ssdm;
        this.remarks = remarks;
        this.images1 = images1;
        this.isCommit = isCommit;
    }

    @Generated(hash = 265240695)
    public UsageChangeUploadWholeEntity() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(ID);
        dest.writeString(S_CID);
        dest.writeString(jh);
        dest.writeString(ssdm);
        dest.writeString(remarks);
        dest.writeString(images1);
        dest.writeByte((byte) (isCommit ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getS_CID() {
        return this.S_CID;
    }

    public void setS_CID(String S_CID) {
        this.S_CID = S_CID;
    }

    public String getJh() {
        return this.jh;
    }

    public void setJh(String jh) {
        this.jh = jh;
    }

    public String getSsdm() {
        return this.ssdm;
    }

    public void setSsdm(String ssdm) {
        this.ssdm = ssdm;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImages1() {
        return this.images1;
    }

    public void setImages1(String images1) {
        this.images1 = images1;
    }

    public boolean getIsCommit() {
        return this.isCommit;
    }

    public void setIsCommit(boolean isCommit) {
        this.isCommit = isCommit;
    }

    public static final Creator<UsageChangeUploadWholeEntity> CREATOR = new Creator<UsageChangeUploadWholeEntity>() {
        @Override
        public UsageChangeUploadWholeEntity createFromParcel(Parcel in) {
            return new UsageChangeUploadWholeEntity(in);
        }

        @Override
        public UsageChangeUploadWholeEntity[] newArray(int size) {
            return new UsageChangeUploadWholeEntity[size];
        }
    };
}
