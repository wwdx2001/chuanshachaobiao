package com.sh3h.serverprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/11 17:11
 */
public class BiaoKaListBean implements Parcelable {

    /**
     * S_RENWUID : 1
     * S_RENWUMC : 测试1
     * S_CID : 00001
     * S_HUMING : 张三1
     * S_DIZHI : 上海市1
     * BIAOKAZT : 正常
     * XUNJIANZT : 已巡检
     */
    private Long ID;
    private String S_RENWUID;
    private String S_RENWUMC;
    private String S_CID;
    private String S_HUMING;
    private String S_DIZHI;
    private String BIAOKAZT;
    private String XUNJIANZT;
    private String XJLX;
    private double lat;
    private double lon;

    private boolean isCommit;
    private boolean isSave;
    private boolean isUploadImage;

    private String ISYC;
    private String GDDS_COUNT;
    private String TYPE;
    private String XUHAO;

  public String getXUHAO() {
    return XUHAO;
  }

  public void setXUHAO(String XUHAO) {
    this.XUHAO = XUHAO;
  }

  public Long getID() {
        return Long.valueOf(this.S_CID.hashCode());
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getS_RENWUID() {
        return S_RENWUID;
    }

    public void setS_RENWUID(String s_RENWUID) {
        S_RENWUID = s_RENWUID;
    }

    public String getS_RENWUMC() {
        return S_RENWUMC;
    }

    public void setS_RENWUMC(String s_RENWUMC) {
        S_RENWUMC = s_RENWUMC;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public String getS_HUMING() {
        return S_HUMING;
    }

    public void setS_HUMING(String s_HUMING) {
        S_HUMING = s_HUMING;
    }

    public String getS_DIZHI() {
        return S_DIZHI;
    }

    public void setS_DIZHI(String s_DIZHI) {
        S_DIZHI = s_DIZHI;
    }

    public String getBIAOKAZT() {
        return BIAOKAZT;
    }

    public void setBIAOKAZT(String BIAOKAZT) {
        this.BIAOKAZT = BIAOKAZT;
    }

    public String getXUNJIANZT() {
        return XUNJIANZT;
    }

    public void setXUNJIANZT(String XUNJIANZT) {
        this.XUNJIANZT = XUNJIANZT;
    }

    public boolean isCommit() {
        return isCommit;
    }

    public void setCommit(boolean commit) {
        isCommit = commit;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public boolean isUploadImage() {
        return isUploadImage;
    }

    public void setUploadImage(boolean uploadImage) {
        isUploadImage = uploadImage;
    }

    public boolean getIsCommit() {
        return isCommit;
    }

    public void setIsCommit(boolean commit) {
        isCommit = commit;
    }

    public boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean save) {
        isSave = save;
    }

    public boolean getIsUploadImage() {
        return isUploadImage;
    }

    public void setIsUploadImage(boolean uploadImage) {
        isUploadImage = uploadImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.ID);
        dest.writeString(this.S_RENWUID);
        dest.writeString(this.S_RENWUMC);
        dest.writeString(this.S_CID);
        dest.writeString(this.S_HUMING);
        dest.writeString(this.S_DIZHI);
        dest.writeString(this.BIAOKAZT);
        dest.writeString(this.XUNJIANZT);
        dest.writeString(this.XJLX);
        dest.writeByte(this.isCommit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSave ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isUploadImage ? (byte) 1 : (byte) 0);
    }

    public BiaoKaListBean() {
    }

    protected BiaoKaListBean(Parcel in) {
        this.ID = (Long) in.readValue(Long.class.getClassLoader());
        this.S_RENWUID = in.readString();
        this.S_RENWUMC = in.readString();
        this.S_CID = in.readString();
        this.S_HUMING = in.readString();
        this.S_DIZHI = in.readString();
        this.BIAOKAZT = in.readString();
        this.XUNJIANZT = in.readString();
        this.XJLX = in.readString();
        this.isCommit = in.readByte() != 0;
        this.isSave = in.readByte() != 0;
        this.isUploadImage = in.readByte() != 0;
    }

    public BiaoKaListBean(Long ID, String S_RENWUID, String S_RENWUMC, String S_CID,
                          String S_HUMING, String S_DIZHI, String BIAOKAZT, String XUNJIANZT, String XJLX,
                          double lat, double lon, boolean isCommit, boolean isSave, boolean isUploadImage,
                          String ISYC, String GDDS_COUNT, String TYPE,String XUHAO) {
        this.ID = ID;
        this.S_RENWUID = S_RENWUID;
        this.S_RENWUMC = S_RENWUMC;
        this.S_CID = S_CID;
        this.S_HUMING = S_HUMING;
        this.S_DIZHI = S_DIZHI;
        this.BIAOKAZT = BIAOKAZT;
        this.XUNJIANZT = XUNJIANZT;
        this.XJLX = XJLX;
        this.lat = lat;
        this.lon = lon;
        this.isCommit = isCommit;
        this.isSave = isSave;
        this.isUploadImage = isUploadImage;
        this.ISYC = ISYC;
        this.GDDS_COUNT = GDDS_COUNT;
        this.TYPE = TYPE;
        this.XUHAO=XUHAO;
    }

    public static final Creator<BiaoKaListBean> CREATOR = new Creator<BiaoKaListBean>() {
        @Override
        public BiaoKaListBean createFromParcel(Parcel source) {
            return new BiaoKaListBean(source);
        }

        @Override
        public BiaoKaListBean[] newArray(int size) {
            return new BiaoKaListBean[size];
        }
    };

    @Override
    public String toString() {
        return "BiaoKaListBean{" +
                "ID=" + ID +
                ", S_RENWUID='" + S_RENWUID + '\'' +
                ", S_RENWUMC='" + S_RENWUMC + '\'' +
                ", S_CID='" + S_CID + '\'' +
                ", S_HUMING='" + S_HUMING + '\'' +
                ", S_DIZHI='" + S_DIZHI + '\'' +
                ", BIAOKAZT='" + BIAOKAZT + '\'' +
                ", XUNJIANZT='" + XUNJIANZT + '\'' +
                ", isCommit=" + isCommit +
                ", isSave=" + isSave +
                ", isUploadImage=" + isUploadImage +
                '}';
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getXJLX() {
        return this.XJLX;
    }

    public void setXJLX(String XJLX) {
        this.XJLX = XJLX;
    }

    public String getISYC() {
        return this.ISYC;
    }

    public void setISYC(String ISYC) {
        this.ISYC = ISYC;
    }

    public String getGDDS_COUNT() {
        return this.GDDS_COUNT;
    }

    public void setGDDS_COUNT(String GDDS_COUNT) {
        this.GDDS_COUNT = GDDS_COUNT;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
