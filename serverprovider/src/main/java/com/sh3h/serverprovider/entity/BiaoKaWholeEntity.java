package com.sh3h.serverprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 巡检表卡回填
 *
 * @author Administrator
 */
public class BiaoKaWholeEntity implements Parcelable {


    private Long ID;
    private String detailInfo;

    private String RENWUID;
    private String RENWUMC;
    private String XIAOGENH;
    private String XUNJIANCM;
    private String TXM;
    private String XUNJIANYL;
    private String YLSM;
    private String BEIZHU;
    private String images1;
    private String images2;
    private String images3;
    private String images4;
    private String images5;
    private String images6;
    private String images7;
    private String vedioUrl;
    private boolean isCommit;
    private boolean isSave;
    private boolean isUploadImage;
    private String cbzt;

    private String GONGDANDJ;
    private String GONGDANDJBZ;
    private String BIAOWU_GZYY;
    private String MOKUAI_GZYY;
    private String SFGHBP;

    private String GZLB;


    // 定位时间
    private String gpsTime;
    // 纬度
    private String lat;
    // 经度
    private String lon;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getRENWUID() {
        return RENWUID;
    }

    public void setRENWUID(String RENWUID) {
        this.RENWUID = RENWUID;
    }

    public String getRENWUMC() {
        return RENWUMC;
    }

    public void setRENWUMC(String RENWUMC) {
        this.RENWUMC = RENWUMC;
    }

    public String getXIAOGENH() {
        return XIAOGENH;
    }

    public void setXIAOGENH(String XIAOGENH) {
        this.XIAOGENH = XIAOGENH;
    }

    public String getXUNJIANCM() {
        return XUNJIANCM;
    }

    public void setXUNJIANCM(String XUNJIANCM) {
        this.XUNJIANCM = XUNJIANCM;
    }

    public String getTXM() {
        return TXM;
    }

    public void setTXM(String TXM) {
        this.TXM = TXM;
    }

    public String getXUNJIANYL() {
        return XUNJIANYL;
    }

    public void setXUNJIANYL(String XUNJIANYL) {
        this.XUNJIANYL = XUNJIANYL;
    }

    public String getYLSM() {
        return YLSM;
    }

    public void setYLSM(String YLSM) {
        this.YLSM = YLSM;
    }

    public String getBEIZHU() {
        return BEIZHU;
    }

    public void setBEIZHU(String BEIZHU) {
        this.BEIZHU = BEIZHU;
    }

    public String getImages1() {
        return images1;
    }

    public void setImages1(String images1) {
        this.images1 = images1;
    }

    public String getImages2() {
        return images2;
    }

    public void setImages2(String images2) {
        this.images2 = images2;
    }

    public String getImages3() {
        return images3;
    }

    public void setImages3(String images3) {
        this.images3 = images3;
    }

    public String getImages4() {
        return images4;
    }

    public void setImages4(String images4) {
        this.images4 = images4;
    }

    public String getImages5() {
        return images5;
    }

    public void setImages5(String images5) {
        this.images5 = images5;
    }

    public String getImages6() {
        return images6;
    }

    public void setImages6(String images6) {
        this.images6 = images6;
    }

    public String getImages7() {
        return images7;
    }

