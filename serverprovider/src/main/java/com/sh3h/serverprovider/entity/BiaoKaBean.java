package com.sh3h.serverprovider.entity;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/11 17:11
 */
public class BiaoKaBean implements Parcelable {

    /**
     * S_STATE : 1
     * XIAOGENH : 00001
     * S_ST : 00
     * HUMING : 张三1
     * DIZHI : 上海市1
     * BIAOKAZT : 1
     * XUNJIANZT : 1
     * YONGSHUIXZ : 111
     * BIAOHAO : 1
     * ISNB : 1
     * SHANGCICM : 1
     * QIANSANCIPJ : 1
     * CHAOBIAORQ : 2020-03-02 00:00:00
     * CHAOBIAOYL : 1
     */
    private Long ID;
    private String S_STATE;
    private String XIAOGENH;
    private String S_ST;
    private String HUMING;
    private String DIZHI;
    private String BIAOKAZT;
    private String XUNJIANZT;
    private String YONGSHUIXZ;
    private String BIAOHAO;
    private String ISNB;
    private String SHANGCICM;
    private String QIANSANCIPJ;
    private String CHAOBIAORQ;
    private String CHAOBIAOYL;
    private String S_X;
    private String S_Y;
    private String CJMC;
    private String BXMC;
    private String XJLX;

    private String TDYY;
    private String TDBZ;
    private String TDSJ;
    private String TDR;

    private String ISYC;
    private String GDDS_COUNT;
    private String I_SHUIBIAOZL;
    private String D_YCCHAOBIAOSJ;
    private Integer I_YCCHAOMA;



    public BiaoKaBean(Long ID, String S_STATE, String XIAOGENH, String S_ST,
                      String HUMING, String DIZHI, String BIAOKAZT, String XUNJIANZT,
                      String YONGSHUIXZ, String BIAOHAO, String ISNB, String SHANGCICM,
                      String QIANSANCIPJ, String CHAOBIAORQ, String CHAOBIAOYL, String S_X,
                      String S_Y, String CJMC, String BXMC, String XJLX, String TDYY,
                      String TDBZ, String TDSJ, String TDR, String ISYC, String GDDS_COUNT,
                      String I_SHUIBIAOZL, String D_YCCHAOBIAOSJ, Integer I_YCCHAOMA) {
        this.ID = ID;
        this.S_STATE = S_STATE;
        this.XIAOGENH = XIAOGENH;
        this.S_ST = S_ST;
        this.HUMING = HUMING;
        this.DIZHI = DIZHI;
        this.BIAOKAZT = BIAOKAZT;
        this.XUNJIANZT = XUNJIANZT;
        this.YONGSHUIXZ = YONGSHUIXZ;
        this.BIAOHAO = BIAOHAO;
        this.ISNB = ISNB;
        this.SHANGCICM = SHANGCICM;
        this.QIANSANCIPJ = QIANSANCIPJ;
        this.CHAOBIAORQ = CHAOBIAORQ;
        this.CHAOBIAOYL = CHAOBIAOYL;
        this.S_X = S_X;
        this.S_Y = S_Y;
        this.CJMC = CJMC;
        this.BXMC = BXMC;
        this.XJLX = XJLX;
        this.TDYY = TDYY;
        this.TDBZ = TDBZ;
        this.TDSJ = TDSJ;
        this.TDR = TDR;
        this.ISYC = ISYC;
        this.GDDS_COUNT = GDDS_COUNT;
        this.I_SHUIBIAOZL = I_SHUIBIAOZL;
        this.D_YCCHAOBIAOSJ = D_YCCHAOBIAOSJ;
        this.I_YCCHAOMA = I_YCCHAOMA;
    }

    public BiaoKaBean() {
    }

