package com.example.dataprovider3.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CuijiaoEntity implements Parcelable {

  @Id
  private Long Id;
  private String S_CH;
  private String ROWNUM;
  private String S_CID;
  private String S_RENWUID;
  private String S_RENWUMC;
  private String D_PAIFARQ;
  private String S_HM;
  private String S_DZ;
  private String I_QIANFEIZS;
  private String N_SHUIFEI;
  private String N_QIANFEIZJE;
  private String S_QIANFEISJFW;
  private String N_WEIYUEJ;
  private String N_QIANFEIJEWYJ;
  private String S_ST;
  private String S_CEBENH;
  private String S_BIAOKAZT;
  private String S_LIANXIR;
  private String S_LIANXIDH;
  private String S_QIANFEIZS;
  private String S_QIANFEIYY1;
  private String S_QIANFEIYY2;
  private String ZHFFSJ;
  private String ZHFFJE;
  private String ZHKZCM;
  private String ZHKZSL;
  private String ZHKZSJ;
  private String HDDFF;
  private String CJDFF;
  private String CJDHTSJ;
  private String S_SHESHUIID;
  private String S_JH;
  private String S_WEIZHIFL;
  private String S_ANZHUANGWZ;
  private String S_SHUIBIAOTXM;
  private String ISSHIM;
  private String KOUJINGMC;
  private String I_SFFS;
  private String I_DIANZIZD;
  private String D_HUANBIAO;
  private Boolean isDetailMessage;
  @Generated(hash = 923247287)
public CuijiaoEntity(Long Id, String S_CH, String ROWNUM, String S_CID, String S_RENWUID,
                     String S_RENWUMC, String D_PAIFARQ, String S_HM, String S_DZ, String I_QIANFEIZS,
                     String N_SHUIFEI, String N_QIANFEIZJE, String S_QIANFEISJFW, String N_WEIYUEJ,
                     String N_QIANFEIJEWYJ, String S_ST, String S_CEBENH, String S_BIAOKAZT,
                     String S_LIANXIR, String S_LIANXIDH, String S_QIANFEIZS, String S_QIANFEIYY1,
                     String S_QIANFEIYY2, String ZHFFSJ, String ZHFFJE, String ZHKZCM, String ZHKZSL,
                     String ZHKZSJ, String HDDFF, String CJDFF, String CJDHTSJ, String S_SHESHUIID,
                     String S_JH, String S_WEIZHIFL, String S_ANZHUANGWZ, String S_SHUIBIAOTXM,
                     String ISSHIM, String KOUJINGMC, String I_SFFS, String I_DIANZIZD,
                     String D_HUANBIAO, Boolean isDetailMessage) {
    this.Id = Id;
    this.S_CH = S_CH;
    this.ROWNUM = ROWNUM;
    this.S_CID = S_CID;
    this.S_RENWUID = S_RENWUID;
    this.S_RENWUMC = S_RENWUMC;
    this.D_PAIFARQ = D_PAIFARQ;
    this.S_HM = S_HM;
    this.S_DZ = S_DZ;
    this.I_QIANFEIZS = I_QIANFEIZS;
    this.N_SHUIFEI = N_SHUIFEI;
    this.N_QIANFEIZJE = N_QIANFEIZJE;
    this.S_QIANFEISJFW = S_QIANFEISJFW;
    this.N_WEIYUEJ = N_WEIYUEJ;
    this.N_QIANFEIJEWYJ = N_QIANFEIJEWYJ;
    this.S_ST = S_ST;
    this.S_CEBENH = S_CEBENH;
    this.S_BIAOKAZT = S_BIAOKAZT;
    this.S_LIANXIR = S_LIANXIR;
    this.S_LIANXIDH = S_LIANXIDH;
    this.S_QIANFEIZS = S_QIANFEIZS;
    this.S_QIANFEIYY1 = S_QIANFEIYY1;
    this.S_QIANFEIYY2 = S_QIANFEIYY2;
    this.ZHFFSJ = ZHFFSJ;
    this.ZHFFJE = ZHFFJE;
    this.ZHKZCM = ZHKZCM;
    this.ZHKZSL = ZHKZSL;
    this.ZHKZSJ = ZHKZSJ;
    this.HDDFF = HDDFF;
    this.CJDFF = CJDFF;
    this.CJDHTSJ = CJDHTSJ;
    this.S_SHESHUIID = S_SHESHUIID;
    this.S_JH = S_JH;
    this.S_WEIZHIFL = S_WEIZHIFL;
    this.S_ANZHUANGWZ = S_ANZHUANGWZ;
    this.S_SHUIBIAOTXM = S_SHUIBIAOTXM;
    this.ISSHIM = ISSHIM;
    this.KOUJINGMC = KOUJINGMC;
    this.I_SFFS = I_SFFS;
    this.I_DIANZIZD = I_DIANZIZD;
    this.D_HUANBIAO = D_HUANBIAO;
    this.isDetailMessage = isDetailMessage;
}
@Generated(hash = 422089955)
  public CuijiaoEntity() {
  }

  protected CuijiaoEntity(Parcel in) {
    Id = in.readLong();
    ROWNUM = in.readString();
    S_CID = in.readString();
    S_RENWUID = in.readString();
    S_RENWUMC = in.readString();
    D_PAIFARQ = in.readString();
    S_HM = in.readString();
    S_DZ = in.readString();
    I_QIANFEIZS = in.readString();
    N_SHUIFEI = in.readString();
    N_QIANFEIZJE = in.readString();
    S_QIANFEISJFW = in.readString();
    N_WEIYUEJ = in.readString();
    N_QIANFEIJEWYJ = in.readString();
    S_ST = in.readString();
    S_CEBENH = in.readString();
    S_BIAOKAZT = in.readString();
    S_LIANXIR = in.readString();
    S_LIANXIDH = in.readString();
    S_QIANFEIZS = in.readString();
    S_QIANFEIYY1 = in.readString();
    S_QIANFEIYY2 = in.readString();
    ZHFFSJ = in.readString();
    ZHFFJE = in.readString();
    ZHKZCM = in.readString();
    ZHKZSL = in.readString();
    ZHKZSJ = in.readString();
    HDDFF = in.readString();
    CJDFF = in.readString();
    CJDHTSJ = in.readString();
    S_SHESHUIID = in.readString();
    S_JH = in.readString();
    S_WEIZHIFL = in.readString();
    S_ANZHUANGWZ = in.readString();
    S_SHUIBIAOTXM = in.readString();
    ISSHIM = in.readString();
    KOUJINGMC = in.readString();
    I_SFFS = in.readString();
    I_DIANZIZD = in.readString();
    D_HUANBIAO = in.readString();
  }

  public static final Creator<CuijiaoEntity> CREATOR = new Creator<CuijiaoEntity>() {
    @Override
    public CuijiaoEntity createFromParcel(Parcel in) {
      return new CuijiaoEntity(in);
    }

    @Override
    public CuijiaoEntity[] newArray(int size) {
      return new CuijiaoEntity[size];
    }
  };

  public Long getId() {
      return this.Id;
  }
  public String getROWNUM() {
      return this.ROWNUM;
  }
  public void setROWNUM(String ROWNUM) {
      this.ROWNUM = ROWNUM;
  }
  public String getS_CID() {
      return this.S_CID;
  }
  public void setS_CID(String S_CID) {
      this.S_CID = S_CID;
  }
  public String getS_RENWUID() {
      return this.S_RENWUID;
  }
  public void setS_RENWUID(String S_RENWUID) {
      this.S_RENWUID = S_RENWUID;
  }
  public String getS_RENWUMC() {
      return this.S_RENWUMC;
  }
  public void setS_RENWUMC(String S_RENWUMC) {
      this.S_RENWUMC = S_RENWUMC;
  }
  public String getD_PAIFARQ() {
      return this.D_PAIFARQ;
  }
  public void setD_PAIFARQ(String D_PAIFARQ) {
      this.D_PAIFARQ = D_PAIFARQ;
  }
  public String getS_HM() {
      return this.S_HM;
  }
  public void setS_HM(String S_HM) {
      this.S_HM = S_HM;
  }
  public String getS_DZ() {
      return this.S_DZ;
  }
  public void setS_DZ(String S_DZ) {
      this.S_DZ = S_DZ;
  }
  public String getI_QIANFEIZS() {
      return this.I_QIANFEIZS;
  }
  public void setI_QIANFEIZS(String I_QIANFEIZS) {
      this.I_QIANFEIZS = I_QIANFEIZS;
  }
  public String getN_SHUIFEI() {
      return this.N_SHUIFEI;
  }
  public void setN_SHUIFEI(String N_SHUIFEI) {
      this.N_SHUIFEI = N_SHUIFEI;
  }
  public String getN_QIANFEIZJE() {
      return this.N_QIANFEIZJE;
  }
  public void setN_QIANFEIZJE(String N_QIANFEIZJE) {
      this.N_QIANFEIZJE = N_QIANFEIZJE;
  }
  public String getS_QIANFEISJFW() {
      return this.S_QIANFEISJFW;
  }
  public void setS_QIANFEISJFW(String S_QIANFEISJFW) {
      this.S_QIANFEISJFW = S_QIANFEISJFW;
  }
  public String getN_WEIYUEJ() {
      return this.N_WEIYUEJ;
  }
  public void setN_WEIYUEJ(String N_WEIYUEJ) {
      this.N_WEIYUEJ = N_WEIYUEJ;
  }
  public String getN_QIANFEIJEWYJ() {
      return this.N_QIANFEIJEWYJ;
  }
  public void setN_QIANFEIJEWYJ(String N_QIANFEIJEWYJ) {
      this.N_QIANFEIJEWYJ = N_QIANFEIJEWYJ;
  }
  public String getS_ST() {
      return this.S_ST;
  }
  public void setS_ST(String S_ST) {
      this.S_ST = S_ST;
  }
  public String getS_CEBENH() {
      return this.S_CEBENH;
  }
  public void setS_CEBENH(String S_CEBENH) {
      this.S_CEBENH = S_CEBENH;
  }
  public String getS_BIAOKAZT() {
      return this.S_BIAOKAZT;
  }
  public void setS_BIAOKAZT(String S_BIAOKAZT) {
      this.S_BIAOKAZT = S_BIAOKAZT;
  }
  public String getS_LIANXIR() {
      return this.S_LIANXIR;
  }
  public void setS_LIANXIR(String S_LIANXIR) {
      this.S_LIANXIR = S_LIANXIR;
  }
  public String getS_LIANXIDH() {
      return this.S_LIANXIDH;
  }
  public void setS_LIANXIDH(String S_LIANXIDH) {
      this.S_LIANXIDH = S_LIANXIDH;
  }
  public String getS_QIANFEIZS() {
      return this.S_QIANFEIZS;
  }
  public void setS_QIANFEIZS(String S_QIANFEIZS) {
      this.S_QIANFEIZS = S_QIANFEIZS;
  }
  public String getS_QIANFEIYY1() {
      return this.S_QIANFEIYY1;
  }
  public void setS_QIANFEIYY1(String S_QIANFEIYY1) {
      this.S_QIANFEIYY1 = S_QIANFEIYY1;
  }
  public String getS_QIANFEIYY2() {
      return this.S_QIANFEIYY2;
  }
  public void setS_QIANFEIYY2(String S_QIANFEIYY2) {
      this.S_QIANFEIYY2 = S_QIANFEIYY2;
  }
  public String getZHFFSJ() {
      return this.ZHFFSJ;
  }
  public void setZHFFSJ(String ZHFFSJ) {
      this.ZHFFSJ = ZHFFSJ;
  }
  public String getZHFFJE() {
      return this.ZHFFJE;
  }
  public void setZHFFJE(String ZHFFJE) {
      this.ZHFFJE = ZHFFJE;
  }
  public String getZHKZCM() {
      return this.ZHKZCM;
  }
  public void setZHKZCM(String ZHKZCM) {
      this.ZHKZCM = ZHKZCM;
  }
  public String getZHKZSL() {
      return this.ZHKZSL;
  }
  public void setZHKZSL(String ZHKZSL) {
      this.ZHKZSL = ZHKZSL;
  }
  public String getZHKZSJ() {
      return this.ZHKZSJ;
  }
  public void setZHKZSJ(String ZHKZSJ) {
      this.ZHKZSJ = ZHKZSJ;
  }
  public String getHDDFF() {
      return this.HDDFF;
  }
  public void setHDDFF(String HDDFF) {
      this.HDDFF = HDDFF;
  }
  public String getCJDFF() {
      return this.CJDFF;
  }
  public void setCJDFF(String CJDFF) {
      this.CJDFF = CJDFF;
  }
  public String getCJDHTSJ() {
      return this.CJDHTSJ;
  }
  public void setCJDHTSJ(String CJDHTSJ) {
      this.CJDHTSJ = CJDHTSJ;
  }
  public String getS_SHESHUIID() {
      return this.S_SHESHUIID;
  }
  public void setS_SHESHUIID(String S_SHESHUIID) {
      this.S_SHESHUIID = S_SHESHUIID;
  }
  public String getS_JH() {
      return this.S_JH;
  }
  public void setS_JH(String S_JH) {
      this.S_JH = S_JH;
  }
  public String getS_WEIZHIFL() {
      return this.S_WEIZHIFL;
  }
  public void setS_WEIZHIFL(String S_WEIZHIFL) {
      this.S_WEIZHIFL = S_WEIZHIFL;
  }
  public String getS_ANZHUANGWZ() {
      return this.S_ANZHUANGWZ;
  }
  public void setS_ANZHUANGWZ(String S_ANZHUANGWZ) {
      this.S_ANZHUANGWZ = S_ANZHUANGWZ;
  }
  public String getS_SHUIBIAOTXM() {
      return this.S_SHUIBIAOTXM;
  }
  public void setS_SHUIBIAOTXM(String S_SHUIBIAOTXM) {
      this.S_SHUIBIAOTXM = S_SHUIBIAOTXM;
  }
  public String getISSHIM() {
      return this.ISSHIM;
  }
  public void setISSHIM(String ISSHIM) {
      this.ISSHIM = ISSHIM;
  }
  public String getKOUJINGMC() {
      return this.KOUJINGMC;
  }
  public void setKOUJINGMC(String KOUJINGMC) {
      this.KOUJINGMC = KOUJINGMC;
  }
  public String getI_SFFS() {
      return this.I_SFFS;
  }
  public void setI_SFFS(String I_SFFS) {
      this.I_SFFS = I_SFFS;
  }
  public String getI_DIANZIZD() {
      return this.I_DIANZIZD;
  }
  public void setI_DIANZIZD(String I_DIANZIZD) {
      this.I_DIANZIZD = I_DIANZIZD;
  }
  public String getD_HUANBIAO() {
      return this.D_HUANBIAO;
  }
  public void setD_HUANBIAO(String D_HUANBIAO) {
      this.D_HUANBIAO = D_HUANBIAO;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(Id);
    dest.writeString(ROWNUM);
    dest.writeString(S_CID);
    dest.writeString(S_RENWUID);
    dest.writeString(S_RENWUMC);
    dest.writeString(D_PAIFARQ);
    dest.writeString(S_HM);
    dest.writeString(S_DZ);
    dest.writeString(I_QIANFEIZS);
    dest.writeString(N_SHUIFEI);
    dest.writeString(N_QIANFEIZJE);
    dest.writeString(S_QIANFEISJFW);
    dest.writeString(N_WEIYUEJ);
    dest.writeString(N_QIANFEIJEWYJ);
    dest.writeString(S_ST);
    dest.writeString(S_CEBENH);
    dest.writeString(S_BIAOKAZT);
    dest.writeString(S_LIANXIR);
    dest.writeString(S_LIANXIDH);
    dest.writeString(S_QIANFEIZS);
    dest.writeString(S_QIANFEIYY1);
    dest.writeString(S_QIANFEIYY2);
    dest.writeString(ZHFFSJ);
    dest.writeString(ZHFFJE);
    dest.writeString(ZHKZCM);
    dest.writeString(ZHKZSL);
    dest.writeString(ZHKZSJ);
    dest.writeString(HDDFF);
    dest.writeString(CJDFF);
    dest.writeString(CJDHTSJ);
    dest.writeString(S_SHESHUIID);
    dest.writeString(S_JH);
    dest.writeString(S_WEIZHIFL);
    dest.writeString(S_ANZHUANGWZ);
    dest.writeString(S_SHUIBIAOTXM);
    dest.writeString(ISSHIM);
    dest.writeString(KOUJINGMC);
    dest.writeString(I_SFFS);
    dest.writeString(I_DIANZIZD);
    dest.writeString(D_HUANBIAO);
  }
public void setId(Long Id) {
    this.Id = Id;
}
public Boolean getIsDetailMessage() {
    return this.isDetailMessage;
}
public void setIsDetailMessage(Boolean isDetailMessage) {
    this.isDetailMessage = isDetailMessage;
}
public String getS_CH() {
    return this.S_CH;
}
public void setS_CH(String S_CH) {
    this.S_CH = S_CH;
}
}
