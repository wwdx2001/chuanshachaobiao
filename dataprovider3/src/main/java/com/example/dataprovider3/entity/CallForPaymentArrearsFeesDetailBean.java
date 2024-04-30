package com.example.dataprovider3.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CallForPaymentArrearsFeesDetailBean {


  /**
   * I_ZHANGWUNY : 202107
   * N_SHUIFEI : 1.92
   * N_PAISHUIF : 1.53
   * N_JE : 3.5
   * N_WEIYUEJ : 1.8
   * D_KAIZHANG : 2021-07-17
   */

  @Id
  private Long Id;
  private String S_CID;
  private String S_RENWUID;
  private String I_ZHANGWUNY;
  private String N_SHUIFEI;
  private String N_PAISHUIF;
  private String N_JE;
  private String N_WEIYUEJ;
  private String D_KAIZHANG;

  @Generated(hash = 1468183785)
  public CallForPaymentArrearsFeesDetailBean(Long Id, String S_CID, String S_RENWUID, String I_ZHANGWUNY, String N_SHUIFEI, String N_PAISHUIF,
                                             String N_JE, String N_WEIYUEJ, String D_KAIZHANG) {
      this.Id = Id;
      this.S_CID = S_CID;
      this.S_RENWUID = S_RENWUID;
      this.I_ZHANGWUNY = I_ZHANGWUNY;
      this.N_SHUIFEI = N_SHUIFEI;
      this.N_PAISHUIF = N_PAISHUIF;
      this.N_JE = N_JE;
      this.N_WEIYUEJ = N_WEIYUEJ;
      this.D_KAIZHANG = D_KAIZHANG;
  }

  @Generated(hash = 1509448127)
  public CallForPaymentArrearsFeesDetailBean() {
  }

  public String getI_ZHANGWUNY() {
    return I_ZHANGWUNY;
  }

  public void setI_ZHANGWUNY(String i_ZHANGWUNY) {
    I_ZHANGWUNY = i_ZHANGWUNY;
  }

  public String getN_SHUIFEI() {
    return N_SHUIFEI;
  }

  public void setN_SHUIFEI(String n_SHUIFEI) {
    N_SHUIFEI = n_SHUIFEI;
  }

  public String getN_PAISHUIF() {
    return N_PAISHUIF;
  }

  public void setN_PAISHUIF(String n_PAISHUIF) {
    N_PAISHUIF = n_PAISHUIF;
  }

  public String getN_JE() {
    return N_JE;
  }

  public void setN_JE(String n_JE) {
    N_JE = n_JE;
  }

  public String getN_WEIYUEJ() {
    return N_WEIYUEJ;
  }

  public void setN_WEIYUEJ(String n_WEIYUEJ) {
    N_WEIYUEJ = n_WEIYUEJ;
  }

  public String getD_KAIZHANG() {
    return D_KAIZHANG;
  }

  public void setD_KAIZHANG(String d_KAIZHANG) {
    D_KAIZHANG = d_KAIZHANG;
  }

  public Long getId() {
      return this.Id;
  }

  public void setId(Long Id) {
      this.Id = Id;
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
}