    @Override
    public String toString() {
        return "BiaoKaBean{" +
                "ID=" + ID +
                ", S_STATE='" + S_STATE + '\'' +
                ", XIAOGENH='" + XIAOGENH + '\'' +
                ", S_ST='" + S_ST + '\'' +
                ", HUMING='" + HUMING + '\'' +
                ", DIZHI='" + DIZHI + '\'' +
                ", BIAOKAZT='" + BIAOKAZT + '\'' +
                ", XUNJIANZT='" + XUNJIANZT + '\'' +
                ", YONGSHUIXZ='" + YONGSHUIXZ + '\'' +
                ", BIAOHAO='" + BIAOHAO + '\'' +
                ", ISNB=" + ISNB +
                ", SHANGCICM='" + SHANGCICM + '\'' +
                ", QIANSANCIPJ=" + QIANSANCIPJ +
                ", CHAOBIAORQ='" + CHAOBIAORQ + '\'' +
                ", CHAOBIAOYL=" + CHAOBIAOYL +
                ", S_X='" + S_X + '\'' +
                ", S_Y='" + S_Y + '\'' +
                ", CJMC='" + CJMC + '\'' +
                ", BXMC='" + BXMC + '\'' +
                ", XJLX='" + XJLX + '\'' +
                ", TDYY='" + TDYY + '\'' +
                ", TDBZ='" + TDBZ + '\'' +
                ", TDSJ='" + TDSJ + '\'' +
                ", TDR='" + TDR + '\'' +
                ", D_YCCHAOBIAOSJ='" + D_YCCHAOBIAOSJ + '\'' +
                ", I_YCCHAOMA='" + I_YCCHAOMA + '\'' +
                '}';
    }

  public Long getID() {
    return ID;
  }

  public void setID(Long ID) {
    this.ID = ID;
  }

  public String getS_STATE() {
    return S_STATE;
  }

  public void setS_STATE(String s_STATE) {
    S_STATE = s_STATE;
  }

  public String getXIAOGENH() {
    return XIAOGENH;
  }

  public void setXIAOGENH(String XIAOGENH) {
    this.XIAOGENH = XIAOGENH;
  }

  public String getS_ST() {
    return S_ST;
  }

  public void setS_ST(String s_ST) {
    S_ST = s_ST;
  }

  public String getHUMING() {
    return HUMING;
  }

  public void setHUMING(String HUMING) {
    this.HUMING = HUMING;
  }

  public String getDIZHI() {
    return DIZHI;
  }

