package com.example.dataprovider3.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CallForPaymentOrderDetailBean implements Parcelable {



  /**
   * S_RENWUID : CJ000000093
   * S_RENWUMC : 催缴任务KF17120240318151125
   * S_CID : 949028177
   * D_PAIFARQ : 2024/3/18 15:11:39
   * S_ST : 94
   * S_CEBENH : 554411
   * S_HM : 上海新中杨房地产有限公司
   * S_DZ : 浦明路688弄9号1601室
   * S_BIAOKAZT : 正常
   * S_LIANXIR :
   * S_LIANXIDH :
   * S_QIANFEIZS : 1
   * S_QIANFEISJFW : 202107-202107
   * N_SHUIFEI : 1.92
   * N_QIANFEIZJE : 3.5
   * N_WEIYUEJ : 1.8
   * N_QIANFEIJEWYJ : 5.3
   * S_QIANFEIYY1 :
   * S_QIANFEIYY2 :
   * ZHFFSJ :
   * ZHFFJE :
   * ZHKZCM : 12
   * ZHKZSL : 0
   * ZHKZSJ :
   * HDDFF : 0
   * CJDFF : 0
   * CJDHTSJ :
   * S_SHESHUIID : 1040
   * S_JH : 13
   * S_WEIZHIFL : 室外龙头
   * S_ANZHUANGWZ :
   * S_SHUIBIAOTXM : 20231808028222
   * ISSHIM : 是
   * KOUJINGMC : DN25
   * I_SFFS : 现金
   * I_DIANZIZD : 否
   * D_HUANBIAO : 2019/10/10 10:52:39
   */

  private String S_RENWUID;
  private String S_RENWUMC;
  private String S_CID;
  private String D_PAIFARQ;
  private String S_ST;
  private String S_CEBENH;
  private String S_HM;
  private String S_DZ;
  private String S_BIAOKAZT;
  private String S_LIANXIR;
  private String S_LIANXIDH;
  private String S_QIANFEIZS;
  private String S_QIANFEISJFW;
  private String N_SHUIFEI;
  private String N_QIANFEIZJE;
  private String N_WEIYUEJ;
  private String N_QIANFEIJEWYJ;
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

  public CallForPaymentOrderDetailBean(String s_RENWUID, String s_RENWUMC, String s_CID, String d_PAIFARQ,
                                       String s_ST, String s_CEBENH, String s_HM, String s_DZ, String s_BIAOKAZT,
                                       String s_LIANXIR, String s_LIANXIDH, String s_QIANFEIZS, String s_QIANFEISJFW,
                                       String n_SHUIFEI, String n_QIANFEIZJE, String n_WEIYUEJ, String n_QIANFEIJEWYJ,
                                       String s_QIANFEIYY1, String s_QIANFEIYY2, String ZHFFSJ, String ZHFFJE,
                                       String ZHKZCM, String ZHKZSL, String ZHKZSJ, String HDDFF, String CJDFF,
                                       String CJDHTSJ, String s_SHESHUIID, String s_JH, String s_WEIZHIFL,
                                       String s_ANZHUANGWZ, String s_SHUIBIAOTXM, String ISSHIM, String KOUJINGMC,
                                       String i_SFFS, String i_DIANZIZD, String d_HUANBIAO) {
    S_RENWUID = s_RENWUID;
    S_RENWUMC = s_RENWUMC;
    S_CID = s_CID;
    D_PAIFARQ = d_PAIFARQ;
    S_ST = s_ST;
    S_CEBENH = s_CEBENH;
    S_HM = s_HM;
    S_DZ = s_DZ;
    S_BIAOKAZT = s_BIAOKAZT;
    S_LIANXIR = s_LIANXIR;
    S_LIANXIDH = s_LIANXIDH;
    S_QIANFEIZS = s_QIANFEIZS;
    S_QIANFEISJFW = s_QIANFEISJFW;
    N_SHUIFEI = n_SHUIFEI;
    N_QIANFEIZJE = n_QIANFEIZJE;
    N_WEIYUEJ = n_WEIYUEJ;
    N_QIANFEIJEWYJ = n_QIANFEIJEWYJ;
    S_QIANFEIYY1 = s_QIANFEIYY1;
    S_QIANFEIYY2 = s_QIANFEIYY2;
    this.ZHFFSJ = ZHFFSJ;
    this.ZHFFJE = ZHFFJE;
    this.ZHKZCM = ZHKZCM;
    this.ZHKZSL = ZHKZSL;
    this.ZHKZSJ = ZHKZSJ;
    this.HDDFF = HDDFF;
    this.CJDFF = CJDFF;
    this.CJDHTSJ = CJDHTSJ;
    S_SHESHUIID = s_SHESHUIID;
    S_JH = s_JH;
    S_WEIZHIFL = s_WEIZHIFL;
    S_ANZHUANGWZ = s_ANZHUANGWZ;
    S_SHUIBIAOTXM = s_SHUIBIAOTXM;
    this.ISSHIM = ISSHIM;
    this.KOUJINGMC = KOUJINGMC;
    I_SFFS = i_SFFS;
    I_DIANZIZD = i_DIANZIZD;
    D_HUANBIAO = d_HUANBIAO;
  }

  protected CallForPaymentOrderDetailBean(Parcel in) {
    S_RENWUID = in.readString();
    S_RENWUMC = in.readString();
    S_CID = in.readString();
    D_PAIFARQ = in.readString();
    S_ST = in.readString();
    S_CEBENH = in.readString();
    S_HM = in.readString();
    S_DZ = in.readString();
    S_BIAOKAZT = in.readString();
    S_LIANXIR = in.readString();
    S_LIANXIDH = in.readString();
    S_QIANFEIZS = in.readString();
    S_QIANFEISJFW = in.readString();
    N_SHUIFEI = in.readString();
    N_QIANFEIZJE = in.readString();
    N_WEIYUEJ = in.readString();
    N_QIANFEIJEWYJ = in.readString();
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

  public static final Creator<CallForPaymentOrderDetailBean> CREATOR = new Creator<CallForPaymentOrderDetailBean>() {
    @Override
    public CallForPaymentOrderDetailBean createFromParcel(Parcel in) {
      return new CallForPaymentOrderDetailBean(in);
    }

    @Override
    public CallForPaymentOrderDetailBean[] newArray(int size) {
      return new CallForPaymentOrderDetailBean[size];
    }
  };

  public String getS_RENWUID() {
    return S_RENWUID;
  }

  public void setS_RENWUID(String S_RENWUID) {
    this.S_RENWUID = S_RENWUID;
  }

  public String getS_RENWUMC() {
    return S_RENWUMC;
  }

  public void setS_RENWUMC(String S_RENWUMC) {
    this.S_RENWUMC = S_RENWUMC;
  }

  public String getS_CID() {
    return S_CID;
  }

  public void setS_CID(String S_CID) {
    this.S_CID = S_CID;
  }

  public String getD_PAIFARQ() {
    return D_PAIFARQ;
  }

  public void setD_PAIFARQ(String D_PAIFARQ) {
    this.D_PAIFARQ = D_PAIFARQ;
  }

  public String getS_ST() {
    return S_ST;
  }

  public void setS_ST(String S_ST) {
    this.S_ST = S_ST;
  }

  public String getS_CEBENH() {
    return S_CEBENH;
  }

  public void setS_CEBENH(String S_CEBENH) {
    this.S_CEBENH = S_CEBENH;
  }

  public String getS_HM() {
    return S_HM;
  }

  public void setS_HM(String S_HM) {
    this.S_HM = S_HM;
  }

  public String getS_DZ() {
    return S_DZ;
  }

  public void setS_DZ(String S_DZ) {
    this.S_DZ = S_DZ;
  }

  public String getS_BIAOKAZT() {
    return S_BIAOKAZT;
  }

  public void setS_BIAOKAZT(String S_BIAOKAZT) {
    this.S_BIAOKAZT = S_BIAOKAZT;
  }

  public String getS_LIANXIR() {
    return S_LIANXIR;
  }

  public void setS_LIANXIR(String S_LIANXIR) {
    this.S_LIANXIR = S_LIANXIR;
  }

  public String getS_LIANXIDH() {
    return S_LIANXIDH;
  }

  public void setS_LIANXIDH(String S_LIANXIDH) {
    this.S_LIANXIDH = S_LIANXIDH;
  }

  public String getS_QIANFEIZS() {
    return S_QIANFEIZS;
  }

  public void setS_QIANFEIZS(String S_QIANFEIZS) {
    this.S_QIANFEIZS = S_QIANFEIZS;
  }

  public String getS_QIANFEISJFW() {
    return S_QIANFEISJFW;
  }

  public void setS_QIANFEISJFW(String S_QIANFEISJFW) {
    this.S_QIANFEISJFW = S_QIANFEISJFW;
  }

  public String getN_SHUIFEI() {
    return N_SHUIFEI;
  }

  public void setN_SHUIFEI(String N_SHUIFEI) {
    this.N_SHUIFEI = N_SHUIFEI;
  }

  public String getN_QIANFEIZJE() {
    return N_QIANFEIZJE;
  }

  public void setN_QIANFEIZJE(String N_QIANFEIZJE) {
    this.N_QIANFEIZJE = N_QIANFEIZJE;
  }

  public String getN_WEIYUEJ() {
    return N_WEIYUEJ;
  }

  public void setN_WEIYUEJ(String N_WEIYUEJ) {
    this.N_WEIYUEJ = N_WEIYUEJ;
  }

  public String getN_QIANFEIJEWYJ() {
    return N_QIANFEIJEWYJ;
  }

  public void setN_QIANFEIJEWYJ(String N_QIANFEIJEWYJ) {
    this.N_QIANFEIJEWYJ = N_QIANFEIJEWYJ;
  }

  public String getS_QIANFEIYY1() {
    return S_QIANFEIYY1;
  }

  public void setS_QIANFEIYY1(String S_QIANFEIYY1) {
    this.S_QIANFEIYY1 = S_QIANFEIYY1;
  }

  public String getS_QIANFEIYY2() {
    return S_QIANFEIYY2;
  }

  public void setS_QIANFEIYY2(String S_QIANFEIYY2) {
    this.S_QIANFEIYY2 = S_QIANFEIYY2;
  }

  public String getZHFFSJ() {
    return ZHFFSJ;
  }

  public void setZHFFSJ(String ZHFFSJ) {
    this.ZHFFSJ = ZHFFSJ;
  }

  public String getZHFFJE() {
    return ZHFFJE;
  }

  public void setZHFFJE(String ZHFFJE) {
    this.ZHFFJE = ZHFFJE;
  }

  public String getZHKZCM() {
    return ZHKZCM;
  }

  public void setZHKZCM(String ZHKZCM) {
    this.ZHKZCM = ZHKZCM;
  }

  public String getZHKZSL() {
    return ZHKZSL;
  }

  public void setZHKZSL(String ZHKZSL) {
    this.ZHKZSL = ZHKZSL;
  }

  public String getZHKZSJ() {
    return ZHKZSJ;
  }

  public void setZHKZSJ(String ZHKZSJ) {
    this.ZHKZSJ = ZHKZSJ;
  }

  public String getHDDFF() {
    return HDDFF;
  }

  public void setHDDFF(String HDDFF) {
    this.HDDFF = HDDFF;
  }

  public String getCJDFF() {
    return CJDFF;
  }

  public void setCJDFF(String CJDFF) {
    this.CJDFF = CJDFF;
  }

  public String getCJDHTSJ() {
    return CJDHTSJ;
  }

  public void setCJDHTSJ(String CJDHTSJ) {
    this.CJDHTSJ = CJDHTSJ;
  }

  public String getS_SHESHUIID() {
    return S_SHESHUIID;
  }

  public void setS_SHESHUIID(String S_SHESHUIID) {
    this.S_SHESHUIID = S_SHESHUIID;
  }

  public String getS_JH() {
    return S_JH;
  }

  public void setS_JH(String S_JH) {
    this.S_JH = S_JH;
  }

  public String getS_WEIZHIFL() {
    return S_WEIZHIFL;
  }

  public void setS_WEIZHIFL(String S_WEIZHIFL) {
    this.S_WEIZHIFL = S_WEIZHIFL;
  }

  public String getS_ANZHUANGWZ() {
    return S_ANZHUANGWZ;
  }

  public void setS_ANZHUANGWZ(String S_ANZHUANGWZ) {
    this.S_ANZHUANGWZ = S_ANZHUANGWZ;
  }

  public String getS_SHUIBIAOTXM() {
    return S_SHUIBIAOTXM;
  }

  public void setS_SHUIBIAOTXM(String S_SHUIBIAOTXM) {
    this.S_SHUIBIAOTXM = S_SHUIBIAOTXM;
  }

  public String getISSHIM() {
    return ISSHIM;
  }

  public void setISSHIM(String ISSHIM) {
    this.ISSHIM = ISSHIM;
  }

  public String getKOUJINGMC() {
    return KOUJINGMC;
  }

  public void setKOUJINGMC(String KOUJINGMC) {
    this.KOUJINGMC = KOUJINGMC;
  }

  public String getI_SFFS() {
    return I_SFFS;
  }

  public void setI_SFFS(String I_SFFS) {
    this.I_SFFS = I_SFFS;
  }

  public String getI_DIANZIZD() {
    return I_DIANZIZD;
  }

  public void setI_DIANZIZD(String I_DIANZIZD) {
    this.I_DIANZIZD = I_DIANZIZD;
  }

  public String getD_HUANBIAO() {
    return D_HUANBIAO;
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
    dest.writeString(S_RENWUID);
    dest.writeString(S_RENWUMC);
    dest.writeString(S_CID);
    dest.writeString(D_PAIFARQ);
    dest.writeString(S_ST);
    dest.writeString(S_CEBENH);
    dest.writeString(S_HM);
    dest.writeString(S_DZ);
    dest.writeString(S_BIAOKAZT);
    dest.writeString(S_LIANXIR);
    dest.writeString(S_LIANXIDH);
    dest.writeString(S_QIANFEIZS);
    dest.writeString(S_QIANFEISJFW);
    dest.writeString(N_SHUIFEI);
    dest.writeString(N_QIANFEIZJE);
    dest.writeString(N_WEIYUEJ);
    dest.writeString(N_QIANFEIJEWYJ);
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


}
