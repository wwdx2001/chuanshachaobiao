package com.example.dataprovider3.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class RealNameWholeEntity implements Parcelable {
    @Id
    private long ID;
    private String S_CID;
    private String userType;
    private String contactPerson;
    private String phoneNum;
    private String phone;
    private String email;
    private String remarks;
    private String images1;
    private String images2;
    private boolean isCommit;
    private Integer userTypePosition;

    @Generated(hash = 355816370)
    public RealNameWholeEntity(long ID, String S_CID, String userType, String contactPerson,
            String phoneNum, String phone, String email, String remarks, String images1, String images2,
            boolean isCommit, Integer userTypePosition) {
        this.ID = ID;
        this.S_CID = S_CID;
        this.userType = userType;
        this.contactPerson = contactPerson;
        this.phoneNum = phoneNum;
        this.phone = phone;
        this.email = email;
        this.remarks = remarks;
        this.images1 = images1;
        this.images2 = images2;
        this.isCommit = isCommit;
        this.userTypePosition = userTypePosition;
    }

    @Generated(hash = 1188013123)
    public RealNameWholeEntity() {
    }

    protected RealNameWholeEntity(Parcel in) {
        ID = in.readLong();
        S_CID = in.readString();
        userType = in.readString();
        contactPerson = in.readString();
        phoneNum = in.readString();
        email = in.readString();
        remarks = in.readString();
        images1 = in.readString();
        images2 = in.readString();
        isCommit = in.readByte() != 0;
    }

    public static final Creator<RealNameWholeEntity> CREATOR = new Creator<RealNameWholeEntity>() {
        @Override
        public RealNameWholeEntity createFromParcel(Parcel in) {
            return new RealNameWholeEntity(in);
        }

        @Override
        public RealNameWholeEntity[] newArray(int size) {
            return new RealNameWholeEntity[size];
        }
    };

    public String getS_CID() {
        return this.S_CID;
    }

    public void setS_CID(String S_CID) {
        this.S_CID = S_CID;
    }

    public String getImages1() {
        return this.images1;
    }

    public void setImages1(String images1) {
        this.images1 = images1;
    }

    public String getImages2() {
        return this.images2;
    }

    public void setImages2(String images2) {
        this.images2 = images2;
    }

    public boolean getIsCommit() {
        return this.isCommit;
    }

    public void setIsCommit(boolean isCommit) {
        this.isCommit = isCommit;
    }

    public long getID() {
        return this.S_CID.hashCode();
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(ID);
        parcel.writeString(S_CID);
        parcel.writeString(userType);
        parcel.writeString(contactPerson);
        parcel.writeString(phoneNum);
        parcel.writeString(email);
        parcel.writeString(remarks);
        parcel.writeString(images1);
        parcel.writeString(images2);
        parcel.writeByte((byte) (isCommit ? 1 : 0));
    }

    public Integer getUserTypePosition() {
        return this.userTypePosition;
    }

    public void setUserTypePosition(Integer userTypePosition) {
        this.userTypePosition = userTypePosition;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