    public void setImages7(String images7) {
        this.images7 = images7;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
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

    public BiaoKaWholeEntity() {
    }

    public BiaoKaWholeEntity(Long ID, String detailInfo, String RENWUID, String RENWUMC, String XIAOGENH, String XUNJIANCM,
                             String TXM, String XUNJIANYL, String YLSM, String BEIZHU, String images1, String images2, String images3,
                             String images4, String images5, String images6, String images7, String vedioUrl, boolean isCommit,
                             boolean isSave, boolean isUploadImage, String cbzt, String GONGDANDJ, String GONGDANDJBZ, String BIAOWU_GZYY,
                             String MOKUAI_GZYY, String SFGHBP, String GZLB, String gpsTime, String lat, String lon) {
        this.ID = ID;
        this.detailInfo = detailInfo;
        this.RENWUID = RENWUID;
        this.RENWUMC = RENWUMC;
        this.XIAOGENH = XIAOGENH;
        this.XUNJIANCM = XUNJIANCM;
        this.TXM = TXM;
        this.XUNJIANYL = XUNJIANYL;
        this.YLSM = YLSM;
        this.BEIZHU = BEIZHU;
        this.images1 = images1;
        this.images2 = images2;
        this.images3 = images3;
        this.images4 = images4;
        this.images5 = images5;
        this.images6 = images6;
        this.images7 = images7;
        this.vedioUrl = vedioUrl;
        this.isCommit = isCommit;
        this.isSave = isSave;
        this.isUploadImage = isUploadImage;
        this.cbzt = cbzt;
        this.GONGDANDJ = GONGDANDJ;
        this.GONGDANDJBZ = GONGDANDJBZ;
        this.BIAOWU_GZYY = BIAOWU_GZYY;
        this.MOKUAI_GZYY = MOKUAI_GZYY;
        this.SFGHBP = SFGHBP;
        this.GZLB = GZLB;
        this.gpsTime = gpsTime;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "BiaoKaWholeEntity{" +
                "ID=" + ID +
                ", detailInfo='" + detailInfo + '\'' +
                ", RENWUID='" + RENWUID + '\'' +
                ", RENWUMC='" + RENWUMC + '\'' +
                ", XIAOGENH='" + XIAOGENH + '\'' +
                ", XUNJIANCM='" + XUNJIANCM + '\'' +
                ", TXM='" + TXM + '\'' +
                ", XUNJIANYL='" + XUNJIANYL + '\'' +
                ", YLSM='" + YLSM + '\'' +
                ", BEIZHU='" + BEIZHU + '\'' +
                ", images1='" + images1 + '\'' +
                ", images2='" + images2 + '\'' +
                ", images3='" + images3 + '\'' +
                ", images4='" + images4 + '\'' +
                ", images5='" + images5 + '\'' +
                ", images6='" + images6 + '\'' +
                ", images7='" + images7 + '\'' +
                ", vedioUrl='" + vedioUrl + '\'' +
                ", isCommit=" + isCommit +
                ", isSave=" + isSave +
                ", isUploadImage=" + isUploadImage +
                ", gpsTime='" + gpsTime + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

    public String getGpsTime() {
        return this.gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCbzt() {
        return this.cbzt;
    }

    public void setCbzt(String cbzt) {
        this.cbzt = cbzt;
    }

    public String getGONGDANDJ() {
        return this.GONGDANDJ;
    }

    public void setGONGDANDJ(String GONGDANDJ) {
        this.GONGDANDJ = GONGDANDJ;
    }

    public String getGONGDANDJBZ() {
        return this.GONGDANDJBZ;
    }

    public void setGONGDANDJBZ(String GONGDANDJBZ) {
        this.GONGDANDJBZ = GONGDANDJBZ;
    }

    public String getBIAOWU_GZYY() {
        return this.BIAOWU_GZYY;
    }

    public void setBIAOWU_GZYY(String BIAOWU_GZYY) {
        this.BIAOWU_GZYY = BIAOWU_GZYY;
    }

    public String getMOKUAI_GZYY() {
        return this.MOKUAI_GZYY;
    }

    public void setMOKUAI_GZYY(String MOKUAI_GZYY) {
        this.MOKUAI_GZYY = MOKUAI_GZYY;
    }

    public String getSFGHBP() {
        return this.SFGHBP;
    }

    public void setSFGHBP(String SFGHBP) {
        this.SFGHBP = SFGHBP;
    }

    public String getGZLB() {
        return this.GZLB;
    }

    public void setGZLB(String GZLB) {
        this.GZLB = GZLB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.ID);
        dest.writeString(this.detailInfo);
        dest.writeString(this.RENWUID);
        dest.writeString(this.RENWUMC);
        dest.writeString(this.XIAOGENH);
        dest.writeString(this.XUNJIANCM);
        dest.writeString(this.TXM);
        dest.writeString(this.XUNJIANYL);
        dest.writeString(this.YLSM);
        dest.writeString(this.BEIZHU);
        dest.writeString(this.images1);
        dest.writeString(this.images2);
        dest.writeString(this.images3);
        dest.writeString(this.images4);
        dest.writeString(this.images5);
        dest.writeString(this.images6);
        dest.writeString(this.images7);
        dest.writeString(this.vedioUrl);
        dest.writeByte(this.isCommit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSave ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isUploadImage ? (byte) 1 : (byte) 0);
        dest.writeString(this.cbzt);
        dest.writeString(this.GONGDANDJ);
        dest.writeString(this.GONGDANDJBZ);
        dest.writeString(this.BIAOWU_GZYY);
        dest.writeString(this.MOKUAI_GZYY);
        dest.writeString(this.SFGHBP);
        dest.writeString(this.GZLB);
        dest.writeString(this.gpsTime);
        dest.writeString(this.lat);
        dest.writeString(this.lon);
    }

    protected BiaoKaWholeEntity(Parcel in) {
        this.ID = (Long) in.readValue(Long.class.getClassLoader());
        this.detailInfo = in.readString();
        this.RENWUID = in.readString();
        this.RENWUMC = in.readString();
        this.XIAOGENH = in.readString();
        this.XUNJIANCM = in.readString();
        this.TXM = in.readString();
        this.XUNJIANYL = in.readString();
        this.YLSM = in.readString();
        this.BEIZHU = in.readString();
        this.images1 = in.readString();
        this.images2 = in.readString();
        this.images3 = in.readString();
        this.images4 = in.readString();
        this.images5 = in.readString();
        this.images6 = in.readString();
        this.images7 = in.readString();
        this.vedioUrl = in.readString();
        this.isCommit = in.readByte() != 0;
        this.isSave = in.readByte() != 0;
        this.isUploadImage = in.readByte() != 0;
        this.cbzt = in.readString();
        this.GONGDANDJ = in.readString();
        this.GONGDANDJBZ = in.readString();
        this.BIAOWU_GZYY = in.readString();
        this.MOKUAI_GZYY = in.readString();
        this.SFGHBP = in.readString();
        this.GZLB = in.readString();
        this.gpsTime = in.readString();
        this.lat = in.readString();
        this.lon = in.readString();
    }

    public static final Creator<BiaoKaWholeEntity> CREATOR = new Creator<BiaoKaWholeEntity>() {
        @Override
        public BiaoKaWholeEntity createFromParcel(Parcel source) {
            return new BiaoKaWholeEntity(source);
        }

        @Override
        public BiaoKaWholeEntity[] newArray(int size) {
            return new BiaoKaWholeEntity[size];
        }
    };
}