  public void setDIZHI(String DIZHI) {
    this.DIZHI = DIZHI;
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

  public String getYONGSHUIXZ() {
    return YONGSHUIXZ;
  }

  public void setYONGSHUIXZ(String YONGSHUIXZ) {
    this.YONGSHUIXZ = YONGSHUIXZ;
  }

  public String getBIAOHAO() {
    return BIAOHAO;
  }

  public void setBIAOHAO(String BIAOHAO) {
    this.BIAOHAO = BIAOHAO;
  }

  public String getISNB() {
    return ISNB;
  }

  public void setISNB(String ISNB) {
    this.ISNB = ISNB;
  }

  public String getSHANGCICM() {
    return SHANGCICM;
  }

  public void setSHANGCICM(String SHANGCICM) {
    this.SHANGCICM = SHANGCICM;
  }

  public String getQIANSANCIPJ() {
    return QIANSANCIPJ;
  }

  public void setQIANSANCIPJ(String QIANSANCIPJ) {
    this.QIANSANCIPJ = QIANSANCIPJ;
  }

  public String getCHAOBIAORQ() {
    return CHAOBIAORQ;
  }

  public void setCHAOBIAORQ(String CHAOBIAORQ) {
    this.CHAOBIAORQ = CHAOBIAORQ;
  }

  public String getCHAOBIAOYL() {
    return CHAOBIAOYL;
  }

  public void setCHAOBIAOYL(String CHAOBIAOYL) {
    this.CHAOBIAOYL = CHAOBIAOYL;
  }

  public String getS_X() {
    return S_X;
  }

  public void setS_X(String s_X) {
    S_X = s_X;
  }

  public String getS_Y() {
    return S_Y;
  }

  public void setS_Y(String s_Y) {
    S_Y = s_Y;
  }

  public String getCJMC() {
    return CJMC;
  }

  public void setCJMC(String CJMC) {
    this.CJMC = CJMC;
  }

  public String getBXMC() {
    return BXMC;
  }

  public void setBXMC(String BXMC) {
    this.BXMC = BXMC;
  }

  public String getXJLX() {
    return XJLX;
  }

  public void setXJLX(String XJLX) {
    this.XJLX = XJLX;
  }

  public String getTDYY() {
    return TDYY;
  }

  public void setTDYY(String TDYY) {
    this.TDYY = TDYY;
  }

  public String getTDBZ() {
    return TDBZ;
  }

  public void setTDBZ(String TDBZ) {
    this.TDBZ = TDBZ;
  }

  public String getTDSJ() {
    return TDSJ;
  }

  public void setTDSJ(String TDSJ) {
    this.TDSJ = TDSJ;
  }

  public String getTDR() {
    return TDR;
  }

  public void setTDR(String TDR) {
    this.TDR = TDR;
  }

  public String getISYC() {
    return ISYC;
  }

  public void setISYC(String ISYC) {
    this.ISYC = ISYC;
  }

  public String getGDDS_COUNT() {
    return GDDS_COUNT;
  }

  public void setGDDS_COUNT(String GDDS_COUNT) {
    this.GDDS_COUNT = GDDS_COUNT;
  }

  public String getI_SHUIBIAOZL() {
    return I_SHUIBIAOZL;
  }

  public void setI_SHUIBIAOZL(String i_SHUIBIAOZL) {
    I_SHUIBIAOZL = i_SHUIBIAOZL;
  }

    public String getD_YCCHAOBIAOSJ() {
        return D_YCCHAOBIAOSJ;
    }

    public void setD_YCCHAOBIAOSJ(String d_YCCHAOBIAOSJ) {
        D_YCCHAOBIAOSJ = d_YCCHAOBIAOSJ;
    }

    public Integer getI_YCCHAOMA() {
        return I_YCCHAOMA;
    }

    public void setI_YCCHAOMA(Integer i_YCCHAOMA) {
        I_YCCHAOMA = i_YCCHAOMA;
    }

    public static Creator<BiaoKaBean> getCREATOR() {
    return CREATOR;
  }

  @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.ID);
        dest.writeString(this.S_STATE);
        dest.writeString(this.XIAOGENH);
        dest.writeString(this.S_ST);
        dest.writeString(this.HUMING);
        dest.writeString(this.DIZHI);
        dest.writeString(this.BIAOKAZT);
        dest.writeString(this.XUNJIANZT);
        dest.writeString(this.YONGSHUIXZ);
        dest.writeString(this.BIAOHAO);
        dest.writeString(this.ISNB);
        dest.writeString(this.SHANGCICM);
        dest.writeString(this.QIANSANCIPJ);
        dest.writeString(this.CHAOBIAORQ);
        dest.writeString(this.CHAOBIAOYL);
        dest.writeString(this.S_X);
        dest.writeString(this.S_Y);
        dest.writeString(this.CJMC);
        dest.writeString(this.BXMC);
        dest.writeString(this.XJLX);
        dest.writeString(this.TDYY);
        dest.writeString(this.TDBZ);
        dest.writeString(this.TDSJ);
        dest.writeString(this.TDR);
        dest.writeString(this.ISYC);
        dest.writeString(this.GDDS_COUNT);
        dest.writeString(this.I_SHUIBIAOZL);
        dest.writeString(this.D_YCCHAOBIAOSJ);
        dest.writeInt(this.I_YCCHAOMA);
    }

    protected BiaoKaBean(Parcel in) {
        this.ID = (Long) in.readValue(Long.class.getClassLoader());
        this.S_STATE = in.readString();
        this.XIAOGENH = in.readString();
        this.S_ST = in.readString();
        this.HUMING = in.readString();
        this.DIZHI = in.readString();
        this.BIAOKAZT = in.readString();
        this.XUNJIANZT = in.readString();
        this.YONGSHUIXZ = in.readString();
        this.BIAOHAO = in.readString();
        this.ISNB = in.readString();
        this.SHANGCICM = in.readString();
        this.QIANSANCIPJ = in.readString();
        this.CHAOBIAORQ = in.readString();
        this.CHAOBIAOYL = in.readString();
        this.S_X = in.readString();
        this.S_Y = in.readString();
        this.CJMC = in.readString();
        this.BXMC = in.readString();
        this.XJLX = in.readString();
        this.TDYY = in.readString();
        this.TDBZ = in.readString();
        this.TDSJ = in.readString();
        this.TDR = in.readString();
        this.ISYC = in.readString();
        this.GDDS_COUNT = in.readString();
        this.I_SHUIBIAOZL = in.readString();
        this.D_YCCHAOBIAOSJ = in.readString();
        this.I_YCCHAOMA = in.readInt();
    }

    public static final Creator<BiaoKaBean> CREATOR = new Creator<BiaoKaBean>() {
        @Override
        public BiaoKaBean createFromParcel(Parcel source) {
            return new BiaoKaBean(source);
        }

        @Override
        public BiaoKaBean[] newArray(int size) {
            return new BiaoKaBean[size];
        }
    };
}
