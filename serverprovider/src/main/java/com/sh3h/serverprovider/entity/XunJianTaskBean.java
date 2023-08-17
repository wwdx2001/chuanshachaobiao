package com.sh3h.serverprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 巡检任务
 *
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/11 16:12
 */
public class XunJianTaskBean {

    /**
     * RENWUMC : 测试2
     * PAIFASJ : 2020-03-02 00:00:00
     * ZONGHS : 10
     * YIWANC : 5
     */
    private Long ID;
    private String RENWUMC;
    private String PAIFASJ;
    private int ZONGHS;
    private int YIWANC;
    private String xunjianTaskType = "";
    private String XJLX;

    private boolean isFinish;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getRENWUMC() {
        return RENWUMC;
    }

    public void setRENWUMC(String RENWUMC) {
        this.RENWUMC = RENWUMC;
    }

    public String getPAIFASJ() {
        return PAIFASJ;
    }

    public void setPAIFASJ(String PAIFASJ) {
        this.PAIFASJ = PAIFASJ;
    }

    public int getZONGHS() {
        return ZONGHS;
    }

    public void setZONGHS(int ZONGHS) {
        this.ZONGHS = ZONGHS;
    }

    public int getYIWANC() {
        return YIWANC;
    }

    public void setYIWANC(int YIWANC) {
        this.YIWANC = YIWANC;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean finish) {
        isFinish = finish;
    }

    public String getXunjianTaskType() {
        return this.xunjianTaskType;
    }

    public void setXunjianTaskType(String xunjianTaskType) {
        this.xunjianTaskType = xunjianTaskType;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(this.ID);
//        dest.writeString(this.RENWUMC);
//        dest.writeString(this.PAIFASJ);
//        dest.writeString(this.XJLX);
//        dest.writeInt(this.ZONGHS);
//        dest.writeInt(this.YIWANC);
//        dest.writeString(this.xunjianTaskType);
//        dest.writeByte(this.isFinish ? (byte) 1 : (byte) 0);
//    }

    protected XunJianTaskBean(Parcel in) {
        this.ID = (Long) in.readValue(Long.class.getClassLoader());
        this.RENWUMC = in.readString();
        this.PAIFASJ = in.readString();
        this.XJLX = in.readString();
        this.ZONGHS = in.readInt();
        this.YIWANC = in.readInt();
        this.xunjianTaskType = in.readString();
        this.isFinish = in.readByte() != 0;
    }

    public XunJianTaskBean(Long ID, String RENWUMC, String PAIFASJ, int ZONGHS, int YIWANC,
                           String xunjianTaskType, String XJLX, boolean isFinish) {
        this.ID = ID;
        this.RENWUMC = RENWUMC;
        this.PAIFASJ = PAIFASJ;
        this.ZONGHS = ZONGHS;
        this.YIWANC = YIWANC;
        this.xunjianTaskType = xunjianTaskType;
        this.XJLX = XJLX;
        this.isFinish = isFinish;
    }

    public XunJianTaskBean() {
    }

    public static final Parcelable.Creator<XunJianTaskBean> CREATOR = new Parcelable.Creator<XunJianTaskBean>() {
        @Override
        public XunJianTaskBean createFromParcel(Parcel source) {
            return new XunJianTaskBean(source);
        }

        @Override
        public XunJianTaskBean[] newArray(int size) {
            return new XunJianTaskBean[size];
        }
    };

    @Override
    public String toString() {
        return "XunJianTaskBean{" +
                "ID=" + ID +
                ", RENWUMC='" + RENWUMC + '\'' +
                ", PAIFASJ='" + PAIFASJ + '\'' +
                ", ZONGHS=" + ZONGHS +
                ", YIWANC=" + YIWANC +
                ", xunjianTaskType='" + xunjianTaskType + '\'' +
                ", XJLX='" + XJLX + '\'' +
                ", isFinish=" + isFinish +
                '}';
    }

    public String getXJLX() {
        return this.XJLX;
    }

    public void setXJLX(String XJLX) {
        this.XJLX = XJLX;
    }
}
